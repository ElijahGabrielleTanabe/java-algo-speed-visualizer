package com.github.elijahgabrielletanabe.Algorithms;

import java.util.ArrayList;

import com.github.elijahgabrielletanabe.AlgorithmBase;

public class InsertionSort extends AlgorithmBase
{
    public InsertionSort()
    {
        this.timeComplexity = "O(n^2)";
    }

    @Override
    protected <T extends Comparable<T>> void sort(ArrayList<T> sort)
    {
        for (int i = 1; i < sort.size(); i++)
        {
            T key = sort.get(i); 
            int j = i - 1;

            while (j >= 0 && sort.get(j).compareTo(key) > 0) 
            {
                sort.set(j + 1, sort.get(j));
                j = j - 1;
                this.iterations++;
            }
            sort.set(j + 1, key);

            this.iterations++;
        }

        verifySort(sort);
    }

    @Override
    public String toString()
    {
        return "InsertionSort";
    }
}
