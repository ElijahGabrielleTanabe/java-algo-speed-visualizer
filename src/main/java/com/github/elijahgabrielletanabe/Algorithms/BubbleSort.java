package com.github.elijahgabrielletanabe.Algorithms;

import java.util.ArrayList;

import com.github.elijahgabrielletanabe.AlgorithmBase;

public class BubbleSort extends AlgorithmBase
{
    public BubbleSort()
    {
        this.timeComplexity = "O(n^2)";
    }

    @Override
    protected <T extends Comparable<T>> void sort(ArrayList<T> sort)
    {
        boolean swapped;

        for (int i = 0; i < sort.size() - 1; i++) 
        {
            swapped = false;

            for (int j = 0; j < sort.size() - i - 1; j++) 
            {
                if (sort.get(j).compareTo(sort.get(j + 1)) > 0) 
                {
                    T temp = sort.get(j);
                    sort.set(j, sort.get(j + 1));
                    sort.set(j + 1, temp);
                    swapped = true;
                }

                this.iterations++;
            }

            if (swapped == false) { break; }

            this.iterations++;
        }
    }

    @Override
    public String toString()
    {
        return "BubbleSort";
    }
}
