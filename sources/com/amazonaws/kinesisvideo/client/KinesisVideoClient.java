package com.amazonaws.kinesisvideo.client;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSource;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceConfiguration;
import com.amazonaws.kinesisvideo.producer.DeviceInfo;
import java.util.List;

public interface KinesisVideoClient {
    MediaSource createMediaSource(String str, MediaSourceConfiguration mediaSourceConfiguration);

    void free();

    void initialize(@NonNull DeviceInfo deviceInfo);

    boolean isInitialized();

    List<MediaSourceConfiguration.Builder<? extends MediaSourceConfiguration>> listSupportedConfigurations();

    void registerMediaSource(MediaSource mediaSource);

    void startAllMediaSources();

    void stopAllMediaSources();

    void unregisterMediaSource(MediaSource mediaSource);
}
