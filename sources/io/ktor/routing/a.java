package io.ktor.routing;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.http.u;
import io.ktor.request.e;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RouteSelector.kt */
public final class a extends j {
    @NotNull
    private final u b;

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            return (obj instanceof a) && k.a(this.b, ((a) obj).b);
        }
        return true;
    }

    public int hashCode() {
        u uVar = this.b;
        if (uVar != null) {
            return uVar.hashCode();
        }
        return 0;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a(@NotNull u method) {
        super(0.8d);
        k.f(method, FirebaseAnalytics.Param.METHOD);
        this.b = method;
    }

    @NotNull
    public k a(@NotNull v context, int segmentIndex) {
        k.f(context, "context");
        if (k.a(e.d(context.a().getRequest()), this.b)) {
            return k.f.a();
        }
        return k.f.c();
    }

    @NotNull
    public String toString() {
        return "(method:" + this.b.i() + ')';
    }
}
