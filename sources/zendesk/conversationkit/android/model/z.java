package zendesk.conversationkit.android.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.collections.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto;
import zendesk.conversationkit.android.internal.rest.model.ConversationDto;
import zendesk.conversationkit.android.internal.rest.model.RealtimeSettingsDto;
import zendesk.conversationkit.android.internal.rest.model.TypingSettingsDto;
import zendesk.conversationkit.android.model.f;

/* compiled from: User.kt */
public final class z {
    public static /* synthetic */ User d(AppUserResponseDto appUserResponseDto, String str, f fVar, int i, Object obj) {
        if ((i & 2) != 0) {
            if (appUserResponseDto.e() != null) {
                fVar = new f.b(appUserResponseDto.e());
            } else {
                fVar = f.c.a;
            }
        }
        return c(appUserResponseDto, str, fVar);
    }

    @NotNull
    public static final User c(@NotNull AppUserResponseDto $this$toUser, @NotNull String appId, @NotNull f authenticationType) {
        String str = appId;
        f fVar = authenticationType;
        k.e($this$toUser, "<this>");
        k.e(str, "appId");
        k.e(fVar, "authenticationType");
        String d = $this$toUser.a().d();
        String j = $this$toUser.a().j();
        String c = $this$toUser.a().c();
        String i = $this$toUser.a().i();
        String b = $this$toUser.a().b();
        String e = $this$toUser.a().e();
        String h = $this$toUser.a().h();
        Iterable<ConversationDto> $this$map$iv = $this$toUser.c();
        ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
        for (ConversationDto d2 : $this$map$iv) {
            arrayList.add(j.d(d2, $this$toUser.a().d(), $this$toUser.b(), (List) null, false, 12, (Object) null));
            $this$map$iv = $this$map$iv;
        }
        RealtimeSettings a = a($this$toUser.f().a(), str, $this$toUser.a().d());
        TypingSettings b2 = b($this$toUser.f().b());
        f.a aVar = fVar instanceof f.a ? (f.a) fVar : null;
        String a2 = aVar == null ? null : aVar.a();
        f.b bVar = fVar instanceof f.b ? (f.b) fVar : null;
        return new User(d, j, c, i, b, e, h, arrayList, a, b2, bVar == null ? null : bVar.a(), a2);
    }

    @NotNull
    public static final RealtimeSettings a(@NotNull RealtimeSettingsDto $this$toRealtimeSettings, @NotNull String appId, @NotNull String userId) {
        k.e($this$toRealtimeSettings, "<this>");
        k.e(appId, "appId");
        k.e(userId, "userId");
        return new RealtimeSettings($this$toRealtimeSettings.c(), $this$toRealtimeSettings.a(), (long) $this$toRealtimeSettings.e(), $this$toRealtimeSettings.d(), (long) $this$toRealtimeSettings.b(), (TimeUnit) null, appId, userId, 32, (DefaultConstructorMarker) null);
    }

    @NotNull
    public static final TypingSettings b(@NotNull TypingSettingsDto $this$toTypingSettings) {
        k.e($this$toTypingSettings, "<this>");
        return new TypingSettings($this$toTypingSettings.a());
    }
}
