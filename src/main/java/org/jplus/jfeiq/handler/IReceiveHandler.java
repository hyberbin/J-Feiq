/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.jfeiq.handler;

import java.net.DatagramPacket;
import org.jplus.jfeiq.feiq.FeiqServer;
import org.jplus.jfeiq.feiq.IPMSGData;

/**
 *
 * @author hyberbin
 */
public interface IReceiveHandler {
    /**
     * 获取飞秋服务器
     * @return 
     */
    FeiqServer getServer();
    /**
     * 接收消息报文
     * @param receviepacket 
     */
    void receiveData(DatagramPacket receviepacket);
    /**
     * 处理消息
     * @param data 
     */
    void dealWith(IPMSGData data);
}
