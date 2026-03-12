package com.example.escritura_rapida.model;

import java.util.List;
import java.util.Random;

public class wordRepository {
    private List<String> words;
    private Random random = new Random();

    public void setWords(List<String> words) {
        this.words = words;
    }
    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
}
