package androidx.camera.core;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.location.Location;
import android.media.AudioRecord;
import android.media.CamcorderProfile;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.VideoCaptureConfig;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.x;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.core.internal.UseCaseEventConfig;
import androidx.camera.core.internal.utils.VideoUtil;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class VideoCapture extends UseCase {
    private static final String AUDIO_MIME_TYPE = "audio/mp4a-latm";
    private static final int[] CamcorderQuality = {8, 6, 5, 4};
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final Defaults DEFAULT_CONFIG = new Defaults();
    private static final int DEQUE_TIMEOUT_USEC = 10000;
    public static final int ERROR_ENCODER = 1;
    public static final int ERROR_FILE_IO = 4;
    public static final int ERROR_INVALID_CAMERA = 5;
    public static final int ERROR_MUXER = 2;
    public static final int ERROR_RECORDING_IN_PROGRESS = 3;
    public static final int ERROR_UNKNOWN = 0;
    private static final String TAG = "VideoCapture";
    private static final String VIDEO_MIME_TYPE = "video/avc";
    private static final short[] sAudioEncoding = {2, 3, 4};
    private int mAudioBitRate;
    private final MediaCodec.BufferInfo mAudioBufferInfo = new MediaCodec.BufferInfo();
    private int mAudioBufferSize;
    private int mAudioChannelCount;
    @NonNull
    private MediaCodec mAudioEncoder;
    private Handler mAudioHandler;
    private HandlerThread mAudioHandlerThread;
    @NonNull
    private AudioRecord mAudioRecorder;
    private int mAudioSampleRate;
    private int mAudioTrackIndex;
    Surface mCameraSurface;
    private DeferrableSurface mDeferrableSurface;
    private final AtomicBoolean mEndOfAudioStreamSignal = new AtomicBoolean(true);
    private final AtomicBoolean mEndOfAudioVideoSignal = new AtomicBoolean(true);
    private final AtomicBoolean mEndOfVideoStreamSignal = new AtomicBoolean(true);
    private final AtomicBoolean mIsFirstAudioSampleWrite = new AtomicBoolean(false);
    private final AtomicBoolean mIsFirstVideoSampleWrite = new AtomicBoolean(false);
    private boolean mIsRecording = false;
    @GuardedBy("mMuxerLock")
    private MediaMuxer mMuxer;
    private final Object mMuxerLock = new Object();
    private boolean mMuxerStarted = false;
    private ParcelFileDescriptor mParcelFileDescriptor;
    @Nullable
    private ListenableFuture<Void> mRecordingFuture = null;
    Uri mSavedVideoUri;
    private final MediaCodec.BufferInfo mVideoBufferInfo = new MediaCodec.BufferInfo();
    @NonNull
    MediaCodec mVideoEncoder;
    private Handler mVideoHandler;
    private HandlerThread mVideoHandlerThread;
    private int mVideoTrackIndex;

    public static final class Metadata {
        @Nullable
        public Location location;
    }

    public interface OnVideoSavedCallback {
        void onError(int i, @NonNull String str, @Nullable Throwable th);

        void onVideoSaved(@NonNull OutputFileResults outputFileResults);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoCaptureError {
    }

    VideoCapture(@NonNull VideoCaptureConfig config) {
        super(config);
    }

    private static MediaFormat createMediaFormat(VideoCaptureConfig config, Size resolution) {
        MediaFormat format = MediaFormat.createVideoFormat(VIDEO_MIME_TYPE, resolution.getWidth(), resolution.getHeight());
        format.setInteger("color-format", 2130708361);
        format.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, config.getBitRate());
        format.setInteger("frame-rate", config.getVideoFrameRate());
        format.setInteger("i-frame-interval", config.getIFrameInterval());
        return format;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig<?> getDefaultConfig(boolean applyDefaultConfig, @NonNull UseCaseConfigFactory factory) {
        Config captureConfig = factory.getConfig(UseCaseConfigFactory.CaptureType.VIDEO_CAPTURE);
        if (applyDefaultConfig) {
            captureConfig = x.b(captureConfig, DEFAULT_CONFIG.getConfig());
        }
        if (captureConfig == null) {
            return null;
        }
        return getUseCaseConfigBuilder(captureConfig).getUseCaseConfig();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onAttached() {
        this.mVideoHandlerThread = new HandlerThread("CameraX-video encoding thread");
        this.mAudioHandlerThread = new HandlerThread("CameraX-audio encoding thread");
        this.mVideoHandlerThread.start();
        this.mVideoHandler = new Handler(this.mVideoHandlerThread.getLooper());
        this.mAudioHandlerThread.start();
        this.mAudioHandler = new Handler(this.mAudioHandlerThread.getLooper());
    }

    /* access modifiers changed from: protected */
    @RequiresPermission("android.permission.RECORD_AUDIO")
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Size onSuggestedResolutionUpdated(@NonNull Size suggestedResolution) {
        if (this.mCameraSurface != null) {
            this.mVideoEncoder.stop();
            this.mVideoEncoder.release();
            this.mAudioEncoder.stop();
            this.mAudioEncoder.release();
            releaseCameraSurface(false);
        }
        try {
            this.mVideoEncoder = MediaCodec.createEncoderByType(VIDEO_MIME_TYPE);
            this.mAudioEncoder = MediaCodec.createEncoderByType(AUDIO_MIME_TYPE);
            setupEncoder(getCameraId(), suggestedResolution);
            return suggestedResolution;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create MediaCodec due to: " + e.getCause());
        }
    }

    @RequiresPermission("android.permission.RECORD_AUDIO")
    /* renamed from: startRecording */
    public void lambda$startRecording$0(@NonNull OutputFileOptions outputFileOptions, @NonNull Executor executor, @NonNull OnVideoSavedCallback callback) {
        Location location;
        Executor executor2 = executor;
        OnVideoSavedCallback onVideoSavedCallback = callback;
        if (Looper.getMainLooper() != Looper.myLooper()) {
            CameraXExecutors.mainThreadExecutor().execute(new p1(this, outputFileOptions, executor2, onVideoSavedCallback));
            return;
        }
        OutputFileOptions outputFileOptions2 = outputFileOptions;
        Logger.i(TAG, "startRecording");
        this.mIsFirstVideoSampleWrite.set(false);
        this.mIsFirstAudioSampleWrite.set(false);
        OnVideoSavedCallback postListener = new VideoSavedListenerWrapper(executor2, onVideoSavedCallback);
        CameraInternal attachedCamera = getCamera();
        if (attachedCamera == null) {
            postListener.onError(5, "Not bound to a Camera [" + this + "]", (Throwable) null);
        } else if (!this.mEndOfAudioVideoSignal.get()) {
            postListener.onError(3, "It is still in video recording!", (Throwable) null);
        } else {
            try {
                this.mAudioRecorder.startRecording();
                AtomicReference<CallbackToFutureAdapter.Completer<Void>> recordingCompleterRef = new AtomicReference<>();
                this.mRecordingFuture = CallbackToFutureAdapter.getFuture(new w1(recordingCompleterRef));
                CallbackToFutureAdapter.Completer<Void> recordingCompleter = (CallbackToFutureAdapter.Completer) Preconditions.checkNotNull(recordingCompleterRef.get());
                this.mRecordingFuture.addListener(new u1(this), CameraXExecutors.mainThreadExecutor());
                try {
                    Logger.i(TAG, "videoEncoder start");
                    this.mVideoEncoder.start();
                    Logger.i(TAG, "audioEncoder start");
                    this.mAudioEncoder.start();
                    try {
                        synchronized (this.mMuxerLock) {
                            MediaMuxer initMediaMuxer = initMediaMuxer(outputFileOptions);
                            this.mMuxer = initMediaMuxer;
                            Preconditions.checkNotNull(initMediaMuxer);
                            this.mMuxer.setOrientationHint(getRelativeRotation(attachedCamera));
                            Metadata metadata = outputFileOptions.getMetadata();
                            if (!(metadata == null || (location = metadata.location) == null)) {
                                this.mMuxer.setLocation((float) location.getLatitude(), (float) metadata.location.getLongitude());
                            }
                        }
                        this.mEndOfVideoStreamSignal.set(false);
                        this.mEndOfAudioStreamSignal.set(false);
                        this.mEndOfAudioVideoSignal.set(false);
                        this.mIsRecording = true;
                        notifyActive();
                        this.mAudioHandler.post(new q1(this, postListener));
                        String cameraId = getCameraId();
                        Size resolution = getAttachedSurfaceResolution();
                        String str = cameraId;
                        String str2 = cameraId;
                        v1 v1Var = r1;
                        Handler handler = this.mVideoHandler;
                        v1 v1Var2 = new v1(this, postListener, str, resolution, recordingCompleter);
                        handler.post(v1Var);
                    } catch (IOException e) {
                        recordingCompleter.set(null);
                        postListener.onError(2, "MediaMuxer creation failed!", e);
                    }
                } catch (IllegalStateException e2) {
                    recordingCompleter.set(null);
                    postListener.onError(1, "Audio/Video encoder start fail", e2);
                }
            } catch (IllegalStateException e3) {
                postListener.onError(1, "AudioRecorder start fail", e3);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$startRecording$2 */
    public /* synthetic */ void c() {
        this.mRecordingFuture = null;
        if (getCamera() != null) {
            setupEncoder(getCameraId(), getAttachedSurfaceResolution());
            notifyReset();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$startRecording$4 */
    public /* synthetic */ void e(OnVideoSavedCallback postListener, String cameraId, Size resolution, CallbackToFutureAdapter.Completer recordingCompleter) {
        if (!videoEncode(postListener, cameraId, resolution)) {
            postListener.onVideoSaved(new OutputFileResults(this.mSavedVideoUri));
            this.mSavedVideoUri = null;
        }
        recordingCompleter.set(null);
    }

    /* renamed from: stopRecording */
    public void lambda$stopRecording$5() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            CameraXExecutors.mainThreadExecutor().execute(new x1(this));
            return;
        }
        Logger.i(TAG, "stopRecording");
        notifyInactive();
        if (!this.mEndOfAudioVideoSignal.get() && this.mIsRecording) {
            this.mEndOfAudioStreamSignal.set(true);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onDetached() {
        lambda$stopRecording$5();
        ListenableFuture<Void> listenableFuture = this.mRecordingFuture;
        if (listenableFuture != null) {
            listenableFuture.addListener(new y1(this), CameraXExecutors.mainThreadExecutor());
        } else {
            lambda$onDetached$6();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: releaseResources */
    public void lambda$onDetached$6() {
        this.mVideoHandlerThread.quitSafely();
        this.mAudioHandlerThread.quitSafely();
        MediaCodec mediaCodec = this.mAudioEncoder;
        if (mediaCodec != null) {
            mediaCodec.release();
            this.mAudioEncoder = null;
        }
        AudioRecord audioRecord = this.mAudioRecorder;
        if (audioRecord != null) {
            audioRecord.release();
            this.mAudioRecorder = null;
        }
        if (this.mCameraSurface != null) {
            releaseCameraSurface(true);
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig.Builder<?, ?, ?> getUseCaseConfigBuilder(@NonNull Config config) {
        return Builder.fromConfig(config);
    }

    @UiThread
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onStateDetached() {
        lambda$stopRecording$5();
    }

    @UiThread
    private void releaseCameraSurface(boolean releaseVideoEncoder) {
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            MediaCodec videoEncoder = this.mVideoEncoder;
            deferrableSurface.close();
            this.mDeferrableSurface.getTerminationFuture().addListener(new t1(releaseVideoEncoder, videoEncoder), CameraXExecutors.mainThreadExecutor());
            if (releaseVideoEncoder) {
                this.mVideoEncoder = null;
            }
            this.mCameraSurface = null;
            this.mDeferrableSurface = null;
        }
    }

    static /* synthetic */ void lambda$releaseCameraSurface$7(boolean releaseVideoEncoder, MediaCodec videoEncoder) {
        if (releaseVideoEncoder && videoEncoder != null) {
            videoEncoder.release();
        }
    }

    public void setTargetRotation(int rotation) {
        setTargetRotationInternal(rotation);
    }

    /* access modifiers changed from: package-private */
    @RequiresPermission("android.permission.RECORD_AUDIO")
    @UiThread
    public void setupEncoder(@NonNull final String cameraId, @NonNull final Size resolution) {
        VideoCaptureConfig config = (VideoCaptureConfig) getCurrentConfig();
        this.mVideoEncoder.reset();
        this.mVideoEncoder.configure(createMediaFormat(config, resolution), (Surface) null, (MediaCrypto) null, 1);
        if (this.mCameraSurface != null) {
            releaseCameraSurface(false);
        }
        Surface cameraSurface = this.mVideoEncoder.createInputSurface();
        this.mCameraSurface = cameraSurface;
        SessionConfig.Builder sessionConfigBuilder = SessionConfig.Builder.createFrom(config);
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
        }
        ImmediateSurface immediateSurface = new ImmediateSurface(this.mCameraSurface);
        this.mDeferrableSurface = immediateSurface;
        ListenableFuture<Void> terminationFuture = immediateSurface.getTerminationFuture();
        Objects.requireNonNull(cameraSurface);
        terminationFuture.addListener(new y0(cameraSurface), CameraXExecutors.mainThreadExecutor());
        sessionConfigBuilder.addSurface(this.mDeferrableSurface);
        sessionConfigBuilder.addErrorListener(new SessionConfig.ErrorListener() {
            @RequiresPermission("android.permission.RECORD_AUDIO")
            public void onError(@NonNull SessionConfig sessionConfig, @NonNull SessionConfig.SessionError error) {
                if (VideoCapture.this.isCurrentCamera(cameraId)) {
                    VideoCapture.this.setupEncoder(cameraId, resolution);
                    VideoCapture.this.notifyReset();
                }
            }
        });
        updateSessionConfig(sessionConfigBuilder.build());
        setAudioParametersByCamcorderProfile(resolution, cameraId);
        this.mAudioEncoder.reset();
        this.mAudioEncoder.configure(createAudioMediaFormat(), (Surface) null, (MediaCrypto) null, 1);
        AudioRecord audioRecord = this.mAudioRecorder;
        if (audioRecord != null) {
            audioRecord.release();
        }
        AudioRecord autoConfigAudioRecordSource = autoConfigAudioRecordSource(config);
        this.mAudioRecorder = autoConfigAudioRecordSource;
        if (autoConfigAudioRecordSource == null) {
            Logger.e(TAG, "AudioRecord object cannot initialized correctly!");
        }
        this.mVideoTrackIndex = -1;
        this.mAudioTrackIndex = -1;
        this.mIsRecording = false;
    }

    private boolean writeVideoEncodedBuffer(int bufferIndex) {
        if (bufferIndex < 0) {
            Logger.e(TAG, "Output buffer should not have negative index: " + bufferIndex);
            return false;
        }
        ByteBuffer outputBuffer = this.mVideoEncoder.getOutputBuffer(bufferIndex);
        if (outputBuffer == null) {
            Logger.d(TAG, "OutputBuffer was null.");
            return false;
        }
        if (this.mAudioTrackIndex >= 0 && this.mVideoTrackIndex >= 0) {
            MediaCodec.BufferInfo bufferInfo = this.mVideoBufferInfo;
            if (bufferInfo.size > 0) {
                outputBuffer.position(bufferInfo.offset);
                MediaCodec.BufferInfo bufferInfo2 = this.mVideoBufferInfo;
                outputBuffer.limit(bufferInfo2.offset + bufferInfo2.size);
                this.mVideoBufferInfo.presentationTimeUs = System.nanoTime() / 1000;
                synchronized (this.mMuxerLock) {
                    if (!this.mIsFirstVideoSampleWrite.get()) {
                        Logger.i(TAG, "First video sample written.");
                        this.mIsFirstVideoSampleWrite.set(true);
                    }
                    this.mMuxer.writeSampleData(this.mVideoTrackIndex, outputBuffer, this.mVideoBufferInfo);
                }
            }
        }
        this.mVideoEncoder.releaseOutputBuffer(bufferIndex, false);
        if ((this.mVideoBufferInfo.flags & 4) != 0) {
            return true;
        }
        return false;
    }

    private boolean writeAudioEncodedBuffer(int bufferIndex) {
        ByteBuffer buffer = getOutputBuffer(this.mAudioEncoder, bufferIndex);
        buffer.position(this.mAudioBufferInfo.offset);
        if (this.mAudioTrackIndex >= 0 && this.mVideoTrackIndex >= 0) {
            MediaCodec.BufferInfo bufferInfo = this.mAudioBufferInfo;
            if (bufferInfo.size > 0 && bufferInfo.presentationTimeUs > 0) {
                try {
                    synchronized (this.mMuxerLock) {
                        if (!this.mIsFirstAudioSampleWrite.get()) {
                            Logger.i(TAG, "First audio sample written.");
                            this.mIsFirstAudioSampleWrite.set(true);
                        }
                        this.mMuxer.writeSampleData(this.mAudioTrackIndex, buffer, this.mAudioBufferInfo);
                    }
                } catch (Exception e) {
                    Logger.e(TAG, "audio error:size=" + this.mAudioBufferInfo.size + "/offset=" + this.mAudioBufferInfo.offset + "/timeUs=" + this.mAudioBufferInfo.presentationTimeUs);
                    e.printStackTrace();
                }
            }
        }
        this.mAudioEncoder.releaseOutputBuffer(bufferIndex, false);
        if ((this.mAudioBufferInfo.flags & 4) != 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean videoEncode(@NonNull OnVideoSavedCallback videoSavedCallback, @NonNull String cameraId, @NonNull Size resolution) {
        boolean errorOccurred;
        boolean errorOccurred2 = false;
        boolean videoEos = false;
        while (!videoEos && !errorOccurred2) {
            if (this.mEndOfVideoStreamSignal.get()) {
                this.mVideoEncoder.signalEndOfInputStream();
                this.mEndOfVideoStreamSignal.set(false);
            }
            int outputBufferId = this.mVideoEncoder.dequeueOutputBuffer(this.mVideoBufferInfo, 10000);
            switch (outputBufferId) {
                case -2:
                    if (this.mMuxerStarted) {
                        videoSavedCallback.onError(1, "Unexpected change in video encoding format.", (Throwable) null);
                        errorOccurred = true;
                    } else {
                        errorOccurred = errorOccurred2;
                    }
                    synchronized (this.mMuxerLock) {
                        int addTrack = this.mMuxer.addTrack(this.mVideoEncoder.getOutputFormat());
                        this.mVideoTrackIndex = addTrack;
                        if (this.mAudioTrackIndex >= 0 && addTrack >= 0) {
                            this.mMuxerStarted = true;
                            Logger.i(TAG, "media mMuxer start");
                            this.mMuxer.start();
                        }
                    }
                    errorOccurred2 = errorOccurred;
                    break;
                case -1:
                    break;
                default:
                    videoEos = writeVideoEncodedBuffer(outputBufferId);
                    break;
            }
        }
        try {
            Logger.i(TAG, "videoEncoder stop");
            this.mVideoEncoder.stop();
        } catch (IllegalStateException e) {
            videoSavedCallback.onError(1, "Video encoder stop failed!", e);
            errorOccurred2 = true;
        }
        try {
            synchronized (this.mMuxerLock) {
                MediaMuxer mediaMuxer = this.mMuxer;
                if (mediaMuxer != null) {
                    if (this.mMuxerStarted) {
                        mediaMuxer.stop();
                    }
                    this.mMuxer.release();
                    this.mMuxer = null;
                }
            }
        } catch (IllegalStateException e2) {
            videoSavedCallback.onError(2, "Muxer stop failed!", e2);
            errorOccurred2 = true;
        }
        ParcelFileDescriptor parcelFileDescriptor = this.mParcelFileDescriptor;
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
                this.mParcelFileDescriptor = null;
            } catch (IOException e3) {
                videoSavedCallback.onError(2, "File descriptor close failed!", e3);
                errorOccurred2 = true;
            }
        }
        this.mMuxerStarted = false;
        this.mEndOfAudioVideoSignal.set(true);
        Logger.i(TAG, "Video encode thread end.");
        return errorOccurred2;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* renamed from: audioEncode */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean lambda$startRecording$3(androidx.camera.core.VideoCapture.OnVideoSavedCallback r18) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r0 = 0
            r3 = 0
            r4 = r3
            r3 = r0
        L_0x0009:
            r6 = 1
            r7 = 0
            if (r3 != 0) goto L_0x00ce
            boolean r0 = r1.mIsRecording
            if (r0 == 0) goto L_0x00ce
            java.util.concurrent.atomic.AtomicBoolean r0 = r1.mEndOfAudioStreamSignal
            boolean r0 = r0.get()
            if (r0 == 0) goto L_0x0020
            java.util.concurrent.atomic.AtomicBoolean r0 = r1.mEndOfAudioStreamSignal
            r0.set(r7)
            r1.mIsRecording = r7
        L_0x0020:
            android.media.MediaCodec r0 = r1.mAudioEncoder
            if (r0 == 0) goto L_0x0009
            android.media.AudioRecord r8 = r1.mAudioRecorder
            if (r8 == 0) goto L_0x0009
            r8 = -1
            int r8 = r0.dequeueInputBuffer(r8)
            if (r8 < 0) goto L_0x005d
            android.media.MediaCodec r0 = r1.mAudioEncoder
            java.nio.ByteBuffer r0 = r1.getInputBuffer(r0, r8)
            r0.clear()
            android.media.AudioRecord r9 = r1.mAudioRecorder
            int r10 = r1.mAudioBufferSize
            int r9 = r9.read(r0, r10)
            if (r9 <= 0) goto L_0x005d
            android.media.MediaCodec r10 = r1.mAudioEncoder
            r12 = 0
            long r13 = java.lang.System.nanoTime()
            r15 = 1000(0x3e8, double:4.94E-321)
            long r14 = r13 / r15
            boolean r11 = r1.mIsRecording
            if (r11 == 0) goto L_0x0055
            r16 = r7
            goto L_0x0058
        L_0x0055:
            r11 = 4
            r16 = r11
        L_0x0058:
            r11 = r8
            r13 = r9
            r10.queueInputBuffer(r11, r12, r13, r14, r16)
        L_0x005d:
            android.media.MediaCodec r0 = r1.mAudioEncoder
            android.media.MediaCodec$BufferInfo r9 = r1.mAudioBufferInfo
            r10 = 0
            int r9 = r0.dequeueOutputBuffer(r9, r10)
            switch(r9) {
                case -2: goto L_0x007e;
                case -1: goto L_0x007d;
                default: goto L_0x006a;
            }
        L_0x006a:
            android.media.MediaCodec$BufferInfo r0 = r1.mAudioBufferInfo
            long r10 = r0.presentationTimeUs
            int r0 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x00a1
            boolean r0 = r1.writeAudioEncodedBuffer(r9)
            android.media.MediaCodec$BufferInfo r3 = r1.mAudioBufferInfo
            long r3 = r3.presentationTimeUs
            r4 = r3
            r3 = r0
            goto L_0x00c8
        L_0x007d:
            goto L_0x00c8
        L_0x007e:
            java.lang.Object r10 = r1.mMuxerLock
            monitor-enter(r10)
            android.media.MediaMuxer r0 = r1.mMuxer     // Catch:{ all -> 0x009e }
            android.media.MediaCodec r11 = r1.mAudioEncoder     // Catch:{ all -> 0x009e }
            android.media.MediaFormat r11 = r11.getOutputFormat()     // Catch:{ all -> 0x009e }
            int r0 = r0.addTrack(r11)     // Catch:{ all -> 0x009e }
            r1.mAudioTrackIndex = r0     // Catch:{ all -> 0x009e }
            if (r0 < 0) goto L_0x009c
            int r0 = r1.mVideoTrackIndex     // Catch:{ all -> 0x009e }
            if (r0 < 0) goto L_0x009c
            r1.mMuxerStarted = r6     // Catch:{ all -> 0x009e }
            android.media.MediaMuxer r0 = r1.mMuxer     // Catch:{ all -> 0x009e }
            r0.start()     // Catch:{ all -> 0x009e }
        L_0x009c:
            monitor-exit(r10)     // Catch:{ all -> 0x009e }
            goto L_0x00c8
        L_0x009e:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x009e }
            throw r0
        L_0x00a1:
            java.lang.String r0 = "VideoCapture"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Drops frame, current frame's timestamp "
            r10.append(r11)
            android.media.MediaCodec$BufferInfo r11 = r1.mAudioBufferInfo
            long r11 = r11.presentationTimeUs
            r10.append(r11)
            java.lang.String r11 = " is earlier that last frame "
            r10.append(r11)
            r10.append(r4)
            java.lang.String r10 = r10.toString()
            androidx.camera.core.Logger.w(r0, r10)
            android.media.MediaCodec r0 = r1.mAudioEncoder
            r0.releaseOutputBuffer(r9, r7)
        L_0x00c8:
            if (r9 < 0) goto L_0x00cc
            if (r3 == 0) goto L_0x005d
        L_0x00cc:
            goto L_0x0009
        L_0x00ce:
            java.lang.String r0 = "VideoCapture"
            java.lang.String r8 = "audioRecorder stop"
            androidx.camera.core.Logger.i(r0, r8)     // Catch:{ IllegalStateException -> 0x00db }
            android.media.AudioRecord r0 = r1.mAudioRecorder     // Catch:{ IllegalStateException -> 0x00db }
            r0.stop()     // Catch:{ IllegalStateException -> 0x00db }
            goto L_0x00e1
        L_0x00db:
            r0 = move-exception
            java.lang.String r8 = "Audio recorder stop failed!"
            r2.onError(r6, r8, r0)
        L_0x00e1:
            android.media.MediaCodec r0 = r1.mAudioEncoder     // Catch:{ IllegalStateException -> 0x00e7 }
            r0.stop()     // Catch:{ IllegalStateException -> 0x00e7 }
            goto L_0x00ed
        L_0x00e7:
            r0 = move-exception
            java.lang.String r8 = "Audio encoder stop failed!"
            r2.onError(r6, r8, r0)
        L_0x00ed:
            java.lang.String r0 = "VideoCapture"
            java.lang.String r8 = "Audio encode thread end"
            androidx.camera.core.Logger.i(r0, r8)
            java.util.concurrent.atomic.AtomicBoolean r0 = r1.mEndOfVideoStreamSignal
            r0.set(r6)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.VideoCapture.lambda$startRecording$3(androidx.camera.core.VideoCapture$OnVideoSavedCallback):boolean");
    }

    private ByteBuffer getInputBuffer(MediaCodec codec, int index) {
        return codec.getInputBuffer(index);
    }

    private ByteBuffer getOutputBuffer(MediaCodec codec, int index) {
        return codec.getOutputBuffer(index);
    }

    private MediaFormat createAudioMediaFormat() {
        MediaFormat format = MediaFormat.createAudioFormat(AUDIO_MIME_TYPE, this.mAudioSampleRate, this.mAudioChannelCount);
        format.setInteger("aac-profile", 2);
        format.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, this.mAudioBitRate);
        return format;
    }

    @RequiresPermission("android.permission.RECORD_AUDIO")
    private AudioRecord autoConfigAudioRecordSource(VideoCaptureConfig config) {
        int i;
        short[] sArr = sAudioEncoding;
        int length = sArr.length;
        int i2 = 0;
        while (i2 < length) {
            short audioFormat = sArr[i2];
            if (this.mAudioChannelCount == 1) {
                i = 16;
            } else {
                i = 12;
            }
            int channelConfig = i;
            int source = config.getAudioRecordSource();
            try {
                int bufferSize = AudioRecord.getMinBufferSize(this.mAudioSampleRate, channelConfig, audioFormat);
                if (bufferSize <= 0) {
                    bufferSize = config.getAudioMinBufferSize();
                }
                AudioRecord recorder = new AudioRecord(source, this.mAudioSampleRate, channelConfig, audioFormat, bufferSize * 2);
                if (recorder.getState() == 1) {
                    this.mAudioBufferSize = bufferSize;
                    Logger.i(TAG, "source: " + source + " audioSampleRate: " + this.mAudioSampleRate + " channelConfig: " + channelConfig + " audioFormat: " + audioFormat + " bufferSize: " + bufferSize);
                    return recorder;
                }
                i2++;
            } catch (Exception e) {
                Logger.e(TAG, "Exception, keep trying.", e);
            }
        }
        return null;
    }

    private void setAudioParametersByCamcorderProfile(Size currentResolution, String cameraId) {
        boolean isCamcorderProfileFound = false;
        try {
            int[] iArr = CamcorderQuality;
            int length = iArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                int quality = iArr[i];
                if (CamcorderProfile.hasProfile(Integer.parseInt(cameraId), quality)) {
                    CamcorderProfile profile = CamcorderProfile.get(Integer.parseInt(cameraId), quality);
                    if (currentResolution.getWidth() == profile.videoFrameWidth && currentResolution.getHeight() == profile.videoFrameHeight) {
                        this.mAudioChannelCount = profile.audioChannels;
                        this.mAudioSampleRate = profile.audioSampleRate;
                        this.mAudioBitRate = profile.audioBitRate;
                        isCamcorderProfileFound = true;
                        break;
                    }
                }
                i++;
            }
        } catch (NumberFormatException e) {
            Logger.i(TAG, "The camera Id is not an integer because the camera may be a removable device. Use the default values for the audio related settings.");
        }
        if (!isCamcorderProfileFound) {
            VideoCaptureConfig config = (VideoCaptureConfig) getCurrentConfig();
            this.mAudioChannelCount = config.getAudioChannelCount();
            this.mAudioSampleRate = config.getAudioSampleRate();
            this.mAudioBitRate = config.getAudioBitRate();
        }
    }

    @SuppressLint({"UnsafeNewApiCall"})
    @NonNull
    private MediaMuxer initMediaMuxer(@NonNull OutputFileOptions outputFileOptions) {
        ContentValues values;
        MediaMuxer mediaMuxer;
        if (outputFileOptions.isSavingToFile()) {
            File savedVideoFile = outputFileOptions.getFile();
            this.mSavedVideoUri = Uri.fromFile(outputFileOptions.getFile());
            return new MediaMuxer(savedVideoFile.getAbsolutePath(), 0);
        } else if (outputFileOptions.isSavingToFileDescriptor()) {
            if (Build.VERSION.SDK_INT >= 26) {
                return new MediaMuxer(outputFileOptions.getFileDescriptor(), 0);
            }
            throw new IllegalArgumentException("Using a FileDescriptor to record a video is only supported for Android 8.0 or above.");
        } else if (outputFileOptions.isSavingToMediaStore()) {
            if (outputFileOptions.getContentValues() != null) {
                values = new ContentValues(outputFileOptions.getContentValues());
            } else {
                values = new ContentValues();
            }
            Uri insert = outputFileOptions.getContentResolver().insert(outputFileOptions.getSaveCollection(), values);
            this.mSavedVideoUri = insert;
            if (insert != null) {
                try {
                    if (Build.VERSION.SDK_INT < 26) {
                        String savedLocationPath = VideoUtil.getAbsolutePathFromUri(outputFileOptions.getContentResolver(), this.mSavedVideoUri);
                        Logger.i(TAG, "Saved Location Path: " + savedLocationPath);
                        mediaMuxer = new MediaMuxer(savedLocationPath, 0);
                    } else {
                        this.mParcelFileDescriptor = outputFileOptions.getContentResolver().openFileDescriptor(this.mSavedVideoUri, "rw");
                        mediaMuxer = new MediaMuxer(this.mParcelFileDescriptor.getFileDescriptor(), 0);
                    }
                    return mediaMuxer;
                } catch (IOException e) {
                    this.mSavedVideoUri = null;
                    throw e;
                }
            } else {
                throw new IOException("Invalid Uri!");
            }
        } else {
            throw new IllegalArgumentException("The OutputFileOptions should assign before recording");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class Defaults implements ConfigProvider<VideoCaptureConfig> {
        private static final int DEFAULT_ASPECT_RATIO = 1;
        private static final int DEFAULT_AUDIO_BIT_RATE = 64000;
        private static final int DEFAULT_AUDIO_CHANNEL_COUNT = 1;
        private static final int DEFAULT_AUDIO_MIN_BUFFER_SIZE = 1024;
        private static final int DEFAULT_AUDIO_RECORD_SOURCE = 1;
        private static final int DEFAULT_AUDIO_SAMPLE_RATE = 8000;
        private static final int DEFAULT_BIT_RATE = 8388608;
        private static final VideoCaptureConfig DEFAULT_CONFIG;
        private static final int DEFAULT_INTRA_FRAME_INTERVAL = 1;
        private static final Size DEFAULT_MAX_RESOLUTION;
        private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 3;
        private static final int DEFAULT_VIDEO_FRAME_RATE = 30;

        static {
            Size size = new Size(1920, 1080);
            DEFAULT_MAX_RESOLUTION = size;
            DEFAULT_CONFIG = new Builder().setVideoFrameRate(30).setBitRate(8388608).setIFrameInterval(1).setAudioBitRate(DEFAULT_AUDIO_BIT_RATE).setAudioSampleRate(DEFAULT_AUDIO_SAMPLE_RATE).setAudioChannelCount(1).setAudioRecordSource(1).setAudioMinBufferSize(1024).setMaxResolution(size).setSurfaceOccupancyPriority(3).setTargetAspectRatio(1).getUseCaseConfig();
        }

        @NonNull
        public VideoCaptureConfig getConfig() {
            return DEFAULT_CONFIG;
        }
    }

    public static final class VideoSavedListenerWrapper implements OnVideoSavedCallback {
        @NonNull
        Executor mExecutor;
        @NonNull
        OnVideoSavedCallback mOnVideoSavedCallback;

        VideoSavedListenerWrapper(@NonNull Executor executor, @NonNull OnVideoSavedCallback onVideoSavedCallback) {
            this.mExecutor = executor;
            this.mOnVideoSavedCallback = onVideoSavedCallback;
        }

        /* access modifiers changed from: private */
        /* renamed from: lambda$onVideoSaved$0 */
        public /* synthetic */ void b(OutputFileResults outputFileResults) {
            this.mOnVideoSavedCallback.onVideoSaved(outputFileResults);
        }

        public void onVideoSaved(@NonNull OutputFileResults outputFileResults) {
            try {
                this.mExecutor.execute(new s1(this, outputFileResults));
            } catch (RejectedExecutionException e) {
                Logger.e(VideoCapture.TAG, "Unable to post to the supplied executor.");
            }
        }

        public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
            try {
                this.mExecutor.execute(new r1(this, videoCaptureError, message, cause));
            } catch (RejectedExecutionException e) {
                Logger.e(VideoCapture.TAG, "Unable to post to the supplied executor.");
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: lambda$onError$1 */
        public /* synthetic */ void a(int videoCaptureError, String message, Throwable cause) {
            this.mOnVideoSavedCallback.onError(videoCaptureError, message, cause);
        }
    }

    public static final class Builder implements UseCaseConfig.Builder<VideoCapture, VideoCaptureConfig, Builder>, ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder> {
        private final MutableOptionsBundle mMutableConfig;

        public Builder() {
            this(MutableOptionsBundle.create());
        }

        private Builder(@NonNull MutableOptionsBundle mutableConfig) {
            Class<VideoCapture> cls = VideoCapture.class;
            this.mMutableConfig = mutableConfig;
            Class<?> oldConfigClass = (Class) mutableConfig.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
            if (oldConfigClass == null || oldConfigClass.equals(cls)) {
                setTargetClass(cls);
                return;
            }
            throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + oldConfigClass);
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        static Builder fromConfig(@NonNull Config configuration) {
            return new Builder(MutableOptionsBundle.from(configuration));
        }

        @NonNull
        public static Builder fromConfig(@NonNull VideoCaptureConfig configuration) {
            return new Builder(MutableOptionsBundle.from(configuration));
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public VideoCaptureConfig getUseCaseConfig() {
            return new VideoCaptureConfig(OptionsBundle.from(this.mMutableConfig));
        }

        @NonNull
        public VideoCapture build() {
            if (getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, null) == null || getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null) == null) {
                return new VideoCapture(getUseCaseConfig());
            }
            throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setVideoFrameRate(int videoFrameRate) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_VIDEO_FRAME_RATE, Integer.valueOf(videoFrameRate));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setBitRate(int bitRate) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_BIT_RATE, Integer.valueOf(bitRate));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setIFrameInterval(int interval) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_INTRA_FRAME_INTERVAL, Integer.valueOf(interval));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAudioBitRate(int bitRate) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_BIT_RATE, Integer.valueOf(bitRate));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAudioSampleRate(int sampleRate) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_SAMPLE_RATE, Integer.valueOf(sampleRate));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAudioChannelCount(int channelCount) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_CHANNEL_COUNT, Integer.valueOf(channelCount));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAudioRecordSource(int source) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_RECORD_SOURCE, Integer.valueOf(source));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAudioMinBufferSize(int minBufferSize) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_MIN_BUFFER_SIZE, Integer.valueOf(minBufferSize));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetClass(@NonNull Class<VideoCapture> targetClass) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_CLASS, targetClass);
            if (getMutableConfig().retrieveOption(TargetConfig.OPTION_TARGET_NAME, null) == null) {
                setTargetName(targetClass.getCanonicalName() + "-" + UUID.randomUUID());
            }
            return this;
        }

        @NonNull
        public Builder setTargetName(@NonNull String targetName) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_NAME, targetName);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetAspectRatio(int aspectRatio) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, Integer.valueOf(aspectRatio));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetRotation(int rotation) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ROTATION, Integer.valueOf(rotation));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetResolution(@NonNull Size resolution) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, resolution);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultResolution(@NonNull Size resolution) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, resolution);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setMaxResolution(@NonNull Size resolution) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_MAX_RESOLUTION, resolution);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSupportedResolutions(@NonNull List<Pair<Integer, Size[]>> resolutions) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS, resolutions);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setBackgroundExecutor(@NonNull Executor executor) {
            getMutableConfig().insertOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR, executor);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultSessionConfig(@NonNull SessionConfig sessionConfig) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG, sessionConfig);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultCaptureConfig(@NonNull CaptureConfig captureConfig) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG, captureConfig);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSessionOptionUnpacker(@NonNull SessionConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCaptureOptionUnpacker(@NonNull CaptureConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSurfaceOccupancyPriority(int priority) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(priority));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public Builder setCameraSelector(@NonNull CameraSelector cameraSelector) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, cameraSelector);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setUseCaseEventCallback(@NonNull UseCase.EventCallback useCaseEventCallback) {
            getMutableConfig().insertOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK, useCaseEventCallback);
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAttachedUseCasesUpdateListener(@NonNull Consumer<Collection<UseCase>> attachedUseCasesUpdateListener) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_ATTACHED_USE_CASES_UPDATE_LISTENER, attachedUseCasesUpdateListener);
            return this;
        }
    }

    public static class OutputFileResults {
        @Nullable
        private Uri mSavedUri;

        OutputFileResults(@Nullable Uri savedUri) {
            this.mSavedUri = savedUri;
        }

        @Nullable
        public Uri getSavedUri() {
            return this.mSavedUri;
        }
    }

    public static final class OutputFileOptions {
        private static final Metadata EMPTY_METADATA = new Metadata();
        @Nullable
        private final ContentResolver mContentResolver;
        @Nullable
        private final ContentValues mContentValues;
        @Nullable
        private final File mFile;
        @Nullable
        private final FileDescriptor mFileDescriptor;
        @Nullable
        private final Metadata mMetadata;
        @Nullable
        private final Uri mSaveCollection;

        OutputFileOptions(@Nullable File file, @Nullable FileDescriptor fileDescriptor, @Nullable ContentResolver contentResolver, @Nullable Uri saveCollection, @Nullable ContentValues contentValues, @Nullable Metadata metadata) {
            this.mFile = file;
            this.mFileDescriptor = fileDescriptor;
            this.mContentResolver = contentResolver;
            this.mSaveCollection = saveCollection;
            this.mContentValues = contentValues;
            this.mMetadata = metadata == null ? EMPTY_METADATA : metadata;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public File getFile() {
            return this.mFile;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public FileDescriptor getFileDescriptor() {
            return this.mFileDescriptor;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public ContentResolver getContentResolver() {
            return this.mContentResolver;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public Uri getSaveCollection() {
            return this.mSaveCollection;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public ContentValues getContentValues() {
            return this.mContentValues;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public Metadata getMetadata() {
            return this.mMetadata;
        }

        /* access modifiers changed from: package-private */
        public boolean isSavingToMediaStore() {
            return (getSaveCollection() == null || getContentResolver() == null || getContentValues() == null) ? false : true;
        }

        /* access modifiers changed from: package-private */
        public boolean isSavingToFile() {
            return getFile() != null;
        }

        /* access modifiers changed from: package-private */
        public boolean isSavingToFileDescriptor() {
            return getFileDescriptor() != null;
        }

        public static final class Builder {
            @Nullable
            private ContentResolver mContentResolver;
            @Nullable
            private ContentValues mContentValues;
            @Nullable
            private File mFile;
            @Nullable
            private FileDescriptor mFileDescriptor;
            @Nullable
            private Metadata mMetadata;
            @Nullable
            private Uri mSaveCollection;

            public Builder(@NonNull File file) {
                this.mFile = file;
            }

            public Builder(@NonNull FileDescriptor fileDescriptor) {
                Preconditions.checkArgument(Build.VERSION.SDK_INT >= 26, "Using a FileDescriptor to record a video is only supported for Android 8.0 or above.");
                this.mFileDescriptor = fileDescriptor;
            }

            public Builder(@NonNull ContentResolver contentResolver, @NonNull Uri saveCollection, @NonNull ContentValues contentValues) {
                this.mContentResolver = contentResolver;
                this.mSaveCollection = saveCollection;
                this.mContentValues = contentValues;
            }

            @NonNull
            public Builder setMetadata(@NonNull Metadata metadata) {
                this.mMetadata = metadata;
                return this;
            }

            @NonNull
            public OutputFileOptions build() {
                return new OutputFileOptions(this.mFile, this.mFileDescriptor, this.mContentResolver, this.mSaveCollection, this.mContentValues, this.mMetadata);
            }
        }
    }
}
