package com.p1.q1.controller;

import com.p1.q1.model.WeatherForecast;
import com.p1.q1.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public List<WeatherForecast> getForecast(@PathVariable String city) {
        return weatherService.getWeatherForecast(city);
    }

    @GetMapping("/compare")
    public Map<String, List<WeatherForecast>> compareCities(
            @RequestParam String city1,
            @RequestParam String city2) {
        return weatherService.compareCities(city1, city2);
    }
}