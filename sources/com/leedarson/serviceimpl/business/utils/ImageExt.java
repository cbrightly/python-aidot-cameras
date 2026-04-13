package com.leedarson.serviceimpl.business.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import com.luck.picture.lib.config.PictureMimeType;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.yanzhenjie.andserver.util.h;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Locale;
import kotlin.io.j;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: ImageExt.kt */
public final class ImageExt {
    private static final String ALBUM_DIR = Environment.DIRECTORY_PICTURES;
    @NotNull
    private static final String TAG = "ImageExt";
    public static ChangeQuickRedirect changeQuickRedirect;

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0064, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0065, code lost:
        kotlin.io.b.a(r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0068, code lost:
        throw r2;
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final android.net.Uri copyToAlbum(@org.jetbrains.annotations.NotNull java.io.File r9, @org.jetbrains.annotations.NotNull android.content.Context r10, @org.jetbrains.annotations.NotNull java.lang.String r11, @org.jetbrains.annotations.Nullable java.lang.String r12) {
        /*
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            r2[r3] = r9
            r4 = 1
            r2[r4] = r10
            r5 = 2
            r2[r5] = r11
            r6 = 3
            r2[r6] = r12
            com.meituan.robust.ChangeQuickRedirect r7 = changeQuickRedirect
            java.lang.Class[] r1 = new java.lang.Class[r1]
            java.lang.Class<java.io.File> r8 = java.io.File.class
            r1[r3] = r8
            java.lang.Class<android.content.Context> r3 = android.content.Context.class
            r1[r4] = r3
            r1[r5] = r0
            r1[r6] = r0
            java.lang.Class<android.net.Uri> r8 = android.net.Uri.class
            r3 = 0
            r5 = 1
            r6 = 7312(0x1c90, float:1.0246E-41)
            r4 = r7
            r7 = r1
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0036
            java.lang.Object r9 = r0.result
            android.net.Uri r9 = (android.net.Uri) r9
            return r9
        L_0x0036:
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.k.e(r9, r0)
            java.lang.String r0 = "context"
            kotlin.jvm.internal.k.e(r10, r0)
            java.lang.String r0 = "fileName"
            kotlin.jvm.internal.k.e(r11, r0)
            boolean r0 = r9.canRead()
            r1 = 0
            if (r0 == 0) goto L_0x0069
            boolean r0 = r9.exists()
            if (r0 != 0) goto L_0x0053
            goto L_0x0069
        L_0x0053:
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r9)
            r2 = r0
            r3 = 0
            android.net.Uri r4 = saveToAlbum(r2, r10, r11, r12)     // Catch:{ all -> 0x0062 }
            kotlin.io.b.a(r0, r1)
            return r4
        L_0x0062:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0064 }
        L_0x0064:
            r2 = move-exception
            kotlin.io.b.a(r0, r1)
            throw r2
        L_0x0069:
            java.lang.String r0 = "check: read file error: "
            java.lang.String r0 = kotlin.jvm.internal.k.l(r0, r9)
            java.lang.String r2 = "ImageExt"
            android.util.Log.w(r2, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.utils.ImageExt.copyToAlbum(java.io.File, android.content.Context, java.lang.String, java.lang.String):android.net.Uri");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0082, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        kotlin.io.b.a(r12, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0086, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0089, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008a, code lost:
        kotlin.io.b.a(r4, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008d, code lost:
        throw r5;
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final android.net.Uri saveToAlbum(@org.jetbrains.annotations.NotNull java.io.InputStream r12, @org.jetbrains.annotations.NotNull android.content.Context r13, @org.jetbrains.annotations.NotNull java.lang.String r14, @org.jetbrains.annotations.Nullable java.lang.String r15) {
        /*
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r12
            r10 = 1
            r2[r10] = r13
            r11 = 2
            r2[r11] = r14
            r3 = 3
            r2[r3] = r15
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<java.io.InputStream> r1 = java.io.InputStream.class
            r7[r9] = r1
            java.lang.Class<android.content.Context> r1 = android.content.Context.class
            r7[r10] = r1
            r7[r11] = r0
            r7[r3] = r0
            java.lang.Class<android.net.Uri> r8 = android.net.Uri.class
            r3 = 0
            r5 = 1
            r6 = 7313(0x1c91, float:1.0248E-41)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0034
            java.lang.Object r12 = r0.result
            android.net.Uri r12 = (android.net.Uri) r12
            return r12
        L_0x0034:
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.k.e(r12, r0)
            java.lang.String r0 = "context"
            kotlin.jvm.internal.k.e(r13, r0)
            java.lang.String r0 = "fileName"
            kotlin.jvm.internal.k.e(r14, r0)
            android.content.ContentResolver r0 = r13.getContentResolver()
            com.leedarson.serviceimpl.business.utils.OutputFileTaker r1 = new com.leedarson.serviceimpl.business.utils.OutputFileTaker
            r2 = 0
            r1.<init>(r2, r10, r2)
            java.lang.String r3 = "resolver"
            kotlin.jvm.internal.k.d(r0, r3)
            android.net.Uri r3 = insertMediaImage(r0, r14, r15, r1)
            if (r3 != 0) goto L_0x0060
            java.lang.String r4 = "ImageExt"
            java.lang.String r5 = "insert: error: uri == null"
            android.util.Log.w(r4, r5)
            return r2
        L_0x0060:
            java.io.OutputStream r4 = outputStream(r3, r0)
            if (r4 != 0) goto L_0x0067
            return r2
        L_0x0067:
            r5 = r4
            r6 = 0
            r7 = r12
            r8 = 0
            kotlin.io.a.b(r7, r5, r9, r11, r2)     // Catch:{ all -> 0x0080 }
            java.io.File r9 = r1.getFile()     // Catch:{ all -> 0x0080 }
            finishPending(r3, r13, r0, r9)     // Catch:{ all -> 0x0080 }
            kotlin.x r7 = kotlin.x.a     // Catch:{ all -> 0x0080 }
            kotlin.io.b.a(r12, r2)     // Catch:{ all -> 0x0087 }
            kotlin.io.b.a(r4, r2)
            return r3
        L_0x0080:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0082 }
        L_0x0082:
            r7 = move-exception
            kotlin.io.b.a(r12, r2)     // Catch:{ all -> 0x0087 }
            throw r7     // Catch:{ all -> 0x0087 }
        L_0x0087:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0089 }
        L_0x0089:
            r5 = move-exception
            kotlin.io.b.a(r4, r2)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.utils.ImageExt.saveToAlbum(java.io.InputStream, android.content.Context, java.lang.String, java.lang.String):android.net.Uri");
    }

    public static /* synthetic */ Uri saveToAlbum$default(Bitmap bitmap, Context context, String str, String str2, int i, int i2, Object obj) {
        String str3;
        Bitmap bitmap2 = bitmap;
        Context context2 = context;
        String str4 = str;
        int i3 = i2;
        Class<String> cls = String.class;
        int i4 = i;
        Object[] objArr = {bitmap2, context2, str4, str2, new Integer(i4), new Integer(i3), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7315, new Class[]{Bitmap.class, Context.class, cls, cls, cls2, cls2, Object.class}, Uri.class);
        if (proxy.isSupported) {
            return (Uri) proxy.result;
        }
        if ((i3 & 4) != 0) {
            str3 = null;
        } else {
            str3 = str2;
        }
        if ((i3 & 8) != 0) {
            i4 = 75;
        }
        return saveToAlbum(bitmap2, context2, str4, str3, i4);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x008f, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0090, code lost:
        kotlin.io.b.a(r4, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0093, code lost:
        throw r5;
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final android.net.Uri saveToAlbum(@org.jetbrains.annotations.NotNull android.graphics.Bitmap r10, @org.jetbrains.annotations.NotNull android.content.Context r11, @org.jetbrains.annotations.NotNull java.lang.String r12, @org.jetbrains.annotations.Nullable java.lang.String r13, int r14) {
        /*
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 5
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            r2[r3] = r10
            r9 = 1
            r2[r9] = r11
            r4 = 2
            r2[r4] = r12
            r5 = 3
            r2[r5] = r13
            java.lang.Integer r6 = new java.lang.Integer
            r6.<init>(r14)
            r7 = 4
            r2[r7] = r6
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r1 = new java.lang.Class[r1]
            java.lang.Class<android.graphics.Bitmap> r8 = android.graphics.Bitmap.class
            r1[r3] = r8
            java.lang.Class<android.content.Context> r3 = android.content.Context.class
            r1[r9] = r3
            r1[r4] = r0
            r1[r5] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r1[r7] = r0
            java.lang.Class<android.net.Uri> r8 = android.net.Uri.class
            r3 = 0
            r5 = 1
            r0 = 7314(0x1c92, float:1.0249E-41)
            r4 = r6
            r6 = r0
            r7 = r1
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0043
            java.lang.Object r10 = r0.result
            android.net.Uri r10 = (android.net.Uri) r10
            return r10
        L_0x0043:
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.k.e(r10, r0)
            java.lang.String r0 = "context"
            kotlin.jvm.internal.k.e(r11, r0)
            java.lang.String r0 = "fileName"
            kotlin.jvm.internal.k.e(r12, r0)
            android.content.ContentResolver r0 = r11.getContentResolver()
            com.leedarson.serviceimpl.business.utils.OutputFileTaker r1 = new com.leedarson.serviceimpl.business.utils.OutputFileTaker
            r2 = 0
            r1.<init>(r2, r9, r2)
            java.lang.String r3 = "resolver"
            kotlin.jvm.internal.k.d(r0, r3)
            android.net.Uri r3 = insertMediaImage(r0, r12, r13, r1)
            if (r3 != 0) goto L_0x006f
            java.lang.String r4 = "ImageExt"
            java.lang.String r5 = "insert: error: uri == null"
            android.util.Log.w(r4, r5)
            return r2
        L_0x006f:
            java.io.OutputStream r4 = outputStream(r3, r0)
            if (r4 != 0) goto L_0x0076
            return r2
        L_0x0076:
            r5 = r4
            r6 = 0
            android.graphics.Bitmap$CompressFormat r7 = getBitmapFormat(r12)     // Catch:{ all -> 0x008d }
            r10.compress(r7, r14, r5)     // Catch:{ all -> 0x008d }
            java.io.File r8 = r1.getFile()     // Catch:{ all -> 0x008d }
            finishPending(r3, r11, r0, r8)     // Catch:{ all -> 0x008d }
            kotlin.x r5 = kotlin.x.a     // Catch:{ all -> 0x008d }
            kotlin.io.b.a(r4, r2)
            return r3
        L_0x008d:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x008f }
        L_0x008f:
            r5 = move-exception
            kotlin.io.b.a(r4, r2)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.utils.ImageExt.saveToAlbum(android.graphics.Bitmap, android.content.Context, java.lang.String, java.lang.String, int):android.net.Uri");
    }

    private static final OutputStream outputStream(Uri $this$outputStream, ContentResolver resolver) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this$outputStream, resolver}, (Object) null, changeQuickRedirect2, true, 7316, new Class[]{Uri.class, ContentResolver.class}, OutputStream.class);
        if (proxy.isSupported) {
            return (OutputStream) proxy.result;
        }
        try {
            return resolver.openOutputStream($this$outputStream);
        } catch (FileNotFoundException e) {
            Log.e(TAG, k.l("save: open stream error: ", e));
            return null;
        }
    }

    private static final void finishPending(Uri $this$finishPending, Context context, ContentResolver resolver, File outputFile) {
        Class[] clsArr = {Uri.class, Context.class, ContentResolver.class, File.class};
        if (!PatchProxy.proxy(new Object[]{$this$finishPending, context, resolver, outputFile}, (Object) null, changeQuickRedirect, true, 7317, clsArr, Void.TYPE).isSupported) {
            ContentValues imageValues = new ContentValues();
            if (Build.VERSION.SDK_INT < 29) {
                if (outputFile != null) {
                    imageValues.put("_size", Long.valueOf(outputFile.length()));
                }
                resolver.update($this$finishPending, imageValues, (String) null, (String[]) null);
                context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", $this$finishPending));
                return;
            }
            imageValues.put("is_pending", 0);
            Log.i("zqr", k.l("result:", Integer.valueOf(resolver.update($this$finishPending, imageValues, (String) null, (String[]) null))));
        }
    }

    private static final Bitmap.CompressFormat getBitmapFormat(String $this$getBitmapFormat) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this$getBitmapFormat}, (Object) null, changeQuickRedirect, true, 7318, new Class[]{String.class}, Bitmap.CompressFormat.class);
        if (proxy.isSupported) {
            return (Bitmap.CompressFormat) proxy.result;
        }
        if ($this$getBitmapFormat != null) {
            String fileName = $this$getBitmapFormat.toLowerCase(Locale.ROOT);
            k.d(fileName, "(this as java.lang.Strin….toLowerCase(Locale.ROOT)");
            if (w.x(fileName, PictureMimeType.PNG, false, 2, (Object) null)) {
                return Bitmap.CompressFormat.PNG;
            }
            if (w.x(fileName, ".jpg", false, 2, (Object) null) || w.x(fileName, ".jpeg", false, 2, (Object) null)) {
                return Bitmap.CompressFormat.JPEG;
            }
            return Bitmap.CompressFormat.PNG;
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
    }

    private static final String getMimeType(String $this$getMimeType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this$getMimeType}, (Object) null, changeQuickRedirect, true, 7319, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if ($this$getMimeType != null) {
            String fileName = $this$getMimeType.toLowerCase(Locale.ROOT);
            k.d(fileName, "(this as java.lang.Strin….toLowerCase(Locale.ROOT)");
            if (w.x(fileName, PictureMimeType.PNG, false, 2, (Object) null)) {
                return "image/png";
            }
            if (w.x(fileName, ".jpg", false, 2, (Object) null) || w.x(fileName, ".jpeg", false, 2, (Object) null)) {
                return "image/jpeg";
            }
            if (w.x(fileName, ".webp", false, 2, (Object) null)) {
                return "image/webp";
            }
            if (w.x(fileName, ".gif", false, 2, (Object) null)) {
                return h.IMAGE_GIF_VALUE;
            }
            return null;
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
    }

    static /* synthetic */ Uri insertMediaImage$default(ContentResolver contentResolver, String str, String str2, OutputFileTaker outputFileTaker, int i, Object obj) {
        Class<String> cls = String.class;
        Object[] objArr = {contentResolver, str, str2, outputFileTaker, new Integer(i), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7321, new Class[]{ContentResolver.class, cls, cls, OutputFileTaker.class, Integer.TYPE, Object.class}, Uri.class);
        if (proxy.isSupported) {
            return (Uri) proxy.result;
        }
        if ((i & 4) != 0) {
            outputFileTaker = null;
        }
        return insertMediaImage(contentResolver, str, str2, outputFileTaker);
    }

    private static final Uri insertMediaImage(ContentResolver contentResolver, String str, String str2, OutputFileTaker outputFileTaker) {
        Uri collection;
        String path;
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{contentResolver, str, str2, outputFileTaker}, (Object) null, changeQuickRedirect, true, 7320, new Class[]{ContentResolver.class, cls, cls, OutputFileTaker.class}, Uri.class);
        if (proxy.isSupported) {
            return (Uri) proxy.result;
        }
        ContentResolver $this$insertMediaImage = contentResolver;
        String newName = str2;
        String fileName = str;
        OutputFileTaker outputFileTaker2 = outputFileTaker;
        ContentValues imageValues = new ContentValues();
        ContentValues $this$insertMediaImage_u24lambda_u2d4 = imageValues;
        String mimeType = getMimeType(fileName);
        if (mimeType != null) {
            $this$insertMediaImage_u24lambda_u2d4.put("mime_type", mimeType);
        }
        long date = System.currentTimeMillis() / ((long) 1000);
        $this$insertMediaImage_u24lambda_u2d4.put("date_added", Long.valueOf(date));
        $this$insertMediaImage_u24lambda_u2d4.put("date_modified", Long.valueOf(date));
        if (Build.VERSION.SDK_INT >= 29) {
            if (!TextUtils.isEmpty(newName)) {
                path = ALBUM_DIR + '/' + newName;
            } else {
                path = ALBUM_DIR;
            }
            ContentValues $this$insertMediaImage_u24lambda_u2d5 = imageValues;
            $this$insertMediaImage_u24lambda_u2d5.put("_display_name", fileName);
            $this$insertMediaImage_u24lambda_u2d5.put("relative_path", path);
            $this$insertMediaImage_u24lambda_u2d5.put("is_pending", 1);
            Uri contentUri = MediaStore.Images.Media.getContentUri("external_primary");
            k.d(contentUri, "getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)");
            collection = contentUri;
            String str3 = newName;
            String str4 = fileName;
        } else {
            File pictures = Environment.getExternalStoragePublicDirectory(ALBUM_DIR);
            File saveDir = newName != null ? new File(pictures, newName) : pictures;
            if (saveDir.exists() || saveDir.mkdirs()) {
                File file = new File(saveDir, fileName);
                String fileNameWithoutExtension = j.e(file);
                String fileExtension = j.d(file);
                String absolutePath = file.getAbsolutePath();
                k.d(absolutePath, "imageFile.absolutePath");
                Uri queryUri = queryMediaImage28($this$insertMediaImage, absolutePath);
                int suffix = 1;
                while (queryUri != null) {
                    String relativePath = newName;
                    StringBuilder sb = new StringBuilder();
                    sb.append(fileNameWithoutExtension);
                    String fileName2 = fileName;
                    sb.append('(');
                    int suffix2 = suffix + 1;
                    sb.append(suffix);
                    sb.append(").");
                    sb.append(fileExtension);
                    file = new File(saveDir, sb.toString());
                    String absolutePath2 = file.getAbsolutePath();
                    k.d(absolutePath2, "imageFile.absolutePath");
                    queryUri = queryMediaImage28($this$insertMediaImage, absolutePath2);
                    newName = relativePath;
                    suffix = suffix2;
                    fileName = fileName2;
                }
                String relativePath2 = newName;
                String str5 = fileName;
                ContentValues $this$insertMediaImage_u24lambda_u2d6 = imageValues;
                $this$insertMediaImage_u24lambda_u2d6.put("_display_name", file.getName());
                String imagePath = file.getAbsolutePath();
                Log.v(TAG, k.l("save file: ", imagePath));
                $this$insertMediaImage_u24lambda_u2d6.put("_data", imagePath);
                if (outputFileTaker2 != null) {
                    outputFileTaker2.setFile(file);
                }
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                k.d(uri, "EXTERNAL_CONTENT_URI");
                collection = uri;
            } else {
                Log.e(TAG, "save: error: can't create Pictures directory");
                return null;
            }
        }
        return $this$insertMediaImage.insert(collection, imageValues);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00c0, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c1, code lost:
        kotlin.io.b.a(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c4, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final android.net.Uri queryMediaImage28(android.content.ContentResolver r13, java.lang.String r14) {
        /*
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r13
            r9 = 1
            r1[r9] = r14
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.content.ContentResolver> r0 = android.content.ContentResolver.class
            r6[r8] = r0
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r9] = r0
            java.lang.Class<android.net.Uri> r7 = android.net.Uri.class
            r2 = 0
            r4 = 1
            r5 = 7322(0x1c9a, float:1.026E-41)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0028
            java.lang.Object r13 = r0.result
            android.net.Uri r13 = (android.net.Uri) r13
            return r13
        L_0x0028:
            r0 = r13
            r13 = r14
            int r14 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            r6 = 0
            if (r14 < r1) goto L_0x0032
            return r6
        L_0x0032:
            java.io.File r14 = new java.io.File
            r14.<init>(r13)
            boolean r1 = r14.canRead()
            java.lang.String r7 = "query: path: "
            java.lang.String r10 = "ImageExt"
            if (r1 == 0) goto L_0x0063
            boolean r1 = r14.exists()
            if (r1 == 0) goto L_0x0063
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r7)
            r1.append(r13)
            java.lang.String r2 = " exists"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r10, r1)
            android.net.Uri r1 = android.net.Uri.fromFile(r14)
            return r1
        L_0x0063:
            android.net.Uri r11 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            java.lang.String r12 = "_id"
            java.lang.String r1 = "_data"
            java.lang.String[] r2 = new java.lang.String[]{r12, r1}
            java.lang.String[] r4 = new java.lang.String[r9]
            r4[r8] = r13
            r5 = 0
            java.lang.String r3 = "_data == ?"
            r1 = r11
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5)
            if (r1 != 0) goto L_0x007f
            goto L_0x00bd
        L_0x007f:
            r2 = r1
            r3 = 0
            boolean r4 = r2.moveToNext()     // Catch:{ all -> 0x00be }
            if (r4 == 0) goto L_0x00b7
            int r4 = r2.getColumnIndexOrThrow(r12)     // Catch:{ all -> 0x00be }
            long r8 = r2.getLong(r4)     // Catch:{ all -> 0x00be }
            android.net.Uri r5 = android.content.ContentUris.withAppendedId(r11, r8)     // Catch:{ all -> 0x00be }
            java.lang.String r12 = "withAppendedId(collection, id)"
            kotlin.jvm.internal.k.d(r5, r12)     // Catch:{ all -> 0x00be }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x00be }
            r12.<init>()     // Catch:{ all -> 0x00be }
            r12.append(r7)     // Catch:{ all -> 0x00be }
            r12.append(r13)     // Catch:{ all -> 0x00be }
            java.lang.String r7 = " exists uri: "
            r12.append(r7)     // Catch:{ all -> 0x00be }
            r12.append(r5)     // Catch:{ all -> 0x00be }
            java.lang.String r7 = r12.toString()     // Catch:{ all -> 0x00be }
            android.util.Log.v(r10, r7)     // Catch:{ all -> 0x00be }
            kotlin.io.b.a(r1, r6)
            return r5
        L_0x00b7:
            kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x00be }
            kotlin.io.b.a(r1, r6)
        L_0x00bd:
            return r6
        L_0x00be:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x00c0 }
        L_0x00c0:
            r3 = move-exception
            kotlin.io.b.a(r1, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.utils.ImageExt.queryMediaImage28(android.content.ContentResolver, java.lang.String):android.net.Uri");
    }
}
