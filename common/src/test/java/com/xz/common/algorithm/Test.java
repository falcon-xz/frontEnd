package com.xz.common.algorithm;

import com.xz.common.algorithm.type.Type;

import java.util.Arrays;

/**
 * falcon -- 2016/12/14.
 */
public class Test {
    public static void main(String[] args) {
        int[] ints = new int[]{74,5,84,43,127,19,33,466,11,33,2325,844,5934,71,324,3,16,38,2325,8454,593544,7,34,453,111,73,2325,844,5934,71,734,53,141,353,2325,844,5934,7,34,3,6,518,575,344,6,452,434,53,61,6,56,45,346,45,6,7,34,3,6,518,455,344,6,52,4,53,61,6,56,45,346,45,6} ;
        long l1 = System.currentTimeMillis() ;
        //BubbleSort.bubbleSort(ints, Type.desc);
        //InsertSort.insertSort(ints,Type.desc);
        ShellSort.shellSort(ints,Type.desc);
        long l2 = System.currentTimeMillis() ;
        System.out.println("耗时:"+(l2-l1));
        for (int i:ints) {
            System.out.print(i+",");
        }
    }
}
