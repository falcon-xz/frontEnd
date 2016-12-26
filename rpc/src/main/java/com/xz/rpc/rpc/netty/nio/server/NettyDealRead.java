package com.xz.rpc.rpc.netty.nio.server;

import com.xz.rpc.rpc.info.po.RequestNoSer;
import com.xz.rpc.rpc.info.po.ResponseNoSer;
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
    private RequestNoSer msg ;
    public NettyDealRead(ChannelHandlerContext channelHandlerContext, RequestNoSer msg){
        this.channelHandlerContext = channelHandlerContext ;
        this.msg = msg ;
    }
    @Override
    public void run() {
        final ResponseNoSer responseNoSer = new ResponseNoSer();
        responseNoSer.setRequestId(msg.getRequestId());
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
        responseNoSer.setObject(ret);
        System.out.println("处理完 request 发送response");
        channelHandlerContext.writeAndFlush(responseNoSer).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("Send response for request " + responseNoSer.getRequestId());
            }
        });
        channelHandlerContext.close();
    }
}
