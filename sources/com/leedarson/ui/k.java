package com.leedarson.ui;

import android.content.Intent;
import android.net.Uri;
import com.leedarson.R$string;
import com.leedarson.bean.H5ActionName;
import com.leedarson.log.f;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.utils.FileUtils;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.h0;
import com.leedarson.smartcamera.utils.e;
import com.leedarson.utils.j;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

/* compiled from: SDPlayPresenter */
public class k extends com.leedarson.base.presenters.a<l, SDPlayFragment> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Timer f;
    private String g = "";
    /* access modifiers changed from: private */
    public int h;
    String i;
    SimpleDateFormat j;

    static /* synthetic */ int s(k x0) {
        int i2 = x0.h;
        x0.h = i2 + 1;
        return i2;
    }

    static /* synthetic */ void t(k x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 11425, new Class[]{k.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.u(x1);
        }
    }

    public k(l view, SDPlayFragment activity) {
        super(view, activity);
    }

    public void G(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 11407, new Class[]{String.class}, Void.TYPE).isSupported) {
            String deviceName = str;
            f.b("Record", "startRecord: " + deviceName);
            if (((l) m()).x() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                this.g = com.leedarson.smartcamera.utils.f.b(((SDPlayFragment) l()).getContext()) + (deviceName + "_" + formatter.format(new Date()) + ".mp4");
                if (((l) m()).x().R(this.g) == 0) {
                    L();
                    Timer timer = new Timer();
                    this.f = timer;
                    this.h = 0;
                    timer.schedule(new a(), 100, 1000);
                    ((l) m()).d();
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                        jsonObject.put("desc", (Object) "");
                        jsonObject.put("messageCode", 10005);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                        jsonObject2.put("desc", (Object) "");
                        jsonObject2.put("messageCode", 10006);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    /* compiled from: SDPlayPresenter */
    public class a extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11426, new Class[0], Void.TYPE).isSupported) {
                if (k.this.m() != null) {
                    k.s(k.this);
                    ((l) k.this.m()).e(k.this.h);
                    e.e("", "startRecord:" + k.this.h);
                }
            }
        }
    }

    public void K() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11408, new Class[0], Void.TYPE).isSupported) {
            if (((l) m()).x() != null) {
                int result = ((l) m()).x().W();
                L();
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", 10006);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (this.h < 3) {
                    FileUtils.deleteFile(this.g);
                    ((l) m()).showToast(R$string.player_videotape_fail);
                    this.h = 0;
                    ((l) m()).c();
                    return;
                }
                this.h = 0;
                if (result == 0) {
                    ((l) m()).c();
                    try {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                        jsonObject2.put("desc", (Object) "");
                        jsonObject2.put("messageCode", 10007);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                        ((SDPlayFragment) l()).getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.g))));
                        ((l) m()).showToast(R$string.player_videotape_sucess);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    ((l) m()).showToast(R$string.player_videotape_fail);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void L() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 11409(0x2c91, float:1.5987E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.f
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.f = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.k.L():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00e4 A[Catch:{ Exception -> 0x00fc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void E(java.lang.String r11, int r12) {
        /*
            r10 = this;
            java.lang.String r0 = ""
            r1 = 2
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            r2[r3] = r11
            java.lang.Integer r4 = new java.lang.Integer
            r4.<init>(r12)
            r9 = 1
            r2[r9] = r4
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r7[r3] = r5
            java.lang.Class r3 = java.lang.Integer.TYPE
            r7[r9] = r3
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 11410(0x2c92, float:1.5989E-41)
            r3 = r10
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x002b
            return
        L_0x002b:
            r2 = r10
            java.lang.Object r3 = r2.m()     // Catch:{ Exception -> 0x00fc }
            com.leedarson.ui.l r3 = (com.leedarson.ui.l) r3     // Catch:{ Exception -> 0x00fc }
            com.leedarson.smartcamera.codec.c r3 = r3.x()     // Catch:{ Exception -> 0x00fc }
            if (r3 == 0) goto L_0x00fb
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x00fc }
            java.lang.String r4 = "yyyyMMddHHmmss"
            r3.<init>(r4)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r4 = ".jpg"
            if (r12 != r1) goto L_0x0084
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fc }
            r0.<init>()     // Catch:{ Exception -> 0x00fc }
            r0.append(r11)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r1 = "_"
            r0.append(r1)     // Catch:{ Exception -> 0x00fc }
            java.util.Date r1 = new java.util.Date     // Catch:{ Exception -> 0x00fc }
            r1.<init>()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r1 = r3.format(r1)     // Catch:{ Exception -> 0x00fc }
            r0.append(r1)     // Catch:{ Exception -> 0x00fc }
            r0.append(r4)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00fc }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fc }
            r1.<init>()     // Catch:{ Exception -> 0x00fc }
            java.lang.Object r4 = r2.l()     // Catch:{ Exception -> 0x00fc }
            com.leedarson.ui.SDPlayFragment r4 = (com.leedarson.ui.SDPlayFragment) r4     // Catch:{ Exception -> 0x00fc }
            android.content.Context r4 = r4.getContext()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r4 = com.leedarson.smartcamera.utils.f.b(r4)     // Catch:{ Exception -> 0x00fc }
            r1.append(r4)     // Catch:{ Exception -> 0x00fc }
            r1.append(r0)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00fc }
            r2.i = r1     // Catch:{ Exception -> 0x00fc }
            goto L_0x008a
        L_0x0084:
            if (r12 == r9) goto L_0x008b
            r1 = 3
            if (r12 != r1) goto L_0x008a
            goto L_0x008b
        L_0x008a:
            goto L_0x00d3
        L_0x008b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fc }
            r1.<init>()     // Catch:{ Exception -> 0x00fc }
            java.lang.Object r5 = r2.l()     // Catch:{ Exception -> 0x00fc }
            com.leedarson.ui.SDPlayFragment r5 = (com.leedarson.ui.SDPlayFragment) r5     // Catch:{ Exception -> 0x00fc }
            android.content.Context r5 = r5.getContext()     // Catch:{ Exception -> 0x00fc }
            java.io.File r5 = r5.getFilesDir()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r5 = r5.getPath()     // Catch:{ Exception -> 0x00fc }
            r1.append(r5)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r5 = "/web/"
            r1.append(r5)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r5 = "build"
            java.lang.String r5 = r11.replace(r5, r0)     // Catch:{ Exception -> 0x00fc }
            r1.append(r5)     // Catch:{ Exception -> 0x00fc }
            r1.append(r4)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00fc }
            r2.i = r1     // Catch:{ Exception -> 0x00fc }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fc }
            r1.<init>()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r4 = "path:"
            r1.append(r4)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r4 = r2.i     // Catch:{ Exception -> 0x00fc }
            r1.append(r4)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00fc }
            com.leedarson.smartcamera.utils.e.e(r0, r1)     // Catch:{ Exception -> 0x00fc }
        L_0x00d3:
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x00fc }
            java.lang.String r1 = r2.i     // Catch:{ Exception -> 0x00fc }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00fc }
            java.io.File r1 = r0.getParentFile()     // Catch:{ Exception -> 0x00fc }
            boolean r4 = r1.exists()     // Catch:{ Exception -> 0x00fc }
            if (r4 != 0) goto L_0x00e7
            r1.mkdirs()     // Catch:{ Exception -> 0x00fc }
        L_0x00e7:
            java.lang.Object r4 = r2.m()     // Catch:{ Exception -> 0x00fc }
            com.leedarson.ui.l r4 = (com.leedarson.ui.l) r4     // Catch:{ Exception -> 0x00fc }
            com.leedarson.smartcamera.codec.c r4 = r4.x()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r5 = r2.i     // Catch:{ Exception -> 0x00fc }
            com.leedarson.ui.k$b r6 = new com.leedarson.ui.k$b     // Catch:{ Exception -> 0x00fc }
            r6.<init>(r12)     // Catch:{ Exception -> 0x00fc }
            r4.M(r5, r6)     // Catch:{ Exception -> 0x00fc }
        L_0x00fb:
            goto L_0x0100
        L_0x00fc:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0100:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.k.E(java.lang.String, int):void");
    }

    /* compiled from: SDPlayPresenter */
    public class b implements com.leedarson.smartcamera.codec.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        b(int i) {
            this.a = i;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11427, new Class[0], Void.TYPE).isSupported) {
                if (this.a == 2) {
                    ((l) k.this.m()).f(k.this.i);
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) ((SDPlayFragment) k.this.l()).c3());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", 10004);
                    if (this.a == 3) {
                        String base64 = j.e(k.this.i);
                        JSONObject dataObj = new JSONObject();
                        dataObj.put("imageData", (Object) base64);
                        jsonObject.put("data", (Object) dataObj);
                    }
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11428, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (this.a == 2) {
                    ((l) k.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00fb A[Catch:{ Exception -> 0x0109 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void w(com.leedarson.smartcamera.kvswebrtc.f0 r14, org.webrtc.SurfaceViewRenderer r15, java.lang.String r16, int r17) {
        /*
            r13 = this;
            java.lang.String r0 = ""
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            r2[r3] = r14
            r9 = 1
            r2[r9] = r15
            r10 = 2
            r2[r10] = r16
            java.lang.Integer r4 = new java.lang.Integer
            r11 = r17
            r4.<init>(r11)
            r12 = 3
            r2[r12] = r4
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<com.leedarson.smartcamera.kvswebrtc.f0> r1 = com.leedarson.smartcamera.kvswebrtc.f0.class
            r7[r3] = r1
            java.lang.Class<org.webrtc.SurfaceViewRenderer> r1 = org.webrtc.SurfaceViewRenderer.class
            r7[r9] = r1
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            r7[r10] = r1
            java.lang.Class r1 = java.lang.Integer.TYPE
            r7[r12] = r1
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 11411(0x2c93, float:1.599E-41)
            r3 = r13
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x003b
            return
        L_0x003b:
            r1 = r13
            r2 = r15
            r3 = r17
            r4 = r14
            r5 = r16
            if (r4 == 0) goto L_0x010d
            java.lang.Object r6 = r1.m()     // Catch:{ Exception -> 0x0109 }
            com.leedarson.ui.l r6 = (com.leedarson.ui.l) r6     // Catch:{ Exception -> 0x0109 }
            com.leedarson.smartcamera.codec.c r6 = r6.x()     // Catch:{ Exception -> 0x0109 }
            if (r6 == 0) goto L_0x0108
            java.text.SimpleDateFormat r6 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0109 }
            java.lang.String r7 = "yyyyMMddHHmmss"
            r6.<init>(r7)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r7 = ".jpg"
            if (r3 != r10) goto L_0x009c
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0109 }
            r0.<init>()     // Catch:{ Exception -> 0x0109 }
            r0.append(r5)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r8 = "_"
            r0.append(r8)     // Catch:{ Exception -> 0x0109 }
            java.util.Date r8 = new java.util.Date     // Catch:{ Exception -> 0x0109 }
            r8.<init>()     // Catch:{ Exception -> 0x0109 }
            java.lang.String r8 = r6.format(r8)     // Catch:{ Exception -> 0x0109 }
            r0.append(r8)     // Catch:{ Exception -> 0x0109 }
            r0.append(r7)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0109 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0109 }
            r7.<init>()     // Catch:{ Exception -> 0x0109 }
            java.lang.Object r8 = r1.l()     // Catch:{ Exception -> 0x0109 }
            com.leedarson.ui.SDPlayFragment r8 = (com.leedarson.ui.SDPlayFragment) r8     // Catch:{ Exception -> 0x0109 }
            android.content.Context r8 = r8.getContext()     // Catch:{ Exception -> 0x0109 }
            java.lang.String r8 = com.leedarson.smartcamera.utils.f.b(r8)     // Catch:{ Exception -> 0x0109 }
            r7.append(r8)     // Catch:{ Exception -> 0x0109 }
            r7.append(r0)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0109 }
            r1.i = r7     // Catch:{ Exception -> 0x0109 }
            goto L_0x00a1
        L_0x009c:
            if (r3 == r9) goto L_0x00a2
            if (r3 != r12) goto L_0x00a1
            goto L_0x00a2
        L_0x00a1:
            goto L_0x00ea
        L_0x00a2:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0109 }
            r8.<init>()     // Catch:{ Exception -> 0x0109 }
            java.lang.Object r9 = r1.l()     // Catch:{ Exception -> 0x0109 }
            com.leedarson.ui.SDPlayFragment r9 = (com.leedarson.ui.SDPlayFragment) r9     // Catch:{ Exception -> 0x0109 }
            android.content.Context r9 = r9.getContext()     // Catch:{ Exception -> 0x0109 }
            java.io.File r9 = r9.getFilesDir()     // Catch:{ Exception -> 0x0109 }
            java.lang.String r9 = r9.getPath()     // Catch:{ Exception -> 0x0109 }
            r8.append(r9)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r9 = "/web/"
            r8.append(r9)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r9 = "build"
            java.lang.String r9 = r5.replace(r9, r0)     // Catch:{ Exception -> 0x0109 }
            r8.append(r9)     // Catch:{ Exception -> 0x0109 }
            r8.append(r7)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r7 = r8.toString()     // Catch:{ Exception -> 0x0109 }
            r1.i = r7     // Catch:{ Exception -> 0x0109 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0109 }
            r7.<init>()     // Catch:{ Exception -> 0x0109 }
            java.lang.String r8 = "path:"
            r7.append(r8)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r8 = r1.i     // Catch:{ Exception -> 0x0109 }
            r7.append(r8)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0109 }
            com.leedarson.smartcamera.utils.e.e(r0, r7)     // Catch:{ Exception -> 0x0109 }
        L_0x00ea:
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0109 }
            java.lang.String r7 = r1.i     // Catch:{ Exception -> 0x0109 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0109 }
            java.io.File r7 = r0.getParentFile()     // Catch:{ Exception -> 0x0109 }
            boolean r8 = r7.exists()     // Catch:{ Exception -> 0x0109 }
            if (r8 != 0) goto L_0x00fe
            r7.mkdirs()     // Catch:{ Exception -> 0x0109 }
        L_0x00fe:
            java.lang.String r8 = r1.i     // Catch:{ Exception -> 0x0109 }
            com.leedarson.ui.k$c r9 = new com.leedarson.ui.k$c     // Catch:{ Exception -> 0x0109 }
            r9.<init>(r3)     // Catch:{ Exception -> 0x0109 }
            r4.B0(r8, r9)     // Catch:{ Exception -> 0x0109 }
        L_0x0108:
            goto L_0x010d
        L_0x0109:
            r0 = move-exception
            r0.printStackTrace()
        L_0x010d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.k.w(com.leedarson.smartcamera.kvswebrtc.f0, org.webrtc.SurfaceViewRenderer, java.lang.String, int):void");
    }

    /* compiled from: SDPlayPresenter */
    public class c implements h0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        c(int i) {
            this.a = i;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11429, new Class[0], Void.TYPE).isSupported) {
                k.t(k.this, this.a);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11430, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (this.a == 2) {
                    ((l) k.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    private void u(int saveStatus) {
        Object[] objArr = {new Integer(saveStatus)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11412, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (saveStatus == 2) {
                ((l) m()).f(this.i);
            }
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10004);
                if (saveStatus == 3) {
                    String base64 = j.e(this.i);
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("imageData", (Object) base64);
                    jsonObject.put("data", (Object) dataObj);
                }
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11413, new Class[0], Void.TYPE).isSupported) {
            try {
                if (l() != null && ((SDPlayFragment) l()).getChannel() != null) {
                    if (((SDPlayFragment) l()).x() != null) {
                        ((SDPlayFragment) l()).x().J();
                        ((SDPlayFragment) l()).x().A();
                    }
                    ((l) m()).getChannel().p1(v(((SDPlayFragment) l()).d3()), ((SDPlayFragment) l()).f3());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public long v(String dataStr) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dataStr}, this, changeQuickRedirect, false, 11414, new Class[]{String.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        try {
            if (this.j == null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.j = simpleDateFormat;
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
            }
            SimpleDateFormat simpleDateFormat2 = this.j;
            if (simpleDateFormat2 != null) {
                return simpleDateFormat2.parse(dataStr).getTime();
            }
            return 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11415, new Class[0], Void.TYPE).isSupported) {
            if (l() != null && ((SDPlayFragment) l()).getChannel() != null) {
                if (((SDPlayFragment) l()).x() != null) {
                    ((SDPlayFragment) l()).x().G();
                }
                ((SDPlayFragment) l()).getChannel().o1(v(((SDPlayFragment) l()).d3()), ((SDPlayFragment) l()).f3());
            }
        }
    }

    public void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11416, new Class[0], Void.TYPE).isSupported) {
            if (l() != null && ((SDPlayFragment) l()).getChannel() != null) {
                if (((SDPlayFragment) l()).x() != null) {
                    ((SDPlayFragment) l()).x().J();
                }
                ((SDPlayFragment) l()).getChannel().s1(v(((SDPlayFragment) l()).d3()), ((SDPlayFragment) l()).f3());
            }
        }
    }

    public void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11417, new Class[0], Void.TYPE).isSupported) {
            if (l() != null && ((SDPlayFragment) l()).getChannel() != null) {
                if (((SDPlayFragment) l()).x() != null) {
                    ((SDPlayFragment) l()).x().G();
                }
                ((SDPlayFragment) l()).getChannel().u1(v(((SDPlayFragment) l()).d3()), ((SDPlayFragment) l()).f3());
            }
        }
    }

    public void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11418, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10002);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11419, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10001);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11420, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10009);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11421, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10008);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void B(long l, int sdType, int seekFlag) {
        Object[] objArr = {new Long(l), new Integer(sdType), new Integer(seekFlag)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Long.TYPE, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11422, clsArr, Void.TYPE).isSupported) {
            if (l() != null && ((SDPlayFragment) l()).getChannel() != null) {
                if (((SDPlayFragment) l()).x() != null) {
                    ((SDPlayFragment) l()).x().J();
                }
                ((SDPlayFragment) l()).getChannel().t1(l, sdType, seekFlag);
            }
        }
    }

    public void F(f0 f0Var, String str) {
        if (!PatchProxy.proxy(new Object[]{f0Var, str}, this, changeQuickRedirect, false, 11423, new Class[]{f0.class, String.class}, Void.TYPE).isSupported) {
            String prePath = str;
            f0 webRTCChannel = f0Var;
            if (webRTCChannel != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String str2 = com.leedarson.smartcamera.utils.f.b(((SDPlayFragment) l()).getContext()) + (prePath + "_" + formatter.format(new Date()) + ".mp4");
                this.g = str2;
                boolean isSuc = webRTCChannel.p3(str2);
                e.c("", "startKVSRecord: " + isSuc);
                if (isSuc) {
                    L();
                    Timer timer = new Timer();
                    this.f = timer;
                    this.h = 0;
                    timer.schedule(new d(), 100, 1000);
                    ((l) m()).d();
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                        jsonObject.put("desc", (Object) "");
                        jsonObject.put("messageCode", 10005);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                        jsonObject2.put("desc", (Object) "");
                        jsonObject2.put("messageCode", 10006);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    /* compiled from: SDPlayPresenter */
    public class d extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11431, new Class[0], Void.TYPE).isSupported) {
                if (k.this.m() != null) {
                    k.s(k.this);
                    ((l) k.this.m()).e(k.this.h);
                    e.e("", "startRecord:" + k.this.h);
                }
            }
        }
    }

    public void I(f0 f0Var) {
        if (!PatchProxy.proxy(new Object[]{f0Var}, this, changeQuickRedirect, false, 11424, new Class[]{f0.class}, Void.TYPE).isSupported) {
            f0 webRTCChannel = f0Var;
            if (webRTCChannel != null) {
                webRTCChannel.w3();
                L();
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", 10006);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (this.h < 3) {
                    FileUtils.deleteFile(this.g);
                    ((l) m()).showToast(R$string.player_videotape_fail);
                    this.h = 0;
                    ((l) m()).c();
                    return;
                }
                this.h = 0;
                ((l) m()).c();
                try {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("deviceId", (Object) ((SDPlayFragment) l()).c3());
                    jsonObject2.put("desc", (Object) "");
                    jsonObject2.put("messageCode", 10007);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                    ((SDPlayFragment) l()).getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.g))));
                    ((l) m()).showToast(R$string.player_videotape_sucess);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
