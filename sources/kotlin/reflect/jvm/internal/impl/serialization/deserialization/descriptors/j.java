package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.f0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.p;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.c;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.k;
import kotlin.reflect.jvm.internal.impl.metadata.i;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.b;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedMemberDescriptor.kt */
public final class j extends f0 implements b {
    @NotNull
    private f.a O4;
    @NotNull
    private final i P4;
    @NotNull
    private final c Q4;
    @NotNull
    private final h R4;
    @NotNull
    private final k S4;
    @Nullable
    private final e T4;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public j(@NotNull m containingDeclaration, @Nullable n0 original, @NotNull g annotations, @NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull b.a kind, @NotNull i proto, @NotNull c nameResolver, @NotNull h typeTable, @NotNull k versionRequirementTable, @Nullable e containerSource, @Nullable o0 source) {
        super(containingDeclaration, original, annotations, name, kind, source != null ? source : o0.a);
        i iVar = proto;
        c cVar = nameResolver;
        h hVar = typeTable;
        k kVar = versionRequirementTable;
        kotlin.jvm.internal.k.f(containingDeclaration, "containingDeclaration");
        kotlin.jvm.internal.k.f(annotations, "annotations");
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(kind, "kind");
        kotlin.jvm.internal.k.f(iVar, "proto");
        kotlin.jvm.internal.k.f(cVar, "nameResolver");
        kotlin.jvm.internal.k.f(hVar, "typeTable");
        kotlin.jvm.internal.k.f(kVar, "versionRequirementTable");
        this.P4 = iVar;
        this.Q4 = cVar;
        this.R4 = hVar;
        this.S4 = kVar;
        this.T4 = containerSource;
        this.O4 = f.a.COMPATIBLE;
    }

    @NotNull
    public List<kotlin.reflect.jvm.internal.impl.metadata.deserialization.j> E0() {
        return b.a.a(this);
    }

    @NotNull
    /* renamed from: j1 */
    public i Y() {
        return this.P4;
    }

    @NotNull
    public c G() {
        return this.Q4;
    }

    @NotNull
    public h C() {
        return this.R4;
    }

    @NotNull
    public k F() {
        return this.S4;
    }

    @Nullable
    public e h1() {
        return this.T4;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ j(m mVar, n0 n0Var, g gVar, kotlin.reflect.jvm.internal.impl.name.f fVar, b.a aVar, i iVar, c cVar, h hVar, k kVar, e eVar, o0 o0Var, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(mVar, n0Var, gVar, fVar, aVar, iVar, cVar, hVar, kVar, eVar, (i & 1024) != 0 ? null : o0Var);
    }

    @NotNull
    public f.a i1() {
        return this.O4;
    }

    @NotNull
    public final f0 k1(@Nullable l0 extensionReceiverParameter, @Nullable l0 dispatchReceiverParameter, @NotNull List<? extends t0> typeParameters, @NotNull List<? extends w0> unsubstitutedValueParameters, @Nullable b0 unsubstitutedReturnType, @Nullable w modality, @NotNull a1 visibility, @NotNull Map<? extends a.C0348a<?>, ?> userDataMap, @NotNull f.a isExperimentalCoroutineInReleaseEnvironment) {
        kotlin.jvm.internal.k.f(typeParameters, "typeParameters");
        kotlin.jvm.internal.k.f(unsubstitutedValueParameters, "unsubstitutedValueParameters");
        kotlin.jvm.internal.k.f(visibility, "visibility");
        kotlin.jvm.internal.k.f(userDataMap, "userDataMap");
        kotlin.jvm.internal.k.f(isExperimentalCoroutineInReleaseEnvironment, "isExperimentalCoroutineInReleaseEnvironment");
        f0 g1 = super.g1(extensionReceiverParameter, dispatchReceiverParameter, typeParameters, unsubstitutedValueParameters, unsubstitutedReturnType, modality, visibility, userDataMap);
        f0 f0Var = g1;
        this.O4 = isExperimentalCoroutineInReleaseEnvironment;
        kotlin.jvm.internal.k.b(g1, "super.initialize(\n      …easeEnvironment\n        }");
        return g1;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public p A0(@NotNull m newOwner, @Nullable u original, @NotNull b.a kind, @Nullable kotlin.reflect.jvm.internal.impl.name.f newName, @NotNull g annotations, @NotNull o0 source) {
        kotlin.reflect.jvm.internal.impl.name.f fVar;
        kotlin.jvm.internal.k.f(newOwner, "newOwner");
        kotlin.jvm.internal.k.f(kind, "kind");
        kotlin.jvm.internal.k.f(annotations, "annotations");
        kotlin.jvm.internal.k.f(source, "source");
        n0 n0Var = (n0) original;
        if (newName != null) {
            fVar = newName;
        } else {
            kotlin.reflect.jvm.internal.impl.name.f name = getName();
            kotlin.jvm.internal.k.b(name, "name");
            fVar = name;
        }
        j it = new j(newOwner, n0Var, annotations, fVar, kind, Y(), G(), C(), F(), h1(), source);
        it.O4 = i1();
        return it;
    }
}
