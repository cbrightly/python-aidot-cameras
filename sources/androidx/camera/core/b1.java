package androidx.camera.core;

import androidx.camera.core.ProcessingImageReader;
import androidx.camera.core.impl.ImageReaderProxy;

/* compiled from: lambda */
public final /* synthetic */ class b1 implements Runnable {
    public final /* synthetic */ ProcessingImageReader.AnonymousClass2 c;
    public final /* synthetic */ ImageReaderProxy.OnImageAvailableListener d;

    public /* synthetic */ b1(ProcessingImageReader.AnonymousClass2 r1, ImageReaderProxy.OnImageAvailableListener onImageAvailableListener) {
        this.c = r1;
        this.d = onImageAvailableListener;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
