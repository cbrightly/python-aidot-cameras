package com.amazonaws.kinesisvideo.internal.producer.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.auth.KinesisVideoCredentialsProvider;
import com.amazonaws.kinesisvideo.client.KinesisVideoClientConfiguration;
import com.amazonaws.kinesisvideo.common.function.Consumer;
import com.amazonaws.kinesisvideo.producer.StreamDescription;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

public interface KinesisVideoServiceClient {
    String createStream(@NonNull String str, @NonNull String str2, @NonNull String str3, @Nullable String str4, long j, long j2, @Nullable KinesisVideoCredentialsProvider kinesisVideoCredentialsProvider);

    void deleteStream(@NonNull String str, @NonNull String str2, Date date, long j, @Nullable KinesisVideoCredentialsProvider kinesisVideoCredentialsProvider);

    StreamDescription describeStream(@NonNull String str, long j, @Nullable KinesisVideoCredentialsProvider kinesisVideoCredentialsProvider);

    String getDataEndpoint(@NonNull String str, @NonNull String str2, long j, @Nullable KinesisVideoCredentialsProvider kinesisVideoCredentialsProvider);

    void initialize(@NonNull KinesisVideoClientConfiguration kinesisVideoClientConfiguration);

    void putMedia(@NonNull String str, @NonNull String str2, long j, boolean z, boolean z2, @NonNull String str3, long j2, @Nullable KinesisVideoCredentialsProvider kinesisVideoCredentialsProvider, @NonNull InputStream inputStream, @NonNull Consumer<InputStream> consumer, @Nullable Consumer<Exception> consumer2);

    void tagStream(@NonNull String str, @Nullable Map<String, String> map, long j, @Nullable KinesisVideoCredentialsProvider kinesisVideoCredentialsProvider);
}
