package com.leedarson.smartcamera.kvswebrtc.signaling;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.RtpReceiver;
import org.webrtc.RtpTransceiver;
import org.webrtc.s0;
import timber.log.a;

/* compiled from: LdsWebRtcChannelConnection */
public class b implements PeerConnection.Observer {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean a = false;
    private String b = "";

    public /* synthetic */ void onRemoveTrack(RtpReceiver rtpReceiver) {
        s0.d(this, rtpReceiver);
    }

    public /* synthetic */ void onStandardizedIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
        s0.f(this, iceConnectionState);
    }

    public /* synthetic */ void onTrack(RtpTransceiver rtpTransceiver) {
        s0.g(this, rtpTransceiver);
    }

    public b(String peerId) {
    }

    public void c() {
        this.a = true;
    }

    public boolean b() {
        return this.a;
    }

    public void onSignalingChange(PeerConnection.SignalingState signalingState) {
        if (!PatchProxy.proxy(new Object[]{signalingState}, this, changeQuickRedirect, false, 9984, new Class[]{PeerConnection.SignalingState.class}, Void.TYPE).isSupported) {
            a("onSignalingChange(): signalingState = [" + signalingState + "]");
        }
    }

    public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
        if (!PatchProxy.proxy(new Object[]{iceConnectionState}, this, changeQuickRedirect, false, 9985, new Class[]{PeerConnection.IceConnectionState.class}, Void.TYPE).isSupported) {
            a("onIceConnectionChange(): iceConnectionState = [" + iceConnectionState + "]");
        }
    }

    public void onIceConnectionReceivingChange(boolean connectionChange) {
        Object[] objArr = {new Byte(connectionChange ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9986, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            a("onIceConnectionReceivingChange(): connectionChange = [" + connectionChange + "]");
        }
    }

    public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
        if (!PatchProxy.proxy(new Object[]{iceGatheringState}, this, changeQuickRedirect, false, 9987, new Class[]{PeerConnection.IceGatheringState.class}, Void.TYPE).isSupported) {
            a("onIceGatheringChange(): iceGatheringState = [" + iceGatheringState + "]");
        }
    }

    public void onIceCandidate(IceCandidate iceCandidate) {
        if (!PatchProxy.proxy(new Object[]{iceCandidate}, this, changeQuickRedirect, false, 9988, new Class[]{IceCandidate.class}, Void.TYPE).isSupported) {
            Log.d("LdsWebRtcChannelConnection", "onIceCandidate(): iceCandidate = [" + iceCandidate + "]");
        }
    }

    public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {
        if (!PatchProxy.proxy(new Object[]{iceCandidates}, this, changeQuickRedirect, false, 9989, new Class[]{IceCandidate[].class}, Void.TYPE).isSupported) {
            a("onIceCandidatesRemoved(): iceCandidates Length = [" + iceCandidates.length + "]");
        }
    }

    public void onAddStream(MediaStream mediaStream) {
        if (!PatchProxy.proxy(new Object[]{mediaStream}, this, changeQuickRedirect, false, 9990, new Class[]{MediaStream.class}, Void.TYPE).isSupported) {
            a("[Stream] 收到远端添加一个stream " + mediaStream);
        }
    }

    public void onRemoveStream(MediaStream mediaStream) {
        if (!PatchProxy.proxy(new Object[]{mediaStream}, this, changeQuickRedirect, false, 9991, new Class[]{MediaStream.class}, Void.TYPE).isSupported) {
            a("[Stream] 收到远端移除一个stream " + mediaStream);
        }
    }

    public void onDataChannel(DataChannel dataChannel) {
        if (!PatchProxy.proxy(new Object[]{dataChannel}, this, changeQuickRedirect, false, 9992, new Class[]{DataChannel.class}, Void.TYPE).isSupported) {
            a("[DataChannel] DataChannel打开 onDataChannel(): dataChannel = [" + dataChannel + "]");
        }
    }

    public void onRenegotiationNeeded() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9993, new Class[0], Void.TYPE).isSupported) {
            a("[媒体协商] 重新触发媒体协商 onRenegotiationNeeded():");
        }
    }

    public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {
        Class[] clsArr = {RtpReceiver.class, MediaStream[].class};
        if (!PatchProxy.proxy(new Object[]{rtpReceiver, mediaStreams}, this, changeQuickRedirect, false, 9994, clsArr, Void.TYPE).isSupported) {
            a("[媒体Track新增] onAddTrack(): rtpReceiver = [" + rtpReceiver + "], mediaStreams Length = [" + mediaStreams.length + "]");
        }
    }

    private void a(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 9995, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g("LdsWebRtcChannelConnection").a(message, new Object[0]);
        }
    }
}
