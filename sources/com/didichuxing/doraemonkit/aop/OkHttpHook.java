package com.didichuxing.doraemonkit.aop;

import android.util.Log;
import com.blankj.utilcode.util.ReflectUtils;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.DoraemonInterceptor;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.DoraemonWeakNetworkInterceptor;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.LargePictureInterceptor;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.MockInterceptor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import okhttp3.w;
import okhttp3.z;

public class OkHttpHook {
    private static boolean IS_INSTALL = false;
    public static List<w> globalInterceptors = new ArrayList();
    public static List<w> globalNetworkInterceptors = new ArrayList();

    public static void installInterceptor() {
        if (!IS_INSTALL) {
            try {
                globalInterceptors.add(new MockInterceptor());
                globalInterceptors.add(new LargePictureInterceptor());
                globalInterceptors.add(new DoraemonInterceptor());
                globalNetworkInterceptors.add(new DoraemonWeakNetworkInterceptor());
                IS_INSTALL = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void performOkhttpOneParamBuilderInit(Object builder, Object okHttpClient) {
        try {
            if (builder instanceof z.a) {
                z.a localBuild = (z.a) builder;
                localBuild.N().addAll(globalInterceptors);
                localBuild.O().addAll(globalNetworkInterceptors);
                List<w> removeDuplicate = removeDuplicate(localBuild.N());
                List<w> removeDuplicate2 = removeDuplicate(localBuild.O());
                ReflectUtils.g(localBuild).c("interceptors", removeDuplicate);
                ReflectUtils.g(localBuild).c("networkInterceptors", removeDuplicate2);
            }
        } catch (Exception e) {
            Log.i("Doraemon", "" + e.getMessage());
        }
    }

    private static List<w> removeDuplicate(List<w> list) {
        LinkedHashSet h = new LinkedHashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
}
