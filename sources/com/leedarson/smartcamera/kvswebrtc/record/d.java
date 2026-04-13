package com.leedarson.smartcamera.kvswebrtc.record;

import org.webrtc.audio.JavaAudioDeviceModule;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ m c;
    public final /* synthetic */ JavaAudioDeviceModule.AudioSamples d;

    public /* synthetic */ d(m mVar, JavaAudioDeviceModule.AudioSamples audioSamples) {
        this.c = mVar;
        this.d = audioSamples;
    }

    public final void run() {
        this.c.k(this.d);
    }
}
