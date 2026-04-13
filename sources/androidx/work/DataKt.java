package androidx.work;

import androidx.exifinterface.media.ExifInterface;
import androidx.work.Data;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.k;
import kotlin.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: Data.kt */
public final class DataKt {
    @NotNull
    public static final Data workDataOf(@NotNull n<String, ? extends Object>... pairs) {
        k.e(pairs, "pairs");
        Data.Builder dataBuilder = new Data.Builder();
        int length = pairs.length;
        int i = 0;
        while (i < length) {
            n pair = pairs[i];
            i++;
            dataBuilder.put(pair.getFirst(), pair.getSecond());
        }
        Data build = dataBuilder.build();
        k.d(build, "dataBuilder.build()");
        return build;
    }

    public static final /* synthetic */ <T> boolean hasKeyWithValueOfType(Data $this$hasKeyWithValueOfType, String key) {
        k.e($this$hasKeyWithValueOfType, "<this>");
        k.e(key, CacheEntity.KEY);
        k.i(4, ExifInterface.GPS_DIRECTION_TRUE);
        return $this$hasKeyWithValueOfType.hasKeyWithValueOfType(key, Object.class);
    }
}
