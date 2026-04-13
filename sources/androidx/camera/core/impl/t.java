package androidx.camera.core.impl;

import androidx.camera.core.impl.CameraStateRegistry;

/* compiled from: lambda */
public final /* synthetic */ class t implements Runnable {
    public final /* synthetic */ CameraStateRegistry.OnOpenAvailableListener c;

    public /* synthetic */ t(CameraStateRegistry.OnOpenAvailableListener onOpenAvailableListener) {
        this.c = onOpenAvailableListener;
    }

    public final void run() {
        this.c.onOpenAvailable();
    }
}
