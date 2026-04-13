package com.leedarson.base.jsbridge2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.leedarson.base.http.observer.l;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.e;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import okhttp3.e0;
import timber.log.a;

/* compiled from: AiServiceCacheStrategy */
public class b extends d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context b;
    final String c = "ai-service";
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public String e;
    final String f = "WebCacheStrategy";

    static /* synthetic */ File d(b x0, String x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 218, new Class[]{b.class, String.class}, File.class);
        return proxy.isSupported ? (File) proxy.result : x0.m(x1);
    }

    static /* synthetic */ void f(b x0, int x1, e0 x2, File x3) {
        Object[] objArr = {x0, new Integer(x1), x2, x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 219, new Class[]{b.class, Integer.TYPE, e0.class, File.class}, Void.TYPE).isSupported) {
            x0.i(x1, x2, x3);
        }
    }

    static /* synthetic */ void g(b x0, File x1, File x2) {
        Class[] clsArr = {b.class, File.class, File.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 220, clsArr, Void.TYPE).isSupported) {
            x0.j(x1, x2);
        }
    }

    public b(Context context) {
        this.b = context;
    }

    public boolean b(WebView webView, WebResourceRequest request) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{webView, request}, this, changeQuickRedirect2, false, 209, new Class[]{WebView.class, WebResourceRequest.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return n(request.getUrl());
    }

    public WebResourceResponse a(WebView webView, WebResourceRequest request) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{webView, request}, this, changeQuickRedirect, false, 210, new Class[]{WebView.class, WebResourceRequest.class}, WebResourceResponse.class);
        if (proxy.isSupported) {
            return (WebResourceResponse) proxy.result;
        }
        String str = this.e;
        if (str == null || this.d == null) {
            return null;
        }
        File target = new File(m(str), this.d);
        if (target.exists()) {
            try {
                FileInputStream input = new FileInputStream(target);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(l(target.getName()));
                a.b g = timber.log.a.g("WebCacheStrategy");
                g.m("缓存图片已存在，直接返回response,mimeType:" + mimeType, new Object[0]);
                return new WebResourceResponse(mimeType, "UTF-8", input);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        } else {
            k(request);
            return null;
        }
    }

    private boolean n(Uri uri) {
        List<String> pathSegments;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{uri}, this, changeQuickRedirect, false, 211, new Class[]{Uri.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (uri.getPath().contains("ai-service") && (pathSegments = uri.getPathSegments()) != null) {
                if (pathSegments.size() == 5) {
                    this.d = pathSegments.get(4);
                    this.e = pathSegments.get(3);
                    a.b g = timber.log.a.g("WebCacheStrategy");
                    g.a("matches success deviceId:" + this.e + ",imageId:" + this.d, new Object[0]);
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            a.b g2 = timber.log.a.g("WebCacheStrategy");
            g2.c("matches err:" + e2.toString(), new Object[0]);
            return false;
        }
    }

    private void k(WebResourceRequest request) {
        if (!PatchProxy.proxy(new Object[]{request}, this, changeQuickRedirect, false, 212, new Class[]{WebResourceRequest.class}, Void.TYPE).isSupported) {
            ((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).h(request.getUrl().toString()).b0(l.h).J(l.h).Y(new a(), new C0080b());
        }
    }

    /* compiled from: AiServiceCacheStrategy */
    public class a implements e<e0> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 222, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((e0) obj);
            }
        }

        public void a(e0 response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 221, new Class[]{e0.class}, Void.TYPE).isSupported) {
                b bVar = b.this;
                File dir = b.d(bVar, bVar.e);
                File target = new File(dir, b.this.d);
                a.b g = timber.log.a.g("WebCacheStrategy");
                g.c("下载成功:" + target.getAbsolutePath(), new Object[0]);
                b.f(b.this, 50, response, target);
                b.g(b.this, dir, target);
            }
        }
    }

    /* renamed from: com.leedarson.base.jsbridge2.b$b  reason: collision with other inner class name */
    /* compiled from: AiServiceCacheStrategy */
    public class C0080b implements e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0080b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 224, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 223, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("WebCacheStrategy");
                g.c("下载失败:" + throwable.toString(), new Object[0]);
            }
        }
    }

    private File m(String subDir) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{subDir}, this, changeQuickRedirect, false, 213, new Class[]{String.class}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        File dir = new File(this.b.getCacheDir() + File.separator + this.a, subDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private void i(int i, e0 e0Var, File file) {
        Object[] objArr = {new Integer(i), e0Var, file};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 214, new Class[]{Integer.TYPE, e0.class, File.class}, Void.TYPE).isSupported) {
            e0 responseBody = e0Var;
            int quality = i;
            File target = file;
            long kb = responseBody.contentLength() / 1024;
            Bitmap bitmap = h(BitmapFactory.decodeStream(responseBody.byteStream()), 0.8f);
            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(target));
                bitmap.compress(format, quality, bufferedOutputStream);
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                long kb2 = target.length() / 1024;
                a.b g = timber.log.a.g("WebCacheStrategy");
                StringBuilder sb = new StringBuilder();
                BufferedOutputStream bufferedOutputStream2 = bufferedOutputStream;
                sb.append("压缩并保存成功:");
                sb.append(target.getName());
                sb.append("原图大小 kb:");
                sb.append(kb);
                sb.append(",压缩后:");
                sb.append(kb2);
                g.c(sb.toString(), new Object[0]);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static Bitmap h(Bitmap bitmap, float f2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap, new Float(f2)}, (Object) null, changeQuickRedirect, true, 215, new Class[]{Bitmap.class, Float.TYPE}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        Bitmap beforBitmap = bitmap;
        float scale = f2;
        float beforeHeight = (float) beforBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(beforBitmap, 0, 0, (int) ((float) beforBitmap.getWidth()), (int) beforeHeight, matrix, true);
    }

    private void j(File dir, File target) {
        if (!PatchProxy.proxy(new Object[]{dir, target}, this, changeQuickRedirect, false, 216, new Class[]{File.class, File.class}, Void.TYPE).isSupported) {
            for (File file : dir.listFiles()) {
                if (!file.getName().equals(target.getName())) {
                    timber.log.a.g("WebCacheStrategy").a("删除旧缓存图片:" + file.getName(), new Object[0]);
                    file.delete();
                }
            }
        }
    }

    public String l(String filename) {
        int dot;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filename}, this, changeQuickRedirect, false, 217, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (filename == null || filename.length() <= 0 || (dot = filename.lastIndexOf(46)) <= -1 || dot >= filename.length() - 1) {
            return "";
        }
        return filename.substring(dot + 1);
    }
}
