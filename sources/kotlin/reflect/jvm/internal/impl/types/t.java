package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: ErrorType.kt */
public class t extends i0 {
    @NotNull
    private final u0 d;
    @NotNull
    private final h f;
    @NotNull
    private final List<w0> q;
    private final boolean x;
    @NotNull
    private final String y;

    public t(@NotNull u0 u0Var, @NotNull h hVar) {
        this(u0Var, hVar, (List) null, false, (String) null, 28, (DefaultConstructorMarker) null);
    }

    public t(@NotNull u0 u0Var, @NotNull h hVar, @NotNull List<? extends w0> list, boolean z) {
        this(u0Var, hVar, list, z, (String) null, 16, (DefaultConstructorMarker) null);
    }

    public t(@NotNull u0 constructor, @NotNull h memberScope, @NotNull List<? extends w0> arguments, boolean isMarkedNullable, @NotNull String presentableName) {
        k.f(constructor, "constructor");
        k.f(memberScope, "memberScope");
        k.f(arguments, "arguments");
        k.f(presentableName, "presentableName");
        this.d = constructor;
        this.f = memberScope;
        this.q = arguments;
        this.x = isMarkedNullable;
        this.y = presentableName;
    }

    @NotNull
    public u0 I0() {
        return this.d;
    }

    @NotNull
    public h l() {
        return this.f;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ t(kotlin.reflect.jvm.internal.impl.types.u0 r7, kotlin.reflect.jvm.internal.impl.resolve.scopes.h r8, java.util.List r9, boolean r10, java.lang.String r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 4
            if (r13 == 0) goto L_0x000a
            java.util.List r9 = kotlin.collections.q.g()
            r3 = r9
            goto L_0x000b
        L_0x000a:
            r3 = r9
        L_0x000b:
            r9 = r12 & 8
            if (r9 == 0) goto L_0x0012
            r10 = 0
            r4 = r10
            goto L_0x0013
        L_0x0012:
            r4 = r10
        L_0x0013:
            r9 = r12 & 16
            if (r9 == 0) goto L_0x001b
            java.lang.String r11 = "???"
            r5 = r11
            goto L_0x001c
        L_0x001b:
            r5 = r11
        L_0x001c:
            r0 = r6
            r1 = r7
            r2 = r8
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.t.<init>(kotlin.reflect.jvm.internal.impl.types.u0, kotlin.reflect.jvm.internal.impl.resolve.scopes.h, java.util.List, boolean, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public List<w0> H0() {
        return this.q;
    }

    public boolean J0() {
        return this.x;
    }

    @NotNull
    public String R0() {
        return this.y;
    }

    @NotNull
    public g getAnnotations() {
        return g.b.b();
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(I0().toString());
        sb.append(H0().isEmpty() ? "" : y.a0(H0(), ", ", "<", ">", -1, "...", (l) null));
        return sb.toString();
    }

    @NotNull
    /* renamed from: Q0 */
    public i0 O0(@NotNull g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        return this;
    }

    @NotNull
    /* renamed from: P0 */
    public i0 M0(boolean newNullability) {
        return new t(I0(), l(), H0(), newNullability, (String) null, 16, (DefaultConstructorMarker) null);
    }

    @NotNull
    /* renamed from: S0 */
    public t N0(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }
}
