package com.example.escritura_rapida.model;

import java.util.List;

public class WordService {
    private WordRepository repository = new WordRepository();
    private WordApiClient apiClient = new WordApiClient();

    public void initializeWords() {
        List<String> downloadedWords = apiClient.fetchWords();

        repository.setWords(downloadedWords);
    }

    public String getRandomWord() {
        return repository.getRandomWord();
    }
}
