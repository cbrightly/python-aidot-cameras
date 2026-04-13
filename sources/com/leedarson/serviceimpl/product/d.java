package com.leedarson.serviceimpl.product;

import androidx.core.app.NotificationCompat;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.leedarson.serviceinterface.BleMeshService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import meshsdk.BaseResp;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.EnergySaveMode;
import meshsdk.model.json.SecuAlarmRule;
import meshsdk.util.LDSMeshUtil;
import org.json.JSONObject;

/* compiled from: WallLampService */
public class d extends com.leedarson.serviceimpl.g {
    public static ChangeQuickRedirect changeQuickRedirect;

    static /* synthetic */ void a(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8484, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void h(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8485, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void i(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8494, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void j(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8495, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void k(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8496, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void l(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8497, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void m(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8498, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void n(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8486, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void o(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8487, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void p(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8488, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void q(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8489, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void r(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8490, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void s(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8491, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void t(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8492, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    static /* synthetic */ void u(d x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 8493, new Class[]{d.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.postJsBridgeCallback(x1, x2, x3);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007c, code lost:
        if (r6.equals("cancelSecurityAlarm") != false) goto L_0x008a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(android.app.Activity r21, java.lang.String r22, java.lang.String r23, java.lang.String r24) {
        /*
            r20 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r21
            r10 = 1
            r2[r10] = r22
            r11 = 2
            r2[r11] = r23
            r12 = 3
            r2[r12] = r24
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<android.app.Activity> r3 = android.app.Activity.class
            r7[r9] = r3
            r7[r10] = r0
            r7[r11] = r0
            r7[r12] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 8482(0x2122, float:1.1886E-41)
            r3 = r20
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002f
            return
        L_0x002f:
            r2 = r20
            r3 = r22
            r4 = r24
            r5 = r21
            r6 = r23
            r7 = 0
            r8 = 0
            int r0 = r6.hashCode()
            r13 = -1
            switch(r0) {
                case -1849927877: goto L_0x007f;
                case -1448185193: goto L_0x0076;
                case -1299637476: goto L_0x006c;
                case 613596591: goto L_0x0062;
                case 1167070273: goto L_0x0058;
                case 1941201485: goto L_0x004e;
                case 2068146595: goto L_0x0044;
                default: goto L_0x0043;
            }
        L_0x0043:
            goto L_0x0089
        L_0x0044:
            java.lang.String r0 = "controlWallWasherLight"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0043
            r1 = 5
            goto L_0x008a
        L_0x004e:
            java.lang.String r0 = "setEnergyMode"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0043
            r1 = r11
            goto L_0x008a
        L_0x0058:
            java.lang.String r0 = "getEnergyMode"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0043
            r1 = r12
            goto L_0x008a
        L_0x0062:
            java.lang.String r0 = "setSecurityAlarm"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0043
            r1 = r9
            goto L_0x008a
        L_0x006c:
            java.lang.String r0 = "getWallWasherLightStatus"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0043
            r1 = 6
            goto L_0x008a
        L_0x0076:
            java.lang.String r0 = "cancelSecurityAlarm"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0043
            goto L_0x008a
        L_0x007f:
            java.lang.String r0 = "getSecurityAlarm"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0043
            r1 = r10
            goto L_0x008a
        L_0x0089:
            r1 = r13
        L_0x008a:
            java.lang.String r0 = ""
            java.lang.String r9 = "mac"
            switch(r1) {
                case 0: goto L_0x017d;
                case 1: goto L_0x0161;
                case 2: goto L_0x0147;
                case 3: goto L_0x012b;
                case 4: goto L_0x010d;
                case 5: goto L_0x00c6;
                case 6: goto L_0x0093;
                default: goto L_0x0091;
            }
        L_0x0091:
            goto L_0x019a
        L_0x0093:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x00ab }
            r1.<init>((java.lang.String) r4)     // Catch:{ Exception -> 0x00ab }
            r7 = r1
            java.lang.String r0 = r7.optString(r9, r0)     // Catch:{ Exception -> 0x00ab }
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x00ab }
            com.leedarson.serviceimpl.product.d$g r9 = new com.leedarson.serviceimpl.product.d$g     // Catch:{ Exception -> 0x00ab }
            r9.<init>(r3, r6)     // Catch:{ Exception -> 0x00ab }
            r1.getWallWasherLightStatus(r0, r9)     // Catch:{ Exception -> 0x00ab }
            goto L_0x019a
        L_0x00ab:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r9 = "getWallWasherLightStatus error:"
            r1.append(r9)
            java.lang.String r9 = r0.getMessage()
            r1.append(r9)
            java.lang.String r1 = r1.toString()
            meshsdk.MeshLog.e(r1)
            goto L_0x019a
        L_0x00c6:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x00f2 }
            r1.<init>((java.lang.String) r4)     // Catch:{ Exception -> 0x00f2 }
            r7 = r1
            java.lang.String r15 = r7.optString(r9, r0)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r1 = "onOff"
            int r16 = r7.optInt(r1, r13)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r1 = "dimming"
            int r17 = r7.optInt(r1, r13)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r1 = "RGB"
            java.lang.String r18 = r7.optString(r1, r0)     // Catch:{ Exception -> 0x00f2 }
            meshsdk.SIGMesh r14 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x00f2 }
            com.leedarson.serviceimpl.product.d$f r0 = new com.leedarson.serviceimpl.product.d$f     // Catch:{ Exception -> 0x00f2 }
            r0.<init>(r3, r6)     // Catch:{ Exception -> 0x00f2 }
            r19 = r0
            r14.controlWallWasherLight(r15, r16, r17, r18, r19)     // Catch:{ Exception -> 0x00f2 }
            goto L_0x019a
        L_0x00f2:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r9 = "controlWallWasherLight error:"
            r1.append(r9)
            java.lang.String r9 = r0.getMessage()
            r1.append(r9)
            java.lang.String r1 = r1.toString()
            meshsdk.MeshLog.e(r1)
            goto L_0x019a
        L_0x010d:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0125 }
            r0.<init>((java.lang.String) r4)     // Catch:{ JSONException -> 0x0125 }
            r7 = r0
            java.lang.String r0 = r7.getString(r9)     // Catch:{ JSONException -> 0x0125 }
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0125 }
            com.leedarson.serviceimpl.product.d$e r9 = new com.leedarson.serviceimpl.product.d$e     // Catch:{ JSONException -> 0x0125 }
            r9.<init>(r3, r6)     // Catch:{ JSONException -> 0x0125 }
            r1.cancelSecurityAlarm(r0, r9)     // Catch:{ JSONException -> 0x0125 }
            goto L_0x019a
        L_0x0125:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x019a
        L_0x012b:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0142 }
            r0.<init>((java.lang.String) r4)     // Catch:{ JSONException -> 0x0142 }
            r7 = r0
            java.lang.String r0 = r7.getString(r9)     // Catch:{ JSONException -> 0x0142 }
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0142 }
            com.leedarson.serviceimpl.product.d$d r9 = new com.leedarson.serviceimpl.product.d$d     // Catch:{ JSONException -> 0x0142 }
            r9.<init>(r3, r6)     // Catch:{ JSONException -> 0x0142 }
            r1.getEnergyMode(r0, r9)     // Catch:{ JSONException -> 0x0142 }
            goto L_0x019a
        L_0x0142:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x019a
        L_0x0147:
            com.google.gson.Gson r0 = new com.google.gson.Gson
            r0.<init>()
            java.lang.Class<meshsdk.model.json.EnergySaveMode> r1 = meshsdk.model.json.EnergySaveMode.class
            java.lang.Object r0 = r0.fromJson((java.lang.String) r4, r1)
            meshsdk.model.json.EnergySaveMode r0 = (meshsdk.model.json.EnergySaveMode) r0
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            com.leedarson.serviceimpl.product.d$c r9 = new com.leedarson.serviceimpl.product.d$c
            r9.<init>(r3, r6)
            r1.setEnergyMode(r0, r9)
            goto L_0x019a
        L_0x0161:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0178 }
            r0.<init>((java.lang.String) r4)     // Catch:{ JSONException -> 0x0178 }
            r7 = r0
            java.lang.String r0 = r7.getString(r9)     // Catch:{ JSONException -> 0x0178 }
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0178 }
            com.leedarson.serviceimpl.product.d$b r9 = new com.leedarson.serviceimpl.product.d$b     // Catch:{ JSONException -> 0x0178 }
            r9.<init>(r3, r6)     // Catch:{ JSONException -> 0x0178 }
            r1.getSecurityAlarm(r0, r9)     // Catch:{ JSONException -> 0x0178 }
            goto L_0x019a
        L_0x0178:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x019a
        L_0x017d:
            com.google.gson.Gson r0 = new com.google.gson.Gson
            r0.<init>()
            java.lang.Class<meshsdk.model.json.SecuAlarmRule> r1 = meshsdk.model.json.SecuAlarmRule.class
            java.lang.Object r0 = r0.fromJson((java.lang.String) r4, r1)
            meshsdk.model.json.SecuAlarmRule r0 = (meshsdk.model.json.SecuAlarmRule) r0
            r0.convertTimespan()
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            com.leedarson.serviceimpl.product.d$a r9 = new com.leedarson.serviceimpl.product.d$a
            r9.<init>(r3, r6)
            r1.setSecurityAlarm(r0, r9)
        L_0x019a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.product.d.handleData(android.app.Activity, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* compiled from: WallLampService */
    public class a extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8499, new Class[]{Object.class}, Void.TYPE).isSupported) {
                d.a(d.this, this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8500, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                d.h(d.this, this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    /* compiled from: WallLampService */
    public class b extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        b(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 8501, new Class[]{Object.class}, Void.TYPE).isSupported) {
                if (data instanceof SecuAlarmRule) {
                    d.n(d.this, this.a, new Gson().toJson((Object) (SecuAlarmRule) data), this.b);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                d.o(d.this, this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    /* compiled from: WallLampService */
    public class c extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        c(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8503, new Class[]{Object.class}, Void.TYPE).isSupported) {
                d.p(d.this, this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8504, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                d.q(d.this, this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    /* renamed from: com.leedarson.serviceimpl.product.d$d  reason: collision with other inner class name */
    /* compiled from: WallLampService */
    public class C0152d extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        C0152d(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 8505, new Class[]{Object.class}, Void.TYPE).isSupported) {
                if (data instanceof EnergySaveMode) {
                    d.r(d.this, this.a, new Gson().toJson((Object) (EnergySaveMode) data), this.b);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8506, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                d.s(d.this, this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    /* compiled from: WallLampService */
    public class e extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        e(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8507, new Class[]{Object.class}, Void.TYPE).isSupported) {
                d.t(d.this, this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8508, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                d.u(d.this, this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    /* compiled from: WallLampService */
    public class f extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        f(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8509, new Class[]{Object.class}, Void.TYPE).isSupported) {
                d.i(d.this, this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8510, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                d.j(d.this, getCallbackKey(), BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    /* compiled from: WallLampService */
    public class g extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        g(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 8511, new Class[]{Object.class}, Void.TYPE).isSupported) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    if (data instanceof Object[]) {
                        Object[] value = (Object[]) data;
                        jsonObject.put("onOff", value[0]);
                        jsonObject.put("RGB", value[1]);
                    }
                    d.k(d.this, this.a, BaseResp.generatorSuccessResp(jsonObject).toString(), this.b);
                } catch (Exception e) {
                    d.l(d.this, this.a, BaseResp.generatorFailResp(-1, "").toString(), this.b);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8512, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                d.m(d.this, getCallbackKey(), BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public void onSecurityTrigger(int meshAddr, int act) {
        Object[] objArr = {new Integer(meshAddr), new Integer(act)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8483, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, meshAddr);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mac", (Object) nodeInfo.macAddress);
                jsonObject.put(NotificationCompat.CATEGORY_ALARM, act);
                jsonObject.put(NotificationCompat.CATEGORY_EVENT, (Object) BleMeshService.EVT_SECURITY_ALARM);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative(BleMeshService.ON_EVENT_MESSAGE, jsonObject.toString());
        }
    }
}
