package com.didichuxing.doraemonkit.kit.gpsmock;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.gpsmock.BaseServiceHooker;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LocationHooker extends BaseServiceHooker {
    private static final String TAG = "LocationHooker";

    public String getServiceName() {
        return FirebaseAnalytics.Param.LOCATION;
    }

    public String getStubName() {
        return "android.location.ILocationManager$Stub";
    }

    public Map<String, BaseServiceHooker.MethodHandler> getMethodHandlers() {
        Map<String, BaseServiceHooker.MethodHandler> methodHandlers = new HashMap<>();
        methodHandlers.put("requestLocationUpdates", new RequestLocationUpdatesMethodHandler());
        methodHandlers.put("getLastLocation", new GetLastLocationMethodHandler());
        methodHandlers.put("getLastKnownLocation", new GetLastKnownLocationMethodHandler());
        return methodHandlers;
    }

    public void replaceBinder(Context context, IBinder proxy) {
        LocationManager locationManager = (LocationManager) context.getSystemService(FirebaseAnalytics.Param.LOCATION);
        if (locationManager != null) {
            Field mServiceField = locationManager.getClass().getDeclaredField("mService");
            mServiceField.setAccessible(true);
            mServiceField.set(locationManager, Class.forName(getStubName()).getDeclaredMethod("asInterface", new Class[]{IBinder.class}).invoke((Object) null, new Object[]{proxy}));
            mServiceField.setAccessible(false);
        }
    }

    public static class GetLastKnownLocationMethodHandler implements BaseServiceHooker.MethodHandler {
        GetLastKnownLocationMethodHandler() {
        }

        public Object onInvoke(Object originObject, Object proxyObject, Method method, Object[] args) {
            if (!GpsMockManager.getInstance().isMocking()) {
                return method.invoke(originObject, args);
            }
            Location lastKnownLocation = (Location) method.invoke(originObject, args);
            if (lastKnownLocation == null) {
                lastKnownLocation = LocationHooker.buildValidLocation((String) args[0].getClass().getDeclaredMethod("getProvider", new Class[0]).invoke(args[0], new Object[0]));
            }
            lastKnownLocation.setLongitude(GpsMockManager.getInstance().getLongitude());
            lastKnownLocation.setLatitude(GpsMockManager.getInstance().getLatitude());
            lastKnownLocation.setTime(System.currentTimeMillis());
            if (Build.VERSION.SDK_INT >= 17) {
                lastKnownLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
            }
            return lastKnownLocation;
        }
    }

    public static class GetLastLocationMethodHandler implements BaseServiceHooker.MethodHandler {
        GetLastLocationMethodHandler() {
        }

        public Object onInvoke(Object originObject, Object proxyObject, Method method, Object[] args) {
            if (!GpsMockManager.getInstance().isMocking()) {
                return method.invoke(originObject, args);
            }
            Location lastLocation = (Location) method.invoke(originObject, args);
            if (lastLocation == null) {
                lastLocation = LocationHooker.buildValidLocation((String) null);
            }
            lastLocation.setLongitude(GpsMockManager.getInstance().getLongitude());
            lastLocation.setLatitude(GpsMockManager.getInstance().getLatitude());
            lastLocation.setTime(System.currentTimeMillis());
            if (Build.VERSION.SDK_INT >= 17) {
                lastLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
            }
            return lastLocation;
        }
    }

    public static class LocationListenerProxy implements LocationListener {
        LocationListener locationListener;

        private LocationListenerProxy(LocationListener locationListener2) {
            this.locationListener = locationListener2;
        }

        public void onLocationChanged(Location location) {
            if (this.locationListener != null) {
                if (GpsMockManager.getInstance().isMocking()) {
                    location.setLongitude(GpsMockManager.getInstance().getLongitude());
                    location.setLatitude(GpsMockManager.getInstance().getLatitude());
                    location.setTime(System.currentTimeMillis());
                }
                this.locationListener.onLocationChanged(location);
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener locationListener2 = this.locationListener;
            if (locationListener2 != null) {
                locationListener2.onStatusChanged(provider, status, extras);
            }
        }

        public void onProviderEnabled(String provider) {
            LocationListener locationListener2 = this.locationListener;
            if (locationListener2 != null) {
                locationListener2.onProviderEnabled(provider);
            }
        }

        public void onProviderDisabled(String provider) {
            LocationListener locationListener2 = this.locationListener;
            if (locationListener2 != null) {
                locationListener2.onProviderDisabled(provider);
            }
        }
    }

    public static class RequestLocationUpdatesMethodHandler implements BaseServiceHooker.MethodHandler {
        RequestLocationUpdatesMethodHandler() {
        }

        public Object onInvoke(Object originService, Object proxy, Method method, Object[] args) {
            if (!GpsMockManager.getInstance().isMocking()) {
                return method.invoke(originService, args);
            }
            Object listenerTransport = args[1];
            Field mListenerField = listenerTransport.getClass().getDeclaredField("mListener");
            mListenerField.setAccessible(true);
            mListenerField.set(listenerTransport, new LocationListenerProxy((LocationListener) mListenerField.get(listenerTransport)));
            mListenerField.setAccessible(false);
            return method.invoke(originService, args);
        }
    }

    /* access modifiers changed from: private */
    public static Location buildValidLocation(String provider) {
        if (TextUtils.isEmpty(provider)) {
            provider = "gps";
        }
        Location validLocation = new Location(provider);
        validLocation.setAccuracy(5.36f);
        validLocation.setBearing(315.0f);
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            validLocation.setBearingAccuracyDegrees(52.285362f);
        }
        validLocation.setSpeed(0.79f);
        if (i >= 26) {
            validLocation.setSpeedAccuracyMetersPerSecond(0.9462558f);
        }
        if (i >= 26) {
            validLocation.setVerticalAccuracyMeters(8.0f);
        }
        validLocation.setTime(System.currentTimeMillis());
        if (i >= 17) {
            validLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }
        return validLocation;
    }
}
