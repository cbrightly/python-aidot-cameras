package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.NonNull;

public class StorageInfo {
    public static final int STORAGE_INFO_CURRENT_VERSION = 0;
    private final DeviceStorageType mDeviceStorageType;
    private final String mRootDirectory;
    private final int mSpillRatio;
    private final long mStorageSize;
    private final int mVersion;

    public enum DeviceStorageType {
        DEVICE_STORAGE_TYPE_IN_MEM(0),
        DEVICE_STORAGE_TYPE_HYBRID_FILE(1);
        
        private int value;

        private DeviceStorageType(int i) {
            this.value = i;
        }

        public int getIntValue() {
            return this.value;
        }
    }

    public StorageInfo(int version, DeviceStorageType deviceStorageType, long storageSize, int spillRatio, @NonNull String rootDirectory) {
        this.mVersion = version;
        this.mDeviceStorageType = deviceStorageType;
        this.mStorageSize = storageSize;
        this.mSpillRatio = spillRatio;
        this.mRootDirectory = rootDirectory;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public int getDeviceStorageType() {
        return this.mDeviceStorageType.getIntValue();
    }

    public long getStorageSize() {
        return this.mStorageSize;
    }

    public int getSpillRatio() {
        return this.mSpillRatio;
    }

    @NonNull
    public String getRootDirectory() {
        return this.mRootDirectory;
    }
}
