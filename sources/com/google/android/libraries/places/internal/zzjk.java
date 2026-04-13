package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzjk extends zzjl {
    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final String toString() {
        String obj = this.zza.toString();
        return "\\" + obj + "/";
    }

    /* access modifiers changed from: package-private */
    public final void zzc(StringBuilder sb) {
        sb.append('[');
        sb.append(this.zza);
    }

    /* access modifiers changed from: package-private */
    public final void zzd(StringBuilder sb) {
        sb.append(this.zza);
        sb.append(')');
    }

    /* access modifiers changed from: package-private */
    public final boolean zze(Comparable comparable) {
        Comparable comparable2 = this.zza;
        int i = zzkc.zzc;
        return comparable2.compareTo(comparable) <= 0;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzjk(Comparable comparable) {
        super(comparable);
        if (comparable != null) {
            return;
        }
        throw null;
    }
}
