package com.bumptech.glide.integration.webp;

import android.graphics.Bitmap;
import androidx.annotation.Keep;

@Keep
public class WebpFrame {
    static final int FRAME_DURATION_MS_FOR_MIN = 100;
    static final int MIN_FRAME_DURATION_MS = 11;
    boolean blendPreviousFrame;
    int delay;
    boolean disposeBackgroundColor;
    int ih;
    int iw;
    int ix;
    int iy;
    @Keep
    private long mNativePtr;

    private native void nativeDispose();

    private native void nativeFinalize();

    private native void nativeRenderFrame(int i, int i2, Bitmap bitmap);

    WebpFrame(long nativePtr, int xOffset, int yOffset, int width, int height, int delay2, boolean blendPreviousFrame2, boolean disposeBackgroundColor2) {
        this.mNativePtr = nativePtr;
        this.ix = xOffset;
        this.iy = yOffset;
        this.iw = width;
        this.ih = height;
        this.delay = delay2;
        this.blendPreviousFrame = blendPreviousFrame2;
        this.disposeBackgroundColor = disposeBackgroundColor2;
        fixFrameDuration();
    }

    private void fixFrameDuration() {
        if (this.delay < 11) {
            this.delay = 100;
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        nativeFinalize();
    }

    public void dispose() {
        nativeDispose();
    }

    public void renderFrame(int width, int height, Bitmap bitmap) {
        nativeRenderFrame(width, height, bitmap);
    }

    public int getWidth() {
        return this.iw;
    }

    public int getHeight() {
        return this.ih;
    }

    public int getDurationMs() {
        return this.delay;
    }

    public int getXOffest() {
        return this.ix;
    }

    public int getYOffest() {
        return this.iy;
    }

    public boolean shouldDisposeToBackgroundColor() {
        return this.disposeBackgroundColor;
    }

    public boolean isBlendWithPreviousFrame() {
        return this.blendPreviousFrame;
    }
}
