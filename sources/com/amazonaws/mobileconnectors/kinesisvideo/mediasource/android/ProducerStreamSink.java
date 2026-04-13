package com.amazonaws.mobileconnectors.kinesisvideo.mediasource.android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceSink;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFrame;

public class ProducerStreamSink implements MediaSourceSink {
    private final KinesisVideoProducerStream mProducerStream;

    public ProducerStreamSink(KinesisVideoProducerStream producerStream) {
        this.mProducerStream = producerStream;
    }

    public void onFrame(@NonNull KinesisVideoFrame kinesisVideoFrame) {
        Preconditions.checkNotNull(kinesisVideoFrame);
        this.mProducerStream.putFrame(kinesisVideoFrame);
    }

    public void onCodecPrivateData(@Nullable byte[] bytes) {
        this.mProducerStream.streamFormatChanged(bytes);
    }

    public void onFragmentMetadata(@NonNull String metadataName, @NonNull String metadataValue, boolean persistent) {
        this.mProducerStream.putFragmentMetadata(metadataName, metadataValue, persistent);
    }

    public KinesisVideoProducerStream getProducerStream() {
        return this.mProducerStream;
    }
}
