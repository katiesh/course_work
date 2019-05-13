package com.company;

public class Main {

    public static void main(String[] args) {
        Service service = new Service();
        double stepX;
        double stepT;
        int numberOfStepsX = 10;
        int numberOfStepsT = 100;
        double[][] exactValue = new double[numberOfStepsX][numberOfStepsT];
        double[][] sequantial = new double[numberOfStepsX][numberOfStepsT];
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
            x+=stepX;
        }
        t = 0D;
        for (int i = 0; i < numberOfStepsT; i++) {
            sequantial[0][i] = service.calculateBoundaryConditionsForZero(t);
            sequantial[numberOfStepsX-1][i] = service.calculateBoundaryConditionsForOne(t);
            t+=stepT;
        }
        for (int j = 1; j < numberOfStepsT-1; j++) {
            for (int i = 1; i < numberOfStepsX; i++) {
                sequantial[i][j] = service.calculateWgrid(sequantial[i][j-1], sequantial[i-1][j-1],
                        sequantial[i+1][j-1], stepX, stepT);
            }
        }


    }
}
