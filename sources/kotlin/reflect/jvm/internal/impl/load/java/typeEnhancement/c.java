package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: typeEnhancement.kt */
public final class c<T> {
    private final T a;
    @Nullable
    private final g b;

    public final T a() {
        return this.a;
    }

    @Nullable
    public final g b() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return k.a(this.a, cVar.a) && k.a(this.b, cVar.b);
    }

    public int hashCode() {
        T t = this.a;
        int i = 0;
        int hashCode = (t != null ? t.hashCode() : 0) * 31;
        g gVar = this.b;
        if (gVar != null) {
            i = gVar.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "EnhancementResult(result=" + this.a + ", enhancementAnnotations=" + this.b + ")";
    }

    public c(T result, @Nullable g enhancementAnnotations) {
        this.a = result;
        this.b = enhancementAnnotations;
    }
}
