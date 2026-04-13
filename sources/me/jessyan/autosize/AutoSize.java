package me.jessyan.autosize;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import java.util.Locale;
import me.jessyan.autosize.external.ExternalAdaptInfo;
import me.jessyan.autosize.internal.CustomAdapt;
import me.jessyan.autosize.unit.Subunits;
import me.jessyan.autosize.utils.AutoSizeLog;
import me.jessyan.autosize.utils.Preconditions;

public final class AutoSize {
    private static final int MODE_DEVICE_SIZE = Integer.MIN_VALUE;
    private static final int MODE_MASK = -1073741824;
    private static final int MODE_ON_WIDTH = 1073741824;
    private static final int MODE_SHIFT = 30;
    private static SparseArray<DisplayMetricsInfo> mCache = new SparseArray<>();

    private AutoSize() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static boolean checkInit() {
        return AutoSizeConfig.getInstance().getInitDensity() != -1.0f;
    }

    public static void checkAndInit(Application application) {
        if (!checkInit()) {
            AutoSizeConfig.getInstance().setLog(true).init(application).setUseDeviceSize(false);
        }
    }

    public static void autoConvertDensityOfGlobal(Activity activity) {
        if (AutoSizeConfig.getInstance().isBaseOnWidth()) {
            autoConvertDensityBaseOnWidth(activity, (float) AutoSizeConfig.getInstance().getDesignWidthInDp());
        } else {
            autoConvertDensityBaseOnHeight(activity, (float) AutoSizeConfig.getInstance().getDesignHeightInDp());
        }
    }

    public static void autoConvertDensityOfCustomAdapt(Activity activity, CustomAdapt customAdapt) {
        Preconditions.checkNotNull(customAdapt, "customAdapt == null");
        float sizeInDp = customAdapt.getSizeInDp();
        if (sizeInDp <= 0.0f) {
            if (customAdapt.isBaseOnWidth()) {
                sizeInDp = (float) AutoSizeConfig.getInstance().getDesignWidthInDp();
            } else {
                sizeInDp = (float) AutoSizeConfig.getInstance().getDesignHeightInDp();
            }
        }
        autoConvertDensity(activity, sizeInDp, customAdapt.isBaseOnWidth());
    }

    public static void autoConvertDensityOfExternalAdaptInfo(Activity activity, ExternalAdaptInfo externalAdaptInfo) {
        Preconditions.checkNotNull(externalAdaptInfo, "externalAdaptInfo == null");
        float sizeInDp = externalAdaptInfo.getSizeInDp();
        if (sizeInDp <= 0.0f) {
            if (externalAdaptInfo.isBaseOnWidth()) {
                sizeInDp = (float) AutoSizeConfig.getInstance().getDesignWidthInDp();
            } else {
                sizeInDp = (float) AutoSizeConfig.getInstance().getDesignHeightInDp();
            }
        }
        autoConvertDensity(activity, sizeInDp, externalAdaptInfo.isBaseOnWidth());
    }

    public static void autoConvertDensityBaseOnWidth(Activity activity, float designWidthInDp) {
        autoConvertDensity(activity, designWidthInDp, true);
    }

    public static void autoConvertDensityBaseOnHeight(Activity activity, float designHeightInDp) {
        autoConvertDensity(activity, designHeightInDp, false);
    }

    public static void autoConvertDensity(Activity activity, float sizeInDp, boolean isBaseOnWidth) {
        float subunitsDesignSize;
        int screenSize;
        float targetDensity;
        float targetXdpi;
        int targetScreenHeightDp;
        int targetScreenWidthDp;
        int targetDensityDpi;
        float systemFontScale;
        float systemFontScale2;
        Activity activity2 = activity;
        Preconditions.checkNotNull(activity2, "activity == null");
        Preconditions.checkMainThread();
        if (isBaseOnWidth) {
            subunitsDesignSize = AutoSizeConfig.getInstance().getUnitsManager().getDesignWidth();
        } else {
            subunitsDesignSize = AutoSizeConfig.getInstance().getUnitsManager().getDesignHeight();
        }
        float subunitsDesignSize2 = subunitsDesignSize > 0.0f ? subunitsDesignSize : sizeInDp;
        if (isBaseOnWidth) {
            screenSize = AutoSizeConfig.getInstance().getScreenWidth();
        } else {
            screenSize = AutoSizeConfig.getInstance().getScreenHeight();
        }
        int key = Math.round((sizeInDp + subunitsDesignSize2 + ((float) screenSize)) * AutoSizeConfig.getInstance().getInitScaledDensity()) & 1073741823;
        int key2 = isBaseOnWidth ? 1073741824 | key : -1073741825 & key;
        int key3 = AutoSizeConfig.getInstance().isUseDeviceSize() ? Integer.MIN_VALUE | key2 : Integer.MAX_VALUE & key2;
        DisplayMetricsInfo displayMetricsInfo = mCache.get(key3);
        if (displayMetricsInfo == null) {
            if (isBaseOnWidth) {
                targetDensity = (((float) AutoSizeConfig.getInstance().getScreenWidth()) * 1.0f) / sizeInDp;
            } else {
                targetDensity = (((float) AutoSizeConfig.getInstance().getScreenHeight()) * 1.0f) / sizeInDp;
            }
            if (AutoSizeConfig.getInstance().getPrivateFontScale() > 0.0f) {
                systemFontScale = AutoSizeConfig.getInstance().getPrivateFontScale() * targetDensity;
            } else {
                if (AutoSizeConfig.getInstance().isExcludeFontScale()) {
                    systemFontScale2 = 1.0f;
                } else {
                    systemFontScale2 = (AutoSizeConfig.getInstance().getInitScaledDensity() * 1.0f) / AutoSizeConfig.getInstance().getInitDensity();
                }
                systemFontScale = targetDensity * systemFontScale2;
            }
            targetDensityDpi = (int) (160.0f * targetDensity);
            targetScreenWidthDp = (int) (((float) AutoSizeConfig.getInstance().getScreenWidth()) / targetDensity);
            targetScreenHeightDp = (int) (((float) AutoSizeConfig.getInstance().getScreenHeight()) / targetDensity);
            if (isBaseOnWidth) {
                targetXdpi = (((float) AutoSizeConfig.getInstance().getScreenWidth()) * 1.0f) / subunitsDesignSize2;
            } else {
                targetXdpi = (((float) AutoSizeConfig.getInstance().getScreenHeight()) * 1.0f) / subunitsDesignSize2;
            }
            SparseArray<DisplayMetricsInfo> sparseArray = mCache;
            int i = screenSize;
            DisplayMetricsInfo displayMetricsInfo2 = r12;
            DisplayMetricsInfo displayMetricsInfo3 = new DisplayMetricsInfo(targetDensity, targetDensityDpi, systemFontScale, targetXdpi, targetScreenWidthDp, targetScreenHeightDp);
            sparseArray.put(key3, displayMetricsInfo2);
        } else {
            targetDensity = displayMetricsInfo.getDensity();
            targetDensityDpi = displayMetricsInfo.getDensityDpi();
            systemFontScale = displayMetricsInfo.getScaledDensity();
            targetXdpi = displayMetricsInfo.getXdpi();
            targetScreenWidthDp = displayMetricsInfo.getScreenWidthDp();
            targetScreenHeightDp = displayMetricsInfo.getScreenHeightDp();
        }
        setDensity(activity2, targetDensity, targetDensityDpi, systemFontScale, targetXdpi);
        setScreenSizeDp(activity2, targetScreenWidthDp, targetScreenHeightDp);
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[13];
        objArr[0] = activity.getClass().getName();
        objArr[1] = activity.getClass().getSimpleName();
        objArr[2] = Boolean.valueOf(isBaseOnWidth);
        objArr[3] = isBaseOnWidth ? "designWidthInDp" : "designHeightInDp";
        objArr[4] = Float.valueOf(sizeInDp);
        objArr[5] = isBaseOnWidth ? "designWidthInSubunits" : "designHeightInSubunits";
        objArr[6] = Float.valueOf(subunitsDesignSize2);
        objArr[7] = Float.valueOf(targetDensity);
        objArr[8] = Float.valueOf(systemFontScale);
        objArr[9] = Integer.valueOf(targetDensityDpi);
        objArr[10] = Float.valueOf(targetXdpi);
        objArr[11] = Integer.valueOf(targetScreenWidthDp);
        objArr[12] = Integer.valueOf(targetScreenHeightDp);
        AutoSizeLog.d(String.format(locale, "The %s has been adapted! \n%s Info: isBaseOnWidth = %s, %s = %f, %s = %f, targetDensity = %f, targetScaledDensity = %f, targetDensityDpi = %d, targetXdpi = %f, targetScreenWidthDp = %d, targetScreenHeightDp = %d", objArr));
    }

    public static void cancelAdapt(Activity activity) {
        Preconditions.checkMainThread();
        float initXdpi = AutoSizeConfig.getInstance().getInitXdpi();
        switch (AnonymousClass1.$SwitchMap$me$jessyan$autosize$unit$Subunits[AutoSizeConfig.getInstance().getUnitsManager().getSupportSubunits().ordinal()]) {
            case 1:
                initXdpi /= 72.0f;
                break;
            case 2:
                initXdpi /= 25.4f;
                break;
        }
        setDensity(activity, AutoSizeConfig.getInstance().getInitDensity(), AutoSizeConfig.getInstance().getInitDensityDpi(), AutoSizeConfig.getInstance().getInitScaledDensity(), initXdpi);
        setScreenSizeDp(activity, AutoSizeConfig.getInstance().getInitScreenWidthDp(), AutoSizeConfig.getInstance().getInitScreenHeightDp());
    }

    /* renamed from: me.jessyan.autosize.AutoSize$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$me$jessyan$autosize$unit$Subunits;

        static {
            int[] iArr = new int[Subunits.values().length];
            $SwitchMap$me$jessyan$autosize$unit$Subunits = iArr;
            try {
                iArr[Subunits.PT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$me$jessyan$autosize$unit$Subunits[Subunits.MM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$me$jessyan$autosize$unit$Subunits[Subunits.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$me$jessyan$autosize$unit$Subunits[Subunits.IN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static void initCompatMultiProcess(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.query(Uri.parse("content://" + context.getPackageName() + ".autosize-init-provider"), (String[]) null, (String) null, (String[]) null, (String) null);
    }

    private static void setDensity(Activity activity, float density, int densityDpi, float scaledDensity, float xdpi) {
        setDensity(activity.getResources().getDisplayMetrics(), density, densityDpi, scaledDensity, xdpi);
        setDensity(AutoSizeConfig.getInstance().getApplication().getResources().getDisplayMetrics(), density, densityDpi, scaledDensity, xdpi);
        DisplayMetrics activityDisplayMetricsOnMIUI = getMetricsOnMiui(activity.getResources());
        DisplayMetrics appDisplayMetricsOnMIUI = getMetricsOnMiui(AutoSizeConfig.getInstance().getApplication().getResources());
        if (activityDisplayMetricsOnMIUI != null) {
            setDensity(activityDisplayMetricsOnMIUI, density, densityDpi, scaledDensity, xdpi);
        }
        if (appDisplayMetricsOnMIUI != null) {
            setDensity(appDisplayMetricsOnMIUI, density, densityDpi, scaledDensity, xdpi);
        }
    }

    private static void setDensity(DisplayMetrics displayMetrics, float density, int densityDpi, float scaledDensity, float xdpi) {
        if (AutoSizeConfig.getInstance().getUnitsManager().isSupportDP()) {
            displayMetrics.density = density;
            displayMetrics.densityDpi = densityDpi;
        }
        if (AutoSizeConfig.getInstance().getUnitsManager().isSupportSP()) {
            displayMetrics.scaledDensity = scaledDensity;
        }
        switch (AnonymousClass1.$SwitchMap$me$jessyan$autosize$unit$Subunits[AutoSizeConfig.getInstance().getUnitsManager().getSupportSubunits().ordinal()]) {
            case 1:
                displayMetrics.xdpi = 72.0f * xdpi;
                return;
            case 2:
                displayMetrics.xdpi = 25.4f * xdpi;
                return;
            case 4:
                displayMetrics.xdpi = xdpi;
                return;
            default:
                return;
        }
    }

    private static void setScreenSizeDp(Activity activity, int screenWidthDp, int screenHeightDp) {
        if (AutoSizeConfig.getInstance().getUnitsManager().isSupportDP() && AutoSizeConfig.getInstance().getUnitsManager().isSupportScreenSizeDP()) {
            setScreenSizeDp(activity.getResources().getConfiguration(), screenWidthDp, screenHeightDp);
            setScreenSizeDp(AutoSizeConfig.getInstance().getApplication().getResources().getConfiguration(), screenWidthDp, screenHeightDp);
        }
    }

    private static void setScreenSizeDp(Configuration configuration, int screenWidthDp, int screenHeightDp) {
        configuration.screenWidthDp = screenWidthDp;
        configuration.screenHeightDp = screenHeightDp;
    }

    private static DisplayMetrics getMetricsOnMiui(Resources resources) {
        if (!AutoSizeConfig.getInstance().isMiui() || AutoSizeConfig.getInstance().getTmpMetricsField() == null) {
            return null;
        }
        try {
            return (DisplayMetrics) AutoSizeConfig.getInstance().getTmpMetricsField().get(resources);
        } catch (Exception e) {
            return null;
        }
    }
}
