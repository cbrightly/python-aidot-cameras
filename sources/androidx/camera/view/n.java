package androidx.camera.view;

import androidx.camera.core.SurfaceRequest;
import androidx.camera.view.SurfaceViewImplementation;
import androidx.core.util.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class n implements Consumer {
    public final /* synthetic */ SurfaceViewImplementation.SurfaceRequestCallback a;

    public /* synthetic */ n(SurfaceViewImplementation.SurfaceRequestCallback surfaceRequestCallback) {
        this.a = surfaceRequestCallback;
    }

    public final void accept(Object obj) {
        this.a.a((SurfaceRequest.Result) obj);
    }
}
