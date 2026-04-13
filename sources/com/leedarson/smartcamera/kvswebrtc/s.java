package com.leedarson.smartcamera.kvswebrtc;

import android.content.Context;
import com.leedarson.smartcamera.kvswebrtc.signaling.c;

/* compiled from: lambda */
public final /* synthetic */ class s implements Runnable {
    public final /* synthetic */ f0 c;
    public final /* synthetic */ Context d;
    public final /* synthetic */ c f;

    public /* synthetic */ s(f0 f0Var, Context context, c cVar) {
        this.c = f0Var;
        this.d = context;
        this.f = cVar;
    }

    public final void run() {
        this.c.E1(this.d, this.f);
    }
}
