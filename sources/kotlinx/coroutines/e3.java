package kotlinx.coroutines;

import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.internal.i;
import kotlinx.coroutines.internal.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0011\u0010\u0000\u001a\u00020\u0001H@ø\u0001\u0000¢\u0006\u0002\u0010\u0002\u0002\u0004\n\u0002\b\u0019¨\u0006\u0003"}, d2 = {"yield", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: Yield.kt */
public final class e3 {
    @Nullable
    public static final Object a(@NotNull d<? super x> $completion) {
        x xVar;
        d uCont = $completion;
        g context = uCont.getContext();
        c2.i(context);
        d<? super x> c = b.c(uCont);
        i cont = c instanceof i ? (i) c : null;
        if (cont == null) {
            xVar = x.a;
        } else {
            if (cont.x.isDispatchNeeded(context)) {
                cont.m(context, x.a);
            } else {
                d3 yieldContext = new d3();
                g plus = context.plus(yieldContext);
                x xVar2 = x.a;
                cont.m(plus, xVar2);
                if (yieldContext.d) {
                    xVar = j.d(cont) ? c.d() : xVar2;
                }
            }
            xVar = c.d();
        }
        if (xVar == c.d()) {
            h.c($completion);
        }
        return xVar == c.d() ? xVar : x.a;
    }
}
