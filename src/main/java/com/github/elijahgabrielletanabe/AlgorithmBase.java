package com.github.elijahgabrielletanabe;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.chart.XYChart;

public abstract class AlgorithmBase
{
    protected int iterations;
    protected ArrayList<XYChart.Data<String, Long>> dataList;

    public AlgorithmBase()
    {
        this.iterations = 0;
        this.dataList = new ArrayList<>();
    }

    public <T> void experiment(ArrayList<T> a, int sortSize)
    {
        System.out.println("Sorting on: " + this);

        Instant before = Instant.now();
        
        //# Run sort
        this.sort(a);

        Instant after = Instant.now();
        long delta = Duration.between(before, after).toMillis();
        System.out.println(delta);

        Random rand = new Random();

        this.dataList.add(new XYChart.Data<>(Integer.toString(sortSize), Long.valueOf(rand.nextInt(500))));
    }

    protected abstract <T> void sort(ArrayList<T> sort);

    public ArrayList<XYChart.Data<String, Long>> getDataList() { return this.dataList; }

    public void clearDataList() { this.dataList.clear(); }

    @Override
    public abstract String toString();
}
