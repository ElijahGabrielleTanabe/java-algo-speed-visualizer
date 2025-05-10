package com.github.elijahgabrielletanabe;

import java.io.IOException;

import com.github.elijahgabrielletanabe.Algorithms.BubbleSort;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 * Algorithm Visualizer
 */
public class App extends Application {

    private static Scene scene;

    private Parent createContent()
    {
        HBox root = new HBox();
        VBox leftControls = new VBox();
        AlgorithmList<AlgorithmBase> ab = new AlgorithmList<>();
        ab.addItem(new BubbleSort());

        ChoiceBox<AlgorithmBase> cb = new ChoiceBox<>(ab);
        leftControls.getChildren().add(cb);

        root.getChildren().add(leftControls);

        return root;
    }

    @Override
    public void start(Stage stage) throws IOException 
    {
        scene = new Scene(createContent());
        scene.getStylesheets().add(getCssString("App.css"));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

    private String getCssString(String path)
    {
        return this.getClass().getResource(path).toExternalForm();
    }

}