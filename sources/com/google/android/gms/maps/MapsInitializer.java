package com.google.android.gms.maps;

import android.content.Context;
import androidx.annotation.NonNull;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
public final class MapsInitializer {
    private static final String zza = MapsInitializer.class.getSimpleName();
    @GuardedBy("MapsInitializer.class")
    private static boolean zzb = false;
    @GuardedBy("MapsInitializer.class")
    private static Renderer zzc = Renderer.LEGACY;

    /* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
    public enum Renderer {
        LEGACY,
        LATEST
    }

    private MapsInitializer() {
    }

    public static synchronized int initialize(@NonNull Context context) {
        int initialize;
        synchronized (MapsInitializer.class) {
            initialize = initialize(context, (Renderer) null, (OnMapsSdkInitializedCallback) null);
        }
        return initialize;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0086, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0029, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized int initialize(@androidx.annotation.NonNull android.content.Context r5, @androidx.annotation.Nullable com.google.android.gms.maps.MapsInitializer.Renderer r6, @androidx.annotation.Nullable com.google.android.gms.maps.OnMapsSdkInitializedCallback r7) {
        /*
            java.lang.Class<com.google.android.gms.maps.MapsInitializer> r0 = com.google.android.gms.maps.MapsInitializer.class
            monitor-enter(r0)
            java.lang.String r1 = "Context is null"
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r5, r1)     // Catch:{ all -> 0x0093 }
            java.lang.String r1 = zza     // Catch:{ all -> 0x0093 }
            java.lang.String r2 = "preferredRenderer: "
            java.lang.String r3 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x0093 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0093 }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ all -> 0x0093 }
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0093 }
            boolean r1 = zzb     // Catch:{ all -> 0x0093 }
            r2 = 0
            if (r1 == 0) goto L_0x002a
            if (r7 == 0) goto L_0x0028
            com.google.android.gms.maps.MapsInitializer$Renderer r5 = zzc     // Catch:{ all -> 0x0093 }
            r7.onMapsSdkInitialized(r5)     // Catch:{ all -> 0x0093 }
        L_0x0028:
            monitor-exit(r0)
            return r2
        L_0x002a:
            com.google.android.gms.maps.internal.zzf r1 = com.google.android.gms.maps.internal.zzcb.zza(r5, r6)     // Catch:{ GooglePlayServicesNotAvailableException -> 0x008e }
            com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate r3 = r1.zze()     // Catch:{ RemoteException -> 0x0087 }
            com.google.android.gms.maps.CameraUpdateFactory.zza(r3)     // Catch:{ RemoteException -> 0x0087 }
            com.google.android.gms.internal.maps.zzi r3 = r1.zzj()     // Catch:{ RemoteException -> 0x0087 }
            com.google.android.gms.maps.model.BitmapDescriptorFactory.zza(r3)     // Catch:{ RemoteException -> 0x0087 }
            r3 = 1
            zzb = r3     // Catch:{ all -> 0x0093 }
            r4 = 2
            if (r6 == 0) goto L_0x004e
            int r6 = r6.ordinal()     // Catch:{ all -> 0x0093 }
            switch(r6) {
                case 0: goto L_0x004d;
                case 1: goto L_0x004b;
                default: goto L_0x0049;
            }
        L_0x0049:
            r3 = r2
            goto L_0x004f
        L_0x004b:
            r3 = r4
            goto L_0x004f
        L_0x004d:
            goto L_0x004f
        L_0x004e:
            r3 = r2
        L_0x004f:
            int r6 = r1.zzd()     // Catch:{ RemoteException -> 0x0061 }
            if (r6 != r4) goto L_0x0059
            com.google.android.gms.maps.MapsInitializer$Renderer r6 = com.google.android.gms.maps.MapsInitializer.Renderer.LATEST     // Catch:{ RemoteException -> 0x0061 }
            zzc = r6     // Catch:{ RemoteException -> 0x0061 }
        L_0x0059:
            com.google.android.gms.dynamic.IObjectWrapper r5 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r5)     // Catch:{ RemoteException -> 0x0061 }
            r1.zzl(r5, r3)     // Catch:{ RemoteException -> 0x0061 }
            goto L_0x0069
        L_0x0061:
            r5 = move-exception
            java.lang.String r6 = zza     // Catch:{ all -> 0x0093 }
            java.lang.String r1 = "Failed to retrieve renderer type or log initialization."
            android.util.Log.e(r6, r1, r5)     // Catch:{ all -> 0x0093 }
        L_0x0069:
            java.lang.String r5 = zza     // Catch:{ all -> 0x0093 }
            java.lang.String r6 = "loadedRenderer: "
            com.google.android.gms.maps.MapsInitializer$Renderer r1 = zzc     // Catch:{ all -> 0x0093 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0093 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0093 }
            java.lang.String r6 = r6.concat(r1)     // Catch:{ all -> 0x0093 }
            android.util.Log.d(r5, r6)     // Catch:{ all -> 0x0093 }
            if (r7 == 0) goto L_0x0085
            com.google.android.gms.maps.MapsInitializer$Renderer r5 = zzc     // Catch:{ all -> 0x0093 }
            r7.onMapsSdkInitialized(r5)     // Catch:{ all -> 0x0093 }
        L_0x0085:
            monitor-exit(r0)
            return r2
        L_0x0087:
            r5 = move-exception
            com.google.android.gms.maps.model.RuntimeRemoteException r6 = new com.google.android.gms.maps.model.RuntimeRemoteException     // Catch:{ all -> 0x0093 }
            r6.<init>(r5)     // Catch:{ all -> 0x0093 }
            throw r6     // Catch:{ all -> 0x0093 }
        L_0x008e:
            r5 = move-exception
            int r5 = r5.errorCode     // Catch:{ all -> 0x0093 }
            monitor-exit(r0)
            return r5
        L_0x0093:
            r5 = move-exception
            monitor-exit(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.MapsInitializer.initialize(android.content.Context, com.google.android.gms.maps.MapsInitializer$Renderer, com.google.android.gms.maps.OnMapsSdkInitializedCallback):int");
    }
}
