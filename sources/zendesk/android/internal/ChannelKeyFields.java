package zendesk.android.internal;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: ChannelKeyFields.kt */
public final class ChannelKeyFields {
    @NotNull
    private final String a;

    @NotNull
    public final ChannelKeyFields copy(@e(name = "settings_url") @NotNull String str) {
        k.e(str, "settingsUrl");
        return new ChannelKeyFields(str);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChannelKeyFields) && k.a(this.a, ((ChannelKeyFields) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "ChannelKeyFields(settingsUrl=" + this.a + ')';
    }

    public ChannelKeyFields(@e(name = "settings_url") @NotNull String settingsUrl) {
        k.e(settingsUrl, "settingsUrl");
        this.a = settingsUrl;
    }

    @NotNull
    public final String a() {
        return this.a;
    }
}
