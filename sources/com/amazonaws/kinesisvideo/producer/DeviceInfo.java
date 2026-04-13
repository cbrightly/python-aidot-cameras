package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;

public class DeviceInfo {
    public static final int DEVICE_INFO_CURRENT_VERSION = 0;
    private final String mName;
    private final StorageInfo mStorageInfo;
    private final int mStreamCount;
    private final Tag[] mTags;
    private final int mVersion;

    public DeviceInfo(int version, @Nullable String name, @NonNull StorageInfo storageInfo, int streamCount, @Nullable Tag[] tags) {
        this.mStorageInfo = (StorageInfo) Preconditions.checkNotNull(storageInfo);
        this.mName = name;
        this.mTags = tags;
        this.mVersion = version;
        this.mStreamCount = streamCount;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public String getName() {
        return this.mName;
    }

    @NonNull
    public StorageInfo getStorageInfo() {
        return this.mStorageInfo;
    }

    public int getStreamCount() {
        return this.mStreamCount;
    }

    public int getStorageInfoVersion() {
        return this.mStorageInfo.getVersion();
    }

    public int getDeviceStorageType() {
        return this.mStorageInfo.getDeviceStorageType();
    }

    public long getStorageSize() {
        return this.mStorageInfo.getStorageSize();
    }

    public int getSpillRatio() {
        return this.mStorageInfo.getSpillRatio();
    }

    @Nullable
    public String getRootDirectory() {
        return this.mStorageInfo.getRootDirectory();
    }

    @Nullable
    public Tag[] getTags() {
        return this.mTags;
    }
}
