package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: specialBuiltinMembers.kt */
public final class u {
    @NotNull
    private final f a;
    @NotNull
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof u)) {
            return false;
        }
        u uVar = (u) obj;
        return k.a(this.a, uVar.a) && k.a(this.b, uVar.b);
    }

    public int hashCode() {
        f fVar = this.a;
        int i = 0;
        int hashCode = (fVar != null ? fVar.hashCode() : 0) * 31;
        String str = this.b;
        if (str != null) {
            i = str.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "NameAndSignature(name=" + this.a + ", signature=" + this.b + ")";
    }

    public u(@NotNull f name, @NotNull String signature) {
        k.f(name, "name");
        k.f(signature, "signature");
        this.a = name;
        this.b = signature;
    }

    @NotNull
    public final f a() {
        return this.a;
    }

    @NotNull
    public final String b() {
        return this.b;
    }
}
