package com.leedarson.serviceimpl.system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import com.leedarson.base.http.observer.l;
import com.leedarson.serviceinterface.utils.FileUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import okhttp3.e0;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: ImageDownloader */
public class i {
    public static ChangeQuickRedirect changeQuickRedirect;
    final String a = "ImageDownloader";
    private Context b;
    private File c;
    private File d;
    private boolean e = false;
    private boolean f = false;
    private int g;
    private int h;
    private int i;
    public b j;

    /* compiled from: ImageDownloader */
    public interface b {
        void onError(String str);

        void onSuccess(String str);
    }

    public i(Context context) {
        this.b = context;
    }

    public void j(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 8810, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                this.f = jsonObject.optBoolean("truncate");
                this.e = jsonObject.optBoolean("compress");
                JSONArray urls = jsonObject.getJSONArray("urls");
                String path = jsonObject.optString("path");
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
                File file = new File(this.b.getFilesDir().getPath() + "/web" + path);
                this.c = file;
                if (!file.exists()) {
                    this.c.mkdirs();
                }
                if (this.f) {
                    File file2 = new File(this.b.getFilesDir().getPath() + "/tmp");
                    this.d = file2;
                    if (!file2.exists()) {
                        this.d.mkdirs();
                    }
                }
                i(urls);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void i(JSONArray array) {
        if (!PatchProxy.proxy(new Object[]{array}, this, changeQuickRedirect, false, 8811, new Class[]{JSONArray.class}, Void.TYPE).isSupported) {
            this.g = array.length();
            for (int i2 = 0; i2 < array.length(); i2++) {
                String url = array.getString(i2);
                List<String> pathSegments = Uri.parse(url).getPathSegments();
                if (pathSegments != null) {
                    c(url, this.c, pathSegments.get(pathSegments.size() - 1));
                }
            }
        }
    }

    private void c(String url, File dir, String fileName) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, File.class, cls};
        if (!PatchProxy.proxy(new Object[]{url, dir, fileName}, this, changeQuickRedirect, false, 8812, clsArr, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("ImageDownloader");
            g2.a("downloadImg:" + url, new Object[0]);
            ((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).h(url).b0(l.h).J(l.h).Y(new e(this, fileName, dir), new f(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public /* synthetic */ void e(String fileName, File dir, e0 response) {
        File target;
        if (!PatchProxy.proxy(new Object[]{fileName, dir, response}, this, changeQuickRedirect, false, 8817, new Class[]{String.class, File.class, e0.class}, Void.TYPE).isSupported) {
            if (!this.f || this.d == null) {
                target = new File(dir, fileName);
            } else {
                target = new File(this.d, fileName);
            }
            timber.log.a.g("ImageDownloader").a("下载成功:" + target.getAbsolutePath(), new Object[0]);
            boolean compressRet = false;
            if (this.e) {
                compressRet = b(50, response, target);
            }
            if (!compressRet || !this.e) {
                com.leedarson.base.utils.l.g(response, target, new a());
                timber.log.a.g("ImageDownloader").a("原图写入成功:" + target.getAbsolutePath(), new Object[0]);
            }
            this.i++;
            this.h++;
            h();
        }
    }

    /* compiled from: ImageDownloader */
    public class a implements com.leedarson.base.http.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onStart() {
        }

        public void onProgress(int currentLength) {
        }

        public void onFinish(String localPath) {
        }

        public void onFailure(String e) {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public /* synthetic */ void g(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 8816, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("ImageDownloader");
            g2.c("下载失败:" + throwable.toString(), new Object[0]);
            this.h = this.h + 1;
            h();
        }
    }

    private synchronized boolean b(int i2, e0 e0Var, File file) {
        int len;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i2), e0Var, file}, this, changeQuickRedirect, false, 8813, new Class[]{Integer.TYPE, e0.class, File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        e0 responseBody = e0Var;
        int quality = i2;
        File target = file;
        long kb = responseBody.contentLength() / 1024;
        InputStream is = responseBody.byteStream();
        try {
            is.reset();
        } catch (Exception e2) {
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] bytes = new byte[512];
            while (true) {
                int read = is.read(bytes);
                len = read;
                if (read == -1) {
                    break;
                }
                int len2 = len;
                try {
                    bos.write(bytes, 0, len2);
                    int i3 = len2;
                } catch (Exception e3) {
                    e = e3;
                    e0 e0Var2 = responseBody;
                    int i4 = quality;
                    e.printStackTrace();
                    return false;
                }
            }
            int i5 = len;
            bos.flush();
            Bitmap bmp = BitmapFactory.decodeStream(new ByteArrayInputStream(bos.toByteArray()));
            if (bmp == null) {
                return false;
            }
            Bitmap bitmap = a(bmp, 0.5f);
            byte[] bArr = bytes;
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(target));
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bufferedOutputStream);
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            long kb2 = target.length() / 1024;
            BufferedOutputStream bufferedOutputStream2 = bufferedOutputStream;
            a.b g2 = timber.log.a.g("ImageDownloader");
            try {
                StringBuilder sb = new StringBuilder();
                e0 e0Var3 = responseBody;
                try {
                    sb.append("压缩并保存成功:");
                    sb.append(target.getName());
                    sb.append("原图大小 kb:");
                    sb.append(kb);
                    sb.append(",压缩后:");
                    int i6 = quality;
                    long kb22 = kb2;
                    try {
                        sb.append(kb22);
                        long j2 = kb22;
                        g2.a(sb.toString(), new Object[0]);
                        return true;
                    } catch (Exception e4) {
                        e = e4;
                        e.printStackTrace();
                        return false;
                    }
                } catch (Exception e5) {
                    e = e5;
                    int i7 = quality;
                    e.printStackTrace();
                    return false;
                }
            } catch (Exception e6) {
                e = e6;
                e0 e0Var4 = responseBody;
                int i8 = quality;
                e.printStackTrace();
                return false;
            }
        } catch (Exception e7) {
            e = e7;
            e0 e0Var5 = responseBody;
            int i9 = quality;
            e.printStackTrace();
            return false;
        }
    }

    public static Bitmap a(Bitmap bitmap, float f2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap, new Float(f2)}, (Object) null, changeQuickRedirect, true, 8814, new Class[]{Bitmap.class, Float.TYPE}, Bitmap.class);
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

    private void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8815, new Class[0], Void.TYPE).isSupported) {
            if (this.h == this.g) {
                if (this.f) {
                    timber.log.a.g("ImageDownloader").a("clear dir:" + this.c.getAbsolutePath(), new Object[0]);
                    File[] files = this.c.listFiles();
                    int length = files.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        files[i2].delete();
                    }
                    for (File f2 : this.d.listFiles()) {
                        FileUtils.moveFile(f2, new File(this.c, f2.getName()));
                    }
                }
                b bVar = this.j;
                if (bVar == null || this.i != this.h) {
                    bVar.onError("下载失败....");
                } else {
                    bVar.onSuccess("数据下载完成...");
                }
            }
        }
    }
}
