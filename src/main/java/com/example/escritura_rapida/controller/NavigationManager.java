package com.example.escritura_rapida.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

/**
 * Centralizes JavaFX navigation by loading FXML views, wiring controllers,
 * passing data, and reusing a single Stage instance.
 */
public class NavigationManager {
    private static final double DEFAULT_WIDTH = 1000;
    private static final double DEFAULT_HEIGHT = 700;
    private static final String WINDOW_TITLE = "Escritura Rapida";
    private static final String ICON_PATH = "/com/example/escritura_rapida/mega-man-zero-3-mega-man-x-mega-man-zero-2-mega-man-zero-collection-megaman-thumbnail.png";

    private final Stage stage;
    private final Map<String, String> viewMap = Map.of(
            ViewRoutes.MAIN_MENU, "/com/example/escritura_rapida/main-menu-view.fxml",
            ViewRoutes.RULES, "/com/example/escritura_rapida/rules-view.fxml",
            ViewRoutes.GAME, "/com/example/escritura_rapida/game-view.fxml",
            ViewRoutes.RESULTS, "/com/example/escritura_rapida/results-view.fxml"
    );

    /**
     * Creates a navigation manager tied to the provided primary stage.
     *
     * @param stage primary application stage used for all view transitions
     */
    public NavigationManager(Stage stage) {
        this.stage = stage;
    }

    /**
     * Shows a view identified by the route without passing data.
     *
     * @param route logical view route from {@link ViewRoutes}
     */
    public void show(String route) {
        show(route, null);
    }

    /**
     * Shows a view identified by the route and optionally passes data to the controller.
     *
     * @param route logical view route from {@link ViewRoutes}
     * @param data data object to pass to controllers implementing {@link DataReceiver}
     * @param <T> data type
     */
    public <T> void show(String route, T data) {
        String fxmlPath = viewMap.get(route);
        if (fxmlPath == null) {
            throw new IllegalArgumentException("No FXML registered for route: " + route);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Object controller = loader.getController();

            if (controller instanceof NavigationAware navigationAware) {
                navigationAware.setNavigationManager(this);
            }

            if (data != null && controller instanceof DataReceiver<?> receiver) {
                @SuppressWarnings("unchecked")
                DataReceiver<T> typed = (DataReceiver<T>) receiver;
                typed.setData(data);
            }

            Scene scene = stage.getScene();
            if (scene == null) {
                scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
                stage.setScene(scene);
            } else {
                scene.setRoot(root);
            }

            stage.setTitle(WINDOW_TITLE);
            if (stage.getIcons().isEmpty()) {
                Image icon = new Image(getClass().getResourceAsStream(ICON_PATH));
                stage.getIcons().add(icon);
            }
            stage.setMinWidth(DEFAULT_WIDTH);
            stage.setMinHeight(DEFAULT_HEIGHT);
            stage.setWidth(DEFAULT_WIDTH);
            stage.setHeight(DEFAULT_HEIGHT);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load route: " + route, e);
        }
    }
}
