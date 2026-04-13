package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: ClientDto.kt */
public final class ClientDto {
    @NotNull
    private final String a;
    @Nullable
    private final String b;
    @Nullable
    private final String c;
    @NotNull
    private final String d;
    @NotNull
    private final String e;
    @Nullable
    private final String f;
    @Nullable
    private final String g;
    @Nullable
    private final String h;
    @NotNull
    private final ClientInfoDto i;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClientDto)) {
            return false;
        }
        ClientDto clientDto = (ClientDto) obj;
        return k.a(this.a, clientDto.a) && k.a(this.b, clientDto.b) && k.a(this.c, clientDto.c) && k.a(this.d, clientDto.d) && k.a(this.e, clientDto.e) && k.a(this.f, clientDto.f) && k.a(this.g, clientDto.g) && k.a(this.h, clientDto.h) && k.a(this.i, clientDto.i);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        int i2 = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.c;
        int hashCode3 = (((((hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31;
        String str3 = this.f;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.g;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.h;
        if (str5 != null) {
            i2 = str5.hashCode();
        }
        return ((hashCode5 + i2) * 31) + this.i.hashCode();
    }

    @NotNull
    public String toString() {
        return "ClientDto(id=" + this.a + ", status=" + this.b + ", lastSeen=" + this.c + ", platform=" + this.d + ", integrationId=" + this.e + ", pushNotificationToken=" + this.f + ", appVersion=" + this.g + ", displayName=" + this.h + ", info=" + this.i + ')';
    }

    public ClientDto(@NotNull String id, @Nullable String status, @Nullable String lastSeen, @NotNull String platform, @NotNull String integrationId, @Nullable String pushNotificationToken, @Nullable String appVersion, @Nullable String displayName, @NotNull ClientInfoDto info) {
        k.e(id, "id");
        k.e(platform, "platform");
        k.e(integrationId, "integrationId");
        k.e(info, "info");
        this.a = id;
        this.b = status;
        this.c = lastSeen;
        this.d = platform;
        this.e = integrationId;
        this.f = pushNotificationToken;
        this.g = appVersion;
        this.h = displayName;
        this.i = info;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ ClientDto(java.lang.String r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, zendesk.conversationkit.android.internal.rest.model.ClientInfoDto r22, int r23, kotlin.jvm.internal.DefaultConstructorMarker r24) {
        /*
            r13 = this;
            r0 = r23
            r1 = r0 & 2
            r2 = 0
            if (r1 == 0) goto L_0x0009
            r5 = r2
            goto L_0x000a
        L_0x0009:
            r5 = r15
        L_0x000a:
            r1 = r0 & 4
            if (r1 == 0) goto L_0x0010
            r6 = r2
            goto L_0x0012
        L_0x0010:
            r6 = r16
        L_0x0012:
            r0 = r0 & 128(0x80, float:1.794E-43)
            if (r0 == 0) goto L_0x0018
            r11 = r2
            goto L_0x001a
        L_0x0018:
            r11 = r21
        L_0x001a:
            r3 = r13
            r4 = r14
            r7 = r17
            r8 = r18
            r9 = r19
            r10 = r20
            r12 = r22
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.rest.model.ClientDto.<init>(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, zendesk.conversationkit.android.internal.rest.model.ClientInfoDto, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final String c() {
        return this.a;
    }

    @Nullable
    public final String i() {
        return this.b;
    }

    @Nullable
    public final String f() {
        return this.c;
    }

    @NotNull
    public final String g() {
        return this.d;
    }

    @NotNull
    public final String e() {
        return this.e;
    }

    @Nullable
    public final String h() {
        return this.f;
    }

    @Nullable
    public final String a() {
        return this.g;
    }

    @Nullable
    public final String b() {
        return this.h;
    }

    @NotNull
    public final ClientInfoDto d() {
        return this.i;
    }
}
