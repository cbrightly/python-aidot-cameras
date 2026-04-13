package com.leedarson.newui.pages.repos;

import com.leedarson.bean.SDRecord;
import io.reactivex.g;
import java.util.ArrayList;

/* compiled from: lambda */
public final /* synthetic */ class f implements g {
    public final /* synthetic */ ArrayList a;
    public final /* synthetic */ SDRecord b;
    public final /* synthetic */ i c;
    public final /* synthetic */ int d;

    public /* synthetic */ f(ArrayList arrayList, SDRecord sDRecord, i iVar, int i) {
        this.a = arrayList;
        this.b = sDRecord;
        this.c = iVar;
        this.d = i;
    }

    public final void subscribe(io.reactivex.f fVar) {
        i.o(this.a, this.b, this.c, this.d, fVar);
    }
}
