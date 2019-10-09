package com.xz.jvm.stack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2019/6/21.
 */
public class StackException {
    public static int c = 0;
    public static int d = 0;
    public static List<String> list = new ArrayList<String>();

//    如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverflowError异常。--无限递归
//    如果虚拟机在扩展栈时无法申请到足够的内存空间，则抛出OutOfMemoryError异常。 --占用的空间太大

    public static void main(String[] args) {
        StackException exception = new StackException();
        //-Xss1k
        //exception.testStackOverflowError(c);
        //-Xmx1M
        exception.Java_heap_space(d);
        //-Xmx1M
        exception.Java_heap_space2(d);
    }

    public void testStackOverflowError(int c) {//StackOverflowError
        System.out.println(c++);//10444
        testStackOverflowError(c);
    }


    public void Java_heap_space(int d) {//OutOfMemoryError
        while (true) {
            System.out.println(d++);//2702970
            String s = "11111111111111111111111111111111111111111111111111111111";
            list.add(s + s + s + s + s);
        }
    }

    public void Java_heap_space2(int d) {//OutOfMemoryError
        while (true) {
            System.out.println(d++);//2702970
            String s = "11111111111111111111111111111111111111111111111111111111";
            list.add(s.intern());
        }
    }
}
