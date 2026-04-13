package com.didichuxing.doraemonkit.aop.bigimg.glide;

import android.graphics.Bitmap;
import com.bumptech.glide.load.m;

public class GlideTransformHook {
    public static m<Bitmap> transform(Object baseRequestOptions, Object transformation) {
        return new DokitGlideTransform(baseRequestOptions, transformation);
    }
}
