package com.didichuxing.doraemonkit.aop.bigimg.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.f;
import com.blankj.utilcode.util.l;
import com.bumptech.glide.load.a;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.e;
import com.bumptech.glide.request.target.j;
import com.didichuxing.doraemonkit.config.PerformanceSpInfoConfig;
import com.didichuxing.doraemonkit.kit.largepicture.LargePictureManager;

public class DokitGlideRequestListener<R> implements e<R> {
    private static final String TAG = "DokitGlideListener";

    public boolean onLoadFailed(@Nullable GlideException e, Object model, j<R> jVar, boolean isFirstResource) {
        return false;
    }

    public boolean onResourceReady(R resource, Object model, j<R> jVar, a dataSource, boolean isFirstResource) {
        R r = resource;
        try {
            if (!PerformanceSpInfoConfig.isLargeImgOpen()) {
                return false;
            }
            if (r instanceof Bitmap) {
                Bitmap bitmap = (Bitmap) r;
                LargePictureManager.getInstance().saveImageInfo(model.toString(), f.b((long) bitmap.getByteCount(), 1048576), bitmap.getWidth(), bitmap.getHeight(), "Glide");
                return false;
            } else if (!(r instanceof BitmapDrawable)) {
                return false;
            } else {
                Bitmap bitmap2 = l.a((BitmapDrawable) r);
                LargePictureManager.getInstance().saveImageInfo(model.toString(), f.b((long) bitmap2.getByteCount(), 1048576), bitmap2.getWidth(), bitmap2.getHeight(), "Glide");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
