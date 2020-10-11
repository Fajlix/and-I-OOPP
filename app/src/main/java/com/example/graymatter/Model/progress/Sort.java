package com.example.graymatter.Model.progress;

public class Sort {

    public static int[] sort(int[] toSort){
        int [] nArr = new int[toSort.length];
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
}
