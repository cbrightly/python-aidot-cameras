package com.lzf.easyfloat;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.lzf.easyfloat.utils.LifecycleUtils;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EasyFloatInitializer.kt */
public final class EasyFloatInitializer extends ContentProvider {
    public boolean onCreate() {
        LifecycleUtils lifecycleUtils = LifecycleUtils.INSTANCE;
        Context context = getContext();
        k.c(context);
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            lifecycleUtils.setLifecycleCallbacks((Application) applicationContext);
            return true;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.app.Application");
    }

    @Nullable
    public Cursor query(@NotNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        k.e(uri, "uri");
        return null;
    }

    @Nullable
    public String getType(@NotNull Uri uri) {
        k.e(uri, "uri");
        return null;
    }

    @Nullable
    public Uri insert(@NotNull Uri uri, @Nullable ContentValues values) {
        k.e(uri, "uri");
        return null;
    }

    public int delete(@NotNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        k.e(uri, "uri");
        return 0;
    }

    public int update(@NotNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        k.e(uri, "uri");
        return 0;
    }
}
