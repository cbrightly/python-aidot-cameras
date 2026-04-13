package com.sensorsdata.analytics.android.sdk;

import android.os.Process;
import com.sensorsdata.analytics.android.sdk.internal.beans.EventType;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public class SensorsDataExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final int SLEEP_TIMEOUT_MS = 500;
    private static boolean isTrackCrash = false;
    private static final ArrayList<SAExceptionListener> sExceptionListeners = new ArrayList<>();
    private static SensorsDataExceptionHandler sInstance;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    public interface SAExceptionListener {
        void uncaughtException(Thread thread, Throwable th);
    }

    private SensorsDataExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    static synchronized void init() {
        synchronized (SensorsDataExceptionHandler.class) {
            if (sInstance == null) {
                sInstance = new SensorsDataExceptionHandler();
            }
        }
    }

    static void addExceptionListener(SAExceptionListener listener) {
        sExceptionListeners.add(listener);
    }

    static void enableAppCrash() {
        isTrackCrash = true;
    }

    public void uncaughtException(Thread t, Throwable e) {
        try {
            if (isTrackCrash) {
                try {
                    JSONObject messageProp = new JSONObject();
                    try {
                        Writer writer = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(writer);
                        e.printStackTrace(printWriter);
                        for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
                            cause.printStackTrace(printWriter);
                        }
                        printWriter.close();
                        messageProp.put("app_crashed_reason", (Object) writer.toString());
                    } catch (Exception ex) {
                        SALog.printStackTrace(ex);
                    }
                    SensorsDataAPI.sharedInstance().trackEvent(EventType.TRACK, "AppCrashed", messageProp, (String) null);
                } catch (Exception ex2) {
                    SALog.printStackTrace(ex2);
                }
            }
            Iterator<SAExceptionListener> it = sExceptionListeners.iterator();
            while (it.hasNext()) {
                try {
                    it.next().uncaughtException(t, e);
                } catch (Exception e1) {
                    SALog.printStackTrace(e1);
                }
            }
            SensorsDataAPI.sharedInstance().flush();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e12) {
                SALog.printStackTrace(e12);
            }
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.mDefaultExceptionHandler;
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(t, e);
            } else {
                killProcessAndExit();
            }
        } catch (Exception e2) {
        }
    }

    private void killProcessAndExit() {
        try {
            Process.killProcess(Process.myPid());
            System.exit(10);
        } catch (Exception e) {
        }
    }
}
