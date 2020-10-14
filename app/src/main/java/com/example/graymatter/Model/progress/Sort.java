package com.example.graymatter.Model.progress;

public class Sort {

    public static int[] sort(int[] toSort){
        for (int i = 0; i < toSort.length; i++){
            for (int o =i; o < toSort.length; o++){
                if(toSort[i] > toSort[o]){
                    int temp = toSort[i];
                    toSort[i] = toSort[o];
                    toSort[o] = temp;
                }
            }
        }
        return toSort;
    }

    /**
     * Sort after ints in int[0]
     * @param toSort multirowmatrix
     * @return int[][] sorted after contents in int[0]
     */
    public static int[][] multRowSort(int[][] toSort){
        for (int i = 0; i < toSort.length; i++){
            for (int o =i; o < toSort.length; o++){
                if(toSort[0][i] > toSort[0][o]){
                    for(int[]col:toSort){
                        int temp = col[i];
                        col[i] = col[o];
                        col[o] = temp;
                    }

                }
            }
        }
        return toSort;
    }
}
