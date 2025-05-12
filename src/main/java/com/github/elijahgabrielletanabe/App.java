package com.github.elijahgabrielletanabe;

import java.io.IOException;

import com.github.elijahgabrielletanabe.Algorithms.BubbleSort;

import javafx.application.Application;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * JavaFX App
 * Algorithm Visualizer
 */
public class App extends Application {

    private static Scene scene;

    private Parent createContent()
    {
        BorderPane root = new BorderPane();
  
        VBox sortList = createSortList();
        LineChart<Integer, Integer> lc = createAlgoGraph();
        VBox stats = createStatsBox();
        HBox top = new HBox();
        HBox bottom = new HBox();

        top.setBackground(new Background(new BackgroundFill(
            Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY
        )));
        top.setPrefHeight(60);
        bottom.setBackground(new Background(new BackgroundFill(
            Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY
        )));
        bottom.setPrefHeight(60);

        root.setLeft(sortList);
        root.setCenter(lc);
        root.setRight(stats);
        root.setTop(top);
        root.setBottom(bottom);
        //BorderPane.setAlignment(center, Pos.CENTER);

        return root;
    }

    private VBox createSortList()
    {
        VBox sortList = new VBox();
        sortList.getStyleClass().add("vbox");
        sortList.getStylesheets().add(getCssString("SortList.css"));

        AlgorithmList<AlgorithmBase> ab = new AlgorithmList<>();
        ab.addItem(new BubbleSort());

        ChoiceBox<AlgorithmBase> cb = new ChoiceBox<>(ab);
        sortList.getChildren().add(cb);

        return sortList;
    }

    private LineChart createAlgoGraph()
    {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Sort Size");
        yAxis.setLabel("Time in Milliseconds");

        LineChart<Integer, Integer> lc = new LineChart(xAxis, yAxis);
        lc.getStylesheets().add(getCssString("LineChart.css"));

        return lc;
    }

    private VBox createStatsBox()
    {
        VBox stats = new VBox();
        stats.getStyleClass().add("vbox");
        stats.getStylesheets().add(getCssString("Stats.css"));

        return stats;
    }

    @Override
    public void start(Stage stage) throws IOException 
    {
        scene = new Scene(createContent(), 1100, 650);
        scene.getStylesheets().add(getCssString("App.css"));

        stage.setResizable(false);
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