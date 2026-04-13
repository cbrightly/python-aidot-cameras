package com.amazonaws.kinesisvideo.client.mediasource;

import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceConfiguration;

public class UnsupportedConfigurationException extends RuntimeException {
    UnsupportedConfigurationException(MediaSourceConfiguration mediaSourceConfiguration) {
        super("Configuration is not supported: '" + mediaSourceConfiguration.toString() + "'");
    }
}
