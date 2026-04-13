package com.leedarson.serviceimpl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo;
import com.google.chip.chiptool.util.MatterSpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.base.utils.p;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.bean.AddDeviceBean;
import com.leedarson.serviceimpl.bean.ControlDeviceBean;
import com.leedarson.serviceimpl.bean.DCLModelInfoBean;
import com.leedarson.serviceimpl.bean.FabricBean;
import com.leedarson.serviceimpl.ctrl.q;
import com.leedarson.serviceimpl.matterprocessors.OpenPairingWidowMatterProcessor;
import com.leedarson.serviceimpl.reporters.beans.MatterConfigStepBeans;
import com.leedarson.serviceimpl.reporters.j;
import com.leedarson.serviceinterface.MatterService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import demo.MatterListActivity;
import java.util.ArrayList;
import meshsdk.BaseResp;
import meshsdk.util.MeshConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MatterServiceImpl extends com.leedarson.serviceimpl.listener.c implements MatterService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private Activity b;
    public com.leedarson.serviceimpl.matterprocessors.a[] c = {new OpenPairingWidowMatterProcessor()};

    public void init(Context context) {
        this.a = context;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0125, code lost:
        if (r7.equals(com.leedarson.serviceinterface.MatterService.ON_GET_DEV_TYPE) != false) goto L_0x0129;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(android.app.Activity r17, java.lang.String r18, java.lang.String r19, java.lang.String r20) {
        /*
            r16 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r17
            r10 = 1
            r2[r10] = r18
            r11 = 2
            r2[r11] = r19
            r12 = 3
            r2[r12] = r20
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<android.app.Activity> r3 = android.app.Activity.class
            r7[r9] = r3
            r7[r10] = r0
            r7[r11] = r0
            r7[r12] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 6044(0x179c, float:8.47E-42)
            r3 = r16
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002f
            return
        L_0x002f:
            r13 = r16
            r14 = r18
            r15 = r20
            r8 = r17
            r7 = r19
            java.lang.String r0 = "Matter"
            timber.log.a$b r0 = timber.log.a.g(r0)
            java.lang.Object[] r2 = new java.lang.Object[r12]
            r2[r9] = r14
            r2[r10] = r7
            r2[r11] = r15
            java.lang.String r3 = "RX==>handleData callback:%s,action:%s,data:%s "
            r0.h(r3, r2)
            boolean r0 = com.leedarson.serviceimpl.j.a
            java.lang.String r2 = "info"
            r3 = -1
            java.lang.String r4 = ""
            if (r0 == 0) goto L_0x008a
            com.leedarson.serviceimpl.k r0 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r5 = "intercept jsbridge when isPairing,action="
            r1.append(r5)
            r1.append(r7)
            java.lang.String r5 = ",data:"
            r1.append(r5)
            r1.append(r15)
            java.lang.String r1 = r1.toString()
            r0.j(r1, r2, r7, r4)
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.String r2 = "request intercepted when isPairing"
            org.json.JSONObject r2 = meshsdk.BaseResp.generatorFailResp(r3, r2)
            java.lang.String r2 = r2.toString()
            r1.<init>(r14, r2)
            r0.l(r1)
            return
        L_0x008a:
            int r0 = r7.hashCode()
            java.lang.String r5 = "addDevice"
            switch(r0) {
                case -1698910023: goto L_0x011f;
                case -1696839979: goto L_0x0114;
                case -1421170990: goto L_0x010a;
                case -1296592902: goto L_0x0100;
                case -442317225: goto L_0x00f8;
                case -56011720: goto L_0x00ed;
                case 102619978: goto L_0x00e2;
                case 126588703: goto L_0x00d8;
                case 441136659: goto L_0x00cd;
                case 829480164: goto L_0x00c2;
                case 1187776936: goto L_0x00b7;
                case 1194689176: goto L_0x00ab;
                case 1848811730: goto L_0x00a0;
                case 1912613350: goto L_0x0095;
                default: goto L_0x0093;
            }
        L_0x0093:
            goto L_0x0128
        L_0x0095:
            java.lang.String r0 = "parseManualCode"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = r12
            goto L_0x0129
        L_0x00a0:
            java.lang.String r0 = "getDCLModel"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = r1
            goto L_0x0129
        L_0x00ab:
            java.lang.String r0 = "addGroupMember"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = 10
            goto L_0x0129
        L_0x00b7:
            java.lang.String r0 = "parseBle"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = r11
            goto L_0x0129
        L_0x00c2:
            java.lang.String r0 = "syncDeviceProperties"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = 9
            goto L_0x0129
        L_0x00cd:
            java.lang.String r0 = "controlDevice"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = 8
            goto L_0x0129
        L_0x00d8:
            java.lang.String r0 = "disconnectBle"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = 7
            goto L_0x0129
        L_0x00e2:
            java.lang.String r0 = "removeFabrics"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = 13
            goto L_0x0129
        L_0x00ed:
            java.lang.String r0 = "getFabrics"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = 12
            goto L_0x0129
        L_0x00f8:
            boolean r0 = r7.equals(r5)
            if (r0 == 0) goto L_0x0093
            r9 = 5
            goto L_0x0129
        L_0x0100:
            java.lang.String r0 = "removeDevice"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = 6
            goto L_0x0129
        L_0x010a:
            java.lang.String r0 = "aesCTR"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = r10
            goto L_0x0129
        L_0x0114:
            java.lang.String r0 = "removeGroupMember"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            r9 = 11
            goto L_0x0129
        L_0x011f:
            java.lang.String r0 = "getDevType"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0093
            goto L_0x0129
        L_0x0128:
            r9 = r3
        L_0x0129:
            java.lang.String r0 = "groupId"
            java.lang.String r6 = "matterAddrs"
            java.lang.String r3 = "controlDetail"
            java.lang.String r1 = "配网参数出错:"
            r12 = 0
            java.lang.String r11 = "matterAddr"
            switch(r9) {
                case 0: goto L_0x0569;
                case 1: goto L_0x044b;
                case 2: goto L_0x0424;
                case 3: goto L_0x0401;
                case 4: goto L_0x03d8;
                case 5: goto L_0x0338;
                case 6: goto L_0x02be;
                case 7: goto L_0x02a2;
                case 8: goto L_0x0255;
                case 9: goto L_0x01fc;
                case 10: goto L_0x01b1;
                case 11: goto L_0x018e;
                case 12: goto L_0x0166;
                case 13: goto L_0x013b;
                default: goto L_0x0137;
            }
        L_0x0137:
            r9 = r7
            r0 = 0
            goto L_0x057e
        L_0x013b:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x015d }
            r0.<init>((java.lang.String) r15)     // Catch:{ JSONException -> 0x015d }
            long r4 = r0.optLong(r11)     // Catch:{ JSONException -> 0x015d }
            java.lang.String r1 = "fabricIndexs"
            org.json.JSONArray r6 = r0.getJSONArray(r1)     // Catch:{ JSONException -> 0x015d }
            com.leedarson.serviceimpl.ctrl.r r1 = new com.leedarson.serviceimpl.ctrl.r     // Catch:{ JSONException -> 0x015d }
            r2 = r1
            r3 = r8
            r9 = r7
            r7 = r14
            r10 = r8
            r8 = r13
            r2.<init>(r3, r4, r6, r7, r8)     // Catch:{ JSONException -> 0x015b }
            r1.i()     // Catch:{ JSONException -> 0x015b }
            r8 = r10
            goto L_0x0596
        L_0x015b:
            r0 = move-exception
            goto L_0x0160
        L_0x015d:
            r0 = move-exception
            r9 = r7
            r10 = r8
        L_0x0160:
            r0.printStackTrace()
            r8 = r10
            goto L_0x0596
        L_0x0166:
            r9 = r7
            r10 = r8
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0187 }
            r0.<init>((java.lang.String) r15)     // Catch:{ JSONException -> 0x0187 }
            long r1 = r0.optLong(r11)     // Catch:{ JSONException -> 0x0187 }
            r11 = r1
            com.leedarson.serviceimpl.i r1 = com.leedarson.serviceimpl.i.a     // Catch:{ JSONException -> 0x0187 }
            long r4 = r1.m()     // Catch:{ JSONException -> 0x0187 }
            com.leedarson.serviceimpl.MatterServiceImpl$i r8 = new com.leedarson.serviceimpl.MatterServiceImpl$i     // Catch:{ JSONException -> 0x0187 }
            r2 = r8
            r3 = r13
            r6 = r14
            r7 = r9
            r2.<init>(r4, r6, r7)     // Catch:{ JSONException -> 0x0187 }
            r1.C(r11, r8)     // Catch:{ JSONException -> 0x0187 }
            r8 = r10
            goto L_0x0596
        L_0x0187:
            r0 = move-exception
            r0.printStackTrace()
            r8 = r10
            goto L_0x0596
        L_0x018e:
            r9 = r7
            r10 = r8
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01aa }
            r1.<init>((java.lang.String) r15)     // Catch:{ JSONException -> 0x01aa }
            int r0 = r1.optInt(r0)     // Catch:{ JSONException -> 0x01aa }
            long r2 = r1.optLong(r11)     // Catch:{ JSONException -> 0x01aa }
            com.leedarson.serviceimpl.i r4 = com.leedarson.serviceimpl.i.a     // Catch:{ JSONException -> 0x01aa }
            com.leedarson.serviceimpl.MatterServiceImpl$h r5 = new com.leedarson.serviceimpl.MatterServiceImpl$h     // Catch:{ JSONException -> 0x01aa }
            r5.<init>(r14, r9)     // Catch:{ JSONException -> 0x01aa }
            r4.E(r2, r0, r5)     // Catch:{ JSONException -> 0x01aa }
            r8 = r10
            goto L_0x0596
        L_0x01aa:
            r0 = move-exception
            r0.printStackTrace()
            r8 = r10
            goto L_0x0596
        L_0x01b1:
            r9 = r7
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01f6 }
            r1.<init>((java.lang.String) r15)     // Catch:{ JSONException -> 0x01f6 }
            int r0 = r1.optInt(r0)     // Catch:{ JSONException -> 0x01f6 }
            java.lang.String r2 = "testcase"
            int r2 = r1.optInt(r2)     // Catch:{ JSONException -> 0x01f6 }
            long r3 = r1.optLong(r11)     // Catch:{ JSONException -> 0x01f6 }
            if (r2 != 0) goto L_0x01d2
            com.leedarson.serviceimpl.i r5 = com.leedarson.serviceimpl.i.a     // Catch:{ JSONException -> 0x01f6 }
            com.leedarson.serviceimpl.MatterServiceImpl$g r6 = new com.leedarson.serviceimpl.MatterServiceImpl$g     // Catch:{ JSONException -> 0x01f6 }
            r6.<init>(r14, r9)     // Catch:{ JSONException -> 0x01f6 }
            r5.d(r3, r0, r6)     // Catch:{ JSONException -> 0x01f6 }
            goto L_0x01f4
        L_0x01d2:
            if (r2 != r10) goto L_0x01da
            com.leedarson.serviceimpl.i r5 = com.leedarson.serviceimpl.i.a     // Catch:{ JSONException -> 0x01f6 }
            r5.p(r3, r0, r12)     // Catch:{ JSONException -> 0x01f6 }
            goto L_0x01f4
        L_0x01da:
            r5 = 2
            if (r2 != r5) goto L_0x01e3
            com.leedarson.serviceimpl.i r5 = com.leedarson.serviceimpl.i.a     // Catch:{ JSONException -> 0x01f6 }
            r5.t(r3, r0, r12)     // Catch:{ JSONException -> 0x01f6 }
            goto L_0x01f4
        L_0x01e3:
            r5 = 3
            if (r2 != r5) goto L_0x01ec
            com.leedarson.serviceimpl.i r5 = com.leedarson.serviceimpl.i.a     // Catch:{ JSONException -> 0x01f6 }
            r5.e(r3, r0, r12)     // Catch:{ JSONException -> 0x01f6 }
            goto L_0x01f4
        L_0x01ec:
            r5 = 4
            if (r2 != r5) goto L_0x01f4
            com.leedarson.serviceimpl.i r5 = com.leedarson.serviceimpl.i.a     // Catch:{ JSONException -> 0x01f6 }
            r5.N(r3, r0, r12)     // Catch:{ JSONException -> 0x01f6 }
        L_0x01f4:
            goto L_0x0596
        L_0x01f6:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0596
        L_0x01fc:
            r9 = r7
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x024f }
            r0.<init>((java.lang.String) r15)     // Catch:{ JSONException -> 0x024f }
            org.json.JSONArray r1 = r0.getJSONArray(r6)     // Catch:{ JSONException -> 0x024f }
            if (r1 != 0) goto L_0x0217
            java.lang.String r2 = "matterAddrs 参数为空"
            r3 = -1
            org.json.JSONObject r2 = meshsdk.BaseResp.generatorFailResp(r3, r2)     // Catch:{ JSONException -> 0x024f }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x024f }
            r13.postJsBridgeCallback(r14, r2, r9)     // Catch:{ JSONException -> 0x024f }
            goto L_0x024d
        L_0x0217:
            int r2 = r1.length()     // Catch:{ JSONException -> 0x024f }
            long[] r2 = new long[r2]     // Catch:{ JSONException -> 0x024f }
            r3 = 0
        L_0x021e:
            int r4 = r1.length()     // Catch:{ JSONException -> 0x024f }
            if (r3 >= r4) goto L_0x023d
            java.lang.Object r4 = r1.get(r3)     // Catch:{ JSONException -> 0x024f }
            boolean r5 = r4 instanceof java.lang.Integer     // Catch:{ JSONException -> 0x024f }
            if (r5 == 0) goto L_0x0234
            int r5 = r1.getInt(r3)     // Catch:{ JSONException -> 0x024f }
            long r6 = (long) r5     // Catch:{ JSONException -> 0x024f }
            r2[r3] = r6     // Catch:{ JSONException -> 0x024f }
            goto L_0x023a
        L_0x0234:
            long r5 = r1.getLong(r3)     // Catch:{ JSONException -> 0x024f }
            r2[r3] = r5     // Catch:{ JSONException -> 0x024f }
        L_0x023a:
            int r3 = r3 + 1
            goto L_0x021e
        L_0x023d:
            com.leedarson.serviceimpl.manager.d r3 = com.leedarson.serviceimpl.manager.d.a     // Catch:{ JSONException -> 0x024f }
            r3.k(r2)     // Catch:{ JSONException -> 0x024f }
            org.json.JSONObject r3 = com.leedarson.base.utils.p.c()     // Catch:{ JSONException -> 0x024f }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x024f }
            r13.postJsBridgeCallback(r14, r3, r9)     // Catch:{ JSONException -> 0x024f }
        L_0x024d:
            goto L_0x0596
        L_0x024f:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0596
        L_0x0255:
            r9 = r7
            com.google.gson.Gson r0 = new com.google.gson.Gson
            r0.<init>()
            com.leedarson.serviceimpl.MatterServiceImpl$e r1 = new com.leedarson.serviceimpl.MatterServiceImpl$e
            r1.<init>()
            java.lang.reflect.Type r1 = r1.getType()
            java.lang.Object r0 = r0.fromJson((java.lang.String) r15, (java.lang.reflect.Type) r1)
            com.leedarson.serviceimpl.bean.ControlDeviceBean r0 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r0
            com.leedarson.serviceimpl.reporters.j r5 = new com.leedarson.serviceimpl.reporters.j
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            long r6 = r0.getMatterAddr()
            r1.append(r6)
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            r5.<init>(r4, r1)
            com.leedarson.serviceimpl.reporters.beans.MatterConfigStepBeans r1 = new com.leedarson.serviceimpl.reporters.beans.MatterConfigStepBeans
            java.lang.String r2 = "DeviceControl.control"
            r4 = 200(0xc8, float:2.8E-43)
            r1.<init>(r2, r4)
            r1.putRequestParams(r3, r15)
            r1.beginTrace()
            com.leedarson.serviceimpl.i r10 = com.leedarson.serviceimpl.i.a
            com.leedarson.serviceimpl.MatterServiceImpl$f r11 = new com.leedarson.serviceimpl.MatterServiceImpl$f
            r2 = r11
            r3 = r13
            r4 = r1
            r6 = r14
            r7 = r9
            r2.<init>(r4, r5, r6, r7)
            r10.f(r0, r11)
            goto L_0x0596
        L_0x02a2:
            r9 = r7
            com.leedarson.serviceimpl.i r0 = com.leedarson.serviceimpl.i.a
            r0.g()
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            org.json.JSONObject r2 = com.leedarson.base.utils.p.c()
            java.lang.String r2 = r2.toString()
            r1.<init>(r14, r2)
            r0.l(r1)
            goto L_0x0596
        L_0x02be:
            r9 = r7
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x02f2 }
            r0.<init>((java.lang.String) r15)     // Catch:{ Exception -> 0x02f2 }
            org.json.JSONArray r2 = r0.getJSONArray(r6)     // Catch:{ Exception -> 0x02f2 }
            com.leedarson.serviceimpl.ctrl.q r5 = new com.leedarson.serviceimpl.ctrl.q     // Catch:{ Exception -> 0x02f2 }
            r5.<init>(r2, r14, r13)     // Catch:{ Exception -> 0x02f2 }
            com.leedarson.serviceimpl.reporters.j r6 = new com.leedarson.serviceimpl.reporters.j     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r7 = r2.toString()     // Catch:{ Exception -> 0x02f2 }
            r6.<init>(r4, r7)     // Catch:{ Exception -> 0x02f2 }
            com.leedarson.serviceimpl.reporters.beans.MatterConfigStepBeans r7 = new com.leedarson.serviceimpl.reporters.beans.MatterConfigStepBeans     // Catch:{ Exception -> 0x02f2 }
            java.lang.String r10 = "DeviceControl.removeDevice"
            r11 = 200(0xc8, float:2.8E-43)
            r7.<init>(r10, r11)     // Catch:{ Exception -> 0x02f2 }
            r7.putRequestParams(r3, r15)     // Catch:{ Exception -> 0x02f2 }
            r7.beginTrace()     // Catch:{ Exception -> 0x02f2 }
            com.leedarson.serviceimpl.MatterServiceImpl$d r3 = new com.leedarson.serviceimpl.MatterServiceImpl$d     // Catch:{ Exception -> 0x02f2 }
            r3.<init>(r7, r6)     // Catch:{ Exception -> 0x02f2 }
            r5.h(r3)     // Catch:{ Exception -> 0x02f2 }
            r5.e()     // Catch:{ Exception -> 0x02f2 }
            goto L_0x0596
        L_0x02f2:
            r0 = move-exception
            com.leedarson.serviceimpl.k r2 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "参数出错:"
            r3.append(r5)
            java.lang.String r5 = r0.toString()
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            r2.c(r3, r4)
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r3 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            java.lang.String r1 = r0.toString()
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            r4 = 400(0x190, float:5.6E-43)
            org.json.JSONObject r1 = com.leedarson.base.utils.p.a(r4, r1)
            java.lang.String r1 = r1.toString()
            r3.<init>(r14, r1)
            r2.l(r3)
            goto L_0x0596
        L_0x0338:
            r9 = r7
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x0394 }
            java.lang.Class<com.leedarson.serviceinterface.BleMeshService> r3 = com.leedarson.serviceinterface.BleMeshService.class
            java.lang.Object r0 = r0.g(r3)     // Catch:{ Exception -> 0x0394 }
            com.leedarson.serviceinterface.BleMeshService r0 = (com.leedarson.serviceinterface.BleMeshService) r0     // Catch:{ Exception -> 0x0394 }
            if (r0 == 0) goto L_0x034c
            java.lang.String r3 = "matter web->addDevice"
            r0.stopScan(r3)     // Catch:{ Exception -> 0x0394 }
        L_0x034c:
            com.leedarson.serviceimpl.k r3 = com.leedarson.serviceimpl.k.a     // Catch:{ Exception -> 0x0394 }
            r3.j(r15, r2, r5, r4)     // Catch:{ Exception -> 0x0394 }
            com.google.gson.Gson r2 = new com.google.gson.Gson     // Catch:{ Exception -> 0x0394 }
            r2.<init>()     // Catch:{ Exception -> 0x0394 }
            com.leedarson.serviceimpl.MatterServiceImpl$c r3 = new com.leedarson.serviceimpl.MatterServiceImpl$c     // Catch:{ Exception -> 0x0394 }
            r3.<init>()     // Catch:{ Exception -> 0x0394 }
            java.lang.reflect.Type r3 = r3.getType()     // Catch:{ Exception -> 0x0394 }
            java.lang.Object r2 = r2.fromJson((java.lang.String) r15, (java.lang.reflect.Type) r3)     // Catch:{ Exception -> 0x0394 }
            com.leedarson.serviceimpl.bean.AddDeviceBean r2 = (com.leedarson.serviceimpl.bean.AddDeviceBean) r2     // Catch:{ Exception -> 0x0394 }
            com.leedarson.serviceimpl.ctrl.i r3 = new com.leedarson.serviceimpl.ctrl.i     // Catch:{ Exception -> 0x0394 }
            r3.<init>(r2, r14, r13, r8)     // Catch:{ Exception -> 0x0394 }
            com.leedarson.serviceimpl.manager.d r5 = com.leedarson.serviceimpl.manager.d.a     // Catch:{ Exception -> 0x0394 }
            r5.e()     // Catch:{ Exception -> 0x0394 }
            com.leedarson.serviceimpl.reporters.i r5 = new com.leedarson.serviceimpl.reporters.i     // Catch:{ Exception -> 0x0394 }
            java.lang.String r6 = r2.getTraceId()     // Catch:{ Exception -> 0x0394 }
            r5.<init>(r6, r15)     // Catch:{ Exception -> 0x0394 }
            com.leedarson.serviceimpl.reporters.beans.MatterConfigStepBeans r6 = new com.leedarson.serviceimpl.reporters.beans.MatterConfigStepBeans     // Catch:{ Exception -> 0x0394 }
            java.lang.String r7 = "JsCallNativeAddDevice"
            r10 = 200(0xc8, float:2.8E-43)
            r6.<init>(r7, r10)     // Catch:{ Exception -> 0x0394 }
            r6.beginTrace()     // Catch:{ Exception -> 0x0394 }
            java.lang.String r7 = "调用添加网络成功"
            r6.endTrace(r7, r10)     // Catch:{ Exception -> 0x0394 }
            r5.c(r6)     // Catch:{ Exception -> 0x0394 }
            r3.q(r5)     // Catch:{ Exception -> 0x0394 }
            r3.e()     // Catch:{ Exception -> 0x0394 }
            goto L_0x0596
        L_0x0394:
            r0 = move-exception
            com.leedarson.serviceimpl.k r2 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r1)
            java.lang.String r5 = r0.toString()
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            r2.c(r3, r4)
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r3 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            java.lang.String r1 = r0.toString()
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            r4 = 400(0x190, float:5.6E-43)
            org.json.JSONObject r1 = com.leedarson.base.utils.p.a(r4, r1)
            java.lang.String r1 = r1.toString()
            r3.<init>(r14, r1)
            r2.l(r3)
            goto L_0x0596
        L_0x03d8:
            r9 = r7
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x03fb }
            r0.<init>((java.lang.String) r15)     // Catch:{ JSONException -> 0x03fb }
            java.lang.String r1 = "vendorId"
            java.lang.String r1 = r0.optString(r1)     // Catch:{ JSONException -> 0x03fb }
            java.lang.String r2 = "productId"
            java.lang.String r2 = r0.optString(r2)     // Catch:{ JSONException -> 0x03fb }
            com.leedarson.serviceimpl.i r3 = com.leedarson.serviceimpl.i.a     // Catch:{ JSONException -> 0x03fb }
            com.leedarson.serviceimpl.MatterServiceImpl$a r4 = new com.leedarson.serviceimpl.MatterServiceImpl$a     // Catch:{ JSONException -> 0x03fb }
            r4.<init>(r14)     // Catch:{ JSONException -> 0x03fb }
            com.leedarson.serviceimpl.MatterServiceImpl$b r5 = new com.leedarson.serviceimpl.MatterServiceImpl$b     // Catch:{ JSONException -> 0x03fb }
            r5.<init>(r14)     // Catch:{ JSONException -> 0x03fb }
            r3.A(r1, r2, r4, r5)     // Catch:{ JSONException -> 0x03fb }
            goto L_0x0596
        L_0x03fb:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0596
        L_0x0401:
            r9 = r7
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x041e }
            r0.<init>((java.lang.String) r15)     // Catch:{ JSONException -> 0x041e }
            java.lang.String r1 = "manualCode"
            java.lang.String r1 = r0.optString(r1)     // Catch:{ JSONException -> 0x041e }
            com.leedarson.serviceimpl.i r2 = com.leedarson.serviceimpl.i.a     // Catch:{ JSONException -> 0x041e }
            com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo r2 = r2.y(r1)     // Catch:{ JSONException -> 0x041e }
            com.leedarson.serviceimpl.k r3 = com.leedarson.serviceimpl.k.a     // Catch:{ JSONException -> 0x041e }
            java.lang.String r5 = r2.getFormat()     // Catch:{ JSONException -> 0x041e }
            r3.g(r5, r4)     // Catch:{ JSONException -> 0x041e }
            goto L_0x0596
        L_0x041e:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0596
        L_0x0424:
            r9 = r7
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0445 }
            r0.<init>((java.lang.String) r15)     // Catch:{ JSONException -> 0x0445 }
            java.lang.String r1 = "advertisingData"
            java.lang.String r1 = r0.optString(r1)     // Catch:{ JSONException -> 0x0445 }
            com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo$Companion r2 = com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo.Companion     // Catch:{ JSONException -> 0x0445 }
            byte[] r3 = com.leedarson.base.utils.e.g(r1)     // Catch:{ JSONException -> 0x0445 }
            com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo r2 = r2.fromScanRecord(r3)     // Catch:{ JSONException -> 0x0445 }
            com.leedarson.serviceimpl.k r3 = com.leedarson.serviceimpl.k.a     // Catch:{ JSONException -> 0x0445 }
            java.lang.String r5 = r2.getFormat()     // Catch:{ JSONException -> 0x0445 }
            r3.g(r5, r4)     // Catch:{ JSONException -> 0x0445 }
            goto L_0x0596
        L_0x0445:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0596
        L_0x044b:
            r9 = r7
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x054a }
            r0.<init>((java.lang.String) r15)     // Catch:{ Exception -> 0x054a }
            java.lang.String r1 = "content"
            java.lang.String r1 = r0.optString(r1)     // Catch:{ Exception -> 0x054a }
            java.lang.String r2 = "aesKey"
            java.lang.String r2 = r0.optString(r2)     // Catch:{ Exception -> 0x054a }
            java.lang.String r3 = "iv"
            java.lang.String r3 = r0.optString(r3)     // Catch:{ Exception -> 0x054a }
            java.lang.String r5 = "aesType"
            int r5 = r0.optInt(r5)     // Catch:{ Exception -> 0x054a }
            java.lang.String r6 = "encrypt"
            boolean r6 = r0.optBoolean(r6, r10)     // Catch:{ Exception -> 0x054a }
            java.lang.String r7 = "\\"
            java.lang.String r7 = r1.replace(r7, r4)     // Catch:{ Exception -> 0x054a }
            com.leedarson.serviceimpl.k r10 = com.leedarson.serviceimpl.k.a     // Catch:{ Exception -> 0x054a }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x054a }
            r11.<init>()     // Catch:{ Exception -> 0x054a }
            java.lang.String r12 = "key:"
            r11.append(r12)     // Catch:{ Exception -> 0x054a }
            r11.append(r2)     // Catch:{ Exception -> 0x054a }
            java.lang.String r12 = ",str:"
            r11.append(r12)     // Catch:{ Exception -> 0x054a }
            r11.append(r7)     // Catch:{ Exception -> 0x054a }
            java.lang.String r12 = ",type:"
            r11.append(r12)     // Catch:{ Exception -> 0x054a }
            r11.append(r5)     // Catch:{ Exception -> 0x054a }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x054a }
            r10.c(r11, r4)     // Catch:{ Exception -> 0x054a }
            java.lang.String r11 = "ivHex:"
            if (r6 == 0) goto L_0x04e7
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x054a }
            r12.<init>()     // Catch:{ Exception -> 0x054a }
            r12.append(r11)     // Catch:{ Exception -> 0x054a }
            byte[] r11 = r3.getBytes()     // Catch:{ Exception -> 0x054a }
            java.lang.String r11 = com.leedarson.base.utils.e.a(r11)     // Catch:{ Exception -> 0x054a }
            r12.append(r11)     // Catch:{ Exception -> 0x054a }
            java.lang.String r11 = r12.toString()     // Catch:{ Exception -> 0x054a }
            r10.c(r11, r4)     // Catch:{ Exception -> 0x054a }
            byte[] r11 = r3.getBytes()     // Catch:{ Exception -> 0x054a }
            if (r5 != 0) goto L_0x04c2
            com.leedarson.base.utils.b$a r12 = com.leedarson.base.utils.b.a.AES256     // Catch:{ Exception -> 0x054a }
            goto L_0x04c4
        L_0x04c2:
            com.leedarson.base.utils.b$a r12 = com.leedarson.base.utils.b.a.AES128     // Catch:{ Exception -> 0x054a }
        L_0x04c4:
            r17 = r0
            byte[] r0 = r7.getBytes()     // Catch:{ Exception -> 0x054a }
            byte[] r0 = com.leedarson.base.utils.b.g(r11, r12, r2, r0)     // Catch:{ Exception -> 0x054a }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x054a }
            r11.<init>()     // Catch:{ Exception -> 0x054a }
            java.lang.String r12 = "aes ctr encrypt :"
            r11.append(r12)     // Catch:{ Exception -> 0x054a }
            java.lang.String r12 = com.leedarson.base.utils.e.a(r0)     // Catch:{ Exception -> 0x054a }
            r11.append(r12)     // Catch:{ Exception -> 0x054a }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x054a }
            r10.c(r11, r4)     // Catch:{ Exception -> 0x054a }
            goto L_0x0549
        L_0x04e7:
            r17 = r0
            byte[] r0 = com.leedarson.base.utils.e.g(r7)     // Catch:{ Exception -> 0x054a }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x054a }
            r12.<init>()     // Catch:{ Exception -> 0x054a }
            r12.append(r11)     // Catch:{ Exception -> 0x054a }
            byte[] r11 = r3.getBytes()     // Catch:{ Exception -> 0x054a }
            java.lang.String r11 = com.leedarson.base.utils.e.a(r11)     // Catch:{ Exception -> 0x054a }
            r12.append(r11)     // Catch:{ Exception -> 0x054a }
            java.lang.String r11 = ",待解密数据:"
            r12.append(r11)     // Catch:{ Exception -> 0x054a }
            java.lang.String r11 = com.leedarson.base.utils.e.a(r0)     // Catch:{ Exception -> 0x054a }
            r12.append(r11)     // Catch:{ Exception -> 0x054a }
            java.lang.String r11 = r12.toString()     // Catch:{ Exception -> 0x054a }
            r10.c(r11, r4)     // Catch:{ Exception -> 0x054a }
            byte[] r11 = r3.getBytes()     // Catch:{ Exception -> 0x054a }
            if (r5 != 0) goto L_0x051c
            com.leedarson.base.utils.b$a r12 = com.leedarson.base.utils.b.a.AES256     // Catch:{ Exception -> 0x054a }
            goto L_0x051e
        L_0x051c:
            com.leedarson.base.utils.b$a r12 = com.leedarson.base.utils.b.a.AES128     // Catch:{ Exception -> 0x054a }
        L_0x051e:
            byte[] r11 = com.leedarson.base.utils.b.c(r11, r12, r2, r0)     // Catch:{ Exception -> 0x054a }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x054a }
            r12.<init>()     // Catch:{ Exception -> 0x054a }
            r18 = r0
            java.lang.String r0 = "aes ctr decrypt :"
            r12.append(r0)     // Catch:{ Exception -> 0x054a }
            java.lang.String r0 = com.leedarson.base.utils.e.a(r11)     // Catch:{ Exception -> 0x054a }
            r12.append(r0)     // Catch:{ Exception -> 0x054a }
            java.lang.String r0 = ",utf-8:"
            r12.append(r0)     // Catch:{ Exception -> 0x054a }
            java.lang.String r0 = new java.lang.String     // Catch:{ Exception -> 0x054a }
            r0.<init>(r11)     // Catch:{ Exception -> 0x054a }
            r12.append(r0)     // Catch:{ Exception -> 0x054a }
            java.lang.String r0 = r12.toString()     // Catch:{ Exception -> 0x054a }
            r10.c(r0, r4)     // Catch:{ Exception -> 0x054a }
        L_0x0549:
            goto L_0x0596
        L_0x054a:
            r0 = move-exception
            r0.printStackTrace()
            com.leedarson.serviceimpl.k r1 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "encryptCTR ex:"
            r2.append(r3)
            java.lang.String r3 = r0.toString()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.c(r2, r4)
            goto L_0x0596
        L_0x0569:
            r9 = r7
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0579 }
            r0.<init>((java.lang.String) r15)     // Catch:{ JSONException -> 0x0579 }
            long r1 = r0.optLong(r11)     // Catch:{ JSONException -> 0x0579 }
            com.leedarson.serviceimpl.i r3 = com.leedarson.serviceimpl.i.a     // Catch:{ JSONException -> 0x0579 }
            r3.k(r1, r12)     // Catch:{ JSONException -> 0x0579 }
            goto L_0x0596
        L_0x0579:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0596
        L_0x057e:
            com.leedarson.serviceimpl.matterprocessors.a[] r1 = r13.c
            int r2 = r1.length
            if (r0 >= r2) goto L_0x0596
            r1 = r1[r0]
            boolean r1 = r1.c(r9)
            if (r1 == 0) goto L_0x0593
            com.leedarson.serviceimpl.matterprocessors.a[] r1 = r13.c
            r1 = r1[r0]
            r1.a(r8, r14, r9, r15)
            goto L_0x0596
        L_0x0593:
            int r0 = r0 + 1
            goto L_0x057e
        L_0x0596:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.MatterServiceImpl.handleData(android.app.Activity, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public class a implements io.reactivex.functions.e<DCLModelInfoBean> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        a(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6056, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((DCLModelInfoBean) obj);
            }
        }

        public void a(DCLModelInfoBean modelInfoBean) {
            if (!PatchProxy.proxy(new Object[]{modelInfoBean}, this, changeQuickRedirect, false, 6055, new Class[]{DCLModelInfoBean.class}, Void.TYPE).isSupported) {
                if (modelInfoBean == null || modelInfoBean.getVid().intValue() < 0) {
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, p.a(BaseResp.ERR_MSG_SEND_FAIL, modelInfoBean.getErrJson()).toString()));
                } else {
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, p.d(modelInfoBean.asJSON()).toString()));
                }
            }
        }
    }

    public class b implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        b(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6058, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 6057, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                org.greenrobot.eventbus.c c2 = org.greenrobot.eventbus.c.c();
                String str = this.c;
                c2.l(new JsBridgeCallbackEvent(str, p.a(BaseResp.ERR_MSG_SEND_FAIL, "接口出错:" + throwable.toString()).toString()));
            }
        }
    }

    public class c extends TypeToken<AddDeviceBean> {
        c() {
        }
    }

    public class d implements q.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MatterConfigStepBeans a;
        final /* synthetic */ j b;

        d(MatterConfigStepBeans matterConfigStepBeans, j jVar) {
            this.a = matterConfigStepBeans;
            this.b = jVar;
        }

        public void onStart() {
        }

        public void onFail(@NonNull String data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6059, new Class[]{String.class}, Void.TYPE).isSupported) {
                this.a.endTrace(data, BaseResp.ERR_MSG_SEND_FAIL);
                this.b.a.add(this.a);
                this.b.b();
            }
        }

        public void onSuccess(@NonNull String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6060, new Class[]{String.class}, Void.TYPE).isSupported) {
                this.a.endTrace("删除成功", 200);
                this.b.a.add(this.a);
                this.b.b();
            }
        }
    }

    public class e extends TypeToken<ControlDeviceBean> {
        e() {
        }
    }

    public class f implements com.leedarson.serviceimpl.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MatterConfigStepBeans a;
        final /* synthetic */ j b;
        final /* synthetic */ String c;
        final /* synthetic */ String d;

        f(MatterConfigStepBeans matterConfigStepBeans, j jVar, String str, String str2) {
            this.a = matterConfigStepBeans;
            this.b = jVar;
            this.c = str;
            this.d = str2;
        }

        public void onSuccess(long j, Object obj) {
            Object[] objArr = {new Long(j), obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6061, new Class[]{Long.TYPE, Object.class}, Void.TYPE).isSupported) {
                this.a.endTrace("控制成功", 200);
                this.b.a.add(this.a);
                this.b.b();
                MatterServiceImpl.this.postJsBridgeCallback(this.c, p.c().toString(), this.d);
            }
        }

        public void onFail(int code, Exception ex) {
            Object[] objArr = {new Integer(code), ex};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6062, new Class[]{Integer.TYPE, Exception.class}, Void.TYPE).isSupported) {
                this.a.endTrace("控制失败", code);
                MatterConfigStepBeans matterConfigStepBeans = this.a;
                matterConfigStepBeans.setResponse("code=" + code + "  exception=" + ex.toString());
                this.b.a.add(this.a);
                this.b.b();
                MatterServiceImpl.this.postJsBridgeCallback(this.c, p.a(-1, ex.toString()).toString(), this.d);
            }
        }
    }

    public class g implements com.leedarson.serviceimpl.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        g(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(long j, Object obj) {
            Object[] objArr = {new Long(j), obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6063, new Class[]{Long.TYPE, Object.class}, Void.TYPE).isSupported) {
                MatterServiceImpl.this.postJsBridgeCallback(this.a, p.c().toString(), this.b);
            }
        }

        public void onFail(int i, Exception ex) {
            Object[] objArr = {new Integer(i), ex};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6064, new Class[]{Integer.TYPE, Exception.class}, Void.TYPE).isSupported) {
                MatterServiceImpl.this.postJsBridgeCallback(this.a, p.a(-1, ex.toString()).toString(), this.b);
            }
        }
    }

    public class h implements com.leedarson.serviceimpl.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        h(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(long j, Object obj) {
            Object[] objArr = {new Long(j), obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6065, new Class[]{Long.TYPE, Object.class}, Void.TYPE).isSupported) {
                MatterServiceImpl.this.postJsBridgeCallback(this.a, p.c().toString(), this.b);
            }
        }

        public void onFail(int i, Exception ex) {
            Object[] objArr = {new Integer(i), ex};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6066, new Class[]{Integer.TYPE, Exception.class}, Void.TYPE).isSupported) {
                MatterServiceImpl.this.postJsBridgeCallback(this.a, p.a(-1, ex.toString()).toString(), this.b);
            }
        }
    }

    public class i implements com.leedarson.serviceimpl.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        i(long j, String str, String str2) {
            this.a = j;
            this.b = str;
            this.c = str2;
        }

        public void onSuccess(long j, Object data) {
            Object[] objArr = {new Long(j), data};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6067, new Class[]{Long.TYPE, Object.class}, Void.TYPE).isSupported) {
                if (data instanceof ArrayList) {
                    try {
                        ArrayList<FabricBean> list = (ArrayList) data;
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).checkIsCurrentFabricIndex(this.a);
                        }
                        MatterServiceImpl.this.postJsBridgeCallback(this.b, p.d(new JSONArray(new Gson().toJson((Object) list))).toString(), this.c);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void onFail(int code, Exception ex) {
            Object[] objArr = {new Integer(code), ex};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6068, new Class[]{Integer.TYPE, Exception.class}, Void.TYPE).isSupported) {
                MatterServiceImpl.this.postJsBridgeCallback(this.b, p.a(code, ex.toString()).toString(), this.c);
            }
        }
    }

    public void initMatterSDK(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 6045, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            this.b = activity;
            i.a.u(activity);
            com.leedarson.serviceimpl.manager.d.a.h(this);
        }
    }

    public void uninitMatterSDK(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 6046, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.manager.d.a.j();
        }
    }

    public void openDebug(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 6047, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            activity.startActivity(new Intent(activity, MatterListActivity.class));
        }
    }

    public void onHouseChange(String newHouseId, String lastHouseId) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{newHouseId, lastHouseId}, this, changeQuickRedirect, false, 6048, clsArr, Void.TYPE).isSupported) {
            k kVar = k.a;
            kVar.g("onHouseChange newHouseId:" + newHouseId + ",lastHouseId:" + lastHouseId, "");
            MatterSpUtil.INSTANCE.setHouseId(this.a, newHouseId);
        }
    }

    public JSONObject parseQrcode(String code) {
        CHIPDeviceInfo chipDeviceInfo;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{code}, this, changeQuickRedirect, false, 6049, new Class[]{String.class}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        if (code == null || !code.startsWith("MT:") || (chipDeviceInfo = i.a.z(code)) == null) {
            return null;
        }
        return chipDeviceInfo.toJSON("qrcode");
    }

    public JSONObject parseBle(String serviceData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{serviceData}, this, changeQuickRedirect, false, 6050, new Class[]{String.class}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        CHIPDeviceInfo chipDeviceInfo = CHIPDeviceInfo.Companion.fromScanRecord(com.leedarson.base.utils.e.g(serviceData));
        if (chipDeviceInfo != null) {
            return chipDeviceInfo.toJSON("ble");
        }
        return null;
    }

    public void postJsBridgeCallback(String callbackKey, String message, String action) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, message, action}, this, changeQuickRedirect, false, 6051, clsArr, Void.TYPE).isSupported) {
            k kVar = k.a;
            kVar.c("TX==>handleData " + action + ":" + message, "");
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, message));
        }
    }

    public void postJsCallH5ByNative(String callbackkey, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackkey, message}, this, changeQuickRedirect, false, 6052, clsArr, Void.TYPE).isSupported) {
            k kVar = k.a;
            kVar.c("TX==>handleData " + callbackkey + ":" + message, "");
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_Matter, callbackkey, message));
        }
    }

    public void a(long nodeId, int online) {
        Object[] objArr = {new Long(nodeId), new Integer(online)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6053, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            JSONObject json = new JSONObject();
            try {
                json.put("matterAddr", nodeId);
                json.put(MeshConstants.AC_STATE_LOGIN_SUCCESS, online);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative("onDeviceOnlineChange", json.toString());
        }
    }

    public void h(long nodeId, JSONObject json) {
        Object[] objArr = {new Long(nodeId), json};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6054, new Class[]{Long.TYPE, JSONObject.class}, Void.TYPE).isSupported) {
            try {
                json.put("matterAddr", nodeId);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative(MatterService.ON_DEVICE_PROPERTIES_CHANGE, json.toString());
        }
    }
}
