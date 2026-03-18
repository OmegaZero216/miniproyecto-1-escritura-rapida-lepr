package com.example.escritura_rapida.view;

import com.example.escritura_rapida.controller.ResultsController;
import com.example.escritura_rapida.model.GameManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Legacy helper to show the results screen.
 * Prefer {@link com.example.escritura_rapida.controller.NavigationManager} for navigation.
 */
public class ResultsView {

    /**
     * Loads the results view into the provided stage and injects stats.
     *
     * @param stage target stage to update
     * @param gameManager game state with stats
     */
    public void show(Stage stage, GameManager gameManager) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/escritura_rapida/results-view.fxml"));
            Parent root = loader.load();
            ResultsController controller = loader.getController();
            controller.setStats(gameManager);
            stage.setScene(new Scene(root));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
