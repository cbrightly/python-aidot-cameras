package androidx.activity.contextaware;

import android.content.Context;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.p;
import kotlinx.coroutines.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: ContextAware.kt */
public final class ContextAwareKt$withContextAvailable$2$listener$1 implements OnContextAvailableListener {
    final /* synthetic */ n<R> $co;
    final /* synthetic */ l<Context, R> $onContextAvailable;

    public ContextAwareKt$withContextAvailable$2$listener$1(n<? super R> $co2, l<? super Context, ? extends R> $onContextAvailable2) {
        this.$co = $co2;
        this.$onContextAvailable = $onContextAvailable2;
    }

    public void onContextAvailable(@NotNull Context context) {
        Object obj;
        k.e(context, "context");
        n<R> nVar = this.$co;
        l<Context, R> lVar = this.$onContextAvailable;
        try {
            o.a aVar = o.Companion;
            obj = o.m17constructorimpl(lVar.invoke(context));
        } catch (Throwable th) {
            o.a aVar2 = o.Companion;
            obj = o.m17constructorimpl(p.a(th));
        }
        nVar.resumeWith(obj);
    }
}
