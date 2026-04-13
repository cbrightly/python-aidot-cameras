package com.amazonaws.event;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProgressListenerChain implements ProgressListener {
    private static final Log log = LogFactory.getLog(ProgressListenerChain.class);
    private final List<ProgressListener> listeners;
    private final ProgressEventFilter progressEventFilter;

    public interface ProgressEventFilter {
        ProgressEvent filter(ProgressEvent progressEvent);
    }

    public ProgressListenerChain(ProgressListener... listeners2) {
        this((ProgressEventFilter) null, listeners2);
    }

    public ProgressListenerChain(ProgressEventFilter progressEventFilter2, ProgressListener... listeners2) {
        this.listeners = new CopyOnWriteArrayList();
        if (listeners2 != null) {
            for (ProgressListener listener : listeners2) {
                addProgressListener(listener);
            }
            this.progressEventFilter = progressEventFilter2;
            return;
        }
        throw new IllegalArgumentException("Progress Listeners cannot be null.");
    }

    public synchronized void addProgressListener(ProgressListener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    public synchronized void removeProgressListener(ProgressListener listener) {
        if (listener != null) {
            this.listeners.remove(listener);
        }
    }

    /* access modifiers changed from: protected */
    public List<ProgressListener> getListeners() {
        return this.listeners;
    }

    public void progressChanged(ProgressEvent progressEvent) {
        ProgressEvent filteredEvent = progressEvent;
        ProgressEventFilter progressEventFilter2 = this.progressEventFilter;
        if (progressEventFilter2 == null || (filteredEvent = progressEventFilter2.filter(progressEvent)) != null) {
            for (ProgressListener listener : this.listeners) {
                try {
                    listener.progressChanged(filteredEvent);
                } catch (RuntimeException e) {
                    log.warn("Couldn't update progress listener", e);
                }
            }
        }
    }
}
