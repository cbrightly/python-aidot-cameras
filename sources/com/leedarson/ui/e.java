package com.leedarson.ui;

import android.content.Intent;
import android.net.Uri;
import com.leedarson.R$string;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.utils.FileUtils;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.h0;
import com.leedarson.smartcamera.listener.n;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

/* compiled from: LivePresenter */
public class e extends com.leedarson.base.presenters.a<f, LiveFragment> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Timer f;
    private String g = "";
    /* access modifiers changed from: private */
    public int h;
    /* access modifiers changed from: private */
    public int i = -1;
    /* access modifiers changed from: private */
    public boolean j = false;
    private Timer k;
    private ExecutorService l = Executors.newSingleThreadExecutor();
    private Timer m;
    /* access modifiers changed from: private */
    public int n = 0;
    String o;

    static /* synthetic */ int v(e x0) {
        int i2 = x0.h;
        x0.h = i2 + 1;
        return i2;
    }

    static /* synthetic */ void w(e x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 11140, new Class[]{e.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.A(x1);
        }
    }

    static /* synthetic */ int y(e x0) {
        int i2 = x0.n;
        x0.n = i2 + 1;
        return i2;
    }

    public e(f view, LiveFragment activity) {
        super(view, activity);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void U() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 11116(0x2b6c, float:1.5577E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.k
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.k = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.e.U():void");
    }

    public void O() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11117, new Class[0], Void.TYPE).isSupported) {
            U();
            this.i = -1;
            Timer timer = new Timer();
            this.k = timer;
            timer.schedule(new d(), 150, 200);
        }
    }

    /* compiled from: LivePresenter */
    public class d extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11141, new Class[0], Void.TYPE).isSupported) {
                if (e.this.l() != null && ((LiveFragment) e.this.l()).getChannel() != null) {
                    if (!e.this.j) {
                        boolean unused = e.this.j = true;
                        ((LiveFragment) e.this.l()).getChannel().h1(1);
                    }
                    switch (e.this.i) {
                        case 1:
                            ((LiveFragment) e.this.l()).getChannel().j1(4);
                            return;
                        case 2:
                            ((LiveFragment) e.this.l()).getChannel().e1(4);
                            return;
                        case 3:
                            ((LiveFragment) e.this.l()).getChannel().f1(4);
                            return;
                        case 6:
                            ((LiveFragment) e.this.l()).getChannel().g1(4);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    public void K(int control) {
        this.i = control;
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11118, new Class[0], Void.TYPE).isSupported) {
            U();
            if (this.j) {
                if (!(l() == null || ((LiveFragment) l()).getChannel() == null)) {
                    ((LiveFragment) l()).getChannel().i1(1);
                }
                this.j = false;
            }
        }
    }

    public void H(int fx) {
        Object[] objArr = {new Integer(fx)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11119, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (l() != null && ((LiveFragment) l()).getChannel() != null) {
                switch (fx) {
                    case 1:
                        ((LiveFragment) l()).getChannel().j1(1);
                        return;
                    case 2:
                        ((LiveFragment) l()).getChannel().e1(1);
                        return;
                    case 3:
                        ((LiveFragment) l()).getChannel().f1(1);
                        return;
                    case 6:
                        ((LiveFragment) l()).getChannel().g1(1);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* renamed from: com.leedarson.ui.e$e  reason: collision with other inner class name */
    /* compiled from: LivePresenter */
    public class C0194e implements com.leedarson.smartcamera.listener.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0194e() {
        }

        public void onSuccess(int resolution) {
            Object[] objArr = {new Integer(resolution)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11145, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                ((f) e.this.m()).E(resolution);
            }
        }
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11120, new Class[0], Void.TYPE).isSupported) {
            if (((f) m()).getChannel() != null) {
                ((f) m()).getChannel().M0(new C0194e());
            }
        }
    }

    public void P(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 11121, new Class[]{String.class}, Void.TYPE).isSupported) {
            String prePath = str;
            if (((f) m()).x() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                this.g = com.leedarson.smartcamera.utils.f.b(((LiveFragment) l()).getContext()) + (prePath + "_" + formatter.format(new Date()) + ".mp4");
                if (((f) m()).x().R(this.g) == 0) {
                    X();
                    Timer timer = new Timer();
                    this.f = timer;
                    this.h = 0;
                    timer.schedule(new f(), 100, 1000);
                    ((f) m()).d();
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("deviceId", (Object) ((LiveFragment) l()).j4());
                        jsonObject.put("desc", (Object) "");
                        jsonObject.put("messageCode", 10005);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("deviceId", (Object) ((LiveFragment) l()).j4());
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

    /* compiled from: LivePresenter */
    public class f extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11147, new Class[0], Void.TYPE).isSupported) {
                if (e.this.m() != null) {
                    e.v(e.this);
                    ((f) e.this.m()).e(e.this.h);
                    com.leedarson.smartcamera.utils.e.e("", "startRecord:" + e.this.h);
                }
            }
        }
    }

    public void M(f0 f0Var, String str) {
        if (!PatchProxy.proxy(new Object[]{f0Var, str}, this, changeQuickRedirect, false, 11122, new Class[]{f0.class, String.class}, Void.TYPE).isSupported) {
            String prePath = str;
            f0 webRTCChannel = f0Var;
            if (webRTCChannel != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String str2 = com.leedarson.smartcamera.utils.f.b(((LiveFragment) l()).getContext()) + (prePath + "_" + formatter.format(new Date()) + ".mp4");
                this.g = str2;
                boolean isSuc = webRTCChannel.p3(str2);
                com.leedarson.smartcamera.utils.e.c("", "startKVSRecord: " + isSuc);
                if (isSuc) {
                    X();
                    Timer timer = new Timer();
                    this.f = timer;
                    this.h = 0;
                    timer.schedule(new g(), 100, 1000);
                    ((f) m()).d();
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("deviceId", (Object) ((LiveFragment) l()).j4());
                        jsonObject.put("desc", (Object) "");
                        jsonObject.put("messageCode", 10005);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("deviceId", (Object) ((LiveFragment) l()).j4());
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

    /* compiled from: LivePresenter */
    public class g extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11148, new Class[0], Void.TYPE).isSupported) {
                if (e.this.m() != null) {
                    e.v(e.this);
                    ((f) e.this.m()).e(e.this.h);
                    com.leedarson.smartcamera.utils.e.e("", "startRecord:" + e.this.h);
                }
            }
        }
    }

    public void R(f0 f0Var) {
        if (!PatchProxy.proxy(new Object[]{f0Var}, this, changeQuickRedirect, false, 11123, new Class[]{f0.class}, Void.TYPE).isSupported) {
            f0 webRTCChannel = f0Var;
            if (webRTCChannel != null) {
                webRTCChannel.w3();
                X();
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) ((LiveFragment) l()).j4());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", 10006);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (this.h < 3) {
                    FileUtils.deleteFile(this.g);
                    ((f) m()).showToast(R$string.player_videotape_fail);
                    this.h = 0;
                    ((f) m()).c();
                    return;
                }
                this.h = 0;
                ((f) m()).c();
                try {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("deviceId", (Object) ((LiveFragment) l()).j4());
                    jsonObject2.put("desc", (Object) "");
                    jsonObject2.put("messageCode", 10007);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                    ((LiveFragment) l()).getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.g))));
                    ((f) m()).showToast(R$string.player_videotape_sucess);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void V() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11124, new Class[0], Void.TYPE).isSupported) {
            if (((f) m()).x() != null) {
                int result = ((f) m()).x().W();
                X();
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) ((LiveFragment) l()).j4());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", 10006);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (this.h < 3) {
                    FileUtils.deleteFile(this.g);
                    ((f) m()).showToast(R$string.player_videotape_fail);
                    this.h = 0;
                    ((f) m()).c();
                    return;
                }
                this.h = 0;
                if (result == 0) {
                    ((f) m()).c();
                    try {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("deviceId", (Object) ((LiveFragment) l()).j4());
                        jsonObject2.put("desc", (Object) "");
                        jsonObject2.put("messageCode", 10007);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                        ((LiveFragment) l()).getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.g))));
                        ((f) m()).showToast(R$string.player_videotape_sucess);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    ((f) m()).showToast(R$string.player_videotape_error);
                }
            }
        }
    }

    public void I() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11125, new Class[0], Void.TYPE).isSupported) {
            X();
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) ((LiveFragment) l()).j4());
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10006);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.h < 3) {
                FileUtils.deleteFile(this.g);
            }
            this.h = 0;
            ((LiveFragment) l()).getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.g))));
            ((f) m()).showToast(R$string.player_record_error_stop);
            ((f) m()).c();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void X() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 11126(0x2b76, float:1.5591E-41)
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
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.e.X():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00e4 A[Catch:{ Exception -> 0x00fc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void L(java.lang.String r11, int r12) {
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
            r6 = 11127(0x2b77, float:1.5592E-41)
            r3 = r10
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x002b
            return
        L_0x002b:
            r2 = r10
            java.lang.Object r3 = r2.m()     // Catch:{ Exception -> 0x00fc }
            com.leedarson.ui.f r3 = (com.leedarson.ui.f) r3     // Catch:{ Exception -> 0x00fc }
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
            com.leedarson.ui.LiveFragment r4 = (com.leedarson.ui.LiveFragment) r4     // Catch:{ Exception -> 0x00fc }
            android.content.Context r4 = r4.getContext()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r4 = com.leedarson.smartcamera.utils.f.b(r4)     // Catch:{ Exception -> 0x00fc }
            r1.append(r4)     // Catch:{ Exception -> 0x00fc }
            r1.append(r0)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00fc }
            r2.o = r1     // Catch:{ Exception -> 0x00fc }
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
            com.leedarson.ui.LiveFragment r5 = (com.leedarson.ui.LiveFragment) r5     // Catch:{ Exception -> 0x00fc }
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
            r2.o = r1     // Catch:{ Exception -> 0x00fc }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fc }
            r1.<init>()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r4 = "path:"
            r1.append(r4)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r4 = r2.o     // Catch:{ Exception -> 0x00fc }
            r1.append(r4)     // Catch:{ Exception -> 0x00fc }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00fc }
            com.leedarson.smartcamera.utils.e.e(r0, r1)     // Catch:{ Exception -> 0x00fc }
        L_0x00d3:
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x00fc }
            java.lang.String r1 = r2.o     // Catch:{ Exception -> 0x00fc }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00fc }
            java.io.File r1 = r0.getParentFile()     // Catch:{ Exception -> 0x00fc }
            boolean r4 = r1.exists()     // Catch:{ Exception -> 0x00fc }
            if (r4 != 0) goto L_0x00e7
            r1.mkdirs()     // Catch:{ Exception -> 0x00fc }
        L_0x00e7:
            java.lang.Object r4 = r2.m()     // Catch:{ Exception -> 0x00fc }
            com.leedarson.ui.f r4 = (com.leedarson.ui.f) r4     // Catch:{ Exception -> 0x00fc }
            com.leedarson.smartcamera.codec.c r4 = r4.x()     // Catch:{ Exception -> 0x00fc }
            java.lang.String r5 = r2.o     // Catch:{ Exception -> 0x00fc }
            com.leedarson.ui.e$h r6 = new com.leedarson.ui.e$h     // Catch:{ Exception -> 0x00fc }
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
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.e.L(java.lang.String, int):void");
    }

    /* compiled from: LivePresenter */
    public class h implements com.leedarson.smartcamera.codec.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        h(int i) {
            this.a = i;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11149, new Class[0], Void.TYPE).isSupported) {
                e.w(e.this, this.a);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11150, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (this.a == 2) {
                    ((f) e.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00fb A[Catch:{ Exception -> 0x0109 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void E(com.leedarson.smartcamera.kvswebrtc.f0 r14, org.webrtc.SurfaceViewRenderer r15, java.lang.String r16, int r17) {
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
            r6 = 11128(0x2b78, float:1.5594E-41)
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
            com.leedarson.ui.f r6 = (com.leedarson.ui.f) r6     // Catch:{ Exception -> 0x0109 }
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
            com.leedarson.ui.LiveFragment r8 = (com.leedarson.ui.LiveFragment) r8     // Catch:{ Exception -> 0x0109 }
            android.content.Context r8 = r8.getContext()     // Catch:{ Exception -> 0x0109 }
            java.lang.String r8 = com.leedarson.smartcamera.utils.f.b(r8)     // Catch:{ Exception -> 0x0109 }
            r7.append(r8)     // Catch:{ Exception -> 0x0109 }
            r7.append(r0)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0109 }
            r1.o = r7     // Catch:{ Exception -> 0x0109 }
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
            com.leedarson.ui.LiveFragment r9 = (com.leedarson.ui.LiveFragment) r9     // Catch:{ Exception -> 0x0109 }
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
            r1.o = r7     // Catch:{ Exception -> 0x0109 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0109 }
            r7.<init>()     // Catch:{ Exception -> 0x0109 }
            java.lang.String r8 = "path:"
            r7.append(r8)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r8 = r1.o     // Catch:{ Exception -> 0x0109 }
            r7.append(r8)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0109 }
            com.leedarson.smartcamera.utils.e.e(r0, r7)     // Catch:{ Exception -> 0x0109 }
        L_0x00ea:
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0109 }
            java.lang.String r7 = r1.o     // Catch:{ Exception -> 0x0109 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0109 }
            java.io.File r7 = r0.getParentFile()     // Catch:{ Exception -> 0x0109 }
            boolean r8 = r7.exists()     // Catch:{ Exception -> 0x0109 }
            if (r8 != 0) goto L_0x00fe
            r7.mkdirs()     // Catch:{ Exception -> 0x0109 }
        L_0x00fe:
            java.lang.String r8 = r1.o     // Catch:{ Exception -> 0x0109 }
            com.leedarson.ui.e$i r9 = new com.leedarson.ui.e$i     // Catch:{ Exception -> 0x0109 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.e.E(com.leedarson.smartcamera.kvswebrtc.f0, org.webrtc.SurfaceViewRenderer, java.lang.String, int):void");
    }

    /* compiled from: LivePresenter */
    public class i implements h0 {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        i(int i) {
            this.a = i;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11151, new Class[0], Void.TYPE).isSupported) {
                e.w(e.this, this.a);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11152, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (this.a == 2) {
                    ((f) e.this.m()).showToast(R$string.player_screenshot_fail);
                }
            }
        }
    }

    private void A(int saveStatus) {
        Object[] objArr = {new Integer(saveStatus)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11129, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (saveStatus == 2) {
                ((f) m()).f(this.o);
            }
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) ((LiveFragment) l()).j4());
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10004);
                if (saveStatus == 3) {
                    String base64 = com.leedarson.utils.j.e(this.o);
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

    public void Q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11130, new Class[0], Void.TYPE).isSupported) {
            if (((f) m()).getChannel() != null) {
                if (((f) m()).getChannel().S0() == 2) {
                    ((f) m()).b();
                }
                ((f) m()).getChannel().O1(new j());
            }
        }
    }

    /* compiled from: LivePresenter */
    public class j implements n {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11153, new Class[0], Void.TYPE).isSupported) {
                ((f) e.this.m()).H();
                ((f) e.this.m()).a();
                ((f) e.this.m()).x().N(((f) e.this.m()).getChannel().K0(), 1, 16);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) ((LiveFragment) e.this.l()).j4());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", (int) H5ActionName.PLAYER_TALKTODEVICE_START_STATUS);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11154, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (code == 100) {
                    ((f) e.this.m()).showToast(R$string.speak_error);
                } else {
                    ((f) e.this.m()).showToast(R$string.player_error_1);
                }
                ((f) e.this.m()).a();
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) ((LiveFragment) e.this.l()).j4());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", (int) H5ActionName.PLAYER_TALKTODEVICE_END_STATUS);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void b(short[] data, int length, int i) {
            Object[] objArr = {data, new Integer(length), new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11155, new Class[]{short[].class, cls, cls}, Void.TYPE).isSupported) {
                for (int i2 = 0; i2 < length; i2 += 60) {
                    ((f) e.this.m()).v(data[i2]);
                }
            }
        }
    }

    public void W() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11131, new Class[0], Void.TYPE).isSupported) {
            if (((f) m()).getChannel() != null) {
                ((f) m()).b();
                ((f) m()).getChannel().T1(new k());
            }
        }
    }

    /* compiled from: LivePresenter */
    public class k implements n {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11156, new Class[0], Void.TYPE).isSupported) {
                if (((f) e.this.m()).x() != null) {
                    ((f) e.this.m()).x().T();
                }
                ((f) e.this.m()).A();
                ((f) e.this.m()).a();
                ((f) e.this.m()).showToast(R$string.stop_talk_tip);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) ((LiveFragment) e.this.l()).j4());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", (int) H5ActionName.PLAYER_TALKTODEVICE_END_STATUS);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11157, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                ((f) e.this.m()).a();
                ((f) e.this.m()).showToast(R$string.stop_talk_error);
            }
        }

        public void b(short[] data, int length, int db) {
        }
    }

    /* compiled from: LivePresenter */
    public class l implements n {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11158, new Class[0], Void.TYPE).isSupported) {
                ((f) e.this.m()).H();
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) ((LiveFragment) e.this.l()).j4());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", (int) H5ActionName.PLAYER_TALKTODEVICE_START_STATUS);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(int code) {
        }

        public void b(short[] data, int length, int db) {
        }
    }

    public void F(f0 webRTCChannel) {
        if (!PatchProxy.proxy(new Object[]{webRTCChannel}, this, changeQuickRedirect, false, 11132, new Class[]{f0.class}, Void.TYPE).isSupported) {
            if (webRTCChannel != null) {
                webRTCChannel.q3(new l());
            }
        }
    }

    /* compiled from: LivePresenter */
    public class a implements n {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11142, new Class[0], Void.TYPE).isSupported) {
                ((f) e.this.m()).A();
                ((f) e.this.m()).showToast(R$string.stop_talk_tip);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) ((LiveFragment) e.this.l()).j4());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", (int) H5ActionName.PLAYER_TALKTODEVICE_END_STATUS);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void a(int code) {
        }

        public void b(short[] data, int length, int db) {
        }
    }

    public void G(f0 webRTCChannel) {
        if (!PatchProxy.proxy(new Object[]{webRTCChannel}, this, changeQuickRedirect, false, 11133, new Class[]{f0.class}, Void.TYPE).isSupported) {
            if (webRTCChannel != null) {
                webRTCChannel.z3(new a());
            }
        }
    }

    /* compiled from: LivePresenter */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11143, new Class[0], Void.TYPE).isSupported) {
                try {
                    if (e.this.l() != null) {
                        com.leedarson.smartcamera.utils.d.a(((LiveFragment) e.this.l()).getContext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11134, new Class[0], Void.TYPE).isSupported) {
            this.l.execute(new b());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void S() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 11135(0x2b7f, float:1.5603E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.m
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.m = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.e.S():void");
    }

    public void N() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11136, new Class[0], Void.TYPE).isSupported) {
            this.n = 0;
            S();
            Timer timer = new Timer();
            this.m = timer;
            timer.schedule(new c(), 1000, 1000);
        }
    }

    /* compiled from: LivePresenter */
    public class c extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void run() {
            int count;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11144, new Class[0], Void.TYPE).isSupported) {
                e.y(e.this);
                if (e.this.n >= 3600 && (count = 3610 - e.this.n) >= 0) {
                    ((f) e.this.m()).I(count);
                }
            }
        }
    }

    public void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11137, new Class[0], Void.TYPE).isSupported) {
            N();
        }
    }

    public void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11138, new Class[0], Void.TYPE).isSupported) {
            S();
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) ((LiveFragment) l()).j4());
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", (int) H5ActionName.PLAYER_LIVE_GOBACK);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void J(byte[] data, int length) {
        if (!PatchProxy.proxy(new Object[]{data, new Integer(length)}, this, changeQuickRedirect, false, 11139, new Class[]{byte[].class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                ((f) m()).getChannel().v1(data, length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
