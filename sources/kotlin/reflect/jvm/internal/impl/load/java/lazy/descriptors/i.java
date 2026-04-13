package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.z;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.h;
import kotlin.reflect.jvm.internal.impl.load.java.structure.t;
import kotlin.reflect.jvm.internal.impl.load.java.structure.x;
import kotlin.reflect.jvm.internal.impl.load.kotlin.o;
import kotlin.reflect.jvm.internal.impl.load.kotlin.p;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyJavaPackageFragment.kt */
public final class i extends z {
    static final /* synthetic */ k[] y;
    private final d a1;
    @NotNull
    private final g a2;
    @NotNull
    private final f p0;
    private final f<List<kotlin.reflect.jvm.internal.impl.name.b>> p1;
    private final f p2;
    /* access modifiers changed from: private */
    public final t p3;
    /* access modifiers changed from: private */
    public final h z;

    static {
        Class<i> cls = i.class;
        y = new k[]{a0.h(new u(a0.b(cls), "binaryClasses", "getBinaryClasses$descriptors_jvm()Ljava/util/Map;")), a0.h(new u(a0.b(cls), "partToFacade", "getPartToFacade()Ljava/util/HashMap;"))};
    }

    @NotNull
    public final Map<String, p> C0() {
        return (Map) kotlin.reflect.jvm.internal.impl.storage.i.a(this.p0, this, y[0]);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public i(@NotNull h outerContext, @NotNull t jPackage) {
        super(outerContext.d(), jPackage.e());
        g gVar;
        kotlin.jvm.internal.k.f(outerContext, "outerContext");
        kotlin.jvm.internal.k.f(jPackage, "jPackage");
        this.p3 = jPackage;
        h d = kotlin.reflect.jvm.internal.impl.load.java.lazy.a.d(outerContext, this, (x) null, 0, 6, (Object) null);
        this.z = d;
        this.p0 = d.e().c(new a(this));
        this.a1 = new d(d, jPackage, this);
        this.p1 = d.e().b(new c(this), q.g());
        if (d.a().a().c()) {
            gVar = g.b.b();
        } else {
            gVar = kotlin.reflect.jvm.internal.impl.load.java.lazy.f.a(d, jPackage);
        }
        this.a2 = gVar;
        this.p2 = d.e().c(new b(this));
    }

    /* compiled from: LazyJavaPackageFragment.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Map<String, ? extends p>> {
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(i iVar) {
            super(0);
            this.this$0 = iVar;
        }

        @NotNull
        public final Map<String, p> invoke() {
            kotlin.reflect.jvm.internal.impl.load.kotlin.u m = this.this$0.z.a().m();
            String b = this.this$0.e().b();
            kotlin.jvm.internal.k.b(b, "fqName.asString()");
            Iterable<String> $this$mapNotNullTo$iv$iv = m.a(b);
            Collection destination$iv$iv = new ArrayList();
            for (String partName : $this$mapNotNullTo$iv$iv) {
                kotlin.reflect.jvm.internal.impl.resolve.jvm.c d = kotlin.reflect.jvm.internal.impl.resolve.jvm.c.d(partName);
                kotlin.jvm.internal.k.b(d, "JvmClassName.byInternalName(partName)");
                kotlin.reflect.jvm.internal.impl.name.a classId = kotlin.reflect.jvm.internal.impl.name.a.m(d.e());
                kotlin.jvm.internal.k.b(classId, "ClassId.topLevel(JvmClas…velClassMaybeWithDollars)");
                p it = o.b(this.this$0.z.a().h(), classId);
                Object it$iv$iv = it != null ? kotlin.t.a(partName, it) : null;
                if (it$iv$iv != null) {
                    destination$iv$iv.add(it$iv$iv);
                }
            }
            return l0.o(destination$iv$iv);
        }
    }

    /* compiled from: LazyJavaPackageFragment.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.name.b>> {
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(i iVar) {
            super(0);
            this.this$0 = iVar;
        }

        @NotNull
        public final List<kotlin.reflect.jvm.internal.impl.name.b> invoke() {
            Iterable<t> $this$mapTo$iv$iv = this.this$0.p3.p();
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (t e : $this$mapTo$iv$iv) {
                arrayList.add(e.e());
            }
            return arrayList;
        }
    }

    @NotNull
    public g getAnnotations() {
        return this.a2;
    }

    @NotNull
    public final List<kotlin.reflect.jvm.internal.impl.name.b> H0() {
        return (List) this.p1.invoke();
    }

    @Nullable
    public final e A0(@NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.g jClass) {
        kotlin.jvm.internal.k.f(jClass, "jClass");
        return this.a1.i().J(jClass);
    }

    /* compiled from: LazyJavaPackageFragment.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<HashMap<kotlin.reflect.jvm.internal.impl.resolve.jvm.c, kotlin.reflect.jvm.internal.impl.resolve.jvm.c>> {
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(i iVar) {
            super(0);
            this.this$0 = iVar;
        }

        @NotNull
        public final HashMap<kotlin.reflect.jvm.internal.impl.resolve.jvm.c, kotlin.reflect.jvm.internal.impl.resolve.jvm.c> invoke() {
            HashMap result = new HashMap();
            for (Map.Entry next : this.this$0.C0().entrySet()) {
                kotlin.reflect.jvm.internal.impl.resolve.jvm.c partName = kotlin.reflect.jvm.internal.impl.resolve.jvm.c.d((String) next.getKey());
                kotlin.jvm.internal.k.b(partName, "JvmClassName.byInternalName(partInternalName)");
                kotlin.reflect.jvm.internal.impl.load.kotlin.header.a header = ((p) next.getValue()).b();
                switch (h.a[header.c().ordinal()]) {
                    case 1:
                        String e = header.e();
                        if (e == null) {
                            break;
                        } else {
                            kotlin.reflect.jvm.internal.impl.resolve.jvm.c d = kotlin.reflect.jvm.internal.impl.resolve.jvm.c.d(e);
                            kotlin.jvm.internal.k.b(d, "JvmClassName.byInternalN…: continue@kotlinClasses)");
                            result.put(partName, d);
                            break;
                        }
                    case 2:
                        result.put(partName, partName);
                        break;
                }
            }
            return result;
        }
    }

    @NotNull
    /* renamed from: G0 */
    public d l() {
        return this.a1;
    }

    @NotNull
    public String toString() {
        return "Lazy Java package fragment: " + e();
    }

    @NotNull
    public o0 n() {
        return new kotlin.reflect.jvm.internal.impl.load.kotlin.q(this);
    }
}
