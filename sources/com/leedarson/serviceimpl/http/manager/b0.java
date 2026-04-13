package com.leedarson.serviceimpl.http.manager;

import android.content.Context;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.requests.c;
import com.leedarson.serviceimpl.http.requests.d;
import com.leedarson.serviceimpl.http.requests.g;
import com.leedarson.serviceimpl.http.requests.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import io.reactivex.f;
import io.reactivex.r;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import okhttp3.e0;

/* compiled from: HttpManager */
public class b0 {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Random a;

    /* compiled from: HttpManager */
    public static class b {
        /* access modifiers changed from: private */
        public static b0 a = new b0();
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    private b0() {
        this.a = new Random();
    }

    public static b0 b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 7959, new Class[0], b0.class);
        return proxy.isSupported ? (b0) proxy.result : b.a;
    }

    private String c(String url) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 7960, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return url + this.a.nextInt(9999999);
    }

    public io.reactivex.disposables.b O(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String url, String header, String jsonData, i<String> iVar) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, bVar, url, header, jsonData, iVar}, this, changeQuickRedirect, false, 7961, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, cls, i.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        return P(context, bVar, url, header, jsonData, iVar, (r) null);
    }

    public io.reactivex.disposables.b P(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String header, String jsonData, i<String> iVar, r onObserverScheduler) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, bVar, str, header, jsonData, iVar, onObserverScheduler}, this, changeQuickRedirect, false, 7962, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, cls, i.class, r.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        i<String> iVar2 = iVar;
        Context context2 = context;
        String url = str;
        iVar2.setTag(c(url));
        g postJsonRequest = new g(bVar);
        postJsonRequest.f = jsonData;
        postJsonRequest.b = url;
        postJsonRequest.d = header;
        postJsonRequest.a = "application/json";
        return postJsonRequest.b(new v(iVar2), new q(iVar2), true, onObserverScheduler);
    }

    static /* synthetic */ void C(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 8008, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void D(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 8007, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    public e<String> Q(com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> lifecycle, String url, String header, String jsonData) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{lifecycle, url, header, jsonData}, this, changeQuickRedirect, false, 7963, new Class[]{com.trello.rxlifecycle3.b.class, cls, cls, cls}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        return e.d(new h(lifecycle, jsonData, url, header), io.reactivex.a.DROP);
    }

    static /* synthetic */ void G(com.trello.rxlifecycle3.b lifecycle, String jsonData, String url, String header, f emitter) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{lifecycle, jsonData, url, header, emitter}, (Object) null, changeQuickRedirect, true, 8004, new Class[]{com.trello.rxlifecycle3.b.class, cls, cls, cls, f.class}, Void.TYPE).isSupported) {
            g postJsonRequest = new g(lifecycle);
            postJsonRequest.f = jsonData;
            postJsonRequest.b = url;
            postJsonRequest.d = header;
            postJsonRequest.a = "application/json";
            postJsonRequest.a(new i(emitter), new c(emitter), true);
        }
    }

    static /* synthetic */ void E(f emitter, String response) {
        Class[] clsArr = {f.class, String.class};
        if (!PatchProxy.proxy(new Object[]{emitter, response}, (Object) null, changeQuickRedirect, true, 8006, clsArr, Void.TYPE).isSupported) {
            emitter.onNext(response);
            emitter.onComplete();
        }
    }

    static /* synthetic */ void F(f emitter, Throwable error) {
        Class[] clsArr = {f.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{emitter, error}, (Object) null, changeQuickRedirect, true, 8005, clsArr, Void.TYPE).isSupported) {
            if (error instanceof ApiException) {
                emitter.onError((ApiException) error);
            } else {
                emitter.onError(new ApiException(error, -1000));
            }
        }
    }

    public void N(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String header, String formData, i<String> iVar) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, bVar, str, header, formData, iVar}, this, changeQuickRedirect, false, 7964, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, cls, i.class}, Void.TYPE).isSupported) {
            i<String> iVar2 = iVar;
            Context context2 = context;
            String url = str;
            iVar2.setTag(c(url));
            g postJsonRequest = new g(bVar);
            postJsonRequest.e = formData;
            postJsonRequest.b = url;
            postJsonRequest.d = header;
            postJsonRequest.a = "application/json";
            postJsonRequest.a(new n(iVar2), new r(iVar2), false);
        }
    }

    static /* synthetic */ void A(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 8003, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void B(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 8002, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    public void R(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String header, String jsonData, i<String> iVar) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, bVar, str, header, jsonData, iVar}, this, changeQuickRedirect, false, 7965, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, cls, i.class}, Void.TYPE).isSupported) {
            i<String> iVar2 = iVar;
            Context context2 = context;
            String url = str;
            iVar2.setTag(c(url));
            h request = new h(bVar);
            request.f = jsonData;
            request.b = url;
            request.d = header;
            request.a = "application/json";
            request.a(new t(iVar2), new e(iVar2));
        }
    }

    static /* synthetic */ void H(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 8001, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void I(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 8000, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    public void M(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String header, String jsonData, i<String> iVar) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, bVar, str, header, jsonData, iVar}, this, changeQuickRedirect, false, 7966, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, cls, i.class}, Void.TYPE).isSupported) {
            i<String> iVar2 = iVar;
            Context context2 = context;
            String url = str;
            iVar2.setTag(c(url));
            com.leedarson.serviceimpl.http.requests.f request = new com.leedarson.serviceimpl.http.requests.f(bVar);
            request.f = jsonData;
            request.b = url;
            request.d = header;
            request.a = "application/json";
            request.a(new g(iVar2), new a(iVar2));
        }
    }

    static /* synthetic */ void y(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 7998, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    static /* synthetic */ void z(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 7999, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    public io.reactivex.disposables.b K(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String url, String header, String jsonData, i<String> iVar) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, bVar, url, header, jsonData, iVar}, this, changeQuickRedirect, false, 7967, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, cls, i.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        return L(context, bVar, url, header, jsonData, iVar, (r) null);
    }

    public io.reactivex.disposables.b L(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String header, String jsonData, i<String> iVar, r newOnObserverScheduler) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, bVar, str, header, jsonData, iVar, newOnObserverScheduler}, this, changeQuickRedirect, false, 7968, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, cls, i.class, r.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        i<String> iVar2 = iVar;
        Context context2 = context;
        String url = str;
        iVar2.setTag(c(url));
        com.leedarson.serviceimpl.http.requests.e getRequest = new com.leedarson.serviceimpl.http.requests.e(bVar);
        getRequest.e = jsonData;
        getRequest.b = url;
        getRequest.d = header;
        return getRequest.a(new d(iVar2), new b(iVar2), newOnObserverScheduler);
    }

    static /* synthetic */ void w(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 7997, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void x(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 7996, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    public io.reactivex.disposables.b J(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String header, String jsonData, i<String> iVar) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, bVar, str, header, jsonData, iVar}, this, changeQuickRedirect, false, 7969, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, cls, i.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        i<String> iVar2 = iVar;
        Context context2 = context;
        String url = str;
        iVar2.setTag(c(url));
        d request = new d(bVar);
        request.e = jsonData;
        request.b = url;
        request.d = header;
        return request.a(new k(iVar2), new w(iVar2));
    }

    static /* synthetic */ void u(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 7995, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void v(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 7994, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    public void g(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String str2, ArrayList<File> files, String str3, i<String> iVar) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, bVar, str, str2, files, str3, iVar}, this, changeQuickRedirect, false, 7971, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, ArrayList.class, cls, i.class}, Void.TYPE).isSupported) {
            com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar2 = bVar;
            String params = str3;
            String header = str2;
            Context context2 = context;
            String url = str;
            i<String> iVar2 = iVar;
            iVar2.setTag(c(url));
            List<String> filePaths = new ArrayList<>();
            Iterator<File> it = files.iterator();
            while (it.hasNext()) {
                filePaths.add(it.next().getPath());
            }
            c fileUploadRequest = new c(bVar2);
            fileUploadRequest.a = com.yanzhenjie.andserver.util.h.MULTIPART_FORM_DATA_VALUE;
            fileUploadRequest.c = filePaths;
            fileUploadRequest.d = url;
            fileUploadRequest.g = params;
            fileUploadRequest.f = header;
            fileUploadRequest.a(new o(iVar2), new j(iVar2));
        }
    }

    static /* synthetic */ void o(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 7991, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void p(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 7990, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    public io.reactivex.disposables.b h(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String str2, ArrayList<File> files, String str3, i<String> iVar) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, bVar, str, str2, files, str3, iVar}, this, changeQuickRedirect, false, 7972, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, ArrayList.class, cls, i.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar2 = bVar;
        String params = str3;
        String header = str2;
        Context context2 = context;
        String url = str;
        i<String> iVar2 = iVar;
        iVar2.setTag(c(url));
        List<String> filePaths = new ArrayList<>();
        Iterator<File> it = files.iterator();
        while (it.hasNext()) {
            filePaths.add(it.next().getPath());
        }
        com.leedarson.serviceimpl.http.requests.b fileUploadRequest = new com.leedarson.serviceimpl.http.requests.b(bVar2);
        fileUploadRequest.a = com.yanzhenjie.andserver.util.h.MULTIPART_FORM_DATA_VALUE;
        fileUploadRequest.c = filePaths;
        fileUploadRequest.d = url;
        fileUploadRequest.g = params;
        fileUploadRequest.f = header;
        return fileUploadRequest.a(new l(iVar2), new p(iVar2));
    }

    static /* synthetic */ void s(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 7989, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void t(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 7988, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    public void f(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String header, File file, String str2, i<String> iVar) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, bVar, str, header, file, str2, iVar}, this, changeQuickRedirect, false, 7973, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, File.class, cls, i.class}, Void.TYPE).isSupported) {
            String str3 = str2;
            Context context2 = context;
            String url = str;
            i<String> iVar2 = iVar;
            iVar2.setTag(c(url));
            List<String> filePaths = new ArrayList<>();
            filePaths.add(file.getPath());
            c fileUploadRequest = new c(bVar);
            fileUploadRequest.a = com.yanzhenjie.andserver.util.h.MULTIPART_FORM_DATA_VALUE;
            fileUploadRequest.c = filePaths;
            fileUploadRequest.d = url;
            fileUploadRequest.f = header;
            fileUploadRequest.a(new s(iVar2), new z(iVar2));
        }
    }

    static /* synthetic */ void q(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 7987, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void r(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 7986, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    public void e(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String str2, ArrayList<File> files, String str3, i<String> iVar) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, bVar, str, str2, files, str3, iVar}, this, changeQuickRedirect, false, 7974, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, ArrayList.class, cls, i.class}, Void.TYPE).isSupported) {
            com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar2 = bVar;
            String params = str3;
            String header = str2;
            Context context2 = context;
            String url = str;
            i<String> iVar2 = iVar;
            iVar2.setTag(c(url));
            List<String> filePaths = new ArrayList<>();
            Iterator<File> it = files.iterator();
            while (it.hasNext()) {
                filePaths.add(it.next().getPath());
            }
            c fileUploadRequest = new c(bVar2);
            fileUploadRequest.a = com.yanzhenjie.andserver.util.h.MULTIPART_FORM_DATA_VALUE;
            fileUploadRequest.c = filePaths;
            fileUploadRequest.d = url;
            fileUploadRequest.f = header;
            fileUploadRequest.g = params;
            fileUploadRequest.a(new f(iVar2), new a0(iVar2));
        }
    }

    static /* synthetic */ void m(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 7985, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void n(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 7984, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    public io.reactivex.disposables.b a(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> lifecycle, String url, i<e0> httpRxObserver) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, lifecycle, url, httpRxObserver}, this, changeQuickRedirect, false, 7975, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, String.class, i.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        httpRxObserver.setTag(c(url));
        com.leedarson.serviceimpl.http.requests.a downloadRequest = new com.leedarson.serviceimpl.http.requests.a(lifecycle);
        downloadRequest.b = url;
        return downloadRequest.a(new x(httpRxObserver), new y(httpRxObserver));
    }

    static /* synthetic */ void i(i httpRxObserver, e0 n) {
        Class[] clsArr = {i.class, e0.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 7983, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void j(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 7982, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }

    public void d(Context context, com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a> bVar, String str, String header, String params, i<String> iVar, long delay) {
        Class<String> cls = String.class;
        Object[] objArr = {context, bVar, str, header, params, iVar, new Long(delay)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7976, new Class[]{Context.class, com.trello.rxlifecycle3.b.class, cls, cls, cls, i.class, Long.TYPE}, Void.TYPE).isSupported) {
            i<String> iVar2 = iVar;
            Context context2 = context;
            String url = str;
            iVar2.setTag(c(url));
            com.leedarson.serviceimpl.http.requests.e getRequest = new com.leedarson.serviceimpl.http.requests.e(bVar);
            getRequest.b = url;
            getRequest.d = header;
            getRequest.e = params;
            getRequest.b(new m(iVar2), new u(iVar2), delay);
        }
    }

    static /* synthetic */ void k(i httpRxObserver, String n) {
        Class[] clsArr = {i.class, String.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, n}, (Object) null, changeQuickRedirect, true, 7981, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onNext(n);
        }
    }

    static /* synthetic */ void l(i httpRxObserver, Throwable e) {
        Class[] clsArr = {i.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{httpRxObserver, e}, (Object) null, changeQuickRedirect, true, 7980, clsArr, Void.TYPE).isSupported) {
            httpRxObserver.onError(e);
        }
    }
}
