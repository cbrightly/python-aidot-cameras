package com.didichuxing.doraemonkit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.datapick.DataPickManager;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.health.AppHealthInfoUtil;
import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo;
import com.didichuxing.doraemonkit.kit.uiperformance.UIPerformanceUtil;
import com.didichuxing.doraemonkit.model.ActivityLifecycleInfo;
import com.didichuxing.doraemonkit.model.ViewInfo;
import com.didichuxing.doraemonkit.util.LifecycleListenerUtil;
import com.didichuxing.doraemonkit.util.PermissionUtil;
import com.didichuxing.doraemonkit.util.UIUtils;

public class DokitActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private static int LIFE_CYCLE_STATUS_CREATE = 100;
    private static int LIFE_CYCLE_STATUS_DESTROY = 103;
    private static int LIFE_CYCLE_STATUS_RESUME = 101;
    private static int LIFE_CYCLE_STATUS_STOPPED = 102;
    private FragmentManager.FragmentLifecycleCallbacks sFragmentLifecycleCallbacks = new DokitFragmentLifecycleCallbacks();
    private boolean sHasRequestPermission;
    private int startedActivityCounts;

    DokitActivityLifecycleCallbacks() {
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        recordActivityLifeCycleStatus(activity, LIFE_CYCLE_STATUS_CREATE);
        if (!ignoreCurrentActivityDokitView(activity) && (activity instanceof FragmentActivity)) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(this.sFragmentLifecycleCallbacks, true);
        }
    }

    public void onActivityStarted(Activity activity) {
        if (!ignoreCurrentActivityDokitView(activity)) {
            if (this.startedActivityCounts == 0) {
                DokitViewManager.getInstance().notifyForeground();
            }
            this.startedActivityCounts++;
        }
    }

    public void onActivityResumed(Activity activity) {
        recordActivityLifeCycleStatus(activity, LIFE_CYCLE_STATUS_RESUME);
        if (!activity.getClass().getCanonicalName().equals("com.didichuxing.doraemonkit.kit.base.UniversalActivity")) {
            recordActivityUiLevel(activity);
        }
        if (!ignoreCurrentActivityDokitView(activity)) {
            UIUtils.getDokitAppContentView(activity);
            resumeAndAttachDokitViews(activity);
            for (LifecycleListenerUtil.LifecycleListener listener : LifecycleListenerUtil.LIFECYCLE_LISTENERS) {
                listener.onActivityResumed(activity);
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        if (!ignoreCurrentActivityDokitView(activity)) {
            for (LifecycleListenerUtil.LifecycleListener listener : LifecycleListenerUtil.LIFECYCLE_LISTENERS) {
                listener.onActivityPaused(activity);
            }
            DokitViewManager.getInstance().onActivityPause(activity);
        }
    }

    public void onActivityStopped(Activity activity) {
        recordActivityLifeCycleStatus(activity, LIFE_CYCLE_STATUS_STOPPED);
        if (!ignoreCurrentActivityDokitView(activity)) {
            int i = this.startedActivityCounts - 1;
            this.startedActivityCounts = i;
            if (i == 0) {
                DokitViewManager.getInstance().notifyBackground();
                DataPickManager.getInstance().postData();
            }
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (!ignoreCurrentActivityDokitView(activity)) {
        }
    }

    public void onActivityDestroyed(Activity activity) {
        recordActivityLifeCycleStatus(activity, LIFE_CYCLE_STATUS_DESTROY);
        if (!ignoreCurrentActivityDokitView(activity)) {
            if (activity instanceof FragmentActivity) {
                ((FragmentActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(this.sFragmentLifecycleCallbacks);
            }
            DokitViewManager.getInstance().onActivityDestroy(activity);
        }
    }

    private static boolean ignoreCurrentActivityDokitView(Activity activity) {
        for (String activityClassName : new String[]{"DisplayLeakActivity"}) {
            if (activity.getClass().getSimpleName().equals(activityClassName)) {
                return true;
            }
        }
        return false;
    }

    private void resumeAndAttachDokitViews(Activity activity) {
        if (DokitConstant.IS_NORMAL_FLOAT_MODE) {
            DokitViewManager.getInstance().resumeAndAttachDokitViews(activity);
        } else if (PermissionUtil.canDrawOverlays(activity)) {
            DokitViewManager.getInstance().resumeAndAttachDokitViews(activity);
        } else {
            requestPermission(activity);
        }
    }

    private void requestPermission(Context context) {
        if (!PermissionUtil.canDrawOverlays(context) && !this.sHasRequestPermission) {
            Toast.makeText(context, context.getText(R.string.dk_float_permission_toast), 0).show();
            PermissionUtil.requestDrawOverlays(context);
            this.sHasRequestPermission = true;
        }
    }

    private void recordActivityUiLevel(Activity activity) {
        try {
            if (DokitConstant.APP_HEALTH_RUNNING) {
                int maxLevel = 0;
                float maxTime = 0.0f;
                float totalTime = 0.0f;
                ViewInfo maxLevelViewInfo = null;
                ViewInfo maxTimeViewInfo = null;
                for (ViewInfo viewInfo : UIPerformanceUtil.getViewInfos(activity)) {
                    int i = viewInfo.layerNum;
                    if (i > maxLevel) {
                        maxLevel = i;
                        maxLevelViewInfo = viewInfo;
                    }
                    float f = viewInfo.drawTime;
                    if (f > maxTime) {
                        maxTime = f;
                        maxTimeViewInfo = viewInfo;
                    }
                    totalTime += f;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("最大层级:");
                sb.append(maxLevel);
                sb.append("\n控件id:");
                String str = "no id";
                sb.append(maxLevelViewInfo == null ? str : maxLevelViewInfo.id);
                sb.append("\n总绘制耗时:");
                sb.append(totalTime);
                sb.append("ms\n绘制耗时最长控件:");
                sb.append(maxTime);
                sb.append("ms\n绘制耗时最长控件id:");
                if (maxTimeViewInfo != null) {
                    str = maxTimeViewInfo.id;
                }
                sb.append(str);
                sb.append("\n");
                String detail = sb.toString();
                AppHealthInfo.DataBean.UiLevelBean uiLevelBean = new AppHealthInfo.DataBean.UiLevelBean();
                uiLevelBean.setPage(activity.getClass().getCanonicalName());
                uiLevelBean.setLevel("" + maxLevel);
                uiLevelBean.setDetail(detail);
                AppHealthInfoUtil.getInstance().addUiLevelInfo(uiLevelBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recordActivityLifeCycleStatus(Activity activity, int lifeCycleStatus) {
        ActivityLifecycleInfo activityLifecaycleInfo = DokitConstant.ACTIVITY_LIFECYCLE_INFOS.get(activity.getClass().getCanonicalName());
        if (activityLifecaycleInfo == null) {
            ActivityLifecycleInfo activityLifecaycleInfo2 = new ActivityLifecycleInfo();
            activityLifecaycleInfo2.setActivityName(activity.getClass().getCanonicalName());
            if (lifeCycleStatus == LIFE_CYCLE_STATUS_CREATE) {
                activityLifecaycleInfo2.setActivityLifeCycleCount(0);
            } else if (lifeCycleStatus == LIFE_CYCLE_STATUS_RESUME) {
                activityLifecaycleInfo2.setActivityLifeCycleCount(activityLifecaycleInfo2.getActivityLifeCycleCount() + 1);
            } else if (lifeCycleStatus == LIFE_CYCLE_STATUS_STOPPED) {
                activityLifecaycleInfo2.setInvokeStopMethod(true);
            }
            DokitConstant.ACTIVITY_LIFECYCLE_INFOS.put(activity.getClass().getCanonicalName(), activityLifecaycleInfo2);
            return;
        }
        activityLifecaycleInfo.setActivityName(activity.getClass().getCanonicalName());
        if (lifeCycleStatus == LIFE_CYCLE_STATUS_CREATE) {
            activityLifecaycleInfo.setActivityLifeCycleCount(0);
        } else if (lifeCycleStatus == LIFE_CYCLE_STATUS_RESUME) {
            activityLifecaycleInfo.setActivityLifeCycleCount(activityLifecaycleInfo.getActivityLifeCycleCount() + 1);
        } else if (lifeCycleStatus == LIFE_CYCLE_STATUS_STOPPED) {
            activityLifecaycleInfo.setInvokeStopMethod(true);
        } else if (lifeCycleStatus == LIFE_CYCLE_STATUS_DESTROY) {
            DokitConstant.ACTIVITY_LIFECYCLE_INFOS.remove(activity.getClass().getCanonicalName());
        }
    }
}
