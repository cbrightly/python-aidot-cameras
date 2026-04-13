package com.didichuxing.doraemonkit.kit.core;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.e;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.health.CountDownDokitView;
import com.didichuxing.doraemonkit.kit.main.MainIconDokitView;
import com.didichuxing.doraemonkit.kit.performance.PerformanceDokitView;
import com.didichuxing.doraemonkit.model.ActivityLifecycleInfo;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.util.SystemUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NormalDokitViewManager implements DokitViewManagerInterface {
    private static final String TAG = "NormalDokitViewManager";
    private Map<Activity, Map<String, AbsDokitView>> mActivityDokitViews = new HashMap();
    private Context mContext;
    private Map<String, GlobalSingleDokitViewInfo> mGlobalSingleDokitViews = new HashMap();

    public void notifyBackground() {
        Map<Activity, Map<String, AbsDokitView>> map = this.mActivityDokitViews;
        if (map != null) {
            for (Map<String, AbsDokitView> dokitViewMap : map.values()) {
                for (AbsDokitView dokitView : dokitViewMap.values()) {
                    dokitView.onEnterBackground();
                }
            }
        }
    }

    public void notifyForeground() {
        Map<Activity, Map<String, AbsDokitView>> map = this.mActivityDokitViews;
        if (map != null) {
            for (Map<String, AbsDokitView> dokitViewMap : map.values()) {
                for (AbsDokitView dokitView : dokitViewMap.values()) {
                    dokitView.onEnterForeground();
                }
            }
        }
    }

    NormalDokitViewManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void resumeAndAttachDokitViews(Activity activity) {
        if (this.mActivityDokitViews != null) {
            if (SystemUtil.isOnlyFirstLaunchActivity(activity)) {
                onMainActivityCreate(activity);
                return;
            }
            ActivityLifecycleInfo activityLifecycleInfo = DokitConstant.ACTIVITY_LIFECYCLE_INFOS.get(activity.getClass().getCanonicalName());
            if (activityLifecycleInfo != null) {
                if (activityLifecycleInfo.getActivityLifeCycleCount() == 1) {
                    onActivityCreate(activity);
                } else if (activityLifecycleInfo.getActivityLifeCycleCount() > 1) {
                    onActivityResume(activity);
                }
            }
        }
    }

    public void onMainActivityCreate(Activity activity) {
        if (!(activity instanceof UniversalActivity)) {
            attachCountDownDokitView(activity);
            if (!DokitConstant.AWAYS_SHOW_MAIN_ICON) {
                DokitConstant.MAIN_ICON_HAS_SHOW = false;
                return;
            }
            DokitIntent dokitIntent = new DokitIntent(MainIconDokitView.class);
            dokitIntent.mode = 1;
            attach(dokitIntent);
            DokitConstant.MAIN_ICON_HAS_SHOW = true;
        }
    }

    public void onActivityCreate(Activity activity) {
        Class<MainIconDokitView> cls = MainIconDokitView.class;
        Map<String, GlobalSingleDokitViewInfo> map = this.mGlobalSingleDokitViews;
        if (map == null) {
            LogHelper.e(TAG, "resumeAndAttachDokitViews 方法执行异常");
            return;
        }
        for (GlobalSingleDokitViewInfo dokitViewInfo : map.values()) {
            if (!(activity instanceof UniversalActivity) || dokitViewInfo.getAbsDokitViewClass() == PerformanceDokitView.class) {
                if (DokitConstant.AWAYS_SHOW_MAIN_ICON || dokitViewInfo.getAbsDokitViewClass() != cls) {
                    if (dokitViewInfo.getAbsDokitViewClass() == cls) {
                        DokitConstant.MAIN_ICON_HAS_SHOW = true;
                    }
                    DokitIntent dokitIntent = new DokitIntent(dokitViewInfo.getAbsDokitViewClass());
                    dokitIntent.mode = 1;
                    dokitIntent.bundle = dokitViewInfo.getBundle();
                    attach(dokitIntent);
                } else {
                    DokitConstant.MAIN_ICON_HAS_SHOW = false;
                }
            }
        }
        if (DokitConstant.AWAYS_SHOW_MAIN_ICON && !DoraemonKit.isShow()) {
            DoraemonKit.show();
        }
        attachCountDownDokitView(activity);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: com.didichuxing.doraemonkit.kit.core.AbsDokitView} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResume(android.app.Activity r9) {
        /*
            r8 = this;
            java.lang.Class<com.didichuxing.doraemonkit.kit.main.MainIconDokitView> r0 = com.didichuxing.doraemonkit.kit.main.MainIconDokitView.class
            java.util.Map<android.app.Activity, java.util.Map<java.lang.String, com.didichuxing.doraemonkit.kit.core.AbsDokitView>> r1 = r8.mActivityDokitViews
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.Object r1 = r1.get(r9)
            java.util.Map r1 = (java.util.Map) r1
            if (r1 == 0) goto L_0x004f
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Collection r3 = r1.values()
            java.util.Iterator r3 = r3.iterator()
        L_0x001c:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x003b
            java.lang.Object r4 = r3.next()
            com.didichuxing.doraemonkit.kit.core.AbsDokitView r4 = (com.didichuxing.doraemonkit.kit.core.AbsDokitView) r4
            int r5 = r4.getMode()
            r6 = 2
            if (r5 != r6) goto L_0x003a
            java.lang.Class r5 = r4.getClass()
            java.lang.String r5 = r5.getSimpleName()
            r2.add(r5)
        L_0x003a:
            goto L_0x001c
        L_0x003b:
            java.util.Iterator r3 = r2.iterator()
        L_0x003f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x004f
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            r8.detach((java.lang.String) r4)
            goto L_0x003f
        L_0x004f:
            java.util.Map<java.lang.String, com.didichuxing.doraemonkit.kit.core.GlobalSingleDokitViewInfo> r2 = r8.mGlobalSingleDokitViews
            if (r2 == 0) goto L_0x00ea
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x00ea
            java.util.Map<java.lang.String, com.didichuxing.doraemonkit.kit.core.GlobalSingleDokitViewInfo> r2 = r8.mGlobalSingleDokitViews
            java.util.Collection r2 = r2.values()
            java.util.Iterator r2 = r2.iterator()
        L_0x0063:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x00da
            java.lang.Object r3 = r2.next()
            com.didichuxing.doraemonkit.kit.core.GlobalSingleDokitViewInfo r3 = (com.didichuxing.doraemonkit.kit.core.GlobalSingleDokitViewInfo) r3
            boolean r4 = r9 instanceof com.didichuxing.doraemonkit.kit.core.UniversalActivity
            if (r4 == 0) goto L_0x007c
            java.lang.Class r4 = r3.getAbsDokitViewClass()
            java.lang.Class<com.didichuxing.doraemonkit.kit.performance.PerformanceDokitView> r5 = com.didichuxing.doraemonkit.kit.performance.PerformanceDokitView.class
            if (r4 == r5) goto L_0x007c
            goto L_0x0063
        L_0x007c:
            boolean r4 = com.didichuxing.doraemonkit.constant.DokitConstant.AWAYS_SHOW_MAIN_ICON
            r5 = 0
            if (r4 != 0) goto L_0x008a
            java.lang.Class r4 = r3.getAbsDokitViewClass()
            if (r4 != r0) goto L_0x008a
            com.didichuxing.doraemonkit.constant.DokitConstant.MAIN_ICON_HAS_SHOW = r5
            goto L_0x0063
        L_0x008a:
            java.lang.Class r4 = r3.getAbsDokitViewClass()
            r6 = 1
            if (r4 != r0) goto L_0x0093
            com.didichuxing.doraemonkit.constant.DokitConstant.MAIN_ICON_HAS_SHOW = r6
        L_0x0093:
            r4 = 0
            if (r1 == 0) goto L_0x00a7
            boolean r7 = r1.isEmpty()
            if (r7 != 0) goto L_0x00a7
            java.lang.String r7 = r3.getTag()
            java.lang.Object r7 = r1.get(r7)
            r4 = r7
            com.didichuxing.doraemonkit.kit.core.AbsDokitView r4 = (com.didichuxing.doraemonkit.kit.core.AbsDokitView) r4
        L_0x00a7:
            if (r4 == 0) goto L_0x00c1
            android.view.View r7 = r4.getRootView()
            if (r7 == 0) goto L_0x00c1
            android.view.View r7 = r4.getRootView()
            r7.setVisibility(r5)
            java.lang.String r5 = r4.getTag()
            r4.updateViewLayout(r5, r6)
            r4.onResume()
            goto L_0x00d9
        L_0x00c1:
            com.didichuxing.doraemonkit.kit.core.DokitIntent r5 = new com.didichuxing.doraemonkit.kit.core.DokitIntent
            java.lang.Class r6 = r3.getAbsDokitViewClass()
            r5.<init>(r6)
            int r6 = r3.getMode()
            r5.mode = r6
            android.os.Bundle r6 = r3.getBundle()
            r5.bundle = r6
            r8.attach(r5)
        L_0x00d9:
            goto L_0x0063
        L_0x00da:
            java.util.Map<java.lang.String, com.didichuxing.doraemonkit.kit.core.GlobalSingleDokitViewInfo> r2 = r8.mGlobalSingleDokitViews
            java.lang.String r0 = r0.getSimpleName()
            boolean r0 = r2.containsKey(r0)
            if (r0 != 0) goto L_0x00ed
            r8.attachMainIconDokitView(r9)
            goto L_0x00ed
        L_0x00ea:
            r8.attachMainIconDokitView(r9)
        L_0x00ed:
            r8.attachCountDownDokitView(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.core.NormalDokitViewManager.onActivityResume(android.app.Activity):void");
    }

    private void attachMainIconDokitView(Activity activity) {
        if (DokitConstant.AWAYS_SHOW_MAIN_ICON && !(activity instanceof UniversalActivity)) {
            DokitIntent dokitIntent = new DokitIntent(MainIconDokitView.class);
            dokitIntent.mode = 1;
            attach(dokitIntent);
        }
    }

    public void onActivityPause(Activity activity) {
        for (AbsDokitView absDokitView : getDokitViews(activity).values()) {
            absDokitView.onPause();
        }
    }

    private void attachCountDownDokitView(Activity activity) {
        if (DokitConstant.APP_HEALTH_RUNNING && !(activity instanceof UniversalActivity)) {
            DokitIntent dokitIntent = new DokitIntent(CountDownDokitView.class);
            dokitIntent.mode = 2;
            attach(dokitIntent);
        }
    }

    public void attach(final DokitIntent dokitIntent) {
        Map<String, AbsDokitView> dokitViews;
        Map<String, GlobalSingleDokitViewInfo> map;
        try {
            if (dokitIntent.activity == null) {
                LogHelper.e(TAG, "activity = null");
                return;
            }
            Class<? extends AbsDokitView> cls = dokitIntent.targetClass;
            if (cls != null) {
                final AbsDokitView dokitView = (AbsDokitView) cls.newInstance();
                if (this.mActivityDokitViews.get(dokitIntent.activity) == null) {
                    dokitViews = new HashMap<>();
                    this.mActivityDokitViews.put(dokitIntent.activity, dokitViews);
                } else {
                    dokitViews = this.mActivityDokitViews.get(dokitIntent.activity);
                }
                if (dokitIntent.mode != 1 || dokitViews.get(dokitIntent.getTag()) == null) {
                    dokitViews.put(dokitView.getTag(), dokitView);
                    dokitView.setMode(dokitIntent.mode);
                    dokitView.setBundle(dokitIntent.bundle);
                    dokitView.setTag(dokitIntent.getTag());
                    dokitView.setActivity(dokitIntent.activity);
                    dokitView.performCreate(this.mContext);
                    if (dokitIntent.mode == 1 && (map = this.mGlobalSingleDokitViews) != null) {
                        map.put(dokitView.getTag(), createGlobalSingleDokitViewInfo(dokitView));
                    }
                    final FrameLayout mDecorView = (FrameLayout) dokitIntent.activity.getWindow().getDecorView();
                    if (dokitView.getNormalLayoutParams() != null && dokitView.getRootView() != null) {
                        getDokitRootContentView(dokitIntent.activity, mDecorView).addView(dokitView.getRootView(), dokitView.getNormalLayoutParams());
                        dokitView.postDelayed(new Runnable() {
                            public void run() {
                                dokitView.onResume();
                                dokitView.dealDecorRootView(NormalDokitViewManager.this.getDokitRootContentView(dokitIntent.activity, mDecorView));
                            }
                        }, 100);
                        return;
                    }
                    return;
                }
                dokitViews.get(dokitIntent.getTag()).updateViewLayout(dokitIntent.getTag(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public FrameLayout getDokitRootContentView(final Activity activity, FrameLayout decorView) {
        int i = R.id.dokit_contentview_id;
        FrameLayout dokitRootView = (FrameLayout) decorView.findViewById(i);
        if (dokitRootView != null) {
            return dokitRootView;
        }
        FrameLayout dokitRootView2 = new DokitFrameLayout(this.mContext);
        dokitRootView2.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Map<String, AbsDokitView> dokitViews;
                if (keyCode != 4 || (dokitViews = NormalDokitViewManager.this.getDokitViews(activity)) == null || dokitViews.size() == 0) {
                    return false;
                }
                for (AbsDokitView dokitView : dokitViews.values()) {
                    if (dokitView.shouldDealBackKey()) {
                        return dokitView.onBackPressed();
                    }
                }
                return false;
            }
        });
        dokitRootView2.setClipChildren(false);
        dokitRootView2.setFocusable(true);
        dokitRootView2.setFocusableInTouchMode(true);
        dokitRootView2.requestFocus();
        dokitRootView2.setId(i);
        FrameLayout.LayoutParams dokitParams = new FrameLayout.LayoutParams(-1, -1);
        try {
            if (e.f(activity)) {
                dokitParams.topMargin = e.c();
            }
            if (e.g() && e.d(activity)) {
                dokitParams.bottomMargin = e.a();
            }
        } catch (Exception e) {
        }
        dokitRootView2.setLayoutParams(dokitParams);
        decorView.addView(dokitRootView2);
        return dokitRootView2;
    }

    public void detach(AbsDokitView dokitView) {
        if (this.mActivityDokitViews != null) {
            detach(dokitView.getTag());
        }
    }

    public void detach(Activity activity, AbsDokitView dokitView) {
        detach(activity, dokitView.getTag());
    }

    public void detach(String tag) {
        Map<Activity, Map<String, AbsDokitView>> map = this.mActivityDokitViews;
        if (map != null) {
            for (Activity activityKey : map.keySet()) {
                Map<String, AbsDokitView> dokitViews = this.mActivityDokitViews.get(activityKey);
                AbsDokitView dokitView = dokitViews.get(tag);
                if (dokitView != null) {
                    if (dokitView.getRootView() != null) {
                        dokitView.getRootView().setVisibility(8);
                        getDokitRootContentView(dokitView.getActivity(), (FrameLayout) activityKey.getWindow().getDecorView()).removeView(dokitView.getRootView());
                    }
                    activityKey.getWindow().getDecorView().requestLayout();
                    dokitView.performDestroy();
                    dokitViews.remove(tag);
                }
            }
            Map<String, GlobalSingleDokitViewInfo> map2 = this.mGlobalSingleDokitViews;
            if (map2 != null && map2.containsKey(tag)) {
                this.mGlobalSingleDokitViews.remove(tag);
            }
        }
    }

    public void detach(Activity activity, String tag) {
        Map<String, AbsDokitView> dokitViews;
        AbsDokitView dokitView;
        if (activity != null && (dokitViews = this.mActivityDokitViews.get(activity)) != null && (dokitView = dokitViews.get(tag)) != null) {
            if (dokitView.getRootView() != null) {
                dokitView.getRootView().setVisibility(8);
                getDokitRootContentView(dokitView.getActivity(), (FrameLayout) activity.getWindow().getDecorView()).removeView(dokitView.getRootView());
            }
            activity.getWindow().getDecorView().requestLayout();
            dokitView.performDestroy();
            dokitViews.remove(tag);
            Map<String, GlobalSingleDokitViewInfo> map = this.mGlobalSingleDokitViews;
            if (map != null && map.containsKey(tag)) {
                this.mGlobalSingleDokitViews.remove(tag);
            }
        }
    }

    public void detach(Class<? extends AbsDokitView> dokitViewClass) {
        detach(dokitViewClass.getSimpleName());
    }

    public void detach(Activity activity, Class<? extends AbsDokitView> dokitViewClass) {
        detach(activity, dokitViewClass.getSimpleName());
    }

    public void detachAll() {
        Map<Activity, Map<String, AbsDokitView>> map = this.mActivityDokitViews;
        if (map != null) {
            for (Activity activityKey : map.keySet()) {
                getDokitRootContentView(activityKey, (FrameLayout) activityKey.getWindow().getDecorView()).removeAllViews();
                this.mActivityDokitViews.get(activityKey).clear();
            }
            Map<String, GlobalSingleDokitViewInfo> map2 = this.mGlobalSingleDokitViews;
            if (map2 != null) {
                map2.clear();
            }
        }
    }

    public void onActivityDestroy(Activity activity) {
        Map<String, AbsDokitView> dokitViewMap;
        if (this.mActivityDokitViews != null && (dokitViewMap = getDokitViews(activity)) != null) {
            for (AbsDokitView dokitView : dokitViewMap.values()) {
                dokitView.performDestroy();
            }
            this.mActivityDokitViews.remove(activity);
        }
    }

    public AbsDokitView getDokitView(Activity activity, String tag) {
        Map<Activity, Map<String, AbsDokitView>> map;
        if (TextUtils.isEmpty(tag) || activity == null || (map = this.mActivityDokitViews) == null || map.get(activity) == null) {
            return null;
        }
        return (AbsDokitView) this.mActivityDokitViews.get(activity).get(tag);
    }

    @NonNull
    public Map<String, AbsDokitView> getDokitViews(Activity activity) {
        if (activity == null) {
            return Collections.emptyMap();
        }
        Map<Activity, Map<String, AbsDokitView>> map = this.mActivityDokitViews;
        if (map == null) {
            return Collections.emptyMap();
        }
        return map.get(activity) == null ? Collections.emptyMap() : this.mActivityDokitViews.get(activity);
    }

    private GlobalSingleDokitViewInfo createGlobalSingleDokitViewInfo(AbsDokitView dokitView) {
        return new GlobalSingleDokitViewInfo(dokitView.getClass(), dokitView.getTag(), 1, dokitView.getBundle());
    }
}
