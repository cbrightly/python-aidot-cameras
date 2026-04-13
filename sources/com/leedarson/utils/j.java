package com.leedarson.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import androidx.core.view.MotionEventCompat;
import com.bumptech.glide.h;
import com.bumptech.glide.load.engine.i;
import com.leedarson.R$color;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.l;

/* compiled from: PlayerUtils */
public final class j {
    public static boolean a = false;
    static byte[] b;
    static byte[] c;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String e(String path) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{path}, (Object) null, changeQuickRedirect, true, 11504, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        String result = null;
        try {
            InputStream is2 = new FileInputStream(path);
            byte[] data = new byte[is2.available()];
            is2.read(data);
            result = Base64.encodeToString(data, 0);
            try {
                is2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (is != null) {
                is.close();
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
        return "data:image/png;base64," + result;
    }

    public static void g(Context context, String path, boolean isShow, ImageView iv_preview) {
        if (!PatchProxy.proxy(new Object[]{context, path, new Byte(isShow ? (byte) 1 : 0), iv_preview}, (Object) null, changeQuickRedirect, true, 11505, new Class[]{Context.class, String.class, Boolean.TYPE, ImageView.class}, Void.TYPE).isSupported) {
            if (isShow) {
                timber.log.a.g("ipc").a("#setPreviewThumb:" + isShow + ",  " + path, new Object[0]);
                String url = path;
                if (path.startsWith(l.DEFAULT_SCHEME_NAME)) {
                    timber.log.a.g("ipc").a("#load http url", new Object[0]);
                } else {
                    timber.log.a.g("ipc").a("#load cache img :" + path, new Object[0]);
                    url = context.getFilesDir().toString() + File.separator + path.replace("build", "web");
                }
                com.bumptech.glide.b.t(context).i().M0(url).D0(new a(iv_preview, iv_preview));
                iv_preview.setVisibility(0);
                return;
            }
            timber.log.a.g("ipc").a("#setPreviewThumb:" + isShow, new Object[0]);
            iv_preview.setVisibility(8);
        }
    }

    /* compiled from: PlayerUtils */
    public class a extends com.bumptech.glide.request.target.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ImageView p1;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(ImageView view, ImageView imageView) {
            super(view);
            this.p1 = imageView;
        }

        public /* bridge */ /* synthetic */ void n(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11510, new Class[]{Object.class}, Void.TYPE).isSupported) {
                p((Bitmap) obj);
            }
        }

        public void p(Bitmap resource) {
            if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 11509, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                if (resource != null) {
                    if (resource.getHeight() > resource.getWidth()) {
                        this.p1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    } else {
                        this.p1.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    this.p1.setImageBitmap(resource);
                }
            }
        }
    }

    public static int c(byte[] res) {
        return (res[3] & 255) | ((res[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((res[1] << 24) >>> 8) | (res[0] << 24);
    }

    public static long d(byte[] res) {
        long num = 0;
        for (int ix = 0; ix < 8; ix++) {
            num = (num << 8) | ((long) (res[ix] & 255));
        }
        return num;
    }

    public static void f(Context context, String picPath, ImageView imageView) {
        Class[] clsArr = {Context.class, String.class, ImageView.class};
        if (!PatchProxy.proxy(new Object[]{context, picPath, imageView}, (Object) null, changeQuickRedirect, true, 11506, clsArr, Void.TYPE).isSupported) {
            try {
                Log.d("TAG", a + " setResource: setCacheImg");
                int _holderId = R$color.lds_black_deep;
                ((h) ((h) ((h) ((h) com.bumptech.glide.b.t(context).i().M0(picPath).d0(_holderId)).j(_holderId)).m0(true)).f(i.b)).D0(new b(imageView, imageView));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* compiled from: PlayerUtils */
    public class b extends com.bumptech.glide.request.target.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ImageView p1;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(ImageView view, ImageView imageView) {
            super(view);
            this.p1 = imageView;
        }

        public /* bridge */ /* synthetic */ void n(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11512, new Class[]{Object.class}, Void.TYPE).isSupported) {
                p((Bitmap) obj);
            }
        }

        public void p(Bitmap resource) {
            if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 11511, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                Log.d("TAG", j.a + " setResource: " + resource);
                if (!j.a) {
                    this.p1.setVisibility(0);
                    if (resource != null) {
                        if (this.p1.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
                            this.p1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }
                        this.p1.setImageBitmap(resource);
                    }
                }
            }
        }
    }

    public static byte[] a(byte[] i420bytes, int width, int height) {
        Object[] objArr = {i420bytes, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 11507, new Class[]{byte[].class, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] bArr = b;
        if (bArr == null || bArr.length != i420bytes.length) {
            b = new byte[i420bytes.length];
        }
        int ySize = width * height;
        int yuvSize = ((width * height) * 3) / 2;
        int uIndex = ySize;
        int vIndex = (ySize * 5) / 4;
        System.arraycopy(i420bytes, 0, b, 0, ySize);
        for (int i = ySize; i < yuvSize; i += 2) {
            byte[] bArr2 = b;
            bArr2[i] = i420bytes[uIndex];
            bArr2[i + 1] = i420bytes[vIndex];
            uIndex++;
            vIndex++;
        }
        return b;
    }

    public static byte[] b(byte[] i420bytes, int width, int height) {
        Object[] objArr = {i420bytes, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 11508, new Class[]{byte[].class, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] bArr = c;
        if (bArr == null || bArr.length != i420bytes.length) {
            c = new byte[i420bytes.length];
        }
        int ySize = width * height;
        int yuvSize = ((width * height) * 3) / 2;
        int uIndex = ySize;
        int vIndex = (ySize * 5) / 4;
        System.arraycopy(i420bytes, 0, c, 0, ySize);
        for (int i = ySize; i < yuvSize; i += 2) {
            byte[] bArr2 = c;
            bArr2[i] = i420bytes[uIndex];
            bArr2[i + 1] = i420bytes[vIndex];
            uIndex++;
            vIndex++;
        }
        return c;
    }
}
