package com.sensorsdata.analytics.android.sdk.autotrack.aop;

import android.os.Bundle;
import android.view.View;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.autotrack.SAFragmentLifecycleCallbacks;
import com.sensorsdata.analytics.android.sdk.util.SAFragmentUtils;
import java.util.HashSet;
import java.util.Set;

public class FragmentTrackHelper {
    private static final Set<SAFragmentLifecycleCallbacks> FRAGMENT_CALLBACKS = new HashSet();

    public static void onFragmentViewCreated(Object object, View rootView, Bundle bundle) {
        if (SAFragmentUtils.isFragment(object)) {
            for (SAFragmentLifecycleCallbacks fragmentCallbacks : FRAGMENT_CALLBACKS) {
                try {
                    fragmentCallbacks.onViewCreated(object, rootView, bundle);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        }
    }

    public static void trackFragmentResume(Object object) {
        if (SAFragmentUtils.isFragment(object)) {
            for (SAFragmentLifecycleCallbacks fragmentCallbacks : FRAGMENT_CALLBACKS) {
                try {
                    fragmentCallbacks.onResume(object);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        }
    }

    public static void trackFragmentPause(Object object) {
        if (SAFragmentUtils.isFragment(object)) {
            for (SAFragmentLifecycleCallbacks fragmentCallbacks : FRAGMENT_CALLBACKS) {
                try {
                    fragmentCallbacks.onPause(object);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        }
    }

    public static void trackFragmentSetUserVisibleHint(Object object, boolean isVisibleToUser) {
        if (SAFragmentUtils.isFragment(object)) {
            for (SAFragmentLifecycleCallbacks fragmentCallbacks : FRAGMENT_CALLBACKS) {
                try {
                    fragmentCallbacks.setUserVisibleHint(object, isVisibleToUser);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        }
    }

    public static void trackOnHiddenChanged(Object object, boolean hidden) {
        if (SAFragmentUtils.isFragment(object)) {
            for (SAFragmentLifecycleCallbacks fragmentCallbacks : FRAGMENT_CALLBACKS) {
                try {
                    fragmentCallbacks.onHiddenChanged(object, hidden);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        }
    }

    public static void addFragmentCallbacks(SAFragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        FRAGMENT_CALLBACKS.add(fragmentLifecycleCallbacks);
    }

    public static void removeFragmentCallbacks(SAFragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        FRAGMENT_CALLBACKS.remove(fragmentLifecycleCallbacks);
    }
}
