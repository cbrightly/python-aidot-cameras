package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.EnumMap;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.a;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.e;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: context.kt */
public final class d {
    @NotNull
    private final EnumMap<a.C0356a, h> a;

    public d(@NotNull EnumMap<a.C0356a, h> nullabilityQualifiers) {
        k.f(nullabilityQualifiers, "nullabilityQualifiers");
        this.a = nullabilityQualifiers;
    }

    @NotNull
    public final EnumMap<a.C0356a, h> b() {
        return this.a;
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d a(@Nullable a.C0356a applicabilityType) {
        h nullabilityQualifierWithMigrationStatus = this.a.get(applicabilityType);
        if (nullabilityQualifierWithMigrationStatus == null) {
            return null;
        }
        k.b(nullabilityQualifierWithMigrationStatus, "nullabilityQualifiers[ap…ilityType] ?: return null");
        return new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d(nullabilityQualifierWithMigrationStatus.c(), (e) null, false, nullabilityQualifierWithMigrationStatus.d());
    }
}
