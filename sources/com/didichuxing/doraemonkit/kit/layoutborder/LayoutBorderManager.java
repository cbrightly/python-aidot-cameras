package com.didichuxing.doraemonkit.kit.layoutborder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.fragment.app.Fragment;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.didichuxing.doraemonkit.util.LifecycleListenerUtil;

public class LayoutBorderManager {
    private boolean isRunning;
    private LifecycleListenerUtil.LifecycleListener mLifecycleListener;
    /* access modifiers changed from: private */
    public ViewBorderFrameLayout mViewBorderFrameLayout;

    /* access modifiers changed from: private */
    public void resolveActivity(Activity activity) {
        Window window;
        ViewGroup root;
        if (activity != null && !(activity instanceof UniversalActivity) && (window = activity.getWindow()) != null && (root = (ViewGroup) window.getDecorView()) != null) {
            this.mViewBorderFrameLayout = new ViewBorderFrameLayout(root.getContext());
            while (root.getChildCount() != 0) {
                View child = root.getChildAt(0);
                if (child instanceof ViewBorderFrameLayout) {
                    this.mViewBorderFrameLayout = (ViewBorderFrameLayout) child;
                    return;
                } else {
                    root.removeView(child);
                    this.mViewBorderFrameLayout.addView(child);
                }
            }
            root.addView(this.mViewBorderFrameLayout);
        }
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static LayoutBorderManager INSTANCE = new LayoutBorderManager();

        private Holder() {
        }
    }

    private LayoutBorderManager() {
        this.mLifecycleListener = new LifecycleListenerUtil.LifecycleListener() {
            public void onActivityResumed(Activity activity) {
                LayoutBorderManager.this.resolveActivity(activity);
            }

            public void onActivityPaused(Activity activity) {
            }

            public void onFragmentAttached(Fragment f) {
            }

            public void onFragmentDetached(Fragment f) {
                if (LayoutBorderManager.this.mViewBorderFrameLayout != null) {
                    ViewBorderFrameLayout unused = LayoutBorderManager.this.mViewBorderFrameLayout = null;
                }
            }
        };
    }

    public static LayoutBorderManager getInstance() {
        return Holder.INSTANCE;
    }

    public void start() {
        this.isRunning = true;
        resolveActivity(a.b());
        LifecycleListenerUtil.registerListener(this.mLifecycleListener);
    }

    public void stop() {
        this.isRunning = false;
        ViewBorderFrameLayout viewBorderFrameLayout = this.mViewBorderFrameLayout;
        if (viewBorderFrameLayout != null) {
            viewBorderFrameLayout.requestLayout();
        }
        this.mViewBorderFrameLayout = null;
        LifecycleListenerUtil.unRegisterListener(this.mLifecycleListener);
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}
