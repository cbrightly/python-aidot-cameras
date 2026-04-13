package com.didichuxing.doraemonkit.kit.network;

import android.os.Handler;
import android.os.Looper;
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkManager {
    public static final String APP_DATA_PICK_URL = "https://www.dokit.cn/pointData/addPointData";
    public static final String APP_DOCUMENT_URL = "https://xingyun.xiaojukeji.com/docs/dokit/#/TimeProfiler";
    public static final String APP_HEALTH_URL = "https://www.dokit.cn/healthCheck/addCheckData";
    public static final String APP_START_DATA_PICK_URL = "https://doraemon.xiaojukeji.com/uploadAppData";
    public static final String FILE_MANAGER_DOCUMENT_URL = "https://xingyun.xiaojukeji.com/docs/dokit/#/FileList";
    private static final int MAX_SIZE = 100;
    private static final String MOCK_DEBUG_DOMAIN = "https://mock.dokit.cn";
    public static final String MOCK_DOMAIN = "https://mock.dokit.cn";
    public static final String MOCK_HOST = "mock.dokit.cn";
    private static final String MOCK_HOST_DEBUG = "mock.dokit.cn";
    private static final String MOCK_HOST_RELEASE = "mock.dokit.cn";
    private static final String MOCK_RELEASE_DOMAIN = "https://mock.dokit.cn";
    public static final String MOCK_SCHEME_HTTP = "http://";
    public static final String MOCK_SCHEME_HTTPS = "https://";
    private int mGetCount;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private AtomicBoolean mIsActive = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public OnNetworkInfoUpdateListener mOnNetworkInfoUpdateListener;
    private int mPostCount;
    private List<NetworkRecord> mRecords = Collections.synchronizedList(new ArrayList());
    private long mStartTime;
    private int mTotalCount;

    public NetworkRecord getRecord(int requestId) {
        for (NetworkRecord record : this.mRecords) {
            if (record.mRequestId == requestId) {
                return record;
            }
        }
        return null;
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static NetworkManager INSTANCE = new NetworkManager();

        private Holder() {
        }
    }

    public static NetworkManager get() {
        return Holder.INSTANCE;
    }

    public void addRecord(int requestId, NetworkRecord record) {
        if (this.mRecords.size() > 100) {
            this.mRecords.remove(0);
        }
        if (record.isPostRecord()) {
            this.mPostCount++;
        } else if (record.isGetRecord()) {
            this.mGetCount++;
        }
        this.mTotalCount++;
        this.mRecords.add(record);
        updateRecord(record, true);
    }

    public void updateRecord(final NetworkRecord record, final boolean add) {
        if (this.mOnNetworkInfoUpdateListener != null) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    if (NetworkManager.this.mOnNetworkInfoUpdateListener != null) {
                        NetworkManager.this.mOnNetworkInfoUpdateListener.onNetworkInfoUpdate(record, add);
                    }
                }
            });
        }
    }

    public void startMonitor() {
        if (!this.mIsActive.get()) {
            this.mIsActive.set(true);
            this.mStartTime = System.currentTimeMillis();
        }
    }

    public void stopMonitor() {
        if (this.mIsActive.get()) {
            this.mIsActive.set(false);
            this.mStartTime = 0;
        }
    }

    public static boolean isActive() {
        return get().mIsActive.get();
    }

    public List<NetworkRecord> getRecords() {
        return this.mRecords;
    }

    public void setOnNetworkInfoUpdateListener(OnNetworkInfoUpdateListener onNetworkInfoUpdateListener) {
        this.mOnNetworkInfoUpdateListener = onNetworkInfoUpdateListener;
    }

    public long getRunningTime() {
        long j = this.mStartTime;
        if (j == 0) {
            return j;
        }
        return System.currentTimeMillis() - this.mStartTime;
    }

    public long getTotalRequestSize() {
        long totalSize = 0;
        for (NetworkRecord record : this.mRecords) {
            totalSize += record.requestLength;
        }
        return totalSize;
    }

    public long getTotalSize() {
        long totalSize = 0;
        for (NetworkRecord record : this.mRecords) {
            totalSize = totalSize + record.requestLength + record.responseLength;
        }
        return totalSize;
    }

    public long getTotalResponseSize() {
        long totalSize = 0;
        for (NetworkRecord record : this.mRecords) {
            totalSize += record.responseLength;
        }
        return totalSize;
    }

    public int getPostCount() {
        return this.mPostCount;
    }

    public int getGetCount() {
        return this.mGetCount;
    }

    public int getTotalCount() {
        return this.mTotalCount;
    }
}
