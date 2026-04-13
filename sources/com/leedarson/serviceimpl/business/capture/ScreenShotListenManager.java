package com.leedarson.serviceimpl.business.capture;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.leedarson.bean.H5ActionName;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;

public class ScreenShotListenManager {
    private static final String[] KEYWORDS = {H5ActionName.ACTION_SCREENSHOT, "screen_shot", "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", "screen-cap", "screen cap"};
    private static final String[] MEDIA_PROJECTIONS = {"_data", "datetaken"};
    private static final String[] MEDIA_PROJECTIONS_API_16 = {"_data", "datetaken", "width", "height"};
    private static final String TAG = "ScreenShotListenManager";
    public static ChangeQuickRedirect changeQuickRedirect;
    private static final List<String> sHasCallbackPaths = new ArrayList();
    private static Point sScreenRealSize;
    private Context mContext;
    private MediaContentObserver mExternalObserver;
    private MediaContentObserver mInternalObserver;
    private OnScreenShotListener mListener;
    private long mStartListenTime;
    private final Handler mUiHandler = new Handler(Looper.getMainLooper());

    public interface OnScreenShotListener {
        void onShot(String str);
    }

    static /* synthetic */ void access$000(ScreenShotListenManager x0, Uri x1) {
        Class[] clsArr = {ScreenShotListenManager.class, Uri.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 7139, clsArr, Void.TYPE).isSupported) {
            x0.handleMediaContentChange(x1);
        }
    }

    private ScreenShotListenManager(Context context) {
        if (context != null) {
            this.mContext = context;
            if (sScreenRealSize == null) {
                Point realScreenSize = getRealScreenSize();
                sScreenRealSize = realScreenSize;
                if (realScreenSize != null) {
                    Log.d(TAG, "Screen Real Size: " + sScreenRealSize.x + " * " + sScreenRealSize.y);
                    return;
                }
                Log.w(TAG, "Get screen real size failed.");
                return;
            }
            return;
        }
        throw new IllegalArgumentException("The context must not be null.");
    }

    public static ScreenShotListenManager newInstance(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7128, new Class[]{Context.class}, ScreenShotListenManager.class);
        if (proxy.isSupported) {
            return (ScreenShotListenManager) proxy.result;
        }
        assertInMainThread();
        return new ScreenShotListenManager(context);
    }

    public void startListen() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7129, new Class[0], Void.TYPE).isSupported) {
            assertInMainThread();
            this.mStartListenTime = System.currentTimeMillis();
            this.mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, this.mUiHandler);
            this.mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.mUiHandler);
            this.mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, false, this.mInternalObserver);
            this.mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, this.mExternalObserver);
        }
    }

    public void stopListen() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7130, new Class[0], Void.TYPE).isSupported) {
            assertInMainThread();
            if (this.mInternalObserver != null) {
                try {
                    this.mContext.getContentResolver().unregisterContentObserver(this.mInternalObserver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.mInternalObserver = null;
            }
            if (this.mExternalObserver != null) {
                try {
                    this.mContext.getContentResolver().unregisterContentObserver(this.mExternalObserver);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.mExternalObserver = null;
            }
            this.mStartListenTime = 0;
            this.mListener = null;
        }
    }

    private void handleMediaContentChange(Uri uri) {
        int height;
        int width;
        if (!PatchProxy.proxy(new Object[]{uri}, this, changeQuickRedirect, false, 7131, new Class[]{Uri.class}, Void.TYPE).isSupported) {
            Uri contentUri = uri;
            Log.e(TAG, "handleMediaContentChange contentUri=" + contentUri.getPath());
            Cursor cursor = null;
            try {
                ContentResolver contentResolver = this.mContext.getContentResolver();
                int i = Build.VERSION.SDK_INT;
                Cursor cursor2 = contentResolver.query(contentUri, i < 16 ? MEDIA_PROJECTIONS : MEDIA_PROJECTIONS_API_16, (String) null, (String[]) null, "date_added desc limit 1");
                if (cursor2 == null) {
                    try {
                        Log.e(TAG, "Deviant logic.");
                        if (cursor2 != null && !cursor2.isClosed()) {
                            cursor2.close();
                        }
                    } catch (Exception e) {
                        e = e;
                        cursor = cursor2;
                        try {
                            Log.i(TAG, "handleMediaContentChange: " + e.toString());
                            e.printStackTrace();
                            if (cursor != null && !cursor.isClosed()) {
                                cursor.close();
                            }
                            Cursor cursor3 = cursor;
                        } catch (Throwable th) {
                            th = th;
                            if (cursor != null && !cursor.isClosed()) {
                                cursor.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = cursor2;
                        cursor.close();
                        throw th;
                    }
                } else {
                    if (cursor2.moveToPosition(0)) {
                        Log.d(TAG, "Cursor moveToPosition（0）is true");
                    }
                    int dataIndex = cursor2.getColumnIndex("_data");
                    int dateTakenIndex = cursor2.getColumnIndex("datetaken");
                    int widthIndex = -1;
                    int heightIndex = -1;
                    if (i >= 16) {
                        widthIndex = cursor2.getColumnIndex("width");
                        heightIndex = cursor2.getColumnIndex("height");
                    }
                    String data = cursor2.getString(dataIndex);
                    long dateTaken = cursor2.getLong(dateTakenIndex);
                    Log.i(TAG, "handleMediaContentChange: dataIndex=" + dataIndex + " dateTakenIndex=" + dateTakenIndex + " data=" + data);
                    if (widthIndex < 0 || heightIndex < 0) {
                        Point size = getImageSize(data);
                        width = size.x;
                        height = size.y;
                    } else {
                        width = cursor2.getInt(widthIndex);
                        height = cursor2.getInt(heightIndex);
                    }
                    handleMediaRowData(data, dateTaken, width, height);
                    if (!cursor2.isClosed()) {
                        cursor2.close();
                    }
                }
            } catch (Exception e2) {
                e = e2;
                Log.i(TAG, "handleMediaContentChange: " + e.toString());
                e.printStackTrace();
                cursor.close();
                Cursor cursor32 = cursor;
            }
        }
    }

    private Point getImageSize(String imagePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{imagePath}, this, changeQuickRedirect, false, 7132, new Class[]{String.class}, Point.class);
        if (proxy.isSupported) {
            return (Point) proxy.result;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        return new Point(options.outWidth, options.outHeight);
    }

    private void handleMediaRowData(String data, long dateTaken, int width, int height) {
        Object[] objArr = {data, new Long(dateTaken), new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {String.class, Long.TYPE, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7133, clsArr, Void.TYPE).isSupported) {
            if (checkScreenShot(data, dateTaken, width, height)) {
                Log.d(TAG, "ScreenShot: path = " + data + "; size = " + width + " * " + height + "; date = " + dateTaken);
                if (this.mListener != null && !checkCallback(data)) {
                    this.mListener.onShot(data);
                    return;
                }
                return;
            }
            Log.w(TAG, "Media content changed, but not screenshot: path = " + data + "; size = " + width + " * " + height + "; date = " + dateTaken);
        }
    }

    private boolean checkScreenShot(String data, long dateTaken, int width, int height) {
        int i;
        Object[] objArr = {data, new Long(dateTaken), new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7134, new Class[]{String.class, Long.TYPE, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (dateTaken < this.mStartListenTime || System.currentTimeMillis() - dateTaken > 10000) {
            return false;
        }
        Point point = sScreenRealSize;
        if ((point != null && ((width > (i = point.x) || height > point.y) && (height > i || width > point.y))) || TextUtils.isEmpty(data)) {
            return false;
        }
        String data2 = data.toLowerCase();
        for (String keyWork : KEYWORDS) {
            if (data2.contains(keyWork)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCallback(String imagePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{imagePath}, this, changeQuickRedirect, false, 7135, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        List<String> list = sHasCallbackPaths;
        if (list.contains(imagePath)) {
            Log.d(TAG, "ScreenShot: imgPath has done; imagePath = " + imagePath);
            return true;
        }
        if (list.size() >= 20) {
            for (int i = 0; i < 5; i++) {
                sHasCallbackPaths.remove(0);
            }
        }
        sHasCallbackPaths.add(imagePath);
        return false;
    }

    private Point getRealScreenSize() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7136, new Class[0], Point.class);
        if (proxy.isSupported) {
            return (Point) proxy.result;
        }
        Point screenSize = null;
        try {
            screenSize = new Point();
            Display defaultDisplay = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= 17) {
                defaultDisplay.getRealSize(screenSize);
            } else {
                try {
                    screenSize.set(((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue(), ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue());
                } catch (Exception e) {
                    screenSize.set(defaultDisplay.getWidth(), defaultDisplay.getHeight());
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return screenSize;
    }

    private int dp2px(Context ctx, float dp) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ctx, new Float(dp)}, this, changeQuickRedirect, false, 7137, new Class[]{Context.class, Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (int) ((dp * ctx.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void setListener(OnScreenShotListener listener) {
        this.mListener = listener;
    }

    private static void assertInMainThread() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 7138, new Class[0], Void.TYPE).isSupported && Looper.myLooper() != Looper.getMainLooper()) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String methodMsg = null;
            if (elements != null && elements.length >= 4) {
                methodMsg = elements[3].toString();
            }
            throw new IllegalStateException("Call the method must be in main thread: " + methodMsg);
        }
    }

    public class MediaContentObserver extends ContentObserver {
        public static ChangeQuickRedirect changeQuickRedirect;
        private Uri mContentUri;

        public MediaContentObserver(Uri contentUri, Handler handler) {
            super(handler);
            this.mContentUri = contentUri;
        }

        public void onChange(boolean selfChange) {
            Object[] objArr = {new Byte(selfChange ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7140, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                super.onChange(selfChange);
                ScreenShotListenManager.access$000(ScreenShotListenManager.this, this.mContentUri);
            }
        }
    }
}
