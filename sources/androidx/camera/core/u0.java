package androidx.camera.core;

import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class u0 implements Runnable {
    public final /* synthetic */ ImageSaver c;
    public final /* synthetic */ File d;

    public /* synthetic */ u0(ImageSaver imageSaver, File file) {
        this.c = imageSaver;
        this.d = file;
    }

    public final void run() {
        this.c.c(this.d);
    }
}
