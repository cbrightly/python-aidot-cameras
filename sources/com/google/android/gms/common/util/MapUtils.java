package com.google.android.gms.common.util;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.maps.android.BuildConfig;
import java.util.HashMap;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class MapUtils {
    @KeepForSdk
    public static void writeStringMapToJson(@NonNull StringBuilder sb, @NonNull HashMap<String, String> stringMap) {
        sb.append("{");
        boolean z = true;
        for (String next : stringMap.keySet()) {
            if (!z) {
                sb.append(",");
            }
            String str = stringMap.get(next);
            sb.append("\"");
            sb.append(next);
            sb.append("\":");
            if (str == null) {
                sb.append(BuildConfig.TRAVIS);
                z = false;
            } else {
                sb.append("\"");
                sb.append(str);
                sb.append("\"");
                z = false;
            }
        }
        sb.append("}");
    }
}
