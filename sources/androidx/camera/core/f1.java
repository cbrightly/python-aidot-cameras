package androidx.camera.core;

import androidx.camera.core.impl.ImageReaderProxy;

/* compiled from: lambda */
public final /* synthetic */ class f1 implements ImageReaderProxy.OnImageAvailableListener {
    public final /* synthetic */ SafeCloseImageReaderProxy a;
    public final /* synthetic */ ImageReaderProxy.OnImageAvailableListener b;

    public /* synthetic */ f1(SafeCloseImageReaderProxy safeCloseImageReaderProxy, ImageReaderProxy.OnImageAvailableListener onImageAvailableListener) {
        this.a = safeCloseImageReaderProxy;
        this.b = onImageAvailableListener;
    }

    public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
        this.a.b(this.b, imageReaderProxy);
    }
}
