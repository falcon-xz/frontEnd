package com.xz.shell.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * test zk syc Event
 * falcon -- 2017/1/31.
 */
public class ZkListenGetChildrenTest implements Runnable{
    private ScheduledExecutorService service = null ;
    private ZooKeeper zk = null ;
    private CountDownLatch latch = null ;
    private String root = "/ShellZn" ;
    private List<String> list ;
    private Lock lock = new ReentrantLock() ;
    private Random random = new Random() ;
    public ZkListenGetChildrenTest(){
        service = Executors.newSingleThreadScheduledExecutor() ;
        latch = new CountDownLatch(1) ;
    }
    public void start(){
        service.scheduleWithFixedDelay(this,0,2, TimeUnit.SECONDS) ;
        try {
            zk = new ZooKeeper("192.168.211.135", 6000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                    if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged
                            && root.equals(watchedEvent.getPath())){
                        updateServer();
                    }
                }
            });
            latch.await();
            if (zk!=null && zk.getState()== ZooKeeper.States.CONNECTED){
                try {
                    list = zk.getChildren(root,true) ;
                    System.out.println("初始化zk 获得子节点");
                } catch (KeeperException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println(list);
            if (list==null||list.size()==0){
                return ;
            }
            String zn = list.get(random.nextInt(list.size())) ;
            String znPath = root+"/"+zn ;
            String data = new String(zk.getData(znPath,false, null),"utf-8");
            System.out.println(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void updateServer() {
        lock.lock();
        try{
            if (zk!=null && zk.getState()== ZooKeeper.States.CONNECTED){
                list = zk.getChildren(root,true);
                System.out.println("通过回调获得子节点");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new ZkListenGetChildrenTest().start();
    }
}
