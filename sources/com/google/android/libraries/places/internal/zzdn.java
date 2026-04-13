package com.google.android.libraries.places.internal;

import com.android.volley.VolleyError;
import com.android.volley.j;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzdn {
    private final j zza;
    private final zzfb zzb;

    zzdn(j jVar, zzfb zzfb) {
        this.zza = jVar;
        this.zzb = zzfb;
    }

    public final Task zza(zzdv zzdv, Class cls) {
        TaskCompletionSource taskCompletionSource;
        String zzc = zzdv.zzc();
        Map zzd = zzdv.zzd();
        CancellationToken zza2 = zzdv.zza();
        if (zza2 != null) {
            taskCompletionSource = new TaskCompletionSource(zza2);
        } else {
            taskCompletionSource = new TaskCompletionSource();
        }
        zzdm zzdm = new zzdm(this, 0, zzc, (JSONObject) null, new zzdj(this, cls, taskCompletionSource), new zzdk(taskCompletionSource), zzd);
        if (zza2 != null) {
            zza2.onCanceledRequested(new zzdl(zzdm));
        }
        this.zza.a(zzdm);
        return taskCompletionSource.getTask();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void zzb(Class cls, TaskCompletionSource taskCompletionSource, JSONObject jSONObject) {
        try {
            taskCompletionSource.trySetResult((zzdw) this.zzb.zza(jSONObject.toString(), cls));
        } catch (zzdx e) {
            try {
                taskCompletionSource.trySetException(new ApiException(new Status(8, e.getMessage())));
            } catch (Error | RuntimeException e2) {
                zzgt.zzb(e2);
                throw e2;
            }
        }
    }

    static /* synthetic */ void zzc(TaskCompletionSource taskCompletionSource, VolleyError volleyError) {
        try {
            taskCompletionSource.trySetException(zzdh.zza(volleyError));
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }
}
