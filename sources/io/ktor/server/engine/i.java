package io.ktor.server.engine;

import androidx.core.app.NotificationCompat;
import io.ktor.request.b;
import io.ktor.request.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BaseApplicationRequest.kt */
public abstract class i implements d {
    @NotNull
    private final b a;
    @NotNull
    private final io.ktor.application.b b;

    public i(@NotNull io.ktor.application.b call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        this.b = call;
        b $this$apply = new b();
        $this$apply.r(d().a().B());
        this.a = $this$apply;
    }

    @NotNull
    public io.ktor.application.b d() {
        return this.b;
    }

    @NotNull
    public b a() {
        return this.a;
    }
}
