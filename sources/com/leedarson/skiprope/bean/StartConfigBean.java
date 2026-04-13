package com.leedarson.skiprope.bean;

import com.leedarson.skiprope.util.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class StartConfigBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public BroadcastMode broadcast;
    public int countdown = 3;
    public String mac;
    public boolean resetDevice = true;
    public TrainingMode training;

    public static class BroadcastMode {
        public String bgmName = "-1";
        public int interval;
        public int mode;
        public boolean mute = false;
        public int voiceType;
    }

    public static class TrainingMode {
        public int mode;
        public int target;
    }

    public String getBgmName(String defaultName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{defaultName}, this, changeQuickRedirect, false, 9504, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        BroadcastMode broadcastMode = this.broadcast;
        if (broadcastMode != null && !"-1".equals(broadcastMode.bgmName)) {
            return this.broadcast.bgmName;
        }
        a.c("broadcast.bgmName字段为空，取默认第一首:" + defaultName);
        return defaultName;
    }
}
