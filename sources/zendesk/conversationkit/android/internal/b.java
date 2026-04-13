package zendesk.conversationkit.android.internal;

import com.alibaba.fastjson.asm.Opcodes;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.i;
import zendesk.conversationkit.android.internal.faye.c;
import zendesk.conversationkit.android.internal.noaccess.a;
import zendesk.conversationkit.android.internal.rest.d;
import zendesk.conversationkit.android.internal.rest.e;
import zendesk.conversationkit.android.internal.rest.g;
import zendesk.conversationkit.android.internal.user.Jwt;
import zendesk.conversationkit.android.model.User;
import zendesk.conversationkit.android.model.h;

/* compiled from: AccessLevelBuilder.kt */
public final class b {
    @NotNull
    private final d a;
    @NotNull
    private final c b;
    @NotNull
    private final b0 c;
    @NotNull
    private final h d;
    @NotNull
    private final e e;

    public b(@NotNull d restClientFactory, @NotNull c sunCoFayeClientFactory, @NotNull b0 storageFactory, @NotNull h clientDtoProvider, @NotNull e restClientFiles) {
        k.e(restClientFactory, "restClientFactory");
        k.e(sunCoFayeClientFactory, "sunCoFayeClientFactory");
        k.e(storageFactory, "storageFactory");
        k.e(clientDtoProvider, "clientDtoProvider");
        k.e(restClientFiles, "restClientFiles");
        this.a = restClientFactory;
        this.b = sunCoFayeClientFactory;
        this.c = storageFactory;
        this.d = clientDtoProvider;
        this.e = restClientFiles;
    }

    @NotNull
    public final a c() {
        return new a0(new a());
    }

    @NotNull
    public final a b(@NotNull i conversationKitSettings) {
        k.e(conversationKitSettings, "conversationKitSettings");
        String baseUrl = conversationKitSettings.a();
        if (baseUrl.length() == 0) {
            baseUrl = NetworkManager.MOCK_SCHEME_HTTPS + conversationKitSettings.b() + ".config" + conversationKitSettings.c().getValue$zendesk_conversationkit_conversationkit_android() + ".smooch.io";
        }
        return new y(new zendesk.conversationkit.android.internal.integration.a(conversationKitSettings, this.a.d(conversationKitSettings.b(), baseUrl), (j) null, 4, (DefaultConstructorMarker) null));
    }

    @NotNull
    public final a a(@NotNull i conversationKitSettings, @NotNull h config) {
        k.e(conversationKitSettings, "conversationKitSettings");
        k.e(config, "config");
        k conversationKitStorage = this.c.b();
        return new g(new zendesk.conversationkit.android.internal.app.a(conversationKitSettings, config, this.a.c(config.a().a(), config.b()), this.d, this.c.a(config.a().a()), conversationKitStorage, (Jwt.a) null, (j) null, Opcodes.CHECKCAST, (DefaultConstructorMarker) null), conversationKitStorage);
    }

    @NotNull
    public final a d(@NotNull i conversationKitSettings, @NotNull h config, @NotNull User user, @NotNull String clientId) {
        String str = clientId;
        k.e(conversationKitSettings, "conversationKitSettings");
        k.e(config, "config");
        k.e(user, "user");
        k.e(str, "clientId");
        k conversationKitStorage = this.c.b();
        zendesk.conversationkit.android.internal.faye.b a2 = this.b.a(user.k(), user.c());
        g g = this.a.g(config.a().a(), user.h(), config.b(), str);
        zendesk.conversationkit.android.internal.user.c c2 = this.c.c(user.h());
        zendesk.conversationkit.android.internal.app.b a3 = this.c.a(config.a().a());
        h hVar = this.d;
        return new d0(new zendesk.conversationkit.android.internal.user.a(conversationKitSettings, config, user, a2, g, c2, a3, conversationKitStorage, this.e, hVar, (Jwt.a) null, 1024, (DefaultConstructorMarker) null), conversationKitStorage);
    }
}
