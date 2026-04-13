package com.amazonaws.kinesisvideo.internal.producer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.producer.Tag;

public interface ServiceCallbacks {
    void addStream(@NonNull KinesisVideoProducerStream kinesisVideoProducerStream);

    void createDevice(@NonNull String str, long j, long j2, @Nullable byte[] bArr, int i, long j3);

    void createStream(@NonNull String str, @NonNull String str2, @NonNull String str3, @Nullable String str4, long j, long j2, long j3, @Nullable byte[] bArr, int i, long j4);

    void describeStream(@NonNull String str, long j, long j2, @Nullable byte[] bArr, int i, long j3);

    void deviceCertToToken(@NonNull String str, long j, long j2, @Nullable byte[] bArr, int i, long j3);

    void free();

    void getStreamingEndpoint(@NonNull String str, @NonNull String str2, long j, long j2, @Nullable byte[] bArr, int i, long j3);

    void getStreamingToken(@NonNull String str, long j, long j2, @Nullable byte[] bArr, int i, long j3);

    void initialize(@NonNull KinesisVideoProducer kinesisVideoProducer);

    boolean isInitialized();

    void putStream(@NonNull String str, @NonNull String str2, long j, boolean z, boolean z2, @NonNull String str3, long j2, long j3, @Nullable byte[] bArr, int i, long j4);

    void removeStream(@NonNull KinesisVideoProducerStream kinesisVideoProducerStream);

    void tagResource(@NonNull String str, @Nullable Tag[] tagArr, long j, long j2, @Nullable byte[] bArr, int i, long j3);
}
