package zendesk.android.internal;

import android.content.Context;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import zendesk.android.events.internal.a;

/* compiled from: ZendeskModule.kt */
public final class p {
    @NotNull
    private final Context a;
    @NotNull
    private final o0 b;
    @NotNull
    private final g c;

    public p(@NotNull Context context, @NotNull o0 scope, @NotNull g componentConfig) {
        k.e(context, "context");
        k.e(scope, "scope");
        k.e(componentConfig, "componentConfig");
        this.a = context;
        this.b = scope;
        this.c = componentConfig;
    }

    @NotNull
    public final Context b() {
        return this.a;
    }

    @NotNull
    public final g a() {
        return this.c;
    }

    @NotNull
    public final o0 c() {
        return this.b;
    }

    @NotNull
    public final h d() {
        return new h((i0) null, (i0) null, (i0) null, 7, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final a e() {
        return new a((i0) null, 1, (DefaultConstructorMarker) null);
    }
}
