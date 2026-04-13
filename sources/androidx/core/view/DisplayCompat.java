package androidx.core.view;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

public final class DisplayCompat {
    private static final int DISPLAY_SIZE_4K_HEIGHT = 2160;
    private static final int DISPLAY_SIZE_4K_WIDTH = 3840;

    private DisplayCompat() {
    }

    @NonNull
    public static ModeCompat getMode(@NonNull Context context, @NonNull Display display) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.getMode(context, display);
        }
        return new ModeCompat(getDisplaySize(context, display));
    }

    @NonNull
    private static Point getDisplaySize(@NonNull Context context, @NonNull Display display) {
        Point displaySize = getCurrentDisplaySizeFromWorkarounds(context, display);
        if (displaySize != null) {
            return displaySize;
        }
        Point displaySize2 = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            Api17Impl.getRealSize(display, displaySize2);
        } else {
            display.getSize(displaySize2);
        }
        return displaySize2;
    }

    @SuppressLint({"ArrayReturn"})
    @NonNull
    public static ModeCompat[] getSupportedModes(@NonNull Context context, @NonNull Display display) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.getSupportedModes(context, display);
        }
        return new ModeCompat[]{getMode(context, display)};
    }

    private static Point parseDisplaySize(@NonNull String displaySize) {
        String[] displaySizeParts = displaySize.trim().split("x", -1);
        if (displaySizeParts.length == 2) {
            int width = Integer.parseInt(displaySizeParts[0]);
            int height = Integer.parseInt(displaySizeParts[1]);
            if (width > 0 && height > 0) {
                return new Point(width, height);
            }
        }
        throw new NumberFormatException();
    }

    @Nullable
    private static String getSystemProperty(String name) {
        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            return (String) systemProperties.getMethod("get", new Class[]{String.class}).invoke(systemProperties, new Object[]{name});
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean isTv(@NonNull Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService("uimode");
        return uiModeManager != null && uiModeManager.getCurrentModeType() == 4;
    }

    @Nullable
    private static Point parsePhysicalDisplaySizeFromSystemProperties(@NonNull String property, @NonNull Display display) {
        if (display.getDisplayId() != 0) {
            return null;
        }
        String displaySize = getSystemProperty(property);
        if (TextUtils.isEmpty(displaySize)) {
            return null;
        }
        try {
            return parseDisplaySize(displaySize);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static Point getCurrentDisplaySizeFromWorkarounds(@NonNull Context context, @NonNull Display display) {
        Point displaySize;
        if (Build.VERSION.SDK_INT < 28) {
            displaySize = parsePhysicalDisplaySizeFromSystemProperties("sys.display-size", display);
        } else {
            displaySize = parsePhysicalDisplaySizeFromSystemProperties("vendor.display-size", display);
        }
        if (displaySize != null) {
            return displaySize;
        }
        if (!isSonyBravia4kTv(context) || !isCurrentModeTheLargestMode(display)) {
            return null;
        }
        return new Point(DISPLAY_SIZE_4K_WIDTH, DISPLAY_SIZE_4K_HEIGHT);
    }

    private static boolean isSonyBravia4kTv(@NonNull Context context) {
        return isTv(context) && "Sony".equals(Build.MANUFACTURER) && Build.MODEL.startsWith("BRAVIA") && context.getPackageManager().hasSystemFeature("com.sony.dtv.hardware.panel.qfhd");
    }

    static boolean isCurrentModeTheLargestMode(@NonNull Display display) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.isCurrentModeTheLargestMode(display);
        }
        return true;
    }

    @RequiresApi(23)
    public static class Api23Impl {
        private Api23Impl() {
        }

        @NonNull
        static ModeCompat getMode(@NonNull Context context, @NonNull Display display) {
            Display.Mode currentMode = display.getMode();
            Point workaroundSize = DisplayCompat.getCurrentDisplaySizeFromWorkarounds(context, display);
            if (workaroundSize == null || physicalSizeEquals(currentMode, workaroundSize)) {
                return new ModeCompat(currentMode, true);
            }
            return new ModeCompat(currentMode, workaroundSize);
        }

        @SuppressLint({"ArrayReturn"})
        @NonNull
        public static ModeCompat[] getSupportedModes(@NonNull Context context, @NonNull Display display) {
            ModeCompat modeCompat;
            Display.Mode[] supportedModes = display.getSupportedModes();
            ModeCompat[] supportedModesCompat = new ModeCompat[supportedModes.length];
            Display.Mode currentMode = display.getMode();
            Point workaroundSize = DisplayCompat.getCurrentDisplaySizeFromWorkarounds(context, display);
            if (workaroundSize == null || physicalSizeEquals(currentMode, workaroundSize)) {
                for (int i = 0; i < supportedModes.length; i++) {
                    supportedModesCompat[i] = new ModeCompat(supportedModes[i], physicalSizeEquals(supportedModes[i], currentMode));
                }
            } else {
                for (int i2 = 0; i2 < supportedModes.length; i2++) {
                    if (physicalSizeEquals(supportedModes[i2], currentMode)) {
                        modeCompat = new ModeCompat(supportedModes[i2], workaroundSize);
                    } else {
                        modeCompat = new ModeCompat(supportedModes[i2], false);
                    }
                    supportedModesCompat[i2] = modeCompat;
                }
            }
            return supportedModesCompat;
        }

        static boolean isCurrentModeTheLargestMode(@NonNull Display display) {
            Display.Mode currentMode = display.getMode();
            Display.Mode[] supportedModes = display.getSupportedModes();
            for (int i = 0; i < supportedModes.length; i++) {
                if (currentMode.getPhysicalHeight() < supportedModes[i].getPhysicalHeight() || currentMode.getPhysicalWidth() < supportedModes[i].getPhysicalWidth()) {
                    return false;
                }
            }
            return true;
        }

        static boolean physicalSizeEquals(Display.Mode mode, Point size) {
            return (mode.getPhysicalWidth() == size.x && mode.getPhysicalHeight() == size.y) || (mode.getPhysicalWidth() == size.y && mode.getPhysicalHeight() == size.x);
        }

        static boolean physicalSizeEquals(Display.Mode mode, Display.Mode otherMode) {
            return mode.getPhysicalWidth() == otherMode.getPhysicalWidth() && mode.getPhysicalHeight() == otherMode.getPhysicalHeight();
        }
    }

    @RequiresApi(17)
    public static class Api17Impl {
        private Api17Impl() {
        }

        static void getRealSize(Display display, Point displaySize) {
            display.getRealSize(displaySize);
        }
    }

    public static final class ModeCompat {
        private final boolean mIsNative;
        private final Display.Mode mMode;
        private final Point mPhysicalSize;

        ModeCompat(@NonNull Point physicalSize) {
            Preconditions.checkNotNull(physicalSize, "physicalSize == null");
            this.mPhysicalSize = physicalSize;
            this.mMode = null;
            this.mIsNative = true;
        }

        @RequiresApi(23)
        ModeCompat(@NonNull Display.Mode mode, boolean isNative) {
            Preconditions.checkNotNull(mode, "mode == null, can't wrap a null reference");
            this.mPhysicalSize = new Point(mode.getPhysicalWidth(), mode.getPhysicalHeight());
            this.mMode = mode;
            this.mIsNative = isNative;
        }

        @RequiresApi(23)
        ModeCompat(@NonNull Display.Mode mode, @NonNull Point physicalSize) {
            Preconditions.checkNotNull(mode, "mode == null, can't wrap a null reference");
            Preconditions.checkNotNull(physicalSize, "physicalSize == null");
            this.mPhysicalSize = physicalSize;
            this.mMode = mode;
            this.mIsNative = true;
        }

        public int getPhysicalWidth() {
            return this.mPhysicalSize.x;
        }

        public int getPhysicalHeight() {
            return this.mPhysicalSize.y;
        }

        @Deprecated
        public boolean isNative() {
            return this.mIsNative;
        }

        @RequiresApi(23)
        @Nullable
        public Display.Mode toMode() {
            return this.mMode;
        }
    }
}
