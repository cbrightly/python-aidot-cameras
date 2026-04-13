package org.webrtc;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.SystemClock;
import android.util.AndroidException;
import android.util.Range;
import android.util.Size;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.webrtc.CameraEnumerationAndroid;
import org.webrtc.CameraVideoCapturer;

@TargetApi(21)
public class Camera2Enumerator implements CameraEnumerator {
    private static final double NANO_SECONDS_PER_SECOND = 1.0E9d;
    private static final String TAG = "Camera2Enumerator";
    private static final Map<String, List<CameraEnumerationAndroid.CaptureFormat>> cachedSupportedFormats = new HashMap();
    @Nullable
    final CameraManager cameraManager;
    final Context context;

    public Camera2Enumerator(Context context2) {
        this.context = context2;
        this.cameraManager = (CameraManager) context2.getSystemService("camera");
    }

    public String[] getDeviceNames() {
        try {
            return this.cameraManager.getCameraIdList();
        } catch (AndroidException e) {
            Logging.e(TAG, "Camera access exception", e);
            return new String[0];
        }
    }

    public boolean isFrontFacing(String deviceName) {
        CameraCharacteristics characteristics = getCameraCharacteristics(deviceName);
        return characteristics != null && ((Integer) characteristics.get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
    }

    public boolean isBackFacing(String deviceName) {
        CameraCharacteristics characteristics = getCameraCharacteristics(deviceName);
        if (characteristics == null || ((Integer) characteristics.get(CameraCharacteristics.LENS_FACING)).intValue() != 1) {
            return false;
        }
        return true;
    }

    @Nullable
    public List<CameraEnumerationAndroid.CaptureFormat> getSupportedFormats(String deviceName) {
        return getSupportedFormats(this.context, deviceName);
    }

    public CameraVideoCapturer createCapturer(String deviceName, CameraVideoCapturer.CameraEventsHandler eventsHandler) {
        return new Camera2Capturer(this.context, deviceName, eventsHandler);
    }

    @Nullable
    private CameraCharacteristics getCameraCharacteristics(String deviceName) {
        try {
            return this.cameraManager.getCameraCharacteristics(deviceName);
        } catch (AndroidException e) {
            Logging.e(TAG, "Camera access exception", e);
            return null;
        }
    }

    public static boolean isSupported(Context context2) {
        if (Build.VERSION.SDK_INT < 21) {
            return false;
        }
        CameraManager cameraManager2 = (CameraManager) context2.getSystemService("camera");
        try {
            for (String id : cameraManager2.getCameraIdList()) {
                if (((Integer) cameraManager2.getCameraCharacteristics(id).get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2) {
                    return false;
                }
            }
            return true;
        } catch (AndroidException | RuntimeException e) {
            Logging.e(TAG, "Failed to check if camera2 is supported", e);
            return false;
        }
    }

    static int getFpsUnitFactor(Range<Integer>[] fpsRanges) {
        if (fpsRanges.length != 0 && fpsRanges[0].getUpper().intValue() >= 1000) {
            return 1;
        }
        return 1000;
    }

    static List<Size> getSupportedSizes(CameraCharacteristics cameraCharacteristics) {
        int supportLevel = ((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue();
        List<Size> sizes = convertSizes(((StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(SurfaceTexture.class));
        if (Build.VERSION.SDK_INT >= 22 || supportLevel != 2) {
            return sizes;
        }
        Rect activeArraySize = (Rect) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        ArrayList<Size> filteredSizes = new ArrayList<>();
        for (Size size : sizes) {
            if (activeArraySize.width() * size.height == activeArraySize.height() * size.width) {
                filteredSizes.add(size);
            }
        }
        return filteredSizes;
    }

    @Nullable
    static List<CameraEnumerationAndroid.CaptureFormat> getSupportedFormats(Context context2, String cameraId) {
        return getSupportedFormats((CameraManager) context2.getSystemService("camera"), cameraId);
    }

    @Nullable
    static List<CameraEnumerationAndroid.CaptureFormat> getSupportedFormats(CameraManager cameraManager2, String cameraId) {
        List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> framerateRanges;
        Range<Integer>[] fpsRanges;
        StreamConfigurationMap streamMap;
        int maxFps;
        String str = cameraId;
        Map<String, List<CameraEnumerationAndroid.CaptureFormat>> map = cachedSupportedFormats;
        synchronized (map) {
            if (map.containsKey(str)) {
                List<CameraEnumerationAndroid.CaptureFormat> list = map.get(str);
                return list;
            }
            Logging.d(TAG, "Get supported formats for camera index " + str + ".");
            long startTimeMs = SystemClock.elapsedRealtime();
            try {
                CameraCharacteristics cameraCharacteristics = cameraManager2.getCameraCharacteristics(cameraId);
                StreamConfigurationMap streamMap2 = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                Range<Integer>[] fpsRanges2 = (Range[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
                List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> framerateRanges2 = convertFramerates(fpsRanges2, getFpsUnitFactor(fpsRanges2));
                List<Size> sizes = getSupportedSizes(cameraCharacteristics);
                int defaultMaxFps = 0;
                for (CameraEnumerationAndroid.CaptureFormat.FramerateRange framerateRange : framerateRanges2) {
                    defaultMaxFps = Math.max(defaultMaxFps, framerateRange.max);
                }
                List<CameraEnumerationAndroid.CaptureFormat> formatList = new ArrayList<>();
                for (Size size : sizes) {
                    long minFrameDurationNs = 0;
                    CameraCharacteristics cameraCharacteristics2 = cameraCharacteristics;
                    try {
                        fpsRanges = fpsRanges2;
                        try {
                            framerateRanges = framerateRanges2;
                            try {
                                minFrameDurationNs = streamMap2.getOutputMinFrameDuration(SurfaceTexture.class, new Size(size.width, size.height));
                            } catch (Exception e) {
                            }
                        } catch (Exception e2) {
                            framerateRanges = framerateRanges2;
                        }
                    } catch (Exception e3) {
                        fpsRanges = fpsRanges2;
                        framerateRanges = framerateRanges2;
                    }
                    if (minFrameDurationNs == 0) {
                        streamMap = streamMap2;
                        maxFps = defaultMaxFps;
                    } else {
                        streamMap = streamMap2;
                        maxFps = ((int) Math.round(NANO_SECONDS_PER_SECOND / ((double) minFrameDurationNs))) * 1000;
                    }
                    formatList.add(new CameraEnumerationAndroid.CaptureFormat(size.width, size.height, 0, maxFps));
                    Logging.d(TAG, "Format: " + size.width + "x" + size.height + "@" + maxFps);
                    cameraCharacteristics = cameraCharacteristics2;
                    fpsRanges2 = fpsRanges;
                    framerateRanges2 = framerateRanges;
                    streamMap2 = streamMap;
                }
                StreamConfigurationMap streamConfigurationMap = streamMap2;
                Range<Integer>[] rangeArr = fpsRanges2;
                List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> list2 = framerateRanges2;
                cachedSupportedFormats.put(str, formatList);
                long endTimeMs = SystemClock.elapsedRealtime();
                Logging.d(TAG, "Get supported formats for camera index " + str + " done. Time spent: " + (endTimeMs - startTimeMs) + " ms.");
                return formatList;
            } catch (Exception e4) {
                Logging.e(TAG, "getCameraCharacteristics()", e4);
                return new ArrayList();
            }
        }
    }

    private static List<Size> convertSizes(Size[] cameraSizes) {
        if (cameraSizes == null || cameraSizes.length == 0) {
            return Collections.emptyList();
        }
        List<Size> sizes = new ArrayList<>(cameraSizes.length);
        for (Size size : cameraSizes) {
            sizes.add(new Size(size.getWidth(), size.getHeight()));
        }
        return sizes;
    }

    static List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> convertFramerates(Range<Integer>[] arrayRanges, int unitFactor) {
        List<CameraEnumerationAndroid.CaptureFormat.FramerateRange> ranges = new ArrayList<>();
        for (Range<Integer> range : arrayRanges) {
            ranges.add(new CameraEnumerationAndroid.CaptureFormat.FramerateRange(range.getLower().intValue() * unitFactor, range.getUpper().intValue() * unitFactor));
        }
        return ranges;
    }
}
