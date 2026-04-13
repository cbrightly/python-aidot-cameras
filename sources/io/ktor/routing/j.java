package io.ktor.routing;

import org.jetbrains.annotations.NotNull;

/* compiled from: RouteSelector.kt */
public abstract class j {
    private final double a;

    @NotNull
    public abstract k a(@NotNull v vVar, int i);

    public j(double quality) {
        this.a = quality;
    }

    public final double b() {
        return this.a;
    }
}
