package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.IInterface;
import androidx.annotation.Nullable;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public interface zzek extends IInterface {
    @Nullable
    String zzd(zzq zzq);

    @Nullable
    List zze(zzq zzq, boolean z);

    List zzf(@Nullable String str, @Nullable String str2, zzq zzq);

    List zzg(String str, @Nullable String str2, @Nullable String str3);

    List zzh(@Nullable String str, @Nullable String str2, boolean z, zzq zzq);

    List zzi(String str, @Nullable String str2, @Nullable String str3, boolean z);

    void zzj(zzq zzq);

    void zzk(zzaw zzaw, zzq zzq);

    void zzl(zzaw zzaw, String str, @Nullable String str2);

    void zzm(zzq zzq);

    void zzn(zzac zzac, zzq zzq);

    void zzo(zzac zzac);

    void zzp(zzq zzq);

    void zzq(long j, @Nullable String str, @Nullable String str2, String str3);

    void zzr(Bundle bundle, zzq zzq);

    void zzs(zzq zzq);

    void zzt(zzlj zzlj, zzq zzq);

    @Nullable
    byte[] zzu(zzaw zzaw, String str);
}
