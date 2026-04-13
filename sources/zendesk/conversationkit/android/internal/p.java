package zendesk.conversationkit.android.internal;

import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.d;
import zendesk.conversationkit.android.g;
import zendesk.conversationkit.android.internal.o;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.User;

/* compiled from: EffectMapper.kt */
public final class p {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);

    @NotNull
    public final List<zendesk.conversationkit.android.d> a(@NotNull o effect) {
        kotlin.jvm.internal.k.e(effect, "effect");
        if (effect instanceof o.e) {
            return c((o.e) effect);
        }
        if (effect instanceof o.u) {
            return m((o.u) effect);
        }
        if (effect instanceof o.f) {
            return d((o.f) effect);
        }
        if (effect instanceof o.h) {
            return e((o.h) effect);
        }
        if (effect instanceof o.t) {
            return l((o.t) effect);
        }
        if (effect instanceof o.m) {
            return h((o.m) effect);
        }
        if (effect instanceof o.l) {
            return g((o.l) effect);
        }
        if (effect instanceof o.v) {
            return n((o.v) effect);
        }
        if (effect instanceof o.q) {
            return j((o.q) effect);
        }
        if (effect instanceof o.r) {
            return k((o.r) effect);
        }
        if (effect instanceof o.a) {
            return b((o.a) effect);
        }
        if (effect instanceof o.p) {
            return i((o.p) effect);
        }
        if (effect instanceof o.y) {
            return o((o.y) effect);
        }
        if (effect instanceof o.k) {
            return f((o.k) effect);
        }
        zendesk.logger.a.b("EffectMapper", "Effect " + effect + " has no public counterpart, skipping.", new Object[0]);
        return q.g();
    }

    /* compiled from: EffectMapper.kt */
    public static final class f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.k $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(o.k kVar) {
            super(1);
            this.$effect = kVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            zendesk.conversationkit.android.g result;
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            zendesk.conversationkit.android.g<Object> d = this.$effect.d();
            if (d instanceof g.a) {
                result = this.$effect.d();
            } else if (d instanceof g.b) {
                result = new g.b(x.a);
            } else {
                throw new NoWhenBranchMatchedException();
            }
            $this$mapEvents.b(new a(result));
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ zendesk.conversationkit.android.g<x> $result;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(zendesk.conversationkit.android.g<x> gVar) {
                super(0);
                this.$result = gVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.C0504d(this.$result);
            }
        }
    }

    private final List<zendesk.conversationkit.android.d> f(o.k effect) {
        return q.b(new f(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class o extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.y $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        o(o.y yVar) {
            super(1);
            this.$effect = yVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.y $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.y yVar) {
                super(0);
                this.$effect = yVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.j(((g.a) this.$effect.d()).a());
            }
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            if (this.$effect.d() instanceof g.a) {
                $this$mapEvents.b(new a(this.$effect));
            }
        }
    }

    private final List<zendesk.conversationkit.android.d> o(o.y effect) {
        return q.b(new o(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.e $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(o.e eVar) {
            super(1);
            this.$effect = eVar;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.e $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.e eVar) {
                super(0);
                this.$effect = eVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.b(this.$effect.a());
            }
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            $this$mapEvents.b(new a(this.$effect));
        }
    }

    private final List<zendesk.conversationkit.android.d> c(o.e effect) {
        return q.b(new c(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class m extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.u $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        m(o.u uVar) {
            super(1);
            this.$effect = uVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.u $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.u uVar) {
                super(0);
                this.$effect = uVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.k((User) ((g.b) this.$effect.b()).a());
            }
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            if (this.$effect.b() instanceof g.b) {
                $this$mapEvents.b(new a(this.$effect));
            }
        }
    }

    private final List<zendesk.conversationkit.android.d> m(o.u effect) {
        return q.b(new m(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.f $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(o.f fVar) {
            super(1);
            this.$effect = fVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.f $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.f fVar) {
                super(0);
                this.$effect = fVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.c((Conversation) ((g.b) this.$effect.b()).a());
            }
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            if (this.$effect.b() instanceof g.b) {
                $this$mapEvents.b(new a(this.$effect));
            }
        }
    }

    private final List<zendesk.conversationkit.android.d> d(o.f effect) {
        return q.b(new d(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.h $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(o.h hVar) {
            super(1);
            this.$effect = hVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.h $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.h hVar) {
                super(0);
                this.$effect = hVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.c((Conversation) ((g.b) this.$effect.b()).a());
            }
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            if (this.$effect.b() instanceof g.b) {
                $this$mapEvents.b(new a(this.$effect));
            }
        }
    }

    private final List<zendesk.conversationkit.android.d> e(o.h effect) {
        return q.b(new e(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class l extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.t $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        l(o.t tVar) {
            super(1);
            this.$effect = tVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.t $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.t tVar) {
                super(0);
                this.$effect = tVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.c((Conversation) ((g.b) this.$effect.b()).a());
            }
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            if (this.$effect.b() instanceof g.b) {
                $this$mapEvents.b(new a(this.$effect));
            }
        }
    }

    private final List<zendesk.conversationkit.android.d> l(o.t effect) {
        return q.b(new l(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class h extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.m $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(o.m mVar) {
            super(1);
            this.$effect = mVar;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.m $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.m mVar) {
                super(0);
                this.$effect = mVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.e(this.$effect.d(), this.$effect.c());
            }
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            $this$mapEvents.b(new a(this.$effect));
            $this$mapEvents.a(this.$effect.b(), b.INSTANCE);
        }

        /* compiled from: EffectMapper.kt */
        public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Conversation, zendesk.conversationkit.android.d> {
            public static final b INSTANCE = new b();

            b() {
                super(1);
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke(@NotNull Conversation conversation) {
                kotlin.jvm.internal.k.e(conversation, "conversation");
                return new d.c(conversation);
            }
        }
    }

    private final List<zendesk.conversationkit.android.d> h(o.m effect) {
        return q.b(new h(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class g extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.l $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(o.l lVar) {
            super(1);
            this.$effect = lVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Conversation, zendesk.conversationkit.android.d> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke(@NotNull Conversation conversation) {
                kotlin.jvm.internal.k.e(conversation, "conversation");
                return new d.c(conversation);
            }
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            $this$mapEvents.a(this.$effect.b(), a.INSTANCE);
        }
    }

    private final List<zendesk.conversationkit.android.d> g(o.l effect) {
        return q.b(new g(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class n extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.v $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        n(o.v vVar) {
            super(1);
            this.$effect = vVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            Message effectMessage;
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            zendesk.conversationkit.android.g<Message> e = this.$effect.e();
            if (e instanceof g.b) {
                effectMessage = (Message) ((g.b) this.$effect.e()).a();
            } else if (e instanceof g.a) {
                effectMessage = this.$effect.d();
            } else {
                throw new NoWhenBranchMatchedException();
            }
            $this$mapEvents.a(effectMessage, new a(this.$effect));
            $this$mapEvents.a(this.$effect.b(), b.INSTANCE);
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Message, zendesk.conversationkit.android.d> {
            final /* synthetic */ o.v $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.v vVar) {
                super(1);
                this.$effect = vVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke(@NotNull Message message) {
                kotlin.jvm.internal.k.e(message, "message");
                return new d.f(message, this.$effect.c());
            }
        }

        /* compiled from: EffectMapper.kt */
        public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Conversation, zendesk.conversationkit.android.d> {
            public static final b INSTANCE = new b();

            b() {
                super(1);
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke(@NotNull Conversation conversation) {
                kotlin.jvm.internal.k.e(conversation, "conversation");
                return new d.c(conversation);
            }
        }
    }

    private final List<zendesk.conversationkit.android.d> n(o.v effect) {
        return q.b(new n(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class j extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.q $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(o.q qVar) {
            super(1);
            this.$effect = qVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.q $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.q qVar) {
                super(0);
                this.$effect = qVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.h(this.$effect.b());
            }
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            $this$mapEvents.b(new a(this.$effect));
        }
    }

    private final List<zendesk.conversationkit.android.d> j(o.q effect) {
        return q.b(new j(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class k extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.r $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        k(o.r rVar) {
            super(1);
            this.$effect = rVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.r $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.r rVar) {
                super(0);
                this.$effect = rVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.i(this.$effect.c(), this.$effect.b());
            }
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            $this$mapEvents.b(new a(this.$effect));
        }
    }

    private final List<zendesk.conversationkit.android.d> k(o.r effect) {
        return q.b(new k(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.a $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(o.a aVar) {
            super(1);
            this.$effect = aVar;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.a $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.a aVar) {
                super(0);
                this.$effect = aVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.a(this.$effect.b());
            }
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            $this$mapEvents.b(new a(this.$effect));
            $this$mapEvents.a(this.$effect.c(), C0513b.INSTANCE);
        }

        /* renamed from: zendesk.conversationkit.android.internal.p$b$b  reason: collision with other inner class name */
        /* compiled from: EffectMapper.kt */
        public static final class C0513b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Conversation, zendesk.conversationkit.android.d> {
            public static final C0513b INSTANCE = new C0513b();

            C0513b() {
                super(1);
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke(@NotNull Conversation conversation) {
                kotlin.jvm.internal.k.e(conversation, "conversation");
                return new d.c(conversation);
            }
        }
    }

    private final List<zendesk.conversationkit.android.d> b(o.a effect) {
        return q.b(new b(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class i extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<v, x> {
        final /* synthetic */ o.p $effect;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(o.p pVar) {
            super(1);
            this.$effect = pVar;
        }

        /* compiled from: EffectMapper.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.conversationkit.android.d> {
            final /* synthetic */ o.p $effect;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(o.p pVar) {
                super(0);
                this.$effect = pVar;
            }

            @NotNull
            public final zendesk.conversationkit.android.d invoke() {
                return new d.g(this.$effect.b());
            }
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((v) p1);
            return x.a;
        }

        public final void invoke(@NotNull v $this$mapEvents) {
            kotlin.jvm.internal.k.e($this$mapEvents, "$this$mapEvents");
            $this$mapEvents.b(new a(this.$effect));
        }
    }

    private final List<zendesk.conversationkit.android.d> i(o.p effect) {
        return q.b(new i(effect));
    }

    /* compiled from: EffectMapper.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
