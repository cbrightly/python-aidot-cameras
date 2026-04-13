package com.leedarson.serviceimpl.business.netease.LDNetDiagnoService;

import io.reactivex.functions.e;
import org.json.JSONObject;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ JSONObject c;

    public /* synthetic */ b(JSONObject jSONObject) {
        this.c = jSONObject;
    }

    public final void accept(Object obj) {
        LDNetDiagnoService.lambda$uploadNetDiagMessage$0(this.c, (Integer) obj);
    }
}
