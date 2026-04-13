package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.protobuf.f;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.checker.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: context.kt */
public final class l {
    @NotNull
    private final j a;
    @NotNull
    private final j b;
    @NotNull
    private final y c;
    @NotNull
    private final m d;
    @NotNull
    private final i e;
    @NotNull
    private final c<c, g<?>> f;
    @NotNull
    private final c0 g;
    @NotNull
    private final v h;
    @NotNull
    private final r i;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.incremental.components.c j;
    @NotNull
    private final s k;
    @NotNull
    private final Iterable<b> l;
    @NotNull
    private final a0 m;
    @NotNull
    private final k n;
    @NotNull
    private final a o;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c p;
    @NotNull
    private final f q;
    @NotNull
    private final n r;

    public l(@NotNull j storageManager, @NotNull y moduleDescriptor, @NotNull m configuration, @NotNull i classDataFinder, @NotNull c<? extends c, ? extends g<?>> annotationAndConstantLoader, @NotNull c0 packageFragmentProvider, @NotNull v localClassifierTypeSettings, @NotNull r errorReporter, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.c lookupTracker, @NotNull s flexibleTypeDeserializer, @NotNull Iterable<? extends b> fictitiousClassDescriptorFactories, @NotNull a0 notFoundClasses, @NotNull k contractDeserializer, @NotNull a additionalClassPartsProvider, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c platformDependentDeclarationFilter, @NotNull f extensionRegistryLite, @NotNull n kotlinTypeChecker) {
        j jVar = storageManager;
        y yVar = moduleDescriptor;
        m mVar = configuration;
        i iVar = classDataFinder;
        c<? extends c, ? extends g<?>> cVar = annotationAndConstantLoader;
        c0 c0Var = packageFragmentProvider;
        v vVar = localClassifierTypeSettings;
        r rVar = errorReporter;
        kotlin.reflect.jvm.internal.impl.incremental.components.c cVar2 = lookupTracker;
        s sVar = flexibleTypeDeserializer;
        Iterable<? extends b> iterable = fictitiousClassDescriptorFactories;
        a0 a0Var = notFoundClasses;
        k kVar = contractDeserializer;
        a aVar = additionalClassPartsProvider;
        f fVar = extensionRegistryLite;
        k.f(jVar, "storageManager");
        k.f(yVar, "moduleDescriptor");
        k.f(mVar, "configuration");
        k.f(iVar, "classDataFinder");
        k.f(cVar, "annotationAndConstantLoader");
        k.f(c0Var, "packageFragmentProvider");
        k.f(vVar, "localClassifierTypeSettings");
        k.f(rVar, "errorReporter");
        k.f(cVar2, "lookupTracker");
        k.f(sVar, "flexibleTypeDeserializer");
        k.f(iterable, "fictitiousClassDescriptorFactories");
        k.f(a0Var, "notFoundClasses");
        k.f(kVar, "contractDeserializer");
        k.f(aVar, "additionalClassPartsProvider");
        k.f(platformDependentDeclarationFilter, "platformDependentDeclarationFilter");
        k.f(extensionRegistryLite, "extensionRegistryLite");
        k.f(kotlinTypeChecker, "kotlinTypeChecker");
        this.b = jVar;
        this.c = yVar;
        this.d = mVar;
        this.e = iVar;
        this.f = cVar;
        this.g = c0Var;
        this.h = vVar;
        this.i = rVar;
        this.j = cVar2;
        this.k = sVar;
        this.l = iterable;
        this.m = a0Var;
        this.n = kVar;
        this.o = aVar;
        this.p = platformDependentDeclarationFilter;
        this.q = extensionRegistryLite;
        this.r = kotlinTypeChecker;
        this.a = new j(this);
    }

    @NotNull
    public final j t() {
        return this.b;
    }

    @NotNull
    public final y p() {
        return this.c;
    }

    @NotNull
    public final m g() {
        return this.d;
    }

    @NotNull
    public final i e() {
        return this.e;
    }

    @NotNull
    public final c<c, g<?>> d() {
        return this.f;
    }

    @NotNull
    public final c0 r() {
        return this.g;
    }

    @NotNull
    public final v n() {
        return this.h;
    }

    @NotNull
    public final r i() {
        return this.i;
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.incremental.components.c o() {
        return this.j;
    }

    @NotNull
    public final s l() {
        return this.k;
    }

    @NotNull
    public final Iterable<b> k() {
        return this.l;
    }

    @NotNull
    public final a0 q() {
        return this.m;
    }

    @NotNull
    public final k h() {
        return this.n;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ l(kotlin.reflect.jvm.internal.impl.storage.j r21, kotlin.reflect.jvm.internal.impl.descriptors.y r22, kotlin.reflect.jvm.internal.impl.serialization.deserialization.m r23, kotlin.reflect.jvm.internal.impl.serialization.deserialization.i r24, kotlin.reflect.jvm.internal.impl.serialization.deserialization.c r25, kotlin.reflect.jvm.internal.impl.descriptors.c0 r26, kotlin.reflect.jvm.internal.impl.serialization.deserialization.v r27, kotlin.reflect.jvm.internal.impl.serialization.deserialization.r r28, kotlin.reflect.jvm.internal.impl.incremental.components.c r29, kotlin.reflect.jvm.internal.impl.serialization.deserialization.s r30, java.lang.Iterable r31, kotlin.reflect.jvm.internal.impl.descriptors.a0 r32, kotlin.reflect.jvm.internal.impl.serialization.deserialization.k r33, kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a r34, kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c r35, kotlin.reflect.jvm.internal.impl.protobuf.f r36, kotlin.reflect.jvm.internal.impl.types.checker.n r37, int r38, kotlin.jvm.internal.DefaultConstructorMarker r39) {
        /*
            r20 = this;
            r0 = r38
            r1 = r0 & 8192(0x2000, float:1.14794E-41)
            if (r1 == 0) goto L_0x000b
            kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a$a r1 = kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a.C0350a.a
            r16 = r1
            goto L_0x000d
        L_0x000b:
            r16 = r34
        L_0x000d:
            r1 = r0 & 16384(0x4000, float:2.2959E-41)
            if (r1 == 0) goto L_0x0016
            kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c$a r1 = kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c.a.a
            r17 = r1
            goto L_0x0018
        L_0x0016:
            r17 = r35
        L_0x0018:
            r1 = 65536(0x10000, float:9.18355E-41)
            r0 = r0 & r1
            if (r0 == 0) goto L_0x0026
            kotlin.reflect.jvm.internal.impl.types.checker.n$a r0 = kotlin.reflect.jvm.internal.impl.types.checker.n.b
            kotlin.reflect.jvm.internal.impl.types.checker.o r0 = r0.a()
            r19 = r0
            goto L_0x0028
        L_0x0026:
            r19 = r37
        L_0x0028:
            r2 = r20
            r3 = r21
            r4 = r22
            r5 = r23
            r6 = r24
            r7 = r25
            r8 = r26
            r9 = r27
            r10 = r28
            r11 = r29
            r12 = r30
            r13 = r31
            r14 = r32
            r15 = r33
            r18 = r36
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.l.<init>(kotlin.reflect.jvm.internal.impl.storage.j, kotlin.reflect.jvm.internal.impl.descriptors.y, kotlin.reflect.jvm.internal.impl.serialization.deserialization.m, kotlin.reflect.jvm.internal.impl.serialization.deserialization.i, kotlin.reflect.jvm.internal.impl.serialization.deserialization.c, kotlin.reflect.jvm.internal.impl.descriptors.c0, kotlin.reflect.jvm.internal.impl.serialization.deserialization.v, kotlin.reflect.jvm.internal.impl.serialization.deserialization.r, kotlin.reflect.jvm.internal.impl.incremental.components.c, kotlin.reflect.jvm.internal.impl.serialization.deserialization.s, java.lang.Iterable, kotlin.reflect.jvm.internal.impl.descriptors.a0, kotlin.reflect.jvm.internal.impl.serialization.deserialization.k, kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a, kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c, kotlin.reflect.jvm.internal.impl.protobuf.f, kotlin.reflect.jvm.internal.impl.types.checker.n, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final a c() {
        return this.o;
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c s() {
        return this.p;
    }

    @NotNull
    public final f j() {
        return this.q;
    }

    @NotNull
    public final n m() {
        return this.r;
    }

    @NotNull
    public final j f() {
        return this.a;
    }

    @Nullable
    public final e b(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
        k.f(classId, "classId");
        return j.e(this.a, classId, (h) null, 2, (Object) null);
    }

    @NotNull
    public final n a(@NotNull b0 descriptor, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull h typeTable, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.k versionRequirementTable, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.a metadataVersion, @Nullable kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e containerSource) {
        k.f(descriptor, "descriptor");
        k.f(nameResolver, "nameResolver");
        k.f(typeTable, "typeTable");
        k.f(versionRequirementTable, "versionRequirementTable");
        k.f(metadataVersion, "metadataVersion");
        return new n(this, nameResolver, descriptor, typeTable, versionRequirementTable, metadataVersion, containerSource, (e0) null, q.g());
    }
}
