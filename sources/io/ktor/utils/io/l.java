package io.ktor.utils.io;

import io.ktor.utils.io.core.a0;
import io.ktor.utils.io.core.q;
import java.nio.ByteBuffer;
import kotlin.coroutines.d;
import kotlin.jvm.functions.p;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteWriteChannel.kt */
public interface l {
    boolean A();

    boolean d(@Nullable Throwable th);

    void flush();

    @Nullable
    Object h(@NotNull ByteBuffer byteBuffer, @NotNull d<? super x> dVar);

    @Nullable
    Object m(@NotNull a0 a0Var, @NotNull d<? super x> dVar);

    @Nullable
    Object n(@NotNull p<? super b0, ? super d<? super x>, ? extends Object> pVar, @NotNull d<? super x> dVar);

    @Nullable
    Object q(@NotNull byte[] bArr, int i, int i2, @NotNull d<? super x> dVar);

    @Nullable
    Object x(@NotNull q qVar, @NotNull d<? super x> dVar);

    @Nullable
    Object z(@NotNull kotlin.jvm.functions.l<? super ByteBuffer, Boolean> lVar, @NotNull d<? super x> dVar);
}
