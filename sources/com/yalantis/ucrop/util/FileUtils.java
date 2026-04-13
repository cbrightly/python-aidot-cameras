package com.yalantis.ucrop.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.ValueOf;
import java.util.Locale;

public class FileUtils {
    private static final String TAG = "FileUtils";

    private FileUtils() {
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, (String) null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor == null) {
                    return null;
                }
                cursor.close();
                return null;
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            cursor.close();
            return string;
        } catch (IllegalArgumentException ex) {
            Log.i(TAG, String.format(Locale.getDefault(), "getDataColumn: _data - [%s]", new Object[]{ex.getMessage()}));
            if (cursor == null) {
                return null;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    @SuppressLint({"NewApi"})
    public static String getPath(Context context, Uri uri) {
        if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(context, uri)) {
            if (FirebaseAnalytics.Param.CONTENT.equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, (String) null, (String[]) null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(":");
            if ("primary".equalsIgnoreCase(split[0])) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        } else if (isDownloadsDocument(uri)) {
            String id = DocumentsContract.getDocumentId(uri);
            if (!TextUtils.isEmpty(id)) {
                try {
                    return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), ValueOf.toLong(id)), (String) null, (String[]) null);
                } catch (NumberFormatException e) {
                    Log.i(TAG, e.getMessage());
                    return "";
                }
            }
        } else if (isMediaDocument(uri)) {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
            String type = split2[0];
            Uri contentUri = null;
            if (PictureMimeType.MIME_TYPE_PREFIX_IMAGE.equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            return getDataColumn(context, contentUri, "_id=?", new String[]{split2[1]});
        }
        return "";
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0056  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyFile(java.io.FileInputStream r10, java.lang.String r11) {
        /*
            r0 = 0
            if (r10 != 0) goto L_0x0004
            return r0
        L_0x0004:
            r1 = 0
            r2 = 0
            java.nio.channels.FileChannel r3 = r10.getChannel()     // Catch:{ Exception -> 0x0047, all -> 0x0038 }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r4.<init>(r11)     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r1.<init>(r4)     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            java.nio.channels.FileChannel r1 = r1.getChannel()     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r2 = r1
            r4 = 0
            long r6 = r3.size()     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r8 = r2
            r3.transferTo(r4, r6, r8)     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r3.close()     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r0 = 1
            r10.close()
            r3.close()
            if (r2 == 0) goto L_0x0032
            r2.close()
        L_0x0032:
            return r0
        L_0x0033:
            r0 = move-exception
            r1 = r3
            goto L_0x0039
        L_0x0036:
            r1 = move-exception
            goto L_0x004b
        L_0x0038:
            r0 = move-exception
        L_0x0039:
            r10.close()
            if (r1 == 0) goto L_0x0041
            r1.close()
        L_0x0041:
            if (r2 == 0) goto L_0x0046
            r2.close()
        L_0x0046:
            throw r0
        L_0x0047:
            r3 = move-exception
            r9 = r3
            r3 = r1
            r1 = r9
        L_0x004b:
            r10.close()
            if (r3 == 0) goto L_0x0054
            r3.close()
        L_0x0054:
            if (r2 == 0) goto L_0x0059
            r2.close()
        L_0x0059:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yalantis.ucrop.util.FileUtils.copyFile(java.io.FileInputStream, java.lang.String):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0048  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyFile(@androidx.annotation.NonNull java.lang.String r10, @androidx.annotation.NonNull java.lang.String r11) {
        /*
            boolean r0 = r10.equalsIgnoreCase(r11)
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            r0 = 0
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x003e }
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x003e }
            r3.<init>(r10)     // Catch:{ all -> 0x003e }
            r2.<init>(r3)     // Catch:{ all -> 0x003e }
            java.nio.channels.FileChannel r4 = r2.getChannel()     // Catch:{ all -> 0x003e }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x003c }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x003c }
            r2.<init>(r11)     // Catch:{ all -> 0x003c }
            r1.<init>(r2)     // Catch:{ all -> 0x003c }
            java.nio.channels.FileChannel r1 = r1.getChannel()     // Catch:{ all -> 0x003c }
            r0 = r1
            r5 = 0
            long r7 = r4.size()     // Catch:{ all -> 0x003c }
            r9 = r0
            r4.transferTo(r5, r7, r9)     // Catch:{ all -> 0x003c }
            r4.close()     // Catch:{ all -> 0x003c }
            r4.close()
            if (r0 == 0) goto L_0x003b
            r0.close()
        L_0x003b:
            return
        L_0x003c:
            r1 = move-exception
            goto L_0x0041
        L_0x003e:
            r2 = move-exception
            r4 = r1
            r1 = r2
        L_0x0041:
            if (r4 == 0) goto L_0x0046
            r4.close()
        L_0x0046:
            if (r0 == 0) goto L_0x004b
            r0.close()
        L_0x004b:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yalantis.ucrop.util.FileUtils.copyFile(java.lang.String, java.lang.String):void");
    }

    public static String rename(String fileName) {
        String temp = fileName.substring(0, fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return temp + "_" + DateUtils.getCreateFileName() + suffix;
    }
}
