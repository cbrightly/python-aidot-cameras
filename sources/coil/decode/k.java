package coil.decode;

import java.util.concurrent.atomic.AtomicInteger;
import kotlin.KotlinNothingValueException;
import kotlin.jvm.functions.l;
import kotlin.x;
import kotlinx.coroutines.n;
import okio.c;
import okio.e0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: InterruptibleSource.kt */
public final class k extends okio.k implements l<Throwable, x> {
    @NotNull
    private final AtomicInteger c = new AtomicInteger(1);
    private final Thread d = Thread.currentThread();

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        g((Throwable) p1);
        return x.a;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public k(@NotNull n<?> continuation, @NotNull e0 delegate) {
        super(delegate);
        int state;
        kotlin.jvm.internal.k.e(continuation, "continuation");
        kotlin.jvm.internal.k.e(delegate, "delegate");
        continuation.f(this);
        AtomicInteger $this$loop$iv = this.c;
        do {
            state = $this$loop$iv.get();
            switch (state) {
                case 1:
                    break;
                case 3:
                case 4:
                case 5:
                    return;
                default:
                    c(state);
                    throw new KotlinNothingValueException();
            }
        } while (!this.c.compareAndSet(state, 1));
    }

    /* JADX INFO: finally extract failed */
    public long read(@NotNull c sink, long byteCount) {
        kotlin.jvm.internal.k.e(sink, "sink");
        try {
            i(false);
            long read = super.read(sink, byteCount);
            i(true);
            return read;
        } catch (Throwable th) {
            i(true);
            throw th;
        }
    }

    private final void i(boolean interruptible) {
        AtomicInteger $this$loop$iv = this.c;
        while (true) {
            int state = $this$loop$iv.get();
            switch (state) {
                case 0:
                case 1:
                    if (!this.c.compareAndSet(state, (int) (!interruptible))) {
                        break;
                    } else {
                        return;
                    }
                case 3:
                    if (!this.c.compareAndSet(state, 4)) {
                        break;
                    } else {
                        this.d.interrupt();
                        this.c.set(5);
                        return;
                    }
                case 4:
                    break;
                case 5:
                    Thread.interrupted();
                    return;
                default:
                    c(state);
                    throw new KotlinNothingValueException();
            }
        }
    }

    public final void a() {
        AtomicInteger $this$loop$iv = this.c;
        while (true) {
            int state = $this$loop$iv.get();
            switch (state) {
                case 0:
                case 3:
                    if (!this.c.compareAndSet(state, 2)) {
                        break;
                    } else {
                        return;
                    }
                case 4:
                    break;
                case 5:
                    Thread.interrupted();
                    return;
                default:
                    c(state);
                    throw new KotlinNothingValueException();
            }
        }
    }

    public void g(@Nullable Throwable cause) {
        AtomicInteger $this$loop$iv = this.c;
        while (true) {
            int state = $this$loop$iv.get();
            switch (state) {
                case 0:
                    if (!this.c.compareAndSet(state, 4)) {
                        break;
                    } else {
                        this.d.interrupt();
                        this.c.set(5);
                        return;
                    }
                case 1:
                    if (!this.c.compareAndSet(state, 3)) {
                        break;
                    } else {
                        return;
                    }
                case 2:
                case 3:
                case 4:
                case 5:
                    return;
                default:
                    c(state);
                    throw new KotlinNothingValueException();
            }
        }
    }

    private final Void c(int state) {
        throw new IllegalStateException(kotlin.jvm.internal.k.l("Illegal state: ", Integer.valueOf(state)).toString());
    }
}
