package com.amazonaws.kinesisvideo.internal.producer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.producer.DeviceInfo;
import com.amazonaws.kinesisvideo.producer.StreamCallbacks;
import com.amazonaws.kinesisvideo.producer.StreamDescription;
import com.amazonaws.kinesisvideo.producer.StreamInfo;

public interface KinesisVideoProducer {
    public static final long READY_TIMEOUT_IN_MILLISECONDS = 5000;

    void create(@NonNull DeviceInfo deviceInfo);

    void createDeviceResult(long j, @Nullable String str, int i);

    @NonNull
    KinesisVideoProducerStream createStream(@NonNull StreamInfo streamInfo, @Nullable StreamCallbacks streamCallbacks);

    void createStreamResult(long j, @Nullable String str, int i);

    @NonNull
    KinesisVideoProducerStream createStreamSync(@NonNull StreamInfo streamInfo, @Nullable StreamCallbacks streamCallbacks);

    void createSync(@NonNull DeviceInfo deviceInfo);

    void describeStreamResult(long j, @Nullable StreamDescription streamDescription, int i);

    void deviceCertToTokenResult(long j, @Nullable byte[] bArr, long j2, int i);

    void free();

    void freeStream(@NonNull KinesisVideoProducerStream kinesisVideoProducerStream);

    void freeStreams();

    @NonNull
    KinesisVideoMetrics getMetrics();

    void getStreamingEndpointResult(long j, @Nullable String str, int i);

    void getStreamingTokenResult(long j, @Nullable byte[] bArr, long j2, int i);

    boolean isInitialized();

    boolean isReady();

    void putStreamResult(long j, long j2, int i);

    void stopStreams();

    void tagResourceResult(long j, int i);
}
