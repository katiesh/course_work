package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Service service = new Service();
        int numberOfStepsX = 10; // 20 30
        int numberOfStepsT = 100; // 400 900
        double stepX = 1D / numberOfStepsX ;
        double sigma = 1D / numberOfStepsT;
        double stepT = Math.pow(stepX,2) * sigma;

        double[][] exactValue = new double[numberOfStepsX][numberOfStepsT];
        double[][] sequantial = new double[numberOfStepsX][numberOfStepsT];
        double[][] paralel = new double[numberOfStepsX][numberOfStepsT];
        double x = 0D;
        double t = 0D;
        for (int i = 0; i < numberOfStepsX ; i++) {
            for (int j = 0; j < numberOfStepsT ; j++) {
                exactValue[i][j] = service.calculateExactValue(x,t);
                t+=stepT;
            }
            x+=stepX;
        }
        x = 0D;
        for (int i = 0; i < numberOfStepsX; i++) {
            sequantial[i][0] = service.calculateInitialConditions(x);
            paralel[i][0] = service.calculateInitialConditions(x);
            x+=stepX;
        }
        t = 0D;
        for (int i = 0; i < numberOfStepsT; i++) {
            sequantial[0][i] = service.calculateBoundaryConditionsForZero(t);
            paralel[0][i] = service.calculateBoundaryConditionsForZero(t);
            sequantial[numberOfStepsX-1][i] = service.calculateBoundaryConditionsForOne(t);
            paralel[numberOfStepsX-1][i] = service.calculateBoundaryConditionsForOne(t);
            t+=stepT;
        }
        for (int j = 1; j < numberOfStepsT; j++) {
            for (int i = 1; i < numberOfStepsX-1; i++) {
                sequantial[i][j] = service.calculateWgrid(sequantial[i][j-1], sequantial[i-1][j-1],
                        sequantial[i+1][j-1], stepX, stepT);
            }
        }
        int numberOfThreads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < numberOfStepsX ; i++) {
            for (int k = 0; k < numberOfStepsT ; k++) {
                System.out.print(paralel[i][k] + "  ");
            }
            System.out.println();
        }
        System.out.println("+++++++++++++++++++++");
        for (int j = 1; j < numberOfStepsT; j++) {
            List<CalculationThread> calculationThreads = new ArrayList<>();
            for (int i = 0; i < numberOfThreads; i++) {
                calculationThreads.add(new CalculationThread(i==0?1:(numberOfStepsX-1)/numberOfThreads * i,
                        i==(numberOfThreads - 1)?(numberOfStepsX-1):(numberOfStepsX-1)/numberOfThreads * (i + 1),
                        j,paralel,stepX, stepT));

                calculationThreads.get(i).start();
            }
            for (int i = 0; i < numberOfThreads; i++){
                calculationThreads.get(i).join();
            }
        }
    }
}
