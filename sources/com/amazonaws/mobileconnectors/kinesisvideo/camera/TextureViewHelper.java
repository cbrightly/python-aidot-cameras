package com.amazonaws.mobileconnectors.kinesisvideo.camera;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.mobileconnectors.kinesisvideo.mediasource.android.AndroidCameraMediaSource;
import java.util.Arrays;

public class TextureViewHelper {
    private static final String FRAGMENT_DIALOG = "fragmentDialog";
    private final Activity mActivity;
    /* access modifiers changed from: private */
    public final CameraAdapter mCameraAdapter;
    /* access modifiers changed from: private */
    public TextureReadyListener mTextureReadyListener;
    /* access modifiers changed from: private */
    public final TextureView mTextureView;

    public interface TextureReadyListener {
        void onTextureReady();
    }

    public TextureViewHelper(Activity activity, CameraAdapter cameraAdapter, TextureView textureView) {
        this.mActivity = activity;
        this.mCameraAdapter = cameraAdapter;
        this.mTextureView = textureView;
    }

    public void setTextureReadyListener(TextureReadyListener textureReadyListener) {
        this.mTextureReadyListener = textureReadyListener;
        if (this.mTextureView.isAvailable()) {
            this.mTextureReadyListener.onTextureReady();
        } else {
            this.mTextureView.setSurfaceTextureListener(callListenerWhenReady());
        }
    }

    private TextureView.SurfaceTextureListener callListenerWhenReady() {
        return new TextureView.SurfaceTextureListener() {
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                TextureViewHelper.this.mTextureReadyListener.onTextureReady();
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        };
    }

    public void startPreviewWhenTextureReady(AndroidCameraMediaSource.CaptureCallback captureCallback) {
        if (this.mTextureView.isAvailable()) {
            this.mCameraAdapter.openCamera(startPreviewWhenCameraOpen(captureCallback));
        } else {
            this.mTextureView.setSurfaceTextureListener(openCameraWhenTextureReady(captureCallback));
        }
    }

    private TextureView.SurfaceTextureListener openCameraWhenTextureReady(final AndroidCameraMediaSource.CaptureCallback captureCallback) {
        return new TextureView.SurfaceTextureListener() {
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                TextureViewHelper.this.mCameraAdapter.openCamera(TextureViewHelper.this.startPreviewWhenCameraOpen(captureCallback));
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return true;
            }

            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        };
    }

    /* access modifiers changed from: private */
    public AndroidCameraMediaSource.OpenCameraCallback startPreviewWhenCameraOpen(final AndroidCameraMediaSource.CaptureCallback captureCallback) {
        return new AndroidCameraMediaSource.OpenCameraCallback() {
            public void onOpened() {
                TextureViewHelper.this.mCameraAdapter.startPreview(Arrays.asList(new Surface[]{new Surface(TextureViewHelper.this.mTextureView.getSurfaceTexture())}), captureCallback);
            }

            public void onError(KinesisVideoException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
