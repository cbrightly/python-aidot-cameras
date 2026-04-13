package androidx.camera.core;

import androidx.camera.core.impl.ImageReaderProxy;

/* compiled from: lambda */
public final /* synthetic */ class x0 implements Runnable {
    public final /* synthetic */ MetadataImageReader c;
    public final /* synthetic */ ImageReaderProxy.OnImageAvailableListener d;

    public /* synthetic */ x0(MetadataImageReader metadataImageReader, ImageReaderProxy.OnImageAvailableListener onImageAvailableListener) {
        this.c = metadataImageReader;
        this.d = onImageAvailableListener;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
