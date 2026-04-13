package com.didichuxing.doraemonkit;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.didichuxing.doraemonkit.util.LifecycleListenerUtil;

public class DokitFragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {
    private static final String TAG = "DokitFragmentLifecycleCallbacks";

    public void onFragmentAttached(FragmentManager fm, Fragment fragment, Context context) {
        super.onFragmentAttached(fm, fragment, context);
        for (LifecycleListenerUtil.LifecycleListener listener : LifecycleListenerUtil.LIFECYCLE_LISTENERS) {
            listener.onFragmentAttached(fragment);
        }
    }

    public void onFragmentDetached(FragmentManager fm, Fragment fragment) {
        super.onFragmentDetached(fm, fragment);
        for (LifecycleListenerUtil.LifecycleListener listener : LifecycleListenerUtil.LIFECYCLE_LISTENERS) {
            listener.onFragmentDetached(fragment);
        }
    }
}
