package androidx.camera.core;

import androidx.camera.core.impl.ImageReaderProxy;
import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class j0 implements ImageReaderProxy.OnImageAvailableListener {
    public final /* synthetic */ CallbackToFutureAdapter.Completer a;

    public /* synthetic */ j0(CallbackToFutureAdapter.Completer completer) {
        this.a = completer;
    }

    public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
        ImageCapture.lambda$takePictureInternal$7(this.a, imageReaderProxy);
    }
}
