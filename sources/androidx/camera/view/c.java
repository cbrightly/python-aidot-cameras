package androidx.camera.view;

import androidx.arch.core.util.Function;
import androidx.camera.lifecycle.ProcessCameraProvider;

/* compiled from: lambda */
public final /* synthetic */ class c implements Function {
    public final /* synthetic */ CameraController a;

    public /* synthetic */ c(CameraController cameraController) {
        this.a = cameraController;
    }

    public final Object apply(Object obj) {
        this.a.a((ProcessCameraProvider) obj);
        return null;
    }
}
