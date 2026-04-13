package androidx.camera.core;

import android.net.Uri;

/* compiled from: lambda */
public final /* synthetic */ class t0 implements Runnable {
    public final /* synthetic */ ImageSaver c;
    public final /* synthetic */ Uri d;

    public /* synthetic */ t0(ImageSaver imageSaver, Uri uri) {
        this.c = imageSaver;
        this.d = uri;
    }

    public final void run() {
        this.c.b(this.d);
    }
}
