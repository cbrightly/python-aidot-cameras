package com.didichuxing.doraemonkit.kit.filemanager;

import com.didichuxing.doraemonkit.constant.DokitConstant;
import io.ktor.server.cio.c;
import io.ktor.server.engine.p;
import java.util.List;
import kotlin.jvm.functions.a;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Lio/ktor/server/cio/c;", "invoke", "()Lio/ktor/server/cio/c;", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: HttpServer.kt */
public final class HttpServer$server$2 extends kotlin.jvm.internal.l implements a<c> {
    public static final HttpServer$server$2 INSTANCE = new HttpServer$server$2();

    HttpServer$server$2() {
        super(0);
    }

    @NotNull
    public final c invoke() {
        return (c) p.d(io.ktor.server.cio.a.a, DokitConstant.INSTANCE.getFILE_MANAGER_HTTP_PORT(), (String) null, (List) null, (kotlin.jvm.functions.l) null, DokitFileRouterKt.getDoKitFileRouter(), 28, (Object) null);
    }
}
