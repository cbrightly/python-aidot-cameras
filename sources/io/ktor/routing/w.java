package io.ktor.routing;

import com.google.android.libraries.places.api.model.PlaceTypes;
import io.ktor.http.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: RoutingResolve.kt */
public abstract class w {
    @NotNull
    private final i a;

    @NotNull
    public abstract y a();

    private w(i route) {
        this.a = route;
    }

    public /* synthetic */ w(i route, DefaultConstructorMarker $constructor_marker) {
        this(route);
    }

    @NotNull
    public final i b() {
        return this.a;
    }

    /* compiled from: RoutingResolve.kt */
    public static final class b extends w {
        @NotNull
        private final y b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull i route, @NotNull y parameters) {
            super(route, (DefaultConstructorMarker) null);
            k.f(route, PlaceTypes.ROUTE);
            k.f(parameters, "parameters");
            this.b = parameters;
        }

        @NotNull
        public y a() {
            return this.b;
        }

        @NotNull
        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("SUCCESS");
            if (a().isEmpty()) {
                str = "";
            } else {
                str = "; " + a();
            }
            sb.append(str);
            sb.append(" @ ");
            sb.append(b());
            sb.append(')');
            return sb.toString();
        }
    }

    /* compiled from: RoutingResolve.kt */
    public static final class a extends w {
        @NotNull
        private final String b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull i route, @NotNull String reason) {
            super(route, (DefaultConstructorMarker) null);
            k.f(route, PlaceTypes.ROUTE);
            k.f(reason, "reason");
            this.b = reason;
        }

        public /* bridge */ /* synthetic */ y a() {
            return (y) c();
        }

        @NotNull
        public Void c() {
            throw new UnsupportedOperationException("Parameters are available only when routing resolve succeeds");
        }

        @NotNull
        public String toString() {
            return "FAILURE \"" + this.b + "\" @ " + b() + ')';
        }
    }
}
