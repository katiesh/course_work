package com.company;

public class ErrorCalculationService {
    private int iMax;
    private int jMax;
    private double maxDifference;
    public double calculateAbsoluteError(Double matrix1[][], Double[][] matrix2){
        for (int i = 0; i < matrix1.length ; i++) {
            for (int j = 0; j < matrix1[i].length ; j++) {
                if (Math.abs(matrix2[i][j] - matrix1[i][j]) > maxDifference){
                    maxDifference = Math.abs(matrix2[i][j] - matrix1[i][j]);
                    iMax = i;
                    jMax = j;
                }
            }
        }
        return maxDifference;
    }
    public double calculateRelativeError(Double[][] matrix){
        return maxDifference/matrix[iMax][jMax];
    }

}
