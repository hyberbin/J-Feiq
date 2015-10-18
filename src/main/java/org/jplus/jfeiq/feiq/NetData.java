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

import java.util.Date;

public abstract class NetData {



    public static final int VERSION = 0x001; // 版本号
    public static final int PORT = 0x0979; // 端口号，飞鸽协议默认端口2425

    // 命令
    public static final int IPMSG_NOOPERATION = 0; // 不进行任何操作
    public static final int IPMSG_BR_ENTRY = 1; // 用户上线
    public static final int IPMSG_BR_EXIT = 2; // 用户退出
    public static final int IPMSG_ANSENTRY = 3; // 通报在线
    public static final int IPMSG_BR_ABSENCE = 4; // 改为缺席模式

    public static final int IPMSG_BR_ISGETLIST = 16; // 寻找有效的可以发送用户列表的成员
    public static final int IPMSG_OKGETLIST = 17; // 通知用户列表已经获得
    public static final int IPMSG_GETLIST = 18; // 用户列表发送请求
    public static final int IPMSG_ANSLIST = 19; // 应答用户列表发送请求
    public static final int IPMSG_FILE_MTIME = 20; //
    public static final int IPMSG_FILE_CREATETIME = 22; //
    public static final int IPMSG_BR_ISGETLIST2 = 24; //

    public static final int IPMSG_SENDMSG = 32; // 发送消息
    public static final int IPMSG_RECVMSG = 33; // 通报收到消息
    public static final int IPMSG_READMSG = 48; // 消息打开通知
    public static final int IPMSG_DELMSG = 49; // 消息丢弃通知
    public static final int IPMSG_ANSREADMSG = 50; // 消息打开确认通知（version-8中添加）

    public static final int IPMSG_GETINFO = 64; // 获得IPMSG版本信息
    public static final int IPMSG_SENDINFO = 65; // 发送IPMSG版本信息

    public static final int IPMSG_GETABSENCEINFO = 80; // 获得缺席信息
    public static final int IPMSG_SENDABSENCEINFO = 81; // 发送缺席信息

    public static final int IPMSG_GETFILEDATA = 96; // 文件传输请求
    public static final int IPMSG_RELEASEFILES = 97; // 丢弃附加文件
    public static final int IPMSG_GETDIRFILES = 98; // 附着统计文件请求

    public static final int IPMSG_GETPUBKEY = 114; // 获得RSA公钥
    public static final int IPMSG_ANSPUBKEY = 115; // 应答RSA公钥

    /* option for all command */
    public static final int IPMSG_ABSENCEOPT = 256; // 缺席模式
    public static final int IPMSG_SERVEROPT = 512; // 服务器（保留）
    public static final int IPMSG_DIALUPOPT = 65536; // 发送给个人
    public static final int IPMSG_FILEATTACHOPT = 2097152; // 附加文件
    public static final int IPMSG_ENCRYPTOPT = 4194304; // 加密

    /* option for send command */
    public static final int IPMSG_SENDCHECKOPT = 256; // 传送验证
    public static final int IPMSG_SECRETOPT = 512; // 密封的消息
    public static final int IPMSG_BROADCASTOPT = 1024; // 广播
    public static final int IPMSG_MULTICASTOPT = 2048; // 多播
    public static final int IPMSG_NOPOPUPOPT = 4096; // （不再有效）
    public static final int IPMSG_AUTORETOPT = 8192; // 自动应答(Ping-pong
    // protection)
    public static final int IPMSG_RETRYOPT = 16384; // 重发标识（用于请求用户列表时）
    public static final int IPMSG_PASSWORDOPT = 32768; // 密码
    public static final int IPMSG_NOLOGOPT = 131072; // 没有日志文件
    public static final int IPMSG_NEWMUTIOPT = 262144; // 新版本的多播（保留）
    public static final int IPMSG_NOADDLISTOPT = 524288; // 不添加用户列表 Notice
    // to the
    // members
    // outside of
    // BR_ENTRY
    public static final int IPMSG_READCHECKOPT = 1048576; // 密封消息验证（version8中添加）
    public static final int IPMSG_SECRETEXOPT = (IPMSG_READCHECKOPT | IPMSG_SECRETOPT);

    /* encryption flags for encrypt command */
    public static final int IPMSG_RSA_512 = 1;
    public static final int IPMSG_RSA_1024 = 2;
    public static final int IPMSG_RSA_2048 = 4;
    public static final int IPMSG_RC2_40 = 4096;
    public static final int IPMSG_RC2_128 = 16384;
    public static final int IPMSG_RC2_256 = 32768;
    public static final int IPMSG_BLOWFISH_128 = 131072;
    public static final int IPMSG_BLOWFISH_256 = 262144;
    public static final int IPMSG_SIGN_MD5 = 268435456;

    /* file types for fileattach command */
    public static final int IPMSG_FILE_REGULAR = 1;
    public static final int IPMSG_FILE_DIR = 2;
    public static final int IPMSG_FILE_RETPARENT = 3; // return parent
    // directory
    public static final int IPMSG_FILE_SYMLINK = 0x00000004;
    public static final int IPMSG_FILE_CDEV = 0x00000005; // for UNIX
    public static final int IPMSG_FILE_BDEV = 0x00000006; // for UNIX
    public static final int IPMSG_FILE_FIFO = 0x00000007; // for UNIX
    public static final int IPMSG_FILE_RESFORK = 0x00000010; // for Mac

    /* file attribute options for fileattach command */
    public static final int IPMSG_FILE_RONLYOPT = 0x00000100;
    public static final int IPMSG_FILE_HIDDENOPT = 0x00001000;
    public static final int IPMSG_FILE_EXHIDDENOPT = 0x00002000; // for MacOS X
    public static final int IPMSG_FILE_ARCHIVEOPT = 0x00004000;
    public static final int IPMSG_FILE_SYSTEMOPT = 0x00008000;

    /* extend attribute types for fileattach command */
    public static final int IPMSG_FILE_UID = 0x00000001;
    public static final int IPMSG_FILE_USERNAME = 0x00000002; // uid by string
    public static final int IPMSG_FILE_GID = 0x00000003;
    public static final int IPMSG_FILE_GROUPNAME = 0x00000004; // gid by string
    public static final int IPMSG_FILE_PERM = 0x00000010; // for UNIX
    public static final int IPMSG_FILE_MAJORNO = 0x00000011; // for UNIX devfile
    public static final int IPMSG_FILE_MINORNO = 0x00000012; // for UNIX devfile
    public static final int IPMSG_FILE_CTIME = 0x00000013; // for UNIX
    public static final int IPMSG_FILE_ATIME = 0x00000015;
    public static final int IPMSG_FILE_CREATOR = 0x00000020; // for Mac
    public static final int IPMSG_FILE_FILETYPE = 0x00000021; // for Mac
    public static final int IPMSG_FILE_FINDERINFO = 0x00000022; // for Mac
    public static final int IPMSG_FILE_ACL = 0x00000030;
    public static final int IPMSG_FILE_ALIASFNAME = 0x00000040; // alias fname
    public static final int IPMSG_FILE_UNICODEFNAME = 0x00000041;// Unicode
    // fname

    protected String version; // 版本号 目前都为1
    protected String packetNo;// 数据包编号
    protected String senderName; // 发送者昵称（若是PC，则为登录名）
    protected String senderHost; // 发送主机名
    protected int commandNo; // 命令
    protected String additionalSection; // 附加数据
    protected String mac;
    protected String ip;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPacketNo() {
        return packetNo;
    }

    public void setPacketNo(String packetNo) {
        this.packetNo = packetNo;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderHost() {
        return senderHost;
    }

    public void setSenderHost(String senderHost) {
        this.senderHost = senderHost;
    }

    public int getCommandNo() {
        return commandNo;
    }

    public void setCommandNo(int commandNo) {
        this.commandNo = commandNo;
    }

    public String getAdditionalSection() {
        return additionalSection;
    }

    public void setAdditionalSection(String additionalSection) {
        this.additionalSection = additionalSection;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    // 得到数据包编号，毫秒数
    protected String getSeconds() {
        Date nowDate = new Date();
        return Long.toString(nowDate.getTime());
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 得到协议串
     *
     * @return
     */
    public abstract String getMsg();
}