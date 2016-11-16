package com.xz.newland.rpc.nio.echoNIO_v2;

/**
 * 本例使用 一个bytebuffer  负责读 写
 * channel.read前 buffer.clear
 * channel.write前 buffer.flip
 * 都使用nio 循环方式
 * Created by xz on 2016/10/26.
 */
class Config {
    final static String IP = "127.0.0.1" ;
    final static int PORT = 9999 ;
}
