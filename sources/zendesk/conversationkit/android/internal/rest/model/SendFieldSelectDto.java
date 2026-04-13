package zendesk.conversationkit.android.internal.rest.model;

import com.google.firebase.messaging.Constants;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: SendMessageRequestDto.kt */
public final class SendFieldSelectDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SendFieldSelectDto)) {
            return false;
        }
        SendFieldSelectDto sendFieldSelectDto = (SendFieldSelectDto) obj;
        return k.a(this.a, sendFieldSelectDto.a) && k.a(this.b, sendFieldSelectDto.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "SendFieldSelectDto(name=" + this.a + ", label=" + this.b + ')';
    }

    public SendFieldSelectDto(@NotNull String name, @NotNull String label) {
        k.e(name, "name");
        k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
        this.a = name;
        this.b = label;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    @NotNull
    public final String a() {
        return this.b;
    }
}
