package com.bumptech.glide.integration.webp;

import androidx.annotation.Keep;
import com.bumptech.glide.util.i;
import java.nio.ByteBuffer;

@Keep
public class WebpImage {
    private int mBackgroundColor;
    private int mDurationMs;
    private int mFrameCount;
    private int[] mFrameDurations;
    private int mHeigth;
    private int mLoopCount;
    @Keep
    private long mNativePtr;
    private int mWidth;

    private static native WebpImage nativeCreateFromDirectByteBuffer(ByteBuffer byteBuffer);

    private native void nativeDispose();

    private native void nativeFinalize();

    private native WebpFrame nativeGetFrame(int i);

    private native int nativeGetSizeInBytes();

    static {
        System.loadLibrary("glide-webp");
    }

    public static WebpImage create(byte[] source) {
        i.d(source);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(source.length);
        byteBuffer.put(source);
        byteBuffer.rewind();
        return nativeCreateFromDirectByteBuffer(byteBuffer);
    }

    @Keep
    WebpImage(long nativePtr, int width, int height, int frameCount, int durationMs, int[] frameDurations, int loopCount, int backgroundColor) {
        if (nativePtr != 0) {
            this.mWidth = width;
            this.mHeigth = height;
            this.mFrameCount = frameCount;
            this.mDurationMs = durationMs;
            this.mFrameDurations = frameDurations;
            this.mLoopCount = loopCount;
            fixFrameDurations(frameDurations);
            this.mBackgroundColor = backgroundColor;
            this.mNativePtr = nativePtr;
            return;
        }
        throw new RuntimeException("internal error: native WebpImage is 0");
    }

    private void fixFrameDurations(int[] frameDurationMs) {
        for (int i = 0; i < frameDurationMs.length; i++) {
            if (frameDurationMs[i] < 11) {
                frameDurationMs[i] = 100;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        nativeFinalize();
    }

    public void dispose() {
        nativeDispose();
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeigth;
    }

    public int getFrameCount() {
        return this.mFrameCount;
    }

    public int getDuration() {
        return this.mDurationMs;
    }

    public int[] getFrameDurations() {
        return this.mFrameDurations;
    }

    public int getLoopCount() {
        return this.mLoopCount;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public WebpFrame getFrame(int frameNubmer) {
        return nativeGetFrame(frameNubmer);
    }

    public a getFrameInfo(int frameNumber) {
        WebpFrame frame = getFrame(frameNumber);
        try {
            return new a(frameNumber, frame);
        } finally {
            frame.dispose();
        }
    }

    public int getSizeInBytes() {
        return nativeGetSizeInBytes();
    }
}
