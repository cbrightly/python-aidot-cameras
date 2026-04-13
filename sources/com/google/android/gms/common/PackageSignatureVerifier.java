package com.google.android.gms.common;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.RestrictedInheritance;

@RestrictedInheritance(allowedOnPath = ".*javatests.*/com/google/android/gms/common/.*", explanation = "Sub classing of GMS Core's APIs are restricted to testing fakes.", link = "go/gmscore-restrictedinheritance")
@ShowFirstParty
@KeepForSdk
@CheckReturnValue
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class PackageSignatureVerifier {
    @Nullable
    @VisibleForTesting
    static volatile zzac zza;
    @Nullable
    private static zzad zzb;

    private static zzad zza() {
        zzad zzad;
        synchronized (zzad.class) {
            if (zzb == null) {
                zzb = new zzad();
            }
            zzad = zzb;
        }
        return zzad;
    }

    @ShowFirstParty
    @NonNull
    @KeepForSdk
    public PackageVerificationResult queryPackageSignatureVerified(@NonNull Context context, @NonNull String callingPackage) {
        String str;
        boolean honorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(context);
        zza();
        if (zzn.zzf()) {
            if (true != honorsDebugCertificates) {
                str = "-0";
            } else {
                str = "-1";
            }
            String concat = String.valueOf(callingPackage).concat(str);
            if (zza != null && zza.zza.equals(concat)) {
                return zza.zzb;
            }
            zza();
            zzx zzc = zzn.zzc(callingPackage, honorsDebugCertificates, false, false);
            if (zzc.zza) {
                zza = new zzac(concat, PackageVerificationResult.zzd(callingPackage, zzc.zzd));
                return zza.zzb;
            }
            Preconditions.checkNotNull(zzc.zzb);
            return PackageVerificationResult.zza(callingPackage, zzc.zzb, zzc.zzc);
        }
        throw new zzae();
    }

    @ShowFirstParty
    @NonNull
    @KeepForSdk
    public PackageVerificationResult queryPackageSignatureVerifiedWithRetry(@NonNull Context context, @NonNull String callingPackage) {
        try {
            PackageVerificationResult queryPackageSignatureVerified = queryPackageSignatureVerified(context, callingPackage);
            queryPackageSignatureVerified.zzb();
            return queryPackageSignatureVerified;
        } catch (SecurityException e) {
            PackageVerificationResult queryPackageSignatureVerified2 = queryPackageSignatureVerified(context, callingPackage);
            if (!queryPackageSignatureVerified2.zzc()) {
                return queryPackageSignatureVerified2;
            }
            Log.e("PkgSignatureVerifier", "Got flaky result during package signature verification", e);
            return queryPackageSignatureVerified2;
        }
    }
}
