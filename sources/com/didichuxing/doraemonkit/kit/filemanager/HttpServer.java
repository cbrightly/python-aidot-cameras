package com.didichuxing.doraemonkit.kit.filemanager;

import io.ktor.server.cio.c;
import kotlin.g;
import kotlin.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpServer.kt */
public final class HttpServer {
    public static final HttpServer INSTANCE = new HttpServer();
    @NotNull
    private static final g server$delegate = i.b(HttpServer$server$2.INSTANCE);

    @NotNull
    public final c getServer() {
        return (c) server$delegate.getValue();
    }

    private HttpServer() {
    }
}
