package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Service service = new Service();
        View view = new View();
        int numberOfStepsX = 10; // 20 30
        int numberOfStepsT = 100; // 400 900
        double stepX = 1D / numberOfStepsX;
        double sigma = 1D / numberOfStepsT;
        double stepT = Math.pow(stepX, 2) * sigma;

        Double[][] exactValue = new Double[numberOfStepsX][numberOfStepsT];
        Double[][] sequantial = new Double[numberOfStepsX][numberOfStepsT];
        Double[][] paralel = new Double[numberOfStepsX][numberOfStepsT];
        double x = 0D;
        double t = 0D;
        for (int i = 0; i < numberOfStepsX; i++) {
            for (int j = 0; j < numberOfStepsT; j++) {
                exactValue[i][j] = service.calculateExactValue(x, t);
                t += stepT;
            }
            x += stepX;
        }
        x = 0D;
        for (int i = 0; i < numberOfStepsX; i++) {
            sequantial[i][0] = service.calculateInitialConditions(x);
            paralel[i][0] = service.calculateInitialConditions(x);
            x += stepX;
        }
        t = 0D;
        for (int i = 0; i < numberOfStepsT; i++) {
            sequantial[0][i] = service.calculateBoundaryConditionsForZero(t);
            paralel[0][i] = service.calculateBoundaryConditionsForZero(t);
            sequantial[numberOfStepsX - 1][i] = service.calculateBoundaryConditionsForOne(t);
            paralel[numberOfStepsX - 1][i] = service.calculateBoundaryConditionsForOne(t);
            t += stepT;
        }
        long sequantialTime = System.nanoTime();
        for (int j = 1; j < numberOfStepsT; j++) {
            for (int i = 1; i < numberOfStepsX - 1; i++) {
                sequantial[i][j] = service.calculateWgrid(sequantial[i][j - 1], sequantial[i - 1][j - 1],
                        sequantial[i + 1][j - 1], stepX, stepT);
            }
        }
        sequantialTime = System.nanoTime() - sequantialTime;
        int numberOfThreads = 3;
        long parallelTime = System.nanoTime();
        for (int j = 1; j < numberOfStepsT; j++) {
            List<CalculationThread> calculationThreads = new ArrayList<>();
            for (int i = 0; i < numberOfThreads; i++) {
                calculationThreads.add(new CalculationThread(i == 0 ? 1 : (numberOfStepsX - 1) / numberOfThreads * i,
                        i == (numberOfThreads - 1) ? (numberOfStepsX - 1) : (numberOfStepsX - 1) / numberOfThreads * (i + 1),
                        j, paralel, stepX, stepT));

                calculationThreads.get(i).start();
            }
            for (int i = 0; i < numberOfThreads; i++) {
                calculationThreads.get(i).join();
            }
        }
        parallelTime = System.nanoTime() - parallelTime;
        view.print("Sequantial time: " + sequantialTime);
        view.print("Parallel time: " + parallelTime);
        view.printMatrix(sequantial, "Sequantial");
        view.printMatrix(paralel, "Parallel");
        view.printMatrixInFile(exactValue, "ExactValueMatrix.txt", "ExactValueMatrix");
        view.printMatrixInFile(sequantial, "SequantialMatrix.txt", "SequantialMatrix");
        view.printMatrixInFile(paralel, "ParallelMatrix.txt", "ParallelMatrix");
        ErrorCalculationService errorCalculationService = new ErrorCalculationService();
        view.print("Absolute error: " + errorCalculationService.calculateAbsoluteError(exactValue, paralel));
        view.print("Relative error: " + errorCalculationService.calculateRelativeError(exactValue));
    }
}
