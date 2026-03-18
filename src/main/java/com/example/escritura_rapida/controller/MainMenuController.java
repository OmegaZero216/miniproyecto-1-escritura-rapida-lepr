package com.example.escritura_rapida.controller;

import javafx.fxml.FXML;

/**
 * Controller for the main menu view. Delegates navigation to {@link NavigationManager}.
 */
public class MainMenuController implements NavigationAware {
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

    /**
     * Handles the "rules" action and navigates to the rules view.
     */
    @FXML
    private void handleRules() {
        if (navigationManager != null) {
            navigationManager.show(ViewRoutes.RULES);
        }
    }
}
