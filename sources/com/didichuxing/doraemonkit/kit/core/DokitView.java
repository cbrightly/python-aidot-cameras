package com.didichuxing.doraemonkit.kit.core;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public interface DokitView {
    boolean canDrag();

    void initDokitViewLayoutParams(DokitViewLayoutParams dokitViewLayoutParams);

    boolean onBackPressed();

    void onCreate(Context context);

    View onCreateView(Context context, FrameLayout frameLayout);

    void onDestroy();

    void onEnterBackground();

    void onEnterForeground();

    void onPause();

    void onResume();

    void onViewCreated(FrameLayout frameLayout);

    boolean shouldDealBackKey();
}
