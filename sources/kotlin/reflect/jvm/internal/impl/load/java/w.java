package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.d;
import kotlin.reflect.jvm.internal.impl.load.kotlin.v;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: specialBuiltinMembers.kt */
public final class w {
    /* access modifiers changed from: private */
    public static final kotlin.reflect.jvm.internal.impl.name.b d(@NotNull kotlin.reflect.jvm.internal.impl.name.b $this$child, String name) {
        kotlin.reflect.jvm.internal.impl.name.b c2 = $this$child.c(f.f(name));
        k.b(c2, "child(Name.identifier(name))");
        return c2;
    }

    /* access modifiers changed from: private */
    public static final kotlin.reflect.jvm.internal.impl.name.b e(@NotNull kotlin.reflect.jvm.internal.impl.name.c $this$childSafe, String name) {
        kotlin.reflect.jvm.internal.impl.name.b l = $this$childSafe.c(f.f(name)).l();
        k.b(l, "child(Name.identifier(name)).toSafe()");
        return l;
    }

    /* access modifiers changed from: private */
    public static final u n(@NotNull String $this$method, String name, String parameters, String returnType) {
        f f = f.f(name);
        k.b(f, "Name.identifier(name)");
        v vVar = v.a;
        return new u(f, vVar.k($this$method, name + '(' + parameters + ')' + returnType));
    }

    @Nullable
    public static final <T extends kotlin.reflect.jvm.internal.impl.descriptors.b> T i(@NotNull T $this$getOverriddenBuiltinWithDifferentJvmName) {
        k.f($this$getOverriddenBuiltinWithDifferentJvmName, "$this$getOverriddenBuiltinWithDifferentJvmName");
        if (!c.f.d().contains($this$getOverriddenBuiltinWithDifferentJvmName.getName()) && !e.e.c().contains(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.p($this$getOverriddenBuiltinWithDifferentJvmName).getName())) {
            return null;
        }
        if (($this$getOverriddenBuiltinWithDifferentJvmName instanceof i0) || ($this$getOverriddenBuiltinWithDifferentJvmName instanceof h0)) {
            return kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.e($this$getOverriddenBuiltinWithDifferentJvmName, false, a.INSTANCE, 1, (Object) null);
        }
        if ($this$getOverriddenBuiltinWithDifferentJvmName instanceof n0) {
            return kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.e($this$getOverriddenBuiltinWithDifferentJvmName, false, b.INSTANCE, 1, (Object) null);
        }
        return null;
    }

    /* compiled from: specialBuiltinMembers.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, Boolean> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((kotlin.reflect.jvm.internal.impl.descriptors.b) obj));
        }

        public final boolean invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b it) {
            k.f(it, "it");
            return e.e.d(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.p(it));
        }
    }

    /* compiled from: specialBuiltinMembers.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, Boolean> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((kotlin.reflect.jvm.internal.impl.descriptors.b) obj));
        }

        public final boolean invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b it) {
            k.f(it, "it");
            return c.f.f((n0) it);
        }
    }

    public static final boolean f(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b $this$doesOverrideBuiltinWithDifferentJvmName) {
        k.f($this$doesOverrideBuiltinWithDifferentJvmName, "$this$doesOverrideBuiltinWithDifferentJvmName");
        return i($this$doesOverrideBuiltinWithDifferentJvmName) != null;
    }

    @Nullable
    public static final <T extends kotlin.reflect.jvm.internal.impl.descriptors.b> T j(@NotNull T $this$getOverriddenSpecialBuiltin) {
        k.f($this$getOverriddenSpecialBuiltin, "$this$getOverriddenSpecialBuiltin");
        kotlin.reflect.jvm.internal.impl.descriptors.b it = i($this$getOverriddenSpecialBuiltin);
        if (it != null) {
            return it;
        }
        d dVar = d.h;
        f name = $this$getOverriddenSpecialBuiltin.getName();
        k.b(name, "name");
        if (!dVar.d(name)) {
            return null;
        }
        return kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.e($this$getOverriddenSpecialBuiltin, false, c.INSTANCE, 1, (Object) null);
    }

    /* compiled from: specialBuiltinMembers.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, Boolean> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((kotlin.reflect.jvm.internal.impl.descriptors.b) obj));
        }

        public final boolean invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b it) {
            k.f(it, "it");
            return g.h0(it) && d.e(it) != null;
        }
    }

    @Nullable
    public static final String g(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b callableMemberDescriptor) {
        kotlin.reflect.jvm.internal.impl.descriptors.b overriddenBuiltin;
        f c2;
        k.f(callableMemberDescriptor, "callableMemberDescriptor");
        kotlin.reflect.jvm.internal.impl.descriptors.b h = h(callableMemberDescriptor);
        if (h == null || (overriddenBuiltin = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.p(h)) == null) {
            return null;
        }
        if (overriddenBuiltin instanceof i0) {
            return e.e.a(overriddenBuiltin);
        }
        if (!(overriddenBuiltin instanceof n0) || (c2 = c.f.c((n0) overriddenBuiltin)) == null) {
            return null;
        }
        return c2.b();
    }

    private static final kotlin.reflect.jvm.internal.impl.descriptors.b h(kotlin.reflect.jvm.internal.impl.descriptors.b callableMemberDescriptor) {
        if (g.h0(callableMemberDescriptor)) {
            return i(callableMemberDescriptor);
        }
        return null;
    }

    public static final boolean k(@NotNull e $this$hasRealKotlinSuperClassWithOverrideOf, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a specialCallableDescriptor) {
        k.f($this$hasRealKotlinSuperClassWithOverrideOf, "$this$hasRealKotlinSuperClassWithOverrideOf");
        k.f(specialCallableDescriptor, "specialCallableDescriptor");
        m b2 = specialCallableDescriptor.b();
        if (b2 != null) {
            kotlin.reflect.jvm.internal.impl.types.i0 builtinContainerDefaultType = ((e) b2).m();
            k.b(builtinContainerDefaultType, "(specialCallableDescript…ssDescriptor).defaultType");
            e superClassDescriptor = kotlin.reflect.jvm.internal.impl.resolve.c.s($this$hasRealKotlinSuperClassWithOverrideOf);
            while (true) {
                boolean doesOverrideBuiltinDeclaration = false;
                if (superClassDescriptor == null) {
                    return false;
                }
                if (!(superClassDescriptor instanceof d)) {
                    if (kotlin.reflect.jvm.internal.impl.types.checker.v.e(superClassDescriptor.m(), builtinContainerDefaultType) != null) {
                        doesOverrideBuiltinDeclaration = true;
                    }
                    if (doesOverrideBuiltinDeclaration) {
                        return !g.h0(superClassDescriptor);
                    }
                }
                superClassDescriptor = kotlin.reflect.jvm.internal.impl.resolve.c.s(superClassDescriptor);
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
    }

    public static final boolean l(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b $this$isFromJava) {
        k.f($this$isFromJava, "$this$isFromJava");
        return kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.p($this$isFromJava).b() instanceof d;
    }

    public static final boolean m(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b $this$isFromJavaOrBuiltins) {
        k.f($this$isFromJavaOrBuiltins, "$this$isFromJavaOrBuiltins");
        return l($this$isFromJavaOrBuiltins) || g.h0($this$isFromJavaOrBuiltins);
    }
}
