package androidx.camera.core;

import androidx.camera.core.ImageCapture;
import java.util.concurrent.Executor;

/* compiled from: lambda */
public final /* synthetic */ class l0 implements Runnable {
    public final /* synthetic */ ImageCapture c;
    public final /* synthetic */ Executor d;
    public final /* synthetic */ ImageCapture.OnImageCapturedCallback f;

    public /* synthetic */ l0(ImageCapture imageCapture, Executor executor, ImageCapture.OnImageCapturedCallback onImageCapturedCallback) {
        this.c = imageCapture;
        this.d = executor;
        this.f = onImageCapturedCallback;
    }

    public final void run() {
        this.c.h(this.d, this.f);
    }
}
