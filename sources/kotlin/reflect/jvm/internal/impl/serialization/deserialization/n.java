package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.a;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.c;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.k;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.l;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: context.kt */
public final class n {
    @NotNull
    private final e0 a;
    @NotNull
    private final x b;
    @NotNull
    private final l c;
    @NotNull
    private final c d;
    @NotNull
    private final m e;
    @NotNull
    private final h f;
    @NotNull
    private final k g;
    @NotNull
    private final a h;
    @Nullable
    private final e i;

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0068, code lost:
        r0 = r27.a();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public n(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.l r21, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r22, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.m r23, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.h r24, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.k r25, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.a r26, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e r27, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.serialization.deserialization.e0 r28, @org.jetbrains.annotations.NotNull java.util.List<kotlin.reflect.jvm.internal.impl.metadata.s> r29) {
        /*
            r20 = this;
            r9 = r20
            r10 = r21
            r11 = r22
            r12 = r23
            r13 = r24
            r14 = r25
            r15 = r26
            r8 = r27
            java.lang.String r0 = "components"
            kotlin.jvm.internal.k.f(r10, r0)
            java.lang.String r0 = "nameResolver"
            kotlin.jvm.internal.k.f(r11, r0)
            java.lang.String r0 = "containingDeclaration"
            kotlin.jvm.internal.k.f(r12, r0)
            java.lang.String r0 = "typeTable"
            kotlin.jvm.internal.k.f(r13, r0)
            java.lang.String r0 = "versionRequirementTable"
            kotlin.jvm.internal.k.f(r14, r0)
            java.lang.String r0 = "metadataVersion"
            kotlin.jvm.internal.k.f(r15, r0)
            java.lang.String r0 = "typeParameters"
            r7 = r29
            kotlin.jvm.internal.k.f(r7, r0)
            r20.<init>()
            r9.c = r10
            r9.d = r11
            r9.e = r12
            r9.f = r13
            r9.g = r14
            r9.h = r15
            r9.i = r8
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.e0 r6 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.e0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Deserializer for \""
            r0.append(r1)
            kotlin.reflect.jvm.internal.impl.name.f r1 = r23.getName()
            r0.append(r1)
            r1 = 34
            r0.append(r1)
            java.lang.String r4 = r0.toString()
            if (r8 == 0) goto L_0x006f
            java.lang.String r0 = r27.a()
            if (r0 == 0) goto L_0x006f
            goto L_0x0071
        L_0x006f:
            java.lang.String r0 = "[container not found]"
        L_0x0071:
            r5 = r0
            r16 = 0
            r17 = 32
            r18 = 0
            r0 = r6
            r1 = r20
            r2 = r28
            r3 = r29
            r19 = r6
            r6 = r16
            r7 = r17
            r8 = r18
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r0 = r19
            r9.a = r0
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.x r0 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.x
            r0.<init>(r9)
            r9.b = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.n.<init>(kotlin.reflect.jvm.internal.impl.serialization.deserialization.l, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c, kotlin.reflect.jvm.internal.impl.descriptors.m, kotlin.reflect.jvm.internal.impl.metadata.deserialization.h, kotlin.reflect.jvm.internal.impl.metadata.deserialization.k, kotlin.reflect.jvm.internal.impl.metadata.deserialization.a, kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e, kotlin.reflect.jvm.internal.impl.serialization.deserialization.e0, java.util.List):void");
    }

    @NotNull
    public final l c() {
        return this.c;
    }

    @NotNull
    public final c g() {
        return this.d;
    }

    @NotNull
    public final m e() {
        return this.e;
    }

    @NotNull
    public final h j() {
        return this.f;
    }

    @NotNull
    public final k k() {
        return this.g;
    }

    @Nullable
    public final e d() {
        return this.i;
    }

    @NotNull
    public final e0 i() {
        return this.a;
    }

    @NotNull
    public final x f() {
        return this.b;
    }

    @NotNull
    public final j h() {
        return this.c.t();
    }

    public static /* synthetic */ n b(n nVar, m mVar, List list, c cVar, h hVar, k kVar, a aVar, int i2, Object obj) {
        h hVar2;
        k kVar2;
        a aVar2;
        c cVar2 = (i2 & 4) != 0 ? nVar.d : cVar;
        if ((i2 & 8) != 0) {
            hVar2 = nVar.f;
        } else {
            hVar2 = hVar;
        }
        if ((i2 & 16) != 0) {
            kVar2 = nVar.g;
        } else {
            kVar2 = kVar;
        }
        if ((i2 & 32) != 0) {
            aVar2 = nVar.h;
        } else {
            aVar2 = aVar;
        }
        return nVar.a(mVar, list, cVar2, hVar2, kVar2, aVar2);
    }

    @NotNull
    public final n a(@NotNull m descriptor, @NotNull List<s> typeParameterProtos, @NotNull c nameResolver, @NotNull h typeTable, @NotNull k versionRequirementTable, @NotNull a metadataVersion) {
        kotlin.jvm.internal.k.f(descriptor, "descriptor");
        kotlin.jvm.internal.k.f(typeParameterProtos, "typeParameterProtos");
        kotlin.jvm.internal.k.f(nameResolver, "nameResolver");
        kotlin.jvm.internal.k.f(typeTable, "typeTable");
        k kVar = versionRequirementTable;
        kotlin.jvm.internal.k.f(kVar, "versionRequirementTable");
        kotlin.jvm.internal.k.f(metadataVersion, "metadataVersion");
        return new n(this.c, nameResolver, descriptor, typeTable, l.b(metadataVersion) ? kVar : this.g, metadataVersion, this.i, this.a, typeParameterProtos);
    }
}
