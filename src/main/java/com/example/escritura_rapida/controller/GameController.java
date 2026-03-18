package com.example.escritura_rapida.controller;

import com.example.escritura_rapida.model.GameManager;
import com.example.escritura_rapida.model.WordService;
import com.example.escritura_rapida.view.ResultsView;
import com.example.escritura_rapida.view.UIStateManager;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GameController {

    private WordService wordService = new WordService();
    private GameManager gameManager = new GameManager();
    private AnimationTimer timer;
    private UIStateManager uiStateManager = new UIStateManager();
    private Stage stage;


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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handlerText () {
        String text = typingInput.getText();

        if (text.equals(targetWord.getText())) {
            statusMessage.setText("¡Correcto!");

            gameManager.correctWord();

            targetWord.setText(wordService.getRandomWord());

            startTimer();
        }
        else {
            gameManager.incorrectWord();
            statusMessage.setText("incorrecto, era:" + targetWord.getText());

            if (gameManager.mistakeGameOver()) {
                timer.stop();
                handleGameOver();
            }
        }

        typingInput.clear();
    }

    private long startTime;

    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }
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
                    handleGameOver();
                    return;
                }

                double progress =
                        remaining / gameManager.getTotalTime();

                energyTimer.setProgress(progress);

                uiStateManager.update(progress, energyTimer, mainHud);
            }
        };
        timer.start();
    }

    private void handleGameOver() {
        String text = typingInput.getText();

        timer.stop();

        if (text.equals(targetWord.getText())) {
            gameManager.correctWord();
            statusMessage.setText("Ultima palabra correcta!");
        }
        else {
            gameManager.incorrectWord();
            statusMessage.setText("se acabó el tiempo!");
        }
        Platform.runLater(() -> {
            Stage stage = (Stage) mainHud.getScene().getWindow();
            new ResultsView().show(stage, gameManager);
        });
    }
}
