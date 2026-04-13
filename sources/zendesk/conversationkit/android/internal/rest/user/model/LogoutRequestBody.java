package zendesk.conversationkit.android.internal.rest.user.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.internal.rest.model.ClientDto;

@g(generateAdapter = true)
/* compiled from: LogoutRequestBody.kt */
public final class LogoutRequestBody {
    @NotNull
    private final ClientDto a;

    public LogoutRequestBody(@e(name = "client") @NotNull ClientDto client) {
        k.e(client, "client");
        this.a = client;
    }

    @NotNull
    public final ClientDto a() {
        return this.a;
    }
}
