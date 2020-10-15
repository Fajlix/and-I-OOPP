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
     * Sort after ints in int[c]
     * @param toSort multirowmatrix
     * @param highValueLowIndex determines which direction to sort int[0] after
     * @param c column index to sort after
     * @return int[][] sorted after contents in int[0]
     */
    public static int[][] multRowSort(int[][] toSort, boolean highValueLowIndex, int c){
        for (int i = 0; i < toSort.length; i++){
            for (int o =i; o < toSort[i].length; o++){
                if(highValueLowIndex){
                    if(toSort[c][i] < toSort[c][o]){
                        for(int[]col:toSort){
                            int temp = col[i];
                            col[i] = col[o];
                            col[o] = temp;
                        }

                    }
                } else {
                    if (toSort[c][i] > toSort[c][o]) {
                        for (int[] col : toSort) {
                            int temp = col[i];
                            col[i] = col[o];
                            col[o] = temp;
                        }

                    }
                }
            }
        }
        return toSort;
    }
}
