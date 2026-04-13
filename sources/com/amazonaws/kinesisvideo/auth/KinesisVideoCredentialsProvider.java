package com.amazonaws.kinesisvideo.auth;

import androidx.annotation.Nullable;

public interface KinesisVideoCredentialsProvider {
    @Nullable
    KinesisVideoCredentials getCredentials();

    @Nullable
    KinesisVideoCredentials getUpdatedCredentials();
}
