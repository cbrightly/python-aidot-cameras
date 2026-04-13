package org.webrtc;

public class LibH265Encoder extends WrappedNativeVideoDecoder {
    static native long nativeCreateDecoder();

    public long createNativeVideoDecoder() {
        return nativeCreateDecoder();
    }
}
