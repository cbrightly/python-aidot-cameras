package com.leedarson.serviceimpl.product;

import android.content.Context;
import com.leedarson.serviceimpl.g;
import com.leedarson.serviceimpl.strategys.f;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import meshsdk.BaseResp;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.NetworkEncryptInfo;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.MeshEncryptInfoUtil;
import org.json.JSONObject;

/* compiled from: MeshHubService */
public class c extends g {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long a = 0;
    private MeshEncryptInfoUtil b;
    private Context c;
    private String d = null;

    public c(Context context, MeshDataManager meshDataManager) {
        this.c = context;
        this.b = new MeshEncryptInfoUtil(context, meshDataManager);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0050, code lost:
        if (r5.equals("getNetworkEncryptInfo") != false) goto L_0x005e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(android.app.Activity r14, java.lang.String r15, java.lang.String r16, java.lang.String r17) {
        /*
            r13 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r14
            r10 = 1
            r2[r10] = r15
            r11 = 2
            r2[r11] = r16
            r3 = 3
            r2[r3] = r17
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<android.app.Activity> r1 = android.app.Activity.class
            r7[r9] = r1
            r7[r10] = r0
            r7[r11] = r0
            r7[r3] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 8477(0x211d, float:1.1879E-41)
            r3 = r13
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002e
            return
        L_0x002e:
            r1 = r13
            r2 = r15
            r3 = r17
            r4 = r14
            r5 = r16
            r6 = 0
            r7 = 0
            int r0 = r5.hashCode()
            r8 = -1
            switch(r0) {
                case -1883970744: goto L_0x0053;
                case 760248219: goto L_0x004a;
                case 910959880: goto L_0x0040;
                default: goto L_0x003f;
            }
        L_0x003f:
            goto L_0x005d
        L_0x0040:
            java.lang.String r0 = "getDeviceAddress"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x003f
            r9 = r10
            goto L_0x005e
        L_0x004a:
            java.lang.String r0 = "getNetworkEncryptInfo"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x003f
            goto L_0x005e
        L_0x0053:
            java.lang.String r0 = "getProvisionerAddress"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x003f
            r9 = r11
            goto L_0x005e
        L_0x005d:
            r9 = r8
        L_0x005e:
            java.lang.String r0 = "mac"
            switch(r9) {
                case 0: goto L_0x00cd;
                case 1: goto L_0x0098;
                case 2: goto L_0x0065;
                default: goto L_0x0063;
            }
        L_0x0063:
            goto L_0x0122
        L_0x0065:
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0092 }
            r8.<init>((java.lang.String) r3)     // Catch:{ JSONException -> 0x0092 }
            r6 = r8
            java.lang.String r8 = ""
            java.lang.String r0 = r6.optString(r0, r8)     // Catch:{ JSONException -> 0x0092 }
            com.leedarson.serviceimpl.strategys.f r8 = new com.leedarson.serviceimpl.strategys.f     // Catch:{ JSONException -> 0x0092 }
            r8.<init>()     // Catch:{ JSONException -> 0x0092 }
            int r8 = r8.c(r0)     // Catch:{ JSONException -> 0x0092 }
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0092 }
            r9.<init>()     // Catch:{ JSONException -> 0x0092 }
            r7 = r9
            java.lang.String r9 = "provisionerAddr"
            r7.put((java.lang.String) r9, (int) r8)     // Catch:{ JSONException -> 0x0092 }
            org.json.JSONObject r9 = meshsdk.BaseResp.generatorSuccessResp(r7)     // Catch:{ JSONException -> 0x0092 }
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x0092 }
            r1.postJsBridgeCallback(r2, r9, r5)     // Catch:{ JSONException -> 0x0092 }
            goto L_0x0122
        L_0x0092:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0122
        L_0x0098:
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ Exception -> 0x00bd }
            r8.<init>((java.lang.String) r3)     // Catch:{ Exception -> 0x00bd }
            r6 = r8
            java.lang.String r0 = r6.optString(r0)     // Catch:{ Exception -> 0x00bd }
            int r8 = r1.a(r0)     // Catch:{ Exception -> 0x00bd }
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Exception -> 0x00bd }
            r9.<init>()     // Catch:{ Exception -> 0x00bd }
            r7 = r9
            java.lang.String r9 = "bleMeshAddr"
            r7.put((java.lang.String) r9, (int) r8)     // Catch:{ Exception -> 0x00bd }
            org.json.JSONObject r9 = meshsdk.BaseResp.generatorSuccessResp(r7)     // Catch:{ Exception -> 0x00bd }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x00bd }
            r1.postJsBridgeCallback(r2, r9, r5)     // Catch:{ Exception -> 0x00bd }
            goto L_0x0122
        L_0x00bd:
            r0 = move-exception
            r0.printStackTrace()
            org.json.JSONObject r8 = meshsdk.BaseResp.generatorSuccessResp(r7)
            java.lang.String r8 = r8.toString()
            r1.postJsBridgeCallback(r2, r8, r5)
            goto L_0x0122
        L_0x00cd:
            long r9 = java.lang.System.currentTimeMillis()
            long r11 = r1.a
            long r9 = r9 - r11
            r11 = 800(0x320, double:3.953E-321)
            int r0 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r0 > 0) goto L_0x00e8
            java.lang.String r0 = "距上次调用小于500ms，防抖处理"
            org.json.JSONObject r0 = meshsdk.BaseResp.generatorFailResp(r8, r0)
            java.lang.String r0 = r0.toString()
            r1.l(r2, r0)
            return
        L_0x00e8:
            long r8 = java.lang.System.currentTimeMillis()
            r1.a = r8
            r1.d = r2
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x011d }
            r0.<init>((java.lang.String) r3)     // Catch:{ JSONException -> 0x011d }
            r6 = r0
            java.lang.String r0 = "houseId"
            java.lang.String r0 = r6.optString(r0)     // Catch:{ JSONException -> 0x011d }
            meshsdk.util.MeshEncryptInfoUtil r8 = r1.b     // Catch:{ JSONException -> 0x011d }
            io.reactivex.l r8 = r8.getNetworkEncryptInfo(r0)     // Catch:{ JSONException -> 0x011d }
            io.reactivex.r r9 = com.leedarson.base.http.observer.l.g     // Catch:{ JSONException -> 0x011d }
            io.reactivex.l r9 = r8.b0(r9)     // Catch:{ JSONException -> 0x011d }
            io.reactivex.r r10 = com.leedarson.base.http.observer.l.g     // Catch:{ JSONException -> 0x011d }
            io.reactivex.l r9 = r9.J(r10)     // Catch:{ JSONException -> 0x011d }
            com.leedarson.serviceimpl.product.b r10 = new com.leedarson.serviceimpl.product.b     // Catch:{ JSONException -> 0x011d }
            r10.<init>(r1)     // Catch:{ JSONException -> 0x011d }
            com.leedarson.serviceimpl.product.a r11 = new com.leedarson.serviceimpl.product.a     // Catch:{ JSONException -> 0x011d }
            r11.<init>(r1)     // Catch:{ JSONException -> 0x011d }
            r9.Y(r10, r11)     // Catch:{ JSONException -> 0x011d }
            goto L_0x0122
        L_0x011d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0122:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.product.c.handleData(android.app.Activity, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public /* synthetic */ void i(JSONObject jsonObject) {
        if (!PatchProxy.proxy(new Object[]{jsonObject}, this, changeQuickRedirect, false, 8481, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            l(this.d, BaseResp.generatorSuccessResp(jsonObject).toString());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public /* synthetic */ void k(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 8480, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            MeshLog.e("meshHub getNetworkEncryptInfo 出错:" + throwable.getMessage());
            l(this.d, BaseResp.generatorSuccessResp(new NetworkEncryptInfo().toJSON()).toString());
        }
    }

    private int a(String mac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 8478, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        NodeInfo node = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, mac);
        if (node != null) {
            return node.meshAddress;
        }
        return new f().b(mac);
    }

    private void l(String callbackKey, String data) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, data}, this, changeQuickRedirect, false, 8479, clsArr, Void.TYPE).isSupported) {
            MeshLog.i("回复 mesh network info :" + data);
            postJsBridgeCallback(callbackKey, data, "getNetworkEncryptInfo");
            this.d = null;
        }
    }
}
