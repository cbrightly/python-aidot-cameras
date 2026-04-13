package com.amazonaws.mobileconnectors.kinesisvideo.mediasource.android;

import android.content.Context;
import android.media.ImageReader;
import android.util.Log;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.client.mediasource.CameraMediaSourceConfiguration;
import com.amazonaws.kinesisvideo.client.mediasource.MediaSourceState;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSource;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceConfiguration;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceSink;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFrame;
import com.amazonaws.kinesisvideo.producer.StreamCallbacks;
import com.amazonaws.kinesisvideo.producer.StreamInfo;
import com.amazonaws.kinesisvideo.producer.Tag;
import com.amazonaws.kinesisvideo.util.StreamInfoConstants;
import com.amazonaws.mobileconnectors.kinesisvideo.camera.CameraFramesSource;
import com.amazonaws.mobileconnectors.kinesisvideo.camera.EncodingCancellationToken;
import com.amazonaws.mobileconnectors.kinesisvideo.encoding.EncoderWrapper;
import java.util.Arrays;
import java.util.List;
import meshsdk.model.json.RoutineRule;

public class AndroidCameraMediaSource implements MediaSource {
    /* access modifiers changed from: private */
    public static final String TAG = AndroidCameraMediaSource.class.getSimpleName();
    private static final int TWO_FRAMES_BUFFER = 2;
    private CameraFramesSource mCameraFramesSource;
    private final Context mContext;
    private EncodingCancellationToken mEncodingCancellationToken = new EncodingCancellationToken();
    private CameraMediaSourceConfiguration mMediaSourceConfiguration;
    /* access modifiers changed from: private */
    public MediaSourceSink mMediaSourceSink;
    private MediaSourceState mMediaSourceState;
    private List<Surface> mPreivewSurfaces;
    private final String mStreamName;

    public interface CaptureCallback {
        void onFailed();

        void onStarted();
    }

    public interface OpenCameraCallback {
        void onError(KinesisVideoException kinesisVideoException);

        void onOpened();
    }

    public AndroidCameraMediaSource(String streamName, Context context) {
        this.mContext = context;
        this.mStreamName = streamName;
    }

    public void setPreviewSurfaces(Surface... surfaces) {
        this.mPreivewSurfaces = Arrays.asList(surfaces);
    }

    private CameraFramesSource createFramesSource(ImageReader imageReader) {
        this.mEncodingCancellationToken = new EncodingCancellationToken();
        CameraFramesSource cameraFramesSource = new CameraFramesSource(imageReader, this.mMediaSourceConfiguration, this.mEncodingCancellationToken);
        cameraFramesSource.setCodecPrivateDataListener(waitForCodecPrivateData());
        cameraFramesSource.setFramesListener(pushFrameToSink());
        return cameraFramesSource;
    }

    public MediaSourceState getMediaSourceState() {
        return this.mMediaSourceState;
    }

    public MediaSourceConfiguration getConfiguration() {
        return this.mMediaSourceConfiguration;
    }

    public StreamInfo getStreamInfo() {
        String contentType = this.mMediaSourceConfiguration.getEncoderMimeType();
        if (contentType.equals("video/avc")) {
            contentType = "video/h264";
        }
        return new StreamInfo(0, this.mStreamName, StreamInfo.StreamingType.STREAMING_TYPE_REALTIME, contentType, StreamInfoConstants.NO_KMS_KEY_ID, ((long) this.mMediaSourceConfiguration.getRetentionPeriodInHours()) * 36000000000L, false, StreamInfoConstants.MAX_LATENCY, StreamInfoConstants.DEFAULT_GOP_DURATION, true, false, this.mMediaSourceConfiguration.getIsAbsoluteTimecode(), true, true, StreamInfo.codecIdFromContentType(this.mMediaSourceConfiguration.getEncoderMimeType()), StreamInfo.createTrackName(this.mMediaSourceConfiguration.getEncoderMimeType()), this.mMediaSourceConfiguration.getBitRate(), this.mMediaSourceConfiguration.getFrameRate(), StreamInfoConstants.DEFAULT_BUFFER_DURATION, 200000000, 200000000, this.mMediaSourceConfiguration.getTimeScale() / 100, true, this.mMediaSourceConfiguration.getCodecPrivateData(), new Tag[]{new Tag(RoutineRule.THEN_TYPE_DEVICE, "Test Device"), new Tag("stream", "Test Stream")}, this.mMediaSourceConfiguration.getNalAdaptationFlags());
    }

    public void initialize(@NonNull MediaSourceSink mediaSourceSink) {
        this.mMediaSourceSink = mediaSourceSink;
        this.mMediaSourceState = MediaSourceState.INITIALIZED;
    }

    public void configure(MediaSourceConfiguration configuration) {
        if (configuration instanceof CameraMediaSourceConfiguration) {
            this.mMediaSourceConfiguration = (CameraMediaSourceConfiguration) configuration;
            this.mCameraFramesSource = createFramesSource(createImageReader());
            return;
        }
        throw new IllegalArgumentException("expected instance of CameraMediaSourceConfiguration, received " + configuration);
    }

    private ImageReader createImageReader() {
        return ImageReader.newInstance(this.mMediaSourceConfiguration.getHorizontalResolution(), this.mMediaSourceConfiguration.getVerticalResolution(), 35, 2);
    }

    public void start() {
        this.mMediaSourceState = MediaSourceState.RUNNING;
        startEncoding();
    }

    public void stop() {
        stopEncoding();
        this.mMediaSourceState = MediaSourceState.STOPPED;
    }

    public boolean isStopped() {
        return this.mMediaSourceState == MediaSourceState.STOPPED;
    }

    public void free() {
    }

    @Nullable
    public StreamCallbacks getStreamCallbacks() {
        return null;
    }

    private void startEncoding() {
        Log.i(TAG, "encoding starting");
        this.mCameraFramesSource.startEncoding(this.mContext, this.mPreivewSurfaces, this.mMediaSourceConfiguration.getCameraId());
    }

    private void stopEncoding() {
        Log.i(TAG, "encoding stopping");
        EncodingCancellationToken encodingCancellationToken = this.mEncodingCancellationToken;
        if (encodingCancellationToken != null) {
            encodingCancellationToken.cancelEncoding();
            this.mEncodingCancellationToken = null;
        }
    }

    private EncoderWrapper.CodecPrivateDataAvailableListener waitForCodecPrivateData() {
        return new EncoderWrapper.CodecPrivateDataAvailableListener() {
            public void onCodecPrivateDataAvailable(byte[] privateData) {
                AndroidCameraMediaSource.this.updateSinkWithPrivateData(privateData);
            }
        };
    }

    /* access modifiers changed from: private */
    public void updateSinkWithPrivateData(byte[] privateData) {
        try {
            Log.i(TAG, "updating sink with codec private data");
            this.mMediaSourceSink.onCodecPrivateData(privateData);
        } catch (KinesisVideoException e) {
            Log.e(TAG, "error updating sink with codec private data", e);
            throw new RuntimeException("error updating sink with codec private data", e);
        }
    }

    private EncoderWrapper.FrameAvailableListener pushFrameToSink() {
        return new EncoderWrapper.FrameAvailableListener() {
            public void onFrameAvailable(KinesisVideoFrame frame) {
                try {
                    Log.i(AndroidCameraMediaSource.TAG, "updating sink with frame");
                    AndroidCameraMediaSource.this.mMediaSourceSink.onFrame(frame);
                } catch (KinesisVideoException e) {
                    Log.e(AndroidCameraMediaSource.TAG, "error updating sink with frame", e);
                }
            }
        };
    }

    public MediaSourceSink getMediaSourceSink() {
        return this.mMediaSourceSink;
    }
}
