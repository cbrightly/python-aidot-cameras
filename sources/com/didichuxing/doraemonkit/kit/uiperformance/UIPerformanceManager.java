package com.didichuxing.doraemonkit.kit.uiperformance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.model.ViewInfo;
import com.didichuxing.doraemonkit.util.LifecycleListenerUtil;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.util.UIUtils;
import java.util.ArrayList;
import java.util.List;

public class UIPerformanceManager implements LifecycleListenerUtil.LifecycleListener {
    private static final String TAG = "UIPerformanceManager";
    private List<PerformanceDataListener> mListeners;
    private Canvas mPerformanceCanvas;

    public interface PerformanceDataListener {
        void onRefresh(List<ViewInfo> list);
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static UIPerformanceManager INSTANCE = new UIPerformanceManager();

        private Holder() {
        }
    }

    public static UIPerformanceManager getInstance() {
        return Holder.INSTANCE;
    }

    private UIPerformanceManager() {
        this.mListeners = new ArrayList();
    }

    public void start(Context context) {
        this.mPerformanceCanvas = new Canvas(Bitmap.createBitmap(UIUtils.getWidthPixels(), UIUtils.getHeightPixels(), Bitmap.Config.ARGB_8888));
        LifecycleListenerUtil.registerListener(this);
    }

    public void stop() {
        this.mListeners.clear();
        this.mPerformanceCanvas = null;
        LifecycleListenerUtil.unRegisterListener(this);
    }

    public List<ViewInfo> getViewInfos(Activity activity) {
        if (activity == null) {
            LogHelper.d(TAG, "resume activity is null");
            return new ArrayList();
        } else if (activity.getWindow() != null) {
            return getViewInfos(UIUtils.getDokitAppContentView(activity));
        } else {
            LogHelper.d(TAG, "resume activity window is null");
            return new ArrayList();
        }
    }

    private List<ViewInfo> getViewInfos(View view) {
        List<ViewInfo> infos = new ArrayList<>();
        traverseViews(view, infos, 0);
        return infos;
    }

    private void traverseViews(View view, List<ViewInfo> infos, int layerNum) {
        if (view != null) {
            int layerNum2 = layerNum + 1;
            if (view instanceof ViewGroup) {
                int childCount = ((ViewGroup) view).getChildCount();
                if (childCount != 0) {
                    for (int index = childCount - 1; index >= 0; index--) {
                        traverseViews(((ViewGroup) view).getChildAt(index), infos, layerNum2);
                    }
                    return;
                }
                return;
            }
            ViewInfo viewInfo = new ViewInfo(view);
            try {
                if (view.getVisibility() == 0) {
                    long startTime = System.nanoTime();
                    Canvas canvas = this.mPerformanceCanvas;
                    if (canvas != null) {
                        view.draw(canvas);
                    }
                    viewInfo.drawTime = ((float) ((System.nanoTime() - startTime) / 10000)) / 100.0f;
                    viewInfo.layerNum = layerNum2;
                }
            } catch (Exception e) {
                viewInfo.drawTime = -1.0f;
                viewInfo.layerNum = -1;
            }
            infos.add(viewInfo);
        }
    }

    public void addListener(PerformanceDataListener listener) {
        this.mListeners.add(listener);
    }

    public void removeListener(PerformanceDataListener listener) {
        this.mListeners.remove(listener);
    }

    public void onActivityResumed(Activity activity) {
        for (PerformanceDataListener listener : this.mListeners) {
            listener.onRefresh(getViewInfos(activity));
        }
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onFragmentAttached(Fragment f) {
        for (PerformanceDataListener listener : this.mListeners) {
            listener.onRefresh(getViewInfos((Activity) f.getActivity()));
        }
    }

    public void onFragmentDetached(Fragment f) {
        for (PerformanceDataListener listener : this.mListeners) {
            listener.onRefresh(getViewInfos((Activity) f.getActivity()));
        }
    }

    public void initRefresh() {
        for (PerformanceDataListener listener : this.mListeners) {
            listener.onRefresh(getViewInfos(a.b()));
        }
    }
}
