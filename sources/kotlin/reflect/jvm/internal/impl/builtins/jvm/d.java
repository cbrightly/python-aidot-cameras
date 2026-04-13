package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.collections.n0;
import kotlin.collections.p;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.h;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.storage.i;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JvmBuiltInClassDescriptorFactory.kt */
public final class d implements kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b {
    static final /* synthetic */ k[] a = {a0.h(new u(a0.b(d.class), "cloneable", "getCloneable()Lorg/jetbrains/kotlin/descriptors/impl/ClassDescriptorImpl;"))};
    /* access modifiers changed from: private */
    public static final kotlin.reflect.jvm.internal.impl.name.b b = g.b;
    /* access modifiers changed from: private */
    public static final f c;
    /* access modifiers changed from: private */
    @NotNull
    public static final kotlin.reflect.jvm.internal.impl.name.a d;
    public static final b e = new b((DefaultConstructorMarker) null);
    private final kotlin.reflect.jvm.internal.impl.storage.f f;
    /* access modifiers changed from: private */
    public final y g;
    /* access modifiers changed from: private */
    public final l<y, m> h;

    private final h i() {
        return (h) i.a(this.f, this, a[0]);
    }

    public d(@NotNull j storageManager, @NotNull y moduleDescriptor, @NotNull l<? super y, ? extends m> computeContainingDeclaration) {
        kotlin.jvm.internal.k.f(storageManager, "storageManager");
        kotlin.jvm.internal.k.f(moduleDescriptor, "moduleDescriptor");
        kotlin.jvm.internal.k.f(computeContainingDeclaration, "computeContainingDeclaration");
        this.g = moduleDescriptor;
        this.h = computeContainingDeclaration;
        this.f = storageManager.c(new c(this, storageManager));
    }

    /* compiled from: JvmBuiltInClassDescriptorFactory.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<y, kotlin.reflect.jvm.internal.impl.builtins.b> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.builtins.b invoke(@NotNull y module) {
            kotlin.jvm.internal.k.f(module, "module");
            kotlin.reflect.jvm.internal.impl.name.b g = d.b;
            kotlin.jvm.internal.k.b(g, "KOTLIN_FQ_NAME");
            Iterable $this$filterIsInstanceTo$iv$iv = module.e0(g).b0();
            ArrayList arrayList = new ArrayList();
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (element$iv$iv instanceof kotlin.reflect.jvm.internal.impl.builtins.b) {
                    arrayList.add(element$iv$iv);
                }
            }
            return (kotlin.reflect.jvm.internal.impl.builtins.b) kotlin.collections.y.S(arrayList);
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ d(j jVar, y yVar, l lVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jVar, yVar, (i & 4) != 0 ? a.INSTANCE : lVar);
    }

    /* compiled from: JvmBuiltInClassDescriptorFactory.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<h> {
        final /* synthetic */ j $storageManager;
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(d dVar, j jVar) {
            super(0);
            this.this$0 = dVar;
            this.$storageManager = jVar;
        }

        @NotNull
        public final h invoke() {
            h $this$apply = new h((m) this.this$0.h.invoke(this.this$0.g), d.c, w.ABSTRACT, kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE, p.b(this.this$0.g.j().j()), o0.a, false, this.$storageManager);
            $this$apply.l0(new a(this.$storageManager, $this$apply), kotlin.collections.o0.d(), (kotlin.reflect.jvm.internal.impl.descriptors.d) null);
            return $this$apply;
        }
    }

    public boolean b(@NotNull kotlin.reflect.jvm.internal.impl.name.b packageFqName, @NotNull f name) {
        kotlin.jvm.internal.k.f(packageFqName, "packageFqName");
        kotlin.jvm.internal.k.f(name, "name");
        return kotlin.jvm.internal.k.a(name, c) && kotlin.jvm.internal.k.a(packageFqName, b);
    }

    @Nullable
    public e c(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
        kotlin.jvm.internal.k.f(classId, "classId");
        if (kotlin.jvm.internal.k.a(classId, d)) {
            return i();
        }
        return null;
    }

    @NotNull
    public Collection<e> a(@NotNull kotlin.reflect.jvm.internal.impl.name.b packageFqName) {
        kotlin.jvm.internal.k.f(packageFqName, "packageFqName");
        if (kotlin.jvm.internal.k.a(packageFqName, b)) {
            return n0.c(i());
        }
        return kotlin.collections.o0.d();
    }

    /* compiled from: JvmBuiltInClassDescriptorFactory.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.name.a a() {
            return d.d;
        }
    }

    static {
        g.e eVar = g.h;
        f i = eVar.c.i();
        kotlin.jvm.internal.k.b(i, "KotlinBuiltIns.FQ_NAMES.cloneable.shortName()");
        c = i;
        kotlin.reflect.jvm.internal.impl.name.a m = kotlin.reflect.jvm.internal.impl.name.a.m(eVar.c.l());
        kotlin.jvm.internal.k.b(m, "ClassId.topLevel(KotlinB…NAMES.cloneable.toSafe())");
        d = m;
    }
}
