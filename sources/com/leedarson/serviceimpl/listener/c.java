package com.leedarson.serviceimpl.listener;

import com.leedarson.serviceimpl.g;
import com.meituan.robust.ChangeQuickRedirect;
import org.json.JSONObject;

/* compiled from: MatterGlobalCallback */
public abstract class c extends g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public abstract void a(long j, int i);

    public abstract void h(long j, JSONObject jSONObject);
}
