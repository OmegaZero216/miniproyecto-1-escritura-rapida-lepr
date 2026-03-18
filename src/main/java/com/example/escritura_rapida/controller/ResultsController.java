package com.example.escritura_rapida.controller;

import com.example.escritura_rapida.model.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for the results screen that displays stats and offers retry/exit actions.
 */
public class ResultsController implements NavigationAware, DataReceiver<GameManager> {
    private NavigationManager navigationManager;

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

    /**
     * Receives the navigation manager after FXML load.
     *
     * @param navigationManager central navigation manager
     */
    @Override
    public void setNavigationManager(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    /**
     * Receives the game state from the previous screen.
     *
     * @param gameManager game manager containing stats
     */
    @Override
    public void setData(GameManager gameManager) {
        setStats(gameManager);
    }

    /**
     * Populates the results UI with current stats.
     *
     * @param gameManager game manager containing stats
     */
    public void setStats(GameManager gameManager) {
        levelLabel.setText(String.valueOf(gameManager.getLevel()));
        scoreLabel.setText(String.valueOf(gameManager.getScore()));
        correctLabel.setText(String.valueOf(gameManager.getCorrectWords()));
        incorrectLabel.setText(String.valueOf(gameManager.getIncorrectWords()));
        timeLabel.setText(String.valueOf(gameManager.getTimeRemaining() + "s"));
    }

    /**
     * Navigates back to the game screen to restart.
     */
    @FXML
    private void handleRetry() {
        if (navigationManager != null) {
            navigationManager.show(ViewRoutes.GAME);
        }
    }

    /**
     * Navigates back to the main menu.
     */
    @FXML
    private void handleExit() {
        if (navigationManager != null) {
            navigationManager.show(ViewRoutes.MAIN_MENU);
        }
    }
}
