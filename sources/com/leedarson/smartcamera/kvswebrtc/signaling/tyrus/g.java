package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus;

import com.leedarson.smartcamera.kvswebrtc.signaling.c;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class g implements e {
    public final /* synthetic */ o c;
    public final /* synthetic */ c d;
    public final /* synthetic */ String f;

    public /* synthetic */ g(o oVar, c cVar, String str) {
        this.c = oVar;
        this.d = cVar;
        this.f = str;
    }

    public final void accept(Object obj) {
        this.c.n(this.d, this.f, (Throwable) obj);
    }
}
