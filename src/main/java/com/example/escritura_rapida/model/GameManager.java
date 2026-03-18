package com.example.escritura_rapida.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Encapsulates game state and core scoring/leveling rules.
 */
public class GameManager {
    private int score;
    private int correctWords;
    private int incorrectWords;
    private int totalTime = 20; //segundos
    private int timeRemaining;
    private int mistakes = 0;
    private final int MAX_MISTAKES = 1;
    private final int MAX_LEVEL = 45;
    private final int MIN_TIME = 2;
    private int level;
    private int consecutiveCorrect;
    private Set<String> mistakenWords = new HashSet<>();

    /**
     * Creates a new game manager with default settings.
     */
    public GameManager() {
        resetGame();
    }

    /**
     * Resets all game state for a new session.
     */
    public void resetGame() {
        score = 0;
        correctWords = 0;
        incorrectWords = 0;
        timeRemaining = totalTime;
        level = 1;
        consecutiveCorrect = 0;
        mistakenWords.clear();
    }

    /**
     * Applies rules for a correct word:
     * increments score, tracks streaks, levels up for new words,
     * and reduces time after each five consecutive correct words.
     *
     * @param word the word that was typed correctly
     * @return true if the level increased for a previously unmissed word
     */
    public boolean correctWord(String word) {
        mistakes = 0;
        score+= 10;
        correctWords++;
        consecutiveCorrect++;
        boolean leveledUp = false;
        if (word != null && !mistakenWords.contains(word) && level < MAX_LEVEL) {
            level++;
            leveledUp = true;
        }
        if (consecutiveCorrect % 5 == 0) {
            decreaseTime();
        }
        return leveledUp;
    }

    /**
     * Applies rules for an incorrect word: increments mistakes and breaks streak.
     *
     * @param word the word that was typed incorrectly
     */
    public void incorrectWord(String word) {
        mistakes++;
        score-= 5;
        incorrectWords++;
        consecutiveCorrect = 0;
        if (word != null) {
            mistakenWords.add(word);
        }
    }

    /**
     * Determines if the game should end due to too many mistakes.
     *
     * @return true if mistakes exceed the maximum allowed
     */
    public boolean mistakeGameOver() {
        return mistakes > MAX_MISTAKES;
    }

    /**
     * Decreases the per-word timer down to a minimum threshold.
     */
    public void decreaseTime() {
        totalTime = Math.max(MIN_TIME, totalTime - 2);
        timeRemaining = totalTime;
    }

    /**
     * @return remaining time for the current word
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return total number of correct words
     */
    public int getCorrectWords() {
        return correctWords;
    }

    /**
     * @return total number of incorrect words
     */
    public int getIncorrectWords() {
        return incorrectWords;
    }

    /**
     * @return total time per word in seconds
     */
    public int getTotalTime() {
        return totalTime;
    }

    /**
     * @return current mission level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return true if the max level has been reached
     */
    public boolean isMaxLevelReached() {
        return level >= MAX_LEVEL;
    }
}
