package com.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.d;
import com.leedarson.base.utils.w;
import io.reactivex.functions.e;
import io.reactivex.functions.f;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import okhttp3.e0;
import timber.log.a;

/* compiled from: ImageUtil */
public class b {
    /* access modifiers changed from: private */
    public String a = b.class.getSimpleName();

    public void f(Context context, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        String str = File.separator;
        sb.append(str);
        sb.append(Environment.DIRECTORY_DCIM);
        sb.append(str);
        sb.append("Camera");
        sb.append(str);
        String dir = sb.toString() + "/" + w.p(context) + "";
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String name = d.a(url + System.currentTimeMillis());
        String ext = FileUtil.JPG;
        int dot = url.lastIndexOf("\\.");
        if (dot > 0 && "png".equals(url.substring(dot))) {
            ext = "png";
        }
        File mFile = new File(dir + "/" + name + "." + ext);
        a.b g = timber.log.a.g(this.a);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("save net Img: ");
        sb2.append(mFile.getPath());
        g.h(sb2.toString(), new Object[0]);
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        e(context, url, mFile, ext);
    }

    private void e(Context context, String url, File file, String ext) {
        ((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).h(url).b0(l.e).J(l.e).G(new c(ext, context, file)).J(io.reactivex.android.schedulers.a.a()).Y(new a(context), new C0229b(context));
    }

    /* compiled from: ImageUtil */
    public class c implements f<e0, String> {
        final /* synthetic */ String c;
        final /* synthetic */ Context d;
        final /* synthetic */ File f;

        c(String str, Context context, File file) {
            this.c = str;
            this.d = context;
            this.f = file;
        }

        /* renamed from: a */
        public String apply(e0 responseBody) {
            if (responseBody.contentLength() < 0) {
                timber.log.a.g(b.this.a).c("image download fail...", new Object[0]);
                return "";
            }
            timber.log.a.g(b.this.a).a("download success,bitmap responseBody to file...", new Object[0]);
            if (b.this.c(this.d, BitmapFactory.decodeStream(responseBody.byteStream()), this.f, "png".equals(this.c) ? "image/png" : "image/jpeg", "png".equals(this.c) ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG)) {
                return this.f.getAbsolutePath();
            }
            return "";
        }
    }

    /* compiled from: ImageUtil */
    public class a implements e<String> {
        final /* synthetic */ Context c;

        a(Context context) {
            this.c = context;
        }

        /* renamed from: a */
        public void accept(String s) {
            a.b g = timber.log.a.g(b.this.a);
            g.a("save net Img result: " + s, new Object[0]);
            if (!TextUtils.isEmpty(s)) {
                Context context = this.c;
                Toast.makeText(context, "图片已保存至" + s, 1).show();
                return;
            }
            Toast.makeText(this.c, "保存失败", 0).show();
        }
    }

    /* renamed from: com.utils.b$b  reason: collision with other inner class name */
    /* compiled from: ImageUtil */
    public class C0229b implements e<Throwable> {
        final /* synthetic */ Context c;

        C0229b(Context context) {
            this.c = context;
        }

        /* renamed from: a */
        public void accept(Throwable throwable) {
            a.b g = timber.log.a.g(b.this.a);
            g.c("save net Img fail: " + throwable.toString(), new Object[0]);
            Context context = this.c;
            if (context instanceof Activity) {
                ((Activity) context).runOnUiThread(new a(throwable));
            }
        }

        /* renamed from: com.utils.b$b$a */
        /* compiled from: ImageUtil */
        public class a implements Runnable {
            final /* synthetic */ Throwable c;

            a(Throwable th) {
                this.c = th;
            }

            public void run() {
                Context context = C0229b.this.c;
                Toast.makeText(context, "保存失败:" + this.c.toString(), 0).show();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean c(Context context, Bitmap bitmap, File file, String mimeType, Bitmap.CompressFormat compressFormat) {
        try {
            bitmap.compress(compressFormat, 100, new BufferedOutputStream(new FileOutputStream(file)));
            if (Build.VERSION.SDK_INT >= 29) {
                ContentValues values = new ContentValues();
                values.put("_display_name", file.getName());
                values.put("mime_type", mimeType);
                values.put("relative_path", Environment.DIRECTORY_DCIM);
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (uri == null) {
                    timber.log.a.g(this.a).a("contentResolver.insert uri==null", new Object[0]);
                    return false;
                }
                try {
                    OutputStream outputStream = contentResolver.openOutputStream(uri);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    FileUtils.copy(fileInputStream, outputStream);
                    fileInputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                MediaScannerConnection.scanFile(context.getApplicationContext(), new String[]{file.getAbsolutePath()}, new String[]{"image/jpeg"}, a.a);
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return true;
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    static /* synthetic */ void d(String path, Uri uri) {
    }
}
