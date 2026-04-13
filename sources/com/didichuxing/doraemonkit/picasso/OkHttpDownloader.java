package com.didichuxing.doraemonkit.picasso;

import android.content.Context;
import android.net.Uri;
import com.didichuxing.doraemonkit.picasso.Downloader;
import com.squareup.okhttp.c;
import com.squareup.okhttp.d;
import com.squareup.okhttp.t;
import com.squareup.okhttp.v;
import com.squareup.okhttp.x;
import com.squareup.okhttp.y;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpDownloader implements Downloader {
    private final t client;

    private static t defaultOkHttpClient() {
        t client2 = new t();
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        client2.F(15000, timeUnit);
        client2.G(20000, timeUnit);
        client2.H(20000, timeUnit);
        return client2;
    }

    public OkHttpDownloader(Context context) {
        this(Utils.createDefaultCacheDir(context));
    }

    public OkHttpDownloader(File cacheDir) {
        this(cacheDir, Utils.calculateDiskCacheSize(cacheDir));
    }

    public OkHttpDownloader(Context context, long maxSize) {
        this(Utils.createDefaultCacheDir(context), maxSize);
    }

    public OkHttpDownloader(File cacheDir, long maxSize) {
        this(defaultOkHttpClient());
        this.client.D(new c(cacheDir, maxSize));
    }

    public OkHttpDownloader(t client2) {
        this.client = client2;
    }

    /* access modifiers changed from: protected */
    public final t getClient() {
        return this.client;
    }

    public Downloader.Response load(Uri uri, int networkPolicy) {
        d cacheControl = null;
        if (networkPolicy != 0) {
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                cacheControl = d.b;
            } else {
                d.b builder = new d.b();
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    builder.c();
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    builder.d();
                }
                cacheControl = builder.a();
            }
        }
        v.b builder2 = new v.b().n(uri.toString());
        if (cacheControl != null) {
            builder2.h(cacheControl);
        }
        x response = this.client.C(builder2.g()).b();
        int responseCode = response.o();
        if (responseCode < 300) {
            boolean fromCache = response.m() != null;
            y responseBody = response.k();
            return new Downloader.Response(responseBody.a(), fromCache, responseBody.c());
        }
        response.k().close();
        throw new Downloader.ResponseException(responseCode + " " + response.t(), networkPolicy, responseCode);
    }

    public void shutdown() {
        c cache = this.client.d();
        if (cache != null) {
            try {
                cache.j();
            } catch (IOException e) {
            }
        }
    }
}
