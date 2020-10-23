package com.example.graymatter.model.progress;

/**
 * @author Aline
 * Class containing static methods providing sorting for different kinds of arguments.
 */
public class Sort {

    /**
     * Ordinary sorter sorting an integer array to low values on low indexes and v.v.
     * @param toSort
     * @return
     */
    public static int[] sort(int[] toSort){
        for (int i = 0; i < toSort.length; i++){
            for (int o =i +1; o < toSort.length; o++){
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
     * Sort rows in matrix after ints in int[c]
     * @param toSort multi-row-matrix to sort. toSort[c] contains the numbers to sort after.
     * @param highValueLowIndex determines which direction to sort int[0] after. if true, low indexes contains high values, if false, low indexes contains low values.
     * @param c index of column which content to sort after.
     * @return int[][] sorted after contents in int[c]
     */
    public static int[][] multRowSort(int[][] toSort, boolean highValueLowIndex, int c){
        for (int i = 0; i < toSort[c].length; i++){
            for (int o =i; o < toSort[c].length; o++){
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
