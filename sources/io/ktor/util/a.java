package io.ktor.util;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Attributes.kt */
public final class a<T> {
    @NotNull
    private final String a;

    public a(@NotNull String name) {
        k.f(name, "name");
        this.a = name;
    }

    @NotNull
    public final String a() {
        return this.a;
    }

    @NotNull
    public String toString() {
        if (this.a.length() == 0) {
            return super.toString();
        }
        return "AttributeKey: " + this.a;
    }
}
