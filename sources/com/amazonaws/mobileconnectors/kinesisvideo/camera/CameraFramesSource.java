package com.amazonaws.mobileconnectors.kinesisvideo.camera;

import android.content.Context;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import com.amazonaws.kinesisvideo.client.mediasource.CameraMediaSourceConfiguration;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.mobileconnectors.kinesisvideo.encoding.EncoderWrapper;
import com.amazonaws.mobileconnectors.kinesisvideo.mediasource.android.AndroidCameraMediaSource;
import java.util.ArrayList;
import java.util.List;

public class CameraFramesSource {
    /* access modifiers changed from: private */
    public static final String TAG = CameraFramesSource.class.getSimpleName();
    /* access modifiers changed from: private */
    public CameraAdapter mCameraAdapter;
    private EncoderWrapper.CodecPrivateDataAvailableListener mCodecPrivateDataListener;
    /* access modifiers changed from: private */
    public EncoderWrapper mEncoderWrapper;
    /* access modifiers changed from: private */
    public final EncodingCancellationToken mEncodingCancellationToken;
    private EncoderWrapper.FrameAvailableListener mFrameAvailableListener;
    private final ImageReader mImageReader;
    private boolean mIsReleased = false;
    private final CameraMediaSourceConfiguration mMediaSourceConfiguration;

    public CameraFramesSource(ImageReader imageReader, CameraMediaSourceConfiguration mediaSourceConfiguration, EncodingCancellationToken encodingCancellationToken) {
        this.mMediaSourceConfiguration = mediaSourceConfiguration;
        this.mEncodingCancellationToken = encodingCancellationToken;
        this.mImageReader = imageReader;
        imageReader.setOnImageAvailableListener(getOnImageAvailableListener(), (Handler) null);
    }

    private ImageReader.OnImageAvailableListener getOnImageAvailableListener() {
        return new ImageReader.OnImageAvailableListener() {
            public void onImageAvailable(ImageReader imageReader) {
                Image image = imageReader.acquireNextImage();
                CameraFramesSource.this.mEncoderWrapper.encodeFrame(image, CameraFramesSource.this.mEncodingCancellationToken.isEncodingCancelled());
                image.close();
                if (CameraFramesSource.this.mEncodingCancellationToken.isEncodingCancelled()) {
                    CameraFramesSource.this.release();
                }
            }
        };
    }

    public void startEncoding(Context context, List<Surface> previewSurfaces, String cameraId) {
        List<Surface> cameraOutputSurfaces = new ArrayList<>();
        cameraOutputSurfaces.addAll(previewSurfaces);
        cameraOutputSurfaces.add(this.mImageReader.getSurface());
        CameraFramesSourceRunnableWrapper.startEncoding(this, context, cameraOutputSurfaces, cameraId);
    }

    public static class CameraFramesSourceRunnableWrapper implements Runnable {
        private final CameraFramesSource mCameraFramesSource;
        private final String mCameraId;
        private final List<Surface> mCameraOutputSurfaces;
        private final Context mContext;

        private CameraFramesSourceRunnableWrapper(CameraFramesSource cameraFramesSource, Context context, List<Surface> cameraOutputSurfaces, String cameraId) {
            this.mCameraFramesSource = cameraFramesSource;
            this.mContext = context;
            this.mCameraOutputSurfaces = cameraOutputSurfaces;
            this.mCameraId = cameraId;
        }

        public void run() {
            try {
                this.mCameraFramesSource.startCapturing(this.mContext, this.mCameraOutputSurfaces, this.mCameraId);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        public static void startEncoding(CameraFramesSource test, Context context, List<Surface> cameraOutputSurfaces, String cameraId) {
            new Thread(new CameraFramesSourceRunnableWrapper(test, context, cameraOutputSurfaces, cameraId), "CameraFramesSource").start();
        }
    }

    /* access modifiers changed from: private */
    public void startCapturing(Context context, List<Surface> cameraOutputSurfaces, String cameraId) {
        try {
            EncoderWrapper encoderWrapper = new EncoderWrapper(this.mMediaSourceConfiguration);
            this.mEncoderWrapper = encoderWrapper;
            encoderWrapper.setCodecPrivateDataAvailableListener(this.mCodecPrivateDataListener);
            this.mEncoderWrapper.setEncodedFrameAvailableListener(this.mFrameAvailableListener);
            prepareCameraAndPreview(context, cameraOutputSurfaces, cameraId);
        } catch (Throwable e) {
            String str = TAG;
            Log.e(str, "encoder loop exception" + threadId(), e);
        }
    }

    public void setCodecPrivateDataListener(EncoderWrapper.CodecPrivateDataAvailableListener listener) {
        this.mCodecPrivateDataListener = listener;
    }

    public void setFramesListener(EncoderWrapper.FrameAvailableListener listener) {
        this.mFrameAvailableListener = listener;
    }

    private void prepareCameraAndPreview(Context context, List<Surface> cameraOutputSurfaces, String cameraId) {
        if (this.mCameraAdapter == null) {
            this.mCameraAdapter = new CameraAdapter(context, cameraId);
            Log.i(TAG, "camera adapter");
            this.mCameraAdapter.openCamera(startPreviewWhenReady(cameraOutputSurfaces));
            return;
        }
        String str = TAG;
        Log.e(str, "starting camera frames source second time" + threadId());
        throw new RuntimeException("starting camera frames source second time");
    }

    /* access modifiers changed from: private */
    public void release() {
        if (!this.mIsReleased) {
            this.mIsReleased = true;
            String str = TAG;
            Log.i(str, "releasing everything");
            this.mEncoderWrapper.stop();
            releaseCamera();
            Log.i(str, "released everything");
        }
    }

    private void releaseCamera() {
        try {
            this.mCameraAdapter.closeCamera();
        } catch (Throwable th) {
            Log.e(TAG, "error releasing camera");
        }
    }

    private AndroidCameraMediaSource.OpenCameraCallback startPreviewWhenReady(final List<Surface> cameraOutputSurfaces) {
        return new AndroidCameraMediaSource.OpenCameraCallback() {
            public void onOpened() {
                String access$400 = CameraFramesSource.TAG;
                Log.i(access$400, "camera opened" + CameraFramesSource.threadId());
                CameraFramesSource.this.mCameraAdapter.startPreview(cameraOutputSurfaces, CameraFramesSource.this.getCaptureCallback());
            }

            public void onError(KinesisVideoException e) {
                String access$400 = CameraFramesSource.TAG;
                Log.e(access$400, "failed to open camera" + CameraFramesSource.threadId(), e);
                CameraFramesSource.this.release();
                throw new RuntimeException(e);
            }
        };
    }

    /* access modifiers changed from: private */
    public AndroidCameraMediaSource.CaptureCallback getCaptureCallback() {
        return new AndroidCameraMediaSource.CaptureCallback() {
            public void onStarted() {
                String access$400 = CameraFramesSource.TAG;
                Log.i(access$400, "capture started" + CameraFramesSource.threadId());
            }

            public void onFailed() {
                String access$400 = CameraFramesSource.TAG;
                Log.i(access$400, "capture failed" + CameraFramesSource.threadId());
            }
        };
    }

    /* access modifiers changed from: private */
    public static String threadId() {
        return " | threadId=" + Thread.currentThread().getId();
    }
}
