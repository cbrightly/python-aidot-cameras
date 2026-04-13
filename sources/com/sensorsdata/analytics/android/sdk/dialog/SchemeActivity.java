package com.sensorsdata.analytics.android.sdk.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPIEmptyImplementation;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;

@SensorsDataIgnoreTrackAppViewScreen
public class SchemeActivity extends Activity {
    private static final String TAG = "SA.SchemeActivity";
    public static boolean isPopWindow = false;
    private boolean isStartApp = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SALog.i(TAG, "onCreate");
        try {
            requestWindowFeature(1);
            if (Build.VERSION.SDK_INT >= 14) {
                setTheme(16974123);
            } else {
                setTheme(16973836);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        if (SensorsDataAPI.sharedInstance() instanceof SensorsDataAPIEmptyImplementation) {
            SensorsDataDialogUtils.startLaunchActivity(this);
        } else {
            SensorsDataUtils.handleSchemeUrl(this, getIntent());
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (SensorsDataAPI.sharedInstance() instanceof SensorsDataAPIEmptyImplementation) {
            SensorsDataDialogUtils.startLaunchActivity(this);
        } else {
            SensorsDataUtils.handleSchemeUrl(this, getIntent());
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        SALog.i(TAG, "onResume");
        if (this.isStartApp) {
            this.isStartApp = false;
            SensorsDataDialogUtils.startLaunchActivity(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        SALog.i(TAG, "onPause");
        if (isPopWindow) {
            isPopWindow = false;
            this.isStartApp = true;
        }
    }

    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
