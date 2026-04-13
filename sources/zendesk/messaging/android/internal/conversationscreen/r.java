package zendesk.messaging.android.internal.conversationscreen;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: MessageLogTimestampFormatter.kt */
public final class r {
    @NotNull
    private final SimpleDateFormat a;
    @NotNull
    private final SimpleDateFormat b;

    public r(@NotNull Context context, @NotNull Locale locale, @NotNull TimeZone timeZone, boolean is24HourFormat) {
        k.e(context, "context");
        k.e(locale, "locale");
        k.e(timeZone, "timeZone");
        SimpleDateFormat $this$timeOnlyFormat_u24lambda_u2d0 = new SimpleDateFormat(is24HourFormat ? "H:mm" : "h:mm a", locale);
        $this$timeOnlyFormat_u24lambda_u2d0.setTimeZone(timeZone);
        x xVar = x.a;
        this.a = $this$timeOnlyFormat_u24lambda_u2d0;
        SimpleDateFormat $this$dayAndTimeFormat_u24lambda_u2d1 = new SimpleDateFormat(is24HourFormat ? "MMMM d, H:mm" : "MMMM d, h:mm a", locale);
        $this$dayAndTimeFormat_u24lambda_u2d1.setTimeZone(timeZone);
        this.b = $this$dayAndTimeFormat_u24lambda_u2d1;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ r(android.content.Context r2, java.util.Locale r3, java.util.TimeZone r4, boolean r5, int r6, kotlin.jvm.internal.DefaultConstructorMarker r7) {
        /*
            r1 = this;
            r7 = r6 & 2
            java.lang.String r0 = "getDefault()"
            if (r7 == 0) goto L_0x000d
            java.util.Locale r3 = java.util.Locale.getDefault()
            kotlin.jvm.internal.k.d(r3, r0)
        L_0x000d:
            r7 = r6 & 4
            if (r7 == 0) goto L_0x0018
            java.util.TimeZone r4 = java.util.TimeZone.getDefault()
            kotlin.jvm.internal.k.d(r4, r0)
        L_0x0018:
            r6 = r6 & 8
            if (r6 == 0) goto L_0x0022
            boolean r5 = android.text.format.DateFormat.is24HourFormat(r2)
        L_0x0022:
            r1.<init>(r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.r.<init>(android.content.Context, java.util.Locale, java.util.TimeZone, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final String b(@NotNull Date timestamp) {
        k.e(timestamp, "timestamp");
        String format = this.a.format(timestamp);
        k.d(format, "timeOnlyFormat.format(timestamp)");
        return format;
    }

    @NotNull
    public final String a(@NotNull Date timestamp) {
        k.e(timestamp, "timestamp");
        String format = this.b.format(timestamp);
        k.d(format, "dayAndTimeFormat.format(timestamp)");
        return format;
    }
}
