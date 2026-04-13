package com.didichuxing.doraemonkit.aop.urlconnection;

import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.a;
import kotlin.l;
import okhttp3.z;

@l(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0010\u0004\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"Lokhttp3/z;", "kotlin.jvm.PlatformType", "invoke", "()Lokhttp3/z;", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: OkhttpClientUtil.kt */
public final class OkhttpClientUtil$okhttpClient$2 extends kotlin.jvm.internal.l implements a<z> {
    public static final OkhttpClientUtil$okhttpClient$2 INSTANCE = new OkhttpClientUtil$okhttpClient$2();

    OkhttpClientUtil$okhttpClient$2() {
        super(0);
    }

    public final z invoke() {
        z.a S = new z.a().S(true);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        return S.R(60000, timeUnit).V(60000, timeUnit).e(60000, timeUnit).c();
    }
}
