package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzjj extends zzjl {
    /* access modifiers changed from: private */
    public static final zzjj zzb = new zzjj();

    private zzjj() {
        super("");
    }

    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((zzjl) obj);
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    public final String toString() {
        return "-∞";
    }

    public final int zza(zzjl zzjl) {
        return zzjl == this ? 0 : -1;
    }

    /* access modifiers changed from: package-private */
    public final void zzc(StringBuilder sb) {
        sb.append("(-∞");
    }

    /* access modifiers changed from: package-private */
    public final void zzd(StringBuilder sb) {
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public final boolean zze(Comparable comparable) {
        return true;
    }
}
