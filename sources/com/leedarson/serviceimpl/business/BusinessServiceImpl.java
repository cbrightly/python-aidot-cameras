package com.leedarson.serviceimpl.business;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.beans.FeedbackDoneParamsBean;
import com.leedarson.base.beans.FeedbackRequestBean;
import com.leedarson.base.http.b;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.c;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.log.mgr.r;
import com.leedarson.serviceimpl.business.bean.NetDiagnosisDomain;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetDiagnoListener;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetDiagnoService;
import com.leedarson.serviceimpl.business.utils.DeviceUtils;
import com.leedarson.serviceimpl.business.utils.FileIOUtils;
import com.leedarson.serviceimpl.business.utils.PackageLocalLogFileUtil;
import com.leedarson.serviceimpl.business.utils.PhotoUtils;
import com.leedarson.serviceinterface.BusinessService;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.WiFiService;
import com.leedarson.serviceinterface.ZendeskService;
import com.leedarson.serviceinterface.event.AppLogoutEvent;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.util.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.functions.f;
import io.reactivex.l;
import io.reactivex.m;
import io.reactivex.o;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import meshsdk.BaseResp;
import okhttp3.MultipartBody;
import okhttp3.c0;
import okhttp3.x;
import okhttp3.y;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

public class BusinessServiceImpl implements BusinessService {
    private static final String ACTION_CHECK_UPDATE = "checkUpgrade";
    private static final String ACTION_MTR = "MTR";
    private static final String ACTION_PING = "ping";
    private static final String ACTION_SET_CONFIG = "setConfig";
    private static final String LISTENTAKESCREENSHOT = "listenTakeScreenshot";
    private static final String LOGOUT = "logout";
    private static final String ONNETWORK_DIAGNOSIS_MESSAGE = "onNetworkDiagnosisMessage";
    private static final String START_NETWORK_DIAGNOSIS = "startNetworkDiagnosis";
    private static final String STOP_NETWORK_DIAGNOSIS = "stopNetworkDiagnosis";
    private static final String TAG = "BusinessServiceImpl";
    private static final String TAKE_SCREENSHOT = "takeScreenshot";
    private static final String UPLOAD = "upload";
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDNetDiagnoService _netDiagnoService;
    String appId = "";
    String code = "";
    private Context context;
    String country = "";
    String fileId = "";
    JSONArray fileIdArray = new JSONArray();
    JSONArray filePath = new JSONArray();
    private String fileType;
    private JSONObject headers;
    private String httpServer;
    boolean isUploading = false;
    JSONObject joParams = null;
    String nativeVersion = "";
    private String netDiagCallback;
    private JSONObject netDiagDataObj;
    String os = "";
    String presignedUrl;
    String reportMethod = "";
    String reportUrl = "";
    Long timestamp;
    String webVersion = "";

    static /* synthetic */ void access$000(BusinessServiceImpl x0, String x1, JSONArray x2) {
        Class[] clsArr = {BusinessServiceImpl.class, String.class, JSONArray.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 7025, clsArr, Void.TYPE).isSupported) {
            x0.takeScreenShot(x1, x2);
        }
    }

    static /* synthetic */ void access$100(BusinessServiceImpl x0, String x1) {
        Class[] clsArr = {BusinessServiceImpl.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 7026, clsArr, Void.TYPE).isSupported) {
            x0.I(x1);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x037e A[SYNTHETIC, Splitter:B:166:0x037e] */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x03ab A[Catch:{ JSONException -> 0x0520 }] */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x0442 A[Catch:{ JSONException -> 0x051e }] */
    /* JADX WARNING: Removed duplicated region for block: B:211:0x044a A[Catch:{ JSONException -> 0x051e }] */
    /* JADX WARNING: Removed duplicated region for block: B:217:0x04ad A[Catch:{ JSONException -> 0x051e }] */
    /* JADX WARNING: Removed duplicated region for block: B:220:0x04b5 A[Catch:{ JSONException -> 0x051e }] */
    /* JADX WARNING: Removed duplicated region for block: B:223:0x04c6 A[Catch:{ JSONException -> 0x051e }] */
    /* JADX WARNING: Removed duplicated region for block: B:231:0x0500 A[Catch:{ JSONException -> 0x051e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r23, android.app.Activity r24, java.lang.String r25, java.lang.String r26) {
        /*
            r22 = this;
            java.lang.String r1 = "countryCode"
            java.lang.String r2 = "accessToken"
            java.lang.String r3 = "username"
            java.lang.String r4 = "staticHttpServer"
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            java.lang.String r5 = "houseId"
            java.lang.String r6 = "httpServer"
            java.lang.String r7 = "userId"
            java.lang.String r8 = "refreshToken"
            java.lang.String r9 = "owner"
            r10 = 4
            java.lang.Object[] r11 = new java.lang.Object[r10]
            r15 = 0
            r11[r15] = r23
            r18 = 1
            r11[r18] = r24
            r19 = 2
            r11[r19] = r25
            r20 = 3
            r11[r20] = r26
            com.meituan.robust.ChangeQuickRedirect r13 = changeQuickRedirect
            java.lang.Class[] r14 = new java.lang.Class[r10]
            r14[r15] = r0
            java.lang.Class<android.app.Activity> r12 = android.app.Activity.class
            r14[r18] = r12
            r14[r19] = r0
            r14[r20] = r0
            java.lang.Class r17 = java.lang.Void.TYPE
            r0 = 0
            r16 = 7003(0x1b5b, float:9.813E-42)
            r12 = r22
            r21 = r14
            r14 = r0
            r10 = r15
            r15 = r16
            r16 = r21
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r11, r12, r13, r14, r15, r16, r17)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x004c
            return
        L_0x004c:
            r11 = r22
            r12 = r24
            r13 = r26
            r14 = r23
            r15 = r25
            java.lang.String r0 = "BusinessServiceImpl"
            timber.log.a$b r0 = timber.log.a.g(r0)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r12 = "action:"
            r10.append(r12)
            r10.append(r15)
            java.lang.String r12 = " data:"
            r10.append(r12)
            r10.append(r13)
            java.lang.String r10 = r10.toString()
            r17 = r1
            r12 = 0
            java.lang.Object[] r1 = new java.lang.Object[r12]
            r0.h(r10, r1)
            r1 = 0
            boolean r0 = android.text.TextUtils.isEmpty(r13)     // Catch:{ JSONException -> 0x008b }
            if (r0 != 0) goto L_0x008a
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x008b }
            r0.<init>((java.lang.String) r13)     // Catch:{ JSONException -> 0x008b }
            r1 = r0
        L_0x008a:
            goto L_0x008f
        L_0x008b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x008f:
            r0 = -1
            int r10 = r15.hashCode()
            switch(r10) {
                case -2075195163: goto L_0x00f8;
                case -1489040251: goto L_0x00ed;
                case -1097329270: goto L_0x00e2;
                case -838595071: goto L_0x00d8;
                case -291826700: goto L_0x00cd;
                case 76683: goto L_0x00c3;
                case 3441010: goto L_0x00b9;
                case 126605892: goto L_0x00af;
                case 194959693: goto L_0x00a4;
                case 1482918644: goto L_0x0099;
                default: goto L_0x0097;
            }
        L_0x0097:
            goto L_0x0103
        L_0x0099:
            java.lang.String r10 = "listenTakeScreenshot"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x0097
            r10 = 5
            goto L_0x0104
        L_0x00a4:
            java.lang.String r10 = "takeScreenshot"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x0097
            r10 = 9
            goto L_0x0104
        L_0x00af:
            java.lang.String r10 = "setConfig"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x0097
            r10 = 0
            goto L_0x0104
        L_0x00b9:
            java.lang.String r10 = "ping"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x0097
            r10 = 7
            goto L_0x0104
        L_0x00c3:
            java.lang.String r10 = "MTR"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x0097
            r10 = 6
            goto L_0x0104
        L_0x00cd:
            java.lang.String r10 = "checkUpgrade"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x0097
            r10 = r18
            goto L_0x0104
        L_0x00d8:
            java.lang.String r10 = "upload"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x0097
            r10 = 4
            goto L_0x0104
        L_0x00e2:
            java.lang.String r10 = "logout"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x0097
            r10 = 8
            goto L_0x0104
        L_0x00ed:
            java.lang.String r10 = "stopNetworkDiagnosis"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x0097
            r10 = r20
            goto L_0x0104
        L_0x00f8:
            java.lang.String r10 = "startNetworkDiagnosis"
            boolean r10 = r15.equals(r10)
            if (r10 == 0) goto L_0x0097
            r10 = r19
            goto L_0x0104
        L_0x0103:
            r10 = r0
        L_0x0104:
            java.lang.String r12 = "code"
            java.lang.String r0 = "host"
            r25 = r15
            java.lang.String r15 = ""
            switch(r10) {
                case 0: goto L_0x028d;
                case 1: goto L_0x025c;
                case 2: goto L_0x0252;
                case 3: goto L_0x0226;
                case 4: goto L_0x01c3;
                case 5: goto L_0x017c;
                case 6: goto L_0x0152;
                case 7: goto L_0x012d;
                case 8: goto L_0x012a;
                case 9: goto L_0x0112;
                default: goto L_0x010f;
            }
        L_0x010f:
            r5 = r14
            goto L_0x055e
        L_0x0112:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0123 }
            r0.<init>((java.lang.String) r13)     // Catch:{ Exception -> 0x0123 }
            java.lang.String r2 = "frame"
            org.json.JSONArray r2 = r0.getJSONArray(r2)     // Catch:{ Exception -> 0x0123 }
            r11.takeScreenShot(r14, r2)     // Catch:{ Exception -> 0x0123 }
            r5 = r14
            goto L_0x055e
        L_0x0123:
            r0 = move-exception
            r0.printStackTrace()
            r5 = r14
            goto L_0x055e
        L_0x012a:
            r5 = r14
            goto L_0x055e
        L_0x012d:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x014b }
            r2.<init>((java.lang.String) r13)     // Catch:{ Exception -> 0x014b }
            boolean r3 = r2.has(r0)     // Catch:{ Exception -> 0x014b }
            if (r3 == 0) goto L_0x0148
            java.lang.String r0 = r2.getString(r0)     // Catch:{ Exception -> 0x014b }
            com.leedarson.serviceimpl.business.netease.MTR r3 = com.leedarson.serviceimpl.business.netease.MTR.getInstance()     // Catch:{ Exception -> 0x014b }
            com.leedarson.serviceimpl.business.BusinessServiceImpl$3 r4 = new com.leedarson.serviceimpl.business.BusinessServiceImpl$3     // Catch:{ Exception -> 0x014b }
            r4.<init>(r14)     // Catch:{ Exception -> 0x014b }
            r3.startPing(r0, r4)     // Catch:{ Exception -> 0x014b }
        L_0x0148:
            r5 = r14
            goto L_0x055e
        L_0x014b:
            r0 = move-exception
            r0.printStackTrace()
            r5 = r14
            goto L_0x055e
        L_0x0152:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0175 }
            r2.<init>((java.lang.String) r13)     // Catch:{ Exception -> 0x0175 }
            boolean r3 = r2.has(r0)     // Catch:{ Exception -> 0x0175 }
            if (r3 == 0) goto L_0x0172
            java.lang.String r0 = r2.getString(r0)     // Catch:{ Exception -> 0x0175 }
            org.json.JSONArray r3 = new org.json.JSONArray     // Catch:{ Exception -> 0x0175 }
            r3.<init>()     // Catch:{ Exception -> 0x0175 }
            com.leedarson.serviceimpl.business.netease.MTR r4 = com.leedarson.serviceimpl.business.netease.MTR.getInstance()     // Catch:{ Exception -> 0x0175 }
            com.leedarson.serviceimpl.business.BusinessServiceImpl$2 r5 = new com.leedarson.serviceimpl.business.BusinessServiceImpl$2     // Catch:{ Exception -> 0x0175 }
            r5.<init>(r3, r14)     // Catch:{ Exception -> 0x0175 }
            r4.startTraceRoute(r0, r5)     // Catch:{ Exception -> 0x0175 }
        L_0x0172:
            r5 = r14
            goto L_0x055e
        L_0x0175:
            r0 = move-exception
            r0.printStackTrace()
            r5 = r14
            goto L_0x055e
        L_0x017c:
            r2 = 0
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.NeedPermissionEvent r3 = new com.leedarson.serviceinterface.event.NeedPermissionEvent
            r4 = 70
            r3.<init>(r4, r15)
            r0.l(r3)
            boolean r0 = android.text.TextUtils.isEmpty(r13)     // Catch:{ JSONException -> 0x01bc }
            if (r0 != 0) goto L_0x0197
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01bc }
            r0.<init>((java.lang.String) r13)     // Catch:{ JSONException -> 0x01bc }
            r2 = r0
        L_0x0197:
            java.lang.String r0 = "buttons"
            boolean r0 = r2.has(r0)     // Catch:{ JSONException -> 0x01bc }
            if (r0 == 0) goto L_0x01b9
            java.lang.String r0 = "buttons"
            org.json.JSONArray r0 = r2.getJSONArray(r0)     // Catch:{ JSONException -> 0x01bc }
            java.lang.String r3 = "Ghunt"
            java.lang.String r4 = r0.toString()     // Catch:{ JSONException -> 0x01bc }
            android.util.Log.i(r3, r4)     // Catch:{ JSONException -> 0x01bc }
            android.content.Context r3 = r11.context     // Catch:{ JSONException -> 0x01bc }
            java.lang.String r4 = "feedback_buttons"
            java.lang.String r5 = r0.toString()     // Catch:{ JSONException -> 0x01bc }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r3, r4, r5)     // Catch:{ JSONException -> 0x01bc }
        L_0x01b9:
            r5 = r14
            goto L_0x055e
        L_0x01bc:
            r0 = move-exception
            r0.printStackTrace()
            r5 = r14
            goto L_0x055e
        L_0x01c3:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r2 = r0
            com.leedarson.serviceimpl.business.BusinessServiceImpl$1 r0 = new com.leedarson.serviceimpl.business.BusinessServiceImpl$1     // Catch:{ JSONException -> 0x021f }
            r0.<init>(r2, r14)     // Catch:{ JSONException -> 0x021f }
            java.lang.String r3 = "done"
            boolean r3 = r1.has(r3)     // Catch:{ JSONException -> 0x021f }
            if (r3 == 0) goto L_0x0215
            java.lang.String r3 = "done"
            org.json.JSONObject r3 = r1.getJSONObject(r3)     // Catch:{ JSONException -> 0x021f }
            java.lang.String r4 = "url"
            boolean r4 = r3.has(r4)     // Catch:{ JSONException -> 0x021f }
            if (r4 == 0) goto L_0x0215
            java.lang.String r4 = "url"
            java.lang.String r4 = r3.getString(r4)     // Catch:{ JSONException -> 0x021f }
            if (r4 == 0) goto L_0x0215
            java.lang.String r5 = "feedback"
            boolean r5 = r4.contains(r5)     // Catch:{ JSONException -> 0x021f }
            if (r5 == 0) goto L_0x0215
            com.leedarson.serviceimpl.business.utils.PackageLocalLogFileUtil r5 = new com.leedarson.serviceimpl.business.utils.PackageLocalLogFileUtil     // Catch:{ JSONException -> 0x021f }
            r5.<init>()     // Catch:{ JSONException -> 0x021f }
            r6 = r1
            io.reactivex.e r7 = r5.createPackageLogJob()     // Catch:{ JSONException -> 0x021f }
            io.reactivex.i r8 = com.leedarson.base.http.observer.l.c()     // Catch:{ JSONException -> 0x021f }
            io.reactivex.e r7 = r7.c(r8)     // Catch:{ JSONException -> 0x021f }
            com.leedarson.serviceimpl.business.h r8 = new com.leedarson.serviceimpl.business.h     // Catch:{ JSONException -> 0x021f }
            r8.<init>(r11, r6, r0)     // Catch:{ JSONException -> 0x021f }
            com.leedarson.serviceimpl.business.o r9 = new com.leedarson.serviceimpl.business.o     // Catch:{ JSONException -> 0x021f }
            r9.<init>(r11)     // Catch:{ JSONException -> 0x021f }
            io.reactivex.disposables.b r7 = r7.I(r8, r9)     // Catch:{ JSONException -> 0x021f }
            return
        L_0x0215:
            java.lang.String r3 = r1.toString()     // Catch:{ JSONException -> 0x021f }
            r11.doUpload(r3, r0)     // Catch:{ JSONException -> 0x021f }
            r5 = r14
            goto L_0x055e
        L_0x021f:
            r0 = move-exception
            r0.printStackTrace()
            r5 = r14
            goto L_0x055e
        L_0x0226:
            com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetDiagnoService r0 = r11._netDiagnoService
            if (r0 == 0) goto L_0x022d
            r0.stopNetDialogsis()
        L_0x022d:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r2 = r0
            r3 = 200(0xc8, float:2.8E-43)
            r2.put((java.lang.String) r12, (int) r3)     // Catch:{ JSONException -> 0x024b }
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x024b }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r3 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x024b }
            java.lang.String r4 = r2.toString()     // Catch:{ JSONException -> 0x024b }
            r3.<init>(r14, r4)     // Catch:{ JSONException -> 0x024b }
            r0.l(r3)     // Catch:{ JSONException -> 0x024b }
            r5 = r14
            goto L_0x055e
        L_0x024b:
            r0 = move-exception
            r0.printStackTrace()
            r5 = r14
            goto L_0x055e
        L_0x0252:
            r11.netDiagCallback = r14
            r11.netDiagDataObj = r1
            r11.startNetDiag()
            r5 = r14
            goto L_0x055e
        L_0x025c:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r2 = r0
            r3 = 200(0xc8, float:2.8E-43)
            r2.put((java.lang.String) r12, (int) r3)     // Catch:{ JSONException -> 0x0286 }
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0286 }
            com.leedarson.serviceinterface.event.CheckUpdateEvent r3 = new com.leedarson.serviceinterface.event.CheckUpdateEvent     // Catch:{ JSONException -> 0x0286 }
            r3.<init>()     // Catch:{ JSONException -> 0x0286 }
            r0.l(r3)     // Catch:{ JSONException -> 0x0286 }
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0286 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r3 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x0286 }
            java.lang.String r4 = r2.toString()     // Catch:{ JSONException -> 0x0286 }
            r3.<init>(r14, r4)     // Catch:{ JSONException -> 0x0286 }
            r0.l(r3)     // Catch:{ JSONException -> 0x0286 }
            r5 = r14
            goto L_0x055e
        L_0x0286:
            r0 = move-exception
            r0.printStackTrace()
            r5 = r14
            goto L_0x055e
        L_0x028d:
            if (r1 == 0) goto L_0x0527
            r0 = 0
            r10 = 0
            boolean r18 = r1.has(r4)     // Catch:{ JSONException -> 0x0520 }
            if (r18 == 0) goto L_0x02ae
            java.lang.String r18 = r1.optString(r4)     // Catch:{ JSONException -> 0x02a9 }
            r26 = r18
            r18 = r0
            android.content.Context r0 = r11.context     // Catch:{ JSONException -> 0x02a9 }
            r19 = r10
            r10 = r26
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r0, r4, r10)     // Catch:{ JSONException -> 0x02a9 }
            goto L_0x02b2
        L_0x02a9:
            r0 = move-exception
            r26 = r14
            goto L_0x0523
        L_0x02ae:
            r18 = r0
            r19 = r10
        L_0x02b2:
            boolean r0 = r1.has(r9)     // Catch:{ JSONException -> 0x0520 }
            if (r0 == 0) goto L_0x02c1
            java.lang.String r0 = r1.getString(r9)     // Catch:{ JSONException -> 0x02a9 }
            android.content.Context r4 = r11.context     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r4, r9, r0)     // Catch:{ JSONException -> 0x02a9 }
        L_0x02c1:
            boolean r0 = r1.has(r8)     // Catch:{ JSONException -> 0x0520 }
            if (r0 == 0) goto L_0x02d0
            java.lang.String r0 = r1.getString(r8)     // Catch:{ JSONException -> 0x02a9 }
            android.content.Context r4 = r11.context     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r4, r8, r0)     // Catch:{ JSONException -> 0x02a9 }
        L_0x02d0:
            boolean r0 = r1.has(r6)     // Catch:{ JSONException -> 0x0520 }
            if (r0 == 0) goto L_0x02ff
            java.lang.String r0 = r1.getString(r6)     // Catch:{ JSONException -> 0x02a9 }
            r11.httpServer = r0     // Catch:{ JSONException -> 0x02a9 }
            android.content.Context r0 = r11.context     // Catch:{ JSONException -> 0x02a9 }
            java.lang.String r0 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r0, r6, r15)     // Catch:{ JSONException -> 0x02a9 }
            java.lang.String r4 = r11.httpServer     // Catch:{ JSONException -> 0x02a9 }
            boolean r4 = r0.equals(r4)     // Catch:{ JSONException -> 0x02a9 }
            if (r4 != 0) goto L_0x02ff
            android.content.Context r4 = r11.context     // Catch:{ JSONException -> 0x02a9 }
            java.lang.String r10 = r11.httpServer     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r4, r6, r10)     // Catch:{ JSONException -> 0x02a9 }
            org.greenrobot.eventbus.c r4 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceimpl.business.event.ReceiveHttpServerEnvent r6 = new com.leedarson.serviceimpl.business.event.ReceiveHttpServerEnvent     // Catch:{ JSONException -> 0x02a9 }
            java.lang.String r10 = r11.httpServer     // Catch:{ JSONException -> 0x02a9 }
            r6.<init>(r10)     // Catch:{ JSONException -> 0x02a9 }
            r4.l(r6)     // Catch:{ JSONException -> 0x02a9 }
        L_0x02ff:
            boolean r0 = r1.has(r7)     // Catch:{ JSONException -> 0x0520 }
            if (r0 == 0) goto L_0x0342
            java.lang.String r0 = r1.getString(r7)     // Catch:{ JSONException -> 0x02a9 }
            boolean r0 = r15.equals(r0)     // Catch:{ JSONException -> 0x02a9 }
            if (r0 != 0) goto L_0x0342
            java.lang.String r0 = r1.getString(r7)     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.Constans.userId = r0     // Catch:{ JSONException -> 0x02a9 }
            android.content.Context r0 = r11.context     // Catch:{ JSONException -> 0x02a9 }
            java.lang.String r4 = r1.getString(r7)     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r0, r7, r4)     // Catch:{ JSONException -> 0x02a9 }
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ JSONException -> 0x02a9 }
            java.lang.Class<com.leedarson.serviceinterface.ZendeskService> r4 = com.leedarson.serviceinterface.ZendeskService.class
            java.lang.Object r0 = r0.g(r4)     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.ZendeskService r0 = (com.leedarson.serviceinterface.ZendeskService) r0     // Catch:{ JSONException -> 0x02a9 }
            if (r0 == 0) goto L_0x032f
            r0.getJwt()     // Catch:{ JSONException -> 0x02a9 }
        L_0x032f:
            com.alibaba.android.arouter.launcher.a r4 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ JSONException -> 0x02a9 }
            java.lang.Class<com.leedarson.serviceinterface.LoggerService> r6 = com.leedarson.serviceinterface.LoggerService.class
            java.lang.Object r4 = r4.g(r6)     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.LoggerService r4 = (com.leedarson.serviceinterface.LoggerService) r4     // Catch:{ JSONException -> 0x02a9 }
            if (r4 == 0) goto L_0x0342
            java.lang.String r6 = com.leedarson.serviceinterface.Constans.userId     // Catch:{ JSONException -> 0x02a9 }
            r4.loginSensorsData(r6)     // Catch:{ JSONException -> 0x02a9 }
        L_0x0342:
            boolean r0 = r1.has(r3)     // Catch:{ JSONException -> 0x0520 }
            if (r0 != 0) goto L_0x0354
            java.lang.String r0 = "userName"
            boolean r0 = r1.has(r0)     // Catch:{ JSONException -> 0x02a9 }
            if (r0 == 0) goto L_0x0351
            goto L_0x0354
        L_0x0351:
            r10 = r19
            goto L_0x0378
        L_0x0354:
            r10 = 1
            java.lang.String r0 = "userName"
            java.lang.String r0 = r1.optString(r0, r15)     // Catch:{ JSONException -> 0x0520 }
            java.lang.String r0 = r1.optString(r3, r0)     // Catch:{ JSONException -> 0x0520 }
            boolean r4 = android.text.TextUtils.isEmpty(r0)     // Catch:{ JSONException -> 0x0520 }
            if (r4 != 0) goto L_0x0378
            com.leedarson.serviceinterface.Constans.userName = r0     // Catch:{ JSONException -> 0x02a9 }
            android.content.Context r4 = r11.context     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r4, r3, r0)     // Catch:{ JSONException -> 0x02a9 }
            org.greenrobot.eventbus.c r3 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.event.UserInfoUpdateEvent r4 = new com.leedarson.serviceinterface.event.UserInfoUpdateEvent     // Catch:{ JSONException -> 0x02a9 }
            r4.<init>()     // Catch:{ JSONException -> 0x02a9 }
            r3.l(r4)     // Catch:{ JSONException -> 0x02a9 }
        L_0x0378:
            boolean r0 = r1.has(r2)     // Catch:{ JSONException -> 0x0520 }
            if (r0 == 0) goto L_0x03a3
            java.lang.String r0 = r1.getString(r2)     // Catch:{ JSONException -> 0x02a9 }
            android.content.Context r3 = r11.context     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r3, r2, r0)     // Catch:{ JSONException -> 0x02a9 }
            boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch:{ JSONException -> 0x02a9 }
            if (r2 == 0) goto L_0x0391
            r11.logout()     // Catch:{ JSONException -> 0x02a9 }
            goto L_0x03a3
        L_0x0391:
            boolean r2 = r1.has(r8)     // Catch:{ JSONException -> 0x02a9 }
            if (r2 == 0) goto L_0x03a0
            java.lang.String r2 = r1.getString(r8)     // Catch:{ JSONException -> 0x02a9 }
            android.content.Context r3 = r11.context     // Catch:{ JSONException -> 0x02a9 }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r3, r8, r2)     // Catch:{ JSONException -> 0x02a9 }
        L_0x03a0:
            r11.login()     // Catch:{ JSONException -> 0x02a9 }
        L_0x03a3:
            java.lang.String r0 = "theme"
            boolean r0 = r1.has(r0)     // Catch:{ JSONException -> 0x0520 }
            if (r0 == 0) goto L_0x0442
            java.lang.String r0 = "theme"
            org.json.JSONObject r0 = r1.getJSONObject(r0)     // Catch:{ JSONException -> 0x0520 }
            java.lang.String r2 = "primary"
            boolean r2 = r0.has(r2)     // Catch:{ JSONException -> 0x0520 }
            java.lang.String r3 = "text"
            java.lang.String r4 = "active"
            java.lang.String r6 = "main"
            if (r2 == 0) goto L_0x03fe
            java.lang.String r2 = "primary"
            org.json.JSONObject r2 = r0.getJSONObject(r2)     // Catch:{ JSONException -> 0x0520 }
            boolean r7 = r2.has(r6)     // Catch:{ JSONException -> 0x0520 }
            if (r7 == 0) goto L_0x03d9
            java.lang.String r7 = r2.getString(r6)     // Catch:{ JSONException -> 0x0520 }
            android.content.Context r8 = r11.context     // Catch:{ JSONException -> 0x0520 }
            r26 = r14
            java.lang.String r14 = "primary_main"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r8, r14, r7)     // Catch:{ JSONException -> 0x051e }
            goto L_0x03db
        L_0x03d9:
            r26 = r14
        L_0x03db:
            boolean r7 = r2.has(r4)     // Catch:{ JSONException -> 0x051e }
            if (r7 == 0) goto L_0x03ec
            java.lang.String r7 = r2.getString(r4)     // Catch:{ JSONException -> 0x051e }
            android.content.Context r8 = r11.context     // Catch:{ JSONException -> 0x051e }
            java.lang.String r14 = "primary_active"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r8, r14, r7)     // Catch:{ JSONException -> 0x051e }
        L_0x03ec:
            boolean r7 = r2.has(r3)     // Catch:{ JSONException -> 0x051e }
            if (r7 == 0) goto L_0x0400
            java.lang.String r7 = r2.getString(r3)     // Catch:{ JSONException -> 0x051e }
            android.content.Context r8 = r11.context     // Catch:{ JSONException -> 0x051e }
            java.lang.String r14 = "primary_text"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r8, r14, r7)     // Catch:{ JSONException -> 0x051e }
            goto L_0x0400
        L_0x03fe:
            r26 = r14
        L_0x0400:
            java.lang.String r2 = "secondary"
            boolean r2 = r0.has(r2)     // Catch:{ JSONException -> 0x051e }
            if (r2 == 0) goto L_0x0444
            java.lang.String r2 = "secondary"
            org.json.JSONObject r2 = r0.getJSONObject(r2)     // Catch:{ JSONException -> 0x051e }
            boolean r7 = r2.has(r6)     // Catch:{ JSONException -> 0x051e }
            if (r7 == 0) goto L_0x041f
            java.lang.String r6 = r2.getString(r6)     // Catch:{ JSONException -> 0x051e }
            android.content.Context r7 = r11.context     // Catch:{ JSONException -> 0x051e }
            java.lang.String r8 = "secondary_main"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r7, r8, r6)     // Catch:{ JSONException -> 0x051e }
        L_0x041f:
            boolean r6 = r2.has(r4)     // Catch:{ JSONException -> 0x051e }
            if (r6 == 0) goto L_0x0430
            java.lang.String r4 = r2.getString(r4)     // Catch:{ JSONException -> 0x051e }
            android.content.Context r6 = r11.context     // Catch:{ JSONException -> 0x051e }
            java.lang.String r7 = "secondary_active"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r6, r7, r4)     // Catch:{ JSONException -> 0x051e }
        L_0x0430:
            boolean r4 = r2.has(r3)     // Catch:{ JSONException -> 0x051e }
            if (r4 == 0) goto L_0x0444
            java.lang.String r3 = r2.getString(r3)     // Catch:{ JSONException -> 0x051e }
            android.content.Context r4 = r11.context     // Catch:{ JSONException -> 0x051e }
            java.lang.String r6 = "secondary_text"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r4, r6, r3)     // Catch:{ JSONException -> 0x051e }
            goto L_0x0444
        L_0x0442:
            r26 = r14
        L_0x0444:
            boolean r0 = r1.has(r5)     // Catch:{ JSONException -> 0x051e }
            if (r0 == 0) goto L_0x04ad
            java.lang.String r0 = r1.getString(r5)     // Catch:{ JSONException -> 0x051e }
            android.content.Context r2 = r11.context     // Catch:{ JSONException -> 0x051e }
            java.lang.String r2 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r2, r5, r15)     // Catch:{ JSONException -> 0x051e }
            boolean r2 = r15.equals(r2)     // Catch:{ JSONException -> 0x051e }
            android.content.Context r3 = r11.context     // Catch:{ JSONException -> 0x051e }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r3, r5, r0)     // Catch:{ JSONException -> 0x051e }
            com.alibaba.android.arouter.launcher.a r3 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ JSONException -> 0x051e }
            java.lang.Class<com.leedarson.serviceinterface.BleMeshService> r4 = com.leedarson.serviceinterface.BleMeshService.class
            java.lang.Object r3 = r3.g(r4)     // Catch:{ JSONException -> 0x051e }
            com.leedarson.serviceinterface.BleMeshService r3 = (com.leedarson.serviceinterface.BleMeshService) r3     // Catch:{ JSONException -> 0x051e }
            if (r3 == 0) goto L_0x04ab
            java.lang.String r4 = "MeshJsonLog"
            timber.log.a$b r4 = timber.log.a.g(r4)     // Catch:{ JSONException -> 0x051e }
            java.lang.String r5 = "收到 js -> businsess config接口调用"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ JSONException -> 0x051e }
            r4.m(r5, r6)     // Catch:{ JSONException -> 0x051e }
            r3.onHouseChange(r0)     // Catch:{ JSONException -> 0x051e }
            if (r2 == 0) goto L_0x04ab
            java.lang.String r4 = "BleMesh"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x051e }
            r5.<init>()     // Catch:{ JSONException -> 0x051e }
            java.lang.String r6 = "初次登陆，正在强制更新配置文件---》houseId="
            r5.append(r6)     // Catch:{ JSONException -> 0x051e }
            r5.append(r0)     // Catch:{ JSONException -> 0x051e }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x051e }
            com.leedarson.base.logger.a.c(r4, r5)     // Catch:{ JSONException -> 0x051e }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x051e }
            r4.<init>()     // Catch:{ JSONException -> 0x051e }
            java.lang.String r5 = "初次登录，收到 js -> businsess config接口调用，强制请求更新meshjson文件-》hourseId="
            r4.append(r5)     // Catch:{ JSONException -> 0x051e }
            r4.append(r0)     // Catch:{ JSONException -> 0x051e }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x051e }
            r3.reportMeshJsonReport(r4)     // Catch:{ JSONException -> 0x051e }
            r3.forceUpdateConfig()     // Catch:{ JSONException -> 0x051e }
        L_0x04ab:
            r0 = r2
            goto L_0x04af
        L_0x04ad:
            r0 = r18
        L_0x04af:
            boolean r2 = r1.has(r9)     // Catch:{ JSONException -> 0x051e }
            if (r2 == 0) goto L_0x04be
            android.content.Context r2 = r11.context     // Catch:{ JSONException -> 0x051e }
            java.lang.String r3 = r1.getString(r9)     // Catch:{ JSONException -> 0x051e }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r9, r3)     // Catch:{ JSONException -> 0x051e }
        L_0x04be:
            java.lang.String r2 = "currentCountry"
            boolean r2 = r1.has(r2)     // Catch:{ JSONException -> 0x051e }
            if (r2 == 0) goto L_0x04f2
            java.lang.String r2 = "currentCountry"
            org.json.JSONObject r2 = r1.getJSONObject(r2)     // Catch:{ JSONException -> 0x051e }
            r3 = r17
            boolean r4 = r2.has(r3)     // Catch:{ JSONException -> 0x051e }
            if (r4 == 0) goto L_0x04dd
            android.content.Context r4 = r11.context     // Catch:{ JSONException -> 0x051e }
            java.lang.String r5 = r2.getString(r3)     // Catch:{ JSONException -> 0x051e }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r4, r3, r5)     // Catch:{ JSONException -> 0x051e }
        L_0x04dd:
            java.lang.String r3 = "regionCode"
            boolean r3 = r2.has(r3)     // Catch:{ JSONException -> 0x051e }
            if (r3 == 0) goto L_0x04f2
            android.content.Context r3 = r11.context     // Catch:{ JSONException -> 0x051e }
            java.lang.String r4 = "regionCode"
            java.lang.String r5 = "regionCode"
            java.lang.String r5 = r2.getString(r5)     // Catch:{ JSONException -> 0x051e }
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r3, r4, r5)     // Catch:{ JSONException -> 0x051e }
        L_0x04f2:
            com.alibaba.android.arouter.launcher.a r2 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ JSONException -> 0x051e }
            java.lang.Class<com.leedarson.serviceinterface.LoggerService> r3 = com.leedarson.serviceinterface.LoggerService.class
            java.lang.Object r2 = r2.g(r3)     // Catch:{ JSONException -> 0x051e }
            com.leedarson.serviceinterface.LoggerService r2 = (com.leedarson.serviceinterface.LoggerService) r2     // Catch:{ JSONException -> 0x051e }
            if (r2 == 0) goto L_0x051d
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x051e }
            r3.<init>()     // Catch:{ JSONException -> 0x051e }
            java.lang.String r4 = "Business.setConfig:"
            r3.append(r4)     // Catch:{ JSONException -> 0x051e }
            r3.append(r13)     // Catch:{ JSONException -> 0x051e }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x051e }
            if (r10 == 0) goto L_0x0516
            java.lang.String r4 = "info"
            goto L_0x0518
        L_0x0516:
            java.lang.String r4 = "debug"
        L_0x0518:
            java.lang.String r5 = "config"
            r2.reportELK(r11, r3, r4, r5)     // Catch:{ JSONException -> 0x051e }
        L_0x051d:
            goto L_0x0529
        L_0x051e:
            r0 = move-exception
            goto L_0x0523
        L_0x0520:
            r0 = move-exception
            r26 = r14
        L_0x0523:
            r0.printStackTrace()
            goto L_0x0529
        L_0x0527:
            r26 = r14
        L_0x0529:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r2 = r0
            r3 = 200(0xc8, float:2.8E-43)
            r2.put((java.lang.String) r12, (int) r3)     // Catch:{ JSONException -> 0x0557 }
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0557 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r3 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x0557 }
            java.lang.String r4 = r2.toString()     // Catch:{ JSONException -> 0x0557 }
            r5 = r26
            r3.<init>(r5, r4)     // Catch:{ JSONException -> 0x0555 }
            r0.l(r3)     // Catch:{ JSONException -> 0x0555 }
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0555 }
            com.leedarson.serviceinterface.event.Event r3 = new com.leedarson.serviceinterface.event.Event     // Catch:{ JSONException -> 0x0555 }
            java.lang.String r4 = "AdvertEvent"
            r3.<init>(r4)     // Catch:{ JSONException -> 0x0555 }
            r0.l(r3)     // Catch:{ JSONException -> 0x0555 }
            goto L_0x055e
        L_0x0555:
            r0 = move-exception
            goto L_0x055a
        L_0x0557:
            r0 = move-exception
            r5 = r26
        L_0x055a:
            r0.printStackTrace()
        L_0x055e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.BusinessServiceImpl.handleData(java.lang.String, android.app.Activity, java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$handleData$0 */
    public /* synthetic */ void j(JSONObject finalDataObj, BusinessService.UploadCallback _handlerUpload, String backUpPath) {
        Class[] clsArr = {JSONObject.class, BusinessService.UploadCallback.class, String.class};
        if (!PatchProxy.proxy(new Object[]{finalDataObj, _handlerUpload, backUpPath}, this, changeQuickRedirect, false, 7024, clsArr, Void.TYPE).isSupported) {
            if (finalDataObj.has("paths")) {
                JSONArray _jsonArrayObj = finalDataObj.getJSONArray("paths");
                _jsonArrayObj.put((Object) backUpPath);
                finalDataObj.put("paths", (Object) _jsonArrayObj);
            }
            I("feedback 日志上报后参数-->" + finalDataObj.toString());
            doUpload(finalDataObj.toString(), _handlerUpload);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$handleData$1 */
    public /* synthetic */ void k(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 7023, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            E("Feedback 日志统计出现异常  throwable=" + throwable.toString());
        }
    }

    private void I(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 7004, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g(TAG).h(msg, new Object[0]);
        }
    }

    private void E(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 7005, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g(TAG).c(msg, new Object[0]);
        }
    }

    private void takeScreenShot(String str, JSONArray jSONArray) {
        int _height;
        int _width;
        int _y;
        int _x;
        if (!PatchProxy.proxy(new Object[]{str, jSONArray}, this, changeQuickRedirect, false, 7006, new Class[]{String.class, JSONArray.class}, Void.TYPE).isSupported) {
            final JSONArray frame = jSONArray;
            String callbackKey = str;
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (frame == null || frame.length() != 4) {
                _x = 0;
                _y = 0;
                _width = 0;
                _height = 0;
            } else {
                try {
                    int _x2 = frame.getInt(0);
                    int _y2 = frame.getInt(1);
                    _x = _x2;
                    _y = _y2;
                    _width = frame.getInt(2);
                    _height = frame.getInt(3);
                } catch (Exception e) {
                    _x = 0;
                    _y = 0;
                    _width = 0;
                    _height = 0;
                }
            }
            int x = _x;
            int y = _y;
            int width = _width;
            int height = _height;
            Activity activity = c.h().c();
            if (activity != null) {
                if (Build.VERSION.SDK_INT >= 33) {
                    saveScreenShot(activity, x, y, width, height, callbackKey);
                    int i = _x;
                    Activity activity2 = activity;
                    String[] strArr = perms;
                    JSONArray jSONArray2 = frame;
                } else if (EasyPermissions.a(this.context, perms)) {
                    saveScreenShot(activity, x, y, width, height, callbackKey);
                    int i2 = _x;
                    Activity activity3 = activity;
                    String[] strArr2 = perms;
                    JSONArray jSONArray3 = frame;
                } else {
                    LDSPermissionGuide.GuideParam param = new LDSPermissionGuide.AlbumGuideParam(this.context);
                    if (activity instanceof FragmentActivity) {
                        int i3 = _x;
                        AnonymousClass4 r0 = r2;
                        final Activity activity4 = activity;
                        Activity activity5 = activity;
                        final String[] strArr3 = perms;
                        String[] strArr4 = perms;
                        final String str2 = callbackKey;
                        JSONArray jSONArray4 = frame;
                        AnonymousClass4 r2 = new LDSPermissitonGuideFragment.a() {
                            public static ChangeQuickRedirect changeQuickRedirect;

                            public void onCloseClick() {
                            }

                            public void onActionClick(LDSPermissitonGuideFragment fragment) {
                                if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 7034, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                                    LDSPermissionGuide.b(fragment, activity4, strArr3, "albumDeny", new f(this, str2, frame));
                                }
                            }

                            /* access modifiers changed from: private */
                            /* renamed from: lambda$onActionClick$0 */
                            public /* synthetic */ void a(String callbackKey, JSONArray frame) {
                                Class[] clsArr = {String.class, JSONArray.class};
                                if (!PatchProxy.proxy(new Object[]{callbackKey, frame}, this, changeQuickRedirect, false, 7035, clsArr, Void.TYPE).isSupported) {
                                    BusinessServiceImpl.access$000(BusinessServiceImpl.this, callbackKey, frame);
                                }
                            }
                        };
                        LDSPermissionGuide.d((FragmentActivity) activity, param, r0);
                        return;
                    }
                    Activity activity6 = activity;
                    String[] strArr5 = perms;
                    JSONArray jSONArray5 = frame;
                }
            }
        }
    }

    private void saveScreenShot(Activity activity, int i, int i2, int i3, int i4, String callbackKey) {
        Object[] objArr = {activity, new Integer(i), new Integer(i2), new Integer(i3), new Integer(i4), callbackKey};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Activity.class, cls, cls, cls, cls, String.class};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7007, clsArr, Void.TYPE).isSupported) {
            int x = i;
            int width = i3;
            Activity activity2 = activity;
            int y = i2;
            int height = i4;
            l.k(new j(activity2, x, y, width, height)).b0(io.reactivex.schedulers.a.c()).J(io.reactivex.android.schedulers.a.a()).X(new p(activity2, callbackKey, y, x, width, height));
        }
    }

    static /* synthetic */ void lambda$saveScreenShot$2(Activity activity, int x, int y, int width, int height, m mVar) {
        Object[] objArr = {activity, new Integer(x), new Integer(y), new Integer(width), new Integer(height), mVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7022, new Class[]{Activity.class, cls, cls, cls, cls, m.class}, Void.TYPE).isSupported) {
            m emitter = mVar;
            PhotoUtils photoUtils = PhotoUtils.INSTANCE;
            emitter.onNext(photoUtils.saveScreenShot(activity, x, y, width, height));
            emitter.onComplete();
        }
    }

    static /* synthetic */ void lambda$saveScreenShot$3(Activity activity, String str, int i, int i2, int i3, int i4, Uri uri) {
        Object[] objArr = {activity, str, new Integer(i), new Integer(i2), new Integer(i3), new Integer(i4), uri};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7021, new Class[]{Activity.class, String.class, cls, cls, cls, cls, Uri.class}, Void.TYPE).isSupported) {
            Activity activity2 = activity;
            int y = i;
            Uri uri2 = uri;
            int width = i3;
            String callbackKey = str;
            int x = i2;
            int height = i4;
            try {
                JSONObject json = new JSONObject();
                boolean isSuccess = false;
                if (uri2 == null) {
                    ToastUtil.showShort(activity2, activity2.getString(R.string.save_screenshot_failed));
                    json.put("code", -1);
                } else {
                    json.put("code", 200);
                    isSuccess = true;
                }
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, json.toString()));
                if (isSuccess) {
                    IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                    int marginTop = ((int) Resources.getSystem().getDisplayMetrics().density) * y;
                    int marginLeft = ((int) Resources.getSystem().getDisplayMetrics().density) * x;
                    int marginRight = marginLeft;
                    if (activity2 instanceof FragmentActivity) {
                        ipcService.showSnap((FragmentActivity) activity2, uri2, width, height, marginLeft, marginTop, marginRight);
                    } else {
                        ToastUtil.showShort(activity2, activity2.getString(R.string.save_screenshot_success));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void logout() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7008, new Class[0], Void.TYPE).isSupported) {
            IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
            if (ipcService != null) {
                ipcService.disconnectAll();
            }
            ZendeskService zendeskService = (ZendeskService) com.alibaba.android.arouter.launcher.a.c().g(ZendeskService.class);
            if (zendeskService != null) {
                zendeskService.logout();
            }
            LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
            if (_mqttService != null) {
                _mqttService.loginOut(new LDSBaseMqttService.MqttActionHandler() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void onActionSuccess(String taskId, JSONObject callbackData) {
                    }

                    public void onActionFail(int code, String taskId, String desc) {
                    }
                });
            }
            Constans.appLogin = false;
            org.greenrobot.eventbus.c.c().l(new AppLogoutEvent());
            c.h().g();
        }
    }

    private void login() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7009, new Class[0], Void.TYPE).isSupported) {
            Constans.appLogin = true;
            r.e().p("Business.setConfig");
            WiFiService wiFiService = (WiFiService) com.alibaba.android.arouter.launcher.a.c().g(WiFiService.class);
            if (wiFiService != null) {
                wiFiService.getRouterInfo("", "");
            }
            LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
            if (_mqttService != null) {
                _mqttService.loginIn(new LDSBaseMqttService.MqttActionHandler() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void onActionSuccess(String taskId, JSONObject callbackData) {
                    }

                    public void onActionFail(int code, String taskId, String desc) {
                    }
                });
            }
        }
    }

    public void startNetDiag() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7010, new Class[0], Void.TYPE).isSupported) {
            try {
                if (this.netDiagDataObj.has("domains")) {
                    List<NetDiagnosisDomain> domains = (List) new Gson().fromJson(this.netDiagDataObj.getJSONArray("domains").toString(), new TypeToken<List<NetDiagnosisDomain>>() {
                    }.getType());
                    final File fileDiag = new File(this.context.getFilesDir().getPath() + "/Arnoo_network_diagnose.log");
                    if (fileDiag.exists()) {
                        fileDiag.delete();
                    }
                    File file = fileDiag;
                    LDNetDiagnoService lDNetDiagnoService = new LDNetDiagnoService(this.context.getApplicationContext(), this.context.getPackageName(), this.context.getApplicationInfo().name, DeviceUtils.getVersion(this.context), "", "", domains, "", "", "", "", new LDNetDiagnoListener() {
                        public static ChangeQuickRedirect changeQuickRedirect;

                        public void OnNetDiagnoFinished(String log) {
                        }

                        public void OnNetDiagnoUpdated(String log) {
                            if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 7036, new Class[]{String.class}, Void.TYPE).isSupported) {
                                a.g("Ghunt").h(log, new Object[0]);
                                FileIOUtils.writeFileFromString(fileDiag, log, true);
                            }
                        }
                    });
                    this._netDiagnoService = lDNetDiagnoService;
                    lDNetDiagnoService.setIfUseJNICTrace(true);
                    this._netDiagnoService.execute(new String[0]);
                    JSONObject jsonObjectStartDiao = new JSONObject();
                    try {
                        if (!TextUtils.isEmpty(this.netDiagCallback)) {
                            jsonObjectStartDiao.put("code", 200);
                            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.netDiagCallback, jsonObjectStartDiao.toString()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void reUploadLog(String uploadDiagLogData) {
        if (!PatchProxy.proxy(new Object[]{uploadDiagLogData}, this, changeQuickRedirect, false, 7011, new Class[]{String.class}, Void.TYPE).isSupported) {
            boolean isUploadNetDiagLogFail = SharePreferenceUtils.getPrefBoolean(this.context, "isUploadNetDiagLogFail", false);
            a.b g = a.g("Ghunt");
            g.h("reUpload-isuploading=" + this.isUploading, new Object[0]);
            if ((true ^ this.isUploading) && isUploadNetDiagLogFail) {
                try {
                    doUpload(uploadDiagLogData, new BusinessService.UploadCallback() {
                        public static ChangeQuickRedirect changeQuickRedirect;

                        public void success(Object o) {
                        }

                        public void fail(Object e, Object data) {
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void doUpload(String objString, BusinessService.UploadCallback mCallback) {
        if (!PatchProxy.proxy(new Object[]{objString, mCallback}, this, changeQuickRedirect, false, 7012, new Class[]{String.class, BusinessService.UploadCallback.class}, Void.TYPE).isSupported) {
            BusinessService.UploadCallback mCallback2 = mCallback;
            a.b g = a.g("Ghunt");
            String str = "timestamp";
            StringBuilder sb = new StringBuilder();
            String str2 = "nativeVersion";
            sb.append("doUpload=");
            String objString2 = objString;
            sb.append(objString2);
            String str3 = "webVersion";
            g.h(sb.toString(), new Object[0]);
            this.fileIdArray = new JSONArray();
            try {
                this.isUploading = true;
                JSONObject dataObj = new JSONObject(objString2);
                if (dataObj.has("paths")) {
                    this.filePath = dataObj.getJSONArray("paths");
                }
                if (dataObj.has("headers")) {
                    this.headers = dataObj.getJSONObject("headers");
                } else {
                    this.headers = null;
                }
                if (dataObj.has("done")) {
                    JSONObject joDone = dataObj.getJSONObject("done");
                    if (joDone.has("url")) {
                        this.reportUrl = joDone.getString("url");
                    }
                    if (joDone.has("header")) {
                        JSONObject joHeader = joDone.getJSONObject("header");
                        if (joHeader.has("appId")) {
                            this.appId = joHeader.getString("appId");
                        } else if (joHeader.has("APP_ID")) {
                            this.appId = joHeader.getString("APP_ID");
                        }
                    }
                    if (joDone.has("params")) {
                        JSONObject jSONObject = joDone.getJSONObject("params");
                        this.joParams = jSONObject;
                        if (jSONObject.has("os")) {
                            this.os = this.joParams.getString("os");
                        }
                        if (this.joParams.has(PlaceTypes.COUNTRY)) {
                            this.country = this.joParams.getString(PlaceTypes.COUNTRY);
                        }
                        if (this.joParams.has("code")) {
                            this.code = this.joParams.getString("code");
                        }
                        String str4 = str3;
                        if (this.joParams.has(str4)) {
                            this.webVersion = this.joParams.getString(str4);
                        }
                        String str5 = str2;
                        if (this.joParams.has(str5)) {
                            this.nativeVersion = this.joParams.getString(str5);
                        }
                        String str6 = str;
                        if (this.joParams.has(str6)) {
                            this.timestamp = Long.valueOf(this.joParams.getLong(str6));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            _actualUploadData(objString2, mCallback2);
        }
    }

    private void _actualUploadData(String str, BusinessService.UploadCallback uploadCallback) {
        int i = 1;
        if (!PatchProxy.proxy(new Object[]{str, uploadCallback}, this, changeQuickRedirect, false, 7013, new Class[]{String.class, BusinessService.UploadCallback.class}, Void.TYPE).isSupported) {
            BusinessService.UploadCallback mCallback = uploadCallback;
            String objString = str;
            String url = SharePreferenceUtils.getPrefString(this.context, "httpServer", "") + "/files";
            String accessToken = SharePreferenceUtils.getPrefString(this.context, "accessToken", "");
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put("appId", this.appId);
            if (!TextUtils.isEmpty(accessToken)) {
                headerMap.put("token", accessToken);
            }
            headerMap.put("terminal", "app");
            Observable<Object> just = l.F("");
            new ArrayList();
            int i2 = 0;
            while (i2 < this.filePath.length()) {
                Map<String, Object> paramMap = new HashMap<>();
                String type = null;
                final String path = this.filePath.get(i2).toString();
                String[] split = path.split("[.]");
                if (split.length > 0) {
                    type = split[split.length - i];
                    paramMap.put("fileType", type);
                }
                Log.i("Ghunt", "fileMapType=" + type);
                just = just.u(new i(((com.leedarson.base.http.api.a) b.b().a(com.leedarson.base.http.api.a.class)).g(url, paramMap, headerMap).u(new f() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public Object apply(Object o) {
                        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o}, this, changeQuickRedirect, false, 7030, new Class[]{Object.class}, Object.class);
                        if (proxy.isSupported) {
                            return proxy.result;
                        }
                        Map<String, Object> header = new HashMap<>();
                        List<MultipartBody.Part> parts = new ArrayList<>();
                        if (o == null) {
                            return null;
                        }
                        JSONObject joUpload = new JSONObject(o.toString());
                        if (joUpload.has("presignedUrl")) {
                            BusinessServiceImpl.this.presignedUrl = joUpload.getString("presignedUrl");
                            BusinessServiceImpl businessServiceImpl = BusinessServiceImpl.this;
                            BusinessServiceImpl.access$100(businessServiceImpl, "原文件：" + o.toString() + " 预签名地址：presignedUrl=" + BusinessServiceImpl.this.presignedUrl);
                        }
                        if (joUpload.has("fileId")) {
                            BusinessServiceImpl.this.fileId = joUpload.getString("fileId");
                            BusinessServiceImpl businessServiceImpl2 = BusinessServiceImpl.this;
                            businessServiceImpl2.fileIdArray.put((Object) businessServiceImpl2.fileId);
                            BusinessServiceImpl businessServiceImpl3 = BusinessServiceImpl.this;
                            BusinessServiceImpl.access$100(businessServiceImpl3, "文件ID: fileId=" + BusinessServiceImpl.this.fileId);
                        }
                        if (joUpload.has("headers")) {
                            JSONObject joHeader = joUpload.getJSONObject("headers");
                            Iterator keys = joHeader.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                String value = joHeader.get(key).toString();
                                header.put(key, value);
                                if (key.equals("Content-Type")) {
                                    String contentType = value;
                                }
                            }
                        }
                        File file = new File(path);
                        c0 requestFile = c0.create((x) null, file);
                        parts.add(y.c.b("file", com.leedarson.serviceimpl.http.b.a(file.getName()), requestFile));
                        return ((com.leedarson.base.http.api.a) b.b().a(com.leedarson.base.http.api.a.class)).e(BusinessServiceImpl.this.presignedUrl, requestFile, header);
                    }
                })));
                i2++;
                i = 1;
            }
            just.u(new l(this, objString, headerMap)).b0(io.reactivex.schedulers.a.c()).J(io.reactivex.android.schedulers.a.a()).Y(new n(this, mCallback), new g(this, mCallback, objString));
        }
    }

    static /* synthetic */ o lambda$_actualUploadData$4(l observable, Object s) {
        return observable;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$_actualUploadData$5 */
    public /* synthetic */ Object a(String objString, Map headerMap, Object obj) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objString, headerMap, obj}, this, changeQuickRedirect2, false, 7020, new Class[]{String.class, Map.class, Object.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        if (!objString.contains("done")) {
            return l.F(this.fileId);
        }
        this.joParams.put("fileIds", (Object) this.fileIdArray);
        I("文件上传地址： url=" + this.reportUrl);
        I("文件上传内容： body=" + this.joParams.toString());
        I("文件头信息： headerMap=" + headerMap);
        return ((com.leedarson.base.http.api.a) b.b().a(com.leedarson.base.http.api.a.class)).j(this.reportUrl, c0.create(x.h("application/json"), this.joParams.toString()), headerMap);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$_actualUploadData$6 */
    public /* synthetic */ void h(BusinessService.UploadCallback mCallback, Object l) {
        if (!PatchProxy.proxy(new Object[]{mCallback, l}, this, changeQuickRedirect, false, 7019, new Class[]{BusinessService.UploadCallback.class, Object.class}, Void.TYPE).isSupported) {
            this.isUploading = false;
            if (mCallback != null) {
                mCallback.success(l);
            }
            I("同步到工单系统成功：---------l=" + l);
            reportIssueToElk(this.joParams.toString(), 200);
            SharePreferenceUtils.setPrefBoolean(this.context, "isUploadNetDiagLogFail", false);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$_actualUploadData$7 */
    public /* synthetic */ void i(BusinessService.UploadCallback mCallback, String objString, Object e) {
        if (!PatchProxy.proxy(new Object[]{mCallback, objString, e}, this, changeQuickRedirect, false, 7018, new Class[]{BusinessService.UploadCallback.class, String.class, Object.class}, Void.TYPE).isSupported) {
            reportIssueToElk(this.joParams.toString(), BaseResp.ERR_MSG_SEND_FAIL);
            this.isUploading = false;
            I("文件上传失败：---------error=" + e);
            if (mCallback != null) {
                mCallback.fail(e, objString);
            }
            SharePreferenceUtils.setPrefBoolean(this.context, "isUploadNetDiagLogFail", true);
            SharePreferenceUtils.setPrefString(this.context, "uploadDiagLogData", objString);
        }
    }

    public void reportIssues(FeedbackRequestBean requestParams, BusinessService.UploadCallback mCallback) {
        Class[] clsArr = {FeedbackRequestBean.class, BusinessService.UploadCallback.class};
        if (!PatchProxy.proxy(new Object[]{requestParams, mCallback}, this, changeQuickRedirect, false, 7014, clsArr, Void.TYPE).isSupported) {
            requestParams.done.header.put("Content-Type", "application/json");
            requestParams.done.header.put("token", SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", ""));
            requestParams.done.header.put("terminal", "app");
            requestParams.done.header.put("appId", SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", ""));
            WVJBWebView instanceWebView = c.h().j();
            String webViewVersion = "";
            if (instanceWebView != null) {
                webViewVersion = w.I(instanceWebView.getSettings().getUserAgentString());
            }
            FeedbackDoneParamsBean feedbackDoneParamsBean = requestParams.done.params;
            feedbackDoneParamsBean.os = "Android";
            feedbackDoneParamsBean.webVersion = webViewVersion;
            feedbackDoneParamsBean.nativeVersion = BaseApplication.b().c();
            requestParams.done.params.appVersion = BaseApplication.b().c();
            FeedbackDoneParamsBean feedbackDoneParamsBean2 = requestParams.done.params;
            feedbackDoneParamsBean2.osVersion = Build.VERSION.SDK_INT + "";
            FeedbackDoneParamsBean feedbackDoneParamsBean3 = requestParams.done.params;
            feedbackDoneParamsBean3.modelName = Build.MODEL;
            feedbackDoneParamsBean3.country = "";
            feedbackDoneParamsBean3.code = "";
            feedbackDoneParamsBean3.timestamp = System.currentTimeMillis();
            try {
                new PackageLocalLogFileUtil().createPackageLogJob().c(com.leedarson.base.http.observer.l.c()).I(new m(this, requestParams, new Gson(), mCallback), new k(this));
            } catch (Exception e) {
                E(" reportIssues 发生异常..... " + e.toString());
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$reportIssues$8 */
    public /* synthetic */ void l(FeedbackRequestBean requestParams, Gson gson, BusinessService.UploadCallback mCallback, String backUpPath) {
        if (!PatchProxy.proxy(new Object[]{requestParams, gson, mCallback, backUpPath}, this, changeQuickRedirect, false, 7017, new Class[]{FeedbackRequestBean.class, Gson.class, BusinessService.UploadCallback.class, String.class}, Void.TYPE).isSupported) {
            requestParams.paths.add(backUpPath);
            String jsonContent = gson.toJson((Object) requestParams);
            doUpload(jsonContent, mCallback);
            I("feedback 日志上报后参数-->" + jsonContent);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$reportIssues$9 */
    public /* synthetic */ void m(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 7016, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            E("Feedback 日志统计出现异常  throwable=" + throwable.toString());
        }
    }

    public void init(Context context2) {
        this.context = context2;
    }

    private void reportIssueToElk(String requestParams, int code2) {
        if (!PatchProxy.proxy(new Object[]{requestParams, new Integer(code2)}, this, changeQuickRedirect, false, 7015, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.log.elk.a.y(this).c(BusinessServiceImpl.class.getSimpleName()).t("ReportIssue").o("info").e("ReportIssue").p(requestParams).u("code", Integer.valueOf(code2)).u("desc", code2 == 200 ? "上报成功" : "上报失败").a().b();
        }
    }
}
