package com.didichuxing.doraemonkit.kit.crash;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.datapick.DataPickManager;
import com.didichuxing.doraemonkit.util.CacheUtils;
import com.didichuxing.doraemonkit.util.FileUtil;
import java.io.File;
import java.io.Serializable;
import java.lang.Thread;
import java.util.Date;
import meshsdk.cache.CacheHandler;

public class CrashCaptureManager implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashCaptureManager";
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public final Thread.UncaughtExceptionHandler mDefaultHandler;
    private final Handler mHandler;

    private CrashCaptureManager() {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static final CrashCaptureManager INSTANCE = new CrashCaptureManager();

        private Holder() {
        }
    }

    public static CrashCaptureManager getInstance() {
        return Holder.INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void start() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void stop() {
        Thread.setDefaultUncaughtExceptionHandler(this.mDefaultHandler);
    }

    public void uncaughtException(final Thread t, final Throwable e) {
        CacheUtils.saveObject((Serializable) Log.getStackTraceString(e), getCrashCacheFile());
        DataPickManager.getInstance().saveData2Local();
        post(new Runnable() {
            public void run() {
                Toast.makeText(CrashCaptureManager.this.mContext, CrashCaptureManager.this.mContext.getString(R.string.dk_crash_capture_tips), 0).show();
            }
        });
        postDelay(new Runnable() {
            public void run() {
                if (CrashCaptureManager.this.mDefaultHandler != null) {
                    CrashCaptureManager.this.mDefaultHandler.uncaughtException(t, e);
                }
            }
        }, CacheHandler.delayTime);
    }

    private void post(Runnable r) {
        this.mHandler.post(r);
    }

    private void postDelay(Runnable r, long delayMillis) {
        this.mHandler.postDelayed(r, delayMillis);
    }

    public File getCrashCacheDir() {
        File dir = new File(this.mContext.getCacheDir() + File.separator + "crash");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    private File getCrashCacheFile() {
        String fileName = new Date().toString();
        return new File(getCrashCacheDir() + File.separator + fileName);
    }

    public void clearCacheHistory() {
        FileUtil.deleteDirectory(getCrashCacheDir());
    }
}
