package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.metadata.m;
import kotlin.reflect.jvm.internal.impl.metadata.o;
import kotlin.reflect.jvm.internal.impl.metadata.p;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedPackageFragmentImpl.kt */
public abstract class q extends p {
    private m a1;
    private final kotlin.reflect.jvm.internal.impl.metadata.deserialization.a a2;
    @NotNull
    private final z p0;
    private h p1;
    /* access modifiers changed from: private */
    public final e p2;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.metadata.deserialization.e z;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public q(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName, @NotNull j storageManager, @NotNull y module, @NotNull m proto, @NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.a metadataVersion, @Nullable e containerSource) {
        super(fqName, storageManager, module);
        k.f(fqName, "fqName");
        k.f(storageManager, "storageManager");
        k.f(module, "module");
        k.f(proto, "proto");
        k.f(metadataVersion, "metadataVersion");
        this.a2 = metadataVersion;
        this.p2 = containerSource;
        p strings = proto.getStrings();
        k.b(strings, "proto.strings");
        o qualifiedNames = proto.getQualifiedNames();
        k.b(qualifiedNames, "proto.qualifiedNames");
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.e eVar = new kotlin.reflect.jvm.internal.impl.metadata.deserialization.e(strings, qualifiedNames);
        this.z = eVar;
        this.p0 = new z(proto, eVar, metadataVersion, new a(this));
        this.a1 = proto;
    }

    /* compiled from: DeserializedPackageFragmentImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.a, o0> {
        final /* synthetic */ q this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(q qVar) {
            super(1);
            this.this$0 = qVar;
        }

        @NotNull
        public final o0 invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.a it) {
            k.f(it, "it");
            e C0 = this.this$0.p2;
            if (C0 != null) {
                return C0;
            }
            o0 o0Var = o0.a;
            k.b(o0Var, "SourceElement.NO_SOURCE");
            return o0Var;
        }
    }

    @NotNull
    /* renamed from: G0 */
    public z f0() {
        return this.p0;
    }

    public void A0(@NotNull l components) {
        k.f(components, "components");
        m proto = this.a1;
        if (proto != null) {
            this.a1 = null;
            kotlin.reflect.jvm.internal.impl.metadata.l lVar = proto.getPackage();
            k.b(lVar, "proto.`package`");
            this.p1 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.h(this, lVar, this.z, this.a2, this.p2, components, new b(this));
            return;
        }
        throw new IllegalStateException("Repeated call to DeserializedPackageFragmentImpl::initialize".toString());
    }

    /* compiled from: DeserializedPackageFragmentImpl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<List<? extends f>> {
        final /* synthetic */ q this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(q qVar) {
            super(0);
            this.this$0 = qVar;
        }

        @NotNull
        public final List<f> invoke() {
            Iterable $this$filterTo$iv$iv = this.this$0.f0().b();
            ArrayList arrayList = new ArrayList();
            for (T next : $this$filterTo$iv$iv) {
                kotlin.reflect.jvm.internal.impl.name.a classId = (kotlin.reflect.jvm.internal.impl.name.a) next;
                if (!classId.l() && !j.b.a().contains(classId)) {
                    arrayList.add(next);
                }
            }
            Iterable<kotlin.reflect.jvm.internal.impl.name.a> $this$mapTo$iv$iv = arrayList;
            ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (kotlin.reflect.jvm.internal.impl.name.a it : $this$mapTo$iv$iv) {
                arrayList2.add(it.j());
            }
            return arrayList2;
        }
    }

    @NotNull
    public h l() {
        h hVar = this.p1;
        if (hVar == null) {
            k.t("_memberScope");
        }
        return hVar;
    }
}
