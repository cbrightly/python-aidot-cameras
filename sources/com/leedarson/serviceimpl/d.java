package com.leedarson.serviceimpl;

import io.reactivex.functions.e;
import org.json.JSONArray;

/* compiled from: lambda */
public final /* synthetic */ class d implements e {
    public final /* synthetic */ LightsRhythmServiceImpl c;

    public /* synthetic */ d(LightsRhythmServiceImpl lightsRhythmServiceImpl) {
        this.c = lightsRhythmServiceImpl;
    }

    public final void accept(Object obj) {
        this.c.y((JSONArray) obj);
    }
}
