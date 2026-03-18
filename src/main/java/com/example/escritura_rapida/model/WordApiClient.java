package com.example.escritura_rapida.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class WordApiClient {
    private static final String API_URL = "https://random-word-api.herokuapp.com/word?number=200&lang=es";

    public List<String> fetchWords() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_URL)).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            body = body.replace("[", "").replace("]", "").replace("\"","");

            return Arrays.asList(body.split(","));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return List.of("ERROR");
    };
}
