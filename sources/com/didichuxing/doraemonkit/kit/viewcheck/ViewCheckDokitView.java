package com.didichuxing.doraemonkit.kit.viewcheck;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.util.LifecycleListenerUtil;
import com.didichuxing.doraemonkit.util.UIUtils;
import java.util.ArrayList;
import java.util.List;

public class ViewCheckDokitView extends AbsDokitView implements LifecycleListenerUtil.LifecycleListener {
    private static final String TAG = "ViewCheckFloatPage";
    private FindCheckViewRunnable mFindCheckViewRunnable;
    /* access modifiers changed from: private */
    public Activity mResumedActivity;
    private Handler mTraverHandler;
    private HandlerThread mTraverHandlerThread;
    private List<OnViewSelectListener> mViewSelectListeners = new ArrayList();

    public interface OnViewSelectListener {
        void onViewSelected(@Nullable View view, @NonNull List<View> list);
    }

    public void onCreate(Context context) {
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mTraverHandlerThread = handlerThread;
        handlerThread.start();
        this.mTraverHandler = new Handler(this.mTraverHandlerThread.getLooper());
        this.mFindCheckViewRunnable = new FindCheckViewRunnable();
        this.mResumedActivity = a.b();
        LifecycleListenerUtil.registerListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mTraverHandler.removeCallbacks(this.mFindCheckViewRunnable);
        this.mTraverHandlerThread.quit();
        LifecycleListenerUtil.unRegisterListener(this);
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_view_check, (ViewGroup) null);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.x = UIUtils.getWidthPixels() / 2;
        params.y = UIUtils.getHeightPixels() / 2;
        int i = DokitViewLayoutParams.WRAP_CONTENT;
        params.height = i;
        params.width = i;
    }

    public void onUp(int x, int y) {
        super.onUp(x, y);
        preformFindCheckView();
    }

    public void onActivityResumed(Activity activity) {
        this.mResumedActivity = activity;
        preformFindCheckView();
    }

    public void onViewCreated(FrameLayout view) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onFragmentAttached(Fragment f) {
    }

    public void onFragmentDetached(Fragment f) {
    }

    public void onDown(int x, int y) {
    }

    /* access modifiers changed from: package-private */
    public void setViewSelectListener(OnViewSelectListener viewSelectListener) {
        this.mViewSelectListeners.add(viewSelectListener);
        preformFindCheckView();
    }

    /* access modifiers changed from: package-private */
    public void removeViewSelectListener(OnViewSelectListener viewSelectListener) {
        this.mViewSelectListeners.remove(viewSelectListener);
    }

    /* access modifiers changed from: package-private */
    public void preformPreCheckView() {
        FindCheckViewRunnable.access$010(this.mFindCheckViewRunnable);
        if (this.mFindCheckViewRunnable.mIndex < 0) {
            FindCheckViewRunnable findCheckViewRunnable = this.mFindCheckViewRunnable;
            int unused = findCheckViewRunnable.mIndex = findCheckViewRunnable.mIndex + this.mFindCheckViewRunnable.mCheckViewList.size();
        }
        this.mFindCheckViewRunnable.dispatchOnViewSelected();
    }

    /* access modifiers changed from: package-private */
    public void preformNextCheckView() {
        FindCheckViewRunnable.access$008(this.mFindCheckViewRunnable);
        if (this.mFindCheckViewRunnable.mIndex >= this.mFindCheckViewRunnable.mCheckViewList.size()) {
            FindCheckViewRunnable findCheckViewRunnable = this.mFindCheckViewRunnable;
            int unused = findCheckViewRunnable.mIndex = findCheckViewRunnable.mIndex - this.mFindCheckViewRunnable.mCheckViewList.size();
        }
        this.mFindCheckViewRunnable.dispatchOnViewSelected();
    }

    private void preformFindCheckView() {
        int y;
        int x;
        if (isNormalMode()) {
            x = getNormalLayoutParams().leftMargin + (getRootView().getWidth() / 2);
            y = getNormalLayoutParams().topMargin + (getRootView().getHeight() / 2);
        } else {
            x = getSystemLayoutParams().x + (getRootView().getWidth() / 2);
            y = getSystemLayoutParams().y + (getRootView().getHeight() / 2);
        }
        this.mTraverHandler.removeCallbacks(this.mFindCheckViewRunnable);
        int unused = this.mFindCheckViewRunnable.mX = x;
        int unused2 = this.mFindCheckViewRunnable.mY = y;
        this.mTraverHandler.post(this.mFindCheckViewRunnable);
    }

    /* access modifiers changed from: private */
    public void traverseViews(List<View> viewList, View view, int x, int y) {
        if (view != null) {
            int[] location = new int[2];
            view.getLocationInWindow(location);
            int left = location[0];
            int top = location[1];
            int right = view.getWidth() + left;
            int bottom = view.getHeight() + top;
            if (view instanceof ViewGroup) {
                int childCount = ((ViewGroup) view).getChildCount();
                if (childCount != 0) {
                    for (int index = childCount - 1; index >= 0; index--) {
                        traverseViews(viewList, ((ViewGroup) view).getChildAt(index), x, y);
                    }
                }
                if (left < x && x < right && top < y && y < bottom) {
                    viewList.add(view);
                }
            } else if (left < x && x < right && top < y && y < bottom) {
                viewList.add(view);
            }
        }
    }

    /* access modifiers changed from: private */
    public void onViewSelected(View current, List<View> checkViewList) {
        for (OnViewSelectListener listener : this.mViewSelectListeners) {
            listener.onViewSelected(current, checkViewList);
        }
    }

    public class FindCheckViewRunnable implements Runnable {
        /* access modifiers changed from: private */
        public List<View> mCheckViewList;
        /* access modifiers changed from: private */
        public int mIndex = 0;
        /* access modifiers changed from: private */
        public int mX = 0;
        /* access modifiers changed from: private */
        public int mY = 0;

        FindCheckViewRunnable() {
        }

        static /* synthetic */ int access$008(FindCheckViewRunnable x0) {
            int i = x0.mIndex;
            x0.mIndex = i + 1;
            return i;
        }

        static /* synthetic */ int access$010(FindCheckViewRunnable x0) {
            int i = x0.mIndex;
            x0.mIndex = i - 1;
            return i;
        }

        public void run() {
            List<View> viewList = new ArrayList<>(20);
            if (!(ViewCheckDokitView.this.mResumedActivity == null || ViewCheckDokitView.this.mResumedActivity.getWindow() == null)) {
                if (ViewCheckDokitView.this.isNormalMode()) {
                    ViewCheckDokitView viewCheckDokitView = ViewCheckDokitView.this;
                    viewCheckDokitView.traverseViews(viewList, UIUtils.getDokitAppContentView(viewCheckDokitView.mResumedActivity), this.mX, this.mY);
                } else {
                    ViewCheckDokitView viewCheckDokitView2 = ViewCheckDokitView.this;
                    viewCheckDokitView2.traverseViews(viewList, viewCheckDokitView2.mResumedActivity.getWindow().getDecorView(), this.mX, this.mY);
                }
            }
            this.mIndex = 0;
            this.mCheckViewList = viewList;
            dispatchOnViewSelected();
        }

        /* access modifiers changed from: private */
        public void dispatchOnViewSelected() {
            ViewCheckDokitView.this.post(new Runnable() {
                public void run() {
                    FindCheckViewRunnable findCheckViewRunnable = FindCheckViewRunnable.this;
                    ViewCheckDokitView.this.onViewSelected(findCheckViewRunnable.getCurrentCheckView(), FindCheckViewRunnable.this.mCheckViewList);
                }
            });
        }

        /* access modifiers changed from: private */
        public View getCurrentCheckView() {
            if (this.mCheckViewList.size() == 0) {
                return null;
            }
            return this.mCheckViewList.get(this.mIndex);
        }
    }
}
