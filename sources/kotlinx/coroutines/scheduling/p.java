package kotlinx.coroutines.scheduling;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.l;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0000\b\u0000\u0018\u00002\u00020*B\u0007¢\u0006\u0004\b\u0001\u0010\u0002J!\u0010\u0007\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\t\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\t\u0010\nJ\u0015\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u000b¢\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0010\u0010\u0011J\u0011\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0002¢\u0006\u0004\b\u0012\u0010\u0011J\u0017\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u0014\u0010\u0015J\u0015\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0000¢\u0006\u0004\b\u0018\u0010\u0019J\u0015\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0000¢\u0006\u0004\b\u001a\u0010\u0019J\u001f\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u0005H\u0002¢\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010\u001e\u001a\u00020\r*\u0004\u0018\u00010\u0003H\u0002¢\u0006\u0004\b\u001e\u0010\u001fR\u001c\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030 8\u0002X\u0004¢\u0006\u0006\n\u0004\b!\u0010\"R\u0014\u0010&\u001a\u00020#8@X\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010(\u001a\u00020#8@X\u0004¢\u0006\u0006\u001a\u0004\b'\u0010%¨\u0006)"}, d2 = {"Lkotlinx/coroutines/scheduling/WorkQueue;", "<init>", "()V", "Lkotlinx/coroutines/scheduling/Task;", "task", "", "fair", "add", "(Lkotlinx/coroutines/scheduling/Task;Z)Lkotlinx/coroutines/scheduling/Task;", "addLast", "(Lkotlinx/coroutines/scheduling/Task;)Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalQueue", "", "offloadAllWorkTo", "(Lkotlinx/coroutines/scheduling/GlobalQueue;)V", "poll", "()Lkotlinx/coroutines/scheduling/Task;", "pollBuffer", "queue", "pollTo", "(Lkotlinx/coroutines/scheduling/GlobalQueue;)Z", "victim", "", "tryStealBlockingFrom", "(Lkotlinx/coroutines/scheduling/WorkQueue;)J", "tryStealFrom", "blockingOnly", "tryStealLastScheduled", "(Lkotlinx/coroutines/scheduling/WorkQueue;Z)J", "decrementIfBlocking", "(Lkotlinx/coroutines/scheduling/Task;)V", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "buffer", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "", "getBufferSize$kotlinx_coroutines_core", "()I", "bufferSize", "getSize$kotlinx_coroutines_core", "size", "kotlinx-coroutines-core", ""}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: WorkQueue.kt */
public final class p {
    private static final /* synthetic */ AtomicReferenceFieldUpdater a;
    private static final /* synthetic */ AtomicIntegerFieldUpdater b;
    private static final /* synthetic */ AtomicIntegerFieldUpdater c;
    private static final /* synthetic */ AtomicIntegerFieldUpdater d;
    @NotNull
    private volatile /* synthetic */ int blockingTasksInBuffer = 0;
    @NotNull
    private volatile /* synthetic */ int consumerIndex = 0;
    @NotNull
    private final AtomicReferenceArray<j> e = new AtomicReferenceArray<>(128);
    @NotNull
    private volatile /* synthetic */ Object lastScheduledTask = null;
    @NotNull
    private volatile /* synthetic */ int producerIndex = 0;

    static {
        Class<p> cls = p.class;
        a = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "lastScheduledTask");
        b = AtomicIntegerFieldUpdater.newUpdater(cls, "producerIndex");
        c = AtomicIntegerFieldUpdater.newUpdater(cls, "consumerIndex");
        d = AtomicIntegerFieldUpdater.newUpdater(cls, "blockingTasksInBuffer");
    }

    public final int e() {
        return this.producerIndex - this.consumerIndex;
    }

    public final int f() {
        return this.lastScheduledTask != null ? e() + 1 : e();
    }

    @Nullable
    public final j h() {
        j jVar = (j) a.getAndSet(this, (Object) null);
        return jVar == null ? i() : jVar;
    }

    public static /* synthetic */ j b(p pVar, j jVar, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return pVar.a(jVar, z);
    }

    @Nullable
    public final j a(@NotNull j task, boolean fair) {
        if (fair) {
            return c(task);
        }
        j previous = (j) a.getAndSet(this, task);
        if (previous == null) {
            return null;
        }
        return c(previous);
    }

    private final j c(j task) {
        boolean z = true;
        if (task.d.E() != 1) {
            z = false;
        }
        if (z) {
            d.incrementAndGet(this);
        }
        if (e() == 127) {
            return task;
        }
        int nextIndex = this.producerIndex & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
        while (this.e.get(nextIndex) != null) {
            Thread.yield();
        }
        this.e.lazySet(nextIndex, task);
        b.incrementAndGet(this);
        return null;
    }

    public final long l(@NotNull p victim) {
        boolean z = true;
        if (s0.a()) {
            if ((e() == 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        j task = victim.i();
        if (task == null) {
            return m(victim, false);
        }
        j notAdded = b(this, task, false, 2, (Object) null);
        if (!s0.a()) {
            return -1;
        }
        if (notAdded != null) {
            z = false;
        }
        if (z) {
            return -1;
        }
        throw new AssertionError();
    }

    public final long k(@NotNull p victim) {
        if (s0.a()) {
            if ((e() == 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        int end = victim.producerIndex;
        AtomicReferenceArray buffer = victim.e;
        for (int start = victim.consumerIndex; start != end; start++) {
            int index = start & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
            if (victim.blockingTasksInBuffer == 0) {
                break;
            }
            j value = buffer.get(index);
            if (value != null) {
                if ((value.d.E() == 1 ? 1 : null) != null && buffer.compareAndSet(index, value, (Object) null)) {
                    d.decrementAndGet(victim);
                    b(this, value, false, 2, (Object) null);
                    return -1;
                }
            }
        }
        return m(victim, true);
    }

    public final void g(@NotNull e globalQueue) {
        j it = (j) a.getAndSet(this, (Object) null);
        if (it != null) {
            globalQueue.a(it);
        }
        do {
        } while (j(globalQueue));
    }

    private final long m(p victim, boolean blockingOnly) {
        j lastScheduled;
        do {
            lastScheduled = (j) victim.lastScheduledTask;
            if (lastScheduled == null) {
                return -2;
            }
            if (blockingOnly) {
                boolean z = true;
                if (lastScheduled.d.E() != 1) {
                    z = false;
                }
                if (!z) {
                    return -2;
                }
            }
            long staleness = n.e.a() - lastScheduled.c;
            long j = n.a;
            if (staleness < j) {
                return j - staleness;
            }
        } while (!a.compareAndSet(victim, lastScheduled, (Object) null));
        b(this, lastScheduled, false, 2, (Object) null);
        return -1;
    }

    private final boolean j(e queue) {
        j task = i();
        if (task == null) {
            return false;
        }
        queue.a(task);
        return true;
    }

    private final j i() {
        j value;
        while (true) {
            int tailLocal = this.consumerIndex;
            if (tailLocal - this.producerIndex == 0) {
                return null;
            }
            int index = tailLocal & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
            if (c.compareAndSet(this, tailLocal, tailLocal + 1) && (value = this.e.getAndSet(index, (Object) null)) != null) {
                d(value);
                return value;
            }
        }
    }

    private final void d(j $this$decrementIfBlocking) {
        if ($this$decrementIfBlocking != null) {
            boolean z = false;
            if (($this$decrementIfBlocking.d.E() == 1 ? 1 : null) != null) {
                int value = d.decrementAndGet(this);
                if (s0.a()) {
                    if (value >= 0) {
                        z = true;
                    }
                    if (!z) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }
}
