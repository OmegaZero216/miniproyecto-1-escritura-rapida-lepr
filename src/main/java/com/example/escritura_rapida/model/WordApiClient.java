package com.example.escritura_rapida.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WordApiClient {
    private static final String API_URL = "https://random-word-api.herokuapp.com/word?number=200&lang=es";

    public List<String> fetchWords() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_URL)).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return List.of();
            }

            String body = response.body();
            body = body.replace("[", "").replace("]", "").replace("\"", "");

            return Arrays.stream(body.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    };
}
