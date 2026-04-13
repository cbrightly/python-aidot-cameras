package io.ktor.util;

import java.util.Collections;
import java.util.Set;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: CollectionsJvm.kt */
public final class i {
    @NotNull
    public static final <T> Set<T> a(@NotNull Set<? extends T> $this$unmodifiable) {
        k.f($this$unmodifiable, "$this$unmodifiable");
        Set<T> unmodifiableSet = Collections.unmodifiableSet($this$unmodifiable);
        k.b(unmodifiableSet, "Collections.unmodifiableSet(this)");
        return unmodifiableSet;
    }
}
