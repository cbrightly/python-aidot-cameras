package org.webrtc;

import org.webrtc.AndroidVideoDecoder;

/* compiled from: lambda */
public final /* synthetic */ class s implements AndroidVideoDecoder.IOnAndroidVideoCodecError {
    public final /* synthetic */ MediaCodecVideoDecoderFactory a;

    public /* synthetic */ s(MediaCodecVideoDecoderFactory mediaCodecVideoDecoderFactory) {
        this.a = mediaCodecVideoDecoderFactory;
    }

    public final void onFirstFrameKeyDecodedError(int i, String str) {
        this.a.a(i, str);
    }
}
