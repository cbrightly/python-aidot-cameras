package com.sensorsdata.analytics.android.sdk.visual.snap;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EditState extends UIThreadSet<Activity> {
    private static final String TAG = "SA.EditState";
    private final Map<Activity, Set<EditBinding>> mCurrentEdits = new HashMap();
    private final Map<String, List<ViewVisitor>> mIntendedEdits = new HashMap();
    private final Handler mUiThreadHandler = new Handler(Looper.getMainLooper());

    public void add(Activity newOne) {
        super.add(newOne);
        applyEditsOnActivity(newOne);
    }

    public void remove(Activity oldOne) {
        super.remove(oldOne);
        removeChangesOnActivity(oldOne);
    }

    private void applyEditsOnActivity(Activity activity) {
        List<ViewVisitor> specificChanges;
        List<ViewVisitor> wildcardChanges;
        String activityName = activity.getClass().getCanonicalName();
        View rootView = null;
        Window window = activity.getWindow();
        if (window != null && window.isActive()) {
            rootView = window.getDecorView().getRootView();
        }
        if (rootView != null) {
            synchronized (this.mIntendedEdits) {
                specificChanges = this.mIntendedEdits.get(activityName);
                wildcardChanges = this.mIntendedEdits.get((Object) null);
            }
            if (specificChanges != null) {
                applyChangesFromList(activity, rootView, specificChanges);
            }
            if (wildcardChanges != null) {
                applyChangesFromList(activity, rootView, wildcardChanges);
            }
        }
    }

    private void applyChangesFromList(Activity activity, View rootView, List<ViewVisitor> changes) {
        synchronized (this.mCurrentEdits) {
            if (!this.mCurrentEdits.containsKey(activity)) {
                this.mCurrentEdits.put(activity, new HashSet());
            }
            int size = changes.size();
            for (int i = 0; i < size; i++) {
                this.mCurrentEdits.get(activity).add(new EditBinding(rootView, changes.get(i), this.mUiThreadHandler));
            }
        }
    }

    private void removeChangesOnActivity(Activity activity) {
        synchronized (this.mCurrentEdits) {
            Set<EditBinding> bindingSet = this.mCurrentEdits.get(activity);
            if (bindingSet != null) {
                for (EditBinding binding : bindingSet) {
                    binding.kill();
                }
                this.mCurrentEdits.remove(activity);
            }
        }
    }

    public static class EditBinding implements ViewTreeObserver.OnGlobalLayoutListener, Runnable {
        private boolean mAlive = true;
        private volatile boolean mDying = false;
        private final ViewVisitor mEdit;
        private final Handler mHandler;
        private final WeakReference<View> mViewRoot;

        public EditBinding(View viewRoot, ViewVisitor edit, Handler uiThreadHandler) {
            this.mEdit = edit;
            this.mViewRoot = new WeakReference<>(viewRoot);
            this.mHandler = uiThreadHandler;
            ViewTreeObserver observer = viewRoot.getViewTreeObserver();
            if (observer.isAlive()) {
                observer.addOnGlobalLayoutListener(this);
            }
            run();
        }

        public void onGlobalLayout() {
            run();
        }

        public void run() {
            if (this.mAlive) {
                View viewRoot = (View) this.mViewRoot.get();
                if (viewRoot == null || this.mDying) {
                    cleanUp();
                    return;
                }
                this.mEdit.visit(viewRoot);
                this.mHandler.removeCallbacks(this);
                this.mHandler.postDelayed(this, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
            }
        }

        public void kill() {
            this.mDying = true;
            this.mHandler.post(this);
        }

        private void cleanUp() {
            if (this.mAlive) {
                View viewRoot = (View) this.mViewRoot.get();
                if (viewRoot != null) {
                    ViewTreeObserver observer = viewRoot.getViewTreeObserver();
                    if (observer.isAlive()) {
                        if (Build.VERSION.SDK_INT < 16) {
                            observer.removeGlobalOnLayoutListener(this);
                        } else {
                            observer.removeOnGlobalLayoutListener(this);
                        }
                    }
                }
                this.mEdit.cleanup();
            }
            this.mAlive = false;
        }
    }
}
