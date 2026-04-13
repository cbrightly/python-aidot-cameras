package zendesk.faye.internal;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.HashSet;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.d0;
import okhttp3.h0;
import okhttp3.i0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import zendesk.faye.c;
import zendesk.faye.d;
import zendesk.faye.e;
import zendesk.faye.g;
import zendesk.faye.h;
import zendesk.faye.i;
import zendesk.faye.j;

/* compiled from: DefaultFayeClient.kt */
public final class b implements e {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final String b;
    /* access modifiers changed from: private */
    @NotNull
    public final c c;
    @NotNull
    private final C0518b d = new C0518b(this);
    @NotNull
    private final Set<h> e = new HashSet();
    @Nullable
    private String f;
    /* access modifiers changed from: private */
    public boolean g;
    /* access modifiers changed from: private */
    @Nullable
    public c h;
    private boolean i = true;

    public b(@NotNull String serverUrl, @NotNull c webSocket) {
        k.e(serverUrl, "serverUrl");
        k.e(webSocket, "webSocket");
        this.b = serverUrl;
        this.c = webSocket;
    }

    @NotNull
    public final Set<h> i() {
        return this.e;
    }

    public boolean h() {
        return this.i;
    }

    /* renamed from: zendesk.faye.internal.b$b  reason: collision with other inner class name */
    /* compiled from: DefaultFayeClient.kt */
    public final class C0518b extends i0 {
        final /* synthetic */ b a;

        public C0518b(b this$0) {
            k.e(this$0, "this$0");
            this.a = this$0;
        }

        public void f(@NotNull h0 webSocket, @NotNull d0 response) {
            k.e(webSocket, "webSocket");
            k.e(response, "response");
            c currentConnectMessage = this.a.h;
            if (currentConnectMessage != null) {
                this.a.c.d(a.a.c(currentConnectMessage.c(), currentConnectMessage.b()));
                return;
            }
            zendesk.logger.a.h("DefaultFayeClient", "onOpen called but connectMessage was null, closing the socket", new Object[0]);
            this.a.c.b();
        }

        public void d(@NotNull h0 webSocket, @NotNull String text) {
            k.e(webSocket, "webSocket");
            k.e(text, "text");
            zendesk.logger.a.b("DefaultFayeClient", k.l("Message received: ", text), new Object[0]);
            this.a.p(text);
        }

        public void a(@NotNull h0 webSocket, int code, @NotNull String reason) {
            k.e(webSocket, "webSocket");
            k.e(reason, "reason");
            this.a.g = false;
            this.a.c.c();
            for (h listener : this.a.i()) {
                listener.a();
            }
        }

        public void c(@NotNull h0 webSocket, @NotNull Throwable t, @Nullable d0 response) {
            k.e(webSocket, "webSocket");
            k.e(t, "t");
            this.a.g = false;
            this.a.c.c();
            for (h listener : this.a.i()) {
                listener.a();
                listener.f(g.CLIENT_TRANSPORT_ERROR, t);
            }
        }
    }

    public void b(@NotNull h listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.e.add(listener);
    }

    public boolean isConnected() {
        return this.g;
    }

    public void a(@NotNull zendesk.faye.a bayeuxMessage) {
        k.e(bayeuxMessage, "bayeuxMessage");
        if (bayeuxMessage instanceof c) {
            if (this.c.a(this.b, this.d)) {
                this.h = (c) bayeuxMessage;
            }
        } else if (bayeuxMessage instanceof d) {
            g((d) bayeuxMessage);
        } else if (bayeuxMessage instanceof j) {
            r((j) bayeuxMessage);
        } else if (bayeuxMessage instanceof zendesk.faye.k) {
            s((zendesk.faye.k) bayeuxMessage);
        } else if (bayeuxMessage instanceof i) {
            q((i) bayeuxMessage);
        }
    }

    private final void g(d disconnectMessage) {
        String it = this.f;
        if (it != null) {
            this.c.d(a.a.b(it, disconnectMessage.a()));
        }
        this.c.b();
        this.g = false;
    }

    private final void r(j subscribeMessage) {
        String currentFayeClientId = this.f;
        if (!this.g || currentFayeClientId == null) {
            for (h listener : this.e) {
                listener.f(g.CLIENT_NOT_CONNECTED_ERROR, (Throwable) null);
            }
            return;
        }
        this.c.d(a.a.h(currentFayeClientId, subscribeMessage.a(), subscribeMessage.b()));
    }

    private final void s(zendesk.faye.k unsubscribeMessage) {
        String currentFayeClientId = this.f;
        if (!this.g || currentFayeClientId == null) {
            for (h listener : this.e) {
                listener.f(g.CLIENT_NOT_CONNECTED_ERROR, (Throwable) null);
            }
            return;
        }
        this.c.d(a.a.i(currentFayeClientId, unsubscribeMessage.a(), unsubscribeMessage.b()));
    }

    private final void q(i publishMessage) {
        if (!this.g) {
            for (h listener : this.e) {
                listener.f(g.CLIENT_NOT_CONNECTED_ERROR, (Throwable) null);
            }
            return;
        }
        String payload = a.a.f(publishMessage.a(), publishMessage.b(), this.f, publishMessage.c());
        zendesk.logger.a.b("DefaultFayeClient", "Publishing to channel " + publishMessage.a() + ", message: " + publishMessage.b(), new Object[0]);
        this.c.d(payload);
        for (h listener2 : this.e) {
            listener2.b(publishMessage.a(), publishMessage.b());
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void p(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 0
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch:{ JSONException -> 0x0078 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0078 }
            int r2 = r1.length()
            if (r2 <= 0) goto L_0x0077
        L_0x000e:
            r3 = r0
            int r0 = r0 + 1
            org.json.JSONObject r4 = r1.optJSONObject(r3)
            if (r4 != 0) goto L_0x0018
            goto L_0x0075
        L_0x0018:
            java.lang.String r5 = "channel"
            java.lang.String r6 = r4.optString(r5)
            java.lang.String r7 = "successful"
            boolean r7 = r4.optBoolean(r7)
            if (r6 == 0) goto L_0x006f
            int r8 = r6.hashCode()
            switch(r8) {
                case -1992173988: goto L_0x0062;
                case -1839038474: goto L_0x0055;
                case -1548011601: goto L_0x0048;
                case -114481009: goto L_0x003b;
                case 1006455511: goto L_0x002e;
                default: goto L_0x002d;
            }
        L_0x002d:
            goto L_0x006f
        L_0x002e:
            java.lang.String r8 = "/meta/disconnect"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x0037
            goto L_0x006f
        L_0x0037:
            r9.l(r7)
            goto L_0x0075
        L_0x003b:
            java.lang.String r8 = "/meta/connect"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x0044
            goto L_0x006f
        L_0x0044:
            r9.k(r7)
            goto L_0x0075
        L_0x0048:
            java.lang.String r8 = "/meta/subscribe"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x0051
            goto L_0x006f
        L_0x0051:
            r9.n(r4, r7)
            goto L_0x0075
        L_0x0055:
            java.lang.String r8 = "/meta/unsubscribe"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x005e
            goto L_0x006f
        L_0x005e:
            r9.o(r4, r7)
            goto L_0x0075
        L_0x0062:
            java.lang.String r8 = "/meta/handshake"
            boolean r8 = r6.equals(r8)
            if (r8 != 0) goto L_0x006b
            goto L_0x006f
        L_0x006b:
            r9.m(r4, r7)
            goto L_0x0075
        L_0x006f:
            kotlin.jvm.internal.k.d(r6, r5)
            r9.j(r6, r4)
        L_0x0075:
            if (r0 < r2) goto L_0x000e
        L_0x0077:
            return
        L_0x0078:
            r1 = move-exception
            java.lang.String r2 = "parseFayeMessage failed to parse message: "
            java.lang.String r2 = kotlin.jvm.internal.k.l(r2, r10)
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.String r3 = "DefaultFayeClient"
            zendesk.logger.a.h(r3, r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.faye.internal.b.p(java.lang.String):void");
    }

    private final void m(JSONObject fayeMessage, boolean success) {
        String fayeClientId = fayeMessage.optString("clientId");
        c connectMessage = this.h;
        if (!success || fayeClientId == null || connectMessage == null) {
            this.c.b();
            return;
        }
        this.g = success;
        this.f = fayeClientId;
        this.c.d(a.a.a(fayeClientId, connectMessage.a()));
        for (h listener : this.e) {
            listener.e();
        }
    }

    private final void k(boolean success) {
        String currentFayeClientId = this.f;
        c connectMessage = this.h;
        if (!success || connectMessage == null || currentFayeClientId == null) {
            this.c.b();
            for (h listener : this.e) {
                listener.a();
            }
        } else if (h()) {
            this.c.d(a.a.a(currentFayeClientId, connectMessage.a()));
        }
    }

    private final void l(boolean success) {
        if (success) {
            this.g = false;
            this.c.b();
            for (h listener : this.e) {
                listener.a();
            }
            return;
        }
        zendesk.logger.a.h("DefaultFayeClient", k.l("handleDisconnectMessage called, but success was ", Boolean.valueOf(success)), new Object[0]);
    }

    private final void n(JSONObject fayeMessage, boolean success) {
        if (success) {
            for (h listener : this.e) {
                String optString = fayeMessage.optString("subscription");
                k.d(optString, "fayeMessage.optString(Bayeux.KEY_SUBSCRIPTION)");
                listener.c(optString);
            }
            return;
        }
        zendesk.logger.a.h("DefaultFayeClient", k.l("handleSubscribeMessage called, but success was ", Boolean.valueOf(success)), new Object[0]);
    }

    private final void o(JSONObject fayeMessage, boolean success) {
        if (success) {
            for (h listener : this.e) {
                String optString = fayeMessage.optString("subscription");
                k.d(optString, "fayeMessage.optString(Bayeux.KEY_SUBSCRIPTION)");
                listener.d(optString);
            }
            return;
        }
        zendesk.logger.a.h("DefaultFayeClient", k.l("handleUnsubscribeMessage called, but success was ", Boolean.valueOf(success)), new Object[0]);
    }

    private final void j(String channel, JSONObject fayeMessage) {
        JSONObject it = fayeMessage.optJSONObject("data");
        if (it != null) {
            for (h listener : i()) {
                String jSONObject = it.toString();
                k.d(jSONObject, "it.toString()");
                listener.g(channel, jSONObject);
            }
        }
    }

    /* compiled from: DefaultFayeClient.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
