package com.bumptech.glide.load.data;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import androidx.annotation.NonNull;
import java.io.FileNotFoundException;

/* compiled from: AssetFileDescriptorLocalUriFetcher */
public final class a extends l<AssetFileDescriptor> {
    public a(ContentResolver contentResolver, Uri uri) {
        super(contentResolver, uri);
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public AssetFileDescriptor e(Uri uri, ContentResolver contentResolver) {
        AssetFileDescriptor result = contentResolver.openAssetFileDescriptor(uri, "r");
        if (result != null) {
            return result;
        }
        throw new FileNotFoundException("FileDescriptor is null for: " + uri);
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public void c(AssetFileDescriptor data) {
        data.close();
    }

    @NonNull
    public Class<AssetFileDescriptor> a() {
        return AssetFileDescriptor.class;
    }
}
