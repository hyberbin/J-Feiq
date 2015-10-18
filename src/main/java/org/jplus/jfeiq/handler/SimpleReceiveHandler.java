/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.jfeiq.handler;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jplus.jfeiq.feiq.FeiqServer;
import org.jplus.jfeiq.feiq.IPMSGData;

/**
 *
 * @author hyberbin
 */
public class SimpleReceiveHandler implements IReceiveHandler {

    private final FeiqServer server;

    private static final Logger log = Logger.getLogger(SimpleReceiveHandler.class.getName());

    public SimpleReceiveHandler(FeiqServer server) {
        this.server = server;
    }

    @Override
    public void receiveData(DatagramPacket receviepacket) {
        try {
            String ip = receviepacket.getAddress().getHostAddress();
            String content = new String(receviepacket.getData(), 0, receviepacket.getLength(), "GBK");
            log.log(Level.INFO, "收到报文:{0}", content);
            IPMSGData data = new IPMSGData(content);
            data.setIp(ip);
            dealWith(data);
        } catch (UnsupportedEncodingException ex) {
            log.log(Level.SEVERE, "不能识别的编码格式!", ex);
        }
    }

    @Override
    public FeiqServer getServer() {
        return server;
    }

    @Override
    public void dealWith(IPMSGData data) {
        log.log(Level.INFO, "准备处理消息:{0}", data.getAdditionalSection());
        if ((data.getCommandNo() & IPMSGData.IPMSG_SENDCHECKOPT) == IPMSGData.IPMSG_SENDCHECKOPT) {//当别人给我发消息并且需要回执的时候
            IPMSGData ipmsgData = new IPMSGData(IPMSGData.IPMSG_RECVMSG, server.getServerName(), data.getIp());
            getServer().sendMsg(ipmsgData);//告诉他我我已经收到了你的信息了
        } else if (data.getCommandNo() == 0 || data.getCommandNo() == 1) {//当有人问我在不在线的时候
            IPMSGData ipmsgData = new IPMSGData(IPMSGData.IPMSG_ANSENTRY, server.getServerName(), data.getIp());
            getServer().sendMsg(ipmsgData);//告诉他我在线
        }
    }

}
