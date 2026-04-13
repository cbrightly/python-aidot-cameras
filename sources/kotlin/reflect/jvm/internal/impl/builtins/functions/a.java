package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.o0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.functions.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BuiltInFictitiousFunctionClassFactory.kt */
public final class a implements kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b {
    public static final C0343a a = new C0343a((DefaultConstructorMarker) null);
    private final j b;
    private final y c;

    public a(@NotNull j storageManager, @NotNull y module) {
        k.f(storageManager, "storageManager");
        k.f(module, "module");
        this.b = storageManager;
        this.c = module;
    }

    /* compiled from: BuiltInFictitiousFunctionClassFactory.kt */
    public static final class b {
        @NotNull
        private final b.d a;
        private final int b;

        @NotNull
        public final b.d a() {
            return this.a;
        }

        public final int b() {
            return this.b;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return k.a(this.a, bVar.a) && this.b == bVar.b;
        }

        public int hashCode() {
            b.d dVar = this.a;
            return ((dVar != null ? dVar.hashCode() : 0) * 31) + this.b;
        }

        @NotNull
        public String toString() {
            return "KindWithArity(kind=" + this.a + ", arity=" + this.b + ")";
        }

        public b(@NotNull b.d kind, int arity) {
            k.f(kind, "kind");
            this.a = kind;
            this.b = arity;
        }

        @NotNull
        public final b.d c() {
            return this.a;
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.builtins.functions.a$a  reason: collision with other inner class name */
    /* compiled from: BuiltInFictitiousFunctionClassFactory.kt */
    public static final class C0343a {
        private C0343a() {
        }

        public /* synthetic */ C0343a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* access modifiers changed from: private */
        public final b c(String className, kotlin.reflect.jvm.internal.impl.name.b packageFqName) {
            b.d kind = b.d.Companion.a(packageFqName, className);
            if (kind == null) {
                return null;
            }
            int length = kind.getClassNamePrefix().length();
            if (className != null) {
                String substring = className.substring(length);
                k.b(substring, "(this as java.lang.String).substring(startIndex)");
                Integer d = d(substring);
                if (d != null) {
                    return new b(kind, d.intValue());
                }
                return null;
            }
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }

        @Nullable
        public final b.d b(@NotNull String className, @NotNull kotlin.reflect.jvm.internal.impl.name.b packageFqName) {
            k.f(className, "className");
            k.f(packageFqName, "packageFqName");
            b c = c(className, packageFqName);
            if (c != null) {
                return c.c();
            }
            return null;
        }

        private final Integer d(String s) {
            if (s.length() == 0) {
                return null;
            }
            int result = 0;
            int length = s.length();
            for (int i = 0; i < length; i++) {
                int d = s.charAt(i) - '0';
                if (d < 0 || 9 < d) {
                    return null;
                }
                result = (result * 10) + d;
            }
            return Integer.valueOf(result);
        }
    }

    public boolean b(@NotNull kotlin.reflect.jvm.internal.impl.name.b packageFqName, @NotNull f name) {
        k.f(packageFqName, "packageFqName");
        k.f(name, "name");
        String string = name.b();
        k.b(string, "name.asString()");
        if ((w.N(string, "Function", false, 2, (Object) null) || w.N(string, "KFunction", false, 2, (Object) null) || w.N(string, "SuspendFunction", false, 2, (Object) null) || w.N(string, "KSuspendFunction", false, 2, (Object) null)) && a.c(string, packageFqName) != null) {
            return true;
        }
        return false;
    }

    @Nullable
    public e c(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId) {
        k.f(classId, "classId");
        if (classId.k() || classId.l()) {
            return null;
        }
        String className = classId.i().b();
        k.b(className, "classId.relativeClassName.asString()");
        if (!x.S(className, "Function", false, 2, (Object) null)) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.name.b packageFqName = classId.h();
        k.b(packageFqName, "classId.packageFqName");
        b a2 = a.c(className, packageFqName);
        if (a2 == null) {
            return null;
        }
        b.d kind = a2.a();
        int arity = a2.b();
        Iterable $this$filterIsInstanceTo$iv$iv = this.c.e0(packageFqName).b0();
        List arrayList = new ArrayList();
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (element$iv$iv instanceof kotlin.reflect.jvm.internal.impl.builtins.b) {
                arrayList.add(element$iv$iv);
            }
        }
        List builtInsFragments = arrayList;
        ArrayList arrayList2 = new ArrayList();
        for (Object element$iv$iv2 : builtInsFragments) {
            if (element$iv$iv2 instanceof kotlin.reflect.jvm.internal.impl.builtins.e) {
                arrayList2.add(element$iv$iv2);
            }
        }
        kotlin.reflect.jvm.internal.impl.builtins.b containingPackageFragment = (kotlin.reflect.jvm.internal.impl.builtins.e) kotlin.collections.y.U(arrayList2);
        if (containingPackageFragment == null) {
            containingPackageFragment = (kotlin.reflect.jvm.internal.impl.builtins.b) kotlin.collections.y.S(builtInsFragments);
        }
        return new b(this.b, containingPackageFragment, kind, arity);
    }

    @NotNull
    public Collection<e> a(@NotNull kotlin.reflect.jvm.internal.impl.name.b packageFqName) {
        k.f(packageFqName, "packageFqName");
        return o0.d();
    }
}
