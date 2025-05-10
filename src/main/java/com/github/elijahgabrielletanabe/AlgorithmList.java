package com.github.elijahgabrielletanabe;

import java.util.ArrayList;

import javafx.collections.ObservableListBase;

public class AlgorithmList <T> extends ObservableListBase<T>
{
    private final ArrayList<T> al;

    public AlgorithmList()
    {
        this.al = new ArrayList<>();
    }

    public T get(int i) { return al.get(i); }

    @Override
    public int size() { return al.size(); }

    public void addItem(T a)
    {
        beginChange();
        al.add(a);
        nextAdd(al.size() - 1, al.size());
        endChange();
    }

    public void removeItem(int i)
    {
        beginChange();
        T removed = al.remove(i);
        nextRemove(i, removed);
        endChange();
    }
}
