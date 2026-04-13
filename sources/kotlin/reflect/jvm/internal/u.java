package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.calls.e;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.i;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KPropertyImpl.kt */
public final class u {
    @Nullable
    public static final Object d(@NotNull t.a<?, ?> $this$boundReceiver) {
        k.f($this$boundReceiver, "$this$boundReceiver");
        return $this$boundReceiver.w().w();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0131  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final kotlin.reflect.jvm.internal.calls.d<?> c(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.t.a<?, ?> r7, boolean r8) {
        /*
            kotlin.reflect.jvm.internal.j$a r0 = kotlin.reflect.jvm.internal.j.f
            kotlin.text.j r0 = r0.a()
            kotlin.reflect.jvm.internal.t r1 = r7.w()
            java.lang.String r1 = r1.B()
            boolean r0 = r0.matches(r1)
            if (r0 == 0) goto L_0x0017
            kotlin.reflect.jvm.internal.calls.j r7 = kotlin.reflect.jvm.internal.calls.j.a
            return r7
        L_0x0017:
            kotlin.reflect.jvm.internal.u$a r0 = new kotlin.reflect.jvm.internal.u$a
            r0.<init>(r7)
            kotlin.reflect.jvm.internal.u$b r1 = new kotlin.reflect.jvm.internal.u$b
            r1.<init>(r7)
            kotlin.reflect.jvm.internal.u$c r2 = new kotlin.reflect.jvm.internal.u$c
            r2.<init>(r7, r8, r1, r0)
            kotlin.reflect.jvm.internal.e0 r1 = kotlin.reflect.jvm.internal.e0.b
            kotlin.reflect.jvm.internal.t r3 = r7.w()
            kotlin.reflect.jvm.internal.impl.descriptors.i0 r3 = r3.s()
            kotlin.reflect.jvm.internal.d r1 = r1.f(r3)
            boolean r3 = r1 instanceof kotlin.reflect.jvm.internal.d.c
            r4 = 0
            if (r3 == 0) goto L_0x0181
            kotlin.reflect.jvm.internal.d$c r1 = (kotlin.reflect.jvm.internal.d.c) r1
            kotlin.reflect.jvm.internal.impl.metadata.jvm.a$d r3 = r1.f()
            if (r8 == 0) goto L_0x0050
            boolean r8 = r3.hasGetter()
            if (r8 == 0) goto L_0x005b
            kotlin.reflect.jvm.internal.impl.metadata.jvm.a$c r8 = r3.getGetter()
            goto L_0x005c
        L_0x0050:
            boolean r8 = r3.hasSetter()
            if (r8 == 0) goto L_0x005b
            kotlin.reflect.jvm.internal.impl.metadata.jvm.a$c r8 = r3.getSetter()
            goto L_0x005c
        L_0x005b:
            r8 = r4
        L_0x005c:
            if (r8 == 0) goto L_0x0087
            kotlin.reflect.jvm.internal.t r3 = r7.w()
            kotlin.reflect.jvm.internal.j r3 = r3.q()
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r5 = r1.d()
            int r6 = r8.getName()
            java.lang.String r5 = r5.getString(r6)
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r1 = r1.d()
            int r8 = r8.getDesc()
            java.lang.String r8 = r1.getString(r8)
            java.lang.reflect.Method r8 = r3.t(r5, r8)
            goto L_0x0088
        L_0x0087:
            r8 = r4
        L_0x0088:
            if (r8 != 0) goto L_0x0131
            kotlin.reflect.jvm.internal.t r8 = r7.w()
            kotlin.reflect.jvm.internal.impl.descriptors.i0 r8 = r8.s()
            boolean r8 = kotlin.reflect.jvm.internal.impl.resolve.e.d(r8)
            if (r8 == 0) goto L_0x0106
            kotlin.reflect.jvm.internal.t r8 = r7.w()
            kotlin.reflect.jvm.internal.impl.descriptors.i0 r8 = r8.s()
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r8 = r8.getVisibility()
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r0 = kotlin.reflect.jvm.internal.impl.descriptors.z0.d
            boolean r8 = kotlin.jvm.internal.k.a(r8, r0)
            if (r8 == 0) goto L_0x0106
            kotlin.reflect.jvm.internal.t r8 = r7.w()
            kotlin.reflect.jvm.internal.impl.descriptors.i0 r8 = r8.s()
            kotlin.reflect.jvm.internal.impl.descriptors.m r8 = r8.b()
            java.lang.Class r8 = kotlin.reflect.jvm.internal.calls.h.h(r8)
            if (r8 == 0) goto L_0x00e6
            kotlin.reflect.jvm.internal.t r0 = r7.w()
            kotlin.reflect.jvm.internal.impl.descriptors.i0 r0 = r0.s()
            java.lang.reflect.Method r8 = kotlin.reflect.jvm.internal.calls.h.f(r8, r0)
            if (r8 == 0) goto L_0x00e6
            boolean r0 = r7.u()
            if (r0 == 0) goto L_0x00df
            kotlin.reflect.jvm.internal.calls.i$a r0 = new kotlin.reflect.jvm.internal.calls.i$a
            java.lang.Object r1 = d(r7)
            r0.<init>(r8, r1)
            goto L_0x00e4
        L_0x00df:
            kotlin.reflect.jvm.internal.calls.i$b r0 = new kotlin.reflect.jvm.internal.calls.i$b
            r0.<init>(r8)
        L_0x00e4:
            goto L_0x01be
        L_0x00e6:
            kotlin.reflect.jvm.internal.y r8 = new kotlin.reflect.jvm.internal.y
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Underlying property of inline class "
            r0.append(r1)
            kotlin.reflect.jvm.internal.t r7 = r7.w()
            r0.append(r7)
            java.lang.String r7 = " should have a field"
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            r8.<init>(r7)
            throw r8
        L_0x0106:
            kotlin.reflect.jvm.internal.t r8 = r7.w()
            java.lang.reflect.Field r8 = r8.A()
            if (r8 == 0) goto L_0x0116
            kotlin.reflect.jvm.internal.calls.e r0 = r2.invoke((java.lang.reflect.Field) r8)
            goto L_0x01be
        L_0x0116:
            kotlin.reflect.jvm.internal.y r8 = new kotlin.reflect.jvm.internal.y
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "No accessors or field is found for property "
            r0.append(r1)
            kotlin.reflect.jvm.internal.t r7 = r7.w()
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            r8.<init>(r7)
            throw r8
        L_0x0131:
            int r1 = r8.getModifiers()
            boolean r1 = java.lang.reflect.Modifier.isStatic(r1)
            if (r1 != 0) goto L_0x0152
            boolean r0 = r7.u()
            if (r0 == 0) goto L_0x014b
            kotlin.reflect.jvm.internal.calls.e$h$a r0 = new kotlin.reflect.jvm.internal.calls.e$h$a
            java.lang.Object r1 = d(r7)
            r0.<init>(r8, r1)
            goto L_0x0150
        L_0x014b:
            kotlin.reflect.jvm.internal.calls.e$h$d r0 = new kotlin.reflect.jvm.internal.calls.e$h$d
            r0.<init>(r8)
        L_0x0150:
            goto L_0x01be
        L_0x0152:
            boolean r0 = r0.invoke()
            if (r0 == 0) goto L_0x016a
            boolean r0 = r7.u()
            if (r0 == 0) goto L_0x0164
            kotlin.reflect.jvm.internal.calls.e$h$b r0 = new kotlin.reflect.jvm.internal.calls.e$h$b
            r0.<init>(r8)
            goto L_0x0169
        L_0x0164:
            kotlin.reflect.jvm.internal.calls.e$h$e r0 = new kotlin.reflect.jvm.internal.calls.e$h$e
            r0.<init>(r8)
        L_0x0169:
            goto L_0x01be
        L_0x016a:
            boolean r0 = r7.u()
            if (r0 == 0) goto L_0x017a
            kotlin.reflect.jvm.internal.calls.e$h$c r0 = new kotlin.reflect.jvm.internal.calls.e$h$c
            java.lang.Object r1 = d(r7)
            r0.<init>(r8, r1)
            goto L_0x017f
        L_0x017a:
            kotlin.reflect.jvm.internal.calls.e$h$f r0 = new kotlin.reflect.jvm.internal.calls.e$h$f
            r0.<init>(r8)
        L_0x017f:
            goto L_0x01be
        L_0x0181:
            boolean r0 = r1 instanceof kotlin.reflect.jvm.internal.d.a
            if (r0 == 0) goto L_0x0190
            kotlin.reflect.jvm.internal.d$a r1 = (kotlin.reflect.jvm.internal.d.a) r1
            java.lang.reflect.Field r8 = r1.b()
            kotlin.reflect.jvm.internal.calls.e r0 = r2.invoke((java.lang.reflect.Field) r8)
            goto L_0x01be
        L_0x0190:
            boolean r0 = r1 instanceof kotlin.reflect.jvm.internal.d.b
            if (r0 == 0) goto L_0x01e5
            if (r8 == 0) goto L_0x019e
            kotlin.reflect.jvm.internal.d$b r1 = (kotlin.reflect.jvm.internal.d.b) r1
            java.lang.reflect.Method r8 = r1.b()
            goto L_0x01a6
        L_0x019e:
            kotlin.reflect.jvm.internal.d$b r1 = (kotlin.reflect.jvm.internal.d.b) r1
            java.lang.reflect.Method r8 = r1.c()
            if (r8 == 0) goto L_0x01ca
        L_0x01a6:
            boolean r0 = r7.u()
            if (r0 == 0) goto L_0x01b8
            kotlin.reflect.jvm.internal.calls.e$h$a r0 = new kotlin.reflect.jvm.internal.calls.e$h$a
            java.lang.Object r1 = d(r7)
            r0.<init>(r8, r1)
            goto L_0x01bd
        L_0x01b8:
            kotlin.reflect.jvm.internal.calls.e$h$d r0 = new kotlin.reflect.jvm.internal.calls.e$h$d
            r0.<init>(r8)
        L_0x01bd:
        L_0x01be:
            kotlin.reflect.jvm.internal.impl.descriptors.h0 r7 = r7.v()
            r8 = 0
            r1 = 2
            kotlin.reflect.jvm.internal.calls.d r7 = kotlin.reflect.jvm.internal.calls.h.c(r0, r7, r8, r1, r4)
            return r7
        L_0x01ca:
            kotlin.reflect.jvm.internal.y r7 = new kotlin.reflect.jvm.internal.y
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r0 = "No source found for setter of Java method property: "
            r8.append(r0)
            java.lang.reflect.Method r0 = r1.b()
            r8.append(r0)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x01e5:
            boolean r0 = r1 instanceof kotlin.reflect.jvm.internal.d.C0336d
            if (r0 == 0) goto L_0x0288
            if (r8 == 0) goto L_0x01f3
            kotlin.reflect.jvm.internal.d$d r1 = (kotlin.reflect.jvm.internal.d.C0336d) r1
            kotlin.reflect.jvm.internal.c$e r8 = r1.b()
            goto L_0x01fb
        L_0x01f3:
            kotlin.reflect.jvm.internal.d$d r1 = (kotlin.reflect.jvm.internal.d.C0336d) r1
            kotlin.reflect.jvm.internal.c$e r8 = r1.c()
            if (r8 == 0) goto L_0x026d
        L_0x01fb:
            kotlin.reflect.jvm.internal.t r0 = r7.w()
            kotlin.reflect.jvm.internal.j r0 = r0.q()
            java.lang.String r1 = r8.c()
            java.lang.String r8 = r8.b()
            java.lang.reflect.Method r8 = r0.t(r1, r8)
            if (r8 == 0) goto L_0x0252
            int r0 = r8.getModifiers()
            boolean r0 = java.lang.reflect.Modifier.isStatic(r0)
            r0 = r0 ^ 1
            if (r0 == 0) goto L_0x0237
            boolean r0 = r7.u()
            if (r0 == 0) goto L_0x0231
            kotlin.reflect.jvm.internal.calls.e$h$a r0 = new kotlin.reflect.jvm.internal.calls.e$h$a
            java.lang.Object r7 = d(r7)
            r0.<init>(r8, r7)
            goto L_0x0236
        L_0x0231:
            kotlin.reflect.jvm.internal.calls.e$h$d r0 = new kotlin.reflect.jvm.internal.calls.e$h$d
            r0.<init>(r8)
        L_0x0236:
            return r0
        L_0x0237:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r0 = "Mapped property cannot have a static accessor: "
            r8.append(r0)
            kotlin.reflect.jvm.internal.t r7 = r7.w()
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            java.lang.AssertionError r8 = new java.lang.AssertionError
            r8.<init>(r7)
            throw r8
        L_0x0252:
            kotlin.reflect.jvm.internal.y r8 = new kotlin.reflect.jvm.internal.y
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "No accessor found for property "
            r0.append(r1)
            kotlin.reflect.jvm.internal.t r7 = r7.w()
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            r8.<init>(r7)
            throw r8
        L_0x026d:
            kotlin.reflect.jvm.internal.y r8 = new kotlin.reflect.jvm.internal.y
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "No setter found for property "
            r0.append(r1)
            kotlin.reflect.jvm.internal.t r7 = r7.w()
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            r8.<init>(r7)
            throw r8
        L_0x0288:
            kotlin.NoWhenBranchMatchedException r7 = new kotlin.NoWhenBranchMatchedException
            r7.<init>()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.u.c(kotlin.reflect.jvm.internal.t$a, boolean):kotlin.reflect.jvm.internal.calls.d");
    }

    /* compiled from: KPropertyImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Boolean> {
        final /* synthetic */ t.a $this_computeCallerForAccessor;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(t.a aVar) {
            super(0);
            this.$this_computeCallerForAccessor = aVar;
        }

        public final boolean invoke() {
            return this.$this_computeCallerForAccessor.w().s().getAnnotations().I(h0.g());
        }
    }

    /* compiled from: KPropertyImpl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<Boolean> {
        final /* synthetic */ t.a $this_computeCallerForAccessor;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(t.a aVar) {
            super(0);
            this.$this_computeCallerForAccessor = aVar;
        }

        public final boolean invoke() {
            return !c1.l(this.$this_computeCallerForAccessor.w().s().getType());
        }
    }

    /* compiled from: KPropertyImpl.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<Field, e<? extends Field>> {
        final /* synthetic */ boolean $isGetter;
        final /* synthetic */ a $isJvmStaticProperty$1;
        final /* synthetic */ b $isNotNullProperty$2;
        final /* synthetic */ t.a $this_computeCallerForAccessor;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(t.a aVar, boolean z, b bVar, a aVar2) {
            super(1);
            this.$this_computeCallerForAccessor = aVar;
            this.$isGetter = z;
            this.$isNotNullProperty$2 = bVar;
            this.$isJvmStaticProperty$1 = aVar2;
        }

        @NotNull
        public final e<Field> invoke(@NotNull Field field) {
            k.f(field, "field");
            if (u.e(this.$this_computeCallerForAccessor.w().s()) || !Modifier.isStatic(field.getModifiers())) {
                if (this.$isGetter) {
                    if (this.$this_computeCallerForAccessor.u()) {
                        return new e.f.a(field, u.d(this.$this_computeCallerForAccessor));
                    }
                    return new e.f.c(field);
                } else if (this.$this_computeCallerForAccessor.u()) {
                    return new e.g.a(field, this.$isNotNullProperty$2.invoke(), u.d(this.$this_computeCallerForAccessor));
                } else {
                    return new e.g.c(field, this.$isNotNullProperty$2.invoke());
                }
            } else if (this.$isJvmStaticProperty$1.invoke()) {
                if (this.$isGetter) {
                    if (this.$this_computeCallerForAccessor.u()) {
                        return new e.f.b(field);
                    }
                    return new e.f.d(field);
                } else if (this.$this_computeCallerForAccessor.u()) {
                    return new e.g.b(field, this.$isNotNullProperty$2.invoke());
                } else {
                    return new e.g.d(field, this.$isNotNullProperty$2.invoke());
                }
            } else if (this.$isGetter) {
                return new e.f.C0333e(field);
            } else {
                return new e.g.C0334e(field, this.$isNotNullProperty$2.invoke());
            }
        }
    }

    /* access modifiers changed from: private */
    public static final boolean e(@NotNull i0 $this$isJvmFieldPropertyInCompanionObject) {
        m container = $this$isJvmFieldPropertyInCompanionObject.b();
        k.b(container, "containingDeclaration");
        if (!kotlin.reflect.jvm.internal.impl.resolve.c.x(container)) {
            return false;
        }
        m outerClass = container.b();
        if (!kotlin.reflect.jvm.internal.impl.resolve.c.C(outerClass) && !kotlin.reflect.jvm.internal.impl.resolve.c.t(outerClass)) {
            return true;
        }
        if (!($this$isJvmFieldPropertyInCompanionObject instanceof i) || !kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.i.f(((i) $this$isJvmFieldPropertyInCompanionObject).Y())) {
            return false;
        }
        return true;
    }
}
