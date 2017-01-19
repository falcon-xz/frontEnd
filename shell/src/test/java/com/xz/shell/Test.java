package com.xz.shell;

import com.xz.shell.zookeeper.ZooKeeperUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * falcon -- 2017/1/19.
 */
public class Test {
    public static void main(String[] args) {
        ZooKeeper zk = ZooKeeperUtils.getConnection() ;
        try {
            List<String> list = zk.getChildren("/ShellZn", false) ;
            if (list.size()>0){
                String path = list.get(0) ;
                zk.exists("/ShellZn/"+path, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        System.out.println(event.getType()== Event.EventType.NodeDeleted);
                    }
                }) ;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true){

        }
    }
}
