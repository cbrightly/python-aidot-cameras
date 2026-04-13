package com.leedarson.mqtt.repos;

import io.reactivex.f;
import io.reactivex.g;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class d implements g {
    public final /* synthetic */ h a;
    public final /* synthetic */ JSONObject b;

    public /* synthetic */ d(h hVar, JSONObject jSONObject) {
        this.a = hVar;
        this.b = jSONObject;
    }

    public final void subscribe(f fVar) {
        this.a.v(this.b, fVar);
    }
}
