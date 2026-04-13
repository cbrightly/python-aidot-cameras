package com.leedarson.smartcamera.kvswebrtc;

import org.webrtc.AndroidVideoDecoder;

/* compiled from: lambda */
public final /* synthetic */ class v implements AndroidVideoDecoder.IOnAndroidVideoCodecError {
    public final /* synthetic */ f0 a;

    public /* synthetic */ v(f0 f0Var) {
        this.a = f0Var;
    }

    public final void onFirstFrameKeyDecodedError(int i, String str) {
        this.a.O1(i, str);
    }
}
