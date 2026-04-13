package zendesk.conversationkit.android.model;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.moshi.g;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: Message.kt */
public final class Message {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final String b;
    @NotNull
    private final Author c;
    @NotNull
    private final u d;
    @Nullable
    private final Date e;
    @NotNull
    private final Date f;
    @NotNull
    private final MessageContent g;
    @Nullable
    private final Map<String, Object> h;
    @Nullable
    private final String i;
    @NotNull
    private final String j;
    @Nullable
    private final String k;

    public static /* synthetic */ Message b(Message message, String str, Author author, u uVar, Date date, Date date2, MessageContent messageContent, Map map, String str2, String str3, String str4, int i2, Object obj) {
        Message message2 = message;
        int i3 = i2;
        return message.a((i3 & 1) != 0 ? message2.b : str, (i3 & 2) != 0 ? message2.c : author, (i3 & 4) != 0 ? message2.d : uVar, (i3 & 8) != 0 ? message2.e : date, (i3 & 16) != 0 ? message2.f : date2, (i3 & 32) != 0 ? message2.g : messageContent, (i3 & 64) != 0 ? message2.h : map, (i3 & 128) != 0 ? message2.i : str2, (i3 & 256) != 0 ? message2.j : str3, (i3 & 512) != 0 ? message2.k : str4);
    }

    @NotNull
    public final Message a(@NotNull String str, @NotNull Author author, @NotNull u uVar, @Nullable Date date, @NotNull Date date2, @NotNull MessageContent messageContent, @Nullable Map<String, ? extends Object> map, @Nullable String str2, @NotNull String str3, @Nullable String str4) {
        k.e(str, "id");
        k.e(author, "author");
        k.e(uVar, "status");
        k.e(date2, "received");
        k.e(messageContent, FirebaseAnalytics.Param.CONTENT);
        String str5 = str3;
        k.e(str5, "localId");
        return new Message(str, author, uVar, date, date2, messageContent, map, str2, str5, str4);
    }

    public Message(@NotNull String id, @NotNull Author author, @NotNull u status, @Nullable Date created, @NotNull Date received, @NotNull MessageContent content, @Nullable Map<String, ? extends Object> metadata, @Nullable String sourceId, @NotNull String localId, @Nullable String payload) {
        k.e(id, "id");
        k.e(author, "author");
        k.e(status, "status");
        k.e(received, "received");
        k.e(content, FirebaseAnalytics.Param.CONTENT);
        k.e(localId, "localId");
        this.b = id;
        this.c = author;
        this.d = status;
        this.e = created;
        this.f = received;
        this.g = content;
        this.h = metadata;
        this.i = sourceId;
        this.j = localId;
        this.k = payload;
    }

    @NotNull
    public final String g() {
        return this.b;
    }

    @NotNull
    public final Author c() {
        return this.c;
    }

    @NotNull
    public final u m() {
        return this.d;
    }

    @Nullable
    public final Date e() {
        return this.e;
    }

    @NotNull
    public final Date k() {
        return this.f;
    }

    @NotNull
    public final MessageContent d() {
        return this.g;
    }

    @Nullable
    public final Map<String, Object> i() {
        return this.h;
    }

    @Nullable
    public final String l() {
        return this.i;
    }

    @NotNull
    public final String h() {
        return this.j;
    }

    @Nullable
    public final String j() {
        return this.k;
    }

    @NotNull
    public final l f() {
        return new l(this);
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof Message) && k.a(f(), ((Message) other).f());
    }

    public int hashCode() {
        return f().hashCode();
    }

    @NotNull
    public String toString() {
        return w.J(f().toString(), "EssentialMessageData", "Message", false, 4, (Object) null);
    }

    public final boolean n(@Nullable Participant participant) {
        return k.a(this.c.f(), participant == null ? null : participant.f());
    }

    /* compiled from: Message.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        public static /* synthetic */ Message b(a aVar, MessageContent messageContent, Map map, String str, int i, Object obj) {
            if ((i & 2) != 0) {
                map = null;
            }
            if ((i & 4) != 0) {
                str = null;
            }
            return aVar.a(messageContent, map, str);
        }

        @NotNull
        public final Message a(@NotNull MessageContent content, @Nullable Map<String, ? extends Object> metadata, @Nullable String payload) {
            k.e(content, FirebaseAnalytics.Param.CONTENT);
            String randomId = UUID.randomUUID().toString();
            k.d(randomId, "randomUUID().toString()");
            return new Message(randomId, new Author((String) null, (g) null, (String) null, (String) null, 15, (DefaultConstructorMarker) null), u.PENDING, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), content, metadata, (String) null, randomId, payload);
        }
    }
}
