package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: AppUserRequestDto.kt */
public final class AppUserRequestDto {
    @NotNull
    private final ClientDto a;
    @Nullable
    private final String b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;
    @Nullable
    private final String e;
    @Nullable
    private final Map<String, Object> f;
    @NotNull
    private final String g;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppUserRequestDto)) {
            return false;
        }
        AppUserRequestDto appUserRequestDto = (AppUserRequestDto) obj;
        return k.a(this.a, appUserRequestDto.a) && k.a(this.b, appUserRequestDto.b) && k.a(this.c, appUserRequestDto.c) && k.a(this.d, appUserRequestDto.d) && k.a(this.e, appUserRequestDto.e) && k.a(this.f, appUserRequestDto.f) && k.a(this.g, appUserRequestDto.g);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.c;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.d;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.e;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        Map<String, Object> map = this.f;
        if (map != null) {
            i = map.hashCode();
        }
        return ((hashCode5 + i) * 31) + this.g.hashCode();
    }

    @NotNull
    public String toString() {
        return "AppUserRequestDto(client=" + this.a + ", userId=" + this.b + ", givenName=" + this.c + ", surname=" + this.d + ", email=" + this.e + ", properties=" + this.f + ", intent=" + this.g + ')';
    }

    public AppUserRequestDto(@NotNull ClientDto client, @Nullable String userId, @Nullable String givenName, @Nullable String surname, @Nullable String email, @Nullable Map<String, ? extends Object> properties, @NotNull String intent) {
        k.e(client, "client");
        k.e(intent, "intent");
        this.a = client;
        this.b = userId;
        this.c = givenName;
        this.d = surname;
        this.e = email;
        this.f = properties;
        this.g = intent;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ AppUserRequestDto(zendesk.conversationkit.android.internal.rest.model.ClientDto r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, java.util.Map r12, java.lang.String r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r6 = this;
            r0 = r14 & 2
            r1 = 0
            if (r0 == 0) goto L_0x0007
            r0 = r1
            goto L_0x0008
        L_0x0007:
            r0 = r8
        L_0x0008:
            r2 = r14 & 4
            if (r2 == 0) goto L_0x000e
            r2 = r1
            goto L_0x000f
        L_0x000e:
            r2 = r9
        L_0x000f:
            r3 = r14 & 8
            if (r3 == 0) goto L_0x0015
            r3 = r1
            goto L_0x0016
        L_0x0015:
            r3 = r10
        L_0x0016:
            r4 = r14 & 16
            if (r4 == 0) goto L_0x001c
            r4 = r1
            goto L_0x001d
        L_0x001c:
            r4 = r11
        L_0x001d:
            r5 = r14 & 32
            if (r5 == 0) goto L_0x0022
            goto L_0x0023
        L_0x0022:
            r1 = r12
        L_0x0023:
            r5 = r14 & 64
            if (r5 == 0) goto L_0x002a
            java.lang.String r5 = "conversation:start"
            goto L_0x002b
        L_0x002a:
            r5 = r13
        L_0x002b:
            r8 = r6
            r9 = r7
            r10 = r0
            r11 = r2
            r12 = r3
            r13 = r4
            r14 = r1
            r15 = r5
            r8.<init>(r9, r10, r11, r12, r13, r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.rest.model.AppUserRequestDto.<init>(zendesk.conversationkit.android.internal.rest.model.ClientDto, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final ClientDto a() {
        return this.a;
    }

    @Nullable
    public final String g() {
        return this.b;
    }

    @Nullable
    public final String c() {
        return this.c;
    }

    @Nullable
    public final String f() {
        return this.d;
    }

    @Nullable
    public final String b() {
        return this.e;
    }

    @Nullable
    public final Map<String, Object> e() {
        return this.f;
    }

    @NotNull
    public final String d() {
        return this.g;
    }
}
