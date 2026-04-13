package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Collection;
import java.util.List;
import kotlin.collections.o0;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.storage.d;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractDeserializedPackageFragmentProvider.kt */
public abstract class a implements c0 {
    @NotNull
    protected l a;
    private final d<b, b0> b;
    @NotNull
    private final j c;
    @NotNull
    private final u d;
    @NotNull
    private final y e;

    /* access modifiers changed from: protected */
    @Nullable
    public abstract p b(@NotNull b bVar);

    public a(@NotNull j storageManager, @NotNull u finder, @NotNull y moduleDescriptor) {
        k.f(storageManager, "storageManager");
        k.f(finder, "finder");
        k.f(moduleDescriptor, "moduleDescriptor");
        this.c = storageManager;
        this.d = finder;
        this.e = moduleDescriptor;
        this.b = storageManager.g(new C0414a(this));
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final j f() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final u d() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final y e() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final l c() {
        l lVar = this.a;
        if (lVar == null) {
            k.t("components");
        }
        return lVar;
    }

    /* access modifiers changed from: protected */
    public final void g(@NotNull l lVar) {
        k.f(lVar, "<set-?>");
        this.a = lVar;
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.a$a  reason: collision with other inner class name */
    /* compiled from: AbstractDeserializedPackageFragmentProvider.kt */
    public static final class C0414a extends l implements kotlin.jvm.functions.l<b, p> {
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0414a(a aVar) {
            super(1);
            this.this$0 = aVar;
        }

        @Nullable
        public final p invoke(@NotNull b fqName) {
            k.f(fqName, "fqName");
            p $this$apply = this.this$0.b(fqName);
            if ($this$apply == null) {
                return null;
            }
            $this$apply.A0(this.this$0.c());
            return $this$apply;
        }
    }

    @NotNull
    public List<b0> a(@NotNull b fqName) {
        k.f(fqName, "fqName");
        return q.k(this.b.invoke(fqName));
    }

    @NotNull
    public Collection<b> k(@NotNull b fqName, @NotNull kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(fqName, "fqName");
        k.f(nameFilter, "nameFilter");
        return o0.d();
    }
}
