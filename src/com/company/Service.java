package com.company;

public class Service {
    public static final double a = 1.7;
    public static final double b = 0.5;
    public static final double A = 72;
    public static final double B = 14;

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
    public double calculateWgrid(double wi, double wiPrev, double wiNext, double h, double tau){
        return wi + tau*(a*(Math.pow((wiNext - wiPrev)/(2D*h), 2) + wi*((wiPrev - 2D*wi + wiNext)/Math.pow(h,2))) + b*wi);
    }
}
