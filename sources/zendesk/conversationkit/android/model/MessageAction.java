package zendesk.conversationkit.android.model;

import com.google.chip.chiptool.setuppayloadscanner.a;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.bean.Constants;
import com.squareup.moshi.g;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessageAction.kt */
public abstract class MessageAction {
    @NotNull
    private final q a;

    public /* synthetic */ MessageAction(q qVar, DefaultConstructorMarker defaultConstructorMarker) {
        this(qVar);
    }

    private MessageAction(q type) {
        this.a = type;
    }

    @g(generateAdapter = true)
    /* compiled from: MessageAction.kt */
    public static final class Buy extends MessageAction {
        @NotNull
        private final String b;
        @Nullable
        private final Map<String, Object> c;
        @NotNull
        private final String d;
        @NotNull
        private final String e;
        private final long f;
        @NotNull
        private final String g;
        @NotNull
        private final o h;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Buy)) {
                return false;
            }
            Buy buy = (Buy) obj;
            return k.a(c(), buy.c()) && k.a(d(), buy.d()) && k.a(this.d, buy.d) && k.a(this.e, buy.e) && this.f == buy.f && k.a(this.g, buy.g) && this.h == buy.h;
        }

        public int hashCode() {
            return (((((((((((c().hashCode() * 31) + (d() == null ? 0 : d().hashCode())) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + a.a(this.f)) * 31) + this.g.hashCode()) * 31) + this.h.hashCode();
        }

        @NotNull
        public String toString() {
            return "Buy(id=" + c() + ", metadata=" + d() + ", text=" + this.d + ", uri=" + this.e + ", amount=" + this.f + ", currency=" + this.g + ", state=" + this.h + ')';
        }

        @NotNull
        public String c() {
            return this.b;
        }

        @Nullable
        public Map<String, Object> d() {
            return this.c;
        }

        @NotNull
        public final String f() {
            return this.d;
        }

        @NotNull
        public final String g() {
            return this.e;
        }

        public final long a() {
            return this.f;
        }

        @NotNull
        public final String b() {
            return this.g;
        }

        @NotNull
        public final o e() {
            return this.h;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Buy(@NotNull String id, @Nullable Map<String, ? extends Object> metadata, @NotNull String text, @NotNull String uri, long amount, @NotNull String currency, @NotNull o state) {
            super(q.BUY, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(text, "text");
            k.e(uri, "uri");
            k.e(currency, FirebaseAnalytics.Param.CURRENCY);
            k.e(state, Constants.ACTION_STATE);
            this.b = id;
            this.c = metadata;
            this.d = text;
            this.e = uri;
            this.f = amount;
            this.g = currency;
            this.h = state;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: MessageAction.kt */
    public static final class Link extends MessageAction {
        @NotNull
        private final String b;
        @Nullable
        private final Map<String, Object> c;
        @NotNull
        private final String d;
        @NotNull
        private final String e;
        private final boolean f;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Link)) {
                return false;
            }
            Link link = (Link) obj;
            return k.a(b(), link.b()) && k.a(c(), link.c()) && k.a(this.d, link.d) && k.a(this.e, link.e) && this.f == link.f;
        }

        public int hashCode() {
            int hashCode = ((((((b().hashCode() * 31) + (c() == null ? 0 : c().hashCode())) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31;
            boolean z = this.f;
            if (z) {
                z = true;
            }
            return hashCode + (z ? 1 : 0);
        }

        @NotNull
        public String toString() {
            return "Link(id=" + b() + ", metadata=" + c() + ", text=" + this.d + ", uri=" + this.e + ", default=" + this.f + ')';
        }

        @NotNull
        public String b() {
            return this.b;
        }

        @Nullable
        public Map<String, Object> c() {
            return this.c;
        }

        @NotNull
        public final String d() {
            return this.d;
        }

        @NotNull
        public final String e() {
            return this.e;
        }

        public final boolean a() {
            return this.f;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Link(@NotNull String id, @Nullable Map<String, ? extends Object> metadata, @NotNull String text, @NotNull String uri, boolean z) {
            super(q.LINK, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(text, "text");
            k.e(uri, "uri");
            this.b = id;
            this.c = metadata;
            this.d = text;
            this.e = uri;
            this.f = z;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: MessageAction.kt */
    public static final class LocationRequest extends MessageAction {
        @NotNull
        private final String b;
        @Nullable
        private final Map<String, Object> c;
        @NotNull
        private final String d;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LocationRequest)) {
                return false;
            }
            LocationRequest locationRequest = (LocationRequest) obj;
            return k.a(a(), locationRequest.a()) && k.a(b(), locationRequest.b()) && k.a(this.d, locationRequest.d);
        }

        public int hashCode() {
            return (((a().hashCode() * 31) + (b() == null ? 0 : b().hashCode())) * 31) + this.d.hashCode();
        }

        @NotNull
        public String toString() {
            return "LocationRequest(id=" + a() + ", metadata=" + b() + ", text=" + this.d + ')';
        }

        @NotNull
        public String a() {
            return this.b;
        }

        @Nullable
        public Map<String, Object> b() {
            return this.c;
        }

        @NotNull
        public final String c() {
            return this.d;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LocationRequest(@NotNull String id, @Nullable Map<String, ? extends Object> metadata, @NotNull String text) {
            super(q.LOCATION_REQUEST, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(text, "text");
            this.b = id;
            this.c = metadata;
            this.d = text;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: MessageAction.kt */
    public static final class Postback extends MessageAction {
        @NotNull
        private final String b;
        @Nullable
        private final Map<String, Object> c;
        @NotNull
        private final String d;
        @NotNull
        private final String e;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Postback)) {
                return false;
            }
            Postback postback = (Postback) obj;
            return k.a(a(), postback.a()) && k.a(b(), postback.b()) && k.a(this.d, postback.d) && k.a(this.e, postback.e);
        }

        public int hashCode() {
            return (((((a().hashCode() * 31) + (b() == null ? 0 : b().hashCode())) * 31) + this.d.hashCode()) * 31) + this.e.hashCode();
        }

        @NotNull
        public String toString() {
            return "Postback(id=" + a() + ", metadata=" + b() + ", text=" + this.d + ", payload=" + this.e + ')';
        }

        @NotNull
        public String a() {
            return this.b;
        }

        @Nullable
        public Map<String, Object> b() {
            return this.c;
        }

        @NotNull
        public final String d() {
            return this.d;
        }

        @NotNull
        public final String c() {
            return this.e;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Postback(@NotNull String id, @Nullable Map<String, ? extends Object> metadata, @NotNull String text, @NotNull String payload) {
            super(q.POSTBACK, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(text, "text");
            k.e(payload, "payload");
            this.b = id;
            this.c = metadata;
            this.d = text;
            this.e = payload;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: MessageAction.kt */
    public static final class Reply extends MessageAction {
        @NotNull
        private final String b;
        @Nullable
        private final Map<String, Object> c;
        @NotNull
        private final String d;
        @Nullable
        private final String e;
        @NotNull
        private final String f;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Reply)) {
                return false;
            }
            Reply reply = (Reply) obj;
            return k.a(b(), reply.b()) && k.a(c(), reply.c()) && k.a(this.d, reply.d) && k.a(this.e, reply.e) && k.a(this.f, reply.f);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((((b().hashCode() * 31) + (c() == null ? 0 : c().hashCode())) * 31) + this.d.hashCode()) * 31;
            String str = this.e;
            if (str != null) {
                i = str.hashCode();
            }
            return ((hashCode + i) * 31) + this.f.hashCode();
        }

        @NotNull
        public String toString() {
            return "Reply(id=" + b() + ", metadata=" + c() + ", text=" + this.d + ", iconUrl=" + this.e + ", payload=" + this.f + ')';
        }

        @NotNull
        public String b() {
            return this.b;
        }

        @Nullable
        public Map<String, Object> c() {
            return this.c;
        }

        @NotNull
        public final String e() {
            return this.d;
        }

        @Nullable
        public final String a() {
            return this.e;
        }

        @NotNull
        public final String d() {
            return this.f;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Reply(@NotNull String id, @Nullable Map<String, ? extends Object> metadata, @NotNull String text, @Nullable String iconUrl, @NotNull String payload) {
            super(q.REPLY, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(text, "text");
            k.e(payload, "payload");
            this.b = id;
            this.c = metadata;
            this.d = text;
            this.e = iconUrl;
            this.f = payload;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: MessageAction.kt */
    public static final class Share extends MessageAction {
        @NotNull
        private final String b;
        @Nullable
        private final Map<String, Object> c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Share)) {
                return false;
            }
            Share share = (Share) obj;
            return k.a(a(), share.a()) && k.a(b(), share.b());
        }

        public int hashCode() {
            return (a().hashCode() * 31) + (b() == null ? 0 : b().hashCode());
        }

        @NotNull
        public String toString() {
            return "Share(id=" + a() + ", metadata=" + b() + ')';
        }

        @NotNull
        public String a() {
            return this.b;
        }

        @Nullable
        public Map<String, Object> b() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Share(@NotNull String id, @Nullable Map<String, ? extends Object> metadata) {
            super(q.SHARE, (DefaultConstructorMarker) null);
            k.e(id, "id");
            this.b = id;
            this.c = metadata;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: MessageAction.kt */
    public static final class WebView extends MessageAction {
        @NotNull
        private final String b;
        @Nullable
        private final Map<String, Object> c;
        @NotNull
        private final String d;
        @NotNull
        private final String e;
        @NotNull
        private final String f;
        private final boolean g;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof WebView)) {
                return false;
            }
            WebView webView = (WebView) obj;
            return k.a(c(), webView.c()) && k.a(d(), webView.d()) && k.a(this.d, webView.d) && k.a(this.e, webView.e) && k.a(this.f, webView.f) && this.g == webView.g;
        }

        public int hashCode() {
            int hashCode = ((((((((c().hashCode() * 31) + (d() == null ? 0 : d().hashCode())) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31;
            boolean z = this.g;
            if (z) {
                z = true;
            }
            return hashCode + (z ? 1 : 0);
        }

        @NotNull
        public String toString() {
            return "WebView(id=" + c() + ", metadata=" + d() + ", text=" + this.d + ", uri=" + this.e + ", fallback=" + this.f + ", default=" + this.g + ')';
        }

        @NotNull
        public String c() {
            return this.b;
        }

        @Nullable
        public Map<String, Object> d() {
            return this.c;
        }

        @NotNull
        public final String e() {
            return this.d;
        }

        @NotNull
        public final String f() {
            return this.e;
        }

        @NotNull
        public final String b() {
            return this.f;
        }

        public final boolean a() {
            return this.g;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public WebView(@NotNull String id, @Nullable Map<String, ? extends Object> metadata, @NotNull String text, @NotNull String uri, @NotNull String fallback, boolean z) {
            super(q.WEBVIEW, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(text, "text");
            k.e(uri, "uri");
            k.e(fallback, "fallback");
            this.b = id;
            this.c = metadata;
            this.d = text;
            this.e = uri;
            this.f = fallback;
            this.g = z;
        }
    }
}
