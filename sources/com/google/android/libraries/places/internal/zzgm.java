package com.google.android.libraries.places.internal;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import androidx.annotation.Nullable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzgm {
    @Nullable
    public static String zza(PackageManager packageManager, String str) {
        Signature[] signatureArr;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 64);
            if (!(packageInfo == null || (signatureArr = packageInfo.signatures) == null || signatureArr.length == 0)) {
                if (signatureArr[0] != null) {
                    return zzb(signatureArr[0]);
                }
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("CredentialsHelper", "Unable to get certificate fingerprint for package: ".concat(String.valueOf(str)), e);
            return null;
        }
    }

    @Nullable
    private static String zzb(Signature signature) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-1").digest(signature.toByteArray());
            return zznb.zzd().zze(digest, 0, digest.length);
        } catch (NoSuchAlgorithmException e) {
            Log.e("CredentialsHelper", "Unable to get certificate fingerprint.", e);
            return null;
        }
    }
}
