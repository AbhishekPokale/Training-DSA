package Threading;

import java.util.List;
import java.util.concurrent.CompletableFuture;

//
//
//public class Practice {
//    public static void main(String[] args) {
//        CompletableFuture<String> asyncData = fetchDataAsync();
//
//        System.out.println("Doing other work while data is being fetched");
//
//
//        asyncData.thenAccept(data -> {
//            System.out.println("Received async data: " + data);
//        }).join();
//
//
//
//
//    }
//
//    static CompletableFuture<String> fetchDataAsync() {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
//            return "Async data stored!";
//        });
//    }
//}








import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class Q {
    public static void main(String[] args) throws Exception {
        String filePath = "Threading/file.csv";
        List<String> symbolsToFetch = List.of(
                "AAPL", "GOOGL", "MSFT", "AMZN", "TSLA",
                "NFLX", "META", "NVDA", "BABA", "INTC"
        );

        Map<String, Double> stockData = loadStockData(filePath);

        int numThreads = 10;
        List<List<String>> parts = partition(symbolsToFetch, numThreads);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<CompletableFuture<Map<String, Double>>> futures = parts.stream()
                .map(part -> CompletableFuture.supplyAsync(() -> {
                    Map<String, Double> result = new HashMap<>();
                    for (String symbol : part) {
                        if (stockData.containsKey(symbol)) {
                            result.put(symbol, stockData.get(symbol));
                        }
                    }
                    return result;
                }, executor))
                .collect(Collectors.toList());

        CompletableFuture<Void> allDone = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        CompletableFuture<Map<String, Double>> finalResultFuture = allDone.thenApply(v -> {
            Map<String, Double> combined = new HashMap<>();
            for (CompletableFuture<Map<String, Double>> f : futures) {
                combined.putAll(f.join());
            }
            return combined;
        });

        Map<String, Double> finalResult = finalResultFuture.get();
        executor.shutdown();
        finalResult.forEach((k, v) -> System.out.println(k + " -> " + v));
    }


    //storing data
    static Map<String, Double> loadStockData(String file) throws IOException {
        Map<String, Double> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                map.put(parts[0].trim(), Double.parseDouble(parts[1].trim()));
            }
        }
        return map;
    }

    static <T> List<List<T>> partition(List<T> list, int n) {
        int size = (int) Math.ceil((double) list.size() / n);
        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            result.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return result;
    }
}
