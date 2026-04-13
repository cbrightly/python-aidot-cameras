package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public abstract class k extends g<x> {
    public static final a b = new a((DefaultConstructorMarker) null);

    public k() {
        super(x.a);
    }

    @NotNull
    /* renamed from: c */
    public x b() {
        throw new UnsupportedOperationException();
    }

    /* compiled from: constantValues.kt */
    public static final class b extends k {
        @NotNull
        private final String c;

        public b(@NotNull String message) {
            kotlin.jvm.internal.k.f(message, "message");
            this.c = message;
        }

        @NotNull
        /* renamed from: d */
        public i0 a(@NotNull y module) {
            kotlin.jvm.internal.k.f(module, "module");
            i0 j = u.j(this.c);
            kotlin.jvm.internal.k.b(j, "ErrorUtils.createErrorType(message)");
            return j;
        }

        @NotNull
        public String toString() {
            return this.c;
        }
    }

    /* compiled from: constantValues.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final k a(@NotNull String message) {
            kotlin.jvm.internal.k.f(message, "message");
            return new b(message);
        }
    }
}
