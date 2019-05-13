package com.company;

public class Service {
    public static final double a = 0;
    public static final double b = 0;
    public static final double A = 0;
    public static final double B = 0;

    public double calculateInitialConditions(double x){
        return calculateExactValue(x,0);
    }
    public double calculateBoundaryConditionsForOne(double t){
        return calculateExactValue(1, t);
    }
    public double calculateBoundaryConditionsForZero(double t){
        return calculateExactValue(0, t);
    }

    public double calculateExactValue(double x, double t){
        return Math.exp(b*t)*Math.pow((A*x + B), 0.5D);
    }
}
