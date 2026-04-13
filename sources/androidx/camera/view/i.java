package androidx.camera.view;

import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.view.PreviewView;

/* compiled from: lambda */
public final /* synthetic */ class i implements SurfaceRequest.TransformationInfoListener {
    public final /* synthetic */ PreviewView.AnonymousClass1 a;
    public final /* synthetic */ CameraInternal b;
    public final /* synthetic */ SurfaceRequest c;

    public /* synthetic */ i(PreviewView.AnonymousClass1 r1, CameraInternal cameraInternal, SurfaceRequest surfaceRequest) {
        this.a = r1;
        this.b = cameraInternal;
        this.c = surfaceRequest;
    }

    public final void onTransformationInfoUpdate(SurfaceRequest.TransformationInfo transformationInfo) {
        this.a.b(this.b, this.c, transformationInfo);
    }
}
