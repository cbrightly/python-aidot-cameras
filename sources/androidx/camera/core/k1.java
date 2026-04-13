package androidx.camera.core;

import androidx.camera.core.SurfaceRequest;

/* compiled from: lambda */
public final /* synthetic */ class k1 implements Runnable {
    public final /* synthetic */ SurfaceRequest.TransformationInfoListener c;
    public final /* synthetic */ SurfaceRequest.TransformationInfo d;

    public /* synthetic */ k1(SurfaceRequest.TransformationInfoListener transformationInfoListener, SurfaceRequest.TransformationInfo transformationInfo) {
        this.c = transformationInfoListener;
        this.d = transformationInfo;
    }

    public final void run() {
        this.c.onTransformationInfoUpdate(this.d);
    }
}
