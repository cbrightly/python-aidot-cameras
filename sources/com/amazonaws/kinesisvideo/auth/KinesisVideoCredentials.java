package com.amazonaws.kinesisvideo.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import java.io.Serializable;
import java.util.Date;

public class KinesisVideoCredentials implements Serializable {
    public static final Date CREDENTIALS_NEVER_EXPIRE = new Date(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    public static final KinesisVideoCredentials EMPTY_KINESIS_VIDEO_CREDENTIALS = new KinesisVideoCredentials("", "");
    private final String accessKey;
    private final Date expiration;
    private final String secretKey;
    private final String sessionToken;

    public KinesisVideoCredentials(@NonNull String accessKey2, @NonNull String secretKey2) {
        this(accessKey2, secretKey2, (String) null, CREDENTIALS_NEVER_EXPIRE);
    }

    public KinesisVideoCredentials(@NonNull String accessKey2, @NonNull String secretKey2, @Nullable String sessionToken2, @NonNull Date expiration2) {
        this.accessKey = (String) Preconditions.checkNotNull(accessKey2);
        this.secretKey = (String) Preconditions.checkNotNull(secretKey2);
        this.sessionToken = sessionToken2;
        this.expiration = expiration2;
    }

    @NonNull
    public String getAccessKey() {
        return this.accessKey;
    }

    @NonNull
    public String getSecretKey() {
        return this.secretKey;
    }

    @Nullable
    public String getSessionToken() {
        return this.sessionToken;
    }

    @NonNull
    public Date getExpiration() {
        return this.expiration;
    }
}
