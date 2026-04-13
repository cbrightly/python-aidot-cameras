package coil.util;

import androidx.core.app.NotificationCompat;
import java.io.IOException;
import kotlin.jvm.functions.l;
import kotlin.o;
import kotlin.p;
import kotlin.x;
import kotlinx.coroutines.n;
import okhttp3.d0;
import okhttp3.e;
import okhttp3.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Calls.kt */
public final class k implements f, l<Throwable, x> {
    @NotNull
    private final e c;
    @NotNull
    private final n<d0> d;

    public k(@NotNull e call, @NotNull n<? super d0> continuation) {
        kotlin.jvm.internal.k.e(call, NotificationCompat.CATEGORY_CALL);
        kotlin.jvm.internal.k.e(continuation, "continuation");
        this.c = call;
        this.d = continuation;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        a((Throwable) p1);
        return x.a;
    }

    public void onResponse(@NotNull e call, @NotNull d0 response) {
        kotlin.jvm.internal.k.e(call, NotificationCompat.CATEGORY_CALL);
        kotlin.jvm.internal.k.e(response, "response");
        n<d0> nVar = this.d;
        o.a aVar = o.Companion;
        nVar.resumeWith(o.m17constructorimpl(response));
    }

    public void onFailure(@NotNull e call, @NotNull IOException e) {
        kotlin.jvm.internal.k.e(call, NotificationCompat.CATEGORY_CALL);
        kotlin.jvm.internal.k.e(e, "e");
        if (!call.isCanceled()) {
            n<d0> nVar = this.d;
            o.a aVar = o.Companion;
            nVar.resumeWith(o.m17constructorimpl(p.a(e)));
        }
    }

    public void a(@Nullable Throwable cause) {
        try {
            this.c.cancel();
        } catch (Throwable th) {
        }
    }
}
