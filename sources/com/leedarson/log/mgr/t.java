package com.leedarson.log.mgr;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.disposables.b;
import io.reactivex.l;
import io.reactivex.m;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: LogMgr */
public class t {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    /* access modifiers changed from: private */
    public Set<String> b = new CopyOnWriteArraySet();
    private b c;

    public t(Context context) {
        this.a = context;
    }

    private String c(byte[] src) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{src}, this, changeQuickRedirect, false, 1300, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b2 : src) {
            String hv = Integer.toHexString(b2 & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public String e(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 1301, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            if (!file.isFile()) {
                return "";
            }
            byte[] buffer = new byte[1024];
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                FileInputStream in = new FileInputStream(file);
                while (true) {
                    int read = in.read(buffer, 0, 1024);
                    int len = read;
                    if (read != -1) {
                        digest.update(buffer, 0, len);
                    } else {
                        in.close();
                        return c(digest.digest());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            Log.e("PatchConfigRepos", "getFileMd5  exception   e=" + e2.toString());
            return "";
        }
    }

    public l<File> f(File... targetFiles) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{targetFiles}, this, changeQuickRedirect, false, 1302, new Class[]{File[].class}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        b(targetFiles);
        return l.k(new p(this, targetFiles));
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public /* synthetic */ void i(File[] fileArr, m mVar) {
        if (!PatchProxy.proxy(new Object[]{fileArr, mVar}, this, changeQuickRedirect, false, 1306, new Class[]{File[].class, m.class}, Void.TYPE).isSupported) {
            m emitter = mVar;
            File[] targetFiles = fileArr;
            String url = SharePreferenceUtils.getPrefString(this.a, "logUploadUrl", "");
            a.b g = timber.log.a.g("LogMgr");
            g.h("上报elk 文件:" + url, new Object[0]);
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(Arrays.asList(targetFiles));
            JSONObject headerJson = new JSONObject();
            String accessToken = SharePreferenceUtils.getPrefString(this.a, "accessToken", "");
            try {
                headerJson.put("appId", (Object) SharePreferenceUtils.getPrefString(this.a, "APP_ID", ""));
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson.put("token", (Object) accessToken);
                }
                headerJson.put("terminal", (Object) "app");
                if (targetFiles.length == 1) {
                    headerJson.put("file-identity", (Object) BaseApplication.b().d() + "-" + e(targetFiles[0]));
                }
            } catch (Exception e) {
                e.printStackTrace();
                a.b g2 = timber.log.a.g("LogMgr");
                g2.c("文件日志上报失败 --> " + e.toString(), new Object[0]);
            }
            String str = accessToken;
            JSONObject jSONObject = headerJson;
            this.c = b0.b().h(this.a, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), arrayList, "", new a(emitter, targetFiles));
        }
    }

    /* compiled from: LogMgr */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ m c;
        final /* synthetic */ File[] d;

        a(m mVar, File[] fileArr) {
            this.c = mVar;
            this.d = fileArr;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1309, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 1307, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                Log.e("ELK", "httpUpload err:" + e.getMessage() + ",disposed:" + this.c.isDisposed());
                if (!this.c.isDisposed()) {
                    this.c.onError(e);
                }
                for (File file : this.d) {
                    t.this.b.remove(file.getAbsolutePath());
                }
            }
        }

        public void onSuccess(String response) {
            int i = 0;
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1308, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (TextUtils.isEmpty(response) || !"TRUE".equals(response.toUpperCase())) {
                    if (!this.c.isDisposed()) {
                        m mVar = this.c;
                        mVar.onError(new Throwable("elk 服务器端返回异常 response=" + response));
                    }
                    File[] fileArr = this.d;
                    int length = fileArr.length;
                    while (i < length) {
                        t.this.b.remove(fileArr[i].getAbsolutePath());
                        i++;
                    }
                    return;
                }
                this.c.onNext(this.d[0]);
                this.c.onComplete();
                File[] fileArr2 = this.d;
                int length2 = fileArr2.length;
                while (i < length2) {
                    t.this.b.remove(fileArr2[i].getAbsolutePath());
                    i++;
                }
            }
        }
    }

    public void b(File... targetFiles) {
        if (!PatchProxy.proxy(new Object[]{targetFiles}, this, changeQuickRedirect, false, 1303, new Class[]{File[].class}, Void.TYPE).isSupported) {
            for (File file : targetFiles) {
                this.b.add(file.getAbsolutePath());
            }
        }
    }

    public boolean g(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 1304, new Class[]{File.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.b.contains(file.getAbsolutePath());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 1305(0x519, float:1.829E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.c
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.c
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.log.mgr.t.d():void");
    }
}
