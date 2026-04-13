package com.amazonaws.kinesisvideo.auth;

public final class EmptyCredentialsProvider implements KinesisVideoCredentialsProvider {
    public KinesisVideoCredentials getCredentials() {
        return KinesisVideoCredentials.EMPTY_KINESIS_VIDEO_CREDENTIALS;
    }

    public KinesisVideoCredentials getUpdatedCredentials() {
        return KinesisVideoCredentials.EMPTY_KINESIS_VIDEO_CREDENTIALS;
    }
}
