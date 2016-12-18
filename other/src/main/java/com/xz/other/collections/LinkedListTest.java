package com.xz.other.collections;

import java.util.LinkedList;
import java.util.UUID;

/**
 * falcon -- 2016/12/15.
 */
public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList() ;
        for (int i = 0; i <5 ; i++) {
            String uuid = UUID.randomUUID().toString() ;
            System.out.println(uuid);
            linkedList.addFirst(uuid);
        }

        for (int i = 0; i <5 ; i++) {
            System.out.println(linkedList.getLast());
            linkedList.removeLast();
        }
    }
}
