package org.webrtc;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.Nullable;
import java.util.concurrent.Callable;
import org.webrtc.EglBase;
import org.webrtc.TextureBufferImpl;
import org.webrtc.VideoFrame;

public class SurfaceTextureHelper {
    private static final String TAG = "SurfaceTextureHelper";
    private final EglBase eglBase;
    /* access modifiers changed from: private */
    public final FrameRefMonitor frameRefMonitor;
    private int frameRotation;
    private final Handler handler;
    /* access modifiers changed from: private */
    public boolean hasPendingTexture;
    private boolean isQuitting;
    private volatile boolean isTextureInUse;
    private int keyFrame;
    /* access modifiers changed from: private */
    @Nullable
    public VideoSink listener;
    private final int oesTextureId;
    /* access modifiers changed from: private */
    @Nullable
    public VideoSink pendingListener;
    final Runnable setListenerRunnable;
    private String streamId;
    private final SurfaceTexture surfaceTexture;
    private int textureHeight;
    private final TextureBufferImpl.RefCountMonitor textureRefCountMonitor;
    private int textureWidth;
    @Nullable
    private final TimestampAligner timestampAligner;
    private final YuvConverter yuvConverter;

    public interface FrameRefMonitor {
        void onDestroyBuffer(VideoFrame.TextureBuffer textureBuffer);

        void onNewBuffer(VideoFrame.TextureBuffer textureBuffer);

        void onReleaseBuffer(VideoFrame.TextureBuffer textureBuffer);

        void onRetainBuffer(VideoFrame.TextureBuffer textureBuffer);
    }

    public static SurfaceTextureHelper create(String threadName, EglBase.Context sharedContext, boolean alignTimestamps, YuvConverter yuvConverter2, FrameRefMonitor frameRefMonitor2) {
        HandlerThread thread = new HandlerThread(threadName);
        thread.start();
        Handler handler2 = new Handler(thread.getLooper());
        final EglBase.Context context = sharedContext;
        final Handler handler3 = handler2;
        final boolean z = alignTimestamps;
        final YuvConverter yuvConverter3 = yuvConverter2;
        final FrameRefMonitor frameRefMonitor3 = frameRefMonitor2;
        final String str = threadName;
        return (SurfaceTextureHelper) ThreadUtils.invokeAtFrontUninterruptibly(handler2, new Callable<SurfaceTextureHelper>() {
            @Nullable
            public SurfaceTextureHelper call() {
                try {
                    return new SurfaceTextureHelper(EglBase.Context.this, handler3, z, yuvConverter3, frameRefMonitor3);
                } catch (RuntimeException e) {
                    Logging.e(SurfaceTextureHelper.TAG, str + " create failure", e);
                    return null;
                }
            }
        });
    }

    public static SurfaceTextureHelper create(String threadName, EglBase.Context sharedContext) {
        return create(threadName, sharedContext, false, new YuvConverter(), (FrameRefMonitor) null);
    }

    public static SurfaceTextureHelper create(String threadName, EglBase.Context sharedContext, boolean alignTimestamps) {
        return create(threadName, sharedContext, alignTimestamps, new YuvConverter(), (FrameRefMonitor) null);
    }

    public static SurfaceTextureHelper create(String threadName, EglBase.Context sharedContext, boolean alignTimestamps, YuvConverter yuvConverter2) {
        return create(threadName, sharedContext, alignTimestamps, yuvConverter2, (FrameRefMonitor) null);
    }

    private SurfaceTextureHelper(EglBase.Context sharedContext, Handler handler2, boolean alignTimestamps, YuvConverter yuvConverter2, FrameRefMonitor frameRefMonitor2) {
        this.textureRefCountMonitor = new TextureBufferImpl.RefCountMonitor() {
            public void onRetain(TextureBufferImpl textureBuffer) {
                if (SurfaceTextureHelper.this.frameRefMonitor != null) {
                    SurfaceTextureHelper.this.frameRefMonitor.onRetainBuffer(textureBuffer);
                }
            }

            public void onRelease(TextureBufferImpl textureBuffer) {
                if (SurfaceTextureHelper.this.frameRefMonitor != null) {
                    SurfaceTextureHelper.this.frameRefMonitor.onReleaseBuffer(textureBuffer);
                }
            }

            public void onDestroy(TextureBufferImpl textureBuffer) {
                SurfaceTextureHelper.this.returnTextureFrame();
                if (SurfaceTextureHelper.this.frameRefMonitor != null) {
                    SurfaceTextureHelper.this.frameRefMonitor.onDestroyBuffer(textureBuffer);
                }
            }
        };
        this.setListenerRunnable = new Runnable() {
            public void run() {
                Logging.d(SurfaceTextureHelper.TAG, "Setting listener to " + SurfaceTextureHelper.this.pendingListener);
                SurfaceTextureHelper surfaceTextureHelper = SurfaceTextureHelper.this;
                VideoSink unused = surfaceTextureHelper.listener = surfaceTextureHelper.pendingListener;
                VideoSink unused2 = SurfaceTextureHelper.this.pendingListener = null;
                if (SurfaceTextureHelper.this.hasPendingTexture) {
                    SurfaceTextureHelper.this.updateTexImage();
                    boolean unused3 = SurfaceTextureHelper.this.hasPendingTexture = false;
                }
            }
        };
        if (handler2.getLooper().getThread() == Thread.currentThread()) {
            this.handler = handler2;
            this.timestampAligner = alignTimestamps ? new TimestampAligner() : null;
            this.yuvConverter = yuvConverter2;
            this.frameRefMonitor = frameRefMonitor2;
            EglBase c = r0.c(sharedContext, EglBase.CONFIG_PIXEL_BUFFER);
            this.eglBase = c;
            try {
                c.createDummyPbufferSurface();
                c.makeCurrent();
                int generateTexture = GlUtil.generateTexture(36197);
                this.oesTextureId = generateTexture;
                SurfaceTexture surfaceTexture2 = new SurfaceTexture(generateTexture);
                this.surfaceTexture = surfaceTexture2;
                setOnFrameAvailableListener(surfaceTexture2, new v(this), handler2);
            } catch (RuntimeException e) {
                this.eglBase.release();
                handler2.getLooper().quit();
                throw e;
            }
        } else {
            throw new IllegalStateException("SurfaceTextureHelper must be created on the handler thread");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$0 */
    public /* synthetic */ void c(SurfaceTexture st) {
        if (this.hasPendingTexture) {
            Logging.d(TAG, "A frame is already pending, dropping frame.");
        }
        this.hasPendingTexture = true;
        tryDeliverTextureFrame();
    }

    @TargetApi(21)
    private static void setOnFrameAvailableListener(SurfaceTexture surfaceTexture2, SurfaceTexture.OnFrameAvailableListener listener2, Handler handler2) {
        if (Build.VERSION.SDK_INT >= 21) {
            surfaceTexture2.setOnFrameAvailableListener(listener2, handler2);
        } else {
            surfaceTexture2.setOnFrameAvailableListener(listener2);
        }
    }

    public void startListening(VideoSink listener2) {
        if (this.listener == null && this.pendingListener == null) {
            this.pendingListener = listener2;
            this.handler.post(this.setListenerRunnable);
            return;
        }
        throw new IllegalStateException("SurfaceTextureHelper listener has already been set.");
    }

    public void stopListening() {
        Logging.d(TAG, "stopListening()");
        this.handler.removeCallbacks(this.setListenerRunnable);
        ThreadUtils.invokeAtFrontUninterruptibly(this.handler, (Runnable) new a0(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$stopListening$1 */
    public /* synthetic */ void g() {
        this.listener = null;
        this.pendingListener = null;
    }

    public void setTextureSize(int textureWidth2, int textureHeight2) {
        if (textureWidth2 <= 0) {
            throw new IllegalArgumentException("Texture width must be positive, but was " + textureWidth2);
        } else if (textureHeight2 > 0) {
            this.surfaceTexture.setDefaultBufferSize(textureWidth2, textureHeight2);
            this.handler.post(new x(this, textureWidth2, textureHeight2));
        } else {
            throw new IllegalArgumentException("Texture height must be positive, but was " + textureHeight2);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$setTextureSize$2 */
    public /* synthetic */ void f(int textureWidth2, int textureHeight2) {
        this.textureWidth = textureWidth2;
        this.textureHeight = textureHeight2;
        tryDeliverTextureFrame();
    }

    public void setTextureStreamId(String streamId2) {
        this.streamId = streamId2;
    }

    public void setTextureFrameKey(int keyFrame2) {
        this.keyFrame = keyFrame2;
    }

    public void forceFrame() {
        this.handler.post(new y(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$forceFrame$3 */
    public /* synthetic */ void b() {
        this.hasPendingTexture = true;
        tryDeliverTextureFrame();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$setFrameRotation$4 */
    public /* synthetic */ void e(int rotation) {
        this.frameRotation = rotation;
    }

    public void setFrameRotation(int rotation) {
        this.handler.post(new z(this, rotation));
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.surfaceTexture;
    }

    public Handler getHandler() {
        return this.handler;
    }

    /* access modifiers changed from: private */
    public void returnTextureFrame() {
        this.handler.post(new b0(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$returnTextureFrame$5 */
    public /* synthetic */ void d() {
        this.isTextureInUse = false;
        if (this.isQuitting) {
            release();
        } else {
            tryDeliverTextureFrame();
        }
    }

    public boolean isTextureInUse() {
        return this.isTextureInUse;
    }

    public void dispose() {
        Logging.d(TAG, "dispose()");
        ThreadUtils.invokeAtFrontUninterruptibly(this.handler, (Runnable) new w(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$dispose$6 */
    public /* synthetic */ void a() {
        this.isQuitting = true;
        if (!this.isTextureInUse) {
            release();
        }
    }

    @Deprecated
    public VideoFrame.I420Buffer textureToYuv(VideoFrame.TextureBuffer textureBuffer) {
        return textureBuffer.toI420();
    }

    /* access modifiers changed from: private */
    public void updateTexImage() {
        synchronized (EglBase.lock) {
            this.surfaceTexture.updateTexImage();
        }
    }

    private void tryDeliverTextureFrame() {
        if (this.handler.getLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Wrong thread.");
        } else if (!this.isQuitting && this.hasPendingTexture && !this.isTextureInUse && this.listener != null) {
            if (this.textureWidth == 0 || this.textureHeight == 0) {
                Logging.w(TAG, "Texture size has not been set.");
                return;
            }
            this.isTextureInUse = true;
            this.hasPendingTexture = false;
            updateTexImage();
            float[] transformMatrix = new float[16];
            this.surfaceTexture.getTransformMatrix(transformMatrix);
            long timestampNs = this.surfaceTexture.getTimestamp();
            TimestampAligner timestampAligner2 = this.timestampAligner;
            if (timestampAligner2 != null) {
                timestampNs = timestampAligner2.translateTimestamp(timestampNs);
            }
            VideoFrame.TextureBuffer buffer = new TextureBufferImpl(this.textureWidth, this.textureHeight, VideoFrame.TextureBuffer.Type.OES, this.oesTextureId, RendererCommon.convertMatrixToAndroidGraphicsMatrix(transformMatrix), this.handler, this.yuvConverter, this.textureRefCountMonitor);
            FrameRefMonitor frameRefMonitor2 = this.frameRefMonitor;
            if (frameRefMonitor2 != null) {
                frameRefMonitor2.onNewBuffer(buffer);
            }
            VideoFrame frame = new VideoFrame(buffer, this.frameRotation, timestampNs, this.streamId, this.keyFrame);
            this.listener.onFrame(frame);
            frame.release();
        }
    }

    private void release() {
        if (this.handler.getLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Wrong thread.");
        } else if (this.isTextureInUse || !this.isQuitting) {
            throw new IllegalStateException("Unexpected release.");
        } else {
            this.yuvConverter.release();
            GLES20.glDeleteTextures(1, new int[]{this.oesTextureId}, 0);
            this.surfaceTexture.release();
            this.eglBase.release();
            this.handler.getLooper().quit();
            TimestampAligner timestampAligner2 = this.timestampAligner;
            if (timestampAligner2 != null) {
                timestampAligner2.dispose();
            }
        }
    }
}
