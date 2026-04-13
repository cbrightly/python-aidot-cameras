package com.leedarson.smartcamera.kvswebrtc.signaling;

import org.webrtc.DataChannel;
import org.webrtc.PeerConnection;

/* compiled from: OnDataListener */
public interface d {
    void a(DataChannel.State state);

    void b();

    void c(byte[] bArr);

    void onError(String str);

    void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState);
}
