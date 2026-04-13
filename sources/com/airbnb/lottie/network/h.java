package com.airbnb.lottie.network;

import android.content.Context;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieResult;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.d0;
import com.airbnb.lottie.k0;
import com.airbnb.lottie.utils.d;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: NetworkFetcher */
public class h {
    @Nullable
    private final g a;
    @NonNull
    private final f b;

    public h(@Nullable g networkCache, @NonNull f fetcher) {
        this.a = networkCache;
        this.b = fetcher;
    }

    @WorkerThread
    @NonNull
    public k0<c0> c(Context context, @NonNull String url, @Nullable String cacheKey) {
        c0 result = a(context, url, cacheKey);
        if (result != null) {
            return new k0<>(result);
        }
        d.a("Animation for " + url + " not found in cache. Fetching from network.");
        return b(context, url, cacheKey);
    }

    @WorkerThread
    @Nullable
    private c0 a(Context context, @NonNull String url, @Nullable String cacheKey) {
        g gVar;
        Pair<c, InputStream> a2;
        LottieResult<LottieComposition> result;
        if (cacheKey == null || (gVar = this.a) == null || (a2 = gVar.a(url)) == null) {
            return null;
        }
        c extension = (c) a2.first;
        InputStream inputStream = (InputStream) a2.second;
        if (extension == c.ZIP) {
            result = d0.r(context, new ZipInputStream(inputStream), cacheKey);
        } else {
            result = d0.h(inputStream, cacheKey);
        }
        if (result.b() != null) {
            return result.b();
        }
        return null;
    }

    @WorkerThread
    @NonNull
    private k0<c0> b(Context context, @NonNull String url, @Nullable String cacheKey) {
        d.a("Fetching " + url);
        d fetchResult = null;
        try {
            fetchResult = this.b.a(url);
            if (fetchResult.h0()) {
                LottieResult<LottieComposition> result = d(context, url, fetchResult.Y(), fetchResult.d(), cacheKey);
                StringBuilder sb = new StringBuilder();
                sb.append("Completed fetch from network. Success: ");
                sb.append(result.b() != null);
                d.a(sb.toString());
                try {
                    fetchResult.close();
                } catch (IOException e) {
                    d.d("LottieFetchResult close failed ", e);
                }
                return result;
            }
            k0<c0> k0Var = new k0<>((Throwable) new IllegalArgumentException(fetchResult.p()));
            try {
                fetchResult.close();
            } catch (IOException e2) {
                d.d("LottieFetchResult close failed ", e2);
            }
            return k0Var;
        } catch (Exception e3) {
            k0<c0> k0Var2 = new k0<>((Throwable) e3);
            if (fetchResult != null) {
                try {
                    fetchResult.close();
                } catch (IOException e4) {
                    d.d("LottieFetchResult close failed ", e4);
                }
            }
            return k0Var2;
        } catch (Throwable th) {
            if (fetchResult != null) {
                try {
                    fetchResult.close();
                } catch (IOException e5) {
                    d.d("LottieFetchResult close failed ", e5);
                }
            }
            throw th;
        }
    }

    @NonNull
    private k0<c0> d(Context context, @NonNull String url, @NonNull InputStream inputStream, @Nullable String contentType, @Nullable String cacheKey) {
        LottieResult<LottieComposition> result;
        c extension;
        g gVar;
        if (contentType == null) {
            contentType = "application/json";
        }
        if (contentType.contains("application/zip") || contentType.contains("application/x-zip") || contentType.contains("application/x-zip-compressed") || url.split("\\?")[0].endsWith(".lottie")) {
            d.a("Handling zip response.");
            extension = c.ZIP;
            result = f(context, url, inputStream, cacheKey);
        } else {
            d.a("Received json response.");
            extension = c.JSON;
            result = e(url, inputStream, cacheKey);
        }
        if (!(cacheKey == null || result.b() == null || (gVar = this.a) == null)) {
            gVar.f(url, extension);
        }
        return result;
    }

    @NonNull
    private k0<c0> f(Context context, @NonNull String url, @NonNull InputStream inputStream, @Nullable String cacheKey) {
        g gVar;
        if (cacheKey == null || (gVar = this.a) == null) {
            return d0.r(context, new ZipInputStream(inputStream), (String) null);
        }
        return d0.r(context, new ZipInputStream(new FileInputStream(gVar.g(url, inputStream, c.ZIP))), url);
    }

    @NonNull
    private k0<c0> e(@NonNull String url, @NonNull InputStream inputStream, @Nullable String cacheKey) {
        g gVar;
        if (cacheKey == null || (gVar = this.a) == null) {
            return d0.h(inputStream, (String) null);
        }
        return d0.h(new FileInputStream(gVar.g(url, inputStream, c.JSON).getAbsolutePath()), url);
    }
}
