package com.leedarson.serviceimpl.camera;

import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.serviceimpl.camera.CameraServiceImpl;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ CameraServiceImpl.c c;
    public final /* synthetic */ LinkedTreeMap d;
    public final /* synthetic */ String f;
    public final /* synthetic */ String q;

    public /* synthetic */ a(CameraServiceImpl.c cVar, LinkedTreeMap linkedTreeMap, String str, String str2) {
        this.c = cVar;
        this.d = linkedTreeMap;
        this.f = str;
        this.q = str2;
    }

    public final void run() {
        this.c.b(this.d, this.f, this.q);
    }
}
