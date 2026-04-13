package com.leedarson.serviceimpl.business;

import com.leedarson.base.http.listener.b;
import io.reactivex.functions.e;
import okhttp3.e0;

/* compiled from: lambda */
public final /* synthetic */ class u implements e {
    public final /* synthetic */ TRVOta c;
    public final /* synthetic */ String d;
    public final /* synthetic */ b f;

    public /* synthetic */ u(TRVOta tRVOta, String str, b bVar) {
        this.c = tRVOta;
        this.d = str;
        this.f = bVar;
    }

    public final void accept(Object obj) {
        this.c.a(this.d, this.f, (e0) obj);
    }
}
