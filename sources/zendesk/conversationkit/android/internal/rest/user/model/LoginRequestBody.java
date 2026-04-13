package zendesk.conversationkit.android.internal.rest.user.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.rest.model.ClientDto;

@g(generateAdapter = true)
/* compiled from: LoginRequestBody.kt */
public final class LoginRequestBody {
    @NotNull
    private final String a;
    @NotNull
    private final ClientDto b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;

    public LoginRequestBody(@e(name = "userId") @NotNull String userId, @e(name = "client") @NotNull ClientDto client, @e(name = "appUserId") @Nullable String appUserId, @e(name = "sessionToken") @Nullable String sessionToken) {
        k.e(userId, "userId");
        k.e(client, "client");
        this.a = userId;
        this.b = client;
        this.c = appUserId;
        this.d = sessionToken;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ LoginRequestBody(String str, ClientDto clientDto, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, clientDto, (i & 4) != 0 ? null : str2, (i & 8) != 0 ? null : str3);
    }

    @NotNull
    public final String d() {
        return this.a;
    }

    @NotNull
    public final ClientDto b() {
        return this.b;
    }

    @Nullable
    public final String a() {
        return this.c;
    }

    @Nullable
    public final String c() {
        return this.d;
    }
}
