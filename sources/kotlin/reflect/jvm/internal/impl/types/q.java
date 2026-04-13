package kotlin.reflect.jvm.internal.impl.types;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DisjointKeysUnionTypeSubstitution.kt */
public final class q extends z0 {
    public static final a c = new a((DefaultConstructorMarker) null);
    private final z0 d;
    private final z0 e;

    @NotNull
    public static final z0 h(@NotNull z0 z0Var, @NotNull z0 z0Var2) {
        return c.a(z0Var, z0Var2);
    }

    private q(z0 first, z0 second) {
        this.d = first;
        this.e = second;
    }

    public /* synthetic */ q(z0 first, z0 second, DefaultConstructorMarker $constructor_marker) {
        this(first, second);
    }

    /* compiled from: DisjointKeysUnionTypeSubstitution.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final z0 a(@NotNull z0 first, @NotNull z0 second) {
            k.f(first, "first");
            k.f(second, "second");
            if (first.f()) {
                return second;
            }
            if (second.f()) {
                return first;
            }
            return new q(first, second, (DefaultConstructorMarker) null);
        }
    }

    @Nullable
    public w0 e(@NotNull b0 key) {
        k.f(key, CacheEntity.KEY);
        w0 e2 = this.d.e(key);
        return e2 != null ? e2 : this.e.e(key);
    }

    @NotNull
    public b0 g(@NotNull b0 topLevelType, @NotNull h1 position) {
        k.f(topLevelType, "topLevelType");
        k.f(position, "position");
        return this.e.g(this.d.g(topLevelType, position), position);
    }

    public boolean f() {
        return false;
    }

    public boolean a() {
        return this.d.a() || this.e.a();
    }

    public boolean b() {
        return this.d.b() || this.e.b();
    }

    @NotNull
    public g d(@NotNull g annotations) {
        k.f(annotations, "annotations");
        return this.e.d(this.d.d(annotations));
    }
}
