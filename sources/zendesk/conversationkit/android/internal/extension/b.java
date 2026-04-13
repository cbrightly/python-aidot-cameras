package zendesk.conversationkit.android.internal.extension;

import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.channels.s;
import kotlinx.coroutines.channels.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.d;
import zendesk.conversationkit.android.e;

/* compiled from: ConversationKit.kt */
public final class b {

    @f(c = "zendesk.conversationkit.android.internal.extension.ConversationKitKt$eventFlow$1", f = "ConversationKit.kt", l = {18}, m = "invokeSuspend")
    /* compiled from: ConversationKit.kt */
    public static final class a extends l implements p<u<? super d>, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ zendesk.conversationkit.android.b $this_eventFlow;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(zendesk.conversationkit.android.b bVar, kotlin.coroutines.d<? super a> dVar) {
            super(2, dVar);
            this.$this_eventFlow = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            a aVar = new a(this.$this_eventFlow, dVar);
            aVar.L$0 = obj;
            return aVar;
        }

        @Nullable
        public final Object invoke(@NotNull u<? super d> uVar, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((a) create(uVar, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    u $this$callbackFlow = (u) this.L$0;
                    e eventListener = new a($this$callbackFlow);
                    this.$this_eventFlow.i(eventListener);
                    C0508a aVar = new C0508a(this.$this_eventFlow, eventListener);
                    this.label = 1;
                    if (s.a($this$callbackFlow, aVar, this) != d) {
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

        /* access modifiers changed from: private */
        public static final void a(u $$this$callbackFlow, d it) {
            $$this$callbackFlow.p(it);
        }

        /* renamed from: zendesk.conversationkit.android.internal.extension.b$a$a  reason: collision with other inner class name */
        /* compiled from: ConversationKit.kt */
        public static final class C0508a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ e $eventListener;
            final /* synthetic */ zendesk.conversationkit.android.b $this_eventFlow;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0508a(zendesk.conversationkit.android.b bVar, e eVar) {
                super(0);
                this.$this_eventFlow = bVar;
                this.$eventListener = eVar;
            }

            public final void invoke() {
                this.$this_eventFlow.a(this.$eventListener);
            }
        }
    }

    @NotNull
    public static final kotlinx.coroutines.flow.c<d> a(@NotNull zendesk.conversationkit.android.b $this$eventFlow) {
        k.e($this$eventFlow, "<this>");
        return kotlinx.coroutines.flow.e.e(new a($this$eventFlow, (kotlin.coroutines.d<? super a>) null));
    }
}
