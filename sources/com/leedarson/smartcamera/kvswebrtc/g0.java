package com.leedarson.smartcamera.kvswebrtc;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

/* compiled from: KinesisVideoSdpObserver */
public class g0 implements SdpObserver {
    protected static final String a = g0.class.getSimpleName();
    public static ChangeQuickRedirect changeQuickRedirect;

    public void onCreateSuccess(SessionDescription sessionDescription) {
        if (!PatchProxy.proxy(new Object[]{sessionDescription}, this, changeQuickRedirect, false, 9919, new Class[]{SessionDescription.class}, Void.TYPE).isSupported) {
            Log.d(a, "onCreateSuccess(): SDP=");
        }
    }

    public void onSetSuccess() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9920, new Class[0], Void.TYPE).isSupported) {
            Log.d(a, "onSetSuccess(): SDP");
        }
    }

    public void onCreateFailure(String error) {
        if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 9921, new Class[]{String.class}, Void.TYPE).isSupported) {
            String str = a;
            Log.e(str, "onCreateFailure(): Error=" + error);
        }
    }

    public void onSetFailure(String error) {
        if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 9922, new Class[]{String.class}, Void.TYPE).isSupported) {
            String str = a;
            Log.e(str, "onSetFailure(): Error=" + error);
        }
    }
}
