package org.webrtc;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.view.Surface;
import androidx.annotation.Nullable;

@TargetApi(21)
public class ScreenCapturerAndroid implements VideoCapturer, VideoSink {
    private static final int DISPLAY_FLAGS = 3;
    private static final int VIRTUAL_DISPLAY_DPI = 400;
    /* access modifiers changed from: private */
    @Nullable
    public CapturerObserver capturerObserver;
    private int height;
    private boolean isDisposed;
    /* access modifiers changed from: private */
    @Nullable
    public MediaProjection mediaProjection;
    /* access modifiers changed from: private */
    public final MediaProjection.Callback mediaProjectionCallback;
    @Nullable
    private MediaProjectionManager mediaProjectionManager;
    private final Intent mediaProjectionPermissionResultData;
    private long numCapturedFrames;
    /* access modifiers changed from: private */
    @Nullable
    public SurfaceTextureHelper surfaceTextureHelper;
    /* access modifiers changed from: private */
    @Nullable
    public VirtualDisplay virtualDisplay;
    private int width;

    public ScreenCapturerAndroid(Intent mediaProjectionPermissionResultData2, MediaProjection.Callback mediaProjectionCallback2) {
        this.mediaProjectionPermissionResultData = mediaProjectionPermissionResultData2;
        this.mediaProjectionCallback = mediaProjectionCallback2;
    }

    private void checkNotDisposed() {
        if (this.isDisposed) {
            throw new RuntimeException("capturer is disposed.");
        }
    }

    @Nullable
    public MediaProjection getMediaProjection() {
        return this.mediaProjection;
    }

    public synchronized void initialize(SurfaceTextureHelper surfaceTextureHelper2, Context applicationContext, CapturerObserver capturerObserver2) {
        checkNotDisposed();
        if (capturerObserver2 != null) {
            this.capturerObserver = capturerObserver2;
            if (surfaceTextureHelper2 != null) {
                this.surfaceTextureHelper = surfaceTextureHelper2;
                this.mediaProjectionManager = (MediaProjectionManager) applicationContext.getSystemService("media_projection");
            } else {
                throw new RuntimeException("surfaceTextureHelper not set.");
            }
        } else {
            throw new RuntimeException("capturerObserver not set.");
        }
    }

    public synchronized void startCapture(int width2, int height2, int ignoredFramerate) {
        checkNotDisposed();
        this.width = width2;
        this.height = height2;
        MediaProjection mediaProjection2 = this.mediaProjectionManager.getMediaProjection(-1, this.mediaProjectionPermissionResultData);
        this.mediaProjection = mediaProjection2;
        mediaProjection2.registerCallback(this.mediaProjectionCallback, this.surfaceTextureHelper.getHandler());
        createVirtualDisplay();
        this.capturerObserver.onCapturerStarted(true);
        this.surfaceTextureHelper.startListening(this);
    }

    public synchronized void stopCapture() {
        checkNotDisposed();
        ThreadUtils.invokeAtFrontUninterruptibly(this.surfaceTextureHelper.getHandler(), (Runnable) new Runnable() {
            public void run() {
                ScreenCapturerAndroid.this.surfaceTextureHelper.stopListening();
                ScreenCapturerAndroid.this.capturerObserver.onCapturerStopped();
                if (ScreenCapturerAndroid.this.virtualDisplay != null) {
                    ScreenCapturerAndroid.this.virtualDisplay.release();
                    VirtualDisplay unused = ScreenCapturerAndroid.this.virtualDisplay = null;
                }
                if (ScreenCapturerAndroid.this.mediaProjection != null) {
                    ScreenCapturerAndroid.this.mediaProjection.unregisterCallback(ScreenCapturerAndroid.this.mediaProjectionCallback);
                    ScreenCapturerAndroid.this.mediaProjection.stop();
                    MediaProjection unused2 = ScreenCapturerAndroid.this.mediaProjection = null;
                }
            }
        });
    }

    public synchronized void dispose() {
        this.isDisposed = true;
    }

    public synchronized void changeCaptureFormat(int width2, int height2, int ignoredFramerate) {
        checkNotDisposed();
        this.width = width2;
        this.height = height2;
        if (this.virtualDisplay != null) {
            ThreadUtils.invokeAtFrontUninterruptibly(this.surfaceTextureHelper.getHandler(), (Runnable) new Runnable() {
                public void run() {
                    ScreenCapturerAndroid.this.virtualDisplay.release();
                    ScreenCapturerAndroid.this.createVirtualDisplay();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void createVirtualDisplay() {
        this.surfaceTextureHelper.setTextureSize(this.width, this.height);
        this.virtualDisplay = this.mediaProjection.createVirtualDisplay("WebRTC_ScreenCapture", this.width, this.height, 400, 3, new Surface(this.surfaceTextureHelper.getSurfaceTexture()), (VirtualDisplay.Callback) null, (Handler) null);
    }

    public void onFrame(VideoFrame frame) {
        this.numCapturedFrames++;
        this.capturerObserver.onFrameCaptured(frame);
    }

    public boolean isScreencast() {
        return true;
    }

    public long getNumCapturedFrames() {
        return this.numCapturedFrames;
    }
}
