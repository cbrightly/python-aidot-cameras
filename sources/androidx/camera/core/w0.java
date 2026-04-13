package androidx.camera.core;

import androidx.camera.core.impl.ImageReaderProxy;

/* compiled from: lambda */
public final /* synthetic */ class w0 implements ImageReaderProxy.OnImageAvailableListener {
    public final /* synthetic */ MetadataImageReader a;

    public /* synthetic */ w0(MetadataImageReader metadataImageReader) {
        this.a = metadataImageReader;
    }

    public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
        this.a.b(imageReaderProxy);
    }
}
