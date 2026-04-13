package com.didichuxing.doraemonkit.kit.network.okhttp.interceptor;

import android.text.TextUtils;
import com.didichuxing.doraemonkit.aop.DokitPluginConfig;
import com.didichuxing.doraemonkit.config.PerformanceSpInfoConfig;
import com.didichuxing.doraemonkit.kit.largepicture.LargePictureManager;
import com.didichuxing.doraemonkit.kit.network.okhttp.InterceptorUtil;
import okhttp3.d0;
import okhttp3.w;
import org.jetbrains.annotations.NotNull;

public class LargePictureInterceptor implements w {
    public static final String TAG = "LargePictureInterceptor";

    @NotNull
    public d0 intercept(w.a chain) {
        d0 response = chain.a(chain.g());
        if (DokitPluginConfig.SWITCH_BIG_IMG && InterceptorUtil.isImg(response.n("Content-Type")) && PerformanceSpInfoConfig.isLargeImgOpen()) {
            processResponse(response);
        }
        return response;
    }

    private void processResponse(d0 response) {
        String field = response.n("Content-Length");
        if (!TextUtils.isEmpty(field)) {
            LargePictureManager.getInstance().process(response.J().l().toString(), Integer.parseInt(field));
        }
    }
}
