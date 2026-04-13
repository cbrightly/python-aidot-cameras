package zendesk.messaging.android.internal.conversationscreen;

import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Field;
import zendesk.messaging.android.internal.model.b;

/* compiled from: ConversationScreenAction.kt */
public abstract class e {
    public /* synthetic */ e(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private e() {
    }

    /* compiled from: ConversationScreenAction.kt */
    public static final class g extends e {
        @NotNull
        private final String a;
        @Nullable
        private final String b;
        @Nullable
        private final Map<String, Object> c;
        @NotNull
        private final String d;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof g)) {
                return false;
            }
            g gVar = (g) obj;
            return k.a(this.a, gVar.a) && k.a(this.b, gVar.b) && k.a(this.c, gVar.c) && k.a(this.d, gVar.d);
        }

        public int hashCode() {
            int hashCode = this.a.hashCode() * 31;
            String str = this.b;
            int i = 0;
            int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
            Map<String, Object> map = this.c;
            if (map != null) {
                i = map.hashCode();
            }
            return ((hashCode2 + i) * 31) + this.d.hashCode();
        }

        @NotNull
        public String toString() {
            return "SendTextMessage(textMessage=" + this.a + ", payload=" + this.b + ", metadata=" + this.c + ", conversationId=" + this.d + ')';
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ g(String str, String str2, Map map, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : map, str3);
        }

        @NotNull
        public final String d() {
            return this.a;
        }

        @Nullable
        public final String c() {
            return this.b;
        }

        @Nullable
        public final Map<String, Object> b() {
            return this.c;
        }

        @NotNull
        public final String a() {
            return this.d;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public g(@NotNull String textMessage, @Nullable String payload, @Nullable Map<String, ? extends Object> metadata, @NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            k.e(textMessage, "textMessage");
            k.e(conversationId, "conversationId");
            this.a = textMessage;
            this.b = payload;
            this.c = metadata;
            this.d = conversationId;
        }
    }

    /* compiled from: ConversationScreenAction.kt */
    public static final class i extends e {
        @NotNull
        private final List<zendesk.messaging.android.internal.model.h> a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof i)) {
                return false;
            }
            i iVar = (i) obj;
            return k.a(this.a, iVar.a) && k.a(this.b, iVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "UploadFiles(uploads=" + this.a + ", conversationId=" + this.b + ')';
        }

        @NotNull
        public final List<zendesk.messaging.android.internal.model.h> b() {
            return this.a;
        }

        @NotNull
        public final String a() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public i(@NotNull List<zendesk.messaging.android.internal.model.h> uploads, @NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            k.e(uploads, "uploads");
            k.e(conversationId, "conversationId");
            this.a = uploads;
            this.b = conversationId;
        }
    }

    /* compiled from: ConversationScreenAction.kt */
    public static final class c extends e {
        @NotNull
        private final b.a a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return k.a(this.a, cVar.a) && k.a(this.b, cVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "ResendFailedMessage(failedMessageContainer=" + this.a + ", conversationId=" + this.b + ')';
        }

        @NotNull
        public final b.a b() {
            return this.a;
        }

        @NotNull
        public final String a() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@NotNull b.a failedMessageContainer, @NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            k.e(failedMessageContainer, "failedMessageContainer");
            k.e(conversationId, "conversationId");
            this.a = failedMessageContainer;
            this.b = conversationId;
        }
    }

    /* compiled from: ConversationScreenAction.kt */
    public static final class f extends e {
        @NotNull
        private final List<Field> a;
        @NotNull
        private final b.a b;
        @NotNull
        private final String c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof f)) {
                return false;
            }
            f fVar = (f) obj;
            return k.a(this.a, fVar.a) && k.a(this.b, fVar.b) && k.a(this.c, fVar.c);
        }

        public int hashCode() {
            return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
        }

        @NotNull
        public String toString() {
            return "SendFormResponse(fields=" + this.a + ", formMessageContainer=" + this.b + ", conversationId=" + this.c + ')';
        }

        @NotNull
        public final List<Field> b() {
            return this.a;
        }

        @NotNull
        public final b.a c() {
            return this.b;
        }

        @NotNull
        public final String a() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public f(@NotNull List<? extends Field> fields, @NotNull b.a formMessageContainer, @NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            k.e(fields, "fields");
            k.e(formMessageContainer, "formMessageContainer");
            k.e(conversationId, "conversationId");
            this.a = fields;
            this.b = formMessageContainer;
            this.c = conversationId;
        }
    }

    /* renamed from: zendesk.messaging.android.internal.conversationscreen.e$e  reason: collision with other inner class name */
    /* compiled from: ConversationScreenAction.kt */
    public static final class C0526e extends e {
        @NotNull
        private final zendesk.conversationkit.android.model.a a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C0526e)) {
                return false;
            }
            C0526e eVar = (C0526e) obj;
            return this.a == eVar.a && k.a(this.b, eVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "SendActivityData(activityData=" + this.a + ", conversationId=" + this.b + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.model.a a() {
            return this.a;
        }

        @NotNull
        public final String b() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C0526e(@NotNull zendesk.conversationkit.android.model.a activityData, @NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            k.e(activityData, "activityData");
            k.e(conversationId, "conversationId");
            this.a = activityData;
            this.b = conversationId;
        }
    }

    /* compiled from: ConversationScreenAction.kt */
    public static final class d extends e {
        @NotNull
        public static final d a = new d();

        private d() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ConversationScreenAction.kt */
    public static final class a extends e {
        @NotNull
        public static final a a = new a();

        private a() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ConversationScreenAction.kt */
    public static final class h extends e {
        @NotNull
        public static final h a = new h();

        private h() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ConversationScreenAction.kt */
    public static final class b extends e {
        @NotNull
        private final String a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return k.a(this.a, bVar.a) && k.a(this.b, bVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "PersistComposerText(conversationId=" + this.a + ", composerText=" + this.b + ')';
        }

        @NotNull
        public final String b() {
            return this.a;
        }

        @NotNull
        public final String a() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull String conversationId, @NotNull String composerText) {
            super((DefaultConstructorMarker) null);
            k.e(conversationId, "conversationId");
            k.e(composerText, "composerText");
            this.a = conversationId;
            this.b = composerText;
        }
    }
}
