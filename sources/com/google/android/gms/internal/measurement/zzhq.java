package com.google.android.gms.internal.measurement;

import android.net.Uri;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzhq {
    @GuardedBy("PhenotypeConstants.class")
    private static final ArrayMap zza = new ArrayMap();

    public static synchronized Uri zza(String str) {
        Uri uri;
        synchronized (zzhq.class) {
            ArrayMap arrayMap = zza;
            uri = (Uri) arrayMap.get("com.google.android.gms.measurement");
            if (uri == null) {
                uri = Uri.parse("content://com.google.android.gms.phenotype/".concat(String.valueOf(Uri.encode("com.google.android.gms.measurement"))));
                arrayMap.put("com.google.android.gms.measurement", uri);
            }
        }
        return uri;
    }
}
