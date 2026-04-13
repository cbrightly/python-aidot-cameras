package zendesk.conversationkit.android.internal.rest.user;

import kotlin.coroutines.d;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.http.i;
import retrofit2.http.k;
import retrofit2.http.o;
import retrofit2.http.s;
import zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto;
import zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody;
import zendesk.conversationkit.android.internal.rest.user.model.LogoutRequestBody;

/* compiled from: SunshineAppUserService.kt */
public interface a {
    @o("v2/apps/{appId}/appusers/{appUserId}/logout")
    @Nullable
    @k({"Content-Type:application/json"})
    Object e(@NotNull @s("appId") String str, @NotNull @s("appUserId") String str2, @NotNull @i("Authorization") String str3, @NotNull @retrofit2.http.a LogoutRequestBody logoutRequestBody, @NotNull d<? super x> dVar);

    @o("v2/apps/{appId}/login")
    @Nullable
    @k({"Content-Type:application/json"})
    Object h(@NotNull @s("appId") String str, @NotNull @i("Authorization") String str2, @NotNull @retrofit2.http.a LoginRequestBody loginRequestBody, @NotNull d<? super AppUserResponseDto> dVar);
}
