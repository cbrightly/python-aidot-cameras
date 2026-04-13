package com.amazonaws.kinesisvideo.client;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.producer.StorageCallbacks;
import com.amazonaws.kinesisvideo.storage.DefaultStorageCallbacks;

public final class KinesisVideoClientConfigurationDefaults {
    public static final int DEFAULT_SERVICE_CALL_TIMEOUT_IN_MILLIS = 5000;
    static final int DEVICE_VERSION = 0;
    static final StorageCallbacks NO_OP_STORAGE_CALLBACKS = new DefaultStorageCallbacks();
    static final String PROD_CONTROL_PLANE_ENDPOINT_FORMAT = "kinesisvideo.%s.amazonaws.com";
    static final int SPILL_RATIO_90_PERCENT = 90;
    static final int STORAGE_SIZE_256_MEGS = 268435456;
    static final int TEN_STREAMS = 10;
    static final String US_WEST_2 = "us-west-2";

    public static String getControlPlaneEndpoint(@NonNull String region) {
        return String.format(PROD_CONTROL_PLANE_ENDPOINT_FORMAT, new Object[]{region});
    }

    private KinesisVideoClientConfigurationDefaults() {
    }
}
