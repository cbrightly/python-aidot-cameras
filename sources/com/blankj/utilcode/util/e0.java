package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import com.blankj.utilcode.util.f0;
import com.google.maps.android.BuildConfig;
import java.lang.reflect.Field;
import meshsdk.cache.CacheHandler;

/* compiled from: ToastUtils */
public final class e0 {
    /* access modifiers changed from: private */
    public static c a;
    /* access modifiers changed from: private */
    public static int b = -1;
    /* access modifiers changed from: private */
    public static int c = -1;
    /* access modifiers changed from: private */
    public static int d = -1;
    private static int e = -16777217;
    private static int f = -1;
    /* access modifiers changed from: private */
    public static int g = -16777217;
    /* access modifiers changed from: private */
    public static int h = -1;

    /* compiled from: ToastUtils */
    public interface c {
        void a(int i, int i2, int i3);

        void cancel();

        View getView();

        void show();
    }

    public static void n(CharSequence text) {
        k(text == null ? BuildConfig.TRAVIS : text, 0);
    }

    public static void o(String format, Object... args) {
        l(format, 0, args);
    }

    public static void m(CharSequence text) {
        k(text == null ? BuildConfig.TRAVIS : text, 1);
    }

    public static void i() {
        c cVar = a;
        if (cVar != null) {
            cVar.cancel();
        }
    }

    private static void l(String format, int duration, Object... args) {
        String text = format;
        if (text == null) {
            text = BuildConfig.TRAVIS;
        } else if (args != null && args.length > 0) {
            text = String.format(format, args);
        }
        k(text, duration);
    }

    /* compiled from: ToastUtils */
    public static final class a implements Runnable {
        final /* synthetic */ CharSequence c;
        final /* synthetic */ int d;

        a(CharSequence charSequence, int i) {
            this.c = charSequence;
            this.d = i;
        }

        @SuppressLint({"ShowToast"})
        public void run() {
            e0.i();
            c unused = e0.a = e.b(f0.a(), this.c, this.d);
            View toastView = e0.a.getView();
            if (toastView != null) {
                TextView tvMessage = (TextView) toastView.findViewById(16908299);
                if (e0.g != -16777217) {
                    tvMessage.setTextColor(e0.g);
                }
                if (e0.h != -1) {
                    tvMessage.setTextSize((float) e0.h);
                }
                if (!(e0.b == -1 && e0.c == -1 && e0.d == -1)) {
                    e0.a.a(e0.b, e0.c, e0.d);
                }
                e0.j(tvMessage);
                e0.a.show();
            }
        }
    }

    private static void k(CharSequence text, int duration) {
        h0.I(new a(text, duration));
    }

    /* access modifiers changed from: private */
    public static void j(TextView tvMsg) {
        if (f != -1) {
            a.getView().setBackgroundResource(f);
            tvMsg.setBackgroundColor(0);
        } else if (e != -16777217) {
            View toastView = a.getView();
            Drawable tvBg = toastView.getBackground();
            Drawable msgBg = tvMsg.getBackground();
            if (tvBg != null && msgBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(e, PorterDuff.Mode.SRC_IN));
                tvMsg.setBackgroundColor(0);
            } else if (tvBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(e, PorterDuff.Mode.SRC_IN));
            } else if (msgBg != null) {
                msgBg.setColorFilter(new PorterDuffColorFilter(e, PorterDuff.Mode.SRC_IN));
            } else {
                toastView.setBackgroundColor(e);
            }
        }
    }

    /* compiled from: ToastUtils */
    public static class e {
        static c b(Context context, CharSequence text, int duration) {
            if (!NotificationManagerCompat.from(context).areNotificationsEnabled() || Build.VERSION.SDK_INT < 23 || h0.B()) {
                return new f(a(context, text, duration));
            }
            return new d(a(context, text, duration));
        }

        private static Toast a(Context context, CharSequence text, int duration) {
            Toast toast = Toast.makeText(context, "", duration);
            toast.setText(text);
            return toast;
        }
    }

    /* compiled from: ToastUtils */
    public static class d extends b {
        d(Toast toast) {
            super(toast);
            if (Build.VERSION.SDK_INT == 25) {
                try {
                    Field mTNField = Toast.class.getDeclaredField("mTN");
                    mTNField.setAccessible(true);
                    Object mTN = mTNField.get(toast);
                    Field mTNmHandlerField = mTNField.getType().getDeclaredField("mHandler");
                    mTNmHandlerField.setAccessible(true);
                    mTNmHandlerField.set(mTN, new a((Handler) mTNmHandlerField.get(mTN)));
                } catch (Exception e) {
                }
            }
        }

        public void show() {
            this.a.show();
        }

        public void cancel() {
            this.a.cancel();
        }

        /* compiled from: ToastUtils */
        public static class a extends Handler {
            private Handler a;

            a(Handler impl) {
                this.a = impl;
            }

            public void handleMessage(Message msg) {
                this.a.handleMessage(msg);
            }

            public void dispatchMessage(Message msg) {
                try {
                    this.a.dispatchMessage(msg);
                } catch (Exception e) {
                    Log.e("ToastUtils", e.toString());
                }
            }
        }
    }

    /* compiled from: ToastUtils */
    public static class f extends b {
        private View b;
        private WindowManager c;
        private WindowManager.LayoutParams d = new WindowManager.LayoutParams();

        f(Toast toast) {
            super(toast);
        }

        /* compiled from: ToastUtils */
        public class a implements Runnable {
            a() {
            }

            public void run() {
                f.this.d();
            }
        }

        public void show() {
            h0.J(new a(), 300);
        }

        /* access modifiers changed from: private */
        public void d() {
            Toast toast = this.a;
            if (toast != null) {
                View view = toast.getView();
                this.b = view;
                if (view != null) {
                    Context context = this.a.getView().getContext();
                    int i = Build.VERSION.SDK_INT;
                    if (i < 25) {
                        this.c = (WindowManager) context.getSystemService("window");
                        this.d.type = 2005;
                    } else if (h0.B()) {
                        this.c = (WindowManager) context.getSystemService("window");
                        if (i >= 26) {
                            this.d.type = 2038;
                        } else {
                            this.d.type = 2002;
                        }
                    } else {
                        Context topActivityOrApp = h0.w();
                        if (!(topActivityOrApp instanceof Activity)) {
                            Log.w("ToastUtils", "Couldn't get top Activity.");
                            new d(this.a).show();
                            return;
                        }
                        Activity topActivity = (Activity) topActivityOrApp;
                        if (topActivity.isFinishing() || topActivity.isDestroyed()) {
                            Log.w("ToastUtils", topActivity + " is useless");
                            new d(this.a).show();
                            return;
                        }
                        this.c = topActivity.getWindowManager();
                        this.d.type = 99;
                        h0.a(topActivity, c());
                    }
                    e();
                    try {
                        WindowManager windowManager = this.c;
                        if (windowManager != null) {
                            windowManager.addView(this.b, this.d);
                        }
                    } catch (Exception e) {
                    }
                    h0.J(new b(), this.a.getDuration() == 0 ? CacheHandler.delayTime : 3500);
                }
            }
        }

        /* compiled from: ToastUtils */
        public class b implements Runnable {
            b() {
            }

            public void run() {
                f.this.cancel();
            }
        }

        private void e() {
            WindowManager.LayoutParams layoutParams = this.d;
            layoutParams.height = -2;
            layoutParams.width = -2;
            layoutParams.format = -3;
            layoutParams.windowAnimations = 16973828;
            layoutParams.setTitle("ToastWithoutNotification");
            WindowManager.LayoutParams layoutParams2 = this.d;
            layoutParams2.flags = 152;
            layoutParams2.packageName = f0.a().getPackageName();
            this.d.gravity = this.a.getGravity();
            WindowManager.LayoutParams layoutParams3 = this.d;
            int i = layoutParams3.gravity;
            if ((i & 7) == 7) {
                layoutParams3.horizontalWeight = 1.0f;
            }
            if ((i & 112) == 112) {
                layoutParams3.verticalWeight = 1.0f;
            }
            layoutParams3.x = this.a.getXOffset();
            this.d.y = this.a.getYOffset();
            this.d.horizontalMargin = this.a.getHorizontalMargin();
            this.d.verticalMargin = this.a.getVerticalMargin();
        }

        /* compiled from: ToastUtils */
        public class c extends f0.a {
            c() {
            }

            public void b(@NonNull Activity activity) {
                if (activity == null) {
                    throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
                } else if (e0.a != null) {
                    activity.getWindow().getDecorView().setVisibility(8);
                    e0.a.cancel();
                }
            }
        }

        private f0.a c() {
            return new c();
        }

        public void cancel() {
            try {
                WindowManager windowManager = this.c;
                if (windowManager != null) {
                    windowManager.removeViewImmediate(this.b);
                }
            } catch (Exception e) {
            }
            this.b = null;
            this.c = null;
            this.a = null;
        }
    }

    /* compiled from: ToastUtils */
    public static abstract class b implements c {
        Toast a;

        b(Toast toast) {
            this.a = toast;
        }

        public View getView() {
            return this.a.getView();
        }

        public void a(int gravity, int xOffset, int yOffset) {
            this.a.setGravity(gravity, xOffset, yOffset);
        }
    }
}
