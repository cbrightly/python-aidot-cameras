package okhttp3.internal.platform.android;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.jetbrains.annotations.NotNull;

/* compiled from: AndroidLog.kt */
public final class e {
    /* access modifiers changed from: private */
    public static final int b(@NotNull LogRecord $this$androidLevel) {
        if ($this$androidLevel.getLevel().intValue() > Level.INFO.intValue()) {
            return 5;
        }
        if ($this$androidLevel.getLevel().intValue() == Level.INFO.intValue()) {
            return 4;
        }
        return 3;
    }
}
