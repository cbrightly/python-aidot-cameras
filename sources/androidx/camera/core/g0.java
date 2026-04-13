package androidx.camera.core;

import androidx.camera.core.ImageCapture;
import java.util.concurrent.Executor;

/* compiled from: lambda */
public final /* synthetic */ class g0 implements Runnable {
    public final /* synthetic */ ImageCapture c;
    public final /* synthetic */ ImageCapture.OutputFileOptions d;
    public final /* synthetic */ Executor f;
    public final /* synthetic */ ImageCapture.OnImageSavedCallback q;

    public /* synthetic */ g0(ImageCapture imageCapture, ImageCapture.OutputFileOptions outputFileOptions, Executor executor, ImageCapture.OnImageSavedCallback onImageSavedCallback) {
        this.c = imageCapture;
        this.d = outputFileOptions;
        this.f = executor;
        this.q = onImageSavedCallback;
    }

    public final void run() {
        this.c.i(this.d, this.f, this.q);
    }
}
