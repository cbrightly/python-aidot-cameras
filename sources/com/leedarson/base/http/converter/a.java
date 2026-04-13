package com.leedarson.base.http.converter;

import com.alibaba.fastjson.asm.Opcodes;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import okhttp3.c0;
import okhttp3.e0;
import okhttp3.x;
import org.json.JSONObject;
import retrofit2.h;
import retrofit2.t;

/* compiled from: StringConverterFactory */
public class a extends h.a {
    private static final x a = x.h("application/json; charset=UTF-8");
    private static final Charset b = Charset.forName("UTF-8");
    public static ChangeQuickRedirect changeQuickRedirect;
    private JSONObject c;

    private a(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.c = jsonObject;
            return;
        }
        throw new NullPointerException("json == null");
    }

    public static a a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, Opcodes.IF_ICMPEQ, new Class[0], a.class);
        return proxy.isSupported ? (a) proxy.result : new a(new JSONObject());
    }

    public h<e0, ?> responseBodyConverter(Type type, Annotation[] annotationArr, t tVar) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{type, annotationArr, tVar}, this, changeQuickRedirect, false, Opcodes.IF_ICMPNE, new Class[]{Type.class, Annotation[].class, t.class}, h.class);
        return proxy.isSupported ? (h) proxy.result : new c(this.c);
    }

    public h<?, c0> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, t tVar) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{type, annotationArr, annotationArr2, tVar}, this, changeQuickRedirect, false, Opcodes.IF_ICMPLT, new Class[]{Type.class, Annotation[].class, Annotation[].class, t.class}, h.class);
        if (proxy.isSupported) {
            return (h) proxy.result;
        }
        return new b();
    }
}
