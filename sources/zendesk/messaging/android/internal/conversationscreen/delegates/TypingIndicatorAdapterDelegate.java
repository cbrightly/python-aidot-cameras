package zendesk.messaging.android.internal.conversationscreen.delegates;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.bean.Constants;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import zendesk.messaging.R$color;
import zendesk.messaging.R$id;
import zendesk.messaging.R$layout;
import zendesk.messaging.android.internal.adapterdelegate.b;
import zendesk.messaging.android.internal.conversationscreen.messagelog.e;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.avatar.AvatarImageView;
import zendesk.ui.android.conversation.avatar.c;
import zendesk.ui.android.conversation.receipt.MessageReceiptView;

/* compiled from: TypingIndicatorAdapterDelegate.kt */
public final class TypingIndicatorAdapterDelegate extends b<b.d, zendesk.messaging.android.internal.model.b, ViewHolder> {
    /* access modifiers changed from: protected */
    /* renamed from: h */
    public boolean d(@NotNull zendesk.messaging.android.internal.model.b item, @NotNull List<? extends zendesk.messaging.android.internal.model.b> items, int position) {
        k.e(item, "item");
        k.e(items, FirebaseAnalytics.Param.ITEMS);
        return item instanceof b.d;
    }

    @NotNull
    /* renamed from: j */
    public ViewHolder c(@NotNull ViewGroup parent) {
        k.e(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R$layout.zma_view_message_log_entry_message_container, parent, false);
        k.d(view, "from(parent.context)\n   …container, parent, false)");
        return new ViewHolder(view);
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public void f(@NotNull b.d item, @NotNull ViewHolder holder, @NotNull List<? extends Object> payloads) {
        k.e(item, "item");
        k.e(holder, "holder");
        k.e(payloads, "payloads");
        holder.b(item);
    }

    /* compiled from: TypingIndicatorAdapterDelegate.kt */
    public static final class ViewHolder extends RecyclerView.ViewHolder {
        @NotNull
        private final TextView a;
        /* access modifiers changed from: private */
        @NotNull
        public final AvatarImageView b;
        @NotNull
        private final LinearLayout c;
        @NotNull
        private final MessageReceiptView d;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            k.e(itemView, "itemView");
            View findViewById = itemView.findViewById(R$id.zma_message_label);
            k.d(findViewById, "itemView.findViewById(R.id.zma_message_label)");
            this.a = (TextView) findViewById;
            View findViewById2 = itemView.findViewById(R$id.zma_avatar_view);
            k.d(findViewById2, "itemView.findViewById(R.id.zma_avatar_view)");
            this.b = (AvatarImageView) findViewById2;
            View findViewById3 = itemView.findViewById(R$id.zma_message_content);
            k.d(findViewById3, "itemView.findViewById(R.id.zma_message_content)");
            this.c = (LinearLayout) findViewById3;
            View findViewById4 = itemView.findViewById(R$id.zma_message_receipt);
            k.d(findViewById4, "itemView.findViewById(R.id.zma_message_receipt)");
            this.d = (MessageReceiptView) findViewById4;
        }

        public final void b(@NotNull b.d item) {
            k.e(item, "item");
            this.d.setVisibility(8);
            this.a.setVisibility(8);
            d();
            c(item.a());
        }

        private final void d() {
            LinearLayout $this$renderContent_u24lambda_u2d0 = this.c;
            $this$renderContent_u24lambda_u2d0.removeAllViews();
            $this$renderContent_u24lambda_u2d0.addView(e.a.n(this.c));
            $this$renderContent_u24lambda_u2d0.getLayoutParams().width = -2;
            $this$renderContent_u24lambda_u2d0.requestLayout();
        }

        /* compiled from: TypingIndicatorAdapterDelegate.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.avatar.a, zendesk.ui.android.conversation.avatar.a> {
            final /* synthetic */ String $avatarUrl;
            final /* synthetic */ ViewHolder this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String str, ViewHolder viewHolder) {
                super(1);
                this.$avatarUrl = str;
                this.this$0 = viewHolder;
            }

            @NotNull
            public final zendesk.ui.android.conversation.avatar.a invoke(@NotNull zendesk.ui.android.conversation.avatar.a rendering) {
                k.e(rendering, "rendering");
                return rendering.b().d(new C0524a(this.$avatarUrl, this.this$0)).a();
            }

            /* renamed from: zendesk.messaging.android.internal.conversationscreen.delegates.TypingIndicatorAdapterDelegate$ViewHolder$a$a  reason: collision with other inner class name */
            /* compiled from: TypingIndicatorAdapterDelegate.kt */
            public static final class C0524a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.avatar.b, zendesk.ui.android.conversation.avatar.b> {
                final /* synthetic */ String $avatarUrl;
                final /* synthetic */ ViewHolder this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0524a(String str, ViewHolder viewHolder) {
                    super(1);
                    this.$avatarUrl = str;
                    this.this$0 = viewHolder;
                }

                @NotNull
                public final zendesk.ui.android.conversation.avatar.b invoke(@NotNull zendesk.ui.android.conversation.avatar.b state) {
                    k.e(state, Constants.ACTION_STATE);
                    return zendesk.ui.android.conversation.avatar.b.b(state, Uri.parse(this.$avatarUrl), false, 0, Integer.valueOf(ContextCompat.getColor(this.this$0.b.getContext(), R$color.zma_color_message_inbound_background)), c.CIRCLE, 6, (Object) null);
                }
            }
        }

        private final void c(String avatarUrl) {
            this.b.a(new a(avatarUrl, this));
            this.b.setVisibility(0);
        }
    }
}
