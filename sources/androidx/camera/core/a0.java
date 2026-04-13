package androidx.camera.core;

import androidx.camera.core.ImageCapture;

/* compiled from: lambda */
public final /* synthetic */ class a0 implements Runnable {
    public final /* synthetic */ ImageCapture.ImageCaptureRequest c;
    public final /* synthetic */ int d;
    public final /* synthetic */ String f;
    public final /* synthetic */ Throwable q;

    public /* synthetic */ a0(ImageCapture.ImageCaptureRequest imageCaptureRequest, int i, String str, Throwable th) {
        this.c = imageCaptureRequest;
        this.d = i;
        this.f = str;
        this.q = th;
    }

    public final void run() {
        this.c.b(this.d, this.f, this.q);
    }
}
