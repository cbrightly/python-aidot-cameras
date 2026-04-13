package com.airbnb.lottie;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.annotation.WorkerThread;
import com.airbnb.lottie.model.g;
import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.parser.w;
import com.airbnb.lottie.utils.d;
import com.airbnb.lottie.utils.h;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipInputStream;
import okio.e;
import okio.p;

/* compiled from: LottieCompositionFactory */
public class d0 {
    private static final Map<String, l0<c0>> a = new HashMap();
    private static final Set<m0> b = new HashSet();
    private static final byte[] c = {80, 75, 3, 4};

    public static l0<c0> p(Context context, String url) {
        return q(context, url, "url_" + url);
    }

    public static l0<c0> q(Context context, String url, @Nullable String cacheKey) {
        return a(cacheKey, new j(context, url, cacheKey));
    }

    static /* synthetic */ k0 B(Context context, String url, String cacheKey) {
        LottieResult<LottieComposition> result = b0.e(context).c(context, url, cacheKey);
        if (!(cacheKey == null || result.b() == null)) {
            g.b().c(cacheKey, result.b());
        }
        return result;
    }

    public static l0<c0> c(Context context, String fileName) {
        return d(context, fileName, "asset_" + fileName);
    }

    public static l0<c0> d(Context context, String fileName, @Nullable String cacheKey) {
        return a(cacheKey, new e(context.getApplicationContext(), fileName, cacheKey));
    }

    @WorkerThread
    public static k0<c0> e(Context context, String fileName) {
        return f(context, fileName, "asset_" + fileName);
    }

    @WorkerThread
    public static k0<c0> f(Context context, String fileName, @Nullable String cacheKey) {
        try {
            if (!fileName.endsWith(".zip")) {
                if (!fileName.endsWith(".lottie")) {
                    return h(context.getAssets().open(fileName), cacheKey);
                }
            }
            return r(context, new ZipInputStream(context.getAssets().open(fileName)), cacheKey);
        } catch (IOException e) {
            return new k0<>((Throwable) e);
        }
    }

    public static l0<c0> l(Context context, @RawRes int rawRes) {
        return m(context, rawRes, D(context, rawRes));
    }

    public static l0<c0> m(Context context, @RawRes int rawRes, @Nullable String cacheKey) {
        return a(cacheKey, new f(new WeakReference<>(context), context.getApplicationContext(), rawRes, cacheKey));
    }

    static /* synthetic */ k0 A(WeakReference contextRef, Context appContext, int rawRes, String cacheKey) {
        Context originalContext = (Context) contextRef.get();
        return o(originalContext != null ? originalContext : appContext, rawRes, cacheKey);
    }

    @WorkerThread
    public static k0<c0> n(Context context, @RawRes int rawRes) {
        return o(context, rawRes, D(context, rawRes));
    }

    @WorkerThread
    public static k0<c0> o(Context context, @RawRes int rawRes, @Nullable String cacheKey) {
        try {
            e source = p.d(p.l(context.getResources().openRawResource(rawRes)));
            if (u(source).booleanValue()) {
                return r(context, new ZipInputStream(source.Y0()), cacheKey);
            }
            return h(source.Y0(), cacheKey);
        } catch (Resources.NotFoundException e) {
            return new k0<>((Throwable) e);
        }
    }

    private static String D(Context context, @RawRes int resId) {
        StringBuilder sb = new StringBuilder();
        sb.append("rawRes");
        sb.append(t(context) ? "_night_" : "_day_");
        sb.append(resId);
        return sb.toString();
    }

    private static boolean t(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static l0<c0> g(InputStream stream, @Nullable String cacheKey) {
        return a(cacheKey, new i(stream, cacheKey));
    }

    @WorkerThread
    public static k0<c0> h(InputStream stream, @Nullable String cacheKey) {
        return i(stream, cacheKey, true);
    }

    @WorkerThread
    private static k0<c0> i(InputStream stream, @Nullable String cacheKey, boolean close) {
        try {
            return j(a.t(p.d(p.l(stream))), cacheKey);
        } finally {
            if (close) {
                h.c(stream);
            }
        }
    }

    @WorkerThread
    public static k0<c0> j(a reader, @Nullable String cacheKey) {
        return k(reader, cacheKey, true);
    }

    private static k0<c0> k(a reader, @Nullable String cacheKey, boolean close) {
        try {
            c0 composition = w.a(reader);
            if (cacheKey != null) {
                g.b().c(cacheKey, composition);
            }
            k0<c0> k0Var = new k0<>(composition);
            if (close) {
                h.c(reader);
            }
            return k0Var;
        } catch (Exception e) {
            k0<c0> k0Var2 = new k0<>((Throwable) e);
            if (close) {
                h.c(reader);
            }
            return k0Var2;
        } catch (Throwable th) {
            if (close) {
                h.c(reader);
            }
            throw th;
        }
    }

    @WorkerThread
    public static k0<c0> r(@Nullable Context context, ZipInputStream inputStream, @Nullable String cacheKey) {
        try {
            return s(context, inputStream, cacheKey);
        } finally {
            h.c(inputStream);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:0x012e A[Catch:{ IOException -> 0x0169 }] */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.airbnb.lottie.k0<com.airbnb.lottie.c0> s(android.content.Context r17, java.util.zip.ZipInputStream r18, @androidx.annotation.Nullable java.lang.String r19) {
        /*
            r1 = r19
            r2 = 0
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r3 = r0
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r4 = r0
            java.util.zip.ZipEntry r0 = r18.getNextEntry()     // Catch:{ IOException -> 0x02e4 }
            r5 = r2
            r2 = r0
        L_0x0015:
            r6 = 0
            r0 = 1
            r7 = 0
            if (r2 == 0) goto L_0x0171
            java.lang.String r8 = r2.getName()     // Catch:{ IOException -> 0x016b }
            java.lang.String r9 = "__MACOSX"
            boolean r9 = r8.contains(r9)     // Catch:{ IOException -> 0x016b }
            if (r9 == 0) goto L_0x002d
            r18.closeEntry()     // Catch:{ IOException -> 0x016b }
            r14 = r18
            goto L_0x0162
        L_0x002d:
            java.lang.String r9 = r2.getName()     // Catch:{ IOException -> 0x016b }
            java.lang.String r10 = "manifest.json"
            boolean r9 = r9.equalsIgnoreCase(r10)     // Catch:{ IOException -> 0x016b }
            if (r9 == 0) goto L_0x0040
            r18.closeEntry()     // Catch:{ IOException -> 0x016b }
            r14 = r18
            goto L_0x0162
        L_0x0040:
            java.lang.String r9 = r2.getName()     // Catch:{ IOException -> 0x016b }
            java.lang.String r10 = ".json"
            boolean r9 = r9.contains(r10)     // Catch:{ IOException -> 0x016b }
            if (r9 == 0) goto L_0x0068
            okio.e0 r0 = okio.p.l(r18)     // Catch:{ IOException -> 0x016b }
            okio.e r0 = okio.p.d(r0)     // Catch:{ IOException -> 0x016b }
            com.airbnb.lottie.parser.moshi.a r0 = com.airbnb.lottie.parser.moshi.a.t(r0)     // Catch:{ IOException -> 0x016b }
            com.airbnb.lottie.k0 r6 = k(r0, r6, r7)     // Catch:{ IOException -> 0x016b }
            java.lang.Object r6 = r6.b()     // Catch:{ IOException -> 0x016b }
            com.airbnb.lottie.c0 r6 = (com.airbnb.lottie.c0) r6     // Catch:{ IOException -> 0x016b }
            r0 = r6
            r14 = r18
            r5 = r0
            goto L_0x0162
        L_0x0068:
            java.lang.String r6 = ".png"
            boolean r6 = r8.contains(r6)     // Catch:{ IOException -> 0x016b }
            java.lang.String r9 = "/"
            if (r6 != 0) goto L_0x0150
            java.lang.String r6 = ".webp"
            boolean r6 = r8.contains(r6)     // Catch:{ IOException -> 0x016b }
            if (r6 != 0) goto L_0x0150
            java.lang.String r6 = ".jpg"
            boolean r6 = r8.contains(r6)     // Catch:{ IOException -> 0x016b }
            if (r6 != 0) goto L_0x0150
            java.lang.String r6 = ".jpeg"
            boolean r6 = r8.contains(r6)     // Catch:{ IOException -> 0x016b }
            if (r6 == 0) goto L_0x008e
            r14 = r18
            goto L_0x0152
        L_0x008e:
            java.lang.String r6 = ".ttf"
            boolean r6 = r8.contains(r6)     // Catch:{ IOException -> 0x016b }
            if (r6 != 0) goto L_0x00a6
            java.lang.String r6 = ".otf"
            boolean r6 = r8.contains(r6)     // Catch:{ IOException -> 0x016b }
            if (r6 == 0) goto L_0x009f
            goto L_0x00a6
        L_0x009f:
            r18.closeEntry()     // Catch:{ IOException -> 0x016b }
            r14 = r18
            goto L_0x0162
        L_0x00a6:
            java.lang.String[] r6 = r8.split(r9)     // Catch:{ IOException -> 0x016b }
            int r9 = r6.length     // Catch:{ IOException -> 0x016b }
            int r9 = r9 - r0
            r0 = r6[r9]     // Catch:{ IOException -> 0x016b }
            r9 = r0
            java.lang.String r0 = "\\."
            java.lang.String[] r0 = r9.split(r0)     // Catch:{ IOException -> 0x016b }
            r0 = r0[r7]     // Catch:{ IOException -> 0x016b }
            r10 = r0
            java.io.File r0 = new java.io.File     // Catch:{ IOException -> 0x016b }
            java.io.File r11 = r17.getCacheDir()     // Catch:{ IOException -> 0x016b }
            r0.<init>(r11, r9)     // Catch:{ IOException -> 0x016b }
            r11 = r0
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x016b }
            r0.<init>(r11)     // Catch:{ IOException -> 0x016b }
            r12 = r0
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ all -> 0x0100 }
            r0.<init>(r11)     // Catch:{ all -> 0x0100 }
            r13 = r0
            r0 = 4096(0x1000, float:5.74E-42)
            byte[] r0 = new byte[r0]     // Catch:{ all -> 0x00f0 }
        L_0x00d2:
            r14 = r18
            int r15 = r14.read(r0)     // Catch:{ all -> 0x00ee }
            r16 = r15
            r7 = -1
            if (r15 == r7) goto L_0x00e5
            r7 = r16
            r15 = 0
            r13.write(r0, r15, r7)     // Catch:{ all -> 0x00ee }
            r7 = 0
            goto L_0x00d2
        L_0x00e5:
            r7 = r16
            r13.flush()     // Catch:{ all -> 0x00ee }
            r13.close()     // Catch:{ all -> 0x00fe }
            goto L_0x0124
        L_0x00ee:
            r0 = move-exception
            goto L_0x00f3
        L_0x00f0:
            r0 = move-exception
            r14 = r18
        L_0x00f3:
            r7 = r0
            r13.close()     // Catch:{ all -> 0x00f8 }
            goto L_0x00fd
        L_0x00f8:
            r0 = move-exception
            r15 = r0
            r7.addSuppressed(r15)     // Catch:{ all -> 0x00fe }
        L_0x00fd:
            throw r7     // Catch:{ all -> 0x00fe }
        L_0x00fe:
            r0 = move-exception
            goto L_0x0103
        L_0x0100:
            r0 = move-exception
            r14 = r18
        L_0x0103:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0169 }
            r7.<init>()     // Catch:{ IOException -> 0x0169 }
            java.lang.String r13 = "Unable to save font "
            r7.append(r13)     // Catch:{ IOException -> 0x0169 }
            r7.append(r10)     // Catch:{ IOException -> 0x0169 }
            java.lang.String r13 = " to the temporary file: "
            r7.append(r13)     // Catch:{ IOException -> 0x0169 }
            r7.append(r9)     // Catch:{ IOException -> 0x0169 }
            java.lang.String r13 = ". "
            r7.append(r13)     // Catch:{ IOException -> 0x0169 }
            java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x0169 }
            com.airbnb.lottie.utils.d.d(r7, r0)     // Catch:{ IOException -> 0x0169 }
        L_0x0124:
            android.graphics.Typeface r0 = android.graphics.Typeface.createFromFile(r11)     // Catch:{ IOException -> 0x0169 }
            boolean r7 = r11.delete()     // Catch:{ IOException -> 0x0169 }
            if (r7 != 0) goto L_0x014b
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0169 }
            r7.<init>()     // Catch:{ IOException -> 0x0169 }
            java.lang.String r13 = "Failed to delete temp font file "
            r7.append(r13)     // Catch:{ IOException -> 0x0169 }
            java.lang.String r13 = r11.getAbsolutePath()     // Catch:{ IOException -> 0x0169 }
            r7.append(r13)     // Catch:{ IOException -> 0x0169 }
            java.lang.String r13 = "."
            r7.append(r13)     // Catch:{ IOException -> 0x0169 }
            java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x0169 }
            com.airbnb.lottie.utils.d.c(r7)     // Catch:{ IOException -> 0x0169 }
        L_0x014b:
            r4.put(r10, r0)     // Catch:{ IOException -> 0x0169 }
            goto L_0x0162
        L_0x0150:
            r14 = r18
        L_0x0152:
            java.lang.String[] r6 = r8.split(r9)     // Catch:{ IOException -> 0x0169 }
            int r7 = r6.length     // Catch:{ IOException -> 0x0169 }
            int r7 = r7 - r0
            r0 = r6[r7]     // Catch:{ IOException -> 0x0169 }
            android.graphics.Bitmap r7 = android.graphics.BitmapFactory.decodeStream(r18)     // Catch:{ IOException -> 0x0169 }
            r3.put(r0, r7)     // Catch:{ IOException -> 0x0169 }
        L_0x0162:
            java.util.zip.ZipEntry r0 = r18.getNextEntry()     // Catch:{ IOException -> 0x0169 }
            r2 = r0
            goto L_0x0015
        L_0x0169:
            r0 = move-exception
            goto L_0x016e
        L_0x016b:
            r0 = move-exception
            r14 = r18
        L_0x016e:
            r2 = r5
            goto L_0x02e7
        L_0x0171:
            r14 = r18
            if (r5 != 0) goto L_0x0183
            com.airbnb.lottie.k0 r0 = new com.airbnb.lottie.k0
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Unable to parse composition"
            r2.<init>(r6)
            r0.<init>((java.lang.Throwable) r2)
            return r0
        L_0x0183:
            java.util.Set r2 = r3.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x018b:
            boolean r7 = r2.hasNext()
            if (r7 == 0) goto L_0x01b9
            java.lang.Object r7 = r2.next()
            java.util.Map$Entry r7 = (java.util.Map.Entry) r7
            java.lang.Object r8 = r7.getKey()
            java.lang.String r8 = (java.lang.String) r8
            com.airbnb.lottie.f0 r8 = b(r5, r8)
            if (r8 == 0) goto L_0x01b8
            java.lang.Object r9 = r7.getValue()
            android.graphics.Bitmap r9 = (android.graphics.Bitmap) r9
            int r10 = r8.e()
            int r11 = r8.c()
            android.graphics.Bitmap r9 = com.airbnb.lottie.utils.h.l(r9, r10, r11)
            r8.f(r9)
        L_0x01b8:
            goto L_0x018b
        L_0x01b9:
            java.util.Set r2 = r4.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x01c1:
            boolean r7 = r2.hasNext()
            if (r7 == 0) goto L_0x0221
            java.lang.Object r7 = r2.next()
            java.util.Map$Entry r7 = (java.util.Map.Entry) r7
            r8 = 0
            java.util.Map r9 = r5.g()
            java.util.Collection r9 = r9.values()
            java.util.Iterator r9 = r9.iterator()
        L_0x01da:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x01ff
            java.lang.Object r10 = r9.next()
            com.airbnb.lottie.model.c r10 = (com.airbnb.lottie.model.c) r10
            java.lang.String r11 = r10.a()
            java.lang.Object r12 = r7.getKey()
            boolean r11 = r11.equals(r12)
            if (r11 == 0) goto L_0x01fe
            r8 = 1
            java.lang.Object r11 = r7.getValue()
            android.graphics.Typeface r11 = (android.graphics.Typeface) r11
            r10.e(r11)
        L_0x01fe:
            goto L_0x01da
        L_0x01ff:
            if (r8 != 0) goto L_0x0220
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Parsed font for "
            r9.append(r10)
            java.lang.Object r10 = r7.getKey()
            java.lang.String r10 = (java.lang.String) r10
            r9.append(r10)
            java.lang.String r10 = " however it was not found in the animation."
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            com.airbnb.lottie.utils.d.c(r9)
        L_0x0220:
            goto L_0x01c1
        L_0x0221:
            boolean r2 = r3.isEmpty()
            if (r2 == 0) goto L_0x028a
            java.util.Map r2 = r5.j()
            java.util.Set r2 = r2.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x0233:
            boolean r7 = r2.hasNext()
            if (r7 == 0) goto L_0x028a
            java.lang.Object r7 = r2.next()
            java.util.Map$Entry r7 = (java.util.Map.Entry) r7
            java.lang.Object r8 = r7.getValue()
            com.airbnb.lottie.f0 r8 = (com.airbnb.lottie.f0) r8
            if (r8 != 0) goto L_0x0248
            return r6
        L_0x0248:
            java.lang.String r9 = r8.b()
            android.graphics.BitmapFactory$Options r10 = new android.graphics.BitmapFactory$Options
            r10.<init>()
            r10.inScaled = r0
            r11 = 160(0xa0, float:2.24E-43)
            r10.inDensity = r11
            java.lang.String r11 = "data:"
            boolean r11 = r9.startsWith(r11)
            if (r11 == 0) goto L_0x0288
            java.lang.String r11 = "base64,"
            int r11 = r9.indexOf(r11)
            if (r11 <= 0) goto L_0x0288
            r11 = 44
            int r11 = r9.indexOf(r11)     // Catch:{ IllegalArgumentException -> 0x0281 }
            int r11 = r11 + r0
            java.lang.String r11 = r9.substring(r11)     // Catch:{ IllegalArgumentException -> 0x0281 }
            r12 = 0
            byte[] r11 = android.util.Base64.decode(r11, r12)     // Catch:{ IllegalArgumentException -> 0x0281 }
            int r13 = r11.length
            android.graphics.Bitmap r13 = android.graphics.BitmapFactory.decodeByteArray(r11, r12, r13, r10)
            r8.f(r13)
            goto L_0x0289
        L_0x0281:
            r0 = move-exception
            java.lang.String r2 = "data URL did not have correct base64 format."
            com.airbnb.lottie.utils.d.d(r2, r0)
            return r6
        L_0x0288:
            r12 = 0
        L_0x0289:
            goto L_0x0233
        L_0x028a:
            java.util.Map r0 = r5.j()
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0296:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x02d5
            java.lang.Object r2 = r0.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r6 = r2.getValue()
            com.airbnb.lottie.f0 r6 = (com.airbnb.lottie.f0) r6
            android.graphics.Bitmap r6 = r6.a()
            if (r6 != 0) goto L_0x02d4
            com.airbnb.lottie.k0 r0 = new com.airbnb.lottie.k0
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "There is no image for "
            r7.append(r8)
            java.lang.Object r8 = r2.getValue()
            com.airbnb.lottie.f0 r8 = (com.airbnb.lottie.f0) r8
            java.lang.String r8 = r8.b()
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            r0.<init>((java.lang.Throwable) r6)
            return r0
        L_0x02d4:
            goto L_0x0296
        L_0x02d5:
            if (r1 == 0) goto L_0x02de
            com.airbnb.lottie.model.g r0 = com.airbnb.lottie.model.g.b()
            r0.c(r1, r5)
        L_0x02de:
            com.airbnb.lottie.k0 r0 = new com.airbnb.lottie.k0
            r0.<init>(r5)
            return r0
        L_0x02e4:
            r0 = move-exception
            r14 = r18
        L_0x02e7:
            com.airbnb.lottie.k0 r5 = new com.airbnb.lottie.k0
            r5.<init>((java.lang.Throwable) r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.d0.s(android.content.Context, java.util.zip.ZipInputStream, java.lang.String):com.airbnb.lottie.k0");
    }

    private static Boolean u(e inputSource) {
        try {
            e peek = inputSource.peek();
            for (byte b2 : c) {
                if (peek.readByte() != b2) {
                    return false;
                }
            }
            peek.close();
            return true;
        } catch (NoSuchMethodError e) {
            return false;
        } catch (Exception e2) {
            d.b("Failed to check zip file header", e2);
            return false;
        }
    }

    @Nullable
    private static f0 b(c0 composition, String fileName) {
        for (f0 asset : composition.j().values()) {
            if (asset.b().equals(fileName)) {
                return asset;
            }
        }
        return null;
    }

    private static l0<c0> a(@Nullable String cacheKey, Callable<k0<c0>> callable) {
        c0 cachedComposition = cacheKey == null ? null : g.b().a(cacheKey);
        if (cachedComposition != null) {
            return new l0<>(new h(cachedComposition));
        }
        if (cacheKey != null) {
            Map<String, l0<c0>> map = a;
            if (map.containsKey(cacheKey)) {
                return map.get(cacheKey);
            }
        }
        LottieTask<LottieComposition> task = new l0<>(callable);
        if (cacheKey != null) {
            AtomicBoolean resultAlreadyCalled = new AtomicBoolean(false);
            task.c(new g(cacheKey, resultAlreadyCalled));
            task.b(new d(cacheKey, resultAlreadyCalled));
            if (!resultAlreadyCalled.get()) {
                Map<String, l0<c0>> map2 = a;
                map2.put(cacheKey, task);
                if (map2.size() == 1) {
                    C(false);
                }
            }
        }
        return task;
    }

    static /* synthetic */ k0 w(c0 cachedComposition) {
        return new k0(cachedComposition);
    }

    static /* synthetic */ void x(String cacheKey, AtomicBoolean resultAlreadyCalled, c0 result) {
        Map<String, l0<c0>> map = a;
        map.remove(cacheKey);
        resultAlreadyCalled.set(true);
        if (map.size() == 0) {
            C(true);
        }
    }

    static /* synthetic */ void v(String cacheKey, AtomicBoolean resultAlreadyCalled, Throwable result) {
        Map<String, l0<c0>> map = a;
        map.remove(cacheKey);
        resultAlreadyCalled.set(true);
        if (map.size() == 0) {
            C(true);
        }
    }

    private static void C(boolean idle) {
        List<LottieTaskIdleListener> listeners = new ArrayList<>(b);
        for (int i = 0; i < listeners.size(); i++) {
            ((m0) listeners.get(i)).a(idle);
        }
    }
}
