package androidx.camera.core;

import androidx.camera.core.ImageCapture;

/* compiled from: lambda */
public final /* synthetic */ class c0 implements Runnable {
    public final /* synthetic */ ImageCapture c;
    public final /* synthetic */ ImageCapture.OnImageCapturedCallback d;

    public /* synthetic */ c0(ImageCapture imageCapture, ImageCapture.OnImageCapturedCallback onImageCapturedCallback) {
        this.c = imageCapture;
        this.d = onImageCapturedCallback;
    }

    public final void run() {
        this.c.g(this.d);
    }
}
