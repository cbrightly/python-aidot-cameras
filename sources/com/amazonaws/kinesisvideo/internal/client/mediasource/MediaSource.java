package com.amazonaws.kinesisvideo.internal.client.mediasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.client.mediasource.MediaSourceState;
import com.amazonaws.kinesisvideo.producer.StreamCallbacks;
import com.amazonaws.kinesisvideo.producer.StreamInfo;

public interface MediaSource {
    void configure(MediaSourceConfiguration mediaSourceConfiguration);

    void free();

    MediaSourceConfiguration getConfiguration();

    MediaSourceSink getMediaSourceSink();

    MediaSourceState getMediaSourceState();

    @Nullable
    StreamCallbacks getStreamCallbacks();

    StreamInfo getStreamInfo();

    void initialize(@NonNull MediaSourceSink mediaSourceSink);

    boolean isStopped();

    void start();

    void stop();
}
