package com.leedarson.newui;

import android.view.View;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.Calendar;

/* compiled from: OnMultiClickListener */
public abstract class d6 implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long c = 0;

    public abstract void a(View view);

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2865, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - this.c > 1000) {
            this.c = currentTime;
            a(v);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
