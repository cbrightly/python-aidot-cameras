package kotlinx.coroutines;

import kotlin.NoWhenBranchMatchedException;
import kotlin.coroutines.d;
import kotlin.coroutines.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlinx.coroutines.intrinsics.b;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JC\u0010\b\u001a\u00020\t\"\u0004\b\u0000\u0010\n2\u001c\u0010\u000b\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\n0\rH\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0010J\\\u0010\b\u001a\u00020\t\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\n2'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u0002H\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0012¢\u0006\u0002\b\u00132\u0006\u0010\u0014\u001a\u0002H\u00112\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\n0\rH\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0015R\u001a\u0010\u0003\u001a\u00020\u00048FX\u0004¢\u0006\f\u0012\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0007j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019\u0002\u0004\n\u0002\b\u0019¨\u0006\u001a"}, d2 = {"Lkotlinx/coroutines/CoroutineStart;", "", "(Ljava/lang/String;I)V", "isLazy", "", "isLazy$annotations", "()V", "()Z", "invoke", "", "T", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "completion", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "DEFAULT", "LAZY", "ATOMIC", "UNDISPATCHED", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: CoroutineStart.kt */
public enum q0 {
    DEFAULT,
    LAZY,
    ATOMIC,
    UNDISPATCHED;

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineStart.kt */
    public final /* synthetic */ class a {
        public static final /* synthetic */ int[] a = null;

        static {
            int[] iArr = new int[q0.values().length];
            iArr[q0.DEFAULT.ordinal()] = 1;
            iArr[q0.ATOMIC.ordinal()] = 2;
            iArr[q0.UNDISPATCHED.ordinal()] = 3;
            iArr[q0.LAZY.ordinal()] = 4;
            a = iArr;
        }
    }

    public final <T> void invoke(@NotNull kotlin.jvm.functions.l<? super d<? super T>, ? extends Object> block, @NotNull d<? super T> completion) {
        switch (a.a[ordinal()]) {
            case 1:
                kotlinx.coroutines.intrinsics.a.c(block, completion);
                return;
            case 2:
                f.a(block, completion);
                return;
            case 3:
                b.a(block, completion);
                return;
            case 4:
                return;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final <R, T> void invoke(@NotNull p<? super R, ? super d<? super T>, ? extends Object> block, R receiver, @NotNull d<? super T> completion) {
        switch (a.a[ordinal()]) {
            case 1:
                kotlinx.coroutines.intrinsics.a.e(block, receiver, completion, (kotlin.jvm.functions.l) null, 4, (Object) null);
                return;
            case 2:
                f.b(block, receiver, completion);
                return;
            case 3:
                b.b(block, receiver, completion);
                return;
            case 4:
                return;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public final boolean isLazy() {
        return this == LAZY;
    }
}
