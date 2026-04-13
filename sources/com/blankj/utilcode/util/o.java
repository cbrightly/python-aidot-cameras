package com.blankj.utilcode.util;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import java.lang.reflect.Field;

/* compiled from: KeyboardUtils */
public final class o {
    private static int a = 0;

    public static void c() {
        InputMethodManager imm = (InputMethodManager) f0.a().getSystemService("input_method");
        if (imm != null) {
            imm.toggleSoftInput(0, 0);
        }
    }

    public static void a(@NonNull Activity activity) {
        if (activity != null) {
            b(activity.getWindow());
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void b(@NonNull Window window) {
        if (window != null) {
            InputMethodManager imm = (InputMethodManager) f0.a().getSystemService("input_method");
            if (imm != null) {
                for (String leakView : new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"}) {
                    try {
                        Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                        if (!leakViewField.isAccessible()) {
                            leakViewField.setAccessible(true);
                        }
                        Object obj = leakViewField.get(imm);
                        if (obj instanceof View) {
                            if (((View) obj).getRootView() == window.getDecorView().getRootView()) {
                                leakViewField.set(imm, (Object) null);
                            }
                        }
                    } catch (Throwable th) {
                    }
                }
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }
}
