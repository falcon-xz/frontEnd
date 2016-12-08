package com.xz.newland.baseknow.thread.normal;

/**
 * 死锁
 * falcon -- 2016/12/7.
 */
public class DeadThreadTest implements Runnable{
    private DeadThread deadThread1 ;
    private DeadThread deadThread2 ;
    private boolean bo ;

    public DeadThreadTest() {
    }

    public DeadThreadTest(DeadThread deadThread1, DeadThread deadThread2, boolean bo) {
        this.deadThread1 = deadThread1;
        this.deadThread2 = deadThread2;
        this.bo = bo ;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName() ;
        if (bo){
            synchronized (deadThread1.o){
                System.out.println(name+"获得deadThread1.o的锁");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (deadThread2.o){
                    System.out.println(name+"获得deadThread2.o的锁");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }else {
            synchronized (deadThread2.o){
                System.out.println(name+"获得deadThread2.o的锁");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (deadThread1.o){
                    System.out.println(name+"获得deadThread1.o的锁");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    public static void main(String[] args) {

        DeadThread deadThread1 = new DeadThreadTest().new DeadThread() ;
        DeadThread deadThread2 = new DeadThreadTest().new DeadThread() ;
        DeadThreadTest deadThreadTest1 = new DeadThreadTest(deadThread1,deadThread2,true);
        DeadThreadTest deadThreadTest2 = new DeadThreadTest(deadThread1,deadThread2,false);
        new Thread(deadThreadTest1).start();
        new Thread(deadThreadTest2).start();
    }

    class DeadThread{
        Object o = new Object() ;
    }
}
