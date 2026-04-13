package io.ktor.server.engine;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: EngineConnectorConfig.kt */
public class q implements r {
    @NotNull
    private String a;
    private int b;
    @NotNull
    private final l c;

    public q(@NotNull l type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.c = type;
        this.a = NetworkListener.DEFAULT_NETWORK_HOST;
        this.b = 80;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ q(l lVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? l.c.a() : lVar);
    }

    @NotNull
    public l getType() {
        return this.c;
    }

    @NotNull
    public String b() {
        return this.a;
    }

    public void c(@NotNull String str) {
        k.f(str, "<set-?>");
        this.a = str;
    }

    public int a() {
        return this.b;
    }

    public void d(int i) {
        this.b = i;
    }

    @NotNull
    public String toString() {
        return getType().c() + ' ' + b() + ':' + a();
    }
}
