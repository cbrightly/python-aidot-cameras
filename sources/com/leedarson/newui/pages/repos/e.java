package com.leedarson.newui.pages.repos;

import com.leedarson.bean.SDRecord;
import io.reactivex.f;
import io.reactivex.g;
import java.util.ArrayList;

/* compiled from: lambda */
public final /* synthetic */ class e implements g {
    public final /* synthetic */ ArrayList a;
    public final /* synthetic */ SDRecord b;
    public final /* synthetic */ i c;
    public final /* synthetic */ int d;

    public /* synthetic */ e(ArrayList arrayList, SDRecord sDRecord, i iVar, int i) {
        this.a = arrayList;
        this.b = sDRecord;
        this.c = iVar;
        this.d = i;
    }

    public final void subscribe(f fVar) {
        i.m(this.a, this.b, this.c, this.d, fVar);
    }
}
