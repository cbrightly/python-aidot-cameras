package org.webrtc;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import java.util.ArrayList;

public interface EglBase {
    public static final int[] CONFIG_PIXEL_BUFFER = r0.a().setSupportsPixelBuffer(true).createConfigAttributes();
    public static final int[] CONFIG_PIXEL_RGBA_BUFFER = r0.a().setHasAlphaChannel(true).setSupportsPixelBuffer(true).createConfigAttributes();
    public static final int[] CONFIG_PLAIN = r0.a().createConfigAttributes();
    public static final int[] CONFIG_RECORDABLE = r0.a().setIsRecordable(true).createConfigAttributes();
    public static final int[] CONFIG_RGBA = r0.a().setHasAlphaChannel(true).createConfigAttributes();
    public static final int EGL_OPENGL_ES2_BIT = 4;
    public static final int EGL_OPENGL_ES3_BIT = 64;
    public static final int EGL_RECORDABLE_ANDROID = 12610;
    public static final Object lock = new Object();

    public interface Context {
        public static final long NO_CONTEXT = 0;

        long getNativeEglContext();
    }

    void createDummyPbufferSurface();

    void createPbufferSurface(int i, int i2);

    void createSurface(SurfaceTexture surfaceTexture);

    void createSurface(Surface surface);

    void detachCurrent();

    Context getEglBaseContext();

    boolean hasSurface();

    void makeCurrent();

    void release();

    void releaseSurface();

    int surfaceHeight();

    int surfaceWidth();

    void swapBuffers();

    void swapBuffers(long j);

    public static class ConfigBuilder {
        private boolean hasAlphaChannel;
        private boolean isRecordable;
        private int openGlesVersion = 2;
        private boolean supportsPixelBuffer;

        public ConfigBuilder setOpenGlesVersion(int version) {
            if (version < 1 || version > 3) {
                throw new IllegalArgumentException("OpenGL ES version " + version + " not supported");
            }
            this.openGlesVersion = version;
            return this;
        }

        public ConfigBuilder setHasAlphaChannel(boolean hasAlphaChannel2) {
            this.hasAlphaChannel = hasAlphaChannel2;
            return this;
        }

        public ConfigBuilder setSupportsPixelBuffer(boolean supportsPixelBuffer2) {
            this.supportsPixelBuffer = supportsPixelBuffer2;
            return this;
        }

        public ConfigBuilder setIsRecordable(boolean isRecordable2) {
            this.isRecordable = isRecordable2;
            return this;
        }

        public int[] createConfigAttributes() {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(12324);
            list.add(8);
            list.add(12323);
            list.add(8);
            list.add(12322);
            list.add(8);
            if (this.hasAlphaChannel) {
                list.add(12321);
                list.add(8);
            }
            int i = this.openGlesVersion;
            if (i == 2 || i == 3) {
                list.add(12352);
                list.add(Integer.valueOf(this.openGlesVersion == 3 ? 64 : 4));
            }
            if (this.supportsPixelBuffer) {
                list.add(12339);
                list.add(1);
            }
            if (this.isRecordable) {
                list.add(Integer.valueOf(EglBase.EGL_RECORDABLE_ANDROID));
                list.add(1);
            }
            list.add(12344);
            int[] res = new int[list.size()];
            for (int i2 = 0; i2 < list.size(); i2++) {
                res[i2] = list.get(i2).intValue();
            }
            return res;
        }
    }
}
