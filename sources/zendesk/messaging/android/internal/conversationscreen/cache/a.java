package zendesk.messaging.android.internal.conversationscreen.cache;

import com.google.android.libraries.places.api.model.PlaceTypes;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.h;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessagingStorage.kt */
public final class a {
    @NotNull
    private final i0 a;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.storage.android.c b;

    public a(@NotNull i0 ioDispatcher, @NotNull zendesk.storage.android.c storage) {
        k.e(ioDispatcher, "ioDispatcher");
        k.e(storage, PlaceTypes.STORAGE);
        this.a = ioDispatcher;
        this.b = storage;
    }

    @f(c = "zendesk.messaging.android.internal.conversationscreen.cache.MessagingStorage$setMessagingPersistence$2", f = "MessagingStorage.kt", l = {}, m = "invokeSuspend")
    /* compiled from: MessagingStorage.kt */
    public static final class c extends l implements p<o0, d<? super x>, Object> {
        final /* synthetic */ MessagingUIPersistence $messagingUIPersistence;
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(a aVar, MessagingUIPersistence messagingUIPersistence, d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
            this.$messagingUIPersistence = messagingUIPersistence;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new c(this.this$0, this.$messagingUIPersistence, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.b.a(this.$messagingUIPersistence.d(), this.$messagingUIPersistence, MessagingUIPersistence.class);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object d(@NotNull MessagingUIPersistence messagingUIPersistence, @NotNull d<? super x> $completion) {
        Object g = h.g(this.a, new c(this, messagingUIPersistence, (d<? super c>) null), $completion);
        return g == kotlin.coroutines.intrinsics.c.d() ? g : x.a;
    }

    @f(c = "zendesk.messaging.android.internal.conversationscreen.cache.MessagingStorage$getMessagingPersistence$2", f = "MessagingStorage.kt", l = {}, m = "invokeSuspend")
    /* compiled from: MessagingStorage.kt */
    public static final class b extends l implements p<o0, d<? super MessagingUIPersistence>, Object> {
        final /* synthetic */ String $conversationId;
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(a aVar, String str, d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
            this.$conversationId = str;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new b(this.this$0, this.$conversationId, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super MessagingUIPersistence> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Class<zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence> r0 = zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence.class
                kotlin.coroutines.intrinsics.c.d()
                int r1 = r7.label
                switch(r1) {
                    case 0: goto L_0x0012;
                    default: goto L_0x000a;
                }
            L_0x000a:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L_0x0012:
                kotlin.p.b(r8)
                r1 = r7
                zendesk.messaging.android.internal.conversationscreen.cache.a r2 = r1.this$0
                zendesk.storage.android.c r2 = r2.b
                java.lang.String r3 = r1.$conversationId
                r4 = 0
                java.lang.String r5 = r0.getName()
                int r6 = r5.hashCode()
                switch(r6) {
                    case -2056817302: goto L_0x0061;
                    case -527879800: goto L_0x004f;
                    case 344809556: goto L_0x003d;
                    case 398795216: goto L_0x002b;
                    default: goto L_0x002a;
                }
            L_0x002a:
                goto L_0x0073
            L_0x002b:
                java.lang.String r6 = "java.lang.Long"
                boolean r5 = r5.equals(r6)
                if (r5 != 0) goto L_0x0034
                goto L_0x002a
            L_0x0034:
                java.lang.Class r0 = java.lang.Long.TYPE
                java.lang.Object r0 = r2.b(r3, r0)
                zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence r0 = (zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence) r0
                goto L_0x0077
            L_0x003d:
                java.lang.String r6 = "java.lang.Boolean"
                boolean r5 = r5.equals(r6)
                if (r5 != 0) goto L_0x0046
                goto L_0x002a
            L_0x0046:
                java.lang.Class r0 = java.lang.Boolean.TYPE
                java.lang.Object r0 = r2.b(r3, r0)
                zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence r0 = (zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence) r0
                goto L_0x0077
            L_0x004f:
                java.lang.String r6 = "java.lang.Float"
                boolean r5 = r5.equals(r6)
                if (r5 != 0) goto L_0x0058
                goto L_0x002a
            L_0x0058:
                java.lang.Class r0 = java.lang.Float.TYPE
                java.lang.Object r0 = r2.b(r3, r0)
                zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence r0 = (zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence) r0
                goto L_0x0077
            L_0x0061:
                java.lang.String r6 = "java.lang.Integer"
                boolean r5 = r5.equals(r6)
                if (r5 != 0) goto L_0x006a
                goto L_0x002a
            L_0x006a:
                java.lang.Class r0 = java.lang.Integer.TYPE
                java.lang.Object r0 = r2.b(r3, r0)
                zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence r0 = (zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence) r0
                goto L_0x0077
            L_0x0073:
                java.lang.Object r0 = r2.b(r3, r0)
            L_0x0077:
                zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence r0 = (zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence) r0
                if (r0 != 0) goto L_0x0085
                zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence r0 = new zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence
                java.lang.String r2 = r1.$conversationId
                r3 = 2
                r4 = 0
                r0.<init>(r2, r4, r3, r4)
            L_0x0085:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.cache.a.b.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Nullable
    public final Object c(@NotNull String conversationId, @NotNull d<? super MessagingUIPersistence> $completion) {
        return h.g(this.a, new b(this, conversationId, (d<? super b>) null), $completion);
    }

    @f(c = "zendesk.messaging.android.internal.conversationscreen.cache.MessagingStorage$clear$2", f = "MessagingStorage.kt", l = {}, m = "invokeSuspend")
    /* renamed from: zendesk.messaging.android.internal.conversationscreen.cache.a$a  reason: collision with other inner class name */
    /* compiled from: MessagingStorage.kt */
    public static final class C0521a extends l implements p<o0, d<? super x>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0521a(a aVar, d<? super C0521a> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new C0521a(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((C0521a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.b.clear();
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object b(@NotNull d<? super x> $completion) {
        Object g = h.g(this.a, new C0521a(this, (d<? super C0521a>) null), $completion);
        return g == kotlin.coroutines.intrinsics.c.d() ? g : x.a;
    }
}
