package com.p1.q1.service;

import com.p1.q1.model.WeatherForecast;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Service
public class WeatherService {

    @Autowired
    private ResourceLoader resourceLoader;

    private static final String EXCEL_PATH = "classpath:city-coordinates.xlsx";

    public Map<String, Double> getCoordinatesForCity(String cityName) {
        Map<String, Double> map = new HashMap<>();
        try {
            Resource resource = resourceLoader.getResource(EXCEL_PATH);
            try (InputStream is = resource.getInputStream()) {
                Workbook workbook = WorkbookFactory.create(is);
                Sheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue; // Skip header
                    String city = row.getCell(0).getStringCellValue().trim();
                    if (city.equalsIgnoreCase(cityName)) {
                        double lat = row.getCell(1).getNumericCellValue();
                        double lon = row.getCell(2).getNumericCellValue();
                        map.put("lat", lat);
                        map.put("lon", lon);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel: " + e.getMessage());
        }

        if (map.isEmpty()) {
            throw new RuntimeException("City not found: " + cityName);
        }

        return map;
    }

    public List<WeatherForecast> getWeatherForecast(String city) {
        Map<String, Double> coord = getCoordinatesForCity(city);

        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&daily=temperature_2m_max,relative_humidity_2m_max,wind_speed_10m_max&timezone=auto",
                coord.get("lat"), coord.get("lon"));

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> response = responseEntity.getBody();
        if (response == null || !response.containsKey("daily")) {
            throw new RuntimeException("Invalid API response");
        }

        Object dailyObj = response.get("daily");
        if (!(dailyObj instanceof Map<?, ?> dailyRaw)) {
            throw new RuntimeException("Invalid structure: 'daily' is not a map");
        }

        Map<String, Object> daily = new HashMap<>();
        for (Map.Entry<?, ?> entry : dailyRaw.entrySet()) {
            if (entry.getKey() instanceof String key) {
                daily.put(key, entry.getValue());
            }
        }

        // Extract dates
        List<?> dateListRaw = (List<?>) daily.get("time");
        List<LocalDate> dates = new ArrayList<>();
        for (Object obj : dateListRaw) {
            if (obj instanceof String dateStr) {
                dates.add(LocalDate.parse(dateStr));
            }
        }

        // Extract temperatures
        List<?> tempListRaw = (List<?>) daily.get("temperature_2m_max");
        List<Double> temps = new ArrayList<>();
        for (Object obj : tempListRaw) {
            if (obj instanceof Number num) {
                temps.add(num.doubleValue());
            }
        }

        // Extract humidity
        List<?> humidityListRaw = (List<?>) daily.get("relative_humidity_2m_max");
        List<Double> humidity = new ArrayList<>();
        for (Object obj : humidityListRaw) {
            if (obj instanceof Number num) {
                humidity.add(num.doubleValue());
            }
        }

        // Extract wind speed
        List<?> windListRaw = (List<?>) daily.get("wind_speed_10m_max");
        List<Double> wind = new ArrayList<>();
        for (Object obj : windListRaw) {
            if (obj instanceof Number num) {
                wind.add(num.doubleValue());
            }
        }

        // Build WeatherForecast list
        List<WeatherForecast> forecasts = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            forecasts.add(new WeatherForecast(
                    dates.get(i),
                    i < temps.size() ? temps.get(i) : 0.0,
                    i < humidity.size() ? humidity.get(i) : 0.0,
                    i < wind.size() ? wind.get(i) : 0.0
            ));
        }

        return forecasts;
    }

    public Map<String, List<WeatherForecast>> compareCities(String city1, String city2) {
        Map<String, List<WeatherForecast>> result = new HashMap<>();
        result.put(city1, getWeatherForecast(city1));
        result.put(city2, getWeatherForecast(city2));
        return result;
    }
}
