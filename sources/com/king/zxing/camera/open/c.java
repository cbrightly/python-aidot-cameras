package com.king.zxing.camera.open;

import android.hardware.Camera;
import com.king.zxing.util.b;

/* compiled from: OpenCameraInterface */
public final class c {
    public static b a(int cameraId) {
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            b.i("No cameras!");
            return null;
        } else if (cameraId >= numCameras) {
            b.i("Requested camera does not exist: " + cameraId);
            return null;
        } else {
            if (cameraId <= -1) {
                cameraId = 0;
                while (cameraId < numCameras) {
                    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    Camera.getCameraInfo(cameraId, cameraInfo);
                    if (a.values()[cameraInfo.facing] == a.BACK) {
                        break;
                    }
                    cameraId++;
                }
                if (cameraId == numCameras) {
                    b.f("No camera facing " + a.BACK + "; returning camera #0");
                    cameraId = 0;
                }
            }
            b.f("Opening camera #" + cameraId);
            Camera.CameraInfo cameraInfo2 = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, cameraInfo2);
            Camera camera = Camera.open(cameraId);
            if (camera == null) {
                return null;
            }
            return new b(cameraId, camera, a.values()[cameraInfo2.facing], cameraInfo2.orientation);
        }
    }
}
