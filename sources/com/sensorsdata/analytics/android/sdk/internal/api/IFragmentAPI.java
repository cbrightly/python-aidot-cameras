package com.sensorsdata.analytics.android.sdk.internal.api;

import java.util.List;

public interface IFragmentAPI {
    void enableAutoTrackFragment(Class<?> cls);

    void enableAutoTrackFragments(List<Class<?>> list);

    void ignoreAutoTrackFragment(Class<?> cls);

    void ignoreAutoTrackFragments(List<Class<?>> list);

    boolean isFragmentAutoTrackAppViewScreen(Class<?> cls);

    boolean isTrackFragmentAppViewScreenEnabled();

    void resumeIgnoredAutoTrackFragment(Class<?> cls);

    void resumeIgnoredAutoTrackFragments(List<Class<?>> list);

    void trackFragmentAppViewScreen();
}
