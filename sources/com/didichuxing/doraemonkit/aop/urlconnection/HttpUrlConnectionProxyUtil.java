package com.didichuxing.doraemonkit.aop.urlconnection;

import android.net.Uri;
import com.didichuxing.doraemonkit.aop.urlconnection.ObsoleteUrlFactory;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.DoraemonInterceptor;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.DoraemonWeakNetworkInterceptor;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.LargePictureInterceptor;
import com.didichuxing.doraemonkit.kit.network.okhttp.interceptor.MockInterceptor;
import java.net.URL;
import java.net.URLConnection;
import okhttp3.v;
import okhttp3.w;
import okhttp3.z;
import org.apache.http.l;

public class HttpUrlConnectionProxyUtil {
    private static String[] hosts = {"amap.com"};

    public static URLConnection proxy(URLConnection urlConnection) {
        try {
            if (isIgnore(v.n(urlConnection.getURL().toString()).j())) {
                return urlConnection;
            }
            return createOkHttpURLConnection(urlConnection);
        } catch (Exception e) {
            e.printStackTrace();
            return urlConnection;
        }
    }

    private static URLConnection createOkHttpURLConnection(URLConnection urlConnection) {
        URL url = new URL(encodeUrl(urlConnection.getURL().toString()));
        String protocol = url.getProtocol().toLowerCase();
        if (protocol.equalsIgnoreCase(l.DEFAULT_SCHEME_NAME)) {
            return new ObsoleteUrlFactory.OkHttpURLConnection(url, OkhttpClientUtil.INSTANCE.getOkhttpClient());
        }
        if (protocol.equalsIgnoreCase("https")) {
            return new ObsoleteUrlFactory.OkHttpsURLConnection(url, OkhttpClientUtil.INSTANCE.getOkhttpClient());
        }
        return urlConnection;
    }

    public static String encodeUrl(String url) {
        return Uri.encode(url, "-![.:/,%?&=]");
    }

    public static String decodeUrl(String url) {
        return Uri.decode(url);
    }

    private static void addInterceptor(z.a builder) {
        for (w interceptor : builder.N()) {
            if (interceptor instanceof MockInterceptor) {
                return;
            }
        }
        builder.a(new MockInterceptor()).a(new LargePictureInterceptor()).a(new DoraemonInterceptor()).b(new DoraemonWeakNetworkInterceptor());
    }

    private static boolean isIgnore(String host) {
        for (String jumpHost : hosts) {
            if (host.contains(jumpHost)) {
                return true;
            }
        }
        return false;
    }
}
