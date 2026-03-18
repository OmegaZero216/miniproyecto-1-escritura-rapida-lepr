package com.example.escritura_rapida.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameStage extends Stage {
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
