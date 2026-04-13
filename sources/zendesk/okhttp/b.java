package zendesk.okhttp;

import kotlin.jvm.internal.k;
import okhttp3.w;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;

/* compiled from: NetworkExt.kt */
public final class b {
    @NotNull
    public static final z.a a(@NotNull z.a $this$addInterceptors, @NotNull w... interceptors) {
        k.e($this$addInterceptors, "<this>");
        k.e(interceptors, "interceptors");
        for (w it : interceptors) {
            $this$addInterceptors.a(it);
        }
        return $this$addInterceptors;
    }
}
