package me.jessyan.autosize;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class InitProvider extends ContentProvider {
    public boolean onCreate() {
        Context application = getContext().getApplicationContext();
        if (application == null) {
            application = AutoSizeUtils.getApplicationByReflect();
        }
        AutoSizeConfig.getInstance().setLog(true).init((Application) application).setUseDeviceSize(false);
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
