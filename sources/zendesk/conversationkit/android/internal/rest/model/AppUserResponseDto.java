package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: AppUserResponseDto.kt */
public final class AppUserResponseDto {
    @NotNull
    private final UserSettingsDto a;
    @NotNull
    private final List<ConversationDto> b;
    @NotNull
    private final ConversationsPaginationDto c;
    @NotNull
    private final AppUserDto d;
    @NotNull
    private final Map<String, AppUserDto> e;
    @Nullable
    private final String f;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppUserResponseDto)) {
            return false;
        }
        AppUserResponseDto appUserResponseDto = (AppUserResponseDto) obj;
        return k.a(this.a, appUserResponseDto.a) && k.a(this.b, appUserResponseDto.b) && k.a(this.c, appUserResponseDto.c) && k.a(this.d, appUserResponseDto.d) && k.a(this.e, appUserResponseDto.e) && k.a(this.f, appUserResponseDto.f);
    }

    public int hashCode() {
        int hashCode = ((((((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31;
        String str = this.f;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    @NotNull
    public String toString() {
        return "AppUserResponseDto(settings=" + this.a + ", conversations=" + this.b + ", conversationsPagination=" + this.c + ", appUser=" + this.d + ", appUsers=" + this.e + ", sessionToken=" + this.f + ')';
    }

    public AppUserResponseDto(@NotNull UserSettingsDto settings, @NotNull List<ConversationDto> conversations, @NotNull ConversationsPaginationDto conversationsPagination, @NotNull AppUserDto appUser, @NotNull Map<String, AppUserDto> appUsers, @Nullable String sessionToken) {
        k.e(settings, "settings");
        k.e(conversations, "conversations");
        k.e(conversationsPagination, "conversationsPagination");
        k.e(appUser, "appUser");
        k.e(appUsers, "appUsers");
        this.a = settings;
        this.b = conversations;
        this.c = conversationsPagination;
        this.d = appUser;
        this.e = appUsers;
        this.f = sessionToken;
    }

    @NotNull
    public final UserSettingsDto f() {
        return this.a;
    }

    @NotNull
    public final List<ConversationDto> c() {
        return this.b;
    }

    @NotNull
    public final ConversationsPaginationDto d() {
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

    @Nullable
    public final String e() {
        return this.f;
    }
}
