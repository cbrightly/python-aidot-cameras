package androidx.camera.view;

import androidx.camera.core.impl.CameraInternal;
import androidx.camera.view.PreviewView;
import androidx.camera.view.PreviewViewImplementation;

/* compiled from: lambda */
public final /* synthetic */ class h implements PreviewViewImplementation.OnSurfaceNotInUseListener {
    public final /* synthetic */ PreviewView.AnonymousClass1 a;
    public final /* synthetic */ PreviewStreamStateObserver b;
    public final /* synthetic */ CameraInternal c;

    public /* synthetic */ h(PreviewView.AnonymousClass1 r1, PreviewStreamStateObserver previewStreamStateObserver, CameraInternal cameraInternal) {
        this.a = r1;
        this.b = previewStreamStateObserver;
        this.c = cameraInternal;
    }

    public final void onSurfaceNotInUse() {
        this.a.c(this.b, this.c);
    }
}
