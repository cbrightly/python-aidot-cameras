package coil.decode;

import coil.bitmap.c;
import coil.size.Size;
import kotlin.coroutines.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Decoder.kt */
public interface e {
    @Nullable
    Object a(@NotNull c cVar, @NotNull okio.e eVar, @NotNull Size size, @NotNull m mVar, @NotNull d<? super c> dVar);

    boolean b(@NotNull okio.e eVar, @Nullable String str);
}
