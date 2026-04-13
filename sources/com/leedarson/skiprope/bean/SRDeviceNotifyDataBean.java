package com.leedarson.skiprope.bean;

import com.google.gson.Gson;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.util.internal.StringUtil;

public class SRDeviceNotifyDataBean {
    public static final int COMMAND_TYPE_HISTORY = 3;
    public static final int COMMAND_TYPE_REALTIME = 1;
    public static final int COMMAND_TYPE_RESULT = 2;
    public static final int MODE_COUNT = 3;
    public static final int MODE_FREE = 1;
    public static final int MODE_TIME = 2;
    public static ChangeQuickRedirect changeQuickRedirect;
    public int command;
    public transient long jumpedSecondActually;
    public String mac;
    public int maxConsecutive;
    public int maxFrequency;
    public int meanFrequency;
    public int mode;
    public int target;
    public long timestamp;
    public int totalCount;
    public int totalTime;
    public int trippedOver;

    public static SRDeviceNotifyDataBean fromHistoryBean(String mac2, int command2, SRDeviceHistoryDataBean historyDataBean) {
        Object[] objArr = {mac2, new Integer(command2), historyDataBean};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 9498, new Class[]{String.class, Integer.TYPE, SRDeviceHistoryDataBean.class}, SRDeviceNotifyDataBean.class);
        if (proxy.isSupported) {
            return (SRDeviceNotifyDataBean) proxy.result;
        }
        SRDeviceNotifyDataBean notifyDataBean = new SRDeviceNotifyDataBean(command2);
        notifyDataBean.mac = mac2;
        notifyDataBean.command = command2;
        notifyDataBean.mode = historyDataBean.mode;
        notifyDataBean.target = historyDataBean.target;
        notifyDataBean.totalTime = (int) historyDataBean.jumpedSecondActually;
        notifyDataBean.totalCount = (int) historyDataBean.jumpedCountActually;
        int i = historyDataBean.meanFrequency;
        notifyDataBean.meanFrequency = i;
        int i2 = historyDataBean.maxFrequency;
        notifyDataBean.maxFrequency = i2;
        notifyDataBean.timestamp = historyDataBean.timestamp;
        if (historyDataBean.historyItems != null) {
            notifyDataBean.maxConsecutive = historyDataBean.maxKeepJump;
            notifyDataBean.trippedOver = historyDataBean.fallDownCount;
        }
        if (i < 0) {
            notifyDataBean.meanFrequency = i + 256;
        }
        if (i2 < 0) {
            notifyDataBean.maxFrequency = i2 + 256;
        }
        return notifyDataBean;
    }

    public SRDeviceNotifyDataBean(int command2) {
        this.command = command2;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9499, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "{" + "\"mac\":\"" + this.mac + StringUtil.DOUBLE_QUOTE + ",\"command\":" + this.command + ",\"mode\":" + this.mode + ",\"target\":" + this.target + ",\"totalTime\":" + this.totalTime + ",\"totalCount\":" + this.totalCount + ",\"jumpedSecondActually\":" + this.jumpedSecondActually + ",\"meanFrequency\":" + this.meanFrequency + ",\"maxFrequency\":" + this.maxFrequency + ",\"timestamp\":" + this.timestamp + '}';
    }

    public String toJSON() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9500, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new Gson().toJson((Object) this);
    }
}
