package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;

public class MeizuImpl implements IRomOAID {
    private static final String TAG = "SA.MeizuImpl";
    private final Context mContext;

    public MeizuImpl(Context context) {
        this.mContext = context;
    }

    public boolean isSupported() {
        try {
            if (this.mContext.getPackageManager().resolveContentProvider("com.meizu.flyme.openidsdk", 0) != null) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            SALog.i(TAG, th);
            return false;
        }
    }

    public String getRomOAID() {
        try {
            Cursor cursor = this.mContext.getContentResolver().query(Uri.parse("content://com.meizu.flyme.openidsdk/"), (String[]) null, (String) null, new String[]{"oaid"}, (String) null);
            if (cursor == null || !cursor.moveToFirst()) {
                return null;
            }
            String oaid = cursor.getString(cursor.getColumnIndex("value"));
            SALog.i(TAG, "OAID query success: " + oaid);
            cursor.close();
            return oaid;
        } catch (Throwable th) {
            SALog.i(TAG, th);
            return null;
        }
    }
}
