package io.ktor.utils.io.jvm.javaio;

import io.ktor.utils.io.i;
import java.io.InputStream;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Blocking.kt */
public final class b {
    private static final Object a = new Object();
    private static final Object b = new Object();

    @NotNull
    public static final InputStream a(@NotNull i $this$toInputStream, @Nullable z1 parent) {
        k.f($this$toInputStream, "$this$toInputStream");
        return new c(parent, $this$toInputStream);
    }

    public static /* synthetic */ InputStream b(i iVar, z1 z1Var, int i, Object obj) {
        if ((i & 1) != 0) {
            z1Var = null;
        }
        return a(iVar, z1Var);
    }
}
