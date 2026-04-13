package zendesk.conversationkit.android.internal.faye;

import com.squareup.moshi.r;
import java.util.Date;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.e2;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zendesk.conversationkit.android.internal.c;
import zendesk.conversationkit.android.internal.rest.model.MessageDto;
import zendesk.conversationkit.android.model.RealtimeSettings;
import zendesk.conversationkit.android.model.f;
import zendesk.conversationkit.android.model.t;

/* compiled from: SunCoFayeClient.kt */
public final class a implements b, zendesk.faye.h {
    @NotNull
    public static final C0509a a = new C0509a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.faye.e b;
    /* access modifiers changed from: private */
    @NotNull
    public final RealtimeSettings c;
    @NotNull
    private final zendesk.conversationkit.android.model.f d;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.conversationkit.android.internal.d e;
    @NotNull
    private final o0 f;
    @NotNull
    private final r g;
    /* access modifiers changed from: private */
    public int h;
    @NotNull
    private zendesk.conversationkit.android.a i;

    public a(@NotNull zendesk.faye.e fayeClient, @NotNull RealtimeSettings realtimeSettings, @NotNull zendesk.conversationkit.android.model.f authenticationType, @NotNull zendesk.conversationkit.android.internal.d actionDispatcher, @NotNull o0 coroutineScope, @NotNull r moshi) {
        k.e(fayeClient, "fayeClient");
        k.e(realtimeSettings, "realtimeSettings");
        k.e(authenticationType, "authenticationType");
        k.e(actionDispatcher, "actionDispatcher");
        k.e(coroutineScope, "coroutineScope");
        k.e(moshi, "moshi");
        this.b = fayeClient;
        this.c = realtimeSettings;
        this.d = authenticationType;
        this.e = actionDispatcher;
        this.f = coroutineScope;
        this.g = moshi;
        fayeClient.b(this);
        this.i = zendesk.conversationkit.android.a.DISCONNECTED;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ a(zendesk.faye.e r8, zendesk.conversationkit.android.model.RealtimeSettings r9, zendesk.conversationkit.android.model.f r10, zendesk.conversationkit.android.internal.d r11, kotlinx.coroutines.o0 r12, com.squareup.moshi.r r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r7 = this;
            r14 = r14 & 32
            if (r14 == 0) goto L_0x0020
            com.squareup.moshi.r$b r13 = new com.squareup.moshi.r$b
            r13.<init>()
            java.lang.Class<java.util.Date> r14 = java.util.Date.class
            com.squareup.moshi.adapters.c r15 = new com.squareup.moshi.adapters.c
            r15.<init>()
            com.squareup.moshi.r$b r13 = r13.b(r14, r15)
            com.squareup.moshi.r r13 = r13.c()
            java.lang.String r14 = "Builder()\n        .add(D…apter())\n        .build()"
            kotlin.jvm.internal.k.d(r13, r14)
            r6 = r13
            goto L_0x0021
        L_0x0020:
            r6 = r13
        L_0x0021:
            r0 = r7
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.faye.a.<init>(zendesk.faye.e, zendesk.conversationkit.android.model.RealtimeSettings, zendesk.conversationkit.android.model.f, zendesk.conversationkit.android.internal.d, kotlinx.coroutines.o0, com.squareup.moshi.r, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public void connect() {
        if (!this.c.d()) {
            zendesk.logger.a.h("SunCoFayeClient", k.l("Realtime is not enabled for the user with id ", this.c.h()), new Object[0]);
            return;
        }
        this.i = zendesk.conversationkit.android.a.CONNECTING_REALTIME;
        z1 unused = j.d(this.f, (kotlin.coroutines.g) null, (q0) null, new b(this, (kotlin.coroutines.d<? super b>) null), 3, (Object) null);
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.faye.DefaultSunCoFayeClient$connect$1", f = "SunCoFayeClient.kt", l = {99, 101, 105}, m = "invokeSuspend")
    /* compiled from: SunCoFayeClient.kt */
    public static final class b extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(a aVar, kotlin.coroutines.d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new b(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0047, code lost:
            zendesk.conversationkit.android.internal.faye.a.j(r1.this$0).a(zendesk.faye.c.a.a().a());
            r2 = zendesk.conversationkit.android.internal.faye.a.h(r1.this$0);
            r3 = new zendesk.conversationkit.android.internal.c.p(zendesk.conversationkit.android.a.CONNECTING_REALTIME);
            r1.label = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x006e, code lost:
            if (r2.a(r3, r1) != r0) goto L_0x0071;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0070, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x007b, code lost:
            if (zendesk.conversationkit.android.internal.faye.a.j(r1.this$0).isConnected() == false) goto L_0x0096;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x007d, code lost:
            r2 = zendesk.conversationkit.android.internal.faye.a.h(r1.this$0);
            r3 = new zendesk.conversationkit.android.internal.c.p(zendesk.conversationkit.android.a.CONNECTED_REALTIME);
            r1.label = 3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0091, code lost:
            if (r2.a(r3, r1) != r0) goto L_0x0094;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0093, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0094, code lost:
            r0 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0095, code lost:
            r1 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0098, code lost:
            return kotlin.x.a;
         */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r6) {
            /*
                r5 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r5.label
                switch(r1) {
                    case 0: goto L_0x0021;
                    case 1: goto L_0x001c;
                    case 2: goto L_0x0017;
                    case 3: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r0)
                throw r6
            L_0x0011:
                r0 = r5
                kotlin.p.b(r6)
                goto L_0x0095
            L_0x0017:
                r1 = r5
                kotlin.p.b(r6)
                goto L_0x0071
            L_0x001c:
                r1 = r5
                kotlin.p.b(r6)
                goto L_0x0047
            L_0x0021:
                kotlin.p.b(r6)
                r1 = r5
                zendesk.conversationkit.android.internal.faye.a r2 = r1.this$0
                zendesk.conversationkit.android.model.RealtimeSettings r2 = r2.c
                java.util.concurrent.TimeUnit r2 = r2.g()
                zendesk.conversationkit.android.internal.faye.a r3 = r1.this$0
                zendesk.conversationkit.android.model.RealtimeSettings r3 = r3.c
                long r3 = r3.c()
                long r2 = r2.toMillis(r3)
                r4 = 1
                r1.label = r4
                java.lang.Object r2 = kotlinx.coroutines.z0.a(r2, r1)
                if (r2 != r0) goto L_0x0047
                return r0
            L_0x0047:
                zendesk.conversationkit.android.internal.faye.a r2 = r1.this$0
                zendesk.faye.e r2 = r2.b
                zendesk.faye.c$b r3 = zendesk.faye.c.a
                zendesk.faye.c$a r3 = r3.a()
                zendesk.faye.c r3 = r3.a()
                r2.a(r3)
                zendesk.conversationkit.android.internal.faye.a r2 = r1.this$0
                zendesk.conversationkit.android.internal.d r2 = r2.e
                zendesk.conversationkit.android.internal.c$p r3 = new zendesk.conversationkit.android.internal.c$p
                zendesk.conversationkit.android.a r4 = zendesk.conversationkit.android.a.CONNECTING_REALTIME
                r3.<init>(r4)
                r4 = 2
                r1.label = r4
                java.lang.Object r2 = r2.a(r3, r1)
                if (r2 != r0) goto L_0x0071
                return r0
            L_0x0071:
                zendesk.conversationkit.android.internal.faye.a r2 = r1.this$0
                zendesk.faye.e r2 = r2.b
                boolean r2 = r2.isConnected()
                if (r2 == 0) goto L_0x0096
                zendesk.conversationkit.android.internal.faye.a r2 = r1.this$0
                zendesk.conversationkit.android.internal.d r2 = r2.e
                zendesk.conversationkit.android.internal.c$p r3 = new zendesk.conversationkit.android.internal.c$p
                zendesk.conversationkit.android.a r4 = zendesk.conversationkit.android.a.CONNECTED_REALTIME
                r3.<init>(r4)
                r4 = 3
                r1.label = r4
                java.lang.Object r2 = r2.a(r3, r1)
                if (r2 != r0) goto L_0x0094
                return r0
            L_0x0094:
                r0 = r1
            L_0x0095:
                r1 = r0
            L_0x0096:
                kotlin.x r0 = kotlin.x.a
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.faye.a.b.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public void disconnect() {
        if (!this.c.d()) {
            zendesk.logger.a.h("SunCoFayeClient", k.l("Realtime is not enabled for the user with id ", this.c.h()), new Object[0]);
            return;
        }
        this.i = zendesk.conversationkit.android.a.DISCONNECTED;
        this.b.a(zendesk.faye.d.a.a().a());
        e2.f(this.f.getCoroutineContext(), (CancellationException) null, 1, (Object) null);
    }

    public void f(@NotNull zendesk.faye.g fayeClientError, @Nullable Throwable throwable) {
        k.e(fayeClientError, "fayeClientError");
        zendesk.logger.a.c("SunCoFayeClient", fayeClientError.name(), throwable, new Object[0]);
        zendesk.conversationkit.android.a aVar = this.i;
        if ((aVar == zendesk.conversationkit.android.a.CONNECTING_REALTIME || aVar == zendesk.conversationkit.android.a.DISCONNECTED) && this.h < this.c.e()) {
            zendesk.logger.a.b("SunCoFayeClient", "Reconnecting in %d seconds... [%d/%d]", Long.valueOf(this.c.f()), Integer.valueOf(this.h), Integer.valueOf(this.c.e()));
            z1 unused = j.d(this.f, (kotlin.coroutines.g) null, (q0) null, new c(this, (kotlin.coroutines.d<? super c>) null), 3, (Object) null);
        }
        if (this.h > this.c.e()) {
            zendesk.logger.a.d("SunCoFayeClient", "Failed to reconnect. Attempts exhausted.", new Object[0]);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.faye.DefaultSunCoFayeClient$onClientError$1", f = "SunCoFayeClient.kt", l = {153}, m = "invokeSuspend")
    /* compiled from: SunCoFayeClient.kt */
    public static final class c extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(a aVar, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            c cVar;
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    long millis = this.this$0.c.g().toMillis(this.this$0.c.f());
                    this.label = 1;
                    if (z0.a(millis, this) != d) {
                        cVar = this;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    cVar = this;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            a aVar = cVar.this$0;
            aVar.h = aVar.h + 1;
            cVar.this$0.b.a(zendesk.faye.c.a.a().a());
            return x.a;
        }
    }

    public void e() {
        this.h = 0;
        this.i = zendesk.conversationkit.android.a.CONNECTED_REALTIME;
        RealtimeSettings $this$onConnectedToServer_u24lambda_u2d0 = this.c;
        String userChannel = "/sdk/apps/" + $this$onConnectedToServer_u24lambda_u2d0.a() + "/appusers/" + $this$onConnectedToServer_u24lambda_u2d0.h();
        RealtimeSettings $this$onConnectedToServer_u24lambda_u2d1 = this.c;
        JSONObject args = new JSONObject();
        try {
            args.put("appId", (Object) $this$onConnectedToServer_u24lambda_u2d1.a());
            args.put("appUserId", (Object) $this$onConnectedToServer_u24lambda_u2d1.h());
            zendesk.conversationkit.android.model.f fVar = this.d;
            if (fVar instanceof f.b) {
                args.put("sessionToken", (Object) ((f.b) fVar).a());
            } else if (fVar instanceof f.a) {
                args.put("jwt", (Object) ((f.a) fVar).a());
            } else {
                boolean a2 = k.a(fVar, f.c.a);
            }
        } catch (JSONException e2) {
        }
        String ext = args.toString();
        k.d(ext, "with(realtimeSettings) {…args.toString()\n        }");
        this.b.a(zendesk.faye.j.a.a(userChannel).b(zendesk.faye.b.a.a().b(ext).a()).a());
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.faye.DefaultSunCoFayeClient$onDisconnectedFromServer$1", f = "SunCoFayeClient.kt", l = {215}, m = "invokeSuspend")
    /* compiled from: SunCoFayeClient.kt */
    public static final class d extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(a aVar, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new d(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((d) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    zendesk.conversationkit.android.internal.d h = this.this$0.e;
                    c.p pVar = new c.p(zendesk.conversationkit.android.a.DISCONNECTED);
                    this.label = 1;
                    if (h.a(pVar, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    public void a() {
        this.i = zendesk.conversationkit.android.a.DISCONNECTED;
        z1 unused = j.d(this.f, (kotlin.coroutines.g) null, (q0) null, new d(this, (kotlin.coroutines.d<? super d>) null), 3, (Object) null);
    }

    public void b(@NotNull String channel, @NotNull String message) {
        k.e(channel, "channel");
        k.e(message, "message");
    }

    public void g(@NotNull String channel, @NotNull String message) {
        k.e(channel, "channel");
        k.e(message, "message");
        try {
            JSONArray events = new JSONObject(message).getJSONArray("events");
            k.d(events, "JSONObject(message).getJSONArray(\"events\")");
            int length = events.length();
            if (length > 0) {
                int i2 = 0;
                do {
                    int i3 = i2;
                    i2++;
                    try {
                        JSONObject jSONObject = events.getJSONObject(i3);
                        k.d(jSONObject, "events.getJSONObject(i)");
                        n(jSONObject);
                        continue;
                    } catch (JSONException exception) {
                        zendesk.logger.a.c("SunCoFayeClient", k.l("Unable to processed events: ", events), exception, new Object[0]);
                        continue;
                    }
                } while (i2 < length);
            }
        } catch (JSONException exception2) {
            zendesk.logger.a.c("SunCoFayeClient", k.l("Unable to processed message: ", message), exception2, new Object[0]);
        }
    }

    private final void n(JSONObject event) {
        WsFayeMessageDto fayeMessage = this.g.c(WsFayeMessageDto.class).c(event.toString());
        if (fayeMessage != null) {
            d type = d.Companion.a(fayeMessage.d());
            if (type == d.MESSAGE && fayeMessage.c() != null) {
                o(fayeMessage.b().b(), fayeMessage.c());
            } else if (type != d.ACTIVITY || fayeMessage.a() == null) {
                zendesk.logger.a.h("SunCoFayeClient", k.l("The message has a type which cannot be processed: ", fayeMessage), new Object[0]);
            } else {
                m(fayeMessage.b().b(), fayeMessage.a(), fayeMessage.b());
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.faye.DefaultSunCoFayeClient$processMessageEvent$1", f = "SunCoFayeClient.kt", l = {275}, m = "invokeSuspend")
    /* compiled from: SunCoFayeClient.kt */
    public static final class h extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ String $conversationId;
        final /* synthetic */ MessageDto $message;
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(a aVar, String str, MessageDto messageDto, kotlin.coroutines.d<? super h> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
            this.$conversationId = str;
            this.$message = messageDto;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new h(this.this$0, this.$conversationId, this.$message, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((h) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    zendesk.conversationkit.android.internal.d h = this.this$0.e;
                    c.j jVar = new c.j(this.$conversationId, t.c(this.$message, (Date) null, (String) null, 3, (Object) null));
                    this.label = 1;
                    if (h.a(jVar, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    private final void o(String conversationId, MessageDto message) {
        z1 unused = j.d(this.f, (kotlin.coroutines.g) null, (q0) null, new h(this, conversationId, message, (kotlin.coroutines.d<? super h>) null), 3, (Object) null);
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.faye.DefaultSunCoFayeClient$processActivityEvent$1", f = "SunCoFayeClient.kt", l = {298}, m = "invokeSuspend")
    /* compiled from: SunCoFayeClient.kt */
    public static final class g extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ WsActivityEventDto $activity;
        final /* synthetic */ WsConversationDto $conversation;
        final /* synthetic */ String $conversationId;
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(a aVar, WsActivityEventDto wsActivityEventDto, String str, WsConversationDto wsConversationDto, kotlin.coroutines.d<? super g> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
            this.$activity = wsActivityEventDto;
            this.$conversationId = str;
            this.$conversation = wsConversationDto;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new g(this.this$0, this.$activity, this.$conversationId, this.$conversation, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((g) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    zendesk.conversationkit.android.internal.d h = this.this$0.e;
                    c.a aVar = new c.a(zendesk.conversationkit.android.model.b.a(this.$activity, this.$conversationId, this.$conversation.a()));
                    this.label = 1;
                    if (h.a(aVar, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    private final void m(String conversationId, WsActivityEventDto activity, WsConversationDto conversation) {
        z1 unused = j.d(this.f, (kotlin.coroutines.g) null, (q0) null, new g(this, activity, conversationId, conversation, (kotlin.coroutines.d<? super g>) null), 3, (Object) null);
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.faye.DefaultSunCoFayeClient$onSubscribedToChannel$1", f = "SunCoFayeClient.kt", l = {312}, m = "invokeSuspend")
    /* compiled from: SunCoFayeClient.kt */
    public static final class e extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(a aVar, kotlin.coroutines.d<? super e> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new e(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((e) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    zendesk.conversationkit.android.internal.d h = this.this$0.e;
                    c.p pVar = new c.p(zendesk.conversationkit.android.a.CONNECTED_REALTIME);
                    this.label = 1;
                    if (h.a(pVar, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    public void c(@NotNull String channel) {
        k.e(channel, "channel");
        z1 unused = j.d(this.f, (kotlin.coroutines.g) null, (q0) null, new e(this, (kotlin.coroutines.d<? super e>) null), 3, (Object) null);
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.faye.DefaultSunCoFayeClient$onUnsubscribedFromChannel$1", f = "SunCoFayeClient.kt", l = {325}, m = "invokeSuspend")
    /* compiled from: SunCoFayeClient.kt */
    public static final class f extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(a aVar, kotlin.coroutines.d<? super f> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new f(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((f) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    zendesk.conversationkit.android.internal.d h = this.this$0.e;
                    c.p pVar = new c.p(zendesk.conversationkit.android.a.DISCONNECTED);
                    this.label = 1;
                    if (h.a(pVar, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    public void d(@NotNull String channel) {
        k.e(channel, "channel");
        z1 unused = j.d(this.f, (kotlin.coroutines.g) null, (q0) null, new f(this, (kotlin.coroutines.d<? super f>) null), 3, (Object) null);
    }

    /* renamed from: zendesk.conversationkit.android.internal.faye.a$a  reason: collision with other inner class name */
    /* compiled from: SunCoFayeClient.kt */
    public static final class C0509a {
        public /* synthetic */ C0509a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0509a() {
        }
    }
}
