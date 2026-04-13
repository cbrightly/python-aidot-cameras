package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.content.Context;
import android.os.Build;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;
import com.sensorsdata.analytics.android.sdk.advert.oaid.OAIDRom;

public class VivoImpl implements IRomOAID {
    private static final String TAG = "SA.VivoImpl";
    private final Context mContext;

    public VivoImpl(Context context) {
        this.mContext = context;
    }

    public boolean isSupported() {
        if (Build.VERSION.SDK_INT < 28) {
            return false;
        }
        return OAIDRom.sysProperty("persist.sys.identifierid.supported", "0").equals("1");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003b, code lost:
        if (r9 != null) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003d, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0045, code lost:
        if (r9 == null) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0048, code lost:
        return r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getRomOAID() {
        /*
            r10 = this;
            java.lang.String r0 = "SA.VivoImpl"
            java.lang.String r1 = "content://com.vivo.vms.IdProvider/IdentifierId/OAID"
            android.net.Uri r1 = android.net.Uri.parse(r1)
            r8 = 0
            r9 = 0
            android.content.Context r2 = r10.mContext     // Catch:{ all -> 0x0041 }
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch:{ all -> 0x0041 }
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r3 = r1
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0041 }
            r9 = r2
            if (r9 == 0) goto L_0x003b
            boolean r2 = r9.moveToFirst()     // Catch:{ all -> 0x0041 }
            if (r2 == 0) goto L_0x003b
            java.lang.String r2 = "value"
            int r2 = r9.getColumnIndex(r2)     // Catch:{ all -> 0x0041 }
            java.lang.String r3 = r9.getString(r2)     // Catch:{ all -> 0x0041 }
            r8 = r3
            if (r8 == 0) goto L_0x0036
            int r3 = r8.length()     // Catch:{ all -> 0x0041 }
            if (r3 != 0) goto L_0x003b
        L_0x0036:
            java.lang.String r3 = "OAID query failed"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r0, (java.lang.String) r3)     // Catch:{ all -> 0x0041 }
        L_0x003b:
            if (r9 == 0) goto L_0x0048
        L_0x003d:
            r9.close()
            goto L_0x0048
        L_0x0041:
            r2 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r0, (java.lang.Throwable) r2)     // Catch:{ all -> 0x0049 }
            if (r9 == 0) goto L_0x0048
            goto L_0x003d
        L_0x0048:
            return r8
        L_0x0049:
            r0 = move-exception
            if (r9 == 0) goto L_0x004f
            r9.close()
        L_0x004f:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.advert.oaid.impl.VivoImpl.getRomOAID():java.lang.String");
    }
}
