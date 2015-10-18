/*
 * Copyright 2015 www.hyberbin.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Email:hyberbin@qq.com
 */
package org.jplus.jfeiq.feiq;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jplus.jfeiq.broadcast.IBroadcast;
import org.jplus.jfeiq.broadcast.SimpleBroadcast;
import org.jplus.jfeiq.handler.IReceiveHandler;
import org.jplus.jfeiq.handler.SimpleReceiveHandler;
import org.jplus.jfeiq.mq.SimpleFeiqMq;

/**
 * @author hyberbin
 */
public class FeiqServer extends Thread {

    /** 飞秋待发送的消息队列 */
    private Queue<IPMSGData> mq;
    /** 发送消息的定时器* */
    private final Timer timer = new Timer();
    /** 网络连接 */
    private DatagramSocket socket = null;
    /** 飞秋的端口 */
    private int port = 2425;
    /** 发送消息的间隔* */
    private int senderPeriod = 50;

    private InetSocketAddress inetSocketAddress;
    private IReceiveHandler receiveHandler;
    private IBroadcast broadcast;
    private String serverName = "Jfeiq";

    private static final Logger log = Logger.getLogger(FeiqServer.class.getName());

    public FeiqServer() {
        broadcast = new SimpleBroadcast();
        receiveHandler = new SimpleReceiveHandler(this);
        mq = new SimpleFeiqMq<IPMSGData>(1024);
    }

    public FeiqServer(int port, int senderPeriod, IReceiveHandler receiveHandler) {
        broadcast = new SimpleBroadcast();
        this.receiveHandler = receiveHandler;
        mq = new SimpleFeiqMq<IPMSGData>(1024);
        this.port = port;
        this.senderPeriod = senderPeriod;
    }

    public void run() {
        try {
            if (inetSocketAddress != null) {
                socket = new DatagramSocket(inetSocketAddress);
            }else{
                socket = new DatagramSocket(port);
            }
        } catch (Exception ex) {
            log.log(Level.SEVERE, "网络连接出错!端口占用", ex);
            System.exit(0);
        }
        new receiveThread().start();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mq.size() > 0) {
                    try {
                        IPMSGData ipmsgData = mq.poll();
                        log.log(Level.INFO, "准备发送消息:{0}", ipmsgData.toString());
                        byte[] buffer = ipmsgData.toBytes();
                        InetAddress address = InetAddress.getByName(ipmsgData.getIp()); // 发送给消息的地址
                        final DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                        socket.send(packet); // 发送报文
                    } catch (IOException ex) {
                        log.log(Level.SEVERE, "发送消息出错", ex);
                    }
                }
            }
        }, new Date(), senderPeriod);
        log.info(serverName + "已经启动!");

    }

    public void tellAll(String msg) {
        sendMsg(broadcast.broadcast(msg));
    }

    /**
     * 向队列中添加要发送的消息.
     *
     * @param messageBean
     */
    public synchronized void sendMsg(IPMSGData messageBean) {
        mq.add(messageBean);
    }

    /**
     * 接收消息的线程类
     */
    class receiveThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    final DatagramPacket receviepacket = new DatagramPacket(new byte[5000], 5000);
                    socket.receive(receviepacket);// 接收回应
                    log.info("准备接收消息");
                    receiveHandler.receiveData(receviepacket);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "接收消息出错", e);
                }
            }
        }
    }

    public Queue<IPMSGData> getMq() {
        return mq;
    }

    public void setMq(Queue<IPMSGData> mq) {
        this.mq = mq;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSenderPeriod() {
        return senderPeriod;
    }

    public void setSenderPeriod(int senderPeriod) {
        this.senderPeriod = senderPeriod;
    }

    public IReceiveHandler getReceiveHandler() {
        return receiveHandler;
    }

    public void setReceiveHandler(IReceiveHandler receiveHandler) {
        this.receiveHandler = receiveHandler;
    }

    public IBroadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(IBroadcast broadcast) {
        this.broadcast = broadcast;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
        IPMSGData.SENDER = serverName;
    }

}
