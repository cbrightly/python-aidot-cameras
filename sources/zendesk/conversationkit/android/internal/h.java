package zendesk.conversationkit.android.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.rest.model.ClientDto;
import zendesk.conversationkit.android.internal.rest.model.ClientInfoDto;

/* compiled from: ClientDtoProvider.kt */
public final class h {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final x c;
    @NotNull
    private final String d;

    public h(@NotNull String sdkVendor, @NotNull String sdkVersion, @NotNull x hostAppInfo, @NotNull String localeString) {
        k.e(sdkVendor, "sdkVendor");
        k.e(sdkVersion, "sdkVersion");
        k.e(hostAppInfo, "hostAppInfo");
        k.e(localeString, "localeString");
        this.a = sdkVendor;
        this.b = sdkVersion;
        this.c = hostAppInfo;
        this.d = localeString;
    }

    @NotNull
    public final ClientDto a(@NotNull String integrationId, @NotNull String clientId, @Nullable String pushToken) {
        k.e(integrationId, "integrationId");
        k.e(clientId, "clientId");
        String c2 = this.c.c();
        String b2 = this.c.b();
        String str = this.a;
        String str2 = this.b;
        return new ClientDto(clientId, (String) null, (String) null, "android", integrationId, pushToken, this.c.d(), (String) null, new ClientInfoDto(c2, b2, str, str2, this.c.f() + ' ' + this.c.g(), this.c.h(), this.c.i(), this.c.a(), this.c.e(), this.d), 134, (DefaultConstructorMarker) null);
    }
}
