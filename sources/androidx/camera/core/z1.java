package androidx.camera.core;

/* compiled from: lambda */
public final /* synthetic */ class z1 implements Runnable {
    public final /* synthetic */ SafeCloseImageReaderProxy c;

    public /* synthetic */ z1(SafeCloseImageReaderProxy safeCloseImageReaderProxy) {
        this.c = safeCloseImageReaderProxy;
    }

    public final void run() {
        this.c.safeClose();
    }
}
