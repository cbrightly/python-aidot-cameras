package com.didichuxing.doraemonkit.zxing.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.view.SurfaceHolder;
import java.io.IOException;

public final class CameraManager {
    private static final int MAX_FRAME_HEIGHT = 360;
    private static final int MAX_FRAME_WIDTH = 480;
    private static final int MIN_FRAME_HEIGHT = 240;
    private static final int MIN_FRAME_WIDTH = 240;
    static final int SDK_INT;
    private static final String TAG = CameraManager.class.getSimpleName();
    private static CameraManager cameraManager;
    private final AutoFocusCallback autoFocusCallback;
    private Camera camera;
    private final CameraConfigurationManager configManager;
    private final Context context;
    private Rect framingRect;
    private Rect framingRectInPreview;
    private boolean initialized;
    private final PreviewCallback previewCallback;
    private boolean previewing;
    private final boolean useOneShotPreviewCallback;

    static {
        int sdkInt;
        try {
            sdkInt = Integer.parseInt(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkInt = 10000;
        }
        SDK_INT = sdkInt;
    }

    public static void init(Context context2) {
        if (cameraManager == null) {
            cameraManager = new CameraManager(context2);
        }
    }

    public static CameraManager get() {
        return cameraManager;
    }

    private CameraManager(Context context2) {
        this.context = context2;
        CameraConfigurationManager cameraConfigurationManager = new CameraConfigurationManager(context2);
        this.configManager = cameraConfigurationManager;
        boolean z = Integer.parseInt(Build.VERSION.SDK) > 3;
        this.useOneShotPreviewCallback = z;
        this.previewCallback = new PreviewCallback(cameraConfigurationManager, z);
        this.autoFocusCallback = new AutoFocusCallback();
    }

    public void openDriver(SurfaceHolder holder) {
        if (this.camera == null) {
            Camera open = Camera.open();
            this.camera = open;
            if (open != null) {
                open.setPreviewDisplay(holder);
                if (!this.initialized) {
                    this.initialized = true;
                    this.configManager.initFromCameraParameters(this.camera);
                }
                this.configManager.setDesiredCameraParameters(this.camera);
                FlashlightManager.enableFlashlight();
                return;
            }
            throw new IOException();
        }
    }

    public void closeDriver() {
        if (this.camera != null) {
            FlashlightManager.disableFlashlight();
            this.camera.release();
            this.camera = null;
        }
    }

    public void startPreview() {
        Camera camera2 = this.camera;
        if (camera2 != null && !this.previewing) {
            camera2.startPreview();
            this.previewing = true;
        }
    }

    public void stopPreview() {
        Camera camera2 = this.camera;
        if (camera2 != null && this.previewing) {
            if (!this.useOneShotPreviewCallback) {
                camera2.setPreviewCallback((Camera.PreviewCallback) null);
            }
            this.camera.stopPreview();
            this.previewCallback.setHandler((Handler) null, 0);
            this.autoFocusCallback.setHandler((Handler) null, 0);
            this.previewing = false;
        }
    }

    public void requestPreviewFrame(Handler handler, int message) {
        if (this.camera != null && this.previewing) {
            this.previewCallback.setHandler(handler, message);
            if (this.useOneShotPreviewCallback) {
                this.camera.setOneShotPreviewCallback(this.previewCallback);
            } else {
                this.camera.setPreviewCallback(this.previewCallback);
            }
        }
    }

    public void requestAutoFocus(Handler handler, int message) {
        if (this.camera != null && this.previewing) {
            this.autoFocusCallback.setHandler(handler, message);
            this.camera.autoFocus(this.autoFocusCallback);
        }
    }

    public Rect getFramingRect() {
        Point screenResolution = this.configManager.getScreenResolution();
        if (screenResolution == null) {
            return null;
        }
        if (this.framingRect == null) {
            if (this.camera == null) {
                return null;
            }
            int i = screenResolution.x;
            int width = (i * 7) / 10;
            int i2 = screenResolution.y;
            int height = (i2 * 7) / 10;
            if (height >= width) {
                height = width;
            } else {
                width = height;
            }
            int leftOffset = (i - width) / 2;
            int topOffset = (i2 - height) / 2;
            this.framingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
        }
        return this.framingRect;
    }

    public Rect getFramingRectInPreview() {
        if (this.framingRectInPreview == null) {
            Rect rect = new Rect(getFramingRect());
            Point cameraResolution = this.configManager.getCameraResolution();
            Point screenResolution = this.configManager.getScreenResolution();
            int i = rect.left;
            int i2 = cameraResolution.y;
            int i3 = screenResolution.x;
            rect.left = (i * i2) / i3;
            rect.right = (rect.right * i2) / i3;
            int i4 = rect.top;
            int i5 = cameraResolution.x;
            int i6 = screenResolution.y;
            rect.top = (i4 * i5) / i6;
            rect.bottom = (rect.bottom * i5) / i6;
            this.framingRectInPreview = rect;
        }
        return this.framingRectInPreview;
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height) {
        Rect rect = getFramingRectInPreview();
        int previewFormat = this.configManager.getPreviewFormat();
        String previewFormatString = this.configManager.getPreviewFormatString();
        switch (previewFormat) {
            case 16:
            case 17:
                return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height());
            default:
                if ("yuv420p".equals(previewFormatString)) {
                    return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height());
                }
                throw new IllegalArgumentException("Unsupported picture format: " + previewFormat + '/' + previewFormatString);
        }
    }

    public Context getContext() {
        return this.context;
    }
}
