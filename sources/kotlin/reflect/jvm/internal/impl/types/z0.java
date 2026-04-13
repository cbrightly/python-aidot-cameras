package kotlin.reflect.jvm.internal.impl.types;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeSubstitution.kt */
public abstract class z0 {
    @NotNull
    public static final z0 a = new a();
    public static final b b = new b((DefaultConstructorMarker) null);

    @Nullable
    public abstract w0 e(@NotNull b0 b0Var);

    /* compiled from: TypeSubstitution.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    /* compiled from: TypeSubstitution.kt */
    public static final class a extends z0 {
        a() {
        }

        public /* bridge */ /* synthetic */ w0 e(b0 b0Var) {
            return (w0) h(b0Var);
        }

        @Nullable
        public Void h(@NotNull b0 key) {
            k.f(key, CacheEntity.KEY);
            return null;
        }

        public boolean f() {
            return true;
        }

        @NotNull
        public String toString() {
            return "Empty TypeSubstitution";
        }
    }

    @NotNull
    public b0 g(@NotNull b0 topLevelType, @NotNull h1 position) {
        k.f(topLevelType, "topLevelType");
        k.f(position, "position");
        return topLevelType;
    }

    public boolean f() {
        return false;
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    @NotNull
    public g d(@NotNull g annotations) {
        k.f(annotations, "annotations");
        return annotations;
    }

    @NotNull
    public final TypeSubstitutor c() {
        TypeSubstitutor g = TypeSubstitutor.g(this);
        k.b(g, "TypeSubstitutor.create(this)");
        return g;
    }
}
