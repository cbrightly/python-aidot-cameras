package com.didichuxing.doraemonkit.util;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class LifecycleListenerUtil {
    public static List<LifecycleListener> LIFECYCLE_LISTENERS = new ArrayList();

    public interface LifecycleListener {
        void onActivityPaused(Activity activity);

        void onActivityResumed(Activity activity);

        void onFragmentAttached(Fragment fragment);

        void onFragmentDetached(Fragment fragment);
    }

    public static void registerListener(LifecycleListener listener) {
        LIFECYCLE_LISTENERS.add(listener);
    }

    public static void unRegisterListener(LifecycleListener listener) {
        LIFECYCLE_LISTENERS.remove(listener);
    }
}
