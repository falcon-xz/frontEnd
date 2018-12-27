package com.xz.util.ftp;

import org.apache.commons.net.ftp.*;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * ftp工厂类
 * Created by xz on 2018/12/17.
 */
public class FTPClientFactory implements PooledObjectFactory<FTPClient> {
    private FTPClientConfigure config ;

    public FTPClientFactory(FTPClientConfigure ftpClientConfigure) {
        this.config = ftpClientConfigure;
    }

    @Override
    public PooledObject<FTPClient> makeObject() throws Exception {
        System.out.println("make");
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(config.getClientTimeout());
        try {
            ftpClient.connect(config.getHost(),config.getPort());
            int reply = ftpClient.getReplyCode() ;
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return null;
            }
            boolean result = ftpClient.login(config.getUsername(), config.getPassword());
            if (!result){
                return null;
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE) ;
            ftpClient.setCharset(Charset.forName(config.getCharset()));
            ftpClient.setControlEncoding(config.getEncoding());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new DefaultPooledObject<>(ftpClient);
    }

    @Override
    public void destroyObject(PooledObject<FTPClient> pooledObject) throws Exception {
        System.out.println("destroyObject");
        FTPClient ftpClient = pooledObject.getObject() ;
        if (ftpClient==null){
            return;
        }
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            // 注意,一定要在finally代码中断开连接，否则会导致占用ftp连接情况
            try {
                ftpClient.disconnect();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    @Override
    public boolean validateObject(PooledObject<FTPClient> pooledObject) {
        System.out.println("validateObject");
        FTPClient ftpClient = pooledObject.getObject();
        if (ftpClient == null) {
            return false;
        }
        try {
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<FTPClient> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<FTPClient> pooledObject) throws Exception {

    }
}
