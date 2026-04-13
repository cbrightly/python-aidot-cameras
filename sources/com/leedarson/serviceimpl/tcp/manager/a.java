package com.leedarson.serviceimpl.tcp.manager;

import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import com.google.gson.Gson;
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
import java.util.HashMap;
import java.util.Map;

/* compiled from: GsonUtils */
public class a {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String e(Object object) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{object}, (Object) null, changeQuickRedirect, true, 8995, new Class[]{Object.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (object == null) {
            return "";
        }
        return new GsonBuilder().serializeNulls().registerTypeAdapter(Double.class, new C0164a()).disableHtmlEscaping().create().toJson(object);
    }

    /* renamed from: com.leedarson.serviceimpl.tcp.manager.a$a  reason: collision with other inner class name */
    /* compiled from: GsonUtils */
    public class C0164a implements JsonSerializer<Double> {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0164a() {
        }

        public /* bridge */ /* synthetic */ JsonElement serialize(Object obj, Type type, JsonSerializationContext jsonSerializationContext) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, type, jsonSerializationContext}, this, changeQuickRedirect, false, 9002, new Class[]{Object.class, Type.class, JsonSerializationContext.class}, JsonElement.class);
            return proxy.isSupported ? (JsonElement) proxy.result : a((Double) obj, type, jsonSerializationContext);
        }

        public JsonElement a(Double src, Type type, JsonSerializationContext jsonSerializationContext) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{src, type, jsonSerializationContext}, this, changeQuickRedirect2, false, 9001, new Class[]{Double.class, Type.class, JsonSerializationContext.class}, JsonElement.class);
            if (proxy.isSupported) {
                return (JsonElement) proxy.result;
            }
            if (src.doubleValue() == ((double) src.longValue())) {
                return new JsonPrimitive((Number) Long.valueOf(src.longValue()));
            }
            return new JsonPrimitive((Number) src);
        }
    }

    public static Map<String, String> d(String jsonData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonData}, (Object) null, changeQuickRedirect, true, 8997, new Class[]{String.class}, Map.class);
        if (proxy.isSupported) {
            return (Map) proxy.result;
        }
        return (Map) new Gson().fromJson(jsonData, HashMap.class);
    }

    public static LinkedTreeMap c(String jsonData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonData}, (Object) null, changeQuickRedirect, true, 8998, new Class[]{String.class}, LinkedTreeMap.class);
        if (proxy.isSupported) {
            return (LinkedTreeMap) proxy.result;
        }
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        return (LinkedTreeMap) new GsonBuilder().disableHtmlEscaping().create().fromJson(jsonData, LinkedTreeMap.class);
    }

    public static short b(byte[] b) {
        return (short) (((short) (b[1] & 255)) | ((short) (((short) (b[0] & 255)) << 8)));
    }

    public static int a(byte[] res) {
        return (res[3] & 255) | ((res[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((res[1] << 24) >>> 8) | (res[0] << 24);
    }

    public static byte[] f(String hexString) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{hexString}, (Object) null, changeQuickRedirect, true, 9000, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            int h = "0123456789abcdef".indexOf(hexChars[pos]) << 4;
            int l = "0123456789abcdef".indexOf(hexChars[pos + 1]);
            if (h == -1 || l == -1) {
                return null;
            }
            bytes[i] = (byte) (h | l);
        }
        return bytes;
    }
}
