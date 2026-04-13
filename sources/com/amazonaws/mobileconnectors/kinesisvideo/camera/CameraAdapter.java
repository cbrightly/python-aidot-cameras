package com.amazonaws.mobileconnectors.kinesisvideo.camera;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.mobileconnectors.kinesisvideo.mediasource.android.AndroidCameraMediaSource;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CameraAdapter {
    private static final String TAG = "CameraAdapter";
    /* access modifiers changed from: private */
    public CameraCaptureSession mActivePreviewSession;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    /* access modifiers changed from: private */
    public CameraDevice mCameraDevice;
    private String mCameraId;
    /* access modifiers changed from: private */
    public Semaphore mCameraOpenCloseLock = new Semaphore(1);
    private final Context mContext;

    public CameraAdapter(Context context, String cameraId) {
        this.mContext = context;
        this.mCameraId = cameraId;
    }

    public void openCamera(AndroidCameraMediaSource.OpenCameraCallback cameraOpenCameraCallback) {
        startBackgroundThread();
        Context context = this.mContext;
        if (context == null || isActivityFinishing(context)) {
            Log.i(TAG, "trying to open camera when activity is stopped" + threadId());
            return;
        }
        Log.i(TAG, "trying to open camera" + threadId());
        CameraManager manager = (CameraManager) this.mContext.getSystemService("camera");
        try {
            tryAcquireLock();
            manager.openCamera(this.mCameraId, adaptCameraStateCallback(cameraOpenCameraCallback), new Handler(Looper.getMainLooper()));
        } catch (CameraAccessException e) {
            Log.e(TAG, "failed to open camera" + threadId(), e);
            cameraOpenCameraCallback.onError(new KinesisVideoException((Throwable) e));
        } catch (NullPointerException e2) {
            Log.e(TAG, "failed to open camera" + threadId(), e2);
            cameraOpenCameraCallback.onError(new KinesisVideoException((Throwable) e2));
        } catch (InterruptedException e3) {
            Log.e(TAG, "failed to open camera" + threadId(), e3);
            cameraOpenCameraCallback.onError(new KinesisVideoException((Throwable) e3));
        } catch (SecurityException e4) {
            Log.e(TAG, "Permission issue " + threadId(), e4);
            cameraOpenCameraCallback.onError(new KinesisVideoException((Throwable) e4));
        }
    }

    private boolean isActivityFinishing(Context context) {
        return Activity.class.isAssignableFrom(context.getClass()) && ((Activity) context).isFinishing();
    }

    private void startBackgroundThread() {
        HandlerThread handlerThread = new HandlerThread("CameraBackground");
        this.mBackgroundThread = handlerThread;
        handlerThread.start();
        this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper());
    }

    private void tryAcquireLock() {
        Log.d(TAG, "try acquire" + threadId());
        if (!this.mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("Time out waiting to lock camera opening." + threadId());
        }
    }

    private CameraDevice.StateCallback adaptCameraStateCallback(final AndroidCameraMediaSource.OpenCameraCallback cameraOpenCameraCallback) {
        return new CameraDevice.StateCallback() {
            public void onOpened(CameraDevice cameraDevice) {
                Log.i(CameraAdapter.TAG, "opened" + CameraAdapter.this.threadId());
                CameraDevice unused = CameraAdapter.this.mCameraDevice = cameraDevice;
                CameraAdapter.this.mCameraOpenCloseLock.release();
                cameraOpenCameraCallback.onOpened();
            }

            public void onDisconnected(CameraDevice cameraDevice) {
                Log.i(CameraAdapter.TAG, "disconnected" + CameraAdapter.this.threadId());
                CameraAdapter.this.mCameraOpenCloseLock.release();
                cameraDevice.close();
                CameraDevice unused = CameraAdapter.this.mCameraDevice = null;
            }

            public void onError(CameraDevice cameraDevice, int error) {
                Log.i(CameraAdapter.TAG, "error" + CameraAdapter.this.threadId());
                CameraAdapter.this.mCameraOpenCloseLock.release();
                cameraDevice.close();
                CameraDevice unused = CameraAdapter.this.mCameraDevice = null;
                cameraOpenCameraCallback.onError(new KinesisVideoException("blah"));
            }
        };
    }

    public void closeCamera() {
        stopActiveSession();
        releaseCamera();
        stopBackgroundThread();
    }

    private void stopActiveSession() {
        try {
            Log.d(TAG, "stopping active capture session");
            this.mActivePreviewSession.stopRepeating();
            this.mActivePreviewSession.close();
        } catch (CameraAccessException e) {
            Log.e(TAG, "unable to stop repeating capture session", e);
        }
    }

    private void releaseCamera() {
        Log.d(TAG, "releasing camera");
        try {
            this.mCameraOpenCloseLock.acquire();
            CameraDevice cameraDevice = this.mCameraDevice;
            if (cameraDevice != null) {
                cameraDevice.close();
                this.mCameraDevice = null;
            }
            this.mCameraOpenCloseLock.release();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing." + threadId());
        } catch (Throwable th) {
            this.mCameraOpenCloseLock.release();
            throw th;
        }
    }

    private void stopBackgroundThread() {
        Log.d(TAG, "stopping background thread");
        this.mBackgroundThread.quit();
        this.mBackgroundThread = null;
        this.mBackgroundHandler = null;
        Log.d(TAG, "stopped background thread");
    }

    public void startPreview(List<Surface> previewSurfaces, AndroidCameraMediaSource.CaptureCallback captureCallback) {
        try {
            closeActivePreviewSession();
            this.mCameraDevice.createCaptureSession(previewSurfaces, adaptCaptureSessionCallback(previewSurfaces, captureCallback), this.mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeActivePreviewSession() {
        CameraCaptureSession cameraCaptureSession = this.mActivePreviewSession;
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            this.mActivePreviewSession = null;
        }
    }

    private CameraCaptureSession.StateCallback adaptCaptureSessionCallback(final List<Surface> previewSurfaces, final AndroidCameraMediaSource.CaptureCallback captureCallback) {
        return new CameraCaptureSession.StateCallback() {
            public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                Log.i(CameraAdapter.TAG, "created capture session" + CameraAdapter.this.threadId());
                CameraCaptureSession unused = CameraAdapter.this.mActivePreviewSession = cameraCaptureSession;
                CameraAdapter.this.captureContinously(previewSurfaces);
                captureCallback.onStarted();
            }

            public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                Log.i(CameraAdapter.TAG, "failed to create capture session" + CameraAdapter.this.threadId());
                captureCallback.onFailed();
            }
        };
    }

    /* access modifiers changed from: private */
    public void captureContinously(List<Surface> previewSurfaces) {
        try {
            CaptureRequest.Builder previewRequestBuilder = this.mCameraDevice.createCaptureRequest(1);
            for (Surface surface : previewSurfaces) {
                previewRequestBuilder.addTarget(surface);
            }
            previewRequestBuilder.set(CaptureRequest.CONTROL_MODE, 1);
            this.mActivePreviewSession.setRepeatingRequest(previewRequestBuilder.build(), (CameraCaptureSession.CaptureCallback) null, this.mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public String threadId() {
        return " | threadId=" + Thread.currentThread().getId();
    }
}
