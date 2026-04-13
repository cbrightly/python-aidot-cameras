package androidx.tracing;

import android.os.Trace;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(29)
public final class TraceApi29Impl {
    private TraceApi29Impl() {
    }

    public static void beginAsyncSection(@NonNull String methodName, int cookie) {
        Trace.beginAsyncSection(methodName, cookie);
    }

    public static void endAsyncSection(@NonNull String methodName, int cookie) {
        Trace.endAsyncSection(methodName, cookie);
    }

    public static void setCounter(@NonNull String counterName, int counterValue) {
        Trace.setCounter(counterName, (long) counterValue);
    }
}
