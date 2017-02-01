package com.xz.shell.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * falcon -- 2017/2/1.
 */
public class ZkExistTest {
    public static void main(String[] args) {
        try {
            ZooKeeper zk = new ZooKeeper("192.168.211.135",6000,null) ;
            Stat stat = zk.exists("/r",false) ;
            System.out.println(stat);
            List<String> list = zk.getChildren("/r",false) ;
            System.out.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
