package zendesk.android;

import com.squareup.moshi.r;
import kotlin.jvm.internal.k;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.internal.ChannelKeyFields;
import zendesk.android.internal.i;

/* compiled from: ZendeskCredentials.kt */
public final class e {
    @Nullable
    public static final ChannelKeyFields a(@NotNull d $this$toChannelKeyFields, @NotNull r moshi) {
        k.e($this$toChannelKeyFields, "<this>");
        k.e(moshi, "moshi");
        try {
            f a = f.Companion.a($this$toChannelKeyFields.a());
            String decodedChannelKey = a == null ? null : a.utf8();
            if (decodedChannelKey != null) {
                return moshi.c(ChannelKeyFields.class).c(decodedChannelKey);
            }
            throw i.c.INSTANCE;
        } catch (Throwable th) {
            return null;
        }
    }
}
