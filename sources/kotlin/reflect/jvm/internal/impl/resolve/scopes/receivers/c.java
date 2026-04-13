package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImplicitClassReceiver.kt */
public class c implements d, f {
    private final c a;
    @NotNull
    private final e b;
    @NotNull
    private final e c;

    public c(@NotNull e classDescriptor, @Nullable c original) {
        k.f(classDescriptor, "classDescriptor");
        this.c = classDescriptor;
        this.a = original != null ? original : this;
        this.b = classDescriptor;
    }

    @NotNull
    public final e q() {
        return this.c;
    }

    @NotNull
    /* renamed from: b */
    public i0 getType() {
        i0 m = this.c.m();
        k.b(m, "classDescriptor.defaultType");
        return m;
    }

    public boolean equals(@Nullable Object other) {
        e eVar = this.c;
        e eVar2 = null;
        c cVar = (c) (!(other instanceof c) ? null : other);
        if (cVar != null) {
            eVar2 = cVar.c;
        }
        return k.a(eVar, eVar2);
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "Class{" + getType() + '}';
    }
}
