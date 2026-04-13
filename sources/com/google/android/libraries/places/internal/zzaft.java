package com.google.android.libraries.places.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzaft {
    private static final zzaft zzb = new zzaft(true);
    final zzaif zza = new zzahv(16);
    private boolean zzc;
    private boolean zzd;

    private zzaft() {
    }

    public static zzaft zza() {
        throw null;
    }

    private static final void zzd(zzafs zzafs, Object obj) {
        boolean z;
        zzaiy zzb2 = zzafs.zzb();
        byte[] bArr = zzagi.zzd;
        if (obj != null) {
            zzaiy zzaiy = zzaiy.DOUBLE;
            zzaiz zzaiz = zzaiz.INT;
            switch (zzb2.zza().ordinal()) {
                case 0:
                    z = obj instanceof Integer;
                    break;
                case 1:
                    z = obj instanceof Long;
                    break;
                case 2:
                    z = obj instanceof Float;
                    break;
                case 3:
                    z = obj instanceof Double;
                    break;
                case 4:
                    z = obj instanceof Boolean;
                    break;
                case 5:
                    z = obj instanceof String;
                    break;
                case 6:
                    if ((obj instanceof zzafe) || (obj instanceof byte[])) {
                        return;
                    }
                case 7:
                    if ((obj instanceof Integer) || (obj instanceof zzagb)) {
                        return;
                    }
                case 8:
                    if ((obj instanceof zzahh) || (obj instanceof zzagm)) {
                        return;
                    }
            }
            if (z) {
                return;
            }
            throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", new Object[]{Integer.valueOf(zzafs.zza()), zzafs.zzb().zza(), obj.getClass().getName()}));
        }
        throw null;
    }

    public final /* bridge */ /* synthetic */ Object clone() {
        zzaft zzaft = new zzaft();
        for (int i = 0; i < this.zza.zzb(); i++) {
            Map.Entry zzg = this.zza.zzg(i);
            zzaft.zzc((zzafs) zzg.getKey(), zzg.getValue());
        }
        for (Map.Entry entry : this.zza.zzc()) {
            zzaft.zzc((zzafs) entry.getKey(), entry.getValue());
        }
        zzaft.zzd = this.zzd;
        return zzaft;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaft)) {
            return false;
        }
        return this.zza.equals(((zzaft) obj).zza);
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final void zzb() {
        if (!this.zzc) {
            for (int i = 0; i < this.zza.zzb(); i++) {
                Map.Entry zzg = this.zza.zzg(i);
                if (zzg.getValue() instanceof zzafz) {
                    ((zzafz) zzg.getValue()).zzG();
                }
            }
            this.zza.zza();
            this.zzc = true;
        }
    }

    public final void zzc(zzafs zzafs, Object obj) {
        if (!zzafs.zzc()) {
            zzd(zzafs, obj);
        } else if (obj instanceof List) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                zzd(zzafs, arrayList.get(i));
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof zzagm) {
            this.zzd = true;
        }
        this.zza.put(zzafs, obj);
    }

    private zzaft(boolean z) {
        zzb();
        zzb();
    }
}
