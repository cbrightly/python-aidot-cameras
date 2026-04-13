package kotlinx.coroutines.internal;

import kotlin.jvm.internal.k;
import kotlin.l;

@l(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010\b\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000¨\u0006\u0003"}, d2 = {"checkParallelism", "", "", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: LimitedDispatcher.kt */
public final class p {
    public static final void a(int $this$checkParallelism) {
        boolean z = true;
        if ($this$checkParallelism < 1) {
            z = false;
        }
        if (!z) {
            throw new IllegalArgumentException(k.l("Expected positive parallelism level, but got ", Integer.valueOf($this$checkParallelism)).toString());
        }
    }
}
