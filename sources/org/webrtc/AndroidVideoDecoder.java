package org.webrtc;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Surface;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import meshsdk.ctrl.GroupCtrlAdapter;
import org.webrtc.EglBase;
import org.webrtc.EncodedImage;
import org.webrtc.ThreadUtils;
import org.webrtc.VideoDecoder;
import org.webrtc.VideoFrame;

public class AndroidVideoDecoder implements VideoDecoder, VideoSink {
    private static final int DEQUEUE_INPUT_TIMEOUT_US = 500000;
    private static final int DEQUEUE_OUTPUT_BUFFER_TIMEOUT_US = 100000;
    private static final int MEDIA_CODEC_RELEASE_TIMEOUT_MS = 5000;
    private static final String MEDIA_FORMAT_KEY_CROP_BOTTOM = "crop-bottom";
    private static final String MEDIA_FORMAT_KEY_CROP_LEFT = "crop-left";
    private static final String MEDIA_FORMAT_KEY_CROP_RIGHT = "crop-right";
    private static final String MEDIA_FORMAT_KEY_CROP_TOP = "crop-top";
    private static final String MEDIA_FORMAT_KEY_SLICE_HEIGHT = "slice-height";
    private static final String MEDIA_FORMAT_KEY_STRIDE = "stride";
    private static final String TAG = "AndroidVideoDecoder";
    private IOnAndroidVideoCodecError _onErrorCodecErrorHandler;
    @Nullable
    private VideoDecoder.Callback callback;
    @Nullable
    private MediaCodecWrapper codec;
    private final String codecName;
    private final VideoCodecMimeType codecType;
    private int colorFormat;
    private ThreadUtils.ThreadChecker decoderThreadChecker;
    private final Object dimensionLock = new Object();
    private final BlockingDeque<FrameInfo> frameInfos;
    private boolean hasDecodedFirstFrame;
    private boolean hasDecodedH265FirstFrame = false;
    private int height;
    private boolean keyFrameRequired;
    Handler mainHandler = new Handler(Looper.getMainLooper());
    private final MediaCodecWrapperFactory mediaCodecWrapperFactory;
    @Nullable
    private Thread outputThread;
    /* access modifiers changed from: private */
    public ThreadUtils.ThreadChecker outputThreadChecker;
    @Nullable
    private DecodedTextureMetadata renderedTextureMetadata;
    private final Object renderedTextureMetadataLock = new Object();
    Runnable runnableOfCheckIsSupportH256HardCode = new a(this);
    /* access modifiers changed from: private */
    public volatile boolean running;
    @Nullable
    private final EglBase.Context sharedContext;
    @Nullable
    private volatile Exception shutdownException;
    private int sliceHeight;
    private int stride;
    @Nullable
    private Surface surface;
    @Nullable
    private SurfaceTextureHelper surfaceTextureHelper;
    private int width;

    public interface IOnAndroidVideoCodecError {
        public static final int ERROR_UNSUPORT_H265_HARD_CODE = -7001;

        void onFirstFrameKeyDecodedError(int i, String str);
    }

    public /* synthetic */ long createNativeVideoDecoder() {
        return t0.a(this);
    }

    public static class FrameInfo {
        final long decodeStartTimeMs;
        final int isKeyFrame;
        final int rotation;
        final String streamId;
        VideoCodecMimeType videoCodecMimeType;

        FrameInfo(long decodeStartTimeMs2, int rotation2, String streamId2, int isKeyFrame2, VideoCodecMimeType videoCodecMimeType2) {
            this.decodeStartTimeMs = decodeStartTimeMs2;
            this.rotation = rotation2;
            this.streamId = streamId2;
            this.isKeyFrame = isKeyFrame2;
            this.videoCodecMimeType = videoCodecMimeType2;
        }
    }

    public static class DecodedTextureMetadata {
        final Integer decodeTimeMs;
        final long presentationTimestampUs;

        DecodedTextureMetadata(long presentationTimestampUs2, Integer decodeTimeMs2) {
            this.presentationTimestampUs = presentationTimestampUs2;
            this.decodeTimeMs = decodeTimeMs2;
        }
    }

    AndroidVideoDecoder(MediaCodecWrapperFactory mediaCodecWrapperFactory2, String codecName2, VideoCodecMimeType codecType2, int colorFormat2, @Nullable EglBase.Context sharedContext2) {
        if (isSupportedColorFormat(colorFormat2)) {
            Logging.d(TAG, "ctor name: " + codecName2 + " type: " + codecType2 + " color format: " + colorFormat2 + " context: " + sharedContext2);
            this.mediaCodecWrapperFactory = mediaCodecWrapperFactory2;
            this.codecName = codecName2;
            this.codecType = codecType2;
            this.colorFormat = colorFormat2;
            this.sharedContext = sharedContext2;
            this.frameInfos = new LinkedBlockingDeque();
            return;
        }
        throw new IllegalArgumentException("Unsupported color format: " + colorFormat2);
    }

    public VideoCodecStatus initDecode(VideoDecoder.Settings settings, VideoDecoder.Callback callback2) {
        this.decoderThreadChecker = new ThreadUtils.ThreadChecker();
        this.callback = callback2;
        if (this.sharedContext != null) {
            SurfaceTextureHelper createSurfaceTextureHelper = createSurfaceTextureHelper();
            this.surfaceTextureHelper = createSurfaceTextureHelper;
            if (createSurfaceTextureHelper == null) {
                Logging.d(TAG, "initDecode error ");
                return VideoCodecStatus.FALLBACK_SOFTWARE;
            }
            this.surface = new Surface(this.surfaceTextureHelper.getSurfaceTexture());
            this.surfaceTextureHelper.startListening(this);
        }
        return initDecodeInternal(settings.width, settings.height);
    }

    private VideoCodecStatus initDecodeInternal(int width2, int height2) {
        this.decoderThreadChecker.checkIsOnValidThread();
        Logging.d(TAG, "initDecodeInternal name: " + this.codecName + " type: " + this.codecType + " width: " + width2 + " height: " + height2);
        if (this.outputThread != null) {
            Logging.e(TAG, "initDecodeInternal called while the codec is already running");
            return VideoCodecStatus.FALLBACK_SOFTWARE;
        }
        this.width = width2;
        this.height = height2;
        this.stride = width2;
        this.sliceHeight = height2;
        this.hasDecodedFirstFrame = false;
        this.hasDecodedH265FirstFrame = false;
        this.keyFrameRequired = true;
        try {
            this.codec = this.mediaCodecWrapperFactory.createByCodecName(this.codecName);
            try {
                MediaFormat format = MediaFormat.createVideoFormat(this.codecType.mimeType(), width2, height2);
                if (this.sharedContext == null) {
                    format.setInteger("color-format", this.colorFormat);
                }
                this.codec.configure(format, this.surface, (MediaCrypto) null, 0);
                this.codec.start();
                this.running = true;
                Thread createOutputThread = createOutputThread();
                this.outputThread = createOutputThread;
                createOutputThread.start();
                Logging.d(TAG, "initDecodeInternal done");
                return VideoCodecStatus.OK;
            } catch (IllegalArgumentException | IllegalStateException e) {
                Logging.e(TAG, "initDecode failed", e);
                release();
                return VideoCodecStatus.FALLBACK_SOFTWARE;
            }
        } catch (IOException | IllegalArgumentException | IllegalStateException e2) {
            Logging.e(TAG, "Cannot create media decoder " + this.codecName);
            return VideoCodecStatus.FALLBACK_SOFTWARE;
        }
    }

    public VideoCodecStatus decode(EncodedImage frame, VideoDecoder.DecodeInfo info) {
        int width2;
        int height2;
        VideoCodecStatus status;
        EncodedImage encodedImage = frame;
        this.decoderThreadChecker.checkIsOnValidThread();
        boolean z = true;
        if (this.codec == null || this.callback == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("decode uninitalized, codec: ");
            if (this.codec == null) {
                z = false;
            }
            sb.append(z);
            sb.append(", callback: ");
            sb.append(this.callback);
            Logging.d(TAG, sb.toString());
            return VideoCodecStatus.UNINITIALIZED;
        }
        ByteBuffer byteBuffer = encodedImage.buffer;
        if (byteBuffer == null) {
            Logging.e(TAG, "decode() - no input data");
            return VideoCodecStatus.ERR_PARAMETER;
        }
        int size = byteBuffer.remaining();
        if (size == 0) {
            Logging.e(TAG, "decode() - input buffer empty");
            return VideoCodecStatus.ERR_PARAMETER;
        }
        synchronized (this.dimensionLock) {
            width2 = this.width;
            height2 = this.height;
        }
        int i = encodedImage.encodedWidth;
        int i2 = encodedImage.encodedHeight;
        if (i * i2 > 0 && ((i != width2 || i2 != height2) && (status = reinitDecode(i, i2)) != VideoCodecStatus.OK)) {
            return status;
        }
        if (!this.keyFrameRequired || encodedImage.frameType == EncodedImage.FrameType.VideoFrameKey) {
            EncodedImage.FrameType frameType = encodedImage.frameType;
            EncodedImage.FrameType frameType2 = EncodedImage.FrameType.VideoFrameKey;
            if (frameType == frameType2 && this.codecType == VideoCodecMimeType.H265 && !this.hasDecodedH265FirstFrame) {
                this.mainHandler.removeCallbacks(this.runnableOfCheckIsSupportH256HardCode);
                this.mainHandler.postDelayed(this.runnableOfCheckIsSupportH256HardCode, GroupCtrlAdapter.RETRY_TIMEOUT);
            }
            try {
                int index = this.codec.dequeueInputBuffer(500000);
                if (index < 0) {
                    Logging.e(TAG, "decode() - no HW buffers available; decoder falling behind");
                    return VideoCodecStatus.ERROR;
                }
                try {
                    ByteBuffer buffer = this.codec.getInputBuffers()[index];
                    if (buffer.capacity() < size) {
                        Logging.e(TAG, "decode() - HW buffer too small");
                        return VideoCodecStatus.ERROR;
                    }
                    buffer.put(encodedImage.buffer);
                    this.frameInfos.offer(new FrameInfo(SystemClock.elapsedRealtime(), encodedImage.rotation, encodedImage.streamId, encodedImage.frameType == frameType2 ? 1 : 0, this.codecType));
                    try {
                        ByteBuffer byteBuffer2 = buffer;
                        try {
                            this.codec.queueInputBuffer(index, 0, size, TimeUnit.NANOSECONDS.toMicros(encodedImage.captureTimeNs), 0);
                            if (this.keyFrameRequired) {
                                this.keyFrameRequired = false;
                            }
                            return VideoCodecStatus.OK;
                        } catch (IllegalStateException e) {
                            e = e;
                            Logging.e(TAG, "queueInputBuffer failed", e);
                            this.frameInfos.pollLast();
                            return VideoCodecStatus.ERROR;
                        }
                    } catch (IllegalStateException e2) {
                        e = e2;
                        ByteBuffer byteBuffer3 = buffer;
                        Logging.e(TAG, "queueInputBuffer failed", e);
                        this.frameInfos.pollLast();
                        return VideoCodecStatus.ERROR;
                    }
                } catch (IllegalStateException e3) {
                    Logging.e(TAG, "getInputBuffers failed", e3);
                    return VideoCodecStatus.ERROR;
                }
            } catch (IllegalStateException e4) {
                Logging.e(TAG, "dequeueInputBuffer failed", e4);
                return VideoCodecStatus.ERROR;
            }
        } else {
            Logging.e(TAG, "decode() - key frame required first");
            return VideoCodecStatus.NO_OUTPUT;
        }
    }

    public String getImplementationName() {
        return this.codecName;
    }

    public VideoCodecStatus release() {
        Logging.d(TAG, "release");
        VideoCodecStatus status = releaseInternal();
        if (this.surface != null) {
            releaseSurface();
            this.surface = null;
            this.surfaceTextureHelper.stopListening();
            this.surfaceTextureHelper.dispose();
            this.surfaceTextureHelper = null;
        }
        synchronized (this.renderedTextureMetadataLock) {
            this.renderedTextureMetadata = null;
        }
        this.callback = null;
        this.frameInfos.clear();
        return status;
    }

    private VideoCodecStatus releaseInternal() {
        if (!this.running) {
            Logging.d(TAG, "release: Decoder is not running.");
            return VideoCodecStatus.OK;
        }
        try {
            this.running = false;
            if (!ThreadUtils.joinUninterruptibly(this.outputThread, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS)) {
                Logging.e(TAG, "Media decoder release timeout", new RuntimeException());
                return VideoCodecStatus.TIMEOUT;
            } else if (this.shutdownException != null) {
                Logging.e(TAG, "Media decoder release error", new RuntimeException(this.shutdownException));
                this.shutdownException = null;
                VideoCodecStatus videoCodecStatus = VideoCodecStatus.ERROR;
                this.codec = null;
                this.outputThread = null;
                return videoCodecStatus;
            } else {
                this.codec = null;
                this.outputThread = null;
                return VideoCodecStatus.OK;
            }
        } finally {
            this.codec = null;
            this.outputThread = null;
        }
    }

    private VideoCodecStatus reinitDecode(int newWidth, int newHeight) {
        this.decoderThreadChecker.checkIsOnValidThread();
        VideoCodecStatus status = releaseInternal();
        if (status != VideoCodecStatus.OK) {
            return status;
        }
        return initDecodeInternal(newWidth, newHeight);
    }

    private Thread createOutputThread() {
        return new Thread("AndroidVideoDecoder.outputThread") {
            public void run() {
                ThreadUtils.ThreadChecker unused = AndroidVideoDecoder.this.outputThreadChecker = new ThreadUtils.ThreadChecker();
                while (AndroidVideoDecoder.this.running) {
                    AndroidVideoDecoder.this.deliverDecodedFrame();
                }
                AndroidVideoDecoder.this.releaseCodecOnOutputThread();
            }
        };
    }

    /* access modifiers changed from: protected */
    public void deliverDecodedFrame() {
        int isKey;
        String streamId;
        int rotation;
        Integer decodeTimeMs;
        this.outputThreadChecker.checkIsOnValidThread();
        try {
            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            int result = this.codec.dequeueOutputBuffer(info, 100000);
            if (result == -2) {
                reformat(this.codec.getOutputFormat());
            } else if (result >= 0) {
                FrameInfo frameInfo = this.frameInfos.poll();
                if (frameInfo != null) {
                    Integer decodeTimeMs2 = Integer.valueOf((int) (SystemClock.elapsedRealtime() - frameInfo.decodeStartTimeMs));
                    int rotation2 = frameInfo.rotation;
                    String streamId2 = frameInfo.streamId;
                    int i = frameInfo.isKeyFrame;
                    int isKey2 = i;
                    if (i == 1 && frameInfo.videoCodecMimeType == VideoCodecMimeType.H265) {
                        this.hasDecodedH265FirstFrame = true;
                        this.mainHandler.removeCallbacks(this.runnableOfCheckIsSupportH256HardCode);
                    }
                    decodeTimeMs = decodeTimeMs2;
                    rotation = rotation2;
                    streamId = streamId2;
                    isKey = isKey2;
                } else {
                    decodeTimeMs = null;
                    rotation = 0;
                    streamId = "";
                    isKey = 0;
                }
                this.hasDecodedFirstFrame = true;
                if (this.surfaceTextureHelper != null) {
                    deliverTextureFrame(result, info, rotation, decodeTimeMs, streamId, isKey);
                } else {
                    deliverByteFrame(result, info, rotation, decodeTimeMs, streamId, isKey);
                }
            }
        } catch (Exception e) {
            Logging.e(TAG, "deliverDecodedFrame failed", e);
        }
    }

    private void deliverTextureFrame(int index, MediaCodec.BufferInfo info, int rotation, Integer decodeTimeMs, String streamId, int isKeyFrame) {
        int width2;
        int height2;
        synchronized (this.dimensionLock) {
            width2 = this.width;
            height2 = this.height;
        }
        synchronized (this.renderedTextureMetadataLock) {
            if (this.renderedTextureMetadata != null) {
                this.codec.releaseOutputBuffer(index, false);
                return;
            }
            this.surfaceTextureHelper.setTextureStreamId(streamId);
            this.surfaceTextureHelper.setTextureFrameKey(isKeyFrame);
            this.surfaceTextureHelper.setTextureSize(width2, height2);
            this.surfaceTextureHelper.setFrameRotation(rotation);
            this.renderedTextureMetadata = new DecodedTextureMetadata(info.presentationTimeUs, decodeTimeMs);
            this.codec.releaseOutputBuffer(index, true);
        }
    }

    public void onFrame(VideoFrame frame) {
        long timestampNs;
        Integer decodeTimeMs;
        synchronized (this.renderedTextureMetadataLock) {
            DecodedTextureMetadata decodedTextureMetadata = this.renderedTextureMetadata;
            if (decodedTextureMetadata != null) {
                timestampNs = decodedTextureMetadata.presentationTimestampUs * 1000;
                decodeTimeMs = decodedTextureMetadata.decodeTimeMs;
                this.renderedTextureMetadata = null;
            } else {
                throw new IllegalStateException("Rendered texture metadata was null in onTextureFrameAvailable.");
            }
        }
        this.callback.onDecodedFrame(new VideoFrame(frame.getBuffer(), frame.getRotation(), timestampNs, frame.getStreamId(), frame.getIsKeyFrame()), decodeTimeMs, (Integer) null);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ad, code lost:
        r0 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void deliverByteFrame(int r22, android.media.MediaCodec.BufferInfo r23, int r24, java.lang.Integer r25, java.lang.String r26, int r27) {
        /*
            r21 = this;
            r7 = r21
            r8 = r22
            r9 = r23
            java.lang.Object r1 = r7.dimensionLock
            monitor-enter(r1)
            int r0 = r7.width     // Catch:{ all -> 0x00a8 }
            int r2 = r7.height     // Catch:{ all -> 0x00a8 }
            r10 = r2
            int r2 = r7.stride     // Catch:{ all -> 0x00a8 }
            int r3 = r7.sliceHeight     // Catch:{ all -> 0x00a8 }
            r11 = r3
            monitor-exit(r1)     // Catch:{ all -> 0x00a8 }
            int r1 = r9.size
            int r3 = r0 * r10
            int r3 = r3 * 3
            int r3 = r3 / 2
            if (r1 >= r3) goto L_0x0037
            java.lang.String r1 = "AndroidVideoDecoder"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Insufficient output buffer size: "
            r3.append(r4)
            int r4 = r9.size
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            org.webrtc.Logging.e(r1, r3)
            return
        L_0x0037:
            int r3 = r2 * r10
            int r3 = r3 * 3
            int r3 = r3 / 2
            if (r1 >= r3) goto L_0x004b
            if (r11 != r10) goto L_0x004b
            if (r2 <= r0) goto L_0x004b
            int r1 = r1 * 2
            int r3 = r10 * 3
            int r2 = r1 / r3
            r12 = r2
            goto L_0x004c
        L_0x004b:
            r12 = r2
        L_0x004c:
            org.webrtc.MediaCodecWrapper r1 = r7.codec
            java.nio.ByteBuffer[] r1 = r1.getOutputBuffers()
            r1 = r1[r8]
            int r2 = r9.offset
            r1.position(r2)
            int r2 = r9.offset
            int r3 = r9.size
            int r2 = r2 + r3
            r1.limit(r2)
            java.nio.ByteBuffer r13 = r1.slice()
            int r1 = r7.colorFormat
            r2 = 19
            if (r1 != r2) goto L_0x0077
            r1 = r21
            r2 = r13
            r3 = r12
            r4 = r11
            r5 = r0
            r6 = r10
            org.webrtc.VideoFrame$Buffer r1 = r1.copyI420Buffer(r2, r3, r4, r5, r6)
            goto L_0x0082
        L_0x0077:
            r1 = r21
            r2 = r13
            r3 = r12
            r4 = r11
            r5 = r0
            r6 = r10
            org.webrtc.VideoFrame$Buffer r1 = r1.copyNV12ToI420Buffer(r2, r3, r4, r5, r6)
        L_0x0082:
            org.webrtc.MediaCodecWrapper r2 = r7.codec
            r3 = 0
            r2.releaseOutputBuffer(r8, r3)
            long r2 = r9.presentationTimeUs
            r4 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 * r4
            org.webrtc.VideoFrame r4 = new org.webrtc.VideoFrame
            r14 = r4
            r15 = r1
            r16 = r24
            r17 = r2
            r19 = r26
            r20 = r27
            r14.<init>(r15, r16, r17, r19, r20)
            org.webrtc.VideoDecoder$Callback r5 = r7.callback
            r6 = 0
            r14 = r25
            r5.onDecodedFrame(r4, r14, r6)
            r4.release()
            return
        L_0x00a8:
            r0 = move-exception
            r14 = r25
        L_0x00ab:
            monitor-exit(r1)     // Catch:{ all -> 0x00ad }
            throw r0
        L_0x00ad:
            r0 = move-exception
            goto L_0x00ab
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.AndroidVideoDecoder.deliverByteFrame(int, android.media.MediaCodec$BufferInfo, int, java.lang.Integer, java.lang.String, int):void");
    }

    private VideoFrame.Buffer copyNV12ToI420Buffer(ByteBuffer buffer, int stride2, int sliceHeight2, int width2, int height2) {
        return new NV12Buffer(width2, height2, stride2, sliceHeight2, buffer, (Runnable) null).toI420();
    }

    private VideoFrame.Buffer copyI420Buffer(ByteBuffer buffer, int stride2, int sliceHeight2, int width2, int height2) {
        ByteBuffer byteBuffer = buffer;
        int i = stride2;
        int i2 = width2;
        int i3 = height2;
        if (i % 2 == 0) {
            int chromaWidth = (i2 + 1) / 2;
            int chromaHeight = sliceHeight2 % 2 == 0 ? (i3 + 1) / 2 : i3 / 2;
            int uvStride = i / 2;
            int yEnd = (i * i3) + 0;
            int uPos = (i * sliceHeight2) + 0;
            int uEnd = uPos + (uvStride * chromaHeight);
            int vPos = uPos + ((uvStride * sliceHeight2) / 2);
            VideoFrame.I420Buffer frameBuffer = allocateI420Buffer(i2, i3);
            byteBuffer.limit(yEnd);
            byteBuffer.position(0);
            copyPlane(buffer.slice(), stride2, frameBuffer.getDataY(), frameBuffer.getStrideY(), width2, height2);
            byteBuffer.limit(uEnd);
            byteBuffer.position(uPos);
            int vEnd = vPos + (uvStride * chromaHeight);
            int vPos2 = vPos;
            ByteBuffer slice = buffer.slice();
            int i4 = uEnd;
            int uPos2 = uPos;
            int i5 = yEnd;
            copyPlane(slice, uvStride, frameBuffer.getDataU(), frameBuffer.getStrideU(), chromaWidth, chromaHeight);
            if (sliceHeight2 % 2 == 1) {
                byteBuffer.position(uPos2 + ((chromaHeight - 1) * uvStride));
                ByteBuffer dataU = frameBuffer.getDataU();
                dataU.position(frameBuffer.getStrideU() * chromaHeight);
                dataU.put(byteBuffer);
            }
            byteBuffer.limit(vEnd);
            byteBuffer.position(vPos2);
            copyPlane(buffer.slice(), uvStride, frameBuffer.getDataV(), frameBuffer.getStrideV(), chromaWidth, chromaHeight);
            if (sliceHeight2 % 2 == 1) {
                byteBuffer.position(vPos2 + ((chromaHeight - 1) * uvStride));
                ByteBuffer dataV = frameBuffer.getDataV();
                dataV.position(frameBuffer.getStrideV() * chromaHeight);
                dataV.put(byteBuffer);
            }
            return frameBuffer;
        }
        throw new AssertionError("Stride is not divisible by two: " + i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00bd, code lost:
        if (r6.surfaceTextureHelper != null) goto L_0x010f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c5, code lost:
        if (r7.containsKey("color-format") == false) goto L_0x010f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c7, code lost:
        r6.colorFormat = r7.getInteger("color-format");
        org.webrtc.Logging.d(TAG, "Color: 0x" + java.lang.Integer.toHexString(r6.colorFormat));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00f1, code lost:
        if (isSupportedColorFormat(r6.colorFormat) != false) goto L_0x010f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00f3, code lost:
        stopOnOutputThread(new java.lang.IllegalStateException("Unsupported color format: " + r6.colorFormat));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x010e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x010f, code lost:
        r3 = r6.dimensionLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0111, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0118, code lost:
        if (r7.containsKey(MEDIA_FORMAT_KEY_STRIDE) == false) goto L_0x0122;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x011a, code lost:
        r6.stride = r7.getInteger(MEDIA_FORMAT_KEY_STRIDE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0128, code lost:
        if (r7.containsKey(MEDIA_FORMAT_KEY_SLICE_HEIGHT) == false) goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x012a, code lost:
        r6.sliceHeight = r7.getInteger(MEDIA_FORMAT_KEY_SLICE_HEIGHT);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0132, code lost:
        org.webrtc.Logging.d(TAG, "Frame stride and slice height: " + r6.stride + " x " + r6.sliceHeight);
        r6.stride = java.lang.Math.max(r6.width, r6.stride);
        r6.sliceHeight = java.lang.Math.max(r6.height, r6.sliceHeight);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0168, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0169, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void reformat(android.media.MediaFormat r7) {
        /*
            r6 = this;
            org.webrtc.ThreadUtils$ThreadChecker r0 = r6.outputThreadChecker
            r0.checkIsOnValidThread()
            java.lang.String r0 = "AndroidVideoDecoder"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Decoder format changed: "
            r1.append(r2)
            java.lang.String r2 = r7.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            org.webrtc.Logging.d(r0, r1)
            java.lang.String r0 = "crop-left"
            boolean r0 = r7.containsKey(r0)
            if (r0 == 0) goto L_0x005e
            java.lang.String r0 = "crop-right"
            boolean r0 = r7.containsKey(r0)
            if (r0 == 0) goto L_0x005e
            java.lang.String r0 = "crop-bottom"
            boolean r0 = r7.containsKey(r0)
            if (r0 == 0) goto L_0x005e
            java.lang.String r0 = "crop-top"
            boolean r0 = r7.containsKey(r0)
            if (r0 == 0) goto L_0x005e
            java.lang.String r0 = "crop-right"
            int r0 = r7.getInteger(r0)
            int r0 = r0 + 1
            java.lang.String r1 = "crop-left"
            int r1 = r7.getInteger(r1)
            int r0 = r0 - r1
            java.lang.String r1 = "crop-bottom"
            int r1 = r7.getInteger(r1)
            int r1 = r1 + 1
            java.lang.String r2 = "crop-top"
            int r2 = r7.getInteger(r2)
            int r1 = r1 - r2
            goto L_0x006a
        L_0x005e:
            java.lang.String r0 = "width"
            int r0 = r7.getInteger(r0)
            java.lang.String r1 = "height"
            int r1 = r7.getInteger(r1)
        L_0x006a:
            java.lang.Object r2 = r6.dimensionLock
            monitor-enter(r2)
            int r3 = r6.width     // Catch:{ all -> 0x01a6 }
            if (r0 != r3) goto L_0x0075
            int r3 = r6.height     // Catch:{ all -> 0x01a6 }
            if (r1 == r3) goto L_0x00ba
        L_0x0075:
            boolean r3 = r6.hasDecodedFirstFrame     // Catch:{ all -> 0x01a6 }
            if (r3 == 0) goto L_0x00b0
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ all -> 0x01a6 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01a6 }
            r4.<init>()     // Catch:{ all -> 0x01a6 }
            java.lang.String r5 = "Unexpected size change. Configured "
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            int r5 = r6.width     // Catch:{ all -> 0x01a6 }
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            java.lang.String r5 = "*"
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            int r5 = r6.height     // Catch:{ all -> 0x01a6 }
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            java.lang.String r5 = ". New "
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            r4.append(r0)     // Catch:{ all -> 0x01a6 }
            java.lang.String r5 = "*"
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            r4.append(r1)     // Catch:{ all -> 0x01a6 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x01a6 }
            r3.<init>(r4)     // Catch:{ all -> 0x01a6 }
            r6.stopOnOutputThread(r3)     // Catch:{ all -> 0x01a6 }
            monitor-exit(r2)     // Catch:{ all -> 0x01a6 }
            return
        L_0x00b0:
            if (r0 <= 0) goto L_0x016d
            if (r1 > 0) goto L_0x00b6
            goto L_0x016d
        L_0x00b6:
            r6.width = r0     // Catch:{ all -> 0x01a6 }
            r6.height = r1     // Catch:{ all -> 0x01a6 }
        L_0x00ba:
            monitor-exit(r2)     // Catch:{ all -> 0x01a6 }
            org.webrtc.SurfaceTextureHelper r2 = r6.surfaceTextureHelper
            if (r2 != 0) goto L_0x010f
            java.lang.String r2 = "color-format"
            boolean r2 = r7.containsKey(r2)
            if (r2 == 0) goto L_0x010f
            java.lang.String r2 = "color-format"
            int r2 = r7.getInteger(r2)
            r6.colorFormat = r2
            java.lang.String r2 = "AndroidVideoDecoder"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Color: 0x"
            r3.append(r4)
            int r4 = r6.colorFormat
            java.lang.String r4 = java.lang.Integer.toHexString(r4)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            org.webrtc.Logging.d(r2, r3)
            int r2 = r6.colorFormat
            boolean r2 = r6.isSupportedColorFormat(r2)
            if (r2 != 0) goto L_0x010f
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unsupported color format: "
            r3.append(r4)
            int r4 = r6.colorFormat
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            r6.stopOnOutputThread(r2)
            return
        L_0x010f:
            java.lang.Object r3 = r6.dimensionLock
            monitor-enter(r3)
            java.lang.String r2 = "stride"
            boolean r2 = r7.containsKey(r2)     // Catch:{ all -> 0x016a }
            if (r2 == 0) goto L_0x0122
            java.lang.String r2 = "stride"
            int r2 = r7.getInteger(r2)     // Catch:{ all -> 0x016a }
            r6.stride = r2     // Catch:{ all -> 0x016a }
        L_0x0122:
            java.lang.String r2 = "slice-height"
            boolean r2 = r7.containsKey(r2)     // Catch:{ all -> 0x016a }
            if (r2 == 0) goto L_0x0132
            java.lang.String r2 = "slice-height"
            int r2 = r7.getInteger(r2)     // Catch:{ all -> 0x016a }
            r6.sliceHeight = r2     // Catch:{ all -> 0x016a }
        L_0x0132:
            java.lang.String r2 = "AndroidVideoDecoder"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x016a }
            r4.<init>()     // Catch:{ all -> 0x016a }
            java.lang.String r5 = "Frame stride and slice height: "
            r4.append(r5)     // Catch:{ all -> 0x016a }
            int r5 = r6.stride     // Catch:{ all -> 0x016a }
            r4.append(r5)     // Catch:{ all -> 0x016a }
            java.lang.String r5 = " x "
            r4.append(r5)     // Catch:{ all -> 0x016a }
            int r5 = r6.sliceHeight     // Catch:{ all -> 0x016a }
            r4.append(r5)     // Catch:{ all -> 0x016a }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x016a }
            org.webrtc.Logging.d(r2, r4)     // Catch:{ all -> 0x016a }
            int r2 = r6.width     // Catch:{ all -> 0x016a }
            int r4 = r6.stride     // Catch:{ all -> 0x016a }
            int r2 = java.lang.Math.max(r2, r4)     // Catch:{ all -> 0x016a }
            r6.stride = r2     // Catch:{ all -> 0x016a }
            int r2 = r6.height     // Catch:{ all -> 0x016a }
            int r4 = r6.sliceHeight     // Catch:{ all -> 0x016a }
            int r2 = java.lang.Math.max(r2, r4)     // Catch:{ all -> 0x016a }
            r6.sliceHeight = r2     // Catch:{ all -> 0x016a }
            monitor-exit(r3)     // Catch:{ all -> 0x016a }
            return
        L_0x016a:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x016a }
            throw r2
        L_0x016d:
            java.lang.String r3 = "AndroidVideoDecoder"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01a6 }
            r4.<init>()     // Catch:{ all -> 0x01a6 }
            java.lang.String r5 = "Unexpected format dimensions. Configured "
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            int r5 = r6.width     // Catch:{ all -> 0x01a6 }
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            java.lang.String r5 = "*"
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            int r5 = r6.height     // Catch:{ all -> 0x01a6 }
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            java.lang.String r5 = ". New "
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            r4.append(r0)     // Catch:{ all -> 0x01a6 }
            java.lang.String r5 = "*"
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            r4.append(r1)     // Catch:{ all -> 0x01a6 }
            java.lang.String r5 = ". Skip it"
            r4.append(r5)     // Catch:{ all -> 0x01a6 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x01a6 }
            org.webrtc.Logging.w(r3, r4)     // Catch:{ all -> 0x01a6 }
            monitor-exit(r2)     // Catch:{ all -> 0x01a6 }
            return
        L_0x01a6:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x01a6 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.webrtc.AndroidVideoDecoder.reformat(android.media.MediaFormat):void");
    }

    /* access modifiers changed from: private */
    public void releaseCodecOnOutputThread() {
        this.outputThreadChecker.checkIsOnValidThread();
        Logging.d(TAG, "Releasing MediaCodec on output thread");
        try {
            this.codec.stop();
        } catch (Exception e) {
            Logging.e(TAG, "Media decoder stop failed", e);
        }
        try {
            this.codec.release();
        } catch (Exception e2) {
            Logging.e(TAG, "Media decoder release failed", e2);
            this.shutdownException = e2;
        }
        Logging.d(TAG, "Release on output thread done");
    }

    private void stopOnOutputThread(Exception e) {
        this.outputThreadChecker.checkIsOnValidThread();
        this.running = false;
        this.shutdownException = e;
    }

    private boolean isSupportedColorFormat(int colorFormat2) {
        for (int supported : MediaCodecUtils.DECODER_COLOR_FORMATS) {
            if (supported == colorFormat2) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public SurfaceTextureHelper createSurfaceTextureHelper() {
        return SurfaceTextureHelper.create("decoder-texture-thread", this.sharedContext);
    }

    /* access modifiers changed from: protected */
    public void releaseSurface() {
        this.surface.release();
    }

    /* access modifiers changed from: protected */
    public VideoFrame.I420Buffer allocateI420Buffer(int width2, int height2) {
        return JavaI420Buffer.allocate(width2, height2);
    }

    /* access modifiers changed from: protected */
    public void copyPlane(ByteBuffer src, int srcStride, ByteBuffer dst, int dstStride, int width2, int height2) {
        YuvHelper.copyPlane(src, srcStride, dst, dstStride, width2, height2);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$0 */
    public /* synthetic */ void a() {
        IOnAndroidVideoCodecError iOnAndroidVideoCodecError = this._onErrorCodecErrorHandler;
        if (iOnAndroidVideoCodecError != null) {
            try {
                iOnAndroidVideoCodecError.onFirstFrameKeyDecodedError(IOnAndroidVideoCodecError.ERROR_UNSUPORT_H265_HARD_CODE, "H265 的首帧硬解失败(3S~5内未解码成功)");
            } catch (Exception e) {
                e.toString();
                Logging.e(TAG, "通知上层业务 h265 解码失败 e=" + e.toString());
            }
        }
    }

    public void setOnErrorCodecErrorHandler(IOnAndroidVideoCodecError errorHandler) {
        this._onErrorCodecErrorHandler = errorHandler;
    }
}
