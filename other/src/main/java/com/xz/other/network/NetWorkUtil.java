package com.xz.other.network;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * falcon -- 2016/11/24.
 */
public class NetWorkUtil {

    public static boolean isWindowsOS(){
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().indexOf("windows")>-1){
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    public static String[] getLocalIpList(){
        List<String> list = new ArrayList<>() ;
        try {
            Enumeration<NetworkInterface> enumerations = NetworkInterface.getNetworkInterfaces() ;
            while (enumerations.hasMoreElements()) {
                NetworkInterface netInterface = enumerations.nextElement();
                // 每个网络地址都有多个网络地址 回环地址（lookback） sitelocal IPV4 IPV6
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                String back = null ;
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if(address instanceof Inet6Address){
                        continue;
                    }
                    if (address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
                        list.add(address.getHostAddress());
                        continue;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[list.size()]) ;
    }

    public static String getLocalIp(){
        String ip = null ;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static InetAddress getInetAddress(){
        try{
            return InetAddress.getLocalHost();
        }catch(UnknownHostException e){
            System.out.println("unknown host!");
        }
        return null;

    }

    /**
     * 获取widnows网卡的mac地址.
     * @return mac地址
     */
    public static String getWindowsAddressInfo() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;

        StringBuilder sb = new StringBuilder() ;
        try {
            process = Runtime.getRuntime().exec("ipconfig /all");// windows下的命令，显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(process
                    .getInputStream(),"gbk"));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line+"\r\n") ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return sb.toString();
    }




}
