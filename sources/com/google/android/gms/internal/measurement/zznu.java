package com.google.android.gms.internal.measurement;

import com.google.android.libraries.places.api.model.PlaceTypes;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import libcore.io.Memory;
import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zznu {
    static final long zza = ((long) zzz(byte[].class));
    static final boolean zzb;
    private static final Unsafe zzc;
    private static final Class zzd = Memory.class;
    private static final boolean zze;
    private static final zznt zzf;
    private static final boolean zzg;
    private static final boolean zzh;

    static {
        boolean z;
        boolean z2;
        zznt zznt;
        Class<Object[]> cls = Object[].class;
        Class<double[]> cls2 = double[].class;
        Class<float[]> cls3 = float[].class;
        Class<long[]> cls4 = long[].class;
        Class<int[]> cls5 = int[].class;
        Class<boolean[]> cls6 = boolean[].class;
        Class<Object> cls7 = Object.class;
        Unsafe zzg2 = zzg();
        zzc = zzg2;
        int i = zzjm.zza;
        Class cls8 = Long.TYPE;
        boolean zzv = zzv(cls8);
        zze = zzv;
        boolean zzv2 = zzv(Integer.TYPE);
        zznt zznt2 = null;
        if (zzg2 != null) {
            if (zzv) {
                zznt2 = new zzns(zzg2);
            } else if (zzv2) {
                zznt2 = new zznr(zzg2);
            }
        }
        zzf = zznt2;
        boolean z3 = true;
        if (zznt2 == null) {
            z = false;
        } else {
            try {
                Class<?> cls9 = zznt2.zza.getClass();
                cls9.getMethod("objectFieldOffset", new Class[]{Field.class});
                cls9.getMethod("getLong", new Class[]{cls7, cls8});
                z = zzB() != null;
            } catch (Throwable th) {
                Logger.getLogger(zznu.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
                z = false;
            }
        }
        zzg = z;
        zznt zznt3 = zzf;
        if (zznt3 == null) {
            z2 = false;
        } else {
            try {
                Class<?> cls10 = zznt3.zza.getClass();
                cls10.getMethod("objectFieldOffset", new Class[]{Field.class});
                cls10.getMethod("arrayBaseOffset", new Class[]{Class.class});
                cls10.getMethod("arrayIndexScale", new Class[]{Class.class});
                Class cls11 = Long.TYPE;
                cls10.getMethod("getInt", new Class[]{cls7, cls11});
                cls10.getMethod("putInt", new Class[]{cls7, cls11, Integer.TYPE});
                cls10.getMethod("getLong", new Class[]{cls7, cls11});
                cls10.getMethod("putLong", new Class[]{cls7, cls11, cls11});
                cls10.getMethod("getObject", new Class[]{cls7, cls11});
                cls10.getMethod("putObject", new Class[]{cls7, cls11, cls7});
                z2 = true;
            } catch (Throwable th2) {
                Logger.getLogger(zznu.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th2.toString()));
                z2 = false;
            }
        }
        zzh = z2;
        zzz(cls6);
        zzA(cls6);
        zzz(cls5);
        zzA(cls5);
        zzz(cls4);
        zzA(cls4);
        zzz(cls3);
        zzA(cls3);
        zzz(cls2);
        zzA(cls2);
        zzz(cls);
        zzA(cls);
        Field zzB = zzB();
        if (!(zzB == null || (zznt = zzf) == null)) {
            zznt.zza.objectFieldOffset(zzB);
        }
        if (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN) {
            z3 = false;
        }
        zzb = z3;
    }

    private zznu() {
    }

    private static int zzA(Class cls) {
        if (zzh) {
            return zzf.zza.arrayIndexScale(cls);
        }
        return -1;
    }

    private static Field zzB() {
        int i = zzjm.zza;
        Field zzC = zzC(Buffer.class, "effectiveDirectAddress");
        if (zzC != null) {
            return zzC;
        }
        Field zzC2 = zzC(Buffer.class, PlaceTypes.ADDRESS);
        if (zzC2 == null || zzC2.getType() != Long.TYPE) {
            return null;
        }
        return zzC2;
    }

    private static Field zzC(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable th) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static void zzD(Object obj, long j, byte b) {
        zznt zznt = zzf;
        long j2 = -4 & j;
        int i = zznt.zza.getInt(obj, j2);
        int i2 = ((~((int) j)) & 3) << 3;
        zznt.zza.putInt(obj, j2, ((255 & b) << i2) | (i & (~(255 << i2))));
    }

    /* access modifiers changed from: private */
    public static void zzE(Object obj, long j, byte b) {
        zznt zznt = zzf;
        long j2 = -4 & j;
        int i = (((int) j) & 3) << 3;
        zznt.zza.putInt(obj, j2, ((255 & b) << i) | (zznt.zza.getInt(obj, j2) & (~(255 << i))));
    }

    static double zza(Object obj, long j) {
        return zzf.zza(obj, j);
    }

    static float zzb(Object obj, long j) {
        return zzf.zzb(obj, j);
    }

    static int zzc(Object obj, long j) {
        return zzf.zza.getInt(obj, j);
    }

    static long zzd(Object obj, long j) {
        return zzf.zza.getLong(obj, j);
    }

    static Object zze(Class cls) {
        try {
            return zzc.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    static Object zzf(Object obj, long j) {
        return zzf.zza.getObject(obj, j);
    }

    static Unsafe zzg() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zznq());
        } catch (Throwable th) {
            return null;
        }
    }

    static void zzm(Object obj, long j, boolean z) {
        zzf.zzc(obj, j, z);
    }

    static void zzn(byte[] bArr, long j, byte b) {
        zzf.zzd(bArr, zza + j, b);
    }

    static void zzo(Object obj, long j, double d) {
        zzf.zze(obj, j, d);
    }

    static void zzp(Object obj, long j, float f) {
        zzf.zzf(obj, j, f);
    }

    static void zzq(Object obj, long j, int i) {
        zzf.zza.putInt(obj, j, i);
    }

    static void zzr(Object obj, long j, long j2) {
        zzf.zza.putLong(obj, j, j2);
    }

    static void zzs(Object obj, long j, Object obj2) {
        zzf.zza.putObject(obj, j, obj2);
    }

    static /* bridge */ /* synthetic */ boolean zzt(Object obj, long j) {
        return ((byte) ((zzf.zza.getInt(obj, -4 & j) >>> ((int) (((~j) & 3) << 3))) & 255)) != 0;
    }

    static /* bridge */ /* synthetic */ boolean zzu(Object obj, long j) {
        return ((byte) ((zzf.zza.getInt(obj, -4 & j) >>> ((int) ((j & 3) << 3))) & 255)) != 0;
    }

    static boolean zzv(Class cls) {
        Class<byte[]> cls2 = byte[].class;
        int i = zzjm.zza;
        try {
            Class cls3 = zzd;
            Class cls4 = Boolean.TYPE;
            cls3.getMethod("peekLong", new Class[]{cls, cls4});
            cls3.getMethod("pokeLong", new Class[]{cls, Long.TYPE, cls4});
            Class cls5 = Integer.TYPE;
            cls3.getMethod("pokeInt", new Class[]{cls, cls5, cls4});
            cls3.getMethod("peekInt", new Class[]{cls, cls4});
            cls3.getMethod("pokeByte", new Class[]{cls, Byte.TYPE});
            cls3.getMethod("peekByte", new Class[]{cls});
            cls3.getMethod("pokeByteArray", new Class[]{cls, cls2, cls5, cls5});
            cls3.getMethod("peekByteArray", new Class[]{cls, cls2, cls5, cls5});
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    static boolean zzw(Object obj, long j) {
        return zzf.zzg(obj, j);
    }

    static boolean zzx() {
        return zzh;
    }

    static boolean zzy() {
        return zzg;
    }

    private static int zzz(Class cls) {
        if (zzh) {
            return zzf.zza.arrayBaseOffset(cls);
        }
        return -1;
    }
}
