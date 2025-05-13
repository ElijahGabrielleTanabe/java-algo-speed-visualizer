package com.github.elijahgabrielletanabe;

public abstract class AlgorithmBase
{
    protected int iterations;

    public AlgorithmBase()
    {
        this.iterations = 0;
    }

    public abstract void experiment();

    @Override
    public abstract String toString();
}
