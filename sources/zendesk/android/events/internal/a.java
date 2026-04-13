package zendesk.android.events.internal;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.h;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ZendeskEventDispatcher.kt */
public final class a {
    @NotNull
    private final i0 a;
    /* access modifiers changed from: private */
    @NotNull
    public final Set<zendesk.android.events.b> b;

    public a() {
        this((i0) null, 1, (DefaultConstructorMarker) null);
    }

    public a(@NotNull i0 dispatcher) {
        k.e(dispatcher, "dispatcher");
        this.a = dispatcher;
        this.b = new LinkedHashSet();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(i0 i0Var, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? d1.c() : i0Var);
    }

    @f(c = "zendesk.android.events.internal.ZendeskEventDispatcher$addEventListener$2", f = "ZendeskEventDispatcher.kt", l = {}, m = "invokeSuspend")
    /* renamed from: zendesk.android.events.internal.a$a  reason: collision with other inner class name */
    /* compiled from: ZendeskEventDispatcher.kt */
    public static final class C0500a extends l implements p<o0, d<? super x>, Object> {
        final /* synthetic */ zendesk.android.events.b $listener;
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0500a(a aVar, zendesk.android.events.b bVar, d<? super C0500a> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
            this.$listener = bVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new C0500a(this.this$0, this.$listener, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((C0500a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.b.add(this.$listener);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object b(@NotNull zendesk.android.events.b listener, @NotNull d<? super x> $completion) {
        Object g = h.g(this.a, new C0500a(this, listener, (d<? super C0500a>) null), $completion);
        return g == c.d() ? g : x.a;
    }

    @f(c = "zendesk.android.events.internal.ZendeskEventDispatcher$notifyEventListeners$2", f = "ZendeskEventDispatcher.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ZendeskEventDispatcher.kt */
    public static final class b extends l implements p<o0, d<? super x>, Object> {
        final /* synthetic */ zendesk.android.events.a $event;
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(a aVar, zendesk.android.events.a aVar2, d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
            this.$event = aVar2;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            return new b(this.this$0, this.$event, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    Iterable<zendesk.android.events.b> $this$forEach$iv = this.this$0.b;
                    zendesk.android.events.a aVar = this.$event;
                    for (zendesk.android.events.b it : $this$forEach$iv) {
                        it.onEvent(aVar);
                    }
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object c(@NotNull zendesk.android.events.a event, @NotNull d<? super x> $completion) {
        Object g = h.g(this.a, new b(this, event, (d<? super b>) null), $completion);
        return g == c.d() ? g : x.a;
    }
}
