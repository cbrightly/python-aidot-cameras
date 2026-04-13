package zendesk.conversationkit.android.internal.rest;

import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.rest.model.AppUserRequestDto;
import zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto;
import zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody;

/* compiled from: AppRestClient.kt */
public final class a {
    @NotNull
    private final String a;
    @NotNull
    private final f b;

    public a(@NotNull String appId, @NotNull f sunshineConversationsApi) {
        k.e(appId, "appId");
        k.e(sunshineConversationsApi, "sunshineConversationsApi");
        this.a = appId;
        this.b = sunshineConversationsApi;
    }

    @Nullable
    public final Object a(@NotNull String clientId, @NotNull AppUserRequestDto appUserRequestDto, @NotNull d<? super AppUserResponseDto> $completion) {
        return this.b.i(this.a, clientId, appUserRequestDto, $completion);
    }

    @Nullable
    public final Object b(@NotNull String jwt, @NotNull LoginRequestBody loginRequestBody, @NotNull d<? super AppUserResponseDto> $completion) {
        return this.b.h(this.a, k.l("Bearer ", jwt), loginRequestBody, $completion);
    }
}
