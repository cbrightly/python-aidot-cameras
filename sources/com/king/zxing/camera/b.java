package com.king.zxing.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Camera;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.WindowManager;
import com.king.zxing.camera.open.a;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

/* compiled from: CameraConfigurationManager */
public final class b {
    private final Context a;
    private int b;
    private int c;
    private Point d;
    private Point e;
    private Point f;
    private Point g;

    b(Context context) {
        this.a = context;
    }

    /* access modifiers changed from: package-private */
    public void e(com.king.zxing.camera.open.b camera) {
        int cwRotationFromNaturalToDisplay;
        Camera.Parameters parameters = null;
        Display display = null;
        int displayRotation = 0;
        try {
            parameters = camera.a().getParameters();
            display = ((WindowManager) this.a.getSystemService("window")).getDefaultDisplay();
            displayRotation = display.getRotation();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        switch (displayRotation) {
            case 0:
                cwRotationFromNaturalToDisplay = 0;
                break;
            case 1:
                cwRotationFromNaturalToDisplay = 90;
                break;
            case 2:
                cwRotationFromNaturalToDisplay = 180;
                break;
            case 3:
                cwRotationFromNaturalToDisplay = SubsamplingScaleImageView.ORIENTATION_270;
                break;
            default:
                if (displayRotation % 90 == 0) {
                    cwRotationFromNaturalToDisplay = (displayRotation + 360) % 360;
                    break;
                } else {
                    throw new IllegalArgumentException("Bad rotation: " + displayRotation);
                }
        }
        com.king.zxing.util.b.f("Display at: " + cwRotationFromNaturalToDisplay);
        int cwRotationFromNaturalToCamera = camera.c();
        com.king.zxing.util.b.f("Camera at: " + cwRotationFromNaturalToCamera);
        a b2 = camera.b();
        a aVar = a.FRONT;
        if (b2 == aVar) {
            cwRotationFromNaturalToCamera = (360 - cwRotationFromNaturalToCamera) % 360;
            com.king.zxing.util.b.f("Front camera overriden to: " + cwRotationFromNaturalToCamera);
        }
        this.c = ((cwRotationFromNaturalToCamera + 360) - cwRotationFromNaturalToDisplay) % 360;
        com.king.zxing.util.b.f("Final display orientation: " + this.c);
        if (camera.b() == aVar) {
            com.king.zxing.util.b.f("Compensating rotation for front camera");
            this.b = (360 - this.c) % 360;
        } else {
            this.b = this.c;
        }
        com.king.zxing.util.b.f("Clockwise rotation from display to camera: " + this.b);
        Point theScreenResolution = new Point();
        display.getSize(theScreenResolution);
        this.d = theScreenResolution;
        com.king.zxing.util.b.f("Screen resolution in current orientation: " + this.d);
        this.e = c.b(parameters, this.d);
        com.king.zxing.util.b.h("findBestPreviewSizeValue Camera resolution: " + this.e);
        this.f = c.b(parameters, this.d);
        com.king.zxing.util.b.h("findBestPreviewSizeValue Best available preview size: " + this.f);
        Point point = this.d;
        boolean isPreviewSizePortrait = true;
        boolean isScreenPortrait = point.x < point.y;
        Point point2 = this.f;
        if (point2.x >= point2.y) {
            isPreviewSizePortrait = false;
        }
        if (isScreenPortrait == isPreviewSizePortrait) {
            this.g = point2;
        } else {
            Point point3 = this.f;
            this.g = new Point(point3.y, point3.x);
        }
        com.king.zxing.util.b.f("Preview size on screen: " + this.g);
    }

    /* access modifiers changed from: package-private */
    public void g(com.king.zxing.camera.open.b camera, boolean safeMode) {
        Camera theCamera = camera.a();
        Camera.Parameters parameters = theCamera.getParameters();
        if (parameters == null) {
            com.king.zxing.util.b.i("Device error: no camera parameters are available. Proceeding without configuration.");
            return;
        }
        com.king.zxing.util.b.f("Initial camera parameters: " + parameters.flatten());
        if (safeMode) {
            com.king.zxing.util.b.i("In camera config safe mode -- most settings will not be honored");
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.a);
        if (parameters.isZoomSupported()) {
            parameters.setZoom(parameters.getMaxZoom() / 9);
            com.king.zxing.util.b.h("CameraConfigurationManager maxZoom:" + parameters.getMaxZoom() + ",setZoom:" + (parameters.getMaxZoom() / 9));
        } else {
            com.king.zxing.util.b.h("CameraConfigurationManager 不支持放大");
        }
        f(parameters, prefs, safeMode);
        c.h(parameters, prefs.getBoolean("preferences_auto_focus", true), prefs.getBoolean("preferences_disable_continuous_focus", true), safeMode);
        if (!safeMode) {
            if (prefs.getBoolean("preferences_invert_scan", false)) {
                c.j(parameters);
            }
            if (!prefs.getBoolean("preferences_disable_barcode_scene_mode", true)) {
                c.d(parameters);
            }
            if (!prefs.getBoolean("preferences_disable_metering", true)) {
                c.m(parameters);
                c.i(parameters);
                c.k(parameters);
            }
            parameters.setRecordingHint(true);
        }
        Point point = this.f;
        parameters.setPreviewSize(point.x, point.y);
        theCamera.setParameters(parameters);
        theCamera.setDisplayOrientation(this.c);
        Camera.Size afterSize = theCamera.getParameters().getPreviewSize();
        if (afterSize != null) {
            Point point2 = this.f;
            if (point2.x != afterSize.width || point2.y != afterSize.height) {
                com.king.zxing.util.b.i("Camera said it supported preview size " + this.f.x + 'x' + this.f.y + ", but after setting it, preview size is " + afterSize.width + 'x' + afterSize.height);
                Point point3 = this.f;
                point3.x = afterSize.width;
                point3.y = afterSize.height;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Point b() {
        return this.e;
    }

    /* access modifiers changed from: package-private */
    public Point c() {
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public boolean d(Camera camera) {
        Camera.Parameters parameters;
        if (camera == null || (parameters = camera.getParameters()) == null) {
            return false;
        }
        String flashMode = parameters.getFlashMode();
        if ("on".equals(flashMode) || "torch".equals(flashMode)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void h(Camera camera, boolean newSetting) {
        Camera.Parameters parameters = camera.getParameters();
        a(parameters, newSetting, false);
        camera.setParameters(parameters);
    }

    private void f(Camera.Parameters parameters, SharedPreferences prefs, boolean safeMode) {
        a(parameters, e.readPref(prefs) == e.ON, safeMode);
    }

    private void a(Camera.Parameters parameters, boolean newSetting, boolean safeMode) {
        c.l(parameters, newSetting);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.a);
        if (!safeMode && !prefs.getBoolean("preferences_disable_exposure", true)) {
            c.e(parameters, newSetting);
        }
        c.f(parameters);
    }
}
