package com.telink.ble.mesh.foundation;

import android.os.Parcelable;
import com.meituan.robust.ChangeQuickRedirect;

public abstract class Event<T> implements Parcelable {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected Object sender;
    protected ThreadMode threadMode;
    protected T type;

    public enum ThreadMode {
        Background,
        Main,
        Default;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public Event() {
        this.threadMode = ThreadMode.Default;
    }

    public Event(Object sender2, T type2) {
        this(sender2, type2, ThreadMode.Default);
    }

    public Event(Object sender2, T type2, ThreadMode threadMode2) {
        this.threadMode = ThreadMode.Default;
        this.sender = sender2;
        this.type = type2;
        this.threadMode = threadMode2;
    }

    public Object getSender() {
        return this.sender;
    }

    public T getType() {
        return this.type;
    }

    public ThreadMode getThreadMode() {
        return this.threadMode;
    }

    public Event<T> setThreadMode(ThreadMode mode) {
        this.threadMode = mode;
        return this;
    }
}
