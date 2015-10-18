/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.jfeiq.broadcast;

import org.jplus.jfeiq.feiq.IPMSGData;
import org.jplus.jfeiq.feiq.NetData;

/**
 *
 * @author hyberbin
 */
public class SimpleBroadcast implements IBroadcast {

    private static final String BroadcastAddress = "255.255.255.255";

    @Override
    public IPMSGData broadcast(String msg) {
        IPMSGData data = new IPMSGData(NetData.IPMSG_SENDMSG, msg, BroadcastAddress);
        data.setIp(BroadcastAddress);
        return data;
    }

}
