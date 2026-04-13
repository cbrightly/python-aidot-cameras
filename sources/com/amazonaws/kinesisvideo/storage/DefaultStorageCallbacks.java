package com.amazonaws.kinesisvideo.storage;

import com.amazonaws.kinesisvideo.producer.StorageCallbacks;

public class DefaultStorageCallbacks implements StorageCallbacks {
    public void storageOverflowPressure(long remainingSize) {
    }
}
