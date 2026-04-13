package androidx.camera.core;

import androidx.camera.core.ImageSaver;

/* compiled from: lambda */
public final /* synthetic */ class v0 implements Runnable {
    public final /* synthetic */ ImageSaver c;
    public final /* synthetic */ ImageSaver.SaveError d;
    public final /* synthetic */ String f;
    public final /* synthetic */ Throwable q;

    public /* synthetic */ v0(ImageSaver imageSaver, ImageSaver.SaveError saveError, String str, Throwable th) {
        this.c = imageSaver;
        this.d = saveError;
        this.f = str;
        this.q = th;
    }

    public final void run() {
        this.c.a(this.d, this.f, this.q);
    }
}
