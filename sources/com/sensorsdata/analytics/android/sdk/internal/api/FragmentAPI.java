package com.sensorsdata.analytics.android.sdk.internal.api;

import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreen;
import com.sensorsdata.analytics.android.sdk.SensorsDataIgnoreTrackAppViewScreenAndAppClick;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class FragmentAPI implements IFragmentAPI {
    private static final String TAG = "FragmentAPI";
    private Set<Integer> mAutoTrackFragments;
    private Set<Integer> mAutoTrackIgnoredFragments;
    private boolean mTrackFragmentAppViewScreen;

    public void trackFragmentAppViewScreen() {
        this.mTrackFragmentAppViewScreen = true;
    }

    public boolean isTrackFragmentAppViewScreenEnabled() {
        return this.mTrackFragmentAppViewScreen;
    }

    public void enableAutoTrackFragment(Class<?> fragment) {
        if (fragment != null) {
            try {
                if (this.mAutoTrackFragments == null) {
                    this.mAutoTrackFragments = new CopyOnWriteArraySet();
                }
                String canonicalName = fragment.getCanonicalName();
                if (!TextUtils.isEmpty(canonicalName)) {
                    this.mAutoTrackFragments.add(Integer.valueOf(canonicalName.hashCode()));
                }
            } catch (Exception ex) {
                SALog.printStackTrace(ex);
            }
        }
    }

    public void enableAutoTrackFragments(List<Class<?>> fragmentsList) {
        if (fragmentsList != null && fragmentsList.size() != 0) {
            if (this.mAutoTrackFragments == null) {
                this.mAutoTrackFragments = new CopyOnWriteArraySet();
            }
            try {
                for (Class<?> fragment : fragmentsList) {
                    String canonicalName = fragment.getCanonicalName();
                    if (!TextUtils.isEmpty(canonicalName)) {
                        this.mAutoTrackFragments.add(Integer.valueOf(canonicalName.hashCode()));
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public boolean isFragmentAutoTrackAppViewScreen(Class<?> fragment) {
        if (fragment == null) {
            return false;
        }
        try {
            if (!SensorsDataAPI.sharedInstance().isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN)) {
                if (this.mTrackFragmentAppViewScreen) {
                    Set<Integer> set = this.mAutoTrackFragments;
                    if (set != null && set.size() > 0) {
                        String canonicalName = fragment.getCanonicalName();
                        if (!TextUtils.isEmpty(canonicalName)) {
                            return this.mAutoTrackFragments.contains(Integer.valueOf(canonicalName.hashCode()));
                        }
                    }
                    if (fragment.getAnnotation(SensorsDataIgnoreTrackAppViewScreen.class) != null || fragment.getAnnotation(SensorsDataIgnoreTrackAppViewScreenAndAppClick.class) != null) {
                        return false;
                    }
                    Set<Integer> set2 = this.mAutoTrackIgnoredFragments;
                    if (set2 != null && set2.size() > 0) {
                        String canonicalName2 = fragment.getCanonicalName();
                        if (!TextUtils.isEmpty(canonicalName2)) {
                            return true ^ this.mAutoTrackIgnoredFragments.contains(Integer.valueOf(canonicalName2.hashCode()));
                        }
                    }
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void ignoreAutoTrackFragments(List<Class<?>> fragmentList) {
        if (fragmentList != null) {
            try {
                if (fragmentList.size() != 0) {
                    if (this.mAutoTrackIgnoredFragments == null) {
                        this.mAutoTrackIgnoredFragments = new CopyOnWriteArraySet();
                    }
                    for (Class<?> fragment : fragmentList) {
                        if (fragment != null) {
                            String canonicalName = fragment.getCanonicalName();
                            if (!TextUtils.isEmpty(canonicalName)) {
                                this.mAutoTrackIgnoredFragments.add(Integer.valueOf(canonicalName.hashCode()));
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                SALog.printStackTrace(ex);
            }
        }
    }

    public void ignoreAutoTrackFragment(Class<?> fragment) {
        if (fragment != null) {
            try {
                if (this.mAutoTrackIgnoredFragments == null) {
                    this.mAutoTrackIgnoredFragments = new CopyOnWriteArraySet();
                }
                String canonicalName = fragment.getCanonicalName();
                if (!TextUtils.isEmpty(canonicalName)) {
                    this.mAutoTrackIgnoredFragments.add(Integer.valueOf(canonicalName.hashCode()));
                }
            } catch (Exception ex) {
                SALog.printStackTrace(ex);
            }
        }
    }

    public void resumeIgnoredAutoTrackFragments(List<Class<?>> fragmentList) {
        if (fragmentList != null) {
            try {
                if (fragmentList.size() == 0) {
                    return;
                }
                if (this.mAutoTrackIgnoredFragments != null) {
                    for (Class fragment : fragmentList) {
                        if (fragment != null) {
                            String canonicalName = fragment.getCanonicalName();
                            if (!TextUtils.isEmpty(canonicalName)) {
                                this.mAutoTrackIgnoredFragments.remove(Integer.valueOf(canonicalName.hashCode()));
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                SALog.printStackTrace(ex);
            }
        }
    }

    public void resumeIgnoredAutoTrackFragment(Class<?> fragment) {
        if (fragment != null) {
            try {
                if (this.mAutoTrackIgnoredFragments != null) {
                    String canonicalName = fragment.getCanonicalName();
                    if (!TextUtils.isEmpty(canonicalName)) {
                        this.mAutoTrackIgnoredFragments.remove(Integer.valueOf(canonicalName.hashCode()));
                    }
                }
            } catch (Exception ex) {
                SALog.printStackTrace(ex);
            }
        }
    }
}
