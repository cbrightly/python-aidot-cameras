package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.calls.d;
import kotlin.reflect.jvm.internal.calls.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: InternalUnderlyingValOfInlineClass.kt */
public abstract class i implements d<Method> {
    @NotNull
    private final Type a;
    private final Method b;
    @NotNull
    private final List<Type> c;

    private i(Method unboxMethod, List<? extends Type> parameterTypes) {
        this.b = unboxMethod;
        this.c = parameterTypes;
        Class<?> returnType = unboxMethod.getReturnType();
        k.b(returnType, "unboxMethod.returnType");
        this.a = returnType;
    }

    public /* synthetic */ i(Method unboxMethod, List parameterTypes, DefaultConstructorMarker $constructor_marker) {
        this(unboxMethod, parameterTypes);
    }

    public void d(@NotNull Object[] args) {
        k.f(args, "args");
        d.a.a(this, args);
    }

    @NotNull
    public final List<Type> a() {
        return this.c;
    }

    @Nullable
    /* renamed from: e */
    public final Method b() {
        return null;
    }

    @NotNull
    public final Type getReturnType() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final Object c(@Nullable Object instance, @NotNull Object[] args) {
        k.f(args, "args");
        return this.b.invoke(instance, Arrays.copyOf(args, args.length));
    }

    /* compiled from: InternalUnderlyingValOfInlineClass.kt */
    public static final class b extends i {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull Method unboxMethod) {
            super(unboxMethod, p.b(unboxMethod.getDeclaringClass()), (DefaultConstructorMarker) null);
            k.f(unboxMethod, "unboxMethod");
        }

        @Nullable
        public Object call(@NotNull Object[] args) {
            Object[] objArr;
            k.f(args, "args");
            d(args);
            Object obj = args[0];
            e.d dVar = e.a;
            Object[] $this$dropFirst$iv = args;
            if ($this$dropFirst$iv.length <= 1) {
                objArr = new Object[0];
            } else {
                objArr = kotlin.collections.k.i($this$dropFirst$iv, 1, $this$dropFirst$iv.length);
                if (objArr == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            }
            return c(obj, objArr);
        }
    }

    /* compiled from: InternalUnderlyingValOfInlineClass.kt */
    public static final class a extends i implements c {
        private final Object d;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull Method unboxMethod, @Nullable Object boundReceiver) {
            super(unboxMethod, q.g(), (DefaultConstructorMarker) null);
            k.f(unboxMethod, "unboxMethod");
            this.d = boundReceiver;
        }

        @Nullable
        public Object call(@NotNull Object[] args) {
            k.f(args, "args");
            d(args);
            return c(this.d, args);
        }
    }
}
