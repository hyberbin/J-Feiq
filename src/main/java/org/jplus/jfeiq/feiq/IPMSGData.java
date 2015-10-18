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

/**
 * Created by hyberbin on 15/9/12.
 */

import java.io.UnsupportedEncodingException;

/**
 *
 * 飞秋或者飞鸽的数据
 * IPMSG数据格式 IPMSG协议格式：
 * Ver(1):PacketNo:SenderName:SenderHost:CommandNo:AdditionalSection
 * 每部分分别对应为：版本号（现在是1）:数据包编号（一般是当前时间）:发送主机:命令:附加数据
 * 其中：数据包编号，一般是取毫秒数。利用这个数据，可以唯一的区别每个数据包； SenderName指的是发送者的昵称(实际上是计算机登录名)
 * 发送主机，指的是发送主机的主机名；（主机名） 命令，指的是飞鸽协议中定义的一系列命令，具体见下文；
 * 附加数据，指的是对应不同的具体命令，需要提供的数据。当为上线报文时，附加信息内容是用户名和分组名，中间用"\0"分隔
 *
 * 例如： 1:100:shirouzu:jupiter:32:Hello 表示 shirouzu用户发送了 Hello
 * 这条消息（32对应为IPMSG_SEND_MSG这个命令，具体需要看源码中的宏定义）。
 */
public class IPMSGData extends NetData {
    public static String SENDER = "JFeiq";
    // 根据协议字符串初始化
    public IPMSGData(String ipmsg) {
        String[] args = ipmsg.split(":"); // 以:分割协议串
        version = args[0];
        packetNo = args[1];
        senderName = args[2];
        senderHost = args[3];
        commandNo = Integer.parseInt(args[4]);
        if (args.length >= 6) { // 是否有附加数据
            additionalSection = args[5];
        } else {
            additionalSection = "";
        }
        for (int i = 6; i < args.length; i++) { // 处理附加数据中有:的情况
            additionalSection += (":" + args[i]);
        }
        // 飞秋（上线）：1_lbt6_0#128#201A065B4BBE#0#0#0#4001#9:1385127288:wangli:WANGLI-PC:6291457:wangli����
        // 飞鸽（上线）：1:832011383:wangli:WANGLI-PC:43521:WANGLI-PC��WorkGroup��A4-DB-30-9E-DA-3B����������0061������28����
        // "\0"
        String[] feiQ = args[0].split("#");// 默认是飞秋，那么就以#号分割第一个字符串
        if (feiQ.length > 1) {// 表名是飞秋，那么第三个字符串是mac地址
            mac = feiQ[2];
        } else {// 表明是飞鸽，那么就通过"\0"截取args的最后一个参数
            String[] feiG = args[5].split("\0");
            if (feiG.length > 2) {// 第三个是mac地址
                mac = feiG[2];
            } else {
                mac = "";
            }
        }
        additionalSection=additionalSection.trim();
    }

    public IPMSGData( int commandNo,String additionalSection,String ip) {
        this.version = "1";
        this.packetNo = getSeconds();
        this.senderName = SENDER;
        this.senderHost = SENDER;
        this.commandNo = commandNo;
        this.additionalSection = additionalSection;
        this.ip=ip;
    }

    public IPMSGData(IPMSGData ipmsgData){
        this.version = "1";
        this.packetNo = getSeconds();
        this.senderName = SENDER;
        this.senderHost = SENDER;
        this.commandNo = ipmsgData.commandNo;
        this.additionalSection = ipmsgData.additionalSection;
        this.ip=ipmsgData.ip;
    }

    public byte[] toBytes(){
        try {
            return getMsg().getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getMsg() {
        StringBuilder sb = new StringBuilder();
        sb.append(version);
        sb.append(":");
        sb.append(packetNo);
        sb.append(":");
        sb.append(senderName);
        sb.append(":");
        sb.append(senderHost);
        sb.append(":");
        sb.append(commandNo);
        sb.append(":");
        sb.append(additionalSection);

        return sb.toString();
    }

    @Override
    public String toString() {
        return "version:" + version + ",packetNo:" + packetNo + ",senderName:"
                + senderName + ",senderHost:" + senderHost + ",commandNo:"
                + commandNo + ",additionalSection:" + additionalSection
                + ",mac:" + mac + ",ip:" + ip;
    }
}