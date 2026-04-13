package com.leedarson.newui;

import com.leedarson.view.IpcWebrtcSurfaceView;
import io.reactivex.functions.e;
import kotlin.n;

/* compiled from: lambda */
public final /* synthetic */ class n4 implements e {
    public final /* synthetic */ b6 c;
    public final /* synthetic */ boolean d;
    public final /* synthetic */ IpcWebrtcSurfaceView f;

    public /* synthetic */ n4(b6 b6Var, boolean z, IpcWebrtcSurfaceView ipcWebrtcSurfaceView) {
        this.c = b6Var;
        this.d = z;
        this.f = ipcWebrtcSurfaceView;
    }

    public final void accept(Object obj) {
        this.c.I0(this.d, this.f, (n) obj);
    }
}
