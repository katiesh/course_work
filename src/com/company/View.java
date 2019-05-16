package com.company;

import java.io.FileWriter;
import java.io.IOException;

public class View {
    public <T> void printMatrix(T matrix[][], String name){
        System.out.println(name + ":");
        for (int i = 0; i <matrix.length ; i++) {
            for (int j = 0; j <matrix[i].length ; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public <T> void printMatrixInFile(T matrix[][], String fileName, String name) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(name + " = {");
        for (int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j < matrix[i].length ; j++) {
                if(j < matrix[i].length){
                    fileWriter.write(matrix[i][j] + ", ");
                } else {
                    fileWriter.write(matrix[i][j] + "");
                }
            }
            if(i < matrix.length -1){
                fileWriter.write(" }, ");
            } else {
                fileWriter.write(" } ");
            }
        }
        fileWriter.write(" };");
    }
}
