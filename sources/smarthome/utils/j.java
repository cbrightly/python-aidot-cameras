package smarthome.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.serviceimpl.camera.HeadImageClipActivity;
import com.leedarson.serviceinterface.utils.IntentUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.yanzhenjie.andserver.util.h;
import java.util.ArrayList;

/* compiled from: PhotoUtils */
public class j {
    public static void i(Activity activity, Uri imageUri, int requestCode) {
        Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
        if (Build.VERSION.SDK_INT >= 24) {
            intentCamera.setFlags(2);
            intentCamera.putExtra("output", imageUri);
        } else {
            intentCamera.putExtra("output", imageUri);
        }
        activity.startActivityForResult(intentCamera, requestCode);
    }

    public static void h(Activity activity, int requestCode) {
        Intent photoPickerIntent = new Intent("android.intent.action.GET_CONTENT");
        photoPickerIntent.setType(h.ALL_VALUE);
        ArrayList<String> mimes = new ArrayList<>();
        mimes.add(IntentUtils.TYPE_IMAGE);
        mimes.add(IntentUtils.TYPE_VIDEO);
        photoPickerIntent.putExtra("android.intent.extra.MIME_TYPES", mimes);
        activity.startActivityForResult(photoPickerIntent, requestCode);
    }

    public static Bitmap b(Uri uri, Context mContext) {
        try {
            return MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint({"NewApi"})
    public static String d(Context context, Uri uri) {
        if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(context, uri)) {
            if (FirebaseAnalytics.Param.CONTENT.equalsIgnoreCase(uri.getScheme())) {
                return "file:///" + c(context, uri, (String) null, (String[]) null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return "file:///" + uri.getPath();
            }
        } else if (f(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(":");
            if ("primary".equalsIgnoreCase(split[0])) {
                return "file:///" + Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        } else if (e(uri)) {
            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue());
            return "file:///" + c(context, contentUri, (String) null, (String[]) null);
        } else if (g(uri)) {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
            String type = split2[0];
            Uri contentUri2 = null;
            if (PictureMimeType.MIME_TYPE_PREFIX_IMAGE.equals(type)) {
                contentUri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            String[] selectionArgs = {split2[1]};
            return "file:///" + c(context, contentUri2, "_id=?", selectionArgs);
        }
        return null;
    }

    private static String c(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, (String) null);
            if (cursor == null || !cursor.moveToFirst()) {
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            cursor.close();
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private static boolean f(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean e(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean g(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static void a(Activity activity, Uri orgUri, Uri desUri, int width, int height, int requestCode, float cutRatio, String cutShape, double scale) {
        Intent intent = new Intent(activity, HeadImageClipActivity.class);
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(1);
        }
        intent.setDataAndType(orgUri, IntentUtils.TYPE_IMAGE);
        intent.putExtra("crop", "true");
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", scale);
        intent.putExtra("output", desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.setData(orgUri);
        intent.putExtra("cutRatio", cutRatio);
        intent.putExtra("cutShape", "circle".equals(cutShape) ? 1 : 0);
        activity.startActivityForResult(intent, requestCode);
    }
}
