package okhttp3.internal.http;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.jvm.internal.k;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpMethod.kt */
public final class f {
    public static final f a = new f();

    private f() {
    }

    public final boolean a(@NotNull String method) {
        k.f(method, FirebaseAnalytics.Param.METHOD);
        return k.a(method, Constants.POST) || k.a(method, "PATCH") || k.a(method, "PUT") || k.a(method, "DELETE") || k.a(method, "MOVE");
    }

    public static final boolean e(@NotNull String method) {
        k.f(method, FirebaseAnalytics.Param.METHOD);
        return k.a(method, Constants.POST) || k.a(method, "PUT") || k.a(method, "PATCH") || k.a(method, "PROPPATCH") || k.a(method, "REPORT");
    }

    public static final boolean b(@NotNull String method) {
        k.f(method, FirebaseAnalytics.Param.METHOD);
        return !k.a(method, Constants.GET) && !k.a(method, Constants.HEAD);
    }

    public final boolean d(@NotNull String method) {
        k.f(method, FirebaseAnalytics.Param.METHOD);
        return k.a(method, "PROPFIND");
    }

    public final boolean c(@NotNull String method) {
        k.f(method, FirebaseAnalytics.Param.METHOD);
        return !k.a(method, "PROPFIND");
    }
}
