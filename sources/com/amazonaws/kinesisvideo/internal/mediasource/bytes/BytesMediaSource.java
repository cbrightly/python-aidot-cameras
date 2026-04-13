package com.amazonaws.kinesisvideo.internal.mediasource.bytes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.client.mediasource.MediaSourceState;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSource;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceConfiguration;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceSink;
import com.amazonaws.kinesisvideo.internal.mediasource.OnStreamDataAvailable;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFrame;
import com.amazonaws.kinesisvideo.producer.StreamCallbacks;
import com.amazonaws.kinesisvideo.producer.StreamInfo;
import com.amazonaws.kinesisvideo.producer.Tag;
import com.amazonaws.kinesisvideo.util.StreamInfoConstants;
import com.yanzhenjie.andserver.util.h;
import java.nio.ByteBuffer;
import meshsdk.model.json.RoutineRule;

public class BytesMediaSource implements MediaSource {
    private static final long DEFAULT_FRAME_DURATION_33MS = 33;
    private static final int KEY_FRAME_EVERY_60_FRAMES = 60;
    private static final String TAG = "BytesMediaSource";
    private BytesGenerator bytesGenerator;
    private BytesMediaSourceConfiguration configuration;
    private int frameIndex;
    /* access modifiers changed from: private */
    public long lastTimestampMillis;
    /* access modifiers changed from: private */
    public MediaSourceSink mediaSourceSink;
    private MediaSourceState mediaSourceState;
    private final String streamName;

    static /* synthetic */ int access$208(BytesMediaSource x0) {
        int i = x0.frameIndex;
        x0.frameIndex = i + 1;
        return i;
    }

    public BytesMediaSource(@NonNull String streamName2) {
        this.streamName = streamName2;
    }

    public MediaSourceState getMediaSourceState() {
        return this.mediaSourceState;
    }

    public MediaSourceConfiguration getConfiguration() {
        return this.configuration;
    }

    public StreamInfo getStreamInfo() {
        return new StreamInfo(0, this.streamName, StreamInfo.StreamingType.STREAMING_TYPE_REALTIME, h.APPLICATION_OCTET_STREAM_VALUE, StreamInfoConstants.NO_KMS_KEY_ID, this.configuration.getRetentionPeriodInHours() * 36000000000L, false, 0, 200000000000L, true, true, true, true, true, (String) null, (String) null, StreamInfoConstants.DEFAULT_BITRATE, 30, StreamInfoConstants.DEFAULT_BUFFER_DURATION, 200000000, 200000000, 10000, true, (byte[]) null, new Tag[]{new Tag(RoutineRule.THEN_TYPE_DEVICE, "Test Device"), new Tag("stream", "Test Stream")}, StreamInfo.NalAdaptationFlags.NAL_ADAPTATION_FLAG_NONE);
    }

    public void initialize(@NonNull MediaSourceSink mediaSourceSink2) {
        this.mediaSourceSink = mediaSourceSink2;
    }

    public void configure(MediaSourceConfiguration configuration2) {
        Preconditions.checkState(this.configuration == null);
        if (configuration2 instanceof BytesMediaSourceConfiguration) {
            this.configuration = (BytesMediaSourceConfiguration) configuration2;
            return;
        }
        throw new IllegalArgumentException("can only use BytesMediaSourceConfiguration");
    }

    public void start() {
        this.mediaSourceState = MediaSourceState.RUNNING;
        BytesGenerator bytesGenerator2 = new BytesGenerator(this.configuration.getFps());
        this.bytesGenerator = bytesGenerator2;
        bytesGenerator2.onStreamDataAvailable(createDataAvailableCallback());
        this.bytesGenerator.start();
    }

    private OnStreamDataAvailable createDataAvailableCallback() {
        return new OnStreamDataAvailable() {
            public void onFrameDataAvailable(ByteBuffer data) {
                long currentTimeMs = System.currentTimeMillis();
                long decodingTs = currentTimeMs * 10000;
                long presentationTs = currentTimeMs * 10000;
                long frameDuration = BytesMediaSource.this.lastTimestampMillis == 0 ? 330000 : (10000 * (currentTimeMs - BytesMediaSource.this.lastTimestampMillis)) / 2;
                KinesisVideoFrame frame = new KinesisVideoFrame(BytesMediaSource.access$208(BytesMediaSource.this), (int) BytesMediaSource.this.isKeyFrame(), decodingTs, presentationTs, frameDuration, data);
                if (frame.getSize() != 0 && frameDuration != 0) {
                    long unused = BytesMediaSource.this.lastTimestampMillis = currentTimeMs;
                    BytesMediaSource.this.submitFrameOnUIThread(frame);
                }
            }

            public void onFragmentMetadataAvailable(String metadataName, String metadataValue, boolean persistent) {
                try {
                    BytesMediaSource.this.mediaSourceSink.onFragmentMetadata(metadataName, metadataValue, persistent);
                } catch (KinesisVideoException e) {
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public boolean isKeyFrame() {
        return this.frameIndex % 60 == 0;
    }

    /* access modifiers changed from: private */
    public void submitFrameOnUIThread(KinesisVideoFrame frame) {
        try {
            this.mediaSourceSink.onFrame(frame);
        } catch (KinesisVideoException e) {
        }
    }

    public void stop() {
        BytesGenerator bytesGenerator2 = this.bytesGenerator;
        if (bytesGenerator2 != null) {
            bytesGenerator2.stop();
        }
        this.mediaSourceState = MediaSourceState.STOPPED;
    }

    public boolean isStopped() {
        return this.mediaSourceState == MediaSourceState.STOPPED;
    }

    public void free() {
    }

    public MediaSourceSink getMediaSourceSink() {
        return this.mediaSourceSink;
    }

    @Nullable
    public StreamCallbacks getStreamCallbacks() {
        return null;
    }
}
