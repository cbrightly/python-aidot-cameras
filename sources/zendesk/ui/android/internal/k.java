package zendesk.ui.android.internal;

import android.view.View;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ThrottledOnClickListener.kt */
public final class k {
    public static /* synthetic */ j b(long j, kotlin.jvm.functions.a aVar, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 500;
        }
        return a(j, aVar);
    }

    /* compiled from: ThrottledOnClickListener.kt */
    public static final class a extends j {
        final /* synthetic */ kotlin.jvm.functions.a<x> f;
        final /* synthetic */ long q;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(kotlin.jvm.functions.a<x> $onClick, long $minimumIntervalMillis) {
            super($minimumIntervalMillis);
            this.f = $onClick;
            this.q = $minimumIntervalMillis;
        }

        public void a(@Nullable View view) {
            this.f.invoke();
        }
    }

    @NotNull
    public static final j a(long minimumIntervalMillis, @NotNull kotlin.jvm.functions.a<x> onClick) {
        kotlin.jvm.internal.k.e(onClick, "onClick");
        return new a(onClick, minimumIntervalMillis);
    }
}
