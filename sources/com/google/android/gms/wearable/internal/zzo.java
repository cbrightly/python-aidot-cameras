package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.CapabilityApi;

public final class zzo implements CapabilityApi {
    public final PendingResult<CapabilityApi.GetCapabilityResult> getCapability(GoogleApiClient googleApiClient, String str, int i) {
        boolean z = true;
        if (!(i == 0 || i == 1)) {
            z = false;
        }
        Preconditions.checkArgument(z);
        return googleApiClient.enqueue(new zzp(this, googleApiClient, str, i));
    }

    public final PendingResult<CapabilityApi.GetAllCapabilitiesResult> getAllCapabilities(GoogleApiClient googleApiClient, int i) {
        boolean z = true;
        if (!(i == 0 || i == 1)) {
            z = false;
        }
        Preconditions.checkArgument(z);
        return googleApiClient.enqueue(new zzq(this, googleApiClient, i));
    }

    public final PendingResult<CapabilityApi.AddLocalCapabilityResult> addLocalCapability(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.enqueue(new zzr(this, googleApiClient, str));
    }

    public final PendingResult<CapabilityApi.RemoveLocalCapabilityResult> removeLocalCapability(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.enqueue(new zzs(this, googleApiClient, str));
    }

    public final PendingResult<Status> addCapabilityListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, String str) {
        Asserts.checkNotNull(str, "capability must not be null");
        zzv zzv = new zzv(capabilityListener, str);
        IntentFilter zzc = zzgj.zzc("com.google.android.gms.wearable.CAPABILITY_CHANGED");
        if (!str.startsWith("/")) {
            String valueOf = String.valueOf(str);
            str = valueOf.length() != 0 ? "/".concat(valueOf) : new String("/");
        }
        zzc.addDataPath(str, 0);
        return zza(googleApiClient, zzv, new IntentFilter[]{zzc});
    }

    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, Uri uri, int i) {
        Asserts.checkNotNull(uri, "uri must not be null");
        Preconditions.checkArgument(i == 0 || i == 1, "invalid filter type");
        return zza(googleApiClient, capabilityListener, new IntentFilter[]{zzgj.zza("com.google.android.gms.wearable.CAPABILITY_CHANGED", uri, i)});
    }

    private static PendingResult<Status> zza(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, IntentFilter[] intentFilterArr) {
        return zzb.zza(googleApiClient, new zzt(intentFilterArr), capabilityListener);
    }

    public final PendingResult<Status> removeCapabilityListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, String str) {
        return googleApiClient.enqueue(new zzz(googleApiClient, new zzv(capabilityListener, str), (zzp) null));
    }

    public final PendingResult<Status> removeListener(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener) {
        return googleApiClient.enqueue(new zzz(googleApiClient, capabilityListener, (zzp) null));
    }
}
