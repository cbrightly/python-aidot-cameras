package org.webrtc;

public class LibH265Decoder extends WrappedNativeVideoDecoder {
    private long mNativeDecoder;

    static native long nativeCreateDecoder();

    static native void nativePendingDecoder(long j);

    static native void nativeResumeDecoder(long j);

    public long createNativeVideoDecoder() {
        long nativeCreateDecoder = nativeCreateDecoder();
        this.mNativeDecoder = nativeCreateDecoder;
        return nativeCreateDecoder;
    }

    public void closeDecoder() {
        nativePendingDecoder(this.mNativeDecoder);
    }

    public void openDecoder() {
        nativeResumeDecoder(this.mNativeDecoder);
    }
}
