package com.github.elijahgabrielletanabe;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import javafx.scene.chart.XYChart;

public abstract class AlgorithmBase
{
    protected int iterations;
    protected String timeComplexity;
    private ArrayList<XYChart.Data<String, Long>> dataList;
    private ArrayList<Long> computeTimes;
    
    public AlgorithmBase()
    {
        this.iterations = 0;
        this.dataList = new ArrayList<>();
        this.computeTimes = new ArrayList<>();
    }

    public <T extends Comparable<T>> void experiment(ArrayList<T> sort, int sortSize)
    {
        System.out.println("Sorting on: " + this);

        Instant before = Instant.now();
        
        //# Run sort
        this.sort(sort);

        Instant after = Instant.now();
        long delta = Duration.between(before, after).toMillis();
        
        this.computeTimes.add(delta);
        this.dataList.add(new XYChart.Data<>(Integer.toString(sortSize), delta));
    }

    protected <T extends Comparable<T>> void verifySort(ArrayList<T> sort)
    {
        for (int i = 0; i < sort.size() - 1; i++)
        {
            if (sort.get(i).compareTo(sort.get(i++)) > 0)
            {
                System.out.println("Unsorted array at " + this + ": " + sort);
            }
        }
    }

    protected abstract <T extends Comparable<T>> void sort(ArrayList<T> sort);

    public ArrayList<XYChart.Data<String, Long>> getDataList() { return this.dataList; }
    public ArrayList<Long> getComputeTimes() { return this.computeTimes; }
    public int getIterations() { return this.iterations; }
    public String getTimeComplexity() { return this.timeComplexity; }

    public void clearDataList() { this.dataList.clear(); }
    public void clearComputeTimes() { this.computeTimes.clear(); }

    public void setIterations(int iterations) { this.iterations = iterations; }

    @Override
    public abstract String toString();
}
