package com.github.elijahgabrielletanabe;

public abstract class AlgorithmBase
{
    protected int iterations;

    public AlgorithmBase()
    {
        this.iterations = 0;
    }

    @Override
    public abstract String toString();
}
