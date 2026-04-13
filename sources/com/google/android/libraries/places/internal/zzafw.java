package com.google.android.libraries.places.internal;

import com.google.android.libraries.places.internal.zzafw;
import com.google.android.libraries.places.internal.zzafz;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public class zzafw<MessageType extends zzafz<MessageType, BuilderType>, BuilderType extends zzafw<MessageType, BuilderType>> extends zzaeq<MessageType, BuilderType> {
    protected zzafz zza;
    private final zzafz zzb;

    protected zzafw(MessageType messagetype) {
        this.zzb = messagetype;
        if (!messagetype.zzL()) {
            this.zza = messagetype.zzy();
            return;
        }
        throw new IllegalArgumentException("Default instance must be immutable.");
    }

    /* renamed from: zzp */
    public final zzafw zzo() {
        zzafw zzafw = (zzafw) this.zzb.zzb(5, (Object) null, (Object) null);
        zzafw.zza = zzs();
        return zzafw;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0036, code lost:
        if (r4 != false) goto L_0x0038;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final MessageType zzq() {
        /*
            r6 = this;
            com.google.android.libraries.places.internal.zzafz r0 = r6.zzs()
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            boolean r1 = r1.booleanValue()
            r2 = 1
            r3 = 0
            java.lang.Object r4 = r0.zzb(r2, r3, r3)
            java.lang.Byte r4 = (java.lang.Byte) r4
            byte r4 = r4.byteValue()
            if (r4 != r2) goto L_0x0019
            goto L_0x0038
        L_0x0019:
            if (r4 == 0) goto L_0x0039
            com.google.android.libraries.places.internal.zzahp r4 = com.google.android.libraries.places.internal.zzahp.zza()
            java.lang.Class r5 = r0.getClass()
            com.google.android.libraries.places.internal.zzahs r4 = r4.zzb(r5)
            boolean r4 = r4.zzh(r0)
            if (r1 == 0) goto L_0x0036
            if (r2 == r4) goto L_0x0031
            r1 = r3
            goto L_0x0032
        L_0x0031:
            r1 = r0
        L_0x0032:
            r2 = 2
            r0.zzb(r2, r1, r3)
        L_0x0036:
            if (r4 == 0) goto L_0x0039
        L_0x0038:
            return r0
        L_0x0039:
            com.google.android.libraries.places.internal.zzaii r1 = new com.google.android.libraries.places.internal.zzaii
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.internal.zzafw.zzq():com.google.android.libraries.places.internal.zzafz");
    }

    /* renamed from: zzr */
    public MessageType zzs() {
        if (!this.zza.zzL()) {
            return this.zza;
        }
        this.zza.zzG();
        return this.zza;
    }

    public final /* bridge */ /* synthetic */ zzahh zzt() {
        throw null;
    }

    /* access modifiers changed from: protected */
    public final void zzu() {
        if (!this.zza.zzL()) {
            zzv();
        }
    }

    /* access modifiers changed from: protected */
    public void zzv() {
        zzafz zzy = this.zzb.zzy();
        zzahp.zza().zzb(zzy.getClass()).zze(zzy, this.zza);
        this.zza = zzy;
    }
}
