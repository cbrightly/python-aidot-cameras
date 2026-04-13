package com.yanzhenjie.andserver.framework.website;

import android.text.TextUtils;
import androidx.annotation.NonNull;

/* compiled from: BasicWebsite */
public abstract class a extends c {
    private final String h;

    public a(@NonNull String indexFileName) {
        com.yanzhenjie.andserver.util.a.b(!TextUtils.isEmpty(indexFileName), "The indexFileName cannot be empty.");
        this.h = indexFileName;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String h() {
        return this.h;
    }
}
