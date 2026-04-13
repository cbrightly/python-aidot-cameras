package com.google.firebase.platforminfo;

import androidx.annotation.Nullable;
import kotlin.e;

public final class KotlinDetector {
    private KotlinDetector() {
    }

    @Nullable
    public static String detectVersion() {
        try {
            return e.c.toString();
        } catch (NoClassDefFoundError e) {
            return null;
        }
    }
}
