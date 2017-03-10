package com.xz.rpc;

/**
 * falcon -- 2016/12/16.
 */
public class PrintABCD2 implements Runnable {
    private String name;
    private Object prev;
    private Object self;
    private static int j = 0;

    private PrintABCD2(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run() {
        int count = 100;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    j++;
                    if (j % 10 ==0) {
                        System.out.println();
                    }
                    count--;
                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        Object d = new Object();
        Object e = new Object();
        Object f = new Object();
        Object g = new Object();
        Object h = new Object();
        Object i = new Object();
        Object j = new Object();

        PrintABCD2 pa = new PrintABCD2("A", j, a);
        PrintABCD2 pb = new PrintABCD2("B", a, b);
        PrintABCD2 pc = new PrintABCD2("C", b, c);
        PrintABCD2 pd = new PrintABCD2("D", c, d);
        PrintABCD2 pe = new PrintABCD2("E", d, e);
        PrintABCD2 pf = new PrintABCD2("F", e, f);
        PrintABCD2 pg = new PrintABCD2("G", f, g);
        PrintABCD2 ph = new PrintABCD2("H", g, h);
        PrintABCD2 pi = new PrintABCD2("I", h, i);
        PrintABCD2 pj = new PrintABCD2("J", i, j);

        new Thread(pa).start();
        Thread.sleep(100); // 确保按顺序A、B、C执行
        new Thread(pb).start();
        Thread.sleep(100);
        new Thread(pc).start();
        Thread.sleep(100);
        new Thread(pd).start();
        Thread.sleep(100);
        new Thread(pe).start();
        Thread.sleep(100);
        new Thread(pf).start();
        Thread.sleep(100); // 确保按顺序A、B、C执行
        new Thread(pg).start();
        Thread.sleep(100);
        new Thread(ph).start();
        Thread.sleep(100);
        new Thread(pi).start();
        Thread.sleep(100);
        new Thread(pj).start();
        Thread.sleep(100);

    }
}
