package zendesk.messaging.android.internal.conversationscreen.delegates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.bean.Constants;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.messaging.R$id;
import zendesk.messaging.R$layout;
import zendesk.messaging.android.internal.adapterdelegate.b;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.unreadmessagedivider.UnreadMessageDividerView;

/* compiled from: UnreadMessagesDividerAdapterDelegate.kt */
public final class UnreadMessagesDividerAdapterDelegate extends b<b.e, zendesk.messaging.android.internal.model.b, ViewHolder> {
    @Nullable
    @ColorInt
    private Integer a;

    @Nullable
    public final Integer h() {
        return this.a;
    }

    public final void l(@Nullable Integer num) {
        this.a = num;
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public boolean d(@NotNull zendesk.messaging.android.internal.model.b item, @NotNull List<? extends zendesk.messaging.android.internal.model.b> items, int position) {
        k.e(item, "item");
        k.e(items, FirebaseAnalytics.Param.ITEMS);
        return item instanceof b.e;
    }

    @NotNull
    /* renamed from: k */
    public ViewHolder c(@NotNull ViewGroup parent) {
        k.e(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R$layout.zma_view_message_log_entry_unread_messages_divider, parent, false);
        k.d(view, "from(parent.context)\n   …s_divider, parent, false)");
        return new ViewHolder(view, this.a);
    }

    /* access modifiers changed from: protected */
    /* renamed from: j */
    public void f(@NotNull b.e item, @NotNull ViewHolder holder, @NotNull List<? extends Object> payloads) {
        k.e(item, "item");
        k.e(holder, "holder");
        k.e(payloads, "payloads");
        holder.b(item);
    }

    /* compiled from: UnreadMessagesDividerAdapterDelegate.kt */
    public static final class ViewHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        @Nullable
        public final Integer a;
        @NotNull
        private final UnreadMessageDividerView b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ViewHolder(@NotNull View itemView, @Nullable @ColorInt Integer dividerColor) {
            super(itemView);
            k.e(itemView, "itemView");
            this.a = dividerColor;
            View findViewById = itemView.findViewById(R$id.zma_unread_message_divider);
            k.d(findViewById, "itemView.findViewById(\n …message_divider\n        )");
            this.b = (UnreadMessageDividerView) findViewById;
        }

        /* compiled from: UnreadMessagesDividerAdapterDelegate.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.unreadmessagedivider.a, zendesk.ui.android.conversation.unreadmessagedivider.a> {
            final /* synthetic */ b.e $item;
            final /* synthetic */ ViewHolder this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b.e eVar, ViewHolder viewHolder) {
                super(1);
                this.$item = eVar;
                this.this$0 = viewHolder;
            }

            @NotNull
            public final zendesk.ui.android.conversation.unreadmessagedivider.a invoke(@NotNull zendesk.ui.android.conversation.unreadmessagedivider.a unreadMessageDividerRendering) {
                k.e(unreadMessageDividerRendering, "unreadMessageDividerRendering");
                return unreadMessageDividerRendering.b().d(new C0525a(this.$item, this.this$0)).a();
            }

            /* renamed from: zendesk.messaging.android.internal.conversationscreen.delegates.UnreadMessagesDividerAdapterDelegate$ViewHolder$a$a  reason: collision with other inner class name */
            /* compiled from: UnreadMessagesDividerAdapterDelegate.kt */
            public static final class C0525a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.unreadmessagedivider.b, zendesk.ui.android.conversation.unreadmessagedivider.b> {
                final /* synthetic */ b.e $item;
                final /* synthetic */ ViewHolder this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0525a(b.e eVar, ViewHolder viewHolder) {
                    super(1);
                    this.$item = eVar;
                    this.this$0 = viewHolder;
                }

                @NotNull
                public final zendesk.ui.android.conversation.unreadmessagedivider.b invoke(@NotNull zendesk.ui.android.conversation.unreadmessagedivider.b state) {
                    k.e(state, Constants.ACTION_STATE);
                    return state.a(this.$item.b(), this.this$0.a);
                }
            }
        }

        public final void b(@NotNull b.e item) {
            k.e(item, "item");
            this.b.a(new a(item, this));
        }
    }
}
