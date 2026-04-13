package kotlinx.coroutines.scheduling;

import android.support.v4.media.session.PlaybackStateCompat;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.ranges.n;
import kotlin.x;
import kotlinx.coroutines.internal.c0;
import kotlinx.coroutines.internal.f0;
import kotlinx.coroutines.s0;
import kotlinx.coroutines.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0000\u0018\u0000 X2\u00020\\2\u00020]:\u0003XYZB+\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u0018\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0004H\b¢\u0006\u0004\b\u0010\u0010\u0011J\u0018\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0004H\b¢\u0006\u0004\b\u0012\u0010\u0011J\u000f\u0010\u0014\u001a\u00020\u0013H\u0016¢\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0016\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u0016\u0010\u0017J!\u0010\u001d\u001a\u00020\n2\n\u0010\u001a\u001a\u00060\u0018j\u0002`\u00192\u0006\u0010\u001c\u001a\u00020\u001b¢\u0006\u0004\b\u001d\u0010\u001eJ\u0018\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0004H\b¢\u0006\u0004\b\u001f\u0010\u0011J\u0015\u0010!\u001a\b\u0018\u00010 R\u00020\u0000H\u0002¢\u0006\u0004\b!\u0010\"J\u0010\u0010#\u001a\u00020\u0013H\b¢\u0006\u0004\b#\u0010\u0015J\u0010\u0010$\u001a\u00020\u0001H\b¢\u0006\u0004\b$\u0010\u0017J-\u0010&\u001a\u00020\u00132\n\u0010\u001a\u001a\u00060\u0018j\u0002`\u00192\b\b\u0002\u0010\u001c\u001a\u00020\u001b2\b\b\u0002\u0010%\u001a\u00020\f¢\u0006\u0004\b&\u0010'J\u001b\u0010)\u001a\u00020\u00132\n\u0010(\u001a\u00060\u0018j\u0002`\u0019H\u0016¢\u0006\u0004\b)\u0010*J\u0010\u0010+\u001a\u00020\u0004H\b¢\u0006\u0004\b+\u0010,J\u0010\u0010-\u001a\u00020\u0001H\b¢\u0006\u0004\b-\u0010\u0017J\u001b\u0010/\u001a\u00020\u00012\n\u0010.\u001a\u00060 R\u00020\u0000H\u0002¢\u0006\u0004\b/\u00100J\u0015\u00101\u001a\b\u0018\u00010 R\u00020\u0000H\u0002¢\u0006\u0004\b1\u0010\"J\u0019\u00102\u001a\u00020\f2\n\u0010.\u001a\u00060 R\u00020\u0000¢\u0006\u0004\b2\u00103J)\u00106\u001a\u00020\u00132\n\u0010.\u001a\u00060 R\u00020\u00002\u0006\u00104\u001a\u00020\u00012\u0006\u00105\u001a\u00020\u0001¢\u0006\u0004\b6\u00107J\u0010\u00108\u001a\u00020\u0004H\b¢\u0006\u0004\b8\u0010,J\u0015\u00109\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b9\u0010:J\u0015\u0010<\u001a\u00020\u00132\u0006\u0010;\u001a\u00020\u0004¢\u0006\u0004\b<\u0010=J\u0017\u0010?\u001a\u00020\u00132\u0006\u0010>\u001a\u00020\fH\u0002¢\u0006\u0004\b?\u0010@J\r\u0010A\u001a\u00020\u0013¢\u0006\u0004\bA\u0010\u0015J\u000f\u0010B\u001a\u00020\u0006H\u0016¢\u0006\u0004\bB\u0010CJ\u0010\u0010D\u001a\u00020\fH\b¢\u0006\u0004\bD\u0010EJ\u0019\u0010F\u001a\u00020\f2\b\b\u0002\u0010\u000f\u001a\u00020\u0004H\u0002¢\u0006\u0004\bF\u0010GJ\u000f\u0010H\u001a\u00020\fH\u0002¢\u0006\u0004\bH\u0010EJ+\u0010I\u001a\u0004\u0018\u00010\n*\b\u0018\u00010 R\u00020\u00002\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010%\u001a\u00020\fH\u0002¢\u0006\u0004\bI\u0010JR\u0015\u0010\u0010\u001a\u00020\u00018Â\u0002X\u0004¢\u0006\u0006\u001a\u0004\bK\u0010\u0017R\u0014\u0010\u0002\u001a\u00020\u00018\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010LR\u0015\u0010\u001f\u001a\u00020\u00018Â\u0002X\u0004¢\u0006\u0006\u001a\u0004\bM\u0010\u0017R\u0014\u0010O\u001a\u00020N8\u0006X\u0004¢\u0006\u0006\n\u0004\bO\u0010PR\u0014\u0010Q\u001a\u00020N8\u0006X\u0004¢\u0006\u0006\n\u0004\bQ\u0010PR\u0014\u0010\u0005\u001a\u00020\u00048\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010RR\u0011\u0010S\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\bS\u0010ER\u0014\u0010\u0003\u001a\u00020\u00018\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010LR\u0014\u0010\u0007\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0007\u0010TR\u001e\u0010V\u001a\f\u0012\b\u0012\u00060 R\u00020\u00000U8\u0006X\u0004¢\u0006\u0006\n\u0004\bV\u0010W¨\u0006["}, d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "", "corePoolSize", "maxPoolSize", "", "idleWorkerKeepAliveNs", "", "schedulerName", "<init>", "(IIJLjava/lang/String;)V", "Lkotlinx/coroutines/scheduling/Task;", "task", "", "addToGlobalQueue", "(Lkotlinx/coroutines/scheduling/Task;)Z", "state", "availableCpuPermits", "(J)I", "blockingTasks", "", "close", "()V", "createNewWorker", "()I", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "Lkotlinx/coroutines/scheduling/TaskContext;", "taskContext", "createTask", "(Ljava/lang/Runnable;Lkotlinx/coroutines/scheduling/TaskContext;)Lkotlinx/coroutines/scheduling/Task;", "createdWorkers", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "currentWorker", "()Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "decrementBlockingTasks", "decrementCreatedWorkers", "tailDispatch", "dispatch", "(Ljava/lang/Runnable;Lkotlinx/coroutines/scheduling/TaskContext;Z)V", "command", "execute", "(Ljava/lang/Runnable;)V", "incrementBlockingTasks", "()J", "incrementCreatedWorkers", "worker", "parkedWorkersStackNextIndex", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;)I", "parkedWorkersStackPop", "parkedWorkersStackPush", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;)Z", "oldIndex", "newIndex", "parkedWorkersStackTopUpdate", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;II)V", "releaseCpuPermit", "runSafely", "(Lkotlinx/coroutines/scheduling/Task;)V", "timeout", "shutdown", "(J)V", "skipUnpark", "signalBlockingWork", "(Z)V", "signalCpuWork", "toString", "()Ljava/lang/String;", "tryAcquireCpuPermit", "()Z", "tryCreateWorker", "(J)Z", "tryUnpark", "submitToLocalQueue", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;Lkotlinx/coroutines/scheduling/Task;Z)Lkotlinx/coroutines/scheduling/Task;", "getAvailableCpuPermits", "I", "getCreatedWorkers", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalBlockingQueue", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalCpuQueue", "J", "isTerminated", "Ljava/lang/String;", "Lkotlinx/coroutines/internal/ResizableAtomicArray;", "workers", "Lkotlinx/coroutines/internal/ResizableAtomicArray;", "Companion", "Worker", "WorkerState", "kotlinx-coroutines-core", "Ljava/util/concurrent/Executor;", "Ljava/io/Closeable;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: CoroutineScheduler.kt */
public final class a implements Executor, Closeable {
    @NotNull
    public static final C0450a c = new C0450a((DefaultConstructorMarker) null);
    private static final /* synthetic */ AtomicLongFieldUpdater d = AtomicLongFieldUpdater.newUpdater(a.class, "parkedWorkersStack");
    static final /* synthetic */ AtomicLongFieldUpdater f = AtomicLongFieldUpdater.newUpdater(a.class, "controlState");
    private static final /* synthetic */ AtomicIntegerFieldUpdater q = AtomicIntegerFieldUpdater.newUpdater(a.class, "_isTerminated");
    @NotNull
    public static final f0 x = new f0("NOT_IN_STACK");
    @NotNull
    private volatile /* synthetic */ int _isTerminated;
    @NotNull
    public final String a1;
    @NotNull
    public final e a2;
    @NotNull
    volatile /* synthetic */ long controlState;
    public final long p0;
    @NotNull
    public final e p1;
    @NotNull
    public final c0<c> p2;
    @NotNull
    private volatile /* synthetic */ long parkedWorkersStack;
    public final int y;
    public final int z;

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineScheduler.kt */
    public final /* synthetic */ class b {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[d.values().length];
            iArr[d.PARKING.ordinal()] = 1;
            iArr[d.BLOCKING.ordinal()] = 2;
            iArr[d.CPU_ACQUIRED.ordinal()] = 3;
            iArr[d.DORMANT.ordinal()] = 4;
            iArr[d.TERMINATED.ordinal()] = 5;
            a = iArr;
        }
    }

    @l(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "", "(Ljava/lang/String;I)V", "CPU_ACQUIRED", "BLOCKING", "PARKING", "DORMANT", "TERMINATED", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineScheduler.kt */
    public enum d {
        CPU_ACQUIRED,
        BLOCKING,
        PARKING,
        DORMANT,
        TERMINATED
    }

    public a(int corePoolSize, int maxPoolSize, long idleWorkerKeepAliveNs, @NotNull String schedulerName) {
        this.y = corePoolSize;
        this.z = maxPoolSize;
        this.p0 = idleWorkerKeepAliveNs;
        this.a1 = schedulerName;
        boolean z2 = true;
        if (corePoolSize >= 1) {
            if (maxPoolSize >= corePoolSize) {
                if (maxPoolSize <= 2097150) {
                    if (idleWorkerKeepAliveNs <= 0 ? false : z2) {
                        this.p1 = new e();
                        this.a2 = new e();
                        this.parkedWorkersStack = 0;
                        this.p2 = new c0<>(corePoolSize + 1);
                        this.controlState = ((long) corePoolSize) << 42;
                        this._isTerminated = 0;
                        return;
                    }
                    throw new IllegalArgumentException(("Idle worker keep alive time " + idleWorkerKeepAliveNs + " must be positive").toString());
                }
                throw new IllegalArgumentException(("Max pool size " + maxPoolSize + " should not exceed maximal supported number of threads 2097150").toString());
            }
            throw new IllegalArgumentException(("Max pool size " + maxPoolSize + " should be greater than or equals to core pool size " + corePoolSize).toString());
        }
        throw new IllegalArgumentException(("Core pool size " + corePoolSize + " should be at least 1").toString());
    }

    private final boolean a(j task) {
        boolean z2 = true;
        if (task.d.E() != 1) {
            z2 = false;
        }
        if (z2) {
            return this.a2.a(task);
        }
        return this.p1.a(task);
    }

    public final void r(@NotNull c worker, int oldIndex, int newIndex) {
        int i;
        while (true) {
            long top = this.parkedWorkersStack;
            int index = (int) (2097151 & top);
            long updVersion = (PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE + top) & -2097152;
            if (index != oldIndex) {
                i = index;
            } else if (newIndex == 0) {
                i = m(worker);
            } else {
                i = newIndex;
            }
            int updIndex = i;
            if (updIndex >= 0) {
                if (d.compareAndSet(this, top, updVersion | ((long) updIndex))) {
                    return;
                }
            }
        }
    }

    public final boolean o(@NotNull c worker) {
        long top;
        long updVersion;
        int updIndex;
        if (worker.h() != x) {
            return false;
        }
        do {
            top = this.parkedWorkersStack;
            int index = (int) (2097151 & top);
            updVersion = (PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE + top) & -2097152;
            updIndex = worker.g();
            if (s0.a()) {
                if ((updIndex != 0 ? 1 : 0) == 0) {
                    throw new AssertionError();
                }
            }
            worker.p(this.p2.b(index));
        } while (!d.compareAndSet(this, top, updVersion | ((long) updIndex)));
        return true;
    }

    private final c n() {
        while (true) {
            long top = this.parkedWorkersStack;
            c b2 = this.p2.b((int) (2097151 & top));
            if (b2 == null) {
                return null;
            }
            c worker = b2;
            long updVersion = (PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE + top) & -2097152;
            int updIndex = m(worker);
            if (updIndex >= 0) {
                int i = updIndex;
                if (d.compareAndSet(this, top, updVersion | ((long) updIndex))) {
                    worker.p(x);
                    return worker;
                }
            }
        }
    }

    private final int m(c worker) {
        Object next = worker.h();
        while (next != x) {
            if (next == null) {
                return 0;
            }
            c nextWorker = (c) next;
            int updIndex = nextWorker.g();
            if (updIndex != 0) {
                return updIndex;
            }
            next = nextWorker.h();
        }
        return -1;
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [int, boolean] */
    public final boolean isTerminated() {
        return this._isTerminated;
    }

    @l(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000e8\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler$Companion;", "", "()V", "BLOCKING_MASK", "", "BLOCKING_SHIFT", "", "CLAIMED", "CPU_PERMITS_MASK", "CPU_PERMITS_SHIFT", "CREATED_MASK", "MAX_SUPPORTED_POOL_SIZE", "MIN_SUPPORTED_POOL_SIZE", "NOT_IN_STACK", "Lkotlinx/coroutines/internal/Symbol;", "PARKED", "PARKED_INDEX_MASK", "PARKED_VERSION_INC", "PARKED_VERSION_MASK", "TERMINATED", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* renamed from: kotlinx.coroutines.scheduling.a$a  reason: collision with other inner class name */
    /* compiled from: CoroutineScheduler.kt */
    public static final class C0450a {
        public /* synthetic */ C0450a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0450a() {
        }
    }

    public void execute(@NotNull Runnable command) {
        l(this, command, (k) null, false, 6, (Object) null);
    }

    public void close() {
        t(10000);
    }

    public final void t(long timeout) {
        int i;
        int i2;
        boolean z2 = false;
        if (q.compareAndSet(this, 0, 1)) {
            c currentWorker = i();
            synchronized (this.p2) {
                try {
                    i = (int) (this.controlState & 2097151);
                } catch (Throwable th) {
                    long j = timeout;
                    throw th;
                }
            }
            int created = i;
            if (1 <= created) {
                int i3 = 1;
                do {
                    i2 = i3;
                    i3++;
                    c b2 = this.p2.b(i2);
                    k.c(b2);
                    c worker = b2;
                    if (worker != currentWorker) {
                        while (worker.isAlive()) {
                            LockSupport.unpark(worker);
                            worker.join(timeout);
                        }
                        long j2 = timeout;
                        d state = worker.f;
                        if (s0.a()) {
                            if ((state == d.TERMINATED ? 1 : 0) == 0) {
                                throw new AssertionError();
                            }
                        }
                        worker.d.g(this.a2);
                        continue;
                    } else {
                        long j3 = timeout;
                        continue;
                    }
                } while (i2 != created);
            } else {
                long j4 = timeout;
            }
            this.a2.b();
            this.p1.b();
            while (true) {
                j task = currentWorker == null ? null : currentWorker.f(true);
                if (task == null && (task = (j) this.p1.d()) == null && (task = (j) this.a2.d()) == null) {
                    break;
                }
                s(task);
            }
            if (currentWorker != null) {
                currentWorker.s(d.TERMINATED);
            }
            if (s0.a()) {
                if (((int) ((9223367638808264704L & this.controlState) >> 42)) == this.y) {
                    z2 = true;
                }
                if (!z2) {
                    throw new AssertionError();
                }
            }
            this.parkedWorkersStack = 0;
            this.controlState = 0;
        }
    }

    public static /* synthetic */ void l(a aVar, Runnable runnable, k kVar, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            kVar = n.f;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        aVar.j(runnable, kVar, z2);
    }

    public final void j(@NotNull Runnable block, @NotNull k taskContext, boolean tailDispatch) {
        kotlinx.coroutines.b a = kotlinx.coroutines.c.a();
        if (a == null) {
            j task = g(block, taskContext);
            c currentWorker = i();
            j notAdded = x(currentWorker, task, tailDispatch);
            if (notAdded == null || a(notAdded)) {
                boolean skipUnpark = tailDispatch && currentWorker != null;
                if (task.d.E() != 0) {
                    u(skipUnpark);
                } else if (!skipUnpark) {
                    v();
                }
            } else {
                throw new RejectedExecutionException(k.l(this.a1, " was terminated"));
            }
        } else {
            a.d();
            throw null;
        }
    }

    @NotNull
    public final j g(@NotNull Runnable block, @NotNull k taskContext) {
        long nanoTime = n.e.a();
        if (!(block instanceof j)) {
            return new m(block, nanoTime, taskContext);
        }
        ((j) block).c = nanoTime;
        ((j) block).d = taskContext;
        return (j) block;
    }

    private final void u(boolean skipUnpark) {
        long stateSnapshot = f.addAndGet(this, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE);
        if (!skipUnpark && !I() && !z(stateSnapshot)) {
            I();
        }
    }

    public final void v() {
        if (!I() && !E(this, 0, 1, (Object) null)) {
            I();
        }
    }

    static /* synthetic */ boolean E(a aVar, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            j = aVar.controlState;
        }
        return aVar.z(j);
    }

    private final boolean z(long state) {
        if (n.b(((int) (2097151 & state)) - ((int) ((4398044413952L & state) >> 21)), 0) < this.y) {
            int newCpuWorkers = c();
            if (newCpuWorkers == 1 && this.y > 1) {
                c();
            }
            if (newCpuWorkers > 0) {
                return true;
            }
        }
        return false;
    }

    private final boolean I() {
        c worker;
        do {
            worker = n();
            if (worker == null) {
                return false;
            }
        } while (!c.c.compareAndSet(worker, -1, 0));
        LockSupport.unpark(worker);
        return true;
    }

    private final int c() {
        synchronized (this.p2) {
            if (isTerminated()) {
                return -1;
            }
            long state = this.controlState;
            int created = (int) (state & 2097151);
            int cpuWorkers = n.b(created - ((int) ((4398044413952L & state) >> 21)), 0);
            if (cpuWorkers >= this.y) {
                return 0;
            }
            if (created >= this.z) {
                return 0;
            }
            int newIndex = ((int) (this.controlState & 2097151)) + 1;
            if (newIndex > 0 && this.p2.b(newIndex) == null) {
                c worker = new c(newIndex);
                this.p2.c(newIndex, worker);
                if (newIndex == ((int) (f.incrementAndGet(this) & 2097151))) {
                    worker.start();
                    int cpuWorkers2 = cpuWorkers + 1;
                    return cpuWorkers2;
                }
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    private final j x(c $this$submitToLocalQueue, j task, boolean tailDispatch) {
        if ($this$submitToLocalQueue == null || $this$submitToLocalQueue.f == d.TERMINATED) {
            return task;
        }
        if (task.d.E() == 0 && $this$submitToLocalQueue.f == d.BLOCKING) {
            return task;
        }
        $this$submitToLocalQueue.z = true;
        return $this$submitToLocalQueue.d.a(task, tailDispatch);
    }

    private final c i() {
        Thread currentThread = Thread.currentThread();
        c it = currentThread instanceof c ? (c) currentThread : null;
        if (it != null && k.a(a.this, this)) {
            return it;
        }
        return null;
    }

    @NotNull
    public String toString() {
        int parkedWorkers = 0;
        int blockingWorkers = 0;
        int cpuWorkers = 0;
        int dormant = 0;
        int terminated = 0;
        ArrayList queueSizes = new ArrayList();
        int a = this.p2.a();
        int i = 1;
        while (i < a) {
            int index = i;
            i++;
            c worker = this.p2.b(index);
            if (worker != null) {
                int queueSize = worker.d.f();
                switch (b.a[worker.f.ordinal()]) {
                    case 1:
                        parkedWorkers++;
                        break;
                    case 2:
                        blockingWorkers++;
                        StringBuilder sb = new StringBuilder();
                        sb.append(queueSize);
                        sb.append('b');
                        queueSizes.add(sb.toString());
                        break;
                    case 3:
                        cpuWorkers++;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(queueSize);
                        sb2.append('c');
                        queueSizes.add(sb2.toString());
                        break;
                    case 4:
                        dormant++;
                        if (queueSize <= 0) {
                            break;
                        } else {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(queueSize);
                            sb3.append('d');
                            queueSizes.add(sb3.toString());
                            break;
                        }
                    case 5:
                        terminated++;
                        break;
                }
            }
        }
        long state = this.controlState;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.a1);
        sb4.append('@');
        sb4.append(t0.b(this));
        sb4.append("[Pool Size {core = ");
        sb4.append(this.y);
        sb4.append(", max = ");
        sb4.append(this.z);
        sb4.append("}, Worker States {CPU = ");
        sb4.append(cpuWorkers);
        sb4.append(", blocking = ");
        sb4.append(blockingWorkers);
        sb4.append(", parked = ");
        sb4.append(parkedWorkers);
        sb4.append(", dormant = ");
        sb4.append(dormant);
        sb4.append(", terminated = ");
        sb4.append(terminated);
        sb4.append("}, running workers queues = ");
        sb4.append(queueSizes);
        sb4.append(", global CPU queue size = ");
        sb4.append(this.p1.c());
        sb4.append(", global blocking queue size = ");
        sb4.append(this.a2.c());
        sb4.append(", Control State {created workers= ");
        sb4.append((int) (2097151 & state));
        sb4.append(", blocking tasks = ");
        sb4.append((int) ((4398044413952L & state) >> 21));
        sb4.append(", CPUs acquired = ");
        sb4.append(this.y - ((int) ((9223367638808264704L & state) >> 42)));
        sb4.append("}]");
        return sb4.toString();
    }

    public final void s(@NotNull j task) {
        try {
            task.run();
            kotlinx.coroutines.b a = kotlinx.coroutines.c.a();
            if (a != null) {
                a.e();
                throw null;
            }
        } catch (Throwable th) {
            kotlinx.coroutines.b a3 = kotlinx.coroutines.c.a();
            if (a3 == null) {
                throw th;
            }
            a3.e();
            throw null;
        }
    }

    @l(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\b\u0004\u0018\u00002\u00020GB\u0011\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\b\u0010\tJ\u0017\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\n\u0010\tJ\u0017\u0010\r\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u0019\u0010\u0011\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002¢\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0013\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0010\u001a\u00020\u000f¢\u0006\u0004\b\u0013\u0010\u0012J\u0017\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u0015\u0010\tJ\u000f\u0010\u0016\u001a\u00020\u000fH\u0002¢\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0001¢\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u001b\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\u001b\u0010\u001cJ\u0011\u0010\u001d\u001a\u0004\u0018\u00010\u000bH\u0002¢\u0006\u0004\b\u001d\u0010\u001eJ\u000f\u0010\u001f\u001a\u00020\u0007H\u0016¢\u0006\u0004\b\u001f\u0010\u001cJ\u000f\u0010 \u001a\u00020\u0007H\u0002¢\u0006\u0004\b \u0010\u001cJ\u000f\u0010!\u001a\u00020\u000fH\u0002¢\u0006\u0004\b!\u0010\u0017J\u000f\u0010\"\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\"\u0010\u001cJ\u0015\u0010%\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020#¢\u0006\u0004\b%\u0010&J\u0019\u0010(\u001a\u0004\u0018\u00010\u000b2\u0006\u0010'\u001a\u00020\u000fH\u0002¢\u0006\u0004\b(\u0010\u0012J\u000f\u0010)\u001a\u00020\u0007H\u0002¢\u0006\u0004\b)\u0010\u001cR*\u0010*\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00018\u0006@FX\u000e¢\u0006\u0012\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-\"\u0004\b.\u0010\tR\u0014\u00100\u001a\u00020/8\u0006X\u0004¢\u0006\u0006\n\u0004\b0\u00101R\u0016\u00102\u001a\u00020\u000f8\u0006@\u0006X\u000e¢\u0006\u0006\n\u0004\b2\u00103R\u0016\u00105\u001a\u0002048\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\b5\u00106R$\u00108\u001a\u0004\u0018\u0001078\u0006@\u0006X\u000e¢\u0006\u0012\n\u0004\b8\u00109\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u0016\u0010>\u001a\u00020\u00018\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\b>\u0010+R\u0012\u0010B\u001a\u00020?8Æ\u0002¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0016\u0010C\u001a\u00020#8\u0006@\u0006X\u000e¢\u0006\u0006\n\u0004\bC\u0010DR\u0016\u0010E\u001a\u0002048\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\bE\u00106¨\u0006F"}, d2 = {"Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;", "", "index", "<init>", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler;I)V", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler;)V", "taskMode", "", "afterTask", "(I)V", "beforeTask", "Lkotlinx/coroutines/scheduling/Task;", "task", "executeTask", "(Lkotlinx/coroutines/scheduling/Task;)V", "", "scanLocalQueue", "findAnyTask", "(Z)Lkotlinx/coroutines/scheduling/Task;", "findTask", "mode", "idleReset", "inStack", "()Z", "upperBound", "nextInt", "(I)I", "park", "()V", "pollGlobalQueues", "()Lkotlinx/coroutines/scheduling/Task;", "run", "runWorker", "tryAcquireCpuPermit", "tryPark", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "newState", "tryReleaseCpu", "(Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;)Z", "blockingOnly", "trySteal", "tryTerminateWorker", "indexInArray", "I", "getIndexInArray", "()I", "setIndexInArray", "Lkotlinx/coroutines/scheduling/WorkQueue;", "localQueue", "Lkotlinx/coroutines/scheduling/WorkQueue;", "mayHaveLocalTasks", "Z", "", "minDelayUntilStealableTaskNs", "J", "", "nextParkedWorker", "Ljava/lang/Object;", "getNextParkedWorker", "()Ljava/lang/Object;", "setNextParkedWorker", "(Ljava/lang/Object;)V", "rngState", "Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "getScheduler", "()Lkotlinx/coroutines/scheduling/CoroutineScheduler;", "scheduler", "state", "Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;", "terminationDeadline", "kotlinx-coroutines-core", "Ljava/lang/Thread;"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineScheduler.kt */
    public final class c extends Thread {
        static final /* synthetic */ AtomicIntegerFieldUpdater c = AtomicIntegerFieldUpdater.newUpdater(c.class, "workerCtl");
        @NotNull
        public final p d;
        @NotNull
        public d f;
        private volatile int indexInArray;
        @Nullable
        private volatile Object nextParkedWorker;
        private long q;
        @NotNull
        volatile /* synthetic */ int workerCtl;
        private long x;
        private int y;
        public boolean z;

        private c() {
            setDaemon(true);
            this.d = new p();
            this.f = d.DORMANT;
            this.workerCtl = 0;
            this.nextParkedWorker = a.x;
            this.y = kotlin.random.d.Default.nextInt();
        }

        public final int g() {
            return this.indexInArray;
        }

        public final void o(int index) {
            StringBuilder sb = new StringBuilder();
            sb.append(a.this.a1);
            sb.append("-worker-");
            sb.append(index == 0 ? "TERMINATED" : String.valueOf(index));
            setName(sb.toString());
            this.indexInArray = index;
        }

        public c(int index) {
            this();
            o(index);
        }

        @Nullable
        public final Object h() {
            return this.nextParkedWorker;
        }

        public final void p(@Nullable Object obj) {
            this.nextParkedWorker = obj;
        }

        private final boolean q() {
            a this_$iv;
            if (this.f == d.CPU_ACQUIRED) {
                return true;
            }
            a this_$iv2 = a.this;
            a $this$loop$iv$iv = this_$iv2;
            while (true) {
                long state$iv = $this$loop$iv$iv.controlState;
                a aVar = this_$iv2;
                if (((int) ((9223367638808264704L & state$iv) >> 42)) != 0) {
                    if (a.f.compareAndSet(this_$iv2, state$iv, state$iv - 4398046511104L)) {
                        this_$iv = 1;
                        break;
                    }
                } else {
                    this_$iv = null;
                    break;
                }
            }
            if (this_$iv == null) {
                return false;
            }
            this.f = d.CPU_ACQUIRED;
            return true;
        }

        public final boolean s(@NotNull d newState) {
            d previousState = this.f;
            boolean hadCpu = previousState == d.CPU_ACQUIRED;
            if (hadCpu) {
                a.f.addAndGet(a.this, 4398046511104L);
            }
            if (previousState != newState) {
                this.f = newState;
            }
            return hadCpu;
        }

        public void run() {
            n();
        }

        private final void n() {
            boolean rescanned = false;
            while (!a.this.isTerminated() && this.f != d.TERMINATED) {
                j task = f(this.z);
                if (task != null) {
                    rescanned = false;
                    this.x = 0;
                    d(task);
                } else {
                    this.z = false;
                    if (this.x == 0) {
                        r();
                    } else if (!rescanned) {
                        rescanned = true;
                    } else {
                        rescanned = false;
                        s(d.PARKING);
                        Thread.interrupted();
                        LockSupport.parkNanos(this.x);
                        this.x = 0;
                    }
                }
            }
            s(d.TERMINATED);
        }

        private final void r() {
            if (!j()) {
                a.this.o(this);
                return;
            }
            if (s0.a()) {
                if (!(this.d.f() == 0)) {
                    throw new AssertionError();
                }
            }
            this.workerCtl = -1;
            while (j() && this.workerCtl == -1 && !a.this.isTerminated() && this.f != d.TERMINATED) {
                s(d.PARKING);
                Thread.interrupted();
                l();
            }
        }

        private final boolean j() {
            return this.nextParkedWorker != a.x;
        }

        private final void d(j task) {
            int taskMode = task.d.E();
            i(taskMode);
            c(taskMode);
            a.this.s(task);
            b(taskMode);
        }

        private final void c(int taskMode) {
            if (taskMode != 0 && s(d.BLOCKING)) {
                a.this.v();
            }
        }

        private final void b(int taskMode) {
            if (taskMode != 0) {
                a.f.addAndGet(a.this, -2097152);
                d currentState = this.f;
                if (currentState != d.TERMINATED) {
                    if (s0.a()) {
                        if (!(currentState == d.BLOCKING)) {
                            throw new AssertionError();
                        }
                    }
                    this.f = d.DORMANT;
                }
            }
        }

        public final int k(int upperBound) {
            int r = this.y;
            int r2 = r ^ (r << 13);
            int r3 = r2 ^ (r2 >> 17);
            int r4 = r3 ^ (r3 << 5);
            this.y = r4;
            int mask = upperBound - 1;
            if ((mask & upperBound) == 0) {
                return r4 & mask;
            }
            return (Integer.MAX_VALUE & r4) % upperBound;
        }

        private final void l() {
            if (this.q == 0) {
                this.q = System.nanoTime() + a.this.p0;
            }
            LockSupport.parkNanos(a.this.p0);
            if (System.nanoTime() - this.q >= 0) {
                this.q = 0;
                u();
            }
        }

        private final void u() {
            a this_$iv = a.this;
            synchronized (this_$iv.p2) {
                if (!this_$iv.isTerminated()) {
                    if (((int) (this_$iv.controlState & 2097151)) > this_$iv.y) {
                        if (c.compareAndSet(this, -1, 1)) {
                            int oldIndex = g();
                            o(0);
                            this_$iv.r(this, oldIndex, 0);
                            a this_$iv2 = this_$iv;
                            a aVar = this_$iv2;
                            int lastIndex = (int) (2097151 & a.f.getAndDecrement(this_$iv2));
                            if (lastIndex != oldIndex) {
                                c b = this_$iv.p2.b(lastIndex);
                                k.c(b);
                                c lastWorker = b;
                                this_$iv.p2.c(oldIndex, lastWorker);
                                lastWorker.o(oldIndex);
                                this_$iv.r(lastWorker, lastIndex, oldIndex);
                            }
                            this_$iv.p2.c(lastIndex, null);
                            x xVar = x.a;
                            this.f = d.TERMINATED;
                        }
                    }
                }
            }
        }

        private final void i(int mode) {
            this.q = 0;
            if (this.f == d.PARKING) {
                if (s0.a()) {
                    boolean z2 = true;
                    if (mode != 1) {
                        z2 = false;
                    }
                    if (!z2) {
                        throw new AssertionError();
                    }
                }
                this.f = d.BLOCKING;
            }
        }

        @Nullable
        public final j f(boolean scanLocalQueue) {
            j task;
            if (q()) {
                return e(scanLocalQueue);
            }
            if (scanLocalQueue) {
                task = this.d.h();
                if (task == null) {
                    task = (j) a.this.a2.d();
                }
            } else {
                task = (j) a.this.a2.d();
            }
            return task == null ? t(true) : task;
        }

        private final j e(boolean scanLocalQueue) {
            j it;
            j it2;
            if (scanLocalQueue) {
                boolean globalFirst = k(a.this.y * 2) == 0;
                if (globalFirst && (it2 = m()) != null) {
                    return it2;
                }
                j it3 = this.d.h();
                if (it3 != null) {
                    return it3;
                }
                if (!globalFirst && (it = m()) != null) {
                    return it;
                }
            } else {
                j it4 = m();
                if (it4 != null) {
                    return it4;
                }
            }
            return t(false);
        }

        private final j m() {
            if (k(2) == 0) {
                j it = (j) a.this.p1.d();
                if (it == null) {
                    return (j) a.this.a2.d();
                }
                return it;
            }
            j it2 = (j) a.this.a2.d();
            if (it2 == null) {
                return (j) a.this.p1.d();
            }
            return it2;
        }

        private final j t(boolean blockingOnly) {
            int currentIndex;
            long stealResult;
            if (s0.a()) {
                if ((this.d.f() == 0 ? 1 : 0) == 0) {
                    throw new AssertionError();
                }
            }
            int created = (int) (a.this.controlState & 2097151);
            if (created < 2) {
                return null;
            }
            int currentIndex2 = k(created);
            long minDelay = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            a aVar = a.this;
            int i = 0;
            while (true) {
                long j = 0;
                if (i < created) {
                    int i2 = i + 1;
                    int currentIndex3 = currentIndex2 + 1;
                    if (currentIndex3 > created) {
                        currentIndex3 = 1;
                    }
                    c worker = aVar.p2.b(currentIndex3);
                    if (worker == null || worker == this) {
                        currentIndex = currentIndex3;
                    } else {
                        if (s0.a()) {
                            if (!(this.d.f() == 0)) {
                                throw new AssertionError();
                            }
                        }
                        if (blockingOnly) {
                            stealResult = this.d.k(worker.d);
                        } else {
                            stealResult = this.d.l(worker.d);
                        }
                        currentIndex = currentIndex3;
                        long stealResult2 = stealResult;
                        if (stealResult2 == -1) {
                            return this.d.h();
                        }
                        if (stealResult2 > 0) {
                            minDelay = Math.min(minDelay, stealResult2);
                        }
                    }
                    currentIndex2 = currentIndex;
                    i = i2;
                } else {
                    if (minDelay != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                        j = minDelay;
                    }
                    this.x = j;
                    return null;
                }
            }
        }
    }
}
