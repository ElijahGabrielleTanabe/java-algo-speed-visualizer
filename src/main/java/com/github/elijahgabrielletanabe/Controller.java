package com.github.elijahgabrielletanabe;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
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
    @FXML private LineChart<String, Long> lineChart;

    @FXML private VBox sortList;
    @FXML private ScrollPane sortListContainer;

    @FXML private CategoryAxis x;
    @FXML private NumberAxis y;

    private final HashMap<String, AlgorithmBase> algoList;
    private final ArrayList<AlgorithmBase> queueList;
    private final ObservableList<String> sortSizes;

    public Controller()
    {
        this.algoList = new HashMap<>();
        this.queueList = new ArrayList<>();
        this.sortSizes = FXCollections.observableList(new ArrayList<>());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        System.out.println("Running!");

        //# Load algorithms
        loadAlgorithms();

        //# Populate SortList
        sortListContainer.getStylesheets().add(getFileByString("LeftPanel.css", "css").toExternalForm());
        sortList.getStyleClass().add("vbox");
        VBox.setVgrow(sortListContainer, Priority.ALWAYS);
        sortList.setPrefHeight(Region.USE_COMPUTED_SIZE);

        for (String key : algoList.keySet())
        {
            //# Set up Button
            Button button = new Button(key);
            button.getStyleClass().add("sort-button");
            button.setCursor(Cursor.HAND);
            VBox.setVgrow(button, Priority.ALWAYS);

            //# Add Event Handler
            SortButtonEvent ae = new SortButtonEvent(this.queueList, algoList.get(key));
            button.setOnAction(ae);

            sortList.getChildren().add(button);
        }

        //# Have user specify sort sizes, into arraylist for usage in tests and GUI
 
        // Default sort size values
        this.sortSizes.addAll(new String[]{"100", "500", "1000", "5000", "10000"});

        this.x.setCategories(sortSizes);
    }

    @FXML
    //For loops galore
    public void runTest(MouseEvent event) throws InterruptedException 
    {
        Button button = (Button) event.getSource();
        button.setDisable(true);

        ArrayList<Thread> spawnedThreads = new ArrayList<>();

        if (this.queueList.isEmpty())
        {
            button.setDisable(false);
            return;
        }

        //# Clear all XYSeries on the chart
        this.lineChart.getData().clear();

        Thread side = new Thread(() -> {

            for (String s : this.sortSizes)
            {
                System.out.println("Sort Size: " + s);

                int sortSize = Integer.parseInt(s);

                //# Generate array to sort
                ArrayList<Integer> toSort = new ArrayList<>();

                for (int i = 0; i < sortSize; i++)
                {
                    toSort.add(i);
                }

                Collections.shuffle(toSort);

                for (AlgorithmBase ab : this.queueList)
                {
                    //# Run experiment with worker thread
                    Thread t = new Thread(() -> {
                        System.out.println("Executing task on: " + Thread.currentThread().getName());

                        //# Update later
                        ab.experiment(toSort, sortSize);
                    });

                    t.start();
                    spawnedThreads.add(t);
                }

                for (Thread thread : spawnedThreads)
                {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted Thread: " + thread.getName());
                    }
                }
                spawnedThreads.clear();
            }

            System.out.println("\tReached!");

            Platform.runLater(() -> {
                for (AlgorithmBase ab : queueList)
                {
                    XYChart.Series<String, Long> cs = new XYChart.Series<>();
                    cs.setName(ab.toString());

                    for (XYChart.Data<String, Long> cd : ab.getDataList())
                    {
                        cs.getData().add(new XYChart.Data<>(cd.getXValue(), cd.getYValue()));
                    }

                    ab.clearDataList();

                    this.lineChart.getData().add(cs);
                }
                
                button.setDisable(false);
            });
        });

        side.start();
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

    private URL getFileByString(String path, String type)
    {
        return this.getClass().getResource(type + "/" + path);
    }
}
