package com.leedarson.serviceimpl.tcp.beans;

import com.leedarson.serviceinterface.TcpService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class TCPSendTaskBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public TcpService.INativeTcpSendMsgHandler callback;
    public TCPChannelMapBean channel;
    public String deviceId;
    public String messageBody;
    public int priority;
    public String seqNum = "";
    public int taskId;
    public long timeOutDeadline = 0;

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8994, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "TCPSendTaskBean{taskId=" + this.taskId + ", seqNum='" + this.seqNum + '\'' + ", deviceId='" + this.deviceId + '\'' + ", messageBody='" + this.messageBody + '\'' + ", timeOutDeadline=" + this.timeOutDeadline + ", callback=" + this.callback + ", channel=" + this.channel + ", priority=" + this.priority + '}';
    }
}
