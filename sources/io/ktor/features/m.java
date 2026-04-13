package io.ktor.features;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.http.e0;
import io.ktor.http.u;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.o;
import kotlin.reflect.k;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* compiled from: OriginConnectionPoint.kt */
public final class m implements e0 {
    static final /* synthetic */ k[] a;
    @NotNull
    private final a b;
    @NotNull
    private final a c;
    @NotNull
    private final a d;
    @NotNull
    private final a e;
    @NotNull
    private final a f;

    static {
        Class<m> cls = m.class;
        a = new k[]{a0.f(new o(a0.b(cls), ConfigUtil.VERSION_FILE, "getVersion()Ljava/lang/String;")), a0.f(new o(a0.b(cls), "uri", "getUri()Ljava/lang/String;")), a0.f(new o(a0.b(cls), FirebaseAnalytics.Param.METHOD, "getMethod()Lio/ktor/http/HttpMethod;")), a0.f(new o(a0.b(cls), "scheme", "getScheme()Ljava/lang/String;")), a0.f(new o(a0.b(cls), SerializableCookie.HOST, "getHost()Ljava/lang/String;")), a0.f(new o(a0.b(cls), IjkMediaPlayer.OnNativeInvokeListener.ARG_PORT, "getPort()I")), a0.f(new o(a0.b(cls), "remoteHost", "getRemoteHost()Ljava/lang/String;"))};
    }

    public int a() {
        this.f.a(this, a[5]);
        throw null;
    }

    @NotNull
    public String b() {
        this.e.a(this, a[4]);
        throw null;
    }

    @NotNull
    public String c() {
        this.d.a(this, a[3]);
        throw null;
    }

    @NotNull
    public u getMethod() {
        this.c.a(this, a[2]);
        throw null;
    }

    @NotNull
    public String getUri() {
        this.b.a(this, a[1]);
        throw null;
    }
}
