package com.github.elijahgabrielletanabe.Algorithms;

import java.util.ArrayList;

import com.github.elijahgabrielletanabe.AlgorithmBase;

public class QuickSort extends AlgorithmBase
{
    public QuickSort() 
    {
        this.timeComplexity = "O(n log n)";
    }

    @Override
    protected <T extends Comparable<T>> void sort(ArrayList<T> sort)
    {
        sort(sort, 0, sort.size() - 1);

        verifySort(sort);
    }

    public <T extends Comparable<T>> void sort(ArrayList<T> sort, int begin, int end) 
    {
        if (begin < end)
        {
            int partitionIndex = partition(sort, begin, end);

            sort(sort, begin, partitionIndex - 1);
            sort(sort, partitionIndex + 1, end);
        }
    }

    private <T extends Comparable<T>> int partition(ArrayList<T> sort, int begin, int end) 
    {
        T pivot = sort.get(end);
        int i = begin - 1;

        for (int j = begin; j < end; j++)
        {
            if (sort.get(j).compareTo(pivot) <= 0)
            {
                i++;

                T temp = sort.get(i);
                sort.set(i, sort.get(j));
                sort.set(j, temp);
            }
        }

        T swapTemp = sort.get(i + 1);
        sort.set(i + 1, sort.get(end));
        sort.set(end, swapTemp);

        return i + 1;
    }

    @Override
    public String toString()
    {
        return "QuickSort";
    }
}
