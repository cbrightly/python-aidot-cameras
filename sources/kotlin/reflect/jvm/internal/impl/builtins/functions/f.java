package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.d0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.f0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.p;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.util.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FunctionInvokeDescriptor.kt */
public final class f extends f0 {
    public static final a O4 = new a((DefaultConstructorMarker) null);

    private f(m container, f original, b.a callableKind, boolean isSuspend) {
        super(container, original, g.b.b(), j.g, callableKind, o0.a);
        X0(true);
        Z0(isSuspend);
        Q0(false);
    }

    public /* synthetic */ f(m container, f original, b.a callableKind, boolean isSuspend, DefaultConstructorMarker $constructor_marker) {
        this(container, original, callableKind, isSuspend);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public u C0(@NotNull p.c configuration) {
        w0 it;
        k.f(configuration, "configuration");
        f substituted = (f) super.C0(configuration);
        if (substituted == null) {
            return null;
        }
        List<w0> g = substituted.g();
        k.b(g, "substituted.valueParameters");
        boolean z = false;
        if (!(g instanceof Collection) || !g.isEmpty()) {
            Iterator<T> it2 = g.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = true;
                    break;
                }
                w0 it3 = (w0) it2.next();
                k.b(it3, "it");
                b0 type = it3.getType();
                k.b(type, "it.type");
                if (kotlin.reflect.jvm.internal.impl.builtins.f.c(type) != null) {
                    it = 1;
                    continue;
                } else {
                    it = null;
                    continue;
                }
                if (it != null) {
                    break;
                }
            }
        } else {
            z = true;
        }
        if (z) {
            return substituted;
        }
        Iterable<w0> $this$mapTo$iv$iv = substituted.g();
        k.b($this$mapTo$iv$iv, "substituted.valueParameters");
        List parameterNames = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (w0 it4 : $this$mapTo$iv$iv) {
            k.b(it4, "it");
            b0 type2 = it4.getType();
            k.b(type2, "it.type");
            parameterNames.add(kotlin.reflect.jvm.internal.impl.builtins.f.c(type2));
        }
        return substituted.h1(parameterNames);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public p A0(@NotNull m newOwner, @Nullable u original, @NotNull b.a kind, @Nullable kotlin.reflect.jvm.internal.impl.name.f newName, @NotNull g annotations, @NotNull o0 source) {
        k.f(newOwner, "newOwner");
        k.f(kind, "kind");
        k.f(annotations, "annotations");
        k.f(source, "source");
        return new f(newOwner, (f) original, kind, isSuspend());
    }

    public boolean isExternal() {
        return false;
    }

    public boolean isInline() {
        return false;
    }

    public boolean A() {
        return false;
    }

    private final u h1(List<kotlin.reflect.jvm.internal.impl.name.f> parameterNames) {
        kotlin.reflect.jvm.internal.impl.name.f it;
        int indexShift = g().size() - parameterNames.size();
        boolean z = true;
        if (indexShift == 0 || indexShift == 1) {
            Iterable<w0> $this$mapTo$iv$iv = g();
            k.b($this$mapTo$iv$iv, "valueParameters");
            List arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (w0 it2 : $this$mapTo$iv$iv) {
                k.b(it2, "it");
                kotlin.reflect.jvm.internal.impl.name.f newName = it2.getName();
                k.b(newName, "it.name");
                int parameterIndex = it2.getIndex();
                int nameIndex = parameterIndex - indexShift;
                if (nameIndex >= 0) {
                    kotlin.reflect.jvm.internal.impl.name.f parameterName = parameterNames.get(nameIndex);
                    if (parameterName != null) {
                        newName = parameterName;
                    }
                } else {
                    List<kotlin.reflect.jvm.internal.impl.name.f> list = parameterNames;
                }
                arrayList.add(it2.T(this, newName, parameterIndex));
            }
            List<kotlin.reflect.jvm.internal.impl.name.f> list2 = parameterNames;
            List newValueParameters = arrayList;
            p.c K0 = K0(TypeSubstitutor.a);
            List<kotlin.reflect.jvm.internal.impl.name.f> list3 = parameterNames;
            if (!list3.isEmpty()) {
                Iterator<T> it3 = list3.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        z = false;
                        break;
                    }
                    if (((kotlin.reflect.jvm.internal.impl.name.f) it3.next()) == null) {
                        it = 1;
                        continue;
                    } else {
                        it = null;
                        continue;
                    }
                    if (it != null) {
                        break;
                    }
                }
            } else {
                z = false;
            }
            p.c copyConfiguration = K0.F(z).b(newValueParameters).m(c0());
            k.b(copyConfiguration, "newCopyBuilder(TypeSubst…   .setOriginal(original)");
            u C0 = super.C0(copyConfiguration);
            if (C0 == null) {
                k.n();
            }
            return C0;
        }
        List<kotlin.reflect.jvm.internal.impl.name.f> list4 = parameterNames;
        throw new AssertionError("Assertion failed");
    }

    /* compiled from: FunctionInvokeDescriptor.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final f a(@NotNull b functionClass, boolean isSuspend) {
            k.f(functionClass, "functionClass");
            List<t0> $this$takeWhile$iv = functionClass.o();
            f result = new f(functionClass, (f) null, b.a.DECLARATION, isSuspend, (DefaultConstructorMarker) null);
            l0 F0 = functionClass.F0();
            List g = q.g();
            ArrayList list$iv = new ArrayList();
            for (T next : $this$takeWhile$iv) {
                if (!(((t0) next).y() == h1.IN_VARIANCE)) {
                    break;
                }
                list$iv.add(next);
            }
            Iterable<d0> $this$map$iv = y.J0(list$iv);
            ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
            for (d0 it : $this$map$iv) {
                arrayList.add(f.O4.b(result, it.c(), (t0) it.d()));
                $this$map$iv = $this$map$iv;
            }
            result.J0((l0) null, F0, g, arrayList, ((t0) y.d0($this$takeWhile$iv)).m(), w.ABSTRACT, z0.e);
            result.R0(true);
            return result;
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final kotlin.reflect.jvm.internal.impl.descriptors.w0 b(kotlin.reflect.jvm.internal.impl.builtins.functions.f r16, int r17, kotlin.reflect.jvm.internal.impl.descriptors.t0 r18) {
            /*
                r15 = this;
                kotlin.reflect.jvm.internal.impl.name.f r0 = r18.getName()
                java.lang.String r0 = r0.b()
                java.lang.String r1 = "typeParameter.name.asString()"
                kotlin.jvm.internal.k.b(r0, r1)
                int r1 = r0.hashCode()
                switch(r1) {
                    case 69: goto L_0x0021;
                    case 84: goto L_0x0016;
                    default: goto L_0x0015;
                }
            L_0x0015:
                goto L_0x002d
            L_0x0016:
                java.lang.String r1 = "T"
                boolean r1 = r0.equals(r1)
                if (r1 == 0) goto L_0x002d
                java.lang.String r1 = "instance"
                goto L_0x0036
            L_0x0021:
                java.lang.String r1 = "E"
                boolean r1 = r0.equals(r1)
                if (r1 == 0) goto L_0x002d
                java.lang.String r1 = "receiver"
                goto L_0x0036
            L_0x002d:
                java.lang.String r1 = r0.toLowerCase()
                java.lang.String r2 = "(this as java.lang.String).toLowerCase()"
                kotlin.jvm.internal.k.b(r1, r2)
            L_0x0036:
                kotlin.reflect.jvm.internal.impl.descriptors.impl.k0 r14 = new kotlin.reflect.jvm.internal.impl.descriptors.impl.k0
                r4 = 0
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.g$a r2 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r6 = r2.b()
                kotlin.reflect.jvm.internal.impl.name.f r7 = kotlin.reflect.jvm.internal.impl.name.f.f(r1)
                java.lang.String r2 = "Name.identifier(name)"
                kotlin.jvm.internal.k.b(r7, r2)
                kotlin.reflect.jvm.internal.impl.types.i0 r8 = r18.m()
                java.lang.String r2 = "typeParameter.defaultType"
                kotlin.jvm.internal.k.b(r8, r2)
                r9 = 0
                r10 = 0
                r11 = 0
                r12 = 0
                kotlin.reflect.jvm.internal.impl.descriptors.o0 r13 = kotlin.reflect.jvm.internal.impl.descriptors.o0.a
                java.lang.String r2 = "SourceElement.NO_SOURCE"
                kotlin.jvm.internal.k.b(r13, r2)
                r2 = r14
                r3 = r16
                r5 = r17
                r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
                return r14
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.builtins.functions.f.a.b(kotlin.reflect.jvm.internal.impl.builtins.functions.f, int, kotlin.reflect.jvm.internal.impl.descriptors.t0):kotlin.reflect.jvm.internal.impl.descriptors.w0");
        }
    }
}
