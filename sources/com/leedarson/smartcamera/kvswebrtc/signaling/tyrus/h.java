package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus;

import com.leedarson.smartcamera.kvswebrtc.signaling.c;
import io.reactivex.functions.e;
import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class h implements e {
    public final /* synthetic */ o c;
    public final /* synthetic */ String d;
    public final /* synthetic */ List f;
    public final /* synthetic */ c q;

    public /* synthetic */ h(o oVar, String str, List list, c cVar) {
        this.c = oVar;
        this.d = str;
        this.f = list;
        this.q = cVar;
    }

    public final void accept(Object obj) {
        this.c.l(this.d, this.f, this.q, (String) obj);
    }
}
