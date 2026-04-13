package me.jessyan.autosize;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import java.lang.reflect.Field;
import me.jessyan.autosize.external.ExternalAdaptManager;
import me.jessyan.autosize.unit.Subunits;
import me.jessyan.autosize.unit.UnitsManager;
import me.jessyan.autosize.utils.AutoSizeLog;
import me.jessyan.autosize.utils.Preconditions;
import me.jessyan.autosize.utils.ScreenUtils;

public final class AutoSizeConfig {
    public static final boolean DEPENDENCY_ANDROIDX = findClassByClassName("androidx.fragment.app.FragmentActivity");
    public static final boolean DEPENDENCY_SUPPORT = findClassByClassName("androidx.fragment.app.FragmentActivity");
    private static final String KEY_DESIGN_HEIGHT_IN_DP = "design_height_in_dp";
    private static final String KEY_DESIGN_WIDTH_IN_DP = "design_width_in_dp";
    private static volatile AutoSizeConfig sInstance;
    private boolean isBaseOnWidth = true;
    private boolean isCustomFragment;
    private boolean isExcludeFontScale;
    private boolean isMiui;
    private boolean isStop;
    private boolean isUseDeviceSize = true;
    /* access modifiers changed from: private */
    public boolean isVertical;
    private ActivityLifecycleCallbacksImpl mActivityLifecycleCallbacks;
    private Application mApplication;
    /* access modifiers changed from: private */
    public int mDesignHeightInDp;
    /* access modifiers changed from: private */
    public int mDesignWidthInDp;
    private ExternalAdaptManager mExternalAdaptManager = new ExternalAdaptManager();
    private float mInitDensity = -1.0f;
    private int mInitDensityDpi;
    /* access modifiers changed from: private */
    public float mInitScaledDensity;
    private int mInitScreenHeightDp;
    private int mInitScreenWidthDp;
    private float mInitXdpi;
    private onAdaptListener mOnAdaptListener;
    /* access modifiers changed from: private */
    public int mScreenHeight;
    /* access modifiers changed from: private */
    public int mScreenWidth;
    private int mStatusBarHeight;
    private Field mTmpMetricsField;
    private UnitsManager mUnitsManager = new UnitsManager();
    private float privateFontScale;

    private static boolean findClassByClassName(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static AutoSizeConfig getInstance() {
        if (sInstance == null) {
            synchronized (AutoSizeConfig.class) {
                if (sInstance == null) {
                    sInstance = new AutoSizeConfig();
                }
            }
        }
        return sInstance;
    }

    private AutoSizeConfig() {
    }

    public Application getApplication() {
        Preconditions.checkNotNull(this.mApplication, "Please call the AutoSizeConfig#init() first");
        return this.mApplication;
    }

    /* access modifiers changed from: package-private */
    public AutoSizeConfig init(Application application) {
        return init(application, true, (AutoAdaptStrategy) null);
    }

    /* access modifiers changed from: package-private */
    public AutoSizeConfig init(Application application, boolean isBaseOnWidth2) {
        return init(application, isBaseOnWidth2, (AutoAdaptStrategy) null);
    }

    /* access modifiers changed from: package-private */
    public AutoSizeConfig init(final Application application, boolean isBaseOnWidth2, AutoAdaptStrategy strategy) {
        Preconditions.checkArgument(this.mInitDensity == -1.0f, "AutoSizeConfig#init() can only be called once");
        Preconditions.checkNotNull(application, "application == null");
        this.mApplication = application;
        this.isBaseOnWidth = isBaseOnWidth2;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        Configuration configuration = Resources.getSystem().getConfiguration();
        if (getInstance().getUnitsManager().getSupportSubunits() == Subunits.NONE) {
            this.mDesignWidthInDp = 360;
            this.mDesignHeightInDp = 640;
        } else {
            this.mDesignWidthInDp = 1080;
            this.mDesignHeightInDp = 1920;
        }
        getMetaData(application);
        this.isVertical = application.getResources().getConfiguration().orientation == 1;
        int[] screenSize = ScreenUtils.getScreenSize(application);
        this.mScreenWidth = screenSize[0];
        this.mScreenHeight = screenSize[1];
        this.mStatusBarHeight = ScreenUtils.getStatusBarHeight();
        AutoSizeLog.d("designWidthInDp = " + this.mDesignWidthInDp + ", designHeightInDp = " + this.mDesignHeightInDp + ", screenWidth = " + this.mScreenWidth + ", screenHeight = " + this.mScreenHeight);
        this.mInitDensity = displayMetrics.density;
        this.mInitDensityDpi = displayMetrics.densityDpi;
        this.mInitScaledDensity = displayMetrics.scaledDensity;
        this.mInitXdpi = displayMetrics.xdpi;
        this.mInitScreenWidthDp = configuration.screenWidthDp;
        this.mInitScreenHeightDp = configuration.screenHeightDp;
        application.registerComponentCallbacks(new ComponentCallbacks() {
            public void onConfigurationChanged(Configuration newConfig) {
                if (newConfig != null) {
                    if (newConfig.fontScale > 0.0f) {
                        float unused = AutoSizeConfig.this.mInitScaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
                        AutoSizeLog.d("initScaledDensity = " + AutoSizeConfig.this.mInitScaledDensity + " on ConfigurationChanged");
                    }
                    boolean unused2 = AutoSizeConfig.this.isVertical = newConfig.orientation == 1;
                    int[] screenSize = ScreenUtils.getScreenSize(application);
                    int unused3 = AutoSizeConfig.this.mScreenWidth = screenSize[0];
                    int unused4 = AutoSizeConfig.this.mScreenHeight = screenSize[1];
                }
            }

            public void onLowMemory() {
            }
        });
        AutoSizeLog.d("initDensity = " + this.mInitDensity + ", initScaledDensity = " + this.mInitScaledDensity);
        ActivityLifecycleCallbacksImpl activityLifecycleCallbacksImpl = new ActivityLifecycleCallbacksImpl(new WrapperAutoAdaptStrategy(strategy == null ? new DefaultAutoAdaptStrategy() : strategy));
        this.mActivityLifecycleCallbacks = activityLifecycleCallbacksImpl;
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacksImpl);
        if ("MiuiResources".equals(application.getResources().getClass().getSimpleName()) || "XResources".equals(application.getResources().getClass().getSimpleName())) {
            this.isMiui = true;
            try {
                Field declaredField = Resources.class.getDeclaredField("mTmpMetrics");
                this.mTmpMetricsField = declaredField;
                declaredField.setAccessible(true);
            } catch (Exception e) {
                this.mTmpMetricsField = null;
            }
        }
        return this;
    }

    public void restart() {
        Preconditions.checkNotNull(this.mActivityLifecycleCallbacks, "Please call the AutoSizeConfig#init() first");
        synchronized (AutoSizeConfig.class) {
            if (this.isStop) {
                this.mApplication.registerActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
                this.isStop = false;
            }
        }
    }

    public void stop(Activity activity) {
        Preconditions.checkNotNull(this.mActivityLifecycleCallbacks, "Please call the AutoSizeConfig#init() first");
        synchronized (AutoSizeConfig.class) {
            if (!this.isStop) {
                this.mApplication.unregisterActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
                AutoSize.cancelAdapt(activity);
                this.isStop = true;
            }
        }
    }

    public AutoSizeConfig setAutoAdaptStrategy(AutoAdaptStrategy autoAdaptStrategy) {
        Preconditions.checkNotNull(autoAdaptStrategy, "autoAdaptStrategy == null");
        Preconditions.checkNotNull(this.mActivityLifecycleCallbacks, "Please call the AutoSizeConfig#init() first");
        this.mActivityLifecycleCallbacks.setAutoAdaptStrategy(new WrapperAutoAdaptStrategy(autoAdaptStrategy));
        return this;
    }

    public AutoSizeConfig setOnAdaptListener(onAdaptListener onAdaptListener) {
        Preconditions.checkNotNull(onAdaptListener, "onAdaptListener == null");
        this.mOnAdaptListener = onAdaptListener;
        return this;
    }

    public AutoSizeConfig setBaseOnWidth(boolean baseOnWidth) {
        this.isBaseOnWidth = baseOnWidth;
        return this;
    }

    public AutoSizeConfig setUseDeviceSize(boolean useDeviceSize) {
        this.isUseDeviceSize = useDeviceSize;
        return this;
    }

    public AutoSizeConfig setLog(boolean log) {
        AutoSizeLog.setDebug(log);
        return this;
    }

    public AutoSizeConfig setCustomFragment(boolean customFragment) {
        this.isCustomFragment = customFragment;
        return this;
    }

    public boolean isCustomFragment() {
        return this.isCustomFragment;
    }

    public boolean isStop() {
        return this.isStop;
    }

    public ExternalAdaptManager getExternalAdaptManager() {
        return this.mExternalAdaptManager;
    }

    public UnitsManager getUnitsManager() {
        return this.mUnitsManager;
    }

    public onAdaptListener getOnAdaptListener() {
        return this.mOnAdaptListener;
    }

    public boolean isBaseOnWidth() {
        return this.isBaseOnWidth;
    }

    public boolean isUseDeviceSize() {
        return this.isUseDeviceSize;
    }

    public int getScreenWidth() {
        return this.mScreenWidth;
    }

    public int getScreenHeight() {
        return isUseDeviceSize() ? this.mScreenHeight : this.mScreenHeight - this.mStatusBarHeight;
    }

    public int getDesignWidthInDp() {
        Preconditions.checkArgument(this.mDesignWidthInDp > 0, "you must set design_width_in_dp  in your AndroidManifest file");
        return this.mDesignWidthInDp;
    }

    public int getDesignHeightInDp() {
        Preconditions.checkArgument(this.mDesignHeightInDp > 0, "you must set design_height_in_dp  in your AndroidManifest file");
        return this.mDesignHeightInDp;
    }

    public float getInitDensity() {
        return this.mInitDensity;
    }

    public int getInitDensityDpi() {
        return this.mInitDensityDpi;
    }

    public float getInitScaledDensity() {
        return this.mInitScaledDensity;
    }

    public float getInitXdpi() {
        return this.mInitXdpi;
    }

    public int getInitScreenWidthDp() {
        return this.mInitScreenWidthDp;
    }

    public int getInitScreenHeightDp() {
        return this.mInitScreenHeightDp;
    }

    public boolean isVertical() {
        return this.isVertical;
    }

    public boolean isMiui() {
        return this.isMiui;
    }

    public Field getTmpMetricsField() {
        return this.mTmpMetricsField;
    }

    public AutoSizeConfig setVertical(boolean vertical) {
        this.isVertical = vertical;
        return this;
    }

    public boolean isExcludeFontScale() {
        return this.isExcludeFontScale;
    }

    public AutoSizeConfig setExcludeFontScale(boolean excludeFontScale) {
        this.isExcludeFontScale = excludeFontScale;
        return this;
    }

    public AutoSizeConfig setPrivateFontScale(float fontScale) {
        this.privateFontScale = fontScale;
        return this;
    }

    public float getPrivateFontScale() {
        return this.privateFontScale;
    }

    public AutoSizeConfig setScreenWidth(int screenWidth) {
        Preconditions.checkArgument(screenWidth > 0, "screenWidth must be > 0");
        this.mScreenWidth = screenWidth;
        return this;
    }

    public AutoSizeConfig setScreenHeight(int screenHeight) {
        Preconditions.checkArgument(screenHeight > 0, "screenHeight must be > 0");
        this.mScreenHeight = screenHeight;
        return this;
    }

    public AutoSizeConfig setDesignWidthInDp(int designWidthInDp) {
        Preconditions.checkArgument(designWidthInDp > 0, "designWidthInDp must be > 0");
        this.mDesignWidthInDp = designWidthInDp;
        return this;
    }

    public AutoSizeConfig setDesignHeightInDp(int designHeightInDp) {
        Preconditions.checkArgument(designHeightInDp > 0, "designHeightInDp must be > 0");
        this.mDesignHeightInDp = designHeightInDp;
        return this;
    }

    public AutoSizeConfig setStatusBarHeight(int statusBarHeight) {
        Preconditions.checkArgument(statusBarHeight > 0, "statusBarHeight must be > 0");
        this.mStatusBarHeight = statusBarHeight;
        return this;
    }

    private void getMetaData(final Context context) {
        new Thread(new Runnable() {
            public void run() {
                Bundle bundle;
                try {
                    ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                    if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                        if (bundle.containsKey(AutoSizeConfig.KEY_DESIGN_WIDTH_IN_DP)) {
                            int unused = AutoSizeConfig.this.mDesignWidthInDp = ((Integer) applicationInfo.metaData.get(AutoSizeConfig.KEY_DESIGN_WIDTH_IN_DP)).intValue();
                        }
                        if (applicationInfo.metaData.containsKey(AutoSizeConfig.KEY_DESIGN_HEIGHT_IN_DP)) {
                            int unused2 = AutoSizeConfig.this.mDesignHeightInDp = ((Integer) applicationInfo.metaData.get(AutoSizeConfig.KEY_DESIGN_HEIGHT_IN_DP)).intValue();
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
