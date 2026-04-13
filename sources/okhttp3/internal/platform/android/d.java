package okhttp3.internal.platform.android;

import com.didichuxing.doraemonkit.kit.network.ui.NetworkListView;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AndroidLog.kt */
public final class d extends Handler {
    public static final d a = new d();

    private d() {
    }

    public void publish(@NotNull LogRecord record) {
        k.f(record, NetworkListView.KEY_RECORD);
        c cVar = c.c;
        String loggerName = record.getLoggerName();
        k.b(loggerName, "record.loggerName");
        int a2 = e.b(record);
        String message = record.getMessage();
        k.b(message, "record.message");
        cVar.a(loggerName, a2, message, record.getThrown());
    }

    public void flush() {
    }

    public void close() {
    }
}
