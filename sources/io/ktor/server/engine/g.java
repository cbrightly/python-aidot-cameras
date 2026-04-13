package io.ktor.server.engine;

import io.ktor.application.a;
import io.ktor.application.b;
import io.ktor.http.y;
import io.ktor.util.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BaseApplicationCall.kt */
public abstract class g implements b {
    @NotNull
    private final io.ktor.util.b a = d.b(false, 1, (Object) null);
    @NotNull
    private final a b;

    @NotNull
    public abstract i c();

    @NotNull
    public abstract BaseApplicationResponse d();

    public g(@NotNull a application) {
        k.f(application, "application");
        this.b = application;
    }

    @NotNull
    public final a a() {
        return this.b;
    }

    @NotNull
    public final io.ktor.util.b getAttributes() {
        return this.a;
    }

    @NotNull
    public y getParameters() {
        return c().e();
    }

    public static /* synthetic */ void f(g gVar, BaseApplicationResponse baseApplicationResponse, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                baseApplicationResponse = gVar.d();
            }
            gVar.e(baseApplicationResponse);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: putResponseAttribute");
    }

    /* access modifiers changed from: protected */
    public final void e(@NotNull BaseApplicationResponse response) {
        k.f(response, "response");
        this.a.b(BaseApplicationResponse.b.a(), response);
    }
}
