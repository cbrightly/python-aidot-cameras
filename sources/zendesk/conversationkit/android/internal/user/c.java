package zendesk.conversationkit.android.internal.user;

import com.google.android.libraries.places.api.model.PlaceTypes;
import com.meituan.robust.Constants;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.h;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p1;
import kotlinx.coroutines.r1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Conversation;

/* compiled from: UserStorage.kt */
public final class c {
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.storage.android.c a;
    @NotNull
    private final p1 b;

    public c(@NotNull zendesk.storage.android.c storage) {
        k.e(storage, PlaceTypes.STORAGE);
        this.a = storage;
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        k.d(newSingleThreadExecutor, "newSingleThreadExecutor()");
        this.b = r1.a(newSingleThreadExecutor);
    }

    @f(c = "zendesk.conversationkit.android.internal.user.UserStorage$clear$2", f = "UserStorage.kt", l = {}, m = "invokeSuspend")
    /* compiled from: UserStorage.kt */
    public static final class a extends l implements p<o0, d<? super x>, Object> {
        int label;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(c cVar, d<? super a> dVar) {
            super(2, dVar);
            this.this$0 = cVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new a(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.a.clear();
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object b(@NotNull d<? super x> $completion) {
        Object g = h.g(this.b, new a(this, (d<? super a>) null), $completion);
        return g == kotlin.coroutines.intrinsics.c.d() ? g : x.a;
    }

    @f(c = "zendesk.conversationkit.android.internal.user.UserStorage$getConversation$2", f = "UserStorage.kt", l = {}, m = "invokeSuspend")
    /* compiled from: UserStorage.kt */
    public static final class b extends l implements p<o0, d<? super Conversation>, Object> {
        final /* synthetic */ String $conversationId;
        int label;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(c cVar, String str, d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = cVar;
            this.$conversationId = str;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new b(this.this$0, this.$conversationId, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super Conversation> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Class cls = Conversation.class;
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    zendesk.storage.android.c $this$get$iv = this.this$0.a;
                    String key$iv = this.$conversationId;
                    String name = cls.getName();
                    switch (name.hashCode()) {
                        case -2056817302:
                            if (name.equals(Constants.LANG_INT)) {
                                return (Conversation) $this$get$iv.b(key$iv, Integer.TYPE);
                            }
                            break;
                        case -527879800:
                            if (name.equals(Constants.LANG_FLOAT)) {
                                return (Conversation) $this$get$iv.b(key$iv, Float.TYPE);
                            }
                            break;
                        case 344809556:
                            if (name.equals(Constants.LANG_BOOLEAN)) {
                                return (Conversation) $this$get$iv.b(key$iv, Boolean.TYPE);
                            }
                            break;
                        case 398795216:
                            if (name.equals(Constants.LANG_LONG)) {
                                return (Conversation) $this$get$iv.b(key$iv, Long.TYPE);
                            }
                            break;
                    }
                    return $this$get$iv.b(key$iv, cls);
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object c(@NotNull String conversationId, @NotNull d<? super Conversation> $completion) {
        return h.g(this.b, new b(this, conversationId, (d<? super b>) null), $completion);
    }

    @f(c = "zendesk.conversationkit.android.internal.user.UserStorage$setConversation$2", f = "UserStorage.kt", l = {}, m = "invokeSuspend")
    /* renamed from: zendesk.conversationkit.android.internal.user.c$c  reason: collision with other inner class name */
    /* compiled from: UserStorage.kt */
    public static final class C0516c extends l implements p<o0, d<? super x>, Object> {
        final /* synthetic */ Conversation $conversation;
        int label;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0516c(c cVar, Conversation conversation, d<? super C0516c> dVar) {
            super(2, dVar);
            this.this$0 = cVar;
            this.$conversation = conversation;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new C0516c(this.this$0, this.$conversation, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((C0516c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.a.a(this.$conversation.i(), this.$conversation, Conversation.class);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object d(@NotNull Conversation conversation, @NotNull d<? super x> $completion) {
        Object g = h.g(this.b, new C0516c(this, conversation, (d<? super C0516c>) null), $completion);
        return g == kotlin.coroutines.intrinsics.c.d() ? g : x.a;
    }
}
