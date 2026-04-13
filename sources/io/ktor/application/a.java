package io.ktor.application;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.v2;
import kotlinx.coroutines.z;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;

/* compiled from: Application.kt */
public final class a extends c implements o0 {
    @NotNull
    private final d A4;
    private final z p4;
    @NotNull
    private final g z4;

    public a(@NotNull d environment) {
        k.f(environment, "environment");
        this.A4 = environment;
        z a = v2.a((z1) environment.e().get(z1.g));
        this.p4 = a;
        this.z4 = environment.e().plus(a);
    }

    @NotNull
    public final d F() {
        return this.A4;
    }

    @NotNull
    public g getCoroutineContext() {
        return this.z4;
    }

    public final void D() {
        z1.a.a(this.p4, (CancellationException) null, 1, (Object) null);
        g.d(this);
    }
}
