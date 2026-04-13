package zendesk.android.internal.network;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import kotlin.collections.o0;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.internal.k;
import kotlin.p;
import kotlin.t;
import kotlin.x;
import okhttp3.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.internal.o;

/* compiled from: HeaderFactory.kt */
public final class a {
    /* access modifiers changed from: private */
    @NotNull
    public final String a;
    /* access modifiers changed from: private */
    @NotNull
    public final b b;
    @NotNull
    private final o c = new o();

    public a(@NotNull String versionName, @NotNull b networkData) {
        k.e(versionName, "versionName");
        k.e(networkData, "networkData");
        this.a = versionName;
        this.b = networkData;
    }

    @NotNull
    public final zendesk.okhttp.a c() {
        return new zendesk.okhttp.a(o0.g(t.a("Accept", new C0502a((kotlin.coroutines.d<? super C0502a>) null)), t.a("Content-Type", new b((kotlin.coroutines.d<? super b>) null)), t.a(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, new c(this, (kotlin.coroutines.d<? super c>) null)), t.a("User-Agent", new d(this, (kotlin.coroutines.d<? super d>) null)), t.a("X-Zendesk-Client", new e((kotlin.coroutines.d<? super e>) null)), t.a("X-Zendesk-Client-Version", new f(this, (kotlin.coroutines.d<? super f>) null))));
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.internal.network.HeaderFactory$createHeaderInterceptor$1", f = "HeaderFactory.kt", l = {}, m = "invokeSuspend")
    /* renamed from: zendesk.android.internal.network.a$a  reason: collision with other inner class name */
    /* compiled from: HeaderFactory.kt */
    public static final class C0502a extends l implements kotlin.jvm.functions.l<kotlin.coroutines.d<? super String>, Object> {
        int label;

        C0502a(kotlin.coroutines.d<? super C0502a> dVar) {
            super(1, dVar);
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new C0502a(dVar);
        }

        @Nullable
        public final Object invoke(@Nullable kotlin.coroutines.d<? super String> dVar) {
            return ((C0502a) create(dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    p.b(obj);
                    return "application/json";
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.internal.network.HeaderFactory$createHeaderInterceptor$2", f = "HeaderFactory.kt", l = {}, m = "invokeSuspend")
    /* compiled from: HeaderFactory.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<kotlin.coroutines.d<? super String>, Object> {
        int label;

        b(kotlin.coroutines.d<? super b> dVar) {
            super(1, dVar);
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new b(dVar);
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
                    return "application/json";
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.internal.network.HeaderFactory$createHeaderInterceptor$3", f = "HeaderFactory.kt", l = {}, m = "invokeSuspend")
    /* compiled from: HeaderFactory.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<kotlin.coroutines.d<? super String>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(a aVar, kotlin.coroutines.d<? super c> dVar) {
            super(1, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, dVar);
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
                    return this.this$0.b.a();
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.internal.network.HeaderFactory$createHeaderInterceptor$4", f = "HeaderFactory.kt", l = {}, m = "invokeSuspend")
    /* compiled from: HeaderFactory.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<kotlin.coroutines.d<? super String>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(a aVar, kotlin.coroutines.d<? super d> dVar) {
            super(1, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new d(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@Nullable kotlin.coroutines.d<? super String> dVar) {
            return ((d) create(dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    p.b(obj);
                    return this.this$0.b.b();
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.internal.network.HeaderFactory$createHeaderInterceptor$5", f = "HeaderFactory.kt", l = {}, m = "invokeSuspend")
    /* compiled from: HeaderFactory.kt */
    public static final class e extends l implements kotlin.jvm.functions.l<kotlin.coroutines.d<? super String>, Object> {
        int label;

        e(kotlin.coroutines.d<? super e> dVar) {
            super(1, dVar);
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new e(dVar);
        }

        @Nullable
        public final Object invoke(@Nullable kotlin.coroutines.d<? super String> dVar) {
            return ((e) create(dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    p.b(obj);
                    return "mobile/android/sdk/messaging";
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.internal.network.HeaderFactory$createHeaderInterceptor$6", f = "HeaderFactory.kt", l = {}, m = "invokeSuspend")
    /* compiled from: HeaderFactory.kt */
    public static final class f extends l implements kotlin.jvm.functions.l<kotlin.coroutines.d<? super String>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(a aVar, kotlin.coroutines.d<? super f> dVar) {
            super(1, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new f(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@Nullable kotlin.coroutines.d<? super String> dVar) {
            return ((f) create(dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    p.b(obj);
                    return this.this$0.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @NotNull
    public final w d() {
        return this.c;
    }
}
