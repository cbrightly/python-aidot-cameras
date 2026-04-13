package zendesk.android.settings.internal;

import kotlin.coroutines.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.http.f;
import retrofit2.http.k;
import retrofit2.http.y;
import zendesk.android.settings.internal.model.SettingsResponseDto;

/* compiled from: SettingsApi.kt */
public interface a {
    @Nullable
    @k({"X-Zendesk-Api-Version:2021-01-01"})
    @f
    Object a(@NotNull @y String str, @NotNull d<? super SettingsResponseDto> dVar);
}
