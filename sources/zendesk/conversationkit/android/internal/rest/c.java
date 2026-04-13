package zendesk.conversationkit.android.internal.rest;

import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.rest.model.ConfigResponseDto;

/* compiled from: IntegrationRestClient.kt */
public final class c {
    @NotNull
    private final String a;
    @NotNull
    private final f b;

    public c(@NotNull String integrationId, @NotNull f sunshineConversationsApi) {
        k.e(integrationId, "integrationId");
        k.e(sunshineConversationsApi, "sunshineConversationsApi");
        this.a = integrationId;
        this.b = sunshineConversationsApi;
    }

    @Nullable
    public final Object a(@NotNull d<? super ConfigResponseDto> $completion) {
        return this.b.l(this.a, $completion);
    }
}
