package androidx.camera.core;

import androidx.camera.core.impl.ImageReaderProxy;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ AndroidImageReaderProxy c;
    public final /* synthetic */ ImageReaderProxy.OnImageAvailableListener d;

    public /* synthetic */ c(AndroidImageReaderProxy androidImageReaderProxy, ImageReaderProxy.OnImageAvailableListener onImageAvailableListener) {
        this.c = androidImageReaderProxy;
        this.d = onImageAvailableListener;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
