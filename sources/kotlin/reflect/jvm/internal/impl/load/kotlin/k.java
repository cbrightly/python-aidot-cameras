package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: methodSignatureMapping.kt */
public abstract class k {
    private k() {
    }

    public /* synthetic */ k(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    /* compiled from: methodSignatureMapping.kt */
    public static final class c extends k {
        @Nullable
        private final d a;

        public c(@Nullable d jvmPrimitiveType) {
            super((DefaultConstructorMarker) null);
            this.a = jvmPrimitiveType;
        }

        @Nullable
        public final d a() {
            return this.a;
        }
    }

    /* compiled from: methodSignatureMapping.kt */
    public static final class b extends k {
        @NotNull
        private final String a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull String internalName) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.f(internalName, "internalName");
            this.a = internalName;
        }

        @NotNull
        public final String a() {
            return this.a;
        }
    }

    /* compiled from: methodSignatureMapping.kt */
    public static final class a extends k {
        @NotNull
        private final k a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull k elementType) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.f(elementType, "elementType");
            this.a = elementType;
        }

        @NotNull
        public final k a() {
            return this.a;
        }
    }

    @NotNull
    public String toString() {
        return m.a.c(this);
    }
}
