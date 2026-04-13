package com.amazonaws.kinesisvideo.internal.mediasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceSink;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFrame;

public class ProducerStreamSink implements MediaSourceSink {
    private final KinesisVideoProducerStream producerStream;

    public ProducerStreamSink(KinesisVideoProducerStream producerStream2) {
        this.producerStream = producerStream2;
    }

    public void onFrame(@NonNull KinesisVideoFrame kinesisVideoFrame) {
        Preconditions.checkNotNull(kinesisVideoFrame);
        this.producerStream.putFrame(kinesisVideoFrame);
    }

    public void onCodecPrivateData(@Nullable byte[] bytes) {
        this.producerStream.streamFormatChanged(bytes);
    }

    public void onFragmentMetadata(String metadataName, String metadataValue, boolean persistent) {
        this.producerStream.putFragmentMetadata(metadataName, metadataValue, persistent);
    }

    public KinesisVideoProducerStream getProducerStream() {
        return this.producerStream;
    }
}
