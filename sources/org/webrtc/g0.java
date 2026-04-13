package org.webrtc;

import org.webrtc.VideoEncoder;

/* compiled from: lambda */
public final /* synthetic */ class g0 implements VideoEncoder.Callback {
    public final /* synthetic */ long a;

    public /* synthetic */ g0(long j) {
        this.a = j;
    }

    public final void onEncodedFrame(EncodedImage encodedImage, VideoEncoder.CodecSpecificInfo codecSpecificInfo) {
        VideoEncoderWrapper.nativeOnEncodedFrame(this.a, encodedImage);
    }
}
