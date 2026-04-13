package com.leedarson.newui.pages.repos;

import io.reactivex.f;
import io.reactivex.g;
import java.util.ArrayList;

/* compiled from: lambda */
public final /* synthetic */ class c implements g {
    public final /* synthetic */ ArrayList a;
    public final /* synthetic */ h b;

    public /* synthetic */ c(ArrayList arrayList, h hVar) {
        this.a = arrayList;
        this.b = hVar;
    }

    public final void subscribe(f fVar) {
        h.d(this.a, this.b, fVar);
    }
}
