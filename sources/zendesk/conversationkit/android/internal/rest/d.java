package zendesk.conversationkit.android.internal.rest;

import androidx.annotation.VisibleForTesting;
import com.squareup.moshi.r;
import java.io.File;
import java.util.Date;
import java.util.Set;
import kotlin.collections.n0;
import kotlin.collections.o0;
import kotlin.collections.p0;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.p;
import kotlin.t;
import kotlin.x;
import okhttp3.logging.a;
import okhttp3.w;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.t;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import zendesk.conversationkit.android.internal.rest.model.SendFieldResponseDto;
import zendesk.conversationkit.android.internal.rest.model.SendMessageDto;

/* compiled from: RestClientFactory.kt */
public final class d {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final Set<n<String, l<kotlin.coroutines.d<? super String>, Object>>> b;
    @NotNull
    private final e c;
    @NotNull
    private final File d;
    @NotNull
    private final retrofit2.converter.moshi.a e;

    public d(@NotNull Set<? extends n<String, ? extends l<? super kotlin.coroutines.d<? super String>, ? extends Object>>> defaultHeaders, @NotNull e restClientFiles, @NotNull File cacheDir) {
        k.e(defaultHeaders, "defaultHeaders");
        k.e(restClientFiles, "restClientFiles");
        k.e(cacheDir, "cacheDir");
        this.b = defaultHeaders;
        this.c = restClientFiles;
        this.d = cacheDir;
        retrofit2.converter.moshi.a a2 = retrofit2.converter.moshi.a.a(a.a());
        k.d(a2, "create(buildMoshi())");
        this.e = a2;
    }

    @NotNull
    public final c d(@NotNull String integrationId, @NotNull String baseUrl) {
        k.e(integrationId, "integrationId");
        k.e(baseUrl, "baseUrl");
        return new c(integrationId, f(this, baseUrl, (Set) null, 2, (Object) null));
    }

    @NotNull
    public final a c(@NotNull String appId, @NotNull String baseUrl) {
        k.e(appId, "appId");
        k.e(baseUrl, "baseUrl");
        return new a(appId, e(baseUrl, n0.c(t.a("x-smooch-appid", new b(appId, (kotlin.coroutines.d<? super b>) null)))));
    }

    @f(c = "zendesk.conversationkit.android.internal.rest.RestClientFactory$createAppRestClient$1", f = "RestClientFactory.kt", l = {}, m = "invokeSuspend")
    /* compiled from: RestClientFactory.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.l implements l<kotlin.coroutines.d<? super String>, Object> {
        final /* synthetic */ String $appId;
        int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(String str, kotlin.coroutines.d<? super b> dVar) {
            super(1, dVar);
            this.$appId = str;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new b(this.$appId, dVar);
        }

        @Nullable
        public final Object invoke(@Nullable kotlin.coroutines.d<? super String> dVar) {
            return ((b) create(dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    p.b(obj);
                    return this.$appId;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @NotNull
    public final g g(@NotNull String appId, @NotNull String appUserId, @NotNull String baseUrl, @NotNull String clientId) {
        k.e(appId, "appId");
        k.e(appUserId, "appUserId");
        k.e(baseUrl, "baseUrl");
        k.e(clientId, "clientId");
        return new g(appId, appUserId, e(baseUrl, o0.g(t.a("x-smooch-appid", new c(appId, (kotlin.coroutines.d<? super c>) null)), t.a("x-smooch-clientid", new C0514d(clientId, (kotlin.coroutines.d<? super C0514d>) null)))), this.c);
    }

    @f(c = "zendesk.conversationkit.android.internal.rest.RestClientFactory$createUserRestClient$1", f = "RestClientFactory.kt", l = {}, m = "invokeSuspend")
    /* compiled from: RestClientFactory.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.l implements l<kotlin.coroutines.d<? super String>, Object> {
        final /* synthetic */ String $appId;
        int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(String str, kotlin.coroutines.d<? super c> dVar) {
            super(1, dVar);
            this.$appId = str;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.$appId, dVar);
        }

        @Nullable
        public final Object invoke(@Nullable kotlin.coroutines.d<? super String> dVar) {
            return ((c) create(dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    p.b(obj);
                    return this.$appId;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @f(c = "zendesk.conversationkit.android.internal.rest.RestClientFactory$createUserRestClient$2", f = "RestClientFactory.kt", l = {}, m = "invokeSuspend")
    /* renamed from: zendesk.conversationkit.android.internal.rest.d$d  reason: collision with other inner class name */
    /* compiled from: RestClientFactory.kt */
    public static final class C0514d extends kotlin.coroutines.jvm.internal.l implements l<kotlin.coroutines.d<? super String>, Object> {
        final /* synthetic */ String $clientId;
        int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0514d(String str, kotlin.coroutines.d<? super C0514d> dVar) {
            super(1, dVar);
            this.$clientId = str;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new C0514d(this.$clientId, dVar);
        }

        @Nullable
        public final Object invoke(@Nullable kotlin.coroutines.d<? super String> dVar) {
            return ((C0514d) create(dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    p.b(obj);
                    return this.$clientId;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    static /* synthetic */ f f(d dVar, String str, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            set = o0.d();
        }
        return dVar.e(str, set);
    }

    private final f e(String baseUrl, Set<? extends n<String, ? extends l<? super kotlin.coroutines.d<? super String>, ? extends Object>>> headers) {
        okhttp3.logging.a logging = new okhttp3.logging.a((a.b) null, 1, (DefaultConstructorMarker) null);
        okhttp3.logging.a $this$createSunshineConversationsApi_u24lambda_u2d0 = logging;
        $this$createSunshineConversationsApi_u24lambda_u2d0.d(a.C0473a.NONE);
        $this$createSunshineConversationsApi_u24lambda_u2d0.c("Authorization");
        Object b2 = b(baseUrl, a(o0.g(new zendesk.okhttp.a(p0.i(this.b, headers)), logging))).b(f.class);
        k.d(b2, "buildRetrofit(baseUrl, o…ersationsApi::class.java)");
        return (f) b2;
    }

    private final z a(Set<? extends w> interceptors) {
        z.a builder = new z.a();
        for (w interceptor : interceptors) {
            builder.a(interceptor);
        }
        builder.d(new okhttp3.c(this.d, 20971520));
        return builder.c();
    }

    private final retrofit2.t b(String baseUrl, z okHttpClient) {
        String actualUrl;
        if (kotlin.text.w.x(baseUrl, "/", false, 2, (Object) null)) {
            actualUrl = baseUrl;
        } else {
            actualUrl = k.l(baseUrl, "/");
        }
        retrofit2.t e2 = new t.b().c(actualUrl).g(okHttpClient).b(this.e).e();
        k.d(e2, "Builder()\n            .b…ory)\n            .build()");
        return e2;
    }

    /* compiled from: RestClientFactory.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        @VisibleForTesting
        public final r a() {
            r c = new r.b().a(com.squareup.moshi.adapters.b.b(SendMessageDto.class, IjkMediaMeta.IJKM_KEY_TYPE).c(SendMessageDto.Text.class, "text").c(SendMessageDto.FormResponse.class, "formResponse")).a(com.squareup.moshi.adapters.b.b(SendFieldResponseDto.class, IjkMediaMeta.IJKM_KEY_TYPE).c(SendFieldResponseDto.Text.class, "text").c(SendFieldResponseDto.Email.class, "email").c(SendFieldResponseDto.Select.class, "select")).b(Date.class, new com.squareup.moshi.adapters.c()).c();
            k.d(c, "Builder()\n            .a…r())\n            .build()");
            return c;
        }
    }
}
