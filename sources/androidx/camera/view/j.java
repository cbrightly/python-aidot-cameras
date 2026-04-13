package androidx.camera.view;

import androidx.camera.core.SurfaceRequest;
import androidx.camera.view.PreviewView;

/* compiled from: lambda */
public final /* synthetic */ class j implements Runnable {
    public final /* synthetic */ PreviewView.AnonymousClass1 c;
    public final /* synthetic */ SurfaceRequest d;

    public /* synthetic */ j(PreviewView.AnonymousClass1 r1, SurfaceRequest surfaceRequest) {
        this.c = r1;
        this.d = surfaceRequest;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
