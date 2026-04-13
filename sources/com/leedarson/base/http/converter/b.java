package com.leedarson.base.http.converter;

import com.alibaba.fastjson.asm.Opcodes;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import okhttp3.c0;
import okhttp3.x;
import okio.c;
import retrofit2.h;

/* compiled from: StringRequestBodyConverter */
public class b<T> implements h<T, c0> {
    private static final x a = x.h("application/json; charset=UTF-8");
    private static final Charset b = Charset.forName("UTF-8");
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ Object convert(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, Opcodes.IF_ICMPGT, new Class[]{Object.class}, Object.class);
        return proxy.isSupported ? proxy.result : a(obj);
    }

    b() {
    }

    public c0 a(T value) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{value}, this, changeQuickRedirect, false, Opcodes.IF_ICMPGE, new Class[]{Object.class}, c0.class);
        if (proxy.isSupported) {
            return (c0) proxy.result;
        }
        c buffer = new c();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), b);
        writer.write(value.toString());
        writer.close();
        return c0.create(a, buffer.D0());
    }
}
