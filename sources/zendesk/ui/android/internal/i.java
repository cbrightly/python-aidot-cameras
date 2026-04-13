package zendesk.ui.android.internal;

import android.text.Editable;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ThrottledAfterTextChanged.kt */
public final class i {
    public static /* synthetic */ h b(long j, l lVar, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 20;
        }
        return a(j, lVar);
    }

    /* compiled from: ThrottledAfterTextChanged.kt */
    public static final class a extends h {
        final /* synthetic */ l<Editable, x> f;
        final /* synthetic */ long q;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(l<? super Editable, x> $afterTextChanged, long $minimumIntervalMillis) {
            super($minimumIntervalMillis);
            this.f = $afterTextChanged;
            this.q = $minimumIntervalMillis;
        }

        public void a(@Nullable Editable text) {
            this.f.invoke(text);
        }
    }

    @NotNull
    public static final h a(long minimumIntervalMillis, @NotNull l<? super Editable, x> afterTextChanged) {
        k.e(afterTextChanged, "afterTextChanged");
        return new a(afterTextChanged, minimumIntervalMillis);
    }
}
