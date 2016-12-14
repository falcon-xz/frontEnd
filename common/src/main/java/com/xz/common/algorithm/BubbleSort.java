package com.xz.common.algorithm;

import com.xz.common.algorithm.type.Type;

/**
 * falcon -- 2016/12/14.
 */
public class BubbleSort {
    public static void bubbleSort(int[] array,Type type){
        for (int i = array.length -1; i > 0 ; i--) {
            for (int j = 0; j < i; j++) {
                if (type==Type.asc){
                    if (array[j]>array[j+1]){
                        int tmp = array[j] ;
                        array[j] = array[j+1] ;
                        array[j+1] = tmp ;
                    }
                }else{
                    if (array[j]<array[j+1]){
                        int tmp = array[j] ;
                        array[j] = array[j+1] ;
                        array[j+1] = tmp ;
                    }
                }

            }
        }

    }
}
