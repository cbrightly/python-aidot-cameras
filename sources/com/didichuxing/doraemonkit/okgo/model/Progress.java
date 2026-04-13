package com.didichuxing.doraemonkit.okgo.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.SystemClock;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;
import com.didichuxing.doraemonkit.okgo.request.base.Request;
import com.didichuxing.doraemonkit.okgo.utils.IOUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Progress implements Serializable {
    public static final String CURRENT_SIZE = "currentSize";
    public static final String DATE = "date";
    public static final int ERROR = 4;
    public static final String EXTRA1 = "extra1";
    public static final String EXTRA2 = "extra2";
    public static final String EXTRA3 = "extra3";
    public static final String FILE_NAME = "fileName";
    public static final String FILE_PATH = "filePath";
    public static final int FINISH = 5;
    public static final String FOLDER = "folder";
    public static final String FRACTION = "fraction";
    public static final int LOADING = 2;
    public static final int NONE = 0;
    public static final int PAUSE = 3;
    public static final String PRIORITY = "priority";
    public static final String REQUEST = "request";
    public static final String STATUS = "status";
    public static final String TAG = "tag";
    public static final String TOTAL_SIZE = "totalSize";
    public static final String URL = "url";
    public static final int WAITING = 1;
    private static final long serialVersionUID = 6353658567594109891L;
    public long currentSize;
    public long date = System.currentTimeMillis();
    public Throwable exception;
    public Serializable extra1;
    public Serializable extra2;
    public Serializable extra3;
    public String fileName;
    public String filePath;
    public String folder;
    public float fraction;
    private transient long lastRefreshTime = SystemClock.elapsedRealtime();
    public int priority = 0;
    public Request<?, ? extends Request> request;
    public transient long speed;
    private transient List<Long> speedBuffer = new ArrayList();
    public int status;
    public String tag;
    private transient long tempSize;
    public long totalSize = -1;
    public String url;

    public interface Action {
        void call(Progress progress);
    }

    public static Progress changeProgress(Progress progress, long writeSize, Action action) {
        return changeProgress(progress, writeSize, progress.totalSize, action);
    }

    public static Progress changeProgress(Progress progress, long writeSize, long totalSize2, Action action) {
        Progress progress2 = progress;
        long j = totalSize2;
        Action action2 = action;
        progress2.totalSize = j;
        progress2.currentSize += writeSize;
        progress2.tempSize += writeSize;
        long currentTime = SystemClock.elapsedRealtime();
        long j2 = progress2.lastRefreshTime;
        if ((currentTime - j2 >= DokitOkGo.REFRESH_TIME) || progress2.currentSize == j) {
            long diffTime = currentTime - j2;
            if (diffTime == 0) {
                diffTime = 1;
            }
            progress2.fraction = (((float) progress2.currentSize) * 1.0f) / ((float) j);
            progress2.speed = progress.bufferSpeed((progress2.tempSize * 1000) / diffTime);
            progress2.lastRefreshTime = currentTime;
            progress2.tempSize = 0;
            if (action2 != null) {
                action2.call(progress);
            }
        }
        return progress2;
    }

    private long bufferSpeed(long speed2) {
        this.speedBuffer.add(Long.valueOf(speed2));
        if (this.speedBuffer.size() > 10) {
            this.speedBuffer.remove(0);
        }
        long sum = 0;
        for (Long longValue : this.speedBuffer) {
            sum = (long) (((float) sum) + ((float) longValue.longValue()));
        }
        return sum / ((long) this.speedBuffer.size());
    }

    public void from(Progress progress) {
        this.totalSize = progress.totalSize;
        this.currentSize = progress.currentSize;
        this.fraction = progress.fraction;
        this.speed = progress.speed;
        this.lastRefreshTime = progress.lastRefreshTime;
        this.tempSize = progress.tempSize;
    }

    public static ContentValues buildContentValues(Progress progress) {
        ContentValues values = new ContentValues();
        values.put(TAG, progress.tag);
        values.put("url", progress.url);
        values.put(FOLDER, progress.folder);
        values.put(FILE_PATH, progress.filePath);
        values.put(FILE_NAME, progress.fileName);
        values.put(FRACTION, Float.valueOf(progress.fraction));
        values.put(TOTAL_SIZE, Long.valueOf(progress.totalSize));
        values.put(CURRENT_SIZE, Long.valueOf(progress.currentSize));
        values.put("status", Integer.valueOf(progress.status));
        values.put(PRIORITY, Integer.valueOf(progress.priority));
        values.put(DATE, Long.valueOf(progress.date));
        values.put(REQUEST, IOUtils.toByteArray((Object) progress.request));
        values.put(EXTRA1, IOUtils.toByteArray((Object) progress.extra1));
        values.put(EXTRA2, IOUtils.toByteArray((Object) progress.extra2));
        values.put(EXTRA3, IOUtils.toByteArray((Object) progress.extra3));
        return values;
    }

    public static ContentValues buildUpdateContentValues(Progress progress) {
        ContentValues values = new ContentValues();
        values.put(FRACTION, Float.valueOf(progress.fraction));
        values.put(TOTAL_SIZE, Long.valueOf(progress.totalSize));
        values.put(CURRENT_SIZE, Long.valueOf(progress.currentSize));
        values.put("status", Integer.valueOf(progress.status));
        values.put(PRIORITY, Integer.valueOf(progress.priority));
        values.put(DATE, Long.valueOf(progress.date));
        return values;
    }

    public static Progress parseCursorToBean(Cursor cursor) {
        Progress progress = new Progress();
        progress.tag = cursor.getString(cursor.getColumnIndex(TAG));
        progress.url = cursor.getString(cursor.getColumnIndex("url"));
        progress.folder = cursor.getString(cursor.getColumnIndex(FOLDER));
        progress.filePath = cursor.getString(cursor.getColumnIndex(FILE_PATH));
        progress.fileName = cursor.getString(cursor.getColumnIndex(FILE_NAME));
        progress.fraction = cursor.getFloat(cursor.getColumnIndex(FRACTION));
        progress.totalSize = cursor.getLong(cursor.getColumnIndex(TOTAL_SIZE));
        progress.currentSize = cursor.getLong(cursor.getColumnIndex(CURRENT_SIZE));
        progress.status = cursor.getInt(cursor.getColumnIndex("status"));
        progress.priority = cursor.getInt(cursor.getColumnIndex(PRIORITY));
        progress.date = cursor.getLong(cursor.getColumnIndex(DATE));
        progress.request = (Request) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(REQUEST)));
        progress.extra1 = (Serializable) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(EXTRA1)));
        progress.extra2 = (Serializable) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(EXTRA2)));
        progress.extra3 = (Serializable) IOUtils.toObject(cursor.getBlob(cursor.getColumnIndex(EXTRA3)));
        return progress;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Progress progress = (Progress) o;
        String str = this.tag;
        if (str != null) {
            return str.equals(progress.tag);
        }
        if (progress.tag == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.tag;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "Progress{fraction=" + this.fraction + ", totalSize=" + this.totalSize + ", currentSize=" + this.currentSize + ", speed=" + this.speed + ", status=" + this.status + ", priority=" + this.priority + ", folder=" + this.folder + ", filePath=" + this.filePath + ", fileName=" + this.fileName + ", tag=" + this.tag + ", url=" + this.url + '}';
    }
}
