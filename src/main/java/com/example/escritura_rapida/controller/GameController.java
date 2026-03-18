package com.example.escritura_rapida.controller;

import com.example.escritura_rapida.model.GameManager;
import com.example.escritura_rapida.model.WordService;
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

import java.util.concurrent.ThreadLocalRandom;

/**
 * Controller for the main game screen. Handles user input, timer updates,
 * game progression, and transitions to the results screen.
 */
public class GameController implements NavigationAware {

    private WordService wordService = new WordService();
    private GameManager gameManager = new GameManager();
    private AnimationTimer timer;
    private UIStateManager uiStateManager = new UIStateManager();
    private Stage stage;
    private Timeline glitchTimeline;
    private NavigationManager navigationManager;


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
    private Label levelLabel;

    /**
     * Initializes the game state and starts the timer when the view is loaded.
     */
    @FXML
    public void initialize()
    {
       wordService.initializeWords();
       startTimer();
       targetWord.setText(wordService.getRandomWord());
       updateLevelLabel();
    }

    /**
     * Optional stage setter (not used by the navigation system).
     *
     * @param stage current stage reference
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

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
     * Handles Enter/submit on the typing input and validates the word.
     */
    @FXML
    private void handlerText () {
        String text = typingInput.getText();

        if (text.equals(targetWord.getText())) {
            statusMessage.setText("¡Correcto!");

            gameManager.correctWord(targetWord.getText());
            updateLevelLabel();

            if (gameManager.isMaxLevelReached()) {
                timer.stop();
                statusMessage.setText("MISIÓN COMPLETADA");
                handleGameOver(false);
                return;
            }

            targetWord.setText(wordService.getRandomWord());

            startTimer();
        }
        else {
            gameManager.incorrectWord(targetWord.getText());
            statusMessage.setText("incorrecto, era:" + targetWord.getText());

            if (gameManager.mistakeGameOver()) {
                timer.stop();
                handleGameOver(true);
            }
        }

        typingInput.clear();
    }

    private long startTime;

    /**
     * Starts or restarts the per-word countdown timer and updates the energy bar UI.
     */
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
                    handleGameOver(true);
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

    /**
     * Finalizes the round and optionally evaluates the last typed word.
     *
     * @param evaluateLastWord whether to score the current input before ending
     */
    private void handleGameOver(boolean evaluateLastWord) {
        String text = typingInput.getText();

        timer.stop();

        if (evaluateLastWord) {
            if (text.equals(targetWord.getText())) {
                gameManager.correctWord(targetWord.getText());
                statusMessage.setText("Ultima palabra correcta!");
            }
            else {
                gameManager.incorrectWord(targetWord.getText());
                statusMessage.setText("se acabó el tiempo!");
            }
        }
        if (evaluateLastWord) {
            playGlitchThenShowResults();
        } else {
            showResults();
        }
    }

    /**
     * Updates the on-screen mission level label to match current game state.
     */
    private void updateLevelLabel() {
        if (levelLabel != null) {
            levelLabel.setText(String.format("MISSION LEVEL %02d", gameManager.getLevel()));
        }
    }

    /**
     * Plays a brief glitch animation and then navigates to the results screen.
     */
    private void playGlitchThenShowResults() {
        typingInput.setDisable(true);
        statusMessage.setText("SYSTEM GLITCH...");

        if (glitchTimeline != null) {
            glitchTimeline.stop();
        }

        final double maxOffset = 8.0;
        glitchTimeline = new Timeline(new KeyFrame(Duration.millis(80), e -> {
            double dx = ThreadLocalRandom.current().nextDouble(-maxOffset, maxOffset);
            double dy = ThreadLocalRandom.current().nextDouble(-maxOffset, maxOffset);
            mainHud.setTranslateX(dx);
            mainHud.setTranslateY(dy);

            if (!mainHud.getStyleClass().contains("glitch")) {
                mainHud.getStyleClass().add("glitch");
            }
        }));

        glitchTimeline.setCycleCount(25);
        glitchTimeline.setOnFinished(e -> {
            mainHud.setTranslateX(0);
            mainHud.setTranslateY(0);
            mainHud.getStyleClass().remove("glitch");
            showResults();
        });
        glitchTimeline.play();
    }

    /**
     * Navigates to the results screen, passing the current game state.
     */
    private void showResults() {
        if (navigationManager == null) {
            return;
        }
        Platform.runLater(() -> navigationManager.show(ViewRoutes.RESULTS, gameManager));
    }
}
