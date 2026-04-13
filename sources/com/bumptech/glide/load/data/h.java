package com.bumptech.glide.load.data;

import android.content.res.AssetManager;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;

/* compiled from: FileDescriptorAssetPathFetcher */
public class h extends b<ParcelFileDescriptor> {
    public h(AssetManager assetManager, String assetPath) {
        super(assetManager, assetPath);
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public ParcelFileDescriptor e(AssetManager assetManager, String path) {
        return assetManager.openFd(path).getParcelFileDescriptor();
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public void c(ParcelFileDescriptor data) {
        data.close();
    }

    @NonNull
    public Class<ParcelFileDescriptor> a() {
        return ParcelFileDescriptor.class;
    }
}
