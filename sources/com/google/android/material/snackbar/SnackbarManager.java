package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;

public class SnackbarManager {
    private static final int LONG_DURATION_MS = 2750;
    static final int MSG_TIMEOUT = 0;
    private static final int SHORT_DURATION_MS = 1500;
    private static SnackbarManager snackbarManager;
    @Nullable
    private SnackbarRecord currentSnackbar;
    @NonNull
    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case 0:
                    SnackbarManager.this.handleTimeout((SnackbarRecord) message.obj);
                    return true;
                default:
                    return false;
            }
        }
    });
    @NonNull
    private final Object lock = new Object();
    @Nullable
    private SnackbarRecord nextSnackbar;

    public interface Callback {
        void dismiss(int i);

        void show();
    }

    static SnackbarManager getInstance() {
        if (snackbarManager == null) {
            snackbarManager = new SnackbarManager();
        }
        return snackbarManager;
    }

    private SnackbarManager() {
    }

    public void show(int duration, Callback callback) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback)) {
                SnackbarRecord snackbarRecord = this.currentSnackbar;
                snackbarRecord.duration = duration;
                this.handler.removeCallbacksAndMessages(snackbarRecord);
                scheduleTimeoutLocked(this.currentSnackbar);
                return;
            }
            if (isNextSnackbarLocked(callback)) {
                this.nextSnackbar.duration = duration;
            } else {
                this.nextSnackbar = new SnackbarRecord(duration, callback);
            }
            SnackbarRecord snackbarRecord2 = this.currentSnackbar;
            if (snackbarRecord2 == null || !cancelSnackbarLocked(snackbarRecord2, 4)) {
                this.currentSnackbar = null;
                showNextSnackbarLocked();
            }
        }
    }

    public void dismiss(Callback callback, int event) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback)) {
                cancelSnackbarLocked(this.currentSnackbar, event);
            } else if (isNextSnackbarLocked(callback)) {
                cancelSnackbarLocked(this.nextSnackbar, event);
            }
        }
    }

    public void onDismissed(Callback callback) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback)) {
                this.currentSnackbar = null;
                if (this.nextSnackbar != null) {
                    showNextSnackbarLocked();
                }
            }
        }
    }

    public void onShown(Callback callback) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback)) {
                scheduleTimeoutLocked(this.currentSnackbar);
            }
        }
    }

    public void pauseTimeout(Callback callback) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback)) {
                SnackbarRecord snackbarRecord = this.currentSnackbar;
                if (!snackbarRecord.paused) {
                    snackbarRecord.paused = true;
                    this.handler.removeCallbacksAndMessages(snackbarRecord);
                }
            }
        }
    }

    public void restoreTimeoutIfPaused(Callback callback) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback)) {
                SnackbarRecord snackbarRecord = this.currentSnackbar;
                if (snackbarRecord.paused) {
                    snackbarRecord.paused = false;
                    scheduleTimeoutLocked(snackbarRecord);
                }
            }
        }
    }

    public boolean isCurrent(Callback callback) {
        boolean isCurrentSnackbarLocked;
        synchronized (this.lock) {
            isCurrentSnackbarLocked = isCurrentSnackbarLocked(callback);
        }
        return isCurrentSnackbarLocked;
    }

    public boolean isCurrentOrNext(Callback callback) {
        boolean z;
        synchronized (this.lock) {
            if (!isCurrentSnackbarLocked(callback)) {
                if (!isNextSnackbarLocked(callback)) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public static class SnackbarRecord {
        @NonNull
        final WeakReference<Callback> callback;
        int duration;
        boolean paused;

        SnackbarRecord(int duration2, Callback callback2) {
            this.callback = new WeakReference<>(callback2);
            this.duration = duration2;
        }

        /* access modifiers changed from: package-private */
        public boolean isSnackbar(@Nullable Callback callback2) {
            return callback2 != null && this.callback.get() == callback2;
        }
    }

    private void showNextSnackbarLocked() {
        SnackbarRecord snackbarRecord = this.nextSnackbar;
        if (snackbarRecord != null) {
            this.currentSnackbar = snackbarRecord;
            this.nextSnackbar = null;
            Callback callback = (Callback) snackbarRecord.callback.get();
            if (callback != null) {
                callback.show();
            } else {
                this.currentSnackbar = null;
            }
        }
    }

    private boolean cancelSnackbarLocked(@NonNull SnackbarRecord record, int event) {
        Callback callback = (Callback) record.callback.get();
        if (callback == null) {
            return false;
        }
        this.handler.removeCallbacksAndMessages(record);
        callback.dismiss(event);
        return true;
    }

    private boolean isCurrentSnackbarLocked(Callback callback) {
        SnackbarRecord snackbarRecord = this.currentSnackbar;
        return snackbarRecord != null && snackbarRecord.isSnackbar(callback);
    }

    private boolean isNextSnackbarLocked(Callback callback) {
        SnackbarRecord snackbarRecord = this.nextSnackbar;
        return snackbarRecord != null && snackbarRecord.isSnackbar(callback);
    }

    private void scheduleTimeoutLocked(@NonNull SnackbarRecord r) {
        int i = r.duration;
        if (i != -2) {
            int durationMs = LONG_DURATION_MS;
            if (i > 0) {
                durationMs = r.duration;
            } else if (i == -1) {
                durationMs = 1500;
            }
            this.handler.removeCallbacksAndMessages(r);
            Handler handler2 = this.handler;
            handler2.sendMessageDelayed(Message.obtain(handler2, 0, r), (long) durationMs);
        }
    }

    /* access modifiers changed from: package-private */
    public void handleTimeout(@NonNull SnackbarRecord record) {
        synchronized (this.lock) {
            if (this.currentSnackbar == record || this.nextSnackbar == record) {
                cancelSnackbarLocked(record, 2);
            }
        }
    }
}
