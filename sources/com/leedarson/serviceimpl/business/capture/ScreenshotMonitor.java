package com.leedarson.serviceimpl.business.capture;

import android.content.Context;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.Iterator;

public class ScreenshotMonitor {
    public static final String EXTRA_FILE_PATH = "screenshot_file_path";
    public static final String TAG = ScreenshotMonitor.class.getSimpleName();
    public static ChangeQuickRedirect changeQuickRedirect;
    private static Context mContext;
    private static ScreenshotMonitor sMonitor;
    /* access modifiers changed from: private */
    public final Handler H = new Handler();
    /* access modifiers changed from: private */
    public Watcher mWatcher;
    private final ArrayList<ScreenshotObserver> observerList;

    private ScreenshotMonitor(Context context) {
        ArrayList<ScreenshotObserver> arrayList = new ArrayList<>();
        this.observerList = arrayList;
        if (Environment.getExternalStorageState().equals("mounted")) {
            try {
                String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
                arrayList.add(new ScreenshotObserver(ROOT + Environment.DIRECTORY_PICTURES + "/Screenshots"));
                arrayList.add(new ScreenshotObserver(ROOT + Environment.DIRECTORY_PICTURES + "/screenshots"));
                arrayList.add(new ScreenshotObserver(ROOT + Environment.DIRECTORY_DCIM + "/Screenshots"));
                arrayList.add(new ScreenshotObserver(ROOT + Environment.DIRECTORY_DCIM + "/screenshots"));
                StringBuilder sb = new StringBuilder();
                sb.append(ROOT);
                sb.append(new String("截屏".getBytes(), "UTF-8"));
                arrayList.add(new ScreenshotObserver(sb.toString()));
            } catch (Exception e) {
            }
        }
    }

    public static ScreenshotMonitor get(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7147, new Class[]{Context.class}, ScreenshotMonitor.class);
        if (proxy.isSupported) {
            return (ScreenshotMonitor) proxy.result;
        }
        if (sMonitor == null) {
            synchronized (ScreenshotMonitor.class) {
                if (sMonitor == null) {
                    sMonitor = new ScreenshotMonitor(context);
                    mContext = context;
                }
            }
        }
        return sMonitor;
    }

    public void register(Watcher watcher) {
        if (!PatchProxy.proxy(new Object[]{watcher}, this, changeQuickRedirect, false, 7148, new Class[]{Watcher.class}, Void.TYPE).isSupported) {
            this.mWatcher = watcher;
            String str = TAG;
            Log.i(str, "register watcher: " + watcher.getClass().getSimpleName());
        }
    }

    public void startWatching() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7149, new Class[0], Void.TYPE).isSupported) {
            Iterator<ScreenshotObserver> it = this.observerList.iterator();
            while (it.hasNext()) {
                it.next().startWatching();
            }
        }
    }

    private void stopWatching() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7150, new Class[0], Void.TYPE).isSupported) {
            Iterator<ScreenshotObserver> it = this.observerList.iterator();
            while (it.hasNext()) {
                it.next().stopWatching();
            }
        }
    }

    public void free() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7151, new Class[0], Void.TYPE).isSupported) {
            stopWatching();
            this.H.removeCallbacksAndMessages((Object) null);
            this.mWatcher = null;
            sMonitor = null;
        }
    }

    public final class ScreenshotObserver extends FileObserver {
        public static ChangeQuickRedirect changeQuickRedirect;
        private String dir;

        public ScreenshotObserver(String path) {
            super(path, 8);
            this.dir = path;
            String str = ScreenshotMonitor.TAG;
            Log.i(str, "Observer on: " + this.dir);
        }

        public void onEvent(int event, String path) {
            if (!PatchProxy.proxy(new Object[]{new Integer(event), path}, this, changeQuickRedirect, false, 7152, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                String imagePath = this.dir + "/" + path;
                if (event == 8 || event == 16) {
                    Log.i(ScreenshotMonitor.TAG, "ScreenshotMonitor CREATE: " + imagePath);
                    onWatch(imagePath);
                } else if (event == 512) {
                    Log.i(ScreenshotMonitor.TAG, "ScreenshotMonitor DELETE: " + imagePath);
                }
            }
        }

        private void onWatch(final String path) {
            if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 7153, new Class[]{String.class}, Void.TYPE).isSupported) {
                ScreenshotMonitor.this.H.post(new Runnable() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void run() {
                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7154, new Class[0], Void.TYPE).isSupported) {
                            if (ScreenshotMonitor.this.mWatcher != null) {
                                ScreenshotMonitor.this.mWatcher.onWatch(path);
                            } else {
                                Log.i(ScreenshotMonitor.TAG, "ScreenshotMonitor Watcher is null");
                            }
                        }
                    }
                });
            }
        }
    }
}
