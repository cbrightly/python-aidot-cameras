package org.webrtc;

import org.webrtc.Camera1Session;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ Camera1Session.AnonymousClass2 c;
    public final /* synthetic */ byte[] d;

    public /* synthetic */ b(Camera1Session.AnonymousClass2 r1, byte[] bArr) {
        this.c = r1;
        this.d = bArr;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
