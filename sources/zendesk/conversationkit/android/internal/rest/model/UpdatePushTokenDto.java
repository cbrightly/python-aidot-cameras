package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: UpdatePushTokenDto.kt */
public final class UpdatePushTokenDto {
    @Nullable
    private final String a;
    @Nullable
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UpdatePushTokenDto)) {
            return false;
        }
        UpdatePushTokenDto updatePushTokenDto = (UpdatePushTokenDto) obj;
        return k.a(this.a, updatePushTokenDto.a) && k.a(this.b, updatePushTokenDto.b);
    }

    public int hashCode() {
        String str = this.a;
        int i = 0;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.b;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "UpdatePushTokenDto(pushNotificationToken=" + this.a + ", integrationId=" + this.b + ')';
    }

    public UpdatePushTokenDto(@Nullable String pushNotificationToken, @Nullable String integrationId) {
        this.a = pushNotificationToken;
        this.b = integrationId;
    }

    @Nullable
    public final String b() {
        return this.a;
    }

    @Nullable
    public final String a() {
        return this.b;
    }
}
