package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.c;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.a;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MemberSignature.kt */
public final class s {
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            return (obj instanceof s) && k.a(this.b, ((s) obj).b);
        }
        return true;
    }

    public int hashCode() {
        String str = this.b;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    @NotNull
    public String toString() {
        return "MemberSignature(signature=" + this.b + ")";
    }

    /* compiled from: MemberSignature.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final s c(@NotNull c nameResolver, @NotNull a.c signature) {
            k.f(nameResolver, "nameResolver");
            k.f(signature, "signature");
            return d(nameResolver.getString(signature.getName()), nameResolver.getString(signature.getDesc()));
        }

        @NotNull
        public final s d(@NotNull String name, @NotNull String desc) {
            k.f(name, "name");
            k.f(desc, "desc");
            return new s(name + desc, (DefaultConstructorMarker) null);
        }

        @NotNull
        public final s a(@NotNull String name, @NotNull String desc) {
            k.f(name, "name");
            k.f(desc, "desc");
            return new s(name + '#' + desc, (DefaultConstructorMarker) null);
        }

        @NotNull
        public final s b(@NotNull e signature) {
            k.f(signature, "signature");
            if (signature instanceof e.b) {
                return d(signature.c(), signature.b());
            }
            if (signature instanceof e.a) {
                return a(signature.c(), signature.b());
            }
            throw new NoWhenBranchMatchedException();
        }

        @NotNull
        public final s e(@NotNull s signature, int index) {
            k.f(signature, "signature");
            return new s(signature.a() + '@' + index, (DefaultConstructorMarker) null);
        }
    }

    private s(String signature) {
        this.b = signature;
    }

    public /* synthetic */ s(String signature, DefaultConstructorMarker $constructor_marker) {
        this(signature);
    }

    @NotNull
    public final String a() {
        return this.b;
    }
}
