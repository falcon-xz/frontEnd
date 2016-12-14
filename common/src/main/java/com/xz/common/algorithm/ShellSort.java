package com.xz.common.algorithm;

import com.xz.common.algorithm.type.Type;

/**
 * falcon -- 2016/12/14.
 */
public class ShellSort {
    public static void shellSort(int[] array , Type type){
        int h = 1 ;
        while (h<=array.length/3){
            h = h*3+1 ;
        }
        while (h>0){
            for (int i = h; i < array.length; i++) {
                if (type ==Type.asc){
                    if (array[i]<array[i-h]){
                        int tmp = array[i] ;
                        int j = i-h ;
                        while (j>=0 && array[j]>tmp){
                            array[j+h] = array[j] ;
                            j-=h ;
                        }
                        array[j+h] = tmp ;
                    }
                }else {
                    if (array[i]>array[i-h]){
                        int tmp = array[i] ;
                        int j = i-h ;
                        while (j>=0 && array[j]<tmp){
                            array[j+h] = array[j] ;
                            j-=h ;
                        }
                        array[j+h] = tmp ;
                    }
                }
            }
            h=(h-1)/3;
        }
    }
}
