package zendesk.android.internal;

import android.net.Uri;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ChannelKeyFields.kt */
public final class b {
    @NotNull
    public static final String a(@NotNull ChannelKeyFields $this$getBaseUrl) {
        k.e($this$getBaseUrl, "<this>");
        Uri parsedUri = Uri.parse($this$getBaseUrl.a());
        String uri = new Uri.Builder().scheme(parsedUri.getScheme()).encodedAuthority(parsedUri.getEncodedAuthority()).build().toString();
        k.d(uri, "uri.toString()");
        return uri;
    }
}
