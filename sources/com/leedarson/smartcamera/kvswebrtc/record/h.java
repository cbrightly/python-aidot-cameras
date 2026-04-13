package com.leedarson.smartcamera.kvswebrtc.record;

import android.annotation.SuppressLint;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.HashMap;
import org.webrtc.audio.JavaAudioDeviceModule;

/* compiled from: AudioSamplesInterceptor */
public class h implements JavaAudioDeviceModule.SamplesReadyCallback {
    public static ChangeQuickRedirect changeQuickRedirect;
    @SuppressLint({"UseSparseArrays"})
    protected final HashMap<Integer, JavaAudioDeviceModule.SamplesReadyCallback> c = new HashMap<>();

    public void onWebRtcAudioRecordSamplesReady(JavaAudioDeviceModule.AudioSamples audioSamples) {
        if (!PatchProxy.proxy(new Object[]{audioSamples}, this, changeQuickRedirect, false, 9942, new Class[]{JavaAudioDeviceModule.AudioSamples.class}, Void.TYPE).isSupported) {
            for (JavaAudioDeviceModule.SamplesReadyCallback callback : this.c.values()) {
                callback.onWebRtcAudioRecordSamplesReady(audioSamples);
            }
        }
    }

    public void a(Integer id, JavaAudioDeviceModule.SamplesReadyCallback callback) {
        Class[] clsArr = {Integer.class, JavaAudioDeviceModule.SamplesReadyCallback.class};
        if (!PatchProxy.proxy(new Object[]{id, callback}, this, changeQuickRedirect, false, 9943, clsArr, Void.TYPE).isSupported) {
            this.c.put(id, callback);
        }
    }

    public void b(Integer id) {
        if (!PatchProxy.proxy(new Object[]{id}, this, changeQuickRedirect, false, 9944, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            this.c.remove(id);
        }
    }
}
