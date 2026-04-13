package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzjb;
import com.google.android.gms.internal.vision.zzjb.zzb;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public abstract class zzjb<MessageType extends zzjb<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzhf<MessageType, BuilderType> {
    private static Map<Object, zzjb<?, ?>> zzd = new ConcurrentHashMap();
    protected zzlx zzb = zzlx.zza();
    private int zzc = -1;

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static final class zza<T extends zzjb<T, ?>> extends zzhg<T> {
        private final T zza;

        public zza(T t) {
            this.zza = t;
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static final class zzg {
        public static final int zza = 1;
        public static final int zzb = 2;
        public static final int zzc = 3;
        public static final int zzd = 4;
        public static final int zze = 5;
        public static final int zzf = 6;
        public static final int zzg = 7;
        private static final /* synthetic */ int[] zzh = {1, 2, 3, 4, 5, 6, 7};

        public static int[] zza() {
            return (int[]) zzh.clone();
        }
    }

    /* access modifiers changed from: protected */
    public abstract Object zza(int i, Object obj, Object obj2);

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType extends zzd<MessageType, BuilderType>> extends zzjb<MessageType, BuilderType> implements zzkm {
        protected zziu<zzf> zzc = zziu.zza();

        /* access modifiers changed from: package-private */
        public final zziu<zzf> zza() {
            if (this.zzc.zzc()) {
                this.zzc = (zziu) this.zzc.clone();
            }
            return this.zzc;
        }

        public final <Type> Type zzb(zzim<MessageType, Type> zzim) {
            zze zza = zzjb.zzb(zzim);
            if (zza.zza == ((zzjb) zzr())) {
                Type zza2 = this.zzc.zza(zza.zzd);
                if (zza2 == null) {
                    return zza.zzb;
                }
                zzf zzf = zza.zzd;
                if (!zzf.zzd) {
                    return zza.zza(zza2);
                }
                if (zzf.zzc.zza() != zzmo.ENUM) {
                    return zza2;
                }
                Type arrayList = new ArrayList();
                for (Object zza3 : (List) zza2) {
                    arrayList.add(zza.zza(zza3));
                }
                return arrayList;
            }
            throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static class zzd<MessageType extends zzc<MessageType, BuilderType>, BuilderType extends zzd<MessageType, BuilderType>> extends zzb<MessageType, BuilderType> implements zzkm {
        protected zzd(MessageType messagetype) {
            super(messagetype);
        }

        /* access modifiers changed from: protected */
        public void zzb() {
            super.zzb();
            MessageType messagetype = this.zza;
            ((zzc) messagetype).zzc = (zziu) ((zzc) messagetype).zzc.clone();
        }

        public /* synthetic */ zzjb zzc() {
            return (zzc) zze();
        }

        public /* synthetic */ zzkk zze() {
            if (this.zzb) {
                return (zzc) this.zza;
            }
            ((zzc) this.zza).zzc.zzb();
            return (zzc) super.zze();
        }
    }

    public String toString() {
        return zzkp.zza(this, super.toString());
    }

    public int hashCode() {
        int i = this.zza;
        if (i != 0) {
            return i;
        }
        int zza2 = zzky.zza().zza(this).zza(this);
        this.zza = zza2;
        return zza2;
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static class zzb<MessageType extends zzjb<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzhe<MessageType, BuilderType> {
        protected MessageType zza;
        protected boolean zzb = false;
        private final MessageType zzc;

        protected zzb(MessageType messagetype) {
            this.zzc = messagetype;
            this.zza = (zzjb) messagetype.zza(zzg.zzd, (Object) null, (Object) null);
        }

        /* access modifiers changed from: protected */
        public void zzb() {
            MessageType messagetype = (zzjb) this.zza.zza(zzg.zzd, (Object) null, (Object) null);
            zza(messagetype, this.zza);
            this.zza = messagetype;
        }

        public final boolean zzk() {
            return zzjb.zza(this.zza, false);
        }

        /* renamed from: zzc */
        public MessageType zze() {
            if (this.zzb) {
                return this.zza;
            }
            MessageType messagetype = this.zza;
            zzky.zza().zza(messagetype).zzc(messagetype);
            this.zzb = true;
            return this.zza;
        }

        /* renamed from: zzd */
        public final MessageType zzf() {
            MessageType messagetype = (zzjb) zze();
            if (messagetype.zzk()) {
                return messagetype;
            }
            throw new zzlv(messagetype);
        }

        public final BuilderType zza(MessageType messagetype) {
            if (this.zzb) {
                zzb();
                this.zzb = false;
            }
            zza(this.zza, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzky.zza().zza(messagetype).zzb(messagetype, messagetype2);
        }

        private final BuilderType zzb(byte[] bArr, int i, int i2, zzio zzio) {
            if (this.zzb) {
                zzb();
                this.zzb = false;
            }
            try {
                zzky.zza().zza(this.zza).zza(this.zza, bArr, 0, i2, new zzhn(zzio));
                return this;
            } catch (zzjk e) {
                throw e;
            } catch (IndexOutOfBoundsException e2) {
                throw zzjk.zza();
            } catch (IOException e3) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e3);
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: zzb */
        public final BuilderType zza(zzif zzif, zzio zzio) {
            if (this.zzb) {
                zzb();
                this.zzb = false;
            }
            try {
                zzky.zza().zza(this.zza).zza(this.zza, zzig.zza(zzif), zzio);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }

        public final /* synthetic */ zzhe zza(byte[] bArr, int i, int i2, zzio zzio) {
            return zzb(bArr, 0, i2, zzio);
        }

        public final /* synthetic */ zzhe zza() {
            return (zzb) clone();
        }

        public final /* synthetic */ zzkk zzr() {
            return this.zzc;
        }

        public /* synthetic */ Object clone() {
            zzb zzb2 = (zzb) this.zzc.zza(zzg.zze, (Object) null, (Object) null);
            zzb2.zza((zzjb) zze());
            return zzb2;
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static final class zzf implements zziw<zzf> {
        final zzjh<?> zza = null;
        final int zzb = 202056002;
        final zzml zzc;
        final boolean zzd;
        final boolean zze;

        zzf(zzjh<?> zzjh, int i, zzml zzml, boolean z, boolean z2) {
            this.zzc = zzml;
            this.zzd = true;
            this.zze = false;
        }

        public final int zza() {
            return this.zzb;
        }

        public final zzml zzb() {
            return this.zzc;
        }

        public final zzmo zzc() {
            return this.zzc.zza();
        }

        public final boolean zzd() {
            return this.zzd;
        }

        public final boolean zze() {
            return false;
        }

        public final zzkn zza(zzkn zzkn, zzkk zzkk) {
            return ((zzb) zzkn).zza((zzjb) zzkk);
        }

        public final zzkt zza(zzkt zzkt, zzkt zzkt2) {
            throw new UnsupportedOperationException();
        }

        public final /* synthetic */ int compareTo(Object obj) {
            return this.zzb - ((zzf) obj).zzb;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzky.zza().zza(this).zza(this, (zzjb) obj);
        }
        return false;
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static final class zze<ContainingType extends zzkk, Type> extends zzim<ContainingType, Type> {
        final ContainingType zza;
        final Type zzb;
        final zzkk zzc;
        final zzf zzd;

        zze(ContainingType containingtype, Type type, zzkk zzkk, zzf zzf, Class cls) {
            if (containingtype == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            } else if (zzf.zzc == zzml.MESSAGE && zzkk == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            } else {
                this.zza = containingtype;
                this.zzb = type;
                this.zzc = zzkk;
                this.zzd = zzf;
            }
        }

        /* JADX WARNING: type inference failed for: r0v3, types: [java.lang.Throwable, com.google.android.gms.internal.vision.zzjh] */
        /* access modifiers changed from: package-private */
        public final Object zza(Object obj) {
            if (this.zzd.zzc.zza() != zzmo.ENUM) {
                return obj;
            }
            ? r0 = 0;
            r0.zza(((Integer) obj).intValue());
            throw r0;
        }
    }

    /* access modifiers changed from: protected */
    public final <MessageType extends zzjb<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> BuilderType zzj() {
        return (zzb) zza(zzg.zze, (Object) null, (Object) null);
    }

    public final boolean zzk() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    public final BuilderType zzl() {
        BuilderType buildertype = (zzb) zza(zzg.zze, (Object) null, (Object) null);
        buildertype.zza(this);
        return buildertype;
    }

    /* access modifiers changed from: package-private */
    public final int zzi() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final void zzb(int i) {
        this.zzc = i;
    }

    public final void zza(zzii zzii) {
        zzky.zza().zza(this).zza(this, (zzmr) zzil.zza(zzii));
    }

    public final int zzm() {
        if (this.zzc == -1) {
            this.zzc = zzky.zza().zza(this).zzb(this);
        }
        return this.zzc;
    }

    static <T extends zzjb<?, ?>> T zza(Class<T> cls) {
        T t = (zzjb) zzd.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzjb) zzd.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t == null) {
            t = (zzjb) ((zzjb) zzma.zza(cls)).zza(zzg.zzf, (Object) null, (Object) null);
            if (t != null) {
                zzd.put(cls, t);
            } else {
                throw new IllegalStateException();
            }
        }
        return t;
    }

    protected static <T extends zzjb<?, ?>> void zza(Class<T> cls, T t) {
        zzd.put(cls, t);
    }

    protected static Object zza(zzkk zzkk, String str, Object[] objArr) {
        return new zzla(zzkk, str, objArr);
    }

    public static <ContainingType extends zzkk, Type> zze<ContainingType, Type> zza(ContainingType containingtype, zzkk zzkk, zzjh<?> zzjh, int i, zzml zzml, boolean z, Class cls) {
        return new zze(containingtype, Collections.emptyList(), zzkk, new zzf((zzjh<?>) null, 202056002, zzml, true, false), cls);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
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

    /* access modifiers changed from: private */
    public static <MessageType extends zzc<MessageType, BuilderType>, BuilderType extends zzd<MessageType, BuilderType>, T> zze<MessageType, T> zzb(zzim<MessageType, T> zzim) {
        return (zze) zzim;
    }

    protected static final <T extends zzjb<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zzg.zza, (Object) null, (Object) null)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean zzd2 = zzky.zza().zza(t).zzd(t);
        if (z) {
            t.zza(zzg.zzb, (Object) zzd2 ? t : null, (Object) null);
        }
        return zzd2;
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [com.google.android.gms.internal.vision.zzjj, com.google.android.gms.internal.vision.zzjd] */
    protected static zzjj zzn() {
        return zzjd.zzd();
    }

    protected static <E> zzjl<E> zzo() {
        return zzlb.zzd();
    }

    protected static <E> zzjl<E> zza(zzjl<E> zzjl) {
        int size = zzjl.size();
        return zzjl.zza(size == 0 ? 10 : size << 1);
    }

    private static <T extends zzjb<T, ?>> T zza(T t, byte[] bArr, int i, int i2, zzio zzio) {
        T t2 = (zzjb) t.zza(zzg.zzd, (Object) null, (Object) null);
        try {
            zzlc zza2 = zzky.zza().zza(t2);
            zza2.zza(t2, bArr, 0, i2, new zzhn(zzio));
            zza2.zzc(t2);
            if (t2.zza == 0) {
                return t2;
            }
            throw new RuntimeException();
        } catch (IOException e) {
            if (e.getCause() instanceof zzjk) {
                throw ((zzjk) e.getCause());
            }
            throw new zzjk(e.getMessage()).zza(t2);
        } catch (IndexOutOfBoundsException e2) {
            throw zzjk.zza().zza(t2);
        }
    }

    private static <T extends zzjb<T, ?>> T zza(T t) {
        if (t == null || t.zzk()) {
            return t;
        }
        throw new zzjk(new zzlv(t).getMessage()).zza(t);
    }

    protected static <T extends zzjb<T, ?>> T zza(T t, byte[] bArr) {
        return zza(zza(t, bArr, 0, bArr.length, zzio.zzb()));
    }

    protected static <T extends zzjb<T, ?>> T zza(T t, byte[] bArr, zzio zzio) {
        return zza(zza(t, bArr, 0, bArr.length, zzio));
    }

    public final /* synthetic */ zzkn zzp() {
        zzb zzb2 = (zzb) zza(zzg.zze, (Object) null, (Object) null);
        zzb2.zza(this);
        return zzb2;
    }

    public final /* synthetic */ zzkn zzq() {
        return (zzb) zza(zzg.zze, (Object) null, (Object) null);
    }

    public final /* synthetic */ zzkk zzr() {
        return (zzjb) zza(zzg.zzf, (Object) null, (Object) null);
    }
}
