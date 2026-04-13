package org.webrtc;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import androidx.annotation.Nullable;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import org.webrtc.EglBase;
import org.webrtc.EglBase10;

public class EglBase10Impl implements EglBase10 {
    private static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
    private static final String TAG = "EglBase10Impl";
    private final EGL10 egl;
    @Nullable
    private EGLConfig eglConfig;
    private EGLContext eglContext;
    private EGLDisplay eglDisplay;
    private EGLSurface eglSurface = EGL10.EGL_NO_SURFACE;

    /* access modifiers changed from: private */
    public static native long nativeGetCurrentNativeEGLContext();

    public static class Context implements EglBase10.Context {
        private final EGL10 egl;
        private final EGLContext eglContext;
        private final EGLConfig eglContextConfig;

        public EGLContext getRawContext() {
            return this.eglContext;
        }

        public long getNativeEglContext() {
            EGLContext previousContext = this.egl.eglGetCurrentContext();
            EGLDisplay currentDisplay = this.egl.eglGetCurrentDisplay();
            EGLSurface previousDrawSurface = this.egl.eglGetCurrentSurface(12377);
            EGLSurface previousReadSurface = this.egl.eglGetCurrentSurface(12378);
            EGLSurface tempEglSurface = null;
            if (currentDisplay == EGL10.EGL_NO_DISPLAY) {
                currentDisplay = this.egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            }
            try {
                if (previousContext != this.eglContext) {
                    tempEglSurface = this.egl.eglCreatePbufferSurface(currentDisplay, this.eglContextConfig, new int[]{12375, 1, 12374, 1, 12344});
                    if (!this.egl.eglMakeCurrent(currentDisplay, tempEglSurface, tempEglSurface, this.eglContext)) {
                        throw new RuntimeException("Failed to make temporary EGL surface active: " + this.egl.eglGetError());
                    }
                }
                return EglBase10Impl.nativeGetCurrentNativeEGLContext();
            } finally {
                if (tempEglSurface != null) {
                    this.egl.eglMakeCurrent(currentDisplay, previousDrawSurface, previousReadSurface, previousContext);
                    this.egl.eglDestroySurface(currentDisplay, tempEglSurface);
                }
            }
        }

        public Context(EGL10 egl2, EGLContext eglContext2, EGLConfig eglContextConfig2) {
            this.egl = egl2;
            this.eglContext = eglContext2;
            this.eglContextConfig = eglContextConfig2;
        }
    }

    public EglBase10Impl(EGLContext sharedContext, int[] configAttributes) {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        this.egl = egl10;
        EGLDisplay eglDisplay2 = getEglDisplay();
        this.eglDisplay = eglDisplay2;
        this.eglConfig = getEglConfig(egl10, eglDisplay2, configAttributes);
        int openGlesVersion = r0.h(configAttributes);
        Logging.d(TAG, "Using OpenGL ES version " + openGlesVersion);
        this.eglContext = createEglContext(sharedContext, this.eglDisplay, this.eglConfig, openGlesVersion);
    }

    public void createSurface(Surface surface) {
        createSurfaceInternal(new SurfaceHolder(surface) {
            private final Surface surface;

            {
                this.surface = surface;
            }

            public void addCallback(SurfaceHolder.Callback callback) {
            }

            public void removeCallback(SurfaceHolder.Callback callback) {
            }

            public boolean isCreating() {
                return false;
            }

            @Deprecated
            public void setType(int i) {
            }

            public void setFixedSize(int i, int i2) {
            }

            public void setSizeFromLayout() {
            }

            public void setFormat(int i) {
            }

            public void setKeepScreenOn(boolean b) {
            }

            @Nullable
            public Canvas lockCanvas() {
                return null;
            }

            @Nullable
            public Canvas lockCanvas(Rect rect) {
                return null;
            }

            public void unlockCanvasAndPost(Canvas canvas) {
            }

            @Nullable
            public Rect getSurfaceFrame() {
                return null;
            }

            public Surface getSurface() {
                return this.surface;
            }
        });
    }

    public void createSurface(SurfaceTexture surfaceTexture) {
        createSurfaceInternal(surfaceTexture);
    }

    private void createSurfaceInternal(Object nativeWindow) {
        if ((nativeWindow instanceof SurfaceHolder) || (nativeWindow instanceof SurfaceTexture)) {
            checkIsNotReleased();
            if (this.eglSurface == EGL10.EGL_NO_SURFACE) {
                EGLSurface eglCreateWindowSurface = this.egl.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, nativeWindow, new int[]{12344});
                this.eglSurface = eglCreateWindowSurface;
                if (eglCreateWindowSurface == EGL10.EGL_NO_SURFACE) {
                    throw new RuntimeException("Failed to create window surface: 0x" + Integer.toHexString(this.egl.eglGetError()));
                }
                return;
            }
            throw new RuntimeException("Already has an EGLSurface");
        }
        throw new IllegalStateException("Input must be either a SurfaceHolder or SurfaceTexture");
    }

    public void createDummyPbufferSurface() {
        createPbufferSurface(1, 1);
    }

    public void createPbufferSurface(int width, int height) {
        checkIsNotReleased();
        if (this.eglSurface == EGL10.EGL_NO_SURFACE) {
            EGLSurface eglCreatePbufferSurface = this.egl.eglCreatePbufferSurface(this.eglDisplay, this.eglConfig, new int[]{12375, width, 12374, height, 12344});
            this.eglSurface = eglCreatePbufferSurface;
            if (eglCreatePbufferSurface == EGL10.EGL_NO_SURFACE) {
                throw new RuntimeException("Failed to create pixel buffer surface with size " + width + "x" + height + ": 0x" + Integer.toHexString(this.egl.eglGetError()));
            }
            return;
        }
        throw new RuntimeException("Already has an EGLSurface");
    }

    public EglBase.Context getEglBaseContext() {
        return new Context(this.egl, this.eglContext, this.eglConfig);
    }

    public boolean hasSurface() {
        return this.eglSurface != EGL10.EGL_NO_SURFACE;
    }

    public int surfaceWidth() {
        int[] widthArray = new int[1];
        this.egl.eglQuerySurface(this.eglDisplay, this.eglSurface, 12375, widthArray);
        return widthArray[0];
    }

    public int surfaceHeight() {
        int[] heightArray = new int[1];
        this.egl.eglQuerySurface(this.eglDisplay, this.eglSurface, 12374, heightArray);
        return heightArray[0];
    }

    public void releaseSurface() {
        EGLSurface eGLSurface = this.eglSurface;
        if (eGLSurface != EGL10.EGL_NO_SURFACE) {
            this.egl.eglDestroySurface(this.eglDisplay, eGLSurface);
            this.eglSurface = EGL10.EGL_NO_SURFACE;
        }
    }

    private void checkIsNotReleased() {
        if (this.eglDisplay == EGL10.EGL_NO_DISPLAY || this.eglContext == EGL10.EGL_NO_CONTEXT || this.eglConfig == null) {
            throw new RuntimeException("This object has been released");
        }
    }

    public void release() {
        checkIsNotReleased();
        releaseSurface();
        detachCurrent();
        this.egl.eglDestroyContext(this.eglDisplay, this.eglContext);
        this.egl.eglTerminate(this.eglDisplay);
        this.eglContext = EGL10.EGL_NO_CONTEXT;
        this.eglDisplay = EGL10.EGL_NO_DISPLAY;
        this.eglConfig = null;
    }

    public void makeCurrent() {
        checkIsNotReleased();
        if (this.eglSurface != EGL10.EGL_NO_SURFACE) {
            synchronized (EglBase.lock) {
                EGL10 egl10 = this.egl;
                EGLDisplay eGLDisplay = this.eglDisplay;
                EGLSurface eGLSurface = this.eglSurface;
                if (!egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext)) {
                    throw new RuntimeException("eglMakeCurrent failed: 0x" + Integer.toHexString(this.egl.eglGetError()));
                }
            }
            return;
        }
        throw new RuntimeException("No EGLSurface - can't make current");
    }

    public void detachCurrent() {
        synchronized (EglBase.lock) {
            EGL10 egl10 = this.egl;
            EGLDisplay eGLDisplay = this.eglDisplay;
            EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
            if (!egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT)) {
                throw new RuntimeException("eglDetachCurrent failed: 0x" + Integer.toHexString(this.egl.eglGetError()));
            }
        }
    }

    public void swapBuffers() {
        checkIsNotReleased();
        if (this.eglSurface != EGL10.EGL_NO_SURFACE) {
            synchronized (EglBase.lock) {
                this.egl.eglSwapBuffers(this.eglDisplay, this.eglSurface);
            }
            return;
        }
        throw new RuntimeException("No EGLSurface - can't swap buffers");
    }

    public void swapBuffers(long timeStampNs) {
        swapBuffers();
    }

    private EGLDisplay getEglDisplay() {
        EGLDisplay eglDisplay2 = this.egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (eglDisplay2 != EGL10.EGL_NO_DISPLAY) {
            if (this.egl.eglInitialize(eglDisplay2, new int[2])) {
                return eglDisplay2;
            }
            throw new RuntimeException("Unable to initialize EGL10: 0x" + Integer.toHexString(this.egl.eglGetError()));
        }
        throw new RuntimeException("Unable to get EGL10 display: 0x" + Integer.toHexString(this.egl.eglGetError()));
    }

    private static EGLConfig getEglConfig(EGL10 egl2, EGLDisplay eglDisplay2, int[] configAttributes) {
        EGLConfig[] configs = new EGLConfig[1];
        int[] numConfigs = new int[1];
        if (!egl2.eglChooseConfig(eglDisplay2, configAttributes, configs, configs.length, numConfigs)) {
            throw new RuntimeException("eglChooseConfig failed: 0x" + Integer.toHexString(egl2.eglGetError()));
        } else if (numConfigs[0] > 0) {
            EGLConfig eglConfig2 = configs[0];
            if (eglConfig2 != null) {
                return eglConfig2;
            }
            throw new RuntimeException("eglChooseConfig returned null");
        } else {
            throw new RuntimeException("Unable to find any matching EGL config");
        }
    }

    private EGLContext createEglContext(@Nullable EGLContext sharedContext, EGLDisplay eglDisplay2, EGLConfig eglConfig2, int openGlesVersion) {
        EGLContext eglContext2;
        if (sharedContext == null || sharedContext != EGL10.EGL_NO_CONTEXT) {
            int[] contextAttributes = {EGL_CONTEXT_CLIENT_VERSION, openGlesVersion, 12344};
            EGLContext rootContext = sharedContext == null ? EGL10.EGL_NO_CONTEXT : sharedContext;
            synchronized (EglBase.lock) {
                eglContext2 = this.egl.eglCreateContext(eglDisplay2, eglConfig2, rootContext, contextAttributes);
            }
            if (eglContext2 != EGL10.EGL_NO_CONTEXT) {
                return eglContext2;
            }
            throw new RuntimeException("Failed to create EGL context: 0x" + Integer.toHexString(this.egl.eglGetError()));
        }
        throw new RuntimeException("Invalid sharedContext");
    }
}
