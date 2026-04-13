package coil.transition;

import androidx.annotation.MainThread;
import coil.request.j;
import kotlin.coroutines.d;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Transition.kt */
public interface b {
    @NotNull
    public static final a a = a.a;
    @NotNull
    public static final b b = a.c;

    @Nullable
    @MainThread
    Object a(@NotNull c cVar, @NotNull j jVar, @NotNull d<? super x> dVar);

    /* compiled from: Transition.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();

        private a() {
        }
    }
}
