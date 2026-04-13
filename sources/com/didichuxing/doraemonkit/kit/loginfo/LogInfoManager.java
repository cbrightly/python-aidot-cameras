package com.didichuxing.doraemonkit.kit.loginfo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import com.didichuxing.doraemonkit.kit.loginfo.reader.LogcatReader;
import com.didichuxing.doraemonkit.kit.loginfo.reader.LogcatReaderLoader;
import com.didichuxing.doraemonkit.util.ExecutorUtil;
import com.didichuxing.doraemonkit.util.LogHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LogInfoManager {
    private static final int MESSAGE_PUBLISH_LOG = 1001;
    private static final String TAG = "LogInfoManager";
    /* access modifiers changed from: private */
    public OnLogCatchListener mListener;
    private LogCatchRunnable mLogCatchTask;

    public interface OnLogCatchListener {
        void onLogCatch(List<LogLine> list);
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static LogInfoManager INSTANCE = new LogInfoManager();

        private Holder() {
        }
    }

    private LogInfoManager() {
    }

    public static LogInfoManager getInstance() {
        return Holder.INSTANCE;
    }

    public void start() {
        LogCatchRunnable logCatchRunnable = this.mLogCatchTask;
        if (logCatchRunnable != null) {
            logCatchRunnable.stop();
        }
        LogCatchRunnable logCatchRunnable2 = new LogCatchRunnable();
        this.mLogCatchTask = logCatchRunnable2;
        ExecutorUtil.execute(logCatchRunnable2);
    }

    public void stop() {
        LogCatchRunnable logCatchRunnable = this.mLogCatchTask;
        if (logCatchRunnable != null) {
            logCatchRunnable.stop();
        }
    }

    public void registerListener(OnLogCatchListener listener) {
        this.mListener = listener;
    }

    public void removeListener() {
        this.mListener = null;
    }

    public static class InternalHandler extends Handler {
        public InternalHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1001:
                    if (LogInfoManager.getInstance().mListener != null) {
                        LogInfoManager.getInstance().mListener.onLogCatch((List) msg.obj);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public static class LogCatchRunnable implements Runnable {
        private Handler internalHandler;
        private boolean isRunning;
        private int mPid;
        private LogcatReader mReader;

        private LogCatchRunnable() {
            this.isRunning = true;
            this.internalHandler = new InternalHandler(Looper.getMainLooper());
            this.mPid = Process.myPid();
        }

        public void run() {
            try {
                this.mReader = LogcatReaderLoader.create(true).loadReader();
                LinkedList<LogLine> initialLines = new LinkedList<>();
                while (true) {
                    String readLine = this.mReader.readLine();
                    String line = readLine;
                    if (readLine == null || !this.isRunning) {
                        this.mReader.killQuietly();
                    } else {
                        LogLine logLine = LogLine.newLogLine(line, false);
                        if (!this.mReader.readyToRecord()) {
                            if (logLine.getProcessId() == this.mPid) {
                                initialLines.add(logLine);
                            }
                            if (initialLines.size() > 10000) {
                                initialLines.removeFirst();
                            }
                        } else if (!initialLines.isEmpty()) {
                            if (logLine.getProcessId() == this.mPid) {
                                initialLines.add(logLine);
                            }
                            Message message = Message.obtain();
                            message.what = 1001;
                            message.obj = new ArrayList(initialLines);
                            this.internalHandler.sendMessage(message);
                            initialLines.clear();
                        } else if (logLine.getProcessId() == this.mPid) {
                            Message message2 = Message.obtain();
                            message2.what = 1001;
                            message2.obj = Collections.singletonList(logLine);
                            this.internalHandler.sendMessage(message2);
                        }
                    }
                }
                this.mReader.killQuietly();
            } catch (IOException e) {
                LogHelper.e(LogInfoManager.TAG, e.toString());
            }
        }

        public void stop() {
            this.isRunning = false;
        }
    }
}
