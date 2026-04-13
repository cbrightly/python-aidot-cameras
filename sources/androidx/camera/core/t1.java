package androidx.camera.core;

import android.media.MediaCodec;

/* compiled from: lambda */
public final /* synthetic */ class t1 implements Runnable {
    public final /* synthetic */ boolean c;
    public final /* synthetic */ MediaCodec d;

    public /* synthetic */ t1(boolean z, MediaCodec mediaCodec) {
        this.c = z;
        this.d = mediaCodec;
    }

    public final void run() {
        VideoCapture.lambda$releaseCameraSurface$7(this.c, this.d);
    }
}
