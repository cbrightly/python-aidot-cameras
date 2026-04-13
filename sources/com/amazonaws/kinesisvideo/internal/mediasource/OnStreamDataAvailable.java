package com.amazonaws.kinesisvideo.internal.mediasource;

import java.nio.ByteBuffer;

public interface OnStreamDataAvailable {
    void onFragmentMetadataAvailable(String str, String str2, boolean z);

    void onFrameDataAvailable(ByteBuffer byteBuffer);
}
