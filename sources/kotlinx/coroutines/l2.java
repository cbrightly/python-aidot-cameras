package kotlinx.coroutines;

import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.internal.q;
import kotlinx.coroutines.internal.s;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJ\b\u0010\r\u001a\u00020\u000bH\u0016R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00008VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/NodeList;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/Incomplete;", "()V", "isActive", "", "()Z", "list", "getList", "()Lkotlinx/coroutines/NodeList;", "getString", "", "state", "toString", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: JobSupport.kt */
public final class l2 extends q implements u1 {
    public boolean isActive() {
        return true;
    }

    @NotNull
    public l2 b() {
        return this;
    }

    @NotNull
    public final String A(@NotNull String state) {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$getString_u24lambda_u2d1 = sb;
        $this$getString_u24lambda_u2d1.append("List{");
        $this$getString_u24lambda_u2d1.append(state);
        $this$getString_u24lambda_u2d1.append("}[");
        boolean first = true;
        for (s cur$iv = (s) m(); !k.a(cur$iv, this); cur$iv = cur$iv.n()) {
            if (cur$iv instanceof f2) {
                f2 node = (f2) cur$iv;
                if (first) {
                    first = false;
                } else {
                    $this$getString_u24lambda_u2d1.append(", ");
                }
                $this$getString_u24lambda_u2d1.append(node);
            }
        }
        $this$getString_u24lambda_u2d1.append("]");
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    @NotNull
    public String toString() {
        return s0.c() ? A("Active") : super.toString();
    }
}
