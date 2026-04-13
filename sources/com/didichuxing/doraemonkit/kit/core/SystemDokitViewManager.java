package com.didichuxing.doraemonkit.kit.core;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.WindowManager;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.health.CountDownDokitView;
import com.didichuxing.doraemonkit.kit.main.MainIconDokitView;
import com.didichuxing.doraemonkit.model.ActivityLifecycleInfo;
import com.didichuxing.doraemonkit.util.SystemUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SystemDokitViewManager implements DokitViewManagerInterface {
    private static final String TAG = "FloatPageManager";
    private Context mContext;
    private List<AbsDokitView> mDokitViews = new ArrayList();
    private List<DokitViewManager.DokitViewAttachedListener> mListeners = new ArrayList();
    private WindowManager mWindowManager = DokitViewManager.getInstance().getWindowManager();

    public Map<String, AbsDokitView> getDokitViews(Activity activity) {
        if (this.mDokitViews == null) {
            return new HashMap();
        }
        Map<String, AbsDokitView> dokitViewMaps = new HashMap<>();
        for (AbsDokitView dokitView : this.mDokitViews) {
            dokitViewMaps.put(dokitView.getTag(), dokitView);
        }
        return dokitViewMaps;
    }

    public void notifyBackground() {
        List<AbsDokitView> list = this.mDokitViews;
        if (list != null) {
            for (AbsDokitView dokitView : list) {
                dokitView.onEnterBackground();
            }
        }
    }

    public void notifyForeground() {
        List<AbsDokitView> list = this.mDokitViews;
        if (list != null) {
            for (AbsDokitView page : list) {
                page.onEnterForeground();
            }
        }
    }

    public SystemDokitViewManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void resumeAndAttachDokitViews(Activity activity) {
        Class<CountDownDokitView> cls = CountDownDokitView.class;
        if (!(activity instanceof UniversalActivity)) {
            if (SystemUtil.isOnlyFirstLaunchActivity(activity)) {
                onMainActivityCreate(activity);
            }
            ActivityLifecycleInfo activityLifecycleInfo = DokitConstant.ACTIVITY_LIFECYCLE_INFOS.get(activity.getClass().getCanonicalName());
            if (activityLifecycleInfo != null && activityLifecycleInfo.getActivityLifeCycleCount() == 1) {
                onActivityCreate(activity);
            }
            if (activityLifecycleInfo != null && activityLifecycleInfo.getActivityLifeCycleCount() > 1) {
                onActivityResume(activity);
            }
            for (AbsDokitView absDokitView : getDokitViews(activity).values()) {
                absDokitView.onResume();
            }
        } else if (getDokitView(activity, cls.getSimpleName()) != null) {
            DokitViewManager.getInstance().detach(cls.getSimpleName());
        }
    }

    private void attachCountDownDokitView(Activity activity) {
        if (DokitConstant.APP_HEALTH_RUNNING && !(activity instanceof UniversalActivity)) {
            DokitIntent dokitIntent = new DokitIntent(CountDownDokitView.class);
            dokitIntent.mode = 2;
            attach(dokitIntent);
        }
    }

    public void onMainActivityCreate(Activity activity) {
        attachCountDownDokitView(activity);
        if (DokitConstant.AWAYS_SHOW_MAIN_ICON) {
            DokitIntent intent = new DokitIntent(MainIconDokitView.class);
            intent.mode = 1;
            DokitViewManager.getInstance().attach(intent);
            DokitConstant.MAIN_ICON_HAS_SHOW = true;
        }
    }

    public void onActivityCreate(Activity activity) {
        Class<CountDownDokitView> cls = CountDownDokitView.class;
        if (DokitConstant.AWAYS_SHOW_MAIN_ICON && !DoraemonKit.isShow()) {
            DoraemonKit.show();
        }
        AbsDokitView countDownDokitView = getDokitView(activity, cls.getSimpleName());
        if (countDownDokitView == null) {
            if (!(activity instanceof UniversalActivity)) {
                attachCountDownDokitView(activity);
            }
        } else if (activity instanceof UniversalActivity) {
            DokitViewManager.getInstance().detach(cls.getSimpleName());
        } else {
            ((CountDownDokitView) countDownDokitView).resetTime();
        }
    }

    public void onActivityResume(Activity activity) {
        Class<MainIconDokitView> cls = MainIconDokitView.class;
        AbsDokitView countDownDokitView = getDokitView(activity, CountDownDokitView.class.getSimpleName());
        if (countDownDokitView == null) {
            attachCountDownDokitView(activity);
        } else {
            ((CountDownDokitView) countDownDokitView).resetTime();
        }
        Map<String, AbsDokitView> dokitViews = getDokitViews(activity);
        if ((dokitViews == null || dokitViews.get(cls.getSimpleName()) == null) && DokitConstant.AWAYS_SHOW_MAIN_ICON && !(activity instanceof UniversalActivity)) {
            DokitIntent intent = new DokitIntent(cls);
            intent.mode = 1;
            DokitViewManager.getInstance().attach(intent);
            DokitConstant.MAIN_ICON_HAS_SHOW = true;
        }
    }

    public void onActivityPause(Activity activity) {
        for (AbsDokitView absDokitView : getDokitViews(activity).values()) {
            absDokitView.onPause();
        }
    }

    public void attach(DokitIntent pageIntent) {
        try {
            List<AbsDokitView> list = this.mDokitViews;
            if (list != null && pageIntent.targetClass != null) {
                if (pageIntent.mode == 1) {
                    for (AbsDokitView dokitView : list) {
                        if (pageIntent.targetClass.isInstance(dokitView)) {
                            return;
                        }
                    }
                }
                AbsDokitView dokitView2 = (AbsDokitView) pageIntent.targetClass.newInstance();
                dokitView2.setBundle(pageIntent.bundle);
                this.mDokitViews.add(dokitView2);
                dokitView2.performCreate(this.mContext);
                this.mWindowManager.addView(dokitView2.getRootView(), dokitView2.getSystemLayoutParams());
                dokitView2.onResume();
                if (!DokitConstant.IS_NORMAL_FLOAT_MODE) {
                    for (DokitViewManager.DokitViewAttachedListener listener : this.mListeners) {
                        listener.onDokitViewAdd(dokitView2);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detach(String tag) {
        List<AbsDokitView> list;
        if (!TextUtils.isEmpty(tag) && this.mWindowManager != null && (list = this.mDokitViews) != null) {
            Iterator<AbsDokitView> it = list.iterator();
            while (it.hasNext()) {
                AbsDokitView dokitView = it.next();
                if (tag.equals(dokitView.getTag())) {
                    this.mWindowManager.removeView(dokitView.getRootView());
                    dokitView.performDestroy();
                    it.remove();
                    return;
                }
            }
        }
    }

    public void detach(Activity activity, String tag) {
    }

    public void detach(AbsDokitView dokitView) {
        detach(dokitView.getClass().getSimpleName());
    }

    public void detach(Activity activity, AbsDokitView dokitView) {
    }

    public void detach(Class<? extends AbsDokitView> dokitViewClass) {
        detach(dokitViewClass.getSimpleName());
    }

    public void detach(Activity activity, Class<? extends AbsDokitView> cls) {
    }

    public void detachAll() {
        List<AbsDokitView> list = this.mDokitViews;
        if (list != null) {
            Iterator<AbsDokitView> it = list.iterator();
            while (it.hasNext()) {
                AbsDokitView dokitView = it.next();
                this.mWindowManager.removeView(dokitView.getRootView());
                dokitView.performDestroy();
                it.remove();
            }
        }
    }

    public AbsDokitView getDokitView(Activity activity, String tag) {
        if (this.mDokitViews == null || TextUtils.isEmpty(tag)) {
            return null;
        }
        for (AbsDokitView dokitView : this.mDokitViews) {
            if (tag.equals(dokitView.getTag())) {
                return dokitView;
            }
        }
        return null;
    }

    public void onActivityDestroy(Activity activity) {
    }

    /* access modifiers changed from: package-private */
    public void addListener(DokitViewManager.DokitViewAttachedListener listener) {
        this.mListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void removeListener(DokitViewManager.DokitViewAttachedListener listener) {
        this.mListeners.remove(listener);
    }
}
