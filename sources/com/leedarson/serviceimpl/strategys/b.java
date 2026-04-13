package com.leedarson.serviceimpl.strategys;

import com.leedarson.serviceimpl.strategys.e;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ e.b c;
    public final /* synthetic */ String d;

    public /* synthetic */ b(e.b bVar, String str) {
        this.c = bVar;
        this.d = str;
    }

    public final void accept(Object obj) {
        this.c.e(this.d, (Throwable) obj);
    }
}
