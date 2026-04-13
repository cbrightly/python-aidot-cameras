package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.load.java.a;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: context.kt */
public final class k {
    @NotNull
    private final h a;
    @NotNull
    private final Collection<a.C0356a> b;

    @NotNull
    public final h a() {
        return this.a;
    }

    @NotNull
    public final Collection<a.C0356a> b() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof k)) {
            return false;
        }
        k kVar = (k) obj;
        return kotlin.jvm.internal.k.a(this.a, kVar.a) && kotlin.jvm.internal.k.a(this.b, kVar.b);
    }

    public int hashCode() {
        h hVar = this.a;
        int i = 0;
        int hashCode = (hVar != null ? hVar.hashCode() : 0) * 31;
        Collection<a.C0356a> collection = this.b;
        if (collection != null) {
            i = collection.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "NullabilityQualifierWithApplicability(nullabilityQualifier=" + this.a + ", qualifierApplicabilityTypes=" + this.b + ")";
    }

    public k(@NotNull h nullabilityQualifier, @NotNull Collection<? extends a.C0356a> qualifierApplicabilityTypes) {
        kotlin.jvm.internal.k.f(nullabilityQualifier, "nullabilityQualifier");
        kotlin.jvm.internal.k.f(qualifierApplicabilityTypes, "qualifierApplicabilityTypes");
        this.a = nullabilityQualifier;
        this.b = qualifierApplicabilityTypes;
    }
}
