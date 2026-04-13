package com.amazonaws.event;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.InputStream;

public class ProgressReportingInputStream extends SdkFilterInputStream {
    private static final int BYTES_IN_KB = 1024;
    private static final int THRESHOLD_IN_KB = 8;
    private boolean fireCompletedEvent;
    private final ProgressListenerCallbackExecutor listenerCallbackExecutor;
    private int notificationThreshold = 8192;
    private int unnotifiedByteCount;

    public ProgressReportingInputStream(InputStream in, ProgressListenerCallbackExecutor listenerCallbackExecutor2) {
        super(in);
        this.listenerCallbackExecutor = listenerCallbackExecutor2;
    }

    public void setNotificationThreshold(int threshold) {
        this.notificationThreshold = threshold * 1024;
    }

    public void setFireCompletedEvent(boolean fireCompletedEvent2) {
        this.fireCompletedEvent = fireCompletedEvent2;
    }

    public boolean getFireCompletedEvent() {
        return this.fireCompletedEvent;
    }

    public int read() {
        int data = super.read();
        if (data == -1) {
            notifyCompleted();
        } else {
            notify(1);
        }
        return data;
    }

    public void reset() {
        super.reset();
        ProgressEvent event = new ProgressEvent((long) this.unnotifiedByteCount);
        event.setEventCode(32);
        this.listenerCallbackExecutor.progressChanged(event);
        this.unnotifiedByteCount = 0;
    }

    public int read(byte[] b, int off, int len) {
        int bytesRead = super.read(b, off, len);
        if (bytesRead == -1) {
            notifyCompleted();
        }
        if (bytesRead != -1) {
            notify(bytesRead);
        }
        return bytesRead;
    }

    public void close() {
        int i = this.unnotifiedByteCount;
        if (i > 0) {
            this.listenerCallbackExecutor.progressChanged(new ProgressEvent((long) i));
            this.unnotifiedByteCount = 0;
        }
        super.close();
    }

    private void notifyCompleted() {
        if (this.fireCompletedEvent) {
            ProgressEvent event = new ProgressEvent((long) this.unnotifiedByteCount);
            event.setEventCode(4);
            this.unnotifiedByteCount = 0;
            this.listenerCallbackExecutor.progressChanged(event);
        }
    }

    private void notify(int bytesRead) {
        int i = this.unnotifiedByteCount + bytesRead;
        this.unnotifiedByteCount = i;
        if (i >= this.notificationThreshold) {
            this.listenerCallbackExecutor.progressChanged(new ProgressEvent((long) i));
            this.unnotifiedByteCount = 0;
        }
    }
}
