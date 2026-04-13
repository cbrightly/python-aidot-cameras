package androidx.work;

import androidx.annotation.RequiresApi;
import androidx.exifinterface.media.ExifInterface;
import androidx.work.PeriodicWorkRequest;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.k;

/* compiled from: PeriodicWorkRequest.kt */
public final class PeriodicWorkRequestKt {
    public static final /* synthetic */ <W extends ListenableWorker> PeriodicWorkRequest.Builder PeriodicWorkRequestBuilder(long repeatInterval, TimeUnit repeatIntervalTimeUnit) {
        k.e(repeatIntervalTimeUnit, "repeatIntervalTimeUnit");
        k.i(4, ExifInterface.LONGITUDE_WEST);
        return new PeriodicWorkRequest.Builder((Class<? extends ListenableWorker>) ListenableWorker.class, repeatInterval, repeatIntervalTimeUnit);
    }

    @RequiresApi(26)
    public static final /* synthetic */ <W extends ListenableWorker> PeriodicWorkRequest.Builder PeriodicWorkRequestBuilder(Duration repeatInterval) {
        k.e(repeatInterval, "repeatInterval");
        k.i(4, ExifInterface.LONGITUDE_WEST);
        return new PeriodicWorkRequest.Builder(ListenableWorker.class, repeatInterval);
    }

    public static final /* synthetic */ <W extends ListenableWorker> PeriodicWorkRequest.Builder PeriodicWorkRequestBuilder(long repeatInterval, TimeUnit repeatIntervalTimeUnit, long flexTimeInterval, TimeUnit flexTimeIntervalUnit) {
        k.e(repeatIntervalTimeUnit, "repeatIntervalTimeUnit");
        k.e(flexTimeIntervalUnit, "flexTimeIntervalUnit");
        k.i(4, ExifInterface.LONGITUDE_WEST);
        return new PeriodicWorkRequest.Builder(ListenableWorker.class, repeatInterval, repeatIntervalTimeUnit, flexTimeInterval, flexTimeIntervalUnit);
    }

    @RequiresApi(26)
    public static final /* synthetic */ <W extends ListenableWorker> PeriodicWorkRequest.Builder PeriodicWorkRequestBuilder(Duration repeatInterval, Duration flexTimeInterval) {
        k.e(repeatInterval, "repeatInterval");
        k.e(flexTimeInterval, "flexTimeInterval");
        k.i(4, ExifInterface.LONGITUDE_WEST);
        return new PeriodicWorkRequest.Builder((Class<? extends ListenableWorker>) ListenableWorker.class, repeatInterval, flexTimeInterval);
    }
}
