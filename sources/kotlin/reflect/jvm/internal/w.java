package kotlin.reflect.jvm.internal;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.m;
import kotlin.reflect.d;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.k;
import kotlin.reflect.n;
import kotlin.reflect.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: KTypeImpl.kt */
public final class w implements n {
    static final /* synthetic */ k[] c;
    @NotNull
    private final a0.a d;
    @Nullable
    private final a0.a f = a0.d(new b(this));
    @NotNull
    private final a0.a q = a0.d(new a(this));
    @NotNull
    private final b0 x;

    static {
        Class<w> cls = w.class;
        c = new k[]{kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "javaType", "getJavaType$kotlin_reflection()Ljava/lang/reflect/Type;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "classifier", "getClassifier()Lkotlin/reflect/KClassifier;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "arguments", "getArguments()Ljava/util/List;"))};
    }

    @Nullable
    public d a() {
        return (d) this.f.b(this, c[1]);
    }

    @NotNull
    public final Type d() {
        return (Type) this.d.b(this, c[0]);
    }

    public w(@NotNull b0 type, @NotNull kotlin.jvm.functions.a<? extends Type> computeJavaType) {
        kotlin.jvm.internal.k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        kotlin.jvm.internal.k.f(computeJavaType, "computeJavaType");
        this.x = type;
        this.d = a0.d(computeJavaType);
    }

    @NotNull
    public final b0 e() {
        return this.x;
    }

    /* compiled from: KTypeImpl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<d> {
        final /* synthetic */ w this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(w wVar) {
            super(0);
            this.this$0 = wVar;
        }

        @Nullable
        public final d invoke() {
            w wVar = this.this$0;
            return wVar.c(wVar.e());
        }
    }

    /* access modifiers changed from: private */
    public final d c(b0 type) {
        b0 argument;
        h descriptor = type.I0().c();
        if (descriptor instanceof e) {
            Class jClass = h0.l((e) descriptor);
            if (jClass == null) {
                return null;
            }
            if (jClass.isArray()) {
                w0 w0Var = (w0) y.s0(type.H0());
                if (w0Var == null || (argument = w0Var.getType()) == null) {
                    return new g(jClass);
                }
                kotlin.jvm.internal.k.b(argument, "type.arguments.singleOrN…return KClassImpl(jClass)");
                d elementClassifier = c(argument);
                if (elementClassifier != null) {
                    return new g(kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.a(kotlin.jvm.a.b(kotlin.reflect.jvm.b.a(elementClassifier))));
                }
                throw new y("Cannot determine classifier for array element type: " + this);
            } else if (c1.l(type)) {
                return new g(jClass);
            } else {
                Class f2 = kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.f(jClass);
                if (f2 == null) {
                    f2 = jClass;
                }
                return new g(f2);
            }
        } else if (descriptor instanceof t0) {
            return new x((t0) descriptor);
        } else {
            if (!(descriptor instanceof s0)) {
                return null;
            }
            throw new m("An operation is not implemented: " + "Type alias classifiers are not yet supported");
        }
    }

    /* compiled from: KTypeImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<List<? extends p>> {
        final /* synthetic */ w this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(w wVar) {
            super(0);
            this.this$0 = wVar;
        }

        @NotNull
        public final List<p> invoke() {
            Iterable $this$mapIndexed$iv;
            List typeArguments;
            p pVar;
            List typeArguments2 = this.this$0.e().H0();
            if (typeArguments2.isEmpty()) {
                return q.g();
            }
            g parameterizedTypeArguments = i.a(kotlin.k.PUBLICATION, new b(this));
            Iterable $this$mapIndexed$iv2 = typeArguments2;
            ArrayList arrayList = new ArrayList(r.r($this$mapIndexed$iv2, 10));
            int index$iv$iv = 0;
            for (T next : $this$mapIndexed$iv2) {
                int index$iv$iv2 = index$iv$iv + 1;
                if (index$iv$iv < 0) {
                    q.q();
                }
                w0 typeProjection = (w0) next;
                if (typeProjection.b()) {
                    pVar = p.b.c();
                    typeArguments = typeArguments2;
                    $this$mapIndexed$iv = $this$mapIndexed$iv2;
                } else {
                    b0 type = typeProjection.getType();
                    typeArguments = typeArguments2;
                    kotlin.jvm.internal.k.b(type, "typeProjection.type");
                    $this$mapIndexed$iv = $this$mapIndexed$iv2;
                    w type2 = new w(type, new C0437a(index$iv$iv, this, parameterizedTypeArguments, (k) null));
                    switch (v.a[typeProjection.c().ordinal()]) {
                        case 1:
                            pVar = p.b.d(type2);
                            break;
                        case 2:
                            pVar = p.b.a(type2);
                            break;
                        case 3:
                            pVar = p.b.b(type2);
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                }
                arrayList.add(pVar);
                index$iv$iv = index$iv$iv2;
                typeArguments2 = typeArguments;
                $this$mapIndexed$iv2 = $this$mapIndexed$iv;
            }
            return arrayList;
        }

        /* compiled from: KTypeImpl.kt */
        public static final class b extends l implements kotlin.jvm.functions.a<List<? extends Type>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<Type> invoke() {
                return kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.e(this.this$0.this$0.d());
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.w$a$a  reason: collision with other inner class name */
        /* compiled from: KTypeImpl.kt */
        public static final class C0437a extends l implements kotlin.jvm.functions.a<Type> {
            final /* synthetic */ int $i;
            final /* synthetic */ g $parameterizedTypeArguments$inlined;
            final /* synthetic */ k $parameterizedTypeArguments$metadata$inlined;
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0437a(int i, a aVar, g gVar, k kVar) {
                super(0);
                this.$i = i;
                this.this$0 = aVar;
                this.$parameterizedTypeArguments$inlined = gVar;
                this.$parameterizedTypeArguments$metadata$inlined = kVar;
            }

            @NotNull
            public final Type invoke() {
                Type argument;
                Type javaType = this.this$0.this$0.d();
                if (javaType instanceof Class) {
                    Class componentType = ((Class) javaType).isArray() ? ((Class) javaType).getComponentType() : Object.class;
                    kotlin.jvm.internal.k.b(componentType, "if (javaType.isArray) ja…Type else Any::class.java");
                    return componentType;
                } else if (javaType instanceof GenericArrayType) {
                    if (this.$i == 0) {
                        Type genericComponentType = ((GenericArrayType) javaType).getGenericComponentType();
                        kotlin.jvm.internal.k.b(genericComponentType, "javaType.genericComponentType");
                        return genericComponentType;
                    }
                    throw new y("Array type has been queried for a non-0th argument: " + this.this$0.this$0);
                } else if (javaType instanceof ParameterizedType) {
                    Type argument2 = (Type) ((List) this.$parameterizedTypeArguments$inlined.getValue()).get(this.$i);
                    if (!(argument2 instanceof WildcardType)) {
                        argument = argument2;
                    } else {
                        Type[] lowerBounds = ((WildcardType) argument2).getLowerBounds();
                        kotlin.jvm.internal.k.b(lowerBounds, "argument.lowerBounds");
                        argument = (Type) kotlin.collections.l.v(lowerBounds);
                        if (argument == null) {
                            Type[] upperBounds = ((WildcardType) argument2).getUpperBounds();
                            kotlin.jvm.internal.k.b(upperBounds, "argument.upperBounds");
                            argument = (Type) kotlin.collections.l.u(upperBounds);
                        }
                    }
                    kotlin.jvm.internal.k.b(argument, "if (argument !is Wildcar…ument.upperBounds.first()");
                    return argument;
                } else {
                    throw new y("Non-generic type has been queried for arguments: " + this.this$0.this$0);
                }
            }
        }
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof w) && kotlin.jvm.internal.k.a(this.x, ((w) other).x);
    }

    public int hashCode() {
        return this.x.hashCode();
    }

    @NotNull
    public String toString() {
        return d0.b.h(this.x);
    }
}
