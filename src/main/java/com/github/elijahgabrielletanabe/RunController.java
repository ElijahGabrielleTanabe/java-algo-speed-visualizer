package com.github.elijahgabrielletanabe;

import java.util.ArrayList;

public class RunController 
{
    private ArrayList<AlgorithmBase> queueList;

    public RunController()
    {
        this.queueList = new ArrayList<>();
    }

    public void queueSort(AlgorithmBase ab)
    {
        this.queueList.add(ab);
    }

    public void runExperiment()
    {
        for (AlgorithmBase ab : queueList)
        {
            //Run tests on each sort;
        }
    }
}
