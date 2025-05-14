package com.github.elijahgabrielletanabe;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 * Algorithm Visualizer
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException 
    {
        scene = new Scene(FXMLLoader.load(getFileByString("sample.fxml", "fxml")));
        scene.getStylesheets().add(getFileByString("App.css", "css").toExternalForm());

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

    private URL getFileByString(String path, String type)
    {
        return this.getClass().getResource(type + "/" + path);
    }

}