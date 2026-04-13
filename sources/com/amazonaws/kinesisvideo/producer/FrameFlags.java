package com.amazonaws.kinesisvideo.producer;

public class FrameFlags {
    public static final int FRAME_FLAG_DISCARDABLE_FRAME = 2;
    public static final int FRAME_FLAG_INVISIBLE_FRAME = 4;
    public static final int FRAME_FLAG_KEY_FRAME = 1;
    public static final int FRAME_FLAG_NONE = 0;

    public static boolean isKeyFrame(int frameFlags) {
        return (frameFlags & 1) == 1;
    }

    public static boolean isDiscardableFrame(int frameFlags) {
        return (frameFlags & 2) == 2;
    }

    public static boolean isInvisibleFrame(int frameFlags) {
        return (frameFlags & 4) == 4;
    }
}
