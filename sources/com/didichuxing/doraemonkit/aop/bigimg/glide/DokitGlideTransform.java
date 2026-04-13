package com.didichuxing.doraemonkit.aop.bigimg.glide;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.ReflectUtils;
import com.bumptech.glide.h;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.m;
import com.didichuxing.doraemonkit.config.PerformanceSpInfoConfig;
import com.didichuxing.doraemonkit.kit.largepicture.LargePictureManager;
import java.security.MessageDigest;

public class DokitGlideTransform implements m<Bitmap> {
    private static final String ID = "com.didichuxing.doraemonkit.aop.bigimg.glide.DokitGlideTransform";
    private static final byte[] ID_BYTES = ID.getBytes(f.a);
    private static final String TAG = "DokitGlideTransform";
    private Object mRequestBuilder;
    private m mWrap;

    public DokitGlideTransform(Object mRequestBuilder2, Object transformation) {
        this.mRequestBuilder = mRequestBuilder2;
        if (transformation instanceof m) {
            this.mWrap = (m) transformation;
        }
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        m mVar = this.mWrap;
        if (mVar != null) {
            mVar.updateDiskCacheKey(messageDigest);
        } else {
            messageDigest.update(ID_BYTES);
        }
    }

    public boolean equals(Object o) {
        m mVar = this.mWrap;
        if (mVar != null) {
            return mVar.equals(o);
        }
        return false;
    }

    public int hashCode() {
        m mVar = this.mWrap;
        if (mVar != null) {
            return mVar.hashCode();
        }
        return 0;
    }

    @NonNull
    public t<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
        String url;
        try {
            m mVar = this.mWrap;
            if (mVar != null) {
                resource = mVar.transform(context, resource, outWidth, outHeight);
            }
            if (!PerformanceSpInfoConfig.isLargeImgOpen()) {
                return resource;
            }
            String url2 = "";
            Object obj = this.mRequestBuilder;
            if (obj instanceof h) {
                if (ReflectUtils.g(obj).b("model").d() instanceof String) {
                    url = (String) ReflectUtils.g(this.mRequestBuilder).b("model").d();
                } else if (ReflectUtils.g(this.mRequestBuilder).b("model").d() instanceof Integer) {
                    url = "" + ReflectUtils.g(this.mRequestBuilder).b("model").d();
                }
                Bitmap bitmap = resource.get();
                LargePictureManager.getInstance().saveImageInfo(url, com.blankj.utilcode.util.f.b((long) bitmap.getByteCount(), 1048576), bitmap.getWidth(), bitmap.getHeight(), "Glide");
                return resource;
            }
            url = url2;
            Bitmap bitmap2 = resource.get();
            LargePictureManager.getInstance().saveImageInfo(url, com.blankj.utilcode.util.f.b((long) bitmap2.getByteCount(), 1048576), bitmap2.getWidth(), bitmap2.getHeight(), "Glide");
            return resource;
        } catch (Exception e) {
            m mVar2 = this.mWrap;
            if (mVar2 != null) {
                return mVar2.transform(context, resource, outWidth, outHeight);
            }
            return resource;
        }
    }
}
