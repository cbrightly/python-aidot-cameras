package org.webrtc;

import androidx.annotation.Nullable;

public interface VideoProcessor extends CapturerObserver {
    void onFrameCaptured(VideoFrame videoFrame, FrameAdaptationParameters frameAdaptationParameters);

    void setSink(@Nullable VideoSink videoSink);

    public static class FrameAdaptationParameters {
        public final int cropHeight;
        public final int cropWidth;
        public final int cropX;
        public final int cropY;
        public final boolean drop;
        public final int scaleHeight;
        public final int scaleWidth;
        public final long timestampNs;

        public FrameAdaptationParameters(int cropX2, int cropY2, int cropWidth2, int cropHeight2, int scaleWidth2, int scaleHeight2, long timestampNs2, boolean drop2) {
            this.cropX = cropX2;
            this.cropY = cropY2;
            this.cropWidth = cropWidth2;
            this.cropHeight = cropHeight2;
            this.scaleWidth = scaleWidth2;
            this.scaleHeight = scaleHeight2;
            this.timestampNs = timestampNs2;
            this.drop = drop2;
        }
    }
}
