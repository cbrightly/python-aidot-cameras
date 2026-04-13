package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: ConversationResponseDto.kt */
public final class ConversationResponseDto {
    @NotNull
    private final ConversationDto a;
    @Nullable
    private final List<MessageDto> b;
    @Nullable
    private final Boolean c;
    @NotNull
    private final AppUserDto d;
    @NotNull
    private final Map<String, AppUserDto> e;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConversationResponseDto)) {
            return false;
        }
        ConversationResponseDto conversationResponseDto = (ConversationResponseDto) obj;
        return k.a(this.a, conversationResponseDto.a) && k.a(this.b, conversationResponseDto.b) && k.a(this.c, conversationResponseDto.c) && k.a(this.d, conversationResponseDto.d) && k.a(this.e, conversationResponseDto.e);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        List<MessageDto> list = this.b;
        int i = 0;
        int hashCode2 = (hashCode + (list == null ? 0 : list.hashCode())) * 31;
        Boolean bool = this.c;
        if (bool != null) {
            i = bool.hashCode();
        }
        return ((((hashCode2 + i) * 31) + this.d.hashCode()) * 31) + this.e.hashCode();
    }

    @NotNull
    public String toString() {
        return "ConversationResponseDto(conversation=" + this.a + ", messages=" + this.b + ", hasPrevious=" + this.c + ", appUser=" + this.d + ", appUsers=" + this.e + ')';
    }

    public ConversationResponseDto(@NotNull ConversationDto conversation, @Nullable List<MessageDto> messages, @Nullable Boolean hasPrevious, @NotNull AppUserDto appUser, @NotNull Map<String, AppUserDto> appUsers) {
        k.e(conversation, "conversation");
        k.e(appUser, "appUser");
        k.e(appUsers, "appUsers");
        this.a = conversation;
        this.b = messages;
        this.c = hasPrevious;
        this.d = appUser;
        this.e = appUsers;
    }

    @NotNull
    public final ConversationDto c() {
        return this.a;
    }

    @Nullable
    public final List<MessageDto> e() {
        return this.b;
    }

    @Nullable
    public final Boolean d() {
        return this.c;
    }

    @NotNull
    public final AppUserDto a() {
        return this.d;
    }

    @NotNull
    public final Map<String, AppUserDto> b() {
        return this.e;
    }
}
