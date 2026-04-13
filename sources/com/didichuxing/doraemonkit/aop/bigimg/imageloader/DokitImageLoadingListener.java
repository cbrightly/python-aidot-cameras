package com.didichuxing.doraemonkit.aop.bigimg.imageloader;

import android.graphics.Bitmap;
import android.view.View;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.f;
import com.didichuxing.doraemonkit.config.PerformanceSpInfoConfig;
import com.didichuxing.doraemonkit.kit.largepicture.LargePictureManager;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DokitImageLoadingListener implements ImageLoadingListener {
    private static final String TAG = "DokitImageLoadingListener";
    @Nullable
    private ImageLoadingListener mOriginalImageLoadingListener;

    public DokitImageLoadingListener(ImageLoadingListener imageLoadingListener) {
        this.mOriginalImageLoadingListener = imageLoadingListener;
    }

    public void onLoadingStarted(String imageUri, View view) {
        ImageLoadingListener imageLoadingListener = this.mOriginalImageLoadingListener;
        if (imageLoadingListener != null) {
            imageLoadingListener.onLoadingStarted(imageUri, view);
        }
    }

    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        ImageLoadingListener imageLoadingListener = this.mOriginalImageLoadingListener;
        if (imageLoadingListener != null) {
            imageLoadingListener.onLoadingFailed(imageUri, view, failReason);
        }
    }

    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        try {
            if (PerformanceSpInfoConfig.isLargeImgOpen()) {
                String str = imageUri;
                LargePictureManager.getInstance().saveImageInfo(str, f.b((long) loadedImage.getByteCount(), 1048576), loadedImage.getWidth(), loadedImage.getHeight(), "ImageLoader");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Exception e2 = this.mOriginalImageLoadingListener;
        if (e2 != null) {
            e2.onLoadingComplete(imageUri, view, loadedImage);
        }
    }

    public void onLoadingCancelled(String imageUri, View view) {
        ImageLoadingListener imageLoadingListener = this.mOriginalImageLoadingListener;
        if (imageLoadingListener != null) {
            imageLoadingListener.onLoadingCancelled(imageUri, view);
        }
    }
}
