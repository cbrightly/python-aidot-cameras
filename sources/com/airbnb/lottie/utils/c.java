package com.airbnb.lottie.utils;

import android.util.Log;
import com.airbnb.lottie.b0;
import com.airbnb.lottie.h0;
import java.util.HashSet;
import java.util.Set;

/* compiled from: LogcatLogger */
public class c implements h0 {
    private static final Set<String> a = new HashSet();

    public void debug(String message) {
        c(message, (Throwable) null);
    }

    public void c(String message, Throwable exception) {
        if (b0.a) {
            Log.d("LOTTIE", message, exception);
        }
    }

    public void a(String message) {
        b(message, (Throwable) null);
    }

    public void b(String message, Throwable exception) {
        Set<String> set = a;
        if (!set.contains(message)) {
            Log.w("LOTTIE", message, exception);
            set.add(message);
        }
    }

    public void error(String message, Throwable exception) {
        if (b0.a) {
            Log.d("LOTTIE", message, exception);
        }
    }
}
