package com.didichuxing.doraemonkit.kit.gpsmock;

public class GpsMockManager {
    private static final String TAG = "GpsMockManager";
    private static boolean isMocking;
    private static double mLatitude = -1.0d;
    private static double mLongitude = -1.0d;

    public void startMock() {
        isMocking = true;
    }

    public void stopMock() {
        isMocking = false;
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static GpsMockManager INSTANCE = new GpsMockManager();

        private Holder() {
        }
    }

    public static GpsMockManager getInstance() {
        return Holder.INSTANCE;
    }

    private GpsMockManager() {
    }

    public void mockLocation(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public boolean isMocking() {
        return (!isMocking || mLongitude == -1.0d || mLatitude == -1.0d) ? false : true;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public boolean isMockEnable() {
        return ServiceHookManager.getInstance().isHookSuccess();
    }
}
