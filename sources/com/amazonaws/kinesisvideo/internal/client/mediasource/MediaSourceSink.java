package com.amazonaws.kinesisvideo.internal.client.mediasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFrame;

public interface MediaSourceSink {
    KinesisVideoProducerStream getProducerStream();

    void onCodecPrivateData(@Nullable byte[] bArr);

    void onFragmentMetadata(@NonNull String str, @NonNull String str2, boolean z);

    void onFrame(@NonNull KinesisVideoFrame kinesisVideoFrame);
}
