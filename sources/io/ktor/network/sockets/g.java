package io.ktor.network.sockets;

import io.ktor.network.selector.m;
import java.nio.channels.SelectableChannel;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Builders.kt */
public final class g {
    @NotNull
    public static final o a(@NotNull m selector) {
        k.f(selector, "selector");
        return new o(selector, q.a.a());
    }

    /* access modifiers changed from: private */
    public static final void c(@NotNull SelectableChannel $this$nonBlocking) {
        $this$nonBlocking.configureBlocking(false);
    }
}
