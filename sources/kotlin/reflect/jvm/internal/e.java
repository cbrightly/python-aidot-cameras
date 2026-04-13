package kotlin.reflect.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.collections.u;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.full.IllegalCallableAccessException;
import kotlin.reflect.j;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.n;
import kotlin.reflect.o;
import kotlin.reflect.s;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KCallableImpl.kt */
public abstract class e<R> implements kotlin.reflect.b<R> {
    private final a0.a<List<Annotation>> c;
    private final a0.a<ArrayList<j>> d;
    private final a0.a<w> f;
    private final a0.a<List<x>> q;

    @NotNull
    public abstract kotlin.reflect.jvm.internal.calls.d<?> p();

    @NotNull
    public abstract j q();

    @Nullable
    public abstract kotlin.reflect.jvm.internal.calls.d<?> r();

    @NotNull
    public abstract kotlin.reflect.jvm.internal.impl.descriptors.b s();

    public abstract boolean u();

    public e() {
        a0.a<List<Annotation>> d2 = a0.d(new a(this));
        k.b(d2, "ReflectProperties.lazySo…or.computeAnnotations() }");
        this.c = d2;
        a0.a<ArrayList<j>> d3 = a0.d(new b(this));
        k.b(d3, "ReflectProperties.lazySo…ze()\n        result\n    }");
        this.d = d3;
        a0.a<w> d4 = a0.d(new c(this));
        k.b(d4, "ReflectProperties.lazySo…eturnType\n        }\n    }");
        this.f = d4;
        a0.a<List<x>> d5 = a0.d(new d(this));
        k.b(d5, "ReflectProperties.lazySo…KTypeParameterImpl)\n    }");
        this.q = d5;
    }

    /* compiled from: KCallableImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<List<? extends Annotation>> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(e eVar) {
            super(0);
            this.this$0 = eVar;
        }

        @NotNull
        public final List<Annotation> invoke() {
            return h0.d(this.this$0.s());
        }
    }

    @NotNull
    public List<Annotation> getAnnotations() {
        List<Annotation> c2 = this.c.c();
        k.b(c2, "_annotations()");
        return c2;
    }

    /* compiled from: KCallableImpl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<ArrayList<j>> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(e eVar) {
            super(0);
            this.this$0 = eVar;
        }

        @NotNull
        public final ArrayList<j> invoke() {
            int i;
            kotlin.reflect.jvm.internal.impl.descriptors.b s = this.this$0.s();
            ArrayList<j> arrayList = new ArrayList<>();
            int i2 = 0;
            if (!this.this$0.u()) {
                l0 f = h0.f(s);
                if (f != null) {
                    arrayList.add(new p(this.this$0, 0, j.a.INSTANCE, new C0337b(f)));
                    i = 1;
                } else {
                    i = 0;
                }
                l0 L = s.L();
                if (L != null) {
                    arrayList.add(new p(this.this$0, i, j.a.EXTENSION_RECEIVER, new c(L)));
                    i++;
                }
            } else {
                i = 0;
            }
            List<w0> g = s.g();
            k.b(g, "descriptor.valueParameters");
            int size = g.size();
            while (i2 < size) {
                arrayList.add(new p(this.this$0, i, j.a.VALUE, new d(s, i2)));
                i2++;
                i++;
            }
            if (this.this$0.t() && (s instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.b) && arrayList.size() > 1) {
                u.w(arrayList, new a());
            }
            arrayList.trimToSize();
            return arrayList;
        }

        /* renamed from: kotlin.reflect.jvm.internal.e$b$b  reason: collision with other inner class name */
        /* compiled from: KCallableImpl.kt */
        public static final class C0337b extends l implements kotlin.jvm.functions.a<l0> {
            final /* synthetic */ l0 $instanceReceiver;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0337b(l0 l0Var) {
                super(0);
                this.$instanceReceiver = l0Var;
            }

            @NotNull
            public final l0 invoke() {
                return this.$instanceReceiver;
            }
        }

        /* compiled from: KCallableImpl.kt */
        public static final class c extends l implements kotlin.jvm.functions.a<l0> {
            final /* synthetic */ l0 $extensionReceiver;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(l0 l0Var) {
                super(0);
                this.$extensionReceiver = l0Var;
            }

            @NotNull
            public final l0 invoke() {
                return this.$extensionReceiver;
            }
        }

        /* compiled from: KCallableImpl.kt */
        public static final class d extends l implements kotlin.jvm.functions.a<w0> {
            final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.b $descriptor;
            final /* synthetic */ int $i;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            d(kotlin.reflect.jvm.internal.impl.descriptors.b bVar, int i) {
                super(0);
                this.$descriptor = bVar;
                this.$i = i;
            }

            public final w0 invoke() {
                w0 w0Var = this.$descriptor.g().get(this.$i);
                k.b(w0Var, "descriptor.valueParameters[i]");
                return w0Var;
            }
        }

        /* compiled from: Comparisons.kt */
        public static final class a<T> implements Comparator<T> {
            public final int compare(T a, T b) {
                return kotlin.comparisons.a.c(((j) a).getName(), ((j) b).getName());
            }
        }
    }

    @NotNull
    public List<j> getParameters() {
        ArrayList<j> c2 = this.d.c();
        k.b(c2, "_parameters()");
        return c2;
    }

    /* compiled from: KCallableImpl.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<w> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(e eVar) {
            super(0);
            this.this$0 = eVar;
        }

        /* compiled from: KCallableImpl.kt */
        public static final class a extends l implements kotlin.jvm.functions.a<Type> {
            final /* synthetic */ c this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(c cVar) {
                super(0);
                this.this$0 = cVar;
            }

            @NotNull
            public final Type invoke() {
                Type b = this.this$0.this$0.n();
                return b != null ? b : this.this$0.this$0.p().getReturnType();
            }
        }

        @NotNull
        public final w invoke() {
            b0 returnType = this.this$0.s().getReturnType();
            if (returnType == null) {
                k.n();
            }
            k.b(returnType, "descriptor.returnType!!");
            return new w(returnType, new a(this));
        }
    }

    @NotNull
    public n getReturnType() {
        w c2 = this.f.c();
        k.b(c2, "_returnType()");
        return c2;
    }

    /* compiled from: KCallableImpl.kt */
    public static final class d extends l implements kotlin.jvm.functions.a<List<? extends x>> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(e eVar) {
            super(0);
            this.this$0 = eVar;
        }

        @NotNull
        public final List<x> invoke() {
            Iterable<t0> $this$mapTo$iv$iv = this.this$0.s().getTypeParameters();
            k.b($this$mapTo$iv$iv, "descriptor.typeParameters");
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (t0 p1 : $this$mapTo$iv$iv) {
                arrayList.add(new x(p1));
            }
            return arrayList;
        }
    }

    @NotNull
    public List<o> getTypeParameters() {
        List<x> c2 = this.q.c();
        k.b(c2, "_typeParameters()");
        return c2;
    }

    @Nullable
    public s getVisibility() {
        a1 visibility = s().getVisibility();
        k.b(visibility, "descriptor.visibility");
        return h0.m(visibility);
    }

    public boolean isFinal() {
        return s().p() == w.FINAL;
    }

    public boolean isOpen() {
        return s().p() == w.OPEN;
    }

    public boolean isAbstract() {
        return s().p() == w.ABSTRACT;
    }

    /* access modifiers changed from: protected */
    public final boolean t() {
        return k.a(getName(), "<init>") && q().b().isAnnotation();
    }

    public R call(@NotNull Object... args) {
        k.f(args, "args");
        try {
            return p().call(args);
        } catch (IllegalAccessException e$iv) {
            throw new IllegalCallableAccessException(e$iv);
        }
    }

    public R callBy(@NotNull Map<j, ? extends Object> args) {
        k.f(args, "args");
        return t() ? c(args) : d(args, (kotlin.coroutines.d<?>) null);
    }

    public final R d(@NotNull Map<j, ? extends Object> args, @Nullable kotlin.coroutines.d<?> continuationArgument) {
        Map<j, ? extends Object> map = args;
        kotlin.coroutines.d<?> dVar = continuationArgument;
        k.f(map, "args");
        List parameters = getParameters();
        ArrayList arguments = new ArrayList(parameters.size());
        ArrayList masks = new ArrayList(1);
        boolean anyOptional = false;
        int index = 0;
        int mask = 0;
        for (j parameter : parameters) {
            if (index != 0 && index % 32 == 0) {
                masks.add(Integer.valueOf(mask));
                mask = 0;
            }
            if (map.containsKey(parameter)) {
                arguments.add(map.get(parameter));
            } else if (parameter.m()) {
                arguments.add(e(kotlin.reflect.jvm.d.b(parameter.getType())));
                mask |= 1 << (index % 32);
                anyOptional = true;
            } else {
                throw new IllegalArgumentException("No argument provided for a required parameter: " + parameter);
            }
            if (parameter.h() == j.a.VALUE) {
                index++;
            }
        }
        if (dVar != null) {
            arguments.add(dVar);
        }
        if (!anyOptional) {
            Object[] array = arguments.toArray(new Object[0]);
            if (array != null) {
                return call(Arrays.copyOf(array, array.length));
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        masks.add(Integer.valueOf(mask));
        kotlin.reflect.jvm.internal.calls.d caller = r();
        if (caller != null) {
            arguments.addAll(masks);
            arguments.add((Object) null);
            try {
                Object[] array2 = arguments.toArray(new Object[0]);
                if (array2 != null) {
                    return caller.call(array2);
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            } catch (IllegalAccessException e$iv) {
                throw new IllegalCallableAccessException(e$iv);
            }
        } else {
            throw new y("This callable does not support a default call: " + s());
        }
    }

    private final R c(Map<j, ? extends Object> args) {
        Object obj;
        Iterable<j> $this$mapTo$iv$iv = getParameters();
        Collection arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (j parameter : $this$mapTo$iv$iv) {
            if (args.containsKey(parameter)) {
                obj = args.get(parameter);
                if (obj == null) {
                    throw new IllegalArgumentException("Annotation argument value cannot be null (" + parameter + ')');
                }
            } else if (parameter.m()) {
                obj = null;
            } else {
                throw new IllegalArgumentException("No argument provided for a required parameter: " + parameter);
            }
            arrayList.add(obj);
        }
        Collection arguments = arrayList;
        kotlin.reflect.jvm.internal.calls.d caller = r();
        if (caller != null) {
            try {
                Object[] array = arguments.toArray(new Object[0]);
                if (array != null) {
                    return caller.call(array);
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            } catch (IllegalAccessException e$iv) {
                throw new IllegalCallableAccessException(e$iv);
            }
        } else {
            throw new y("This callable does not support a default call: " + s());
        }
    }

    private final Object e(Type type) {
        if (!(type instanceof Class) || !((Class) type).isPrimitive()) {
            return null;
        }
        if (k.a(type, Boolean.TYPE)) {
            return false;
        }
        if (k.a(type, Character.TYPE)) {
            return Character.valueOf((char) 0);
        }
        if (k.a(type, Byte.TYPE)) {
            return Byte.valueOf((byte) 0);
        }
        if (k.a(type, Short.TYPE)) {
            return Short.valueOf((short) 0);
        }
        if (k.a(type, Integer.TYPE)) {
            return 0;
        }
        if (k.a(type, Float.TYPE)) {
            return Float.valueOf(0.0f);
        }
        if (k.a(type, Long.TYPE)) {
            return 0L;
        }
        if (k.a(type, Double.TYPE)) {
            return Double.valueOf(0.0d);
        }
        if (k.a(type, Void.TYPE)) {
            throw new IllegalStateException("Parameter with void type is illegal");
        }
        throw new UnsupportedOperationException("Unknown primitive: " + type);
    }

    /* access modifiers changed from: private */
    public final Type n() {
        Type[] lowerBounds;
        kotlin.reflect.jvm.internal.impl.descriptors.b s = s();
        if (!(s instanceof kotlin.reflect.jvm.internal.impl.descriptors.u)) {
            s = null;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.u uVar = (kotlin.reflect.jvm.internal.impl.descriptors.u) s;
        if (uVar != null && uVar.isSuspend()) {
            Object f0 = y.f0(p().a());
            if (!(f0 instanceof ParameterizedType)) {
                f0 = null;
            }
            ParameterizedType continuationType = (ParameterizedType) f0;
            if (k.a(continuationType != null ? continuationType.getRawType() : null, kotlin.coroutines.d.class)) {
                Type[] actualTypeArguments = continuationType.getActualTypeArguments();
                k.b(actualTypeArguments, "continuationType.actualTypeArguments");
                Object J = kotlin.collections.l.J(actualTypeArguments);
                if (!(J instanceof WildcardType)) {
                    J = null;
                }
                WildcardType wildcard = (WildcardType) J;
                if (wildcard == null || (lowerBounds = wildcard.getLowerBounds()) == null) {
                    return null;
                }
                return (Type) kotlin.collections.l.u(lowerBounds);
            }
        }
        return null;
    }
}
