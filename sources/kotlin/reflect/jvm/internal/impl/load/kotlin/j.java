package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.p0;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.c;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.t;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JvmPackagePartSource.kt */
public final class j implements e {
    @NotNull
    private final String b;
    @NotNull
    private final c c;
    @Nullable
    private final c d;
    @Nullable
    private final t<f> e;
    private final boolean f;
    @Nullable
    private final p g;

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x002c, code lost:
        r0 = r6.getString(r0.intValue());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public j(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.resolve.jvm.c r3, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.resolve.jvm.c r4, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.l r5, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r6, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.serialization.deserialization.t<kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f> r7, boolean r8, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.load.kotlin.p r9) {
        /*
            r2 = this;
            java.lang.String r0 = "className"
            kotlin.jvm.internal.k.f(r3, r0)
            java.lang.String r0 = "packageProto"
            kotlin.jvm.internal.k.f(r5, r0)
            java.lang.String r0 = "nameResolver"
            kotlin.jvm.internal.k.f(r6, r0)
            r2.<init>()
            r2.c = r3
            r2.d = r4
            r2.e = r7
            r2.f = r8
            r2.g = r9
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.l, java.lang.Integer> r0 = kotlin.reflect.jvm.internal.impl.metadata.jvm.a.l
            java.lang.String r1 = "JvmProtoBuf.packageModuleName"
            kotlin.jvm.internal.k.b(r0, r1)
            java.lang.Object r0 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.f.a(r5, r0)
            java.lang.Integer r0 = (java.lang.Integer) r0
            if (r0 == 0) goto L_0x0038
            int r0 = r0.intValue()
            r1 = 0
            java.lang.String r0 = r6.getString(r0)
            if (r0 == 0) goto L_0x0038
            goto L_0x003a
        L_0x0038:
            java.lang.String r0 = "main"
        L_0x003a:
            r2.b = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.kotlin.j.<init>(kotlin.reflect.jvm.internal.impl.resolve.jvm.c, kotlin.reflect.jvm.internal.impl.resolve.jvm.c, kotlin.reflect.jvm.internal.impl.metadata.l, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c, kotlin.reflect.jvm.internal.impl.serialization.deserialization.t, boolean, kotlin.reflect.jvm.internal.impl.load.kotlin.p):void");
    }

    @Nullable
    public final c e() {
        return this.d;
    }

    @Nullable
    public final p f() {
        return this.g;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public j(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.load.kotlin.p r10, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.l r11, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r12, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.serialization.deserialization.t<kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f> r13, boolean r14) {
        /*
            r9 = this;
            java.lang.String r0 = "kotlinClass"
            kotlin.jvm.internal.k.f(r10, r0)
            java.lang.String r0 = "packageProto"
            kotlin.jvm.internal.k.f(r11, r0)
            java.lang.String r0 = "nameResolver"
            kotlin.jvm.internal.k.f(r12, r0)
            kotlin.reflect.jvm.internal.impl.name.a r0 = r10.d()
            kotlin.reflect.jvm.internal.impl.resolve.jvm.c r2 = kotlin.reflect.jvm.internal.impl.resolve.jvm.c.b(r0)
            java.lang.String r0 = "JvmClassName.byClassId(kotlinClass.classId)"
            kotlin.jvm.internal.k.b(r2, r0)
            kotlin.reflect.jvm.internal.impl.load.kotlin.header.a r0 = r10.b()
            java.lang.String r0 = r0.e()
            r1 = 0
            if (r0 == 0) goto L_0x0039
            r3 = 0
            int r4 = r0.length()
            if (r4 <= 0) goto L_0x0032
            r4 = 1
            goto L_0x0033
        L_0x0032:
            r4 = 0
        L_0x0033:
            if (r4 == 0) goto L_0x0039
            kotlin.reflect.jvm.internal.impl.resolve.jvm.c r1 = kotlin.reflect.jvm.internal.impl.resolve.jvm.c.d(r0)
        L_0x0039:
            r3 = r1
            r1 = r9
            r4 = r11
            r5 = r12
            r6 = r13
            r7 = r14
            r8 = r10
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.kotlin.j.<init>(kotlin.reflect.jvm.internal.impl.load.kotlin.p, kotlin.reflect.jvm.internal.impl.metadata.l, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c, kotlin.reflect.jvm.internal.impl.serialization.deserialization.t, boolean):void");
    }

    @NotNull
    public String a() {
        return "Class '" + d().b().b() + '\'';
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.name.f g() {
        String f2 = this.c.f();
        k.b(f2, "className.internalName");
        kotlin.reflect.jvm.internal.impl.name.f f3 = kotlin.reflect.jvm.internal.impl.name.f.f(x.U0(f2, '/', (String) null, 2, (Object) null));
        k.b(f3, "Name.identifier(classNam….substringAfterLast('/'))");
        return f3;
    }

    @NotNull
    public final a d() {
        return new a(this.c.g(), g());
    }

    @NotNull
    public String toString() {
        return getClass().getSimpleName() + ": " + this.c;
    }

    @NotNull
    public p0 b() {
        p0 p0Var = p0.a;
        k.b(p0Var, "SourceFile.NO_SOURCE_FILE");
        return p0Var;
    }
}
