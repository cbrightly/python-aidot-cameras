package okhttp3.internal.platform.android;

import android.util.Log;
import com.google.android.gms.wearable.WearableStatusCodes;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.collections.l0;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import okhttp3.internal.concurrent.e;
import okhttp3.internal.http2.d;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AndroidLog.kt */
public final class c {
    private static final CopyOnWriteArraySet<Logger> a = new CopyOnWriteArraySet<>();
    private static final Map<String, String> b;
    public static final c c = new c();

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap $this$apply = linkedHashMap;
        Package packageR = z.class.getPackage();
        String packageName = packageR != null ? packageR.getName() : null;
        if (packageName != null) {
            $this$apply.put(packageName, "OkHttp");
        }
        String name = z.class.getName();
        k.b(name, "OkHttpClient::class.java.name");
        $this$apply.put(name, "okhttp.OkHttpClient");
        String name2 = d.class.getName();
        k.b(name2, "Http2::class.java.name");
        $this$apply.put(name2, "okhttp.Http2");
        String name3 = e.class.getName();
        k.b(name3, "TaskRunner::class.java.name");
        $this$apply.put(name3, "okhttp.TaskRunner");
        b = l0.q(linkedHashMap);
    }

    private c() {
    }

    public final void a(@NotNull String loggerName, int logLevel, @NotNull String message, @Nullable Throwable t) {
        k.f(loggerName, "loggerName");
        k.f(message, "message");
        String tag = d(loggerName);
        if (Log.isLoggable(tag, logLevel)) {
            String logMessage = message;
            if (t != null) {
                logMessage = logMessage + "\n" + Log.getStackTraceString(t);
            }
            int length = logMessage.length();
            int i = 0;
            while (i < length) {
                int newline = x.e0(logMessage, 10, i, false, 4, (Object) null);
                int newline2 = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline2, i + WearableStatusCodes.TARGET_NODE_NOT_CONNECTED);
                    String substring = logMessage.substring(i, end);
                    k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    Log.println(logLevel, tag, substring);
                    i = end;
                } while (i < newline2);
                i++;
            }
        }
    }

    private final String d(String loggerName) {
        String str = b.get(loggerName);
        return str != null ? str : kotlin.text.z.j1(loggerName, 23);
    }

    public final void b() {
        for (Map.Entry next : b.entrySet()) {
            c((String) next.getKey(), (String) next.getValue());
        }
    }

    private final void c(String logger, String tag) {
        Level level;
        Logger logger2 = Logger.getLogger(logger);
        if (a.add(logger2)) {
            k.b(logger2, "logger");
            logger2.setUseParentHandlers(false);
            if (Log.isLoggable(tag, 3)) {
                level = Level.FINE;
            } else if (Log.isLoggable(tag, 4)) {
                level = Level.INFO;
            } else {
                level = Level.WARNING;
            }
            logger2.setLevel(level);
            logger2.addHandler(d.a);
        }
    }
}
