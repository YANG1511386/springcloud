package com.example.privode.client.nettyclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import static com.example.privode.client.nettyclient.NettyClient.start;

/**
 * @program: address
 * @author: YYl
 * @create: 2023-07-01 15:04
 * @description: netty服务端处理类
 **/

@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private static
    ChannelHandlerContext channel;


    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 有客户端连接服务器会触发此函数
     * @return: void
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        channel=ctx;
        ByteBuf message = Unpooled.copiedBuffer("clientId:nett", CharsetUtil.UTF_8);
        ctx.writeAndFlush(message);
    }

    public ChannelHandlerContext getChannel() {
        return channel;
    }

    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 有客户端终止连接服务器会触发此函数
     * @return: void
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws InterruptedException {
        connect();
    }

    public static void connect() throws InterruptedException {
        String host ="127.0.0.1";
        int port =1024;
        start().connect(host, port).addListener((ChannelFuture future) -> {
            if (future.isSuccess()) {
                System.out.println("Connected successfully!");
            } else {
                System.out.println("Connection failed, retrying...");
                future.channel().eventLoop().schedule(() -> {
                    try {
                        connect();
                    } catch (Exception e) {
                        e.printStackTrace(); // 你可以使用更详细的日志记录方式来记录这个异常
                    }
                }, 5, TimeUnit.SECONDS); // 5秒后重试
            }
        });
    }

    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 有客户端发消息会触发此函数
     * @return: void
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        try {
            System.out.println("扫码输出为"+buffer.toString(Charset.defaultCharset()));
        } finally {
            buffer.release();
        }

    }

    /**
     * @param msg        需要发送的消息内容
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 客户端向服务器发送消息
     * @return: void
     */
    public void channelWrite(Object msg) throws Exception {
        channel.writeAndFlush(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


    }

    /**
     * @param ctx
     * @author xiongchuan on 2019/4/28 16:10
     * @DESCRIPTION: 发生异常会触发此函数
     * @return: void
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {


    }
}
