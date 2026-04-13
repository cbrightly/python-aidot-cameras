package io.microshow.rxffmpeg.player;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

public class Helper {
    private static long lastClickTime;

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getFullScreenHeight(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        scanForActivity(context).getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static ViewGroup getDecorView(Context mContext) {
        Activity activity = scanForActivity(mContext);
        if (activity == null) {
            return null;
        }
        return (ViewGroup) activity.getWindow().getDecorView();
    }

    public static ViewGroup getDecorView(Activity _activity) {
        Activity activity = _activity;
        if (activity == null) {
            return null;
        }
        return (ViewGroup) activity.getWindow().getDecorView();
    }

    public static void showSysBar(Activity activity, ViewGroup decorView) {
        int uiOptions = decorView.getSystemUiVisibility();
        int i = Build.VERSION.SDK_INT;
        if (i >= 16) {
            uiOptions &= -3;
        }
        if (i >= 19) {
            uiOptions &= -4097;
        }
        decorView.setSystemUiVisibility(uiOptions);
        activity.getWindow().clearFlags(1024);
    }

    public static void hideSysBar(Activity activity, ViewGroup decorView) {
        int uiOptions = decorView.getSystemUiVisibility();
        int i = Build.VERSION.SDK_INT;
        if (i >= 16) {
            uiOptions |= 2;
        }
        if (i >= 19) {
            uiOptions |= 4096;
        }
        decorView.setSystemUiVisibility(uiOptions);
        activity.getWindow().setFlags(1024, 1024);
    }

    public static ViewGroup setFullScreen(Context context, boolean isFullScreen) {
        Activity activity = scanForActivity(context);
        ViewGroup decorView = getDecorView(activity);
        if (decorView == null) {
            return null;
        }
        if (isFullScreen) {
            hideSysBar(activity, decorView);
            activity.setRequestedOrientation(0);
        } else {
            showSysBar(activity, decorView);
            activity.setRequestedOrientation(1);
        }
        return decorView;
    }

    public static String secdsToDateFormat(int secds, int totalsecds) {
        int i = secds;
        long hours = (long) (i / 3600);
        long minutes = (long) ((i % 3600) / 60);
        long seconds = (long) (i % 60);
        String sh = "00";
        if (hours > 0) {
            if (hours < 10) {
                sh = "0" + hours;
            } else {
                sh = hours + "";
            }
        }
        String sm = "00";
        if (minutes > 0) {
            if (minutes < 10) {
                sm = "0" + minutes;
            } else {
                sm = minutes + "";
            }
        }
        String ss = "00";
        if (seconds > 0) {
            if (seconds < 10) {
                ss = "0" + seconds;
            } else {
                ss = seconds + "";
            }
        }
        if (totalsecds >= 3600) {
            return sh + ":" + sm + ":" + ss;
        }
        return sm + ":" + ss;
    }

    public static synchronized boolean isFastClick() {
        synchronized (Helper.class) {
            long time = System.currentTimeMillis();
            if (time - lastClickTime < 1000) {
                return true;
            }
            lastClickTime = time;
            return false;
        }
    }
}
