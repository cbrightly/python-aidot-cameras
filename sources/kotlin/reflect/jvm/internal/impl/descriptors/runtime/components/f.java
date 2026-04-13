package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.b;
import kotlin.reflect.jvm.internal.impl.load.kotlin.p;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectKotlinClass.kt */
public final class f implements p {
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final Class<?> b;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.load.kotlin.header.a c;

    private f(Class<?> klass, kotlin.reflect.jvm.internal.impl.load.kotlin.header.a classHeader) {
        this.b = klass;
        this.c = classHeader;
    }

    public /* synthetic */ f(Class klass, kotlin.reflect.jvm.internal.impl.load.kotlin.header.a classHeader, DefaultConstructorMarker $constructor_marker) {
        this(klass, classHeader);
    }

    @NotNull
    public final Class<?> e() {
        return this.b;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.load.kotlin.header.a b() {
        return this.c;
    }

    /* compiled from: ReflectKotlinClass.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final f a(@NotNull Class<?> klass) {
            k.f(klass, "klass");
            b headerReader = new b();
            c.a.b(klass, headerReader);
            kotlin.reflect.jvm.internal.impl.load.kotlin.header.a m = headerReader.m();
            if (m != null) {
                return new f(klass, m, (DefaultConstructorMarker) null);
            }
            return null;
        }
    }

    @NotNull
    public String getLocation() {
        StringBuilder sb = new StringBuilder();
        String name = this.b.getName();
        k.b(name, "klass.name");
        sb.append(w.G(name, '.', '/', false, 4, (Object) null));
        sb.append(".class");
        return sb.toString();
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.name.a d() {
        return kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.b(this.b);
    }

    public void c(@NotNull p.c visitor, @Nullable byte[] cachedContents) {
        k.f(visitor, "visitor");
        c.a.b(this.b, visitor);
    }

    public void a(@NotNull p.d visitor, @Nullable byte[] cachedContents) {
        k.f(visitor, "visitor");
        c.a.i(this.b, visitor);
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof f) && k.a(this.b, ((f) other).b);
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return getClass().getName() + ": " + this.b;
    }
}
