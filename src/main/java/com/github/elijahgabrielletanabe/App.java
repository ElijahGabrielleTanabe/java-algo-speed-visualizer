package com.github.elijahgabrielletanabe;

import java.io.IOException;
import java.net.URL;

import com.github.elijahgabrielletanabe.Algorithms.BubbleSort;
import com.github.elijahgabrielletanabe.Algorithms.MergeSort;
import com.github.elijahgabrielletanabe.Algorithms.QuickSort;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 * Algorithm Visualizer
 */
public class App extends Application {

    private static Scene scene;

    private Parent createContent()
    {
        BorderPane root = new BorderPane();
  
        VBox leftPanel = createLeftPanel();
        LineChart<Integer, Integer> lc = createAlgoGraph();
        //VBox stats = createStatsBox();

        root.setLeft(leftPanel);
        root.setCenter(lc);
        //root.setRight(stats);
        //BorderPane.setAlignment(center, Pos.CENTER);

        return root;
    }

    private VBox createLeftPanel()
    {
        VBox leftPanel = new VBox();
        leftPanel.getStyleClass().add("vbox");
        //leftPanel.getStylesheets().add(getFileByString("LeftPanel.css"));

        AlgorithmList<AlgorithmBase> al = new AlgorithmList<>();
        al.addAllItems(
            new BubbleSort(),
            new MergeSort(),
            new QuickSort()
        );

        ListView<AlgorithmBase> lv = new ListView<>();
        lv.setItems(al);

        Button run = new Button("Run");

        VBox buttonContainer = new VBox(run);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setBackground(new Background(new BackgroundFill(
            Color.RED, CornerRadii.EMPTY, Insets.EMPTY
        )));

        leftPanel.getChildren().addAll(lv, buttonContainer);

        return leftPanel;
    }

    private LineChart createAlgoGraph()
    {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Sort Size");
        yAxis.setLabel("Time in Milliseconds");

        LineChart<Integer, Integer> lc = new LineChart(xAxis, yAxis);
        //lc.getStylesheets().add(getFileByString("LineChart.css"));

        return lc;
    }

    /* 
    private VBox createStatsBox()
    {
        VBox stats = new VBox();
        stats.getStyleClass().add("vbox");
        stats.getStylesheets().add(getFileByString("Stats.css"));

        return stats;
    }
    */

    @Override
    public void start(Stage stage) throws IOException 
    {
        //scene = new Scene(createContent(), 1100, 650);
        //scene.getStylesheets().add(getFileByString("App.css", "css").toExternalForm());

        scene = new Scene(FXMLLoader.load(getFileByString("sample.fxml", "fxml")));

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