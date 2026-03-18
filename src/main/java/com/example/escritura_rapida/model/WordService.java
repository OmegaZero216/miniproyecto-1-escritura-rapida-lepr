package com.example.escritura_rapida.model;

import java.util.List;

/**
 * Coordinates word loading from APIs and exposes random word access to the game.
 */
public class WordService {
    private WordRepository repository = new WordRepository();
    private WordApiClient apiClient = new WordApiClient();

    /**
     * Loads words from the API, falling back to a local list when needed.
     */
    public void initializeWords() {
        List<String> downloadedWords = apiClient.fetchWords();

        if (downloadedWords == null || downloadedWords.isEmpty()) {
            repository.setWords(getFallbackWords());
            return;
        }

        repository.setWords(downloadedWords);
    }

    /**
     * Returns a random word from the current repository.
     *
     * @return random word
     */
    public String getRandomWord() {
        return repository.getRandomWord();
    }

    /**
     * Provides a minimal fallback list in case remote APIs fail.
     */
    private List<String> getFallbackWords() {
        return List.of("teclado", "pantalla", "codigo", "rapido", "juego", "nivel");
    }
}
