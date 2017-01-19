package com.xz.shell.zookeeper;

import com.xz.shell.jetty.JettyServer;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

/**
 * falcon -- 2017/1/18.
 */
public class ShellZn {
    private JettyServer jettyServer = JettyServer.newInstance() ;
    private int jettyServerPort = 4444 ;
    private String root = "/ShellZn" ;
    public ShellZn(){
        //创建根节点
        ZooKeeper zk = ZooKeeperUtils.getConnection() ;
        System.out.println("------"+zk.hashCode());
        boolean hasRoot = ZooKeeperUtils.hasZn(zk,root) ;
        if (!hasRoot){
            ZooKeeperUtils.createZn(zk,root) ;
        }
        if (!jettyServer.isStart()){
            jettyServer.start("",jettyServerPort);
        }
    }

    public boolean register(){
        boolean bo = false ;
        ZooKeeper zk = ZooKeeperUtils.getConnection() ;
        System.out.println("------"+zk.hashCode());
        String ip = getIp() ;
        String zn = root+"/"+ip.replaceAll("[.]","") ;
        String data = ip+":"+jettyServerPort ;

        ZooKeeperUtils.createTmpZn(zk,zn,data) ;
        return bo ;
    }

    public String getIp(){
        String ip = null ;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip ;
    }


}
