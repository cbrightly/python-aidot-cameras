package zendesk.conversationkit.android.internal.rest;

import kotlin.coroutines.d;
import kotlin.x;
import okhttp3.y;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.http.i;
import retrofit2.http.k;
import retrofit2.http.l;
import retrofit2.http.o;
import retrofit2.http.p;
import retrofit2.http.q;
import retrofit2.http.s;
import zendesk.conversationkit.android.internal.rest.model.ActivityDataRequestDto;
import zendesk.conversationkit.android.internal.rest.model.AppUserRequestDto;
import zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto;
import zendesk.conversationkit.android.internal.rest.model.AuthorDto;
import zendesk.conversationkit.android.internal.rest.model.ConfigResponseDto;
import zendesk.conversationkit.android.internal.rest.model.ConversationResponseDto;
import zendesk.conversationkit.android.internal.rest.model.CreateConversationRequestDto;
import zendesk.conversationkit.android.internal.rest.model.MetadataDto;
import zendesk.conversationkit.android.internal.rest.model.SendMessageRequestDto;
import zendesk.conversationkit.android.internal.rest.model.SendMessageResponseDto;
import zendesk.conversationkit.android.internal.rest.model.UpdateAppUserLocaleDto;
import zendesk.conversationkit.android.internal.rest.model.UpdatePushTokenDto;
import zendesk.conversationkit.android.internal.rest.model.UploadFileResponseDto;
import zendesk.conversationkit.android.internal.rest.user.a;

/* compiled from: SunshineConversationsApi.kt */
public interface f extends a {
    @Nullable
    @p("v2/apps/{appId}/appusers/{appUserId}/clients/{clientId}")
    @k({"Content-Type:application/json"})
    Object a(@NotNull @i("Authorization") String str, @NotNull @s("appId") String str2, @NotNull @s("appUserId") String str3, @NotNull @s("clientId") String str4, @NotNull @retrofit2.http.a UpdatePushTokenDto updatePushTokenDto, @NotNull d<? super x> dVar);

    @o("v2/apps/{appId}/conversations/{conversationId}/files")
    @Nullable
    @l
    Object b(@NotNull @i("Authorization") String str, @NotNull @s("appId") String str2, @NotNull @s("conversationId") String str3, @NotNull @q("author") AuthorDto authorDto, @NotNull @q("message") MetadataDto metadataDto, @NotNull @q y.c cVar, @NotNull d<? super UploadFileResponseDto> dVar);

    @Nullable
    @k({"Content-Type:application/json"})
    @retrofit2.http.f("v2/apps/{appId}/appusers/{appUserId}")
    Object c(@NotNull @i("Authorization") String str, @NotNull @s("appId") String str2, @NotNull @s("appUserId") String str3, @NotNull d<? super AppUserResponseDto> dVar);

    @o("v2/apps/{appId}/appusers/{appUserId}/conversations")
    @Nullable
    @k({"Content-Type:application/json"})
    Object d(@NotNull @i("Authorization") String str, @NotNull @s("appId") String str2, @NotNull @s("appUserId") String str3, @NotNull @retrofit2.http.a CreateConversationRequestDto createConversationRequestDto, @NotNull d<? super ConversationResponseDto> dVar);

    @o("v2/apps/{appId}/conversations/{conversationId}/activity")
    @Nullable
    @k({"Content-Type:application/json"})
    Object f(@NotNull @i("Authorization") String str, @NotNull @s("appId") String str2, @NotNull @s("conversationId") String str3, @NotNull @retrofit2.http.a ActivityDataRequestDto activityDataRequestDto, @NotNull d<? super x> dVar);

    @Nullable
    @p("v2/apps/{appId}/appusers/{appUserId}")
    @k({"Content-Type:application/json"})
    Object g(@NotNull @i("Authorization") String str, @NotNull @s("appId") String str2, @NotNull @s("appUserId") String str3, @NotNull @retrofit2.http.a UpdateAppUserLocaleDto updateAppUserLocaleDto, @NotNull d<? super x> dVar);

    @o("v2/apps/{appId}/appusers")
    @Nullable
    @k({"Content-Type:application/json"})
    Object i(@NotNull @s("appId") String str, @NotNull @i("x-smooch-clientid") String str2, @NotNull @retrofit2.http.a AppUserRequestDto appUserRequestDto, @NotNull d<? super AppUserResponseDto> dVar);

    @o("v2/apps/{appId}/conversations/{conversationId}/messages")
    @Nullable
    @k({"Content-Type:application/json"})
    Object j(@NotNull @i("Authorization") String str, @NotNull @s("appId") String str2, @NotNull @s("conversationId") String str3, @NotNull @retrofit2.http.a SendMessageRequestDto sendMessageRequestDto, @NotNull d<? super SendMessageResponseDto> dVar);

    @Nullable
    @k({"Content-Type:application/json"})
    @retrofit2.http.f("v2/apps/{appId}/conversations/{conversationId}")
    Object k(@NotNull @i("Authorization") String str, @NotNull @s("appId") String str2, @NotNull @s("conversationId") String str3, @NotNull d<? super ConversationResponseDto> dVar);

    @Nullable
    @k({"Content-Type:application/json"})
    @retrofit2.http.f("sdk/v2/integrations/{integrationId}/config")
    Object l(@NotNull @s("integrationId") String str, @NotNull d<? super ConfigResponseDto> dVar);
}
