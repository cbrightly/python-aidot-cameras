package com.amazonaws.mobileconnectors.kinesisvideo.client;

import android.content.Context;
import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.client.KinesisVideoClientConfiguration;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.internal.client.NativeKinesisVideoClient;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSource;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceConfiguration;
import com.amazonaws.kinesisvideo.internal.producer.client.KinesisVideoServiceClient;
import com.amazonaws.mobileconnectors.kinesisvideo.mediasource.android.AndroidMediaSourceFactory;
import com.amazonaws.mobileconnectors.kinesisvideo.util.CameraUtils;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public final class AndroidKinesisVideoClient extends NativeKinesisVideoClient {
    private final Context mContext;

    public AndroidKinesisVideoClient(@NonNull Log log, @NonNull Context context, @NonNull KinesisVideoClientConfiguration configuration, @NonNull KinesisVideoServiceClient serviceClient, @NonNull ScheduledExecutorService executor) {
        super(log, configuration, serviceClient, executor);
        this.mContext = context;
    }

    public MediaSource createMediaSource(String streamName, MediaSourceConfiguration mediaSourceConfiguration) {
        MediaSource mediaSource = AndroidMediaSourceFactory.createMediaSource(streamName, this.mContext, mediaSourceConfiguration);
        registerMediaSource(mediaSource);
        return mediaSource;
    }

    public List<MediaSourceConfiguration.Builder<? extends MediaSourceConfiguration>> listSupportedConfigurations() {
        return CameraUtils.getSupportedCameraConfigrations(this.mContext);
    }
}
