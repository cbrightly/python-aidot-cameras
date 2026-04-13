package com.leedarson.smartcamera.kvswebrtc.signaling.model.liveplay;

import com.google.gson.Gson;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class LiveRequestParamsBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String dstAddr = "";
    public int livePlay = 1;
    public String method = "livePlayReq";
    public LivePlayPaylodBean payload = new LivePlayPaylodBean();
    public String service = "IPC";
    public String srcAddr = "";

    public String toJson() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10002, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new Gson().toJson((Object) this);
    }
}
