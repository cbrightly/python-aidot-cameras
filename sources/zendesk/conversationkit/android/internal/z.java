package zendesk.conversationkit.android.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.util.Locale;
import kotlin.collections.o0;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.p;
import kotlin.t;
import kotlin.x;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.v2;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.rest.d;

/* compiled from: Environment.kt */
public final class z implements t {
    @NotNull
    private final j b;
    /* access modifiers changed from: private */
    @NotNull
    public final String c;
    @NotNull
    private final File d;
    @NotNull
    private final String e;
    @NotNull
    private final x f;
    @NotNull
    private final b0 g;
    @NotNull
    private final h h;
    @NotNull
    private final zendesk.conversationkit.android.internal.rest.b i;
    @NotNull
    private final d j;
    @Nullable
    private final ConnectivityManager k;

    public z(@NotNull Context context, @NotNull j dispatchers) {
        k.e(context, "context");
        k.e(dispatchers, "dispatchers");
        this.b = dispatchers;
        this.c = "conversation-kit";
        File file = new File(context.getCacheDir(), "zendesk.conversationkit");
        this.d = file;
        this.e = "0.12.0";
        this.f = new x(context);
        this.g = new b0(context, (zendesk.storage.android.b) null, 2, (DefaultConstructorMarker) null);
        String g2 = g();
        x e2 = e();
        String languageTag = Locale.getDefault().toLanguageTag();
        k.d(languageTag, "getDefault().toLanguageTag()");
        this.h = new h("conversation-kit", g2, e2, languageTag);
        zendesk.conversationkit.android.internal.rest.b bVar = new zendesk.conversationkit.android.internal.rest.b(context);
        this.i = bVar;
        this.j = new d(o0.g(t.a("x-smooch-appname", new a(this, (kotlin.coroutines.d<? super a>) null)), t.a("x-smooch-sdk", new b(this, (kotlin.coroutines.d<? super b>) null)), t.a("User-Agent", new c(this, (kotlin.coroutines.d<? super c>) null))), bVar, file);
        this.k = (ConnectivityManager) ContextCompat.getSystemService(context, ConnectivityManager.class);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ z(Context context, j jVar, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? new m() : jVar);
    }

    @NotNull
    public String g() {
        return this.e;
    }

    @NotNull
    public x e() {
        return this.f;
    }

    @NotNull
    public b0 h() {
        return this.g;
    }

    @NotNull
    public d f() {
        return this.j;
    }

    @f(c = "zendesk.conversationkit.android.internal.MainEnvironment$restClientFactory$1", f = "Environment.kt", l = {}, m = "invokeSuspend")
    /* compiled from: Environment.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<kotlin.coroutines.d<? super String>, Object> {
        int label;
        final /* synthetic */ z this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(z zVar, kotlin.coroutines.d<? super a> dVar) {
            super(1, dVar);
            this.this$0 = zVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new a(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@Nullable kotlin.coroutines.d<? super String> dVar) {
            return ((a) create(dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    p.b(obj);
                    return this.this$0.e().b();
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @f(c = "zendesk.conversationkit.android.internal.MainEnvironment$restClientFactory$2", f = "Environment.kt", l = {}, m = "invokeSuspend")
    /* compiled from: Environment.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<kotlin.coroutines.d<? super String>, Object> {
        int label;
        final /* synthetic */ z this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(z zVar, kotlin.coroutines.d<? super b> dVar) {
            super(1, dVar);
            this.this$0 = zVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull kotlin.coroutines.d<?> dVar) {
            return new b(this.this$0, dVar);
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
                    return "android/" + this.this$0.c + '/' + this.this$0.g();
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @f(c = "zendesk.conversationkit.android.internal.MainEnvironment$restClientFactory$3", f = "Environment.kt", l = {}, m = "invokeSuspend")
    /* compiled from: Environment.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<kotlin.coroutines.d<? super String>, Object> {
        int label;
        final /* synthetic */ z this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(z zVar, kotlin.coroutines.d<? super c> dVar) {
            super(1, dVar);
            this.this$0 = zVar;
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
                    return this.this$0.e().b() + '/' + this.this$0.e().d() + " (" + this.this$0.e().f() + ' ' + this.this$0.e().g() + "; Android " + this.this$0.e().i() + ')';
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @NotNull
    public i b() {
        return new i(this.k, d());
    }

    @NotNull
    public l a() {
        zendesk.conversationkit.android.internal.faye.c sunCoFayeClientFactory = new zendesk.conversationkit.android.internal.faye.c(d());
        l conversationKitStore = new l(new r(new p(), new b(f(), sunCoFayeClientFactory, h(), this.h, this.i)), d(), (j) null, 4, (DefaultConstructorMarker) null);
        sunCoFayeClientFactory.b(conversationKitStore);
        return conversationKitStore;
    }

    @NotNull
    public kotlinx.coroutines.o0 d() {
        return p0.a(this.b.b().plus(v2.b((z1) null, 1, (Object) null)));
    }
}
