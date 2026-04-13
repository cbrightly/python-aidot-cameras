package com.sensorsdata.analytics.android.sdk.visual.snap;

import android.os.Looper;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UIThreadSet<T> {
    private Set<T> mSet = new HashSet();

    public void add(T item) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            this.mSet.add(item);
            return;
        }
        throw new RuntimeException("Can't add an activity when not on the UI thread");
    }

    public void remove(T item) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            this.mSet.remove(item);
            return;
        }
        throw new RuntimeException("Can't remove an activity when not on the UI thread");
    }

    public Set<T> getAll() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return Collections.unmodifiableSet(this.mSet);
        }
        throw new RuntimeException("Can't remove an activity when not on the UI thread");
    }

    public boolean isEmpty() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return this.mSet.isEmpty();
        }
        throw new RuntimeException("Can't check isEmpty() when not on the UI thread");
    }
}
