package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.Nullable;

public interface AuthCallbacks {
    @Nullable
    AuthInfo getDeviceCertificate();

    @Nullable
    String getDeviceFingerprint();

    @Nullable
    AuthInfo getSecurityToken();
}
