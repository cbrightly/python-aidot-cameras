package com.google.android.gms.internal.measurement;

import com.meituan.robust.Constants;
import java.util.Iterator;
import javax.annotation.CheckForNull;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public final class zzjg extends zzjb {
    final transient Object zza;

    zzjg(Object obj) {
        if (obj != null) {
            this.zza = obj;
            return;
        }
        throw null;
    }

    public final boolean contains(@CheckForNull Object obj) {
        return this.zza.equals(obj);
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final /* synthetic */ Iterator iterator() {
        return new zzjc(this.zza);
    }

    public final int size() {
        return 1;
    }

    public final String toString() {
        String obj = this.zza.toString();
        return Constants.ARRAY_TYPE + obj + "]";
    }

    /* access modifiers changed from: package-private */
    public final int zza(Object[] objArr, int i) {
        objArr[0] = this.zza;
        return 1;
    }

    public final zzjh zzd() {
        return new zzjc(this.zza);
    }
}
