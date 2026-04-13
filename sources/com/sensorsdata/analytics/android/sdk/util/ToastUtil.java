package com.sensorsdata.analytics.android.sdk.util;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import com.sensorsdata.analytics.android.sdk.SALog;
import java.lang.reflect.Field;

public class ToastUtil {
    private static final String TAG = ToastUtil.class.getSimpleName();
    private static final Handler mToastMainHandler = new Handler(Looper.getMainLooper());

    public static void showShort(Context context, String message) {
        if (context == null) {
            SALog.i(TAG, "context is null");
        } else if (!TextUtils.isEmpty(message)) {
            showToastToMain(context.getApplicationContext(), message, 0);
        }
    }

    public static void showLong(Context context, String message) {
        if (context == null) {
            SALog.i(TAG, "context is null");
        } else if (!TextUtils.isEmpty(message)) {
            showToastToMain(context.getApplicationContext(), message, 1);
        }
    }

    private static void showToastToMain(final Context context, final String message, final int lengthLong) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showToast(context, message, lengthLong);
        } else {
            mToastMainHandler.post(new Runnable() {
                public void run() {
                    ToastUtil.showToast(context, message, lengthLong);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void showToast(Context context, String message, int lengthLong) {
        Toast toast = Toast.makeText(context, message, lengthLong);
        hookToast(toast);
        toast.show();
    }

    private static void hookToast(Toast toast) {
        Field mHandler;
        int i = Build.VERSION.SDK_INT;
        if (26 > i || (isHuaWei() && 26 == i)) {
            try {
                Object mTn = ReflectUtil.findFieldRecur(toast, "mTN");
                if (mTn != null && (mHandler = ReflectUtil.findFieldObj(mTn.getClass(), "mHandler")) != null) {
                    mHandler.setAccessible(true);
                    mHandler.set(mTn, new HandlerProxy((Handler) mHandler.get(mTn)));
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    private static boolean isHuaWei() {
        String manufacturer = DeviceUtils.getManufacturer();
        if (manufacturer == null) {
            return false;
        }
        if (manufacturer.equalsIgnoreCase("honor") || manufacturer.equalsIgnoreCase("huawei")) {
            return true;
        }
        return false;
    }

    public static class HandlerProxy extends Handler {
        private Handler mHandler;

        public HandlerProxy(Handler handler) {
            this.mHandler = handler;
        }

        public void handleMessage(Message msg) {
            try {
                this.mHandler.handleMessage(msg);
            } catch (Exception e) {
            }
        }
    }
}
