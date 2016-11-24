package com.xz.newland.rpc.netty.server;

import com.xz.newland.rpc.netty.transion.Request;
import com.xz.newland.rpc.netty.transion.Response;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * falcon -- 2016/11/23.
 */
public class NettyDealRead implements Runnable {
    private ChannelHandlerContext channelHandlerContext ;
    private Request msg ;
    public NettyDealRead(ChannelHandlerContext channelHandlerContext, Request msg){
        this.channelHandlerContext = channelHandlerContext ;
        this.msg = msg ;
    }
    @Override
    public void run() {
        System.out.println("处理request的线程");
        final Response response = new Response();
        response.setRequestId(msg.getRequestId());
        Class cz = msg.getInterFace() ;
        Object obj = NettyRpcCenter.refer(cz.getName()) ;
        Object ret = null ;
        try {
            if (obj==null){
                ret = new Exception("没有对应接口实现类") ;
            }else{
                Method method = cz.getMethod(msg.getMethodName(),msg.getParamsType()) ;
                ret = method.invoke(obj,msg.getParams()) ;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            ret = e ;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            ret = e ;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            ret = e ;
        }
        response.setObject(ret);
        System.out.println("处理完 request 发送response");
        channelHandlerContext.writeAndFlush(response).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("Send response for request " + response.getRequestId());
            }
        });
    }
}
