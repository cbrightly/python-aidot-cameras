package org.webrtc;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.media.MediaCodec;
import android.opengl.GLES20;
import android.os.Bundle;
import android.view.Surface;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import org.webrtc.EglBase14;
import org.webrtc.EncodedImage;
import org.webrtc.ThreadUtils;
import org.webrtc.VideoEncoder;
import org.webrtc.VideoFrame;

@TargetApi(19)
public class HardwareVideoEncoder implements VideoEncoder {
    private static final int DEQUEUE_OUTPUT_BUFFER_TIMEOUT_US = 100000;
    private static final String KEY_BITRATE_MODE = "bitrate-mode";
    private static final int MAX_ENCODER_Q_SIZE = 2;
    private static final int MAX_VIDEO_FRAMERATE = 30;
    private static final int MEDIA_CODEC_RELEASE_TIMEOUT_MS = 5000;
    private static final int REQUIRED_RESOLUTION_ALIGNMENT = 16;
    private static final String TAG = "HardwareVideoEncoder";
    private static final int VIDEO_AVC_LEVEL_3 = 256;
    private static final int VIDEO_AVC_PROFILE_HIGH = 8;
    private static final int VIDEO_ControlRateConstant = 2;
    private int adjustedBitrate;
    private boolean automaticResizeOn;
    private final BitrateAdjuster bitrateAdjuster;
    private VideoEncoder.Callback callback;
    @Nullable
    private MediaCodecWrapper codec;
    private final String codecName;
    private final VideoCodecMimeType codecType;
    @Nullable
    private ByteBuffer configBuffer;
    private final ThreadUtils.ThreadChecker encodeThreadChecker;
    private final long forcedKeyFrameNs;
    private int height;
    private final int keyFrameIntervalSec;
    private long lastKeyFrameNs;
    private final MediaCodecWrapperFactory mediaCodecWrapperFactory;
    private long nextPresentationTimestampUs;
    @Nullable
    private ByteBuffer[] outputBuffers;
    private final BusyCount outputBuffersBusyCount;
    private final BlockingDeque<EncodedImage.Builder> outputBuilders = new LinkedBlockingDeque();
    @Nullable
    private Thread outputThread;
    private final ThreadUtils.ThreadChecker outputThreadChecker;
    private final Map<String, String> params;
    /* access modifiers changed from: private */
    public volatile boolean running;
    private final EglBase14.Context sharedContext;
    @Nullable
    private volatile Exception shutdownException;
    private final Integer surfaceColorFormat;
    private final GlRectDrawer textureDrawer = new GlRectDrawer();
    @Nullable
    private EglBase14 textureEglBase;
    @Nullable
    private Surface textureInputSurface;
    private boolean useSurfaceMode;
    private final VideoFrameDrawer videoFrameDrawer = new VideoFrameDrawer();
    private int width;
    private final Integer yuvColorFormat;
    private final YuvFormat yuvFormat;

    public /* synthetic */ long createNativeVideoEncoder() {
        return v0.a(this);
    }

    public /* synthetic */ VideoEncoder.ResolutionBitrateLimits[] getResolutionBitrateLimits() {
        return v0.c(this);
    }

    public /* synthetic */ boolean isHardwareEncoder() {
        return v0.d(this);
    }

    public static class BusyCount {
        private int count;
        private final Object countLock;

        private BusyCount() {
            this.countLock = new Object();
        }

        public void increment() {
            synchronized (this.countLock) {
                this.count++;
            }
        }

        public void decrement() {
            synchronized (this.countLock) {
                int i = this.count - 1;
                this.count = i;
                if (i == 0) {
                    this.countLock.notifyAll();
                }
            }
        }

        public void waitForZero() {
            boolean wasInterrupted = false;
            synchronized (this.countLock) {
                while (this.count > 0) {
                    try {
                        this.countLock.wait();
                    } catch (InterruptedException e) {
                        Logging.e(HardwareVideoEncoder.TAG, "Interrupted while waiting on busy count", e);
                        wasInterrupted = true;
                    }
                }
            }
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public HardwareVideoEncoder(MediaCodecWrapperFactory mediaCodecWrapperFactory2, String codecName2, VideoCodecMimeType codecType2, Integer surfaceColorFormat2, Integer yuvColorFormat2, Map<String, String> params2, int keyFrameIntervalSec2, int forceKeyFrameIntervalMs, BitrateAdjuster bitrateAdjuster2, EglBase14.Context sharedContext2) {
        ThreadUtils.ThreadChecker threadChecker = new ThreadUtils.ThreadChecker();
        this.encodeThreadChecker = threadChecker;
        this.outputThreadChecker = new ThreadUtils.ThreadChecker();
        this.outputBuffersBusyCount = new BusyCount();
        this.mediaCodecWrapperFactory = mediaCodecWrapperFactory2;
        this.codecName = codecName2;
        this.codecType = codecType2;
        this.surfaceColorFormat = surfaceColorFormat2;
        this.yuvColorFormat = yuvColorFormat2;
        this.yuvFormat = YuvFormat.valueOf(yuvColorFormat2.intValue());
        this.params = params2;
        this.keyFrameIntervalSec = keyFrameIntervalSec2;
        this.forcedKeyFrameNs = TimeUnit.MILLISECONDS.toNanos((long) forceKeyFrameIntervalMs);
        this.bitrateAdjuster = bitrateAdjuster2;
        this.sharedContext = sharedContext2;
        threadChecker.detachThread();
    }

    public VideoCodecStatus initEncode(VideoEncoder.Settings settings, VideoEncoder.Callback callback2) {
        int i;
        this.encodeThreadChecker.checkIsOnValidThread();
        this.callback = callback2;
        this.automaticResizeOn = settings.automaticResizeOn;
        int i2 = settings.width;
        if (i2 % 16 == 0) {
            int i3 = settings.height;
            if (i3 % 16 == 0) {
                this.width = i2;
                this.height = i3;
                this.useSurfaceMode = canUseSurface();
                int i4 = settings.startBitrate;
                if (!(i4 == 0 || (i = settings.maxFramerate) == 0)) {
                    this.bitrateAdjuster.setTargets(i4 * 1000, (double) i);
                }
                this.adjustedBitrate = this.bitrateAdjuster.getAdjustedBitrateBps();
                Logging.d(TAG, "initEncode: " + this.width + " x " + this.height + ". @ " + settings.startBitrate + "kbps. Fps: " + settings.maxFramerate + " Use surface mode: " + this.useSurfaceMode);
                return initEncodeInternal();
            }
        }
        Logging.e(TAG, "MediaCodec is only tested with resolutions that are 16x16 aligned.");
        return VideoCodecStatus.ERR_SIZE;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.webrtc.VideoCodecStatus initEncodeInternal() {
        /*
            r8 = this;
            java.lang.String r0 = "HardwareVideoEncoder"
            org.webrtc.ThreadUtils$ThreadChecker r1 = r8.encodeThreadChecker
            r1.checkIsOnValidThread()
            r1 = 0
            r8.nextPresentationTimestampUs = r1
            r1 = -1
            r8.lastKeyFrameNs = r1
            org.webrtc.MediaCodecWrapperFactory r1 = r8.mediaCodecWrapperFactory     // Catch:{ IOException -> 0x011b, IllegalArgumentException -> 0x0119 }
            java.lang.String r2 = r8.codecName     // Catch:{ IOException -> 0x011b, IllegalArgumentException -> 0x0119 }
            org.webrtc.MediaCodecWrapper r1 = r1.createByCodecName(r2)     // Catch:{ IOException -> 0x011b, IllegalArgumentException -> 0x0119 }
            r8.codec = r1     // Catch:{ IOException -> 0x011b, IllegalArgumentException -> 0x0119 }
            boolean r1 = r8.useSurfaceMode
            if (r1 == 0) goto L_0x0021
            java.lang.Integer r1 = r8.surfaceColorFormat
            goto L_0x0023
        L_0x0021:
            java.lang.Integer r1 = r8.yuvColorFormat
        L_0x0023:
            int r1 = r1.intValue()
            org.webrtc.VideoCodecMimeType r2 = r8.codecType     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r2 = r2.mimeType()     // Catch:{ IllegalStateException -> 0x010d }
            int r3 = r8.width     // Catch:{ IllegalStateException -> 0x010d }
            int r4 = r8.height     // Catch:{ IllegalStateException -> 0x010d }
            android.media.MediaFormat r2 = android.media.MediaFormat.createVideoFormat(r2, r3, r4)     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r3 = "bitrate"
            int r4 = r8.adjustedBitrate     // Catch:{ IllegalStateException -> 0x010d }
            r2.setInteger(r3, r4)     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r3 = "bitrate-mode"
            r4 = 2
            r2.setInteger(r3, r4)     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r3 = "color-format"
            r2.setInteger(r3, r1)     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r3 = "frame-rate"
            org.webrtc.BitrateAdjuster r4 = r8.bitrateAdjuster     // Catch:{ IllegalStateException -> 0x010d }
            double r4 = r4.getAdjustedFramerateFps()     // Catch:{ IllegalStateException -> 0x010d }
            float r4 = (float) r4     // Catch:{ IllegalStateException -> 0x010d }
            r2.setFloat(r3, r4)     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r3 = "i-frame-interval"
            int r4 = r8.keyFrameIntervalSec     // Catch:{ IllegalStateException -> 0x010d }
            r2.setInteger(r3, r4)     // Catch:{ IllegalStateException -> 0x010d }
            org.webrtc.VideoCodecMimeType r3 = r8.codecType     // Catch:{ IllegalStateException -> 0x010d }
            org.webrtc.VideoCodecMimeType r4 = org.webrtc.VideoCodecMimeType.H264     // Catch:{ IllegalStateException -> 0x010d }
            r5 = 1
            if (r3 != r4) goto L_0x00b2
            java.util.Map<java.lang.String, java.lang.String> r3 = r8.params     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r4 = "profile-level-id"
            java.lang.Object r3 = r3.get(r4)     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r4 = "42e01f"
            if (r3 != 0) goto L_0x0070
            r3 = r4
        L_0x0070:
            r6 = -1
            int r7 = r3.hashCode()     // Catch:{ IllegalStateException -> 0x010d }
            switch(r7) {
                case 1537948542: goto L_0x0083;
                case 1595523974: goto L_0x0079;
                default: goto L_0x0078;
            }     // Catch:{ IllegalStateException -> 0x010d }
        L_0x0078:
            goto L_0x008a
        L_0x0079:
            java.lang.String r4 = "640c1f"
            boolean r4 = r3.equals(r4)     // Catch:{ IllegalStateException -> 0x010d }
            if (r4 == 0) goto L_0x0078
            r6 = 0
            goto L_0x008a
        L_0x0083:
            boolean r4 = r3.equals(r4)     // Catch:{ IllegalStateException -> 0x010d }
            if (r4 == 0) goto L_0x0078
            r6 = r5
        L_0x008a:
            switch(r6) {
                case 0: goto L_0x008f;
                case 1: goto L_0x008e;
                default: goto L_0x008d;
            }     // Catch:{ IllegalStateException -> 0x010d }
        L_0x008d:
            goto L_0x009e
        L_0x008e:
            goto L_0x00b2
        L_0x008f:
            java.lang.String r4 = "profile"
            r6 = 8
            r2.setInteger(r4, r6)     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r4 = "level"
            r6 = 256(0x100, float:3.59E-43)
            r2.setInteger(r4, r6)     // Catch:{ IllegalStateException -> 0x010d }
            goto L_0x00b2
        L_0x009e:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IllegalStateException -> 0x010d }
            r4.<init>()     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r6 = "Unknown profile level id: "
            r4.append(r6)     // Catch:{ IllegalStateException -> 0x010d }
            r4.append(r3)     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r4 = r4.toString()     // Catch:{ IllegalStateException -> 0x010d }
            org.webrtc.Logging.w(r0, r4)     // Catch:{ IllegalStateException -> 0x010d }
        L_0x00b2:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IllegalStateException -> 0x010d }
            r3.<init>()     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r4 = "Format: "
            r3.append(r4)     // Catch:{ IllegalStateException -> 0x010d }
            r3.append(r2)     // Catch:{ IllegalStateException -> 0x010d }
            java.lang.String r3 = r3.toString()     // Catch:{ IllegalStateException -> 0x010d }
            org.webrtc.Logging.d(r0, r3)     // Catch:{ IllegalStateException -> 0x010d }
            org.webrtc.MediaCodecWrapper r3 = r8.codec     // Catch:{ IllegalStateException -> 0x010d }
            r4 = 0
            r3.configure(r2, r4, r4, r5)     // Catch:{ IllegalStateException -> 0x010d }
            boolean r3 = r8.useSurfaceMode     // Catch:{ IllegalStateException -> 0x010d }
            if (r3 == 0) goto L_0x00ec
            org.webrtc.EglBase14$Context r3 = r8.sharedContext     // Catch:{ IllegalStateException -> 0x010d }
            int[] r4 = org.webrtc.EglBase.CONFIG_RECORDABLE     // Catch:{ IllegalStateException -> 0x010d }
            org.webrtc.EglBase14 r3 = org.webrtc.r0.f(r3, r4)     // Catch:{ IllegalStateException -> 0x010d }
            r8.textureEglBase = r3     // Catch:{ IllegalStateException -> 0x010d }
            org.webrtc.MediaCodecWrapper r3 = r8.codec     // Catch:{ IllegalStateException -> 0x010d }
            android.view.Surface r3 = r3.createInputSurface()     // Catch:{ IllegalStateException -> 0x010d }
            r8.textureInputSurface = r3     // Catch:{ IllegalStateException -> 0x010d }
            org.webrtc.EglBase14 r4 = r8.textureEglBase     // Catch:{ IllegalStateException -> 0x010d }
            r4.createSurface((android.view.Surface) r3)     // Catch:{ IllegalStateException -> 0x010d }
            org.webrtc.EglBase14 r3 = r8.textureEglBase     // Catch:{ IllegalStateException -> 0x010d }
            r3.makeCurrent()     // Catch:{ IllegalStateException -> 0x010d }
        L_0x00ec:
            org.webrtc.MediaCodecWrapper r3 = r8.codec     // Catch:{ IllegalStateException -> 0x010d }
            r3.start()     // Catch:{ IllegalStateException -> 0x010d }
            org.webrtc.MediaCodecWrapper r3 = r8.codec     // Catch:{ IllegalStateException -> 0x010d }
            java.nio.ByteBuffer[] r3 = r3.getOutputBuffers()     // Catch:{ IllegalStateException -> 0x010d }
            r8.outputBuffers = r3     // Catch:{ IllegalStateException -> 0x010d }
            r8.running = r5
            org.webrtc.ThreadUtils$ThreadChecker r0 = r8.outputThreadChecker
            r0.detachThread()
            java.lang.Thread r0 = r8.createOutputThread()
            r8.outputThread = r0
            r0.start()
            org.webrtc.VideoCodecStatus r0 = org.webrtc.VideoCodecStatus.OK
            return r0
        L_0x010d:
            r2 = move-exception
            java.lang.String r3 = "initEncodeInternal failed"
            org.webrtc.Logging.e(r0, r3, r2)
            r8.release()
            org.webrtc.VideoCodecStatus r0 = org.webrtc.VideoCodecStatus.FALLBACK_SOFTWARE
            return r0
        L_0x0119:
            r1 = move-exception
            goto L_0x011c
        L_0x011b:
            r1 = move-exception
        L_0x011c:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Cannot create media encoder "
            r2.append(r3)
            java.lang.String r3 = r8.codecName
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            org.webrtc.Logging.e(r0, r2)
            org.webrtc.VideoCodecStatus r0 = org.webrtc.VideoCodecStatus.FALLBACK_SOFTWARE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.HardwareVideoEncoder.initEncodeInternal():org.webrtc.VideoCodecStatus");
    }

    public VideoCodecStatus release() {
        VideoCodecStatus returnValue;
        this.encodeThreadChecker.checkIsOnValidThread();
        if (this.outputThread == null) {
            returnValue = VideoCodecStatus.OK;
        } else {
            this.running = false;
            if (!ThreadUtils.joinUninterruptibly(this.outputThread, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS)) {
                Logging.e(TAG, "Media encoder release timeout");
                returnValue = VideoCodecStatus.TIMEOUT;
            } else if (this.shutdownException != null) {
                Logging.e(TAG, "Media encoder release exception", this.shutdownException);
                returnValue = VideoCodecStatus.ERROR;
            } else {
                returnValue = VideoCodecStatus.OK;
            }
        }
        this.textureDrawer.release();
        this.videoFrameDrawer.release();
        EglBase14 eglBase14 = this.textureEglBase;
        if (eglBase14 != null) {
            eglBase14.release();
            this.textureEglBase = null;
        }
        Surface surface = this.textureInputSurface;
        if (surface != null) {
            surface.release();
            this.textureInputSurface = null;
        }
        this.outputBuilders.clear();
        this.codec = null;
        this.outputBuffers = null;
        this.outputThread = null;
        this.encodeThreadChecker.detachThread();
        return returnValue;
    }

    public VideoCodecStatus encode(VideoFrame videoFrame, VideoEncoder.EncodeInfo encodeInfo) {
        VideoCodecStatus returnValue;
        VideoCodecStatus status;
        this.encodeThreadChecker.checkIsOnValidThread();
        if (this.codec == null) {
            return VideoCodecStatus.UNINITIALIZED;
        }
        VideoFrame.Buffer videoFrameBuffer = videoFrame.getBuffer();
        boolean isTextureBuffer = videoFrameBuffer instanceof VideoFrame.TextureBuffer;
        int frameWidth = videoFrame.getBuffer().getWidth();
        int frameHeight = videoFrame.getBuffer().getHeight();
        boolean shouldUseSurfaceMode = canUseSurface() && isTextureBuffer;
        if ((frameWidth != this.width || frameHeight != this.height || shouldUseSurfaceMode != this.useSurfaceMode) && (status = resetCodec(frameWidth, frameHeight, shouldUseSurfaceMode)) != VideoCodecStatus.OK) {
            return status;
        }
        if (this.outputBuilders.size() > 2) {
            Logging.e(TAG, "Dropped frame, encoder queue full");
            return VideoCodecStatus.NO_OUTPUT;
        }
        boolean requestedKeyFrame = false;
        for (EncodedImage.FrameType frameType : encodeInfo.frameTypes) {
            if (frameType == EncodedImage.FrameType.VideoFrameKey) {
                requestedKeyFrame = true;
            }
        }
        if (requestedKeyFrame || shouldForceKeyFrame(videoFrame.getTimestampNs())) {
            requestKeyFrame(videoFrame.getTimestampNs());
        }
        int bufferSize = ((videoFrameBuffer.getHeight() * videoFrameBuffer.getWidth()) * 3) / 2;
        this.outputBuilders.offer(EncodedImage.builder().setCaptureTimeNs(videoFrame.getTimestampNs()).setEncodedWidth(videoFrame.getBuffer().getWidth()).setEncodedHeight(videoFrame.getBuffer().getHeight()).setRotation(videoFrame.getRotation()));
        long presentationTimestampUs = this.nextPresentationTimestampUs;
        long frameDurationUs = (long) (((double) TimeUnit.SECONDS.toMicros(1)) / this.bitrateAdjuster.getAdjustedFramerateFps());
        this.nextPresentationTimestampUs += frameDurationUs;
        if (this.useSurfaceMode) {
            returnValue = encodeTextureBuffer(videoFrame, presentationTimestampUs);
            long j = frameDurationUs;
            long j2 = presentationTimestampUs;
        } else {
            long j3 = frameDurationUs;
            long j4 = presentationTimestampUs;
            returnValue = encodeByteBuffer(videoFrame, presentationTimestampUs, videoFrameBuffer, bufferSize);
        }
        if (returnValue != VideoCodecStatus.OK) {
            this.outputBuilders.pollLast();
        }
        return returnValue;
    }

    private VideoCodecStatus encodeTextureBuffer(VideoFrame videoFrame, long presentationTimestampUs) {
        this.encodeThreadChecker.checkIsOnValidThread();
        try {
            GLES20.glClear(16384);
            this.videoFrameDrawer.drawFrame(new VideoFrame(videoFrame.getBuffer(), 0, videoFrame.getTimestampNs(), "HardwareVideoEncoder-0-0", videoFrame.getIsKeyFrame()), this.textureDrawer, (Matrix) null);
            this.textureEglBase.swapBuffers(TimeUnit.MICROSECONDS.toNanos(presentationTimestampUs));
            return VideoCodecStatus.OK;
        } catch (RuntimeException e) {
            Logging.e(TAG, "encodeTexture failed", e);
            return VideoCodecStatus.ERROR;
        }
    }

    private VideoCodecStatus encodeByteBuffer(VideoFrame videoFrame, long presentationTimestampUs, VideoFrame.Buffer videoFrameBuffer, int bufferSize) {
        this.encodeThreadChecker.checkIsOnValidThread();
        try {
            int index = this.codec.dequeueInputBuffer(0);
            if (index == -1) {
                Logging.d(TAG, "Dropped frame, no input buffers available");
                return VideoCodecStatus.NO_OUTPUT;
            }
            try {
                fillInputBuffer(this.codec.getInputBuffers()[index], videoFrameBuffer);
                try {
                    this.codec.queueInputBuffer(index, 0, bufferSize, presentationTimestampUs, 0);
                    return VideoCodecStatus.OK;
                } catch (IllegalStateException e) {
                    Logging.e(TAG, "queueInputBuffer failed", e);
                    return VideoCodecStatus.ERROR;
                }
            } catch (IllegalStateException e2) {
                Logging.e(TAG, "getInputBuffers failed", e2);
                return VideoCodecStatus.ERROR;
            }
        } catch (IllegalStateException e3) {
            Logging.e(TAG, "dequeueInputBuffer failed", e3);
            return VideoCodecStatus.ERROR;
        }
    }

    public VideoCodecStatus setRateAllocation(VideoEncoder.BitrateAllocation bitrateAllocation, int framerate) {
        this.encodeThreadChecker.checkIsOnValidThread();
        if (framerate > 30) {
            framerate = 30;
        }
        this.bitrateAdjuster.setTargets(bitrateAllocation.getSum(), (double) framerate);
        return VideoCodecStatus.OK;
    }

    public VideoCodecStatus setRates(VideoEncoder.RateControlParameters rcParameters) {
        this.encodeThreadChecker.checkIsOnValidThread();
        this.bitrateAdjuster.setTargets(rcParameters.bitrate.getSum(), rcParameters.framerateFps);
        return VideoCodecStatus.OK;
    }

    public VideoEncoder.ScalingSettings getScalingSettings() {
        this.encodeThreadChecker.checkIsOnValidThread();
        if (this.automaticResizeOn) {
            VideoCodecMimeType videoCodecMimeType = this.codecType;
            if (videoCodecMimeType == VideoCodecMimeType.VP8) {
                return new VideoEncoder.ScalingSettings(29, 95);
            }
            if (videoCodecMimeType == VideoCodecMimeType.H264) {
                return new VideoEncoder.ScalingSettings(24, 37);
            }
        }
        return VideoEncoder.ScalingSettings.OFF;
    }

    public String getImplementationName() {
        return "HWEncoder";
    }

    public VideoEncoder.EncoderInfo getEncoderInfo() {
        return new VideoEncoder.EncoderInfo(16, false);
    }

    private VideoCodecStatus resetCodec(int newWidth, int newHeight, boolean newUseSurfaceMode) {
        this.encodeThreadChecker.checkIsOnValidThread();
        VideoCodecStatus status = release();
        if (status != VideoCodecStatus.OK) {
            return status;
        }
        if (newWidth % 16 == 0 && newHeight % 16 == 0) {
            this.width = newWidth;
            this.height = newHeight;
            this.useSurfaceMode = newUseSurfaceMode;
            return initEncodeInternal();
        }
        Logging.e(TAG, "MediaCodec is only tested with resolutions that are 16x16 aligned.");
        return VideoCodecStatus.ERR_SIZE;
    }

    private boolean shouldForceKeyFrame(long presentationTimestampNs) {
        this.encodeThreadChecker.checkIsOnValidThread();
        long j = this.forcedKeyFrameNs;
        return j > 0 && presentationTimestampNs > this.lastKeyFrameNs + j;
    }

    private void requestKeyFrame(long presentationTimestampNs) {
        this.encodeThreadChecker.checkIsOnValidThread();
        try {
            Bundle b = new Bundle();
            b.putInt("request-sync", 0);
            this.codec.setParameters(b);
            this.lastKeyFrameNs = presentationTimestampNs;
        } catch (IllegalStateException e) {
            Logging.e(TAG, "requestKeyFrame failed", e);
        }
    }

    private Thread createOutputThread() {
        return new Thread() {
            public void run() {
                while (HardwareVideoEncoder.this.running) {
                    HardwareVideoEncoder.this.deliverEncodedImage();
                }
                HardwareVideoEncoder.this.releaseCodecOnOutputThread();
            }
        };
    }

    /* access modifiers changed from: protected */
    public void deliverEncodedImage() {
        ByteBuffer frameBuffer;
        EncodedImage.FrameType frameType;
        this.outputThreadChecker.checkIsOnValidThread();
        try {
            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            int index = this.codec.dequeueOutputBuffer(info, 100000);
            if (index >= 0) {
                ByteBuffer codecOutputBuffer = this.outputBuffers[index];
                codecOutputBuffer.position(info.offset);
                codecOutputBuffer.limit(info.offset + info.size);
                if ((info.flags & 2) != 0) {
                    Logging.d(TAG, "Config frame generated. Offset: " + info.offset + ". Size: " + info.size);
                    ByteBuffer allocateDirect = ByteBuffer.allocateDirect(info.size);
                    this.configBuffer = allocateDirect;
                    allocateDirect.put(codecOutputBuffer);
                    return;
                }
                this.bitrateAdjuster.reportEncodedFrame(info.size);
                if (this.adjustedBitrate != this.bitrateAdjuster.getAdjustedBitrateBps()) {
                    updateBitrate();
                }
                boolean z = true;
                if ((info.flags & 1) == 0) {
                    z = false;
                }
                boolean isKeyFrame = z;
                if (isKeyFrame) {
                    Logging.d(TAG, "Sync frame generated");
                }
                if ((!isKeyFrame || this.codecType != VideoCodecMimeType.H264) && this.codecType != VideoCodecMimeType.H265) {
                    frameBuffer = codecOutputBuffer.slice();
                } else {
                    Logging.d(TAG, "Prepending config frame of size " + this.configBuffer.capacity() + " to output buffer with offset " + info.offset + ", size " + info.size);
                    frameBuffer = ByteBuffer.allocateDirect(info.size + this.configBuffer.capacity());
                    this.configBuffer.rewind();
                    frameBuffer.put(this.configBuffer);
                    frameBuffer.put(codecOutputBuffer);
                    frameBuffer.rewind();
                }
                if (isKeyFrame) {
                    frameType = EncodedImage.FrameType.VideoFrameKey;
                } else {
                    frameType = EncodedImage.FrameType.VideoFrameDelta;
                }
                this.outputBuffersBusyCount.increment();
                EncodedImage encodedImage = this.outputBuilders.poll().setBuffer(frameBuffer, new p(this, index)).setFrameType(frameType).createEncodedImage();
                this.callback.onEncodedFrame(encodedImage, new VideoEncoder.CodecSpecificInfo());
                encodedImage.release();
            } else if (index == -3) {
                this.outputBuffersBusyCount.waitForZero();
                this.outputBuffers = this.codec.getOutputBuffers();
            }
        } catch (IllegalStateException e) {
            Logging.e(TAG, "deliverOutput failed", e);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$deliverEncodedImage$0 */
    public /* synthetic */ void a(int index) {
        try {
            this.codec.releaseOutputBuffer(index, false);
        } catch (Exception e) {
            Logging.e(TAG, "releaseOutputBuffer failed", e);
        }
        this.outputBuffersBusyCount.decrement();
    }

    /* access modifiers changed from: private */
    public void releaseCodecOnOutputThread() {
        this.outputThreadChecker.checkIsOnValidThread();
        Logging.d(TAG, "Releasing MediaCodec on output thread");
        this.outputBuffersBusyCount.waitForZero();
        try {
            this.codec.stop();
        } catch (Exception e) {
            Logging.e(TAG, "Media encoder stop failed", e);
        }
        try {
            this.codec.release();
        } catch (Exception e2) {
            Logging.e(TAG, "Media encoder release failed", e2);
            this.shutdownException = e2;
        }
        this.configBuffer = null;
        Logging.d(TAG, "Release on output thread done");
    }

    private VideoCodecStatus updateBitrate() {
        this.outputThreadChecker.checkIsOnValidThread();
        this.adjustedBitrate = this.bitrateAdjuster.getAdjustedBitrateBps();
        try {
            Bundle params2 = new Bundle();
            params2.putInt("video-bitrate", this.adjustedBitrate);
            this.codec.setParameters(params2);
            return VideoCodecStatus.OK;
        } catch (IllegalStateException e) {
            Logging.e(TAG, "updateBitrate failed", e);
            return VideoCodecStatus.ERROR;
        }
    }

    private boolean canUseSurface() {
        return (this.sharedContext == null || this.surfaceColorFormat == null) ? false : true;
    }

    /* access modifiers changed from: protected */
    public void fillInputBuffer(ByteBuffer buffer, VideoFrame.Buffer videoFrameBuffer) {
        this.yuvFormat.fillBuffer(buffer, videoFrameBuffer);
    }

    public enum YuvFormat {
        I420 {
            /* access modifiers changed from: package-private */
            public void fillBuffer(ByteBuffer dstBuffer, VideoFrame.Buffer srcBuffer) {
                VideoFrame.I420Buffer i420 = srcBuffer.toI420();
                YuvHelper.I420Copy(i420.getDataY(), i420.getStrideY(), i420.getDataU(), i420.getStrideU(), i420.getDataV(), i420.getStrideV(), dstBuffer, i420.getWidth(), i420.getHeight());
                i420.release();
            }
        },
        NV12 {
            /* access modifiers changed from: package-private */
            public void fillBuffer(ByteBuffer dstBuffer, VideoFrame.Buffer srcBuffer) {
                VideoFrame.I420Buffer i420 = srcBuffer.toI420();
                YuvHelper.I420ToNV12(i420.getDataY(), i420.getStrideY(), i420.getDataU(), i420.getStrideU(), i420.getDataV(), i420.getStrideV(), dstBuffer, i420.getWidth(), i420.getHeight());
                i420.release();
            }
        };

        /* access modifiers changed from: package-private */
        public abstract void fillBuffer(ByteBuffer byteBuffer, VideoFrame.Buffer buffer);

        static YuvFormat valueOf(int colorFormat) {
            switch (colorFormat) {
                case 19:
                    return I420;
                case 21:
                case 2141391872:
                case 2141391876:
                    return NV12;
                default:
                    throw new IllegalArgumentException("Unsupported colorFormat: " + colorFormat);
            }
        }
    }
}
