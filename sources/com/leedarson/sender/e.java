package com.leedarson.sender;

import com.leedarson.base.http.listener.a;
import com.meituan.robust.ChangeQuickRedirect;
import org.json.JSONObject;

/* compiled from: IRhythmSender */
public abstract class e {
    public static ChangeQuickRedirect changeQuickRedirect;

    public abstract void a(String str, JSONObject jSONObject);

    public abstract void b(String str, String str2, byte b, a aVar);

    public abstract void c(String str, String str2, int[] iArr);
}
