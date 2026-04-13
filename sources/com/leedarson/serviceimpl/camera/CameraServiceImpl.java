package com.leedarson.serviceimpl.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.fragment.app.FragmentActivity;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.utils.p;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.serviceinterface.CameraService;
import com.leedarson.serviceinterface.JsbridgeService;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.MatterService;
import com.leedarson.serviceinterface.event.CloseQRScanEvent;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.e;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import meshsdk.BaseResp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class CameraServiceImpl implements CameraService {
    public static ChangeQuickRedirect changeQuickRedirect;
    Context a;
    float b = 0.0f;
    String c = null;
    private String d;
    private String e;
    private Bitmap f;
    private String g = "qrcode.png";
    private String h;
    /* access modifiers changed from: private */
    public Activity i;
    private com.tbruyelle.rxpermissions2.b j;
    private io.reactivex.disposables.b k;

    static /* synthetic */ void a(CameraServiceImpl x0, Activity x1, String x2, LinkedTreeMap x3, String x4) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3, x4}, (Object) null, changeQuickRedirect, true, 7363, new Class[]{CameraServiceImpl.class, Activity.class, cls, LinkedTreeMap.class, cls}, Void.TYPE).isSupported) {
            x0.m(x1, x2, x3, x4);
        }
    }

    static /* synthetic */ void i(CameraServiceImpl x0, LinkedTreeMap x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 7364, new Class[]{CameraServiceImpl.class, LinkedTreeMap.class, cls, cls}, Void.TYPE).isSupported) {
            x0.j(x1, x2, x3);
        }
    }

    public void handleData(String str, Activity activity, String action, String str2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, activity, action, str2}, this, changeQuickRedirect, false, 7348, new Class[]{cls, Activity.class, cls, cls}, Void.TYPE).isSupported) {
            Activity activity2 = activity;
            String data = str2;
            String callbackKey = str;
            String str3 = "url";
            a.b g2 = timber.log.a.g("LdsCamera");
            String str4 = "buttons";
            StringBuilder sb = new StringBuilder();
            String str5 = "customize";
            sb.append("CameraServiceImplhandleData: ");
            String action2 = action;
            sb.append(action2);
            String str6 = FirebaseAnalytics.Param.CONTENT;
            sb.append("  data:");
            sb.append(data);
            g2.h(sb.toString(), new Object[0]);
            try {
                this.i = activity2;
                LinkedTreeMap linkedTreeMap = i.a(data);
                if (linkedTreeMap != null) {
                    if (linkedTreeMap.containsKey(Progress.FILE_NAME)) {
                        this.c = linkedTreeMap.get(Progress.FILE_NAME).toString();
                    }
                    if ("getPhoto".equals(action2)) {
                        int videoflag = 0;
                        if (linkedTreeMap.containsKey("videoFlag")) {
                            videoflag = (int) Double.parseDouble(linkedTreeMap.get("videoFlag").toString());
                        }
                        if (videoflag == 1) {
                            org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(11, data));
                        } else {
                            org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(2, data));
                        }
                        this.d = callbackKey;
                    } else if ("album".equals(action2)) {
                        j(linkedTreeMap, callbackKey, data);
                    } else {
                        if (!"takePhoto".equals(action2)) {
                            if (!"camera".equals(action2)) {
                                if ("QRScan".equals(action2)) {
                                    String repositoryName = SharePreferenceUtils.getPrefString(activity2, "repositoryName", "");
                                    if (!repositoryName.equals("M071-AiDot")) {
                                        if (!repositoryName.equals("M071-Linkind")) {
                                            k(activity2, callbackKey, linkedTreeMap, data);
                                            return;
                                        }
                                    }
                                    m(activity2, callbackKey, linkedTreeMap, data);
                                    return;
                                } else if ("BLEQRScan".equals(action2)) {
                                    Intent intent = new Intent(this.a, ScanQRCodeActivity.class);
                                    String color = null;
                                    String title = "";
                                    if (linkedTreeMap.containsKey("fontColor")) {
                                        color = linkedTreeMap.get("fontColor").toString();
                                    }
                                    if (linkedTreeMap.containsKey("title")) {
                                        title = linkedTreeMap.get("title").toString();
                                    }
                                    if (linkedTreeMap.containsKey("QRColor")) {
                                        String QRColor = linkedTreeMap.get("QRColor").toString();
                                    }
                                    intent.putExtra(TypedValues.Custom.S_COLOR, color);
                                    intent.putExtra("title", title);
                                    intent.putExtra("hide_bottom", true);
                                    activity2.startActivity(intent);
                                    this.e = callbackKey;
                                    return;
                                } else if ("saveImage".equals(action2)) {
                                    this.h = callbackKey;
                                    String imageData = linkedTreeMap.get("imageData").toString();
                                    if (linkedTreeMap.containsKey("name")) {
                                        this.g = linkedTreeMap.get("name").toString().trim();
                                    }
                                    Bitmap n = n(imageData);
                                    this.f = n;
                                    if (n != null) {
                                        org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(7, ""));
                                    } else {
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("code", -1);
                                            jsonObject.put("desc", (Object) "");
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject.toString()));
                                    }
                                    return;
                                } else if ("closeQRScan".equals(action2)) {
                                    JSONObject jsonObject2 = new JSONObject();
                                    try {
                                        jsonObject2.put("code", 200);
                                        jsonObject2.put("desc", (Object) "");
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject2.toString()));
                                    String navigationType = "back";
                                    int delay = 0;
                                    if (linkedTreeMap.containsKey("navigationType")) {
                                        navigationType = (String) linkedTreeMap.get("navigationType");
                                    }
                                    if (linkedTreeMap.containsKey("delay")) {
                                        delay = ((Double) linkedTreeMap.get("delay")).intValue();
                                    }
                                    org.greenrobot.eventbus.c.c().l(new CloseQRScanEvent(navigationType, delay));
                                    return;
                                } else if ("previewMedia".equals(action2)) {
                                    JSONObject dataObj = new JSONObject(data);
                                    int position = dataObj.getInt("current");
                                    JSONArray urlsArr = dataObj.getJSONArray("urls");
                                    ArrayList<String> urls = new ArrayList<>();
                                    for (int i2 = 0; i2 < urlsArr.length(); i2++) {
                                        urls.add(urlsArr.getString(i2));
                                    }
                                    Intent intent2 = new Intent(this.a, ShowImageActivity.class);
                                    intent2.putExtra("position", position);
                                    intent2.putExtra("datas", urls);
                                    if (dataObj.has("isHideNavbar")) {
                                        intent2.putExtra("isHideNavbar", dataObj.getInt("isHideNavbar"));
                                    }
                                    if (dataObj.has("supportSaveToAlbum")) {
                                        intent2.putExtra("supportSaveToAlbum", dataObj.getInt("supportSaveToAlbum"));
                                    }
                                    if (dataObj.has("title")) {
                                        intent2.putExtra("title", dataObj.getString("title"));
                                    }
                                    String str7 = str6;
                                    if (dataObj.has(str7)) {
                                        intent2.putExtra(str7, dataObj.getString(str7));
                                    }
                                    String str8 = str5;
                                    if (dataObj.has(str8)) {
                                        intent2.putExtra(str8, dataObj.getString(str8));
                                    }
                                    String str9 = str4;
                                    if (dataObj.has(str9)) {
                                        intent2.putExtra(str9, dataObj.getJSONArray(str9).toString());
                                    }
                                    activity2.startActivity(intent2);
                                    return;
                                } else {
                                    if ("saveToAlbum".equals(action2)) {
                                        JSONObject dataObj2 = new JSONObject(data);
                                        String str10 = str3;
                                        if (dataObj2.has(str10)) {
                                            org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(201, dataObj2.getString(str10)));
                                        }
                                    } else if ("getPermission".equals(action2)) {
                                        JSONObject dataObj3 = new JSONObject();
                                        dataObj3.put("status", getCameraPermissionStatus(activity2));
                                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, p.d(dataObj3).toString()));
                                        return;
                                    }
                                    return;
                                }
                            }
                        }
                        org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(1, data));
                        this.d = callbackKey;
                    }
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }

    private Bitmap n(String string) {
        byte[] bitmapArray;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{string}, this, changeQuickRedirect, false, 7349, new Class[]{String.class}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        try {
            if (string.contains(",")) {
                bitmapArray = Base64.decode(string.split(",")[1], 0);
            } else {
                bitmapArray = Base64.decode(string, 0);
            }
            return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private boolean l(Bitmap bitmap, String name, Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap, name, context}, this, changeQuickRedirect, false, 7350, new Class[]{Bitmap.class, String.class, Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            String str = File.separator;
            sb.append(str);
            sb.append(Environment.DIRECTORY_DCIM);
            sb.append(str);
            sb.append("Camera");
            sb.append(str);
            String dir = sb.toString() + "/" + w.p(context) + "";
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            File mFile = new File(dir + "/" + name + ".jpg");
            a.b g2 = timber.log.a.g("CameraServiceImpl");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("saveImg: ");
            sb2.append(mFile.getPath());
            g2.c(sb2.toString(), new Object[0]);
            if (mFile.exists()) {
                mFile.delete();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(mFile));
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(mFile)));
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public void handleImageCrop(Intent data) {
        Bitmap bm;
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 7351, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            Bundle extras = data.getExtras();
            if (extras != null && (bm = (Bitmap) extras.getParcelable("data")) != null) {
                File file = w.Z(this.a, bm, this.a.getFilesDir().getPath() + "/web/static/media/", this.c);
                org.greenrobot.eventbus.c c2 = org.greenrobot.eventbus.c.c();
                String str = this.d;
                c2.l(new JsBridgeCallbackEvent(str, "{\"code\":200,\"desc\":\"\",\"data\":{\"uri\":\"" + this.c + "\",\"filePath\":\"" + ("/android/image" + file.getAbsolutePath()) + "\",\"absolutePath\":\"" + file.getPath() + "\"}}"));
            }
        }
    }

    public void handleCallBack(String callbackData) {
        if (!PatchProxy.proxy(new Object[]{callbackData}, this, changeQuickRedirect, false, 7352, new Class[]{String.class}, Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, callbackData));
        }
    }

    public void handleQRCodeData(String result) {
        if (!PatchProxy.proxy(new Object[]{result}, this, changeQuickRedirect, false, 7353, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (TextUtils.isEmpty(result)) {
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.e, "{\"code\":-1,\"desc\":\"\",\"data\":{}}"));
                return;
            }
            JSONObject matterJson = null;
            MatterService matterService = (MatterService) com.alibaba.android.arouter.launcher.a.c().g(MatterService.class);
            if (matterService != null) {
                matterJson = matterService.parseQrcode(result);
            }
            JSONObject dataJSON = new JSONObject();
            try {
                dataJSON.put("result", (Object) result);
                if (matterJson != null) {
                    dataJSON.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) "matter");
                    dataJSON.put("matterInfo", (Object) matterJson);
                } else {
                    dataJSON.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) "unknow");
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.e, p.d(dataJSON).toString()));
        }
    }

    public void saveQRCodePic() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7354, new Class[0], Void.TYPE).isSupported) {
            boolean isSuc = l(this.f, this.g, this.a);
            JSONObject jsonObject = new JSONObject();
            if (isSuc) {
                try {
                    jsonObject.put("code", 200);
                    jsonObject.put("desc", (Object) "");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                try {
                    jsonObject.put("code", -2);
                    jsonObject.put("desc", (Object) "qrcode save fail");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.h, jsonObject.toString()));
        }
    }

    public void clickBack() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7355, new Class[0], Void.TYPE).isSupported) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("code", 402);
                jsonObject.put("desc", (Object) "点击了返回");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.e, jsonObject.toString()));
        }
    }

    public void clickButton() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7356, new Class[0], Void.TYPE).isSupported) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("code", (int) BaseResp.ERR_MSG_SEND_FAIL);
                jsonObject.put("desc", (Object) "点击了 i can't scan");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            boolean b2 = ((JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(JsbridgeService.class)).hasCallbackKey(this.e);
            LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            if (loggerService != null) {
                loggerService.reportELK(this, "点击了 i can't scan,callbackKey:" + this.e + ",jsbridge has the callbackkey:" + b2, "debug", "ScanQrcode");
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.e, jsonObject.toString()));
        }
    }

    public void init(Context context) {
        this.a = context;
    }

    public void saveNetImage(String url) {
        if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 7357, new Class[]{String.class}, Void.TYPE).isSupported) {
            new com.utils.b().f(this.a, url);
        }
    }

    private void k(Activity activity, String str, LinkedTreeMap linkedTreeMap, String str2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{activity, str, linkedTreeMap, str2}, this, changeQuickRedirect, false, 7358, new Class[]{Activity.class, cls, LinkedTreeMap.class, cls}, Void.TYPE).isSupported) {
            String callbackKey = str;
            String data = str2;
            LinkedTreeMap linkedTreeMap2 = linkedTreeMap;
            if (this.j == null) {
                this.j = new com.tbruyelle.rxpermissions2.b(activity);
            }
            this.k = this.j.l("android.permission.CAMERA").Y(new a(activity, callbackKey, linkedTreeMap2, data), new b());
        }
    }

    public class a implements e<com.tbruyelle.rxpermissions2.a> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Activity c;
        final /* synthetic */ String d;
        final /* synthetic */ LinkedTreeMap f;
        final /* synthetic */ String q;

        a(Activity activity, String str, LinkedTreeMap linkedTreeMap, String str2) {
            this.c = activity;
            this.d = str;
            this.f = linkedTreeMap;
            this.q = str2;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7366, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.tbruyelle.rxpermissions2.a) obj);
            }
        }

        public void a(com.tbruyelle.rxpermissions2.a permission) {
            if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 7365, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
                if (loggerService != null) {
                    loggerService.reportPermissionSensorsData("ClickCameraPermissionSystemSettings", permission.b);
                }
                if (permission.b) {
                    timber.log.a.g("CZB").a("授权成功", new Object[0]);
                    CameraServiceImpl.a(CameraServiceImpl.this, this.c, this.d, this.f, this.q);
                } else if (permission.c) {
                    timber.log.a.g("CZB").a("拒绝，下次还可询问", new Object[0]);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("code", 402);
                        jsonObject.put("desc", (Object) "权限已拒绝，下次还可提示");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, jsonObject.toString()));
                } else {
                    timber.log.a.g("CZB").a("已拒绝且不在提示", new Object[0]);
                    JSONObject jsonObject2 = new JSONObject();
                    try {
                        jsonObject2.put("code", 401);
                        jsonObject2.put("desc", (Object) "权限已拒绝且不再提示");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, jsonObject2.toString()));
                }
            }
        }
    }

    public class b implements e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7368, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 7367, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                throwable.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:240:0x05bb  */
    /* JADX WARNING: Removed duplicated region for block: B:266:0x06d0  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0149 A[SYNTHETIC, Splitter:B:27:0x0149] */
    /* JADX WARNING: Removed duplicated region for block: B:300:0x0754  */
    /* JADX WARNING: Removed duplicated region for block: B:303:0x082d  */
    /* JADX WARNING: Removed duplicated region for block: B:304:0x0839  */
    /* JADX WARNING: Removed duplicated region for block: B:307:0x085d  */
    /* JADX WARNING: Removed duplicated region for block: B:308:0x0884  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m(android.app.Activity r76, java.lang.String r77, com.google.gson.internal.LinkedTreeMap r78, java.lang.String r79) {
        /*
            r75 = this;
            r0 = 4
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r76
            r0 = 1
            r1[r0] = r77
            r0 = 2
            r1[r0] = r78
            r0 = 3
            r1[r0] = r79
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            r0 = 4
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.app.Activity> r0 = android.app.Activity.class
            r6[r8] = r0
            r0 = 1
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r0] = r2
            r0 = 2
            java.lang.Class<com.google.gson.internal.LinkedTreeMap> r2 = com.google.gson.internal.LinkedTreeMap.class
            r6[r0] = r2
            r0 = 3
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r0] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 7359(0x1cbf, float:1.0312E-41)
            r2 = r75
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0037
            return
        L_0x0037:
            r1 = r75
            r2 = r77
            r3 = r79
            r4 = r76
            r5 = r78
            android.content.Intent r0 = new android.content.Intent
            android.content.Context r6 = r1.a
            java.lang.Class<com.leedarson.serviceimpl.camera.ScanQRCodeActivity> r7 = com.leedarson.serviceimpl.camera.ScanQRCodeActivity.class
            r0.<init>(r6, r7)
            r6 = r0
            r7 = 0
            java.lang.String r9 = ""
            java.lang.String r10 = ""
            java.lang.String r11 = ""
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            java.lang.String r14 = ""
            java.lang.String r15 = ""
            java.lang.String r16 = ""
            java.lang.String r17 = ""
            java.lang.String r18 = ""
            java.lang.String r19 = ""
            java.lang.String r20 = ""
            java.lang.String r21 = ""
            java.lang.String r22 = ""
            java.lang.String r23 = ""
            java.lang.String r24 = ""
            java.lang.String r25 = ""
            java.lang.String r26 = ""
            java.lang.String r27 = ""
            java.lang.String r28 = ""
            java.lang.String r29 = ""
            java.lang.String r30 = ""
            java.lang.String r31 = "center"
            java.lang.String r32 = ""
            java.lang.String r33 = ""
            r34 = 0
            java.lang.String r35 = ""
            java.lang.String r36 = ""
            r37 = 0
            r0 = 0
            java.lang.String r8 = "resultForNative"
            boolean r39 = r5.containsKey(r8)
            if (r39 == 0) goto L_0x009d
            java.lang.Object r39 = r5.get(r8)
            java.lang.Boolean r39 = (java.lang.Boolean) r39
            boolean r0 = r39.booleanValue()
            r76 = r7
            r7 = r0
            goto L_0x00a0
        L_0x009d:
            r76 = r7
            r7 = r0
        L_0x00a0:
            java.lang.String r0 = "introduction"
            boolean r0 = r5.containsKey(r0)
            r77 = r9
            java.lang.String r9 = "title"
            if (r0 == 0) goto L_0x00ed
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00e0 }
            r0.<init>((java.lang.String) r3)     // Catch:{ JSONException -> 0x00e0 }
            r78 = r0
            java.lang.String r0 = "introduction"
            r79 = r10
            r10 = r78
            org.json.JSONObject r0 = r10.getJSONObject(r0)     // Catch:{ JSONException -> 0x00d6 }
            java.lang.String r37 = r0.optString(r9)     // Catch:{ JSONException -> 0x00d6 }
            r32 = r37
            r37 = r10
            java.lang.String r10 = "description"
            java.lang.String r10 = r0.optString(r10)     // Catch:{ JSONException -> 0x00d4 }
            r33 = r10
            r78 = r11
            r10 = r32
            r11 = r33
            goto L_0x00f5
        L_0x00d4:
            r0 = move-exception
            goto L_0x00e3
        L_0x00d6:
            r0 = move-exception
            r37 = r10
            goto L_0x00e3
        L_0x00da:
            r0 = move-exception
            r37 = r78
            r79 = r10
            goto L_0x00e3
        L_0x00e0:
            r0 = move-exception
            r79 = r10
        L_0x00e3:
            r0.printStackTrace()
            r78 = r11
            r10 = r32
            r11 = r33
            goto L_0x00f5
        L_0x00ed:
            r79 = r10
            r78 = r11
            r10 = r32
            r11 = r33
        L_0x00f5:
            java.lang.String r0 = "navBar"
            boolean r0 = r5.containsKey(r0)
            r32 = r12
            java.lang.String r12 = "position"
            r33 = r13
            java.lang.String r13 = "textAlign"
            r39 = r14
            java.lang.String r14 = "fontWeight"
            r40 = r15
            java.lang.String r15 = "buttonHeight"
            r41 = r4
            java.lang.String r4 = "transitionDuration"
            r42 = r7
            java.lang.String r7 = "buttonBackgroundColor"
            r43 = r8
            java.lang.String r8 = "timeout"
            r44 = r2
            java.lang.String r2 = "buttonWidth"
            r45 = r11
            java.lang.String r11 = "buttonRadius"
            r46 = r10
            java.lang.String r10 = "titleIconColor"
            r47 = r1
            java.lang.String r1 = "titleColor"
            r48 = r8
            java.lang.String r8 = "cornersColor"
            r49 = r6
            java.lang.String r6 = "scanLineColor"
            r50 = r12
            java.lang.String r12 = "borderColor"
            r51 = r15
            java.lang.String r15 = "color"
            r52 = r15
            java.lang.String r15 = "buttonTitle"
            r53 = r15
            java.lang.String r15 = "contentTitle"
            r54 = r15
            java.lang.String r15 = "turnOff"
            r55 = r15
            java.lang.String r15 = "turnOn"
            if (r0 == 0) goto L_0x05bb
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0553 }
            r0.<init>((java.lang.String) r3)     // Catch:{ Exception -> 0x0553 }
            r56 = r3
            java.lang.String r3 = "turnOffIconColor"
            boolean r3 = r5.containsKey(r3)     // Catch:{ Exception -> 0x0536 }
            if (r3 == 0) goto L_0x0181
            java.lang.String r3 = "turnOffIconColor"
            java.lang.Object r3 = r5.get(r3)     // Catch:{ Exception -> 0x0163 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0163 }
            goto L_0x0183
        L_0x0163:
            r0 = move-exception
            r57 = r77
            r58 = r79
            r3 = r0
            r38 = r4
            r60 = r13
            r4 = r48
            r13 = r49
            r0 = r76
            r48 = r7
            r7 = r52
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            goto L_0x0571
        L_0x0181:
            r3 = r76
        L_0x0183:
            r76 = r3
            java.lang.String r3 = "turnOnText"
            boolean r3 = r5.containsKey(r3)     // Catch:{ Exception -> 0x0536 }
            if (r3 == 0) goto L_0x0199
            java.lang.String r3 = "turnOnText"
            java.lang.Object r3 = r5.get(r3)     // Catch:{ Exception -> 0x0163 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0163 }
            r32 = r3
        L_0x0199:
            java.lang.String r3 = "turnOffText"
            boolean r3 = r5.containsKey(r3)     // Catch:{ Exception -> 0x0536 }
            if (r3 == 0) goto L_0x01ad
            java.lang.String r3 = "turnOffText"
            java.lang.Object r3 = r5.get(r3)     // Catch:{ Exception -> 0x0163 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0163 }
            r33 = r3
        L_0x01ad:
            java.lang.String r3 = "navBar"
            java.lang.String r3 = r0.optString(r3)     // Catch:{ Exception -> 0x0536 }
            com.google.gson.internal.LinkedTreeMap r3 = com.leedarson.serviceimpl.camera.i.a(r3)     // Catch:{ Exception -> 0x0536 }
            boolean r57 = r3.containsKey(r9)     // Catch:{ Exception -> 0x0536 }
            if (r57 == 0) goto L_0x01c6
            java.lang.Object r57 = r3.get(r9)     // Catch:{ Exception -> 0x0163 }
            java.lang.String r57 = r57.toString()     // Catch:{ Exception -> 0x0163 }
            goto L_0x01c8
        L_0x01c6:
            r57 = r77
        L_0x01c8:
            boolean r58 = r3.containsKey(r14)     // Catch:{ Exception -> 0x051b }
            if (r58 == 0) goto L_0x01f3
            java.lang.Object r58 = r3.get(r14)     // Catch:{ Exception -> 0x01d7 }
            java.lang.String r58 = r58.toString()     // Catch:{ Exception -> 0x01d7 }
            goto L_0x01f5
        L_0x01d7:
            r0 = move-exception
            r58 = r79
            r3 = r0
            r38 = r4
            r60 = r13
            r4 = r48
            r13 = r49
            r0 = r76
            r48 = r7
            r7 = r52
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            goto L_0x0571
        L_0x01f3:
            r58 = r79
        L_0x01f5:
            boolean r59 = r3.containsKey(r13)     // Catch:{ Exception -> 0x0501 }
            if (r59 == 0) goto L_0x0220
            java.lang.Object r59 = r3.get(r13)     // Catch:{ Exception -> 0x0206 }
            java.lang.String r59 = r59.toString()     // Catch:{ Exception -> 0x0206 }
            r31 = r59
            goto L_0x0220
        L_0x0206:
            r0 = move-exception
            r3 = r0
            r38 = r4
            r60 = r13
            r4 = r48
            r13 = r49
            r0 = r76
            r48 = r7
            r7 = r52
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            goto L_0x0571
        L_0x0220:
            r59 = r3
            java.lang.String r3 = "scanframe"
            boolean r3 = r0.has(r3)     // Catch:{ Exception -> 0x0501 }
            if (r3 == 0) goto L_0x0264
            java.lang.String r3 = "scanframe"
            java.lang.String r3 = r0.optString(r3)     // Catch:{ Exception -> 0x0206 }
            com.google.gson.internal.LinkedTreeMap r3 = com.leedarson.serviceimpl.camera.i.a(r3)     // Catch:{ Exception -> 0x0206 }
            boolean r60 = r3.containsKey(r12)     // Catch:{ Exception -> 0x0206 }
            if (r60 == 0) goto L_0x0244
            java.lang.Object r60 = r3.get(r12)     // Catch:{ Exception -> 0x0206 }
            java.lang.String r60 = r60.toString()     // Catch:{ Exception -> 0x0206 }
            r19 = r60
        L_0x0244:
            boolean r60 = r3.containsKey(r8)     // Catch:{ Exception -> 0x0206 }
            if (r60 == 0) goto L_0x0254
            java.lang.Object r60 = r3.get(r8)     // Catch:{ Exception -> 0x0206 }
            java.lang.String r60 = r60.toString()     // Catch:{ Exception -> 0x0206 }
            r21 = r60
        L_0x0254:
            boolean r60 = r3.containsKey(r6)     // Catch:{ Exception -> 0x0206 }
            if (r60 == 0) goto L_0x0264
            java.lang.Object r60 = r3.get(r6)     // Catch:{ Exception -> 0x0206 }
            java.lang.String r60 = r60.toString()     // Catch:{ Exception -> 0x0206 }
            r20 = r60
        L_0x0264:
            java.lang.String r3 = "menu"
            boolean r3 = r0.has(r3)     // Catch:{ Exception -> 0x0501 }
            if (r3 == 0) goto L_0x0435
            java.lang.String r3 = "menu"
            java.lang.String r3 = r0.optString(r3)     // Catch:{ Exception -> 0x041b }
            com.google.gson.internal.LinkedTreeMap r3 = com.leedarson.serviceimpl.camera.i.a(r3)     // Catch:{ Exception -> 0x041b }
            boolean r60 = r3.containsKey(r9)     // Catch:{ Exception -> 0x041b }
            if (r60 == 0) goto L_0x0286
            java.lang.Object r60 = r3.get(r9)     // Catch:{ Exception -> 0x0206 }
            java.lang.String r60 = r60.toString()     // Catch:{ Exception -> 0x0206 }
            r39 = r60
        L_0x0286:
            r60 = r13
            java.lang.String r13 = "buttonColor"
            boolean r13 = r3.containsKey(r13)     // Catch:{ Exception -> 0x0403 }
            if (r13 == 0) goto L_0x02b5
            java.lang.String r13 = "buttonColor"
            java.lang.Object r13 = r3.get(r13)     // Catch:{ Exception -> 0x029d }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x029d }
            r16 = r13
            goto L_0x02b5
        L_0x029d:
            r0 = move-exception
            r3 = r0
            r38 = r4
            r4 = r48
            r13 = r49
            r0 = r76
            r48 = r7
            r7 = r52
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            goto L_0x0571
        L_0x02b5:
            java.lang.String r13 = "buttonText"
            boolean r13 = r3.containsKey(r13)     // Catch:{ Exception -> 0x0403 }
            if (r13 == 0) goto L_0x02c8
            java.lang.String r13 = "buttonText"
            java.lang.Object r13 = r3.get(r13)     // Catch:{ Exception -> 0x029d }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x029d }
            goto L_0x02ca
        L_0x02c8:
            r13 = r40
        L_0x02ca:
            boolean r40 = r3.containsKey(r7)     // Catch:{ Exception -> 0x03e7 }
            if (r40 == 0) goto L_0x02f5
            java.lang.Object r40 = r3.get(r7)     // Catch:{ Exception -> 0x02db }
            java.lang.String r40 = r40.toString()     // Catch:{ Exception -> 0x02db }
            r17 = r40
            goto L_0x02f5
        L_0x02db:
            r0 = move-exception
            r3 = r0
            r38 = r4
            r40 = r13
            r4 = r48
            r13 = r49
            r0 = r76
            r48 = r7
            r7 = r52
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            goto L_0x0571
        L_0x02f5:
            boolean r40 = r3.containsKey(r4)     // Catch:{ Exception -> 0x03e7 }
            if (r40 == 0) goto L_0x0305
            java.lang.Object r40 = r3.get(r4)     // Catch:{ Exception -> 0x02db }
            java.lang.String r40 = r40.toString()     // Catch:{ Exception -> 0x02db }
            r18 = r40
        L_0x0305:
            boolean r40 = r3.containsKey(r1)     // Catch:{ Exception -> 0x03e7 }
            if (r40 == 0) goto L_0x0315
            java.lang.Object r40 = r3.get(r1)     // Catch:{ Exception -> 0x02db }
            java.lang.String r40 = r40.toString()     // Catch:{ Exception -> 0x02db }
            r22 = r40
        L_0x0315:
            boolean r40 = r3.containsKey(r10)     // Catch:{ Exception -> 0x03e7 }
            if (r40 == 0) goto L_0x0325
            java.lang.Object r40 = r3.get(r10)     // Catch:{ Exception -> 0x02db }
            java.lang.String r40 = r40.toString()     // Catch:{ Exception -> 0x02db }
            r24 = r40
        L_0x0325:
            boolean r40 = r3.containsKey(r11)     // Catch:{ Exception -> 0x03e7 }
            if (r40 == 0) goto L_0x0335
            java.lang.Object r40 = r3.get(r11)     // Catch:{ Exception -> 0x02db }
            java.lang.String r40 = r40.toString()     // Catch:{ Exception -> 0x02db }
            r25 = r40
        L_0x0335:
            boolean r40 = r3.containsKey(r2)     // Catch:{ Exception -> 0x03e7 }
            if (r40 == 0) goto L_0x0345
            java.lang.Object r40 = r3.get(r2)     // Catch:{ Exception -> 0x02db }
            java.lang.String r40 = r40.toString()     // Catch:{ Exception -> 0x02db }
            r26 = r40
        L_0x0345:
            r77 = r13
            r13 = r51
            boolean r40 = r3.containsKey(r13)     // Catch:{ Exception -> 0x03cf }
            if (r40 == 0) goto L_0x0372
            java.lang.Object r40 = r3.get(r13)     // Catch:{ Exception -> 0x035a }
            java.lang.String r40 = r40.toString()     // Catch:{ Exception -> 0x035a }
            r27 = r40
            goto L_0x0372
        L_0x035a:
            r0 = move-exception
            r40 = r77
            r3 = r0
            r38 = r4
            r51 = r14
            r4 = r48
            r14 = r50
            r0 = r76
            r48 = r7
            r50 = r13
            r13 = r49
            r7 = r52
            goto L_0x0571
        L_0x0372:
            r51 = r14
            r14 = r50
            boolean r40 = r3.containsKey(r14)     // Catch:{ Exception -> 0x03bb }
            if (r40 == 0) goto L_0x039b
            java.lang.Object r40 = r3.get(r14)     // Catch:{ Exception -> 0x0387 }
            java.lang.String r40 = r40.toString()     // Catch:{ Exception -> 0x0387 }
            r23 = r40
            goto L_0x039b
        L_0x0387:
            r0 = move-exception
            r40 = r77
            r3 = r0
            r38 = r4
            r50 = r13
            r4 = r48
            r13 = r49
            r0 = r76
            r48 = r7
            r7 = r52
            goto L_0x0571
        L_0x039b:
            r79 = r3
            java.lang.String r3 = "hide_bottom"
            r38 = r4
            r50 = r13
            r13 = r49
            r4 = 0
            r13.putExtra(r3, r4)     // Catch:{ Exception -> 0x03ad }
            r40 = r77
            goto L_0x0443
        L_0x03ad:
            r0 = move-exception
            r40 = r77
            r3 = r0
            r4 = r48
            r0 = r76
            r48 = r7
            r7 = r52
            goto L_0x0571
        L_0x03bb:
            r0 = move-exception
            r38 = r4
            r50 = r13
            r13 = r49
            r40 = r77
            r3 = r0
            r4 = r48
            r0 = r76
            r48 = r7
            r7 = r52
            goto L_0x0571
        L_0x03cf:
            r0 = move-exception
            r38 = r4
            r51 = r14
            r14 = r50
            r50 = r13
            r13 = r49
            r40 = r77
            r3 = r0
            r4 = r48
            r0 = r76
            r48 = r7
            r7 = r52
            goto L_0x0571
        L_0x03e7:
            r0 = move-exception
            r38 = r4
            r77 = r13
            r13 = r49
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            r40 = r77
            r3 = r0
            r4 = r48
            r0 = r76
            r48 = r7
            r7 = r52
            goto L_0x0571
        L_0x0403:
            r0 = move-exception
            r38 = r4
            r13 = r49
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            r3 = r0
            r4 = r48
            r0 = r76
            r48 = r7
            r7 = r52
            goto L_0x0571
        L_0x041b:
            r0 = move-exception
            r38 = r4
            r60 = r13
            r13 = r49
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            r3 = r0
            r4 = r48
            r0 = r76
            r48 = r7
            r7 = r52
            goto L_0x0571
        L_0x0435:
            r38 = r4
            r60 = r13
            r13 = r49
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
        L_0x0443:
            java.lang.String r3 = "processing"
            boolean r3 = r0.has(r3)     // Catch:{ Exception -> 0x04f5 }
            if (r3 == 0) goto L_0x04a7
            java.lang.String r3 = "processing"
            java.lang.String r3 = r0.optString(r3)     // Catch:{ Exception -> 0x04f5 }
            com.google.gson.internal.LinkedTreeMap r3 = com.leedarson.serviceimpl.camera.i.a(r3)     // Catch:{ Exception -> 0x04f5 }
            boolean r4 = r3.containsKey(r9)     // Catch:{ Exception -> 0x04f5 }
            if (r4 == 0) goto L_0x0472
            java.lang.Object r4 = r3.get(r9)     // Catch:{ Exception -> 0x0466 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0466 }
            r28 = r4
            goto L_0x0472
        L_0x0466:
            r0 = move-exception
            r3 = r0
            r4 = r48
            r0 = r76
            r48 = r7
            r7 = r52
            goto L_0x0571
        L_0x0472:
            r4 = r48
            boolean r48 = r3.containsKey(r4)     // Catch:{ Exception -> 0x04a5 }
            if (r48 == 0) goto L_0x048d
            java.lang.Object r48 = r3.get(r4)     // Catch:{ Exception -> 0x0485 }
            java.lang.String r48 = r48.toString()     // Catch:{ Exception -> 0x0485 }
            r30 = r48
            goto L_0x048d
        L_0x0485:
            r0 = move-exception
            r3 = r0
            r48 = r7
            r7 = r52
            goto L_0x04fd
        L_0x048d:
            r48 = r7
            r7 = r52
            boolean r49 = r3.containsKey(r7)     // Catch:{ Exception -> 0x04a2 }
            if (r49 == 0) goto L_0x04ad
            java.lang.Object r49 = r3.get(r7)     // Catch:{ Exception -> 0x04a2 }
            java.lang.String r49 = r49.toString()     // Catch:{ Exception -> 0x04a2 }
            r29 = r49
            goto L_0x04ad
        L_0x04a2:
            r0 = move-exception
            goto L_0x04fc
        L_0x04a5:
            r0 = move-exception
            goto L_0x04f8
        L_0x04a7:
            r4 = r48
            r48 = r7
            r7 = r52
        L_0x04ad:
            r77 = r2
            r49 = r4
            r79 = r8
            r62 = r17
            r63 = r18
            r18 = r21
            r64 = r22
            r65 = r23
            r66 = r24
            r67 = r25
            r68 = r26
            r69 = r27
            r70 = r28
            r71 = r29
            r72 = r30
            r73 = r31
            r8 = r32
            r2 = r53
            r4 = r54
            r3 = r55
            r61 = r58
            r53 = r11
            r22 = r12
            r17 = r16
            r21 = r19
            r19 = r20
            r12 = r57
            r11 = r76
            r16 = r78
            r78 = r1
            r20 = r6
            r76 = r10
            r1 = r33
            r6 = r39
            r10 = r40
            goto L_0x06c8
        L_0x04f5:
            r0 = move-exception
            r4 = r48
        L_0x04f8:
            r48 = r7
            r7 = r52
        L_0x04fc:
            r3 = r0
        L_0x04fd:
            r0 = r76
            goto L_0x0571
        L_0x0501:
            r0 = move-exception
            r38 = r4
            r60 = r13
            r4 = r48
            r13 = r49
            r48 = r7
            r7 = r52
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            r3 = r0
            r0 = r76
            goto L_0x0571
        L_0x051b:
            r0 = move-exception
            r38 = r4
            r60 = r13
            r4 = r48
            r13 = r49
            r48 = r7
            r7 = r52
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            r58 = r79
            r3 = r0
            r0 = r76
            goto L_0x0571
        L_0x0536:
            r0 = move-exception
            r38 = r4
            r60 = r13
            r4 = r48
            r13 = r49
            r48 = r7
            r7 = r52
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            r57 = r77
            r58 = r79
            r3 = r0
            r0 = r76
            goto L_0x0571
        L_0x0553:
            r0 = move-exception
            r56 = r3
            r38 = r4
            r60 = r13
            r4 = r48
            r13 = r49
            r48 = r7
            r7 = r52
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            r57 = r77
            r58 = r79
            r3 = r0
            r0 = r76
        L_0x0571:
            r3.printStackTrace()
            r77 = r2
            r49 = r4
            r79 = r8
            r76 = r10
            r62 = r17
            r63 = r18
            r18 = r21
            r64 = r22
            r65 = r23
            r66 = r24
            r67 = r25
            r68 = r26
            r69 = r27
            r70 = r28
            r71 = r29
            r72 = r30
            r73 = r31
            r8 = r32
            r10 = r40
            r2 = r53
            r4 = r54
            r3 = r55
            r61 = r58
            r53 = r11
            r22 = r12
            r17 = r16
            r21 = r19
            r19 = r20
            r12 = r57
            r16 = r78
            r11 = r0
            r78 = r1
            r20 = r6
            r1 = r33
            r6 = r39
            goto L_0x06c8
        L_0x05bb:
            r56 = r3
            r38 = r4
            r60 = r13
            r4 = r48
            r13 = r49
            r48 = r7
            r7 = r52
            r74 = r51
            r51 = r14
            r14 = r50
            r50 = r74
            java.lang.String r0 = "fontColor"
            boolean r0 = r5.containsKey(r0)
            if (r0 == 0) goto L_0x05e4
            java.lang.String r0 = "fontColor"
            java.lang.Object r0 = r5.get(r0)
            java.lang.String r0 = r0.toString()
            goto L_0x05e6
        L_0x05e4:
            r0 = r76
        L_0x05e6:
            boolean r3 = r5.containsKey(r9)
            if (r3 == 0) goto L_0x05f5
            java.lang.Object r3 = r5.get(r9)
            java.lang.String r3 = r3.toString()
            goto L_0x05f7
        L_0x05f5:
            r3 = r77
        L_0x05f7:
            boolean r49 = r5.containsKey(r15)
            if (r49 == 0) goto L_0x0605
            java.lang.Object r49 = r5.get(r15)
            java.lang.String r32 = r49.toString()
        L_0x0605:
            r76 = r3
            r3 = r55
            boolean r49 = r5.containsKey(r3)
            if (r49 == 0) goto L_0x0617
            java.lang.Object r49 = r5.get(r3)
            java.lang.String r33 = r49.toString()
        L_0x0617:
            r49 = r4
            r4 = r54
            boolean r52 = r5.containsKey(r4)
            if (r52 == 0) goto L_0x0629
            java.lang.Object r52 = r5.get(r4)
            java.lang.String r39 = r52.toString()
        L_0x0629:
            r77 = r2
            r2 = r53
            boolean r52 = r5.containsKey(r2)
            if (r52 == 0) goto L_0x063b
            java.lang.Object r52 = r5.get(r2)
            java.lang.String r40 = r52.toString()
        L_0x063b:
            r52 = r0
            java.lang.String r0 = "QRColor"
            boolean r0 = r5.containsKey(r0)
            if (r0 == 0) goto L_0x068c
            java.lang.String r0 = "QRColor"
            java.lang.Object r0 = r5.get(r0)
            java.lang.String r0 = r0.toString()
            r61 = r79
            r78 = r1
            r79 = r8
            r53 = r11
            r62 = r17
            r63 = r18
            r18 = r21
            r64 = r22
            r65 = r23
            r66 = r24
            r67 = r25
            r68 = r26
            r69 = r27
            r70 = r28
            r71 = r29
            r72 = r30
            r73 = r31
            r8 = r32
            r1 = r33
            r11 = r52
            r22 = r12
            r17 = r16
            r21 = r19
            r19 = r20
            r12 = r76
            r16 = r0
            r20 = r6
            r76 = r10
            r6 = r39
            r10 = r40
            goto L_0x06c8
        L_0x068c:
            r61 = r79
            r79 = r8
            r53 = r11
            r62 = r17
            r63 = r18
            r18 = r21
            r64 = r22
            r65 = r23
            r66 = r24
            r67 = r25
            r68 = r26
            r69 = r27
            r70 = r28
            r71 = r29
            r72 = r30
            r73 = r31
            r8 = r32
            r11 = r52
            r22 = r12
            r17 = r16
            r21 = r19
            r19 = r20
            r12 = r76
            r16 = r78
            r78 = r1
            r20 = r6
            r76 = r10
            r1 = r33
            r6 = r39
            r10 = r40
        L_0x06c8:
            java.lang.String r0 = "addManualButton"
            boolean r0 = r5.containsKey(r0)
            if (r0 == 0) goto L_0x0754
            if (r37 != 0) goto L_0x06f0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x06e8 }
            r23 = r5
            r5 = r56
            r0.<init>((java.lang.String) r5)     // Catch:{ JSONException -> 0x06df }
            r56 = r5
            r5 = r0
            goto L_0x06f6
        L_0x06df:
            r0 = move-exception
            r56 = r5
            r52 = r7
            r25 = r11
            goto L_0x074a
        L_0x06e8:
            r0 = move-exception
            r23 = r5
            r52 = r7
            r25 = r11
            goto L_0x074a
        L_0x06f0:
            r23 = r5
            r5 = r56
            r5 = r37
        L_0x06f6:
            java.lang.String r0 = "addManualButton"
            org.json.JSONObject r0 = r5.getJSONObject(r0)     // Catch:{ JSONException -> 0x0741 }
            r24 = r5
            java.lang.String r5 = "visible"
            int r5 = r0.optInt(r5)     // Catch:{ JSONException -> 0x0739 }
            r34 = r5
            java.lang.String r5 = "text"
            r52 = r7
            r25 = r11
            r7 = r47
            android.content.Context r11 = r7.a     // Catch:{ JSONException -> 0x0733 }
            r47 = r7
            int r7 = com.leedarson.serviceimpl.camera.R$string.add_manually     // Catch:{ JSONException -> 0x072f }
            java.lang.String r7 = com.leedarson.serviceinterface.utils.PubUtils.getString(r11, r7)     // Catch:{ JSONException -> 0x072f }
            java.lang.String r5 = r0.optString(r5, r7)     // Catch:{ JSONException -> 0x072f }
            r35 = r5
            java.lang.String r5 = "bottom"
            java.lang.String r5 = r0.optString(r14, r5)     // Catch:{ JSONException -> 0x072f }
            r36 = r5
            r37 = r24
            r0 = r34
            r5 = r35
            r7 = r36
            goto L_0x0760
        L_0x072f:
            r0 = move-exception
            r37 = r24
            goto L_0x074a
        L_0x0733:
            r0 = move-exception
            r47 = r7
            r37 = r24
            goto L_0x074a
        L_0x0739:
            r0 = move-exception
            r52 = r7
            r25 = r11
            r37 = r24
            goto L_0x074a
        L_0x0741:
            r0 = move-exception
            r24 = r5
            r52 = r7
            r25 = r11
            r37 = r24
        L_0x074a:
            r0.printStackTrace()
            r0 = r34
            r5 = r35
            r7 = r36
            goto L_0x0760
        L_0x0754:
            r23 = r5
            r52 = r7
            r25 = r11
            r0 = r34
            r5 = r35
            r7 = r36
        L_0x0760:
            java.lang.String r11 = "introductionTitle"
            r14 = r46
            r13.putExtra(r11, r14)
            java.lang.String r11 = "introductionDesc"
            r14 = r45
            r13.putExtra(r11, r14)
            r13.putExtra(r2, r10)
            r13.putExtra(r4, r6)
            r13.putExtra(r3, r1)
            r13.putExtra(r15, r8)
            r13.putExtra(r9, r12)
            r2 = r25
            r3 = r52
            r13.putExtra(r3, r2)
            r3 = r21
            r4 = r22
            r13.putExtra(r4, r3)
            r4 = r19
            r9 = r20
            r13.putExtra(r9, r4)
            r11 = r79
            r9 = r18
            r13.putExtra(r11, r9)
            r15 = r78
            r11 = r64
            r13.putExtra(r15, r11)
            java.lang.String r15 = "layoutType"
            r18 = r1
            r1 = r65
            r13.putExtra(r15, r1)
            r19 = r1
            r15 = r66
            r1 = r76
            r13.putExtra(r1, r15)
            r2 = r53
            r1 = r67
            r13.putExtra(r2, r1)
            r20 = r1
            r2 = r68
            r1 = r77
            r13.putExtra(r1, r2)
            java.lang.String r1 = "processingTitle"
            r21 = r2
            r2 = r70
            r13.putExtra(r1, r2)
            java.lang.String r1 = "processingColor"
            r22 = r2
            r2 = r71
            r13.putExtra(r1, r2)
            r24 = r2
            r2 = r49
            r1 = r72
            r13.putExtra(r2, r1)
            r26 = r1
            r1 = r48
            r2 = r62
            r13.putExtra(r1, r2)
            r27 = r2
            r2 = r38
            r1 = r63
            r13.putExtra(r2, r1)
            r28 = r1
            r1 = r50
            r2 = r69
            r13.putExtra(r1, r2)
            r29 = r2
            r2 = r51
            r1 = r61
            r13.putExtra(r2, r1)
            r30 = r1
            r1 = r60
            r2 = r73
            r13.putExtra(r1, r2)
            java.lang.String r1 = "callbackKey"
            r31 = r2
            r2 = r44
            r13.putExtra(r1, r2)
            java.lang.String r1 = "manualButtonVisible"
            r13.putExtra(r1, r0)
            java.lang.String r1 = "manualText"
            r13.putExtra(r1, r5)
            java.lang.String r1 = "manualPosition"
            r13.putExtra(r1, r7)
            r32 = r3
            r1 = r42
            r3 = r43
            r13.putExtra(r3, r1)
            if (r1 == 0) goto L_0x0839
            r3 = 11
            r42 = r1
            r1 = r41
            r1.startActivityForResult(r13, r3)
            r33 = r0
            goto L_0x0849
        L_0x0839:
            r42 = r1
            r1 = r41
            r1.startActivity(r13)
            int r3 = com.leedarson.serviceimpl.camera.R$anim.slide_in_right
            r33 = r0
            int r0 = com.leedarson.serviceimpl.camera.R$anim.slide_in_left
            r1.overridePendingTransition(r3, r0)
        L_0x0849:
            r3 = r47
            r3.e = r2
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            r41 = r1
            java.lang.Class<com.leedarson.serviceinterface.LoggerService> r1 = com.leedarson.serviceinterface.LoggerService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.LoggerService r0 = (com.leedarson.serviceinterface.LoggerService) r0
            if (r0 == 0) goto L_0x0884
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r34 = r4
            java.lang.String r4 = "打开扫码页:"
            r1.append(r4)
            r4 = r56
            r1.append(r4)
            java.lang.String r4 = ",callbackKey:"
            r1.append(r4)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r4 = "debug"
            r44 = r2
            java.lang.String r2 = "ScanQrcode"
            r0.reportELK(r3, r1, r4, r2)
            goto L_0x0888
        L_0x0884:
            r44 = r2
            r34 = r4
        L_0x0888:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.camera.CameraServiceImpl.m(android.app.Activity, java.lang.String, com.google.gson.internal.LinkedTreeMap, java.lang.String):void");
    }

    public int getCameraPermissionStatus(Activity activity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 7360, new Class[]{Activity.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (EasyPermissions.a(activity, "android.permission.CAMERA")) {
            return 3;
        }
        if (!SharePreferenceUtils.getPrefBoolean(activity, "camera_deny", false)) {
            return 0;
        }
        if (EasyPermissions.h(activity, "android.permission.CAMERA") || EasyPermissions.h(activity, "android.permission.CAMERA")) {
            return 1;
        }
        return 2;
    }

    private void j(LinkedTreeMap linkedTreeMap, String callbackKey, String data) {
        int videoflag;
        String[] perms;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{linkedTreeMap, callbackKey, data}, this, changeQuickRedirect, false, 7361, new Class[]{LinkedTreeMap.class, cls, cls}, Void.TYPE).isSupported) {
            if (linkedTreeMap.containsKey("videoFlag")) {
                videoflag = (int) Double.parseDouble(linkedTreeMap.get("videoFlag").toString());
            } else {
                videoflag = 0;
            }
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"};
            }
            if (EasyPermissions.a(this.a, perms)) {
                if (videoflag == 1) {
                    org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(11, data));
                } else {
                    org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(2, data));
                }
                this.d = callbackKey;
                return;
            }
            LDSPermissionGuide.GuideParam param = new LDSPermissionGuide.AlbumGuideParam(this.a);
            Activity activity = this.i;
            if (activity != null && (activity instanceof FragmentActivity)) {
                LDSPermissionGuide.d((FragmentActivity) activity, param, new c(perms, linkedTreeMap, callbackKey, data));
            }
        }
    }

    public class c implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;
        final /* synthetic */ LinkedTreeMap b;
        final /* synthetic */ String c;
        final /* synthetic */ String d;

        c(String[] strArr, LinkedTreeMap linkedTreeMap, String str, String str2) {
            this.a = strArr;
            this.b = linkedTreeMap;
            this.c = str;
            this.d = str2;
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 7369, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, CameraServiceImpl.this.i, this.a, "albumDeny", new a(this, this.b, this.c, this.d));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b(LinkedTreeMap linkedTreeMap, String callbackKey, String data) {
            Class<String> cls = String.class;
            Class[] clsArr = {LinkedTreeMap.class, cls, cls};
            if (!PatchProxy.proxy(new Object[]{linkedTreeMap, callbackKey, data}, this, changeQuickRedirect, false, 7370, clsArr, Void.TYPE).isSupported) {
                CameraServiceImpl.i(CameraServiceImpl.this, linkedTreeMap, callbackKey, data);
            }
        }
    }

    public void scanQrcodeForNative(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 7362, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            LinkedTreeMap map = new LinkedTreeMap();
            map.put("resultForNative", true);
            k(activity, "", map, "");
        }
    }
}
