package zendesk.conversationkit.android.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.g;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.User;

/* compiled from: Effect.kt */
public abstract class o {

    /* compiled from: Effect.kt */
    public interface e {
        @NotNull
        zendesk.conversationkit.android.a a();
    }

    public /* synthetic */ o(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private o() {
    }

    /* renamed from: zendesk.conversationkit.android.internal.o$o  reason: collision with other inner class name */
    /* compiled from: Effect.kt */
    public static final class C0512o extends o {
        @NotNull
        public static final C0512o a = new C0512o();

        private C0512o() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: Effect.kt */
    public static final class i extends o {
        @NotNull
        public static final i a = new i();

        private i() {
            super((DefaultConstructorMarker) null);
        }
    }

    /* compiled from: Effect.kt */
    public static final class y extends o {
        @NotNull
        private final zendesk.conversationkit.android.i a;
        @NotNull
        private final zendesk.conversationkit.android.model.h b;
        @NotNull
        private final zendesk.conversationkit.android.g<Object> c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof y)) {
                return false;
            }
            y yVar = (y) obj;
            return kotlin.jvm.internal.k.a(this.a, yVar.a) && kotlin.jvm.internal.k.a(this.b, yVar.b) && kotlin.jvm.internal.k.a(this.c, yVar.c);
        }

        public int hashCode() {
            return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
        }

        @NotNull
        public String toString() {
            return "UserAccessRevoked(conversationKitSettings=" + this.a + ", config=" + this.b + ", result=" + this.c + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.i c() {
            return this.a;
        }

        @NotNull
        public final zendesk.conversationkit.android.model.h b() {
            return this.b;
        }

        @NotNull
        public final zendesk.conversationkit.android.g<Object> d() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public y(@NotNull zendesk.conversationkit.android.i conversationKitSettings, @NotNull zendesk.conversationkit.android.model.h config, @NotNull zendesk.conversationkit.android.g<? extends Object> result) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
            kotlin.jvm.internal.k.e(config, "config");
            kotlin.jvm.internal.k.e(result, "result");
            this.a = conversationKitSettings;
            this.b = config;
            this.c = result;
        }
    }

    /* compiled from: Effect.kt */
    public static final class x extends o {
        @NotNull
        private final zendesk.conversationkit.android.i a;

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
            return "SettingsReceived(conversationKitSettings=" + this.a + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.i b() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public x(@NotNull zendesk.conversationkit.android.i conversationKitSettings) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
            this.a = conversationKitSettings;
        }
    }

    /* compiled from: Effect.kt */
    public static final class w extends o {
        @NotNull
        private final zendesk.conversationkit.android.i a;
        @NotNull
        private final zendesk.conversationkit.android.model.h b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof w)) {
                return false;
            }
            w wVar = (w) obj;
            return kotlin.jvm.internal.k.a(this.a, wVar.a) && kotlin.jvm.internal.k.a(this.b, wVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "SettingsAndConfigReceived(conversationKitSettings=" + this.a + ", config=" + this.b + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.i c() {
            return this.a;
        }

        @NotNull
        public final zendesk.conversationkit.android.model.h b() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public w(@NotNull zendesk.conversationkit.android.i conversationKitSettings, @NotNull zendesk.conversationkit.android.model.h config) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
            kotlin.jvm.internal.k.e(config, "config");
            this.a = conversationKitSettings;
            this.b = config;
        }
    }

    /* compiled from: Effect.kt */
    public static final class d extends o {
        @NotNull
        private final zendesk.conversationkit.android.i a;
        @NotNull
        private final zendesk.conversationkit.android.g<zendesk.conversationkit.android.model.h> b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            return kotlin.jvm.internal.k.a(this.a, dVar.a) && kotlin.jvm.internal.k.a(this.b, dVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "ConfigResultReceived(conversationKitSettings=" + this.a + ", result=" + this.b + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.i b() {
            return this.a;
        }

        @NotNull
        public final zendesk.conversationkit.android.g<zendesk.conversationkit.android.model.h> c() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(@NotNull zendesk.conversationkit.android.i conversationKitSettings, @NotNull zendesk.conversationkit.android.g<zendesk.conversationkit.android.model.h> result) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
            kotlin.jvm.internal.k.e(result, "result");
            this.a = conversationKitSettings;
            this.b = result;
        }
    }

    /* compiled from: Effect.kt */
    public static final class n extends o implements e {
        @NotNull
        private final zendesk.conversationkit.android.a a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof n) && a() == ((n) obj).a();
        }

        public int hashCode() {
            return a().hashCode();
        }

        @NotNull
        public String toString() {
            return "NetworkConnectionChanged(connectionStatus=" + a() + ')';
        }

        @NotNull
        public zendesk.conversationkit.android.a a() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public n(@NotNull zendesk.conversationkit.android.a connectionStatus) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(connectionStatus, "connectionStatus");
            this.a = connectionStatus;
        }
    }

    /* compiled from: Effect.kt */
    public static final class s extends o implements e {
        @NotNull
        private final zendesk.conversationkit.android.a a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof s) && a() == ((s) obj).a();
        }

        public int hashCode() {
            return a().hashCode();
        }

        @NotNull
        public String toString() {
            return "RealtimeConnectionChanged(connectionStatus=" + a() + ')';
        }

        @NotNull
        public zendesk.conversationkit.android.a a() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public s(@NotNull zendesk.conversationkit.android.a connectionStatus) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(connectionStatus, "connectionStatus");
            this.a = connectionStatus;
        }
    }

    /* compiled from: Effect.kt */
    public static final class g extends o {
        @NotNull
        private final zendesk.conversationkit.android.i a;
        @NotNull
        private final zendesk.conversationkit.android.model.h b;
        @NotNull
        private final zendesk.conversationkit.android.g<User> c;
        @NotNull
        private final String d;
        @Nullable
        private final String e;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof g)) {
                return false;
            }
            g gVar = (g) obj;
            return kotlin.jvm.internal.k.a(this.a, gVar.a) && kotlin.jvm.internal.k.a(this.b, gVar.b) && kotlin.jvm.internal.k.a(this.c, gVar.c) && kotlin.jvm.internal.k.a(this.d, gVar.d) && kotlin.jvm.internal.k.a(this.e, gVar.e);
        }

        public int hashCode() {
            int hashCode = ((((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31;
            String str = this.e;
            return hashCode + (str == null ? 0 : str.hashCode());
        }

        @NotNull
        public String toString() {
            return "CreateUserResult(conversationKitSettings=" + this.a + ", config=" + this.b + ", result=" + this.c + ", clientId=" + this.d + ", pendingPushToken=" + this.e + ')';
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ g(zendesk.conversationkit.android.i iVar, zendesk.conversationkit.android.model.h hVar, zendesk.conversationkit.android.g gVar, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(iVar, hVar, gVar, str, (i & 16) != 0 ? null : str2);
        }

        @NotNull
        public final zendesk.conversationkit.android.i d() {
            return this.a;
        }

        @NotNull
        public final zendesk.conversationkit.android.model.h c() {
            return this.b;
        }

        @NotNull
        public final zendesk.conversationkit.android.g<User> f() {
            return this.c;
        }

        @NotNull
        public final String b() {
            return this.d;
        }

        @Nullable
        public final String e() {
            return this.e;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public g(@NotNull zendesk.conversationkit.android.i conversationKitSettings, @NotNull zendesk.conversationkit.android.model.h config, @NotNull zendesk.conversationkit.android.g<User> result, @NotNull String clientId, @Nullable String pendingPushToken) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
            kotlin.jvm.internal.k.e(config, "config");
            kotlin.jvm.internal.k.e(result, "result");
            kotlin.jvm.internal.k.e(clientId, "clientId");
            this.a = conversationKitSettings;
            this.b = config;
            this.c = result;
            this.d = clientId;
            this.e = pendingPushToken;
        }
    }

    /* compiled from: Effect.kt */
    public static final class j extends o {
        @NotNull
        private final zendesk.conversationkit.android.i a;
        @NotNull
        private final zendesk.conversationkit.android.model.h b;
        @NotNull
        private final zendesk.conversationkit.android.g<User> c;
        @NotNull
        private final String d;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof j)) {
                return false;
            }
            j jVar = (j) obj;
            return kotlin.jvm.internal.k.a(this.a, jVar.a) && kotlin.jvm.internal.k.a(this.b, jVar.b) && kotlin.jvm.internal.k.a(this.c, jVar.c) && kotlin.jvm.internal.k.a(this.d, jVar.d);
        }

        public int hashCode() {
            return (((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode();
        }

        @NotNull
        public String toString() {
            return "LoginUserResult(conversationKitSettings=" + this.a + ", config=" + this.b + ", result=" + this.c + ", clientId=" + this.d + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.i d() {
            return this.a;
        }

        @NotNull
        public final zendesk.conversationkit.android.model.h c() {
            return this.b;
        }

        @NotNull
        public final zendesk.conversationkit.android.g<User> e() {
            return this.c;
        }

        @NotNull
        public final String b() {
            return this.d;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public j(@NotNull zendesk.conversationkit.android.i conversationKitSettings, @NotNull zendesk.conversationkit.android.model.h config, @NotNull zendesk.conversationkit.android.g<User> result, @NotNull String clientId) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
            kotlin.jvm.internal.k.e(config, "config");
            kotlin.jvm.internal.k.e(result, "result");
            kotlin.jvm.internal.k.e(clientId, "clientId");
            this.a = conversationKitSettings;
            this.b = config;
            this.c = result;
            this.d = clientId;
        }
    }

    /* compiled from: Effect.kt */
    public static final class b extends o {
        @NotNull
        private final zendesk.conversationkit.android.g<User> a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && kotlin.jvm.internal.k.a(this.a, ((b) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "AlreadyLoggedInResult(result=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull zendesk.conversationkit.android.g<User> result) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(result, "result");
            this.a = result;
        }

        @NotNull
        public final zendesk.conversationkit.android.g<User> b() {
            return this.a;
        }
    }

    /* compiled from: Effect.kt */
    public static final class k extends o {
        @NotNull
        private final zendesk.conversationkit.android.i a;
        @NotNull
        private final zendesk.conversationkit.android.model.h b;
        @NotNull
        private final zendesk.conversationkit.android.g<Object> c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof k)) {
                return false;
            }
            k kVar = (k) obj;
            return kotlin.jvm.internal.k.a(this.a, kVar.a) && kotlin.jvm.internal.k.a(this.b, kVar.b) && kotlin.jvm.internal.k.a(this.c, kVar.c);
        }

        public int hashCode() {
            return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
        }

        @NotNull
        public String toString() {
            return "LogoutUserResult(conversationKitSettings=" + this.a + ", config=" + this.b + ", result=" + this.c + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.i c() {
            return this.a;
        }

        @NotNull
        public final zendesk.conversationkit.android.model.h b() {
            return this.b;
        }

        @NotNull
        public final zendesk.conversationkit.android.g<Object> d() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public k(@NotNull zendesk.conversationkit.android.i conversationKitSettings, @NotNull zendesk.conversationkit.android.model.h config, @NotNull zendesk.conversationkit.android.g<? extends Object> result) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
            kotlin.jvm.internal.k.e(config, "config");
            kotlin.jvm.internal.k.e(result, "result");
            this.a = conversationKitSettings;
            this.b = config;
            this.c = result;
        }
    }

    /* compiled from: Effect.kt */
    public static final class c extends o {
        @Nullable
        private final User a;
        @NotNull
        private final zendesk.conversationkit.android.i b;
        @NotNull
        private final g.b<zendesk.conversationkit.android.model.h> c;
        @NotNull
        private final String d;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return kotlin.jvm.internal.k.a(this.a, cVar.a) && kotlin.jvm.internal.k.a(this.b, cVar.b) && kotlin.jvm.internal.k.a(this.c, cVar.c) && kotlin.jvm.internal.k.a(this.d, cVar.d);
        }

        public int hashCode() {
            User user = this.a;
            return ((((((user == null ? 0 : user.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode();
        }

        @NotNull
        public String toString() {
            return "CheckForPersistedUserResult(user=" + this.a + ", conversationKitSettings=" + this.b + ", result=" + this.c + ", clientId=" + this.d + ')';
        }

        @Nullable
        public final User e() {
            return this.a;
        }

        @NotNull
        public final zendesk.conversationkit.android.i c() {
            return this.b;
        }

        @NotNull
        public final g.b<zendesk.conversationkit.android.model.h> d() {
            return this.c;
        }

        @NotNull
        public final String b() {
            return this.d;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@Nullable User user, @NotNull zendesk.conversationkit.android.i conversationKitSettings, @NotNull g.b<zendesk.conversationkit.android.model.h> result, @NotNull String clientId) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
            kotlin.jvm.internal.k.e(result, "result");
            kotlin.jvm.internal.k.e(clientId, "clientId");
            this.a = user;
            this.b = conversationKitSettings;
            this.c = result;
            this.d = clientId;
        }
    }

    /* compiled from: Effect.kt */
    public static final class u extends o {
        @NotNull
        private final zendesk.conversationkit.android.g<User> a;

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
            return "RefreshUserResult(result=" + this.a + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.g<User> b() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public u(@NotNull zendesk.conversationkit.android.g<User> result) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(result, "result");
            this.a = result;
        }
    }

    /* compiled from: Effect.kt */
    public static final class f extends o {
        @NotNull
        private final zendesk.conversationkit.android.g<Conversation> a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof f) && kotlin.jvm.internal.k.a(this.a, ((f) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "CreateConversationResult(result=" + this.a + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.g<Conversation> b() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public f(@NotNull zendesk.conversationkit.android.g<Conversation> result) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(result, "result");
            this.a = result;
        }
    }

    /* compiled from: Effect.kt */
    public static final class h extends o {
        @NotNull
        private final zendesk.conversationkit.android.g<Conversation> a;
        private final boolean b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof h)) {
                return false;
            }
            h hVar = (h) obj;
            return kotlin.jvm.internal.k.a(this.a, hVar.a) && this.b == hVar.b;
        }

        public int hashCode() {
            int hashCode = this.a.hashCode() * 31;
            boolean z = this.b;
            if (z) {
                z = true;
            }
            return hashCode + (z ? 1 : 0);
        }

        @NotNull
        public String toString() {
            return "GetConversationResult(result=" + this.a + ", shouldRefresh=" + this.b + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.g<Conversation> b() {
            return this.a;
        }

        public final boolean c() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public h(@NotNull zendesk.conversationkit.android.g<Conversation> result, boolean shouldRefresh) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(result, "result");
            this.a = result;
            this.b = shouldRefresh;
        }
    }

    /* compiled from: Effect.kt */
    public static final class t extends o {
        @NotNull
        private final zendesk.conversationkit.android.g<Conversation> a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof t) && kotlin.jvm.internal.k.a(this.a, ((t) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "RefreshConversationResult(result=" + this.a + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.g<Conversation> b() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public t(@NotNull zendesk.conversationkit.android.g<Conversation> result) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(result, "result");
            this.a = result;
        }
    }

    /* compiled from: Effect.kt */
    public static final class m extends o {
        @NotNull
        private final Message a;
        @NotNull
        private final String b;
        @Nullable
        private final Conversation c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof m)) {
                return false;
            }
            m mVar = (m) obj;
            return kotlin.jvm.internal.k.a(this.a, mVar.a) && kotlin.jvm.internal.k.a(this.b, mVar.b) && kotlin.jvm.internal.k.a(this.c, mVar.c);
        }

        public int hashCode() {
            int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
            Conversation conversation = this.c;
            return hashCode + (conversation == null ? 0 : conversation.hashCode());
        }

        @NotNull
        public String toString() {
            return "MessageReceived(message=" + this.a + ", conversationId=" + this.b + ", conversation=" + this.c + ')';
        }

        @NotNull
        public final Message d() {
            return this.a;
        }

        @NotNull
        public final String c() {
            return this.b;
        }

        @Nullable
        public final Conversation b() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public m(@NotNull Message message, @NotNull String conversationId, @Nullable Conversation conversation) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(message, "message");
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            this.a = message;
            this.b = conversationId;
            this.c = conversation;
        }
    }

    /* compiled from: Effect.kt */
    public static final class l extends o {
        @NotNull
        private final Message a;
        @NotNull
        private final String b;
        @Nullable
        private final Conversation c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof l)) {
                return false;
            }
            l lVar = (l) obj;
            return kotlin.jvm.internal.k.a(this.a, lVar.a) && kotlin.jvm.internal.k.a(this.b, lVar.b) && kotlin.jvm.internal.k.a(this.c, lVar.c);
        }

        public int hashCode() {
            int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
            Conversation conversation = this.c;
            return hashCode + (conversation == null ? 0 : conversation.hashCode());
        }

        @NotNull
        public String toString() {
            return "MessagePrepared(message=" + this.a + ", conversationId=" + this.b + ", conversation=" + this.c + ')';
        }

        @NotNull
        public final Message d() {
            return this.a;
        }

        @NotNull
        public final String c() {
            return this.b;
        }

        @Nullable
        public final Conversation b() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public l(@NotNull Message message, @NotNull String conversationId, @Nullable Conversation conversation) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(message, "message");
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            this.a = message;
            this.b = conversationId;
            this.c = conversation;
        }
    }

    /* compiled from: Effect.kt */
    public static final class v extends o {
        @NotNull
        private final zendesk.conversationkit.android.g<Message> a;
        @NotNull
        private final String b;
        @Nullable
        private final Message c;
        @Nullable
        private final Conversation d;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof v)) {
                return false;
            }
            v vVar = (v) obj;
            return kotlin.jvm.internal.k.a(this.a, vVar.a) && kotlin.jvm.internal.k.a(this.b, vVar.b) && kotlin.jvm.internal.k.a(this.c, vVar.c) && kotlin.jvm.internal.k.a(this.d, vVar.d);
        }

        public int hashCode() {
            int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
            Message message = this.c;
            int i = 0;
            int hashCode2 = (hashCode + (message == null ? 0 : message.hashCode())) * 31;
            Conversation conversation = this.d;
            if (conversation != null) {
                i = conversation.hashCode();
            }
            return hashCode2 + i;
        }

        @NotNull
        public String toString() {
            return "SendMessageResult(result=" + this.a + ", conversationId=" + this.b + ", message=" + this.c + ", conversation=" + this.d + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.g<Message> e() {
            return this.a;
        }

        @NotNull
        public final String c() {
            return this.b;
        }

        @Nullable
        public final Message d() {
            return this.c;
        }

        @Nullable
        public final Conversation b() {
            return this.d;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public v(@NotNull zendesk.conversationkit.android.g<Message> result, @NotNull String conversationId, @Nullable Message message, @Nullable Conversation conversation) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(result, "result");
            kotlin.jvm.internal.k.e(conversationId, "conversationId");
            this.a = result;
            this.b = conversationId;
            this.c = message;
            this.d = conversation;
        }
    }

    /* compiled from: Effect.kt */
    public static final class q extends o {
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
            return "PushTokenPrepared(pushToken=" + this.a + ')';
        }

        @NotNull
        public final String b() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public q(@NotNull String pushToken) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(pushToken, "pushToken");
            this.a = pushToken;
        }
    }

    /* compiled from: Effect.kt */
    public static final class r extends o {
        @NotNull
        private final zendesk.conversationkit.android.g<kotlin.x> a;
        @NotNull
        private final String b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof r)) {
                return false;
            }
            r rVar = (r) obj;
            return kotlin.jvm.internal.k.a(this.a, rVar.a) && kotlin.jvm.internal.k.a(this.b, rVar.b);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "PushTokenUpdateResult(result=" + this.a + ", pushToken=" + this.b + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.g<kotlin.x> c() {
            return this.a;
        }

        @NotNull
        public final String b() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public r(@NotNull zendesk.conversationkit.android.g<kotlin.x> result, @NotNull String pushToken) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(result, "result");
            kotlin.jvm.internal.k.e(pushToken, "pushToken");
            this.a = result;
            this.b = pushToken;
        }
    }

    /* compiled from: Effect.kt */
    public static final class a extends o {
        @NotNull
        private final zendesk.conversationkit.android.model.c a;
        @Nullable
        private final Conversation b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return kotlin.jvm.internal.k.a(this.a, aVar.a) && kotlin.jvm.internal.k.a(this.b, aVar.b);
        }

        public int hashCode() {
            int hashCode = this.a.hashCode() * 31;
            Conversation conversation = this.b;
            return hashCode + (conversation == null ? 0 : conversation.hashCode());
        }

        @NotNull
        public String toString() {
            return "ActivityEventReceived(activityEvent=" + this.a + ", conversation=" + this.b + ')';
        }

        @NotNull
        public final zendesk.conversationkit.android.model.c b() {
            return this.a;
        }

        @Nullable
        public final Conversation c() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull zendesk.conversationkit.android.model.c activityEvent, @Nullable Conversation conversation) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(activityEvent, "activityEvent");
            this.a = activityEvent;
            this.b = conversation;
        }
    }

    /* compiled from: Effect.kt */
    public static final class p extends o {
        @NotNull
        private final User a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof p) && kotlin.jvm.internal.k.a(this.a, ((p) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "PersistedUserReceived(user=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public p(@NotNull User user) {
            super((DefaultConstructorMarker) null);
            kotlin.jvm.internal.k.e(user, "user");
            this.a = user;
        }

        @NotNull
        public final User b() {
            return this.a;
        }
    }
}
