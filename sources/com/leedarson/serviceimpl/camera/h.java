package com.leedarson.serviceimpl.camera;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: FileUtil */
public class h {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String a(Context context, Uri uri) {
        Cursor cursor;
        int index;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, uri}, (Object) null, changeQuickRedirect2, true, 7373, new Class[]{Context.class, Uri.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (uri == null) {
            return null;
        }
        String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            return uri.getPath();
        }
        if ("file".equalsIgnoreCase(scheme)) {
            return uri.getPath();
        }
        if (!FirebaseAnalytics.Param.CONTENT.equalsIgnoreCase(scheme) || (cursor = context.getContentResolver().query(uri, new String[]{"_data"}, (String) null, (String[]) null, (String) null)) == null) {
            return null;
        }
        if (cursor.moveToFirst() && (index = cursor.getColumnIndex("_data")) > -1) {
            data = cursor.getString(index);
        }
        cursor.close();
        return data;
    }
}
