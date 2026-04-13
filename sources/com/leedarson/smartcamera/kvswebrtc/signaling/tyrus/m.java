package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus;

import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.utils.w;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import io.reactivex.f;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Locale;
import org.json.JSONObject;

/* compiled from: IceConfigUpdateJobManager */
public class m {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a = "WebRtcIceConfig";
    String b;
    String c;
    private String d;

    /* compiled from: IceConfigUpdateJobManager */
    public enum b {
        EMPTY,
        VALID,
        INVALID;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public m() {
        Locale locale = Locale.US;
        this.b = String.format(locale, "%s/web/iceConfig", new Object[]{BaseApplication.b().getFilesDir().getPath()});
        this.c = String.format(locale, "%s/web/iceConfig/config.txt", new Object[]{BaseApplication.b().getFilesDir().getPath()});
        this.d = "";
    }

    static /* synthetic */ void a(m x0, String x1) {
        Class[] clsArr = {m.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, H5ActionName.PLAYER_LIVE_CLICK_OFFLINE, clsArr, Void.TYPE).isSupported) {
            x0.n(x1);
        }
    }

    /* access modifiers changed from: package-private */
    public e<String> d(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 10003, new Class[]{String.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        this.d = deviceId;
        return c().o(new d(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public /* synthetic */ e k(b ice_config_of_local_statue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ice_config_of_local_statue}, this, changeQuickRedirect, false, H5ActionName.PLAYER_TALKTODEVICE_END_STATUS, new Class[]{b.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        if (ice_config_of_local_statue == b.VALID) {
            return o();
        }
        return b();
    }

    private e<b> c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10004, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new b(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b4 A[Catch:{ Exception -> 0x010c }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f5  */
    /* renamed from: h */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void i(io.reactivex.f r19) {
        /*
            r18 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r19
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<io.reactivex.f> r0 = io.reactivex.f.class
            r6[r8] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10013(0x271d, float:1.4031E-41)
            r2 = r18
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001e
            return
        L_0x001e:
            r1 = r18
            r2 = r19
            java.io.File r0 = new java.io.File
            java.lang.String r3 = r1.b
            r0.<init>(r3)
            r3 = r0
            boolean r0 = r3.exists()
            if (r0 != 0) goto L_0x0033
            r3.mkdir()
        L_0x0033:
            java.io.File r0 = new java.io.File
            java.lang.String r4 = r1.c
            r0.<init>(r4)
            r4 = r0
            boolean r0 = r4.exists()
            if (r0 != 0) goto L_0x004d
            r4.createNewFile()
            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m$b r0 = com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m.b.EMPTY
            r2.onNext(r0)
            r2.onComplete()
            return
        L_0x004d:
            java.lang.String r0 = r4.getAbsolutePath()
            java.lang.String r5 = r1.p(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 == 0) goto L_0x0068
            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m$b r0 = com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m.b.EMPTY
            r2.onNext(r0)
            r2.onComplete()
            r19 = r3
            r15 = r4
            goto L_0x013f
        L_0x0068:
            java.lang.Class<com.leedarson.smartcamera.bean.IceConfigBean> r0 = com.leedarson.smartcamera.bean.IceConfigBean.class
            java.lang.Object r0 = com.leedarson.base.utils.m.a(r5, r0)     // Catch:{ Exception -> 0x0110 }
            com.leedarson.smartcamera.bean.IceConfigBean r0 = (com.leedarson.smartcamera.bean.IceConfigBean) r0     // Catch:{ Exception -> 0x0110 }
            r6 = 1
            r7 = 1
            r9 = 300(0x12c, float:4.2E-43)
            java.util.List<com.leedarson.smartcamera.bean.IceConfigBean$AppBean> r10 = r0.app     // Catch:{ Exception -> 0x0110 }
            r11 = 1000(0x3e8, double:4.94E-321)
            if (r10 == 0) goto L_0x00a7
            int r10 = r10.size()     // Catch:{ Exception -> 0x009f }
            if (r10 <= 0) goto L_0x00a7
            long r13 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x009f }
            long r13 = r13 / r11
            java.util.List<com.leedarson.smartcamera.bean.IceConfigBean$AppBean> r10 = r0.app     // Catch:{ Exception -> 0x009f }
            java.lang.Object r10 = r10.get(r8)     // Catch:{ Exception -> 0x009f }
            com.leedarson.smartcamera.bean.IceConfigBean$AppBean r10 = (com.leedarson.smartcamera.bean.IceConfigBean.AppBean) r10     // Catch:{ Exception -> 0x009f }
            java.lang.Integer r10 = r10.ttl     // Catch:{ Exception -> 0x009f }
            int r10 = r10.intValue()     // Catch:{ Exception -> 0x009f }
            long r11 = (long) r10
            long r11 = r11 - r13
            r19 = r3
            r15 = r4
            long r3 = (long) r9
            int r3 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x00aa
            r6 = 0
            goto L_0x00aa
        L_0x009f:
            r0 = move-exception
            r19 = r3
            r15 = r4
            r16 = r5
            goto L_0x0116
        L_0x00a7:
            r19 = r3
            r15 = r4
        L_0x00aa:
            java.util.List<com.leedarson.smartcamera.bean.IceConfigBean$DevBean> r3 = r0.dev     // Catch:{ Exception -> 0x010c }
            if (r3 == 0) goto L_0x00f5
            int r3 = r3.size()     // Catch:{ Exception -> 0x010c }
            if (r3 <= 0) goto L_0x00f5
            r3 = 0
        L_0x00b5:
            java.util.List<com.leedarson.smartcamera.bean.IceConfigBean$DevBean> r4 = r0.dev     // Catch:{ Exception -> 0x010c }
            int r4 = r4.size()     // Catch:{ Exception -> 0x010c }
            if (r3 >= r4) goto L_0x00f2
            java.util.List<com.leedarson.smartcamera.bean.IceConfigBean$DevBean> r4 = r0.dev     // Catch:{ Exception -> 0x010c }
            java.lang.Object r4 = r4.get(r3)     // Catch:{ Exception -> 0x010c }
            com.leedarson.smartcamera.bean.IceConfigBean$DevBean r4 = (com.leedarson.smartcamera.bean.IceConfigBean.DevBean) r4     // Catch:{ Exception -> 0x010c }
            java.lang.String r10 = r1.d     // Catch:{ Exception -> 0x010c }
            java.lang.String r11 = r4.id     // Catch:{ Exception -> 0x010c }
            boolean r10 = r10.equals(r11)     // Catch:{ Exception -> 0x010c }
            if (r10 == 0) goto L_0x00e9
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x010c }
            r12 = 1000(0x3e8, double:4.94E-321)
            long r10 = r10 / r12
            java.lang.Integer r14 = r4.ttl     // Catch:{ Exception -> 0x010c }
            int r14 = r14.intValue()     // Catch:{ Exception -> 0x010c }
            long r12 = (long) r14
            long r12 = r12 - r10
            r17 = r4
            r16 = r5
            long r4 = (long) r9
            int r4 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x00ed
            r7 = 0
            goto L_0x00ed
        L_0x00e9:
            r17 = r4
            r16 = r5
        L_0x00ed:
            int r3 = r3 + 1
            r5 = r16
            goto L_0x00b5
        L_0x00f2:
            r16 = r5
            goto L_0x00f7
        L_0x00f5:
            r16 = r5
        L_0x00f7:
            if (r6 != 0) goto L_0x0101
            if (r7 != 0) goto L_0x0101
            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m$b r3 = com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m.b.VALID     // Catch:{ Exception -> 0x010a }
            r2.onNext(r3)     // Catch:{ Exception -> 0x010a }
            goto L_0x0106
        L_0x0101:
            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m$b r3 = com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m.b.INVALID     // Catch:{ Exception -> 0x010a }
            r2.onNext(r3)     // Catch:{ Exception -> 0x010a }
        L_0x0106:
            r2.onComplete()     // Catch:{ Exception -> 0x010a }
            goto L_0x013f
        L_0x010a:
            r0 = move-exception
            goto L_0x0116
        L_0x010c:
            r0 = move-exception
            r16 = r5
            goto L_0x0116
        L_0x0110:
            r0 = move-exception
            r19 = r3
            r15 = r4
            r16 = r5
        L_0x0116:
            java.lang.String r3 = r1.a
            timber.log.a$b r3 = timber.log.a.g(r3)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "本地iceConfig转化错误  e="
            r4.append(r5)
            java.lang.String r5 = r0.toString()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r8]
            r3.c(r4, r5)
            com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m$b r3 = com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m.b.INVALID
            r2.onNext(r3)
            r2.onComplete()
        L_0x013f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.m.i(io.reactivex.f):void");
    }

    /* access modifiers changed from: package-private */
    public e<String> o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10005, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new a(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: l */
    public /* synthetic */ void m(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 10012, new Class[]{f.class}, Void.TYPE).isSupported) {
            emitter.onNext(p(this.c));
            emitter.onComplete();
        }
    }

    /* access modifiers changed from: package-private */
    public String p(String path) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 10006, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String str = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
            while (true) {
                String readLine = br.readLine();
                String mimeTypeLine = readLine;
                if (readLine == null) {
                    break;
                }
                str = str + mimeTypeLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /* access modifiers changed from: package-private */
    public void q(String strcontent, String filePath) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{strcontent, filePath}, this, changeQuickRedirect, false, 10007, clsArr, Void.TYPE).isSupported) {
            String strContent = strcontent;
            try {
                File file = new File(filePath);
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(file.length());
                raf.write(strContent.getBytes());
                raf.close();
            } catch (Exception e) {
                Log.e("TestFile", "Error on write File:" + e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public e<String> b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10008, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new c(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public /* synthetic */ void g(f fVar) {
        if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 10011, new Class[]{f.class}, Void.TYPE).isSupported) {
            f emitter = fVar;
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) w.E(BaseApplication.b()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, String.format(Locale.US, "%s/v15/api/webrtc/iceConfig?forceRefresh=0", new Object[]{baseUrl}), headerJson.toString(), (String) null, new a(emitter));
        }
    }

    /* compiled from: IceConfigUpdateJobManager */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f c;

        a(f fVar) {
            this.c = fVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, H5ActionName.PLAYER_LIVE_GET_QUALITY, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, H5ActionName.PLAYER_LIVE_CLICK_STANDBY, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                this.c.onError(new Throwable("获取getIceConfig（失败） onError:" + e.getCode() + "=" + e.getMsg()));
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, H5ActionName.PLAYER_LIVE_SET_QUALITY, new Class[]{String.class}, Void.TYPE).isSupported) {
                m mVar = m.this;
                m.a(mVar, "获取 getIceConfig （成功） onSuccess:" + response);
                File cacheDir = new File(m.this.b);
                if (!cacheDir.exists()) {
                    cacheDir.mkdir();
                }
                File cacheFile = new File(m.this.c);
                if (!cacheFile.exists()) {
                    try {
                        cacheFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    cacheFile.delete();
                    try {
                        cacheFile.createNewFile();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                m.this.q(response, cacheFile.getAbsolutePath());
                this.c.onNext(response);
                this.c.onComplete();
            }
        }
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10009, new Class[0], Void.TYPE).isSupported) {
            try {
                File iceCacheFile = new File(this.c);
                if (iceCacheFile.exists()) {
                    iceCacheFile.delete();
                    n("本地iceConfig 本地缓存文件删除成功");
                }
            } catch (Exception e) {
                n("本地iceConfig缓存文件删除失败   e=" + e.toString());
            }
        }
    }

    private void n(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 10010, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g(this.a).m(message, new Object[0]);
        }
    }
}
