package com.leedarson.serviceimpl.udp.manager;

import android.text.TextUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.internal.LinkedTreeMap;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.lang.reflect.Type;

/* compiled from: GsonUtils */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String b(Object object) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{object}, (Object) null, changeQuickRedirect, true, 9107, new Class[]{Object.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (object == null) {
            return "";
        }
        return new GsonBuilder().serializeNulls().registerTypeAdapter(Double.class, new C0166a()).disableHtmlEscaping().create().toJson(object);
    }

    /* renamed from: com.leedarson.serviceimpl.udp.manager.a$a  reason: collision with other inner class name */
    /* compiled from: GsonUtils */
    public class C0166a implements JsonSerializer<Double> {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0166a() {
        }

        public /* bridge */ /* synthetic */ JsonElement serialize(Object obj, Type type, JsonSerializationContext jsonSerializationContext) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, type, jsonSerializationContext}, this, changeQuickRedirect, false, 9113, new Class[]{Object.class, Type.class, JsonSerializationContext.class}, JsonElement.class);
            return proxy.isSupported ? (JsonElement) proxy.result : a((Double) obj, type, jsonSerializationContext);
        }

        public JsonElement a(Double src, Type type, JsonSerializationContext jsonSerializationContext) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{src, type, jsonSerializationContext}, this, changeQuickRedirect2, false, 9112, new Class[]{Double.class, Type.class, JsonSerializationContext.class}, JsonElement.class);
            if (proxy.isSupported) {
                return (JsonElement) proxy.result;
            }
            if (src.doubleValue() == ((double) src.longValue())) {
                return new JsonPrimitive((Number) Long.valueOf(src.longValue()));
            }
            return new JsonPrimitive((Number) src);
        }
    }

    public static LinkedTreeMap a(String jsonData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonData}, (Object) null, changeQuickRedirect, true, 9111, new Class[]{String.class}, LinkedTreeMap.class);
        if (proxy.isSupported) {
            return (LinkedTreeMap) proxy.result;
        }
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        return (LinkedTreeMap) new GsonBuilder().disableHtmlEscaping().create().fromJson(jsonData, LinkedTreeMap.class);
    }
}
