package org.webrtc;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.webrtc.EglBase;
import org.webrtc.RendererCommon;

public class EglRenderer implements VideoSink {
    private static final long LOG_INTERVAL_SEC = 4;
    private static final String TAG = "EglRenderer";
    private final GlTextureFrameBuffer bitmapTextureFramebuffer;
    private final Matrix drawMatrix;
    @Nullable
    private RendererCommon.GlDrawer drawer;
    /* access modifiers changed from: private */
    @Nullable
    public EglBase eglBase;
    private final EglSurfaceCreation eglSurfaceCreationRunnable;
    private volatile ErrorCallback errorCallback;
    private final Object fpsReductionLock;
    private final VideoFrameDrawer frameDrawer;
    private final ArrayList<FrameListenerAndParams> frameListeners;
    private final Object frameLock;
    private int framesDropped;
    private int framesReceived;
    private int framesRendered;
    /* access modifiers changed from: private */
    public final Object handlerLock;
    private float layoutAspectRatio;
    private final Object layoutLock;
    /* access modifiers changed from: private */
    public final Runnable logStatisticsRunnable;
    private long minRenderPeriodNs;
    private boolean mirrorHorizontally;
    private boolean mirrorVertically;
    protected final String name;
    private long nextFrameTimeNs;
    @Nullable
    private VideoFrame pendingFrame;
    private long renderSwapBufferTimeNs;
    /* access modifiers changed from: private */
    @Nullable
    public Handler renderThreadHandler;
    private long renderTimeNs;
    private final Object statisticsLock;
    private long statisticsStartTimeNs;
    private boolean usePresentationTimeStamp;

    public interface ErrorCallback {
        void onGlOutOfMemory();
    }

    public interface FrameListener {
        void onFrame(Bitmap bitmap);
    }

    public static class FrameListenerAndParams {
        public final boolean applyFpsReduction;
        public final RendererCommon.GlDrawer drawer;
        public final FrameListener listener;
        public final float scale;

        public FrameListenerAndParams(FrameListener listener2, float scale2, RendererCommon.GlDrawer drawer2, boolean applyFpsReduction2) {
            this.listener = listener2;
            this.scale = scale2;
            this.drawer = drawer2;
            this.applyFpsReduction = applyFpsReduction2;
        }
    }

    public class EglSurfaceCreation implements Runnable {
        private Object surface;

        private EglSurfaceCreation() {
        }

        public synchronized void setSurface(Object surface2) {
            this.surface = surface2;
        }

        public synchronized void run() {
            if (!(this.surface == null || EglRenderer.this.eglBase == null || EglRenderer.this.eglBase.hasSurface())) {
                Object obj = this.surface;
                if (obj instanceof Surface) {
                    EglRenderer.this.eglBase.createSurface((Surface) this.surface);
                } else if (obj instanceof SurfaceTexture) {
                    EglRenderer.this.eglBase.createSurface((SurfaceTexture) this.surface);
                } else {
                    throw new IllegalStateException("Invalid surface: " + this.surface);
                }
                EglRenderer.this.eglBase.makeCurrent();
                GLES20.glPixelStorei(3317, 1);
            }
        }
    }

    public static class HandlerWithExceptionCallback extends Handler {
        private final Runnable exceptionCallback;

        public HandlerWithExceptionCallback(Looper looper, Runnable exceptionCallback2) {
            super(looper);
            this.exceptionCallback = exceptionCallback2;
        }

        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                Logging.e(EglRenderer.TAG, "Exception on EglRenderer thread", e);
                this.exceptionCallback.run();
                throw e;
            }
        }
    }

    public EglRenderer(String name2) {
        this(name2, new VideoFrameDrawer());
    }

    public EglRenderer(String name2, VideoFrameDrawer videoFrameDrawer) {
        this.handlerLock = new Object();
        this.frameListeners = new ArrayList<>();
        this.fpsReductionLock = new Object();
        this.drawMatrix = new Matrix();
        this.frameLock = new Object();
        this.layoutLock = new Object();
        this.statisticsLock = new Object();
        this.bitmapTextureFramebuffer = new GlTextureFrameBuffer(6408);
        this.logStatisticsRunnable = new Runnable() {
            public void run() {
                EglRenderer.this.logStatistics();
                synchronized (EglRenderer.this.handlerLock) {
                    if (EglRenderer.this.renderThreadHandler != null) {
                        EglRenderer.this.renderThreadHandler.removeCallbacks(EglRenderer.this.logStatisticsRunnable);
                        EglRenderer.this.renderThreadHandler.postDelayed(EglRenderer.this.logStatisticsRunnable, TimeUnit.SECONDS.toMillis(4));
                    }
                }
            }
        };
        this.eglSurfaceCreationRunnable = new EglSurfaceCreation();
        this.name = name2;
        this.frameDrawer = videoFrameDrawer;
    }

    public void init(@Nullable EglBase.Context sharedContext, int[] configAttributes, RendererCommon.GlDrawer drawer2, boolean usePresentationTimeStamp2) {
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler == null) {
                logD("Initializing EglRenderer");
                this.drawer = drawer2;
                this.usePresentationTimeStamp = usePresentationTimeStamp2;
                HandlerThread renderThread = new HandlerThread(this.name + TAG);
                renderThread.start();
                HandlerWithExceptionCallback handlerWithExceptionCallback = new HandlerWithExceptionCallback(renderThread.getLooper(), new Runnable() {
                    public void run() {
                        synchronized (EglRenderer.this.handlerLock) {
                            Handler unused = EglRenderer.this.renderThreadHandler = null;
                        }
                    }
                });
                this.renderThreadHandler = handlerWithExceptionCallback;
                ThreadUtils.invokeAtFrontUninterruptibly((Handler) handlerWithExceptionCallback, (Runnable) new l(this, sharedContext, configAttributes));
                this.renderThreadHandler.post(this.eglSurfaceCreationRunnable);
                resetStatistics(System.nanoTime());
                this.renderThreadHandler.postDelayed(this.logStatisticsRunnable, TimeUnit.SECONDS.toMillis(4));
            } else {
                throw new IllegalStateException(this.name + "Already initialized");
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$init$0 */
    public /* synthetic */ void d(EglBase.Context sharedContext, int[] configAttributes) {
        if (sharedContext == null) {
            logD("EglBase10.create context");
            this.eglBase = r0.e(configAttributes);
            return;
        }
        logD("EglBase.create shared context");
        this.eglBase = r0.c(sharedContext, configAttributes);
    }

    public void init(@Nullable EglBase.Context sharedContext, int[] configAttributes, RendererCommon.GlDrawer drawer2) {
        init(sharedContext, configAttributes, drawer2, false);
    }

    public void createEglSurface(Surface surface) {
        createEglSurfaceInternal(surface);
    }

    public void createEglSurface(SurfaceTexture surfaceTexture) {
        createEglSurfaceInternal(surfaceTexture);
    }

    private void createEglSurfaceInternal(Object surface) {
        this.eglSurfaceCreationRunnable.setSurface(surface);
        postToRenderThread(this.eglSurfaceCreationRunnable);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003c, code lost:
        org.webrtc.ThreadUtils.awaitUninterruptibly(r0);
        r2 = r5.frameLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0041, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r1 = r5.pendingFrame;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        if (r1 == null) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0046, code lost:
        r1.release();
        r5.pendingFrame = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004b, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004c, code lost:
        logD("Releasing done.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0051, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void release() {
        /*
            r5 = this;
            java.lang.String r0 = "Releasing."
            r5.logD(r0)
            java.util.concurrent.CountDownLatch r0 = new java.util.concurrent.CountDownLatch
            r1 = 1
            r0.<init>(r1)
            java.lang.Object r1 = r5.handlerLock
            monitor-enter(r1)
            android.os.Handler r2 = r5.renderThreadHandler     // Catch:{ all -> 0x0055 }
            if (r2 != 0) goto L_0x0019
            java.lang.String r2 = "Already released"
            r5.logD(r2)     // Catch:{ all -> 0x0055 }
            monitor-exit(r1)     // Catch:{ all -> 0x0055 }
            return
        L_0x0019:
            java.lang.Runnable r3 = r5.logStatisticsRunnable     // Catch:{ all -> 0x0055 }
            r2.removeCallbacks(r3)     // Catch:{ all -> 0x0055 }
            android.os.Handler r2 = r5.renderThreadHandler     // Catch:{ all -> 0x0055 }
            org.webrtc.j r3 = new org.webrtc.j     // Catch:{ all -> 0x0055 }
            r3.<init>(r5, r0)     // Catch:{ all -> 0x0055 }
            r2.postAtFrontOfQueue(r3)     // Catch:{ all -> 0x0055 }
            android.os.Handler r2 = r5.renderThreadHandler     // Catch:{ all -> 0x0055 }
            android.os.Looper r2 = r2.getLooper()     // Catch:{ all -> 0x0055 }
            android.os.Handler r3 = r5.renderThreadHandler     // Catch:{ all -> 0x0055 }
            org.webrtc.m r4 = new org.webrtc.m     // Catch:{ all -> 0x0055 }
            r4.<init>(r5, r2)     // Catch:{ all -> 0x0055 }
            r3.post(r4)     // Catch:{ all -> 0x0055 }
            r3 = 0
            r5.renderThreadHandler = r3     // Catch:{ all -> 0x0055 }
            monitor-exit(r1)     // Catch:{ all -> 0x0055 }
            org.webrtc.ThreadUtils.awaitUninterruptibly(r0)
            java.lang.Object r2 = r5.frameLock
            monitor-enter(r2)
            org.webrtc.VideoFrame r1 = r5.pendingFrame     // Catch:{ all -> 0x0052 }
            if (r1 == 0) goto L_0x004b
            r1.release()     // Catch:{ all -> 0x0052 }
            r5.pendingFrame = r3     // Catch:{ all -> 0x0052 }
        L_0x004b:
            monitor-exit(r2)     // Catch:{ all -> 0x0052 }
            java.lang.String r1 = "Releasing done."
            r5.logD(r1)
            return
        L_0x0052:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0052 }
            throw r1
        L_0x0055:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0055 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.EglRenderer.release():void");
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$release$1 */
    public /* synthetic */ void e(CountDownLatch eglCleanupBarrier) {
        synchronized (EglBase.lock) {
            GLES20.glUseProgram(0);
        }
        RendererCommon.GlDrawer glDrawer = this.drawer;
        if (glDrawer != null) {
            glDrawer.release();
            this.drawer = null;
        }
        this.frameDrawer.release();
        this.bitmapTextureFramebuffer.release();
        if (this.eglBase != null) {
            logD("eglBase detach and release.");
            this.eglBase.detachCurrent();
            this.eglBase.release();
            this.eglBase = null;
        }
        this.frameListeners.clear();
        eglCleanupBarrier.countDown();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$release$2 */
    public /* synthetic */ void f(Looper renderLooper) {
        logD("Quitting render thread.");
        renderLooper.quit();
    }

    private void resetStatistics(long currentTimeNs) {
        synchronized (this.statisticsLock) {
            this.statisticsStartTimeNs = currentTimeNs;
            this.framesReceived = 0;
            this.framesDropped = 0;
            this.framesRendered = 0;
            this.renderTimeNs = 0;
            this.renderSwapBufferTimeNs = 0;
        }
    }

    public void printStackTrace() {
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            Thread renderThread = handler == null ? null : handler.getLooper().getThread();
            if (renderThread != null) {
                StackTraceElement[] renderStackTrace = renderThread.getStackTrace();
                if (renderStackTrace.length > 0) {
                    logW("EglRenderer stack trace:");
                    for (StackTraceElement traceElem : renderStackTrace) {
                        logW(traceElem.toString());
                    }
                }
            }
        }
    }

    public void setMirror(boolean mirror) {
        logD("setMirrorHorizontally: " + mirror);
        synchronized (this.layoutLock) {
            this.mirrorHorizontally = mirror;
        }
    }

    public void setMirrorVertically(boolean mirrorVertically2) {
        logD("setMirrorVertically: " + mirrorVertically2);
        synchronized (this.layoutLock) {
            this.mirrorVertically = mirrorVertically2;
        }
    }

    public void setLayoutAspectRatio(float layoutAspectRatio2) {
        logD("setLayoutAspectRatio: " + layoutAspectRatio2);
        synchronized (this.layoutLock) {
            this.layoutAspectRatio = layoutAspectRatio2;
        }
    }

    public void setFpsReduction(float fps) {
        logD("setFpsReduction: " + fps);
        synchronized (this.fpsReductionLock) {
            long previousRenderPeriodNs = this.minRenderPeriodNs;
            if (fps <= 0.0f) {
                this.minRenderPeriodNs = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            } else {
                this.minRenderPeriodNs = (long) (((float) TimeUnit.SECONDS.toNanos(1)) / fps);
            }
            if (this.minRenderPeriodNs != previousRenderPeriodNs) {
                this.nextFrameTimeNs = System.nanoTime();
            }
        }
    }

    public void disableFpsReduction() {
        setFpsReduction(Float.POSITIVE_INFINITY);
    }

    public void pauseVideo() {
        setFpsReduction(0.0f);
    }

    public void addFrameListener(FrameListener listener, float scale) {
        addFrameListener(listener, scale, (RendererCommon.GlDrawer) null, false);
    }

    public void addFrameListener(FrameListener listener, float scale, RendererCommon.GlDrawer drawerParam) {
        addFrameListener(listener, scale, drawerParam, false);
    }

    public void addFrameListener(FrameListener listener, float scale, @Nullable RendererCommon.GlDrawer drawerParam, boolean applyFpsReduction) {
        postToRenderThread(new n(this, drawerParam, listener, scale, applyFpsReduction));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$addFrameListener$3 */
    public /* synthetic */ void a(RendererCommon.GlDrawer drawerParam, FrameListener listener, float scale, boolean applyFpsReduction) {
        this.frameListeners.add(new FrameListenerAndParams(listener, scale, drawerParam == null ? this.drawer : drawerParam, applyFpsReduction));
    }

    public void removeFrameListener(FrameListener listener) {
        CountDownLatch latch = new CountDownLatch(1);
        synchronized (this.handlerLock) {
            if (this.renderThreadHandler != null) {
                if (Thread.currentThread() != this.renderThreadHandler.getLooper().getThread()) {
                    postToRenderThread(new i(this, latch, listener));
                    ThreadUtils.awaitUninterruptibly(latch);
                    return;
                }
                throw new RuntimeException("removeFrameListener must not be called on the render thread.");
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$removeFrameListener$4 */
    public /* synthetic */ void h(CountDownLatch latch, FrameListener listener) {
        latch.countDown();
        Iterator<FrameListenerAndParams> iter = this.frameListeners.iterator();
        while (iter.hasNext()) {
            if (iter.next().listener == listener) {
                iter.remove();
            }
        }
    }

    public void setErrorCallback(ErrorCallback errorCallback2) {
        this.errorCallback = errorCallback2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0038, code lost:
        if (r4 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x003a, code lost:
        r0 = r6.statisticsLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x003c, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r6.framesDropped++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0042, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onFrame(org.webrtc.VideoFrame r7) {
        /*
            r6 = this;
            java.lang.Object r0 = r6.statisticsLock
            monitor-enter(r0)
            int r1 = r6.framesReceived     // Catch:{ all -> 0x004e }
            r2 = 1
            int r1 = r1 + r2
            r6.framesReceived = r1     // Catch:{ all -> 0x004e }
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            java.lang.Object r1 = r6.handlerLock
            monitor-enter(r1)
            android.os.Handler r0 = r6.renderThreadHandler     // Catch:{ all -> 0x004b }
            if (r0 != 0) goto L_0x0018
            java.lang.String r0 = "Dropping frame - Not initialized or already released."
            r6.logD(r0)     // Catch:{ all -> 0x004b }
            monitor-exit(r1)     // Catch:{ all -> 0x004b }
            return
        L_0x0018:
            java.lang.Object r0 = r6.frameLock     // Catch:{ all -> 0x004b }
            monitor-enter(r0)     // Catch:{ all -> 0x004b }
            org.webrtc.VideoFrame r3 = r6.pendingFrame     // Catch:{ all -> 0x0048 }
            if (r3 == 0) goto L_0x0021
            r4 = r2
            goto L_0x0022
        L_0x0021:
            r4 = 0
        L_0x0022:
            if (r4 == 0) goto L_0x0027
            r3.release()     // Catch:{ all -> 0x0048 }
        L_0x0027:
            r6.pendingFrame = r7     // Catch:{ all -> 0x0048 }
            r7.retain()     // Catch:{ all -> 0x0048 }
            android.os.Handler r3 = r6.renderThreadHandler     // Catch:{ all -> 0x0048 }
            org.webrtc.k r5 = new org.webrtc.k     // Catch:{ all -> 0x0048 }
            r5.<init>(r6)     // Catch:{ all -> 0x0048 }
            r3.post(r5)     // Catch:{ all -> 0x0048 }
            monitor-exit(r0)     // Catch:{ all -> 0x0048 }
            monitor-exit(r1)     // Catch:{ all -> 0x004b }
            if (r4 == 0) goto L_0x0047
            java.lang.Object r0 = r6.statisticsLock
            monitor-enter(r0)
            int r1 = r6.framesDropped     // Catch:{ all -> 0x0044 }
            int r1 = r1 + r2
            r6.framesDropped = r1     // Catch:{ all -> 0x0044 }
            monitor-exit(r0)     // Catch:{ all -> 0x0044 }
            goto L_0x0047
        L_0x0044:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0044 }
            throw r1
        L_0x0047:
            return
        L_0x0048:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0048 }
            throw r2     // Catch:{ all -> 0x004b }
        L_0x004b:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x004b }
            throw r0
        L_0x004e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.EglRenderer.onFrame(org.webrtc.VideoFrame):void");
    }

    public void releaseEglSurface(Runnable completionCallback) {
        this.eglSurfaceCreationRunnable.setSurface((Object) null);
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            if (handler != null) {
                handler.removeCallbacks(this.eglSurfaceCreationRunnable);
                this.renderThreadHandler.postAtFrontOfQueue(new g(this, completionCallback));
                return;
            }
            completionCallback.run();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$releaseEglSurface$5 */
    public /* synthetic */ void g(Runnable completionCallback) {
        EglBase eglBase2 = this.eglBase;
        if (eglBase2 != null) {
            eglBase2.detachCurrent();
            this.eglBase.releaseSurface();
        }
        completionCallback.run();
    }

    private void postToRenderThread(Runnable runnable) {
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            if (handler != null) {
                handler.post(runnable);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: clearSurfaceOnRenderThread */
    public void lambda$clearImage$6(float r, float g, float b, float a) {
        EglBase eglBase2 = this.eglBase;
        if (eglBase2 != null && eglBase2.hasSurface()) {
            logD("clearSurface");
            GLES20.glClearColor(r, g, b, a);
            GLES20.glClear(16384);
            this.eglBase.swapBuffers();
        }
    }

    public void clearImage() {
        clearImage(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void clearImage(float r, float g, float b, float a) {
        synchronized (this.handlerLock) {
            Handler handler = this.renderThreadHandler;
            if (handler != null) {
                handler.postAtFrontOfQueue(new h(this, r, g, b, a));
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0012, code lost:
        if (r0 == null) goto L_0x0154;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        if (r0.hasSurface() != false) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001c, code lost:
        r2 = r1.fpsReductionLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001e, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r3 = r1.minRenderPeriodNs;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0028, code lost:
        if (r3 != com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002a, code lost:
        r12 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0031, code lost:
        if (r3 > 0) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0033, code lost:
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0036, code lost:
        r3 = java.lang.System.nanoTime();
        r5 = r1.nextFrameTimeNs;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003e, code lost:
        if (r3 >= r5) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0040, code lost:
        logD("Skipping frame rendering - fps reduction is active.");
        r12 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0048, code lost:
        r5 = r5 + r1.minRenderPeriodNs;
        r1.nextFrameTimeNs = r5;
        r1.nextFrameTimeNs = java.lang.Math.max(r5, r3);
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0055, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
        r13 = java.lang.System.nanoTime();
        r15 = ((float) r11.getRotatedWidth()) / ((float) r11.getRotatedHeight());
        r3 = r1.layoutLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0068, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r0 = r1.layoutAspectRatio;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006e, code lost:
        if (r0 == 0.0f) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0071, code lost:
        r0 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0072, code lost:
        r16 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0074, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0077, code lost:
        if (r15 <= r16) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0079, code lost:
        r10 = r16 / r15;
        r9 = 1.0f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0080, code lost:
        r10 = 1.0f;
        r9 = r15 / r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0086, code lost:
        r1.drawMatrix.reset();
        r1.drawMatrix.preTranslate(0.5f, 0.5f);
        r0 = r1.drawMatrix;
        r4 = -1.0f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x009a, code lost:
        if (r1.mirrorHorizontally == false) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x009c, code lost:
        r3 = -1.0f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009e, code lost:
        r3 = 1.0f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00a1, code lost:
        if (r1.mirrorVertically == false) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00a4, code lost:
        r4 = 1.0f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00a5, code lost:
        r0.preScale(r3, r4);
        r1.drawMatrix.preScale(r10, r9);
        r1.drawMatrix.preTranslate(-0.5f, -0.5f);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b4, code lost:
        if (r12 == false) goto L_0x011f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        android.opengl.GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        android.opengl.GLES20.glClear(16384);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d2, code lost:
        r17 = r9;
        r18 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        r1.frameDrawer.drawFrame(r11, r1.drawer, r1.drawMatrix, 0, 0, r1.eglBase.surfaceWidth(), r1.eglBase.surfaceHeight());
        r2 = java.lang.System.nanoTime();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00e2, code lost:
        if (r1.usePresentationTimeStamp == false) goto L_0x00ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00e4, code lost:
        r1.eglBase.swapBuffers(r11.getTimestampNs());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00ee, code lost:
        r1.eglBase.swapBuffers();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00f3, code lost:
        r4 = java.lang.System.nanoTime();
        r6 = r1.statisticsLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00f9, code lost:
        monitor-enter(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r1.framesRendered++;
        r1.renderTimeNs += r4 - r13;
        r1.renderSwapBufferTimeNs += r4 - r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x010e, code lost:
        monitor-exit(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0113, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0114, code lost:
        r17 = r9;
        r18 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0119, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x011a, code lost:
        r17 = r9;
        r18 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x011f, code lost:
        r17 = r9;
        r18 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0123, code lost:
        notifyCallbacks(r11, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0126, code lost:
        r11.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x012a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x012c, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:?, code lost:
        logE("Error while drawing frame", r0);
        r2 = r1.errorCallback;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0134, code lost:
        if (r2 != null) goto L_0x0136;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0136, code lost:
        r2.onGlOutOfMemory();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0139, code lost:
        r1.drawer.release();
        r1.frameDrawer.release();
        r1.bitmapTextureFramebuffer.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0149, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x014a, code lost:
        r11.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x014d, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0154, code lost:
        logD("Dropping frame - No surface");
        r11.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x015c, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        r0 = r1.eglBase;
     */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0136 A[Catch:{ all -> 0x012a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void renderFrameOnRenderThread() {
        /*
            r19 = this;
            r1 = r19
            java.lang.Object r2 = r1.frameLock
            monitor-enter(r2)
            org.webrtc.VideoFrame r0 = r1.pendingFrame     // Catch:{ all -> 0x015d }
            if (r0 != 0) goto L_0x000b
            monitor-exit(r2)     // Catch:{ all -> 0x015d }
            return
        L_0x000b:
            r11 = r0
            r0 = 0
            r1.pendingFrame = r0     // Catch:{ all -> 0x015d }
            monitor-exit(r2)     // Catch:{ all -> 0x015d }
            org.webrtc.EglBase r0 = r1.eglBase
            if (r0 == 0) goto L_0x0154
            boolean r0 = r0.hasSurface()
            if (r0 != 0) goto L_0x001c
            goto L_0x0154
        L_0x001c:
            java.lang.Object r2 = r1.fpsReductionLock
            monitor-enter(r2)
            long r3 = r1.minRenderPeriodNs     // Catch:{ all -> 0x0151 }
            r5 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 != 0) goto L_0x002d
            r0 = 0
            r12 = r0
            goto L_0x0055
        L_0x002d:
            r5 = 0
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 > 0) goto L_0x0036
            r0 = 1
            r12 = r0
            goto L_0x0055
        L_0x0036:
            long r3 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0151 }
            long r5 = r1.nextFrameTimeNs     // Catch:{ all -> 0x0151 }
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x0048
            java.lang.String r0 = "Skipping frame rendering - fps reduction is active."
            r1.logD(r0)     // Catch:{ all -> 0x0151 }
            r0 = 0
            r12 = r0
            goto L_0x0055
        L_0x0048:
            long r7 = r1.minRenderPeriodNs     // Catch:{ all -> 0x0151 }
            long r5 = r5 + r7
            r1.nextFrameTimeNs = r5     // Catch:{ all -> 0x0151 }
            long r5 = java.lang.Math.max(r5, r3)     // Catch:{ all -> 0x0151 }
            r1.nextFrameTimeNs = r5     // Catch:{ all -> 0x0151 }
            r0 = 1
            r12 = r0
        L_0x0055:
            monitor-exit(r2)     // Catch:{ all -> 0x0151 }
            long r13 = java.lang.System.nanoTime()
            int r0 = r11.getRotatedWidth()
            float r0 = (float) r0
            int r2 = r11.getRotatedHeight()
            float r2 = (float) r2
            float r15 = r0 / r2
            java.lang.Object r3 = r1.layoutLock
            monitor-enter(r3)
            float r0 = r1.layoutAspectRatio     // Catch:{ all -> 0x014e }
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0071
            goto L_0x0072
        L_0x0071:
            r0 = r15
        L_0x0072:
            r16 = r0
            monitor-exit(r3)     // Catch:{ all -> 0x014e }
            int r0 = (r15 > r16 ? 1 : (r15 == r16 ? 0 : -1))
            if (r0 <= 0) goto L_0x0080
            float r0 = r16 / r15
            r3 = 1065353216(0x3f800000, float:1.0)
            r10 = r0
            r9 = r3
            goto L_0x0086
        L_0x0080:
            r0 = 1065353216(0x3f800000, float:1.0)
            float r3 = r15 / r16
            r10 = r0
            r9 = r3
        L_0x0086:
            android.graphics.Matrix r0 = r1.drawMatrix
            r0.reset()
            android.graphics.Matrix r0 = r1.drawMatrix
            r3 = 1056964608(0x3f000000, float:0.5)
            r0.preTranslate(r3, r3)
            android.graphics.Matrix r0 = r1.drawMatrix
            boolean r3 = r1.mirrorHorizontally
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r3 == 0) goto L_0x009e
            r3 = r4
            goto L_0x009f
        L_0x009e:
            r3 = r5
        L_0x009f:
            boolean r6 = r1.mirrorVertically
            if (r6 == 0) goto L_0x00a4
            goto L_0x00a5
        L_0x00a4:
            r4 = r5
        L_0x00a5:
            r0.preScale(r3, r4)
            android.graphics.Matrix r0 = r1.drawMatrix
            r0.preScale(r10, r9)
            android.graphics.Matrix r0 = r1.drawMatrix
            r3 = -1090519040(0xffffffffbf000000, float:-0.5)
            r0.preTranslate(r3, r3)
            if (r12 == 0) goto L_0x011f
            android.opengl.GLES20.glClearColor(r2, r2, r2, r2)     // Catch:{ GlOutOfMemoryException -> 0x0119, all -> 0x0113 }
            r0 = 16384(0x4000, float:2.2959E-41)
            android.opengl.GLES20.glClear(r0)     // Catch:{ GlOutOfMemoryException -> 0x0119, all -> 0x0113 }
            org.webrtc.VideoFrameDrawer r3 = r1.frameDrawer     // Catch:{ GlOutOfMemoryException -> 0x0119, all -> 0x0113 }
            org.webrtc.RendererCommon$GlDrawer r5 = r1.drawer     // Catch:{ GlOutOfMemoryException -> 0x0119, all -> 0x0113 }
            android.graphics.Matrix r6 = r1.drawMatrix     // Catch:{ GlOutOfMemoryException -> 0x0119, all -> 0x0113 }
            r7 = 0
            r8 = 0
            org.webrtc.EglBase r0 = r1.eglBase     // Catch:{ GlOutOfMemoryException -> 0x0119, all -> 0x0113 }
            int r0 = r0.surfaceWidth()     // Catch:{ GlOutOfMemoryException -> 0x0119, all -> 0x0113 }
            org.webrtc.EglBase r2 = r1.eglBase     // Catch:{ GlOutOfMemoryException -> 0x0119, all -> 0x0113 }
            int r2 = r2.surfaceHeight()     // Catch:{ GlOutOfMemoryException -> 0x0119, all -> 0x0113 }
            r4 = r11
            r17 = r9
            r9 = r0
            r18 = r10
            r10 = r2
            r3.drawFrame(r4, r5, r6, r7, r8, r9, r10)     // Catch:{ GlOutOfMemoryException -> 0x012c }
            long r2 = java.lang.System.nanoTime()     // Catch:{ GlOutOfMemoryException -> 0x012c }
            boolean r0 = r1.usePresentationTimeStamp     // Catch:{ GlOutOfMemoryException -> 0x012c }
            if (r0 == 0) goto L_0x00ee
            org.webrtc.EglBase r0 = r1.eglBase     // Catch:{ GlOutOfMemoryException -> 0x012c }
            long r4 = r11.getTimestampNs()     // Catch:{ GlOutOfMemoryException -> 0x012c }
            r0.swapBuffers(r4)     // Catch:{ GlOutOfMemoryException -> 0x012c }
            goto L_0x00f3
        L_0x00ee:
            org.webrtc.EglBase r0 = r1.eglBase     // Catch:{ GlOutOfMemoryException -> 0x012c }
            r0.swapBuffers()     // Catch:{ GlOutOfMemoryException -> 0x012c }
        L_0x00f3:
            long r4 = java.lang.System.nanoTime()     // Catch:{ GlOutOfMemoryException -> 0x012c }
            java.lang.Object r6 = r1.statisticsLock     // Catch:{ GlOutOfMemoryException -> 0x012c }
            monitor-enter(r6)     // Catch:{ GlOutOfMemoryException -> 0x012c }
            int r0 = r1.framesRendered     // Catch:{ all -> 0x0110 }
            int r0 = r0 + 1
            r1.framesRendered = r0     // Catch:{ all -> 0x0110 }
            long r7 = r1.renderTimeNs     // Catch:{ all -> 0x0110 }
            long r9 = r4 - r13
            long r7 = r7 + r9
            r1.renderTimeNs = r7     // Catch:{ all -> 0x0110 }
            long r7 = r1.renderSwapBufferTimeNs     // Catch:{ all -> 0x0110 }
            long r9 = r4 - r2
            long r7 = r7 + r9
            r1.renderSwapBufferTimeNs = r7     // Catch:{ all -> 0x0110 }
            monitor-exit(r6)     // Catch:{ all -> 0x0110 }
            goto L_0x0123
        L_0x0110:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0110 }
            throw r0     // Catch:{ GlOutOfMemoryException -> 0x012c }
        L_0x0113:
            r0 = move-exception
            r17 = r9
            r18 = r10
            goto L_0x014a
        L_0x0119:
            r0 = move-exception
            r17 = r9
            r18 = r10
            goto L_0x012d
        L_0x011f:
            r17 = r9
            r18 = r10
        L_0x0123:
            r1.notifyCallbacks(r11, r12)     // Catch:{ GlOutOfMemoryException -> 0x012c }
        L_0x0126:
            r11.release()
            goto L_0x0149
        L_0x012a:
            r0 = move-exception
            goto L_0x014a
        L_0x012c:
            r0 = move-exception
        L_0x012d:
            java.lang.String r2 = "Error while drawing frame"
            r1.logE(r2, r0)     // Catch:{ all -> 0x012a }
            org.webrtc.EglRenderer$ErrorCallback r2 = r1.errorCallback     // Catch:{ all -> 0x012a }
            if (r2 == 0) goto L_0x0139
            r2.onGlOutOfMemory()     // Catch:{ all -> 0x012a }
        L_0x0139:
            org.webrtc.RendererCommon$GlDrawer r3 = r1.drawer     // Catch:{ all -> 0x012a }
            r3.release()     // Catch:{ all -> 0x012a }
            org.webrtc.VideoFrameDrawer r3 = r1.frameDrawer     // Catch:{ all -> 0x012a }
            r3.release()     // Catch:{ all -> 0x012a }
            org.webrtc.GlTextureFrameBuffer r3 = r1.bitmapTextureFramebuffer     // Catch:{ all -> 0x012a }
            r3.release()     // Catch:{ all -> 0x012a }
            goto L_0x0126
        L_0x0149:
            return
        L_0x014a:
            r11.release()
            throw r0
        L_0x014e:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x014e }
            throw r0
        L_0x0151:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0151 }
            throw r0
        L_0x0154:
            java.lang.String r0 = "Dropping frame - No surface"
            r1.logD(r0)
            r11.release()
            return
        L_0x015d:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x015d }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.EglRenderer.renderFrameOnRenderThread():void");
    }

    private void notifyCallbacks(VideoFrame frame, boolean wasRendered) {
        if (!this.frameListeners.isEmpty()) {
            this.drawMatrix.reset();
            this.drawMatrix.preTranslate(0.5f, 0.5f);
            this.drawMatrix.preScale(this.mirrorHorizontally ? -1.0f : 1.0f, this.mirrorVertically ? -1.0f : 1.0f);
            this.drawMatrix.preScale(1.0f, -1.0f);
            this.drawMatrix.preTranslate(-0.5f, -0.5f);
            Iterator<FrameListenerAndParams> it = this.frameListeners.iterator();
            while (it.hasNext()) {
                FrameListenerAndParams listenerAndParams = it.next();
                if (wasRendered || !listenerAndParams.applyFpsReduction) {
                    it.remove();
                    int scaledWidth = (int) (listenerAndParams.scale * ((float) frame.getRotatedWidth()));
                    int scaledHeight = (int) (listenerAndParams.scale * ((float) frame.getRotatedHeight()));
                    if (scaledWidth == 0 || scaledHeight == 0) {
                        listenerAndParams.listener.onFrame((Bitmap) null);
                    } else {
                        this.bitmapTextureFramebuffer.setSize(scaledWidth, scaledHeight);
                        GLES20.glBindFramebuffer(36160, this.bitmapTextureFramebuffer.getFrameBufferId());
                        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.bitmapTextureFramebuffer.getTextureId(), 0);
                        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                        GLES20.glClear(16384);
                        this.frameDrawer.drawFrame(frame, listenerAndParams.drawer, this.drawMatrix, 0, 0, scaledWidth, scaledHeight);
                        ByteBuffer bitmapBuffer = ByteBuffer.allocateDirect(scaledWidth * scaledHeight * 4);
                        GLES20.glViewport(0, 0, scaledWidth, scaledHeight);
                        GLES20.glReadPixels(0, 0, scaledWidth, scaledHeight, 6408, 5121, bitmapBuffer);
                        GLES20.glBindFramebuffer(36160, 0);
                        GlUtil.checkNoGLES2Error("EglRenderer.notifyCallbacks");
                        Bitmap bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
                        bitmap.copyPixelsFromBuffer(bitmapBuffer);
                        listenerAndParams.listener.onFrame(bitmap);
                    }
                }
            }
        }
    }

    private String averageTimeAsString(long sumTimeNs, int count) {
        if (count <= 0) {
            return "NA";
        }
        return TimeUnit.NANOSECONDS.toMicros(sumTimeNs / ((long) count)) + " us";
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x00a8, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void logStatistics() {
        /*
            r11 = this;
            java.text.DecimalFormat r0 = new java.text.DecimalFormat
            java.lang.String r1 = "#.0"
            r0.<init>(r1)
            long r1 = java.lang.System.nanoTime()
            java.lang.Object r3 = r11.statisticsLock
            monitor-enter(r3)
            long r4 = r11.statisticsStartTimeNs     // Catch:{ all -> 0x00a9 }
            long r4 = r1 - r4
            r6 = 0
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x00a7
            long r6 = r11.minRenderPeriodNs     // Catch:{ all -> 0x00a9 }
            r8 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 != 0) goto L_0x0029
            int r6 = r11.framesReceived     // Catch:{ all -> 0x00a9 }
            if (r6 != 0) goto L_0x0029
            goto L_0x00a7
        L_0x0029:
            int r6 = r11.framesRendered     // Catch:{ all -> 0x00a9 }
            long r6 = (long) r6     // Catch:{ all -> 0x00a9 }
            java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x00a9 }
            r9 = 1
            long r8 = r8.toNanos(r9)     // Catch:{ all -> 0x00a9 }
            long r6 = r6 * r8
            float r6 = (float) r6     // Catch:{ all -> 0x00a9 }
            float r7 = (float) r4     // Catch:{ all -> 0x00a9 }
            float r6 = r6 / r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a9 }
            r7.<init>()     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = "Duration: "
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            java.util.concurrent.TimeUnit r8 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ all -> 0x00a9 }
            long r8 = r8.toMillis(r4)     // Catch:{ all -> 0x00a9 }
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = " ms. Frames received: "
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            int r8 = r11.framesReceived     // Catch:{ all -> 0x00a9 }
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = ". Dropped: "
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            int r8 = r11.framesDropped     // Catch:{ all -> 0x00a9 }
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = ". Rendered: "
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            int r8 = r11.framesRendered     // Catch:{ all -> 0x00a9 }
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = ". Render fps: "
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            double r8 = (double) r6     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = r0.format(r8)     // Catch:{ all -> 0x00a9 }
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = ". Average render time: "
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            long r8 = r11.renderTimeNs     // Catch:{ all -> 0x00a9 }
            int r10 = r11.framesRendered     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = r11.averageTimeAsString(r8, r10)     // Catch:{ all -> 0x00a9 }
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = ". Average swapBuffer time: "
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            long r8 = r11.renderSwapBufferTimeNs     // Catch:{ all -> 0x00a9 }
            int r10 = r11.framesRendered     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = r11.averageTimeAsString(r8, r10)     // Catch:{ all -> 0x00a9 }
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            java.lang.String r8 = "."
            r7.append(r8)     // Catch:{ all -> 0x00a9 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00a9 }
            r11.logD(r7)     // Catch:{ all -> 0x00a9 }
            r11.resetStatistics(r1)     // Catch:{ all -> 0x00a9 }
            monitor-exit(r3)     // Catch:{ all -> 0x00a9 }
            return
        L_0x00a7:
            monitor-exit(r3)     // Catch:{ all -> 0x00a9 }
            return
        L_0x00a9:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00a9 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.EglRenderer.logStatistics():void");
    }

    private void logE(String string, Throwable e) {
        Logging.e(TAG, this.name + string, e);
    }

    private void logD(String string) {
        Logging.d(TAG, this.name + string);
    }

    private void logW(String string) {
        Logging.w(TAG, this.name + string);
    }
}
