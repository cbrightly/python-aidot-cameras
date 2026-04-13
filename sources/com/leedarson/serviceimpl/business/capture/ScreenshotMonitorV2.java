package com.leedarson.serviceimpl.business.capture;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import com.leedarson.bean.H5ActionName;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;

public class ScreenshotMonitorV2 {
    public static final String EXTRA_FILE_PATH = "screenshot_file_path";
    public static final String TAG = ScreenshotMonitorV2.class.getSimpleName();
    public static ChangeQuickRedirect changeQuickRedirect;
    private static Context mContext;
    private static ScreenshotMonitorV2 sMonitor;
    /* access modifiers changed from: private */
    public final Uri EXTERNAL_CONTENT_URI;
    /* access modifiers changed from: private */
    public final List<String> FILTERS;
    /* access modifiers changed from: private */
    public final Handler H = new Handler();
    /* access modifiers changed from: private */
    public ContentResolver mContentResolver;
    private ContentObserver mExternalObserver;
    /* access modifiers changed from: private */
    public Watcher mWatcher;

    private ScreenshotMonitorV2(Context context) {
        ArrayList arrayList = new ArrayList();
        this.FILTERS = arrayList;
        try {
            arrayList.add("screenshots");
            arrayList.add(H5ActionName.ACTION_SCREENSHOT);
            arrayList.add("screen_shot");
            arrayList.add("screen-shot");
            arrayList.add("screen shot");
            arrayList.add("screencapture");
            arrayList.add("screen_capture");
            arrayList.add("screen-capture");
            arrayList.add("screen capture");
            arrayList.add("screencap");
            arrayList.add("screen_cap");
            arrayList.add("screen cap");
            arrayList.add(new String("截屏".getBytes(), "UTF-8"));
        } catch (Exception e) {
        }
        this.EXTERNAL_CONTENT_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.mContentResolver = context.getContentResolver();
            HandlerThread thread = new HandlerThread("ScreenshotObserver");
            thread.start();
            this.mExternalObserver = new MediaContentObserver(new Handler(thread.getLooper()));
        }
    }

    public static ScreenshotMonitorV2 get(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7155, new Class[]{Context.class}, ScreenshotMonitorV2.class);
        if (proxy.isSupported) {
            return (ScreenshotMonitorV2) proxy.result;
        }
        if (sMonitor == null) {
            synchronized (ScreenshotMonitorV2.class) {
                if (sMonitor == null) {
                    sMonitor = new ScreenshotMonitorV2(context);
                    mContext = context;
                }
            }
        }
        return sMonitor;
    }

    public void startWatching() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7156, new Class[0], Void.TYPE).isSupported) {
            if (this.mContentResolver != null && this.mExternalObserver != null) {
                String str = TAG;
                Log.i(str, "start watching on: " + this.EXTERNAL_CONTENT_URI.getPath());
                this.mContentResolver.registerContentObserver(this.EXTERNAL_CONTENT_URI, true, this.mExternalObserver);
                this.mContentResolver.registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, true, this.mExternalObserver);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void stopWatching() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 7157(0x1bf5, float:1.0029E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.content.ContentResolver r1 = r0.mContentResolver
            if (r1 == 0) goto L_0x0022
            android.database.ContentObserver r2 = r0.mExternalObserver
            if (r2 == 0) goto L_0x0022
            r1.unregisterContentObserver(r2)
        L_0x0022:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.capture.ScreenshotMonitorV2.stopWatching():void");
    }

    public void free() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7158, new Class[0], Void.TYPE).isSupported) {
            stopWatching();
            this.H.removeCallbacksAndMessages((Object) null);
            this.mContentResolver = null;
            this.mExternalObserver = null;
            this.mWatcher = null;
            sMonitor = null;
        }
    }

    public void register(Watcher watcher) {
        if (!PatchProxy.proxy(new Object[]{watcher}, this, changeQuickRedirect, false, 7159, new Class[]{Watcher.class}, Void.TYPE).isSupported) {
            this.mWatcher = watcher;
            String str = TAG;
            Log.i(str, "register watcher: " + watcher.getClass().getSimpleName());
        }
    }

    public final class MediaContentObserver extends ContentObserver {
        public static ChangeQuickRedirect changeQuickRedirect;
        private final String ORDER = "date_added desc limit 1";
        private final String[] PROJECTION = {"_data", "date_added"};

        public MediaContentObserver(Handler handler) {
            super(handler);
        }

        public void onChange(boolean z) {
            Object[] objArr = {new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7160, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                Log.i(ScreenshotMonitorV2.TAG, "ContentObserver onChange nothing (sdk < 16)");
                handleContentChanged((Uri) null);
            }
        }

        public void onChange(boolean z, Uri uri) {
            Object[] objArr = {new Byte(z ? (byte) 1 : 0), uri};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7161, new Class[]{Boolean.TYPE, Uri.class}, Void.TYPE).isSupported) {
                if (isSingleImageFile(uri)) {
                    String str = ScreenshotMonitorV2.TAG;
                    Log.i(str, "ContentObserver onChange " + uri.getPath());
                    handleContentChanged(uri);
                }
            }
        }

        private boolean isSingleImageFile(Uri uri) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{uri}, this, changeQuickRedirect, false, 7162, new Class[]{Uri.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (uri != null) {
                String uri2 = uri.toString();
                if (uri2.matches(ScreenshotMonitorV2.this.EXTERNAL_CONTENT_URI.toString() + "/*|/[0-9]+")) {
                    return true;
                }
            }
            return false;
        }

        private void handleContentChanged(Uri uri) {
            if (!PatchProxy.proxy(new Object[]{uri}, this, changeQuickRedirect, false, 7163, new Class[]{Uri.class}, Void.TYPE).isSupported) {
                Uri uri2 = uri;
                Cursor cursor = null;
                try {
                    String str = ScreenshotMonitorV2.TAG;
                    Log.i(str, "handleContentChanged: uri path=" + uri2.getPath());
                    cursor = ScreenshotMonitorV2.this.mContentResolver.query(uri2, this.PROJECTION, (String) null, (String[]) null, "date_added desc limit 1");
                    Log.i(str, "handleContentChanged: cursor.getCount()=" + cursor.getCount());
                    if (cursor.moveToFirst()) {
                        final String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                        Log.i(str, "ContentObserver imagePath: " + imagePath);
                        if (Math.abs((System.currentTimeMillis() / 1000) - cursor.getLong(cursor.getColumnIndex("date_added"))) <= 3 && isScreenshotPath(imagePath)) {
                            ScreenshotMonitorV2.this.H.postDelayed(new Runnable() {
                                public static ChangeQuickRedirect changeQuickRedirect;

                                public void run() {
                                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7165, new Class[0], Void.TYPE).isSupported) {
                                        if (ScreenshotMonitorV2.this.mWatcher != null) {
                                            ScreenshotMonitorV2.this.mWatcher.onWatch(imagePath);
                                        }
                                    }
                                }
                            }, 0);
                        }
                    }
                    if (cursor.isClosed()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (cursor == null || cursor.isClosed()) {
                        return;
                    }
                } catch (Throwable th) {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    throw th;
                }
                cursor.close();
            }
        }

        private boolean isScreenshotPath(String path) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 7164, new Class[]{String.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            boolean filtered = false;
            for (String filter : ScreenshotMonitorV2.this.FILTERS) {
                filtered = path.toLowerCase().contains(filter);
                if (filtered) {
                    return true;
                }
            }
            return filtered;
        }
    }
}
