package com.leedarson.serviceimpl.ipcservice.data_processors;

import com.leedarson.bean.H5ActionName;
import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: IPCPetCareProcessor */
public class d extends g {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long c = 0;

    public d() {
        this.b = H5ActionName.ACTION_PUSH;
        this.a = "Navigator";
    }

    /* JADX WARNING: Removed duplicated region for block: B:83:0x0165 A[Catch:{ JSONException -> 0x01b4 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(android.app.Activity r20, java.lang.String r21, java.lang.String r22, java.lang.String r23, java.lang.String r24) {
        /*
            r19 = this;
            java.lang.String r0 = "deviceId"
            java.lang.String r1 = "zoneId"
            java.lang.String r2 = "type"
            java.lang.String r3 = "deviceIds"
            java.lang.String r4 = "params"
            java.lang.String r5 = "page"
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r7 = 5
            java.lang.Object[] r8 = new java.lang.Object[r7]
            r15 = 0
            r8[r15] = r20
            r14 = 1
            java.lang.Boolean r13 = java.lang.Boolean.valueOf(r14)
            r8[r14] = r21
            r9 = 2
            r8[r9] = r22
            r10 = 3
            r8[r10] = r23
            r11 = 4
            r8[r11] = r24
            com.meituan.robust.ChangeQuickRedirect r12 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r7]
            java.lang.Class<android.app.Activity> r16 = android.app.Activity.class
            r7[r15] = r16
            r7[r14] = r6
            r7[r9] = r6
            r7[r10] = r6
            r7[r11] = r6
            java.lang.Class r6 = java.lang.Boolean.TYPE
            r11 = 0
            r16 = 8089(0x1f99, float:1.1335E-41)
            r9 = r19
            r10 = r12
            r12 = r16
            r17 = r13
            r13 = r7
            r7 = r14
            r14 = r6
            com.meituan.robust.PatchProxyResult r6 = com.meituan.robust.PatchProxy.proxy(r8, r9, r10, r11, r12, r13, r14)
            boolean r8 = r6.isSupported
            if (r8 == 0) goto L_0x0054
            java.lang.Object r0 = r6.result
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            return r0
        L_0x0054:
            r6 = r19
            r8 = r21
            r9 = r23
            r10 = r20
            r11 = r22
            r12 = r24
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "process: "
            r13.append(r14)
            r13.append(r12)
            java.lang.String r13 = r13.toString()
            java.lang.String r14 = "TAG"
            android.util.Log.d(r14, r13)
            java.lang.String r13 = r6.b
            boolean r13 = r13.equals(r9)
            if (r13 == 0) goto L_0x01cc
            java.lang.String r13 = r6.a
            boolean r13 = r13.equals(r11)
            if (r13 == 0) goto L_0x01cc
            long r13 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x01c2 }
            r21 = r8
            long r7 = r6.c     // Catch:{ JSONException -> 0x01be }
            long r13 = r13 - r7
            r7 = 600(0x258, double:2.964E-321)
            int r7 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r7 >= 0) goto L_0x009c
            r7 = 0
            int r7 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x009c
            return r15
        L_0x009c:
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x01be }
            r6.c = r7     // Catch:{ JSONException -> 0x01be }
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01be }
            r7.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x01be }
            boolean r8 = r7.has(r5)     // Catch:{ JSONException -> 0x01be }
            if (r8 == 0) goto L_0x01ba
            java.lang.String r8 = r7.getString(r5)     // Catch:{ JSONException -> 0x01be }
            java.lang.String r15 = "IPC.cloudEvents"
            boolean r8 = r8.equals(r15)     // Catch:{ JSONException -> 0x01be }
            if (r8 != 0) goto L_0x00cf
            java.lang.String r5 = r7.getString(r5)     // Catch:{ JSONException -> 0x00ca }
            java.lang.String r8 = "IPC.event"
            boolean r5 = r5.equals(r8)     // Catch:{ JSONException -> 0x00ca }
            if (r5 == 0) goto L_0x00c6
            goto L_0x00cf
        L_0x00c6:
            r17 = r6
            goto L_0x01bc
        L_0x00ca:
            r0 = move-exception
            r17 = r6
            goto L_0x01c7
        L_0x00cf:
            boolean r5 = r7.has(r4)     // Catch:{ JSONException -> 0x01be }
            if (r5 == 0) goto L_0x01b6
            org.json.JSONObject r4 = r7.getJSONObject(r4)     // Catch:{ JSONException -> 0x01be }
            r5 = 0
            r8 = 0
            java.lang.String r15 = ""
            boolean r18 = r4.has(r3)     // Catch:{ JSONException -> 0x01be }
            if (r18 == 0) goto L_0x00e8
            java.lang.String r3 = r4.getString(r3)     // Catch:{ JSONException -> 0x00ca }
            r8 = r3
        L_0x00e8:
            boolean r3 = r4.has(r2)     // Catch:{ JSONException -> 0x01be }
            if (r3 == 0) goto L_0x00f3
            java.lang.String r2 = r4.getString(r2)     // Catch:{ JSONException -> 0x00ca }
            r15 = r2
        L_0x00f3:
            boolean r2 = r4.has(r1)     // Catch:{ JSONException -> 0x01be }
            if (r2 == 0) goto L_0x00fe
            java.lang.String r1 = r4.getString(r1)     // Catch:{ JSONException -> 0x00ca }
            r5 = r1
        L_0x00fe:
            r1 = 0
            boolean r2 = r4.has(r0)     // Catch:{ JSONException -> 0x01be }
            if (r2 == 0) goto L_0x010a
            java.lang.String r0 = r4.getString(r0)     // Catch:{ JSONException -> 0x00ca }
            r1 = r0
        L_0x010a:
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r0 = com.leedarson.serviceimpl.ipcservice.IpcServiceImpl.a     // Catch:{ JSONException -> 0x01be }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ JSONException -> 0x01be }
        L_0x0110:
            boolean r2 = r0.hasNext()     // Catch:{ JSONException -> 0x01be }
            if (r2 == 0) goto L_0x017f
            java.lang.Object r2 = r0.next()     // Catch:{ JSONException -> 0x01be }
            com.leedarson.bean.IpcDeviceBean r2 = (com.leedarson.bean.IpcDeviceBean) r2     // Catch:{ JSONException -> 0x01be }
            boolean r3 = android.text.TextUtils.isEmpty(r8)     // Catch:{ JSONException -> 0x01be }
            r20 = r0
            java.lang.String r0 = "isOwner"
            if (r3 == 0) goto L_0x0142
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x00ca }
            if (r3 == 0) goto L_0x0142
            boolean r3 = r4.has(r0)     // Catch:{ JSONException -> 0x00ca }
            if (r3 == 0) goto L_0x0138
            boolean r0 = r4.getBoolean(r0)     // Catch:{ JSONException -> 0x00ca }
            r2.isOwner = r0     // Catch:{ JSONException -> 0x00ca }
        L_0x0138:
            r0 = 1
            r2.cloudPlayback = r0     // Catch:{ JSONException -> 0x00ca }
            r3 = r17
            r2.isCurrentDevice = r3     // Catch:{ JSONException -> 0x00ca }
            r17 = r6
            goto L_0x0178
        L_0x0142:
            r3 = r17
            boolean r17 = android.text.TextUtils.isEmpty(r8)     // Catch:{ JSONException -> 0x01be }
            if (r17 != 0) goto L_0x0155
            r17 = r6
            java.lang.String r6 = r2.id     // Catch:{ JSONException -> 0x01b4 }
            boolean r6 = r8.contains(r6)     // Catch:{ JSONException -> 0x01b4 }
            if (r6 != 0) goto L_0x015f
            goto L_0x0157
        L_0x0155:
            r17 = r6
        L_0x0157:
            java.lang.String r6 = r2.id     // Catch:{ JSONException -> 0x01b4 }
            boolean r6 = r6.equals(r1)     // Catch:{ JSONException -> 0x01b4 }
            if (r6 == 0) goto L_0x0171
        L_0x015f:
            boolean r6 = r4.has(r0)     // Catch:{ JSONException -> 0x01b4 }
            if (r6 == 0) goto L_0x016b
            boolean r0 = r4.getBoolean(r0)     // Catch:{ JSONException -> 0x01b4 }
            r2.isOwner = r0     // Catch:{ JSONException -> 0x01b4 }
        L_0x016b:
            r0 = 1
            r2.cloudPlayback = r0     // Catch:{ JSONException -> 0x01b4 }
            r2.isCurrentDevice = r3     // Catch:{ JSONException -> 0x01b4 }
            goto L_0x0178
        L_0x0171:
            r6 = 0
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r6)     // Catch:{ JSONException -> 0x01b4 }
            r2.isCurrentDevice = r0     // Catch:{ JSONException -> 0x01b4 }
        L_0x0178:
            r0 = r20
            r6 = r17
            r17 = r3
            goto L_0x0110
        L_0x017f:
            r17 = r6
            android.content.Intent r0 = new android.content.Intent     // Catch:{ JSONException -> 0x01b4 }
            java.lang.Class<com.leedarson.newui.EventsActivity> r2 = com.leedarson.newui.EventsActivity.class
            r0.<init>(r10, r2)     // Catch:{ JSONException -> 0x01b4 }
            com.leedarson.base.utils.c r2 = com.leedarson.base.utils.c.h()     // Catch:{ JSONException -> 0x01b4 }
            r3 = 0
            r2.t(r3)     // Catch:{ JSONException -> 0x01b4 }
            android.os.Bundle r2 = new android.os.Bundle     // Catch:{ JSONException -> 0x01b4 }
            r2.<init>()     // Catch:{ JSONException -> 0x01b4 }
            java.lang.String r3 = "devices"
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r6 = com.leedarson.serviceimpl.ipcservice.IpcServiceImpl.a     // Catch:{ JSONException -> 0x01b4 }
            r2.putParcelableArrayList(r3, r6)     // Catch:{ JSONException -> 0x01b4 }
            r0.putExtras(r2)     // Catch:{ JSONException -> 0x01b4 }
            java.lang.String r3 = "KEY_AREA_ID"
            r0.putExtra(r3, r5)     // Catch:{ JSONException -> 0x01b4 }
            java.lang.String r3 = "KEY_EVENT_TYPE"
            r0.putExtra(r3, r15)     // Catch:{ JSONException -> 0x01b4 }
            r10.startActivity(r0)     // Catch:{ JSONException -> 0x01b4 }
            int r3 = com.leedarson.R$anim.ipc_slide_in_right     // Catch:{ JSONException -> 0x01b4 }
            int r6 = com.leedarson.R$anim.ipc_slide_out_left     // Catch:{ JSONException -> 0x01b4 }
            r10.overridePendingTransition(r3, r6)     // Catch:{ JSONException -> 0x01b4 }
            goto L_0x01b8
        L_0x01b4:
            r0 = move-exception
            goto L_0x01c7
        L_0x01b6:
            r17 = r6
        L_0x01b8:
            r0 = 1
            return r0
        L_0x01ba:
            r17 = r6
        L_0x01bc:
            r1 = 0
            goto L_0x01d1
        L_0x01be:
            r0 = move-exception
            r17 = r6
            goto L_0x01c7
        L_0x01c2:
            r0 = move-exception
            r17 = r6
            r21 = r8
        L_0x01c7:
            r0.printStackTrace()
            r1 = 0
            return r1
        L_0x01cc:
            r17 = r6
            r21 = r8
            r1 = r15
        L_0x01d1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ipcservice.data_processors.d.a(android.app.Activity, java.lang.String, java.lang.String, java.lang.String, java.lang.String):boolean");
    }
}
