package zendesk.conversationkit.android.model;

import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RestRetryPolicy.kt */
public final class x {
    private final int a;
    private final int b;
    @NotNull
    private final TimeUnit c;
    private final int d;
    private final int e;

    public x() {
        this(0, 0, (TimeUnit) null, 0, 0, 31, (DefaultConstructorMarker) null);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof x)) {
            return false;
        }
        x xVar = (x) obj;
        return this.a == xVar.a && this.b == xVar.b && this.c == xVar.c && this.d == xVar.d && this.e == xVar.e;
    }

    public int hashCode() {
        return (((((((this.a * 31) + this.b) * 31) + this.c.hashCode()) * 31) + this.d) * 31) + this.e;
    }

    @NotNull
    public String toString() {
        return "RestRetryPolicy(regular=" + this.a + ", aggressive=" + this.b + ", timeUnit=" + this.c + ", backoffMultiplier=" + this.d + ", maxRetries=" + this.e + ')';
    }

    public x(int regular, int aggressive, @NotNull TimeUnit timeUnit, int backoffMultiplier, int maxRetries) {
        k.e(timeUnit, "timeUnit");
        this.a = regular;
        this.b = aggressive;
        this.c = timeUnit;
        this.d = backoffMultiplier;
        this.e = maxRetries;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ x(int r4, int r5, java.util.concurrent.TimeUnit r6, int r7, int r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r3 = this;
            r10 = r9 & 1
            if (r10 == 0) goto L_0x0006
            r4 = 60
        L_0x0006:
            r10 = r9 & 2
            if (r10 == 0) goto L_0x000e
            r5 = 15
            r10 = r5
            goto L_0x000f
        L_0x000e:
            r10 = r5
        L_0x000f:
            r5 = r9 & 4
            if (r5 == 0) goto L_0x0017
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.SECONDS
            r0 = r6
            goto L_0x0018
        L_0x0017:
            r0 = r6
        L_0x0018:
            r5 = r9 & 8
            if (r5 == 0) goto L_0x001f
            r7 = 2
            r1 = r7
            goto L_0x0020
        L_0x001f:
            r1 = r7
        L_0x0020:
            r5 = r9 & 16
            if (r5 == 0) goto L_0x0027
            r8 = 5
            r2 = r8
            goto L_0x0028
        L_0x0027:
            r2 = r8
        L_0x0028:
            r5 = r3
            r6 = r4
            r7 = r10
            r8 = r0
            r9 = r1
            r10 = r2
            r5.<init>(r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.model.x.<init>(int, int, java.util.concurrent.TimeUnit, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
