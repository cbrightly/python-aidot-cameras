package androidx.camera.core;

import androidx.camera.core.ImageCapture;
import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: lambda */
public final /* synthetic */ class w implements ImageCapture.ImageCaptureRequestProcessor.ImageCaptor {
    public final /* synthetic */ ImageCapture a;

    public /* synthetic */ w(ImageCapture imageCapture) {
        this.a = imageCapture;
    }

    public final ListenableFuture capture(ImageCapture.ImageCaptureRequest imageCaptureRequest) {
        return this.a.a(imageCaptureRequest);
    }
}
