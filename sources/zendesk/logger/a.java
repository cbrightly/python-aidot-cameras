package zendesk.logger;

import android.os.Build;
import android.util.Log;
import com.amazonaws.util.DateUtils;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import com.meituan.robust.Constants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: Logger */
public class a {
    /* access modifiers changed from: private */
    public static final TimeZone a = TimeZone.getTimeZone("UTC");
    private static final List<c> b = new ArrayList();
    private static boolean c = false;
    private static c d;

    /* compiled from: Logger */
    public interface c {
        void a(d dVar, String str, String str2, Throwable th);
    }

    static {
        d = new b();
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                d = new C0519a();
            }
        } catch (ClassNotFoundException e) {
        }
    }

    /* compiled from: Logger */
    public enum d {
        VERBOSE(2),
        DEBUG(3),
        INFO(4),
        WARN(5),
        ERROR(6);
        
        /* access modifiers changed from: private */
        public final int priority;

        private d(int priority2) {
            this.priority = priority2;
        }
    }

    public static void g(boolean loggable) {
        c = loggable;
    }

    public static void h(String tag, String message, Object... args) {
        f(d.WARN, tag, message, (Throwable) null, args);
    }

    public static void d(String tag, String message, Object... args) {
        f(d.ERROR, tag, message, (Throwable) null, args);
    }

    public static void c(String tag, String message, Throwable throwable, Object... args) {
        f(d.ERROR, tag, message, throwable, args);
    }

    public static void e(String tag, String message, Object... args) {
        f(d.INFO, tag, message, (Throwable) null, args);
    }

    public static void b(String tag, String message, Object... args) {
        f(d.DEBUG, tag, message, (Throwable) null, args);
    }

    private static void f(d priority, String tag, String message, Throwable throwable, Object... args) {
        String formattedMessage;
        if (c) {
            if (message == null) {
                message = "";
            }
            if (args == null || args.length <= 0) {
                formattedMessage = message;
            } else {
                formattedMessage = String.format(Locale.US, message, args);
            }
            d.a(priority, tag, formattedMessage, throwable);
            for (c logReceiver : b) {
                logReceiver.a(priority, tag, formattedMessage, throwable);
            }
        }
    }

    /* compiled from: Logger */
    public static class b implements c {
        b() {
        }

        public void a(d priority, String tag, String message, Throwable throwable) {
            char c;
            StringBuilder logBuilder = new StringBuilder(100);
            logBuilder.append(Constants.ARRAY_TYPE);
            logBuilder.append(new SimpleDateFormat(DateUtils.ALTERNATE_ISO8601_DATE_PATTERN, Locale.US).format(new Date()));
            logBuilder.append("]");
            logBuilder.append(" ");
            if (priority == null) {
                c = b.b(d.INFO.priority);
            } else {
                c = b.b(priority.priority);
            }
            logBuilder.append(c);
            logBuilder.append("/");
            logBuilder.append(c.a(tag) ? tag : LDNetUtil.NETWORKTYPE_INVALID);
            logBuilder.append(": ");
            logBuilder.append(message);
            System.out.println(logBuilder.toString());
            if (throwable != null) {
                throwable.printStackTrace(System.out);
            }
        }
    }

    /* renamed from: zendesk.logger.a$a  reason: collision with other inner class name */
    /* compiled from: Logger */
    public static class C0519a implements c {
        C0519a() {
        }

        public void a(d priority, String tag, String message, Throwable throwable) {
            String androidTag = b.a(tag);
            StringBuilder messageBuilder = new StringBuilder(message.length());
            if (d.ERROR == priority) {
                SimpleDateFormat utcFormat = new SimpleDateFormat(DateUtils.ALTERNATE_ISO8601_DATE_PATTERN, Locale.US);
                utcFormat.setTimeZone(a.a);
                messageBuilder.append("[UTC ");
                messageBuilder.append(utcFormat.format(new Date()));
                messageBuilder.append("] ");
            }
            messageBuilder.append(message);
            if (throwable != null) {
                messageBuilder.append(c.a);
                messageBuilder.append(Log.getStackTraceString(throwable));
            }
            for (String line : b.c(messageBuilder.toString(), WearableStatusCodes.TARGET_NODE_NOT_CONNECTED)) {
                Log.println(priority == null ? d.INFO.priority : priority.priority, androidTag, line);
            }
        }
    }
}
