package com.example.escritura_rapida.controller;

/**
 * Implemented by controllers that need a reference to the {@link NavigationManager}.
 */
public interface NavigationAware {
    /**
     * Injects the navigation manager after the FXML controller is created.
     *
     * @param navigationManager central navigation manager
     */
    void setNavigationManager(NavigationManager navigationManager);
}
