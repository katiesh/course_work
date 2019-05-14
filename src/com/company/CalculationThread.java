package com.company;

import java.util.concurrent.Callable;

public class CalculationThread extends Thread{
    private int startIndex;
    private int endIndex;
    private int column;
    private double matrix[][];
    private double stepX;
    private double stepT;
    private Service service;
    public CalculationThread(int startIndex, int endIndex, int column, double matrix[][],
                             double stepX, double stepT){
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.column = column;
        this.matrix = matrix;
        this.stepX = stepX;
        this.stepT = stepT;
        this.service = new Service();
    }

    @Override
    public void run() throws RuntimeException {
        for (int i = startIndex; i <endIndex; i++) {
            matrix[i][column] = service.calculateWgrid(matrix[i-1][column], matrix[i-1][column-1],
                    matrix[i+1][column-1], stepX, stepT);
        }
    }
}
