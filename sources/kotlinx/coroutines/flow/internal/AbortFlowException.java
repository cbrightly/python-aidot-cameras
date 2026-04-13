package kotlinx.coroutines.flow.internal;

import java.util.concurrent.CancellationException;
import kotlin.l;
import kotlinx.coroutines.flow.d;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0000\b\u0000\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0011\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016R\u0015\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lkotlinx/coroutines/flow/internal/AbortFlowException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "owner", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;)V", "getOwner", "()Lkotlinx/coroutines/flow/FlowCollector;", "fillInStackTrace", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: FlowExceptions.kt */
public final class AbortFlowException extends CancellationException {
    @NotNull
    private final d<?> owner;

    @NotNull
    public final d<?> getOwner() {
        return this.owner;
    }

    public AbortFlowException(@NotNull d<?> owner2) {
        super("Flow was aborted, no more elements needed");
        this.owner = owner2;
    }

    @NotNull
    public Throwable fillInStackTrace() {
        if (s0.c()) {
            return super.fillInStackTrace();
        }
        setStackTrace(new StackTraceElement[0]);
        return this;
    }
}
