package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzkx;
import com.google.android.gms.internal.measurement.zzlb;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public abstract class zzlb<MessageType extends zzlb<MessageType, BuilderType>, BuilderType extends zzkx<MessageType, BuilderType>> extends zzjk<MessageType, BuilderType> {
    private static final Map zza = new ConcurrentHashMap();
    protected zznl zzc = zznl.zzc();
    private int zzd = -1;

    private final int zza(zzmt zzmt) {
        if (zzmt != null) {
            return zzmt.zza(this);
        }
        return zzmq.zza().zzb(getClass()).zza(this);
    }

    static zzlb zzbC(Class cls) {
        Map map = zza;
        zzlb zzlb = (zzlb) map.get(cls);
        if (zzlb == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzlb = (zzlb) map.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzlb == null) {
            zzlb = (zzlb) ((zzlb) zznu.zze(cls)).zzl(6, (Object) null, (Object) null);
            if (zzlb != null) {
                map.put(cls, zzlb);
            } else {
                throw new IllegalStateException();
            }
        }
        return zzlb;
    }

    protected static zzlg zzbE() {
        return zzlc.zzf();
    }

    protected static zzlh zzbF() {
        return zzlx.zzf();
    }

    protected static zzlh zzbG(zzlh zzlh) {
        int i;
        int size = zzlh.size();
        if (size == 0) {
            i = 10;
        } else {
            i = size + size;
        }
        return zzlh.zze(i);
    }

    protected static zzli zzbH() {
        return zzmr.zze();
    }

    protected static zzli zzbI(zzli zzli) {
        int i;
        int size = zzli.size();
        if (size == 0) {
            i = 10;
        } else {
            i = size + size;
        }
        return zzli.zzd(i);
    }

    static Object zzbK(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    protected static Object zzbL(zzmi zzmi, String str, Object[] objArr) {
        return new zzms(zzmi, str, objArr);
    }

    protected static void zzbO(Class cls, zzlb zzlb) {
        zzlb.zzbN();
        zza.put(cls, zzlb);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return zzmq.zza().zzb(getClass()).zzj(this, (zzlb) obj);
    }

    public final int hashCode() {
        if (zzbR()) {
            return zzby();
        }
        int i = this.zzb;
        if (i != 0) {
            return i;
        }
        int zzby = zzby();
        this.zzb = zzby;
        return zzby;
    }

    public final String toString() {
        return zzmk.zza(this, super.toString());
    }

    /* access modifiers changed from: protected */
    public final zzkx zzbA() {
        return (zzkx) zzl(5, (Object) null, (Object) null);
    }

    public final zzkx zzbB() {
        zzkx zzkx = (zzkx) zzl(5, (Object) null, (Object) null);
        zzkx.zzaB(this);
        return zzkx;
    }

    /* access modifiers changed from: package-private */
    public final zzlb zzbD() {
        return (zzlb) zzl(4, (Object) null, (Object) null);
    }

    public final /* synthetic */ zzmh zzbJ() {
        return (zzkx) zzl(5, (Object) null, (Object) null);
    }

    /* access modifiers changed from: protected */
    public final void zzbM() {
        zzmq.zza().zzb(getClass()).zzf(this);
        zzbN();
    }

    /* access modifiers changed from: package-private */
    public final void zzbN() {
        this.zzd &= Integer.MAX_VALUE;
    }

    /* access modifiers changed from: package-private */
    public final void zzbP(int i) {
        this.zzd = (this.zzd & Integer.MIN_VALUE) | Integer.MAX_VALUE;
    }

    public final void zzbQ(zzki zzki) {
        zzmq.zza().zzb(getClass()).zzi(this, zzkj.zza(zzki));
    }

    /* access modifiers changed from: package-private */
    public final boolean zzbR() {
        return (this.zzd & Integer.MIN_VALUE) != 0;
    }

    public final /* synthetic */ zzmi zzbV() {
        return (zzlb) zzl(6, (Object) null, (Object) null);
    }

    /* access modifiers changed from: package-private */
    public final int zzby() {
        return zzmq.zza().zzb(getClass()).zzb(this);
    }

    /* access modifiers changed from: protected */
    public abstract Object zzl(int i, Object obj, Object obj2);

    /* access modifiers changed from: package-private */
    public final int zzbu(zzmt zzmt) {
        if (zzbR()) {
            int zza2 = zza(zzmt);
            if (zza2 >= 0) {
                return zza2;
            }
            throw new IllegalStateException("serialized size must be non-negative, was " + zza2);
        }
        int i = this.zzd & Integer.MAX_VALUE;
        if (i != Integer.MAX_VALUE) {
            return i;
        }
        int zza3 = zza(zzmt);
        if (zza3 >= 0) {
            this.zzd = (this.zzd & Integer.MIN_VALUE) | zza3;
            return zza3;
        }
        throw new IllegalStateException("serialized size must be non-negative, was " + zza3);
    }

    public final int zzbz() {
        int i;
        if (zzbR()) {
            i = zza((zzmt) null);
            if (i < 0) {
                throw new IllegalStateException("serialized size must be non-negative, was " + i);
            }
        } else {
            i = this.zzd & Integer.MAX_VALUE;
            if (i == Integer.MAX_VALUE) {
                i = zza((zzmt) null);
                if (i >= 0) {
                    this.zzd = (this.zzd & Integer.MIN_VALUE) | i;
                } else {
                    throw new IllegalStateException("serialized size must be non-negative, was " + i);
                }
            }
        }
        return i;
    }
}
