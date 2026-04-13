package com.didichuxing.doraemonkit.aop.urlconnection;

import kotlin.g;
import kotlin.i;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;

/* compiled from: OkhttpClientUtil.kt */
public final class OkhttpClientUtil {
    public static final OkhttpClientUtil INSTANCE = new OkhttpClientUtil();
    @NotNull
    private static final g okhttpClient$delegate = i.b(OkhttpClientUtil$okhttpClient$2.INSTANCE);

    @NotNull
    public final z getOkhttpClient() {
        return (z) okhttpClient$delegate.getValue();
    }

    private OkhttpClientUtil() {
    }
}
