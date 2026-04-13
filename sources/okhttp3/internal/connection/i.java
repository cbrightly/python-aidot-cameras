package okhttp3.internal.connection;

import com.google.android.libraries.places.api.model.PlaceTypes;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.k;
import okhttp3.f0;
import org.jetbrains.annotations.NotNull;

/* compiled from: RouteDatabase.kt */
public final class i {
    private final Set<f0> a = new LinkedHashSet();

    public final synchronized void b(@NotNull f0 failedRoute) {
        k.f(failedRoute, "failedRoute");
        this.a.add(failedRoute);
    }

    public final synchronized void a(@NotNull f0 route) {
        k.f(route, PlaceTypes.ROUTE);
        this.a.remove(route);
    }

    public final synchronized boolean c(@NotNull f0 route) {
        k.f(route, PlaceTypes.ROUTE);
        return this.a.contains(route);
    }
}
