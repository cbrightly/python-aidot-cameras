package com.google.android.gms.common.config;

import android.os.Binder;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.errorprone.annotations.InlineMe;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public abstract class GservicesValue<T> {
    private static final Object zzc = new Object();
    @NonNull
    protected final String zza;
    @NonNull
    protected final Object zzb;
    @Nullable
    private Object zzd = null;

    protected GservicesValue(@NonNull String str, @NonNull Object obj) {
        this.zza = str;
        this.zzb = obj;
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public static boolean isInitialized() {
        synchronized (zzc) {
        }
        return false;
    }

    @NonNull
    @KeepForSdk
    public static GservicesValue<Float> value(@NonNull String str, @NonNull Float f) {
        return new zzd(str, f);
    }

    @NonNull
    @KeepForSdk
    public static GservicesValue<Integer> value(@NonNull String str, @NonNull Integer num) {
        return new zzc(str, num);
    }

    @NonNull
    @KeepForSdk
    public static GservicesValue<Long> value(@NonNull String str, @NonNull Long l) {
        return new zzb(str, l);
    }

    @NonNull
    @KeepForSdk
    public static GservicesValue<String> value(@NonNull String str, @NonNull String str2) {
        return new zze(str, str2);
    }

    @NonNull
    @KeepForSdk
    public static GservicesValue<Boolean> value(@NonNull String key, boolean defaultValue) {
        return new zza(key, Boolean.valueOf(defaultValue));
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    @KeepForSdk
    public final T get() {
        long clearCallingIdentity;
        T t = this.zzd;
        if (t != null) {
            return t;
        }
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        Object obj = zzc;
        synchronized (obj) {
        }
        synchronized (obj) {
        }
        try {
            T zza2 = zza(this.zza);
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return zza2;
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            T zza3 = zza(this.zza);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            return zza3;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @InlineMe(replacement = "this.get()")
    @NonNull
    @KeepForSdk
    @Deprecated
    public final T getBinderSafe() {
        return get();
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    @com.google.android.gms.common.annotation.KeepForSdk
    @com.google.android.gms.common.util.VisibleForTesting
    public void override(@androidx.annotation.NonNull T r3) {
        /*
            r2 = this;
            java.lang.String r0 = "GservicesValue"
            java.lang.String r1 = "GservicesValue.override(): test should probably call initForTests() first"
            android.util.Log.w(r0, r1)
            r2.zzd = r3
            java.lang.Object r3 = zzc
            monitor-enter(r3)
            monitor-enter(r3)     // Catch:{ all -> 0x0013 }
            monitor-exit(r3)     // Catch:{ all -> 0x0010 }
            monitor-exit(r3)     // Catch:{ all -> 0x0013 }
            return
        L_0x0010:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0010 }
            throw r0     // Catch:{ all -> 0x0013 }
        L_0x0013:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0013 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.config.GservicesValue.override(java.lang.Object):void");
    }

    @KeepForSdk
    @VisibleForTesting
    public void resetOverride() {
        this.zzd = null;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public abstract Object zza(@NonNull String str);
}
