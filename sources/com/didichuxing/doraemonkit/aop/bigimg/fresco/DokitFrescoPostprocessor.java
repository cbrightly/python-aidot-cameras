package com.didichuxing.doraemonkit.aop.bigimg.fresco;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.f;
import com.didichuxing.doraemonkit.config.PerformanceSpInfoConfig;
import com.didichuxing.doraemonkit.kit.largepicture.LargePictureManager;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.nativecode.Bitmaps;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.Postprocessor;

public class DokitFrescoPostprocessor implements Postprocessor {
    private static final String TAG = "DokitPostprocessor";
    @Nullable
    private Postprocessor mOriginalPostprocessor;
    private Uri mUri;

    public DokitFrescoPostprocessor(Uri uri, Postprocessor postprocessor) {
        this.mOriginalPostprocessor = postprocessor;
        this.mUri = uri;
    }

    public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
        try {
            if (PerformanceSpInfoConfig.isLargeImgOpen()) {
                LargePictureManager.getInstance().saveImageInfo(this.mUri.toString(), f.b((long) sourceBitmap.getByteCount(), 1048576), sourceBitmap.getWidth(), sourceBitmap.getHeight(), "Fresco");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Exception e2 = this.mOriginalPostprocessor;
        if (e2 != null) {
            return e2.process(sourceBitmap, bitmapFactory);
        }
        Bitmap.Config sourceBitmapConfig = sourceBitmap.getConfig();
        CloseableReference<Bitmap> destBitmapRef = bitmapFactory.createBitmapInternal(sourceBitmap.getWidth(), sourceBitmap.getHeight(), sourceBitmapConfig != null ? sourceBitmapConfig : BasePostprocessor.FALLBACK_BITMAP_CONFIGURATION);
        try {
            process((Bitmap) destBitmapRef.get(), sourceBitmap);
            return CloseableReference.cloneOrNull(destBitmapRef);
        } finally {
            CloseableReference.closeSafely(destBitmapRef);
        }
    }

    public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
        internalCopyBitmap(destBitmap, sourceBitmap);
        process(destBitmap);
    }

    public void process(Bitmap bitmap) {
    }

    private static void internalCopyBitmap(Bitmap destBitmap, Bitmap sourceBitmap) {
        if (destBitmap.getConfig() == sourceBitmap.getConfig()) {
            Bitmaps.copyBitmap(destBitmap, sourceBitmap);
        } else {
            new Canvas(destBitmap).drawBitmap(sourceBitmap, 0.0f, 0.0f, (Paint) null);
        }
    }

    public String getName() {
        Postprocessor postprocessor = this.mOriginalPostprocessor;
        if (postprocessor != null) {
            return postprocessor.getName();
        }
        return "DoKit&Fresco&DokitPostprocessor";
    }

    @Nullable
    public CacheKey getPostprocessorCacheKey() {
        Postprocessor postprocessor = this.mOriginalPostprocessor;
        if (postprocessor != null) {
            return postprocessor.getPostprocessorCacheKey();
        }
        return null;
    }
}
