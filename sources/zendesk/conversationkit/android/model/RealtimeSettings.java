package zendesk.conversationkit.android.model;

import com.google.chip.chiptool.setuppayloadscanner.a;
import com.squareup.moshi.g;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: User.kt */
public final class RealtimeSettings {
    private final boolean a;
    @NotNull
    private final String b;
    private final long c;
    private final int d;
    private final long e;
    @NotNull
    private final TimeUnit f;
    @NotNull
    private final String g;
    @NotNull
    private final String h;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RealtimeSettings)) {
            return false;
        }
        RealtimeSettings realtimeSettings = (RealtimeSettings) obj;
        return this.a == realtimeSettings.a && k.a(this.b, realtimeSettings.b) && this.c == realtimeSettings.c && this.d == realtimeSettings.d && this.e == realtimeSettings.e && this.f == realtimeSettings.f && k.a(this.g, realtimeSettings.g) && k.a(this.h, realtimeSettings.h);
    }

    public int hashCode() {
        boolean z = this.a;
        if (z) {
            z = true;
        }
        return ((((((((((((((z ? 1 : 0) * true) + this.b.hashCode()) * 31) + a.a(this.c)) * 31) + this.d) * 31) + a.a(this.e)) * 31) + this.f.hashCode()) * 31) + this.g.hashCode()) * 31) + this.h.hashCode();
    }

    @NotNull
    public String toString() {
        return "RealtimeSettings(enabled=" + this.a + ", baseUrl=" + this.b + ", retryInterval=" + this.c + ", maxConnectionAttempts=" + this.d + ", connectionDelay=" + this.e + ", timeUnit=" + this.f + ", appId=" + this.g + ", userId=" + this.h + ')';
    }

    public RealtimeSettings(boolean enabled, @NotNull String baseUrl, long retryInterval, int maxConnectionAttempts, long connectionDelay, @NotNull TimeUnit timeUnit, @NotNull String appId, @NotNull String userId) {
        k.e(baseUrl, "baseUrl");
        k.e(timeUnit, "timeUnit");
        k.e(appId, "appId");
        k.e(userId, "userId");
        this.a = enabled;
        this.b = baseUrl;
        this.c = retryInterval;
        this.d = maxConnectionAttempts;
        this.e = connectionDelay;
        this.f = timeUnit;
        this.g = appId;
        this.h = userId;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ RealtimeSettings(boolean r13, java.lang.String r14, long r15, int r17, long r18, java.util.concurrent.TimeUnit r20, java.lang.String r21, java.lang.String r22, int r23, kotlin.jvm.internal.DefaultConstructorMarker r24) {
        /*
            r12 = this;
            r0 = r23 & 32
            if (r0 == 0) goto L_0x0008
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS
            r9 = r0
            goto L_0x000a
        L_0x0008:
            r9 = r20
        L_0x000a:
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            r6 = r17
            r7 = r18
            r10 = r21
            r11 = r22
            r1.<init>(r2, r3, r4, r6, r7, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.model.RealtimeSettings.<init>(boolean, java.lang.String, long, int, long, java.util.concurrent.TimeUnit, java.lang.String, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final boolean d() {
        return this.a;
    }

    @NotNull
    public final String b() {
        return this.b;
    }

    public final long f() {
        return this.c;
    }

    public final int e() {
        return this.d;
    }

    public final long c() {
        return this.e;
    }

    @NotNull
    public final TimeUnit g() {
        return this.f;
    }

    @NotNull
    public final String a() {
        return this.g;
    }

    @NotNull
    public final String h() {
        return this.h;
    }
}
