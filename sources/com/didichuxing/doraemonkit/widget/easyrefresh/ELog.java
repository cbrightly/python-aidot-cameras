package com.didichuxing.doraemonkit.widget.easyrefresh;

import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;

public final class ELog {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static boolean isEnabled = true;

    private ELog() {
    }

    public static void setEnable(boolean enableBoolean) {
        isEnabled = enableBoolean;
    }

    public static boolean isEnable() {
        return isEnabled;
    }

    private static void log(LEVEL level, String tag, String msg, Throwable thr) {
        if (isEnabled) {
            log2console(level, tag, msg, thr);
        }
    }

    private static void log2console(LEVEL level, String tag, String msg, Throwable thr) {
        if (0 == 0) {
            switch (AnonymousClass1.$SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$ELog$LEVEL[level.ordinal()]) {
                case 1:
                    if (thr == null) {
                        Log.v(tag, msg);
                        return;
                    } else {
                        Log.v(tag, msg, thr);
                        return;
                    }
                case 2:
                    if (thr == null) {
                        Log.d(tag, msg);
                        return;
                    } else {
                        Log.d(tag, msg, thr);
                        return;
                    }
                case 3:
                    if (thr == null) {
                        Log.i(tag, msg);
                        return;
                    } else {
                        Log.i(tag, msg, thr);
                        return;
                    }
                case 4:
                    if (thr == null) {
                        Log.w(tag, msg);
                        return;
                    } else if (TextUtils.isEmpty(msg)) {
                        Log.w(tag, thr);
                        return;
                    } else {
                        Log.w(tag, msg, thr);
                        return;
                    }
                case 5:
                    if (thr == null) {
                        Log.e(tag, msg);
                        return;
                    } else {
                        Log.e(tag, msg, thr);
                        return;
                    }
                case 6:
                    if (thr == null) {
                        Log.wtf(tag, msg);
                        return;
                    } else if (TextUtils.isEmpty(msg)) {
                        Log.wtf(tag, thr);
                        return;
                    } else {
                        Log.wtf(tag, msg, thr);
                        return;
                    }
                default:
                    return;
            }
        }
    }

    /* renamed from: com.didichuxing.doraemonkit.widget.easyrefresh.ELog$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$ELog$LEVEL;

        static {
            int[] iArr = new int[LEVEL.values().length];
            $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$ELog$LEVEL = iArr;
            try {
                iArr[LEVEL.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$ELog$LEVEL[LEVEL.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$ELog$LEVEL[LEVEL.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$ELog$LEVEL[LEVEL.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$ELog$LEVEL[LEVEL.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$widget$easyrefresh$ELog$LEVEL[LEVEL.ASSERT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public static void v(String tag, String msg) {
        log(LEVEL.VERBOSE, tag, msg, (Throwable) null);
    }

    public static void v(String tag, String msg, Throwable thr) {
        log(LEVEL.VERBOSE, tag, msg, thr);
    }

    public static void d(String tag, String msg) {
        log(LEVEL.DEBUG, tag, msg, (Throwable) null);
    }

    public static void d(String tag, String msg, Throwable thr) {
        log(LEVEL.DEBUG, tag, msg, thr);
    }

    public static void i(String tag, String msg) {
        log(LEVEL.INFO, tag, msg, (Throwable) null);
    }

    public static void i(String tag, String msg, Throwable thr) {
        log(LEVEL.INFO, tag, msg, thr);
    }

    public static void w(String tag, String msg) {
        log(LEVEL.WARN, tag, msg, (Throwable) null);
    }

    public static void w(String tag, String msg, Throwable thr) {
        log(LEVEL.WARN, tag, msg, thr);
    }

    public static void w(String tag, Throwable thr) {
        log(LEVEL.WARN, tag, "", thr);
    }

    public static void e(String tag, String msg) {
        log(LEVEL.ERROR, tag, msg, (Throwable) null);
    }

    public static void e(String tag, String msg, Throwable thr) {
        log(LEVEL.ERROR, tag, msg, thr);
    }

    public enum LEVEL {
        VERBOSE(2, ExifInterface.GPS_MEASUREMENT_INTERRUPTED),
        DEBUG(3, "D"),
        INFO(4, "I"),
        WARN(5, ExifInterface.LONGITUDE_WEST),
        ERROR(6, ExifInterface.LONGITUDE_EAST),
        ASSERT(7, ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
        
        final int level;
        final String levelString;

        private LEVEL(int level2, String levelString2) {
            this.level = level2;
            this.levelString = levelString2;
        }

        public String getLevelString() {
            return this.levelString;
        }

        public int getLevel() {
            return this.level;
        }
    }
}
