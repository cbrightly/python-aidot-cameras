package org.webrtc;

import androidx.annotation.Nullable;
import org.webrtc.VideoProcessor;

/* compiled from: VideoProcessor */
public final /* synthetic */ class z0 {
    public static void a(VideoProcessor _this, VideoFrame frame, VideoProcessor.FrameAdaptationParameters parameters) {
        VideoFrame adaptedFrame = b(frame, parameters);
        if (adaptedFrame != null) {
            _this.onFrameCaptured(adaptedFrame);
            adaptedFrame.release();
        }
    }

    @Nullable
    public static VideoFrame b(VideoFrame frame, VideoProcessor.FrameAdaptationParameters parameters) {
        VideoProcessor.FrameAdaptationParameters frameAdaptationParameters = parameters;
        if (frameAdaptationParameters.drop) {
            return null;
        }
        return new VideoFrame(frame.getBuffer().cropAndScale(frameAdaptationParameters.cropX, frameAdaptationParameters.cropY, frameAdaptationParameters.cropWidth, frameAdaptationParameters.cropHeight, frameAdaptationParameters.scaleWidth, frameAdaptationParameters.scaleHeight), frame.getRotation(), frameAdaptationParameters.timestampNs, "VideoProcessor-0-0", frame.getIsKeyFrame());
    }
}
