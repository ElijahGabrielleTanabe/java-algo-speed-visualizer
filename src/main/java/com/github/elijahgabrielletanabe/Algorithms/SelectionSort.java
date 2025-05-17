package com.github.elijahgabrielletanabe.Algorithms;

import java.util.ArrayList;

import com.github.elijahgabrielletanabe.AlgorithmBase;

public class SelectionSort extends AlgorithmBase
{
    public SelectionSort()
    {
        this.timeComplexity = "O(n^2)";
    }

    @Override
    protected <T extends Comparable<T>> void sort(ArrayList<T> sort) 
    {
        for (int i = 0; i < sort.size() - 1; i++) 
        {
            int min = i;

            for (int j = i + 1; j < sort.size(); j++) 
            {
                this.iterations++;

                if (sort.get(j).compareTo(sort.get(min)) < 0) 
                {
                    min = j;
                }
            }

            T temp = sort.get(i);
            sort.set(i, sort.get(min));         
            sort.set(min, temp);

            this.iterations++;
        }

        verifySort(sort);
    }

    @Override
    public String toString() 
    {
        return "SelectionSort";
    }
}
