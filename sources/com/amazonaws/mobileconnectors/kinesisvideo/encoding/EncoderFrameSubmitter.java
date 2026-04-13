package com.amazonaws.mobileconnectors.kinesisvideo.encoding;

import android.media.Image;
import android.media.MediaCodec;
import java.nio.ByteBuffer;

public class EncoderFrameSubmitter {
    private static final int DEQUEUE_NOW = -1;
    private static final int FROM_START = 0;
    private static final int NO_FLAGS = 0;
    private static final long NS_IN_MS = 1000000;
    private static final long NS_IN_US = 1000;
    private final MediaCodec mEncoder;
    private long mFirstFrameTimestamp = -1;

    public EncoderFrameSubmitter(MediaCodec encoder) {
        this.mEncoder = encoder;
    }

    public void submitFrameToEncoder(Image frameImageYUV420, boolean endOfStream) {
        queueIntoInputImage(frameImageYUV420, nanosSinceFirstFrame() / 1000, endOfStream);
    }

    private void queueIntoInputImage(Image frameImageYUV420, long timestampInUS, boolean endOfStream) {
        int flags = endOfStream ? 4 : 0;
        int inputBufferIndex = this.mEncoder.dequeueInputBuffer(-1);
        int tmpBufferSize = this.mEncoder.getInputBuffer(inputBufferIndex).capacity();
        copyCameraFrameIntoInputImage(inputBufferIndex, frameImageYUV420);
        this.mEncoder.queueInputBuffer(inputBufferIndex, 0, tmpBufferSize, timestampInUS, flags);
    }

    private void copyCameraFrameIntoInputImage(int inputBufferIndex, Image cameraFrame) {
        Image codecInputImage = this.mEncoder.getInputImage(inputBufferIndex);
        for (int i = 0; i < cameraFrame.getPlanes().length; i++) {
            copyBuffer(cameraFrame.getPlanes()[i].getBuffer(), codecInputImage.getPlanes()[i].getBuffer());
        }
    }

    private int copyBuffer(ByteBuffer sourceBuffer, ByteBuffer destinationBuffer) {
        int bytesToCopy = Math.min(destinationBuffer.capacity(), sourceBuffer.remaining());
        destinationBuffer.limit(bytesToCopy);
        sourceBuffer.limit(bytesToCopy);
        destinationBuffer.put(sourceBuffer);
        destinationBuffer.rewind();
        return bytesToCopy;
    }

    private long nanosSinceFirstFrame() {
        long currentTime = System.currentTimeMillis();
        if (this.mFirstFrameTimestamp < 0) {
            this.mFirstFrameTimestamp = currentTime;
        }
        return (currentTime - this.mFirstFrameTimestamp) * 1000000;
    }
}
