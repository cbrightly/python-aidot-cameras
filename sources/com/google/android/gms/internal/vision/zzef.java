package com.google.android.gms.internal.vision;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public abstract class zzef<K, V> implements Serializable, Map<K, V> {
    private static final Map.Entry<?, ?>[] zza = new Map.Entry[0];
    private transient zzej<Map.Entry<K, V>> zzb;
    private transient zzej<K> zzc;
    private transient zzeb<V> zzd;

    zzef() {
    }

    public abstract V get(@NullableDecl Object obj);

    /* access modifiers changed from: package-private */
    public abstract zzej<Map.Entry<K, V>> zza();

    /* access modifiers changed from: package-private */
    public abstract zzej<K> zzb();

    /* access modifiers changed from: package-private */
    public abstract zzeb<V> zzc();

    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(@NullableDecl Object obj) {
        return get(obj) != null;
    }

    public boolean containsValue(@NullableDecl Object obj) {
        return ((zzeb) values()).contains(obj);
    }

    public final V getOrDefault(@NullableDecl Object obj, @NullableDecl V v) {
        V v2 = get(obj);
        return v2 != null ? v2 : v;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    public int hashCode() {
        return zzey.zza((zzej) entrySet());
    }

    public String toString() {
        int size = size();
        if (size >= 0) {
            StringBuilder sb = new StringBuilder((int) Math.min(((long) size) << 3, IjkMediaMeta.AV_CH_STEREO_RIGHT));
            sb.append('{');
            boolean z = true;
            for (Map.Entry entry : entrySet()) {
                if (!z) {
                    sb.append(", ");
                }
                z = false;
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue());
            }
            sb.append('}');
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder(String.valueOf("size").length() + 40);
        sb2.append("size");
        sb2.append(" cannot be negative but was: ");
        sb2.append(size);
        throw new IllegalArgumentException(sb2.toString());
    }

    public /* synthetic */ Set entrySet() {
        zzej<Map.Entry<K, V>> zzej = this.zzb;
        if (zzej != null) {
            return zzej;
        }
        zzej<Map.Entry<K, V>> zza2 = zza();
        this.zzb = zza2;
        return zza2;
    }

    public /* synthetic */ Collection values() {
        zzeb<V> zzeb = this.zzd;
        if (zzeb != null) {
            return zzeb;
        }
        zzeb<V> zzc2 = zzc();
        this.zzd = zzc2;
        return zzc2;
    }

    public /* synthetic */ Set keySet() {
        zzej<K> zzej = this.zzc;
        if (zzej != null) {
            return zzej;
        }
        zzej<K> zzb2 = zzb();
        this.zzc = zzb2;
        return zzb2;
    }
}
