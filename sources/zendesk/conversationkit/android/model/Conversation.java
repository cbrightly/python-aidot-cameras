package zendesk.conversationkit.android.model;

import com.squareup.moshi.g;
import java.util.Date;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@g(generateAdapter = true)
/* compiled from: Conversation.kt */
public final class Conversation {
    @NotNull
    private final String a;
    @Nullable
    private final String b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;
    @NotNull
    private final k e;
    private final boolean f;
    @NotNull
    private final List<String> g;
    @Nullable
    private final Date h;
    @Nullable
    private final Double i;
    @Nullable
    private final Participant j;
    @NotNull
    private final List<Participant> k;
    @NotNull
    private final List<Message> l;
    private final boolean m;

    public static /* synthetic */ Conversation b(Conversation conversation, String str, String str2, String str3, String str4, k kVar, boolean z, List list, Date date, Double d2, Participant participant, List list2, List list3, boolean z2, int i2, Object obj) {
        Conversation conversation2 = conversation;
        int i3 = i2;
        return conversation.a((i3 & 1) != 0 ? conversation2.a : str, (i3 & 2) != 0 ? conversation2.b : str2, (i3 & 4) != 0 ? conversation2.c : str3, (i3 & 8) != 0 ? conversation2.d : str4, (i3 & 16) != 0 ? conversation2.e : kVar, (i3 & 32) != 0 ? conversation2.f : z, (i3 & 64) != 0 ? conversation2.g : list, (i3 & 128) != 0 ? conversation2.h : date, (i3 & 256) != 0 ? conversation2.i : d2, (i3 & 512) != 0 ? conversation2.j : participant, (i3 & 1024) != 0 ? conversation2.k : list2, (i3 & 2048) != 0 ? conversation2.l : list3, (i3 & 4096) != 0 ? conversation2.m : z2);
    }

    @NotNull
    public final Conversation a(@NotNull String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @NotNull k kVar, boolean z, @NotNull List<String> list, @Nullable Date date, @Nullable Double d2, @Nullable Participant participant, @NotNull List<Participant> list2, @NotNull List<Message> list3, boolean z2) {
        k.e(str, "id");
        k.e(kVar, IjkMediaMeta.IJKM_KEY_TYPE);
        k.e(list, "business");
        List<Participant> list4 = list2;
        k.e(list4, "participants");
        k.e(list3, "messages");
        return new Conversation(str, str2, str3, str4, kVar, z, list, date, d2, participant, list4, list3, z2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Conversation)) {
            return false;
        }
        Conversation conversation = (Conversation) obj;
        return k.a(this.a, conversation.a) && k.a(this.b, conversation.b) && k.a(this.c, conversation.c) && k.a(this.d, conversation.d) && this.e == conversation.e && this.f == conversation.f && k.a(this.g, conversation.g) && k.a(this.h, conversation.h) && k.a(this.i, conversation.i) && k.a(this.j, conversation.j) && k.a(this.k, conversation.k) && k.a(this.l, conversation.l) && this.m == conversation.m;
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        int i2 = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.c;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.d;
        int hashCode4 = (((hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.e.hashCode()) * 31;
        boolean z = this.f;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int hashCode5 = (((hashCode4 + (z ? 1 : 0)) * 31) + this.g.hashCode()) * 31;
        Date date = this.h;
        int hashCode6 = (hashCode5 + (date == null ? 0 : date.hashCode())) * 31;
        Double d2 = this.i;
        int hashCode7 = (hashCode6 + (d2 == null ? 0 : d2.hashCode())) * 31;
        Participant participant = this.j;
        if (participant != null) {
            i2 = participant.hashCode();
        }
        int hashCode8 = (((((hashCode7 + i2) * 31) + this.k.hashCode()) * 31) + this.l.hashCode()) * 31;
        boolean z3 = this.m;
        if (!z3) {
            z2 = z3;
        }
        return hashCode8 + (z2 ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "Conversation(id=" + this.a + ", displayName=" + this.b + ", description=" + this.c + ", iconUrl=" + this.d + ", type=" + this.e + ", isDefault=" + this.f + ", business=" + this.g + ", businessLastRead=" + this.h + ", lastUpdatedAt=" + this.i + ", myself=" + this.j + ", participants=" + this.k + ", messages=" + this.l + ", hasPrevious=" + this.m + ')';
    }

    public Conversation(@NotNull String id, @Nullable String displayName, @Nullable String description, @Nullable String iconUrl, @NotNull k type, boolean isDefault, @NotNull List<String> business, @Nullable Date businessLastRead, @Nullable Double lastUpdatedAt, @Nullable Participant myself, @NotNull List<Participant> participants, @NotNull List<Message> messages, boolean hasPrevious) {
        k.e(id, "id");
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        k.e(business, "business");
        k.e(participants, "participants");
        k.e(messages, "messages");
        this.a = id;
        this.b = displayName;
        this.c = description;
        this.d = iconUrl;
        this.e = type;
        this.f = isDefault;
        this.g = business;
        this.h = businessLastRead;
        this.i = lastUpdatedAt;
        this.j = myself;
        this.k = participants;
        this.l = messages;
        this.m = hasPrevious;
    }

    @NotNull
    public final String i() {
        return this.a;
    }

    @Nullable
    public final String f() {
        return this.b;
    }

    @Nullable
    public final String e() {
        return this.c;
    }

    @Nullable
    public final String h() {
        return this.d;
    }

    @NotNull
    public final k n() {
        return this.e;
    }

    public final boolean o() {
        return this.f;
    }

    @NotNull
    public final List<String> c() {
        return this.g;
    }

    @Nullable
    public final Date d() {
        return this.h;
    }

    @Nullable
    public final Double j() {
        return this.i;
    }

    @Nullable
    public final Participant l() {
        return this.j;
    }

    @NotNull
    public final List<Participant> m() {
        return this.k;
    }

    @NotNull
    public final List<Message> k() {
        return this.l;
    }

    public final boolean g() {
        return this.m;
    }
}
