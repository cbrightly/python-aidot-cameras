package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: ISignalSwitchChannel */
public interface a {

    /* renamed from: com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.a$a  reason: collision with other inner class name */
    /* compiled from: ISignalSwitchChannel */
    public enum C0173a {
        OFFER,
        CANDIDATE,
        LIVEPLAY;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    void a(String str, String str2, b bVar, LdsSignalSendConfigBean ldsSignalSendConfigBean, C0173a aVar);
}
