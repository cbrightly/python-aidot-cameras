package zendesk.messaging.android.internal.conversationscreen.delegates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.bean.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.r;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.MessageAction;
import zendesk.messaging.R$id;
import zendesk.messaging.R$layout;
import zendesk.messaging.android.internal.adapterdelegate.b;
import zendesk.messaging.android.internal.conversationscreen.messagelog.f;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.quickreply.QuickReplyView;
import zendesk.ui.android.conversation.quickreply.d;
import zendesk.ui.android.conversation.quickreply.e;

/* compiled from: QuickReplyAdapterDelegate.kt */
public final class QuickReplyAdapterDelegate extends b<b.C0546b, zendesk.messaging.android.internal.model.b, ViewHolder> {
    @NotNull
    private l<? super MessageAction.Reply, x> a;
    @Nullable
    @ColorInt
    private Integer b;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ QuickReplyAdapterDelegate(l<MessageAction.Reply, x> lVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? f.e() : lVar);
    }

    public final void k(@NotNull l<? super MessageAction.Reply, x> lVar) {
        k.e(lVar, "<set-?>");
        this.a = lVar;
    }

    public QuickReplyAdapterDelegate(@NotNull l<? super MessageAction.Reply, x> onOptionSelected) {
        k.e(onOptionSelected, "onOptionSelected");
        this.a = onOptionSelected;
    }

    public final void l(@Nullable Integer num) {
        this.b = num;
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public boolean d(@NotNull zendesk.messaging.android.internal.model.b item, @NotNull List<? extends zendesk.messaging.android.internal.model.b> items, int position) {
        k.e(item, "item");
        k.e(items, FirebaseAnalytics.Param.ITEMS);
        return item instanceof b.C0546b;
    }

    @NotNull
    /* renamed from: j */
    public ViewHolder c(@NotNull ViewGroup parent) {
        k.e(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R$layout.zma_view_message_log_entry_quick_reply, parent, false);
        k.d(view, "from(parent.context)\n   …ick_reply, parent, false)");
        return new ViewHolder(view, this.b);
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public void f(@NotNull b.C0546b item, @NotNull ViewHolder holder, @NotNull List<? extends Object> payloads) {
        k.e(item, "item");
        k.e(holder, "holder");
        k.e(payloads, "payloads");
        holder.b(item, this.a);
    }

    /* compiled from: QuickReplyAdapterDelegate.kt */
    public static final class ViewHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        @Nullable
        public final Integer a;
        @NotNull
        private final QuickReplyView b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ViewHolder(@NotNull View itemView, @Nullable @ColorInt Integer quickReplyColor) {
            super(itemView);
            k.e(itemView, "itemView");
            this.a = quickReplyColor;
            View findViewById = itemView.findViewById(R$id.zma_quick_reply);
            k.d(findViewById, "itemView.findViewById(R.id.zma_quick_reply)");
            this.b = (QuickReplyView) findViewById;
        }

        /* compiled from: QuickReplyAdapterDelegate.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<d, d> {
            final /* synthetic */ b.C0546b $item;
            final /* synthetic */ l<MessageAction.Reply, x> $onReplyActionSelected;
            final /* synthetic */ ViewHolder this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b.C0546b bVar, ViewHolder viewHolder, l<? super MessageAction.Reply, x> lVar) {
                super(1);
                this.$item = bVar;
                this.this$0 = viewHolder;
                this.$onReplyActionSelected = lVar;
            }

            @NotNull
            public final d invoke(@NotNull d quickReplyRendering) {
                k.e(quickReplyRendering, "quickReplyRendering");
                return quickReplyRendering.c().g(new C0523a(this.$item, this.this$0)).d(new b(this.$onReplyActionSelected, this.$item)).a();
            }

            /* renamed from: zendesk.messaging.android.internal.conversationscreen.delegates.QuickReplyAdapterDelegate$ViewHolder$a$a  reason: collision with other inner class name */
            /* compiled from: QuickReplyAdapterDelegate.kt */
            public static final class C0523a extends kotlin.jvm.internal.l implements l<e, e> {
                final /* synthetic */ b.C0546b $item;
                final /* synthetic */ ViewHolder this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0523a(b.C0546b bVar, ViewHolder viewHolder) {
                    super(1);
                    this.$item = bVar;
                    this.this$0 = viewHolder;
                }

                @NotNull
                public final e invoke(@NotNull e state) {
                    k.e(state, Constants.ACTION_STATE);
                    Iterable<MessageAction.Reply> $this$mapTo$iv$iv = this.$item.b();
                    List quickReplyOptions = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                    for (MessageAction.Reply actionReply : $this$mapTo$iv$iv) {
                        quickReplyOptions.add(new zendesk.ui.android.conversation.quickreply.a(actionReply.b(), actionReply.e()));
                    }
                    return state.a(quickReplyOptions, this.this$0.a);
                }
            }

            /* compiled from: QuickReplyAdapterDelegate.kt */
            public static final class b extends kotlin.jvm.internal.l implements l<zendesk.ui.android.conversation.quickreply.a, x> {
                final /* synthetic */ b.C0546b $item;
                final /* synthetic */ l<MessageAction.Reply, x> $onReplyActionSelected;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                b(l<? super MessageAction.Reply, x> lVar, b.C0546b bVar) {
                    super(1);
                    this.$onReplyActionSelected = lVar;
                    this.$item = bVar;
                }

                public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                    invoke((zendesk.ui.android.conversation.quickreply.a) p1);
                    return x.a;
                }

                public final void invoke(@NotNull zendesk.ui.android.conversation.quickreply.a clickedOption) {
                    k.e(clickedOption, "clickedOption");
                    l<MessageAction.Reply, x> lVar = this.$onReplyActionSelected;
                    for (T next : this.$item.b()) {
                        if (k.a(((MessageAction.Reply) next).b(), clickedOption.a())) {
                            lVar.invoke(next);
                            return;
                        }
                    }
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
            }
        }

        public final void b(@NotNull b.C0546b item, @NotNull l<? super MessageAction.Reply, x> onReplyActionSelected) {
            k.e(item, "item");
            k.e(onReplyActionSelected, "onReplyActionSelected");
            this.b.a(new a(item, this, onReplyActionSelected));
        }
    }
}
