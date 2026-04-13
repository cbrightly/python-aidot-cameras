package kotlin.reflect.jvm.internal.impl.types;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeSubstitution.kt */
public final class z extends z0 {
    @NotNull
    private final t0[] c;
    @NotNull
    private final w0[] d;
    private final boolean e;

    public z(@NotNull t0[] parameters, @NotNull w0[] arguments, boolean approximateCapturedTypes) {
        k.f(parameters, "parameters");
        k.f(arguments, "arguments");
        this.c = parameters;
        this.d = arguments;
        this.e = approximateCapturedTypes;
        if (!(parameters.length <= arguments.length)) {
            throw new AssertionError("Number of arguments should not be less then number of parameters, but: parameters=" + parameters.length + ", args=" + arguments.length);
        }
    }

    @NotNull
    public final t0[] i() {
        return this.c;
    }

    @NotNull
    public final w0[] h() {
        return this.d;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ z(t0[] t0VarArr, w0[] w0VarArr, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(t0VarArr, w0VarArr, (i & 4) != 0 ? false : z);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public z(@org.jetbrains.annotations.NotNull java.util.List<? extends kotlin.reflect.jvm.internal.impl.descriptors.t0> r13, @org.jetbrains.annotations.NotNull java.util.List<? extends kotlin.reflect.jvm.internal.impl.types.w0> r14) {
        /*
            r12 = this;
            java.lang.String r0 = "parameters"
            kotlin.jvm.internal.k.f(r13, r0)
            java.lang.String r0 = "argumentsList"
            kotlin.jvm.internal.k.f(r14, r0)
            r0 = r13
            r1 = 0
            r2 = r0
            r3 = 0
            kotlin.reflect.jvm.internal.impl.descriptors.t0[] r4 = new kotlin.reflect.jvm.internal.impl.descriptors.t0[r3]
            java.lang.Object[] r4 = r2.toArray(r4)
            java.lang.String r5 = "null cannot be cast to non-null type kotlin.Array<T>"
            if (r4 == 0) goto L_0x0039
            r7 = r4
            kotlin.reflect.jvm.internal.impl.descriptors.t0[] r7 = (kotlin.reflect.jvm.internal.impl.descriptors.t0[]) r7
            r0 = r14
            r1 = 0
            r2 = r0
            kotlin.reflect.jvm.internal.impl.types.w0[] r3 = new kotlin.reflect.jvm.internal.impl.types.w0[r3]
            java.lang.Object[] r3 = r2.toArray(r3)
            if (r3 == 0) goto L_0x0033
            r8 = r3
            kotlin.reflect.jvm.internal.impl.types.w0[] r8 = (kotlin.reflect.jvm.internal.impl.types.w0[]) r8
            r9 = 0
            r10 = 4
            r11 = 0
            r6 = r12
            r6.<init>(r7, r8, r9, r10, r11)
            return
        L_0x0033:
            kotlin.TypeCastException r3 = new kotlin.TypeCastException
            r3.<init>(r5)
            throw r3
        L_0x0039:
            kotlin.TypeCastException r3 = new kotlin.TypeCastException
            r3.<init>(r5)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.z.<init>(java.util.List, java.util.List):void");
    }

    public boolean f() {
        return this.d.length == 0;
    }

    public boolean b() {
        return this.e;
    }

    @Nullable
    public w0 e(@NotNull b0 key) {
        k.f(key, CacheEntity.KEY);
        h c2 = key.I0().c();
        if (!(c2 instanceof t0)) {
            c2 = null;
        }
        t0 parameter = (t0) c2;
        if (parameter == null) {
            return null;
        }
        int index = parameter.getIndex();
        t0[] t0VarArr = this.c;
        if (index >= t0VarArr.length || !k.a(t0VarArr[index].i(), parameter.i())) {
            return null;
        }
        return this.d[index];
    }
}
