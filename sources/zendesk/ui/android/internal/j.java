package zendesk.ui.android.internal;

import android.os.SystemClock;
import android.view.View;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.Map;
import java.util.WeakHashMap;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ThrottledOnClickListener.kt */
public abstract class j implements View.OnClickListener {
    private final long c;
    @NotNull
    private final Map<View, Long> d = new WeakHashMap();

    public abstract void a(@Nullable View view);

    public j(long minimumIntervalMillis) {
        this.c = minimumIntervalMillis;
    }

    @SensorsDataInstrumented
    public void onClick(@NotNull View view) {
        View clickedView = view;
        k.e(clickedView, "clickedView");
        Long previousClickTimestamp = this.d.get(clickedView);
        long currentTimestamp = SystemClock.uptimeMillis();
        this.d.put(clickedView, Long.valueOf(currentTimestamp));
        if (previousClickTimestamp == null || Math.abs(currentTimestamp - previousClickTimestamp.longValue()) > this.c) {
            a(clickedView);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
