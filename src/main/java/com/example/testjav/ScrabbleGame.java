package com.example.testjav;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScrabbleGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // displays the title screen
        FXMLLoader fxmlLoader = new FXMLLoader(ScrabbleGame.class.getResource("Sc2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("FBLA Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}