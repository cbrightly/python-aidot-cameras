package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel;

import com.meituan.robust.ChangeQuickRedirect;
import org.json.JSONObject;

/* compiled from: ISignalSwitchChannelHandler */
public interface b {

    /* compiled from: ISignalSwitchChannelHandler */
    public enum a {
        MQTT,
        TCP;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    void a(String str, JSONObject jSONObject, a aVar);

    void b(int i, String str, String str2, a aVar);
}
