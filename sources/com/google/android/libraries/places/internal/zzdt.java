package com.google.android.libraries.places.internal;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.android.volley.VolleyError;
import com.android.volley.h;
import com.android.volley.j;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.libraries.places.api.net.PlacesStatusCodes;
import java.util.Map;
import meshsdk.BaseResp;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzdt {
    private final j zza;

    zzdt(j jVar) {
        this.zza = jVar;
    }

    static /* synthetic */ void zza(TaskCompletionSource taskCompletionSource, VolleyError volleyError) {
        ApiException apiException;
        try {
            h hVar = volleyError.networkResponse;
            if (hVar != null) {
                switch (hVar.a) {
                    case BaseResp.ERR_MSG_SEND_FAIL /*400*/:
                        apiException = new ApiException(new Status((int) PlacesStatusCodes.INVALID_REQUEST, "The provided parameters are invalid (did you include a max width or height?)."));
                        break;
                    case 403:
                        apiException = new ApiException(new Status((int) PlacesStatusCodes.REQUEST_DENIED, "The provided API key is invalid."));
                        break;
                }
            }
            apiException = zzdh.zza(volleyError);
            taskCompletionSource.trySetException(apiException);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    static /* synthetic */ void zzc(zzek zzek, TaskCompletionSource taskCompletionSource, Bitmap bitmap) {
        try {
            zzek.zzb(bitmap);
            taskCompletionSource.trySetResult(zzek.zza());
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public final Task zzb(zzdv zzdv, zzek zzek) {
        TaskCompletionSource taskCompletionSource;
        String zzc = zzdv.zzc();
        Map zzd = zzdv.zzd();
        CancellationToken zza2 = zzdv.zza();
        if (zza2 != null) {
            taskCompletionSource = new TaskCompletionSource(zza2);
        } else {
            taskCompletionSource = new TaskCompletionSource();
        }
        zzds zzds = new zzds(this, zzc, new zzdp(zzek, taskCompletionSource), 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new zzdq(taskCompletionSource), zzd);
        if (zza2 != null) {
            zza2.onCanceledRequested(new zzdr(zzds));
        }
        this.zza.a(zzds);
        return taskCompletionSource.getTask();
    }
}
