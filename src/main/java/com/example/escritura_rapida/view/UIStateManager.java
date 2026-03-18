package com.example.escritura_rapida.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Manages UI state changes based on timer progress (critical mode, pulsing effects).
 */
public class UIStateManager {
    private boolean criticalMode = false;
    private Timeline criticalPulse;

    /**
     * Updates the HUD and energy bar styles based on progress.
     *
     * @param progress ratio from 0.0 to 1.0
     * @param bar energy progress bar
     * @param hud HUD root pane
     */
    public void update(double progress, ProgressBar bar, Pane hud) {
        if (progress <= 0.3 && !criticalMode) {
            criticalMode = true;
            bar.getStyleClass().add("energy-bar-critical");
            hud.getStyleClass().add("hud-pane-critical");

            startPulse(hud);
        } else if (progress > 0.3 && criticalMode) {
            criticalMode = false;
            bar.getStyleClass().remove("energy-bar-critical");
            hud.getStyleClass().remove("hud-pane-critical");

            stopPulse(hud);

        }
    }

    /**
     * Starts a pulsing effect for critical mode.
     *
     * @param hud HUD root pane
     */
    private void startPulse (Pane hud) {
        criticalPulse = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> hud.setOpacity(0.85)), new KeyFrame(Duration.seconds(1), e -> hud.setOpacity(1))
        );
        criticalPulse.setCycleCount(Timeline.INDEFINITE);
        criticalPulse.play();
    }

    /**
     * Stops the pulsing effect and restores normal opacity.
     *
     * @param hud HUD root pane
     */
    private void  stopPulse(Pane hud) {
        if (criticalPulse != null) {
            criticalPulse.stop();
        }
        hud.setOpacity(1);
    }
}
