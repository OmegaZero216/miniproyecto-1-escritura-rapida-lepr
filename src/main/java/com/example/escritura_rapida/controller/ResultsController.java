package com.example.escritura_rapida.controller;

import com.example.escritura_rapida.model.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.escritura_rapida.view.GameStage;

public class ResultsController {
    @FXML
    private Label levelLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label correctLabel;

    @FXML
    private Label incorrectLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Button retryButton;

    @FXML
    private Button exitButton;

    public void setStats (GameManager gameManager) {
        scoreLabel.setText(String.valueOf(gameManager.getScore()));
        correctLabel.setText(String.valueOf(gameManager.getCorrectWords()));
        incorrectLabel.setText(String.valueOf(gameManager.getIncorrectWords()));
        timeLabel.setText(String.valueOf(gameManager.getTimeRemaining() + "s"));
    }

    @FXML
    private void handleRetry() {
        try {
            Stage stage = (Stage) retryButton.getScene().getWindow();
            stage.close();
            new GameStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
