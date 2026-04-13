package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.List;
import java.util.Set;
import kotlin.collections.n0;
import kotlin.collections.o0;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.a;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.f;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.g;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.i;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.h;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.l;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedDescriptorResolver.kt */
public final class e {
    @NotNull
    private static final Set<a.C0373a> a = n0.c(a.C0373a.CLASS);
    private static final Set<a.C0373a> b = o0.g(a.C0373a.FILE_FACADE, a.C0373a.MULTIFILE_CLASS_PART);
    private static final f c = new f(1, 1, 2);
    private static final f d = new f(1, 1, 11);
    /* access modifiers changed from: private */
    @NotNull
    public static final f e = new f(1, 1, 13);
    public static final a f = new a((DefaultConstructorMarker) null);
    @NotNull
    public l g;

    @NotNull
    public final l d() {
        l lVar = this.g;
        if (lVar == null) {
            k.t("components");
        }
        return lVar;
    }

    public final void l(@NotNull d components) {
        k.f(components, "components");
        this.g = components.a();
    }

    /* access modifiers changed from: private */
    public final boolean f() {
        l lVar = this.g;
        if (lVar == null) {
            k.t("components");
        }
        return lVar.g().b();
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.descriptors.e k(@NotNull p kotlinClass) {
        k.f(kotlinClass, "kotlinClass");
        h classData = i(kotlinClass);
        if (classData == null) {
            return null;
        }
        l lVar = this.g;
        if (lVar == null) {
            k.t("components");
        }
        return lVar.f().d(kotlinClass.d(), classData);
    }

    @Nullable
    public final h i(@NotNull p kotlinClass) {
        String[] strings;
        n<g, c> nVar;
        k.f(kotlinClass, "kotlinClass");
        String[] data = j(kotlinClass, a);
        if (data == null || (strings = kotlinClass.b().g()) == null) {
            return null;
        }
        try {
            nVar = i.i(data, strings);
        } catch (InvalidProtocolBufferException e$iv) {
            throw new IllegalStateException("Could not read data from " + kotlinClass.getLocation(), e$iv);
        } catch (Throwable e$iv2) {
            if (f() || kotlinClass.b().d().g()) {
                throw e$iv2;
            }
            nVar = null;
        }
        if (nVar == null) {
            return null;
        }
        return new h(nVar.component1(), nVar.component2(), kotlinClass.b().d(), new r(kotlinClass, e(kotlinClass), h(kotlinClass)));
    }

    @Nullable
    public final kotlin.reflect.jvm.internal.impl.resolve.scopes.h c(@NotNull b0 descriptor, @NotNull p kotlinClass) {
        n<g, kotlin.reflect.jvm.internal.impl.metadata.l> nVar;
        p pVar = kotlinClass;
        k.f(descriptor, "descriptor");
        k.f(pVar, "kotlinClass");
        String[] j = j(pVar, b);
        if (j == null) {
            return null;
        }
        String[] data = j;
        String[] g2 = kotlinClass.b().g();
        if (g2 == null) {
            return null;
        }
        String[] strings = g2;
        try {
            nVar = i.m(data, strings);
        } catch (InvalidProtocolBufferException e2) {
            throw new IllegalStateException("Could not read data from " + kotlinClass.getLocation(), e2);
        } catch (Throwable e$iv) {
            if (f() || kotlinClass.b().d().g()) {
                String[] strArr = strings;
                String[] strArr2 = data;
                throw e$iv;
            }
            nVar = null;
        }
        if (nVar == null) {
            return null;
        }
        g nameResolver = nVar.component1();
        kotlin.reflect.jvm.internal.impl.metadata.l packageProto = nVar.component2();
        j jVar = new j(kotlinClass, packageProto, nameResolver, e(pVar), h(pVar));
        f d2 = kotlinClass.b().d();
        l lVar = this.g;
        if (lVar == null) {
            k.t("components");
        }
        String[] strArr3 = strings;
        String[] strArr4 = data;
        return new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.h(descriptor, packageProto, nameResolver, d2, jVar, lVar, b.INSTANCE);
    }

    /* compiled from: DeserializedDescriptorResolver.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.name.f>> {
        public static final b INSTANCE = new b();

        b() {
            super(0);
        }

        @NotNull
        public final List<kotlin.reflect.jvm.internal.impl.name.f> invoke() {
            return q.g();
        }
    }

    private final t<f> e(@NotNull p $this$incompatibility) {
        if (f() || $this$incompatibility.b().d().g()) {
            return null;
        }
        return new t<>($this$incompatibility.b().d(), f.g, $this$incompatibility.getLocation(), $this$incompatibility.d());
    }

    private final boolean h(@NotNull p $this$isPreReleaseInvisible) {
        l lVar = this.g;
        if (lVar == null) {
            k.t("components");
        }
        return (lVar.g().c() && ($this$isPreReleaseInvisible.b().h() || k.a($this$isPreReleaseInvisible.b().d(), c))) || g($this$isPreReleaseInvisible);
    }

    private final boolean g(@NotNull p $this$isCompiledWith13M1) {
        l lVar = this.g;
        if (lVar == null) {
            k.t("components");
        }
        return !lVar.g().b() && $this$isCompiledWith13M1.b().h() && k.a($this$isCompiledWith13M1.b().d(), d);
    }

    private final String[] j(p kotlinClass, Set<? extends a.C0373a> expectedKinds) {
        kotlin.reflect.jvm.internal.impl.load.kotlin.header.a header = kotlinClass.b();
        String[] a2 = header.a();
        if (a2 == null) {
            a2 = header.b();
        }
        if (a2 != null) {
            String[] strArr = a2;
            if (expectedKinds.contains(header.c())) {
                return a2;
            }
        }
        return null;
    }

    /* compiled from: DeserializedDescriptorResolver.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final f a() {
            return e.e;
        }
    }
}
