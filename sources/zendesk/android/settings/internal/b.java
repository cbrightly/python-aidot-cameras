package zendesk.android.settings.internal;

import com.squareup.moshi.r;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import retrofit2.t;
import zendesk.android.internal.g;

/* compiled from: SettingsModule.kt */
public final class b {
    @NotNull
    public final f b(@NotNull g settingsRestClient) {
        k.e(settingsRestClient, "settingsRestClient");
        return new f(settingsRestClient);
    }

    @NotNull
    public final g c(@NotNull a settingsApi, @NotNull r moshi, @NotNull g componentConfig) {
        k.e(settingsApi, "settingsApi");
        k.e(moshi, "moshi");
        k.e(componentConfig, "componentConfig");
        return new g(settingsApi, moshi, componentConfig);
    }

    @NotNull
    public final a a(@NotNull t retrofit) {
        k.e(retrofit, "retrofit");
        Object b = retrofit.b(a.class);
        k.d(b, "retrofit.create(SettingsApi::class.java)");
        return (a) b;
    }
}
