package com.xz.common.algorithm;

import com.xz.common.algorithm.type.Type;

/**
 * falcon -- 2016/12/14.
 */
public class InsertSort {
    public static void insertSort(int[] array , Type type){
        int length = array.length ;
        int j,i,key ;
        for (i = 1; i < length; i++) {
            key = array[i] ;
            j = i-1 ;
            if (type == Type.asc){
                while (j>=0 && array[j]>key){
                    array[j+1] = array[j] ;
                    j-- ;
                }
            }else {
                while (j>=0 && array[j]<key){
                    array[j+1] = array[j] ;
                    j-- ;
                }
            }

            array[j+1] = key ;
        }
    }
}
