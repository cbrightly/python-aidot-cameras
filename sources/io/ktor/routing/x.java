package io.ktor.routing;

import androidx.core.app.NotificationCompat;
import com.google.android.libraries.places.api.model.PlaceTypes;
import io.ktor.application.b;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: RoutingResolveTrace.kt */
public final class x {
    private final z<y> a = new z<>();
    private y b;
    @NotNull
    private final b c;
    @NotNull
    private final List<String> d;

    public x(@NotNull b call, @NotNull List<String> segments) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(segments, "segments");
        this.c = call;
        this.d = segments;
    }

    private final void c(y entry) {
        if (this.a.a()) {
            this.b = entry;
        } else {
            this.a.b().a(entry);
        }
    }

    public final void a(@NotNull i route, int segmentIndex) {
        k.f(route, PlaceTypes.ROUTE);
        this.a.d(new y(route, segmentIndex, (w) null, 4, (DefaultConstructorMarker) null));
    }

    public final void b(@NotNull i route, int segmentIndex, @NotNull w result) {
        k.f(route, PlaceTypes.ROUTE);
        k.f(result, "result");
        y entry = this.a.c();
        if (k.a(entry.b(), route)) {
            if (entry.c() == segmentIndex) {
                entry.d(result);
                c(entry);
                return;
            }
            throw new IllegalArgumentException("end should be called for the same segmentIndex as begin".toString());
        }
        throw new IllegalArgumentException("end should be called for the same route as begin".toString());
    }

    public final void d(@NotNull i route, int segmentIndex, @NotNull w result) {
        k.f(route, PlaceTypes.ROUTE);
        k.f(result, "result");
        c(new y(route, segmentIndex, result));
    }

    @NotNull
    public String toString() {
        return "Trace for " + this.d;
    }
}
