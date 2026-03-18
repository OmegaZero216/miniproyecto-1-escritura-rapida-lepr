package com.example.escritura_rapida.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Fetches random Spanish words from public APIs with retries and fallbacks.
 */
public class WordApiClient {
    private static final String PRIMARY_API_URL = "https://random-word-api.herokuapp.com/word?number=%d&lang=es";
    private static final String SECONDARY_API_URL = "https://raw.githubusercontent.com/words/an-array-of-spanish-words/master/index.json";
    private static final int MIN_WORDS = 80;
    private static final int TARGET_WORDS = 120;
    private static final int PRIMARY_BATCH_SIZE = 40;
    private static final int MAX_PRIMARY_REQUESTS = 3;
    private static final int MAX_RETRIES = 2;
    private static final int CONNECT_TIMEOUT_SECONDS = 5;
    private static final int REQUEST_TIMEOUT_SECONDS = 8;
    private static List<String> cachedSecondary;

    /**
     * Fetches a list of random Spanish words, preferring the primary API and
     * falling back to a secondary source if needed.
     *
     * @return list of random words, or an empty list if all sources fail
     */
    public List<String> fetchWords() {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS))
                .build();

        List<String> primary = fetchFromPrimary(client);
        if (primary.size() >= MIN_WORDS) {
            return primary;
        }

        List<String> secondary = fetchFromSecondary(client);
        if (secondary.size() >= MIN_WORDS) {
            return secondary;
        }

        if (!primary.isEmpty()) {
            return primary;
        }

        return secondary;
    }

    /**
     * Attempts to collect enough unique words by batching the primary API.
     */
    private List<String> fetchFromPrimary(HttpClient client) {
        Set<String> unique = new HashSet<>();
        int requestCount = 0;

        while (requestCount < MAX_PRIMARY_REQUESTS && unique.size() < TARGET_WORDS) {
            requestCount++;
            String url = String.format(PRIMARY_API_URL, PRIMARY_BATCH_SIZE);
            List<String> batch = requestWithRetries(client, url);
            if (!batch.isEmpty()) {
                unique.addAll(batch);
            }
        }

        List<String> result = new ArrayList<>(unique);
        if (!result.isEmpty()) {
            Collections.shuffle(result, ThreadLocalRandom.current());
        }
        return result;
    }

    /**
     * Fetches from the secondary JSON list and samples to target size.
     */
    private List<String> fetchFromSecondary(HttpClient client) {
        if (cachedSecondary != null && cachedSecondary.size() >= MIN_WORDS) {
            return sample(cachedSecondary, TARGET_WORDS);
        }

        List<String> all = requestWithRetries(client, SECONDARY_API_URL);
        if (all.isEmpty()) {
            return List.of();
        }

        cachedSecondary = new ArrayList<>(all);
        return sample(cachedSecondary, TARGET_WORDS);
    }

    /**
     * Issues HTTP GET requests with retry and timeout policies.
     */
    private List<String> requestWithRetries(HttpClient client, String url) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(REQUEST_TIMEOUT_SECONDS))
                        .header("User-Agent", "escritura-rapida/1.0")
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    List<String> parsed = parseWords(response.body());
                    if (!parsed.isEmpty()) {
                        return parsed;
                    }
                } else {
                    System.err.println("Word API non-200 response: " + response.statusCode());
                }
            }
            catch (Exception e) {
                System.err.println("Word API attempt " + attempt + " failed: " + e.getMessage());
            }

            try {
                Thread.sleep(300L * attempt);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return List.of();
    }

    /**
     * Parses a JSON array of strings with a lightweight split-based approach.
     */
    private List<String> parseWords(String body) {
        if (body == null || body.isBlank()) {
            return List.of();
        }
        String cleaned = body.replace("[", "").replace("]", "").replace("\"", "");
        return Arrays.stream(cleaned.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Returns a shuffled sample of up to targetSize words.
     */
    private List<String> sample(List<String> words, int targetSize) {
        if (words.isEmpty()) {
            return List.of();
        }
        List<String> copy = new ArrayList<>(words);
        Collections.shuffle(copy, ThreadLocalRandom.current());
        int size = Math.min(copy.size(), targetSize);
        return copy.subList(0, size);
    }
}
