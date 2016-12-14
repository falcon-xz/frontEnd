/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.xz.rpc.baseknow.netty.demo2;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a server-side channel.
 */
public class ObjectServerHandler extends SimpleChannelInboundHandler<ObjectRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ObjectRequest msg) throws Exception {
        System.out.println("------channelRead0--------");
        final ObjectResponce or = new ObjectResponce();
        or.setRequestId(msg.getRequestId());
        or.setTime("ObjectResponce时间");
        channelHandlerContext.writeAndFlush(or).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("Send response for request " + or.getRequestId());
            }

        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
