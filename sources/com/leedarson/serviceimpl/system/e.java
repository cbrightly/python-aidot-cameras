package com.leedarson.serviceimpl.system;

import java.io.File;
import okhttp3.e0;

/* compiled from: lambda */
public final /* synthetic */ class e implements io.reactivex.functions.e {
    public final /* synthetic */ i c;
    public final /* synthetic */ String d;
    public final /* synthetic */ File f;

    public /* synthetic */ e(i iVar, String str, File file) {
        this.c = iVar;
        this.d = str;
        this.f = file;
    }

    public final void accept(Object obj) {
        this.c.e(this.d, this.f, (e0) obj);
    }
}
