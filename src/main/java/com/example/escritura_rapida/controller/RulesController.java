package com.example.escritura_rapida.controller;

import javafx.fxml.FXML;

/**
 * Controller for the rules view. Provides a single action to start the game.
 */
public class RulesController implements NavigationAware {
    private NavigationManager navigationManager;

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
     * Handles the "start" action and navigates to the game view.
     */
    @FXML
    private void handleStart() {
        if (navigationManager != null) {
            navigationManager.show(ViewRoutes.GAME);
        }
    }
}
