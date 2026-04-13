package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContinuationImpl.kt */
public abstract class j extends a {
    public j(@Nullable d<Object> completion) {
        super(completion);
        if (completion != null) {
            if (!(completion.getContext() == h.INSTANCE)) {
                throw new IllegalArgumentException("Coroutines with restricted suspension must have EmptyCoroutineContext".toString());
            }
        }
    }

    @NotNull
    public g getContext() {
        return h.INSTANCE;
    }
}
