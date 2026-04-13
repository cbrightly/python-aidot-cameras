package com.amazonaws.mobileconnectors.kinesisvideo.encoding;

import android.media.Image;
import android.media.MediaCodec;
import android.util.Log;
import com.amazonaws.kinesisvideo.client.mediasource.CameraMediaSourceConfiguration;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFrame;
import com.amazonaws.mobileconnectors.kinesisvideo.util.FrameUtility;
import java.nio.ByteBuffer;

public class EncoderWrapper {
    private static final String TAG = EncoderWrapper.class.getSimpleName();
    private static final int TIMEOUT_USEC = 10000;
    private MediaCodec.BufferInfo mBufferInfo;
    private CodecPrivateDataAvailableListener mCodecPrivateDataListener;
    private MediaCodec mEncoder;
    private EncoderFrameSubmitter mEncoderFrameSubmitter;
    private long mFragmentStart = 0;
    private FrameAvailableListener mFrameAvailableListener;
    private int mFrameIndex;
    private boolean mIsStopped = false;
    private long mLastRecordedFrameTimestamp = 0;
    private final CameraMediaSourceConfiguration mMediaSourceConfiguration;

    public interface CodecPrivateDataAvailableListener {
        void onCodecPrivateDataAvailable(byte[] bArr);
    }

    public interface FrameAvailableListener {
        void onFrameAvailable(KinesisVideoFrame kinesisVideoFrame);
    }

    public EncoderWrapper(CameraMediaSourceConfiguration mediaSourceConfiguration) {
        this.mMediaSourceConfiguration = mediaSourceConfiguration;
        initEncoder();
    }

    private void initEncoder() {
        this.mBufferInfo = new MediaCodec.BufferInfo();
        MediaCodec createConfiguredEncoder = EncoderFactory.createConfiguredEncoder(this.mMediaSourceConfiguration);
        this.mEncoder = createConfiguredEncoder;
        this.mEncoderFrameSubmitter = new EncoderFrameSubmitter(createConfiguredEncoder);
        this.mEncoder.start();
    }

    public void setCodecPrivateDataAvailableListener(CodecPrivateDataAvailableListener listener) {
        this.mCodecPrivateDataListener = listener;
    }

    public void setEncodedFrameAvailableListener(FrameAvailableListener listener) {
        this.mFrameAvailableListener = listener;
    }

    public void encodeFrame(Image frameImageYUV420, boolean endOfStream) {
        if (this.mIsStopped) {
            Log.w(TAG, "received a frame to encode after already stopped. returning");
            return;
        }
        String str = TAG;
        Log.d(str, "encoding frame" + threadId());
        this.mEncoderFrameSubmitter.submitFrameToEncoder(frameImageYUV420, endOfStream);
        Log.d(str, "frame sent to encoder" + threadId());
        getDataFromEncoder(endOfStream);
        Log.d(str, "frame encoded" + threadId());
    }

    private void getDataFromEncoder(boolean endOfStream) {
        boolean stopReadingFromEncoder = false;
        while (!stopReadingFromEncoder) {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            this.mBufferInfo = bufferInfo;
            int outputBufferId = this.mEncoder.dequeueOutputBuffer(bufferInfo, 10000);
            switch (outputBufferId) {
                case -2:
                    break;
                case -1:
                    if (endOfStream) {
                        Log.d(TAG, "no output available, await end of stream");
                        sleep(15);
                    }
                    stopReadingFromEncoder = true;
                    break;
                default:
                    if (outputBufferId >= 0) {
                        processEncoderOutputBuffer(outputBufferId);
                        if (!isEndOfStream()) {
                            break;
                        } else {
                            stopReadingFromEncoder = true;
                            break;
                        }
                    } else {
                        String str = TAG;
                        Log.w(str, "unexpected encoder output buffer id: " + outputBufferId);
                        break;
                    }
            }
        }
    }

    private void processEncoderOutputBuffer(int outputBufferId) {
        if (this.mBufferInfo.size == 0) {
            String str = TAG;
            Log.w(str, "empty buffer " + outputBufferId);
            this.mEncoder.releaseOutputBuffer(outputBufferId, false);
            return;
        }
        ByteBuffer encodedData = this.mEncoder.getOutputBuffer(outputBufferId);
        if (encodedData != null) {
            processEncodedData(encodedData);
            this.mEncoder.releaseOutputBuffer(outputBufferId, false);
            return;
        }
        throw new RuntimeException("encoder output buffer " + outputBufferId + " is null");
    }

    private void processEncodedData(ByteBuffer encodedData) {
        adjustEncodedDataPosition(encodedData);
        adjustEncodedDataPosition(encodedData);
        if (isCodecPrivateData()) {
            notifyCodecPrivateDataAvailable(encodedData);
        } else if (isEndOfStream()) {
            Log.d(TAG, "end of stream reached");
        } else {
            sendEncodedFrameToProducerSDK(encodedData);
        }
    }

    private void adjustEncodedDataPosition(ByteBuffer encodedData) {
        encodedData.position(this.mBufferInfo.offset);
        MediaCodec.BufferInfo bufferInfo = this.mBufferInfo;
        encodedData.limit(bufferInfo.offset + bufferInfo.size);
    }

    private boolean isEndOfStream() {
        return (this.mBufferInfo.flags & 4) != 0;
    }

    private boolean isCodecPrivateData() {
        return (this.mBufferInfo.flags & 2) != 0;
    }

    private void notifyCodecPrivateDataAvailable(ByteBuffer codecPrivateDataBuffer) {
        Log.d(TAG, "got codec private data");
        this.mCodecPrivateDataListener.onCodecPrivateDataAvailable(convertToArray(codecPrivateDataBuffer));
    }

    private void sendEncodedFrameToProducerSDK(ByteBuffer encodedData) {
        long currentTime = System.currentTimeMillis();
        String str = TAG;
        Log.d(str, "time between frames: " + (currentTime - this.mLastRecordedFrameTimestamp) + "ms");
        this.mLastRecordedFrameTimestamp = currentTime;
        if (this.mFragmentStart == 0) {
            this.mFragmentStart = currentTime;
        }
        FrameAvailableListener frameAvailableListener = this.mFrameAvailableListener;
        MediaCodec.BufferInfo bufferInfo = this.mBufferInfo;
        long j = (1 + currentTime) - this.mFragmentStart;
        int i = this.mFrameIndex;
        this.mFrameIndex = i + 1;
        frameAvailableListener.onFrameAvailable(FrameUtility.createFrame(bufferInfo, j, i, encodedData));
    }

    public void stop() {
        Log.d(TAG, "stopping encoder");
        this.mIsStopped = true;
        this.mEncoder.stop();
        this.mEncoder.release();
    }

    private byte[] convertToArray(ByteBuffer byteBuffer) {
        byte[] array = new byte[byteBuffer.remaining()];
        byteBuffer.get(array);
        return array;
    }

    private static String threadId() {
        return " | threadId=" + Thread.currentThread().getId();
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep((long) ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
