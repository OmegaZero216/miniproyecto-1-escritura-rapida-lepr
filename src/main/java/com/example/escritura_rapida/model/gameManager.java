package com.example.escritura_rapida.model;

public class gameManager {
    private int score;
    private int correctWords;
    private int incorrectWords;
    private int totalTime = 20; //segundos
    private int timeRemaining;

    public gameManager() {
        resetGame();
    }
    public void resetGame() {
        score = 0;
        correctWords = 0;
        incorrectWords = 0;
        timeRemaining = totalTime;
    }
    public void correctWord() {
        score+= 10;
        correctWords++;
    }
    public void incorrectWord() {
        score-= 5;
        incorrectWords++;
    }
    public void decreaseTime() {
        timeRemaining--;
    }
    public int getTimeRemaining() {
        return timeRemaining;
    }
    public int getScore() {
        return score;
    }
    public int getCorrectWords() {
        return correctWords;
    }
    public int getIncorrectWords() {
        return incorrectWords;
    }

    public int getTotalTime() {
        return totalTime;
    }
}
