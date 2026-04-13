package kotlinx.coroutines;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0003\n\u0000\b\u0010\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\f\u001a\u00020\u0007H\u0016J\u0010\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0003R\u0014\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078PX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t¨\u0006\u0010"}, d2 = {"Lkotlinx/coroutines/JobImpl;", "Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/CompletableJob;", "parent", "Lkotlinx/coroutines/Job;", "(Lkotlinx/coroutines/Job;)V", "handlesException", "", "getHandlesException$kotlinx_coroutines_core", "()Z", "onCancelComplete", "getOnCancelComplete$kotlinx_coroutines_core", "complete", "completeExceptionally", "exception", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: JobSupport.kt */
public class b2 extends g2 implements z {
    private final boolean d = R0();

    public b2(@Nullable z1 parent) {
        super(true);
        o0(parent);
    }

    public boolean i0() {
        return true;
    }

    public boolean h0() {
        return this.d;
    }

    public boolean complete() {
        return t0(x.a);
    }

    public boolean a(@NotNull Throwable exception) {
        return t0(new b0(exception, false, 2, (DefaultConstructorMarker) null));
    }

    private final boolean R0() {
        t k0 = k0();
        u uVar = k0 instanceof u ? (u) k0 : null;
        g2 parentJob = uVar == null ? null : uVar.z();
        if (parentJob == null) {
            return false;
        }
        while (!parentJob.h0()) {
            t k02 = parentJob.k0();
            u uVar2 = k02 instanceof u ? (u) k02 : null;
            g2 z = uVar2 == null ? null : uVar2.z();
            if (z == null) {
                return false;
            }
            parentJob = z;
        }
        return true;
    }
}
