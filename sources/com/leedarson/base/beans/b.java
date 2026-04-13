package com.leedarson.base.beans;

import androidx.core.app.NotificationCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TimePerformanceRecord */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a;
    private String b = "Native";
    private String c = "Performance";
    private String d;

    public b(String time, String message) {
        this.a = time;
        this.d = message;
    }

    public JSONObject a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, Opcodes.DCMPL, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject jo = new JSONObject();
        try {
            jo.put("time", (Object) this.a);
            jo.put(NotificationCompat.CATEGORY_SERVICE, (Object) "Native");
            jo.put("module", (Object) "Performance");
            jo.put("message", (Object) this.d);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }
}
