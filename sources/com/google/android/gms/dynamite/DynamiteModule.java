package com.google.android.gms.dynamite;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class DynamiteModule {
    @KeepForSdk
    public static final int LOCAL = -1;
    @KeepForSdk
    public static final int NONE = 0;
    @KeepForSdk
    public static final int NO_SELECTION = 0;
    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION = new zzi();
    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new zzj();
    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION = new zzk();
    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_LOCAL = new zzg();
    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_REMOTE = new zzf();
    @NonNull
    @KeepForSdk
    public static final VersionPolicy PREFER_REMOTE_VERSION_NO_FORCE_STAGING = new zzh();
    @KeepForSdk
    public static final int REMOTE = 1;
    @NonNull
    public static final VersionPolicy zza = new zzl();
    @GuardedBy("DynamiteModule.class")
    @Nullable
    private static Boolean zzb;
    @GuardedBy("DynamiteModule.class")
    @Nullable
    private static String zzc;
    @GuardedBy("DynamiteModule.class")
    private static boolean zzd;
    @GuardedBy("DynamiteModule.class")
    private static int zze = -1;
    @GuardedBy("DynamiteModule.class")
    @Nullable
    private static Boolean zzf = null;
    private static final ThreadLocal zzg = new ThreadLocal();
    private static final ThreadLocal zzh = new zzd();
    private static final VersionPolicy.IVersions zzi = new zze();
    @GuardedBy("DynamiteModule.class")
    @Nullable
    private static zzq zzk;
    @GuardedBy("DynamiteModule.class")
    @Nullable
    private static zzr zzl;
    private final Context zzj;

    @DynamiteApi
    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public static class DynamiteLoaderClassLoader {
        @GuardedBy("DynamiteLoaderClassLoader.class")
        @Nullable
        public static ClassLoader sClassLoader;
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public static class LoadingException extends Exception {
        /* synthetic */ LoadingException(String str, zzp zzp) {
            super(str);
        }

        /* synthetic */ LoadingException(String str, Throwable th, zzp zzp) {
            super(str, th);
        }
    }

    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public interface VersionPolicy {

        @KeepForSdk
        /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
        public interface IVersions {
            int zza(@NonNull Context context, @NonNull String str);

            int zzb(@NonNull Context context, @NonNull String str, boolean z);
        }

        @KeepForSdk
        /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
        public static class SelectionResult {
            @KeepForSdk
            public int localVersion = 0;
            @KeepForSdk
            public int remoteVersion = 0;
            @KeepForSdk
            public int selection = 0;
        }

        @NonNull
        @KeepForSdk
        SelectionResult selectModule(@NonNull Context context, @NonNull String str, @NonNull IVersions iVersions);
    }

    private DynamiteModule(Context context) {
        Preconditions.checkNotNull(context);
        this.zzj = context;
    }

    @KeepForSdk
    public static int getLocalVersion(@NonNull Context context, @NonNull String moduleId) {
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            Class<?> loadClass = classLoader.loadClass("com.google.android.gms.dynamite.descriptors." + moduleId + ".ModuleDescriptor");
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (Objects.equal(declaredField.get((Object) null), moduleId)) {
                return declaredField2.getInt((Object) null);
            }
            String valueOf = String.valueOf(declaredField.get((Object) null));
            Log.e("DynamiteModule", "Module descriptor id '" + valueOf + "' didn't match expected id '" + moduleId + "'");
            return 0;
        } catch (ClassNotFoundException e) {
            Log.w("DynamiteModule", "Local module descriptor class for " + moduleId + " not found.");
            return 0;
        } catch (Exception e2) {
            Log.e("DynamiteModule", "Failed to load module descriptor class: ".concat(String.valueOf(e2.getMessage())));
            return 0;
        }
    }

    @KeepForSdk
    public static int getRemoteVersion(@NonNull Context context, @NonNull String moduleId) {
        return zza(context, moduleId, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01bc, code lost:
        throw new com.google.android.gms.dynamite.DynamiteModule.LoadingException("No cached result cursor holder", (com.google.android.gms.dynamite.zzp) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01be, code lost:
        if (r4 != 2) goto L_0x01d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01c0, code lost:
        android.util.Log.w("DynamiteModule", "IDynamite loader version = 2");
        r2 = r2.zzj(com.google.android.gms.dynamic.ObjectWrapper.wrap(r18), r3, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x01d0, code lost:
        android.util.Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to createModuleContext");
        r2 = r2.zzh(com.google.android.gms.dynamic.ObjectWrapper.wrap(r18), r3, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x01df, code lost:
        r2 = com.google.android.gms.dynamic.ObjectWrapper.unwrap(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01e3, code lost:
        if (r2 == null) goto L_0x020a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x01e5, code lost:
        r4 = new com.google.android.gms.dynamite.DynamiteModule((android.content.Context) r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01f0, code lost:
        if (r10 != 0) goto L_0x01f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01f2, code lost:
        r9.remove();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01f6, code lost:
        r9.set(java.lang.Long.valueOf(r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01fe, code lost:
        r1 = r8.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0200, code lost:
        if (r1 == null) goto L_0x0205;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0202, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0205, code lost:
        r0.set(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0209, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0212, code lost:
        throw new com.google.android.gms.dynamite.DynamiteModule.LoadingException("Failed to load remote module.", (com.google.android.gms.dynamite.zzp) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x021b, code lost:
        throw new com.google.android.gms.dynamite.DynamiteModule.LoadingException("Failed to create IDynamiteLoader.", (com.google.android.gms.dynamite.zzp) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x021c, code lost:
        r17 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0226, code lost:
        throw new com.google.android.gms.dynamite.DynamiteModule.LoadingException("Failed to determine which loading route to use.", (com.google.android.gms.dynamite.zzp) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x0239, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x023b, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x023d, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b3, code lost:
        if (r15 == null) goto L_0x021c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b9, code lost:
        if (r15.booleanValue() == false) goto L_0x0170;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00bb, code lost:
        android.util.Log.i("DynamiteModule", "Selected remote version of " + r3 + ", version >= " + r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d9, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r2 = zzl;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00dc, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00dd, code lost:
        if (r2 == null) goto L_0x015e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        r6 = (com.google.android.gms.dynamite.zzn) r0.get();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00e6, code lost:
        if (r6 == null) goto L_0x0153;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00ea, code lost:
        if (r6.zza == null) goto L_0x0153;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ec, code lost:
        r15 = r18.getApplicationContext();
        r6 = r6.zza;
        com.google.android.gms.dynamic.ObjectWrapper.wrap(null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00f7, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00fa, code lost:
        r17 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00fd, code lost:
        if (zze < 2) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ff, code lost:
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0101, code lost:
        r12 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r5 = java.lang.Boolean.valueOf(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0106, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x010b, code lost:
        if (r5.booleanValue() == false) goto L_0x0121;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x010d, code lost:
        android.util.Log.v("DynamiteModule", "Dynamite loader version >= 2, using loadModule2NoCrashUtils");
        r2 = r2.zzf(com.google.android.gms.dynamic.ObjectWrapper.wrap(r15), r3, r13, com.google.android.gms.dynamic.ObjectWrapper.wrap(r6));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0121, code lost:
        android.util.Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to loadModule2");
        r2 = r2.zze(com.google.android.gms.dynamic.ObjectWrapper.wrap(r15), r3, r13, com.google.android.gms.dynamic.ObjectWrapper.wrap(r6));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0134, code lost:
        r2 = (android.content.Context) com.google.android.gms.dynamic.ObjectWrapper.unwrap(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x013a, code lost:
        if (r2 == null) goto L_0x0143;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x013c, code lost:
        r4 = new com.google.android.gms.dynamite.DynamiteModule(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x014b, code lost:
        throw new com.google.android.gms.dynamite.DynamiteModule.LoadingException("Failed to get module context", (com.google.android.gms.dynamite.zzp) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x014c, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x014d, code lost:
        r17 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:?, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0151, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0153, code lost:
        r17 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x015d, code lost:
        throw new com.google.android.gms.dynamite.DynamiteModule.LoadingException("No result cursor", (com.google.android.gms.dynamite.zzp) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x015e, code lost:
        r17 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0168, code lost:
        throw new com.google.android.gms.dynamite.DynamiteModule.LoadingException("DynamiteLoaderV2 was not cached.", (com.google.android.gms.dynamite.zzp) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0169, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x016a, code lost:
        r17 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:?, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x016e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0170, code lost:
        r17 = r5;
        android.util.Log.i("DynamiteModule", "Selected remote version of " + r3 + ", version >= " + r13);
        r2 = zzg(r18);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0194, code lost:
        if (r2 == null) goto L_0x0213;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0196, code lost:
        r4 = r2.zze();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x019b, code lost:
        if (r4 < 3) goto L_0x01bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x019d, code lost:
        r4 = (com.google.android.gms.dynamite.zzn) r0.get();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01a3, code lost:
        if (r4 == null) goto L_0x01b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01a5, code lost:
        r2 = r2.zzi(com.google.android.gms.dynamic.ObjectWrapper.wrap(r18), r3, r13, com.google.android.gms.dynamic.ObjectWrapper.wrap(r4.zza));
     */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x0281 A[Catch:{ all -> 0x030c }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:59:0x0102=Splitter:B:59:0x0102, B:86:0x016c=Splitter:B:86:0x016c} */
    @com.google.errorprone.annotations.ResultIgnorabilityUnspecified
    @androidx.annotation.NonNull
    @com.google.android.gms.common.annotation.KeepForSdk
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.dynamite.DynamiteModule load(@androidx.annotation.NonNull android.content.Context r18, @androidx.annotation.NonNull com.google.android.gms.dynamite.DynamiteModule.VersionPolicy r19, @androidx.annotation.NonNull java.lang.String r20) {
        /*
            r1 = r18
            r2 = r19
            r3 = r20
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r4 = com.google.android.gms.dynamite.DynamiteModule.class
            android.content.Context r5 = r18.getApplicationContext()
            r6 = 0
            if (r5 == 0) goto L_0x032f
            java.lang.ThreadLocal r0 = zzg
            java.lang.Object r7 = r0.get()
            com.google.android.gms.dynamite.zzn r7 = (com.google.android.gms.dynamite.zzn) r7
            com.google.android.gms.dynamite.zzn r8 = new com.google.android.gms.dynamite.zzn
            r8.<init>(r6)
            r0.set(r8)
            java.lang.ThreadLocal r9 = zzh
            java.lang.Object r10 = r9.get()
            java.lang.Long r10 = (java.lang.Long) r10
            long r10 = r10.longValue()
            long r14 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x030c }
            java.lang.Long r14 = java.lang.Long.valueOf(r14)     // Catch:{ all -> 0x030c }
            r9.set(r14)     // Catch:{ all -> 0x030c }
            com.google.android.gms.dynamite.DynamiteModule$VersionPolicy$IVersions r14 = zzi     // Catch:{ all -> 0x030c }
            com.google.android.gms.dynamite.DynamiteModule$VersionPolicy$SelectionResult r14 = r2.selectModule(r1, r3, r14)     // Catch:{ all -> 0x030c }
            java.lang.String r15 = "DynamiteModule"
            int r6 = r14.localVersion     // Catch:{ all -> 0x030c }
            int r12 = r14.remoteVersion     // Catch:{ all -> 0x030c }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x030c }
            r13.<init>()     // Catch:{ all -> 0x030c }
            java.lang.String r2 = "Considering local module "
            r13.append(r2)     // Catch:{ all -> 0x030c }
            r13.append(r3)     // Catch:{ all -> 0x030c }
            java.lang.String r2 = ":"
            r13.append(r2)     // Catch:{ all -> 0x030c }
            r13.append(r6)     // Catch:{ all -> 0x030c }
            java.lang.String r2 = " and remote module "
            r13.append(r2)     // Catch:{ all -> 0x030c }
            r13.append(r3)     // Catch:{ all -> 0x030c }
            java.lang.String r2 = ":"
            r13.append(r2)     // Catch:{ all -> 0x030c }
            r13.append(r12)     // Catch:{ all -> 0x030c }
            java.lang.String r2 = r13.toString()     // Catch:{ all -> 0x030c }
            android.util.Log.i(r15, r2)     // Catch:{ all -> 0x030c }
            int r2 = r14.selection     // Catch:{ all -> 0x030c }
            if (r2 == 0) goto L_0x02db
            r6 = -1
            if (r2 != r6) goto L_0x007a
            int r2 = r14.localVersion     // Catch:{ all -> 0x030c }
            if (r2 == 0) goto L_0x02db
            r2 = r6
        L_0x007a:
            r12 = 1
            if (r2 != r12) goto L_0x0081
            int r13 = r14.remoteVersion     // Catch:{ all -> 0x030c }
            if (r13 == 0) goto L_0x02db
        L_0x0081:
            if (r2 != r6) goto L_0x00a5
            com.google.android.gms.dynamite.DynamiteModule r1 = zzc(r5, r3)     // Catch:{ all -> 0x030c }
            r2 = 0
            int r2 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r2 != 0) goto L_0x0091
            r9.remove()
            goto L_0x0099
        L_0x0091:
            java.lang.Long r2 = java.lang.Long.valueOf(r10)
            r9.set(r2)
        L_0x0099:
            android.database.Cursor r2 = r8.zza
            if (r2 == 0) goto L_0x00a0
            r2.close()
        L_0x00a0:
            r0.set(r7)
            return r1
        L_0x00a5:
            if (r2 != r12) goto L_0x02c3
            int r13 = r14.remoteVersion     // Catch:{ LoadingException -> 0x0260 }
            monitor-enter(r4)     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            boolean r15 = zzf(r18)     // Catch:{ all -> 0x0234 }
            if (r15 == 0) goto L_0x0227
            java.lang.Boolean r15 = zzb     // Catch:{ all -> 0x0234 }
            monitor-exit(r4)     // Catch:{ all -> 0x0234 }
            if (r15 == 0) goto L_0x021c
            boolean r15 = r15.booleanValue()     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            if (r15 == 0) goto L_0x0170
            java.lang.String r15 = "DynamiteModule"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            r6.<init>()     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            java.lang.String r2 = "Selected remote version of "
            r6.append(r2)     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            r6.append(r3)     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            java.lang.String r2 = ", version >= "
            r6.append(r2)     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            r6.append(r13)     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            java.lang.String r2 = r6.toString()     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            android.util.Log.i(r15, r2)     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            monitor-enter(r4)     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            com.google.android.gms.dynamite.zzr r2 = zzl     // Catch:{ all -> 0x0169 }
            monitor-exit(r4)     // Catch:{ all -> 0x0169 }
            if (r2 == 0) goto L_0x015e
            java.lang.Object r6 = r0.get()     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            com.google.android.gms.dynamite.zzn r6 = (com.google.android.gms.dynamite.zzn) r6     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            if (r6 == 0) goto L_0x0153
            android.database.Cursor r15 = r6.zza     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            if (r15 == 0) goto L_0x0153
            android.content.Context r15 = r18.getApplicationContext()     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            android.database.Cursor r6 = r6.zza     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            r16 = 0
            com.google.android.gms.dynamic.ObjectWrapper.wrap(r16)     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            monitor-enter(r4)     // Catch:{ RemoteException -> 0x0252, LoadingException -> 0x024e, all -> 0x023f }
            int r12 = zze     // Catch:{ all -> 0x014c }
            r17 = r5
            r5 = 2
            if (r12 < r5) goto L_0x0101
            r12 = 1
            goto L_0x0102
        L_0x0101:
            r12 = 0
        L_0x0102:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r12)     // Catch:{ all -> 0x0151 }
            monitor-exit(r4)     // Catch:{ all -> 0x0151 }
            boolean r4 = r5.booleanValue()     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            if (r4 == 0) goto L_0x0121
            java.lang.String r4 = "DynamiteModule"
            java.lang.String r5 = "Dynamite loader version >= 2, using loadModule2NoCrashUtils"
            android.util.Log.v(r4, r5)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r4 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r15)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r5 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r6)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r2 = r2.zzf(r4, r3, r13, r5)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            goto L_0x0134
        L_0x0121:
            java.lang.String r4 = "DynamiteModule"
            java.lang.String r5 = "Dynamite loader version < 2, falling back to loadModule2"
            android.util.Log.w(r4, r5)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r4 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r15)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r5 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r6)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r2 = r2.zze(r4, r3, r13, r5)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x0134:
            java.lang.Object r2 = com.google.android.gms.dynamic.ObjectWrapper.unwrap(r2)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            android.content.Context r2 = (android.content.Context) r2     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            if (r2 == 0) goto L_0x0143
            com.google.android.gms.dynamite.DynamiteModule r4 = new com.google.android.gms.dynamite.DynamiteModule     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            r4.<init>(r2)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            goto L_0x01ec
        L_0x0143:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            java.lang.String r2 = "Failed to get module context"
            r4 = 0
            r0.<init>(r2, r4)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            throw r0     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x014c:
            r0 = move-exception
            r17 = r5
        L_0x014f:
            monitor-exit(r4)     // Catch:{ all -> 0x0151 }
            throw r0     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x0151:
            r0 = move-exception
            goto L_0x014f
        L_0x0153:
            r17 = r5
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            java.lang.String r2 = "No result cursor"
            r4 = 0
            r0.<init>(r2, r4)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            throw r0     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x015e:
            r17 = r5
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            java.lang.String r2 = "DynamiteLoaderV2 was not cached."
            r4 = 0
            r0.<init>(r2, r4)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            throw r0     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x0169:
            r0 = move-exception
            r17 = r5
        L_0x016c:
            monitor-exit(r4)     // Catch:{ all -> 0x016e }
            throw r0     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x016e:
            r0 = move-exception
            goto L_0x016c
        L_0x0170:
            r17 = r5
            java.lang.String r2 = "DynamiteModule"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            r4.<init>()     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            java.lang.String r5 = "Selected remote version of "
            r4.append(r5)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            r4.append(r3)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            java.lang.String r5 = ", version >= "
            r4.append(r5)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            r4.append(r13)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            java.lang.String r4 = r4.toString()     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            android.util.Log.i(r2, r4)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamite.zzq r2 = zzg(r18)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            if (r2 == 0) goto L_0x0213
            int r4 = r2.zze()     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            r5 = 3
            if (r4 < r5) goto L_0x01bd
            java.lang.Object r4 = r0.get()     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamite.zzn r4 = (com.google.android.gms.dynamite.zzn) r4     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            if (r4 == 0) goto L_0x01b4
            com.google.android.gms.dynamic.IObjectWrapper r5 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r18)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            android.database.Cursor r4 = r4.zza     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r4 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r4)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r2 = r2.zzi(r5, r3, r13, r4)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            goto L_0x01df
        L_0x01b4:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            java.lang.String r2 = "No cached result cursor holder"
            r4 = 0
            r0.<init>(r2, r4)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            throw r0     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x01bd:
            r5 = 2
            if (r4 != r5) goto L_0x01d0
            java.lang.String r4 = "DynamiteModule"
            java.lang.String r5 = "IDynamite loader version = 2"
            android.util.Log.w(r4, r5)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r4 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r18)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r2 = r2.zzj(r4, r3, r13)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            goto L_0x01df
        L_0x01d0:
            java.lang.String r4 = "DynamiteModule"
            java.lang.String r5 = "Dynamite loader version < 2, falling back to createModuleContext"
            android.util.Log.w(r4, r5)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r4 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r18)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            com.google.android.gms.dynamic.IObjectWrapper r2 = r2.zzh(r4, r3, r13)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x01df:
            java.lang.Object r2 = com.google.android.gms.dynamic.ObjectWrapper.unwrap(r2)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            if (r2 == 0) goto L_0x020a
            com.google.android.gms.dynamite.DynamiteModule r4 = new com.google.android.gms.dynamite.DynamiteModule     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            android.content.Context r2 = (android.content.Context) r2     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            r4.<init>(r2)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x01ec:
            r1 = 0
            int r1 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r1 != 0) goto L_0x01f6
            r9.remove()
            goto L_0x01fe
        L_0x01f6:
            java.lang.Long r1 = java.lang.Long.valueOf(r10)
            r9.set(r1)
        L_0x01fe:
            android.database.Cursor r1 = r8.zza
            if (r1 == 0) goto L_0x0205
            r1.close()
        L_0x0205:
            r0.set(r7)
            return r4
        L_0x020a:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            java.lang.String r2 = "Failed to load remote module."
            r4 = 0
            r0.<init>(r2, r4)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            throw r0     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x0213:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            java.lang.String r2 = "Failed to create IDynamiteLoader."
            r4 = 0
            r0.<init>(r2, r4)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            throw r0     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x021c:
            r17 = r5
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            java.lang.String r2 = "Failed to determine which loading route to use."
            r4 = 0
            r0.<init>(r2, r4)     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
            throw r0     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x0227:
            r17 = r5
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ all -> 0x0232 }
            java.lang.String r2 = "Remote loading disabled"
            r5 = 0
            r0.<init>(r2, r5)     // Catch:{ all -> 0x0232 }
            throw r0     // Catch:{ all -> 0x0232 }
        L_0x0232:
            r0 = move-exception
            goto L_0x0237
        L_0x0234:
            r0 = move-exception
            r17 = r5
        L_0x0237:
            monitor-exit(r4)     // Catch:{ all -> 0x0232 }
            throw r0     // Catch:{ RemoteException -> 0x023d, LoadingException -> 0x023b, all -> 0x0239 }
        L_0x0239:
            r0 = move-exception
            goto L_0x0242
        L_0x023b:
            r0 = move-exception
            goto L_0x0251
        L_0x023d:
            r0 = move-exception
            goto L_0x0255
        L_0x023f:
            r0 = move-exception
            r17 = r5
        L_0x0242:
            com.google.android.gms.common.util.CrashUtils.addDynamiteErrorToDropBox(r1, r0)     // Catch:{ LoadingException -> 0x025e }
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r2 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ LoadingException -> 0x025e }
            java.lang.String r4 = "Failed to load remote module."
            r5 = 0
            r2.<init>(r4, r0, r5)     // Catch:{ LoadingException -> 0x025e }
            throw r2     // Catch:{ LoadingException -> 0x025e }
        L_0x024e:
            r0 = move-exception
            r17 = r5
        L_0x0251:
            throw r0     // Catch:{ LoadingException -> 0x025e }
        L_0x0252:
            r0 = move-exception
            r17 = r5
        L_0x0255:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r2 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ LoadingException -> 0x025e }
            java.lang.String r4 = "Failed to load remote module."
            r5 = 0
            r2.<init>(r4, r0, r5)     // Catch:{ LoadingException -> 0x025e }
            throw r2     // Catch:{ LoadingException -> 0x025e }
        L_0x025e:
            r0 = move-exception
            goto L_0x0263
        L_0x0260:
            r0 = move-exception
            r17 = r5
        L_0x0263:
            java.lang.String r2 = "DynamiteModule"
            java.lang.String r4 = r0.getMessage()     // Catch:{ all -> 0x030c }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x030c }
            r5.<init>()     // Catch:{ all -> 0x030c }
            java.lang.String r6 = "Failed to load remote module: "
            r5.append(r6)     // Catch:{ all -> 0x030c }
            r5.append(r4)     // Catch:{ all -> 0x030c }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x030c }
            android.util.Log.w(r2, r4)     // Catch:{ all -> 0x030c }
            int r2 = r14.localVersion     // Catch:{ all -> 0x030c }
            if (r2 == 0) goto L_0x02ba
            com.google.android.gms.dynamite.zzo r4 = new com.google.android.gms.dynamite.zzo     // Catch:{ all -> 0x030c }
            r5 = 0
            r4.<init>(r2, r5)     // Catch:{ all -> 0x030c }
            r2 = r19
            com.google.android.gms.dynamite.DynamiteModule$VersionPolicy$SelectionResult r1 = r2.selectModule(r1, r3, r4)     // Catch:{ all -> 0x030c }
            int r1 = r1.selection     // Catch:{ all -> 0x030c }
            r2 = -1
            if (r1 != r2) goto L_0x02ba
            r1 = r17
            com.google.android.gms.dynamite.DynamiteModule r0 = zzc(r1, r3)     // Catch:{ all -> 0x030c }
            r1 = 0
            int r1 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r1 != 0) goto L_0x02a4
            java.lang.ThreadLocal r1 = zzh
            r1.remove()
            goto L_0x02ad
        L_0x02a4:
            java.lang.ThreadLocal r1 = zzh
            java.lang.Long r2 = java.lang.Long.valueOf(r10)
            r1.set(r2)
        L_0x02ad:
            android.database.Cursor r1 = r8.zza
            if (r1 == 0) goto L_0x02b4
            r1.close()
        L_0x02b4:
            java.lang.ThreadLocal r1 = zzg
            r1.set(r7)
            return r0
        L_0x02ba:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r1 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ all -> 0x030c }
            java.lang.String r2 = "Remote load failed. No local fallback found."
            r3 = 0
            r1.<init>(r2, r0, r3)     // Catch:{ all -> 0x030c }
            throw r1     // Catch:{ all -> 0x030c }
        L_0x02c3:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ all -> 0x030c }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x030c }
            r1.<init>()     // Catch:{ all -> 0x030c }
            java.lang.String r3 = "VersionPolicy returned invalid code:"
            r1.append(r3)     // Catch:{ all -> 0x030c }
            r1.append(r2)     // Catch:{ all -> 0x030c }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x030c }
            r2 = 0
            r0.<init>(r1, r2)     // Catch:{ all -> 0x030c }
            throw r0     // Catch:{ all -> 0x030c }
        L_0x02db:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ all -> 0x030c }
            int r1 = r14.localVersion     // Catch:{ all -> 0x030c }
            int r2 = r14.remoteVersion     // Catch:{ all -> 0x030c }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x030c }
            r4.<init>()     // Catch:{ all -> 0x030c }
            java.lang.String r5 = "No acceptable module "
            r4.append(r5)     // Catch:{ all -> 0x030c }
            r4.append(r3)     // Catch:{ all -> 0x030c }
            java.lang.String r3 = " found. Local version is "
            r4.append(r3)     // Catch:{ all -> 0x030c }
            r4.append(r1)     // Catch:{ all -> 0x030c }
            java.lang.String r1 = " and remote version is "
            r4.append(r1)     // Catch:{ all -> 0x030c }
            r4.append(r2)     // Catch:{ all -> 0x030c }
            java.lang.String r1 = "."
            r4.append(r1)     // Catch:{ all -> 0x030c }
            java.lang.String r1 = r4.toString()     // Catch:{ all -> 0x030c }
            r2 = 0
            r0.<init>(r1, r2)     // Catch:{ all -> 0x030c }
            throw r0     // Catch:{ all -> 0x030c }
        L_0x030c:
            r0 = move-exception
            r1 = 0
            int r1 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r1 != 0) goto L_0x0319
            java.lang.ThreadLocal r1 = zzh
            r1.remove()
            goto L_0x0322
        L_0x0319:
            java.lang.ThreadLocal r1 = zzh
            java.lang.Long r2 = java.lang.Long.valueOf(r10)
            r1.set(r2)
        L_0x0322:
            android.database.Cursor r1 = r8.zza
            if (r1 == 0) goto L_0x0329
            r1.close()
        L_0x0329:
            java.lang.ThreadLocal r1 = zzg
            r1.set(r7)
            throw r0
        L_0x032f:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException
            java.lang.String r1 = "null application Context"
            r2 = 0
            r0.<init>(r1, r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.load(android.content.Context, com.google.android.gms.dynamite.DynamiteModule$VersionPolicy, java.lang.String):com.google.android.gms.dynamite.DynamiteModule");
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:51:0x009c=Splitter:B:51:0x009c, B:18:0x003d=Splitter:B:18:0x003d} */
    public static int zza(@androidx.annotation.NonNull android.content.Context r10, @androidx.annotation.NonNull java.lang.String r11, boolean r12) {
        /*
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r0 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r0)     // Catch:{ all -> 0x01cb }
            java.lang.Boolean r1 = zzb     // Catch:{ all -> 0x01c8 }
            r2 = 0
            r3 = 0
            if (r1 != 0) goto L_0x00dc
            android.content.Context r1 = r10.getApplicationContext()     // Catch:{ ClassNotFoundException -> 0x00bd, IllegalAccessException -> 0x00bb, NoSuchFieldException -> 0x00b9 }
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x00bd, IllegalAccessException -> 0x00bb, NoSuchFieldException -> 0x00b9 }
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule$DynamiteLoaderClassLoader> r4 = com.google.android.gms.dynamite.DynamiteModule.DynamiteLoaderClassLoader.class
            java.lang.String r4 = r4.getName()     // Catch:{ ClassNotFoundException -> 0x00bd, IllegalAccessException -> 0x00bb, NoSuchFieldException -> 0x00b9 }
            java.lang.Class r1 = r1.loadClass(r4)     // Catch:{ ClassNotFoundException -> 0x00bd, IllegalAccessException -> 0x00bb, NoSuchFieldException -> 0x00b9 }
            java.lang.String r4 = "sClassLoader"
            java.lang.reflect.Field r1 = r1.getDeclaredField(r4)     // Catch:{ ClassNotFoundException -> 0x00bd, IllegalAccessException -> 0x00bb, NoSuchFieldException -> 0x00b9 }
            java.lang.Class r4 = r1.getDeclaringClass()     // Catch:{ ClassNotFoundException -> 0x00bd, IllegalAccessException -> 0x00bb, NoSuchFieldException -> 0x00b9 }
            monitor-enter(r4)     // Catch:{ ClassNotFoundException -> 0x00bd, IllegalAccessException -> 0x00bb, NoSuchFieldException -> 0x00b9 }
            java.lang.Object r5 = r1.get(r2)     // Catch:{ all -> 0x00b6 }
            java.lang.ClassLoader r5 = (java.lang.ClassLoader) r5     // Catch:{ all -> 0x00b6 }
            java.lang.ClassLoader r6 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x00b6 }
            if (r5 != r6) goto L_0x0036
            java.lang.Boolean r1 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x00b6 }
            goto L_0x00b4
        L_0x0036:
            if (r5 == 0) goto L_0x0041
            zzd(r5)     // Catch:{ LoadingException -> 0x003c }
            goto L_0x003d
        L_0x003c:
            r1 = move-exception
        L_0x003d:
            java.lang.Boolean r1 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x00b6 }
            goto L_0x00b4
        L_0x0041:
            boolean r5 = zzf(r10)     // Catch:{ all -> 0x00b6 }
            if (r5 != 0) goto L_0x004a
            monitor-exit(r4)     // Catch:{ all -> 0x00b6 }
            monitor-exit(r0)     // Catch:{ all -> 0x01c8 }
            return r3
        L_0x004a:
            boolean r5 = zzd     // Catch:{ all -> 0x00b6 }
            if (r5 != 0) goto L_0x00aa
            java.lang.Boolean r5 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x00b6 }
            boolean r6 = r5.equals(r2)     // Catch:{ all -> 0x00b6 }
            if (r6 == 0) goto L_0x0057
            goto L_0x00aa
        L_0x0057:
            r6 = 1
            int r6 = zzb(r10, r11, r12, r6)     // Catch:{ LoadingException -> 0x009f }
            java.lang.String r7 = zzc     // Catch:{ LoadingException -> 0x009f }
            if (r7 == 0) goto L_0x009c
            boolean r7 = r7.isEmpty()     // Catch:{ LoadingException -> 0x009f }
            if (r7 == 0) goto L_0x0067
            goto L_0x009c
        L_0x0067:
            java.lang.ClassLoader r7 = com.google.android.gms.dynamite.zzb.zza()     // Catch:{ LoadingException -> 0x009f }
            if (r7 == 0) goto L_0x006e
            goto L_0x0091
        L_0x006e:
            int r7 = android.os.Build.VERSION.SDK_INT     // Catch:{ LoadingException -> 0x009f }
            r8 = 29
            if (r7 < r8) goto L_0x0083
            dalvik.system.DelegateLastClassLoader r7 = new dalvik.system.DelegateLastClassLoader     // Catch:{ LoadingException -> 0x009f }
            java.lang.String r8 = zzc     // Catch:{ LoadingException -> 0x009f }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r8)     // Catch:{ LoadingException -> 0x009f }
            java.lang.ClassLoader r9 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ LoadingException -> 0x009f }
            r7.<init>(r8, r9)     // Catch:{ LoadingException -> 0x009f }
            goto L_0x0091
        L_0x0083:
            com.google.android.gms.dynamite.zzc r7 = new com.google.android.gms.dynamite.zzc     // Catch:{ LoadingException -> 0x009f }
            java.lang.String r8 = zzc     // Catch:{ LoadingException -> 0x009f }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r8)     // Catch:{ LoadingException -> 0x009f }
            java.lang.ClassLoader r9 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ LoadingException -> 0x009f }
            r7.<init>(r8, r9)     // Catch:{ LoadingException -> 0x009f }
        L_0x0091:
            zzd(r7)     // Catch:{ LoadingException -> 0x009f }
            r1.set(r2, r7)     // Catch:{ LoadingException -> 0x009f }
            zzb = r5     // Catch:{ LoadingException -> 0x009f }
            monitor-exit(r4)     // Catch:{ all -> 0x00b6 }
            monitor-exit(r0)     // Catch:{ all -> 0x01c8 }
            return r6
        L_0x009c:
            monitor-exit(r4)     // Catch:{ all -> 0x00b6 }
            monitor-exit(r0)     // Catch:{ all -> 0x01c8 }
            return r6
        L_0x009f:
            r5 = move-exception
            java.lang.ClassLoader r5 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x00b6 }
            r1.set(r2, r5)     // Catch:{ all -> 0x00b6 }
            java.lang.Boolean r1 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x00b6 }
            goto L_0x00b4
        L_0x00aa:
            java.lang.ClassLoader r5 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x00b6 }
            r1.set(r2, r5)     // Catch:{ all -> 0x00b6 }
            java.lang.Boolean r1 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x00b6 }
        L_0x00b4:
            monitor-exit(r4)     // Catch:{ all -> 0x00b6 }
            goto L_0x00da
        L_0x00b6:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00b6 }
            throw r1     // Catch:{ ClassNotFoundException -> 0x00bd, IllegalAccessException -> 0x00bb, NoSuchFieldException -> 0x00b9 }
        L_0x00b9:
            r1 = move-exception
            goto L_0x00be
        L_0x00bb:
            r1 = move-exception
            goto L_0x00be
        L_0x00bd:
            r1 = move-exception
        L_0x00be:
            java.lang.String r4 = "DynamiteModule"
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x01c8 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x01c8 }
            r5.<init>()     // Catch:{ all -> 0x01c8 }
            java.lang.String r6 = "Failed to load module via V2: "
            r5.append(r6)     // Catch:{ all -> 0x01c8 }
            r5.append(r1)     // Catch:{ all -> 0x01c8 }
            java.lang.String r1 = r5.toString()     // Catch:{ all -> 0x01c8 }
            android.util.Log.w(r4, r1)     // Catch:{ all -> 0x01c8 }
            java.lang.Boolean r1 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x01c8 }
        L_0x00da:
            zzb = r1     // Catch:{ all -> 0x01c8 }
        L_0x00dc:
            monitor-exit(r0)     // Catch:{ all -> 0x01c8 }
            boolean r0 = r1.booleanValue()     // Catch:{ all -> 0x01cb }
            if (r0 == 0) goto L_0x0104
            int r10 = zzb(r10, r11, r12, r3)     // Catch:{ LoadingException -> 0x00e8 }
            return r10
        L_0x00e8:
            r11 = move-exception
            java.lang.String r12 = "DynamiteModule"
            java.lang.String r11 = r11.getMessage()     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x01cb }
            r0.<init>()     // Catch:{ all -> 0x01cb }
            java.lang.String r1 = "Failed to retrieve remote module version: "
            r0.append(r1)     // Catch:{ all -> 0x01cb }
            r0.append(r11)     // Catch:{ all -> 0x01cb }
            java.lang.String r11 = r0.toString()     // Catch:{ all -> 0x01cb }
            android.util.Log.w(r12, r11)     // Catch:{ all -> 0x01cb }
            return r3
        L_0x0104:
            com.google.android.gms.dynamite.zzq r4 = zzg(r10)     // Catch:{ all -> 0x01cb }
            if (r4 != 0) goto L_0x010c
            goto L_0x01bf
        L_0x010c:
            int r0 = r4.zze()     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            r1 = 3
            if (r0 < r1) goto L_0x0176
            java.lang.ThreadLocal r0 = zzg     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            java.lang.Object r0 = r0.get()     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            com.google.android.gms.dynamite.zzn r0 = (com.google.android.gms.dynamite.zzn) r0     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            if (r0 == 0) goto L_0x0127
            android.database.Cursor r0 = r0.zza     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            if (r0 == 0) goto L_0x0127
            int r3 = r0.getInt(r3)     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            goto L_0x01bf
        L_0x0127:
            com.google.android.gms.dynamic.IObjectWrapper r5 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r10)     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            java.lang.ThreadLocal r0 = zzh     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            java.lang.Object r0 = r0.get()     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            long r8 = r0.longValue()     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            r6 = r11
            r7 = r12
            com.google.android.gms.dynamic.IObjectWrapper r11 = r4.zzk(r5, r6, r7, r8)     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            java.lang.Object r11 = com.google.android.gms.dynamic.ObjectWrapper.unwrap(r11)     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            android.database.Cursor r11 = (android.database.Cursor) r11     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            if (r11 == 0) goto L_0x0163
            boolean r12 = r11.moveToFirst()     // Catch:{ RemoteException -> 0x0173, all -> 0x0170 }
            if (r12 != 0) goto L_0x014c
            goto L_0x0163
        L_0x014c:
            int r12 = r11.getInt(r3)     // Catch:{ RemoteException -> 0x0173, all -> 0x0170 }
            if (r12 <= 0) goto L_0x015a
            boolean r0 = zze(r11)     // Catch:{ RemoteException -> 0x0173, all -> 0x0170 }
            if (r0 == 0) goto L_0x015a
            goto L_0x015b
        L_0x015a:
            r2 = r11
        L_0x015b:
            if (r2 == 0) goto L_0x0160
            r2.close()     // Catch:{ all -> 0x01cb }
        L_0x0160:
            r3 = r12
            goto L_0x01bf
        L_0x0163:
            java.lang.String r12 = "DynamiteModule"
            java.lang.String r0 = "Failed to retrieve remote module version."
            android.util.Log.w(r12, r0)     // Catch:{ RemoteException -> 0x0173, all -> 0x0170 }
            if (r11 == 0) goto L_0x01bd
            r11.close()     // Catch:{ all -> 0x01cb }
            goto L_0x01bd
        L_0x0170:
            r12 = move-exception
            r2 = r11
            goto L_0x01c2
        L_0x0173:
            r12 = move-exception
            r2 = r11
            goto L_0x019e
        L_0x0176:
            r1 = 2
            if (r0 != r1) goto L_0x0189
            java.lang.String r0 = "DynamiteModule"
            java.lang.String r1 = "IDynamite loader version = 2, no high precision latency measurement."
            android.util.Log.w(r0, r1)     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            com.google.android.gms.dynamic.IObjectWrapper r0 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r10)     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            int r3 = r4.zzg(r0, r11, r12)     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            goto L_0x01bf
        L_0x0189:
            java.lang.String r0 = "DynamiteModule"
            java.lang.String r1 = "IDynamite loader version < 2, falling back to getModuleVersion2"
            android.util.Log.w(r0, r1)     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            com.google.android.gms.dynamic.IObjectWrapper r0 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r10)     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            int r3 = r4.zzf(r0, r11, r12)     // Catch:{ RemoteException -> 0x019c, all -> 0x0199 }
            goto L_0x01bf
        L_0x0199:
            r11 = move-exception
            r12 = r11
            goto L_0x01c2
        L_0x019c:
            r11 = move-exception
            r12 = r11
        L_0x019e:
            java.lang.String r11 = "DynamiteModule"
            java.lang.String r12 = r12.getMessage()     // Catch:{ all -> 0x01c0 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x01c0 }
            r0.<init>()     // Catch:{ all -> 0x01c0 }
            java.lang.String r1 = "Failed to retrieve remote module version: "
            r0.append(r1)     // Catch:{ all -> 0x01c0 }
            r0.append(r12)     // Catch:{ all -> 0x01c0 }
            java.lang.String r12 = r0.toString()     // Catch:{ all -> 0x01c0 }
            android.util.Log.w(r11, r12)     // Catch:{ all -> 0x01c0 }
            if (r2 == 0) goto L_0x01be
            r2.close()     // Catch:{ all -> 0x01cb }
        L_0x01bd:
            goto L_0x01bf
        L_0x01be:
        L_0x01bf:
            return r3
        L_0x01c0:
            r11 = move-exception
            r12 = r11
        L_0x01c2:
            if (r2 == 0) goto L_0x01c7
            r2.close()     // Catch:{ all -> 0x01cb }
        L_0x01c7:
            throw r12     // Catch:{ all -> 0x01cb }
        L_0x01c8:
            r11 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x01c8 }
            throw r11     // Catch:{ all -> 0x01cb }
        L_0x01cb:
            r11 = move-exception
            com.google.android.gms.common.util.CrashUtils.addDynamiteErrorToDropBox(r10, r11)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, java.lang.String, boolean):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x00c8 A[Catch:{ all -> 0x00e4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00c9 A[Catch:{ all -> 0x00e4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00e8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int zzb(android.content.Context r10, java.lang.String r11, boolean r12, boolean r13) {
        /*
            r0 = 0
            java.lang.ThreadLocal r1 = zzh     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            java.lang.Object r1 = r1.get()     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            java.lang.Long r1 = (java.lang.Long) r1     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            long r1 = r1.longValue()     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            android.content.ContentResolver r3 = r10.getContentResolver()     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            java.lang.String r10 = "api_force_staging"
            java.lang.String r4 = "api"
            r9 = 1
            if (r9 == r12) goto L_0x0019
            r10 = r4
        L_0x0019:
            android.net.Uri$Builder r12 = new android.net.Uri$Builder     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            r12.<init>()     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            java.lang.String r4 = "content"
            android.net.Uri$Builder r12 = r12.scheme(r4)     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            java.lang.String r4 = "com.google.android.gms.chimera"
            android.net.Uri$Builder r12 = r12.authority(r4)     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            android.net.Uri$Builder r10 = r12.path(r10)     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            android.net.Uri$Builder r10 = r10.appendPath(r11)     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            java.lang.String r11 = "requestStartTime"
            java.lang.String r12 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            android.net.Uri$Builder r10 = r10.appendQueryParameter(r11, r12)     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            android.net.Uri r4 = r10.build()     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r10 = r3.query(r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x00c1, all -> 0x00be }
            if (r10 == 0) goto L_0x00aa
            boolean r11 = r10.moveToFirst()     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            if (r11 == 0) goto L_0x00aa
            r11 = 0
            int r12 = r10.getInt(r11)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            if (r12 <= 0) goto L_0x0091
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r1 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r1)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            r2 = 2
            java.lang.String r2 = r10.getString(r2)     // Catch:{ all -> 0x008e }
            zzc = r2     // Catch:{ all -> 0x008e }
            java.lang.String r2 = "loaderVersion"
            int r2 = r10.getColumnIndex(r2)     // Catch:{ all -> 0x008e }
            if (r2 < 0) goto L_0x0070
            int r2 = r10.getInt(r2)     // Catch:{ all -> 0x008e }
            zze = r2     // Catch:{ all -> 0x008e }
        L_0x0070:
            java.lang.String r2 = "disableStandaloneDynamiteLoader2"
            int r2 = r10.getColumnIndex(r2)     // Catch:{ all -> 0x008e }
            if (r2 < 0) goto L_0x0084
            int r2 = r10.getInt(r2)     // Catch:{ all -> 0x008e }
            if (r2 == 0) goto L_0x007f
            goto L_0x0080
        L_0x007f:
            r9 = r11
        L_0x0080:
            zzd = r9     // Catch:{ all -> 0x008e }
            r11 = r9
            goto L_0x0085
        L_0x0084:
        L_0x0085:
            monitor-exit(r1)     // Catch:{ all -> 0x008e }
            boolean r1 = zze(r10)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            if (r1 == 0) goto L_0x0092
            r10 = r0
            goto L_0x0092
        L_0x008e:
            r11 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x008e }
            throw r11     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
        L_0x0091:
        L_0x0092:
            if (r13 == 0) goto L_0x00a4
            if (r11 != 0) goto L_0x0097
            goto L_0x00a4
        L_0x0097:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r11 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            java.lang.String r12 = "forcing fallback to container DynamiteLoader impl"
            r11.<init>(r12, r0)     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
            throw r11     // Catch:{ Exception -> 0x00a2, all -> 0x009f }
        L_0x009f:
            r11 = move-exception
            r0 = r10
            goto L_0x00e6
        L_0x00a2:
            r11 = move-exception
            goto L_0x00c4
        L_0x00a4:
            if (r10 == 0) goto L_0x00a9
            r10.close()
        L_0x00a9:
            return r12
        L_0x00aa:
            java.lang.String r11 = "DynamiteModule"
            java.lang.String r12 = "Failed to retrieve remote module version."
            android.util.Log.w(r11, r12)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r11 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            java.lang.String r12 = "Failed to connect to dynamite module ContentResolver."
            r11.<init>(r12, r0)     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
            throw r11     // Catch:{ Exception -> 0x00bc, all -> 0x00b9 }
        L_0x00b9:
            r11 = move-exception
            r0 = r10
            goto L_0x00e6
        L_0x00bc:
            r11 = move-exception
            goto L_0x00c4
        L_0x00be:
            r10 = move-exception
            r11 = r10
            goto L_0x00e6
        L_0x00c1:
            r10 = move-exception
            r11 = r10
            r10 = r0
        L_0x00c4:
            boolean r12 = r11 instanceof com.google.android.gms.dynamite.DynamiteModule.LoadingException     // Catch:{ all -> 0x00e4 }
            if (r12 == 0) goto L_0x00c9
            throw r11     // Catch:{ all -> 0x00e4 }
        L_0x00c9:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r12 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ all -> 0x00e4 }
            java.lang.String r13 = r11.getMessage()     // Catch:{ all -> 0x00e4 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e4 }
            r1.<init>()     // Catch:{ all -> 0x00e4 }
            java.lang.String r2 = "V2 version check failed: "
            r1.append(r2)     // Catch:{ all -> 0x00e4 }
            r1.append(r13)     // Catch:{ all -> 0x00e4 }
            java.lang.String r13 = r1.toString()     // Catch:{ all -> 0x00e4 }
            r12.<init>(r13, r11, r0)     // Catch:{ all -> 0x00e4 }
            throw r12     // Catch:{ all -> 0x00e4 }
        L_0x00e4:
            r11 = move-exception
            r0 = r10
        L_0x00e6:
            if (r0 == 0) goto L_0x00eb
            r0.close()
        L_0x00eb:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzb(android.content.Context, java.lang.String, boolean, boolean):int");
    }

    private static DynamiteModule zzc(Context context, String str) {
        Log.i("DynamiteModule", "Selected local version of ".concat(String.valueOf(str)));
        return new DynamiteModule(context);
    }

    @GuardedBy("DynamiteModule.class")
    private static void zzd(ClassLoader classLoader) {
        zzr zzr;
        try {
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                zzr = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                if (queryLocalInterface instanceof zzr) {
                    zzr = (zzr) queryLocalInterface;
                } else {
                    zzr = new zzr(iBinder);
                }
            }
            zzl = zzr;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new LoadingException("Failed to instantiate dynamite loader", e, (zzp) null);
        }
    }

    private static boolean zze(Cursor cursor) {
        zzn zzn = (zzn) zzg.get();
        if (zzn == null || zzn.zza != null) {
            return false;
        }
        zzn.zza = cursor;
        return true;
    }

    @GuardedBy("DynamiteModule.class")
    private static boolean zzf(Context context) {
        ApplicationInfo applicationInfo;
        Boolean bool = Boolean.TRUE;
        if (bool.equals((Object) null) || bool.equals(zzf)) {
            return true;
        }
        boolean z = false;
        if (zzf == null) {
            ProviderInfo resolveContentProvider = context.getPackageManager().resolveContentProvider("com.google.android.gms.chimera", 0);
            if (GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context, 10000000) == 0 && resolveContentProvider != null && "com.google.android.gms".equals(resolveContentProvider.packageName)) {
                z = true;
            }
            Boolean valueOf = Boolean.valueOf(z);
            zzf = valueOf;
            z = valueOf.booleanValue();
            if (z && (applicationInfo = resolveContentProvider.applicationInfo) != null && (applicationInfo.flags & NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM) == 0) {
                Log.i("DynamiteModule", "Non-system-image GmsCore APK, forcing V1");
                zzd = true;
            }
        }
        if (!z) {
            Log.e("DynamiteModule", "Invalid GmsCore APK, remote loading disabled.");
        }
        return z;
    }

    @Nullable
    private static zzq zzg(Context context) {
        zzq zzq;
        synchronized (DynamiteModule.class) {
            zzq zzq2 = zzk;
            if (zzq2 != null) {
                return zzq2;
            }
            try {
                IBinder iBinder = (IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                if (iBinder == null) {
                    zzq = null;
                } else {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    zzq = queryLocalInterface instanceof zzq ? (zzq) queryLocalInterface : new zzq(iBinder);
                }
                if (zzq != null) {
                    zzk = zzq;
                    return zzq;
                }
            } catch (Exception e) {
                Log.e("DynamiteModule", "Failed to load IDynamiteLoader from GmsCore: " + e.getMessage());
            }
        }
        return null;
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    @KeepForSdk
    public Context getModuleContext() {
        return this.zzj;
    }

    @NonNull
    @KeepForSdk
    public IBinder instantiate(@NonNull String className) {
        try {
            return (IBinder) this.zzj.getClassLoader().loadClass(className).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new LoadingException("Failed to instantiate module class: ".concat(String.valueOf(className)), e, (zzp) null);
        }
    }
}
