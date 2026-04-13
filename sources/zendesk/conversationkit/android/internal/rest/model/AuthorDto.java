package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: AuthorDto.kt */
public final class AuthorDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final ClientDto c;
    @Nullable
    private final String d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AuthorDto)) {
            return false;
        }
        AuthorDto authorDto = (AuthorDto) obj;
        return k.a(this.a, authorDto.a) && k.a(this.b, authorDto.b) && k.a(this.c, authorDto.c) && k.a(this.d, authorDto.d);
    }

    public int hashCode() {
        int hashCode = ((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31;
        String str = this.d;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    @NotNull
    public String toString() {
        return "AuthorDto(appUserId=" + this.a + ", role=" + this.b + ", client=" + this.c + ", sessionId=" + this.d + ')';
    }

    public AuthorDto(@NotNull String appUserId, @NotNull String role, @NotNull ClientDto client, @Nullable String sessionId) {
        k.e(appUserId, "appUserId");
        k.e(role, "role");
        k.e(client, "client");
        this.a = appUserId;
        this.b = role;
        this.c = client;
        this.d = sessionId;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ AuthorDto(String str, String str2, ClientDto clientDto, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, clientDto, (i & 8) != 0 ? null : str3);
    }

    @NotNull
    public final String a() {
        return this.a;
    }

    @NotNull
    public final String c() {
        return this.b;
    }

    @NotNull
    public final ClientDto b() {
        return this.c;
    }

    @Nullable
    public final String d() {
        return this.d;
    }
}
