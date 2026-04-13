package com.amazonaws.kinesisvideo.internal.client.mediasource;

public interface MediaSourceConfiguration {

    public interface Builder<T extends MediaSourceConfiguration> {
        T build();
    }

    String getMediaSourceDescription();

    String getMediaSourceType();
}
