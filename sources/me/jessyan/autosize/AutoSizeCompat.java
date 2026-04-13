package me.jessyan.autosize;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import me.jessyan.autosize.external.ExternalAdaptInfo;
import me.jessyan.autosize.internal.CustomAdapt;
import me.jessyan.autosize.unit.Subunits;
import me.jessyan.autosize.utils.Preconditions;

public final class AutoSizeCompat {
    private static final int MODE_DEVICE_SIZE = Integer.MIN_VALUE;
    private static final int MODE_MASK = -1073741824;
    private static final int MODE_ON_WIDTH = 1073741824;
    private static final int MODE_SHIFT = 30;
    private static SparseArray<DisplayMetricsInfo> mCache = new SparseArray<>();

    private AutoSizeCompat() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static void autoConvertDensityOfGlobal(Resources resources) {
        if (AutoSizeConfig.getInstance().isBaseOnWidth()) {
            autoConvertDensityBaseOnWidth(resources, (float) AutoSizeConfig.getInstance().getDesignWidthInDp());
        } else {
            autoConvertDensityBaseOnHeight(resources, (float) AutoSizeConfig.getInstance().getDesignHeightInDp());
        }
    }

    public static void autoConvertDensityOfCustomAdapt(Resources resources, CustomAdapt customAdapt) {
        Preconditions.checkNotNull(customAdapt, "customAdapt == null");
        float sizeInDp = customAdapt.getSizeInDp();
        if (sizeInDp <= 0.0f) {
            if (customAdapt.isBaseOnWidth()) {
                sizeInDp = (float) AutoSizeConfig.getInstance().getDesignWidthInDp();
            } else {
                sizeInDp = (float) AutoSizeConfig.getInstance().getDesignHeightInDp();
            }
        }
        autoConvertDensity(resources, sizeInDp, customAdapt.isBaseOnWidth());
    }

    public static void autoConvertDensityOfExternalAdaptInfo(Resources resources, ExternalAdaptInfo externalAdaptInfo) {
        Preconditions.checkNotNull(externalAdaptInfo, "externalAdaptInfo == null");
        float sizeInDp = externalAdaptInfo.getSizeInDp();
        if (sizeInDp <= 0.0f) {
            if (externalAdaptInfo.isBaseOnWidth()) {
                sizeInDp = (float) AutoSizeConfig.getInstance().getDesignWidthInDp();
            } else {
                sizeInDp = (float) AutoSizeConfig.getInstance().getDesignHeightInDp();
            }
        }
        autoConvertDensity(resources, sizeInDp, externalAdaptInfo.isBaseOnWidth());
    }

    public static void autoConvertDensityBaseOnWidth(Resources resources, float designWidthInDp) {
        autoConvertDensity(resources, designWidthInDp, true);
    }

    public static void autoConvertDensityBaseOnHeight(Resources resources, float designHeightInDp) {
        autoConvertDensity(resources, designHeightInDp, false);
    }

    public static void autoConvertDensity(Resources resources, float sizeInDp, boolean isBaseOnWidth) {
        float subunitsDesignSize;
        int screenSize;
        float targetDensity;
        float targetXdpi;
        int targetScreenHeightDp;
        int targetScreenWidthDp;
        int targetDensityDpi;
        float systemFontScale;
        float systemFontScale2;
        Resources resources2 = resources;
        Preconditions.checkNotNull(resources2, "resources == null");
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
            float f = subunitsDesignSize2;
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
        setDensity(resources2, targetDensity, targetDensityDpi, systemFontScale, targetXdpi);
        setScreenSizeDp(resources2, targetScreenWidthDp, targetScreenHeightDp);
    }

    public static void cancelAdapt(Resources resources) {
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
        setDensity(resources, AutoSizeConfig.getInstance().getInitDensity(), AutoSizeConfig.getInstance().getInitDensityDpi(), AutoSizeConfig.getInstance().getInitScaledDensity(), initXdpi);
        setScreenSizeDp(resources, AutoSizeConfig.getInstance().getInitScreenWidthDp(), AutoSizeConfig.getInstance().getInitScreenHeightDp());
    }

    /* renamed from: me.jessyan.autosize.AutoSizeCompat$1  reason: invalid class name */
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

    private static void setDensity(Resources resources, float density, int densityDpi, float scaledDensity, float xdpi) {
        setDensity(resources.getDisplayMetrics(), density, densityDpi, scaledDensity, xdpi);
        setDensity(AutoSizeConfig.getInstance().getApplication().getResources().getDisplayMetrics(), density, densityDpi, scaledDensity, xdpi);
        DisplayMetrics activityDisplayMetricsOnMIUI = getMetricsOnMiui(resources);
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

    private static void setScreenSizeDp(Resources resources, int screenWidthDp, int screenHeightDp) {
        if (AutoSizeConfig.getInstance().getUnitsManager().isSupportDP() && AutoSizeConfig.getInstance().getUnitsManager().isSupportScreenSizeDP()) {
            setScreenSizeDp(resources.getConfiguration(), screenWidthDp, screenHeightDp);
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
