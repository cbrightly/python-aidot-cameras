package androidx.camera.core;

import androidx.camera.core.VideoCapture;

/* compiled from: lambda */
public final /* synthetic */ class r1 implements Runnable {
    public final /* synthetic */ VideoCapture.VideoSavedListenerWrapper c;
    public final /* synthetic */ int d;
    public final /* synthetic */ String f;
    public final /* synthetic */ Throwable q;

    public /* synthetic */ r1(VideoCapture.VideoSavedListenerWrapper videoSavedListenerWrapper, int i, String str, Throwable th) {
        this.c = videoSavedListenerWrapper;
        this.d = i;
        this.f = str;
        this.q = th;
    }

    public final void run() {
        this.c.a(this.d, this.f, this.q);
    }
}
