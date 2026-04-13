package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.os.WorkSource;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class WorkSourceUtil {
    private static final int zza = Process.myUid();
    private static final Method zzb;
    private static final Method zzc;
    private static final Method zzd;
    private static final Method zze;
    private static final Method zzf;
    private static final Method zzg;
    private static final Method zzh;
    private static final Method zzi;
    @GuardedBy("WorkSourceUtil.class")
    private static Boolean zzj = null;

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00be  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00d1  */
    static {
        /*
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            java.lang.String r1 = "add"
            int r2 = android.os.Process.myUid()
            zza = r2
            r2 = 1
            r3 = 0
            r4 = 0
            java.lang.Class<android.os.WorkSource> r5 = android.os.WorkSource.class
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x001a }
            java.lang.Class r7 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x001a }
            r6[r3] = r7     // Catch:{ Exception -> 0x001a }
            java.lang.reflect.Method r5 = r5.getMethod(r1, r6)     // Catch:{ Exception -> 0x001a }
            goto L_0x001c
        L_0x001a:
            r5 = move-exception
            r5 = r4
        L_0x001c:
            zzb = r5
            boolean r5 = com.google.android.gms.common.util.PlatformVersion.isAtLeastJellyBeanMR2()
            r6 = 2
            if (r5 == 0) goto L_0x0035
            java.lang.Class<android.os.WorkSource> r5 = android.os.WorkSource.class
            java.lang.Class[] r7 = new java.lang.Class[r6]     // Catch:{ Exception -> 0x0034 }
            java.lang.Class r8 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x0034 }
            r7[r3] = r8     // Catch:{ Exception -> 0x0034 }
            r7[r2] = r0     // Catch:{ Exception -> 0x0034 }
            java.lang.reflect.Method r1 = r5.getMethod(r1, r7)     // Catch:{ Exception -> 0x0034 }
            goto L_0x0036
        L_0x0034:
            r1 = move-exception
        L_0x0035:
            r1 = r4
        L_0x0036:
            zzc = r1
            java.lang.Class<android.os.WorkSource> r1 = android.os.WorkSource.class
            java.lang.String r5 = "size"
            java.lang.Class[] r7 = new java.lang.Class[r3]     // Catch:{ Exception -> 0x0044 }
            java.lang.reflect.Method r1 = r1.getMethod(r5, r7)     // Catch:{ Exception -> 0x0044 }
            goto L_0x0046
        L_0x0044:
            r1 = move-exception
            r1 = r4
        L_0x0046:
            zzd = r1
            java.lang.Class<android.os.WorkSource> r1 = android.os.WorkSource.class
            java.lang.String r5 = "get"
            java.lang.Class[] r7 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x0057 }
            java.lang.Class r8 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x0057 }
            r7[r3] = r8     // Catch:{ Exception -> 0x0057 }
            java.lang.reflect.Method r1 = r1.getMethod(r5, r7)     // Catch:{ Exception -> 0x0057 }
            goto L_0x0059
        L_0x0057:
            r1 = move-exception
            r1 = r4
        L_0x0059:
            zze = r1
            boolean r1 = com.google.android.gms.common.util.PlatformVersion.isAtLeastJellyBeanMR2()
            if (r1 == 0) goto L_0x0071
            java.lang.Class<android.os.WorkSource> r1 = android.os.WorkSource.class
            java.lang.String r5 = "getName"
            java.lang.Class[] r7 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x0070 }
            java.lang.Class r8 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x0070 }
            r7[r3] = r8     // Catch:{ Exception -> 0x0070 }
            java.lang.reflect.Method r1 = r1.getMethod(r5, r7)     // Catch:{ Exception -> 0x0070 }
            goto L_0x0072
        L_0x0070:
            r1 = move-exception
        L_0x0071:
            r1 = r4
        L_0x0072:
            zzf = r1
            boolean r1 = com.google.android.gms.common.util.PlatformVersion.isAtLeastP()
            java.lang.String r5 = "WorkSourceUtil"
            if (r1 == 0) goto L_0x008f
            java.lang.Class<android.os.WorkSource> r1 = android.os.WorkSource.class
            java.lang.String r7 = "createWorkChain"
            java.lang.Class[] r8 = new java.lang.Class[r3]     // Catch:{ Exception -> 0x0087 }
            java.lang.reflect.Method r1 = r1.getMethod(r7, r8)     // Catch:{ Exception -> 0x0087 }
            goto L_0x0090
        L_0x0087:
            r1 = move-exception
            java.lang.String r7 = "Missing WorkChain API createWorkChain"
            android.util.Log.w(r5, r7, r1)
            r1 = r4
            goto L_0x0090
        L_0x008f:
            r1 = r4
        L_0x0090:
            zzg = r1
            boolean r1 = com.google.android.gms.common.util.PlatformVersion.isAtLeastP()
            if (r1 == 0) goto L_0x00b5
            java.lang.String r1 = "android.os.WorkSource$WorkChain"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ Exception -> 0x00ad }
            java.lang.String r7 = "addNode"
            java.lang.Class[] r6 = new java.lang.Class[r6]     // Catch:{ Exception -> 0x00ad }
            java.lang.Class r8 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x00ad }
            r6[r3] = r8     // Catch:{ Exception -> 0x00ad }
            r6[r2] = r0     // Catch:{ Exception -> 0x00ad }
            java.lang.reflect.Method r0 = r1.getMethod(r7, r6)     // Catch:{ Exception -> 0x00ad }
            goto L_0x00b6
        L_0x00ad:
            r0 = move-exception
            java.lang.String r1 = "Missing WorkChain class"
            android.util.Log.w(r5, r1, r0)
            r0 = r4
            goto L_0x00b6
        L_0x00b5:
            r0 = r4
        L_0x00b6:
            zzh = r0
            boolean r0 = com.google.android.gms.common.util.PlatformVersion.isAtLeastP()
            if (r0 == 0) goto L_0x00d1
            java.lang.Class<android.os.WorkSource> r0 = android.os.WorkSource.class
            java.lang.String r1 = "isEmpty"
            java.lang.Class[] r3 = new java.lang.Class[r3]     // Catch:{ Exception -> 0x00ce }
            java.lang.reflect.Method r0 = r0.getMethod(r1, r3)     // Catch:{ Exception -> 0x00ce }
            r0.setAccessible(r2)     // Catch:{ Exception -> 0x00cc }
            goto L_0x00d2
        L_0x00cc:
            r1 = move-exception
            goto L_0x00d2
        L_0x00ce:
            r0 = move-exception
            r0 = r4
            goto L_0x00d2
        L_0x00d1:
            r0 = r4
        L_0x00d2:
            zzi = r0
            zzj = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.WorkSourceUtil.<clinit>():void");
    }

    private WorkSourceUtil() {
    }

    @KeepForSdk
    public static void add(@NonNull WorkSource workSource, int uid, @NonNull String packageName) {
        Method method = zzc;
        if (method != null) {
            if (packageName == null) {
                packageName = "";
            }
            try {
                method.invoke(workSource, new Object[]{Integer.valueOf(uid), packageName});
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        } else {
            Method method2 = zzb;
            if (method2 != null) {
                try {
                    method2.invoke(workSource, new Object[]{Integer.valueOf(uid)});
                } catch (Exception e2) {
                    Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e2);
                }
            }
        }
    }

    @NonNull
    @KeepForSdk
    public static WorkSource fromPackage(@NonNull Context context, @NonNull String packageName) {
        if (context == null || context.getPackageManager() == null || packageName == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = Wrappers.packageManager(context).getApplicationInfo(packageName, 0);
            if (applicationInfo == null) {
                Log.e("WorkSourceUtil", "Could not get applicationInfo from package: ".concat(packageName));
                return null;
            }
            int i = applicationInfo.uid;
            WorkSource workSource = new WorkSource();
            add(workSource, i, packageName);
            return workSource;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("WorkSourceUtil", "Could not find package: ".concat(packageName));
            return null;
        }
    }

    @NonNull
    @KeepForSdk
    public static WorkSource fromPackageAndModuleExperimentalPi(@NonNull Context context, @NonNull String packageName, @NonNull String moduleName) {
        Method method;
        if (context == null || context.getPackageManager() == null || moduleName == null || packageName == null) {
            Log.w("WorkSourceUtil", "Unexpected null arguments");
            return null;
        }
        int i = -1;
        try {
            ApplicationInfo applicationInfo = Wrappers.packageManager(context).getApplicationInfo(packageName, 0);
            if (applicationInfo == null) {
                Log.e("WorkSourceUtil", "Could not get applicationInfo from package: ".concat(packageName));
            } else {
                i = applicationInfo.uid;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("WorkSourceUtil", "Could not find package: ".concat(packageName));
        }
        if (i < 0) {
            return null;
        }
        WorkSource workSource = new WorkSource();
        Method method2 = zzg;
        if (method2 == null || (method = zzh) == null) {
            add(workSource, i, packageName);
        } else {
            try {
                Object invoke = method2.invoke(workSource, new Object[0]);
                int i2 = zza;
                if (i != i2) {
                    method.invoke(invoke, new Object[]{Integer.valueOf(i), packageName});
                }
                method.invoke(invoke, new Object[]{Integer.valueOf(i2), moduleName});
            } catch (Exception e2) {
                Log.w("WorkSourceUtil", "Unable to assign chained blame through WorkSource", e2);
            }
        }
        return workSource;
    }

    @KeepForSdk
    public static int get(@NonNull WorkSource workSource, int i) {
        Method method = zze;
        if (method != null) {
            try {
                Object invoke = method.invoke(workSource, new Object[]{Integer.valueOf(i)});
                Preconditions.checkNotNull(invoke);
                return ((Integer) invoke).intValue();
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        }
        return 0;
    }

    @NonNull
    @KeepForSdk
    public static String getName(@NonNull WorkSource workSource, int i) {
        Method method = zzf;
        if (method == null) {
            return null;
        }
        try {
            return (String) method.invoke(workSource, new Object[]{Integer.valueOf(i)});
        } catch (Exception e) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            return null;
        }
    }

    @NonNull
    @KeepForSdk
    public static List<String> getNames(@NonNull WorkSource workSource) {
        int i;
        ArrayList arrayList = new ArrayList();
        if (workSource == null) {
            i = 0;
        } else {
            i = size(workSource);
        }
        if (i != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                String name = getName(workSource, i2);
                if (!Strings.isEmptyOrWhitespace(name)) {
                    Preconditions.checkNotNull(name);
                    arrayList.add(name);
                }
            }
        }
        return arrayList;
    }

    @KeepForSdk
    public static synchronized boolean hasWorkSourcePermission(@NonNull Context context) {
        synchronized (WorkSourceUtil.class) {
            Boolean bool = zzj;
            if (bool != null) {
                boolean booleanValue = bool.booleanValue();
                return booleanValue;
            }
            boolean z = false;
            if (context == null) {
                return false;
            }
            if (ContextCompat.checkSelfPermission(context, "android.permission.UPDATE_DEVICE_STATS") == 0) {
                z = true;
            }
            Boolean valueOf = Boolean.valueOf(z);
            zzj = valueOf;
            boolean booleanValue2 = valueOf.booleanValue();
            return booleanValue2;
        }
    }

    @KeepForSdk
    public static boolean isEmpty(@NonNull WorkSource workSource) {
        Method method = zzi;
        if (method != null) {
            try {
                Object invoke = method.invoke(workSource, new Object[0]);
                Preconditions.checkNotNull(invoke);
                return ((Boolean) invoke).booleanValue();
            } catch (Exception e) {
                Log.e("WorkSourceUtil", "Unable to check WorkSource emptiness", e);
            }
        }
        if (size(workSource) == 0) {
            return true;
        }
        return false;
    }

    @KeepForSdk
    public static int size(@NonNull WorkSource workSource) {
        Method method = zzd;
        if (method != null) {
            try {
                Object invoke = method.invoke(workSource, new Object[0]);
                Preconditions.checkNotNull(invoke);
                return ((Integer) invoke).intValue();
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        }
        return 0;
    }
}
