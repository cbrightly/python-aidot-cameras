package com.leedarson.serviceimpl.business;

import com.leedarson.serviceimpl.business.BusinessServiceImpl;
import org.json.JSONArray;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    public final /* synthetic */ BusinessServiceImpl.AnonymousClass4 c;
    public final /* synthetic */ String d;
    public final /* synthetic */ JSONArray f;

    public /* synthetic */ f(BusinessServiceImpl.AnonymousClass4 r1, String str, JSONArray jSONArray) {
        this.c = r1;
        this.d = str;
        this.f = jSONArray;
    }

    public final void run() {
        this.c.a(this.d, this.f);
    }
}
