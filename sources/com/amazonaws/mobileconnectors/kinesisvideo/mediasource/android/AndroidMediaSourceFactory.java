package com.amazonaws.mobileconnectors.kinesisvideo.mediasource.android;

import android.content.Context;
import com.amazonaws.kinesisvideo.client.mediasource.CameraMediaSourceConfiguration;
import com.amazonaws.kinesisvideo.client.mediasource.UnknownMediaSourceException;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSource;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceConfiguration;

public final class AndroidMediaSourceFactory {
    public static MediaSource createMediaSource(String streamName, Context context, MediaSourceConfiguration configuration) {
        if (CameraMediaSourceConfiguration.MEDIA_SOURCE_TYPE.equals(configuration.getMediaSourceType())) {
            return createAndroidCameraMediaSource(streamName, context, (CameraMediaSourceConfiguration) configuration);
        }
        throw new UnknownMediaSourceException(configuration.getMediaSourceType());
    }

    private static AndroidCameraMediaSource createAndroidCameraMediaSource(String streamName, Context context, CameraMediaSourceConfiguration configuration) {
        AndroidCameraMediaSource mediaSource = new AndroidCameraMediaSource(streamName, context);
        mediaSource.configure(configuration);
        return mediaSource;
    }

    private AndroidMediaSourceFactory() {
    }
}
