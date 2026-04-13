package androidx.camera.core;

import androidx.camera.core.ImageCapture;
import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class k0 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ImageCapture a;
    public final /* synthetic */ ImageCapture.ImageCaptureRequest b;

    public /* synthetic */ k0(ImageCapture imageCapture, ImageCapture.ImageCaptureRequest imageCaptureRequest) {
        this.a = imageCapture;
        this.b = imageCaptureRequest;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.j(this.b, completer);
    }
}
