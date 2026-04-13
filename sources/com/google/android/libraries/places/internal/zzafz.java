package com.google.android.libraries.places.internal;

import com.google.android.libraries.places.internal.zzafw;
import com.google.android.libraries.places.internal.zzafz;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzafz<MessageType extends zzafz<MessageType, BuilderType>, BuilderType extends zzafw<MessageType, BuilderType>> extends zzaer<MessageType, BuilderType> {
    private static final Map zzb = new ConcurrentHashMap();
    protected zzaik zzc = zzaik.zzc();
    private int zzd = -1;

    protected static zzagg zzA() {
        return zzagw.zze();
    }

    protected static zzagh zzB() {
        return zzahq.zzd();
    }

    protected static zzagh zzC(zzagh zzagh) {
        int i;
        int size = zzagh.size();
        if (size == 0) {
            i = 10;
        } else {
            i = size + size;
        }
        return zzagh.zzf(i);
    }

    static Object zzE(Method method, Object obj, Object... objArr) {
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

    protected static Object zzF(zzahh zzahh, String str, Object[] objArr) {
        return new zzahr(zzahh, str, objArr);
    }

    protected static void zzI(Class cls, zzafz zzafz) {
        zzafz.zzH();
        zzb.put(cls, zzafz);
    }

    private final int zza(zzahs zzahs) {
        if (zzahs != null) {
            return zzahs.zza(this);
        }
        return zzahp.zza().zzb(getClass()).zza(this);
    }

    static zzafz zzx(Class cls) {
        Map map = zzb;
        zzafz zzafz = (zzafz) map.get(cls);
        if (zzafz == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzafz = (zzafz) map.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzafz == null) {
            zzafz = (zzafz) ((zzafz) zzait.zze(cls)).zzb(6, (Object) null, (Object) null);
            if (zzafz != null) {
                map.put(cls, zzafz);
            } else {
                throw new IllegalStateException();
            }
        }
        return zzafz;
    }

    protected static zzage zzz() {
        return zzaga.zze();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return zzahp.zza().zzb(getClass()).zzg(this, (zzafz) obj);
    }

    public final int hashCode() {
        if (zzL()) {
            return zzu();
        }
        int i = this.zza;
        if (i != 0) {
            return i;
        }
        int zzu = zzu();
        this.zza = zzu;
        return zzu;
    }

    public final String toString() {
        return zzahj.zza(this, super.toString());
    }

    public final /* synthetic */ zzahg zzD() {
        return (zzafw) zzb(5, (Object) null, (Object) null);
    }

    /* access modifiers changed from: protected */
    public final void zzG() {
        zzahp.zza().zzb(getClass()).zzd(this);
        zzH();
    }

    /* access modifiers changed from: package-private */
    public final void zzH() {
        this.zzd &= Integer.MAX_VALUE;
    }

    /* access modifiers changed from: package-private */
    public final void zzJ(int i) {
        this.zzd = (this.zzd & Integer.MIN_VALUE) | Integer.MAX_VALUE;
    }

    public final void zzK(zzafm zzafm) {
        zzahp.zza().zzb(getClass()).zzf(this, zzafn.zza(zzafm));
    }

    /* access modifiers changed from: package-private */
    public final boolean zzL() {
        return (this.zzd & Integer.MIN_VALUE) != 0;
    }

    /* access modifiers changed from: protected */
    public abstract Object zzb(int i, Object obj, Object obj2);

    public final /* synthetic */ zzahh zzt() {
        return (zzafz) zzb(6, (Object) null, (Object) null);
    }

    /* access modifiers changed from: package-private */
    public final int zzu() {
        return zzahp.zza().zzb(getClass()).zzb(this);
    }

    /* access modifiers changed from: protected */
    public final zzafw zzw() {
        return (zzafw) zzb(5, (Object) null, (Object) null);
    }

    /* access modifiers changed from: package-private */
    public final zzafz zzy() {
        return (zzafz) zzb(4, (Object) null, (Object) null);
    }

    /* access modifiers changed from: package-private */
    public final int zzr(zzahs zzahs) {
        if (zzL()) {
            int zza = zza(zzahs);
            if (zza >= 0) {
                return zza;
            }
            throw new IllegalStateException("serialized size must be non-negative, was " + zza);
        }
        int i = this.zzd & Integer.MAX_VALUE;
        if (i != Integer.MAX_VALUE) {
            return i;
        }
        int zza2 = zza(zzahs);
        if (zza2 >= 0) {
            this.zzd = (this.zzd & Integer.MIN_VALUE) | zza2;
            return zza2;
        }
        throw new IllegalStateException("serialized size must be non-negative, was " + zza2);
    }

    public final int zzv() {
        int i;
        if (zzL()) {
            i = zza((zzahs) null);
            if (i < 0) {
                throw new IllegalStateException("serialized size must be non-negative, was " + i);
            }
        } else {
            i = this.zzd & Integer.MAX_VALUE;
            if (i == Integer.MAX_VALUE) {
                i = zza((zzahs) null);
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
