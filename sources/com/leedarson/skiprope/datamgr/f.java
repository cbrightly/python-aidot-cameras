package com.leedarson.skiprope.datamgr;

import android.content.Context;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.l;
import io.reactivex.m;
import java.io.File;
import okhttp3.e0;

/* compiled from: SourceFetcher */
public class f {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public Context a;
    private String b;
    private String c;
    private int d;
    private String e;
    /* access modifiers changed from: private */
    public boolean f = false;

    static /* synthetic */ void b(f x0, String x1) {
        Class[] clsArr = {f.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 9584, clsArr, Void.TYPE).isSupported) {
            x0.k(x1);
        }
    }

    static /* synthetic */ l d(f x0, e0 x1, File x2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 9585, new Class[]{f.class, e0.class, File.class}, l.class);
        return proxy.isSupported ? (l) proxy.result : x0.e(x1, x2);
    }

    public f(Context context) {
        this.a = context;
    }

    public void f(String url, String subDir, int newVersion, String targetFile) {
        Class<String> cls = String.class;
        Object[] objArr = {url, subDir, new Integer(newVersion), targetFile};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9579, new Class[]{cls, cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            this.b = url;
            this.c = subDir;
            this.d = newVersion;
            this.e = targetFile;
            this.f = true;
            com.leedarson.skiprope.util.a.a("start download:" + targetFile + ",subDir:" + subDir + ",newVersion:" + newVersion + ",url:" + url);
            g(((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).h(url), subDir, targetFile).Y(new a(targetFile, subDir, newVersion), new b(subDir));
        }
    }

    /* compiled from: SourceFetcher */
    public class a implements io.reactivex.functions.e<e> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;
        final /* synthetic */ int f;

        a(String str, String str2, int i) {
            this.c = str;
            this.d = str2;
            this.f = i;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9587, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((e) obj);
            }
        }

        public void a(e fetcherResp) {
            if (!PatchProxy.proxy(new Object[]{fetcherResp}, this, changeQuickRedirect, false, 9586, new Class[]{e.class}, Void.TYPE).isSupported) {
                boolean unused = f.this.f = false;
                if (fetcherResp.a) {
                    com.leedarson.skiprope.util.a.a("download success:" + this.c);
                    f.b(f.this, fetcherResp.b);
                    com.leedarson.skiprope.util.a.a("unzip success:" + this.c);
                    Context c2 = f.this.a;
                    String str = this.d;
                    b.c(c2, str, this.f + "");
                    if (this.d.equals("bgm")) {
                        SharePreferenceUtils.setPrefString(f.this.a, "skip_rope_bgm", com.leedarson.base.utils.l.b(this.c));
                    }
                    if (this.d.equals("voice")) {
                        SharePreferenceUtils.setPrefString(f.this.a, "skip_voice_bgm", com.leedarson.base.utils.l.b(this.c));
                    }
                }
            }
        }
    }

    /* compiled from: SourceFetcher */
    public class b implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        b(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9589, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 9588, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                com.leedarson.skiprope.util.a.b(this.c + "download error:" + throwable.toString());
                boolean unused = f.this.f = false;
            }
        }
    }

    public l<e> g(l observable, String subDir, String targetFile) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{observable, subDir, targetFile}, this, changeQuickRedirect2, false, 9580, new Class[]{l.class, cls, cls}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        return observable.b0(com.leedarson.base.http.observer.l.a).J(com.leedarson.base.http.observer.l.a).u(new c(subDir, targetFile));
    }

    /* compiled from: SourceFetcher */
    public class c implements io.reactivex.functions.f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;

        c(String str, String str2) {
            this.c = str;
            this.d = str2;
        }

        public Object apply(Object o) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o}, this, changeQuickRedirect, false, 9590, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            File file = new File(b.a(f.this.a, this.c), this.d);
            return f.d(f.this, (e0) o, file);
        }
    }

    /* compiled from: SourceFetcher */
    public class d implements com.leedarson.base.http.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ m a;

        d(m mVar) {
            this.a = mVar;
        }

        public void onStart() {
        }

        public void onProgress(int currentLength) {
        }

        public void onFinish(String localPath) {
            if (!PatchProxy.proxy(new Object[]{localPath}, this, changeQuickRedirect, false, 9591, new Class[]{String.class}, Void.TYPE).isSupported) {
                this.a.onNext(new e(localPath));
                this.a.onComplete();
            }
        }

        public void onFailure(String e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 9592, new Class[]{String.class}, Void.TYPE).isSupported) {
                this.a.onError(new Throwable(e));
            }
        }
    }

    private l<e> e(e0 responseBody, File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{responseBody, file}, this, changeQuickRedirect, false, 9581, new Class[]{e0.class, File.class}, l.class);
        return proxy.isSupported ? (l) proxy.result : l.k(new a(this, responseBody, file));
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public /* synthetic */ void j(e0 responseBody, File file, m emitter) {
        Class[] clsArr = {e0.class, File.class, m.class};
        if (!PatchProxy.proxy(new Object[]{responseBody, file, emitter}, this, changeQuickRedirect, false, 9583, clsArr, Void.TYPE).isSupported) {
            com.leedarson.base.utils.l.g(responseBody, file, new d(emitter));
        }
    }

    /* compiled from: SourceFetcher */
    public static class e {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public boolean a;
        /* access modifiers changed from: private */
        public String b;

        public e(String localPath) {
            c(localPath);
        }

        private void c(String localPath) {
            this.a = true;
            this.b = localPath;
        }
    }

    public boolean h() {
        return this.f;
    }

    private void k(String localPath) {
        if (!PatchProxy.proxy(new Object[]{localPath}, this, changeQuickRedirect, false, 9582, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                com.leedarson.base.utils.l.e(new File(localPath), b.a(this.a, this.c), true);
            } catch (Exception e2) {
                com.leedarson.skiprope.util.a.b("unzip error:" + e2.toString() + ",localPath:" + localPath);
                e2.printStackTrace();
            }
        }
    }
}
