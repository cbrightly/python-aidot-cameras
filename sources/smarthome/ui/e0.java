package smarthome.ui;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.v;
import com.leedarson.base.utils.w;
import com.leedarson.base.webservice.event.ServerStatusEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.l;
import io.reactivex.m;
import io.reactivex.n;
import io.reactivex.q;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: LauncherHelper */
public class e0 {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "LauncherHelper";
    private Activity b;
    private boolean c = false;

    static /* synthetic */ void a(e0 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 14170, new Class[]{e0.class}, Void.TYPE).isSupported) {
            x0.d();
        }
    }

    static /* synthetic */ void b(e0 x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 14171, new Class[]{e0.class}, Void.TYPE).isSupported) {
            x0.g();
        }
    }

    static /* synthetic */ Activity c(e0 x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 14172, new Class[]{e0.class}, Activity.class);
        return proxy.isSupported ? (Activity) proxy.result : x0.h();
    }

    public e0(Activity activity) {
        this.b = activity;
    }

    public void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14161, new Class[0], Void.TYPE).isSupported) {
            m();
            e();
        }
    }

    private void m() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14162, new Class[0], Void.TYPE).isSupported) {
            v.d("CoreActivity#launchHttpServer", "HttpServer初始化");
            com.leedarson.base.webservice.utils.b.b().j(this.b, false, "LauncherHelper.startHttpServer");
        }
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14163, new Class[0], Void.TYPE).isSupported) {
            v.d("CoreActivity#h5zipCheck", "检测H5zip包资源");
            v.d("CoreActivity#loadH5", "原生静态资源检查+HttpServer init");
            timber.log.a.g("LauncherHelper").h("checkH5Update", new Object[0]);
            l.k(new b0(this)).e0(60, TimeUnit.SECONDS).b0(com.leedarson.base.http.observer.l.c).a(new a());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public /* synthetic */ void k(m mVar) {
        m e;
        if (!PatchProxy.proxy(new Object[]{mVar}, this, changeQuickRedirect, false, 14169, new Class[]{m.class}, Void.TYPE).isSupported) {
            m e2 = mVar;
            if (this.b != null) {
                Context context = BaseApplication.b();
                String path = BaseApplication.b().getFilesDir() + "/web/index.html";
                boolean isExist = w.n(path);
                String appVersion = SharePreferenceUtils.getPrefString(context, SharePreferenceUtils.KEY_APP_VERSION, (String) null);
                if (appVersion == null) {
                    e = e2;
                    String str = path;
                    boolean z = isExist;
                } else if (!isExist) {
                    e = e2;
                    String str2 = path;
                    boolean z2 = isExist;
                } else {
                    String assetsWebVersion = SharePreferenceUtils.getPrefString(context, "WEB_VERSION", "");
                    String h5LastDownloadVersion = SharePreferenceUtils.getPrefString(context, SharePreferenceUtils.KEY_H5_LAST_DOWNLOAD_VERSION, "");
                    String h5LastUseVersion = SharePreferenceUtils.getPrefString(context, SharePreferenceUtils.KEY_H5_LAST_USE_VERSION, "");
                    Log.d("LauncherHelper", "assetsWebVersion:" + assetsWebVersion + "-----h5LastDownloadVersion" + h5LastDownloadVersion + "-----h5LastUseVersion" + h5LastUseVersion);
                    String h5LastDownloadVersion2 = w.t(assetsWebVersion, h5LastDownloadVersion);
                    SharePreferenceUtils.setPrefString(context, SharePreferenceUtils.KEY_H5_LAST_DOWNLOAD_VERSION, h5LastDownloadVersion2);
                    String str3 = path;
                    boolean z3 = isExist;
                    long curVersionCode = w.F(context);
                    e = e2;
                    long lastAndVersionCode = SharePreferenceUtils.getPrefLong(context, SharePreferenceUtils.KEY_AND_VERSION_CODE, 0);
                    boolean unzip1 = curVersionCode != lastAndVersionCode;
                    boolean unzip2 = (h5LastUseVersion == null || h5LastDownloadVersion2 == null || h5LastUseVersion.equals(h5LastDownloadVersion2)) ? false : true;
                    boolean unzip3 = !w.E(context).equals(appVersion);
                    String str4 = h5LastDownloadVersion2;
                    StringBuilder sb = new StringBuilder();
                    String str5 = assetsWebVersion;
                    sb.append("curVersionCode:");
                    sb.append(curVersionCode);
                    sb.append("-----lastAndVersionCode");
                    sb.append(lastAndVersionCode);
                    Log.d("LauncherHelper", sb.toString());
                    if (unzip1 || unzip2 || unzip3) {
                        Log.w("LauncherHelper", "check build.zip md5");
                        String md5 = smarthome.utils.l.c(context, "build.zip");
                        long j = lastAndVersionCode;
                        String h5ZipMd5Saved = SharePreferenceUtils.getPrefString(context, "h5_zip_md5", (String) null);
                        a.b g = timber.log.a.g("LauncherHelper");
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(h5ZipMd5Saved);
                        long j2 = curVersionCode;
                        sb2.append("---get:");
                        sb2.append(md5);
                        String str6 = h5LastUseVersion;
                        g.h(sb2.toString(), new Object[0]);
                        Log.e("LauncherHelper", h5ZipMd5Saved + "---get:" + md5);
                        if (!(h5ZipMd5Saved == null || md5 == null || h5ZipMd5Saved.equals(md5))) {
                            w.k(context.getFilesDir() + "/web");
                            SharePreferenceUtils.deleteByKey(context, SharePreferenceUtils.KEY_H5_HAS_COPY);
                            SharePreferenceUtils.deleteByKey(context, SharePreferenceUtils.KEY_H5_VERSION);
                        }
                        m e3 = e;
                        e3.onNext(0);
                        e3.onComplete();
                        return;
                    }
                    Log.d("LauncherHelper", "skip check build.zip md5");
                    m e32 = e;
                    e32.onNext(0);
                    e32.onComplete();
                    return;
                }
                w.k(context.getFilesDir() + "/web");
                SharePreferenceUtils.deleteByKey(context, SharePreferenceUtils.KEY_H5_HAS_COPY);
                SharePreferenceUtils.deleteByKey(context, SharePreferenceUtils.KEY_H5_VERSION);
                SharePreferenceUtils.setPrefString(context, SharePreferenceUtils.KEY_APP_VERSION, w.E(context));
                SharePreferenceUtils.setPreLong(context, SharePreferenceUtils.KEY_AND_VERSION_CODE, w.F(context));
                SharePreferenceUtils.setPrefString(context, SharePreferenceUtils.KEY_H5_LAST_DOWNLOAD_VERSION, SharePreferenceUtils.getPrefString(context, "WEB_VERSION", ""));
                m e322 = e;
                e322.onNext(0);
                e322.onComplete();
                return;
            }
            m mVar2 = e2;
        }
    }

    /* compiled from: LauncherHelper */
    public class a implements q {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
        }

        public void onNext(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 14173, new Class[]{Object.class}, Void.TYPE).isSupported) {
                Log.e("LauncherHelper", "checkH5Update onNext: ");
                e0.a(e0.this);
            }
        }

        public void onError(Throwable e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 14174, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                Log.e("LauncherHelper", "checkH5Update onError: " + e.toString());
                e0.a(e0.this);
            }
        }

        public void onComplete() {
        }
    }

    private void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14164, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("LauncherHelper").h("checkEnd:", new Object[0]);
            v.d("CoreActivity#h5zipCheck", "本地H5zip包检测");
            boolean hasCopy = SharePreferenceUtils.getPrefBoolean(this.b, SharePreferenceUtils.KEY_H5_HAS_COPY, false);
            a.b g = timber.log.a.g("LauncherHelper");
            g.h("checkEnd:-----hasCopy" + hasCopy, new Object[0]);
            if (hasCopy) {
                o();
            } else {
                f();
            }
        }
    }

    private void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14165, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("LauncherHelper").h("copyAssetToStorage", new Object[0]);
            Log.e("LauncherHelper", "copyAssetToStorage: ");
            l.k(new c()).e0(60, TimeUnit.SECONDS).b0(com.leedarson.base.http.observer.l.c).J(io.reactivex.android.schedulers.a.a()).a(new b());
        }
    }

    /* compiled from: LauncherHelper */
    public class c implements n<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x0135  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0140  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void subscribe(io.reactivex.m<java.lang.Integer> r17) {
            /*
                r16 = this;
                java.lang.String r1 = "CoreActivity#md5"
                java.lang.String r2 = "CoreActivity#unzip"
                java.lang.String r3 = "LauncherHelper"
                r4 = 1
                java.lang.Object[] r5 = new java.lang.Object[r4]
                r12 = 0
                r5[r12] = r17
                com.meituan.robust.ChangeQuickRedirect r7 = changeQuickRedirect
                java.lang.Class[] r10 = new java.lang.Class[r4]
                java.lang.Class<io.reactivex.m> r0 = io.reactivex.m.class
                r10[r12] = r0
                java.lang.Class r11 = java.lang.Void.TYPE
                r8 = 0
                r9 = 14177(0x3761, float:1.9866E-41)
                r6 = r16
                com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r5, r6, r7, r8, r9, r10, r11)
                boolean r0 = r0.isSupported
                if (r0 == 0) goto L_0x0024
                return
            L_0x0024:
                r5 = r16
                r6 = r17
                smarthome.ui.e0 r0 = smarthome.ui.e0.this
                android.app.Activity r0 = smarthome.ui.e0.c(r0)
                if (r0 == 0) goto L_0x017a
                r0 = 0
                r7 = 0
                java.lang.String r8 = ""
                r9 = r8
                r8 = r7
                r7 = r0
            L_0x0037:
                java.lang.String r0 = "开始unzip build.zip资源"
                com.leedarson.base.utils.v.d(r2, r0)     // Catch:{ Exception -> 0x00ee }
                smarthome.ui.e0 r0 = smarthome.ui.e0.this     // Catch:{ Exception -> 0x00ee }
                android.app.Activity r0 = smarthome.ui.e0.c(r0)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r10 = "build"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ee }
                r11.<init>()     // Catch:{ Exception -> 0x00ee }
                smarthome.ui.e0 r13 = smarthome.ui.e0.this     // Catch:{ Exception -> 0x00ee }
                android.app.Activity r13 = smarthome.ui.e0.c(r13)     // Catch:{ Exception -> 0x00ee }
                java.io.File r13 = r13.getFilesDir()     // Catch:{ Exception -> 0x00ee }
                java.lang.String r13 = r13.getAbsolutePath()     // Catch:{ Exception -> 0x00ee }
                r11.append(r13)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r13 = "/web"
                r11.append(r13)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x00ee }
                com.leedarson.base.utils.w.c0(r0, r10, r11, r4)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r0 = "结束unzip build.zip资源"
                com.leedarson.base.utils.v.d(r2, r0)     // Catch:{ Exception -> 0x00ee }
                r7 = 1
                timber.log.a$b r0 = timber.log.a.g(r3)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r10 = "copyAssetToStorage:0"
                java.lang.Object[] r11 = new java.lang.Object[r12]     // Catch:{ Exception -> 0x00ee }
                r0.h(r10, r11)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r0 = "copyAssetToStorage:0 "
                android.util.Log.e(r3, r0)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r0 = "开始执行 资源包md5正确性校验"
                com.leedarson.base.utils.v.d(r1, r0)     // Catch:{ Exception -> 0x00ee }
                smarthome.ui.e0 r0 = smarthome.ui.e0.this     // Catch:{ Exception -> 0x00ee }
                android.app.Activity r0 = smarthome.ui.e0.c(r0)     // Catch:{ Exception -> 0x00ee }
                android.content.Context r0 = r0.getApplicationContext()     // Catch:{ Exception -> 0x00ee }
                java.lang.String r10 = "build.zip"
                java.lang.String r0 = smarthome.utils.l.c(r0, r10)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r10 = "结束执行 资源包md5正确性校验"
                com.leedarson.base.utils.v.d(r1, r10)     // Catch:{ Exception -> 0x00ee }
                smarthome.ui.e0 r10 = smarthome.ui.e0.this     // Catch:{ Exception -> 0x00ee }
                android.app.Activity r10 = smarthome.ui.e0.c(r10)     // Catch:{ Exception -> 0x00ee }
                android.content.Context r10 = r10.getApplicationContext()     // Catch:{ Exception -> 0x00ee }
                java.lang.String r11 = "h5_zip_md5"
                com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r10, r11, r0)     // Catch:{ Exception -> 0x00ee }
                smarthome.ui.e0 r10 = smarthome.ui.e0.this     // Catch:{ Exception -> 0x00ee }
                android.app.Activity r10 = smarthome.ui.e0.c(r10)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r11 = "h5_last_download_version"
                java.lang.String r13 = ""
                java.lang.String r10 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r10, r11, r13)     // Catch:{ Exception -> 0x00ee }
                smarthome.ui.e0 r11 = smarthome.ui.e0.this     // Catch:{ Exception -> 0x00ee }
                android.app.Activity r11 = smarthome.ui.e0.c(r11)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r13 = "h5_last_use_version"
                com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r11, r13, r10)     // Catch:{ Exception -> 0x00ee }
                smarthome.ui.e0 r11 = smarthome.ui.e0.this     // Catch:{ Exception -> 0x00ee }
                android.app.Activity r11 = smarthome.ui.e0.c(r11)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r13 = "and_version_code"
                smarthome.ui.e0 r14 = smarthome.ui.e0.this     // Catch:{ Exception -> 0x00ee }
                android.app.Activity r14 = smarthome.ui.e0.c(r14)     // Catch:{ Exception -> 0x00ee }
                long r14 = com.leedarson.base.utils.w.F(r14)     // Catch:{ Exception -> 0x00ee }
                com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPreLong(r11, r13, r14)     // Catch:{ Exception -> 0x00ee }
                timber.log.a$b r11 = timber.log.a.g(r3)     // Catch:{ Exception -> 0x00ee }
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ee }
                r13.<init>()     // Catch:{ Exception -> 0x00ee }
                java.lang.String r14 = "copyAssetToStorage(unzip) success,update h5 last use version to:"
                r13.append(r14)     // Catch:{ Exception -> 0x00ee }
                r13.append(r10)     // Catch:{ Exception -> 0x00ee }
                java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x00ee }
                java.lang.Object[] r14 = new java.lang.Object[r12]     // Catch:{ Exception -> 0x00ee }
                r11.a(r13, r14)     // Catch:{ Exception -> 0x00ee }
                goto L_0x012d
            L_0x00ee:
                r0 = move-exception
                r0.printStackTrace()
                java.lang.String r9 = r0.toString()
                com.leedarson.base.manager.a r10 = com.leedarson.base.manager.a.g()
                java.lang.StringBuilder r11 = new java.lang.StringBuilder
                r11.<init>()
                java.lang.String r13 = "err:"
                r11.append(r13)
                java.lang.String r13 = r0.getMessage()
                r11.append(r13)
                java.lang.String r11 = r11.toString()
                java.lang.String r13 = "unzipH5BuildError"
                r10.i(r13, r11)
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                java.lang.String r11 = "copyAssetToStorage error: "
                r10.append(r11)
                java.lang.String r11 = r0.toString()
                r10.append(r11)
                java.lang.String r10 = r10.toString()
                android.util.Log.e(r3, r10)
                r7 = 0
            L_0x012d:
                int r8 = r8 + r4
                if (r7 != 0) goto L_0x0133
                r0 = 4
                if (r8 < r0) goto L_0x0037
            L_0x0133:
                if (r7 == 0) goto L_0x0140
                java.lang.Integer r0 = java.lang.Integer.valueOf(r12)
                r6.onNext(r0)
                r6.onComplete()
                goto L_0x017a
            L_0x0140:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = " 站点部署解压失败-->    reUnzipCount="
                r0.append(r1)
                r0.append(r8)
                java.lang.String r1 = "  message="
                r0.append(r1)
                r0.append(r9)
                java.lang.String r0 = r0.toString()
                com.alibaba.android.arouter.launcher.a r1 = com.alibaba.android.arouter.launcher.a.c()
                java.lang.Class<com.leedarson.serviceinterface.LoggerService> r2 = com.leedarson.serviceinterface.LoggerService.class
                java.lang.Object r1 = r1.g(r2)
                com.leedarson.serviceinterface.LoggerService r1 = (com.leedarson.serviceinterface.LoggerService) r1
                if (r1 == 0) goto L_0x0172
                com.leedarson.base.application.BaseApplication r2 = com.leedarson.base.application.BaseApplication.b()
                java.lang.String r3 = "error"
                java.lang.String r4 = "startHttpServer"
                r1.reportELK(r2, r0, r3, r4)
            L_0x0172:
                java.lang.Throwable r2 = new java.lang.Throwable
                r2.<init>(r0)
                r6.onError(r2)
            L_0x017a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.e0.c.subscribe(io.reactivex.m):void");
        }
    }

    /* compiled from: LauncherHelper */
    public class b implements q {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
        }

        public void onNext(Object o) {
            if (!PatchProxy.proxy(new Object[]{o}, this, changeQuickRedirect, false, 14175, new Class[]{Object.class}, Void.TYPE).isSupported) {
                timber.log.a.g("LauncherHelper").h("copyAssetToStorage:onNext", new Object[0]);
                Log.e("LauncherHelper", "copyAssetToStorage:onNext ");
                if (((Integer) o).intValue() >= 0) {
                    e0.b(e0.this);
                }
            }
        }

        public void onError(Throwable e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 14176, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                e.printStackTrace();
                com.leedarson.base.manager.a g = com.leedarson.base.manager.a.g();
                g.i("copyZipError", "err:" + e.getMessage());
                a.b g2 = timber.log.a.g("LauncherHelper");
                g2.h("copyAssetToStorage:onError" + e.toString(), new Object[0]);
            }
        }

        public void onComplete() {
        }
    }

    private void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14166, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("LauncherHelper").h("copySuc", new Object[0]);
            Log.e("LauncherHelper", "copySuc: ");
            o();
        }
    }

    private void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14167, new Class[0], Void.TYPE).isSupported) {
            this.c = true;
            SharePreferenceUtils.setPrefBoolean(this.b, SharePreferenceUtils.KEY_H5_HAS_COPY, true);
            org.greenrobot.eventbus.c.c().l(new ServerStatusEvent(i(), 1));
        }
    }

    private Activity h() {
        return this.b;
    }

    public boolean n() {
        return this.c;
    }

    private String i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14168, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        int randomPort = SharePreferenceUtils.getPrefInt(this.b, "serverport", 9999);
        String appLanguageStr = SharePreferenceUtils.getPrefString(BaseApplication.b(), IjkMediaMeta.IJKM_KEY_LANGUAGE, "");
        String _urlParamLang = "";
        if (!TextUtils.isEmpty(appLanguageStr)) {
            _urlParamLang = "?lang=" + appLanguageStr;
        }
        timber.log.a.i("lang---->webReadygetLoadUrl=" + _urlParamLang, new Object[0]);
        return String.format(Locale.US, "https://%s:%d/%s", new Object[]{"127.0.0.1", Integer.valueOf(randomPort), _urlParamLang});
    }
}
