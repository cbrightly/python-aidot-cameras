package com.bumptech.glide.load.resource.drawable;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.k;
import java.util.List;

/* compiled from: ResourceDrawableDecoder */
public class d implements k<Uri, Drawable> {
    private final Context a;

    public d(Context context) {
        this.a = context.getApplicationContext();
    }

    /* renamed from: h */
    public boolean a(@NonNull Uri source, @NonNull i options) {
        return source.getScheme().equals("android.resource");
    }

    @Nullable
    /* renamed from: c */
    public t<Drawable> b(@NonNull Uri source, int width, int height, @NonNull i options) {
        Context targetContext = d(source, source.getAuthority());
        return c.c(a.b(this.a, targetContext, g(targetContext, source)));
    }

    @NonNull
    private Context d(Uri source, String packageName) {
        if (packageName.equals(this.a.getPackageName())) {
            return this.a;
        }
        try {
            return this.a.createPackageContext(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            if (packageName.contains(this.a.getPackageName())) {
                return this.a;
            }
            throw new IllegalArgumentException("Failed to obtain context or unrecognized Uri format for: " + source, e);
        }
    }

    @DrawableRes
    private int g(Context context, Uri source) {
        List<String> segments = source.getPathSegments();
        if (segments.size() == 2) {
            return f(context, source);
        }
        if (segments.size() == 1) {
            return e(source);
        }
        throw new IllegalArgumentException("Unrecognized Uri format: " + source);
    }

    @DrawableRes
    private int f(Context context, Uri source) {
        List<String> segments = source.getPathSegments();
        String packageName = source.getAuthority();
        String typeName = segments.get(0);
        String resourceName = segments.get(1);
        int result = context.getResources().getIdentifier(resourceName, typeName, packageName);
        if (result == 0) {
            result = Resources.getSystem().getIdentifier(resourceName, typeName, "android");
        }
        if (result != 0) {
            return result;
        }
        throw new IllegalArgumentException("Failed to find resource id for: " + source);
    }

    @DrawableRes
    private int e(Uri source) {
        try {
            return Integer.parseInt(source.getPathSegments().get(0));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Unrecognized Uri format: " + source, e);
        }
    }
}
