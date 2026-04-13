package androidx.camera.core;

import android.media.ImageReader;
import androidx.camera.core.impl.ImageReaderProxy;
import java.util.concurrent.Executor;

/* compiled from: lambda */
public final /* synthetic */ class b implements ImageReader.OnImageAvailableListener {
    public final /* synthetic */ AndroidImageReaderProxy a;
    public final /* synthetic */ Executor b;
    public final /* synthetic */ ImageReaderProxy.OnImageAvailableListener c;

    public /* synthetic */ b(AndroidImageReaderProxy androidImageReaderProxy, Executor executor, ImageReaderProxy.OnImageAvailableListener onImageAvailableListener) {
        this.a = androidImageReaderProxy;
        this.b = executor;
        this.c = onImageAvailableListener;
    }

    public final void onImageAvailable(ImageReader imageReader) {
        this.a.b(this.b, this.c, imageReader);
    }
}
