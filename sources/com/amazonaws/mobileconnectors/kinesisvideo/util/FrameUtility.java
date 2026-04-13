package com.amazonaws.mobileconnectors.kinesisvideo.util;

import android.media.MediaCodec;
import android.util.Log;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFrame;
import java.nio.ByteBuffer;

public class FrameUtility {
    private static final long FRAME_DURATION_2_MS = 2;
    private static final int FRAME_FLAG_KEY_FRAME = 1;
    private static final int FRAME_FLAG_NONE = 0;
    private static final long HUNDREDS_OF_NANOS_IN_MS = 10000;
    private static final String TAG = FrameUtility.class.getSimpleName();

    public static KinesisVideoFrame createFrame(MediaCodec.BufferInfo bufferInfo, long timeCodeMs, int frameIndex, ByteBuffer encodedFrameData) {
        long currentTimeMs = System.currentTimeMillis();
        int flags = isKeyFrame(bufferInfo);
        String str = TAG;
        Log.d(str, "frame timestamp: " + currentTimeMs + ", index: " + frameIndex + ", duration: " + 2 + ", keyFrame: " + isKeyFrame(bufferInfo) + ", flags: " + ((int) flags));
        return new KinesisVideoFrame(frameIndex, (int) flags, currentTimeMs * 10000, currentTimeMs * 10000, 20000, encodedFrameData);
    }

    private static boolean isKeyFrame(MediaCodec.BufferInfo bufferInfo) {
        return (bufferInfo.flags & 1) != 0;
    }
}
