package zendesk.conversationkit.android.internal.rest.model;

import com.google.firebase.messaging.Constants;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: MessageDto.kt */
public final class MessageFieldOptionDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MessageFieldOptionDto)) {
            return false;
        }
        MessageFieldOptionDto messageFieldOptionDto = (MessageFieldOptionDto) obj;
        return k.a(this.a, messageFieldOptionDto.a) && k.a(this.b, messageFieldOptionDto.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "MessageFieldOptionDto(name=" + this.a + ", label=" + this.b + ')';
    }

    public MessageFieldOptionDto(@NotNull String name, @NotNull String label) {
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
