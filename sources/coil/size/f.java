package coil.size;

import androidx.annotation.MainThread;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SizeResolver.kt */
public interface f {
    @NotNull
    public static final a a = a.a;

    @Nullable
    @MainThread
    Object b(@NotNull d<? super Size> dVar);

    /* compiled from: SizeResolver.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();

        private a() {
        }

        @NotNull
        public final f a(@NotNull Size size) {
            k.e(size, "size");
            return new c(size);
        }
    }
}
