package kotlinx.coroutines.channels;

import java.util.concurrent.locks.ReentrantLock;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.internal.f0;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.internal.z;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012 \u0010\u0003\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\u0018\u001a\u00020\r2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u001aH\u0014J\u0015\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u001dJ!\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00028\u00002\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 H\u0014¢\u0006\u0002\u0010!J\u0010\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\rH\u0014J\n\u0010$\u001a\u0004\u0018\u00010\u0017H\u0014J\u0016\u0010%\u001a\u0004\u0018\u00010\u00172\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 H\u0014J\u0014\u0010&\u001a\u0004\u0018\u00010'2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0017H\u0002R\u0014\u0010\b\u001a\u00020\t8TX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8DX\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\r8DX\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\r8DX\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0014\u0010\u0011\u001a\u00020\r8DX\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000eR\u0014\u0010\u0012\u001a\u00020\r8VX\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000eR\u0012\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u000e¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lkotlinx/coroutines/channels/ConflatedChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "bufferDebugString", "", "getBufferDebugString", "()Ljava/lang/String;", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "isEmpty", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "value", "", "enqueueReceiveInternal", "receive", "Lkotlinx/coroutines/channels/Receive;", "offerInternal", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "onCancelIdempotent", "wasClosed", "pollInternal", "pollSelectInternal", "updateValueLocked", "Lkotlinx/coroutines/internal/UndeliveredElementException;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ConflatedChannel.kt */
public class p<E> extends a<E> {
    @NotNull
    private final ReentrantLock q = new ReentrantLock();
    @Nullable
    private Object x = b.a;

    public p(@Nullable kotlin.jvm.functions.l<? super E, x> onUndeliveredElement) {
        super(onUndeliveredElement);
    }

    /* access modifiers changed from: protected */
    public final boolean L() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean M() {
        return this.x == b.a;
    }

    /* access modifiers changed from: protected */
    public final boolean s() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean t() {
        return false;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    @NotNull
    public Object v(E element) {
        x xVar;
        f0 token;
        ReentrantLock $this$withLock$iv = this.q;
        $this$withLock$iv.lock();
        try {
            o<?> i = i();
            if (i == null) {
                if (this.x == b.a) {
                    do {
                        x B = B();
                        if (B != null) {
                            xVar = B;
                            if (xVar instanceof o) {
                                $this$withLock$iv.unlock();
                                return xVar;
                            }
                            token = xVar.e(element, (s.c) null);
                        }
                    } while (token == null);
                    if (s0.a()) {
                        if (!(token == kotlinx.coroutines.p.a)) {
                            throw new AssertionError();
                        }
                    }
                    x xVar2 = x.a;
                    $this$withLock$iv.unlock();
                    xVar.d(element);
                    return xVar.a();
                }
                UndeliveredElementException it = V(element);
                if (it == null) {
                    f0 f0Var = b.b;
                    $this$withLock$iv.unlock();
                    return f0Var;
                }
                throw it;
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
    public Object S() {
        ReentrantLock $this$withLock$iv = this.q;
        $this$withLock$iv.lock();
        try {
            Object obj = this.x;
            f0 f0Var = b.a;
            if (obj == f0Var) {
                Object i = i();
                if (i == null) {
                    i = b.d;
                }
                return i;
            }
            Object result = obj;
            this.x = f0Var;
            x xVar = x.a;
            $this$withLock$iv.unlock();
            return result;
        } finally {
            $this$withLock$iv.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public void O(boolean wasClosed) {
        ReentrantLock $this$withLock$iv = this.q;
        $this$withLock$iv.lock();
        try {
            UndeliveredElementException it = V(b.a);
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

    private final UndeliveredElementException V(Object element) {
        kotlin.jvm.functions.l<E, x> lVar;
        Object old = this.x;
        UndeliveredElementException undeliveredElementException = null;
        if (!(old == b.a || (lVar = this.d) == null)) {
            undeliveredElementException = z.d(lVar, old, (UndeliveredElementException) null, 2, (Object) null);
        }
        UndeliveredElementException undeliveredElementException2 = undeliveredElementException;
        this.x = element;
        return undeliveredElementException2;
    }

    /* access modifiers changed from: protected */
    public boolean K(@NotNull v<? super E> receive) {
        ReentrantLock $this$withLock$iv = this.q;
        $this$withLock$iv.lock();
        try {
            return super.K(receive);
        } finally {
            $this$withLock$iv.unlock();
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public String g() {
        return "(value=" + this.x + ')';
    }
}
