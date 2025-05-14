package com.github.elijahgabrielletanabe;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Controller implements Initializable
{
    @FXML private LineChart<String, Double> lineChart;

    @FXML private VBox sortList;
    @FXML private ScrollPane sortListContainer;

    @FXML private CategoryAxis x;
    @FXML private NumberAxis y;

    private ArrayList<AlgorithmBase> ab;

    public Controller()
    {
        this.ab = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        System.out.println("Running!");

        //# Load algorithms
        loadAlgorithms();

        //# Populate SortList
        sortListContainer.getStylesheets().add(getFileByString("LeftPanel.css", "css").toExternalForm());
        VBox.setVgrow(sortListContainer, Priority.ALWAYS);
        sortList.setPrefHeight(Region.USE_COMPUTED_SIZE);

        for (AlgorithmBase algo : ab)
        {
            Button button = new Button(algo.toString());
            button.getStyleClass().add("sort-button");
            VBox.setVgrow(button, Priority.ALWAYS);
            sortList.getChildren().add(button);
        }
        
        //# Testing data
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Some Sort");
        
        series.getData().add(new XYChart.Data<>("100", 20D));
        series.getData().add(new XYChart.Data<>("500", 200D));
        series.getData().add(new XYChart.Data<>("1000", 330D));
        series.getData().add(new XYChart.Data<>("5000", 420D));
        series.getData().add(new XYChart.Data<>("10000", 550D));

        //# Have user specify sort sizes, into arraylist for usage in tests and GUI
        ObservableList<String> sortSizes =  FXCollections.observableList(new ArrayList<>());
        // Default sort size values
        sortSizes.addAll(new String[]{"100", "500", "1000", "5000", "10000"});

        x.setCategories(sortSizes);

        lineChart.getData().addAll(series);
    }

    @FXML
    public void queueSort(MouseEvent event)
    {
        if (event.getSource() == "aButtonfrfr")
        {
            System.out.println("Ayeee!");
        }
    }

    @FXML
    public void runTest(MouseEvent event) 
    {

    }

    private URL getFileByString(String path, String type)
    {
        return this.getClass().getResource(type + "/" + path);
    }

    public void loadAlgorithms()
    {
        //# Find all algorithm files
        Set<String> files = Stream.of(new File("src/main/java/com/github/elijahgabrielletanabe/Algorithms").listFiles())
        .filter(file -> !file.isDirectory())
        .map(File::getName)
        .collect(Collectors.toSet());

        //# Load algorithms into an array
        for (String file : files)
        {
            try
            {
                file = file.replace(".java", "");

                Class<?> clazz = Class.forName("com.github.elijahgabrielletanabe.Algorithms." + file);
                Constructor<?> cons = clazz.getConstructor();
                Object a = cons.newInstance();

                this.ab.add((AlgorithmBase) a);
            } 
            catch (Exception e) 
            {
                System.out.println("Failed to load class: " + file);
                e.printStackTrace();
            }
        }
    }
}
