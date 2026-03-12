package com.example.escritura_rapida;
import com.example.escritura_rapida.view.gameStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        new gameStage();
    }
}
