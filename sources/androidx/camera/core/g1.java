package androidx.camera.core;

import androidx.camera.core.ForwardingImageProxy;

/* compiled from: lambda */
public final /* synthetic */ class g1 implements ForwardingImageProxy.OnImageCloseListener {
    public final /* synthetic */ SafeCloseImageReaderProxy a;

    public /* synthetic */ g1(SafeCloseImageReaderProxy safeCloseImageReaderProxy) {
        this.a = safeCloseImageReaderProxy;
    }

    public final void onImageClose(ImageProxy imageProxy) {
        this.a.a(imageProxy);
    }
}
