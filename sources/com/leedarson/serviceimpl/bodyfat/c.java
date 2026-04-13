package com.leedarson.serviceimpl.bodyfat;

import com.leedarson.serviceimpl.blec075.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Iterator;
import org.json.JSONObject;

/* compiled from: BFUtil */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] b(int i, int bytesLen) {
        Object[] objArr = {new Integer(i), new Integer(bytesLen)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6893, new Class[]{cls, cls}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : h.j((long) i, bytesLen);
    }

    public static long a(byte[] bytes, int bytesLen) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bytes, new Integer(bytesLen)}, (Object) null, changeQuickRedirect, true, 6894, new Class[]{byte[].class, Integer.TYPE}, Long.TYPE);
        return proxy.isSupported ? ((Long) proxy.result).longValue() : h.a(bytes, bytesLen);
    }

    public static JSONObject c(JSONObject json1, JSONObject json2) {
        Class<JSONObject> cls = JSONObject.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{json1, json2}, (Object) null, changeQuickRedirect2, true, 6895, new Class[]{cls, cls}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        Iterator<String> keys = json2.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            json1.put(key, json2.get(key));
        }
        return json1;
    }
}
