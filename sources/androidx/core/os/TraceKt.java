package androidx.core.os;

import kotlin.jvm.functions.a;
import kotlin.jvm.internal.j;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Trace.kt */
public final class TraceKt {
    public static final <T> T trace(@NotNull String sectionName, @NotNull a<? extends T> block) {
        k.e(sectionName, "sectionName");
        k.e(block, "block");
        TraceCompat.beginSection(sectionName);
        try {
            return block.invoke();
        } finally {
            j.b(1);
            TraceCompat.endSection();
            j.a(1);
        }
    }
}
