package com.lzf.easyfloat.utils;

import android.app.Activity;
import android.app.Application;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import com.lzf.easyfloat.core.FloatingWindowHelper;
import com.lzf.easyfloat.core.FloatingWindowManager;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.enums.ShowPattern;
import java.lang.ref.WeakReference;
import java.util.Map;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LifecycleUtils.kt */
public final class LifecycleUtils {
    @NotNull
    public static final LifecycleUtils INSTANCE = new LifecycleUtils();
    /* access modifiers changed from: private */
    public static int activityCount;
    public static Application application;
    /* access modifiers changed from: private */
    @Nullable
    public static WeakReference<Activity> mTopActivity;

    private LifecycleUtils() {
    }

    @NotNull
    public final Application getApplication() {
        Application application2 = application;
        if (application2 != null) {
            return application2;
        }
        k.t("application");
        return null;
    }

    public final void setApplication(@NotNull Application application2) {
        k.e(application2, "<set-?>");
        application = application2;
    }

    @Nullable
    public final Activity getTopActivity() {
        WeakReference<Activity> weakReference = mTopActivity;
        if (weakReference == null) {
            return null;
        }
        return (Activity) weakReference.get();
    }

    public final void setLifecycleCallbacks(@NotNull Application application2) {
        k.e(application2, "application");
        setApplication(application2);
        application2.registerActivityLifecycleCallbacks(new LifecycleUtils$setLifecycleCallbacks$1());
    }

    /* access modifiers changed from: private */
    public final void checkShow(Activity activity) {
        for (Map.Entry $dstr$tag$manager : FloatingWindowManager.INSTANCE.getWindowMap().entrySet()) {
            String tag = (String) $dstr$tag$manager.getKey();
            FloatConfig $this$checkShow_u24lambda_u2d1_u24lambda_u2d0 = ((FloatingWindowHelper) $dstr$tag$manager.getValue()).getConfig();
            if ($this$checkShow_u24lambda_u2d1_u24lambda_u2d0.getShowPattern() != ShowPattern.CURRENT_ACTIVITY) {
                if ($this$checkShow_u24lambda_u2d1_u24lambda_u2d0.getShowPattern() == ShowPattern.BACKGROUND) {
                    INSTANCE.setVisible(false, tag);
                } else if ($this$checkShow_u24lambda_u2d1_u24lambda_u2d0.getNeedShow$easyfloat_release()) {
                    INSTANCE.setVisible(!$this$checkShow_u24lambda_u2d1_u24lambda_u2d0.getFilterSet().contains(activity.getComponentName().getClassName()), tag);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public final void checkHide(Activity activity) {
        IBinder it;
        View decorView;
        if (activity.isFinishing() || !isForeground()) {
            for (Map.Entry $dstr$tag$manager : FloatingWindowManager.INSTANCE.getWindowMap().entrySet()) {
                String tag = (String) $dstr$tag$manager.getKey();
                FloatingWindowHelper manager = (FloatingWindowHelper) $dstr$tag$manager.getValue();
                boolean z = true;
                if (activity.isFinishing() && (it = manager.getParams().token) != null) {
                    Window window = activity.getWindow();
                    IBinder iBinder = null;
                    if (!(window == null || (decorView = window.getDecorView()) == null)) {
                        iBinder = decorView.getWindowToken();
                    }
                    if (k.a(it, iBinder)) {
                        FloatingWindowManager.INSTANCE.dismiss(tag, true);
                    }
                }
                FloatConfig $this$checkHide_u24lambda_u2d4_u24lambda_u2d3 = manager.getConfig();
                LifecycleUtils lifecycleUtils = INSTANCE;
                if (!lifecycleUtils.isForeground() && manager.getConfig().getShowPattern() != ShowPattern.CURRENT_ACTIVITY) {
                    if ($this$checkHide_u24lambda_u2d4_u24lambda_u2d3.getShowPattern() == ShowPattern.FOREGROUND || !$this$checkHide_u24lambda_u2d4_u24lambda_u2d3.getNeedShow$easyfloat_release()) {
                        z = false;
                    }
                    lifecycleUtils.setVisible(z, tag);
                }
            }
        }
    }

    public final boolean isForeground() {
        return activityCount > 0;
    }

    static /* synthetic */ x setVisible$default(LifecycleUtils lifecycleUtils, boolean z, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            z = lifecycleUtils.isForeground();
        }
        return lifecycleUtils.setVisible(z, str);
    }

    private final x setVisible(boolean isShow, String tag) {
        return FloatingWindowManager.visible$default(FloatingWindowManager.INSTANCE, isShow, tag, false, 4, (Object) null);
    }
}
