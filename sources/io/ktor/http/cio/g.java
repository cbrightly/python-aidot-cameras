package io.ktor.http.cio;

import io.ktor.utils.io.pool.b;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpHeadersMap.kt */
public final class g {
    /* access modifiers changed from: private */
    public static final int[] a = new int[0];
    /* access modifiers changed from: private */
    public static final b<int[]> b = new a(1000);

    public static final void c(@NotNull f $this$dumpTo, @NotNull String indent, @NotNull Appendable out) {
        k.f($this$dumpTo, "$this$dumpTo");
        k.f(indent, "indent");
        k.f(out, "out");
        int g = $this$dumpTo.g();
        for (int i = 0; i < g; i++) {
            out.append(indent);
            out.append($this$dumpTo.h(i));
            out.append(" => ");
            out.append($this$dumpTo.k(i));
            out.append("\n");
        }
    }

    /* compiled from: HttpHeadersMap.kt */
    public static final class a extends b<int[]> {
        a(int $super_call_param$0) {
            super($super_call_param$0);
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: s */
        public int[] l() {
            return new int[512];
        }
    }
}
