package kotlinx.coroutines.channels;

import java.util.ArrayList;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.c;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.internal.f0;
import kotlinx.coroutines.internal.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012 \u0010\u0003\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0006¢\u0006\u0002\u0010\u0007J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0014¢\u0006\u0002\u0010\u0015J/\u0010\u0016\u001a\u00020\u00052\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001bH\u0014ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001c\u0010\u001dR\u0014\u0010\b\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0014\u0010\u000b\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\f\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\n\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u001e"}, d2 = {"Lkotlinx/coroutines/channels/LinkedListChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "offerInternal", "", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "onCancelIdempotentList", "list", "Lkotlinx/coroutines/internal/InlineList;", "Lkotlinx/coroutines/channels/Send;", "closed", "Lkotlinx/coroutines/channels/Closed;", "onCancelIdempotentList-w-w6eGU", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: LinkedListChannel.kt */
public class r<E> extends a<E> {
    public r(@Nullable kotlin.jvm.functions.l<? super E, x> onUndeliveredElement) {
        super(onUndeliveredElement);
    }

    /* access modifiers changed from: protected */
    public final boolean L() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean M() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean s() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean t() {
        return false;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Object v(E element) {
        x sendResult;
        do {
            Object result = super.v(element);
            f0 f0Var = b.b;
            if (result == f0Var) {
                return f0Var;
            }
            if (result == b.c) {
                sendResult = z(element);
                if (sendResult == null) {
                    return f0Var;
                }
            } else if (result instanceof o) {
                return result;
            } else {
                throw new IllegalStateException(k.l("Invalid offerInternal result ", result).toString());
            }
        } while (!(sendResult instanceof o));
        return sendResult;
    }

    /* access modifiers changed from: protected */
    public void P(@NotNull Object list, @NotNull o<?> closed) {
        UndeliveredElementException it = null;
        if (list != null) {
            UndeliveredElementException undeliveredElementException = null;
            if (!(list instanceof ArrayList)) {
                z it2 = (z) list;
                if (it2 instanceof c.a) {
                    kotlin.jvm.functions.l<E, x> lVar = this.d;
                    if (lVar != null) {
                        undeliveredElementException = z.c(lVar, ((c.a) it2).q, (UndeliveredElementException) null);
                    }
                    it = undeliveredElementException;
                } else {
                    it2.A(closed);
                }
            } else {
                ArrayList list$iv = (ArrayList) list;
                int size = list$iv.size() - 1;
                if (size >= 0) {
                    do {
                        int i$iv = size;
                        size--;
                        z it3 = (z) list$iv.get(i$iv);
                        if (it3 instanceof c.a) {
                            kotlin.jvm.functions.l<E, x> lVar2 = this.d;
                            it = lVar2 == null ? null : z.c(lVar2, ((c.a) it3).q, it);
                            continue;
                        } else {
                            it3.A(closed);
                            continue;
                        }
                    } while (size >= 0);
                }
            }
        }
        if (it != null) {
            throw it;
        }
    }
}
