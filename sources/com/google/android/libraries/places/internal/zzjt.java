package com.google.android.libraries.places.internal;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public abstract class zzjt implements Map, Serializable {
    @CheckForNull
    private transient zzju zza;
    @CheckForNull
    private transient zzju zzb;
    @CheckForNull
    private transient zzjn zzc;

    zzjt() {
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public final boolean containsKey(@CheckForNull Object obj) {
        return get(obj) != null;
    }

    public final boolean containsValue(@CheckForNull Object obj) {
        return values().contains(obj);
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        return entrySet().equals(((Map) obj).entrySet());
    }

    @CheckForNull
    public abstract Object get(@CheckForNull Object obj);

    @CheckForNull
    public final Object getOrDefault(@CheckForNull Object obj, @CheckForNull Object obj2) {
        Object obj3 = get(obj);
        return obj3 != null ? obj3 : obj2;
    }

    public final int hashCode() {
        return zzkk.zza(entrySet());
    }

    public final boolean isEmpty() {
        return size() == 0;
    }

    public final /* bridge */ /* synthetic */ Set keySet() {
        zzju zzju = this.zzb;
        if (zzju != null) {
            return zzju;
        }
        zzju zzd = zzd();
        this.zzb = zzd;
        return zzd;
    }

    @CheckForNull
    @Deprecated
    public final Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @CheckForNull
    @Deprecated
    public final Object remove(@CheckForNull Object obj) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        int size = size();
        if (size >= 0) {
            StringBuilder sb = new StringBuilder((int) Math.min(((long) size) * 8, IjkMediaMeta.AV_CH_STEREO_RIGHT));
            sb.append('{');
            boolean z = true;
            for (Map.Entry entry : entrySet()) {
                if (!z) {
                    sb.append(", ");
                }
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue());
                z = false;
            }
            sb.append('}');
            return sb.toString();
        }
        throw new IllegalArgumentException("size cannot be negative but was: " + size);
    }

    /* access modifiers changed from: package-private */
    public abstract zzjn zza();

    /* renamed from: zzb */
    public final zzjn values() {
        zzjn zzjn = this.zzc;
        if (zzjn != null) {
            return zzjn;
        }
        zzjn zza2 = zza();
        this.zzc = zza2;
        return zza2;
    }

    /* access modifiers changed from: package-private */
    public abstract zzju zzc();

    /* access modifiers changed from: package-private */
    public abstract zzju zzd();

    /* renamed from: zze */
    public final zzju entrySet() {
        zzju zzju = this.zza;
        if (zzju != null) {
            return zzju;
        }
        zzju zzc2 = zzc();
        this.zza = zzc2;
        return zzc2;
    }
}
