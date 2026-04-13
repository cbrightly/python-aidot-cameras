package com.downloader;

import android.content.Context;
import com.downloader.internal.a;
import com.downloader.internal.b;

/* compiled from: PRDownloader */
public class g {
    public static void d(Context context, h config) {
        a.d().g(context, config);
        b.g();
    }

    public static com.downloader.request.b c(String url, String dirPath, String fileName) {
        return new com.downloader.request.b(url, dirPath, fileName);
    }

    public static void a() {
        b.e().b();
    }

    public static void b(int days) {
        com.downloader.utils.a.b(days);
    }
}
