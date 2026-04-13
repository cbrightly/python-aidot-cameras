package kotlinx.coroutines;

import kotlin.coroutines.d;
import kotlin.l;
import kotlin.o;
import kotlin.p;
import kotlinx.coroutines.internal.i;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0007\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\bH\u0000\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0005\u001a\u00020\u0001*\u00020\u00028@X\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\t"}, d2 = {"classSimpleName", "", "", "getClassSimpleName", "(Ljava/lang/Object;)Ljava/lang/String;", "hexAddress", "getHexAddress", "toDebugString", "Lkotlin/coroutines/Continuation;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: DebugStrings.kt */
public final class t0 {
    @NotNull
    public static final String b(@NotNull Object $this$hexAddress) {
        return Integer.toHexString(System.identityHashCode($this$hexAddress));
    }

    @NotNull
    public static final String c(@NotNull d<?> $this$toDebugString) {
        String str;
        if ($this$toDebugString instanceof i) {
            return $this$toDebugString.toString();
        }
        try {
            o.a aVar = o.Companion;
            d $this$toDebugString_u24lambda_u2d0 = $this$toDebugString;
            str = o.m17constructorimpl($this$toDebugString_u24lambda_u2d0 + '@' + b($this$toDebugString_u24lambda_u2d0));
        } catch (Throwable th) {
            o.a aVar2 = o.Companion;
            str = o.m17constructorimpl(p.a(th));
        }
        Throwable r2 = o.m20exceptionOrNullimpl(str);
        if (r2 != null) {
            Throwable th2 = r2;
            str = $this$toDebugString.getClass().getName() + '@' + b($this$toDebugString);
        }
        return (String) str;
    }

    @NotNull
    public static final String a(@NotNull Object $this$classSimpleName) {
        return $this$classSimpleName.getClass().getSimpleName();
    }
}
