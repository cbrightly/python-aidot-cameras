package com.leedarson.base.http.converter;

import com.alibaba.fastjson.asm.Opcodes;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedReader;
import okhttp3.e0;
import org.json.JSONObject;
import retrofit2.h;

/* compiled from: StringResponseBodyConverter */
public class c implements h<e0, String> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ Object convert(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, Opcodes.IF_ACMPEQ, new Class[]{Object.class}, Object.class);
        return proxy.isSupported ? proxy.result : a((e0) obj);
    }

    c(JSONObject jsonObject) {
    }

    public String a(e0 value) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{value}, this, changeQuickRedirect, false, 164, new Class[]{e0.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        BufferedReader br = new BufferedReader(value.charStream());
        StringBuffer buffer = new StringBuffer();
        while (true) {
            String readLine = br.readLine();
            String line = readLine;
            if (readLine == null) {
                return buffer.toString();
            }
            buffer.append(line);
        }
    }
}
