package okhttp3.internal.concurrent;

import java.util.Arrays;
import java.util.logging.Logger;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: TaskLogger.kt */
public final class b {
    /* access modifiers changed from: private */
    public static final void c(a task, d queue, String message) {
        Logger a = e.c.a();
        StringBuilder sb = new StringBuilder();
        sb.append(queue.f());
        sb.append(' ');
        d0 d0Var = d0.a;
        String format = String.format("%-22s", Arrays.copyOf(new Object[]{message}, 1));
        k.b(format, "java.lang.String.format(format, *args)");
        sb.append(format);
        sb.append(": ");
        sb.append(task.b());
        a.fine(sb.toString());
    }

    @NotNull
    public static final String b(long ns) {
        String s;
        if (ns <= ((long) -999500000)) {
            s = ((ns - ((long) 500000000)) / ((long) 1000000000)) + " s ";
        } else if (ns <= ((long) -999500)) {
            s = ((ns - ((long) 500000)) / ((long) 1000000)) + " ms";
        } else if (ns <= 0) {
            s = ((ns - ((long) 500)) / ((long) 1000)) + " µs";
        } else if (ns < ((long) 999500)) {
            s = ((((long) 500) + ns) / ((long) 1000)) + " µs";
        } else if (ns < ((long) 999500000)) {
            s = ((((long) 500000) + ns) / ((long) 1000000)) + " ms";
        } else {
            s = ((((long) 500000000) + ns) / ((long) 1000000000)) + " s ";
        }
        d0 d0Var = d0.a;
        String format = String.format("%6s", Arrays.copyOf(new Object[]{s}, 1));
        k.b(format, "java.lang.String.format(format, *args)");
        return format;
    }
}
