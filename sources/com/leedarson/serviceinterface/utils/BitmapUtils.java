package com.leedarson.serviceinterface.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.leedarson.base.http.observer.l;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import timber.log.a;

public class BitmapUtils {
    public static ChangeQuickRedirect changeQuickRedirect;
    static DecimalFormat decimalFormat = new DecimalFormat("0.0");
    /* access modifiers changed from: private */
    public static GetImageResponse getImageListener;
    private static BitmapUtils mSingleton = null;
    private static ExecutorService singleThreadPool = l.i(1, "bitmapUtils", 1);

    public interface GetImageResponse {
        void onResponse(Bitmap bitmap);
    }

    private BitmapUtils() {
    }

    public static BitmapUtils getInstance() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9234, new Class[0], BitmapUtils.class);
        if (proxy.isSupported) {
            return (BitmapUtils) proxy.result;
        }
        if (mSingleton == null) {
            synchronized (BitmapUtils.class) {
                if (mSingleton == null) {
                    mSingleton = new BitmapUtils();
                }
            }
        }
        return mSingleton;
    }

    public static Bitmap getRotatedBitmap(Bitmap bitmap, int rotation, boolean z) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap, new Integer(rotation), new Byte(z ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 9235, new Class[]{Bitmap.class, Integer.TYPE, Boolean.TYPE}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        Bitmap bitmap2 = bitmap;
        boolean mirror = z;
        Matrix matrix = new Matrix();
        if (mirror) {
            matrix.setScale(1.0f, -1.0f);
        }
        matrix.postRotate((float) rotation);
        matrix.postTranslate((float) bitmap2.getWidth(), 0.0f);
        return Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, false);
    }

    public static Bitmap getFlipBitmap(Bitmap bitmap) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap}, (Object) null, changeQuickRedirect, true, 9236, new Class[]{Bitmap.class}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        Bitmap bitmap2 = bitmap;
        Matrix matrix = new Matrix();
        matrix.setScale(-1.0f, 1.0f);
        matrix.postTranslate((float) bitmap2.getWidth(), 0.0f);
        return Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, false);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap, new Float(roundPx)}, (Object) null, changeQuickRedirect, true, 9237, new Class[]{Bitmap.class, Float.TYPE}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap getLoacalBitmap(String url) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url}, (Object) null, changeQuickRedirect, true, 9238, new Class[]{String.class}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        try {
            return getRoundedCornerBitmap(BitmapFactory.decodeStream(new FileInputStream(url)), 10.0f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void compressPhto(File mFile) {
        if (!PatchProxy.proxy(new Object[]{mFile}, this, changeQuickRedirect, false, 9239, new Class[]{File.class}, Void.TYPE).isSupported) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mFile.getAbsolutePath(), options);
            int i = options.outHeight;
            int i2 = options.outWidth;
            String str = options.outMimeType;
            options.inJustDecodeBounds = false;
            options.inSampleSize = caculateSampleSize(options, 500, 500);
            String path = mFile.getPath();
            Bitmap decodeFile = BitmapFactory.decodeFile(mFile.getAbsolutePath(), options);
        }
    }

    private int caculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int sampleSize = 1;
        int picWidth = options.outWidth;
        int picHeight = options.outHeight;
        if (picWidth > reqWidth || picHeight > reqHeight) {
            int halfPicWidth = picWidth / 2;
            int halfPicHeight = picHeight / 2;
            while (true) {
                if (halfPicWidth / sampleSize <= reqWidth && halfPicHeight / sampleSize <= reqHeight) {
                    break;
                }
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }

    public static int getScreenWidth(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9240, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            DisplayMetrics outMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getScreenHeight(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9241, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            DisplayMetrics outMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getStatusHeight(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9242, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(clazz.getField("status_bar_height").get(clazz.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Bitmap snapShotWithStatusBar(Activity activity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9243, new Class[]{Activity.class}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bp = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, getScreenWidth(activity), getScreenHeight(activity));
        view.destroyDrawingCache();
        return bp;
    }

    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 9244, new Class[]{Activity.class}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, getScreenWidth(activity), getScreenHeight(activity) - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    public static String getFloatNum(float num) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(num)}, (Object) null, changeQuickRedirect, true, 9245, new Class[]{Float.TYPE}, String.class);
        return proxy.isSupported ? (String) proxy.result : decimalFormat.format((double) num);
    }

    public static byte[] getBytesByBitmap(Bitmap bitmap) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap}, (Object) null, changeQuickRedirect, true, 9246, new Class[]{Bitmap.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap ImageCropWithRect(Bitmap bitmap, int i) {
        int imgSize;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap, new Integer(i)}, (Object) null, changeQuickRedirect, true, 9247, new Class[]{Bitmap.class, Integer.TYPE}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        Bitmap bitmap2 = bitmap;
        int dy = i;
        if (bitmap2.getWidth() > bitmap2.getHeight()) {
            imgSize = bitmap2.getHeight();
        } else {
            imgSize = bitmap2.getWidth();
        }
        if (dy <= 0) {
            return createScaledBitmap(Bitmap.createBitmap(bitmap2, 0, 0, imgSize, imgSize, (Matrix) null, true), 480, 480);
        }
        return createScaledBitmap(Bitmap.createBitmap(bitmap2, 0, dy / 2, imgSize, imgSize, (Matrix) null, true), 480, 480);
    }

    public static Bitmap nv21ToBitmap(byte[] bytes, int w, int h, int imageFormat) {
        Object[] objArr = {bytes, new Integer(w), new Integer(h), new Integer(imageFormat)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9248, new Class[]{byte[].class, cls, cls, cls}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        YuvImage yuvImage = new YuvImage(bytes, imageFormat, w, h, (int[]) null);
        ByteArrayOutputStream os = new ByteArrayOutputStream(bytes.length);
        yuvImage.compressToJpeg(new Rect(0, 0, w, h), 100, os);
        byte[] tmp = os.toByteArray();
        return BitmapFactory.decodeByteArray(tmp, 0, tmp.length);
    }

    public static byte[] bitmapToNV21(int inputWidth, int inputHeight, Bitmap bitmap) {
        Object[] objArr = {new Integer(inputWidth), new Integer(inputHeight), bitmap};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 9249, new Class[]{cls, cls, Bitmap.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        Bitmap scaled = bitmap;
        int[] argb = new int[(inputWidth * inputHeight)];
        scaled.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);
        byte[] yuv = new byte[(inputWidth * inputHeight * 2)];
        encodeYUV420SP(yuv, argb, inputWidth, inputHeight);
        return yuv;
    }

    private static void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
        int i;
        int i2 = width;
        int i3 = height;
        int yIndex = 0;
        int uvIndex = i2 * i3;
        int index = 0;
        for (int j = 0; j < i3; j++) {
            int i4 = 0;
            while (i4 < i2) {
                int i5 = (argb[index] & ViewCompat.MEASURED_STATE_MASK) >> 24;
                int R = (argb[index] & 16711680) >> 16;
                int G = (argb[index] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                int i6 = 255;
                int B = (argb[index] & 255) >> 0;
                int Y = (((((R * 66) + (G * NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM)) + (B * 25)) + 128) >> 8) + 16;
                int U = (((((R * -38) - (G * 74)) + (B * 112)) + 128) >> 8) + 128;
                int V = (((((R * 112) - (G * 94)) - (B * 18)) + 128) >> 8) + 128;
                int yIndex2 = yIndex + 1;
                if (Y < 0) {
                    i6 = 0;
                } else if (Y <= 255) {
                    i6 = Y;
                }
                yuv420sp[yIndex] = (byte) i6;
                if (j % 2 == 0 && index % 2 == 0) {
                    int uvIndex2 = uvIndex + 1;
                    yuv420sp[uvIndex] = (byte) (V < 0 ? 0 : V > 255 ? 255 : V);
                    uvIndex = uvIndex2 + 1;
                    if (U < 0) {
                        i = 0;
                    } else {
                        i = 255;
                        if (U <= 255) {
                            i = U;
                        }
                    }
                    yuv420sp[uvIndex2] = (byte) i;
                }
                index++;
                i4++;
                yIndex = yIndex2;
            }
        }
    }

    public static Bitmap quality(Bitmap bitmap, int reqSize) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap, new Integer(reqSize)}, (Object) null, changeQuickRedirect, true, 9250, new Class[]{Bitmap.class, Integer.TYPE}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 95;
        while (baos.toByteArray().length / 1024 > reqSize) {
            baos.reset();
            boolean compress = bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            if (options <= 5) {
                break;
            }
            options -= 5;
        }
        return BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
    }

    public static Bitmap samplingRate(byte[] bits, int width, int height) {
        Object[] objArr = {bits, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 9251, new Class[]{byte[].class, cls, cls}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeByteArray(bits, 0, bits.length, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bits, 0, bits.length, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;
        int inSampleSize = 1;
        if (originalHeight > reqHeight || originalWidth > reqHeight) {
            int halfHeight = originalHeight / 2;
            int halfWidth = originalWidth / 2;
            while (halfWidth / inSampleSize >= reqWidth && halfHeight / inSampleSize >= reqHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap martix(Bitmap bitmap, float f) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap, new Float(f)}, (Object) null, changeQuickRedirect, true, 9252, new Class[]{Bitmap.class, Float.TYPE}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        Bitmap bit = bitmap;
        float scale = f;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
    }

    public static Bitmap createScaledBitmap(Bitmap bit, int width, int height) {
        Object[] objArr = {bit, new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9253, new Class[]{Bitmap.class, cls, cls}, Bitmap.class);
        return proxy.isSupported ? (Bitmap) proxy.result : Bitmap.createScaledBitmap(bit, width, height, true);
    }

    public void getLastImage(String lastImagePath, GetImageResponse listener) {
        if (!PatchProxy.proxy(new Object[]{lastImagePath, listener}, this, changeQuickRedirect, false, 9254, new Class[]{String.class, GetImageResponse.class}, Void.TYPE).isSupported) {
            getImageListener = listener;
            new GetBitmapTask().executeOnExecutor(singleThreadPool, new String[]{lastImagePath});
        }
    }

    public static class GetBitmapTask extends AsyncTask<String, Void, Void> {
        public static ChangeQuickRedirect changeQuickRedirect;
        private Bitmap bitmap = null;

        GetBitmapTask() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 9258, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : doInBackground((String[]) objArr);
        }

        public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9257, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onPostExecute((Void) obj);
            }
        }

        public Void doInBackground(String... paths) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{paths}, this, changeQuickRedirect, false, 9255, new Class[]{String[].class}, Void.class);
            if (proxy.isSupported) {
                return (Void) proxy.result;
            }
            a.c("getLastImage:" + paths[0], new Object[0]);
            if (TextUtils.isEmpty(paths[0])) {
                return null;
            }
            try {
                this.bitmap = BitmapFactory.decodeFile(paths[0]);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(Void voidR) {
            if (!PatchProxy.proxy(new Object[]{voidR}, this, changeQuickRedirect, false, 9256, new Class[]{Void.class}, Void.TYPE).isSupported) {
                if (BitmapUtils.getImageListener != null) {
                    BitmapUtils.getImageListener.onResponse(this.bitmap);
                }
            }
        }
    }
}
