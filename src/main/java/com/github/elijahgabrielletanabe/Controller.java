package com.github.elijahgabrielletanabe;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.MouseEvent;

public class Controller implements Initializable
{
    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private NumberAxis x;
    
    @FXML
    private NumberAxis y;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        System.out.println("Hello World!");
    }

    @FXML
    void printHelloWorld(MouseEvent event) 
    {
        System.out.println("Hello World!");
    }
}
