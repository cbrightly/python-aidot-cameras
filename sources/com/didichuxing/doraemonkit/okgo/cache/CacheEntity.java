package com.didichuxing.doraemonkit.okgo.cache;

import android.content.ContentValues;
import android.database.Cursor;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.didichuxing.doraemonkit.okgo.utils.IOUtils;
import java.io.Serializable;

public class CacheEntity<T> implements Serializable {
    public static final long CACHE_NEVER_EXPIRE = -1;
    public static final String DATA = "data";
    public static final String HEAD = "head";
    public static final String KEY = "key";
    public static final String LOCAL_EXPIRE = "localExpire";
    private static final long serialVersionUID = -4337711009801627866L;
    private T data;
    private boolean isExpire;
    private String key;
    private long localExpire;
    private HttpHeaders responseHeaders;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    public void setResponseHeaders(HttpHeaders responseHeaders2) {
        this.responseHeaders = responseHeaders2;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data2) {
        this.data = data2;
    }

    public long getLocalExpire() {
        return this.localExpire;
    }

    public void setLocalExpire(long localExpire2) {
        this.localExpire = localExpire2;
    }

    public boolean isExpire() {
        return this.isExpire;
    }

    public void setExpire(boolean expire) {
        this.isExpire = expire;
    }

    public boolean checkExpire(CacheMode cacheMode, long cacheTime, long baseTime) {
        if (cacheMode == CacheMode.DEFAULT) {
            return getLocalExpire() < baseTime;
        }
        if (cacheTime == -1) {
            return false;
        }
        if (getLocalExpire() + cacheTime < baseTime) {
            return true;
        }
        return false;
    }

    public static <T> ContentValues getContentValues(CacheEntity<T> cacheEntity) {
        ContentValues values = new ContentValues();
        values.put(KEY, cacheEntity.getKey());
        values.put(LOCAL_EXPIRE, Long.valueOf(cacheEntity.getLocalExpire()));
        values.put(HEAD, IOUtils.toByteArray((Object) cacheEntity.getResponseHeaders()));
        values.put("data", IOUtils.toByteArray((Object) cacheEntity.getData()));
        return values;
    }

    public static <T> CacheEntity<T> parseCursorToBean(Cursor cursor) {
        CacheEntity<T> cacheEntity = new CacheEntity<>();
        cacheEntity.setKey(cursor.getString(cursor.getColumnIndex(KEY)));
        cacheEntity.setLocalExpire(cursor.getLong(cursor.getColumnIndex(LOCAL_EXPIRE)));
        cacheEntity.setResponseHeaders((HttpHeaders) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(HEAD))));
        cacheEntity.setData(IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex("data"))));
        return cacheEntity;
    }

    public String toString() {
        return "CacheEntity{key='" + this.key + '\'' + ", responseHeaders=" + this.responseHeaders + ", data=" + this.data + ", localExpire=" + this.localExpire + '}';
    }
}
