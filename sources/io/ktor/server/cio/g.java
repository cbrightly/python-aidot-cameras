package io.ktor.server.cio;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpServer.kt */
public final class g {
    @NotNull
    private final String a;
    private final int b;
    private final long c;

    public g() {
        this((String) null, 0, 0, 7, (DefaultConstructorMarker) null);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof g)) {
            return false;
        }
        g gVar = (g) obj;
        return k.a(this.a, gVar.a) && this.b == gVar.b && this.c == gVar.c;
    }

    public int hashCode() {
        String str = this.a;
        int hashCode = str != null ? str.hashCode() : 0;
        long j = this.c;
        return (((hashCode * 31) + this.b) * 31) + ((int) (j ^ (j >>> 32)));
    }

    @NotNull
    public String toString() {
        return "HttpServerSettings(host=" + this.a + ", port=" + this.b + ", connectionIdleTimeoutSeconds=" + this.c + ")";
    }

    public g(@NotNull String host, int port, long connectionIdleTimeoutSeconds) {
        k.f(host, SerializableCookie.HOST);
        this.a = host;
        this.b = port;
        this.c = connectionIdleTimeoutSeconds;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ g(String str, int i, long j, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? NetworkListener.DEFAULT_NETWORK_HOST : str, (i2 & 2) != 0 ? NetworkListener.DEFAULT_NETWORK_PORT : i, (i2 & 4) != 0 ? 45 : j);
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    public final int c() {
        return this.b;
    }

    public final long a() {
        return this.c;
    }
}
