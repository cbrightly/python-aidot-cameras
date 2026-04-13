package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.i;
import java.util.Collections;
import java.util.List;

/* compiled from: ModelLoader */
public interface n<Model, Data> {
    boolean a(@NonNull Model model);

    @Nullable
    a<Data> b(@NonNull Model model, int i, int i2, @NonNull i iVar);

    /* compiled from: ModelLoader */
    public static class a<Data> {
        public final f a;
        public final List<f> b;
        public final d<Data> c;

        public a(@NonNull f sourceKey, @NonNull d<Data> fetcher) {
            this(sourceKey, Collections.emptyList(), fetcher);
        }

        public a(@NonNull f sourceKey, @NonNull List<f> alternateKeys, @NonNull d<Data> fetcher) {
            this.a = (f) com.bumptech.glide.util.i.d(sourceKey);
            this.b = (List) com.bumptech.glide.util.i.d(alternateKeys);
            this.c = (d) com.bumptech.glide.util.i.d(fetcher);
        }
    }
}
