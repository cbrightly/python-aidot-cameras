package androidx.camera.core;

import androidx.camera.core.ImageCapture;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: lambda */
public final /* synthetic */ class m0 implements AsyncFunction {
    public final /* synthetic */ ImageCapture a;
    public final /* synthetic */ ImageCapture.TakePictureState b;

    public /* synthetic */ m0(ImageCapture imageCapture, ImageCapture.TakePictureState takePictureState) {
        this.a = imageCapture;
        this.b = takePictureState;
    }

    public final ListenableFuture apply(Object obj) {
        return this.a.e(this.b, (CameraCaptureResult) obj);
    }
}
