package androidx.camera.core;

import androidx.camera.core.impl.ImageReaderProxy;

/* compiled from: lambda */
public final /* synthetic */ class e1 implements ImageReaderProxy.OnImageAvailableListener {
    public final /* synthetic */ ProcessingSurface a;

    public /* synthetic */ e1(ProcessingSurface processingSurface) {
        this.a = processingSurface;
    }

    public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
        this.a.d(imageReaderProxy);
    }
}
