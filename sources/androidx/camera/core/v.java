package androidx.camera.core;

import androidx.camera.core.ImageCapture;
import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class v implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ImageCapture a;
    public final /* synthetic */ ImageCapture.TakePictureState b;

    public /* synthetic */ v(ImageCapture imageCapture, ImageCapture.TakePictureState takePictureState) {
        this.a = imageCapture;
        this.b = takePictureState;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.d(this.b, completer);
    }
}
