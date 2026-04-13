package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.nio.charset.Charset;

public class AuthInfo {
    private static final int AUTH_INFO_CURRENT_VERSION = 0;
    private final AuthInfoType mAuthType;
    private final byte[] mData;
    private final long mExpiration;
    private final int mVersion;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public AuthInfo(@NonNull AuthInfoType authType, @Nullable String data, long expiration) {
        this(authType, data == null ? null : data.getBytes(Charset.defaultCharset()), expiration);
    }

    public AuthInfo(@NonNull AuthInfoType authType, @Nullable byte[] data, long expiration) {
        this.mAuthType = authType;
        this.mData = data;
        this.mExpiration = expiration;
        this.mVersion = 0;
    }

    @NonNull
    public AuthInfoType getAuthType() {
        return this.mAuthType;
    }

    public int getIntAuthType() {
        return this.mAuthType.getIntType();
    }

    @Nullable
    public byte[] getData() {
        return this.mData;
    }

    public long getExpiration() {
        return this.mExpiration;
    }

    public int getVersion() {
        return this.mVersion;
    }
}
