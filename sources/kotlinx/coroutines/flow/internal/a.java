package kotlinx.coroutines.flow.internal;

import java.util.Arrays;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.flow.internal.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b \u0018\u0000*\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u00060\u0003j\u0002`\u0004B\u0005¢\u0006\u0002\u0010\u0005J\r\u0010\u0018\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010\u0019J\r\u0010\u001a\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0019J\u001d\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000e2\u0006\u0010\u001c\u001a\u00020\tH$¢\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u00020\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u001f0!H\bJ\u0015\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010$R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\tX\u000e¢\u0006\u0002\n\u0000R:\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u000e2\u0010\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u000e@BX\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u0012\u0004\b\u0010\u0010\u0005\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006%"}, d2 = {"Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "S", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "()V", "_subscriptionCount", "Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;", "<set-?>", "", "nCollectors", "getNCollectors", "()I", "nextIndex", "", "slots", "getSlots$annotations", "getSlots", "()[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "getSubscriptionCount", "()Lkotlinx/coroutines/flow/StateFlow;", "allocateSlot", "()Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "createSlot", "createSlotArray", "size", "(I)[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "forEachSlotLocked", "", "block", "Lkotlin/Function1;", "freeSlot", "slot", "(Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: AbstractSharedFlow.kt */
public abstract class a<S extends c<?>> {
    /* access modifiers changed from: private */
    @Nullable
    public S[] c;
    /* access modifiers changed from: private */
    public int d;
    private int f;
    @Nullable
    private t q;

    /* access modifiers changed from: protected */
    @NotNull
    public abstract S f();

    /* access modifiers changed from: protected */
    @NotNull
    public abstract S[] g(int i);

    /* access modifiers changed from: protected */
    @Nullable
    public final S[] j() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public final int i() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final S e() {
        c slot;
        t tVar;
        synchronized (this) {
            c[] curSlots = j();
            if (curSlots == null) {
                c[] it = g(2);
                this.c = it;
                curSlots = it;
            } else if (i() >= curSlots.length) {
                S[] it2 = Arrays.copyOf(curSlots, curSlots.length * 2);
                k.d(it2, "copyOf(this, newSize)");
                this.c = it2;
                curSlots = (c[]) it2;
            }
            int index = this.f;
            do {
                c it3 = curSlots[index];
                if (it3 == null) {
                    it3 = f();
                    curSlots[index] = it3;
                }
                slot = it3;
                index++;
                if (index >= curSlots.length) {
                    index = 0;
                }
            } while (!slot.a(this));
            this.f = index;
            this.d = i() + 1;
            tVar = this.q;
        }
        c slot2 = slot;
        if (tVar != null) {
            tVar.V(1);
        }
        return slot2;
    }

    /* access modifiers changed from: protected */
    public final void h(@NotNull S slot) {
        t tVar;
        int i;
        d[] b;
        synchronized (this) {
            this.d = i() - 1;
            tVar = this.q;
            i = 0;
            if (i() == 0) {
                this.f = 0;
            }
            b = slot.b(this);
        }
        d[] resumes = b;
        int length = resumes.length;
        while (i < length) {
            d cont = resumes[i];
            i++;
            if (cont != null) {
                o.a aVar = o.Companion;
                cont.resumeWith(o.m17constructorimpl(x.a));
            }
        }
        if (tVar != null) {
            tVar.V(-1);
        }
    }
}
