package com.amazonaws.kinesisvideo.auth;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;

public final class StaticCredentialsProvider implements KinesisVideoCredentialsProvider {
    private final KinesisVideoCredentials credentials;

    public StaticCredentialsProvider(@NonNull KinesisVideoCredentials credentials2) {
        this.credentials = (KinesisVideoCredentials) Preconditions.checkNotNull(credentials2);
    }

    public KinesisVideoCredentials getCredentials() {
        return this.credentials;
    }

    public KinesisVideoCredentials getUpdatedCredentials() {
        return this.credentials;
    }
}
