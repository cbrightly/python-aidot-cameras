package com.leedarson.log.elk;

import android.content.Context;
import com.leedarson.log.sensorsdata.a;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONObject;

/* compiled from: ELKCore */
public class b {
    private static b a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context b;
    private String c;

    public Context a() {
        return this.b;
    }

    public void d(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 1242, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.b = context;
            a.b().d(context);
        }
    }

    public String c() {
        return this.c;
    }

    public static b b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1243, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        if (a == null) {
            synchronized (b.class) {
                if (a == null) {
                    a = new b();
                }
            }
        }
        return a;
    }

    public void e(Context context, String data) {
        Class[] clsArr = {Context.class, String.class};
        if (!PatchProxy.proxy(new Object[]{context, data}, this, changeQuickRedirect, false, 1244, clsArr, Void.TYPE).isSupported) {
            this.b = context;
            try {
                JSONObject dataObj = new JSONObject(data);
                if (dataObj.has("reportHttpServer")) {
                    String string = dataObj.getString("reportHttpServer");
                    this.c = string;
                    SharePreferenceUtils.setPrefString(this.b, "reportHttpServer", string);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
