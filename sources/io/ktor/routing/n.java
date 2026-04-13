package io.ktor.routing;

import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import io.ktor.http.e0;
import io.ktor.http.o;
import io.ktor.http.y;
import io.ktor.request.b;
import io.ktor.request.d;
import io.ktor.utils.io.i;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: RoutingApplicationCall.kt */
public final class n implements d {
    @NotNull
    private final m a;
    @NotNull
    private final b b;
    private final /* synthetic */ d c;

    @NotNull
    public e0 b() {
        return this.c.b();
    }

    @NotNull
    public i c() {
        return this.c.c();
    }

    @NotNull
    public y e() {
        return this.c.e();
    }

    @NotNull
    public o getHeaders() {
        return this.c.getHeaders();
    }

    public n(@NotNull m call, @NotNull b pipeline, @NotNull d request) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(pipeline, "pipeline");
        k.f(request, Progress.REQUEST);
        this.c = request;
        this.a = call;
        this.b = pipeline;
    }

    @NotNull
    /* renamed from: f */
    public m d() {
        return this.a;
    }

    @NotNull
    public b a() {
        return this.b;
    }
}
