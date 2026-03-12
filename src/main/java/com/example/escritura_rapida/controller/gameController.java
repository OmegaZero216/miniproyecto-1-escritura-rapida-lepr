package com.example.escritura_rapida.controller;

import com.example.escritura_rapida.model.gameManager;
import com.example.escritura_rapida.model.wordService;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class gameController {

    private wordService wordService = new wordService();
    private gameManager gameManager = new gameManager();
    private AnimationTimer timer;
    private boolean criticalMode = false;
    private Timeline criticalPulse;

    @FXML
    private Label targetWord;

    @FXML
    private ProgressBar energyTimer;

    @FXML
    private BorderPane mainHud;

    @FXML
    private Label statusMessage;

    @FXML
    private TextField typingInput;

    @FXML
    public void initialize()
    {
       wordService.initializeWords();
       startTimer();
       targetWord.setText(wordService.getRandomWord());
    }

    @FXML
    private void handlerText () {
        String text = typingInput.getText();

        if (text.equals(targetWord.getText())) {
            statusMessage.setText("¡Correcto!");

            gameManager.correctWord();

            targetWord.setText(wordService.getRandomWord());
        }
        else {
            gameManager.incorrectWord();

            statusMessage.setText("incorrecto, era:" + targetWord.getText());
        }

        typingInput.clear();
    }
    private long startTime;

    private void startTimer() {
        startTime = System.nanoTime();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                double elapsedSeconds =
                        (now - startTime) / 1_000_000_000.0;

                double remaining =
                        gameManager.getTotalTime() - elapsedSeconds;

                if (remaining <= 0) {
                    energyTimer.setProgress(0);
                    stop();
                    return;
                }

                double progress =
                        remaining / gameManager.getTotalTime();

                energyTimer.setProgress(progress);

                updateCriticalState(progress);
            }
        };

        timer.start();
    }

    private void updateCriticalState(double progress) {
        if (progress <= 0.3 && !criticalMode) {
            criticalMode = true;
            energyTimer.getStyleClass().add("energy-bar-critical");
            mainHud.getStyleClass().add("hud-pane-critical");
            startCriticalPulse();
        }
    }

    private void startCriticalPulse() {
        criticalPulse = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> {
                    mainHud.setOpacity(0.85);
                }), new KeyFrame(Duration.seconds(1), event -> {
                    mainHud.setOpacity(1);
        })
        );
        criticalPulse.setCycleCount(Timeline.INDEFINITE);
        criticalPulse.play();
    }
}
