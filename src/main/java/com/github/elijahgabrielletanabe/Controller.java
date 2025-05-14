package com.github.elijahgabrielletanabe;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Controller implements Initializable
{
    @FXML private LineChart<String, Double> lineChart;

    @FXML private VBox sortList;
    @FXML private ScrollPane sortListContainer;

    @FXML private CategoryAxis x;
    @FXML private NumberAxis y;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        System.out.println("Running!");

        //# Populate SortList
        VBox.setVgrow(sortListContainer, Priority.ALWAYS);
        
        
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
}
