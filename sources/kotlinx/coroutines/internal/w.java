package kotlinx.coroutines.internal;

import java.util.Iterator;
import java.util.List;
import kotlin.l;
import kotlin.sequences.m;
import kotlin.sequences.o;
import kotlinx.coroutines.k2;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lkotlinx/coroutines/internal/MainDispatcherLoader;", "", "()V", "FAST_SERVICE_LOADER_ENABLED", "", "dispatcher", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "loadMainDispatcher", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: MainDispatchers.kt */
public final class w {
    @NotNull
    public static final w a;
    private static final boolean b = g0.e("kotlinx.coroutines.fast.service.loader", true);
    @NotNull
    public static final k2 c;

    private w() {
    }

    static {
        w wVar = new w();
        a = wVar;
        c = wVar.a();
    }

    private final k2 a() {
        List<v> $this$maxByOrNull$iv;
        Object maxElem$iv;
        try {
            if (b) {
                $this$maxByOrNull$iv = l.a.c();
            } else {
                $this$maxByOrNull$iv = o.C(m.c(a.b()));
            }
            Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
            if (!iterator$iv.hasNext()) {
                maxElem$iv = null;
            } else {
                maxElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    int maxValue$iv = ((v) maxElem$iv).getLoadPriority();
                    do {
                        Object next = iterator$iv.next();
                        int v$iv = ((v) next).getLoadPriority();
                        if (maxValue$iv < v$iv) {
                            maxElem$iv = next;
                            maxValue$iv = v$iv;
                        }
                    } while (iterator$iv.hasNext());
                }
            }
            v vVar = (v) maxElem$iv;
            k2 e = vVar == null ? null : x.e(vVar, $this$maxByOrNull$iv);
            if (e == null) {
                return x.b((Throwable) null, (String) null, 3, (Object) null);
            }
            return e;
        } catch (Throwable e2) {
            return x.b(e2, (String) null, 2, (Object) null);
        }
    }
}
