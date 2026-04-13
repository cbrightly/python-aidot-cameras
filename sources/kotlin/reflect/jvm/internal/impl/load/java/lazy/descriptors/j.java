package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.o0;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.b;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.h;
import kotlin.reflect.jvm.internal.impl.load.java.m;
import kotlin.reflect.jvm.internal.impl.load.java.structure.a0;
import kotlin.reflect.jvm.internal.impl.load.java.structure.t;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.a;
import kotlin.reflect.jvm.internal.impl.load.kotlin.n;
import kotlin.reflect.jvm.internal.impl.load.kotlin.o;
import kotlin.reflect.jvm.internal.impl.load.kotlin.p;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.storage.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LazyJavaPackageScope.kt */
public final class j extends m {
    private final g<Set<String>> n;
    private final kotlin.reflect.jvm.internal.impl.storage.d<a, e> o;
    private final t p;
    @NotNull
    private final i q;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public j(@NotNull h c2, @NotNull t jPackage, @NotNull i ownerDescriptor) {
        super(c2);
        k.f(c2, "c");
        k.f(jPackage, "jPackage");
        k.f(ownerDescriptor, "ownerDescriptor");
        this.p = jPackage;
        this.q = ownerDescriptor;
        this.n = c2.e().e(new d(this, c2));
        this.o = c2.e().g(new c(this, c2));
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: L */
    public i y() {
        return this.q;
    }

    /* compiled from: LazyJavaPackageScope.kt */
    public static final class d extends l implements kotlin.jvm.functions.a<Set<? extends String>> {
        final /* synthetic */ h $c;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(j jVar, h hVar) {
            super(0);
            this.this$0 = jVar;
            this.$c = hVar;
        }

        @Nullable
        public final Set<String> invoke() {
            return this.$c.a().d().c(this.this$0.y().e());
        }
    }

    /* compiled from: LazyJavaPackageScope.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<a, e> {
        final /* synthetic */ h $c;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(j jVar, h hVar) {
            super(1);
            this.this$0 = jVar;
            this.$c = hVar;
        }

        @Nullable
        public final e invoke(@NotNull a request) {
            n.a kotlinClassOrClassFileContent;
            byte[] bArr;
            k.f(request, Progress.REQUEST);
            kotlin.reflect.jvm.internal.impl.name.a requestClassId = new kotlin.reflect.jvm.internal.impl.name.a(this.this$0.y().e(), request.b());
            if (request.a() != null) {
                kotlinClassOrClassFileContent = this.$c.a().h().a(request.a());
            } else {
                kotlinClassOrClassFileContent = this.$c.a().h().c(requestClassId);
            }
            p kotlinBinaryClass = kotlinClassOrClassFileContent != null ? kotlinClassOrClassFileContent.a() : null;
            kotlin.reflect.jvm.internal.impl.name.a classId = kotlinBinaryClass != null ? kotlinBinaryClass.d() : null;
            if (classId != null && (classId.l() || classId.k())) {
                return null;
            }
            b kotlinResult = this.this$0.M(kotlinBinaryClass);
            if (kotlinResult instanceof b.a) {
                return ((b.a) kotlinResult).a();
            }
            if (kotlinResult instanceof b.c) {
                return null;
            }
            if (kotlinResult instanceof b.C0363b) {
                kotlin.reflect.jvm.internal.impl.load.java.structure.g javaClass = request.a();
                if (javaClass == null) {
                    m d = this.$c.a().d();
                    if (kotlinClassOrClassFileContent != null) {
                        Object $this$safeAs$iv = kotlinClassOrClassFileContent;
                        n.a.C0378a aVar = (n.a.C0378a) (!($this$safeAs$iv instanceof n.a.C0378a) ? null : $this$safeAs$iv);
                        if (aVar != null) {
                            bArr = aVar.b();
                            javaClass = d.a(new m.a(requestClassId, bArr, (kotlin.reflect.jvm.internal.impl.load.java.structure.g) null, 4, (DefaultConstructorMarker) null));
                        }
                    }
                    bArr = null;
                    javaClass = d.a(new m.a(requestClassId, bArr, (kotlin.reflect.jvm.internal.impl.load.java.structure.g) null, 4, (DefaultConstructorMarker) null));
                }
                if ((javaClass != null ? javaClass.E() : null) != a0.BINARY) {
                    kotlin.reflect.jvm.internal.impl.name.b actualFqName = javaClass != null ? javaClass.e() : null;
                    if (actualFqName == null || actualFqName.d() || (!k.a(actualFqName.e(), this.this$0.y().e()))) {
                        return null;
                    }
                    f p1 = new f(this.$c, this.this$0.y(), javaClass, (e) null, 8, (DefaultConstructorMarker) null);
                    this.$c.a().e().a(p1);
                    return p1;
                }
                throw new IllegalStateException("Couldn't find kotlin binary class for light class created by kotlin binary file\n" + "JavaClass: " + javaClass + 10 + "ClassId: " + requestClassId + 10 + "findKotlinClass(JavaClass) = " + o.a(this.$c.a().h(), javaClass) + 10 + "findKotlinClass(ClassId) = " + o.b(this.$c.a().h(), requestClassId) + 10);
            }
            throw new NoWhenBranchMatchedException();
        }
    }

    /* compiled from: LazyJavaPackageScope.kt */
    public static abstract class b {

        /* compiled from: LazyJavaPackageScope.kt */
        public static final class a extends b {
            @NotNull
            private final e a;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(@NotNull e descriptor) {
                super((DefaultConstructorMarker) null);
                k.f(descriptor, "descriptor");
                this.a = descriptor;
            }

            @NotNull
            public final e a() {
                return this.a;
            }
        }

        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.j$b$b  reason: collision with other inner class name */
        /* compiled from: LazyJavaPackageScope.kt */
        public static final class C0363b extends b {
            public static final C0363b a = new C0363b();

            private C0363b() {
                super((DefaultConstructorMarker) null);
            }
        }

        /* compiled from: LazyJavaPackageScope.kt */
        public static final class c extends b {
            public static final c a = new c();

            private c() {
                super((DefaultConstructorMarker) null);
            }
        }
    }

    /* access modifiers changed from: private */
    public final b M(p kotlinClass) {
        if (kotlinClass == null) {
            return b.C0363b.a;
        }
        if (kotlinClass.b().c() != a.C0373a.CLASS) {
            return b.c.a;
        }
        e descriptor = t().a().b().k(kotlinClass);
        return descriptor != null ? new b.a(descriptor) : b.C0363b.a;
    }

    /* compiled from: LazyJavaPackageScope.kt */
    public static final class a {
        @NotNull
        private final f a;
        @Nullable
        private final kotlin.reflect.jvm.internal.impl.load.java.structure.g b;

        public a(@NotNull f name, @Nullable kotlin.reflect.jvm.internal.impl.load.java.structure.g javaClass) {
            k.f(name, "name");
            this.a = name;
            this.b = javaClass;
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.load.java.structure.g a() {
            return this.b;
        }

        @NotNull
        public final f b() {
            return this.a;
        }

        public boolean equals(@Nullable Object other) {
            return (other instanceof a) && k.a(this.a, ((a) other).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }

    @Nullable
    /* renamed from: K */
    public e c(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return I(name, (kotlin.reflect.jvm.internal.impl.load.java.structure.g) null);
    }

    private final e I(f name, kotlin.reflect.jvm.internal.impl.load.java.structure.g javaClass) {
        if (!kotlin.reflect.jvm.internal.impl.name.h.b(name)) {
            return null;
        }
        Set knownClassNamesInPackage = (Set) this.n.invoke();
        if (javaClass != null || knownClassNamesInPackage == null || knownClassNamesInPackage.contains(name.b())) {
            return this.o.invoke(new a(name, javaClass));
        }
        return null;
    }

    @Nullable
    public final e J(@NotNull kotlin.reflect.jvm.internal.impl.load.java.structure.g javaClass) {
        k.f(javaClass, "javaClass");
        return I(javaClass.getName(), javaClass);
    }

    @NotNull
    public Collection<i0> e(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return q.g();
    }

    /* access modifiers changed from: protected */
    @NotNull
    public b m() {
        return b.a.a;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Set<f> j(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @Nullable kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        kotlin.reflect.jvm.internal.impl.resolve.scopes.d dVar = kindFilter;
        k.f(dVar, "kindFilter");
        if (!dVar.a(kotlin.reflect.jvm.internal.impl.resolve.scopes.d.x.e())) {
            return o0.d();
        }
        Iterable<String> knownClassNamesInPackage = (Set) this.n.invoke();
        if (knownClassNamesInPackage != null) {
            HashSet hashSet = new HashSet();
            for (String it : knownClassNamesInPackage) {
                hashSet.add(f.f(it));
            }
            return hashSet;
        }
        Iterable<kotlin.reflect.jvm.internal.impl.load.java.structure.g> $this$forEach$iv$iv = this.p.y(nameFilter != null ? nameFilter : kotlin.reflect.jvm.internal.impl.utils.d.a());
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (kotlin.reflect.jvm.internal.impl.load.java.structure.g klass : $this$forEach$iv$iv) {
            Object it$iv = klass.E() == a0.SOURCE ? null : klass.getName();
            if (it$iv != null) {
                linkedHashSet.add(it$iv);
            }
        }
        return linkedHashSet;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Set<f> l(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @Nullable kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        return o0.d();
    }

    /* access modifiers changed from: protected */
    public void o(@NotNull Collection<n0> result, @NotNull f name) {
        k.f(result, "result");
        k.f(name, "name");
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Set<f> q(@NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d kindFilter, @Nullable kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        return o0.d();
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0036 A[SYNTHETIC] */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Collection<kotlin.reflect.jvm.internal.impl.descriptors.m> d(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.resolve.scopes.d r12, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, java.lang.Boolean> r13) {
        /*
            r11 = this;
            java.lang.String r0 = "kindFilter"
            kotlin.jvm.internal.k.f(r12, r0)
            java.lang.String r0 = "nameFilter"
            kotlin.jvm.internal.k.f(r13, r0)
            kotlin.reflect.jvm.internal.impl.resolve.scopes.d$a r0 = kotlin.reflect.jvm.internal.impl.resolve.scopes.d.x
            int r1 = r0.c()
            int r0 = r0.e()
            r0 = r0 | r1
            boolean r0 = r12.a(r0)
            if (r0 != 0) goto L_0x0020
            java.util.List r0 = kotlin.collections.q.g()
            goto L_0x006b
        L_0x0020:
            kotlin.reflect.jvm.internal.impl.storage.f r0 = r11.s()
            java.lang.Object r0 = r0.invoke()
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            r1 = 0
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r3 = r0
            r4 = 0
            java.util.Iterator r5 = r3.iterator()
        L_0x0036:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0069
            java.lang.Object r6 = r5.next()
            r7 = r6
            kotlin.reflect.jvm.internal.impl.descriptors.m r7 = (kotlin.reflect.jvm.internal.impl.descriptors.m) r7
            r8 = 0
            boolean r9 = r7 instanceof kotlin.reflect.jvm.internal.impl.descriptors.e
            if (r9 == 0) goto L_0x0062
            r9 = r7
            kotlin.reflect.jvm.internal.impl.descriptors.e r9 = (kotlin.reflect.jvm.internal.impl.descriptors.e) r9
            kotlin.reflect.jvm.internal.impl.name.f r9 = r9.getName()
            java.lang.String r10 = "it.name"
            kotlin.jvm.internal.k.b(r9, r10)
            java.lang.Object r9 = r13.invoke(r9)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x0062
            r9 = 1
            goto L_0x0063
        L_0x0062:
            r9 = 0
        L_0x0063:
            if (r9 == 0) goto L_0x0036
            r2.add(r6)
            goto L_0x0036
        L_0x0069:
            r0 = r2
        L_0x006b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.j.d(kotlin.reflect.jvm.internal.impl.resolve.scopes.d, kotlin.jvm.functions.l):java.util.Collection");
    }
}
