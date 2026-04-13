package com.didichuxing.doraemonkit.zxing.decoding;

import android.app.Activity;
import android.content.DialogInterface;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public final class FinishListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener, Runnable {
    private final Activity activityToFinish;

    public FinishListener(Activity activityToFinish2) {
        this.activityToFinish = activityToFinish2;
    }

    public void onCancel(DialogInterface dialogInterface) {
        run();
    }

    @SensorsDataInstrumented
    public void onClick(DialogInterface dialogInterface, int i) {
        int i2 = i;
        DialogInterface dialogInterface2 = dialogInterface;
        run();
        SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
    }

    public void run() {
        this.activityToFinish.finish();
    }
}
