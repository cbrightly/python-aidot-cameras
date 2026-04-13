package zendesk.conversationkit.android.model;

import com.squareup.moshi.g;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.f;

@g(generateAdapter = true)
/* compiled from: User.kt */
public final class User {
    @NotNull
    private final String a;
    @Nullable
    private final String b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;
    @Nullable
    private final String e;
    @Nullable
    private final String f;
    @Nullable
    private final String g;
    @NotNull
    private final List<Conversation> h;
    @NotNull
    private final RealtimeSettings i;
    @NotNull
    private final TypingSettings j;
    @Nullable
    private final String k;
    @Nullable
    private final String l;

    public static /* synthetic */ User b(User user, String str, String str2, String str3, String str4, String str5, String str6, String str7, List list, RealtimeSettings realtimeSettings, TypingSettings typingSettings, String str8, String str9, int i2, Object obj) {
        User user2 = user;
        int i3 = i2;
        return user.a((i3 & 1) != 0 ? user2.a : str, (i3 & 2) != 0 ? user2.b : str2, (i3 & 4) != 0 ? user2.c : str3, (i3 & 8) != 0 ? user2.d : str4, (i3 & 16) != 0 ? user2.e : str5, (i3 & 32) != 0 ? user2.f : str6, (i3 & 64) != 0 ? user2.g : str7, (i3 & 128) != 0 ? user2.h : list, (i3 & 256) != 0 ? user2.i : realtimeSettings, (i3 & 512) != 0 ? user2.j : typingSettings, (i3 & 1024) != 0 ? user2.k : str8, (i3 & 2048) != 0 ? user2.l : str9);
    }

    @NotNull
    public final User a(@NotNull String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @NotNull List<Conversation> list, @NotNull RealtimeSettings realtimeSettings, @NotNull TypingSettings typingSettings, @Nullable String str8, @Nullable String str9) {
        k.e(str, "id");
        k.e(list, "conversations");
        k.e(realtimeSettings, "realtimeSettings");
        k.e(typingSettings, "typingSettings");
        return new User(str, str2, str3, str4, str5, str6, str7, list, realtimeSettings, typingSettings, str8, str9);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return k.a(this.a, user.a) && k.a(this.b, user.b) && k.a(this.c, user.c) && k.a(this.d, user.d) && k.a(this.e, user.e) && k.a(this.f, user.f) && k.a(this.g, user.g) && k.a(this.h, user.h) && k.a(this.i, user.i) && k.a(this.j, user.j) && k.a(this.k, user.k) && k.a(this.l, user.l);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        int i2 = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.c;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.d;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.e;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.f;
        int hashCode6 = (hashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.g;
        int hashCode7 = (((((((hashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31) + this.h.hashCode()) * 31) + this.i.hashCode()) * 31) + this.j.hashCode()) * 31;
        String str7 = this.k;
        int hashCode8 = (hashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.l;
        if (str8 != null) {
            i2 = str8.hashCode();
        }
        return hashCode8 + i2;
    }

    @NotNull
    public String toString() {
        return "User(id=" + this.a + ", externalId=" + this.b + ", givenName=" + this.c + ", surname=" + this.d + ", email=" + this.e + ", locale=" + this.f + ", signedUpAt=" + this.g + ", conversations=" + this.h + ", realtimeSettings=" + this.i + ", typingSettings=" + this.j + ", sessionToken=" + this.k + ", jwt=" + this.l + ')';
    }

    public User(@NotNull String id, @Nullable String externalId, @Nullable String givenName, @Nullable String surname, @Nullable String email, @Nullable String locale, @Nullable String signedUpAt, @NotNull List<Conversation> conversations, @NotNull RealtimeSettings realtimeSettings, @NotNull TypingSettings typingSettings, @Nullable String sessionToken, @Nullable String jwt) {
        k.e(id, "id");
        k.e(conversations, "conversations");
        k.e(realtimeSettings, "realtimeSettings");
        k.e(typingSettings, "typingSettings");
        this.a = id;
        this.b = externalId;
        this.c = givenName;
        this.d = surname;
        this.e = email;
        this.f = locale;
        this.g = signedUpAt;
        this.h = conversations;
        this.i = realtimeSettings;
        this.j = typingSettings;
        this.k = sessionToken;
        this.l = jwt;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ User(java.lang.String r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, java.lang.String r22, java.lang.String r23, java.util.List r24, zendesk.conversationkit.android.model.RealtimeSettings r25, zendesk.conversationkit.android.model.TypingSettings r26, java.lang.String r27, java.lang.String r28, int r29, kotlin.jvm.internal.DefaultConstructorMarker r30) {
        /*
            r16 = this;
            r0 = r29
            r1 = r0 & 1024(0x400, float:1.435E-42)
            r2 = 0
            if (r1 == 0) goto L_0x0009
            r14 = r2
            goto L_0x000b
        L_0x0009:
            r14 = r27
        L_0x000b:
            r0 = r0 & 2048(0x800, float:2.87E-42)
            if (r0 == 0) goto L_0x0011
            r15 = r2
            goto L_0x0013
        L_0x0011:
            r15 = r28
        L_0x0013:
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            r7 = r20
            r8 = r21
            r9 = r22
            r10 = r23
            r11 = r24
            r12 = r25
            r13 = r26
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.model.User.<init>(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List, zendesk.conversationkit.android.model.RealtimeSettings, zendesk.conversationkit.android.model.TypingSettings, java.lang.String, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final String h() {
        return this.a;
    }

    @Nullable
    public final String f() {
        return this.b;
    }

    @Nullable
    public final String g() {
        return this.c;
    }

    @Nullable
    public final String n() {
        return this.d;
    }

    @Nullable
    public final String e() {
        return this.e;
    }

    @Nullable
    public final String j() {
        return this.f;
    }

    @Nullable
    public final String m() {
        return this.g;
    }

    @NotNull
    public final List<Conversation> d() {
        return this.h;
    }

    @NotNull
    public final RealtimeSettings k() {
        return this.i;
    }

    @NotNull
    public final TypingSettings o() {
        return this.j;
    }

    @Nullable
    public final String l() {
        return this.k;
    }

    @Nullable
    public final String i() {
        return this.l;
    }

    @NotNull
    public final f c() {
        String str = this.l;
        if (str != null) {
            return new f.a(str);
        }
        String str2 = this.k;
        if (str2 != null) {
            return new f.b(str2);
        }
        return f.c.a;
    }
}
