package zendesk.messaging.android.internal.model;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.MessageAction;
import zendesk.conversationkit.android.model.u;

/* compiled from: MessageLogEntry.kt */
public abstract class b {
    @NotNull
    private final String a;

    public /* synthetic */ b(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private b(String id) {
        this.a = id;
    }

    /* compiled from: MessageLogEntry.kt */
    public static final class a extends b {
        @NotNull
        private final String b;
        @Nullable
        private final String c;
        @Nullable
        private final String d;
        @NotNull
        private final a e;
        @NotNull
        private final c f;
        @NotNull
        private final e g;
        @NotNull
        private final u h;
        @NotNull
        private final Message i;
        @Nullable
        private final d j;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return k.a(c(), aVar.c()) && k.a(this.c, aVar.c) && k.a(this.d, aVar.d) && this.e == aVar.e && this.f == aVar.f && this.g == aVar.g && this.h == aVar.h && k.a(this.i, aVar.i) && k.a(this.j, aVar.j);
        }

        public int hashCode() {
            int hashCode = c().hashCode() * 31;
            String str = this.c;
            int i2 = 0;
            int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.d;
            int hashCode3 = (((((((((((hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31) + this.g.hashCode()) * 31) + this.h.hashCode()) * 31) + this.i.hashCode()) * 31;
            d dVar = this.j;
            if (dVar != null) {
                i2 = dVar.hashCode();
            }
            return hashCode3 + i2;
        }

        @NotNull
        public String toString() {
            return "MessageContainer(id=" + c() + ", label=" + this.c + ", avatarUrl=" + this.d + ", direction=" + this.e + ", position=" + this.f + ", shape=" + this.g + ", status=" + this.h + ", message=" + this.i + ", receipt=" + this.j + ')';
        }

        @NotNull
        public String c() {
            return this.b;
        }

        @Nullable
        public final String d() {
            return this.c;
        }

        @Nullable
        public final String a() {
            return this.d;
        }

        @NotNull
        public final a b() {
            return this.e;
        }

        @NotNull
        public final c f() {
            return this.f;
        }

        @NotNull
        public final e h() {
            return this.g;
        }

        @NotNull
        public final u i() {
            return this.h;
        }

        @NotNull
        public final Message e() {
            return this.i;
        }

        @Nullable
        public final d g() {
            return this.j;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull String id, @Nullable String label, @Nullable String avatarUrl, @NotNull a direction, @NotNull c position, @NotNull e shape, @NotNull u status, @NotNull Message message, @Nullable d receipt) {
            super(id, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(direction, "direction");
            k.e(position, "position");
            k.e(shape, "shape");
            k.e(status, "status");
            k.e(message, "message");
            this.b = id;
            this.c = label;
            this.d = avatarUrl;
            this.e = direction;
            this.f = position;
            this.g = shape;
            this.h = status;
            this.i = message;
            this.j = receipt;
        }
    }

    /* compiled from: MessageLogEntry.kt */
    public static final class d extends b {
        @NotNull
        private final String b;
        @NotNull
        private final String c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            return k.a(b(), dVar.b()) && k.a(this.c, dVar.c);
        }

        public int hashCode() {
            return (b().hashCode() * 31) + this.c.hashCode();
        }

        @NotNull
        public String toString() {
            return "TypingIndicator(id=" + b() + ", avatarUrl=" + this.c + ')';
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ d(java.lang.String r1, java.lang.String r2, int r3, kotlin.jvm.internal.DefaultConstructorMarker r4) {
            /*
                r0 = this;
                r3 = r3 & 1
                if (r3 == 0) goto L_0x0011
                java.util.UUID r1 = java.util.UUID.randomUUID()
                java.lang.String r1 = r1.toString()
                java.lang.String r3 = "randomUUID().toString()"
                kotlin.jvm.internal.k.d(r1, r3)
            L_0x0011:
                r0.<init>(r1, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.model.b.d.<init>(java.lang.String, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        @NotNull
        public String b() {
            return this.b;
        }

        @NotNull
        public final String a() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(@NotNull String id, @NotNull String avatarUrl) {
            super(id, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(avatarUrl, "avatarUrl");
            this.b = id;
            this.c = avatarUrl;
        }
    }

    /* compiled from: MessageLogEntry.kt */
    public static final class e extends b {
        @NotNull
        private final String b;
        @NotNull
        private final String c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof e)) {
                return false;
            }
            e eVar = (e) obj;
            return k.a(a(), eVar.a()) && k.a(this.c, eVar.c);
        }

        public int hashCode() {
            return (a().hashCode() * 31) + this.c.hashCode();
        }

        @NotNull
        public String toString() {
            return "UnreadMessagesDivider(id=" + a() + ", text=" + this.c + ')';
        }

        @NotNull
        public String a() {
            return this.b;
        }

        @NotNull
        public final String b() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public e(@NotNull String id, @NotNull String text) {
            super(id, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(text, "text");
            this.b = id;
            this.c = text;
        }
    }

    /* compiled from: MessageLogEntry.kt */
    public static abstract class c extends b {
        @NotNull
        private final String b;
        @NotNull
        private final String c;

        public /* synthetic */ c(String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2);
        }

        @NotNull
        public String a() {
            return this.c;
        }

        private c(String id, String timestamp) {
            super(id, (DefaultConstructorMarker) null);
            this.b = id;
            this.c = timestamp;
        }

        /* renamed from: zendesk.messaging.android.internal.model.b$c$b  reason: collision with other inner class name */
        /* compiled from: MessageLogEntry.kt */
        public static final class C0547b extends c {
            @NotNull
            private final String d;
            @NotNull
            private final String e;

            public boolean equals(@Nullable Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof C0547b)) {
                    return false;
                }
                C0547b bVar = (C0547b) obj;
                return k.a(b(), bVar.b()) && k.a(a(), bVar.a());
            }

            public int hashCode() {
                return (b().hashCode() * 31) + a().hashCode();
            }

            @NotNull
            public String toString() {
                return "MessageTimeDivider(id=" + b() + ", timestamp=" + a() + ')';
            }

            @NotNull
            public String b() {
                return this.d;
            }

            @NotNull
            public String a() {
                return this.e;
            }

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public C0547b(@NotNull String id, @NotNull String timestamp) {
                super(id, timestamp, (DefaultConstructorMarker) null);
                k.e(id, "id");
                k.e(timestamp, "timestamp");
                this.d = id;
                this.e = timestamp;
            }
        }

        /* compiled from: MessageLogEntry.kt */
        public static final class a extends c {
            @NotNull
            private final String d;
            @NotNull
            private final String e;

            public boolean equals(@Nullable Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof a)) {
                    return false;
                }
                a aVar = (a) obj;
                return k.a(b(), aVar.b()) && k.a(a(), aVar.a());
            }

            public int hashCode() {
                return (b().hashCode() * 31) + a().hashCode();
            }

            @NotNull
            public String toString() {
                return "MessageDayDivider(id=" + b() + ", timestamp=" + a() + ')';
            }

            @NotNull
            public String b() {
                return this.d;
            }

            @NotNull
            public String a() {
                return this.e;
            }

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(@NotNull String id, @NotNull String timestamp) {
                super(id, timestamp, (DefaultConstructorMarker) null);
                k.e(id, "id");
                k.e(timestamp, "timestamp");
                this.d = id;
                this.e = timestamp;
            }
        }
    }

    /* renamed from: zendesk.messaging.android.internal.model.b$b  reason: collision with other inner class name */
    /* compiled from: MessageLogEntry.kt */
    public static final class C0546b extends b {
        @NotNull
        private final String b;
        @NotNull
        private final List<MessageAction.Reply> c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C0546b)) {
                return false;
            }
            C0546b bVar = (C0546b) obj;
            return k.a(a(), bVar.a()) && k.a(this.c, bVar.c);
        }

        public int hashCode() {
            return (a().hashCode() * 31) + this.c.hashCode();
        }

        @NotNull
        public String toString() {
            return "QuickReply(id=" + a() + ", replies=" + this.c + ')';
        }

        @NotNull
        public String a() {
            return this.b;
        }

        @NotNull
        public final List<MessageAction.Reply> b() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C0546b(@NotNull String id, @NotNull List<MessageAction.Reply> replies) {
            super(id, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(replies, "replies");
            this.b = id;
            this.c = replies;
        }
    }
}
