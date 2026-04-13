package com.leedarson.serviceimpl.security.http;

import io.reactivex.m;
import io.reactivex.n;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class a implements n {
    public final /* synthetic */ b a;
    public final /* synthetic */ JSONObject b;
    public final /* synthetic */ JSONObject c;
    public final /* synthetic */ int d;

    public /* synthetic */ a(b bVar, JSONObject jSONObject, JSONObject jSONObject2, int i) {
        this.a = bVar;
        this.b = jSONObject;
        this.c = jSONObject2;
        this.d = i;
    }

    public final void subscribe(m mVar) {
        this.a.b(this.b, this.c, this.d, mVar);
    }
}
