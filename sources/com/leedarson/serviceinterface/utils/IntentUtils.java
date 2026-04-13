package com.leedarson.serviceinterface.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import com.leedarson.base.utils.w;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import net.sqlcipher.database.SQLiteDatabase;

public class IntentUtils {
    public static final String TYPE_IMAGE = "image/*";
    public static final String TYPE_VIDEO = "video/*";
    public static final String TYPE_VIDEO_MP4 = "video/mp4";
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void shareImage(Context context, File file, String title) {
        Class[] clsArr = {Context.class, File.class, String.class};
        if (!PatchProxy.proxy(new Object[]{context, file, title}, (Object) null, changeQuickRedirect, true, 9417, clsArr, Void.TYPE).isSupported) {
            shareFile(context, file, TYPE_IMAGE, title);
        }
    }

    public static void shareVideo(Context context, File file, String title) {
        Class[] clsArr = {Context.class, File.class, String.class};
        if (!PatchProxy.proxy(new Object[]{context, file, title}, (Object) null, changeQuickRedirect, true, 9418, clsArr, Void.TYPE).isSupported) {
            shareFile(context, file, TYPE_VIDEO, title);
        }
    }

    public static void shareVideoMp4(Context context, File file, String title) {
        Class[] clsArr = {Context.class, File.class, String.class};
        if (!PatchProxy.proxy(new Object[]{context, file, title}, (Object) null, changeQuickRedirect, true, 9419, clsArr, Void.TYPE).isSupported) {
            shareFile(context, file, "video/mp4", title);
        }
    }

    public static void shareFile(Context context, File file, String shareType, String title) {
        Uri fileUri;
        Class<String> cls = String.class;
        Class[] clsArr = {Context.class, File.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{context, file, shareType, title}, (Object) null, changeQuickRedirect, true, 9420, clsArr, Void.TYPE).isSupported) {
            Intent intent = new Intent("android.intent.action.SEND");
            if (Build.VERSION.SDK_INT >= 24) {
                fileUri = FileProvider.getUriForFile(context, w.z(context) + ".fileProvider", file);
                intent.addFlags(1);
            } else {
                fileUri = Uri.fromFile(file);
            }
            intent.putExtra("android.intent.extra.STREAM", fileUri);
            intent.setType(shareType);
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            Intent chooser = Intent.createChooser(intent, title);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(chooser);
            }
        }
    }

    private static Uri getVideoContentUri(Context context, File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, file}, (Object) null, changeQuickRedirect, true, 9421, new Class[]{Context.class, File.class}, Uri.class);
        if (proxy.isSupported) {
            return (Uri) proxy.result;
        }
        String filePath = file.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=?", new String[]{filePath}, (String) null);
        Uri uri = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                Uri baseUri = Uri.parse("content://media/external/video/media");
                uri = Uri.withAppendedPath(baseUri, "" + id);
            }
            cursor.close();
        }
        if (uri == null) {
            ContentValues values = new ContentValues();
            values.put("_data", filePath);
            uri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        }
        if (uri != null) {
            return uri;
        }
        return FileProvider.getUriForFile(context, w.z(context) + ".fileProvider", file);
    }
}
