package com.king.zxing.camera;

import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import com.king.zxing.util.b;
import com.meituan.robust.Constants;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import meshsdk.BaseResp;

/* compiled from: CameraConfigurationUtils */
public final class c {
    private static final Pattern a = Pattern.compile(Constants.PACKNAME_END);

    public static void h(Camera.Parameters parameters, boolean autoFocus, boolean disableContinuous, boolean safeMode) {
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        String focusMode = null;
        if (autoFocus) {
            if (safeMode || disableContinuous) {
                focusMode = c("focus mode", supportedFocusModes, "auto");
            } else {
                focusMode = c("focus mode", supportedFocusModes, "continuous-picture", "continuous-video", "auto");
            }
        }
        if (!safeMode && focusMode == null) {
            focusMode = c("focus mode", supportedFocusModes, "macro", "edof");
        }
        if (focusMode == null) {
            return;
        }
        if (focusMode.equals(parameters.getFocusMode())) {
            b.h("当前 focusMode 已经是:" + focusMode);
            b.a("Focus mode already set to " + focusMode);
            return;
        }
        b.h("设置 focusMode:" + focusMode);
        parameters.setFocusMode(focusMode);
    }

    public static void l(Camera.Parameters parameters, boolean on) {
        String flashMode;
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        if (on) {
            flashMode = c("flash mode", supportedFlashModes, "torch", "on");
        } else {
            flashMode = c("flash mode", supportedFlashModes, "off");
        }
        if (flashMode == null) {
            return;
        }
        if (flashMode.equals(parameters.getFlashMode())) {
            b.a("Flash mode already set to " + flashMode);
            return;
        }
        b.a("Setting flash mode to " + flashMode);
        parameters.setFlashMode(flashMode);
    }

    public static void e(Camera.Parameters parameters, boolean lightOn) {
        int minExposure = parameters.getMinExposureCompensation();
        int maxExposure = parameters.getMaxExposureCompensation();
        float step = parameters.getExposureCompensationStep();
        if (!(minExposure == 0 && maxExposure == 0)) {
            float targetCompensation = 0.0f;
            if (step > 0.0f) {
                if (!lightOn) {
                    targetCompensation = 1.2f;
                }
                int compensationSteps = Math.round(targetCompensation / step);
                float actualCompensation = ((float) compensationSteps) * step;
                int compensationSteps2 = Math.max(Math.min(compensationSteps, maxExposure), minExposure);
                if (parameters.getExposureCompensation() == compensationSteps2) {
                    b.a("Exposure compensation already set to " + compensationSteps2 + " / " + actualCompensation);
                    return;
                }
                b.a("Setting exposure compensation to " + compensationSteps2 + " / " + actualCompensation);
                parameters.setExposureCompensation(compensationSteps2);
                return;
            }
        }
        b.a("Camera does not support exposure compensation");
    }

    public static void f(Camera.Parameters parameters) {
        g(parameters, 40, 60);
    }

    public static void g(Camera.Parameters parameters, int minFPS, int maxFPS) {
        List<int[]> supportedPreviewFpsRanges = parameters.getSupportedPreviewFpsRange();
        b.a("Supported FPS ranges: " + o(supportedPreviewFpsRanges));
        if (supportedPreviewFpsRanges != null && !supportedPreviewFpsRanges.isEmpty()) {
            int[] suitableFPSRange = null;
            Iterator<int[]> it = supportedPreviewFpsRanges.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                int[] fpsRange = it.next();
                int thisMin = fpsRange[0];
                int thisMax = fpsRange[1];
                if (thisMin >= minFPS * 1000 && thisMax <= maxFPS * 1000) {
                    suitableFPSRange = fpsRange;
                    break;
                }
            }
            if (suitableFPSRange == null) {
                b.a("No suitable FPS range?");
                return;
            }
            int[] currentFpsRange = new int[2];
            parameters.getPreviewFpsRange(currentFpsRange);
            if (Arrays.equals(currentFpsRange, suitableFPSRange)) {
                b.a("FPS range already set to " + Arrays.toString(suitableFPSRange));
                return;
            }
            b.a("Setting FPS range to " + Arrays.toString(suitableFPSRange));
            parameters.setPreviewFpsRange(suitableFPSRange[0], suitableFPSRange[1]);
        }
    }

    public static void i(Camera.Parameters parameters) {
        if (parameters.getMaxNumFocusAreas() > 0) {
            b.a("Old focus areas: " + n(parameters.getFocusAreas()));
            List<Camera.Area> middleArea = a(BaseResp.ERR_MSG_SEND_FAIL);
            b.a("Setting focus area to : " + n(middleArea));
            parameters.setFocusAreas(middleArea);
            return;
        }
        b.a("Device does not support focus areas");
    }

    public static void k(Camera.Parameters parameters) {
        if (parameters.getMaxNumMeteringAreas() > 0) {
            b.a("Old metering areas: " + parameters.getMeteringAreas());
            List<Camera.Area> middleArea = a(BaseResp.ERR_MSG_SEND_FAIL);
            b.a("Setting metering area to : " + n(middleArea));
            parameters.setMeteringAreas(middleArea);
            return;
        }
        b.a("Device does not support metering areas");
    }

    private static List<Camera.Area> a(int areaPer1000) {
        return Collections.singletonList(new Camera.Area(new Rect(-areaPer1000, -areaPer1000, areaPer1000, areaPer1000), 1));
    }

    public static void m(Camera.Parameters parameters) {
        if (!parameters.isVideoStabilizationSupported()) {
            b.a("This device does not support video stabilization");
        } else if (parameters.getVideoStabilization()) {
            b.a("Video stabilization already enabled");
        } else {
            b.a("Enabling video stabilization...");
            parameters.setVideoStabilization(true);
        }
    }

    public static void d(Camera.Parameters parameters) {
        if ("barcode".equals(parameters.getSceneMode())) {
            b.a("Barcode scene mode already set");
            return;
        }
        String sceneMode = c("scene mode", parameters.getSupportedSceneModes(), "barcode");
        if (sceneMode != null) {
            parameters.setSceneMode(sceneMode);
        }
    }

    public static void j(Camera.Parameters parameters) {
        if ("negative".equals(parameters.getColorEffect())) {
            b.a("Negative effect already set");
            return;
        }
        String colorMode = c("color effect", parameters.getSupportedColorEffects(), "negative");
        if (colorMode != null) {
            parameters.setColorEffect(colorMode);
        }
    }

    public static Point b(Camera.Parameters parameters, Point screenResolution) {
        double screenAspectRatio;
        double screenAspectRatio2;
        Iterator<Camera.Size> it;
        List<Camera.Size> rawSupportedSizes;
        double screenAspectRatio3;
        Point point = screenResolution;
        List<Camera.Size> rawSupportedSizes2 = parameters.getSupportedPreviewSizes();
        if (rawSupportedSizes2 == null) {
            b.i("Device returned no supported preview sizes; using default");
            Camera.Size defaultSize = parameters.getPreviewSize();
            if (defaultSize != null) {
                return new Point(defaultSize.width, defaultSize.height);
            }
            throw new IllegalStateException("Parameters contained no preview size!");
        }
        if (b.g()) {
            StringBuilder previewSizesString = new StringBuilder();
            for (Camera.Size size : rawSupportedSizes2) {
                previewSizesString.append(size.width);
                previewSizesString.append('x');
                previewSizesString.append(size.height);
                previewSizesString.append(' ');
            }
            b.h("Supported preview sizes: " + previewSizesString);
        }
        int i = point.x;
        int i2 = point.y;
        if (i < i2) {
            screenAspectRatio = ((double) i) / ((double) i2);
        } else {
            screenAspectRatio = ((double) i2) / ((double) i);
        }
        b.h("screenAspectRatio: " + screenAspectRatio);
        int maxResolution = 0;
        Camera.Size maxResPreviewSize = null;
        Iterator<Camera.Size> it2 = rawSupportedSizes2.iterator();
        while (it2.hasNext()) {
            Camera.Size size2 = it2.next();
            int realWidth = size2.width;
            int realHeight = size2.height;
            int resolution = realWidth * realHeight;
            if (resolution < 153600) {
                rawSupportedSizes = rawSupportedSizes2;
                screenAspectRatio2 = screenAspectRatio;
                it = it2;
            } else {
                boolean isCandidatePortrait = realWidth < realHeight;
                int maybeFlippedWidth = isCandidatePortrait ? realWidth : realHeight;
                int maybeFlippedHeight = isCandidatePortrait ? realHeight : realWidth;
                rawSupportedSizes = rawSupportedSizes2;
                b.a(String.format(Locale.US, "maybeFlipped:%d * %d", new Object[]{Integer.valueOf(maybeFlippedWidth), Integer.valueOf(maybeFlippedHeight)}));
                Camera.Size size3 = size2;
                int maybeFlippedHeight2 = maybeFlippedHeight;
                it = it2;
                double aspectRatio = ((double) maybeFlippedWidth) / ((double) maybeFlippedHeight2);
                b.a("aspectRatio: " + aspectRatio);
                double distortion = Math.abs(aspectRatio - screenAspectRatio);
                screenAspectRatio2 = screenAspectRatio;
                b.a("distortion: " + distortion);
                if (distortion <= 0.05d) {
                    if (maybeFlippedWidth == point.x && maybeFlippedHeight2 == point.y) {
                        Point exactPoint = new Point(realWidth, realHeight);
                        b.h("....Found preview size exactly matching screen size: " + exactPoint);
                        return exactPoint;
                    }
                    if (resolution > maxResolution) {
                        maxResolution = resolution;
                        maxResPreviewSize = size3;
                    }
                    rawSupportedSizes2 = rawSupportedSizes;
                    it2 = it;
                    screenAspectRatio3 = screenAspectRatio2;
                }
            }
            rawSupportedSizes2 = rawSupportedSizes;
            it2 = it;
            screenAspectRatio3 = screenAspectRatio2;
        }
        double d = screenAspectRatio;
        if (maxResPreviewSize != null) {
            Point largestSize = new Point(maxResPreviewSize.width, maxResPreviewSize.height);
            b.a("Using largest suitable preview size: " + largestSize);
            return largestSize;
        }
        Camera.Size defaultPreview = parameters.getPreviewSize();
        if (defaultPreview != null) {
            Point defaultSize2 = new Point(defaultPreview.width, defaultPreview.height);
            b.a("No suitable preview sizes, using default: " + defaultSize2);
            return defaultSize2;
        }
        throw new IllegalStateException("Parameters contained no preview size!");
    }

    private static String c(String name, Collection<String> supportedValues, String... desiredValues) {
        b.a("Requesting " + name + " value from among: " + Arrays.toString(desiredValues));
        b.a("Supported " + name + " values: " + supportedValues);
        if (supportedValues != null) {
            for (String desiredValue : desiredValues) {
                if (supportedValues.contains(desiredValue)) {
                    b.a("Can set " + name + " to: " + desiredValue);
                    return desiredValue;
                }
            }
        }
        b.a("No supported values match");
        return null;
    }

    private static String o(Collection<int[]> arrays) {
        if (arrays == null || arrays.isEmpty()) {
            return "[]";
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        Iterator<int[]> it = arrays.iterator();
        while (it.hasNext()) {
            buffer.append(Arrays.toString(it.next()));
            if (it.hasNext()) {
                buffer.append(", ");
            }
        }
        buffer.append(']');
        return buffer.toString();
    }

    private static String n(Iterable<Camera.Area> areas) {
        if (areas == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (Camera.Area area : areas) {
            result.append(area.rect);
            result.append(':');
            result.append(area.weight);
            result.append(' ');
        }
        return result.toString();
    }
}
