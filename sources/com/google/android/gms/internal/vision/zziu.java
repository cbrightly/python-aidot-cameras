package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zziw;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zziu<T extends zziw<T>> {
    private static final zziu zzd = new zziu(true);
    final zzlh<T, Object> zza;
    private boolean zzb;
    private boolean zzc;

    private zziu() {
        this.zza = zzlh.zza(16);
    }

    private zziu(boolean z) {
        this(zzlh.zza(0));
        zzb();
    }

    private zziu(zzlh<T, Object> zzlh) {
        this.zza = zzlh;
        zzb();
    }

    public static <T extends zziw<T>> zziu<T> zza() {
        return zzd;
    }

    public final void zzb() {
        if (!this.zzb) {
            this.zza.zza();
            this.zzb = true;
        }
    }

    public final boolean zzc() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zziu)) {
            return false;
        }
        return this.zza.equals(((zziu) obj).zza);
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final Iterator<Map.Entry<T, Object>> zzd() {
        if (this.zzc) {
            return new zzjq(this.zza.entrySet().iterator());
        }
        return this.zza.entrySet().iterator();
    }

    /* access modifiers changed from: package-private */
    public final Iterator<Map.Entry<T, Object>> zze() {
        if (this.zzc) {
            return new zzjq(this.zza.zze().iterator());
        }
        return this.zza.zze().iterator();
    }

    public final Object zza(T t) {
        Object obj = this.zza.get(t);
        if (!(obj instanceof zzjp)) {
            return obj;
        }
        zzjp zzjp = (zzjp) obj;
        return zzjp.zza();
    }

    public final void zza(T t, Object obj) {
        if (!t.zzd()) {
            zzd(t, obj);
        } else if (obj instanceof List) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                zzd(t, obj2);
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof zzjp) {
            this.zzc = true;
        }
        this.zza.put(t, obj);
    }

    public final void zzb(T t, Object obj) {
        List list;
        if (t.zzd()) {
            zzd(t, obj);
            Object zza2 = zza(t);
            if (zza2 == null) {
                list = new ArrayList();
                this.zza.put(t, list);
            } else {
                list = (List) zza2;
            }
            list.add(obj);
            return;
        }
        throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
    }

    private static void zzd(T t, Object obj) {
        boolean z;
        zzml zzb2 = t.zzb();
        zzjf.zza(obj);
        switch (zzit.zza[zzb2.zza().ordinal()]) {
            case 1:
                z = obj instanceof Integer;
                break;
            case 2:
                z = obj instanceof Long;
                break;
            case 3:
                z = obj instanceof Float;
                break;
            case 4:
                z = obj instanceof Double;
                break;
            case 5:
                z = obj instanceof Boolean;
                break;
            case 6:
                z = obj instanceof String;
                break;
            case 7:
                if (!(obj instanceof zzht) && !(obj instanceof byte[])) {
                    z = false;
                    break;
                } else {
                    z = true;
                    break;
                }
                break;
            case 8:
                if (!(obj instanceof Integer) && !(obj instanceof zzje)) {
                    z = false;
                    break;
                } else {
                    z = true;
                    break;
                }
            case 9:
                if (!(obj instanceof zzkk) && !(obj instanceof zzjp)) {
                    z = false;
                    break;
                } else {
                    z = true;
                    break;
                }
            default:
                z = false;
                break;
        }
        if (!z) {
            throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", new Object[]{Integer.valueOf(t.zza()), t.zzb().zza(), obj.getClass().getName()}));
        }
    }

    public final boolean zzf() {
        for (int i = 0; i < this.zza.zzc(); i++) {
            if (!zza(this.zza.zzb(i))) {
                return false;
            }
        }
        for (Map.Entry<T, Object> zza2 : this.zza.zzd()) {
            if (!zza(zza2)) {
                return false;
            }
        }
        return true;
    }

    private static <T extends zziw<T>> boolean zza(Map.Entry<T, Object> entry) {
        zziw zziw = (zziw) entry.getKey();
        if (zziw.zzc() == zzmo.MESSAGE) {
            if (zziw.zzd()) {
                for (zzkk zzk : (List) entry.getValue()) {
                    if (!zzk.zzk()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzkk) {
                    if (!((zzkk) value).zzk()) {
                        return false;
                    }
                } else if (value instanceof zzjp) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zziu<T> zziu) {
        for (int i = 0; i < zziu.zza.zzc(); i++) {
            zzb(zziu.zza.zzb(i));
        }
        for (Map.Entry<T, Object> zzb2 : zziu.zza.zzd()) {
            zzb(zzb2);
        }
    }

    private static Object zza(Object obj) {
        if (obj instanceof zzkt) {
            return ((zzkt) obj).zza();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private final void zzb(Map.Entry<T, Object> entry) {
        Object obj;
        zziw zziw = (zziw) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzjp) {
            zzjp zzjp = (zzjp) value;
            value = zzjp.zza();
        }
        if (zziw.zzd()) {
            Object zza2 = zza(zziw);
            if (zza2 == null) {
                zza2 = new ArrayList();
            }
            for (Object zza3 : (List) value) {
                ((List) zza2).add(zza(zza3));
            }
            this.zza.put(zziw, zza2);
        } else if (zziw.zzc() == zzmo.MESSAGE) {
            Object zza4 = zza(zziw);
            if (zza4 == null) {
                this.zza.put(zziw, zza(value));
                return;
            }
            if (zza4 instanceof zzkt) {
                obj = zziw.zza((zzkt) zza4, (zzkt) value);
            } else {
                obj = zziw.zza(((zzkk) zza4).zzp(), (zzkk) value).zzf();
            }
            this.zza.put(zziw, obj);
        } else {
            this.zza.put(zziw, zza(value));
        }
    }

    static void zza(zzii zzii, zzml zzml, int i, Object obj) {
        if (zzml == zzml.GROUP) {
            zzkk zzkk = (zzkk) obj;
            zzjf.zza(zzkk);
            zzii.zza(i, 3);
            zzkk.zza(zzii);
            zzii.zza(i, 4);
            return;
        }
        zzii.zza(i, zzml.zzb());
        switch (zzit.zzb[zzml.ordinal()]) {
            case 1:
                zzii.zza(((Double) obj).doubleValue());
                return;
            case 2:
                zzii.zza(((Float) obj).floatValue());
                return;
            case 3:
                zzii.zza(((Long) obj).longValue());
                return;
            case 4:
                zzii.zza(((Long) obj).longValue());
                return;
            case 5:
                zzii.zza(((Integer) obj).intValue());
                return;
            case 6:
                zzii.zzc(((Long) obj).longValue());
                return;
            case 7:
                zzii.zzd(((Integer) obj).intValue());
                return;
            case 8:
                zzii.zza(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzkk) obj).zza(zzii);
                return;
            case 10:
                zzii.zza((zzkk) obj);
                return;
            case 11:
                if (obj instanceof zzht) {
                    zzii.zza((zzht) obj);
                    return;
                } else {
                    zzii.zza((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzht) {
                    zzii.zza((zzht) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzii.zzb(bArr, 0, bArr.length);
                return;
            case 13:
                zzii.zzb(((Integer) obj).intValue());
                return;
            case 14:
                zzii.zzd(((Integer) obj).intValue());
                return;
            case 15:
                zzii.zzc(((Long) obj).longValue());
                return;
            case 16:
                zzii.zzc(((Integer) obj).intValue());
                return;
            case 17:
                zzii.zzb(((Long) obj).longValue());
                return;
            case 18:
                if (obj instanceof zzje) {
                    zzii.zza(((zzje) obj).zza());
                    return;
                } else {
                    zzii.zza(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    public final int zzg() {
        int i = 0;
        for (int i2 = 0; i2 < this.zza.zzc(); i2++) {
            i += zzc(this.zza.zzb(i2));
        }
        for (Map.Entry<T, Object> zzc2 : this.zza.zzd()) {
            i += zzc(zzc2);
        }
        return i;
    }

    private static int zzc(Map.Entry<T, Object> entry) {
        zziw zziw = (zziw) entry.getKey();
        Object value = entry.getValue();
        if (zziw.zzc() != zzmo.MESSAGE || zziw.zzd() || zziw.zze()) {
            return zzc(zziw, value);
        }
        if (value instanceof zzjp) {
            return zzii.zzb(((zziw) entry.getKey()).zza(), (zzjt) (zzjp) value);
        }
        return zzii.zzb(((zziw) entry.getKey()).zza(), (zzkk) value);
    }

    static int zza(zzml zzml, int i, Object obj) {
        int zze = zzii.zze(i);
        if (zzml == zzml.GROUP) {
            zzjf.zza((zzkk) obj);
            zze <<= 1;
        }
        return zze + zza(zzml, obj);
    }

    private static int zza(zzml zzml, Object obj) {
        switch (zzit.zzb[zzml.ordinal()]) {
            case 1:
                return zzii.zzb(((Double) obj).doubleValue());
            case 2:
                return zzii.zzb(((Float) obj).floatValue());
            case 3:
                return zzii.zzd(((Long) obj).longValue());
            case 4:
                return zzii.zze(((Long) obj).longValue());
            case 5:
                return zzii.zzf(((Integer) obj).intValue());
            case 6:
                return zzii.zzg(((Long) obj).longValue());
            case 7:
                return zzii.zzi(((Integer) obj).intValue());
            case 8:
                return zzii.zzb(((Boolean) obj).booleanValue());
            case 9:
                return zzii.zzc((zzkk) obj);
            case 10:
                if (obj instanceof zzjp) {
                    return zzii.zza((zzjt) (zzjp) obj);
                }
                return zzii.zzb((zzkk) obj);
            case 11:
                if (obj instanceof zzht) {
                    return zzii.zzb((zzht) obj);
                }
                return zzii.zzb((String) obj);
            case 12:
                if (obj instanceof zzht) {
                    return zzii.zzb((zzht) obj);
                }
                return zzii.zzb((byte[]) obj);
            case 13:
                return zzii.zzg(((Integer) obj).intValue());
            case 14:
                return zzii.zzj(((Integer) obj).intValue());
            case 15:
                return zzii.zzh(((Long) obj).longValue());
            case 16:
                return zzii.zzh(((Integer) obj).intValue());
            case 17:
                return zzii.zzf(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzje) {
                    return zzii.zzk(((zzje) obj).zza());
                }
                return zzii.zzk(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zzc(zziw<?> zziw, Object obj) {
        zzml zzb2 = zziw.zzb();
        int zza2 = zziw.zza();
        if (!zziw.zzd()) {
            return zza(zzb2, zza2, obj);
        }
        int i = 0;
        if (zziw.zze()) {
            for (Object zza3 : (List) obj) {
                i += zza(zzb2, zza3);
            }
            return zzii.zze(zza2) + i + zzii.zzl(i);
        }
        for (Object zza4 : (List) obj) {
            i += zza(zzb2, zza2, zza4);
        }
        return i;
    }

    public final /* synthetic */ Object clone() {
        zziu zziu = new zziu();
        for (int i = 0; i < this.zza.zzc(); i++) {
            Map.Entry<T, Object> zzb2 = this.zza.zzb(i);
            zziu.zza((zziw) zzb2.getKey(), zzb2.getValue());
        }
        for (Map.Entry next : this.zza.zzd()) {
            zziu.zza((zziw) next.getKey(), next.getValue());
        }
        zziu.zzc = this.zzc;
        return zziu;
    }
}
