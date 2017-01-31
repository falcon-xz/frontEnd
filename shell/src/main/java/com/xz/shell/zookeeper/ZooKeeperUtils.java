package com.xz.shell.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * falcon -- 2017/1/18.
 */
public class ZooKeeperUtils {
    private final static String zookeeperIp = "192.168.211.135:2181" ;
    /*private final static String zookeeperIp = "172.32.148.163:2181" ;*/
    private final static int zookeeperTimeOut = 6000 ;
    private static ZooKeeper zk = null ;
    private static CountDownLatch latch = null ;
    public static ZooKeeper getConnection(){
        if (zk!=null && zk.getState().isAlive()){
            return zk ;
        }
        try {
            latch = new CountDownLatch(1) ;
            zk = new ZooKeeper(zookeeperIp, zookeeperTimeOut, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zk ;
    }

    public static boolean hasZn(ZooKeeper zk,String znStr){
        boolean bo = false ;
        try {
            if (zk.exists(znStr,false)!=null){
                bo = true ;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bo ;
    }

    public static boolean createTmpZn(ZooKeeper zk,String zn,String data){
        boolean bo = false ;
        try {
            zk.create(zn,data.getBytes("utf-8"), ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.EPHEMERAL);
            bo = true ;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bo ;
    }
    public static boolean createZn(ZooKeeper zk,String zn){
        boolean bo = false ;
        try {
            zk.create(zn,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            bo = true ;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bo ;
    }
}
