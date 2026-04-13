package coil.intercept;

import coil.request.i;
import coil.request.j;
import coil.size.Size;
import kotlin.coroutines.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Interceptor.kt */
public interface b {

    /* compiled from: Interceptor.kt */
    public interface a {
        @NotNull
        i getRequest();

        @NotNull
        Size getSize();
    }

    @Nullable
    Object a(@NotNull a aVar, @NotNull d<? super j> dVar);
}
