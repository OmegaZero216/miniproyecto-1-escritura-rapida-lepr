package com.example.escritura_rapida.model;

import java.util.List;

public class wordService {
    private wordRepository repository = new wordRepository();
    private wordApiClient apiClient = new wordApiClient();

    public void initializeWords() {
        List<String> downloadedWords = apiClient.fetchWords();

        repository.setWords(downloadedWords);
    }

    public String getRandomWord() {
        return repository.getRandomWord();
    }
}
