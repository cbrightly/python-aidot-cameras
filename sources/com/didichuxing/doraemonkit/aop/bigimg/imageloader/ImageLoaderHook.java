package com.didichuxing.doraemonkit.aop.bigimg.imageloader;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderHook {
    public static ImageLoadingListener proxy(ImageLoadingListener imageLoadingListener) {
        return new DokitImageLoadingListener(imageLoadingListener);
    }
}
