package org.webrtc;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.os.SystemClock;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.webrtc.CameraEnumerationAndroid;
import org.webrtc.CameraSession;

public class Camera1Session implements CameraSession {
    private static final int NUMBER_OF_CAPTURE_BUFFERS = 3;
    private static final String TAG = "Camera1Session";
    private static final Histogram camera1ResolutionHistogram = Histogram.createEnumeration("WebRTC.Android.Camera1.Resolution", CameraEnumerationAndroid.COMMON_RESOLUTIONS.size());
    /* access modifiers changed from: private */
    public static final Histogram camera1StartTimeMsHistogram = Histogram.createCounts("WebRTC.Android.Camera1.StartTimeMs", 1, 10000, 50);
    private static final Histogram camera1StopTimeMsHistogram = Histogram.createCounts("WebRTC.Android.Camera1.StopTimeMs", 1, 10000, 50);
    private final Context applicationContext;
    /* access modifiers changed from: private */
    public final Camera camera;
    private final int cameraId;
    /* access modifiers changed from: private */
    public final Handler cameraThreadHandler = new Handler();
    /* access modifiers changed from: private */
    public final CameraEnumerationAndroid.CaptureFormat captureFormat;
    private final boolean captureToTexture;
    /* access modifiers changed from: private */
    public final long constructionTimeNs;
    /* access modifiers changed from: private */
    public final CameraSession.Events events;
    /* access modifiers changed from: private */
    public boolean firstFrameReported;
    private final Camera.CameraInfo info;
    /* access modifiers changed from: private */
    public SessionState state;
    private final SurfaceTextureHelper surfaceTextureHelper;

    public enum SessionState {
        RUNNING,
        STOPPED
    }

    public static void create(CameraSession.CreateSessionCallback callback, CameraSession.Events events2, boolean captureToTexture2, Context applicationContext2, SurfaceTextureHelper surfaceTextureHelper2, String cameraName, int width, int height, int framerate) {
        CameraSession.CreateSessionCallback createSessionCallback = callback;
        boolean z = captureToTexture2;
        int i = width;
        int i2 = height;
        long constructionTimeNs2 = System.nanoTime();
        Logging.d(TAG, "Open camera " + cameraName);
        events2.onCameraOpening();
        try {
            int cameraId2 = Camera1Enumerator.getCameraIndex(cameraName);
            try {
                Camera camera2 = Camera.open(cameraId2);
                if (camera2 == null) {
                    CameraSession.FailureType failureType = CameraSession.FailureType.ERROR;
                    createSessionCallback.onFailure(failureType, "Camera.open returned null for camera id = " + cameraId2);
                    return;
                }
                try {
                    camera2.setPreviewTexture(surfaceTextureHelper2.getSurfaceTexture());
                    Camera.CameraInfo info2 = new Camera.CameraInfo();
                    Camera.getCameraInfo(cameraId2, info2);
                    try {
                        Camera.Parameters parameters = camera2.getParameters();
                        CameraEnumerationAndroid.CaptureFormat captureFormat2 = findClosestCaptureFormat(parameters, i, i2, framerate);
                        updateCameraParameters(camera2, parameters, captureFormat2, findClosestPictureSize(parameters, i, i2), z);
                        if (!z) {
                            int frameSize = captureFormat2.frameSize();
                            for (int i3 = 0; i3 < 3; i3++) {
                                camera2.addCallbackBuffer(ByteBuffer.allocateDirect(frameSize).array());
                            }
                        }
                        try {
                            camera2.setDisplayOrientation(0);
                            Camera.CameraInfo cameraInfo = info2;
                            Camera camera3 = camera2;
                            int i4 = cameraId2;
                            createSessionCallback.onDone(new Camera1Session(events2, captureToTexture2, applicationContext2, surfaceTextureHelper2, cameraId2, camera2, info2, captureFormat2, constructionTimeNs2));
                        } catch (RuntimeException e) {
                            CameraEnumerationAndroid.CaptureFormat captureFormat3 = captureFormat2;
                            Camera.CameraInfo cameraInfo2 = info2;
                            int i5 = cameraId2;
                            camera2.release();
                            createSessionCallback.onFailure(CameraSession.FailureType.ERROR, e.getMessage());
                        }
                    } catch (RuntimeException e2) {
                        Camera.CameraInfo cameraInfo3 = info2;
                        int i6 = cameraId2;
                        camera2.release();
                        createSessionCallback.onFailure(CameraSession.FailureType.ERROR, e2.getMessage());
                    }
                } catch (IOException | RuntimeException e3) {
                    int i7 = cameraId2;
                    camera2.release();
                    createSessionCallback.onFailure(CameraSession.FailureType.ERROR, e3.getMessage());
                }
            } catch (RuntimeException e4) {
                int i8 = cameraId2;
                createSessionCallback.onFailure(CameraSession.FailureType.ERROR, e4.getMessage());
            }
        } catch (IllegalArgumentException e5) {
            createSessionCallback.onFailure(CameraSession.FailureType.ERROR, e5.getMessage());
        }
    }

    private static void updateCameraParameters(Camera camera2, Camera.Parameters parameters, CameraEnumerationAndroid.CaptureFormat captureFormat2, Size pictureSize, boolean captureToTexture2) {
        List<String> focusModes = parameters.getSupportedFocusModes();
        CameraEnumerationAndroid.CaptureFormat.FramerateRange framerateRange = captureFormat2.framerate;
        parameters.setPreviewFpsRange(framerateRange.min, framerateRange.max);
        parameters.setPreviewSize(captureFormat2.width, captureFormat2.height);
        parameters.setPictureSize(pictureSize.width, pictureSize.height);
        if (!captureToTexture2) {
            Objects.requireNonNull(captureFormat2);
            parameters.setPreviewFormat(17);
        }
        if (parameters.isVideoStabilizationSupported()) {
            parameters.setVideoStabilization(true);
        }
        if (focusModes != null && focusModes.contains("continuous-video")) {
            parameters.setFocusMode("continuous-video");
        }
        camera2.setParameters(parameters);
    }

    private static CameraEnumerationAndroid.CaptureFormat findClosestCaptureFormat(Camera.Parameters parameters, int width, int height, int framerate) {
        List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> supportedFramerates = Camera1Enumerator.convertFramerates(parameters.getSupportedPreviewFpsRange());
        Logging.d(TAG, "Available fps ranges: " + supportedFramerates);
        CameraEnumerationAndroid.CaptureFormat.FramerateRange fpsRange = CameraEnumerationAndroid.getClosestSupportedFramerateRange(supportedFramerates, framerate);
        Size previewSize = CameraEnumerationAndroid.getClosestSupportedSize(Camera1Enumerator.convertSizes(parameters.getSupportedPreviewSizes()), width, height);
        CameraEnumerationAndroid.reportCameraResolution(camera1ResolutionHistogram, previewSize);
        return new CameraEnumerationAndroid.CaptureFormat(previewSize.width, previewSize.height, fpsRange);
    }

    private static Size findClosestPictureSize(Camera.Parameters parameters, int width, int height) {
        return CameraEnumerationAndroid.getClosestSupportedSize(Camera1Enumerator.convertSizes(parameters.getSupportedPictureSizes()), width, height);
    }

    private Camera1Session(CameraSession.Events events2, boolean captureToTexture2, Context applicationContext2, SurfaceTextureHelper surfaceTextureHelper2, int cameraId2, Camera camera2, Camera.CameraInfo info2, CameraEnumerationAndroid.CaptureFormat captureFormat2, long constructionTimeNs2) {
        Logging.d(TAG, "Create new camera1 session on camera " + cameraId2);
        this.events = events2;
        this.captureToTexture = captureToTexture2;
        this.applicationContext = applicationContext2;
        this.surfaceTextureHelper = surfaceTextureHelper2;
        this.cameraId = cameraId2;
        this.camera = camera2;
        this.info = info2;
        this.captureFormat = captureFormat2;
        this.constructionTimeNs = constructionTimeNs2;
        surfaceTextureHelper2.setTextureSize(captureFormat2.width, captureFormat2.height);
        startCapturing();
    }

    public void stop() {
        Logging.d(TAG, "Stop camera1 session on camera " + this.cameraId);
        checkIsOnCameraThread();
        if (this.state != SessionState.STOPPED) {
            long stopStartTime = System.nanoTime();
            stopInternal();
            camera1StopTimeMsHistogram.addSample((int) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - stopStartTime));
        }
    }

    private void startCapturing() {
        Logging.d(TAG, "Start capturing");
        checkIsOnCameraThread();
        this.state = SessionState.RUNNING;
        this.camera.setErrorCallback(new Camera.ErrorCallback() {
            public void onError(int error, Camera camera) {
                String errorMessage;
                if (error == 100) {
                    errorMessage = "Camera server died!";
                } else {
                    errorMessage = "Camera error: " + error;
                }
                Logging.e(Camera1Session.TAG, errorMessage);
                Camera1Session.this.stopInternal();
                if (error == 2) {
                    Camera1Session.this.events.onCameraDisconnected(Camera1Session.this);
                } else {
                    Camera1Session.this.events.onCameraError(Camera1Session.this, errorMessage);
                }
            }
        });
        if (this.captureToTexture) {
            listenForTextureFrames();
        } else {
            listenForBytebufferFrames();
        }
        try {
            this.camera.startPreview();
        } catch (RuntimeException e) {
            stopInternal();
            this.events.onCameraError(this, e.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public void stopInternal() {
        Logging.d(TAG, "Stop internal");
        checkIsOnCameraThread();
        SessionState sessionState = this.state;
        SessionState sessionState2 = SessionState.STOPPED;
        if (sessionState == sessionState2) {
            Logging.d(TAG, "Camera is already stopped");
            return;
        }
        this.state = sessionState2;
        this.surfaceTextureHelper.stopListening();
        this.camera.stopPreview();
        this.camera.release();
        this.events.onCameraClosed(this);
        Logging.d(TAG, "Stop done");
    }

    private void listenForTextureFrames() {
        this.surfaceTextureHelper.startListening(new d(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$listenForTextureFrames$0 */
    public /* synthetic */ void a(VideoFrame frame) {
        checkIsOnCameraThread();
        if (this.state != SessionState.RUNNING) {
            Logging.d(TAG, "Texture frame captured but camera is no longer running.");
            return;
        }
        boolean z = true;
        if (!this.firstFrameReported) {
            camera1StartTimeMsHistogram.addSample((int) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.constructionTimeNs));
            this.firstFrameReported = true;
        }
        TextureBufferImpl textureBufferImpl = (TextureBufferImpl) frame.getBuffer();
        if (this.info.facing != 1) {
            z = false;
        }
        VideoFrame videoFrame = new VideoFrame(p0.a(textureBufferImpl, z, 0), getFrameOrientation(), frame.getTimestampNs(), "camera1Session-0-0", frame.getIsKeyFrame());
        this.events.onFrameCaptured(this, videoFrame);
        videoFrame.release();
    }

    private void listenForBytebufferFrames() {
        this.camera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
            public void onPreviewFrame(byte[] data, Camera callbackCamera) {
                Camera1Session.this.checkIsOnCameraThread();
                if (callbackCamera != Camera1Session.this.camera) {
                    Logging.e(Camera1Session.TAG, "Callback from a different camera. This should never happen.");
                } else if (Camera1Session.this.state != SessionState.RUNNING) {
                    Logging.d(Camera1Session.TAG, "Bytebuffer frame captured but camera is no longer running.");
                } else {
                    long captureTimeNs = TimeUnit.MILLISECONDS.toNanos(SystemClock.elapsedRealtime());
                    if (!Camera1Session.this.firstFrameReported) {
                        Camera1Session.camera1StartTimeMsHistogram.addSample((int) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - Camera1Session.this.constructionTimeNs));
                        boolean unused = Camera1Session.this.firstFrameReported = true;
                    }
                    VideoFrame videoFrame = new VideoFrame(new NV21Buffer(data, Camera1Session.this.captureFormat.width, Camera1Session.this.captureFormat.height, new c(this, data)), Camera1Session.this.getFrameOrientation(), captureTimeNs, "Camera1Session-0-0", 0);
                    Camera1Session.this.events.onFrameCaptured(Camera1Session.this, videoFrame);
                    videoFrame.release();
                }
            }

            /* access modifiers changed from: private */
            /* renamed from: lambda$onPreviewFrame$1 */
            public /* synthetic */ void b(byte[] data) {
                Camera1Session.this.cameraThreadHandler.post(new b(this, data));
            }

            /* access modifiers changed from: private */
            /* renamed from: lambda$onPreviewFrame$0 */
            public /* synthetic */ void a(byte[] data) {
                if (Camera1Session.this.state == SessionState.RUNNING) {
                    Camera1Session.this.camera.addCallbackBuffer(data);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public int getFrameOrientation() {
        int rotation = p0.b(this.applicationContext);
        Camera.CameraInfo cameraInfo = this.info;
        if (cameraInfo.facing == 0) {
            rotation = 360 - rotation;
        }
        return (cameraInfo.orientation + rotation) % 360;
    }

    /* access modifiers changed from: private */
    public void checkIsOnCameraThread() {
        if (Thread.currentThread() != this.cameraThreadHandler.getLooper().getThread()) {
            throw new IllegalStateException("Wrong thread");
        }
    }
}
