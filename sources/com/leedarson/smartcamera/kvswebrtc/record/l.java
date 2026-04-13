package com.leedarson.smartcamera.kvswebrtc.record;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.webrtc.audio.JavaAudioDeviceModule;
import org.webrtc.audio.WebRTCAudioTrackUtils;

/* compiled from: OutputAudioSamplesInterceptor */
public class l extends h {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final JavaAudioDeviceModule d;

    public l(JavaAudioDeviceModule audioDeviceModule) {
        this.d = audioDeviceModule;
    }

    public void a(Integer id, JavaAudioDeviceModule.SamplesReadyCallback callback) {
        Class[] clsArr = {Integer.class, JavaAudioDeviceModule.SamplesReadyCallback.class};
        if (!PatchProxy.proxy(new Object[]{id, callback}, this, changeQuickRedirect, false, 9965, clsArr, Void.TYPE).isSupported) {
            if (this.c.isEmpty()) {
                WebRTCAudioTrackUtils.attachOutputCallback(this, this.d);
            }
            super.a(id, callback);
        }
    }

    public void b(Integer id) {
        if (!PatchProxy.proxy(new Object[]{id}, this, changeQuickRedirect, false, 9966, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            super.b(id);
            if (this.c.isEmpty()) {
                WebRTCAudioTrackUtils.detachOutputCallback(this.d);
            }
        }
    }
}
