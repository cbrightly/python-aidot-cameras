package com.sensorsdata.analytics.android.sdk.autotrack;

import android.os.Bundle;
import android.view.View;

public interface SAFragmentLifecycleCallbacks {
    void onCreate(Object obj);

    void onHiddenChanged(Object obj, boolean z);

    void onPause(Object obj);

    void onResume(Object obj);

    void onStart(Object obj);

    void onStop(Object obj);

    void onViewCreated(Object obj, View view, Bundle bundle);

    void setUserVisibleHint(Object obj, boolean z);
}
