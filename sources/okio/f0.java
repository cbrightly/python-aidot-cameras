package okio;

import com.amazonaws.kinesisvideo.producer.Time;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Timeout.kt */
public class f0 {
    @NotNull
    public static final f0 a = new a();
    @NotNull
    public static final b b = new b((DefaultConstructorMarker) null);
    private boolean c;
    private long d;
    private long e;

    @NotNull
    public f0 g(long timeout, @NotNull TimeUnit unit) {
        k.e(unit, "unit");
        if (timeout >= 0) {
            this.e = unit.toNanos(timeout);
            return this;
        }
        throw new IllegalArgumentException(("timeout < 0: " + timeout).toString());
    }

    public long h() {
        return this.e;
    }

    public boolean e() {
        return this.c;
    }

    public long c() {
        if (this.c) {
            return this.d;
        }
        throw new IllegalStateException("No deadline".toString());
    }

    @NotNull
    public f0 d(long deadlineNanoTime) {
        this.c = true;
        this.d = deadlineNanoTime;
        return this;
    }

    @NotNull
    public f0 b() {
        this.e = 0;
        return this;
    }

    @NotNull
    public f0 a() {
        this.c = false;
        return this;
    }

    public void f() {
        Thread currentThread = Thread.currentThread();
        k.d(currentThread, "Thread.currentThread()");
        if (currentThread.isInterrupted()) {
            throw new InterruptedIOException("interrupted");
        } else if (this.c && this.d - System.nanoTime() <= 0) {
            throw new InterruptedIOException("deadline reached");
        }
    }

    public final void i(@NotNull Object monitor) {
        long j;
        k.e(monitor, "monitor");
        try {
            boolean hasDeadline = e();
            long timeoutNanos = h();
            if (hasDeadline || timeoutNanos != 0) {
                long start = System.nanoTime();
                if (hasDeadline && timeoutNanos != 0) {
                    j = Math.min(timeoutNanos, c() - start);
                } else if (hasDeadline) {
                    j = c() - start;
                } else {
                    j = timeoutNanos;
                }
                long waitNanos = j;
                long elapsedNanos = 0;
                if (waitNanos > 0) {
                    long waitMillis = waitNanos / Time.NANOS_IN_A_MILLISECOND;
                    Long.signum(waitMillis);
                    monitor.wait(waitMillis, (int) (waitNanos - (Time.NANOS_IN_A_MILLISECOND * waitMillis)));
                    elapsedNanos = System.nanoTime() - start;
                }
                if (elapsedNanos >= waitNanos) {
                    throw new InterruptedIOException("timeout");
                }
                return;
            }
            monitor.wait();
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted");
        }
    }

    /* compiled from: Timeout.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final long a(long aNanos, long bNanos) {
            return (aNanos != 0 && (bNanos == 0 || aNanos < bNanos)) ? aNanos : bNanos;
        }
    }

    /* compiled from: Timeout.kt */
    public static final class a extends f0 {
        a() {
        }

        @NotNull
        public f0 g(long timeout, @NotNull TimeUnit unit) {
            k.e(unit, "unit");
            return this;
        }

        @NotNull
        public f0 d(long deadlineNanoTime) {
            return this;
        }

        public void f() {
        }
    }
}
