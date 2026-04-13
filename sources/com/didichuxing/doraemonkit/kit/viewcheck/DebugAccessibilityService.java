package com.didichuxing.doraemonkit.kit.viewcheck;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.didichuxing.doraemonkit.constant.BroadcastAction;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.util.LogHelper;

public class DebugAccessibilityService extends AccessibilityService {
    private static final String TAG = "DebugAccessibilityService";

    /* access modifiers changed from: protected */
    public void onServiceConnected() {
        super.onServiceConnected();
    }

    private boolean isActivityEvent(AccessibilityEvent event) {
        try {
            return getPackageManager().getActivityInfo(new ComponentName(event.getPackageName().toString(), event.getClassName().toString()), 0) != null ? true : true;
        } catch (PackageManager.NameNotFoundException e) {
            LogHelper.e(TAG, e.toString());
            return false;
        }
    }

    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo info;
        CharSequence pkgName = event.getPackageName();
        if (pkgName != null && pkgName.equals(getPackageName()) && event.getEventType() == 32 && isActivityEvent(event) && (info = event.getSource()) != null) {
            Intent intent = new Intent(BroadcastAction.ACTION_ACCESSIBILITY_UPDATE);
            intent.putExtra(BundleKey.ACCESSIBILITY_DATA, info);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }
    }

    public void onInterrupt() {
        LogHelper.d(TAG, "onInterrupt");
    }
}
