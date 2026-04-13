package com.bumptech.glide.load.data.mediastore;

import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics;

/* compiled from: MediaStoreUtil */
public final class b {
    public static boolean b(Uri uri) {
        return uri != null && FirebaseAnalytics.Param.CONTENT.equals(uri.getScheme()) && "media".equals(uri.getAuthority());
    }

    private static boolean e(Uri uri) {
        return uri.getPathSegments().contains("video");
    }

    public static boolean c(Uri uri) {
        return b(uri) && e(uri);
    }

    public static boolean a(Uri uri) {
        return b(uri) && !e(uri);
    }

    public static boolean d(int width, int height) {
        return width != Integer.MIN_VALUE && height != Integer.MIN_VALUE && width <= 512 && height <= 384;
    }
}
