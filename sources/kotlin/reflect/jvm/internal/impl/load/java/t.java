package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.p0;
import kotlin.collections.q;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmAnnotationNames.kt */
public final class t {
    @NotNull
    private static final List<b> a;
    @NotNull
    private static final b b;
    @NotNull
    private static final b c = new b("javax.annotation.CheckForNull");
    @NotNull
    private static final List<b> d;
    @NotNull
    private static final b e;
    @NotNull
    private static final b f;
    @NotNull
    private static final b g;
    @NotNull
    private static final b h;
    @NotNull
    private static final Set<b> i;
    @NotNull
    private static final List<b> j = q.j(s.g, s.h);
    @NotNull
    private static final List<b> k = q.j(s.f, s.i);

    static {
        List<b> j2 = q.j(s.e, new b("androidx.annotation.Nullable"), new b("androidx.annotation.Nullable"), new b("android.annotation.Nullable"), new b("com.android.annotations.Nullable"), new b("org.eclipse.jdt.annotation.Nullable"), new b("org.checkerframework.checker.nullness.qual.Nullable"), new b("javax.annotation.Nullable"), new b("javax.annotation.CheckForNull"), new b("edu.umd.cs.findbugs.annotations.CheckForNull"), new b("edu.umd.cs.findbugs.annotations.Nullable"), new b("edu.umd.cs.findbugs.annotations.PossiblyNull"), new b("io.reactivex.annotations.Nullable"));
        a = j2;
        b bVar = new b("javax.annotation.Nonnull");
        b = bVar;
        List<b> j3 = q.j(s.d, new b("edu.umd.cs.findbugs.annotations.NonNull"), new b("androidx.annotation.NonNull"), new b("androidx.annotation.NonNull"), new b("android.annotation.NonNull"), new b("com.android.annotations.NonNull"), new b("org.eclipse.jdt.annotation.NonNull"), new b("org.checkerframework.checker.nullness.qual.NonNull"), new b("lombok.NonNull"), new b("io.reactivex.annotations.NonNull"));
        d = j3;
        b bVar2 = new b("org.checkerframework.checker.nullness.compatqual.NullableDecl");
        e = bVar2;
        b bVar3 = new b("org.checkerframework.checker.nullness.compatqual.NonNullDecl");
        f = bVar3;
        b bVar4 = new b("androidx.annotation.RecentlyNullable");
        g = bVar4;
        b bVar5 = new b("androidx.annotation.RecentlyNonNull");
        h = bVar5;
        i = p0.j(p0.j(p0.j(p0.j(p0.i(p0.j(p0.i(new LinkedHashSet(), j2), bVar), j3), bVar2), bVar3), bVar4), bVar5);
    }

    @NotNull
    public static final List<b> i() {
        return a;
    }

    @NotNull
    public static final b f() {
        return b;
    }

    @NotNull
    public static final b e() {
        return c;
    }

    @NotNull
    public static final List<b> h() {
        return d;
    }

    @NotNull
    public static final b d() {
        return e;
    }

    @NotNull
    public static final b c() {
        return f;
    }

    @NotNull
    public static final b b() {
        return g;
    }

    @NotNull
    public static final b a() {
        return h;
    }

    @NotNull
    public static final List<b> j() {
        return j;
    }

    @NotNull
    public static final List<b> g() {
        return k;
    }
}
