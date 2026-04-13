package com.sensorsdata.analytics.android.sdk.listener;

import android.view.View;
import java.lang.ref.WeakReference;

public interface SAJSListener {
    void onReceiveJSMessage(WeakReference<View> weakReference, String str);
}
