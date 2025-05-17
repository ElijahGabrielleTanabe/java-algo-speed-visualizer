package com.github.elijahgabrielletanabe.Algorithms;

import java.util.ArrayList;

import com.github.elijahgabrielletanabe.AlgorithmBase;

public class MergeSort extends AlgorithmBase
{
    public MergeSort()
    {
        this.timeComplexity = "O(n log n)";
    }

    @Override
    protected <T extends Comparable<T>> void sort(ArrayList<T> sort)
    {
        run(sort, sort.size());

        verifySort(sort);
    }

    public <T extends Comparable<T>> void run(ArrayList<T> a, int n) 
    {
        if (n < 2) { return; }

        int mid = n / 2;
        ArrayList<T> l = new ArrayList<>();
        ArrayList<T> r = new ArrayList<>();

        for (int i = 0; i < mid; i++) 
        {
            l.add(a.get(i));

            this.iterations++;
        }

        for (int i = mid; i < n; i++) 
        {
            r.add(a.get(i));

            this.iterations++;
        }

        run(l, mid);
        run(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }

    public <T extends Comparable<T>> void merge(ArrayList<T> a, ArrayList<T> l, ArrayList<T> r, int left, int right) 
    {
        int i = 0, j = 0, k = 0;

        while (i < left && j < right)
        {
            if (l.get(i).compareTo(r.get(j)) <= 0) 
            {
                a.set(k++, l.get(i++));
            }
            else 
            {
                a.set(k++, r.get(j++));
            }

            this.iterations++;
        }

        while (i < left) 
        {
            a.set(k++, l.get(i++));

            this.iterations++;
        }

        while (j < right) 
        {
            a.set(k++, r.get(j++));

            this.iterations++;
        }
    }

    @Override
    public String toString()
    {
        return "MergeSort";
    }
}
