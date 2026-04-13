package androidx.camera.core;

import androidx.camera.core.ImageCapture;

/* compiled from: lambda */
public final /* synthetic */ class b0 implements Runnable {
    public final /* synthetic */ ImageCapture.ImageCaptureRequest c;
    public final /* synthetic */ ImageProxy d;

    public /* synthetic */ b0(ImageCapture.ImageCaptureRequest imageCaptureRequest, ImageProxy imageProxy) {
        this.c = imageCaptureRequest;
        this.d = imageProxy;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
