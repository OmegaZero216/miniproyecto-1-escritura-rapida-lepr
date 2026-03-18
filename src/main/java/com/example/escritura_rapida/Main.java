package com.example.escritura_rapida;
import com.example.escritura_rapida.controller.NavigationManager;
import com.example.escritura_rapida.controller.ViewRoutes;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX application entry point that initializes navigation and shows the main menu.
 */
public class Main extends Application {

    /**
     * Launches the JavaFX application.
     *
     * @param args CLI arguments (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFX lifecycle hook for initial stage setup.
     *
     * @param primaryStage primary application stage
     * @throws IOException if initial view fails to load
     */
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        NavigationManager navigationManager = new NavigationManager(primaryStage);
        navigationManager.show(ViewRoutes.MAIN_MENU);
    }
}
