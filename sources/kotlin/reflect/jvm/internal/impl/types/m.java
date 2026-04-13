package kotlin.reflect.jvm.internal.impl.types;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeSubstitution.kt */
public class m extends z0 {
    @NotNull
    private final z0 c;

    public m(@NotNull z0 substitution) {
        k.f(substitution, "substitution");
        this.c = substitution;
    }

    @Nullable
    public w0 e(@NotNull b0 key) {
        k.f(key, CacheEntity.KEY);
        return this.c.e(key);
    }

    @NotNull
    public b0 g(@NotNull b0 topLevelType, @NotNull h1 position) {
        k.f(topLevelType, "topLevelType");
        k.f(position, "position");
        return this.c.g(topLevelType, position);
    }

    public boolean f() {
        return this.c.f();
    }

    public boolean a() {
        return this.c.a();
    }

    @NotNull
    public g d(@NotNull g annotations) {
        k.f(annotations, "annotations");
        return this.c.d(annotations);
    }
}
