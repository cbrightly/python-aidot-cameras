package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.ranges.i;
import kotlin.ranges.n;
import kotlin.reflect.jvm.internal.calls.e;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.l;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.resolve.e;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.y;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: InlineClassAwareCaller.kt */
public final class g<M extends Member> implements d<M> {
    private final a a;
    private final d<M> b;
    private final boolean c;

    public g(@NotNull b descriptor, @NotNull d<? extends M> caller, boolean isDefault) {
        a aVar;
        Method method;
        b bVar = descriptor;
        d<? extends M> dVar = caller;
        k.f(bVar, "descriptor");
        k.f(dVar, "caller");
        this.b = dVar;
        this.c = isDefault;
        b0 returnType = descriptor.getReturnType();
        if (returnType == null) {
            k.n();
        }
        k.b(returnType, "descriptor.returnType!!");
        Class<?> i = h.i(returnType);
        Method box = i != null ? h.d(i, bVar) : null;
        if (e.a(descriptor)) {
            aVar = new a(i.y.a(), new Method[0], box);
        } else {
            d<M> dVar2 = this.b;
            int i2 = -1;
            if (!(dVar2 instanceof e.h.c)) {
                if (bVar instanceof l) {
                    if (!(dVar2 instanceof c)) {
                        i2 = 0;
                    }
                } else if (descriptor.I() == null || (this.b instanceof c)) {
                    i2 = 0;
                } else {
                    m b2 = descriptor.b();
                    k.b(b2, "descriptor.containingDeclaration");
                    i2 = kotlin.reflect.jvm.internal.impl.resolve.e.b(b2) ? 0 : 1;
                }
            }
            int shift = i2;
            int extraArgumentsTail = this.c ? 2 : 0;
            List arrayList = new ArrayList();
            ArrayList kotlinParameterTypes = arrayList;
            l0 L = descriptor.L();
            b0 extensionReceiverType = L != null ? L.getType() : null;
            if (extensionReceiverType != null) {
                kotlinParameterTypes.add(extensionReceiverType);
            } else if (bVar instanceof l) {
                kotlin.reflect.jvm.internal.impl.descriptors.e constructedClass = ((l) bVar).X();
                k.b(constructedClass, "descriptor.constructedClass");
                if (constructedClass.x()) {
                    m b3 = constructedClass.b();
                    if (b3 != null) {
                        kotlinParameterTypes.add(((kotlin.reflect.jvm.internal.impl.descriptors.e) b3).m());
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    }
                }
            } else {
                m containingDeclaration = descriptor.b();
                k.b(containingDeclaration, "descriptor.containingDeclaration");
                if ((containingDeclaration instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) && ((kotlin.reflect.jvm.internal.impl.descriptors.e) containingDeclaration).isInline()) {
                    kotlinParameterTypes.add(((kotlin.reflect.jvm.internal.impl.descriptors.e) containingDeclaration).m());
                }
            }
            Iterable<w0> $this$mapTo$iv = descriptor.g();
            k.b($this$mapTo$iv, "descriptor.valueParameters");
            for (w0 p1 : $this$mapTo$iv) {
                kotlinParameterTypes.add(p1.getType());
            }
            List kotlinParameterTypes2 = arrayList;
            int expectedArgsSize = kotlinParameterTypes2.size() + shift + extraArgumentsTail;
            if (f.a(this) == expectedArgsSize) {
                int i3 = 0;
                i argumentRange = n.l(Math.max(shift, 0), kotlinParameterTypes2.size() + shift);
                Method[] unbox = new Method[expectedArgsSize];
                while (i3 < expectedArgsSize) {
                    int i4 = i3;
                    if (argumentRange.h(i4)) {
                        Class<?> i5 = h.i((b0) kotlinParameterTypes2.get(i4 - shift));
                        method = i5 != null ? h.f(i5, bVar) : null;
                    } else {
                        method = null;
                    }
                    unbox[i3] = method;
                    i3++;
                    d<? extends M> dVar3 = caller;
                }
                aVar = new a(argumentRange, unbox, box);
            } else {
                throw new y("Inconsistent number of parameters in the descriptor and Java reflection object: " + f.a(this) + " != " + expectedArgsSize + 10 + "Calling: " + bVar + 10 + "Parameter types: " + a() + ")\n" + "Default: " + this.c);
            }
        }
        this.a = aVar;
    }

    public M b() {
        return this.b.b();
    }

    @NotNull
    public Type getReturnType() {
        return this.b.getReturnType();
    }

    @NotNull
    public List<Type> a() {
        return this.b.a();
    }

    /* compiled from: InlineClassAwareCaller.kt */
    public static final class a {
        @NotNull
        private final i a;
        @NotNull
        private final Method[] b;
        @Nullable
        private final Method c;

        public a(@NotNull i argumentRange, @NotNull Method[] unbox, @Nullable Method box) {
            k.f(argumentRange, "argumentRange");
            k.f(unbox, "unbox");
            this.a = argumentRange;
            this.b = unbox;
            this.c = box;
        }

        @NotNull
        public final i a() {
            return this.a;
        }

        @NotNull
        public final Method[] b() {
            return this.b;
        }

        @Nullable
        public final Method c() {
            return this.c;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004a, code lost:
        r5 = r0.invoke((java.lang.Object) null, new java.lang.Object[]{r4});
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object call(@org.jetbrains.annotations.NotNull java.lang.Object[] r11) {
        /*
            r10 = this;
            java.lang.String r0 = "args"
            kotlin.jvm.internal.k.f(r11, r0)
            kotlin.reflect.jvm.internal.calls.g$a r0 = r10.a
            kotlin.ranges.i r1 = r0.a()
            java.lang.reflect.Method[] r2 = r0.b()
            java.lang.reflect.Method r0 = r0.c()
            int r3 = r11.length
            java.lang.Object[] r3 = java.util.Arrays.copyOf(r11, r3)
            java.lang.String r4 = "java.util.Arrays.copyOf(this, size)"
            kotlin.jvm.internal.k.b(r3, r4)
            if (r3 == 0) goto L_0x0059
            int r4 = r1.a()
            int r5 = r1.b()
            r6 = 0
            if (r4 > r5) goto L_0x0042
        L_0x002a:
            r7 = r2[r4]
            r8 = r11[r4]
            if (r7 == 0) goto L_0x003a
            if (r8 == 0) goto L_0x003a
            java.lang.Object[] r9 = new java.lang.Object[r6]
            java.lang.Object r9 = r7.invoke(r8, r9)
            goto L_0x003b
        L_0x003a:
            r9 = r8
        L_0x003b:
            r3[r4] = r9
            if (r4 == r5) goto L_0x0042
            int r4 = r4 + 1
            goto L_0x002a
        L_0x0042:
            kotlin.reflect.jvm.internal.calls.d<M> r4 = r10.b
            java.lang.Object r4 = r4.call(r3)
            if (r0 == 0) goto L_0x0057
            r5 = 0
            r7 = 1
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r7[r6] = r4
            java.lang.Object r5 = r0.invoke(r5, r7)
            if (r5 == 0) goto L_0x0057
            goto L_0x0058
        L_0x0057:
            r5 = r4
        L_0x0058:
            return r5
        L_0x0059:
            kotlin.TypeCastException r3 = new kotlin.TypeCastException
            java.lang.String r4 = "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.calls.g.call(java.lang.Object[]):java.lang.Object");
    }
}
