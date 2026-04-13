package com.didichuxing.doraemonkit.aop.bigimg.glide;

import com.blankj.utilcode.util.ReflectUtils;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.h;
import java.util.ArrayList;
import java.util.List;

public class GlideHook {
    public static void proxy(Object singleRequest) {
        List<RequestListener> requestListeners = null;
        try {
            if (singleRequest instanceof h) {
                requestListeners = (List) ReflectUtils.g(singleRequest).b("requestListeners").d();
            }
            if (requestListeners == null) {
                requestListeners = new ArrayList<>();
                requestListeners.add(new DokitGlideRequestListener());
            } else {
                requestListeners.add(new DokitGlideRequestListener());
            }
            if (singleRequest instanceof h) {
                ReflectUtils.g(singleRequest).c("requestListeners", requestListeners);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
