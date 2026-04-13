package io.ktor.routing;

import androidx.core.app.NotificationCompat;
import io.ktor.http.v;
import io.ktor.response.a;
import io.ktor.response.d;
import io.ktor.response.f;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RoutingApplicationCall.kt */
public final class o implements a {
    @NotNull
    private final m a;
    @NotNull
    private final d b;
    private final /* synthetic */ a c;

    @Nullable
    public v b() {
        return this.c.b();
    }

    @NotNull
    public f getHeaders() {
        return this.c.getHeaders();
    }

    public o(@NotNull m call, @NotNull d pipeline, @NotNull a response) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(pipeline, "pipeline");
        k.f(response, "response");
        this.c = response;
        this.a = call;
        this.b = pipeline;
    }

    @NotNull
    public d a() {
        return this.b;
    }
}
