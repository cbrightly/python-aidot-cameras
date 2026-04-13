package com.google.firebase.tracing;

import android.os.Build;
import android.os.Trace;

public final class FirebaseTrace {
    private FirebaseTrace() {
    }

    public static void pushTrace(String name) {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.beginSection(name);
        }
    }

    public static void popTrace() {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.endSection();
        }
    }
}
