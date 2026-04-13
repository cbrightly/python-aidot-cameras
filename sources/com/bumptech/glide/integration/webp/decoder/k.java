package com.bumptech.glide.integration.webp.decoder;

import android.util.Log;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.l;
import com.bumptech.glide.util.a;
import java.io.File;
import java.io.IOException;

/* compiled from: WebpDrawableEncoder */
public class k implements l<WebpDrawable> {
    public c b(i options) {
        return c.SOURCE;
    }

    /* renamed from: c */
    public boolean a(t<WebpDrawable> data, File file, i options) {
        try {
            a.e(data.get().c(), file);
            return true;
        } catch (IOException e) {
            if (!Log.isLoggable("WebpEncoder", 5)) {
                return false;
            }
            Log.w("WebpEncoder", "Failed to encode WebP drawable data", e);
            return false;
        }
    }
}
