package io.ktor.http.cio;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.http.cio.internals.b;
import io.ktor.http.u;
import kotlin.jvm.internal.k;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;

/* compiled from: RequestResponse.kt */
public final class m extends h {
    @NotNull
    private final u f;
    @NotNull
    private final CharSequence q;
    @NotNull
    private final CharSequence x;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public m(@NotNull u method, @NotNull CharSequence uri, @NotNull CharSequence version, @NotNull f headers, @NotNull b builder) {
        super(headers, builder);
        k.f(method, FirebaseAnalytics.Param.METHOD);
        k.f(uri, "uri");
        k.f(version, ConfigUtil.VERSION_FILE);
        k.f(headers, "headers");
        k.f(builder, "builder");
        this.f = method;
        this.q = uri;
        this.x = version;
    }

    @NotNull
    public final u c() {
        return this.f;
    }

    @NotNull
    public final CharSequence g() {
        return this.q;
    }

    @NotNull
    public final CharSequence i() {
        return this.x;
    }
}
