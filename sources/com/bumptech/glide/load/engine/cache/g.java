package com.bumptech.glide.load.engine.cache;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.cache.h;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.util.f;

/* compiled from: LruResourceCache */
public class g extends f<com.bumptech.glide.load.f, t<?>> implements h {
    private h.a e;

    @Nullable
    public /* bridge */ /* synthetic */ t b(@NonNull com.bumptech.glide.load.f fVar, @Nullable t tVar) {
        return (t) super.k(fVar, tVar);
    }

    @Nullable
    public /* bridge */ /* synthetic */ t c(@NonNull com.bumptech.glide.load.f fVar) {
        return (t) super.l(fVar);
    }

    public g(long size) {
        super(size);
    }

    public void e(@NonNull h.a listener) {
        this.e = listener;
    }

    /* access modifiers changed from: protected */
    /* renamed from: o */
    public void j(@NonNull com.bumptech.glide.load.f key, @Nullable t<?> item) {
        h.a aVar = this.e;
        if (aVar != null && item != null) {
            aVar.a(item);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: n */
    public int i(@Nullable t<?> item) {
        if (item == null) {
            return super.i(null);
        }
        return item.getSize();
    }

    @SuppressLint({"InlinedApi"})
    public void a(int level) {
        if (level >= 40) {
            d();
        } else if (level >= 20 || level == 15) {
            m(h() / 2);
        }
    }
}
