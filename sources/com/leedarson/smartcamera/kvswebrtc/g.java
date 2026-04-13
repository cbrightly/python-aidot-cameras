package com.leedarson.smartcamera.kvswebrtc;

import com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.n;
import org.webrtc.AudioTrack;
import org.webrtc.DataChannel;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.audio.JavaAudioDeviceModule;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    public final /* synthetic */ f0 c;
    public final /* synthetic */ AudioTrack d;
    public final /* synthetic */ DataChannel f;
    public final /* synthetic */ JavaAudioDeviceModule p0;
    public final /* synthetic */ PeerConnection q;
    public final /* synthetic */ boolean x;
    public final /* synthetic */ n y;
    public final /* synthetic */ PeerConnectionFactory z;

    public /* synthetic */ g(f0 f0Var, AudioTrack audioTrack, DataChannel dataChannel, PeerConnection peerConnection, boolean z2, n nVar, PeerConnectionFactory peerConnectionFactory, JavaAudioDeviceModule javaAudioDeviceModule) {
        this.c = f0Var;
        this.d = audioTrack;
        this.f = dataChannel;
        this.q = peerConnection;
        this.x = z2;
        this.y = nVar;
        this.z = peerConnectionFactory;
        this.p0 = javaAudioDeviceModule;
    }

    public final void run() {
        this.c.c2(this.d, this.f, this.q, this.x, this.y, this.z, this.p0);
    }
}
