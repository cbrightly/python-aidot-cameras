package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.wrappers.Wrappers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@ShowFirstParty
@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class AndroidUtilsLight {
    private static volatile int zza = -1;

    @KeepForSdk
    @Deprecated
    @Nullable
    public static byte[] getPackageCertificateHashBytes(@NonNull Context context, @NonNull String packageName) {
        MessageDigest zza2;
        PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(packageName, 64);
        Signature[] signatureArr = packageInfo.signatures;
        if (signatureArr == null || signatureArr.length != 1 || (zza2 = zza("SHA1")) == null) {
            return null;
        }
        return zza2.digest(packageInfo.signatures[0].toByteArray());
    }

    @Nullable
    public static MessageDigest zza(@NonNull String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i++;
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }
}
