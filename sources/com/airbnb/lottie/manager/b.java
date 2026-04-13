package com.airbnb.lottie.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import androidx.annotation.Nullable;
import com.airbnb.lottie.a0;
import com.airbnb.lottie.f0;
import com.airbnb.lottie.utils.d;
import com.airbnb.lottie.utils.h;
import com.alibaba.fastjson.asm.Opcodes;
import java.io.IOException;
import java.util.Map;

/* compiled from: ImageAssetManager */
public class b {
    private static final Object a = new Object();
    @Nullable
    private final Context b;
    private final String c;
    @Nullable
    private a0 d;
    private final Map<String, f0> e;

    public b(Drawable.Callback callback, String imagesFolder, a0 delegate, Map<String, f0> imageAssets) {
        if (TextUtils.isEmpty(imagesFolder) || imagesFolder.charAt(imagesFolder.length() - 1) == '/') {
            this.c = imagesFolder;
        } else {
            this.c = imagesFolder + '/';
        }
        this.e = imageAssets;
        d(delegate);
        if (!(callback instanceof View)) {
            this.b = null;
        } else {
            this.b = ((View) callback).getContext().getApplicationContext();
        }
    }

    public void d(@Nullable a0 assetDelegate) {
        this.d = assetDelegate;
    }

    @Nullable
    public Bitmap a(String id) {
        f0 asset = this.e.get(id);
        if (asset == null) {
            return null;
        }
        Bitmap bitmap = asset.a();
        if (bitmap != null) {
            return bitmap;
        }
        a0 a0Var = this.d;
        if (a0Var != null) {
            Bitmap bitmap2 = a0Var.a(asset);
            if (bitmap2 != null) {
                c(id, bitmap2);
            }
            return bitmap2;
        }
        Context context = this.b;
        if (context == null) {
            return null;
        }
        String filename = asset.b();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = true;
        opts.inDensity = Opcodes.IF_ICMPNE;
        if (!filename.startsWith("data:") || filename.indexOf("base64,") <= 0) {
            try {
                if (!TextUtils.isEmpty(this.c)) {
                    AssetManager assets = context.getAssets();
                    try {
                        Bitmap bitmap3 = BitmapFactory.decodeStream(assets.open(this.c + filename), (Rect) null, opts);
                        if (bitmap3 != null) {
                            return c(id, h.l(bitmap3, asset.e(), asset.c()));
                        }
                        d.c("Decoded image `" + id + "` is null.");
                        return null;
                    } catch (IllegalArgumentException e2) {
                        d.d("Unable to decode image `" + id + "`.", e2);
                        return null;
                    }
                } else {
                    throw new IllegalStateException("You must set an images folder before loading an image. Set it with LottieComposition#setImagesFolder or LottieDrawable#setImagesFolder");
                }
            } catch (IOException e3) {
                d.d("Unable to open asset.", e3);
                return null;
            }
        } else {
            try {
                byte[] data = Base64.decode(filename.substring(filename.indexOf(44) + 1), 0);
                return c(id, BitmapFactory.decodeByteArray(data, 0, data.length, opts));
            } catch (IllegalArgumentException e4) {
                d.d("data URL did not have correct base64 format.", e4);
                return null;
            }
        }
    }

    public boolean b(Context context) {
        return (context == null && this.b == null) || this.b.equals(context);
    }

    private Bitmap c(String key, @Nullable Bitmap bitmap) {
        synchronized (a) {
            this.e.get(key).f(bitmap);
        }
        return bitmap;
    }
}
