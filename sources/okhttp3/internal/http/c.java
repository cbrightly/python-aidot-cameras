package okhttp3.internal.http;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import kotlin.jvm.internal.k;
import kotlin.x;
import okhttp3.internal.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: dates.kt */
public final class c {
    private static final a a = new a();
    private static final String[] b;
    private static final DateFormat[] c;

    /* compiled from: dates.kt */
    public static final class a extends ThreadLocal<DateFormat> {
        a() {
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: a */
        public DateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            SimpleDateFormat $this$apply = simpleDateFormat;
            $this$apply.setLenient(false);
            $this$apply.setTimeZone(b.f);
            return simpleDateFormat;
        }
    }

    static {
        String[] strArr = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
        b = strArr;
        c = new DateFormat[strArr.length];
    }

    @Nullable
    public static final Date a(@NotNull String $this$toHttpDateOrNull) {
        k.f($this$toHttpDateOrNull, "$this$toHttpDateOrNull");
        if ($this$toHttpDateOrNull.length() == 0) {
            return null;
        }
        ParsePosition position = new ParsePosition(0);
        Date parse = ((DateFormat) a.get()).parse($this$toHttpDateOrNull, position);
        if (position.getIndex() == $this$toHttpDateOrNull.length()) {
            return parse;
        }
        String[] strArr = b;
        synchronized (strArr) {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                DateFormat[] dateFormatArr = c;
                DateFormat format = dateFormatArr[i];
                if (format == null) {
                    DateFormat $this$apply = new SimpleDateFormat(b[i], Locale.US);
                    $this$apply.setTimeZone(b.f);
                    format = $this$apply;
                    dateFormatArr[i] = format;
                }
                position.setIndex(0);
                Date parse2 = format.parse($this$toHttpDateOrNull, position);
                if (position.getIndex() != 0) {
                    return parse2;
                }
            }
            x xVar = x.a;
            return null;
        }
    }

    @NotNull
    public static final String b(@NotNull Date $this$toHttpDateString) {
        k.f($this$toHttpDateString, "$this$toHttpDateString");
        String format = ((DateFormat) a.get()).format($this$toHttpDateString);
        k.b(format, "STANDARD_DATE_FORMAT.get().format(this)");
        return format;
    }
}
