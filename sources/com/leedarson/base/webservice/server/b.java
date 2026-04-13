package com.leedarson.base.webservice.server;

import androidx.annotation.NonNull;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.yanzhenjie.andserver.http.c;
import com.yanzhenjie.andserver.http.d;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import timber.log.a;

/* compiled from: AppExceptionResolver */
public class b implements com.yanzhenjie.andserver.framework.b {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void a(@NonNull c request, @NonNull d response, @NonNull Throwable e) {
        if (!PatchProxy.proxy(new Object[]{request, response, e}, this, changeQuickRedirect, false, 956, new Class[]{c.class, d.class, Throwable.class}, Void.TYPE).isSupported) {
            a.g("LdsCore").d(e);
            e.printStackTrace();
            String redirect = "/";
            String[] split = request.t().split("[?]");
            if (e instanceof IllegalArgumentException) {
                if (split.length > 1) {
                    try {
                        redirect = split[0] + "?" + URLEncoder.encode(URLEncoder.encode(split[1], "utf-8"), "utf-8");
                    } catch (UnsupportedEncodingException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (split.length > 1) {
                redirect = "/?" + split[1];
            }
            response.c(redirect);
        }
    }
}
