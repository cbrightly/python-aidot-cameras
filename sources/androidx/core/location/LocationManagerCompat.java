package androidx.core.location;

import android.content.Context;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.DoNotInline;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.collection.SimpleArrayMap;
import androidx.core.location.GnssStatusCompat;
import androidx.core.os.CancellationSignal;
import androidx.core.os.ExecutorCompat;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public final class LocationManagerCompat {
    private static final long GET_CURRENT_LOCATION_TIMEOUT_MS = 30000;
    private static final long MAX_CURRENT_LOCATION_AGE_MS = 10000;
    private static final long PRE_N_LOOPER_TIMEOUT_S = 5;
    private static Field sContextField;
    @GuardedBy("sGnssStatusListeners")
    private static final SimpleArrayMap<Object, Object> sGnssStatusListeners = new SimpleArrayMap<>();

    public static boolean isLocationEnabled(@NonNull LocationManager locationManager) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            return Api28Impl.isLocationEnabled(locationManager);
        }
        if (i <= 19) {
            try {
                if (sContextField == null) {
                    Field declaredField = LocationManager.class.getDeclaredField("mContext");
                    sContextField = declaredField;
                    declaredField.setAccessible(true);
                }
                Context context = (Context) sContextField.get(locationManager);
                if (context != null) {
                    if (i != 19) {
                        return !TextUtils.isEmpty(Settings.Secure.getString(context.getContentResolver(), "location_providers_allowed"));
                    }
                    if (Settings.Secure.getInt(context.getContentResolver(), "location_mode", 0) != 0) {
                        return true;
                    }
                    return false;
                }
            } catch (ClassCastException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            }
        }
        if (locationManager.isProviderEnabled("network") || locationManager.isProviderEnabled("gps")) {
            return true;
        }
        return false;
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public static void getCurrentLocation(@NonNull LocationManager locationManager, @NonNull String provider, @Nullable CancellationSignal cancellationSignal, @NonNull Executor executor, @NonNull final Consumer<Location> consumer) {
        if (Build.VERSION.SDK_INT >= 30) {
            Api30Impl.getCurrentLocation(locationManager, provider, cancellationSignal, executor, consumer);
            return;
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        final Location location = locationManager.getLastKnownLocation(provider);
        if (location == null || SystemClock.elapsedRealtime() - LocationCompat.getElapsedRealtimeMillis(location) >= 10000) {
            final CancellableLocationListener listener = new CancellableLocationListener(locationManager, executor, consumer);
            locationManager.requestLocationUpdates(provider, 0, 0.0f, listener, Looper.getMainLooper());
            if (cancellationSignal != null) {
                cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
                    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
                    public void onCancel() {
                        CancellableLocationListener.this.cancel();
                    }
                });
            }
            listener.startTimeout(30000);
            return;
        }
        executor.execute(new Runnable() {
            public void run() {
                Consumer.this.accept(location);
            }
        });
    }

    @Nullable
    public static String getGnssHardwareModelName(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getGnssHardwareModelName(locationManager);
        }
        return null;
    }

    public static int getGnssYearOfHardware(@NonNull LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getGnssYearOfHardware(locationManager);
        }
        return 0;
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean registerGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull GnssStatusCompat.Callback callback, @NonNull Handler handler) {
        if (Build.VERSION.SDK_INT >= 30) {
            return registerGnssStatusCallback(locationManager, ExecutorCompat.create(handler), callback);
        }
        return registerGnssStatusCallback(locationManager, (Executor) new InlineHandlerExecutor(handler), callback);
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean registerGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull Executor executor, @NonNull GnssStatusCompat.Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            return registerGnssStatusCallback(locationManager, (Handler) null, executor, callback);
        }
        Looper looper = Looper.myLooper();
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        return registerGnssStatusCallback(locationManager, new Handler(looper), executor, callback);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00d5, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00e1, code lost:
        return false;
     */
    @androidx.annotation.RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean registerGnssStatusCallback(android.location.LocationManager r18, android.os.Handler r19, java.util.concurrent.Executor r20, androidx.core.location.GnssStatusCompat.Callback r21) {
        /*
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            int r0 = android.os.Build.VERSION.SDK_INT
            r5 = 1
            r6 = 0
            r7 = 30
            if (r0 < r7) goto L_0x0032
            androidx.collection.SimpleArrayMap<java.lang.Object, java.lang.Object> r7 = sGnssStatusListeners
            monitor-enter(r7)
            java.lang.Object r0 = r7.get(r4)     // Catch:{ all -> 0x002f }
            androidx.core.location.LocationManagerCompat$GnssStatusTransport r0 = (androidx.core.location.LocationManagerCompat.GnssStatusTransport) r0     // Catch:{ all -> 0x002f }
            if (r0 != 0) goto L_0x0022
            androidx.core.location.LocationManagerCompat$GnssStatusTransport r8 = new androidx.core.location.LocationManagerCompat$GnssStatusTransport     // Catch:{ all -> 0x002f }
            r8.<init>(r4)     // Catch:{ all -> 0x002f }
            r0 = r8
        L_0x0022:
            boolean r8 = r1.registerGnssStatusCallback(r3, r0)     // Catch:{ all -> 0x002f }
            if (r8 == 0) goto L_0x002d
            r7.put(r4, r0)     // Catch:{ all -> 0x002f }
            monitor-exit(r7)     // Catch:{ all -> 0x002f }
            return r5
        L_0x002d:
            monitor-exit(r7)     // Catch:{ all -> 0x002f }
            return r6
        L_0x002f:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x002f }
            throw r0
        L_0x0032:
            r7 = 24
            if (r0 < r7) goto L_0x0067
            if (r2 == 0) goto L_0x003a
            r0 = r5
            goto L_0x003b
        L_0x003a:
            r0 = r6
        L_0x003b:
            androidx.core.util.Preconditions.checkArgument(r0)
            androidx.collection.SimpleArrayMap<java.lang.Object, java.lang.Object> r7 = sGnssStatusListeners
            monitor-enter(r7)
            java.lang.Object r0 = r7.get(r4)     // Catch:{ all -> 0x0064 }
            androidx.core.location.LocationManagerCompat$PreRGnssStatusTransport r0 = (androidx.core.location.LocationManagerCompat.PreRGnssStatusTransport) r0     // Catch:{ all -> 0x0064 }
            if (r0 != 0) goto L_0x0051
            androidx.core.location.LocationManagerCompat$PreRGnssStatusTransport r8 = new androidx.core.location.LocationManagerCompat$PreRGnssStatusTransport     // Catch:{ all -> 0x0064 }
            r8.<init>(r4)     // Catch:{ all -> 0x0064 }
            r0 = r8
            goto L_0x0054
        L_0x0051:
            r0.unregister()     // Catch:{ all -> 0x0064 }
        L_0x0054:
            r0.register(r3)     // Catch:{ all -> 0x0064 }
            boolean r8 = r1.registerGnssStatusCallback(r0, r2)     // Catch:{ all -> 0x0064 }
            if (r8 == 0) goto L_0x0062
            r7.put(r4, r0)     // Catch:{ all -> 0x0064 }
            monitor-exit(r7)     // Catch:{ all -> 0x0064 }
            return r5
        L_0x0062:
            monitor-exit(r7)     // Catch:{ all -> 0x0064 }
            return r6
        L_0x0064:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0064 }
            throw r0
        L_0x0067:
            if (r2 == 0) goto L_0x006b
            r0 = r5
            goto L_0x006c
        L_0x006b:
            r0 = r6
        L_0x006c:
            androidx.core.util.Preconditions.checkArgument(r0)
            androidx.collection.SimpleArrayMap<java.lang.Object, java.lang.Object> r7 = sGnssStatusListeners
            monitor-enter(r7)
            java.lang.Object r0 = r7.get(r4)     // Catch:{ all -> 0x014c }
            androidx.core.location.LocationManagerCompat$GpsStatusTransport r0 = (androidx.core.location.LocationManagerCompat.GpsStatusTransport) r0     // Catch:{ all -> 0x014c }
            if (r0 != 0) goto L_0x0082
            androidx.core.location.LocationManagerCompat$GpsStatusTransport r8 = new androidx.core.location.LocationManagerCompat$GpsStatusTransport     // Catch:{ all -> 0x014c }
            r8.<init>(r1, r4)     // Catch:{ all -> 0x014c }
            r0 = r8
            goto L_0x0086
        L_0x0082:
            r0.unregister()     // Catch:{ all -> 0x014c }
            r8 = r0
        L_0x0086:
            r8.register(r3)     // Catch:{ all -> 0x014c }
            r9 = r8
            java.util.concurrent.FutureTask r0 = new java.util.concurrent.FutureTask     // Catch:{ all -> 0x014c }
            androidx.core.location.LocationManagerCompat$3 r10 = new androidx.core.location.LocationManagerCompat$3     // Catch:{ all -> 0x014c }
            r10.<init>(r1, r9)     // Catch:{ all -> 0x014c }
            r0.<init>(r10)     // Catch:{ all -> 0x014c }
            r10 = r0
            android.os.Looper r0 = android.os.Looper.myLooper()     // Catch:{ all -> 0x014c }
            android.os.Looper r11 = r19.getLooper()     // Catch:{ all -> 0x014c }
            if (r0 != r11) goto L_0x00a3
            r10.run()     // Catch:{ all -> 0x014c }
            goto L_0x00a9
        L_0x00a3:
            boolean r0 = r2.post(r10)     // Catch:{ all -> 0x014c }
            if (r0 == 0) goto L_0x0135
        L_0x00a9:
            r11 = 0
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ ExecutionException -> 0x0105, TimeoutException -> 0x00ed }
            r12 = 5
            long r12 = r0.toNanos(r12)     // Catch:{ ExecutionException -> 0x0105, TimeoutException -> 0x00ed }
            long r14 = java.lang.System.nanoTime()     // Catch:{ ExecutionException -> 0x0105, TimeoutException -> 0x00ed }
            long r14 = r14 + r12
        L_0x00b7:
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ InterruptedException -> 0x00e2 }
            java.lang.Object r0 = r10.get(r12, r0)     // Catch:{ InterruptedException -> 0x00e2 }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ InterruptedException -> 0x00e2 }
            boolean r0 = r0.booleanValue()     // Catch:{ InterruptedException -> 0x00e2 }
            if (r0 == 0) goto L_0x00d6
            androidx.collection.SimpleArrayMap<java.lang.Object, java.lang.Object> r0 = sGnssStatusListeners     // Catch:{ InterruptedException -> 0x00e2 }
            r0.put(r4, r9)     // Catch:{ InterruptedException -> 0x00e2 }
            if (r11 == 0) goto L_0x00d4
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x014c }
            r0.interrupt()     // Catch:{ all -> 0x014c }
        L_0x00d4:
            monitor-exit(r7)     // Catch:{ all -> 0x014c }
            return r5
        L_0x00d6:
            if (r11 == 0) goto L_0x00e0
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x014c }
            r0.interrupt()     // Catch:{ all -> 0x014c }
        L_0x00e0:
            monitor-exit(r7)     // Catch:{ all -> 0x014c }
            return r6
        L_0x00e2:
            r0 = move-exception
            r11 = 1
            long r16 = java.lang.System.nanoTime()     // Catch:{ ExecutionException -> 0x0105, TimeoutException -> 0x00ed }
            long r12 = r14 - r16
            goto L_0x00b7
        L_0x00eb:
            r0 = move-exception
            goto L_0x012a
        L_0x00ed:
            r0 = move-exception
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00eb }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00eb }
            r6.<init>()     // Catch:{ all -> 0x00eb }
            r6.append(r2)     // Catch:{ all -> 0x00eb }
            java.lang.String r12 = " appears to be blocked, please run registerGnssStatusCallback() directly on a Looper thread or ensure the main Looper is not blocked by this thread"
            r6.append(r12)     // Catch:{ all -> 0x00eb }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00eb }
            r5.<init>(r6, r0)     // Catch:{ all -> 0x00eb }
            throw r5     // Catch:{ all -> 0x00eb }
        L_0x0105:
            r0 = move-exception
            java.lang.Throwable r5 = r0.getCause()     // Catch:{ all -> 0x00eb }
            boolean r5 = r5 instanceof java.lang.RuntimeException     // Catch:{ all -> 0x00eb }
            if (r5 != 0) goto L_0x0123
            java.lang.Throwable r5 = r0.getCause()     // Catch:{ all -> 0x00eb }
            boolean r5 = r5 instanceof java.lang.Error     // Catch:{ all -> 0x00eb }
            if (r5 == 0) goto L_0x011d
            java.lang.Throwable r5 = r0.getCause()     // Catch:{ all -> 0x00eb }
            java.lang.Error r5 = (java.lang.Error) r5     // Catch:{ all -> 0x00eb }
            throw r5     // Catch:{ all -> 0x00eb }
        L_0x011d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00eb }
            r5.<init>(r0)     // Catch:{ all -> 0x00eb }
            throw r5     // Catch:{ all -> 0x00eb }
        L_0x0123:
            java.lang.Throwable r5 = r0.getCause()     // Catch:{ all -> 0x00eb }
            java.lang.RuntimeException r5 = (java.lang.RuntimeException) r5     // Catch:{ all -> 0x00eb }
            throw r5     // Catch:{ all -> 0x00eb }
        L_0x012a:
            if (r11 == 0) goto L_0x0133
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x014c }
            r5.interrupt()     // Catch:{ all -> 0x014c }
        L_0x0133:
            throw r0     // Catch:{ all -> 0x014c }
        L_0x0135:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x014c }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x014c }
            r5.<init>()     // Catch:{ all -> 0x014c }
            r5.append(r2)     // Catch:{ all -> 0x014c }
            java.lang.String r6 = " is shutting down"
            r5.append(r6)     // Catch:{ all -> 0x014c }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x014c }
            r0.<init>(r5)     // Catch:{ all -> 0x014c }
            throw r0     // Catch:{ all -> 0x014c }
        L_0x014c:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x014c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.location.LocationManagerCompat.registerGnssStatusCallback(android.location.LocationManager, android.os.Handler, java.util.concurrent.Executor, androidx.core.location.GnssStatusCompat$Callback):boolean");
    }

    public static void unregisterGnssStatusCallback(@NonNull LocationManager locationManager, @NonNull GnssStatusCompat.Callback callback) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            SimpleArrayMap<Object, Object> simpleArrayMap = sGnssStatusListeners;
            synchronized (simpleArrayMap) {
                GnssStatusTransport transport = (GnssStatusTransport) simpleArrayMap.remove(callback);
                if (transport != null) {
                    locationManager.unregisterGnssStatusCallback(transport);
                }
            }
        } else if (i >= 24) {
            SimpleArrayMap<Object, Object> simpleArrayMap2 = sGnssStatusListeners;
            synchronized (simpleArrayMap2) {
                PreRGnssStatusTransport transport2 = (PreRGnssStatusTransport) simpleArrayMap2.remove(callback);
                if (transport2 != null) {
                    transport2.unregister();
                    locationManager.unregisterGnssStatusCallback(transport2);
                }
            }
        } else {
            SimpleArrayMap<Object, Object> simpleArrayMap3 = sGnssStatusListeners;
            synchronized (simpleArrayMap3) {
                GpsStatusTransport transport3 = (GpsStatusTransport) simpleArrayMap3.remove(callback);
                if (transport3 != null) {
                    transport3.unregister();
                    locationManager.removeGpsStatusListener(transport3);
                }
            }
        }
    }

    private LocationManagerCompat() {
    }

    @RequiresApi(30)
    public static class GnssStatusTransport extends GnssStatus.Callback {
        final GnssStatusCompat.Callback mCallback;

        GnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mCallback = callback;
        }

        public void onStarted() {
            this.mCallback.onStarted();
        }

        public void onStopped() {
            this.mCallback.onStopped();
        }

        public void onFirstFix(int ttffMillis) {
            this.mCallback.onFirstFix(ttffMillis);
        }

        public void onSatelliteStatusChanged(GnssStatus status) {
            this.mCallback.onSatelliteStatusChanged(GnssStatusCompat.wrap(status));
        }
    }

    @RequiresApi(24)
    public static class PreRGnssStatusTransport extends GnssStatus.Callback {
        final GnssStatusCompat.Callback mCallback;
        @Nullable
        volatile Executor mExecutor;

        PreRGnssStatusTransport(GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mCallback = callback;
        }

        public void register(Executor executor) {
            boolean z = true;
            Preconditions.checkArgument(executor != null, "invalid null executor");
            if (this.mExecutor != null) {
                z = false;
            }
            Preconditions.checkState(z);
            this.mExecutor = executor;
        }

        public void unregister() {
            this.mExecutor = null;
        }

        public void onStarted() {
            final Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new Runnable() {
                    public void run() {
                        if (PreRGnssStatusTransport.this.mExecutor == executor) {
                            PreRGnssStatusTransport.this.mCallback.onStarted();
                        }
                    }
                });
            }
        }

        public void onStopped() {
            final Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new Runnable() {
                    public void run() {
                        if (PreRGnssStatusTransport.this.mExecutor == executor) {
                            PreRGnssStatusTransport.this.mCallback.onStopped();
                        }
                    }
                });
            }
        }

        public void onFirstFix(final int ttffMillis) {
            final Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new Runnable() {
                    public void run() {
                        if (PreRGnssStatusTransport.this.mExecutor == executor) {
                            PreRGnssStatusTransport.this.mCallback.onFirstFix(ttffMillis);
                        }
                    }
                });
            }
        }

        public void onSatelliteStatusChanged(final GnssStatus status) {
            final Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new Runnable() {
                    public void run() {
                        if (PreRGnssStatusTransport.this.mExecutor == executor) {
                            PreRGnssStatusTransport.this.mCallback.onSatelliteStatusChanged(GnssStatusCompat.wrap(status));
                        }
                    }
                });
            }
        }
    }

    public static class GpsStatusTransport implements GpsStatus.Listener {
        final GnssStatusCompat.Callback mCallback;
        @Nullable
        volatile Executor mExecutor;
        private final LocationManager mLocationManager;

        GpsStatusTransport(LocationManager locationManager, GnssStatusCompat.Callback callback) {
            Preconditions.checkArgument(callback != null, "invalid null callback");
            this.mLocationManager = locationManager;
            this.mCallback = callback;
        }

        public void register(Executor executor) {
            Preconditions.checkState(this.mExecutor == null);
            this.mExecutor = executor;
        }

        public void unregister() {
            this.mExecutor = null;
        }

        @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
        public void onGpsStatusChanged(int event) {
            final Executor executor = this.mExecutor;
            if (executor != null) {
                switch (event) {
                    case 1:
                        executor.execute(new Runnable() {
                            public void run() {
                                if (GpsStatusTransport.this.mExecutor == executor) {
                                    GpsStatusTransport.this.mCallback.onStarted();
                                }
                            }
                        });
                        return;
                    case 2:
                        executor.execute(new Runnable() {
                            public void run() {
                                if (GpsStatusTransport.this.mExecutor == executor) {
                                    GpsStatusTransport.this.mCallback.onStopped();
                                }
                            }
                        });
                        return;
                    case 3:
                        GpsStatus gpsStatus = this.mLocationManager.getGpsStatus((GpsStatus) null);
                        if (gpsStatus != null) {
                            final int ttff = gpsStatus.getTimeToFirstFix();
                            executor.execute(new Runnable() {
                                public void run() {
                                    if (GpsStatusTransport.this.mExecutor == executor) {
                                        GpsStatusTransport.this.mCallback.onFirstFix(ttff);
                                    }
                                }
                            });
                            return;
                        }
                        return;
                    case 4:
                        GpsStatus gpsStatus2 = this.mLocationManager.getGpsStatus((GpsStatus) null);
                        if (gpsStatus2 != null) {
                            final GnssStatusCompat gnssStatus = GnssStatusCompat.wrap(gpsStatus2);
                            executor.execute(new Runnable() {
                                public void run() {
                                    if (GpsStatusTransport.this.mExecutor == executor) {
                                        GpsStatusTransport.this.mCallback.onSatelliteStatusChanged(gnssStatus);
                                    }
                                }
                            });
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    @RequiresApi(30)
    public static class Api30Impl {
        private Api30Impl() {
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        @DoNotInline
        static void getCurrentLocation(LocationManager locationManager, @NonNull String provider, @Nullable CancellationSignal cancellationSignal, @NonNull Executor executor, @NonNull final Consumer<Location> consumer) {
            android.os.CancellationSignal cancellationSignal2;
            if (cancellationSignal != null) {
                cancellationSignal2 = (android.os.CancellationSignal) cancellationSignal.getCancellationSignalObject();
            } else {
                cancellationSignal2 = null;
            }
            locationManager.getCurrentLocation(provider, cancellationSignal2, executor, new java.util.function.Consumer<Location>() {
                public void accept(Location location) {
                    Consumer.this.accept(location);
                }
            });
        }
    }

    @RequiresApi(28)
    public static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        static boolean isLocationEnabled(LocationManager locationManager) {
            return locationManager.isLocationEnabled();
        }

        @DoNotInline
        static String getGnssHardwareModelName(LocationManager locationManager) {
            return locationManager.getGnssHardwareModelName();
        }

        @DoNotInline
        static int getGnssYearOfHardware(LocationManager locationManager) {
            return locationManager.getGnssYearOfHardware();
        }
    }

    public static final class CancellableLocationListener implements LocationListener {
        private Consumer<Location> mConsumer;
        private final Executor mExecutor;
        private final LocationManager mLocationManager;
        private final Handler mTimeoutHandler = new Handler(Looper.getMainLooper());
        @Nullable
        Runnable mTimeoutRunnable;
        @GuardedBy("this")
        private boolean mTriggered;

        CancellableLocationListener(LocationManager locationManager, Executor executor, Consumer<Location> consumer) {
            this.mLocationManager = locationManager;
            this.mExecutor = executor;
            this.mConsumer = consumer;
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void cancel() {
            synchronized (this) {
                if (!this.mTriggered) {
                    this.mTriggered = true;
                    cleanup();
                }
            }
        }

        public void startTimeout(long timeoutMs) {
            synchronized (this) {
                if (!this.mTriggered) {
                    AnonymousClass1 r0 = new Runnable() {
                        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
                        public void run() {
                            CancellableLocationListener cancellableLocationListener = CancellableLocationListener.this;
                            cancellableLocationListener.mTimeoutRunnable = null;
                            cancellableLocationListener.onLocationChanged((Location) null);
                        }
                    };
                    this.mTimeoutRunnable = r0;
                    this.mTimeoutHandler.postDelayed(r0, timeoutMs);
                }
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(@NonNull String provider) {
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void onProviderDisabled(@NonNull String p) {
            onLocationChanged((Location) null);
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        public void onLocationChanged(@Nullable final Location location) {
            synchronized (this) {
                if (!this.mTriggered) {
                    this.mTriggered = true;
                    final Consumer<Location> consumer = this.mConsumer;
                    this.mExecutor.execute(new Runnable() {
                        public void run() {
                            consumer.accept(location);
                        }
                    });
                    cleanup();
                }
            }
        }

        @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
        private void cleanup() {
            this.mConsumer = null;
            this.mLocationManager.removeUpdates(this);
            Runnable runnable = this.mTimeoutRunnable;
            if (runnable != null) {
                this.mTimeoutHandler.removeCallbacks(runnable);
                this.mTimeoutRunnable = null;
            }
        }
    }

    public static final class InlineHandlerExecutor implements Executor {
        private final Handler mHandler;

        InlineHandlerExecutor(@NonNull Handler handler) {
            this.mHandler = (Handler) Preconditions.checkNotNull(handler);
        }

        public void execute(@NonNull Runnable command) {
            if (Looper.myLooper() == this.mHandler.getLooper()) {
                command.run();
            } else if (!this.mHandler.post((Runnable) Preconditions.checkNotNull(command))) {
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }
}
