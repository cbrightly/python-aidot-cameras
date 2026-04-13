package com.leedarson.serviceimpl.business;

import io.reactivex.functions.f;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class l implements f {
    public final /* synthetic */ BusinessServiceImpl c;
    public final /* synthetic */ String d;
    public final /* synthetic */ Map f;

    public /* synthetic */ l(BusinessServiceImpl businessServiceImpl, String str, Map map) {
        this.c = businessServiceImpl;
        this.d = str;
        this.f = map;
    }

    public final Object apply(Object obj) {
        return this.c.a(this.d, this.f, obj);
    }
}
