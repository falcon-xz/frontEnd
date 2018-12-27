package com.xz.util.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xz on 2018/12/19.
 */
public class Main implements Runnable {

    private FTPClientPool ftpClientPool ;

    public Main(FTPClientPool ftpClientPool) {
        this.ftpClientPool = ftpClientPool;
    }

    @Override
    public void run() {
        try {
            FTPClient ftpClient = ftpClientPool.borrowObject() ;
            ftpClient.enterLocalPassiveMode();
            FTPFile[] ftpFiles = ftpClient.listDirectories("/data") ;
            for (FTPFile ftpFile:ftpFiles) {
                System.out.println(Thread.currentThread().getName()+"--"+ftpFile.getName());
            }
            ftpClientPool.returnObject(ftpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        GenericObjectPoolConfig<FTPClient> config = new GenericObjectPoolConfig();
        config.setMinIdle(10);
        config.setMaxTotal(10);
        config.setMaxWaitMillis(30000);
        FTPClientConfigure ftpClientConfigure = new FTPClientConfigure() ;
        ftpClientConfigure.setHost("116.62.221.136");
        ftpClientConfigure.setPort(21);
        ftpClientConfigure.setCharset("utf-8");
        ftpClientConfigure.setEncoding("utf-8");
        ftpClientConfigure.setUsername("ftpuser");
        ftpClientConfigure.setPassword("ftpuser");
        ftpClientConfigure.setClientTimeout(500);
        FTPClientFactory ftpClientFactory = new FTPClientFactory(ftpClientConfigure) ;

        FTPClientPool ftpClientPool = new FTPClientPool(ftpClientFactory,config) ;
        try {
            int num = 5 ;
            ExecutorService executorService = Executors.newFixedThreadPool(num) ;
            boolean idEnd = false ;
            while (!idEnd){
                Thread.sleep(5000);
                for (int i = 0; i < 5; i++) {
                    Main testPool = new Main(ftpClientPool) ;
                    executorService.execute(testPool);
                }
                idEnd = true ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
