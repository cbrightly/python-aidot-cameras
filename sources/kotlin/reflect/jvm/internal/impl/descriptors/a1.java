package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Visibility.kt */
public abstract class a1 {
    @NotNull
    private final String a;
    private final boolean b;

    public abstract boolean d(@Nullable d dVar, @NotNull q qVar, @NotNull m mVar);

    protected a1(@NotNull String name, boolean isPublicAPI) {
        k.f(name, "name");
        this.a = name;
        this.b = isPublicAPI;
    }

    public final boolean c() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Integer a(@NotNull a1 visibility) {
        k.f(visibility, "visibility");
        return z0.e(this, visibility);
    }

    @NotNull
    public String b() {
        return this.a;
    }

    @NotNull
    public final String toString() {
        return b();
    }

    @NotNull
    public a1 e() {
        return this;
    }
}
