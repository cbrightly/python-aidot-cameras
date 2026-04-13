package com.bumptech.glide.load.data;

import android.content.res.AssetManager;
import androidx.annotation.NonNull;
import java.io.InputStream;

/* compiled from: StreamAssetPathFetcher */
public class m extends b<InputStream> {
    public m(AssetManager assetManager, String assetPath) {
        super(assetManager, assetPath);
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public InputStream e(AssetManager assetManager, String path) {
        return assetManager.open(path);
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public void c(InputStream data) {
        data.close();
    }

    @NonNull
    public Class<InputStream> a() {
        return InputStream.class;
    }
}
