package kotlinx.coroutines;

import kotlin.l;
import kotlinx.coroutines.internal.f0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016*\u0004\u0018\u00010\u0016H\u0000\u001a\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0016*\u0004\u0018\u00010\u0016H\u0000\"\u0016\u0010\u0000\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0003\"\u0016\u0010\u0006\u001a\u00020\u00018\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0007\u0010\u0003\"\u0016\u0010\b\u001a\u00020\t8\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\n\u0010\u0003\"\u0016\u0010\u000b\u001a\u00020\t8\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\u0003\"\u000e\u0010\r\u001a\u00020\u000eXT¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u000eXT¢\u0006\u0002\n\u0000\"\u0016\u0010\u0010\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0011\u0010\u0003\"\u0016\u0010\u0012\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0013\u0010\u0003\"\u000e\u0010\u0014\u001a\u00020\u000eXT¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"COMPLETING_ALREADY", "Lkotlinx/coroutines/internal/Symbol;", "getCOMPLETING_ALREADY$annotations", "()V", "COMPLETING_RETRY", "getCOMPLETING_RETRY$annotations", "COMPLETING_WAITING_CHILDREN", "getCOMPLETING_WAITING_CHILDREN$annotations", "EMPTY_ACTIVE", "Lkotlinx/coroutines/Empty;", "getEMPTY_ACTIVE$annotations", "EMPTY_NEW", "getEMPTY_NEW$annotations", "FALSE", "", "RETRY", "SEALED", "getSEALED$annotations", "TOO_LATE_TO_CANCEL", "getTOO_LATE_TO_CANCEL$annotations", "TRUE", "boxIncomplete", "", "unboxState", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: JobSupport.kt */
public final class h2 {
    /* access modifiers changed from: private */
    @NotNull
    public static final f0 a = new f0("COMPLETING_ALREADY");
    @NotNull
    public static final f0 b = new f0("COMPLETING_WAITING_CHILDREN");
    /* access modifiers changed from: private */
    @NotNull
    public static final f0 c = new f0("COMPLETING_RETRY");
    /* access modifiers changed from: private */
    @NotNull
    public static final f0 d = new f0("TOO_LATE_TO_CANCEL");
    /* access modifiers changed from: private */
    @NotNull
    public static final f0 e = new f0("SEALED");
    /* access modifiers changed from: private */
    @NotNull
    public static final i1 f = new i1(false);
    /* access modifiers changed from: private */
    @NotNull
    public static final i1 g = new i1(true);

    @Nullable
    public static final Object g(@Nullable Object $this$boxIncomplete) {
        return $this$boxIncomplete instanceof u1 ? new v1((u1) $this$boxIncomplete) : $this$boxIncomplete;
    }

    @Nullable
    public static final Object h(@Nullable Object $this$unboxState) {
        u1 u1Var;
        v1 v1Var = $this$unboxState instanceof v1 ? (v1) $this$unboxState : null;
        return (v1Var == null || (u1Var = v1Var.a) == null) ? $this$unboxState : u1Var;
    }
}
