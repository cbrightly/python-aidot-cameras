package com.leedarson.serviceimpl.http;

import android.content.Context;
import android.text.TextUtils;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.manager.b0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import okhttp3.e0;
import org.greenrobot.eventbus.c;
import timber.log.a;

/* compiled from: DownloadUtils */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public String a;
    private io.reactivex.disposables.b b;

    /* compiled from: DownloadUtils */
    public static class b {
        /* access modifiers changed from: private */
        public static a a = new a((C0139a) null);
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    /* synthetic */ a(C0139a x0) {
        this();
    }

    static /* synthetic */ void b(a x0, e0 x1, File x2, com.leedarson.base.http.listener.b x3) {
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 7899, new Class[]{a.class, e0.class, File.class, com.leedarson.base.http.listener.b.class}, Void.TYPE).isSupported) {
            x0.f(x1, x2, x3);
        }
    }

    private a() {
    }

    public static a d() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 7895, new Class[0], a.class);
        return proxy.isSupported ? (a) proxy.result : b.a;
    }

    public void e(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> lifecycle) {
        Class[] clsArr = {Context.class, com.trello.rxlifecycle3.b.class};
        if (!PatchProxy.proxy(new Object[]{context, lifecycle}, this, changeQuickRedirect, false, 7896, clsArr, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(this.a)) {
                File file = new File(context.getFilesDir() + "/build.zip.tem");
                if (file.exists()) {
                    file.delete();
                }
                io.reactivex.disposables.b bVar = this.b;
                if (bVar != null) {
                    bVar.dispose();
                }
                c(context, lifecycle, this.a);
            }
        }
    }

    /* renamed from: com.leedarson.serviceimpl.http.a$a  reason: collision with other inner class name */
    /* compiled from: DownloadUtils */
    public class C0139a extends i<e0> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Context c;

        C0139a(Context context) {
            this.c = context;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7902, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((e0) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7900, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("UpgradeTag");
                g.c("downloadFile onError:" + e.toString(), new Object[0]);
                com.leedarson.serviceimpl.http.evnet.a downloadFileEvent = new com.leedarson.serviceimpl.http.evnet.a();
                downloadFileEvent.a = e.getCode();
                c.c().l(downloadFileEvent);
            }
        }

        /* renamed from: com.leedarson.serviceimpl.http.a$a$a  reason: collision with other inner class name */
        /* compiled from: DownloadUtils */
        public class C0140a implements com.leedarson.base.http.listener.b {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ File a;

            C0140a(File file) {
                this.a = file;
            }

            public void onStart() {
            }

            public void onProgress(int currentLength) {
                Object[] objArr = {new Integer(currentLength)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7903, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.http.evnet.a downloadFileEvent = new com.leedarson.serviceimpl.http.evnet.a();
                    downloadFileEvent.b = currentLength;
                    c.c().l(downloadFileEvent);
                }
            }

            public void onFinish(String str) {
                if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7904, new Class[]{String.class}, Void.TYPE).isSupported) {
                    String unused = a.this.a = null;
                    if (this.a.exists()) {
                        File file = this.a;
                        file.renameTo(new File(C0139a.this.c.getFilesDir() + "/build.zip"));
                    }
                }
            }

            public void onFailure(String e) {
                if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7905, new Class[]{String.class}, Void.TYPE).isSupported) {
                    timber.log.a.g("UpgradeTag").c(e, new Object[0]);
                    com.leedarson.serviceimpl.http.evnet.a downloadFileEvent = new com.leedarson.serviceimpl.http.evnet.a();
                    downloadFileEvent.a = 1;
                    c.c().l(downloadFileEvent);
                }
            }
        }

        public void a(e0 response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 7901, new Class[]{e0.class}, Void.TYPE).isSupported) {
                File file = new File(this.c.getFilesDir() + "/build.zip.tem");
                a.b(a.this, response, file, new C0140a(file));
            }
        }
    }

    public void c(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> lifecycle, String url) {
        Class[] clsArr = {Context.class, com.trello.rxlifecycle3.b.class, String.class};
        if (!PatchProxy.proxy(new Object[]{context, lifecycle, url}, this, changeQuickRedirect, false, 7897, clsArr, Void.TYPE).isSupported) {
            this.a = url;
            this.b = b0.b().a(context, lifecycle, url, new C0139a(context));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00b1 A[SYNTHETIC, Splitter:B:36:0x00b1] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00bd A[SYNTHETIC, Splitter:B:41:0x00bd] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00d0 A[SYNTHETIC, Splitter:B:48:0x00d0] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00dc A[SYNTHETIC, Splitter:B:53:0x00dc] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00e5 A[SYNTHETIC, Splitter:B:58:0x00e5] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00f1 A[SYNTHETIC, Splitter:B:63:0x00f1] */
    /* JADX WARNING: Removed duplicated region for block: B:71:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:45:0x00c4=Splitter:B:45:0x00c4, B:33:0x00a5=Splitter:B:33:0x00a5} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f(okhttp3.e0 r19, java.io.File r20, com.leedarson.base.http.listener.b r21) {
        /*
            r18 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r19
            r2 = 1
            r1[r2] = r20
            r3 = 2
            r1[r3] = r21
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<okhttp3.e0> r0 = okhttp3.e0.class
            r6[r8] = r0
            java.lang.Class<java.io.File> r0 = java.io.File.class
            r6[r2] = r0
            java.lang.Class<com.leedarson.base.http.listener.b> r0 = com.leedarson.base.http.listener.b.class
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 7898(0x1eda, float:1.1067E-41)
            r2 = r18
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002e
            return
        L_0x002e:
            r1 = r18
            r2 = r20
            r3 = r19
            r4 = r21
            boolean r0 = r2.exists()
            if (r0 == 0) goto L_0x003f
            r2.delete()
        L_0x003f:
            r4.onStart()
            r5 = 0
            r7 = 0
            java.io.InputStream r9 = r3.byteStream()
            long r10 = r3.contentLength()
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x00c1, IOException -> 0x00a2, all -> 0x009d }
            r0.<init>(r2)     // Catch:{ FileNotFoundException -> 0x00c1, IOException -> 0x00a2, all -> 0x009d }
            r7 = r0
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch:{ FileNotFoundException -> 0x00c1, IOException -> 0x00a2, all -> 0x009d }
        L_0x0057:
            int r12 = r9.read(r0)     // Catch:{ FileNotFoundException -> 0x00c1, IOException -> 0x00a2, all -> 0x009d }
            r13 = r12
            r14 = -1
            if (r12 == r14) goto L_0x0084
            r7.write(r0, r8, r13)     // Catch:{ FileNotFoundException -> 0x00c1, IOException -> 0x00a2, all -> 0x009d }
            long r14 = (long) r13
            long r5 = r5 + r14
            r14 = 100
            long r16 = r5 * r14
            r19 = r9
            long r8 = r16 / r10
            int r8 = (int) r8     // Catch:{ FileNotFoundException -> 0x0082, IOException -> 0x0080 }
            r4.onProgress(r8)     // Catch:{ FileNotFoundException -> 0x0082, IOException -> 0x0080 }
            long r14 = r14 * r5
            long r14 = r14 / r10
            int r8 = (int) r14     // Catch:{ FileNotFoundException -> 0x0082, IOException -> 0x0080 }
            r9 = 100
            if (r8 != r9) goto L_0x007c
            java.lang.String r8 = ""
            r4.onFinish(r8)     // Catch:{ FileNotFoundException -> 0x0082, IOException -> 0x0080 }
        L_0x007c:
            r9 = r19
            r8 = 0
            goto L_0x0057
        L_0x0080:
            r0 = move-exception
            goto L_0x00a5
        L_0x0082:
            r0 = move-exception
            goto L_0x00c4
        L_0x0084:
            r19 = r9
            r7.close()     // Catch:{ IOException -> 0x008b }
            goto L_0x0091
        L_0x008b:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
        L_0x0091:
            r19.close()     // Catch:{ IOException -> 0x0096 }
        L_0x0095:
            goto L_0x00e0
        L_0x0096:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
            goto L_0x0095
        L_0x009d:
            r0 = move-exception
            r19 = r9
            r8 = r0
            goto L_0x00e3
        L_0x00a2:
            r0 = move-exception
            r19 = r9
        L_0x00a5:
            r0.printStackTrace()     // Catch:{ all -> 0x00e1 }
            java.lang.String r8 = r0.getMessage()     // Catch:{ all -> 0x00e1 }
            r4.onFailure(r8)     // Catch:{ all -> 0x00e1 }
            if (r7 == 0) goto L_0x00bb
            r7.close()     // Catch:{ IOException -> 0x00b5 }
            goto L_0x00bb
        L_0x00b5:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
        L_0x00bb:
            if (r19 == 0) goto L_0x00e0
            r19.close()     // Catch:{ IOException -> 0x0096 }
            goto L_0x0095
        L_0x00c1:
            r0 = move-exception
            r19 = r9
        L_0x00c4:
            java.lang.String r8 = r0.getMessage()     // Catch:{ all -> 0x00e1 }
            r4.onFailure(r8)     // Catch:{ all -> 0x00e1 }
            r0.printStackTrace()     // Catch:{ all -> 0x00e1 }
            if (r7 == 0) goto L_0x00da
            r7.close()     // Catch:{ IOException -> 0x00d4 }
            goto L_0x00da
        L_0x00d4:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
        L_0x00da:
            if (r19 == 0) goto L_0x00e0
            r19.close()     // Catch:{ IOException -> 0x0096 }
            goto L_0x0095
        L_0x00e0:
            return
        L_0x00e1:
            r0 = move-exception
            r8 = r0
        L_0x00e3:
            if (r7 == 0) goto L_0x00ef
            r7.close()     // Catch:{ IOException -> 0x00e9 }
            goto L_0x00ef
        L_0x00e9:
            r0 = move-exception
            r9 = r0
            r0 = r9
            r0.printStackTrace()
        L_0x00ef:
            if (r19 == 0) goto L_0x00fb
            r19.close()     // Catch:{ IOException -> 0x00f5 }
            goto L_0x00fb
        L_0x00f5:
            r0 = move-exception
            r9 = r0
            r0 = r9
            r0.printStackTrace()
        L_0x00fb:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.http.a.f(okhttp3.e0, java.io.File, com.leedarson.base.http.listener.b):void");
    }
}
