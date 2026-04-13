package com.leedarson.newui.cloud_play_back.repos;

import io.reactivex.f;
import io.reactivex.g;
import java.util.ArrayList;

/* compiled from: lambda */
public final /* synthetic */ class a implements g {
    public final /* synthetic */ w a;
    public final /* synthetic */ String b;
    public final /* synthetic */ String[] c;
    public final /* synthetic */ ArrayList d;

    public /* synthetic */ a(w wVar, String str, String[] strArr, ArrayList arrayList) {
        this.a = wVar;
        this.b = str;
        this.c = strArr;
        this.d = arrayList;
    }

    public final void subscribe(f fVar) {
        this.a.e(this.b, this.c, this.d, fVar);
    }
}
