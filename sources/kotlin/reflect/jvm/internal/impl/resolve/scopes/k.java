package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.incremental.components.b;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.i;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StaticScopeForKotlinEnum.kt */
public final class k extends i {
    static final /* synthetic */ kotlin.reflect.k[] b = {a0.h(new u(a0.b(k.class), "functions", "getFunctions()Ljava/util/List;"))};
    private final f c;
    /* access modifiers changed from: private */
    public final e d;

    private final List<n0> k() {
        return (List) i.a(this.c, this, b[0]);
    }

    public k(@NotNull j storageManager, @NotNull e containingClass) {
        kotlin.jvm.internal.k.f(storageManager, "storageManager");
        kotlin.jvm.internal.k.f(containingClass, "containingClass");
        this.d = containingClass;
        if (containingClass.h() == kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_CLASS) {
            this.c = storageManager.c(new a(this));
            return;
        }
        throw new AssertionError("Class should be an enum: " + containingClass);
    }

    public /* bridge */ /* synthetic */ h c(kotlin.reflect.jvm.internal.impl.name.f fVar, b bVar) {
        return (h) h(fVar, bVar);
    }

    @Nullable
    public Void h(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        return null;
    }

    /* compiled from: StaticScopeForKotlinEnum.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<List<? extends n0>> {
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(k kVar) {
            super(0);
            this.this$0 = kVar;
        }

        @NotNull
        public final List<n0> invoke() {
            return q.j(kotlin.reflect.jvm.internal.impl.resolve.b.d(this.this$0.d), kotlin.reflect.jvm.internal.impl.resolve.b.e(this.this$0.d));
        }
    }

    @NotNull
    /* renamed from: i */
    public List<n0> d(@NotNull d kindFilter, @NotNull kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
        kotlin.jvm.internal.k.f(kindFilter, "kindFilter");
        kotlin.jvm.internal.k.f(nameFilter, "nameFilter");
        return k();
    }

    @NotNull
    /* renamed from: j */
    public ArrayList<n0> b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        Iterable $this$filterTo$iv = k();
        ArrayList<n0> arrayList = new ArrayList<>(1);
        for (T next : $this$filterTo$iv) {
            if (kotlin.jvm.internal.k.a(((n0) next).getName(), name)) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }
}
