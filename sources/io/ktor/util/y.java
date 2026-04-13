package io.ktor.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.k;

/* compiled from: StringValues.kt */
public final class y {
    /* access modifiers changed from: private */
    public static final boolean c(Set<? extends Map.Entry<String, ? extends List<String>>> a, Set<? extends Map.Entry<String, ? extends List<String>>> b) {
        return k.a(a, b);
    }

    /* access modifiers changed from: private */
    public static final int d(Set<? extends Map.Entry<String, ? extends List<String>>> entries, int seed) {
        return (seed * 31) + entries.hashCode();
    }
}
