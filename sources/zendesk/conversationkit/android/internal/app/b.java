package zendesk.conversationkit.android.internal.app;

import com.google.android.libraries.places.api.model.PlaceTypes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.o;
import kotlin.reflect.k;
import kotlin.x;
import kotlinx.coroutines.h;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p1;
import kotlinx.coroutines.r1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.User;

/* compiled from: AppStorage.kt */
public final class b {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    static final /* synthetic */ k<Object>[] b = {a0.f(new o(b.class, "persistedUser", "getPersistedUser()Lzendesk/conversationkit/android/model/User;", 0))};
    @NotNull
    private final p1 c;
    @NotNull
    private final zendesk.storage.android.a d;

    public b(@NotNull zendesk.storage.android.c storage) {
        kotlin.jvm.internal.k.e(storage, PlaceTypes.STORAGE);
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        kotlin.jvm.internal.k.d(newSingleThreadExecutor, "newSingleThreadExecutor()");
        this.c = r1.a(newSingleThreadExecutor);
        this.d = new zendesk.storage.android.a(storage, "PERSISTED_USER", User.class);
    }

    /* access modifiers changed from: private */
    public final User d() {
        return (User) this.d.a(this, b[0]);
    }

    /* access modifiers changed from: private */
    public final void f(User user) {
        this.d.b(this, b[0], user);
    }

    @f(c = "zendesk.conversationkit.android.internal.app.AppStorage$getUser$2", f = "AppStorage.kt", l = {}, m = "invokeSuspend")
    /* compiled from: AppStorage.kt */
    public static final class c extends l implements p<o0, kotlin.coroutines.d<? super User>, Object> {
        int label;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(b bVar, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super User> dVar) {
            return ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    return this.this$0.d();
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object e(@NotNull kotlin.coroutines.d<? super User> $completion) {
        return h.g(this.c, new c(this, (kotlin.coroutines.d<? super c>) null), $completion);
    }

    @f(c = "zendesk.conversationkit.android.internal.app.AppStorage$setUser$2", f = "AppStorage.kt", l = {}, m = "invokeSuspend")
    /* compiled from: AppStorage.kt */
    public static final class d extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ User $user;
        int label;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(b bVar, User user, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = bVar;
            this.$user = user;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new d(this.this$0, this.$user, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((d) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.f(this.$user);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object g(@NotNull User user, @NotNull kotlin.coroutines.d<? super x> $completion) {
        Object g = h.g(this.c, new d(this, user, (kotlin.coroutines.d<? super d>) null), $completion);
        return g == kotlin.coroutines.intrinsics.c.d() ? g : x.a;
    }

    @f(c = "zendesk.conversationkit.android.internal.app.AppStorage$clearUser$2", f = "AppStorage.kt", l = {}, m = "invokeSuspend")
    /* renamed from: zendesk.conversationkit.android.internal.app.b$b  reason: collision with other inner class name */
    /* compiled from: AppStorage.kt */
    public static final class C0506b extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0506b(b bVar, kotlin.coroutines.d<? super C0506b> dVar) {
            super(2, dVar);
            this.this$0 = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new C0506b(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((C0506b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.f((User) null);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object c(@NotNull kotlin.coroutines.d<? super x> $completion) {
        Object g = h.g(this.c, new C0506b(this, (kotlin.coroutines.d<? super C0506b>) null), $completion);
        return g == kotlin.coroutines.intrinsics.c.d() ? g : x.a;
    }

    /* compiled from: AppStorage.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
