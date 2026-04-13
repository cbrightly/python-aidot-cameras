package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.s;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.c;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.j;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.k;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.b;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.f;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedMemberDescriptor.kt */
public final class i extends b0 implements b {
    @NotNull
    private f.a L4 = f.a.COMPATIBLE;
    @NotNull
    private final n M4;
    @NotNull
    private final c N4;
    @NotNull
    private final h O4;
    @NotNull
    private final k P4;
    @Nullable
    private final e Q4;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public i(@NotNull m containingDeclaration, @Nullable i0 original, @NotNull g annotations, @NotNull w modality, @NotNull a1 visibility, boolean isVar, @NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull b.a kind, boolean isLateInit, boolean isConst, boolean isExternal, boolean isDelegated, boolean isExpect, @NotNull n proto, @NotNull c nameResolver, @NotNull h typeTable, @NotNull k versionRequirementTable, @Nullable e containerSource) {
        super(containingDeclaration, original, annotations, modality, visibility, isVar, name, kind, o0.a, isLateInit, isConst, isExpect, false, isExternal, isDelegated);
        kotlin.jvm.internal.k.f(containingDeclaration, "containingDeclaration");
        kotlin.jvm.internal.k.f(annotations, "annotations");
        kotlin.jvm.internal.k.f(modality, "modality");
        kotlin.jvm.internal.k.f(visibility, "visibility");
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(kind, "kind");
        kotlin.jvm.internal.k.f(proto, "proto");
        kotlin.jvm.internal.k.f(nameResolver, "nameResolver");
        kotlin.jvm.internal.k.f(typeTable, "typeTable");
        kotlin.jvm.internal.k.f(versionRequirementTable, "versionRequirementTable");
        this.M4 = proto;
        this.N4 = nameResolver;
        this.O4 = typeTable;
        this.P4 = versionRequirementTable;
        this.Q4 = containerSource;
    }

    @NotNull
    public List<j> E0() {
        return b.a.a(this);
    }

    @NotNull
    /* renamed from: V0 */
    public n Y() {
        return this.M4;
    }

    @NotNull
    public c G() {
        return this.N4;
    }

    @NotNull
    public h C() {
        return this.O4;
    }

    @NotNull
    public k F() {
        return this.P4;
    }

    @Nullable
    public e U0() {
        return this.Q4;
    }

    public final void W0(@Nullable c0 getter, @Nullable k0 setter, @Nullable s backingField, @Nullable s delegateField, @NotNull f.a isExperimentalCoroutineInReleaseEnvironment) {
        kotlin.jvm.internal.k.f(isExperimentalCoroutineInReleaseEnvironment, "isExperimentalCoroutineInReleaseEnvironment");
        super.N0(getter, setter, backingField, delegateField);
        x xVar = x.a;
        this.L4 = isExperimentalCoroutineInReleaseEnvironment;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public b0 H0(@NotNull m newOwner, @NotNull w newModality, @NotNull a1 newVisibility, @Nullable i0 original, @NotNull b.a kind, @NotNull kotlin.reflect.jvm.internal.impl.name.f newName, @NotNull o0 source) {
        i0 i0Var = original;
        kotlin.reflect.jvm.internal.impl.name.f fVar = newName;
        kotlin.jvm.internal.k.f(newOwner, "newOwner");
        kotlin.jvm.internal.k.f(newModality, "newModality");
        kotlin.jvm.internal.k.f(newVisibility, "newVisibility");
        kotlin.jvm.internal.k.f(kind, "kind");
        kotlin.jvm.internal.k.f(newName, "newName");
        kotlin.jvm.internal.k.f(source, "source");
        return new i(newOwner, i0Var, getAnnotations(), newModality, newVisibility, K(), fVar, kind, t0(), isConst(), isExternal(), z(), d0(), Y(), G(), C(), F(), U0());
    }

    public boolean isExternal() {
        Boolean g = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.z.d(Y().getFlags());
        kotlin.jvm.internal.k.b(g, "Flags.IS_EXTERNAL_PROPERTY.get(proto.flags)");
        return g.booleanValue();
    }
}
