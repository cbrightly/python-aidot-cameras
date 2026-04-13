package coil.bitmap;

import android.graphics.Bitmap;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ i c;
    public final /* synthetic */ Bitmap d;

    public /* synthetic */ a(i iVar, Bitmap bitmap) {
        this.c = iVar;
        this.d = bitmap;
    }

    public final void run() {
        i.f(this.c, this.d);
    }
}
