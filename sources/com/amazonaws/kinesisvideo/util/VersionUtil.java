package com.amazonaws.kinesisvideo.util;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;

public final class VersionUtil {
    public static final String AWS_SDK_KVS_PRODUCER_VERSION_STRING = "1.2.3";
    private static final String DEFAULT_USER_AGENT_NAME = "AWS-SDK-KVS";
    private static final String SYSTEM_INFORMATION_STRING = ("JAVA/" + System.getProperty("java.version") + ' ' + System.getProperty("os.name").replace(' ', '_') + '/' + System.getProperty("os.version") + ' ' + System.getProperty("os.arch"));

    private VersionUtil() {
        throw new UnsupportedOperationException();
    }

    public static String getUserAgent(@NonNull String userAgentName) {
        Preconditions.checkNotNull(userAgentName);
        return userAgentName + '/' + AWS_SDK_KVS_PRODUCER_VERSION_STRING + ' ' + SYSTEM_INFORMATION_STRING;
    }

    public static String getUserAgent() {
        return getUserAgent(DEFAULT_USER_AGENT_NAME);
    }
}
