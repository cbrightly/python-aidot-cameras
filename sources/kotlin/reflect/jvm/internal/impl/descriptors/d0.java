package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;

/* compiled from: PackageFragmentProviderImpl.kt */
public final class d0 implements c0 {
    private final Collection<b0> a;

    /* compiled from: PackageFragmentProviderImpl.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.name.b, Boolean> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.name.b $fqName;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(kotlin.reflect.jvm.internal.impl.name.b bVar) {
            super(1);
            this.$fqName = bVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((kotlin.reflect.jvm.internal.impl.name.b) obj));
        }

        public final boolean invoke(@NotNull kotlin.reflect.jvm.internal.impl.name.b it) {
            k.f(it, "it");
            return !it.d() && k.a(it.e(), this.$fqName);
        }
    }

    public d0(@NotNull Collection<? extends b0> packageFragments) {
        k.f(packageFragments, "packageFragments");
        this.a = packageFragments;
    }

    @NotNull
    public List<b0> a(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
        k.f(fqName, "fqName");
        Iterable $this$filterTo$iv$iv = this.a;
        ArrayList arrayList = new ArrayList();
        for (T next : $this$filterTo$iv$iv) {
            if (k.a(((b0) next).e(), fqName)) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    /* compiled from: PackageFragmentProviderImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<b0, kotlin.reflect.jvm.internal.impl.name.b> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.b invoke(@NotNull b0 it) {
            k.f(it, "it");
            return it.e();
        }
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.name.b> k(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName, @NotNull kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(fqName, "fqName");
        k.f(nameFilter, "nameFilter");
        return o.C(o.o(o.w(y.L(this.a), a.INSTANCE), new b(fqName)));
    }
}
