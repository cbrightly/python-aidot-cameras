package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.storage.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: LazyScopeAdapter.kt */
public final class g extends a {
    private final f<h> b;

    public g(@NotNull f<? extends h> scope) {
        k.f(scope, "scope");
        this.b = scope;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public h g() {
        return (h) this.b.invoke();
    }
}
