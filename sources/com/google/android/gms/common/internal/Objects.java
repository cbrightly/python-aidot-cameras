package com.google.android.gms.common.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class Objects {

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public static final class ToStringHelper {
        private final List zza = new ArrayList();
        private final Object zzb;

        /* synthetic */ ToStringHelper(Object obj, zzai zzai) {
            Preconditions.checkNotNull(obj);
            this.zzb = obj;
        }

        @NonNull
        @KeepForSdk
        @CanIgnoreReturnValue
        public ToStringHelper add(@NonNull String name, @Nullable Object value) {
            List list = this.zza;
            Preconditions.checkNotNull(name);
            String valueOf = String.valueOf(value);
            list.add(name + "=" + valueOf);
            return this;
        }

        @NonNull
        @KeepForSdk
        public String toString() {
            StringBuilder sb = new StringBuilder(100);
            sb.append(this.zzb.getClass().getSimpleName());
            sb.append('{');
            int size = this.zza.size();
            for (int i = 0; i < size; i++) {
                sb.append((String) this.zza.get(i));
                if (i < size - 1) {
                    sb.append(", ");
                }
            }
            sb.append('}');
            return sb.toString();
        }
    }

    private Objects() {
        throw new AssertionError("Uninstantiable");
    }

    @KeepForSdk
    public static boolean checkBundlesEquality(@NonNull Bundle firstBundle, @NonNull Bundle secondBundle) {
        if (firstBundle == null || secondBundle == null) {
            return firstBundle == secondBundle;
        }
        if (firstBundle.size() != secondBundle.size()) {
            return false;
        }
        Set<String> keySet = firstBundle.keySet();
        if (!keySet.containsAll(secondBundle.keySet())) {
            return false;
        }
        for (String str : keySet) {
            if (!equal(firstBundle.get(str), secondBundle.get(str))) {
                return false;
            }
        }
        return true;
    }

    @KeepForSdk
    public static boolean equal(@Nullable Object a, @Nullable Object b) {
        return a == b || (a != null && a.equals(b));
    }

    @KeepForSdk
    public static int hashCode(@NonNull Object... objects) {
        return Arrays.hashCode(objects);
    }

    @NonNull
    @KeepForSdk
    public static ToStringHelper toStringHelper(@NonNull Object object) {
        return new ToStringHelper(object, (zzai) null);
    }
}
