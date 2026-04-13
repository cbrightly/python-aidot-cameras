package org.webrtc;

import org.webrtc.AndroidVideoDecoder;

/* compiled from: lambda */
public final /* synthetic */ class f implements AndroidVideoDecoder.IOnAndroidVideoCodecError {
    public final /* synthetic */ DefaultVideoDecoderFactory a;

    public /* synthetic */ f(DefaultVideoDecoderFactory defaultVideoDecoderFactory) {
        this.a = defaultVideoDecoderFactory;
    }

    public final void onFirstFrameKeyDecodedError(int i, String str) {
        this.a.a(i, str);
    }
}
