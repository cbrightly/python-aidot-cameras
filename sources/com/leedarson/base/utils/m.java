package com.leedarson.base.utils;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: GsonUtils */
public class m {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String d(Object object) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{object}, (Object) null, changeQuickRedirect, true, 483, new Class[]{Object.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (object == null) {
            return "";
        }
        return new GsonBuilder().serializeNulls().registerTypeAdapter(Double.class, new a()).disableHtmlEscaping().create().toJson(object);
    }

    /* compiled from: GsonUtils */
    public class a implements JsonSerializer<Double> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ JsonElement serialize(Object obj, Type type, JsonSerializationContext jsonSerializationContext) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, type, jsonSerializationContext}, this, changeQuickRedirect, false, 489, new Class[]{Object.class, Type.class, JsonSerializationContext.class}, JsonElement.class);
            return proxy.isSupported ? (JsonElement) proxy.result : a((Double) obj, type, jsonSerializationContext);
        }

        public JsonElement a(Double src, Type type, JsonSerializationContext jsonSerializationContext) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{src, type, jsonSerializationContext}, this, changeQuickRedirect2, false, 488, new Class[]{Double.class, Type.class, JsonSerializationContext.class}, JsonElement.class);
            if (proxy.isSupported) {
                return (JsonElement) proxy.result;
            }
            if (src.doubleValue() == ((double) src.longValue())) {
                return new JsonPrimitive((Number) Long.valueOf(src.longValue()));
            }
            return new JsonPrimitive((Number) src);
        }
    }

    public static Object a(String responseData, Class c) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{responseData, c}, (Object) null, changeQuickRedirect2, true, 484, new Class[]{String.class, Class.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        if (responseData == null) {
            return null;
        }
        return new GsonBuilder().serializeNulls().create().fromJson(responseData, c);
    }

    public static Map<String, Object> c(String jsonData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonData}, (Object) null, changeQuickRedirect, true, 485, new Class[]{String.class}, Map.class);
        if (proxy.isSupported) {
            return (Map) proxy.result;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            Iterator<String> keyIter = jsonObject.keys();
            Map<String, Object> valueMap = new HashMap<>();
            while (keyIter.hasNext()) {
                String key = keyIter.next();
                valueMap.put(key, jsonObject.get(key));
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LinkedTreeMap b(String jsonData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonData}, (Object) null, changeQuickRedirect, true, 486, new Class[]{String.class}, LinkedTreeMap.class);
        if (proxy.isSupported) {
            return (LinkedTreeMap) proxy.result;
        }
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        return (LinkedTreeMap) new GsonBuilder().disableHtmlEscaping().create().fromJson(jsonData, LinkedTreeMap.class);
    }

    public static final boolean e(String jsonInString) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonInString}, (Object) null, changeQuickRedirect, true, 487, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            new Gson().fromJson(jsonInString, Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }
}
