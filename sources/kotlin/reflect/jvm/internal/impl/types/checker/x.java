package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.r;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.h;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.e;
import kotlin.reflect.jvm.internal.impl.resolve.constants.n;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.l0;
import kotlin.reflect.jvm.internal.impl.types.y;
import org.jetbrains.annotations.NotNull;

/* compiled from: IntersectionType.kt */
public final class x {
    public static final x a = new x();

    /* compiled from: IntersectionType.kt */
    public static final /* synthetic */ class c extends h implements p<b0, b0, Boolean> {
        c(x xVar) {
            super(2, xVar);
        }

        public final String getName() {
            return "isStrictSupertype";
        }

        public final e getOwner() {
            return a0.b(x.class);
        }

        public final String getSignature() {
            return "isStrictSupertype(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)Z";
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Boolean.valueOf(invoke((b0) obj, (b0) obj2));
        }

        public final boolean invoke(@NotNull b0 p1, @NotNull b0 p2) {
            k.f(p1, "p1");
            k.f(p2, "p2");
            return ((x) this.receiver).e(p1, p2);
        }
    }

    /* compiled from: IntersectionType.kt */
    public static final /* synthetic */ class d extends h implements p<b0, b0, Boolean> {
        d(o oVar) {
            super(2, oVar);
        }

        public final String getName() {
            return "equalTypes";
        }

        public final e getOwner() {
            return a0.b(o.class);
        }

        public final String getSignature() {
            return "equalTypes(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)Z";
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Boolean.valueOf(invoke((b0) obj, (b0) obj2));
        }

        public final boolean invoke(@NotNull b0 p1, @NotNull b0 p2) {
            k.f(p1, "p1");
            k.f(p2, "p2");
            return ((o) this.receiver).b(p1, p2);
        }
    }

    private x() {
    }

    @NotNull
    public final i0 c(@NotNull List<? extends i0> types) {
        k.f(types, "types");
        if (types.size() > 1) {
            ArrayList<g1> inputTypes = new ArrayList<>();
            for (i0 type : types) {
                if (type.I0() instanceof kotlin.reflect.jvm.internal.impl.types.a0) {
                    Iterable<b0> $this$mapTo$iv$iv = type.I0().b();
                    k.b($this$mapTo$iv$iv, "type.constructor.supertypes");
                    Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                    for (b0 it : $this$mapTo$iv$iv) {
                        k.b(it, "it");
                        i0 it2 = y.d(it);
                        if (type.J0()) {
                            it2 = it2.P0(true);
                        }
                        destination$iv$iv.add(it2);
                    }
                    inputTypes.addAll(destination$iv$iv);
                } else {
                    inputTypes.add(type);
                }
            }
            a p1 = a.START;
            for (g1 p2 : inputTypes) {
                p1 = p1.combine(p2);
            }
            a resultNullability = p1;
            LinkedHashSet correctNullability = new LinkedHashSet();
            Iterator it3 = inputTypes.iterator();
            while (it3.hasNext()) {
                i0 it4 = (i0) it3.next();
                if (resultNullability == a.NOT_NULL) {
                    it4 = l0.g(it4);
                }
                correctNullability.add(it4);
            }
            return d(correctNullability);
        }
        throw new AssertionError("Size should be at least 2, but it is " + types.size());
    }

    private final i0 d(Set<? extends i0> inputTypes) {
        if (inputTypes.size() == 1) {
            return (i0) kotlin.collections.y.p0(inputTypes);
        }
        kotlin.jvm.functions.a errorMessage = new b(inputTypes);
        Collection filteredEqualTypes = b(inputTypes, new c(this));
        if (!filteredEqualTypes.isEmpty()) {
            i0 it = n.a.b(filteredEqualTypes);
            if (it != null) {
                return it;
            }
            Collection filteredSuperAndEqualTypes = b(filteredEqualTypes, new d(n.b.a()));
            if (!(true ^ filteredSuperAndEqualTypes.isEmpty())) {
                throw new AssertionError(errorMessage.invoke());
            } else if (filteredSuperAndEqualTypes.size() < 2) {
                return (i0) kotlin.collections.y.p0(filteredSuperAndEqualTypes);
            } else {
                return new kotlin.reflect.jvm.internal.impl.types.a0(inputTypes).f();
            }
        } else {
            throw new AssertionError(errorMessage.invoke());
        }
    }

    /* compiled from: IntersectionType.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<String> {
        final /* synthetic */ Set $inputTypes;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(Set set) {
            super(0);
            this.$inputTypes = set;
        }

        @NotNull
        public final String invoke() {
            return "This collections cannot be empty! input types: " + kotlin.collections.y.b0(this.$inputTypes, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (kotlin.jvm.functions.l) null, 63, (Object) null);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v0, resolved type: kotlin.reflect.jvm.internal.impl.types.i0} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0058 A[EDGE_INSN: B:25:0x0058->B:17:0x0058 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.Collection<kotlin.reflect.jvm.internal.impl.types.i0> b(java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.types.i0> r13, kotlin.jvm.functions.p<? super kotlin.reflect.jvm.internal.impl.types.i0, ? super kotlin.reflect.jvm.internal.impl.types.i0, java.lang.Boolean> r14) {
        /*
            r12 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>(r13)
            java.util.Iterator r1 = r0.iterator()
            java.lang.String r2 = "filteredTypes.iterator()"
            kotlin.jvm.internal.k.b(r1, r2)
        L_0x000e:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x005f
            java.lang.Object r2 = r1.next()
            kotlin.reflect.jvm.internal.impl.types.i0 r2 = (kotlin.reflect.jvm.internal.impl.types.i0) r2
            r3 = r0
            r4 = 0
            boolean r5 = r3.isEmpty()
            r6 = 1
            r7 = 0
            if (r5 == 0) goto L_0x0026
            r6 = r7
            goto L_0x0058
        L_0x0026:
            java.util.Iterator r5 = r3.iterator()
        L_0x002a:
            boolean r8 = r5.hasNext()
            if (r8 == 0) goto L_0x0057
            java.lang.Object r8 = r5.next()
            r9 = r8
            kotlin.reflect.jvm.internal.impl.types.i0 r9 = (kotlin.reflect.jvm.internal.impl.types.i0) r9
            r10 = 0
            if (r9 == r2) goto L_0x0053
            java.lang.String r11 = "lower"
            kotlin.jvm.internal.k.b(r9, r11)
            java.lang.String r11 = "upper"
            kotlin.jvm.internal.k.b(r2, r11)
            java.lang.Object r11 = r14.invoke(r9, r2)
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x0053
            r9 = r6
            goto L_0x0054
        L_0x0053:
            r9 = r7
        L_0x0054:
            if (r9 == 0) goto L_0x002a
            goto L_0x0058
        L_0x0057:
            r6 = r7
        L_0x0058:
            r3 = r6
            if (r3 == 0) goto L_0x005e
            r1.remove()
        L_0x005e:
            goto L_0x000e
        L_0x005f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.checker.x.b(java.util.Collection, kotlin.jvm.functions.p):java.util.Collection");
    }

    /* access modifiers changed from: private */
    public final boolean e(b0 subtype, b0 supertype) {
        o $this$with = n.b.a();
        return $this$with.d(subtype, supertype) && !$this$with.d(supertype, subtype);
    }

    /* compiled from: IntersectionType.kt */
    public enum a {
        ;

        @NotNull
        public abstract a combine(@NotNull g1 g1Var);

        /* compiled from: IntersectionType.kt */
        public static final class c extends a {
            c(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, (DefaultConstructorMarker) null);
            }

            @NotNull
            public a combine(@NotNull g1 nextType) {
                k.f(nextType, "nextType");
                return getResultNullability(nextType);
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.types.checker.x$a$a  reason: collision with other inner class name */
        /* compiled from: IntersectionType.kt */
        public static final class C0424a extends a {
            C0424a(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, (DefaultConstructorMarker) null);
            }

            @NotNull
            public a combine(@NotNull g1 nextType) {
                k.f(nextType, "nextType");
                return getResultNullability(nextType);
            }
        }

        /* compiled from: IntersectionType.kt */
        public static final class d extends a {
            d(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, (DefaultConstructorMarker) null);
            }

            @NotNull
            public a combine(@NotNull g1 nextType) {
                k.f(nextType, "nextType");
                a it = getResultNullability(nextType);
                if (it == a.ACCEPT_NULL) {
                    return this;
                }
                return it;
            }
        }

        /* compiled from: IntersectionType.kt */
        public static final class b extends a {
            b(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, (DefaultConstructorMarker) null);
            }

            @NotNull
            public b combine(@NotNull g1 nextType) {
                k.f(nextType, "nextType");
                return this;
            }
        }

        /* access modifiers changed from: protected */
        @NotNull
        public final a getResultNullability(@NotNull g1 $this$resultNullability) {
            k.f($this$resultNullability, "$this$resultNullability");
            if ($this$resultNullability.J0()) {
                return ACCEPT_NULL;
            }
            if (p.a.a($this$resultNullability)) {
                return NOT_NULL;
            }
            return UNKNOWN;
        }
    }
}
