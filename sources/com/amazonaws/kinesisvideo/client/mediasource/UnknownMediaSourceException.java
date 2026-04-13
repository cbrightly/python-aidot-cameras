package com.amazonaws.kinesisvideo.client.mediasource;

public class UnknownMediaSourceException extends RuntimeException {
    public UnknownMediaSourceException(String mediaSourceType) {
        super("Unknown media source type '" + mediaSourceType + "'. Cannot create instance from the configuration");
    }
}
