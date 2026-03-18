package com.example.escritura_rapida.model;

import java.util.List;
import java.util.Random;

/**
 * In-memory repository for the current list of words.
 */
public class WordRepository {
    private List<String> words;
    private Random random = new Random();

    /**
     * Replaces the current word list.
     *
     * @param words new list of words
     */
    public void setWords(List<String> words) {
        this.words = words;
    }

    /**
     * Returns a random word or a sentinel value when no words are loaded.
     *
     * @return random word or "SIN_PALABRAS"
     */
    public String getRandomWord() {
        if (words == null || words.isEmpty()) {
            return "SIN_PALABRAS";
        }
        return words.get(random.nextInt(words.size()));
    }
}
