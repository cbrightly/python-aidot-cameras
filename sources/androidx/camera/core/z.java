package androidx.camera.core;

import androidx.camera.core.ImageCapture;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: lambda */
public final /* synthetic */ class z implements AsyncFunction {
    public final /* synthetic */ ImageCapture a;
    public final /* synthetic */ ImageCapture.ImageCaptureRequest b;

    public /* synthetic */ z(ImageCapture imageCapture, ImageCapture.ImageCaptureRequest imageCaptureRequest) {
        this.a = imageCapture;
        this.b = imageCaptureRequest;
    }

    public final ListenableFuture apply(Object obj) {
        return this.a.k(this.b, (Void) obj);
    }
}
