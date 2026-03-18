package com.example.escritura_rapida.model;

import java.util.List;

public class WordService {
    private WordRepository repository = new WordRepository();
    private WordApiClient apiClient = new WordApiClient();

    public void initializeWords() {
        List<String> downloadedWords = apiClient.fetchWords();

        if (downloadedWords == null || downloadedWords.isEmpty()) {
            repository.setWords(getFallbackWords());
            return;
        }

        repository.setWords(downloadedWords);
    }

    public String getRandomWord() {
        return repository.getRandomWord();
    }

    private List<String> getFallbackWords() {
        return List.of("teclado", "pantalla", "codigo", "rapido", "juego", "nivel");
    }
}
