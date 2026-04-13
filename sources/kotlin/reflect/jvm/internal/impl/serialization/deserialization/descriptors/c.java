package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.f;
import kotlin.reflect.jvm.internal.impl.descriptors.l;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.metadata.d;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.j;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.k;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.b;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedMemberDescriptor.kt */
public final class c extends f implements b {
    @NotNull
    private f.a Q4;
    @NotNull
    private final d R4;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.metadata.deserialization.c S4;
    @NotNull
    private final h T4;
    @NotNull
    private final k U4;
    @Nullable
    private final e V4;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(@NotNull e containingDeclaration, @Nullable l original, @NotNull g annotations, boolean isPrimary, @NotNull b.a kind, @NotNull d proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c nameResolver, @NotNull h typeTable, @NotNull k versionRequirementTable, @Nullable e containerSource, @Nullable o0 source) {
        super(containingDeclaration, original, annotations, isPrimary, kind, source != null ? source : o0.a);
        d dVar = proto;
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar = nameResolver;
        h hVar = typeTable;
        k kVar = versionRequirementTable;
        kotlin.jvm.internal.k.f(containingDeclaration, "containingDeclaration");
        kotlin.jvm.internal.k.f(annotations, "annotations");
        kotlin.jvm.internal.k.f(kind, "kind");
        kotlin.jvm.internal.k.f(dVar, "proto");
        kotlin.jvm.internal.k.f(cVar, "nameResolver");
        kotlin.jvm.internal.k.f(hVar, "typeTable");
        kotlin.jvm.internal.k.f(kVar, "versionRequirementTable");
        this.R4 = dVar;
        this.S4 = cVar;
        this.T4 = hVar;
        this.U4 = kVar;
        this.V4 = containerSource;
        this.Q4 = f.a.COMPATIBLE;
    }

    @NotNull
    public List<j> E0() {
        return b.a.a(this);
    }

    @NotNull
    /* renamed from: m1 */
    public d Y() {
        return this.R4;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.metadata.deserialization.c G() {
        return this.S4;
    }

    @NotNull
    public h C() {
        return this.T4;
    }

    @NotNull
    public k F() {
        return this.U4;
    }

    @Nullable
    public e k1() {
        return this.V4;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ c(e eVar, l lVar, g gVar, boolean z, b.a aVar, d dVar, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c cVar, h hVar, k kVar, e eVar2, o0 o0Var, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(eVar, lVar, gVar, z, aVar, dVar, cVar, hVar, kVar, eVar2, (i & 1024) != 0 ? null : o0Var);
    }

    @NotNull
    public f.a l1() {
        return this.Q4;
    }

    public void n1(@NotNull f.a aVar) {
        kotlin.jvm.internal.k.f(aVar, "<set-?>");
        this.Q4 = aVar;
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: j1 */
    public c f1(@NotNull m newOwner, @Nullable u original, @NotNull b.a kind, @Nullable kotlin.reflect.jvm.internal.impl.name.f newName, @NotNull g annotations, @NotNull o0 source) {
        m mVar = newOwner;
        kotlin.jvm.internal.k.f(mVar, "newOwner");
        kotlin.jvm.internal.k.f(kind, "kind");
        kotlin.jvm.internal.k.f(annotations, "annotations");
        o0 o0Var = source;
        kotlin.jvm.internal.k.f(o0Var, "source");
        c it = new c((e) mVar, (l) original, annotations, this.P4, kind, Y(), G(), C(), F(), k1(), o0Var);
        it.n1(l1());
        return it;
    }

    public boolean isExternal() {
        return false;
    }

    public boolean isInline() {
        return false;
    }

    public boolean A() {
        return false;
    }

    public boolean isSuspend() {
        return false;
    }
}
