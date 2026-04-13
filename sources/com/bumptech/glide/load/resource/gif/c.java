package com.bumptech.glide.load.resource.gif;

import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.l;
import com.bumptech.glide.util.a;
import java.io.File;
import java.io.IOException;

/* compiled from: GifDrawableEncoder */
public class c implements l<GifDrawable> {
    @NonNull
    public com.bumptech.glide.load.c b(@NonNull i options) {
        return com.bumptech.glide.load.c.SOURCE;
    }

    /* renamed from: c */
    public boolean a(@NonNull t<GifDrawable> data, @NonNull File file, @NonNull i options) {
        try {
            a.e(data.get().c(), file);
            return true;
        } catch (IOException e) {
            if (!Log.isLoggable("GifEncoder", 5)) {
                return false;
            }
            Log.w("GifEncoder", "Failed to encode GIF drawable data", e);
            return false;
        }
    }
}
