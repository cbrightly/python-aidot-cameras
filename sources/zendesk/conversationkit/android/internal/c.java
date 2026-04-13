package zendesk.conversationkit.android.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.User;

/* compiled from: Action.kt */
public abstract class c {
    public /* synthetic */ c(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private c() {
    }

    /* compiled from: Action.kt */
    public static final class u extends c {
        @NotNull
        private final zendesk.conversationkit.android.i a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof u) && kotlin.jvm.internal.k.a(this.a, ((u) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "Setup(conversationKitSettings=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public u(@NotNull zendesk.conversationkit.android.i conversationKitSettings) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
            this.a = conversationKitSettings;
        }

        @NotNull
        public final zendesk.conversationkit.android.i a() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class v extends c {
        @NotNull
        private final zendesk.conversationkit.android.i a;
        @NotNull
        private final zendesk.conversationkit.android.model.h b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof v)) {
                return false;
            }
            v vVar = (v) obj;
            return kotlin.jvm.internal.k.a(this.a, vVar.a) && kotlin.jvm.internal.k.a(this.b, vVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "SetupWithConfig(conversationKitSettings=" + this.a + ", config=" + this.b + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.i b() {
            return this.a;
        }

        @NotNull
        public final zendesk.conversationkit.android.model.h a() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public v(@NotNull zendesk.conversationkit.android.i conversationKitSettings, @NotNull zendesk.conversationkit.android.model.h config) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
            kotlin.jvm.internal.k.e(config, "config");
            this.a = conversationKitSettings;
            this.b = config;
        }
    }

    /* compiled from: Action.kt */
    public static final class f extends c {
        @NotNull
        public static final f a = new f();

        private f() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: Action.kt */
    public static final class e extends c {
        @NotNull
        private final zendesk.conversationkit.android.model.h a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof e) && kotlin.jvm.internal.k.a(this.a, ((e) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "ForwardConfig(config=" + this.a + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.model.h a() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public e(@NotNull zendesk.conversationkit.android.model.h config) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(config, "config");
            this.a = config;
        }
    }

    /* compiled from: Action.kt */
    public static final class p extends c {
        @NotNull
        private final zendesk.conversationkit.android.a a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof p) && this.a == ((p) obj).a;
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "RealtimeConnectionStatusUpdate(connectionStatus=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public p(@NotNull zendesk.conversationkit.android.a connectionStatus) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(connectionStatus, "connectionStatus");
            this.a = connectionStatus;
        }

        @NotNull
        public final zendesk.conversationkit.android.a a() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class j extends c {
        @NotNull
        private final String a;
        @NotNull
        private final Message b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof j)) {
                return false;
            }
            j jVar = (j) obj;
            return kotlin.jvm.internal.k.a(this.a, jVar.a) && kotlin.jvm.internal.k.a(this.b, jVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "MessageReceived(conversationId=" + this.a + ", message=" + this.b + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public j(@NotNull String conversationId, @NotNull Message message) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            kotlin.jvm.internal.k.e(message, "message");
            this.a = conversationId;
            this.b = message;
        }

        @NotNull
        public final String a() {
            return this.a;
        }

        @NotNull
        public final Message b() {
            return this.b;
        }
    }

    /* compiled from: Action.kt */
    public static final class k extends c {
        @NotNull
        private final zendesk.conversationkit.android.a a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof k) && this.a == ((k) obj).a;
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "NetworkConnectionStatusUpdate(connectionStatus=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public k(@NotNull zendesk.conversationkit.android.a connectionStatus) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(connectionStatus, "connectionStatus");
            this.a = connectionStatus;
        }

        @NotNull
        public final zendesk.conversationkit.android.a a() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class d extends c {
        @NotNull
        public static final d a = new d();

        private d() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: Action.kt */
    public static final class b extends c {
        @NotNull
        public static final b a = new b();

        private b() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: Action.kt */
    public static final class w extends c {
        @NotNull
        public static final w a = new w();

        private w() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: Action.kt */
    public static final class l extends c {
        @NotNull
        public static final l a = new l();

        private l() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: Action.kt */
    public static final class r extends c {
        @NotNull
        public static final r a = new r();

        private r() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: Action.kt */
    public static final class h extends c {
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
            return "LoginUser(jwt=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public h(@NotNull String jwt) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(jwt, "jwt");
            this.a = jwt;
        }

        @NotNull
        public final String a() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class i extends c {
        @NotNull
        public static final i a = new i();

        private i() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* renamed from: zendesk.conversationkit.android.internal.c$c  reason: collision with other inner class name */
    /* compiled from: Action.kt */
    public static final class C0507c extends c {
        @NotNull
        public static final C0507c a = new C0507c();

        private C0507c() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: Action.kt */
    public static final class g extends c {
        @NotNull
        private final String a;

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
            return "GetConversation(conversationId=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public g(@NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            this.a = conversationId;
        }

        @NotNull
        public final String a() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class q extends c {
        @NotNull
        private final String a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof q) && kotlin.jvm.internal.k.a(this.a, ((q) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "RefreshConversation(conversationId=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public q(@NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            this.a = conversationId;
        }

        @NotNull
        public final String a() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class n extends c {
        @NotNull
        private final Message a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof n)) {
                return false;
            }
            n nVar = (n) obj;
            return kotlin.jvm.internal.k.a(this.a, nVar.a) && kotlin.jvm.internal.k.a(this.b, nVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "PrepareMessage(message=" + this.a + ", conversationId=" + this.b + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public n(@NotNull Message message, @NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(message, "message");
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            this.a = message;
            this.b = conversationId;
        }

        @NotNull
        public final String a() {
            return this.b;
        }

        @NotNull
        public final Message b() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class t extends c {
        @NotNull
        private final Message a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof t)) {
                return false;
            }
            t tVar = (t) obj;
            return kotlin.jvm.internal.k.a(this.a, tVar.a) && kotlin.jvm.internal.k.a(this.b, tVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "SendMessage(message=" + this.a + ", conversationId=" + this.b + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public t(@NotNull Message message, @NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(message, "message");
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            this.a = message;
            this.b = conversationId;
        }

        @NotNull
        public final String a() {
            return this.b;
        }

        @NotNull
        public final Message b() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class o extends c {
        @NotNull
        private final String a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof o) && kotlin.jvm.internal.k.a(this.a, ((o) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "PreparePushToken(pushToken=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public o(@NotNull String pushToken) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(pushToken, "pushToken");
            this.a = pushToken;
        }

        @NotNull
        public final String a() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class y extends c {
        @NotNull
        private final String a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof y) && kotlin.jvm.internal.k.a(this.a, ((y) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "UpdatePushToken(pushToken=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public y(@NotNull String pushToken) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(pushToken, "pushToken");
            this.a = pushToken;
        }

        @NotNull
        public final String a() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class s extends c {
        @NotNull
        private final zendesk.conversationkit.android.model.a a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof s)) {
                return false;
            }
            s sVar = (s) obj;
            return this.a == sVar.a && kotlin.jvm.internal.k.a(this.b, sVar.b);
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
        public s(@NotNull zendesk.conversationkit.android.model.a activityData, @NotNull String conversationId) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(activityData, "activityData");
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            this.a = activityData;
            this.b = conversationId;
        }
    }

    /* compiled from: Action.kt */
    public static final class a extends c {
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

    /* compiled from: Action.kt */
    public static final class x extends c {
        @NotNull
        private final String a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof x) && kotlin.jvm.internal.k.a(this.a, ((x) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "UpdateAppUserLocale(deviceLocale=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public x(@NotNull String deviceLocale) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(deviceLocale, "deviceLocale");
            this.a = deviceLocale;
        }

        @NotNull
        public final String a() {
            return this.a;
        }
    }

    /* compiled from: Action.kt */
    public static final class m extends c {
        @NotNull
        private final User a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof m) && kotlin.jvm.internal.k.a(this.a, ((m) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "PersistedUserRetrieve(user=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public m(@NotNull User user) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(user, "user");
            this.a = user;
        }

        @NotNull
        public final User a() {
            return this.a;
        }
    }
}
