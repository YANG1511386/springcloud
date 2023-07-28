//package com.example.privode.server.nettyserver;
//
//import io.netty.channel.ChannelId;
//
//import java.net.InetSocketAddress;
//import java.util.ArrayList;
//
//import static com.example.privode.server.nettyserver.NettyServerHandler.CHANNEL_MAP;
//
//
//public class Test {
//    public static void main(String[] args) throws Exception {
//
//        NettyServer nettyServer = new NettyServer();
//        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",1024);
//        nettyServer.start(inetSocketAddress);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        // 使用匿名内部类创建并启动线程
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ArrayList<ChannelId> channelIds = new ArrayList<>();
//                for(ChannelId channelId:CHANNEL_MAP.keySet()){
//                    channelIds.add(channelId);
//                }
//                try {
//                    new NettyServerHandler().channelWrite(channelIds.get(0),"nihao");
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
//    }
//
//
//}
