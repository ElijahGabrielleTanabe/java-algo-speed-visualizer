package com.github.elijahgabrielletanabe;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    private HashMap<String, AlgorithmBase> algoList;
    private HashMap<String, SortButtonEvent> eventHandlers;

    public Controller()
    {
        this.algoList = new HashMap<>();
        this.eventHandlers = new HashMap<>();
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

        for (String key : algoList.keySet())
        {
            //# Set up Button
            Button button = new Button(key);
            button.getStyleClass().add("sort-button");
            VBox.setVgrow(button, Priority.ALWAYS);

            //# Add and Store Event Handler
            SortButtonEvent ae = new SortButtonEvent();
            button.setOnAction(ae);
            eventHandlers.put(key, ae);

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
    public void runTest(MouseEvent event) 
    {
        for (Node n : sortList.getChildren())
        {
            if (!(n instanceof Button)) { throw new IllegalArgumentException("Not a Button"); }

            Button b = (Button) n;

            if (eventHandlers.get(b.getText()).isSelected())
            {
                System.out.println("This button was selected: " + b.getText());
            }
        }
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

                this.algoList.put(file, (AlgorithmBase) a);
            }
            catch (Exception e)
            {
                System.out.println("Failed to load class: " + file);
                e.printStackTrace();
            }
        }
    }
}
