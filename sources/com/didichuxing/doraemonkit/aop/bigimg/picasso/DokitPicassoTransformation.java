package com.didichuxing.doraemonkit.aop.bigimg.picasso;

import android.graphics.Bitmap;
import android.net.Uri;
import com.blankj.utilcode.util.f;
import com.didichuxing.doraemonkit.config.PerformanceSpInfoConfig;
import com.didichuxing.doraemonkit.kit.largepicture.LargePictureManager;
import com.squareup.picasso.Transformation;

public class DokitPicassoTransformation implements Transformation {
    private static final String TAG = "DokitTransformation";
    private int mResourceId;
    private Uri mUri;

    public DokitPicassoTransformation(Uri uri, int resourceId) {
        this.mUri = uri;
        this.mResourceId = resourceId;
    }

    public Bitmap transform(Bitmap source) {
        try {
            if (PerformanceSpInfoConfig.isLargeImgOpen()) {
                if (this.mUri != null) {
                    LargePictureManager.getInstance().saveImageInfo(this.mUri.toString(), f.b((long) source.getByteCount(), 1048576), source.getWidth(), source.getHeight(), "Picasso");
                } else {
                    double imgSize = f.b((long) source.getByteCount(), 1048576);
                    LargePictureManager instance = LargePictureManager.getInstance();
                    instance.saveImageInfo("" + this.mResourceId, imgSize, source.getWidth(), source.getHeight(), "Picasso");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }

    public String key() {
        return "Dokit&Picasso&LargeBitmapTransformation";
    }
}
