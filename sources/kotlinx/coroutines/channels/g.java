package kotlinx.coroutines.channels;

import java.util.concurrent.locks.ReentrantLock;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.internal.f0;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.internal.z;
import kotlinx.coroutines.p;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000BB9\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012 \u0010\t\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\b¢\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\u000e\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00028\u0000H\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ\u001d\u0010\u0013\u001a\u00020\u00122\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010H\u0014¢\u0006\u0004\b\u0013\u0010\u0014J\u0019\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0016\u001a\u00020\u0015H\u0014¢\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001a\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0002H\u0002¢\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001c\u001a\u00020\u00172\u0006\u0010\r\u001a\u00028\u0000H\u0014¢\u0006\u0004\b\u001c\u0010\u001dJ#\u0010 \u001a\u00020\u00172\u0006\u0010\r\u001a\u00028\u00002\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030\u001eH\u0014¢\u0006\u0004\b \u0010!J\u0017\u0010#\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u0012H\u0014¢\u0006\u0004\b#\u0010$J\u0011\u0010%\u001a\u0004\u0018\u00010\u0017H\u0014¢\u0006\u0004\b%\u0010&J\u001d\u0010'\u001a\u0004\u0018\u00010\u00172\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030\u001eH\u0014¢\u0006\u0004\b'\u0010(J\u0019\u0010*\u001a\u0004\u0018\u00010)2\u0006\u0010\f\u001a\u00020\u0002H\u0002¢\u0006\u0004\b*\u0010+R\u001e\u0010-\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170,8\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\b-\u0010.R\u0014\u00102\u001a\u00020/8TX\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0003\u00103R\u0016\u00104\u001a\u00020\u00028\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\b4\u00103R\u0014\u00105\u001a\u00020\u00128DX\u0004¢\u0006\u0006\u001a\u0004\b5\u00106R\u0014\u00107\u001a\u00020\u00128DX\u0004¢\u0006\u0006\u001a\u0004\b7\u00106R\u0014\u00108\u001a\u00020\u00128DX\u0004¢\u0006\u0006\u001a\u0004\b8\u00106R\u0014\u00109\u001a\u00020\u00128DX\u0004¢\u0006\u0006\u001a\u0004\b9\u00106R\u0014\u0010:\u001a\u00020\u00128VX\u0004¢\u0006\u0006\u001a\u0004\b:\u00106R\u0014\u0010;\u001a\u00020\u00128VX\u0004¢\u0006\u0006\u001a\u0004\b;\u00106R\u0018\u0010>\u001a\u00060<j\u0002`=8\u0002X\u0004¢\u0006\u0006\n\u0004\b>\u0010?R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010@¨\u0006A"}, d2 = {"Lkotlinx/coroutines/channels/ArrayChannel;", "E", "", "capacity", "Lkotlinx/coroutines/channels/BufferOverflow;", "onBufferOverflow", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(ILkotlinx/coroutines/channels/BufferOverflow;Lkotlin/jvm/functions/Function1;)V", "currentSize", "element", "enqueueElement", "(ILjava/lang/Object;)V", "Lkotlinx/coroutines/channels/Receive;", "receive", "", "enqueueReceiveInternal", "(Lkotlinx/coroutines/channels/Receive;)Z", "Lkotlinx/coroutines/channels/Send;", "send", "", "enqueueSend", "(Lkotlinx/coroutines/channels/Send;)Ljava/lang/Object;", "ensureCapacity", "(I)V", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "wasClosed", "onCancelIdempotent", "(Z)V", "pollInternal", "()Ljava/lang/Object;", "pollSelectInternal", "(Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/Symbol;", "updateBufferSize", "(I)Lkotlinx/coroutines/internal/Symbol;", "", "buffer", "[Ljava/lang/Object;", "", "getBufferDebugString", "()Ljava/lang/String;", "bufferDebugString", "I", "head", "isBufferAlwaysEmpty", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "isClosedForReceive", "isEmpty", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/channels/BufferOverflow;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractChannel;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ArrayChannel.kt */
public class g<E> extends a<E> {
    private int p0;
    private final int q;
    @NotNull
    private volatile /* synthetic */ int size;
    @NotNull
    private final h x;
    @NotNull
    private final ReentrantLock y;
    @NotNull
    private Object[] z;

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ArrayChannel.kt */
    public final /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[h.values().length];
            iArr[h.SUSPEND.ordinal()] = 1;
            iArr[h.DROP_LATEST.ordinal()] = 2;
            iArr[h.DROP_OLDEST.ordinal()] = 3;
            a = iArr;
        }
    }

    public g(int capacity, @NotNull h onBufferOverflow, @Nullable kotlin.jvm.functions.l<? super E, x> onUndeliveredElement) {
        super(onUndeliveredElement);
        this.q = capacity;
        this.x = onBufferOverflow;
        if (capacity < 1 ? false : true) {
            this.y = new ReentrantLock();
            Object[] $this$buffer_u24lambda_u2d1 = new Object[Math.min(capacity, 8)];
            k.l($this$buffer_u24lambda_u2d1, b.a, 0, 0, 6, (Object) null);
            this.z = $this$buffer_u24lambda_u2d1;
            this.size = 0;
            return;
        }
        throw new IllegalArgumentException(("ArrayChannel capacity must be at least 1, but " + capacity + " was specified").toString());
    }

    /* access modifiers changed from: protected */
    public final boolean L() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean M() {
        return this.size == 0;
    }

    /* access modifiers changed from: protected */
    public final boolean s() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean t() {
        return this.size == this.q && this.x == h.SUSPEND;
    }

    public boolean N() {
        ReentrantLock $this$withLock$iv = this.y;
        $this$withLock$iv.lock();
        try {
            return super.N();
        } finally {
            $this$withLock$iv.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    @NotNull
    public Object v(E element) {
        x xVar;
        f0 token;
        ReentrantLock $this$withLock$iv = this.y;
        $this$withLock$iv.lock();
        try {
            int size2 = this.size;
            o<?> i = i();
            if (i == null) {
                f0 X = X(size2);
                if (X == null) {
                    if (size2 == 0) {
                        do {
                            x B = B();
                            if (B != null) {
                                xVar = B;
                                if (xVar instanceof o) {
                                    this.size = size2;
                                    $this$withLock$iv.unlock();
                                    return xVar;
                                }
                                token = xVar.e(element, (s.c) null);
                            }
                        } while (token == null);
                        if (s0.a()) {
                            if (!(token == p.a)) {
                                throw new AssertionError();
                            }
                        }
                        this.size = size2;
                        x xVar2 = x.a;
                        $this$withLock$iv.unlock();
                        xVar.d(element);
                        return xVar.a();
                    }
                    V(size2, element);
                    f0 f0Var = b.b;
                    $this$withLock$iv.unlock();
                    return f0Var;
                }
                $this$withLock$iv.unlock();
                return X;
            }
            $this$withLock$iv.unlock();
            return i;
        } catch (Throwable th) {
            $this$withLock$iv.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Object f(@NotNull z send) {
        ReentrantLock $this$withLock$iv = this.y;
        $this$withLock$iv.lock();
        try {
            return super.f(send);
        } finally {
            $this$withLock$iv.unlock();
        }
    }

    private final f0 X(int currentSize) {
        if (currentSize < this.q) {
            this.size = currentSize + 1;
            return null;
        }
        switch (a.a[this.x.ordinal()]) {
            case 1:
                return b.c;
            case 2:
                return b.b;
            case 3:
                return null;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final void V(int currentSize, E element) {
        if (currentSize < this.q) {
            W(currentSize);
            Object[] objArr = this.z;
            objArr[(this.p0 + currentSize) % objArr.length] = element;
            return;
        }
        if (s0.a()) {
            if (!(this.x == h.DROP_OLDEST)) {
                throw new AssertionError();
            }
        }
        Object[] objArr2 = this.z;
        int i = this.p0;
        objArr2[i % objArr2.length] = null;
        objArr2[(i + currentSize) % objArr2.length] = element;
        this.p0 = (i + 1) % objArr2.length;
    }

    private final void W(int currentSize) {
        Object[] objArr = this.z;
        if (currentSize >= objArr.length) {
            int newSize = Math.min(objArr.length * 2, this.q);
            Object[] newBuffer = new Object[newSize];
            int i = 0;
            while (i < currentSize) {
                int i2 = i;
                i++;
                Object[] objArr2 = this.z;
                newBuffer[i2] = objArr2[(this.p0 + i2) % objArr2.length];
            }
            k.k(newBuffer, b.a, currentSize, newSize);
            this.z = newBuffer;
            this.p0 = 0;
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Object S() {
        z zVar = null;
        boolean resumed = false;
        ReentrantLock $this$withLock$iv = this.y;
        $this$withLock$iv.lock();
        try {
            int size2 = this.size;
            if (size2 == 0) {
                Object i = i();
                if (i == null) {
                    i = b.d;
                }
                return i;
            }
            Object[] objArr = this.z;
            int i2 = this.p0;
            Object result = objArr[i2];
            objArr[i2] = null;
            this.size = size2 - 1;
            Object replacement = b.d;
            if (size2 == this.q) {
                while (true) {
                    z E = E();
                    if (E == null) {
                        break;
                    }
                    zVar = E;
                    f0 token = zVar.B((s.c) null);
                    if (token != null) {
                        if (s0.a()) {
                            if (!(token == p.a)) {
                                throw new AssertionError();
                            }
                        }
                        resumed = true;
                        replacement = zVar.z();
                    } else {
                        zVar.C();
                    }
                }
            }
            if (replacement != b.d && !(replacement instanceof o)) {
                this.size = size2;
                Object[] objArr2 = this.z;
                objArr2[(this.p0 + size2) % objArr2.length] = replacement;
            }
            this.p0 = (this.p0 + 1) % this.z.length;
            x xVar = x.a;
            $this$withLock$iv.unlock();
            if (resumed) {
                kotlin.jvm.internal.k.c(zVar);
                zVar.y();
            }
            return result;
        } finally {
            $this$withLock$iv.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public boolean K(@NotNull v<? super E> receive) {
        ReentrantLock $this$withLock$iv = this.y;
        $this$withLock$iv.lock();
        try {
            return super.K(receive);
        } finally {
            $this$withLock$iv.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public void O(boolean wasClosed) {
        kotlin.jvm.functions.l onUndeliveredElement = this.d;
        UndeliveredElementException it = null;
        ReentrantLock $this$withLock$iv = this.y;
        $this$withLock$iv.lock();
        try {
            int i = this.size;
            int it2 = 0;
            while (it2 < i) {
                int i2 = it2 + 1;
                Object value = this.z[this.p0];
                if (!(onUndeliveredElement == null || value == b.a)) {
                    it = z.c(onUndeliveredElement, value, it);
                }
                Object[] objArr = this.z;
                int i3 = this.p0;
                objArr[i3] = b.a;
                this.p0 = (i3 + 1) % objArr.length;
                it2 = i2;
            }
            this.size = 0;
            x xVar = x.a;
            $this$withLock$iv.unlock();
            super.O(wasClosed);
            if (it != null) {
                throw it;
            }
        } catch (Throwable th) {
            $this$withLock$iv.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public String g() {
        return "(buffer:capacity=" + this.q + ",size=" + this.size + ')';
    }
}
