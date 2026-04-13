package org.webrtc;

public class LibaomAv1Decoder extends WrappedNativeVideoDecoder {
    static native long nativeCreateDecoder();

    static native boolean nativeIsSupported();

    public long createNativeVideoDecoder() {
        return nativeCreateDecoder();
    }
}
