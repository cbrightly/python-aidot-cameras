package zendesk.conversationkit.android;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.User;

/* compiled from: ConversationKitEvent.kt */
public abstract class d {
    public /* synthetic */ d(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private d() {
    }

    /* compiled from: ConversationKitEvent.kt */
    public static final class b extends d {
        @NotNull
        private final a a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && this.a == ((b) obj).a;
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "ConnectionStatusChanged(connectionStatus=" + this.a + ')';
        }

        @NotNull
        public final a a() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull a connectionStatus) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(connectionStatus, "connectionStatus");
            this.a = connectionStatus;
        }
    }

    /* compiled from: ConversationKitEvent.kt */
    public static final class j extends d {
        @NotNull
        private final Throwable a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof j) && kotlin.jvm.internal.k.a(this.a, ((j) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "UserAccessRevoked(cause=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public j(@NotNull Throwable cause) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(cause, "cause");
            this.a = cause;
        }

        @NotNull
        public final Throwable a() {
            return this.a;
        }
    }

    /* compiled from: ConversationKitEvent.kt */
    public static final class k extends d {
        @NotNull
        private final User a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof k) && kotlin.jvm.internal.k.a(this.a, ((k) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "UserUpdated(user=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public k(@NotNull User user) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(user, "user");
            this.a = user;
        }

        @NotNull
        public final User a() {
            return this.a;
        }
    }

    /* renamed from: zendesk.conversationkit.android.d$d  reason: collision with other inner class name */
    /* compiled from: ConversationKitEvent.kt */
    public static final class C0504d extends d {
        @NotNull
        private final g<x> a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof C0504d) && kotlin.jvm.internal.k.a(this.a, ((C0504d) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "LogoutUserCompleted(result=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C0504d(@NotNull g<x> result) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(result, "result");
            this.a = result;
        }
    }

    /* compiled from: ConversationKitEvent.kt */
    public static final class e extends d {
        @NotNull
        private final Message a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof e)) {
                return false;
            }
            e eVar = (e) obj;
            return kotlin.jvm.internal.k.a(this.a, eVar.a) && kotlin.jvm.internal.k.a(this.b, eVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "MessageReceived(message=" + this.a + ", conversationId=" + this.b + ')';
        }

        @NotNull
        public final String a() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public e(@NotNull Message message, @NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(message, "message");
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            this.a = message;
            this.b = conversationId;
        }
    }

    /* compiled from: ConversationKitEvent.kt */
    public static final class f extends d {
        @NotNull
        private final Message a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof f)) {
                return false;
            }
            f fVar = (f) obj;
            return kotlin.jvm.internal.k.a(this.a, fVar.a) && kotlin.jvm.internal.k.a(this.b, fVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "MessageUpdated(message=" + this.a + ", conversationId=" + this.b + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public f(@NotNull Message message, @NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(message, "message");
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            this.a = message;
            this.b = conversationId;
        }
    }

    /* compiled from: ConversationKitEvent.kt */
    public static final class c extends d {
        @NotNull
        private final Conversation a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof c) && kotlin.jvm.internal.k.a(this.a, ((c) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "ConversationUpdated(conversation=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@NotNull Conversation conversation) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversation, "conversation");
            this.a = conversation;
        }

        @NotNull
        public final Conversation a() {
            return this.a;
        }
    }

    /* compiled from: ConversationKitEvent.kt */
    public static final class h extends d {
        @NotNull
        private final String a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof h) && kotlin.jvm.internal.k.a(this.a, ((h) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "PushTokenPrepared(pushNotificationToken=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public h(@NotNull String pushNotificationToken) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(pushNotificationToken, "pushNotificationToken");
            this.a = pushNotificationToken;
        }
    }

    /* compiled from: ConversationKitEvent.kt */
    public static final class i extends d {
        @NotNull
        private final g<x> a;
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
            return kotlin.jvm.internal.k.a(this.a, iVar.a) && kotlin.jvm.internal.k.a(this.b, iVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "PushTokenUpdateResult(result=" + this.a + ", pushNotificationToken=" + this.b + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public i(@NotNull g<x> result, @NotNull String pushNotificationToken) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(result, "result");
            kotlin.jvm.internal.k.e(pushNotificationToken, "pushNotificationToken");
            this.a = result;
            this.b = pushNotificationToken;
        }
    }

    /* compiled from: ConversationKitEvent.kt */
    public static final class a extends d {
        @NotNull
        private final zendesk.conversationkit.android.model.c a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && kotlin.jvm.internal.k.a(this.a, ((a) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "ActivityEventReceived(activityEvent=" + this.a + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.model.c a() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull zendesk.conversationkit.android.model.c activityEvent) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(activityEvent, "activityEvent");
            this.a = activityEvent;
        }
    }

    /* compiled from: ConversationKitEvent.kt */
    public static final class g extends d {
        @NotNull
        private final User a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof g) && kotlin.jvm.internal.k.a(this.a, ((g) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "PersistedUserReceived(user=" + this.a + ')';
        }

        @NotNull
        public final User a() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public g(@NotNull User user) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(user, "user");
            this.a = user;
        }
    }
}
