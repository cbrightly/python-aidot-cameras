package org.glassfish.grizzly.http.util;

import java.io.Serializable;

public final class TimeStamp implements Serializable {
    private long creationTime = 0;
    int id = -1;
    private boolean isNew = true;
    private boolean isValid = false;
    private long lastAccessedTime = 0;
    private long maxInactiveInterval = -1;
    MessageBytes name;
    Object parent;
    private long thisAccessedTime = 0;

    public void touch(long time) {
        this.lastAccessedTime = this.thisAccessedTime;
        this.thisAccessedTime = time;
        this.isNew = false;
    }

    public MessageBytes getName() {
        if (this.name == null) {
            this.name = MessageBytes.newInstance();
        }
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public void setParent(Object o) {
        this.parent = o;
    }

    public Object getParent() {
        return this.parent;
    }

    public void setCreationTime(long time) {
        this.creationTime = time;
        this.lastAccessedTime = time;
        this.thisAccessedTime = time;
    }

    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    public long getMaxInactiveInterval() {
        return this.maxInactiveInterval;
    }

    public void setMaxInactiveInterval(long interval) {
        this.maxInactiveInterval = interval;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public void setValid(boolean isValid2) {
        this.isValid = isValid2;
    }

    public boolean isNew() {
        return this.isNew;
    }

    public void setNew(boolean isNew2) {
        this.isNew = isNew2;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public void recycle() {
        this.creationTime = 0;
        this.lastAccessedTime = 0;
        this.maxInactiveInterval = -1;
        this.isNew = true;
        this.isValid = false;
        this.id = -1;
        MessageBytes messageBytes = this.name;
        if (messageBytes != null) {
            messageBytes.recycle();
        }
    }
}
