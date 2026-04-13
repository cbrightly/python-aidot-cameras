package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzfu;

public class zzfu<M extends zzfu<M>> extends zzfz {
    protected zzfw zzrj;

    public void zza(zzfs zzfs) {
        if (this.zzrj != null) {
            for (int i = 0; i < this.zzrj.size(); i++) {
                this.zzrj.zzaq(i).zza(zzfs);
            }
        }
    }

    /* access modifiers changed from: protected */
    public int zzen() {
        if (this.zzrj != null) {
            for (int i = 0; i < this.zzrj.size(); i++) {
                this.zzrj.zzaq(i).zzen();
            }
        }
        return 0;
    }

    /* renamed from: zzeo */
    public M clone() {
        M m = (zzfu) super.clone();
        zzfy.zza(this, (zzfu) m);
        return m;
    }

    public /* synthetic */ zzfz zzep() {
        return (zzfu) clone();
    }
}
