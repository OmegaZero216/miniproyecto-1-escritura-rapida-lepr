package com.example.escritura_rapida.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Legacy stage wrapper for the game screen.
 * Prefer {@link com.example.escritura_rapida.controller.NavigationManager} for navigation.
 */
public class GameStage extends Stage {
    /**
     * Constructs and shows a standalone game stage.
     *
     * @throws IOException if the FXML fails to load
     */
    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/escritura_rapida/game-view.fxml")
        );

        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        show();
    }
    }
