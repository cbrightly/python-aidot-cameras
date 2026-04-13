package io.ktor.request;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.b;
import kotlin.reflect.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationReceiveFunctions.kt */
public final class c {
    @NotNull
    private final n a;
    @NotNull
    private final Object b;
    private final boolean c;

    public c(@NotNull n typeInfo, @NotNull Object value, boolean reusableValue) {
        k.f(typeInfo, "typeInfo");
        k.f(value, "value");
        this.a = typeInfo;
        this.b = value;
        this.c = reusableValue;
    }

    @NotNull
    public final n b() {
        return this.a;
    }

    @NotNull
    public final Object c() {
        return this.b;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ c(n nVar, Object obj, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(nVar, obj, (i & 4) != 0 ? false : z);
    }

    @NotNull
    public final kotlin.reflect.c<?> a() {
        return b.b(this.a);
    }
}
