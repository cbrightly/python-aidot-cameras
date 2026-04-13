package org.glassfish.grizzly.http.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Session {
    private final ConcurrentMap<String, Object> attributes;
    private final long creationTime;
    private String id;
    private boolean isNew;
    private boolean isValid;
    private long sessionTimeout;
    private long timestamp;

    public Session() {
        this((String) null);
    }

    public Session(String id2) {
        this.attributes = new ConcurrentHashMap();
        this.id = null;
        this.isValid = true;
        this.isNew = true;
        this.sessionTimeout = -1;
        this.timestamp = -1;
        this.id = id2;
        long currentTimeMillis = System.currentTimeMillis();
        this.timestamp = currentTimeMillis;
        this.creationTime = currentTimeMillis;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public void setValid(boolean isValid2) {
        this.isValid = isValid2;
        if (!isValid2) {
            this.timestamp = -1;
        }
    }

    public boolean isNew() {
        return this.isNew;
    }

    public String getIdInternal() {
        return this.id;
    }

    /* access modifiers changed from: protected */
    public void setIdInternal(String id2) {
        this.id = id2;
    }

    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public Object removeAttribute(String key) {
        return this.attributes.remove(key);
    }

    public ConcurrentMap<String, Object> attributes() {
        return this.attributes;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public long getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout2) {
        this.sessionTimeout = sessionTimeout2;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp2) {
        this.timestamp = timestamp2;
    }

    public long access() {
        long localTimeStamp = System.currentTimeMillis();
        this.timestamp = localTimeStamp;
        this.isNew = false;
        return localTimeStamp;
    }
}
